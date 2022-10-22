package com.example.mealer.datas;

public class Account {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String pwd;
    private String address;
    private String userDescription;

    public Account() {
    }

    public Account(String firstName, String lastName, String emailAddress, String pwd, String address, String userDescription) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.pwd = pwd;
        this.address = address;
        this.userDescription = userDescription;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    @Override
    public String toString() {
        return "Account{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", pwd='" + pwd + '\'' +
                ", address='" + address + '\'' +
                ", userDescription='" + userDescription + '\'' +
                '}';
    }
}
