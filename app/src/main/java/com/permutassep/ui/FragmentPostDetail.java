package com.permutassep.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.lalongooo.permutassep.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.permutassep.BaseFragment;
import com.permutassep.PermutasSEPApplication;
import com.permutassep.model.City;
import com.permutassep.model.Post;
import com.permutassep.model.State;
import com.permutassep.model.Town;
import com.permutassep.rest.inegifacil.InegiFacilRestClient;
import com.permutassep.utils.Utils;
import com.squareup.picasso.Picasso;

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
    private Button btnCall;
    private Button btnMail;


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
        ivArrow = (ImageView) rootView.findViewById(R.id.ivArrow);
        ivArrow.setImageDrawable(new IconicsDrawable(getActivity(), FontAwesome.Icon.faw_angle_right).sizeDp(30));
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        Picasso.with(getActivity())
                .load("https://graph.facebook.com/" + post.getUser().getSocialUserId() + "/picture?width=100&height=100")
                .placeholder(R.drawable.default_profile_picture)
                .error(R.drawable.default_profile_picture)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(getActivity())
                .into(imageView);

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
        btnCall = (Button) rootView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tracker t = ((PermutasSEPApplication) getActivity().getApplication()).getTracker();
                t.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.ga_event_category_ux))
                        .setAction(getString(R.string.ga_event_action_click))
                        .setLabel(getString(R.string.ga_call_intent))
                        .build());

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:".concat(post.getUser().getPhone())));
                startActivity(callIntent);
            }
        });

        btnMail = (Button) rootView.findViewById(R.id.btnEmail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker t = ((PermutasSEPApplication) getActivity().getApplication()).getTracker();
                t.send(new HitBuilders.EventBuilder()
                        .setCategory(getString(R.string.ga_event_category_ux))
                        .setAction(getString(R.string.ga_event_action_click))
                        .setLabel(getString(R.string.ga_mail_intent))
                        .build());
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",post.getUser().getEmail(), null));
                startActivity(Intent.createChooser(emailIntent, "Enviar mail:"));
            }
        });


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
                HashMap<Integer, City> map = new HashMap<>();
                for (City city : cities) {
                    map.put(city.getClaveMunicipio(), city);
                }
                tvCityFrom.setText(map.get(Integer.valueOf(post.getCityFrom())).getNombreMunicipio());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        InegiFacilRestClient.get().getTowns(String.valueOf(post.getStateFrom()), String.valueOf(post.getCityFrom()), new Callback<ArrayList<Town>>() {
            @Override
            public void success(ArrayList<Town> towns, Response response) {
                HashMap<String, Town> map = new HashMap<>();
                for (Town town : towns) {
                    map.put(town.getClave(), town);
                }
                tvTownFrom.setText(map.get(("0000".substring(0, "0000".length() - String.valueOf(post.getTownFrom()).length())) + String.valueOf(post.getTownFrom())).getNombre());
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
                HashMap<Integer, City> map = new HashMap<>();
                for (City city : cities) {
                    map.put(city.getClaveMunicipio(), city);
                }
                tvCityTo.setText(map.get(Integer.valueOf(post.getCityTo())).getNombreMunicipio());
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
        InegiFacilRestClient.get().getTowns(String.valueOf(post.getStateTo()), String.valueOf(post.getCityTo()), new Callback<ArrayList<Town>>() {
            @Override
            public void success(ArrayList<Town> towns, Response response) {
                HashMap<String, Town> map = new HashMap<>();
                for (Town town : towns) {
                    map.put(town.getClave(), town);
                }
                tvTownTo.setText(map.get(("0000".substring(0, "0000".length() - String.valueOf(post.getTownTo()).length())) + String.valueOf(post.getTownTo())).getNombre());
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
        if (pDlg == null) {
            pDlg = ProgressDialog.show(getActivity(), title, text, true);
        }
    }

    private void hideDialog() {
        if (pDlg != null && pDlg.isShowing())
            pDlg.dismiss();
    }
}
