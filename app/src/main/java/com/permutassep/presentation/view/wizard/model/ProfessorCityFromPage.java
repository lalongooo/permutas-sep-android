package com.permutassep.presentation.view.wizard.model;

import android.support.v4.app.Fragment;

import com.permutassep.presentation.view.wizard.ui.ProfessorCityFromFragment;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;

import java.util.ArrayList;

/**
 * A page asking for a name and an email.
 */
public class ProfessorCityFromPage extends Page {

    public static final String STATE_DATA_KEY = "state_from";
    public static final String MUNICIPALITY_DATA_KEY = "municipality_from";
    public static final String LOCALITY_DATA_KEY = "locality_from";

    public ProfessorCityFromPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return ProfessorCityFromFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        State state = mData.getParcelable(STATE_DATA_KEY);
        City city = mData.getParcelable(MUNICIPALITY_DATA_KEY);
        Town town = mData.getParcelable(LOCALITY_DATA_KEY);

        assert state != null;
        assert city != null;
        assert town != null;

        dest.add(new ReviewItem("ESTADO ORIGEN", state.getStateName(), getKey(), -1));
        dest.add(new ReviewItem("MUNICIPIO ORIGEN", city.getNombreMunicipio(), getKey(), -1));
        dest.add(new ReviewItem("LOCALIDAD ORIGEN", town.getNombre(), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return mData.containsKey(LOCALITY_DATA_KEY);
    }
}