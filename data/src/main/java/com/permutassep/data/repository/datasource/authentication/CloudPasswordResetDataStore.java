package com.permutassep.data.repository.datasource.authentication;

import com.permutassep.data.entity.ConfirmPasswordResetEntity;
import com.permutassep.data.entity.EmailEntity;
import com.permutassep.data.repository.datasource.restful.permutassep.PermutasSEPRestClient;

import javax.inject.Inject;

import rx.Observable;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
public class CloudPasswordResetDataStore implements PasswordResetDataStore {

    private final PermutasSEPRestClient restClient;

    @Inject
    public CloudPasswordResetDataStore() {
        restClient = new PermutasSEPRestClient();
    }

    @Override
    public Observable<String> resetPassword(EmailEntity emailEntity) {
        return restClient.get()
                .resetPassword(emailEntity)
                .flatMap(response -> Observable.just(String.valueOf(response.getStatus())));
    }

    @Override
    public Observable<String> confirmPasswordReset(ConfirmPasswordResetEntity confirmPasswordResetEntity) {
        return restClient.get()
                .confirmPasswordReset(confirmPasswordResetEntity)
                .flatMap(response -> Observable.just(String.valueOf(response.getStatus())));
    }
}