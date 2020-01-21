package com.mygdx.forkliftaone.utils;

import com.mygdx.forkliftaone.ForkliftModel;

public class ForkliftData {
    private ForkliftModel.ModelName name;
    private int engine;
    private int tubes;
    private int price;
    private Boolean purchased;

    // To be added: price, TextureRegion

    public ForkliftModel.ModelName getName() {
        return name;
    }

    public void setPurchased(Boolean status){
        this.purchased = status;
    }

    public Boolean getPurchased(){
        return purchased;
    }

    public void setName(ForkliftModel.ModelName name) {
        this.name = name;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getTubes() {
        return tubes;
    }

    public void setTubes(int tubes) {
        this.tubes = tubes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
