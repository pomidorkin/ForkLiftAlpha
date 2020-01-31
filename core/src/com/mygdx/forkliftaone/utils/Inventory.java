package com.mygdx.forkliftaone.utils;

import java.util.List;

public class Inventory {



//    private ForkliftData[] allModels;
    private List<ForkliftData> allModels;
    private List<MapData> allMaps;
    private int balance, donateCurrency;

    public Inventory(int balance, int donateCurrency, List<ForkliftData> fd, List<MapData> md){
        this.balance = balance;
        this.donateCurrency = donateCurrency;
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
}
