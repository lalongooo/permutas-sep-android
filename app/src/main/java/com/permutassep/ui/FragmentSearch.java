package com.permutassep.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lalongooo.permutassep.R;
import com.lalongooo.permutassep.databinding.CaFragmentSearchBinding;
import com.permutassep.adapter.PlaceSpinnerBaseAdapter;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.presentation.utils.Utils;
import com.permutassep.rest.inegifacil.InegiFacilRestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentSearch extends BaseFragment {

    private CaFragmentSearchBinding binding;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CaFragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSpinners();
        setupUI();
    }

    private void setupUI() {
        binding.btnSearch.setOnClickListener(v -> {

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

                new MaterialDialog.Builder(requireActivity())
                        .title(R.string.ups)
                        .content(R.string.search_fragment_filters_error_msg)
                        .positiveText(R.string.accept)
                        .show();
            }

            searchPerformer.onPerformSearch(searchParams);
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        searchPerformer = (SearchPerformer) getActivity();
    }

    private void setupSpinners() {

        List<State> stateList = Utils.getStateList(getActivity());

        binding.spnStateOrigin.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), stateList));
        binding.spnStateOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State) parent.getItemAtPosition(position);

                if (position != stateFromSelectedPosition) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(binding.spnStateOrigin);
                    stateFromSelectedPosition = selectedState.getId();
                    cityFromSelectedPosition = 0;
                    townFromSelectedPosition = "";

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                binding.spnCityOrigin.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), cities));
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
                    resetSpinner(binding.spnCityOrigin);
                    resetSpinner(binding.spnTownOrigin);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spnCityOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                binding.spnTownOrigin.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), towns));
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
                    resetSpinner(binding.spnTownOrigin);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spnTownOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Town town = (Town) parent.getItemAtPosition(position);
                townFromSelectedPosition = town.getClave();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.spnStateTo.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), stateList));
        binding.spnStateTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                State selectedState = (State) parent.getItemAtPosition(position);

                if (position != stateToSelectedPosition) {

                    showDialog(getString(R.string.please_wait), getString(R.string.main_loading_cities));
                    // Remove localities
                    resetSpinner(binding.spnTownTo);
                    stateToSelectedPosition = selectedState.getId();
                    cityToSelectedPosition = 0;
                    townToSelectedPosition = "";

                    try {
                        InegiFacilRestClient.get().getCities(String.valueOf(selectedState.getId()), new Callback<ArrayList<City>>() {
                            @Override
                            public void success(ArrayList<City> cities, Response response) {
                                binding.spnCityTo.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), cities));
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
                    resetSpinner(binding.spnCityTo);
                    resetSpinner(binding.spnTownTo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spnCityTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                binding.spnTownTo.setAdapter(new PlaceSpinnerBaseAdapter(getActivity(), towns));
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
                    resetSpinner(binding.spnTownTo);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spnTownTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        progressDialog = new MaterialDialog.Builder(requireActivity())
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