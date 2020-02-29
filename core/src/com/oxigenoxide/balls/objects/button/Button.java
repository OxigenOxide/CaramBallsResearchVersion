package com.oxigenoxide.balls.objects.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;

public class Button {

    int type;
    public Vector2 pos;
    public boolean isTouched;
    int touchOffset;
    boolean hidden;
    Texture tex;
    Texture tex_pressed;
    boolean firstFrame = true;
    public boolean individualRender;
    public boolean act;
    public boolean released;
    boolean pressed;

    public Button(Vector2 pos) {
        this.pos = pos;
    }

    public void update() {
        if (!(Main.tap[0].x > pos.x && Main.tap[0].x < pos.x + tex.getWidth() && Main.tap[0].y > pos.y && Main.tap[0].y < pos.y + tex.getHeight())) {
            touchOffset = 0;
            if (isTouched) {
                isTouched = false;
                Res.sound_buttonClick2.play(1);
            }
        }

        if (isTouched) {
            touchOffset = tex.getHeight() - tex_pressed.getHeight();
        } else {
            touchOffset = 0;
        }
        act = false;

        released = false;
        if (isTouched) {
            pressed = true;
        } else if (pressed) {
            pressed = false;
            released = true;
        }

    }

    public boolean isTouching() {

        if (!hidden) {
            if (Main.tap[0].x > pos.x && Main.tap[0].x < pos.x + tex.getWidth() && Main.tap[0].y > pos.y && Main.tap[0].y < pos.y + tex.getHeight()) {
                if (Gdx.input.justTouched()) {
                    if (!isTouched) {
                        isTouched = true;
                        Res.sound_buttonClick1.play(1);
                    }

                    //touchOffset = 3;
                } else if (!Gdx.input.isTouched() && isTouched) {
                    action();
                    //touchOffset = 0;
                    isTouched = false;
                    Res.sound_buttonClick2.play(1);
                    touchOffset=0;
                }
                else{
                    touchOffset=0;
                }
                return true;
            }
            else {
                isTouched = false;
                touchOffset=0;
            }
        }
        return false;
    }

    public void action() {
        act = true;
    }

    public void setVisibility(boolean visibility) {
        if (visibility) {
            hidden = false;
        } else {
            hidden = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!hidden) {
            if (isTouched && tex_pressed != null) {
                batch.draw(tex_pressed, pos.x - tex_pressed.getWidth()/2+tex.getWidth()/2, pos.y);
            } else if (tex != null) {
                batch.draw(tex, pos.x, pos.y);
            }
        }
    }

    public void setPosCenterX(float x, float y) {
        pos.set(x - tex.getWidth() / 2, y);
    }


}
