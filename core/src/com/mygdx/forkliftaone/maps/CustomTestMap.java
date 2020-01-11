package com.mygdx.forkliftaone.maps;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.forkliftaone.utils.AssetPaths;
import com.mygdx.forkliftaone.utils.BoxFactory;

public class CustomTestMap extends MapBase {
    private Vector2[][] boxCoords;
    private BoxFactory factory;
    private World world;
    private TextureAtlas atlas;
    private Camera camera;
    private Stage stage;

    public CustomTestMap(World world, Camera camera, Stage stage, TextureAtlas atlas) {
        super(world, AssetPaths.CUSTOM_TILED_MAP, new Vector2(1.5f, 1.5f), 10f, 1f, 1f, 0.5f);

        this.world = world;
        this.atlas = atlas;
        this.camera = camera;
        this.stage = stage;
        factory = new BoxFactory();

        // Logic for spawning different number of different goods
        // Total number of good-types (4 = fuel, cheap, middle, expensive)
        boxCoords = new Vector2[4][];
        // Only one fuel will be spawned
        boxCoords[0] = new Vector2[1];
        // Two cheap will be spawned
        boxCoords[1] = new Vector2[2];
        // Only one middle will be spawned
        boxCoords[2] = new Vector2[1];
        // Only one expensive will be spawned
        boxCoords[3] = new Vector2[1];


        // Coordinates of each box
        boxCoords[0][0] = new Vector2(5f, 5f);
//        boxCoords[0][1] = new Vector2(5.5f, 5f);

        boxCoords[1][0] = new Vector2(5f, 5f);
        boxCoords[1][1] = new Vector2(5.5f, 5f);

        boxCoords[2][0] = new Vector2(5f, 5f);
//        boxCoords[2][1] = new Vector2(5.5f, 5f);
//
        boxCoords[3][0] = new Vector2(5f, 5f);
//        boxCoords[3][1] = new Vector2(5.5f, 5f);
    }

    public void spawnBoxes() {
        // Spawn fuel
        for (Vector2 coord : boxCoords[0]) {
                stage.addActor(factory.getFuelCan(world, camera, atlas, coord));
//            stage.addActor(factory.getBox(world, camera, atlas, coord));
        }

        // Spawn cheap goods
        for (Vector2 coord : boxCoords[1]) {
            stage.addActor(factory.getBox(world, camera, atlas, coord));
        }

        // Spawn middle goods
        for (Vector2 coord : boxCoords[2]) {
            stage.addActor(factory.getBox(world, camera, atlas, coord));
        }

        // Spawn expeisive goods
        for (Vector2 coord : boxCoords[3]) {
            stage.addActor(factory.getBox(world, camera, atlas, coord));
        }
    }
}
