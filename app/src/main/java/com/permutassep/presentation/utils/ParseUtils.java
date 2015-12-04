package com.permutassep.presentation.utils;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;
import com.permutassep.domain.Post;
import com.permutassep.domain.interactor.DefaultSubscriber;
import com.permutassep.domain.interactor.GetMyPostsList;

import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

public class ParseUtils {

    private final GetMyPostsList getMyPostsListUseCase;

    String PARSE_INSTALLATION_COLUMN_PS_USER = "PSUser";
    String PARSE_INSTALLATION_COLUMN_PS_SELF_UPDATED = "PSSelfUpdated";
    String PARSE_INSTALLATION_COLUMN_PS_STATE_INTERESTS = "PSStateInterests";
    String PARSE_INSTALLATION_COLUMN_PS_CITY_INTERESTS = "PSCityInterests";
    String PARSE_INSTALLATION_COLUMN_PS_TOWN_INTERESTS = "PSTownInterests";

    @Inject
    public ParseUtils(GetMyPostsList getMyPostsListUseCase) {
        this.getMyPostsListUseCase = getMyPostsListUseCase;
    }

    public void setUpParseInstallationUser(int userId) {

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if (installation.get(PARSE_INSTALLATION_COLUMN_PS_USER) == null
                ||
                Integer.valueOf(installation.get(PARSE_INSTALLATION_COLUMN_PS_USER).toString()) != userId) {
            installation.put(PARSE_INSTALLATION_COLUMN_PS_USER, userId);
            installation.saveInBackground();
        }

        if (installation.get(PARSE_INSTALLATION_COLUMN_PS_SELF_UPDATED) == null) {
            getMyPostsListUseCase.setUserId(userId);
            getMyPostsListUseCase.execute(new SyncMyPostsSubscriber());
        }
    }

    private final class SyncMyPostsSubscriber extends DefaultSubscriber<List<Post>> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Post> posts) {

            if (posts != null && posts.size() > 0) {

                TreeSet<Integer> states = new TreeSet<>();
                TreeSet<Integer> cities = new TreeSet<>();
                TreeSet<Integer> towns = new TreeSet<>();

                for (Post post : posts) {
                    states.add(Integer.valueOf(post.getStateTo()));
                    cities.add(Integer.valueOf(post.getCityTo()));
                    towns.add(Integer.valueOf(post.getTownTo()));
                }

                final ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                installation.addAll(PARSE_INSTALLATION_COLUMN_PS_STATE_INTERESTS, states);
                installation.addAll(PARSE_INSTALLATION_COLUMN_PS_CITY_INTERESTS, cities);
                installation.addAll(PARSE_INSTALLATION_COLUMN_PS_TOWN_INTERESTS, towns);
                installation.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            installation.put(PARSE_INSTALLATION_COLUMN_PS_SELF_UPDATED, 1);
                            installation.saveInBackground();
                        }
                    }
                });

            }
        }
    }
}