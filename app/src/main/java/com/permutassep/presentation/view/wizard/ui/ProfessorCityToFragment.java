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

package com.permutassep.presentation.view.wizard.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.lalongooo.permutassep.R;
import com.permutassep.adapter.PlaceSpinnerBaseAdapter;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.presentation.view.wizard.model.Page;
import com.permutassep.presentation.view.wizard.model.ProfessorCityToPage;
import com.permutassep.rest.inegifacil.InegiFacilRestClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfessorCityToFragment extends Fragment {
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private Page mPage;

    private ProgressDialog pDlg;
    private Spinner spnState;
    private Spinner spnMunicipality;
    private Spinner spnLocality;

    public static final String STATES_TO_KEY = "states_to";
    public static final String CITIES_TO_KEY = "cities_to";
    public static final String TOWNS_TO_KEY = "towns_to";
    private ArrayList<State> mStates = new ArrayList<>();
    private ArrayList<City> mCities = new ArrayList<>();
    private ArrayList<Town> mTowns = new ArrayList<>();

    public static final String STATE_TO_SELECTED_KEY = "state_to_selected";
    public static final String CITY_TO_SELECTED_KEY = "city_to_selected";
    public static final String TOWN_TO_SELECTED_KEY = "town_to_selected";
    private int stateSelectedPosition = 0;
    private int citySelectedPosition = 0;
    private int townSelectedPosition = 0;

    public static ProfessorCityToFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        ProfessorCityToFragment fragment = new ProfessorCityToFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfessorCityToFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_professor_place_selector, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());
        
		spnState = ((Spinner) rootView.findViewById(R.id.spnState));
		spnMunicipality = ((Spinner) rootView.findViewById(R.id.spnMunicipality));
		spnLocality = ((Spinner) rootView.findViewById(R.id.spnLocality));

        if(savedInstanceState != null){
            mStates = savedInstanceState.getParcelableArrayList(STATES_TO_KEY);
            mCities = savedInstanceState.getParcelableArrayList(CITIES_TO_KEY);
            mTowns = savedInstanceState.getParcelableArrayList(TOWNS_TO_KEY);

            stateSelectedPosition = savedInstanceState.getInt(STATE_TO_SELECTED_KEY);
            citySelectedPosition = savedInstanceState.getInt(CITY_TO_SELECTED_KEY);
            townSelectedPosition = savedInstanceState.getInt(TOWN_TO_SELECTED_KEY);

            if(mStates.size() > 0){
                spnState.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), mStates));
                spnState.setSelection(stateSelectedPosition, false);
            }

            if(mCities.size() > 0){
                spnMunicipality.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), mCities));
                spnMunicipality.setSelection(citySelectedPosition, false);
            }

            if(mTowns.size() > 0){
                spnLocality.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), mTowns));
                spnLocality.setSelection(townSelectedPosition, false);
            }

        }

        setupSpinners();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) getActivity();
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
            for (short i = 0; i < states.length; i++){
                mStates.add(new State(i, states[i]));
            }
            spnState.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), mStates));
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
                    mPage.getData().putParcelable(ProfessorCityToPage.STATE_TO_DATA_KEY, selectedState);
                    mPage.getData().remove(ProfessorCityToPage.MUNICIPALITY_TO_DATA_KEY);
                    mPage.getData().remove(ProfessorCityToPage.LOCALITY_TO_DATA_KEY);
                    mPage.notifyDataChanged();

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                mCities = cities;
                                spnMunicipality.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), cities));
                                hideDialog();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                hideDialog();
                            }
                        });

                    }catch (Exception ex){
                        Log.d("An error ocurred", ex.getMessage());
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
                    mPage.getData().putParcelable(ProfessorCityToPage.MUNICIPALITY_TO_DATA_KEY, selectedCity);
                    mPage.getData().remove(ProfessorCityToPage.LOCALITY_TO_DATA_KEY);
                    mPage.notifyDataChanged();

                    try {
                        InegiFacilRestClient.get().getTowns(String.valueOf(selectedCity.getClaveEntidad()), String.valueOf(selectedCity.getClaveMunicipio()), new Callback<ArrayList<Town>>() {
                            @Override
                            public void success(ArrayList<Town> towns, Response response) {
                                mTowns = towns;
                                spnLocality.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), towns));
                                hideDialog();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                hideDialog();
                            }
                        });

                    }catch (Exception ex){
                        Log.d("An error occurred", ex.getMessage());
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
                mPage.getData().putParcelable(ProfessorCityToPage.LOCALITY_TO_DATA_KEY, town);
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

        outState.putParcelableArrayList(ProfessorCityToFragment.STATES_TO_KEY, mStates);
        outState.putParcelableArrayList(ProfessorCityToFragment.CITIES_TO_KEY, mCities);
        outState.putParcelableArrayList(ProfessorCityToFragment.TOWNS_TO_KEY, mTowns);

        outState.putInt(ProfessorCityToFragment.STATE_TO_SELECTED_KEY, stateSelectedPosition);
        outState.putInt(ProfessorCityToFragment.CITY_TO_SELECTED_KEY, citySelectedPosition);
        outState.putInt(ProfessorCityToFragment.TOWN_TO_SELECTED_KEY, townSelectedPosition);
        Log.i("onSaveInstanceState","onSaveInstanceState launched!");
    }
}
