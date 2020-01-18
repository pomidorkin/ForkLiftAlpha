package com.mygdx.forkliftaone.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BackgroundBase extends Actor {

    private TextureRegion backgroundTexture;
    private ForkliftActorBase forklift;
    private Vector2 backTexturePosition, middleTexturePosition, frontTexturePosition;
    private Viewport viewport;

    public BackgroundBase(TextureRegion backgroundTexture, Viewport viewport, ForkliftActorBase forklift, Camera camera) {
        this.backgroundTexture = backgroundTexture;
        this.forklift = forklift;
        this.viewport = viewport;

//        texturePosition = new Vector2(camera.position.x - getStage().getViewport().getWorldWidth()/2f,
//                getStage().getCamera().position.y - getStage().getViewport().getWorldHeight()/2f);
        backTexturePosition = new Vector2(camera.position.x - viewport.getWorldWidth()/2f,
                camera.position.y - viewport.getWorldHeight()/2f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (backgroundTexture == null) {
            System.out.println("Region not set on Actor " + getClass().getName());
            return;
        } else {
//            if (isBoxInCamera()) {
//                batch.draw(backgroundTexture, texturePosition.x, texturePosition.y,
//                        getStage().getCamera().viewportWidth, getStage().getCamera().viewportHeight);
//            }

            if (true) {
                batch.draw(backgroundTexture, backTexturePosition.x,
                        backTexturePosition.y = getStage().getCamera().position.y - getStage().getViewport().getWorldHeight()/2f,
                        getStage().getCamera().viewportWidth, getStage().getCamera().viewportHeight);

                batch.draw(backgroundTexture, backTexturePosition.x + viewport.getWorldWidth(),
                        backTexturePosition.y = getStage().getCamera().position.y - getStage().getViewport().getWorldHeight()/2f,
                        getStage().getCamera().viewportWidth, getStage().getCamera().viewportHeight);
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        backTexturePosition.x -= forklift.getForklift().getLinearVelocity().x * 0.01;

        // Checking if texture is inside the viewport and positioning if not.
        if (viewport.getCamera().position.x - backTexturePosition.x > viewport.getWorldWidth() * 1.5f){
            System.out.println("Texture is out of screen");
            backTexturePosition.x += viewport.getWorldWidth();
        } else if (backTexturePosition.x - viewport.getCamera().position.x > 0 - viewport.getWorldWidth() / 2f){
            backTexturePosition.x -= viewport.getWorldWidth();
            System.out.println("Triggered");
        }

//        System.out.println("Backgroung X: " + backTexturePosition.x + " Camera X: " +
//                (viewport.getCamera().position.x - viewport.getWorldWidth()/2f));

    }

//    private boolean isBoxInCamera(){
//        if (camera.frustum.boundsInFrustum(new Vector3(body.getPosition().x, body.getPosition().y, 0),
//                new Vector3(boxSize * 2, boxSize * 2, 0))){
//            return true;
//        }else
//            return  false;
//    }
}
