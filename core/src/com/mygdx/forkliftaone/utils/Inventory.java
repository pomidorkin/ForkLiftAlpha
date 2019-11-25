package com.mygdx.forkliftaone.utils;

import com.google.gson.Gson;
import com.mygdx.forkliftaone.ForkliftModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Inventory {



    private ForkliftData[] purchasedModels;
    private int balance;

    public Inventory(int balance, ForkliftData[] fd){
        this.balance = balance;
        purchasedModels = fd;
    };

    public ForkliftData[] getPurchasedModels() {
        return purchasedModels;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
