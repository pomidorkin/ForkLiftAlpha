package com.mygdx.forkliftaone.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.mygdx.forkliftaone.ForkliftModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessInventoryImproved {

//    Json json = new Json();
 Gson gson = new Gson();
    FileHandle myFile = Gdx.files.local("inventory.json");
    FileHandle generalData = Gdx.files.local("generalData.json");

    public boolean write(Inventory inv){
        try
        {
//            myFile.writeString(json.toJson(inv), false);
            myFile.writeString(gson.toJson(inv), false);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean write(GeneralData gd){
        try
        {
//            generalData.writeString(json.toJson(gd), false);
            generalData.writeString(gson.toJson(gd), false);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Inventory read(){
        String reader = null;

        if (myFile.exists()){
            try {
//                FileHandle file = Gdx.files.local(myFile.path());
//                reader = file.readString();
                reader = myFile.readString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Creating new jSon inventory file, where only SMALL forklift is purchased

            List<ForkliftData> fd = new ArrayList<>();
            fd.add(new ForkliftData());
            fd.get(0).setTubes(3);
            fd.get(0).setName(ForkliftModel.ModelName.SMALL);
            fd.get(0).setEngine(3);
            fd.get(0).setPrice(0);
            fd.get(0).setPurchased(true);

            // Map saving
            List<MapData> md = new ArrayList<>();
            md.add(new MapData());
            md.get(0).setName(MapModel.MapName.TEST);
            md.get(0).setPurchased(true);

            SettingsData sd = new SettingsData();
            sd.setMusicVolume(1f);
            sd.setSoundVolume(1f);


            Inventory inv = new Inventory(15000, 0, false, fd, md, sd);
            write(inv);
            return inv;
        }

//        Json json = new Json();
//        Inventory inv = json.fromJson(Inventory.class, reader);
        Gson gson = new Gson();
        Inventory inv = gson.fromJson(reader, Inventory.class);

        return inv;
    }

    public GeneralData readGeneralData(){
        String reader = null;

        // General data (created for updates)
        if (generalData.exists()){
            try {
                FileHandle file = Gdx.files.local(generalData.path());
                reader = file.readString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            // Creating new jSon inventory file, where only SMALL forklift is purchased

            ForkliftData[] fd;
            fd = new ForkliftData[3];
            fd[0] = new ForkliftData();
            fd[0].setTubes(3);
            fd[0].setName(ForkliftModel.ModelName.SMALL);
            fd[0].setEngine(3);
            fd[0].setPrice(0);
            fd[0].setPurchased(true);

            fd[1] = new ForkliftData();
            fd[1].setTubes(3);
            fd[1].setName(ForkliftModel.ModelName.MEDIUM);
            fd[1].setEngine(3);
            fd[1].setPrice(3000);
            fd[1].setPurchased(false);

            fd[2] = new ForkliftData();
            fd[2].setTubes(3);
            fd[2].setName(ForkliftModel.ModelName.LARGE);
            fd[2].setEngine(3);
            fd[2].setPrice(17000);
            fd[2].setPurchased(false);

            // Map saving
            MapData[] md;
            md = new MapData[2];
            md[0] = new MapData();
            md[0].setName(MapModel.MapName.CUSTOM);
            md[0].setPrice(100);
            md[0].setPurchased(false);

            md[1] = new MapData();
            md[1].setName(MapModel.MapName.TEST);
            md[1].setPrice(500);
            md[1].setPurchased(true);


            GeneralData gd = new GeneralData(fd, md);
            write(gd);
            return gd;
        }
//        Json json = new Json();
//        GeneralData gd = json.fromJson(GeneralData.class, reader);

        Gson gson = new Gson();
        GeneralData gd = gson.fromJson(reader, GeneralData.class);

        return gd;
    }

}
