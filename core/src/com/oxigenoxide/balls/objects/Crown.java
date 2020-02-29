package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.ball.Ball_Main;

import static com.oxigenoxide.balls.Game.ball_king;

public class Crown {
    Vector2 pos;
    boolean inTransition;
    Texture tex;

    public Crown() {
        pos = new Vector2();
        tex = Res.tex_crown;
    }

    public void update() {
        if (ball_king != null) {
            if (inTransition) {
                pos.interpolate(ball_king.pos, .5f, Interpolation.linear);
                if (Main.distanceBetweenPoints(pos, ball_king.pos) < 10) {
                    inTransition = false;
                }
            } else {
                pos.set(ball_king.pos.x - tex.getWidth() / 2, ball_king.pos.y + ball_king.sprite.getHeight() / 2 + 1);
            }
        }
    }

    public void newOwner(Ball_Main ball_king) {
        if (Game.ball_king == null)
            pos.set(ball_king.pos.x, ball_king.pos.y);
        Game.ball_king = ball_king;
        inTransition = true;
    }

    public void render(SpriteBatch batch) {
        if (ball_king != null && !ball_king.isUnderGround)
            batch.draw(tex, pos.x, pos.y+ball_king.height);
    }
}
