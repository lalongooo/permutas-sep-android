package com.permutassep.data.entity.mapper;

import com.permutassep.data.entity.EmailEntity;
import com.permutassep.domain.Email;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */
@Singleton
public class EmailEntityDataMapper {

    @Inject
    public EmailEntityDataMapper() {
    }

    public Email transform(EmailEntity emailEntity) {
        Email email = null;
        if (emailEntity != null) {
            email = new Email(emailEntity.getEmail());
        }
        return email;
    }

    public EmailEntity transform(Email email) {
        EmailEntity emailEntity = null;
        if (email != null) {
            emailEntity = new EmailEntity(email.getEmail());
        }
        return emailEntity;
    }
}
