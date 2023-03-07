package com.example.myapplication.model;

public class FeedModel {

    String message,uId, visibility, username;
    Long timeStamp;

    public FeedModel(){

    }
    public FeedModel(String message, String uId, String visibility, Long timeStamp) {
        this.message = message;
        this.uId = uId;
        this.visibility = visibility;
        this.timeStamp = timeStamp;
    }

    public FeedModel(String message, String uId, String visibility) {
        this.message = message;
        this.uId = uId;
        this.visibility = visibility;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
