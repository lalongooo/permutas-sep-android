package com.permutassep.presentation.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lalongooo.permutassep.R;
import com.permutassep.model.City;
import com.permutassep.model.PermutaSepWizardModel;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.presentation.internal.di.HasComponent;
import com.permutassep.presentation.internal.di.components.DaggerPostComponent;
import com.permutassep.presentation.internal.di.components.PostComponent;
import com.permutassep.presentation.internal.di.modules.PostModule;
import com.permutassep.presentation.model.PostModel;
import com.permutassep.presentation.presenter.WritePostPresenter;
import com.permutassep.presentation.utils.PrefUtils;
import com.permutassep.presentation.view.WritePostView;
import com.permutassep.presentation.view.wizard.model.AbstractWizardModel;
import com.permutassep.presentation.view.wizard.model.ModelCallbacks;
import com.permutassep.presentation.view.wizard.model.Page;
import com.permutassep.presentation.view.wizard.model.PostTextPage;
import com.permutassep.presentation.view.wizard.model.ProfessorCityFromPage;
import com.permutassep.presentation.view.wizard.model.ProfessorCityToPage;
import com.permutassep.presentation.view.wizard.ui.PageFragmentCallbacks;
import com.permutassep.presentation.view.wizard.ui.ReviewFragment;
import com.permutassep.presentation.view.wizard.ui.StepPagerStrip;

import java.util.List;

import javax.inject.Inject;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */


public class ActivityWritePost extends BaseActivity implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks,
        HasComponent<PostComponent>,
        WritePostView {

    private boolean suggestDataCompletion = false;
    private boolean mEditingAfterReview;
    private boolean mConsumePageSelectedEvent;

    @Inject
    Toolbar toolbar;
    @Inject
    WritePostPresenter writePostPresenter;

    private PostComponent postComponent;
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;
    private Button mNextButton;
    private Button mPrevButton;
    private StepPagerStrip mStepPagerStrip;

    private List<Page> mCurrentPageSequence;
    private AbstractWizardModel mWizardModel = new PermutaSepWizardModel(this);

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ActivityWritePost.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ca_activity_createpost);
        this.initializeInjector();
        this.setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.action_post);
        }

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mStepPagerStrip.setCurrentPage(position);

                if (mConsumePageSelectedEvent) {
                    mConsumePageSelectedEvent = false;
                    return;
                }

                mEditingAfterReview = false;
                updateBottomBar();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (suggestDataCompletion && mCurrentPageSequence.get(mPager.getCurrentItem()).getKey().equals(PermutaSepWizardModel.CONTACT_INFO_KEY)) {

                    DialogFragment dg = new DialogFragment() {

                        @NonNull
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.wizard_contact_suggest_data_completion_dialog_msg)
                                    .setPositiveButton(R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            suggestDataCompletion = false;
                                        }
                                    })
                                    .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "contact_data_dialog");

                } else if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    DialogFragment dg = new DialogFragment() {

                        @NonNull
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.submit_confirm_message)
                                    .setPositiveButton(R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            PostModel post = new PostModel();

                                            for (Page p : mWizardModel.getCurrentPageSequence()) {
                                                switch (p.getKey()) {
                                                    case PermutaSepWizardModel.CONTACT_INFO_KEY:
                                                        post.setUser(PrefUtils.getUser(ActivityWritePost.this));
                                                        break;
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
                                            writePostPresenter.writePost(post);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "place_order_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();
    }

    private void initializeInjector() {

        this.postComponent = DaggerPostComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .postModule(new PostModule())
                .build();
        this.postComponent.inject(this);
        this.writePostPresenter.setView(this);
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();
        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview ? R.string.review : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceCapturing);
            if (!mCurrentPageSequence.get(position).isCompleted()) {
                mNextButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
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
                mPager.setCurrentItem(i);
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

    /**
     * Methods from the {@link WritePostView} interface
     */

    @Override
    public void writtenPost(PostModel postModel) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    /**
     * The wizard adapter
     */
    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;

        public MyPagerAdapter(FragmentManager fm) {
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
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }
    }

    /**
     * Method from the {@link HasComponent interface}
     */

    @Override
    public PostComponent getComponent() {
        return postComponent;
    }
}