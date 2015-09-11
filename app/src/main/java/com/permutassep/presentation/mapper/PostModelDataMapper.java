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

import com.permutassep.domain.Post;
import com.permutassep.domain.User;
import com.permutassep.presentation.internal.di.PerActivity;
import com.permutassep.presentation.model.PostModel;
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
public class PostModelDataMapper {

    UserModelDataMapper userModelDataMapper;

    @Inject
    public PostModelDataMapper(UserModelDataMapper userModelDataMapper) {
        this.userModelDataMapper = userModelDataMapper;
    }

    /**
     * Transform a {@link Post} into an {@link PostModel}.
     *
     * @param post Object to be transformed.
     * @return {@link PostModel}.
     */
    public PostModel transform(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        PostModel postModel = new PostModel();
        postModel.setAcademicLevel(post.getAcademicLevel());
        postModel.setCityFrom(post.getCityFrom());
        postModel.setCityTo(post.getCityTo());
        postModel.setId(post.getId());
        postModel.setIsTeachingCareer(post.isTeachingCareer());
        postModel.setLatFrom(post.getLatFrom());
        postModel.setLatTo(post.getLatTo());
        postModel.setLonFrom(post.getLonFrom());
        postModel.setLonTo(post.getLonTo());
        postModel.setPositionType(post.getPositionType());
        postModel.setPostDate(post.getPostDate());
        postModel.setPostText(post.getPostText());
        postModel.setStateFrom(post.getStateFrom());
        postModel.setStateTo(post.getStateTo());
        postModel.setTownFrom(post.getTownFrom());
        postModel.setTownTo(post.getTownTo());
        postModel.setUser(userModelDataMapper.transform(post.getUser()));
        postModel.setWorkdayType(post.getWorkdayType());

        return postModel;
    }

    /**
     * Transform a Collection of {@link Post} into a Collection of {@link PostModel}.
     *
     * @param postsCollection Objects to be transformed.
     * @return List of {@link PostModel}.
     */
    public Collection<PostModel> transform(Collection<Post> postsCollection) {
        Collection<PostModel> postModelCollection;

        if (postsCollection != null && !postsCollection.isEmpty()) {
            postModelCollection = new ArrayList<>();
            for (Post post : postsCollection) {
                postModelCollection.add(transform(post));
            }
        } else {
            postModelCollection = Collections.emptyList();
        }

        return postModelCollection;
    }
}
