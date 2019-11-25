package com.mygdx.forkliftaone.screens.market;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.ForkliftModel;
import com.mygdx.forkliftaone.screens.game.ChoosingScreen;
import com.mygdx.forkliftaone.screens.menu.MenuScreen;
import com.mygdx.forkliftaone.screens.menu.MenuScreenBase;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.ProcessInventory;

public class MarketScreen extends MenuScreenBase {

    private Skin skin;
    private Table table;
    private TextButton buyButton, backButton;

    public MarketScreen(ForkLiftGame game) {
        super(game);
    }

    @Override
    protected Actor createUi() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        buyButton = new TextButton("Buy", skin);
        buyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ProcessInventory pi = new ProcessInventory();
                Inventory inv = pi.read();
                inv.setBalance(inv.getBalance()-3);
                inv.getPurchasedModels()[0].setEngine(inv.getPurchasedModels()[0].getEngine() + 1);
                pi.write(inv);
            }
        });

        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                    game.setScreen(new MenuScreen(game));
            }
        });


        table.padTop(30f);
        table.add(buyButton).padBottom(30);
        table.row();
        table.add(backButton);

        return table;
    }
}
