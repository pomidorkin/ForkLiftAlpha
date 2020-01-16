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
    private MapData mapData;

    private List<ForkliftData> unpurchasedForklifts;
    private List<MapData> unpurchasedMaps;

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
        unpurchasedMaps = new ArrayList<>();
    }

    public MarketScreen(ForkLiftGame game, int counter, int mapCounter) {
        super(game);
        this.counter = counter;
        this.mapCounter = mapCounter;
        inv = pi.read();
        gd = pi.readGeneralData();
        this.forkliftData = gd.getAllModels()[counter];
        unpurchasedForklifts = new ArrayList<>();
        unpurchasedMaps = new ArrayList<>();
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

        for (final MapData md : gd.getAllMaps()) {
            boolean mapPurchased = false;

            // Checking if a forklift from general data exists in inventory
            for (MapData mdd : inv.getAllMaps()) {
                if (mdd.getName() == md.getName()) {
                    mapPurchased = true;
                }
            }
            if (!mapPurchased) {
                unpurchasedMaps.add(md);
            }
        }


        // Forklift scrolling buttons logic
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
                    game.setScreen(new MarketScreen(game, counter, mapCounter));

//                forkliftData = fdArray.get(counter);
                    System.out.println("Size of unpurchasedForklifts " + unpurchasedForklifts.size());

                }
            });

            // "Previous button"
            TextButton previousTB = new TextButton("Previous", skin);
            previousTB.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (unpurchasedForklifts.size() != 0) {
                        if (counter == 0) {
                            counter = unpurchasedForklifts.size() - 1;
                        } else {
                            counter--;
                        }
                    }

                    System.out.println("counter = " + counter);


                    forkliftData = unpurchasedForklifts.get(counter);

                    // Refreshing market screen
                    game.setScreen(new MarketScreen(game, counter, mapCounter));

//                forkliftData = fdArray.get(counter);
                    System.out.println("Size of unpurchasedForklifts " + unpurchasedForklifts.size());

                }
            });

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
            table.row();
            table.add(nextTB);
            table.add(previousTB);
        } else {
            System.out.println("No forklifts to buy");
            // Code telling that everything is purchased here
        }

        // Maps buying scrolling logic
        if (unpurchasedMaps.size() > 0) {
            TextButton nextMapTB = new TextButton("Next map", skin);
            nextMapTB.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (unpurchasedMaps.size() != 0) {
                        if (mapCounter + 1 == unpurchasedMaps.size()) {
                            mapCounter = 0;
                        } else {
                            mapCounter++;
                        }
                    }

                    System.out.println("Map counter = " + mapCounter);


                    mapData = unpurchasedMaps.get(mapCounter);

                    // Refreshing market screen
                    game.setScreen(new MarketScreen(game, counter, mapCounter));

//                forkliftData = fdArray.get(counter);
                    System.out.println("Size of unpurchased maps " + unpurchasedMaps.size());

                }
            });

            TextButton previousMapTB = new TextButton("Previous map", skin);
            previousMapTB.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (unpurchasedMaps.size() != 0) {
                        if (mapCounter == 0) {
                            mapCounter = unpurchasedMaps.size() - 1;
                        } else {
                            mapCounter--;
                        }
                    }

                    System.out.println("Map counter = " + mapCounter);


                    mapData = unpurchasedMaps.get(mapCounter);

                    // Refreshing market screen
                    game.setScreen(new MarketScreen(game, counter, mapCounter));

//                forkliftData = fdArray.get(counter);
                    System.out.println("Size of unpurchased maps " + unpurchasedMaps.size());

                }
            });

            TextButton buyMapButton = new TextButton("Buy Map", skin);
            buyMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    inv.setBalance(inv.getBalance() - 10);
//                    forkliftData.setPurchased(true);
                    mapData = unpurchasedMaps.get(mapCounter);
                    mapData.setPurchased(true);

                    // Saving models
                    inv.getAllMaps().add(mapData);

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
            table.add(buyMapButton);
            table.row();
            table.add(nextMapTB);
            table.add(previousMapTB);
        } else {
            System.out.println("No maps to buy");
            // Code telling that everything is purchased here
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
