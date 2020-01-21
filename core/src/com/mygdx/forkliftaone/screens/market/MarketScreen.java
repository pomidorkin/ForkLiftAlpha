package com.mygdx.forkliftaone.screens.market;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.ForkliftModel;
import com.mygdx.forkliftaone.screens.menu.MenuScreen;
import com.mygdx.forkliftaone.utils.AssetDescriptors;
import com.mygdx.forkliftaone.utils.ForkliftData;
import com.mygdx.forkliftaone.utils.GeneralData;
import com.mygdx.forkliftaone.utils.Inventory;
import com.mygdx.forkliftaone.utils.MapData;
import com.mygdx.forkliftaone.utils.ProcessInventory;
import com.mygdx.forkliftaone.utils.RegionNames;

import java.util.ArrayList;
import java.util.List;

public class MarketScreen extends ScreenAdapter {

    private ForkLiftGame game;
    private Skin skin;
    private Table table;
    private TextButton buyButton, backButton;
    ProcessInventory pi = new ProcessInventory();
    private Inventory inv;
    private GeneralData gd;
    private ForkliftData forkliftData;
    private MapData mapData;
    private SpriteBatch batch;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ForkliftModel model;
    private AssetManager assetManager;
    private TextureAtlas gamePlayAtlas;
    private TextureRegion backgroundTexture;

    private List<ForkliftData> unpurchasedForklifts;
    private List<MapData> unpurchasedMaps;

    private int counter;
    private int mapCounter;

    public MarketScreen(ForkLiftGame game) {
        this.game = game;
        this.counter = 0;
        this.mapCounter = 0;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
        unpurchasedForklifts = new ArrayList<>();
        unpurchasedMaps = new ArrayList<>();
        inv = pi.read();
        gd = pi.readGeneralData();
        this.forkliftData = gd.getAllModels()[0];

//        model = new ForkliftModel(unpurchasedForklifts.get(0), gamePlayAtlas);


    }

    public MarketScreen(ForkLiftGame game, int counter, int mapCounter) {
        this.game = game;
        this.counter = counter;
        this.mapCounter = mapCounter;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
        unpurchasedForklifts = new ArrayList<>();
        unpurchasedMaps = new ArrayList<>();
        inv = pi.read();
        gd = pi.readGeneralData();
        this.forkliftData = gd.getAllModels()[counter];


//        model = new ForkliftModel(unpurchasedForklifts.get(counter), gamePlayAtlas);


    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        viewport = new FitViewport(800f, 480f, camera);
        stage = new Stage(viewport, game.getBatch());
        gamePlayAtlas = assetManager.get(AssetDescriptors.TEST_ATLAS);
        stage.addActor(createUi());

        model = new ForkliftModel(unpurchasedForklifts.get(counter), gamePlayAtlas);
        backgroundTexture = gamePlayAtlas.findRegion(RegionNames.TEST_BACKGROUND);

        Gdx.input.setInputProcessor(stage);

    }

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
            ImageButton nextTB = new ImageButton(skin.get("rightButton", ImageButton.ImageButtonStyle.class));
//            TextButton nextTB = new TextButton("Next", skin);
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
            ImageButton previousTB = new ImageButton(skin.get("leftButton", ImageButton.ImageButtonStyle.class));
//            TextButton previousTB = new TextButton("Previous", skin);
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

                    forkliftData = unpurchasedForklifts.get(counter);
                    if (inv.getBalance() >= forkliftData.getPrice()) {
                        inv.setBalance(inv.getBalance() - forkliftData.getPrice());
//                    forkliftData.setPurchased(true);

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
                    } else {
                        System.out.println("Not enough money. Forklift price:" + forkliftData.getPrice());
                    }

                }
            });

            table.row();
            table.add(buyButton);
            table.row();
            table.add(previousTB);
            table.add(nextTB);
        } else {
            System.out.println("No forklifts to buy");
            // Code telling that everything is purchased here
        }

        // Maps buying scrolling logic
        if (unpurchasedMaps.size() > 0) {
            ImageButton nextMapTB = new ImageButton(skin.get("rightButton", ImageButton.ImageButtonStyle.class));
//            TextButton nextMapTB = new TextButton("Next map", skin);
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
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        draw();
        stage.draw();

    }

    private void draw() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(backgroundTexture, 0, 0,
                viewport.getWorldWidth(), viewport.getWorldHeight());


        batch.draw(model.getForkliftRegion(), // Texture
                viewport.getScreenWidth() / 2f - (model.getForkliftRegion().getRegionWidth() / 2f) * 0.75f,
                viewport.getScreenHeight() / 2f - (model.getForkliftRegion().getRegionHeight() / 2f) * 0.75f, // Texture position
                (model.getForkliftRegion().getRegionWidth() / 2) * 100f, (model.getForkliftRegion().getRegionHeight() / 2) * 100f, // Rotation point (width / 2, height /2 = center)
                model.getForkliftRegion().getRegionWidth() * 0.75f, model.getForkliftRegion().getRegionHeight() * 0.75f, // Width and height of the texture
                1f, 1f, //scaling
                0); // Rotation (radiants to degrees)

        batch.end();
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
