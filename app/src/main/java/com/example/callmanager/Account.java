package com.example.callmanager;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("id")
    private int id;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("email")
    private String email;
    @SerializedName("firebaseId")
    private String firebaseId;
    @SerializedName("balance")
    private double balance;

    public Account(String phone_number, String email, String firebaseId, double balance) {
        this.phone_number = phone_number;
        this.email = email;
        this.firebaseId = firebaseId;
        this.balance = balance;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
