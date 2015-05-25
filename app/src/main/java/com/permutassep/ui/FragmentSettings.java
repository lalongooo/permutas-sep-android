package com.permutassep.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lalongooo.permutassep.R;
import com.permutassep.BaseFragment;
import com.permutassep.config.Config;
import com.permutassep.model.User;
import com.permutassep.rest.permutassep.PermutasSEPRestClient;
import com.permutassep.utils.Utils;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class FragmentSettings extends BaseFragment {

    private EditText etName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        etName = ((EditText) rootView.findViewById(R.id.etName));
        etEmail = ((EditText) rootView.findViewById(R.id.etEmail));
        etPhone = ((EditText) rootView.findViewById(R.id.etPhone));
        etPassword = ((EditText) rootView.findViewById(R.id.etPassword));

        etName.setText(Utils.getUser(getActivity()).getName());
        etEmail.setText(Utils.getUser(getActivity()).getEmail());
        etPhone.setText(Utils.getUser(getActivity()).getPhone());
        etPassword.setText(Utils.getUser(getActivity()).getPassword());

        ((TextView) rootView.findViewById(R.id.btnUpdate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = Utils.getUser(getActivity());
                user.setName(etName.getText().toString());
                user.setEmail(etEmail.getText().toString());
                user.setPhone(etPhone.getText().toString());
                user.setPassword(etPassword.getText().toString());

                GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(Config.APP_DATE_FORMAT);
                Gson gson = gsonBuilder.create();
                new PermutasSEPRestClient(new GsonConverter(gson)).get().updateUser(user.getId(), user, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.i("RESPONSE:", user.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });

        Picasso.with(getActivity())
                .load("https://graph.facebook.com/" + Utils.getUser(getActivity()).getSocialUserId() + "/picture?width=500&height=500")
                .placeholder(R.drawable.default_profile_picture)
                .error(R.drawable.default_profile_picture)
                .resizeDimen(R.dimen.list_detail_image_size, R.dimen.list_detail_image_size)
                .centerInside()
                .tag(getActivity())
                .into((ImageView)rootView.findViewById(R.id.ivProfilePicture));

        return rootView;
    }
}