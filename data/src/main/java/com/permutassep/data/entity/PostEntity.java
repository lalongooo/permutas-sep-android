package com.permutassep.data.entity;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Class that represents a Post in the data layer
 */

public class PostEntity {

    @SerializedName("id")
    private int id;
    @SerializedName("user")
    private UserEntity mUserEntity;
    @SerializedName("post")
    private String postText;
    @SerializedName("place_from_state")
    private String stateFrom;
    @SerializedName("place_from_city")
    private String cityFrom;
    @SerializedName("place_from_town")
    private String townFrom;
    @SerializedName("place_from_lat")
    private String latFrom;
    @SerializedName("place_from_lon")
    private String lonFrom;
    @SerializedName("place_to_state")
    private String stateTo;
    @SerializedName("place_to_city")
    private String cityTo;
    @SerializedName("place_to_town")
    private String townTo;
    @SerializedName("place_to_lat")
    private String latTo;
    @SerializedName("place_to_lon")
    private String lonTo;
    @SerializedName("workday_type")
    private String mWorkdayType;
    @SerializedName("position_type")
    private String mPositionType;
    @SerializedName("post_date")
    private String mPostDate;
    @SerializedName("is_teaching_career")
    private boolean mIsTeachingCareer;
    @SerializedName("academic_level")
    private String mAcademicLevel;

    private String stateFromCode;
    private String stateToCode;

    public PostEntity(){
        // empty
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return mUserEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.mUserEntity = userEntity;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getStateFrom() {
        return stateFrom;
    }

    public void setStateFrom(String stateFrom) {
        this.stateFrom = stateFrom;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(String townFrom) {
        this.townFrom = townFrom;
    }

    public String getLatFrom() {
        return latFrom;
    }

    public void setLatFrom(String latFrom) {
        this.latFrom = latFrom;
    }

    public String getLonFrom() {
        return lonFrom;
    }

    public void setLonFrom(String lonFrom) {
        this.lonFrom = lonFrom;
    }

    public String getStateTo() {
        return stateTo;
    }

    public void setStateTo(String stateTo) {
        this.stateTo = stateTo;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public String getTownTo() {
        return townTo;
    }

    public void setTownTo(String townTo) {
        this.townTo = townTo;
    }

    public String getLatTo() {
        return latTo;
    }

    public void setLatTo(String latTo) {
        this.latTo = latTo;
    }

    public String getLonTo() {
        return lonTo;
    }

    public void setLonTo(String lonTo) {
        this.lonTo = lonTo;
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

    public String getPostDate() {
        return mPostDate;
    }

    public void setPostDate(String postDate) {
        this.mPostDate = postDate;
    }

    public String getAcademicLevel() {
        return mAcademicLevel;
    }

    public void setAcademicLevel(String mAcademicLevel) {
        this.mAcademicLevel = mAcademicLevel;
    }

    public String getStateFromCode() {
        return stateFromCode;
    }

    public void setStateFromCode(String stateFromCode) {
        this.stateFromCode = stateFromCode;
    }

    public String getStateToCode() {
        return stateToCode;
    }

    public void setStateToCode(String stateToCode) {
        this.stateToCode = stateToCode;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", mUserEntity=" + mUserEntity +
                ", postText='" + postText + '\'' +
                ", stateFrom=" + stateFrom +
                ", cityFrom=" + cityFrom +
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