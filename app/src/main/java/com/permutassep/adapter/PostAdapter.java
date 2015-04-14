package com.permutassep.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.permutassep.R;
import com.permutassep.model.Post;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    private static LayoutInflater inflater;
    private Activity activity;
    private List<Post> data;

    public PostAdapter(Activity a, List<Post> posts) {
        activity = a;
        data = posts;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null) {
            vi = inflater.inflate(R.layout.tlatoa_translations_list_row, null);
        }

        TextView tvPhraseId = (TextView) vi.findViewById(R.id.tlatoa_phrase_id);
        TextView tvPhraseCounter = (TextView) vi.findViewById(R.id.tlatoa_phrase_counter);
        TextView tvPhrase = (TextView) vi.findViewById(R.id.tlatoa_phrase);
        TextView tvPhraseCreatedAt = (TextView) vi.findViewById(R.id.tlatoa_phrase_created_at);
        Button btnSharePhrase = (Button) vi.findViewById(R.id.tlatoa_share_phrase_button);

        // Get font
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "candara.ttf");
        tvPhraseId.setTypeface(typeface);
        tvPhrase.setTypeface(typeface);
        tvPhraseCreatedAt.setTypeface(typeface);
        btnSharePhrase.setTypeface(typeface);

        Sentence phrase = data.get(position);

        // Setting all values in listview items
        tvPhraseId.setText(String.valueOf(phrase.getId()));
        tvPhraseCounter.setText(String.valueOf(position + 1));
        tvPhrase.setText(String.valueOf(phrase.getText()).length() > 14 ? String.valueOf(phrase.getText()).substring(0, 13).concat("...") : String.valueOf(phrase.getText()));
        tvPhraseCreatedAt.setText(new TimeAgo(activity).timeAgo(phrase.getCreatedAt()));

        return vi;

    }
}