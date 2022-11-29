package com.example.mealer.Accounts;

public class Request {
    private String id;
    private String clientId, cookID;
    private String mealName;
    private String status;
    private String pickUpTime;


    public Request() {
    }

    public Request(String clientId, String cookID, String mealName, String status, String pickUpTime) {
        this.clientId = clientId;
        this.cookID = cookID;
        this.mealName = mealName;
        this.status = status;
        this.pickUpTime = pickUpTime;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookID() {
        return cookID;
    }

    public void setCookID(String cookID) {
        this.cookID = cookID;
    }
}
