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

package com.example.android.wizardpager.wizard.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.permutassep.R;
import com.example.android.wizardpager.wizard.model.Page;
import com.example.android.wizardpager.wizard.model.ProfessorCityFromPage;
import com.permutassep.adapter.CitySpinnerBaseAdapter;
import com.permutassep.adapter.StateSpinnerBaseAdapter;
import com.permutassep.adapter.TownSpinnerBaseAdapter;
import com.permutassep.inegifacil.rest.InegiFacilRestClient;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfessorCityFromFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private Page mPage;

    private ProgressDialog pDlg;
    private Spinner spnState;
    private Spinner spnMunicipality;
    private Spinner spnLocality;

    public static final String STATES_FROM_KEY = "states_from";
    public static final String CITIES_FROM_KEY = "cities_from";
    public static final String TOWNS_FROM_KEY = "towns_from";
    private ArrayList<State> mStates = new ArrayList<>();
    private ArrayList<City> mCities = new ArrayList<>();
    private ArrayList<Town> mTowns = new ArrayList<>();

    public static final String STATE_FROM_SELECTED_KEY = "state_from_selected";
    public static final String CITY_FROM_SELECTED_KEY = "city_from_selected";
    public static final String TOWN_FROM_SELECTED_KEY = "town_from_selected";
    private int stateSelectedPosition = 0;
    private int citySelectedPosition = 0;
    private int townSelectedPosition = 0;

    public static ProfessorCityFromFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ProfessorCityFromFragment fragment = new ProfessorCityFromFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfessorCityFromFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (ProfessorCityFromPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_professor_city_from, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

		spnState = ((Spinner) rootView.findViewById(R.id.spn_your_state));
		spnMunicipality = ((Spinner) rootView.findViewById(R.id.spn_your_municipality));
		spnLocality = ((Spinner) rootView.findViewById(R.id.spn_your_locality));

        if(savedInstanceState != null){
            mStates = savedInstanceState.getParcelableArrayList(STATES_FROM_KEY);
            mCities = savedInstanceState.getParcelableArrayList(CITIES_FROM_KEY);
            mTowns = savedInstanceState.getParcelableArrayList(TOWNS_FROM_KEY);

            stateSelectedPosition = savedInstanceState.getInt(STATE_FROM_SELECTED_KEY);
            citySelectedPosition = savedInstanceState.getInt(CITY_FROM_SELECTED_KEY);
            townSelectedPosition = savedInstanceState.getInt(TOWN_FROM_SELECTED_KEY);

            if(mStates.size() > 0){
                spnState.setAdapter(new StateSpinnerBaseAdapter(getActivity(), mStates));
                spnState.setSelection(stateSelectedPosition, false);
            }

            if(mCities.size() > 0){
                spnMunicipality.setAdapter(new CitySpinnerBaseAdapter(getActivity(), mCities));
                spnMunicipality.setSelection(citySelectedPosition, false);
            }

            if(mTowns.size() > 0){
                spnLocality.setAdapter(new TownSpinnerBaseAdapter(getActivity(), mTowns));
                spnLocality.setSelection(townSelectedPosition, false);
            }

        }
        setupSpinners();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // In a future update to the support library, this should override setUserVisibleHint instead of setMenuVisibility.
        if (spnLocality != null && spnLocality.getSelectedItem() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!menuVisible) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }

    private void setupSpinners(){

        if(spnState.getAdapter() == null){
            String [] states = getResources().getStringArray(R.array.states);
            for (int i = 0; i < states.length; i++){
                mStates.add(new State(i, states[i]));
            }
            spnState.setAdapter(new StateSpinnerBaseAdapter(getActivity(), mStates));
        }

        spnState.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State)parent.getItemAtPosition(position);
                if(position != stateSelectedPosition && selectedState.getId() != 0) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(spnLocality);
                    mCities.clear();
                    mTowns.clear();
                    stateSelectedPosition = position;
                    citySelectedPosition = 0;
                    townSelectedPosition = 0;
                    mPage.getData().putParcelable(ProfessorCityFromPage.STATE_DATA_KEY, selectedState);
                    mPage.getData().remove(ProfessorCityFromPage.MUNICIPALITY_DATA_KEY);
                    mPage.getData().remove(ProfessorCityFromPage.LOCALITY_DATA_KEY);
                    mPage.notifyDataChanged();

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                mCities = cities;
                                spnMunicipality.setAdapter(new CitySpinnerBaseAdapter(getActivity(), cities));
                                hideDialog();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                hideDialog();
                            }
                        });

                    }catch (Exception ex){
                        // TODO: Add exception handling best practices
                    }
                }else{
                    if(selectedState.getId() == 0){
                        resetSpinner(spnMunicipality);
                        resetSpinner(spnLocality);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        spnMunicipality.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                City selectedCity = (City) parent.getItemAtPosition(position);
                if(citySelectedPosition != position && position != 0 && getUserVisibleHint()){

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_localities));
                    mTowns.clear();
                    citySelectedPosition = position;
                    townSelectedPosition = 0;
                    mPage.getData().putParcelable(ProfessorCityFromPage.MUNICIPALITY_DATA_KEY, selectedCity);
                    mPage.getData().remove(ProfessorCityFromPage.LOCALITY_DATA_KEY);
                    mPage.notifyDataChanged();

                    try {
                        InegiFacilRestClient.get().getTowns(String.valueOf(selectedCity.getClaveEntidad()), String.valueOf(selectedCity.getClaveMunicipio()), new Callback<ArrayList<Town>>() {
                            @Override
                            public void success(ArrayList<Town> towns, Response response) {
                                mTowns = towns;
                                spnLocality.setAdapter(new TownSpinnerBaseAdapter(getActivity(), towns));
                                hideDialog();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                hideDialog();
                                // TODO: Inform to the user about a network problem
                            }
                        });

                    }catch (Exception ex){
                        // TODO: Add exception handling best practices
                    }
                }else{
                    if(position != 0){
                        resetSpinner(spnLocality);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        spnLocality.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Town town = (Town) parent.getItemAtPosition(position);
                townSelectedPosition = position;
                mPage.getData().putParcelable(ProfessorCityFromPage.LOCALITY_DATA_KEY, town);
                mPage.notifyDataChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDialog(String title, String text) {
        if(getUserVisibleHint())
            pDlg = ProgressDialog.show(getActivity(), title, text, true);
    }

    private void hideDialog() {
        if(getUserVisibleHint() && pDlg != null)
            pDlg.dismiss();
    }

    private void resetSpinner(Spinner spinner){
        if (spinner.getAdapter() != null && spinner.getAdapter().getCount() > 0){
            spinner.setAdapter(null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(ProfessorCityFromFragment.STATES_FROM_KEY, mStates);
        outState.putParcelableArrayList(ProfessorCityFromFragment.CITIES_FROM_KEY, mCities);
        outState.putParcelableArrayList(ProfessorCityFromFragment.TOWNS_FROM_KEY, mTowns);

        outState.putInt(ProfessorCityFromFragment.STATE_FROM_SELECTED_KEY, stateSelectedPosition);
        outState.putInt(ProfessorCityFromFragment.CITY_FROM_SELECTED_KEY, citySelectedPosition);
        outState.putInt(ProfessorCityFromFragment.TOWN_FROM_SELECTED_KEY, townSelectedPosition);
    }

}
