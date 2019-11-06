package com.mygdx.forkliftaone.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.config.GameConfig;
import com.mygdx.forkliftaone.entity.ForkliftActorBase;
import com.mygdx.forkliftaone.ForkliftModel;
import com.mygdx.forkliftaone.entity.RubbishBox;
import com.mygdx.forkliftaone.maps.MapBase;
import com.mygdx.forkliftaone.utils.TiledObjectUtil;

public class GameScreen extends ScreenAdapter implements InputProcessor {

    private final ForkLiftGame game;
    private final SpriteBatch batch;

    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;


    //test
    private Box2DDebugRenderer b2dr;
    private OrthogonalTiledMapRenderer tmr;
    private World world;
    private ForkliftModel model;
    private ForkliftActorBase forklift;

    private MapBase map;

    public GameScreen(ForkLiftGame game) {
        this.game = game;
        batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(8f,4.8f, camera);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(this);


        //test
        world = new World(new Vector2(0, -9.8f), false);
        b2dr = new Box2DDebugRenderer();

        model = new ForkliftModel(ForkliftModel.ModelName.SMALL);
        forklift = new ForkliftActorBase(world, model);
        forklift.createForklift(model);

        map = new MapBase(world);
        map.createMap();

        //Second parameter is responsible for scaling
        tmr = new OrthogonalTiledMapRenderer(map.getTiledMap(), 1/ GameConfig.SCALE);

        // Rubbish
        RubbishBox box = new RubbishBox();
        box.createRubbishBox(world);
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tmr.render();
        b2dr.render(world, camera.combined);
    }

    private void update(float delta) {
        world.step(1 / 60f, 6, 2);
        tmr.setView(camera);
//        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        super.hide();
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
        batch.dispose();
        stage.dispose();
        world.dispose();
        tmr.dispose();
        b2dr.dispose();
        map.disposeTiledMap();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D){
            forklift.moveForkliftRight();
            return true;
        }

        if (keycode == Input.Keys.A){
            forklift.moveForkliftLeft();
            return true;
        }

        if (keycode == Input.Keys.W){
            forklift.moveTubeUp();
            return true;
        }

        if (keycode == Input.Keys.S){
            forklift.moveTubeDown();
            return true;
        }

        if (keycode == Input.Keys.E){
            forklift.rotateForkUp();
            return true;
        }

        if (keycode == Input.Keys.Q){
            forklift.rotateForkDown();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D){
            forklift.stopMoveForkliftRight();
            return true;
        }

        if (keycode == Input.Keys.A){
            forklift.stopMoveForkliftLeft();
            return true;
        }

        if (keycode == Input.Keys.W){
            forklift.stopMoveTubeUp();
            return true;
        }

        if (keycode == Input.Keys.S){
            forklift.stopMoveTubeDown();
            return true;
        }

        if (keycode == Input.Keys.E){
            forklift.stopRotatingFork();
            return true;
        }

        if (keycode == Input.Keys.Q){
            forklift.stopRotatingFork();
            return true;
        }
        return false;
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
