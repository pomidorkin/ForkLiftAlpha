package com.mygdx.forkliftaone;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.maps.MapBase;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.RegionNames;

public class ForkliftModel {

    public enum ModelName{
        SMALL,
        MEDIUM,
        LARGE
    }

    private Vector2[] cabin;
    private Vector2[] engine;
//    private Vector2[] tube;
    private float[] tubeSize;
    private float rearWheelRadius;
    private float frontWheelRadius;
    private float locationOfTubes;
    private int numberOfTubes;
    private Vector2 spawnPosition; // Should be taken from the map
    private Vector2 frontWheelPosition, rearWheelPosition;
    private ForkliftData fd;
    private TextureRegion forkliftRegion, wheelRegion, tubeRegion, forkRegion;

    private AssetManager assetManager;

//    public ForkliftModel(ModelName modelName, int numberOfTubes, MapBase map){
public ForkliftModel(ForkliftData fd, MapBase map, AssetManager assetManager){

    this.assetManager = assetManager;
    TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.TEST_ATLAS);

        spawnPosition = map.getSpawnCoordinates();

        switch (fd.getName()){
            case SMALL:
                this.numberOfTubes = fd.getTubes();
                cabin = new Vector2[5];
                cabin[0] = new Vector2(1.6f, 1.6f);
                cabin[1] = new Vector2(1f, 1.6f);
                cabin[2] = new Vector2(0.8f, 0.8f);
                cabin[3] = new Vector2(0.8f, 0);
                cabin[4] = new Vector2(1.6f, 0);

                engine = new Vector2[4];
                engine[0] = new Vector2(0.8f, 0.8f);
                engine[1] = new Vector2(0, 0.6f);
                engine[2] = new Vector2(0, 0);
                engine[3] = new Vector2(0.8f, 0);

                tubeSize = new float[2];
                tubeSize[0] = 0.032f;
                tubeSize[1] = 0.8f;

                rearWheelRadius = 0.2f;
                frontWheelRadius = 0.25f;
                locationOfTubes = 1.65f;
                frontWheelPosition = new Vector2(1.3f, 0);
                rearWheelPosition = new Vector2(0.3f, 0);

                // Textures
                forkliftRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                wheelRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                break;

            case MEDIUM:
                this.numberOfTubes = fd.getTubes();
                cabin = new Vector2[4];
                cabin[0] = new Vector2(1.0f, 1.1f);
                cabin[1] = new Vector2(0.4f, 1.2f);
                cabin[2] = new Vector2(0.3f, 0.5f);
                cabin[3] = new Vector2(1.25f, 0.5f);

                engine = new Vector2[4];
                engine[0] = new Vector2(1.5f, 0.5f);
                engine[1] = new Vector2(0, 0.6f);
                engine[2] = new Vector2(0, 0);
                engine[3] = new Vector2(1.5f, 0);

                tubeSize = new float[2];
                tubeSize[0] = 0.032f;
                tubeSize[1] = 0.7f;

                rearWheelRadius = 0.2f;
                frontWheelRadius = 0.22f;
                locationOfTubes = 1.55f;
                frontWheelPosition = new Vector2(1.27f, 0);
                rearWheelPosition = new Vector2(0.2f, 0);

                // Textures
                forkliftRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_BODY);
                wheelRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_WHEEL);

                break;
        }
    }

    public Vector2[] getCabin() {
        return cabin;
    }

    public Vector2[] getEngine() {
        return engine;
    }

    public float[] getTubeSize() {
        return tubeSize;
    }

    public float getRearWheelRadius() {
        return rearWheelRadius;
    }

    public float getFrontWheelRadius() {
        return frontWheelRadius;
    }

    public int getNumberOfTubes() {
        return numberOfTubes;
    }

    public float getLocationOfTubes() {
        return locationOfTubes;
    }

    public Vector2 getSpawnPosition() {
        return spawnPosition;
    }

    public Vector2 getFrontWheelPosition() {
        return frontWheelPosition;
    }

    public Vector2 getRearWheelPosition() {
        return rearWheelPosition;
    }

    public TextureRegion getForkliftRegion() {
        return forkliftRegion;
    }

    public TextureRegion getWheelRegion() {
        return wheelRegion;
    }

    public TextureRegion getTubeRegion() {
        return tubeRegion;
    }

    public TextureRegion getForkRegion() {
        return forkRegion;
    }
}
