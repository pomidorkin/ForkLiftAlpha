package com.mygdx.forkliftaone.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.forkliftaone.ForkLiftGame;
import com.mygdx.forkliftaone.screens.game.GameScreen;
import com.mygdx.forkliftaone.screens.menu.MenuScreen;

public class BackToMenuDialog extends Dialog {
    ForkLiftGame game;
    GameScreen gs;

    public BackToMenuDialog(ForkLiftGame game, GameScreen gs, String title, Skin skin) {
        super(title, skin);
        this.game = game;
        this.gs = gs;
    }

    {
        text("Do you want to go back to the menu?");
        button("Menu", 1);
        button("Continue", 2);
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals(1)) {
            game.setScreen(new MenuScreen(game));
        } else if (object.equals(2)) {
            remove();
            gs.setGamePaused(false);
        }
    }
}
