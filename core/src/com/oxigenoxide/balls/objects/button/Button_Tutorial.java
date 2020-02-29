package com.oxigenoxide.balls.objects.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;

public class Button_Tutorial extends Button {
    public Button_Tutorial(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_tutorial;
        tex_pressed = Res.tex_buttonPressed_tutorial;
    }

    @Override
    public void action() {
        Main.amm.hide();
        Main.setSceneGame();
        Game.doSetTutorialMode = true;
    }
}
