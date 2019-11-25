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

public class ProcessInventory {

    private Gson gson = new Gson();
    private File myFile = new File("inventory.json");


    public boolean write(Inventory inv){

        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(myFile));
            bw.write(gson.toJson(inv));
            bw.flush();
            bw.close();

            return true;
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    public Inventory read(){
        BufferedReader bufferedReader = null;

        if (myFile.exists()){
            try {
                bufferedReader = new BufferedReader(new FileReader(myFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ForkliftData[] fd;
            fd = new ForkliftData[1];
            fd[0] = new ForkliftData();
            fd[0].setTubes(3);
            fd[0].setName(ForkliftModel.ModelName.SMALL);
            fd[0].setEngine(3);

            Inventory inv = new Inventory(0, fd);
            write(inv);
            return inv;
        }

        Gson gson = new Gson();
        Inventory inv = gson.fromJson(bufferedReader, Inventory.class);

        return inv;
    }
}
