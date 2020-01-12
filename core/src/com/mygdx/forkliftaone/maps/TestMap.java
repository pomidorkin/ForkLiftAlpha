package com.mygdx.forkliftaone.maps;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.forkliftaone.entity.TestBox;
import com.mygdx.forkliftaone.utils.AssetPaths;
import com.mygdx.forkliftaone.utils.BoxFactory;

public class TestMap extends MapBase {
    private Vector2[] boxCoords;
    private BoxFactory factory;
    private World world;
    private TextureAtlas atlas;
    private Camera camera;
    private Stage stage;

    public TestMap(World world, Camera camera, Stage stage, TextureAtlas atlas) {
        super(world, AssetPaths.TEST_TILED_MAP, new Vector2(1.5f, 1.5f), 10f, 1f, 1f, 0.5f);

        this.world = world;
        this.atlas = atlas;
        this.camera = camera;
        this.stage = stage;
        factory = new BoxFactory();

        boxCoords = new Vector2[2];
        boxCoords[0] = new Vector2(5f, 5f);
        boxCoords[1] = new Vector2(5.5f, 5f);

    }

    public void spawnBoxes() {
        for (Vector2 coord : boxCoords) {
            stage.addActor(factory.getFuelCan(world, camera, atlas, coord));
//            stage.addActor(factory.getBox(world, camera, atlas, coord));

        }
    }

    @Override
    public void openDoor() {
        // Some code here
    }
}
