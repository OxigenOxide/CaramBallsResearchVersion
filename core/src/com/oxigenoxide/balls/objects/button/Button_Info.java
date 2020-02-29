package com.oxigenoxide.balls.objects.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;

public class Button_Info extends Button {
    public Button_Info(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_info;
        tex_pressed = Res.tex_buttonPressed_info;
    }

    @Override
    public void action() {
        Main.gm.dialog("WHATSUP!!!!!!!!!!!!!!!!!!!");
    }
}
