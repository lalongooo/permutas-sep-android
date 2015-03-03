package com.example.android.wizardpager.wizard.model;


import android.support.v4.app.Fragment;

import com.example.android.wizardpager.wizard.ui.ProfessorCityToFragment;
import com.permutassep.model.City;
import com.permutassep.model.State;
import com.permutassep.model.Town;

import java.util.ArrayList;

/**
 * A page asking for a name and an email.
 */
public class ProfessorCityToPage extends Page {
	public static final String STATE_TO_DATA_KEY = "state_to";
	public static final String MUNICIPALITY_TO_DATA_KEY = "municipality_to";
	public static final String LOCALITY_TO_DATA_KEY = "locality_to";

	public ProfessorCityToPage(ModelCallbacks callbacks, String title) {
		super(callbacks, title);
	}

	@Override
	public Fragment createFragment() {
		return ProfessorCityToFragment.create(getKey());
	}

	@Override
	public void getReviewItems(ArrayList<ReviewItem> dest) {
        State state = (State) mData.getParcelable(STATE_TO_DATA_KEY);
		dest.add(new ReviewItem("ESTADO DESEADO", state.getStateName(), STATE_TO_DATA_KEY, -1));

        City city = (City) mData.getParcelable(MUNICIPALITY_TO_DATA_KEY);
        dest.add(new ReviewItem("MUNICIPIO DESEADO", city.getNombreMunicipio(), MUNICIPALITY_TO_DATA_KEY, -1));

        Town town = (Town) mData.getParcelable(LOCALITY_TO_DATA_KEY);
		dest.add(new ReviewItem("LOCALIDAD DESEADO", town.getNombre(), LOCALITY_TO_DATA_KEY, -1));
	}

	@Override
	public boolean isCompleted() {
		return mData.containsKey(LOCALITY_TO_DATA_KEY);
	}
}