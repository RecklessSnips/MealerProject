package com.example.mealer.Accounts;

public class Cook extends Account{
    // void cheque should be a pic
    private String voidCheque;
    private String description;
    // attributes to determine whether this cook is active
    private String isActive = "active";
    private String numberSold;

    // to avoid JAVA reflection issues
    public Cook() {
    }

    public Cook(String firstName, String lastName, String email, String pwd, String address, String voidCheque, String description) {
        super(firstName, lastName, email, pwd, address);
//        this.numberSold = numberSold;
        this.voidCheque = voidCheque;
        this.description = description;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getVoidCheque() {
        return voidCheque;
    }

    public void setVoidCheque(String voidCheque) {
        this.voidCheque = voidCheque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberSold() {
        return numberSold;
    }

    public void setNumberSold(String numberSold) {
        this.numberSold = numberSold;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", address='" + address + '\'' +
                ", void cheque='" + voidCheque + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
