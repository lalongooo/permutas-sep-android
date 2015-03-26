package com.permutassep.model;

/**
 * Created by lalongooo on 26/03/15.
 */
public class SocialUser extends User {

    private SocialNetwork socialNetwork;
    private String socialUserId;

    public SocialUser(String name, String email, String phone, String password, SocialNetwork socialNetwork, String socialUserId){
        super(name, email, phone, password);
        this.socialNetwork = socialNetwork;
        this.socialUserId = socialUserId;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getSocialUserId() {
        return socialUserId;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }

    public enum SocialNetwork {
        FACEBOOK,
        TWITTER
    }
}