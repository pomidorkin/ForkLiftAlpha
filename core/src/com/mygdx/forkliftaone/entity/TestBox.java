package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.RegionNames;

import java.util.Random;

public class TestBox extends BoxBase {
    public TestBox(World world, Camera camera, TextureAtlas atlas, Vector2 coords) {
        super(world, camera,  atlas,0.1f, 0.3f, 0.3f, RegionNames.BOX_TEXTURE, coords);

        setPrice(new Random().nextInt(5) + 1);

    }
}
