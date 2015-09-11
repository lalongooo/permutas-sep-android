package com.permutassep.presentation.model;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */


import java.util.Date;

/**
 * Class that represents a Post in the presentation layer
 */
public class PostModel {

    private int id;
    private UserModel user;
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
    private String workdayType;
    private String positionType;
    private Date postDate;
    private boolean isTeachingCareer;
    private String academicLevel;

    public PostModel(){
        // empty
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
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

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTeachingCareer() {
        return isTeachingCareer;
    }

    public void setIsTeachingCareer(boolean isTeachingCareer) {
        this.isTeachingCareer = isTeachingCareer;
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

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getWorkdayType() {
        return workdayType;
    }

    public void setWorkdayType(String workdayType) {
        this.workdayType = workdayType;
    }
}