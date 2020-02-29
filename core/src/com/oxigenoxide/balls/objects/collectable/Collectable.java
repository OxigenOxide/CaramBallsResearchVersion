package com.oxigenoxide.balls.objects.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;

public class Collectable {
    Sprite sprite;
    Body body;
    Vector2 pos;
    boolean doDispose;
    float factor_height;
    float factor_width;
    float heightOffset;
    float heightVel;

    Collectable(Texture texture) {
        sprite = new Sprite(texture);
        pos = Game.getFreePosOnTable(5);
        body = Game.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.fixtureDef_collectable);
        body.setUserData(this);

        if (pos == null) {
            pos = new Vector2(-100, -100);
            dispose();
        }
        body.setTransform(pos.x * Main.METERSPERPIXEL, pos.y * Main.METERSPERPIXEL, 0);
        sprite.setPosition((int) (pos.x - sprite.getTexture().getWidth() / 2), (int) (pos.y - sprite.getTexture().getHeight() / 2));
    }

    public void update() {
        if (doDispose) {
            doDispose = false;
            dispose();
        }
        heightOffset += heightVel;
        heightVel -= Game.GRAVITY;
    }

    void jump() {
        heightVel = 5;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void pickUp(Ball ball) {
        doDispose = true;
    }

    public void dispose() {
        Game.destroyBody(body);
        Game.collectablesToRemove.add(this);
    }
}
