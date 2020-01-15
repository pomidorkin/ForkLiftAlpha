package com.mygdx.forkliftaone.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.forkliftaone.maps.CustomTestMap;
import com.mygdx.forkliftaone.maps.MapBase;
import com.mygdx.forkliftaone.maps.TestMap;

public class MapModel {
    public enum MapName{
        CUSTOM,
        TEST
    }

    private MapBase map;

    public  MapModel(MapName md, World world, OrthographicCamera camera, Stage stage,  TextureAtlas atlas){
        switch (md){
            case CUSTOM:
                map = new CustomTestMap(world, camera, stage, atlas);
                break;

            case TEST:
                map = new TestMap(world, camera, stage, atlas);
                break;
        }

    }

    public MapBase getMap() {
        return map;
    }
}
