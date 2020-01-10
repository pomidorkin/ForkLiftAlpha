package com.mygdx.forkliftaone.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.forkliftaone.entity.BoxBase;
import com.mygdx.forkliftaone.entity.FuelCan;
import com.mygdx.forkliftaone.entity.TestBox;

public class BoxFactory {

    public BoxBase getBox(World world, Camera camera, TextureAtlas atlas, Vector2 coords){
        return new TestBox(world, camera, atlas, coords);
    }

    public BoxBase getFuelCan(World world, Camera camera, TextureAtlas atlas, Vector2 coords){
        return new FuelCan(world, camera, atlas, coords);
    }
}
