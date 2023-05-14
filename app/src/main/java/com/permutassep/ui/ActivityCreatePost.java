package com.permutassep.ui;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaActivityCreatepostBinding;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.WritePostPresenter;
import com.permutassep.presentation.utils.ComplexPreferences;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.WritePostView;
import com.permutassep.presentation.view.wizard.model.AbstractWizardModel;
import com.permutassep.presentation.view.wizard.model.ModelCallbacks;
import com.permutassep.presentation.view.wizard.model.Page;
import com.permutassep.presentation.view.wizard.model.PermutaSepWizardModel;
import com.permutassep.presentation.view.wizard.model.PostTextPage;
import com.permutassep.presentation.view.wizard.model.ProfessorCityFromPage;
import com.permutassep.presentation.view.wizard.model.ProfessorCityToPage;
import com.permutassep.presentation.view.wizard.ui.PageFragmentCallbacks;
import com.permutassep.presentation.view.wizard.ui.ReviewFragment;
import com.permutassep.presentation.view.wizard.ui.StepPagerStrip;

import java.util.List;

import javax.inject.Inject;

public class ActivityCreatePost extends BaseActivity implements PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks, HasComponent<PostComponent>, WritePostView {

    public static final String NEW_POST_KEY = "a_new_post";

    private CaActivityCreatepostBinding binding;
    @Inject
    WritePostPresenter writePostPresenter;
    private boolean mEditingAfterReview;
    private boolean mConsumePageSelectedEvent;
    private PostComponent postComponent;
    private MyPagerAdapter mPagerAdapter;
    private PostModel post;
    private List<Page> mCurrentPageSequence;
    private AbstractWizardModel mWizardModel = new PermutaSepWizardModel(this);
    private MaterialDialog progressDialog;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ActivityCreatePost.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CaActivityCreatepostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.initializeInjector();
        // setActionBar(toolbar);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.action_post);
        }

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        binding.pager.setAdapter(mPagerAdapter);
        binding.strip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (binding.pager.getCurrentItem() != position) {
                    binding.pager.setCurrentItem(position);
                }
            }
        });

        binding.pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                binding.strip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        binding.nextButton.setOnClickListener(view -> {

            if (binding.pager.getCurrentItem() == mCurrentPageSequence.size()) {


                new MaterialDialog.Builder(ActivityCreatePost.this).content(R.string.submit_confirm_message).positiveText(R.string.submit_confirm_button).negativeText(android.R.string.cancel).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        writePostPresenter.writePost(getPostModelFromWizard());
                    }
                }).show();
            } else {
                if (mEditingAfterReview) {
                    binding.pager.setCurrentItem(mPagerAdapter.getCount() - 1);
                } else {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                }
            }
        });

        binding.prevButton.setOnClickListener(v -> binding.pager.setCurrentItem(binding.pager.getCurrentItem() - 1));
        binding.retryView.btRetry.setOnClickListener(v -> writePostPresenter.writePost(getPostModelFromWizard()));

        onPageTreeChanged();
        updateBottomBar();
    }

    private void initializeInjector() {

        this.postComponent = DaggerPostComponent.builder().applicationComponent(getApplicationComponent()).activityModule(getActivityModule()).postModule(new PostModule()).build();
        this.postComponent.inject(this);
        this.writePostPresenter.setView(this, PrefUtils.getUser(ActivityCreatePost.this));
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        binding.strip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = binding.pager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            binding.nextButton.setText(R.string.finish);
            binding.nextButton.setBackgroundResource(R.drawable.finish_background);
            binding.nextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            binding.nextButton.setText(mEditingAfterReview ? R.string.review : R.string.next);
            binding.nextButton.setBackgroundResource(R.drawable.selectable_item_background);
            binding.nextButton.setTextAppearance(this, R.style.TextAppearanceCapturing);
            if (!mCurrentPageSequence.get(position).isCompleted()) {
                binding.nextButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            binding.nextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }
        binding.prevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                binding.pager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    private PostModel getPostModelFromWizard() {

        if (post == null) {

            post = new PostModel();
            post.setUser(PrefUtils.getUser(ActivityCreatePost.this));

            for (Page p : mWizardModel.getCurrentPageSequence()) {
                switch (p.getKey()) {
                    case PermutaSepWizardModel.CITY_FROM_KEY:
                        State sf = p.getData().getParcelable(ProfessorCityFromPage.STATE_DATA_KEY);
                        City cf = p.getData().getParcelable(ProfessorCityFromPage.MUNICIPALITY_DATA_KEY);
                        Town tf = p.getData().getParcelable(ProfessorCityFromPage.LOCALITY_DATA_KEY);

                        assert sf != null;
                        assert cf != null;
                        assert tf != null;

                        post.setStateFrom(String.valueOf(sf.getId()));
                        post.setCityFrom(String.valueOf(cf.getClaveMunicipio()));
                        post.setTownFrom(tf.getClave());
                        post.setLatFrom(tf.getLatitud());
                        post.setLonFrom(tf.getLongitud());
                        break;
                    case PermutaSepWizardModel.CITY_TO_KEY:
                        State st = p.getData().getParcelable(ProfessorCityToPage.STATE_TO_DATA_KEY);
                        City ct = p.getData().getParcelable(ProfessorCityToPage.MUNICIPALITY_TO_DATA_KEY);
                        Town tt = p.getData().getParcelable(ProfessorCityToPage.LOCALITY_TO_DATA_KEY);

                        assert st != null;
                        assert ct != null;
                        assert tt != null;

                        post.setStateTo(String.valueOf(st.getId()));
                        post.setCityTo(String.valueOf(ct.getClaveMunicipio()));
                        post.setTownTo(tt.getClave());
                        post.setLatTo(tt.getLatitud());
                        post.setLonTo(tt.getLongitud());

                        break;
                    case PermutaSepWizardModel.ACADEMIC_LEVEL_KEY:
                        post.setAcademicLevel(p.getData().getString(Page.SIMPLE_DATA_KEY));
                        break;
                    case PermutaSepWizardModel.POSITION_TYPE_KEY:
                        post.setPositionType(p.getData().getString(Page.SIMPLE_DATA_KEY));
                        break;
                    case PermutaSepWizardModel.WORKDAY_TYPE_KEY:
                        post.setWorkdayType(p.getData().getString(Page.SIMPLE_DATA_KEY));
                        break;
                    case PermutaSepWizardModel.TEACHING_CAREER_KEY:
                        post.setIsTeachingCareer(p.getData().getString(Page.SIMPLE_DATA_KEY).equals("Si"));
                        break;
                    case PermutaSepWizardModel.POST_TEXT_KEY:
                        post.setPostText(p.getData().getString(PostTextPage.TEXT_DATA_KEY));
                        break;
                }
            }
        }

        return post;
    }

    /**
     * Methods from the {@link WritePostView} interface
     */

    @Override
    public void writtenPost(final PostModel postModel) {
        new MaterialDialog.Builder(this).title(R.string.wizard_post_retry_dlg_success_post_title).content(R.string.wizard_post_retry_dlg_success_post_message).positiveText(android.R.string.ok).cancelable(false).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                ComplexPreferences.get(ActivityCreatePost.this).putObject(NEW_POST_KEY, postModel);
                finish();
            }
        }).show();
    }

    @Override
    public void showLoading() {

        progressDialog = new MaterialDialog.Builder(this).title(R.string.wizard_post_dlg_title).content(R.string.wizard_post_dlg_text).progress(true, 0).cancelable(false).progressIndeterminateStyle(false).show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void showRetry() {
        new MaterialDialog.Builder(this).title(R.string.ups).content(R.string.wizard_post_retry_dlg_message).positiveText(R.string.retry).onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                writePostPresenter.writePost(getPostModelFromWizard());
            }
        }).show();
    }

    @Override
    public void hideRetry() {
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return null;
    }

    /**
     * Method from the {@link HasComponent interface}
     */

    @Override
    public PostComponent getComponent() {
        return postComponent;
    }

    /**
     * The wizard adapter
     */
    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (i >= mCurrentPageSequence.size()) {
                return new ReviewFragment();
            }

            return mCurrentPageSequence.get(i).createFragment();
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        int getCutOffPage() {
            return mCutOffPage;
        }

        void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }
    }
}