package com.example.mealer.Accounts;

public class Client extends Account{
    private String creditCard;

    public Client() {

    }

    public Client(String firstName, String lastName, String email, String pwd, String address, String creditCard) {
        super(firstName, lastName, email, pwd, address);
        this.creditCard = creditCard;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", address='" + address + '\'' +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
