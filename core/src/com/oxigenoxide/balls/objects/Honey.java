package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;

public class Honey {
    Sprite sprite;
    Vector2 pos;
    static final float RADIUS = 8f;
    float wiggle;
    float size;

    public Honey() {
        sprite = new Sprite(Res.tex_honey);
        pos = Game.getRandomPosOnTable(sprite.getWidth(), sprite.getHeight());
        pos.add(0, .5f);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
    }

    public void update() {
        for (Ball ball : Game.balls) {
            if (Main.distanceBetweenPoints(ball.pos, pos) < RADIUS + ball.radius) {
                if (!ball.isStuck) {
                    ball.getStuck();
                    wiggle();
                }
            }
        }
        size = Math.min(1, size + Main.dt_one * .1f);
        sprite.setSize(size * (float) (sprite.getTexture().getWidth() * (1 + wiggle * .5f * -Math.sin(wiggle * 15))), size * (float) (sprite.getTexture().getHeight() * (1 + wiggle * .5f * -Math.cos(wiggle * 15))));
        wiggle = Math.max(0, wiggle -= .05 * Game.dt_one_slowed);
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
    }

    public void wiggle() {
        wiggle = .5f;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        Game.honeyToRemove.add(this);
    }
}
