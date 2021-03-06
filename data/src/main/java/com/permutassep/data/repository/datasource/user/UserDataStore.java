/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.permutassep.data.repository.datasource.user;


import com.permutassep.data.entity.UserEntity;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface UserDataStore {
    /**
     * Get an {@link Observable} which will emit a List of {@link UserEntity}.
     */
    Observable<List<UserEntity>> getUsersList();

    /**
     * Get an {@link Observable} which will emit a {@link UserEntity} by its id.
     *
     * @param userId The id to retrieve user data.
     */
    Observable<UserEntity> getUserDetails(final int userId);
}
