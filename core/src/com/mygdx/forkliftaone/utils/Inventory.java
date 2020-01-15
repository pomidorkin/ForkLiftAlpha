package com.mygdx.forkliftaone.utils;

import java.util.List;

public class Inventory {



//    private ForkliftData[] allModels;
    private List<ForkliftData> allModels;
    private MapData[] allMaps;
    private int balance;

    public Inventory(int balance, List<ForkliftData> fd, MapData[] md){
        this.balance = balance;
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

    public MapData[] getAllMaps() {
        return allMaps;
    }
}
