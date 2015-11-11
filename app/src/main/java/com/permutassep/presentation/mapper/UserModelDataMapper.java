/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            user.setId(user.getId());
            user.setName(user.getName());
            user.setEmail(user.getEmail());
            user.setPassword(user.getPassword());
            user.setPhone(user.getPhone());
            user.setSocialUserId(user.getSocialUserId());
        }

        return user;
    }
}
