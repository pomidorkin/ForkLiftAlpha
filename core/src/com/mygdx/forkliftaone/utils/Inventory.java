package com.mygdx.forkliftaone.utils;

public class Inventory {



    private ForkliftData[] allModels;
    private int balance;

    public Inventory(int balance, ForkliftData[] fd){
        this.balance = balance;
        allModels = fd;
    };

    public ForkliftData[] getAllModels() {
        return allModels;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
