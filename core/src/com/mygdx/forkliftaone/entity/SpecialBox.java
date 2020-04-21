package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.RegionNames;

public class SpecialBox extends Actor {

    private Camera camera;
    private World world;
    private Body body, chain, bird;
    private float density;
    private float boxWidth, boxHeight;// Should by multiplied by 2
    private TextureRegion goodTexture, palleteTexture;
    private Vector2 position;
    private int price, donatePrice;
    private boolean dead;
    private float palleteHieght = 0.1f;
    private float chainHeight = 0.2f;
    private float chainWidth = 0.02f;
    private float birdHeight = 0.13f;
    private float birgWidth = 0.13f;

    AssetManager assetManager;
    private Sound breakingSound;

    public SpecialBox(World world, AssetManager assetManager, Camera camera, TextureAtlas atlas, float boxDensity,
                      float boxWidth, float boxHeight, String goodTexture, Vector2 coords) {
        this.camera = camera;
        this.world = world;
        this.position = coords;
        this.density = boxDensity;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.assetManager = assetManager;
        this.breakingSound = assetManager.get(AssetDescriptors.TEST_ENGINE); // Should be replaced with the breaking sound

        this.body = createRubbishBox();


//        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.TEST_ATLAS);

        this.goodTexture = atlas.findRegion(goodTexture);
        palleteTexture = atlas.findRegion(RegionNames.PALLETE);

        breakingSound = assetManager.get(AssetDescriptors.TEST_ENGINE); // Replace with the breaking sound
    }

    public Body createRubbishBox() {


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

        // Pallete
//        ps.setAsBox(0.37f, 0.65f);
        ps.setAsBox(boxWidth, palleteHieght, new Vector2(0, -(boxHeight + palleteHieght)), 0);
        fixDef.shape = ps;
        fixDef.density = density;
        fixDef.friction = 1f;
        fixDef.filter.categoryBits = GameConfig.BIT_FORKLIFT;
        box.createFixture(fixDef);

        BodyDef chainBodyDef = new BodyDef();
        chainBodyDef.type = BodyDef.BodyType.DynamicBody;
        chainBodyDef.fixedRotation = false;
        chainBodyDef.position.set(box.getPosition().x, box.getPosition().y); // Should be obtained from the map

        chain = world.createBody(chainBodyDef);
//        chain = world.createBody(chainBodyDef);
        ps.setAsBox(chainWidth, chainHeight);

        fixDef.shape = ps;
        fixDef.density = 0.05f;
        fixDef.friction = 1f;
        fixDef.filter.categoryBits = GameConfig.BIT_INTERNALS;
        fixDef.filter.maskBits = (GameConfig.BIT_MAP | GameConfig.BIT_FORKLIFT);
        chain.createFixture(fixDef).setUserData(this); // required for collision

        BodyDef birdBodyDef = new BodyDef();
        birdBodyDef.type = BodyDef.BodyType.DynamicBody;
        birdBodyDef.fixedRotation = false;
        birdBodyDef.position.set(box.getPosition().x, box.getPosition().y); // Should be obtained from the map

        bird = world.createBody(birdBodyDef);
//        chain = world.createBody(chainBodyDef);
        ps.setAsBox(birgWidth, birdHeight);

        fixDef.shape = ps;
        fixDef.density = 0.05f;
        fixDef.friction = 1f;
        fixDef.filter.categoryBits = GameConfig.BIT_INTERNALS;
        fixDef.filter.maskBits = (GameConfig.BIT_MAP | GameConfig.BIT_FORKLIFT);
        bird.createFixture(fixDef).setUserData(this); // required for collision

        ps.dispose();

        RevoluteJointDef rjd = new RevoluteJointDef();
        rjd.bodyA = box;
        rjd.bodyB = chain;
        rjd.collideConnected = false;
        rjd.localAnchorA.set(0, boxHeight);
        rjd.localAnchorB.set(0f, chainHeight);
        world.createJoint(rjd);

        RevoluteJointDef rjd2 = new RevoluteJointDef();
        rjd2.bodyA = chain;
        rjd2.bodyB = bird;
        rjd2.collideConnected = false;
        rjd2.localAnchorA.set(0, -chainHeight);
        rjd2.localAnchorB.set(0f, birdHeight);
        world.createJoint(rjd2);


        return box;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (goodTexture == null) {
            System.out.println("Region not set on Actor " + getClass().getName());
            return;
        } else {
            if (isBoxInCamera()) {
                batch.draw(goodTexture, // Texture
                        body.getPosition().x - boxWidth, body.getPosition().y - boxHeight, // Texture position
                        boxWidth, boxHeight, // Rotation point (width / 2, height /2 = center)
                        boxWidth * 2, boxHeight * 2, // Width and height of the texture
                        getScaleX(), getScaleY(), //scaling
                        body.getAngle() * 57.2957f); // Rotation (radiants to degrees)
            }
        }

        // Drawing pallete
        batch.draw(palleteTexture, // Texture
                body.getPosition().x - boxWidth, body.getPosition().y - (boxHeight + (palleteHieght * 2f)), // Texture position
                boxWidth, (boxHeight + (palleteHieght * 2f)), // Rotation point (width / 2, height /2 = center)
                boxWidth * 2f, palleteHieght * 2f, // Width and height of the texture
                getScaleX(), getScaleY(), //scaling
                body.getAngle() * 57.2957f); // Rotation (radiants to degrees)
    }

    @Override
    public void act(float delta) {
        if (dead) {
            detroyBox();
            breakingSound.play();
        }
        super.act(delta);
        isBoxInCamera();
    }

    private boolean isBoxInCamera() {
        if (camera.frustum.boundsInFrustum(new Vector3(body.getPosition().x, body.getPosition().y, 0),
                new Vector3(boxWidth * 2, boxHeight * 2, 0))) {
            return true;
        } else
            return false;
    }

    public void detroyBox() {
        world.destroyBody(body);
        this.remove();
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
        // Play breaking sound here
    }

    public int getDonatePrice() {
        return donatePrice;
    }

    public void setDonatePrice(int donatePrice) {
        this.donatePrice = donatePrice;
    }


}
