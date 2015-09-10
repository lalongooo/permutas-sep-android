package com.permutassep.data.repository.datasource;


import com.permutassep.data.cache.UserCache;
import com.permutassep.data.entity.UserEntity;
import com.permutassep.data.repository.datasource.rest.permutassep.PermutasSEPRestClient;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudUserDataStore implements UserDataStore {

    private final PermutasSEPRestClient restApi;
    private final UserCache userCache;

    private final Action1<UserEntity> saveToCacheAction =
            userEntity -> {
                if (userEntity != null) {
                    CloudUserDataStore.this.userCache.put(userEntity);
                }
            };

    public CloudUserDataStore(UserCache userCache) {
        this.userCache = userCache;
        this.restApi = new PermutasSEPRestClient();
    }

    @Override
    public Observable<List<UserEntity>> userEntityList() {
        return this.restApi.get().getUsers();
    }

    @Override
    public Observable<UserEntity> userEntityDetails(final int userId) {
        return this.restApi.get().getUser(userId)
                .doOnNext(saveToCacheAction);
    }
}
