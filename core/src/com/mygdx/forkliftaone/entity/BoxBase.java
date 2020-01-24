package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.RegionNames;

public abstract class BoxBase extends Actor {
    private Camera camera;
    private World world;
    private Body body;
    private float density;
    private float boxWidth, boxHeight;// Should by multiplied by 2
    private TextureRegion goodTexture;
    private Vector2 position;
    private int price;
    private boolean dead;

    public BoxBase(World world, Camera camera, TextureAtlas atlas, float boxDensity,
                   float boxWidth, float boxHeight, String goodTexture, Vector2 coords){
        this.camera = camera;
        this.world = world;
        this.position = coords;
        this.density = boxDensity;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;

        this.body = createRubbishBox();


//        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.TEST_ATLAS);

        this.goodTexture = atlas.findRegion(goodTexture);
    }

    public Body createRubbishBox(){


        Body box;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;
        bodyDef.position.set(position); // Should be obtained from the map

        box = world.createBody(bodyDef);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(boxWidth, boxHeight);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = ps;
        fixDef.density = 0.05f;
        fixDef.friction = 1f;
        fixDef.filter.categoryBits = GameConfig.BIT_OBSTACLE;
        box.createFixture(fixDef).setUserData(this); // required for collision

//        ps.setAsBox(0.37f, 0.65f);
        ps.setAsBox(boxWidth, 0.10f, new Vector2(0, -(boxHeight + 0.10f)), 0);
        fixDef.shape = ps;
        fixDef.density = density;
        fixDef.friction = 1f;
        fixDef.filter.categoryBits = GameConfig.BIT_FORKLIFT;
        box.createFixture(fixDef);

        ps.dispose();

        return box;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(goodTexture == null) {
            System.out.println("Region not set on Actor " + getClass().getName());
            return;
        } else {
            if (isBoxInCamera()) {
                batch.draw(goodTexture, // Texture
                        body.getPosition().x - boxWidth, body.getPosition().y  - boxHeight, // Texture position
                        boxWidth, boxHeight, // Rotation point (width / 2, height /2 = center)
                        boxWidth * 2, boxHeight  * 2, // Width and height of the texture
                        getScaleX(), getScaleY(), //scaling
                        body.getAngle() * 57.2957f); // Rotation (radiants to degrees)
            }
        }
    }

    @Override
    public void act(float delta) {
        if (dead){
            detroyBox();
        }
        super.act(delta);
        isBoxInCamera();
    }

    private boolean isBoxInCamera(){
        if (camera.frustum.boundsInFrustum(new Vector3(body.getPosition().x, body.getPosition().y, 0),
                new Vector3(boxWidth * 2, boxHeight * 2, 0))){
            return true;
        }else
        return  false;
    }

    public void detroyBox(){
        world.destroyBody(body);
        this.remove();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public Body getBody() {
        return body;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
