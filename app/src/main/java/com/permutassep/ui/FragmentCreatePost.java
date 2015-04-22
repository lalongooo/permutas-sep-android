/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.permutassep.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.wizardpager.wizard.model.AbstractWizardModel;
import com.example.android.wizardpager.wizard.model.ModelCallbacks;
import com.example.android.wizardpager.wizard.model.Page;
import com.example.android.wizardpager.wizard.model.PostTextPage;
import com.example.android.wizardpager.wizard.model.ProfessorCityFromPage;
import com.example.android.wizardpager.wizard.model.ProfessorCityToPage;
import com.example.android.wizardpager.wizard.ui.PageFragmentCallbacks;
import com.example.android.wizardpager.wizard.ui.ReviewFragment;
import com.example.android.wizardpager.wizard.ui.StepPagerStrip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.permutassep.R;
import com.permutassep.config.Config;
import com.permutassep.model.City;
import com.permutassep.model.PermutaSepWizardModel;
import com.permutassep.model.Post;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.model.User;
import com.permutassep.rest.PermutasSEPRestClient;
import com.permutassep.adapter.PostTypeAdapter;
import com.permutassep.utils.PrefUtils;
import com.permutassep.adapter.UserTypeAdapter;

import java.util.Date;
import java.util.List;

import br.kots.mob.complex.preferences.ComplexPreferences;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;

public class FragmentCreatePost extends Fragment implements
        PageFragmentCallbacks,
        ReviewFragment.Callbacks,
        ModelCallbacks {
    private ViewPager mPager;
    private MyPagerAdapter mPagerAdapter;

    private boolean mEditingAfterReview;

    private AbstractWizardModel mWizardModel = new PermutaSepWizardModel(getActivity());

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;
    private ProgressDialog pDlg;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;
    boolean suggestDataCompletion = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createpost, container, false);
        getActivity().setTitle(R.string.app_main_toolbar_post_action);
        getActivity().invalidateOptionsMenu();

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) rootView.findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) rootView.findViewById(R.id.next_button);
        mPrevButton = (Button) rootView.findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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

                if(suggestDataCompletion && mCurrentPageSequence.get(mPager.getCurrentItem()).getKey() == PermutaSepWizardModel.CONTACT_INFO_KEY){

                    DialogFragment dg = new DialogFragment() {
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
                    dg.show(getActivity().getSupportFragmentManager(), "contact_data_dialog");

                }
                else if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.submit_confirm_message)
                                    .setPositiveButton(R.string.submit_confirm_button, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Post post = new Post();
                                            post.setPostDate(new Date());

                                            for (Page p : mWizardModel.getCurrentPageSequence()) {
                                                switch (p.getKey()){
                                                    case PermutaSepWizardModel.CONTACT_INFO_KEY:
                                                        User user = ComplexPreferences.getComplexPreferences(getActivity(), Config.APP_PREFERENCES_NAME, Context.MODE_PRIVATE).getObject(PrefUtils.PREF_USER_KEY, User.class);
                                                        post.setUser(user);
                                                        break;
                                                    case PermutaSepWizardModel.CITY_FROM_KEY:
                                                        State sf = p.getData().getParcelable(ProfessorCityFromPage.STATE_DATA_KEY);
                                                        City cf = p.getData().getParcelable(ProfessorCityFromPage.MUNICIPALITY_DATA_KEY);
                                                        Town tf = p.getData().getParcelable(ProfessorCityFromPage.LOCALITY_DATA_KEY);

                                                        post.setStateFrom(sf.getId());
                                                        post.setCityFrom(Short.valueOf(String.valueOf(cf.getClaveMunicipio())));
                                                        post.setTownFrom(Short.valueOf(tf.getClave()));
                                                        post.setLatFrom(tf.getLatitud());
                                                        post.setLonFrom(tf.getLongitud());
                                                        break;
                                                    case PermutaSepWizardModel.CITY_TO_KEY:
                                                        State st =  p.getData().getParcelable(ProfessorCityToPage.STATE_TO_DATA_KEY);
                                                        City ct = p.getData().getParcelable(ProfessorCityToPage.MUNICIPALITY_TO_DATA_KEY);
                                                        Town tt = p.getData().getParcelable(ProfessorCityToPage.LOCALITY_TO_DATA_KEY);

                                                        post.setStateTo(st.getId());
                                                        post.setCityTo(Short.valueOf(String.valueOf(ct.getClaveMunicipio())));
                                                        post.setTownTo(Short.valueOf(tt.getClave()));
                                                        post.setLatTo(tt.getLatitud());
                                                        post.setLonTo(tt.getLongitud());

                                                        break;
                                                    case PermutaSepWizardModel.ACADEMIC_LEVEL_KEY:
                                                        post.setAcademicLevel(p.getData().getString(p.SIMPLE_DATA_KEY));
                                                        break;
                                                    case PermutaSepWizardModel.POSITION_TYPE_KEY:
                                                        post.setPositionType(p.getData().getString(p.SIMPLE_DATA_KEY));
                                                        break;
                                                    case PermutaSepWizardModel.WORKDAY_TYPE_KEY:
                                                        post.setWorkdayType(p.getData().getString(p.SIMPLE_DATA_KEY));
                                                        break;
                                                    case PermutaSepWizardModel.TEACHING_CAREER_KEY:
                                                        post.setIsTeachingCareer(p.getData().getString(p.SIMPLE_DATA_KEY).equals("Si") ? true : false);
                                                        break;
                                                    case PermutaSepWizardModel.POST_TEXT_KEY:
                                                        post.setPostText(p.getData().getString(PostTextPage.TEXT_DATA_KEY));
                                                        break;
                                                }
                                            }

                                            showDialog(getString(R.string.wizard_post_dlg_title), getString(R.string.wizard_post_dlg_text));

                                            GsonBuilder gsonBuilder = new GsonBuilder()
                                                    .registerTypeHierarchyAdapter(User.class, new UserTypeAdapter(getActivity()))
                                                    .registerTypeHierarchyAdapter(Post.class, new PostTypeAdapter(getActivity()))
                                                    .setDateFormat(Config.APP_DATE_FORMAT);
                                            Gson gson = gsonBuilder.create();

                                            new PermutasSEPRestClient(new GsonConverter(gson)).get().newPost(post, new Callback<Post>() {
                                                @Override
                                                public void success(Post post, retrofit.client.Response response) {
                                                    replaceFragment();
                                                    hideDialog();
                                                }

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    // TODO: Add the error message dialog
                                                    hideDialog();
                                                }
                                            });

                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .create();
                        }
                    };
                    dg.show(getActivity().getSupportFragmentManager(), "place_order_dialog");
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

        return rootView;
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
            mNextButton.setTextAppearance(getActivity(), R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview ? R.string.review : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            mNextButton.setTextAppearance(getActivity(), R.style.TextAppearanceCapturing);
            if (!mCurrentPageSequence.get(position).isCompleted()){
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

    private void showDialog(String title, String text) {
        pDlg = ProgressDialog.show(getActivity(), title, text, true);
    }

    private void hideDialog() {
        if (pDlg != null)
            pDlg.dismiss();
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int mCutOffPage;
        private Fragment mPrimaryItem;

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
        public int getItemPosition(Object object) {
            // TODO: be smarter about this
            if (object == mPrimaryItem) {
                // Re-use the current fragment (its position never changes)
                return POSITION_UNCHANGED;
            }

            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryItem = (Fragment) object;
        }

        @Override
        public int getCount() {
            if (mCurrentPageSequence == null) {
                return 0;
            }
            return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
        }

        public void setCutOffPage(int cutOffPage) {
            if (cutOffPage < 0) {
                cutOffPage = Integer.MAX_VALUE;
            }
            mCutOffPage = cutOffPage;
        }

        public int getCutOffPage() {
            return mCutOffPage;
        }
    }

    private void replaceFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FragmentNewsFeed()).commit();
        getActivity().invalidateOptionsMenu();
    }
}