package com.mygdx.forkliftaone.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.mygdx.forkliftaone.maps.MapBase;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.MapData;
import com.mygdx.forkliftaone.utils.MapModel;
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
    private MapBase map;
    private ForkliftModel model;
    private MapModel mapModel;
    private final AssetManager assetManager;
    private ForkliftData forkliftData;
    private MapData mapData;
    private Inventory inv;

    private SpriteBatch batch;
    TextureRegion backgoundRegion;

    private int counter;
    private int mapCounter;


    public ChoosingScreen(ForkLiftGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        inv = pi.read();
        forkliftData = inv.getAllModels().get(0);
        mapData = inv.getAllMaps().get(0);
        counter = 0;
        mapCounter = 0;
        batch = game.getBatch();
    }

    private ChoosingScreen(ForkLiftGame game, int counter, int mapCounter) {
        this.game = game;
        assetManager = game.getAssetManager();
        inv = pi.read();
        forkliftData = inv.getAllModels().get(counter);
        mapData = inv.getAllMaps().get(mapCounter);
        this.counter = counter;
        this.mapCounter = mapCounter;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        super.show();

//        camera = new OrthographicCamera();
//        viewport = new FitViewport(Gdx.graphics.getWidth() / 100f, 4.8f, camera);
//        stage = new Stage(viewport, game.getBatch());

        uiCamera = new OrthographicCamera();
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), 480f, uiCamera);
        uiStage = new Stage(hudViewport, game.getBatch());

        Gdx.input.setInputProcessor(uiStage);

        //test
        world = new World(new Vector2(0, -9.8f), false);

        // Creating texture atlas
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.TEST_ATLAS);


//        MapData mapData = new MapData();
//        mapData.setName(MapModel.MapName.CUSTOM);
        mapModel = new MapModel(mapData.getName(), world, camera, stage, gamePlayAtlas);
        map = mapModel.getMap();
        map.createMap();

        backgoundRegion = mapModel.getBackgroundTexture();

//        stage.addActor(map);
        uiStage.addActor(map);

        model = new ForkliftModel(forkliftData, map, gamePlayAtlas);

        b2dr = new Box2DDebugRenderer();
        uiStage.addActor(createUi());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//        viewport.apply();
        hudViewport.apply();

        drawMain();
        uiStage.draw();
    }

    private Actor createUi() {
        skin = new Skin(Gdx.files.internal("custom/CustomSkinUI.json"));

        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight());

        // Test (selection via next/previous buttons is working)
//        final ForkliftData[] fdArray;
        final ArrayList<ForkliftData> fdArray = new ArrayList<>();
        for (int i = 0; i < inv.getAllModels().size(); i++) {
            if (inv.getAllModels().get(i).getPurchased()) {
                fdArray.add(inv.getAllModels().get(i));
            }
        }

        // Map selection
        final ArrayList<MapData> mdArray = new ArrayList<>();
        for (int i = 0; i < inv.getAllMaps().size(); i++) {
            if (inv.getAllMaps().get(i).getPurchased()) {
                mdArray.add(inv.getAllMaps().get(i));
            }
        }

        TextButton nextTB = new TextButton("Next", skin);
        nextTB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (counter + 1 == fdArray.size()) {
                    counter = 0;
                } else {
                    counter++;
                }
                forkliftData = fdArray.get(counter);
                // Delete SOUT
                System.out.println(forkliftData.getName() + "" + fdArray.size() + "" + counter);
                // Refreshing screen
                game.setScreen(new ChoosingScreen(game, counter, mapCounter));

            }
        });
        TextButton previousTB = new TextButton("Previous", skin);
        previousTB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (counter == 0) {
                    counter = fdArray.size() - 1;
                } else {
                    counter--;
                }
                forkliftData = fdArray.get(counter);
                // Delete SOUT
                System.out.println(forkliftData.getName() + "" + fdArray.size() + "" + counter);
                // Refreshing screen
                game.setScreen(new ChoosingScreen(game, counter, mapCounter));

            }
        });

        TextButton nextMap = new TextButton("Next Map", skin);
        nextMap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (mapCounter + 1 == mdArray.size()) {
                    mapCounter = 0;
                } else {
                    mapCounter++;
                }
                mapData = mdArray.get(mapCounter);
                // Delete SOUT
                System.out.println(mapData.getName() + "" + mdArray.size() + "" + mapCounter);
                // Refreshing screen
                game.setScreen(new ChoosingScreen(game, counter, mapCounter));

            }
        });
        TextButton previousMap = new TextButton("Previous Map", skin);
        previousMap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (mapCounter == 0) {
                    mapCounter = mdArray.size() - 1;
                } else {
                    mapCounter--;
                }
                mapData = mdArray.get(mapCounter);
                // Delete SOUT
                System.out.println(mapData.getName() + "" + mdArray.size() + "" + mapCounter);
                // Refreshing screen
                game.setScreen(new ChoosingScreen(game, counter, mapCounter));

            }
        });

        TextButton upgrateButton = new TextButton("Upgrate", skin);
        upgrateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (forkliftData.getTubes() < 7) {
                    if (inv.getBalance() >= ((forkliftData.getTubes() - 1) * 100) + 300) {
                        int newBalance = inv.getBalance() - (((forkliftData.getTubes() - 1) * 100) + 300);
                        forkliftData.setTubes(forkliftData.getTubes() + 1);

                        Inventory inv2 = new Inventory(newBalance, inv.getDonateCurrency(), inv.getAllModels(), inv.getAllMaps());
                        pi.write(inv2);
                        System.out.println("Balance: " + inv.getBalance());
                        game.setScreen(new ChoosingScreen(game, counter, mapCounter));
                    } else {
                        System.out.println("Not enough money! Your balance: " + inv.getBalance() + ", upgrate price: " + ((forkliftData.getTubes() - 1) * 100 + 300));
                    }
                } else {
                    System.out.println("You have reached the upgrade limit");
                }
            }
        });

        // Work with alignment a little bit
        table.add(nextTB).padLeft(-100);
        table.add(previousTB).padLeft(-100);
        table.row();
        table.add(nextMap).padLeft(-100);
        table.add(previousMap).padLeft(-100);
        table.row();
        table.add(upgrateButton);
        table.row();

        startButton = new TextButton("Play", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, forkliftData, mapData));
            }
        });

        quitButton = new TextButton("Choose map", skin);
        quitButton.addListener(new ClickListener() {
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

    private void drawMain() {
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        batch.draw(backgoundRegion, 0, 0,
                hudViewport.getWorldWidth(), hudViewport.getWorldHeight());

//        batch.draw(mapModel.getBackgroundTexture(), 0, 0,
//                viewport.getWorldWidth(), viewport.getWorldHeight());

        batch.draw(model.getForkliftRegion(), // Texture
                hudViewport.getScreenWidth() / 2f - (model.getForkliftRegion().getRegionWidth() / 2f) * 0.75f,
                hudViewport.getScreenHeight() / 2f - (model.getForkliftRegion().getRegionHeight() / 2f) * 0.75f, // Texture position
                model.getForkliftRegion().getRegionWidth() / 2, model.getForkliftRegion().getRegionHeight() / 2, // Rotation point (width / 2, height /2 = center)
                model.getForkliftRegion().getRegionWidth() * 0.75f, model.getForkliftRegion().getRegionHeight() * 0.75f, // Width and height of the texture
                1f, 1f, //scaling
                0); // Rotation (radiants to degrees)


        batch.end();
    }


    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height);
        hudViewport.update(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
//        stage.dispose();
        uiStage.dispose();
        world.dispose();
        b2dr.dispose();
        map.disposeTiledMap();
        skin.dispose(); // Should be removed
    }


}
