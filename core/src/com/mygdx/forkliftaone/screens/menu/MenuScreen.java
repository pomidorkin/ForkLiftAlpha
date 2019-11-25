package com.mygdx.forkliftaone.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.screens.game.ChoosingScreen;
import com.mygdx.forkliftaone.screens.game.GameScreen;
import com.mygdx.forkliftaone.screens.market.MarketScreen;

public class MenuScreen extends MenuScreenBase {
    private Skin skin;
    private Table table;
    private TextButton startButton, quitButton, marketButton;

    public MenuScreen(ForkLiftGame game){super(game);}

    @Override
    protected Actor createUi() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        startButton = new TextButton("Start Game!", skin);
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                play();
            }
        });

        marketButton = new TextButton("Market", skin);
        marketButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shop();
            }
        });

        quitButton = new TextButton("Quit Game", skin);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                quit();
            }
        });

        table.padTop(30f);
        table.add(startButton).padBottom(30);
        table.row();
        table.add(marketButton).padBottom(30);
        table.row();
        table.add(quitButton);

        return table;
    }



    private void play(){
        game.setScreen(new ChoosingScreen(game));
    }

    private void shop(){
        game.setScreen(new MarketScreen(game));
    }

    private void quit() {
        Gdx.app.exit();
    }
}
