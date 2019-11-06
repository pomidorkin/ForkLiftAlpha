package com.mygdx.forkliftaone.maps;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.utils.TiledObjectUtil;

//Spawning objects should be up to the map actor
public class MapBase extends Actor{

    private World world;
    private Body terrain;
    private TiledMap tiledMap;

    public MapBase(World world){
        this.world = world;
    }

    public void createMap(){
        //TiledMap
        tiledMap = new TmxMapLoader().load("mapAssets/map-sample.tmx");
        TiledObjectUtil.parseTiledTiledObjectLayer(world, tiledMap.getLayers().get("collision-layer").getObjects());

        //Terrain
//        BodyDef terrainDef = new BodyDef();
//        terrainDef.type = BodyDef.BodyType.StaticBody;
//        terrainDef.fixedRotation = false;
//        terrainDef.position.set(GameConfig.WORLD_CENTER_X, 2f);
//
//        terrain = world.createBody(terrainDef);
//
//        Vector2[] vertices = new Vector2[2];
//        vertices[0] = new Vector2(0, 0);
//        vertices[1] = new Vector2(15f, 0);
//
//        ChainShape chSh = new ChainShape();
//        chSh.createChain(vertices);
//        terrain.createFixture(chSh, 1.0f);
//        chSh.dispose();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
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


}
