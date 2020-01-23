package com.mygdx.forkliftaone.utils;

import com.mygdx.forkliftaone.ForkliftModel;

public class MapData {
    private  MapModel.MapName name;
    private Boolean purchased;
    private int price;

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public MapModel.MapName getName() {
        return name;
    }

    public void setName(MapModel.MapName name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
