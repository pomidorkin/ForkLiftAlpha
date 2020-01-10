package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.forkliftaone.utils.RegionNames;

public class FuelCan extends BoxBase {
    private boolean active;
    public FuelCan(World world, Camera camera, TextureAtlas atlas, Vector2 coords) {
        super(world, camera, atlas, 0.1f, RegionNames.FORKLIFT_WHEEL, coords);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
