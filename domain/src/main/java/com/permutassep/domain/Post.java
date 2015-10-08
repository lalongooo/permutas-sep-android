package com.permutassep.domain;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import java.util.Date;

/**
 * Class that represents a Post in the domain layer
 */
public class Post {

    private int id;
    private User user;
    private String postText;
    private String stateFrom;
    private String cityFrom;
    private String townFrom;
    private String latFrom;
    private String lonFrom;
    private String stateTo;
    private String cityTo;
    private String townTo;
    private String latTo;
    private String lonTo;
    private String workdayType;
    private String positionType;
    private Date postDate;
    private boolean isTeachingCareer;
    private String academicLevel;

    public Post(){
        // empty
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
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

    public String getStateFrom() {
        return stateFrom;
    }

    public void setStateFrom(String stateFrom) {
        this.stateFrom = stateFrom;
    }

    public String getStateTo() {
        return stateTo;
    }

    public void setStateTo(String stateTo) {
        this.stateTo = stateTo;
    }

    public String getTownFrom() {
        return townFrom;
    }

    public void setTownFrom(String townFrom) {
        this.townFrom = townFrom;
    }

    public String getTownTo() {
        return townTo;
    }

    public void setTownTo(String townTo) {
        this.townTo = townTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWorkdayType() {
        return workdayType;
    }

    public void setWorkdayType(String workdayType) {
        this.workdayType = workdayType;
    }
}