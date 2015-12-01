package com.permutassep.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.analytics.HitBuilders;
import com.lalongooo.permutassep.R;
import com.permutassep.adapter.PlaceSpinnerBaseAdapter;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.presentation.AndroidApplication;
import com.permutassep.presentation.utils.Utils;
import com.permutassep.rest.inegifacil.InegiFacilRestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentSearch extends BaseFragment {

    /**
     * UI elements
     */
    @Bind(R.id.spn_state_origin)
    Spinner spnStateFrom;
    @Bind(R.id.spn_city_origin)
    Spinner spnMunicipalityFrom;
    @Bind(R.id.spn_town_origin)
    Spinner spnLocalityFrom;
    @Bind(R.id.spn_state_to)
    Spinner spnStateTo;
    @Bind(R.id.spn_city_to)
    Spinner spnMunicipalityTo;
    @Bind(R.id.spn_town_to)
    Spinner spnLocalityTo;

    private MaterialDialog progressDialog;
    private int stateFromSelectedPosition = 0;
    private int cityFromSelectedPosition = 0;
    private String townFromSelectedPosition = "";
    private int stateToSelectedPosition = 0;
    private int cityToSelectedPosition = 0;
    private String townToSelectedPosition = "";

    private SearchPerformer searchPerformer;


    public static FragmentSearch newInstance() {
        return new FragmentSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.ca_fragment_search, container, false);
        ButterKnife.bind(this, fragmentView);
        setupSpinners();

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        searchPerformer = (SearchPerformer) getActivity();
    }

    @OnClick(R.id.btnSearch)
    void onBtnSearchClick() {

        ((AndroidApplication) getActivity().getApplication())
                .getTracker()
                .send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.ga_event_category_ux))
                        .setAction(getString(R.string.ga_event_action_click))
                        .setLabel(getString(R.string.ga_app_search))
                        .build());

        HashMap<String, String> searchParams = new HashMap<>();
        try {
            if (stateFromSelectedPosition != 0) {
                searchParams.put("place_from_state", String.valueOf(stateFromSelectedPosition));
            }
            if (cityFromSelectedPosition != 0) {
                searchParams.put("place_from_city", String.valueOf(cityFromSelectedPosition));
            }
            if (!townFromSelectedPosition.equals("")) {
                searchParams.put("place_from_town", townFromSelectedPosition);
            }
            if (stateToSelectedPosition != 0) {
                searchParams.put("place_to_state", String.valueOf(stateToSelectedPosition));
            }
            if (cityToSelectedPosition != 0) {
                searchParams.put("place_to_city", String.valueOf(cityToSelectedPosition));
            }
            if (!townToSelectedPosition.equals("")) {
                searchParams.put("place_to_town", townToSelectedPosition);
            }
        } catch (Exception e) {

            new MaterialDialog.Builder(getActivity())
                    .title(R.string.ups)
                    .content(R.string.search_fragment_filters_error_msg)
                    .positiveText(R.string.accept)
                    .show();
        }

        searchPerformer.onPerformSearch(searchParams);
    }

    private void setupSpinners() {

        List<State> stateList = Utils.getStateList(getActivity());

        spnStateFrom.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), stateList));
        spnStateFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State) parent.getItemAtPosition(position);

                if (position != stateFromSelectedPosition) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(spnLocalityFrom);
                    stateFromSelectedPosition = selectedState.getId();
                    cityFromSelectedPosition = 0;
                    townFromSelectedPosition = "";

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                spnMunicipalityFrom.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), cities));
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
                    stateFromSelectedPosition = 0;
                    cityFromSelectedPosition = 0;
                    townFromSelectedPosition = "";
                    resetSpinner(spnMunicipalityFrom);
                    resetSpinner(spnLocalityFrom);
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
                if (cityFromSelectedPosition != position) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_localities));
                    cityFromSelectedPosition = selectedCity.getClaveMunicipio();
                    townFromSelectedPosition = "";

                    try {
                        InegiFacilRestClient.get().getTowns(String.valueOf(selectedCity.getClaveEntidad()), String.valueOf(selectedCity.getClaveMunicipio()), new Callback<ArrayList<Town>>() {
                            @Override
                            public void success(ArrayList<Town> towns, Response response) {
                                spnLocalityFrom.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), towns));
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
                    townFromSelectedPosition = "";
                    resetSpinner(spnLocalityFrom);
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


        spnStateTo.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), stateList));
        spnStateTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State) parent.getItemAtPosition(position);

                if (position != stateToSelectedPosition) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(spnLocalityTo);
                    stateToSelectedPosition = selectedState.getId();
                    cityToSelectedPosition = 0;
                    townToSelectedPosition = "";

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                spnMunicipalityTo.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), cities));
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
                    stateToSelectedPosition = 0;
                    cityToSelectedPosition = 0;
                    townToSelectedPosition = "";
                    resetSpinner(spnMunicipalityTo);
                    resetSpinner(spnLocalityTo);
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
                if (cityToSelectedPosition != position) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_localities));
                    cityToSelectedPosition = selectedCity.getClaveMunicipio();
                    townToSelectedPosition = "";

                    try {
                        InegiFacilRestClient.get().getTowns(String.valueOf(selectedCity.getClaveEntidad()), String.valueOf(selectedCity.getClaveMunicipio()), new Callback<ArrayList<Town>>() {
                            @Override
                            public void success(ArrayList<Town> towns, Response response) {
                                spnLocalityTo.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), towns));
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
                    townToSelectedPosition = "";
                    resetSpinner(spnLocalityTo);
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
        PlaceSpinnerBaseAdapter adapter = (PlaceSpinnerBaseAdapter) spinner.getAdapter();
        if (adapter != null) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }

    private void showDialog(String title, String text) {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(text)
                .progress(true, 0)
                .cancelable(false)
                .progressIndeterminateStyle(false)
                .show();
    }

    private void hideDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }


    public interface SearchPerformer {
        void onPerformSearch(HashMap<String, String> searchParams);
    }
}