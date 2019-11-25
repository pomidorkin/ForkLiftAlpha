package com.mygdx.forkliftaone.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.ForkliftModel;

public abstract class MenuScreenBase extends ScreenAdapter {
    protected final ForkLiftGame game;
    protected final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;


    public MenuScreenBase(ForkLiftGame game){
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        super.show();

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        viewport = new FitViewport(8f, 4.8f);
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);
        stage.addActor(createUi());
    }

    protected abstract Actor createUi();

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
