package com.mygdx.forkliftaone.utils;

import java.util.List;

public class Inventory {



//    private ForkliftData[] allModels;
    private List<ForkliftData> allModels;
    private List<MapData> allMaps;
    private int balance, donateCurrency;
    private boolean donateBoxesPurchased;

    public Inventory(int balance, int donateCurrency, boolean donateBoxesPurchased, List<ForkliftData> fd, List<MapData> md){
        this.balance = balance;
        this.donateCurrency = donateCurrency;
        this.donateBoxesPurchased = donateBoxesPurchased;
        this.allMaps = md;
        allModels = fd;
    };

    public List<ForkliftData> getAllModels() {
        return allModels;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<MapData> getAllMaps() {
        return allMaps;
    }

    public int getDonateCurrency() {
        return donateCurrency;
    }

    public void setDonateCurrency(int donateCurrency) {
        this.donateCurrency = donateCurrency;
    }

    public boolean isDonateBoxesPurchased() {
        return donateBoxesPurchased;
    }

    public void setDonateBoxesPurchased(boolean donateBoxesPurchased) {
        this.donateBoxesPurchased = donateBoxesPurchased;
    }
}
