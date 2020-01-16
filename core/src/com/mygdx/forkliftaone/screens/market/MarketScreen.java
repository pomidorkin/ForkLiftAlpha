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
import com.mygdx.forkliftaone.utils.GeneralData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.MapData;
import com.mygdx.forkliftaone.utils.MapModel;
import com.mygdx.forkliftaone.utils.ProcessInventory;

import java.util.ArrayList;
import java.util.List;

public class MarketScreen extends MenuScreenBase {

    private Skin skin;
    private Table table;
    private TextButton buyButton, backButton;
    ProcessInventory pi = new ProcessInventory();
    private Inventory inv;
    private GeneralData gd;
    private ForkliftData forkliftData;

    private List<ForkliftData> unpurchasedForklifts;

    private int counter;
    private int mapCounter;

    public MarketScreen(ForkLiftGame game) {
        super(game);
        this.counter = 0;
        this.mapCounter = 0;
        inv = pi.read();
        gd = pi.readGeneralData();
        this.forkliftData = gd.getAllModels()[0];
        unpurchasedForklifts = new ArrayList<>();
    }

    public MarketScreen(ForkLiftGame game, int counter, int mapCounter) {
        super(game);
        this.counter = counter;
        this.mapCounter = mapCounter;
        inv = pi.read();
        gd = pi.readGeneralData();
        this.forkliftData = gd.getAllModels()[counter];
        unpurchasedForklifts = new ArrayList<>();
    }

    @Override
    protected Actor createUi() {
        skin = new Skin(Gdx.files.internal("custom/CustomSkinUI.json"));

        table = new Table();
        table.setWidth(Gdx.graphics.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        // Testing cycle for showing models
        // Testing buying is working

        // Looping through the general data
        for (final ForkliftData fd : gd.getAllModels()) {
            boolean purchased = false;

            // Checking if a forklift from general data exists in inventory
            for (ForkliftData fdd : inv.getAllModels()) {
                if (fdd.getName() == fd.getName()) {
                    purchased = true;
                }
            }
            if (!purchased) {

                TextButton tb = new TextButton("Buy " + fd.getName().name().toLowerCase(), skin);
                tb.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        inv.setBalance(inv.getBalance() - 10);
                        fd.setPurchased(true);

                        // Saving models
                        inv.getAllModels().add(fd);

                        // Saving
                        Inventory inv2 = new Inventory(inv.getBalance(), inv.getAllModels(), inv.getAllMaps());
                        pi.write(inv2);

                        GeneralData gd2 = new GeneralData(gd.getAllModels(), gd.getAllMaps());
                        pi.write(gd2);

                        // Refreshing market screen
                        game.setScreen(new MarketScreen(game));

                    }
                });

                table.padTop(30f);
                table.add(tb).padBottom(30);
                table.row();

            }

        }

        // Buying map
        for (final MapData md : inv.getAllMaps()) {
            if (!md.getPurchased()) {

                TextButton tb = new TextButton("Buy " + md.getName().name().toLowerCase(), skin);
                tb.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        inv.setBalance(inv.getBalance() - 10);
                        md.setPurchased(true);

                        // Saving
                        Inventory inv2 = new Inventory(inv.getBalance(), inv.getAllModels(), inv.getAllMaps());
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

        // Scrolling market

        for (final ForkliftData fd : gd.getAllModels()) {
            boolean purchased = false;

            // Checking if a forklift from general data exists in inventory
            for (ForkliftData fdd : inv.getAllModels()) {
                if (fdd.getName() == fd.getName()) {
                    purchased = true;
                }
            }
            if (!purchased) {

                unpurchasedForklifts.add(fd);

            }


        }

        if (unpurchasedForklifts.size() > 0) {
            TextButton nextTB = new TextButton("Next", skin);
            nextTB.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (unpurchasedForklifts.size() != 0) {
                        if (counter + 1 == unpurchasedForklifts.size()) {
                            counter = 0;
                        } else {
                            counter++;
                        }
                    }

                    System.out.println("counter = " + counter);


                    forkliftData = unpurchasedForklifts.get(counter);

                    // Refreshing market screen
                    game.setScreen(new MarketScreen(game, counter, 0));

//                forkliftData = fdArray.get(counter);
                    System.out.println("Size of unpurchasedForklifts " + unpurchasedForklifts.size());

                }
            });

            table.row();
            table.add(nextTB);
        } else {
            System.out.println("Nothing to buy");
            // Code telling that everything is purchased here
        }

        if (unpurchasedForklifts.size() > 0) {
            TextButton buyButton = new TextButton("Buy", skin);
            buyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    inv.setBalance(inv.getBalance() - 10);
//                    forkliftData.setPurchased(true);
                    forkliftData = unpurchasedForklifts.get(counter);
                    forkliftData.setPurchased(true);

                    // Saving models
                    inv.getAllModels().add(forkliftData);

                    // Saving
                    Inventory inv2 = new Inventory(inv.getBalance(), inv.getAllModels(), inv.getAllMaps());
                    pi.write(inv2);

                    GeneralData gd2 = new GeneralData(gd.getAllModels(), gd.getAllMaps());
                    pi.write(gd2);

                    // Refreshing market screen
                    game.setScreen(new MarketScreen(game));

                }
            });

            table.row();
            table.add(buyButton);
        }



//        TextButton previousTB = new TextButton("Previous", skin);
//        previousTB.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (counter == 0){
//                    counter = fdArray.size()-1;
//                } else {
//                    counter--;
//                }
//                forkliftData = fdArray.get(counter);
//                // Delete SOUT
//                System.out.println(forkliftData.getName() + "" + fdArray.size() + "" + counter);
//                // Refreshing screen
////                game.setScreen(new GameScreen(game, fd));
//                game.setScreen(new MarketScreen(game, counter, mapCounter));
//
//            }
//        });

        // Other buttons

        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
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
