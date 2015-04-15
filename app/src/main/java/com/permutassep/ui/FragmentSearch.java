package com.permutassep.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.permutassep.R;
import com.permutassep.adapter.CitySpinnerBaseAdapter;
import com.permutassep.adapter.StateSpinnerBaseAdapter;
import com.permutassep.adapter.TownSpinnerBaseAdapter;
import com.permutassep.inegifacil.rest.InegiFacilRestClient;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentSearch extends Fragment {

    private ProgressDialog pDlg;
    private Spinner spnState;
    private Spinner spnMunicipality;
    private Spinner spnLocality;
    private int stateSelectedPosition = 0;
    private int citySelectedPosition = 0;
    private int townSelectedPosition = 0;
    private List<State> mStates = new ArrayList<>();
    private List<City> mCities = new ArrayList<>();
    private List<Town> mTowns = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        spnState = ((Spinner) rootView.findViewById(R.id.spn_state_origin));
        spnMunicipality = ((Spinner) rootView.findViewById(R.id.spn_city_origin));
        spnLocality = ((Spinner) rootView.findViewById(R.id.spn_town_origin));

        setupSpinners();

        return rootView;

    }


    private void setupSpinners() {

        if (spnState.getAdapter() == null) {
            String[] states = getResources().getStringArray(R.array.states);
            for (short i = 0; i < states.length; i++) {
                mStates.add(new State(i, states[i]));
            }
            spnState.setAdapter(new StateSpinnerBaseAdapter(getActivity(), mStates));
        }

        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State) parent.getItemAtPosition(position);
                if (position != stateSelectedPosition && selectedState.getId() != 0) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(spnLocality);
                    mCities.clear();
                    mTowns.clear();
                    stateSelectedPosition = position;
                    citySelectedPosition = 0;
                    townSelectedPosition = 0;

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

                    } catch (Exception ex) {
                        Log.d("An error ocurred", ex.getMessage());
                    }
                } else {
                    if (selectedState.getId() == 0) {
                        resetSpinner(spnMunicipality);
                        resetSpinner(spnLocality);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spnMunicipality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                City selectedCity = (City) parent.getItemAtPosition(position);
                if (citySelectedPosition != position && position != 0 && getUserVisibleHint()) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_localities));
                    mTowns.clear();
                    citySelectedPosition = position;
                    townSelectedPosition = 0;

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
                            }
                        });

                    } catch (Exception ex) {
                        Log.d("An error occurred", ex.getMessage());
                    }
                } else {
                    if (position != 0) {
                        resetSpinner(spnLocality);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spnLocality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Town town = (Town) parent.getItemAtPosition(position);
                townSelectedPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void resetSpinner(Spinner spinner) {
        if (spinner.getAdapter() != null && spinner.getAdapter().getCount() > 0) {
            spinner.setAdapter(null);
        }
    }

    private void showDialog(String title, String text) {
        pDlg = ProgressDialog.show(getActivity(), title, text, true);
    }

    private void hideDialog() {
        if (pDlg != null)
            pDlg.dismiss();
    }
}
