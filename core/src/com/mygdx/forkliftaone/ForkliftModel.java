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
    private int price;

    private float bodyWidth, bodyHeight;

//    public ForkliftModel(ModelName modelName, int numberOfTubes, MapBase map){
public ForkliftModel(ForkliftData fd, MapBase map, TextureAtlas atlas){

        spawnPosition = map.getSpawnCoordinates();

        switch (fd.getName()){
            case SMALL:
                this.numberOfTubes = fd.getTubes();
                cabin = new Vector2[4];
                cabin[0] = new Vector2(0.9f, 0.2f);
                cabin[1] = new Vector2(0.8f, 1.0f);
                cabin[2] = new Vector2(0.2f, 1.0f);
                cabin[3] = new Vector2(0.2f, 0.3f);
//                cabin[4] = new Vector2(1.6f, 0);

                engine = new Vector2[4];
                engine[0] = new Vector2(1.0f, 0.25f);
                engine[1] = new Vector2(0, 0.4f);
                engine[2] = new Vector2(0, 0);
                engine[3] = new Vector2(1.0f, 0);

                tubeSize = new float[2];
                tubeSize[0] = 0.032f;
                tubeSize[1] = 0.5f;

                rearWheelRadius = 0.1f;
                frontWheelRadius = 0.13f;
                locationOfTubes = 1.05f; // max width + 0.05f
                frontWheelPosition = new Vector2(0.83f, 0.0f);
                rearWheelPosition = new Vector2(0.13f, -0.03f);

                // Textures
                forkliftRegion = atlas.findRegion(RegionNames.SMALL_FORKLIFT);
                wheelRegion = atlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                forkRegion = atlas.findRegion(RegionNames.FORK_TEXTURE);

                bodyWidth = 1.0f; // The widest number in cabin/engine
                bodyHeight = 1.0f; // The highest number
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
                forkliftRegion = atlas.findRegion(RegionNames.FORKLIFT_BODY);
                wheelRegion = atlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                forkRegion = atlas.findRegion(RegionNames.FORK_TEXTURE);

                bodyWidth = 1.5f; // The biggest X number in cabin/engine
                bodyHeight = 1.2f; // The biggest Y number in cabin/engine

                break;
        }
    }

    public ForkliftModel(ForkliftData fd, TextureAtlas atlas){

        spawnPosition = new Vector2(0,0);

        switch (fd.getName()){
            case SMALL:
                // Textures
                forkliftRegion = atlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                wheelRegion = atlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                forkRegion = atlas.findRegion(RegionNames.FORK_TEXTURE);
                break;

            case MEDIUM:
                // Textures
                forkliftRegion = atlas.findRegion(RegionNames.FORKLIFT_BODY);
                wheelRegion = atlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                forkRegion = atlas.findRegion(RegionNames.FORK_TEXTURE);

                break;

            case LARGE:
                // Textures
                forkliftRegion = atlas.findRegion(RegionNames.BOX_TEXTURE);
                wheelRegion = atlas.findRegion(RegionNames.FORKLIFT_WHEEL);
                forkRegion = atlas.findRegion(RegionNames.FORK_TEXTURE);

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

    public float getBodyWidth() {
        return bodyWidth;
    }

    public float getBodyHeight() {
        return bodyHeight;
    }
}
