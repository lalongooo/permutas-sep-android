package com.permutassep.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lalongooo.permutassep.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.permutassep.BaseFragment;
import com.permutassep.model.City;
import com.permutassep.model.Post;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.rest.inegifacil.InegiFacilRestClient;
import com.permutassep.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentPostDetail extends BaseFragment {

    private static final String EXTRA_POS_TO_SHOW = "post";
    private Post post;
    private HashMap<String, State> states;
    private ProgressDialog pDlg;
    private ImageView ivArrow;
    private ImageView imageView;

    private TextView tvUserName;
    private TextView tvPostDate;
    private TextView tvPostUserEmail;
    private TextView tvPostUserPhone;
    private TextView tvPostText;
    private TextView tvStateFrom;
    private TextView tvCityFrom;
    private TextView tvTownFrom;
    private TextView tvStateTo;
    private TextView tvCityTo;
    private TextView tvTownTo;
    private TextView tvAcademicLevel;
    private TextView tvWorkdayType;
    private TextView tvPositionType;
    private TextView tvIsTeachingCareer;
    private TextView textStateFromCode;
    private TextView textStateToCode;


    public static FragmentPostDetail instance(String post) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_POS_TO_SHOW, post);
        FragmentPostDetail listDetailFragment = new FragmentPostDetail();
        listDetailFragment.setArguments(arguments);
        return listDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);
        getActivity().invalidateOptionsMenu();

        post = new Gson().fromJson(getArguments().getString(EXTRA_POS_TO_SHOW), Post.class);
        states = Utils.getStates(getActivity());
        ivArrow =  (ImageView) rootView.findViewById(R.id.ivArrow);
        ivArrow.setImageDrawable(new IconicsDrawable(getActivity(), FontAwesome.Icon.faw_angle_right).sizeDp(30));
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        if(!TextUtils.isEmpty(post.getUser().getSocialUserId())){
            ImageLoader.getInstance().displayImage("https://graph.facebook.com/" + post.getUser().getSocialUserId() + "/picture?width=200&height=200", imageView);
        }

        tvUserName = (TextView) rootView.findViewById(R.id.tvUserName);
        tvPostDate = (TextView) rootView.findViewById(R.id.tvPostDate);
        tvPostUserEmail = (TextView) rootView.findViewById(R.id.tvPostUserEmail);
        tvPostUserPhone = (TextView) rootView.findViewById(R.id.tvPostUserPhone);
        tvPostText = (TextView) rootView.findViewById(R.id.tvPostText);
        tvStateFrom = (TextView) rootView.findViewById(R.id.tvStateFrom);
        tvCityFrom = (TextView) rootView.findViewById(R.id.tvCityFrom);
        tvTownFrom = (TextView) rootView.findViewById(R.id.tvTownFrom);
        tvStateTo = (TextView) rootView.findViewById(R.id.tvStateTo);
        tvCityTo = (TextView) rootView.findViewById(R.id.tvCityTo);
        tvTownTo = (TextView) rootView.findViewById(R.id.tvTownTo);
        tvAcademicLevel = (TextView) rootView.findViewById(R.id.tvAcademicLevel);
        tvWorkdayType = (TextView) rootView.findViewById(R.id.tvWorkdayType);
        tvPositionType = (TextView) rootView.findViewById(R.id.tvPositionType);
        tvIsTeachingCareer = (TextView) rootView.findViewById(R.id.tvIsTeachingCareer);
        textStateFromCode = (TextView) rootView.findViewById(R.id.textStateFromCode);
        textStateToCode = (TextView) rootView.findViewById(R.id.textStateToCode);

        tvUserName.setText(post.getUser().getName());

        SimpleDateFormat format = new SimpleDateFormat("HH:mm' - 'dd MMM yy");

        tvPostDate.setText(format.format(post.getPostDate()));
        tvPostUserEmail.setText(post.getUser().getEmail());
        tvPostUserPhone.setText(post.getUser().getPhone());
        tvPostText.setText(post.getPostText());

        tvStateFrom.setText(String.valueOf(states.get((String.valueOf(post.getStateFrom()))).getStateName()));
        textStateFromCode.setText(String.valueOf(states.get((String.valueOf(post.getStateFrom()))).getShortCode()));
        showDialog("Cargando", "Espera por favor..");
        InegiFacilRestClient.get().getCities(String.valueOf(post.getStateFrom()), new Callback<ArrayList<City>>() {
            @Override
            public void success(ArrayList<City> cities, Response response) {
                tvCityFrom.setText(cities.get(post.getCityFrom()).getNombreMunicipio());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        InegiFacilRestClient.get().getTowns(String.valueOf(post.getStateFrom()), String.valueOf(post.getCityFrom()), new Callback<ArrayList<Town>>() {
            @Override
            public void success(ArrayList<Town> towns, Response response) {
                HashMap<String, Town> map = new HashMap<>();
                for(Town town : towns){
                    map.put(town.getClave(), town);
                }
                tvTownFrom.setText(map.get(("0000".substring(0,"0000".length()-String.valueOf(post.getTownFrom()).length()))+String.valueOf(post.getTownFrom())).getNombre());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });


        tvStateTo.setText(String.valueOf(states.get((String.valueOf(post.getStateTo()))).getStateName()));
        textStateToCode.setText(String.valueOf(states.get((String.valueOf(post.getStateTo()))).getShortCode()));
        InegiFacilRestClient.get().getCities(String.valueOf(post.getStateTo()), new Callback<ArrayList<City>>() {
            @Override
            public void success(ArrayList<City> cities, Response response) {
                tvCityTo.setText(cities.get(post.getCityTo()).getNombreMunicipio());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        InegiFacilRestClient.get().getTowns(String.valueOf(post.getStateTo()), String.valueOf(post.getCityTo()), new Callback<ArrayList<Town>>() {
            @Override
            public void success(ArrayList<Town> towns, Response response) {
                HashMap<String, Town> map = new HashMap<>();
                for(Town town : towns){
                    map.put(town.getClave(), town);
                }
                tvTownTo.setText(map.get(("0000".substring(0,"0000".length()-String.valueOf(post.getTownTo()).length()))+String.valueOf(post.getTownTo())).getNombre());
                hideDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                hideDialog();
            }
        });

        tvAcademicLevel.setText(post.getAcademicLevel());
        tvWorkdayType.setText(post.getWorkdayType());
        tvPositionType.setText("Plaza " + post.getPositionType());
        if (post.isTeachingCareer()) {
            tvIsTeachingCareer.setText("Carrera magisterial");
        } else {
            tvIsTeachingCareer.setText("Sin carrera magisterial");
        }

        return rootView;
    }

    public interface OnPostItemSelectedListener {
        public void showPostDetail(Post post);
    }

    private void showDialog(String title, String text) {
        if(pDlg == null){
            pDlg = ProgressDialog.show(getActivity(), title, text, true);
        }
    }

    private void hideDialog() {
        if (pDlg != null && pDlg.isShowing())
            pDlg.dismiss();
    }
}
