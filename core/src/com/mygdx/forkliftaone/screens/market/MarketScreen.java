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
import com.mygdx.forkliftaone.screens.menu.MenuScreen;
import com.mygdx.forkliftaone.screens.menu.MenuScreenBase;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.ProcessInventory;

public class MarketScreen extends MenuScreenBase {

    private Skin skin;
    private Table table;
    private TextButton buyButton, backButton;
    ProcessInventory pi = new ProcessInventory();
    private Inventory inv;

    public MarketScreen(ForkLiftGame game) {
        super(game);
        inv = pi.read();
    }

    @Override
    protected Actor createUi() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        // Testing cycle for showing models
        // Testing buying is working
        for(final ForkliftData fd : inv.getAllModels()) {
            if (!fd.getPurchased()) {

               TextButton tb = new TextButton("Buy " + fd.getName().name().toLowerCase(), skin);
               tb.addListener(new ClickListener() {
                   @Override
                   public void clicked(InputEvent event, float x, float y) {
                       inv.setBalance(inv.getBalance() - 10);
                       fd.setPurchased(true);

                       // Saving
                       Inventory inv2 = new Inventory(inv.getBalance(), inv.getAllModels());
                       pi.write(inv2);

                       // Refreshing market screen
                       game.setScreen(new MarketScreen(game));
                   }
               });

                table.padTop(30f);
                table.add(tb).padBottom(30);
                table.row();
            }
        }

        // Other buttons

        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                    game.setScreen(new MenuScreen(game));
            }
        });


        table.padTop(30f);
        table.add(backButton);

        return table;
    }

    @Override
    public void hide() {
        super.hide();
        dispose();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
