package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.forkliftaone.config.GameConfig;

public class RubbishBox extends Actor {
    private Camera camera;
    private World world;
    private Body body;
    private static int counter = 0;

    public RubbishBox(World world, Camera camera){
        this.camera = camera;
        this.world = world;
        this.body = createRubbishBox();
        counter += 1;
    }

    public Body createRubbishBox(){


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
        box.createFixture(fixDef).setUserData(this); // required for collision

        ps.dispose();

        return box;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isBoxInCamera()){
            // draw box
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        isBoxInCamera();
    }

    private boolean isBoxInCamera(){
        if (camera.frustum.pointInFrustum(new Vector3(body.getPosition().x, body.getPosition().y, 0))){
            return true;
        }
        return  false;
    }
}
