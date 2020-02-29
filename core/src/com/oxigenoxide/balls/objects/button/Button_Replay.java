package com.oxigenoxide.balls.objects.button;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;

public class Button_Replay extends Button {
    public Button_Replay(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_replay;
        tex_pressed = Res.tex_buttonPressed_replay;
    }

    @Override
    public void action() {
        super.action();
        Game.doReplay=true;
        Main.startFade();
    }
}
