package com.mygdx.forkliftaone.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
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
import com.mygdx.forkliftaone.dialogs.BackToMenuDialog;
import com.mygdx.forkliftaone.entity.ForkliftActorBase;
import com.mygdx.forkliftaone.entity.BoxBase;
import com.mygdx.forkliftaone.entity.FuelCan;
import com.mygdx.forkliftaone.entity.TestBox;
import com.mygdx.forkliftaone.handlers.SensorContactListener;
import com.mygdx.forkliftaone.maps.CustomTestMap;
import com.mygdx.forkliftaone.maps.MapBase;
import com.mygdx.forkliftaone.maps.TestMap;
import com.mygdx.forkliftaone.screens.menu.MenuScreen;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.BoxFactory;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.ProcessInventory;
import com.mygdx.forkliftaone.utils.RegionNames;

public class GameScreen extends ScreenAdapter implements InputProcessor {

    private final ForkLiftGame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final GlyphLayout layout = new GlyphLayout();

    private Viewport viewport, uiViewport;
    private OrthographicCamera camera, uiCamera;
    private Stage stage, uiStage, fuelStage;

    // UI
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private Table table;
    private Skin skin;
    private TextButton fuelButton;


    //test
    private Box2DDebugRenderer b2dr;
    private OrthogonalTiledMapRenderer tmr;
    private World world;
    private ForkliftModel model;
    private ForkliftActorBase forklift;
    private ForkliftData fd;
    private ProcessInventory pi = new ProcessInventory();
    private Inventory inv;

    private ProgressBar bar;

    private MapBase map;

    public GameScreen(ForkLiftGame game, ForkliftData fd) {
        this.game = game;
        this.fd = fd;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        // Разборки с текстурами end here

        camera = new OrthographicCamera();
        viewport = new FitViewport(8f, 4.8f, camera);
        stage = new Stage(viewport, batch);
//        Gdx.input.setInputProcessor(this);

        // Initializing UI
        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);
        font = assetManager.get(AssetDescriptors.FONT);
        shapeRenderer = new ShapeRenderer();
        uiStage = new Stage(uiViewport, game.getBatch());

        // May be refactor later, because there are too many stages
        fuelStage = new Stage(uiViewport, game.getBatch());
//        Gdx.input.setInputProcessor(uiStage);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(fuelStage);
        Gdx.input.setInputProcessor(multiplexer);

        uiStage.addActor(createUi());
        fuelStage.addActor(createFuelButton());


        //test
        world = new World(new Vector2(0, -9.8f), false);
        // Adding contact listeners
        world.setContactListener(new SensorContactListener());

        // Creating texture atlas
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.TEST_ATLAS);
        TextureRegion forkliftRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_BODY);
        TextureRegion wheelRegion = gamePlayAtlas.findRegion(RegionNames.FORKLIFT_WHEEL);
        TextureRegion backgoundRegion = gamePlayAtlas.findRegion(RegionNames.TEST_BACKGROUND);

//        map = new TestMap(world, camera, stage, gamePlayAtlas);
        map = new CustomTestMap(world, camera, stage, gamePlayAtlas);
        map.setRegion(backgoundRegion);
        map.createMap();
        stage.addActor(map);
        map.spawnBoxes();

        // Class ForkliftModel should have a constructor taking arguments from inventory
//        model = new ForkliftModel(ForkliftModel.ModelName.MEDIUM, map);
        model = new ForkliftModel(fd, map, gamePlayAtlas);
        forklift = new ForkliftActorBase(world, model);
        forklift.createForklift(model);
        forklift.setRegion();
        stage.addActor(forklift);

        b2dr = new Box2DDebugRenderer();
        //Second parameter is responsible for scaling
        tmr = new OrthogonalTiledMapRenderer(map.getTiledMap(), 1 / GameConfig.SCALE);

        // Rubbish
//        BoxBase box = new TestBox(world, camera, gamePlayAtlas);
//        BoxFactory factory = new BoxFactory();
//        stage.addActor(factory.getBox(world, camera, gamePlayAtlas, new Vector2(5f, 5f)));

        // Load & Saving logic
        inv = pi.read();
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Разборки с текстурами
        viewport.apply();
        stage.draw();
        tmr.render();
        b2dr.render(world, camera.combined);

        // Testing UI
        uiViewport.apply();
        renderUi();
        uiStage.draw();

        // Stage acting
        stage.act();

        // Fuel system
        // Should be implemented through dialog window with saving
        if (forklift.isFuelTankEmpty()){
            game.setScreen(new MenuScreen(game));
        }

        if (forklift.isHasFuel()){
            if (fuelButton.getTouchable().equals(Touchable.disabled)){
                fuelButton.setTouchable(Touchable.enabled);
            }
            fuelStage.draw();
        } else if (fuelButton.getTouchable().equals(Touchable.enabled)){
            fuelButton.setTouchable(Touchable.disabled);
        }

    }

    private void update(float delta) {
        world.step(1 / 60f, 6, 2);
        tmr.setView(camera);
        cameraUpdate(delta);

        // Разборки с текстурами
//        stage.setViewport(viewport);
//        batch.setProjectionMatrix(camera.combined);
    }

    // Testing UI
    private void renderUi() {
        // Testing style. Should be replaced with a picture from Asset Manager
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.box(0, Gdx.graphics.getHeight() - (layout.height + 40f), 0,
                Gdx.graphics.getWidth(), layout.height + 40f, 1);
        shapeRenderer.end();

        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();

        // draw lives
        String livesText = "Balance: " + inv.getBalance();
        layout.setText(font, livesText);
        font.draw(batch, layout, 20f, Gdx.graphics.getHeight() - layout.height);

        // draw score
        String scoreText = "SCORE: ";
        layout.setText(font, scoreText);
        font.draw(batch, layout,
                Gdx.graphics.getWidth() - layout.width - 20f,
                Gdx.graphics.getHeight() - layout.height
        );

        // Draw fuel icon
        bar.setValue(forklift.getFuelTank());

        batch.end();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiViewport.update(width, height, true);
    }

    @Override
    public void hide() {
        // Saving example
        super.hide();
        dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        tmr.dispose();
        b2dr.dispose();
        map.disposeTiledMap();
        font.dispose();
        shapeRenderer.dispose();
        skin.dispose();
//        batch.dispose();
    }

    private Actor createUi() {
        skin = new Skin(Gdx.files.internal("neon/neon-ui.json"));


        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        TextButton menuButton = new TextButton("Menu", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Inventory inv2 = new Inventory(inv.getBalance() + map.getSalary(), inv.getAllModels());
                pi.write(inv2);
//                game.setScreen(new MenuScreen(game));
                BackToMenuDialog menuDialog = new BackToMenuDialog(game, "Return to menu?", skin);
                menuDialog.show(uiStage);
            }
        });

        table.add(menuButton).padLeft(Gdx.graphics.getWidth() - 100f);
        table.row();

        // Testing Fuel icon
//        bar = new ProgressBar(0.0f, Gdx.graphics.getWidth()/2, 0.01f, false, skin.get("default", ProgressBar.ProgressBarStyle.class));
        bar = new ProgressBar(0, 100, 0.01f, false, skin);

        // The size of the fuel icon can be changed here
        table.add(bar).width(Gdx.graphics.getWidth()/4).height(10f);
//        table.add(pb);

        return table;
    }

    private Actor createFuelButton(){
        skin = new Skin(Gdx.files.internal("neon/neon-ui.json"));


        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        // Fuel button test rough
        fuelButton = new TextButton("Fill", skin);
        fuelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                forklift.setFuelTank(100f);

                // Logic for deleting fuel can after pressing the button
                for (Actor can : stage.getActors()){
                    if (can instanceof FuelCan && ((FuelCan) can).isActive()){
                        ((FuelCan) can).detroyBox();
                    }
                }

            }
        });

        table.add(fuelButton);
        table.row();

        return table;

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D) {
            forklift.moveForkliftRight();
            return true;
        }

        if (keycode == Input.Keys.A) {
            forklift.moveForkliftLeft();
            return true;
        }

        if (keycode == Input.Keys.W) {
            forklift.moveTubeUp();
            return true;
        }

        if (keycode == Input.Keys.S) {
            forklift.moveTubeDown();
            return true;
        }

        if (keycode == Input.Keys.E) {
            forklift.rotateForkUp();
            return true;
        }

        if (keycode == Input.Keys.Q) {
            forklift.rotateForkDown();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D) {
            forklift.stopMoveForkliftRight();
            return true;
        }

        if (keycode == Input.Keys.A) {
            forklift.stopMoveForkliftLeft();
            return true;
        }

        if (keycode == Input.Keys.W) {
            forklift.stopMoveTubeUp();
            return true;
        }

        if (keycode == Input.Keys.S) {
            forklift.stopMoveTubeDown();
            return true;
        }

        if (keycode == Input.Keys.E) {
            forklift.stopRotatingFork();
            return true;
        }

        if (keycode == Input.Keys.Q) {
            forklift.stopRotatingFork();
            return true;
        }
        return false;
    }

    private void cameraUpdate(float delta) {
        Vector3 position = camera.position;

        position.x = forklift.getFrokPosition().x;
        position.y = forklift.getFrokPosition().y + 1.5f;

        camera.position.set(position);

        camera.update();
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
