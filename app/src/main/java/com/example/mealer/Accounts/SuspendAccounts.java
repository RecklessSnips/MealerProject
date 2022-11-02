package com.example.mealer.Accounts;

public class SuspendAccounts {
    private String suspendDate, cookID, suspendAccountID;


    public SuspendAccounts() {
    }

    public SuspendAccounts(String suspendDate, String cookID) {
        this.suspendDate = suspendDate;
        this.cookID = cookID;
    }


    public String getSuspendAccountID() {
        return suspendAccountID;
    }

    public void setSuspendAccountID(String suspendAccountID) {
        this.suspendAccountID = suspendAccountID;
    }

    public String getSuspendDate() {
        return suspendDate;
    }

    public void setSuspendDate(String suspendDate) {
        this.suspendDate = suspendDate;
    }

    public String getCookID() {
        return cookID;
    }

    public void setCookID(String cookID) {
        this.cookID = cookID;
    }

    @Override
    public String toString() {
        return "SuspendAccounts{" +
                "suspendDate='" + suspendDate + '\'' +
                ", cookID='" + cookID + '\'' +
                ", suspendAccountID='" + suspendAccountID + '\'' +
                '}';
    }
}
