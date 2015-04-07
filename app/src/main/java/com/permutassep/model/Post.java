package com.permutassep.model;

import java.util.Date;

/**
 * Created by lalongooo on 01/03/15.
 */
public class Post {

    private User mUser;

    private State mStateFrom;
    private City mCityFrom;
    private Town mTownFrom;

    private State mStateTo;
    private City mCityTo;
    private Town mTownTo;

    private String mPositionType;
    private String mWorkdayType;

    private boolean mIsTeachingCareer;

    private Date mPostDate;

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public State getStateFrom() {
        return mStateFrom;
    }

    public void setStateFrom(State stateFrom) {
        this.mStateFrom = stateFrom;
    }

    public City getCityFrom() {
        return mCityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.mCityFrom = cityFrom;
    }

    public Town getTownFrom() {
        return mTownFrom;
    }

    public void setTownFrom(Town townFrom) {
        this.mTownFrom = townFrom;
    }

    public State getStateTo() {
        return mStateTo;
    }

    public void setStateTo(State stateTo) {
        this.mStateTo = stateTo;
    }

    public City getCityTo() {
        return mCityTo;
    }

    public void setCityTo(City cityTo) {
        this.mCityTo = cityTo;
    }

    public Town getTownTo() {
        return mTownTo;
    }

    public void setTownTo(Town townTo) {
        this.mTownTo = townTo;
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

    @Override
    public String toString() {
        return "Post{" +
                "mUser=" + mUser +
                ", mStateFrom=" + mStateFrom +
                ", mCityFrom=" + mCityFrom +
                ", mTownFrom=" + mTownFrom +
                ", mStateTo=" + mStateTo +
                ", mCityTo=" + mCityTo +
                ", mTownTo=" + mTownTo +
                ", mPositionType='" + mPositionType + '\'' +
                ", mWorkdayType='" + mWorkdayType + '\'' +
                ", mIsTeachingCareer=" + mIsTeachingCareer +
                ", mPostDate=" + mPostDate +
                '}';
    }
}