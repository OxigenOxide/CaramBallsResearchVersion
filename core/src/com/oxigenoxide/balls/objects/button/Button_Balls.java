package com.oxigenoxide.balls.objects.button;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;

public class Button_Balls extends Button {

    public Button_Balls(Vector2 pos) {
        super(pos);
        tex = Res.tex_button_balls;
        tex_pressed = Res.tex_buttonPressed_balls;
    }

    public void setNew(){
        tex = Res.tex_button_balls_new;
        tex_pressed = Res.tex_buttonPressed_balls_new;
    }
    public void setNormal(){
        tex = Res.tex_button_balls;
        tex_pressed = Res.tex_buttonPressed_balls;
    }

    @Override
    public void action() {
        Main.setSceneShop();
    }
}
