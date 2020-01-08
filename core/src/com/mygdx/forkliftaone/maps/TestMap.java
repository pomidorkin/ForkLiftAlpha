package com.mygdx.forkliftaone.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.forkliftaone.entity.RubbishBox;
import com.mygdx.forkliftaone.utils.AssetPaths;

public class TestMap extends MapBase {
    public TestMap(World world) {
        super(world, AssetPaths.TEST_TILED_MAP, new Vector2(1.5f, 1.5f), 10f, 1f, 1f, 0.5f);
    }


}
