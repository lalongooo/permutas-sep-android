package com.permutassep.domain.repository;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.domain.LoginDataWrapper;
import com.permutassep.domain.User;

import rx.Observable;

public interface AuthenticationRepository {

    Observable<User> login(LoginDataWrapper loginDataWrapper);
}