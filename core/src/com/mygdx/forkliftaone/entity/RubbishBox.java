package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.forkliftaone.config.GameConfig;

public class RubbishBox {
    public void createRubbishBox(World world){
        Body box;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        bodyDef.position.set(5f, 5f); // Should be obtained from the map

        box = world.createBody(bodyDef);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(0.37f, 0.37f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = ps;
        fixDef.density = 0.05f;
        fixDef.friction = 1f;
        fixDef.filter.categoryBits = GameConfig.BIT_OBSTACLE;
        box.createFixture(fixDef);

        ps.setAsBox(0.37f, 0.65f);
        fixDef.shape = ps;
        fixDef.density = 0.05f;
        fixDef.friction = 1f;
        fixDef.filter.categoryBits = GameConfig.BIT_FORKLIFT;
        box.createFixture(fixDef);

        ps.dispose();
    }
}
