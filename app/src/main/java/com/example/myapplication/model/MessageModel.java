package com.example.myapplication.model;

public class MessageModel {
    private String userId;
    private String message;
    private String messageId;
    private Long timeStamp;
    private String visibility;


    public MessageModel(String userId, String message,  Long timeStamp) {
        this.userId = userId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public MessageModel(String userId, String message, String messageId, Long timeStamp, String visibility) {
        this.userId = userId;
        this.message = message;
        this.messageId = messageId;
        this.timeStamp = timeStamp;
        this.visibility = visibility;
    }

    public MessageModel(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }


    public MessageModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
