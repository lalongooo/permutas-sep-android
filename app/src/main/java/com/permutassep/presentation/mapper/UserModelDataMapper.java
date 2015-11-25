package com.permutassep.presentation.mapper;

import com.permutassep.domain.User;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.model.UserModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link User} (in the domain layer)
 * to {@link UserModel} in the presentation layer.
 */
@PerActivity
public class UserModelDataMapper {

    @Inject
    public UserModelDataMapper() {
    }

    /**
     * Transform a {@link User} into an {@link UserModel}.
     *
     * @param user Object to be transformed.
     * @return {@link UserModel}.
     */
    public UserModel transform(User user) {
        UserModel userModel = null;
        if (user != null) {
            userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            userModel.setPassword(user.getPassword());
            userModel.setPhone(user.getPhone());
            userModel.setSocialUserId(user.getSocialUserId());
        }

        return userModel;
    }

    /**
     * Transform a Collection of {@link User} into a Collection of {@link UserModel}.
     *
     * @param usersCollection Objects to be transformed.
     * @return List of {@link UserModel}.
     */
    public Collection<UserModel> transform(Collection<User> usersCollection) {
        Collection<UserModel> userModelsCollection;

        if (usersCollection != null && !usersCollection.isEmpty()) {
            userModelsCollection = new ArrayList<>();
            for (User user : usersCollection) {
                userModelsCollection.add(transform(user));
            }
        } else {
            userModelsCollection = Collections.emptyList();
        }

        return userModelsCollection;
    }


    /**
     * Transform a {@link UserModel} into an {@link User}.
     *
     * @param userModel Object to be transformed.
     * @return {@link User}.
     */
    public User transform(UserModel userModel) {
        User user = null;
        if (userModel != null) {
            user = new User();
            user.setId(userModel.getId());
            user.setName(userModel.getName());
            user.setEmail(userModel.getEmail());
            user.setPassword(userModel.getPassword());
            user.setPhone(userModel.getPhone());
            user.setSocialUserId(userModel.getSocialUserId());
        }

        return user;
    }
}
