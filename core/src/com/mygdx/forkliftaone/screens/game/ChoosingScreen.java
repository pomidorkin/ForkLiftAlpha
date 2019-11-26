package com.mygdx.forkliftaone.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.ForkliftModel;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.entity.ForkliftActorBase;
import com.mygdx.forkliftaone.maps.MapBase;
import com.mygdx.forkliftaone.maps.TestMap;
import com.mygdx.forkliftaone.screens.menu.MenuScreenBase;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.ProcessInventory;
import com.mygdx.forkliftaone.utils.RegionNames;

public class ChoosingScreen extends ScreenAdapter {

    private ForkLiftGame game;
    private Skin skin;
    private Table table;
    private TextButton startButton, quitButton;
    private Viewport viewport, hudViewport;
    private OrthographicCamera camera, uiCamera;
    private Stage stage, uiStage;
    private World world;

    private Box2DDebugRenderer b2dr;
    private OrthogonalTiledMapRenderer tmr;
    private MapBase map;
    private ForkliftModel model;
    private ForkliftActorBase forklift;
    private final AssetManager assetManager;


    public ChoosingScreen(ForkLiftGame game){
        this.game = game;
        assetManager = game.getAssetManager();
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

        map = new TestMap(world);
        map.setRegion(backgoundRegion);
        map.createMap();
        stage.addActor(map);

        model = new ForkliftModel(ForkliftModel.ModelName.MEDIUM, map);
        forklift = new ForkliftActorBase(world, model);
        forklift.createForklift(model);
        forklift.setRegion(forkliftRegion, forkliftRegion, wheelRegion, forkliftRegion);
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
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight());

        startButton = new TextButton("Choose forklift", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
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
