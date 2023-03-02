package com.example.myapplication.model;

import java.util.Random;

public class Users{
    private String profilePic;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String shareCode;
    private String userId;
    private String lastMessage;
    private String status;
    private boolean read;

    public Users() {
    }

    public Users(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Users(String firstName, String lastName, String email){
        this.userName = firstName +" "+ lastName;
        this.email = email;
        this.shareCode = setShareCode();
    }


    public Users(String userId, String firstName, String lastName, String lastMessage, boolean read) {
        this.userName = firstName + " "+ lastName;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.read = read;
    }

    public Users(String profilePic, String firstName, String lastName, String phoneNumber, String userId, String lastMessage, String status) {
        this.profilePic = profilePic;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = phoneNumber;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String firstName, String LastName) {
        this.userName = firstName + " " + LastName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }


    public String getShareCode() {
        return shareCode;
    }

    public String setShareCode() {
        Random rand = new Random();
        this.shareCode = String.format("%04d", rand.nextInt(10000));
        return shareCode;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.email = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
