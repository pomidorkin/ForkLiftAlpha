package com.mygdx.forkliftaone.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.ForkliftModel;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.entity.ForkliftActorBase;
import com.mygdx.forkliftaone.maps.MapBase;
import com.mygdx.forkliftaone.maps.TestMap;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.ProcessInventory;
import com.mygdx.forkliftaone.utils.RegionNames;

import java.util.ArrayList;

public class ChoosingScreen extends ScreenAdapter {

    private ForkLiftGame game;
    private Skin skin;
    private Table table;
    private TextButton startButton, quitButton;
    private Viewport viewport, hudViewport;
    private OrthographicCamera camera, uiCamera;
    private Stage stage, uiStage;
    private World world;
    ProcessInventory pi = new ProcessInventory();

    private Box2DDebugRenderer b2dr;
    private OrthogonalTiledMapRenderer tmr;
    private MapBase map;
    private ForkliftModel model;
    private ForkliftActorBase forklift;
    private final AssetManager assetManager;
    private ForkliftData forkliftData;
    private Inventory inv;

    private int counter;


    public ChoosingScreen(ForkLiftGame game){
        this.game = game;
        assetManager = game.getAssetManager();
        inv = pi.read();
        forkliftData = inv.getAllModels()[0];
        counter = 0;
    }

//    public ChoosingScreen(ForkLiftGame game, ForkliftData chosenModel, int counter){
//        this.game = game;
//        assetManager = game.getAssetManager();
//        inv = pi.read();
//        forkliftData = chosenModel;
//        this.counter = counter;
//    }

    private ChoosingScreen(ForkLiftGame game, int counter){
        this.game = game;
        assetManager = game.getAssetManager();
        inv = pi.read();
        forkliftData = inv.getAllModels()[counter];
        this.counter = counter;
    }

    @Override
    public void show() {
        super.show();

        camera = new OrthographicCamera();
        viewport = new FitViewport(8f, 4.8f, camera);
        stage = new Stage(viewport, game.getBatch());

        uiCamera = new OrthographicCamera();
        hudViewport = new FitViewport(800f, 480f, uiCamera);
        uiStage = new Stage(hudViewport, game.getBatch());

        Gdx.input.setInputProcessor(uiStage);

        //test
        world = new World(new Vector2(0, -9.8f), false);

        // Creating texture atlas
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.TEST_ATLAS);
        TextureRegion forkliftRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_BODY);
        TextureRegion wheelRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_WHEEL);
        TextureRegion backgoundRegion = gamePlayAtlas.findRegion(RegionNames.TEST_BACKGROUND);
         forkliftRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_BODY);
         wheelRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_WHEEL);

        map = new TestMap(world);
        map.setRegion(backgoundRegion);
        map.createMap();
        stage.addActor(map);

        model = new ForkliftModel(forkliftData, map, gamePlayAtlas);
        forklift = new ForkliftActorBase(world, model);
        forklift.createForklift(model);
        // Change the texture later so that they are different for each forklift
        forklift.setRegion();
        stage.addActor(forklift);

        b2dr = new Box2DDebugRenderer();
        //Second parameter is responsible for scaling
        tmr = new OrthogonalTiledMapRenderer(map.getTiledMap(), 1/ GameConfig.SCALE);

        uiStage.addActor(createUi());
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        viewport.apply();
        tmr.render();
        b2dr.render(world, camera.combined);
        stage.draw();
        hudViewport.apply();
        uiStage.draw();
    }

    private Actor createUi(){
        skin = new Skin(Gdx.files.internal("neon/neon-ui.json"));


        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight());

        // Test (selection via next/previous buttons is working)
//        final ForkliftData[] fdArray;
        final ArrayList<ForkliftData> fdArray = new ArrayList<>();
        for (int i = 0; i < inv.getAllModels().length; i++){
            if (inv.getAllModels()[i].getPurchased()){
                fdArray.add(inv.getAllModels()[i]);
            }
        }

        TextButton nextTB = new TextButton("Next", skin);
        nextTB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (counter+1 == fdArray.size()){
                    counter = 0;
                } else {
                    counter++;
                }
                forkliftData = fdArray.get(counter);
                // Delete SOUT
                System.out.println(forkliftData.getName() + "" + fdArray.size() + "" + counter);
                // Refreshing screen
//                game.setScreen(new GameScreen(game, fd));
                game.setScreen(new ChoosingScreen(game, counter));

            }
        });
        TextButton previousTB = new TextButton("Previous", skin);
        previousTB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (counter == 0){
                    counter = fdArray.size()-1;
                } else {
                    counter--;
                }
                forkliftData = fdArray.get(counter);
                // Delete SOUT
                System.out.println(forkliftData.getName() + "" + fdArray.size() + "" + counter);
                // Refreshing screen
//                game.setScreen(new GameScreen(game, fd));
                game.setScreen(new ChoosingScreen(game, counter));

            }
        });

        TextButton upgrateButton = new TextButton("Upgrate", skin);
        upgrateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                forkliftData.setTubes(forkliftData.getTubes()+1);
                Inventory inv2 = new Inventory(inv.getBalance() - 10, inv.getAllModels());
                pi.write(inv2);
                game.setScreen(new ChoosingScreen(game, counter));
            }
        });

        // Work with alignment a little bit
        table.add(nextTB).padLeft(-100);
        table.add(previousTB).padLeft(-100);
        table.row();
        table.add(upgrateButton);
        table.row();

        startButton = new TextButton("Play", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, forkliftData));
            }
        });

        quitButton = new TextButton("Choose map", skin);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                quit();
            }
        });

        table.padTop(30f);
        table.add(startButton).padBottom(30);
        table.row();
        table.add(quitButton);

        return table;
    }

    private void update(float delta) {
        world.step(1 / 60f, 6, 2);
        tmr.setView(camera);

        // Разборки с текстурами
//        stage.setViewport(viewport);
//        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hudViewport.update(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        uiStage.dispose();
        world.dispose();
        tmr.dispose();
        b2dr.dispose();
        map.disposeTiledMap();
        skin.dispose();
    }


}
