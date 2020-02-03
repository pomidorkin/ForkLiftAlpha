package com.mygdx.forkliftaone.screens.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.screens.menu.MenuScreen;
import com.mygdx.forkliftaone.screens.tests.LayoutTestScreen;
import com.mygdx.forkliftaone.utils.AssetDescriptors;

public class TestLoading extends ScreenAdapter {
    // == attributes ==
    private Viewport viewport;

    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;

    private final ForkLiftGame game;
    private final AssetManager assetManager;

    private Skin skin;
    private Table table;
    private ProgressBar pb;
    private Stage stage;

    // == constructors ==
    public TestLoading(ForkLiftGame game) {
        this.game = game;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport, game.getBatch());
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {

//        skin = new Skin(Gdx.files.internal("neon/neon-ui.json"));
        skin = new Skin(Gdx.files.internal("freezing/freezing-ui.json"));

        // How to access different fields within the json class
        pb = new ProgressBar(0.0f, Gdx.graphics.getWidth()/2, 0.01f, false, skin.get("fancy", ProgressBar.ProgressBarStyle.class));

//        pb.setAnimateDuration(0.25f);

        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight()/2);

        table.add(pb).width(Gdx.graphics.getWidth()/2).height(10f);
//        table.add(pb);
        stage.addActor(table);

        this.loadAssets();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();

        pb.setValue(assetManager.getProgress() * Gdx.graphics.getWidth()/2);
        stage.draw();

        if(changeScreen) {
            game.setScreen(new MenuScreen(game));
//            game.setScreen(new LayoutTestScreen(game)); // Testing lining up & layout
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        // NOTE: screens dont dispose automatically
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    // == private methods ==
    private void update(float delta) {
        // progress is between 0 and 1
        progress = assetManager.getProgress();

        // update returns true when all assets are loaded
        if(assetManager.update()) {
            waitTime -= delta;

            if(waitTime <= 0) {
                changeScreen = true;
            }
        }
    }


    private static void waitMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAssets(){
        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.TEST_ATLAS);
        assetManager.load(AssetDescriptors.TEST_MUSIC);
        assetManager.load(AssetDescriptors.TEST_ENGINE);
        assetManager.finishLoading();//?
    }
}
