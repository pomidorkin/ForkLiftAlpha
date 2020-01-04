package com.mygdx.forkliftaone.maps;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.entity.Sensor;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.AssetPaths;
import com.mygdx.forkliftaone.utils.TiledObjectUtil;

//Spawning objects should be up to the map actor
public abstract class MapBase extends Actor{

    private World world;
    private TiledMap tiledMap;
    private Vector2 spawnCoordinates;
    private TextureRegion background;
    private String mapName;
    private Sensor sensor;

    public MapBase(World world, String mapName, Vector2 spawnCoordinates){
        this.world = world;
        this.mapName = mapName;
        this.spawnCoordinates = spawnCoordinates;
        sensor = new Sensor(world, 10f, 3f, 1f, 0.5f);
    }

    public void createMap(){
        //TiledMap
        tiledMap = new TmxMapLoader().load(mapName);
        TiledObjectUtil.parseTiledTiledObjectLayer(world, tiledMap.getLayers().get("collision-layer").getObjects());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        batch.draw(background, getX(), getY(), 8f, 4.8f); //viewport width
        batch.draw(background, getStage().getCamera().position.x - getStage().getViewport().getWorldWidth()/2f,
                getStage().getCamera().position.y - getStage().getViewport().getWorldHeight()/2f,
                8f, 4.8f);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void disposeTiledMap(){
        tiledMap.dispose();
    }

    public Vector2 getSpawnCoordinates() {
        return spawnCoordinates;
    }

    public void setRegion(TextureRegion backgroundTexture) {
        background = backgroundTexture;
    }
}
