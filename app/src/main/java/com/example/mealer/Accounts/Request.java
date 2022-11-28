package com.example.mealer.Accounts;

public class Request {
    private String id;
    private String clientId;
    private String mealName;
    private String status;

    public Request() {
    }

    public Request(String clientId, String mealName, String status) {
        this.clientId = clientId;
        this.mealName = mealName;
        this.status = status;
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
}
