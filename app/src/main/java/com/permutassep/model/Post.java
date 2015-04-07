package com.permutassep.model;

import java.util.Date;

/**
 * Created by lalongooo on 01/03/15.
 */
public class Post {

    private User mUser;
    private Place mPlaceFrom;
    private Place mPlaceTo;
    private String mWorkdayType;
    private String mPositionType;
    private Date mPostDate;
    private boolean mIsTeachingCareer;


    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public Place getPlaceFrom() {
        return mPlaceFrom;
    }

    public void setPlaceFrom(Place mPlaceFrom) {
        this.mPlaceFrom = mPlaceFrom;
    }

    public Place getPlaceTo() {
        return mPlaceTo;
    }

    public void setPlaceTo(Place mPlaceTo) {
        this.mPlaceTo = mPlaceTo;
    }

    public String getPositionType() {
        return mPositionType;
    }

    public void setPositionType(String positionType) {
        this.mPositionType = positionType;
    }

    public String getWorkdayType() {
        return mWorkdayType;
    }

    public void setWorkdayType(String workdayType) {
        this.mWorkdayType = workdayType;
    }

    public boolean isTeachingCareer() {
        return mIsTeachingCareer;
    }

    public void setIsTeachingCareer(boolean isTeachingCareer) {
        this.mIsTeachingCareer = isTeachingCareer;
    }

    public Date getPostDate() {
        return mPostDate;
    }

    public void setPostDate(Date postDate) {
        this.mPostDate = postDate;
    }

}