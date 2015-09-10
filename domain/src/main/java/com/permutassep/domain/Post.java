package com.permutassep.domain;

/*
* By Jorge E. Hernandez (@lalongooo) 2015
* */

import java.util.Date;

/**
 * Created by lalongooo on 01/03/15.
 */
public class Post {

    private int id;
    private User mUser;
    private String postText;
    private short stateFrom;
    private short cityFrom;
    private short townFrom;
    private String latFrom;
    private String lonFrom;
    private short stateTo;
    private short cityTo;
    private short townTo;
    private String latTo;
    private String lonTo;
    private String mWorkdayType;
    private String mPositionType;
    private Date mPostDate;
    private boolean mIsTeachingCareer;
    private String mAcademicLevel;

    public Post(int id) {
        this.id = id;
    }

    public short getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(short cityFrom) {
        this.cityFrom = cityFrom;
    }

    public short getCityTo() {
        return cityTo;
    }

    public void setCityTo(short cityTo) {
        this.cityTo = cityTo;
    }

    public int getId() {
        return id;
    }

    public String getLatFrom() {
        return latFrom;
    }

    public void setLatFrom(String latFrom) {
        this.latFrom = latFrom;
    }

    public String getLatTo() {
        return latTo;
    }

    public void setLatTo(String latTo) {
        this.latTo = latTo;
    }

    public String getLonFrom() {
        return lonFrom;
    }

    public void setLonFrom(String lonFrom) {
        this.lonFrom = lonFrom;
    }

    public String getLonTo() {
        return lonTo;
    }

    public void setLonTo(String lonTo) {
        this.lonTo = lonTo;
    }

    public String getmAcademicLevel() {
        return mAcademicLevel;
    }

    public void setmAcademicLevel(String mAcademicLevel) {
        this.mAcademicLevel = mAcademicLevel;
    }

    public boolean ismIsTeachingCareer() {
        return mIsTeachingCareer;
    }

    public void setmIsTeachingCareer(boolean mIsTeachingCareer) {
        this.mIsTeachingCareer = mIsTeachingCareer;
    }

    public String getmPositionType() {
        return mPositionType;
    }

    public void setmPositionType(String mPositionType) {
        this.mPositionType = mPositionType;
    }

    public Date getmPostDate() {
        return mPostDate;
    }

    public void setmPostDate(Date mPostDate) {
        this.mPostDate = mPostDate;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public String getmWorkdayType() {
        return mWorkdayType;
    }

    public void setmWorkdayType(String mWorkdayType) {
        this.mWorkdayType = mWorkdayType;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public short getStateFrom() {
        return stateFrom;
    }

    public void setStateFrom(short stateFrom) {
        this.stateFrom = stateFrom;
    }

    public short getStateTo() {
        return stateTo;
    }

    public void setStateTo(short stateTo) {
        this.stateTo = stateTo;
    }

    public short getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(short townFrom) {
        this.townFrom = townFrom;
    }

    public short getTownTo() {
        return townTo;
    }

    public void setTownTo(short townTo) {
        this.townTo = townTo;
    }

    @Override
    public String toString() {
        return "Post{" +
                "cityFrom=" + cityFrom +
                ", id=" + id +
                ", mUser=" + mUser +
                ", postText='" + postText + '\'' +
                ", stateFrom=" + stateFrom +
                ", townFrom=" + townFrom +
                ", latFrom='" + latFrom + '\'' +
                ", lonFrom='" + lonFrom + '\'' +
                ", stateTo=" + stateTo +
                ", cityTo=" + cityTo +
                ", townTo=" + townTo +
                ", latTo='" + latTo + '\'' +
                ", lonTo='" + lonTo + '\'' +
                ", mWorkdayType='" + mWorkdayType + '\'' +
                ", mPositionType='" + mPositionType + '\'' +
                ", mPostDate=" + mPostDate +
                ", mIsTeachingCareer=" + mIsTeachingCareer +
                ", mAcademicLevel='" + mAcademicLevel + '\'' +
                '}';
    }
}