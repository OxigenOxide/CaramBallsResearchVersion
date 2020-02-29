package com.oxigenoxide.balls.objects.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;

public class Button_Pause extends Button {
    public Button_Pause(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_pause;
        tex_pressed = Res.tex_buttonPressed_pause;
    }

    @Override
    public void action() {
        if(Game.isPaused){
            Game.unpause();
            tex = Res.tex_button_pause;
            tex_pressed = Res.tex_buttonPressed_pause;

        }
        else{
            Game.pause();
            tex = Res.tex_button_resume;
            tex_pressed = Res.tex_buttonPressed_resume;
        }
    }
}
