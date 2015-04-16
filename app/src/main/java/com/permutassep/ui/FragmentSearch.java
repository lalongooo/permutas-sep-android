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
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.permutassep.R;
import com.permutassep.adapter.CitySpinnerBaseAdapter;
import com.permutassep.adapter.StateSpinnerBaseAdapter;
import com.permutassep.adapter.TownSpinnerBaseAdapter;
import com.permutassep.config.Config;
import com.permutassep.inegifacil.rest.InegiFacilRestClient;
import com.permutassep.model.City;
import com.permutassep.model.Post;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.model.User;
import com.permutassep.rest.PermutasSEPRestClient;
import com.permutassep.utils.PostTypeAdapter;
import com.permutassep.utils.UserTypeAdapter;
import com.permutassep.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class FragmentSearch extends Fragment {

    private ProgressDialog pDlg;
    private Button btnSearch;
    private Spinner spnStateFrom;
    private Spinner spnMunicipalityFrom;
    private Spinner spnLocalityFrom;
    private Spinner spnStateTo;
    private Spinner spnMunicipalityTo;
    private Spinner spnLocalityTo;

    private int stateFromSelectedPosition = 0;
    private int cityFromSelectedPosition = 0;
    private String townFromSelectedPosition = "";
    private int stateToSelectedPosition = 0;
    private int cityToSelectedPosition = 0;
    private String townToSelectedPosition = "";

    private List<State> mStatesFrom = new ArrayList<>();
    private List<City> mCitiesFrom = new ArrayList<>();
    private List<Town> mTownsFrom = new ArrayList<>();
    private List<State> mStatesTo = new ArrayList<>();
    private List<City> mCitiesTo = new ArrayList<>();
    private List<Town> mTownsTo = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(listener);
        spnStateFrom = ((Spinner) rootView.findViewById(R.id.spn_state_origin));
        spnMunicipalityFrom = ((Spinner) rootView.findViewById(R.id.spn_city_origin));
        spnLocalityFrom = ((Spinner) rootView.findViewById(R.id.spn_town_origin));

        spnStateTo = ((Spinner) rootView.findViewById(R.id.spn_state_to));
        spnMunicipalityTo = ((Spinner) rootView.findViewById(R.id.spn_city_to));
        spnLocalityTo = ((Spinner) rootView.findViewById(R.id.spn_town_to));

        setupSpinners();

        return rootView;
    }

    private void setupSpinners() {

        mStatesFrom = Utils.getStateList(getActivity());
        spnStateFrom.setAdapter(new StateSpinnerBaseAdapter(getActivity(), mStatesFrom));

        spnStateFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State) parent.getItemAtPosition(position);

                if (position != stateFromSelectedPosition && selectedState.getId() != 0) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(spnLocalityFrom);
                    mCitiesFrom.clear();
                    mTownsFrom.clear();

                    stateFromSelectedPosition = position;
                    cityFromSelectedPosition = 0;

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                mCitiesFrom = cities;
                                spnMunicipalityFrom.setAdapter(new CitySpinnerBaseAdapter(getActivity(), cities));
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
                        resetSpinner(spnMunicipalityFrom);
                        resetSpinner(spnLocalityFrom);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spnMunicipalityFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                City selectedCity = (City) parent.getItemAtPosition(position);
                if (cityFromSelectedPosition != position && position != 0 && getUserVisibleHint()) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_localities));
                    mTownsFrom.clear();
                    cityFromSelectedPosition = position;

                    try {
                        InegiFacilRestClient.get().getTowns(String.valueOf(selectedCity.getClaveEntidad()), String.valueOf(selectedCity.getClaveMunicipio()), new Callback<ArrayList<Town>>() {
                            @Override
                            public void success(ArrayList<Town> towns, Response response) {
                                mTownsFrom = towns;
                                spnLocalityFrom.setAdapter(new TownSpinnerBaseAdapter(getActivity(), towns));
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
                        resetSpinner(spnLocalityFrom);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spnLocalityFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Town town = (Town) parent.getItemAtPosition(position);
                townFromSelectedPosition = town.getClave();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mStatesTo = Utils.getStateList(getActivity());
        spnStateTo.setAdapter(new StateSpinnerBaseAdapter(getActivity(), mStatesTo));

        spnStateTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State) parent.getItemAtPosition(position);

                if (position != stateToSelectedPosition && selectedState.getId() != 0) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(spnLocalityTo);
                    mCitiesTo.clear();
                    mTownsTo.clear();

                    stateToSelectedPosition = position;
                    cityToSelectedPosition = 0;

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                mCitiesTo = cities;
                                spnMunicipalityTo.setAdapter(new CitySpinnerBaseAdapter(getActivity(), cities));
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
                        resetSpinner(spnMunicipalityTo);
                        resetSpinner(spnLocalityTo);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spnMunicipalityTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                City selectedCity = (City) parent.getItemAtPosition(position);
                if (cityToSelectedPosition != position && position != 0 && getUserVisibleHint()) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_localities));
                    mTownsTo.clear();
                    cityToSelectedPosition = position;

                    try {
                        InegiFacilRestClient.get().getTowns(String.valueOf(selectedCity.getClaveEntidad()), String.valueOf(selectedCity.getClaveMunicipio()), new Callback<ArrayList<Town>>() {
                            @Override
                            public void success(ArrayList<Town> towns, Response response) {
                                mTownsTo = towns;
                                spnLocalityTo.setAdapter(new TownSpinnerBaseAdapter(getActivity(), towns));
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
                        resetSpinner(spnLocalityTo);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spnLocalityTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Town town = (Town) parent.getItemAtPosition(position);
                townToSelectedPosition = town.getClave();
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

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Map<String, String> params = new HashMap<>();

            if(stateFromSelectedPosition != 0){
                params.put("place_from_state",String.valueOf(stateFromSelectedPosition));
            }
            if(cityFromSelectedPosition != 0){
                params.put("place_from_city", String.valueOf(cityFromSelectedPosition));
            }
            if(!townFromSelectedPosition.equals("")){
                params.put("place_from_town",townFromSelectedPosition);
            }
            if(stateToSelectedPosition != 0){
                params.put("place_to_state", String.valueOf(stateToSelectedPosition));
            }
            if(cityToSelectedPosition != 0){
                params.put("place_to_city", String.valueOf(cityToSelectedPosition));
            }
            if(!townToSelectedPosition.equals("")){
                params.put("place_to_town", townToSelectedPosition);
            }


            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeHierarchyAdapter(User.class, new UserTypeAdapter(getActivity()))
                    .registerTypeHierarchyAdapter(Post.class, new PostTypeAdapter(getActivity()))
                    .setDateFormat(Config.APP_DATE_FORMAT);
            Gson gson = gsonBuilder.create();
            new PermutasSEPRestClient(new GsonConverter(gson)).get().searchPosts(params, new Callback<List<Post>>() {
                @Override
                public void success(List<Post> posts, Response response) {
                    Log.i("", "");
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("", "");
                }
            });

        }
    };

    private void hideDialog() {
        if (pDlg != null)
            pDlg.dismiss();
    }
}
