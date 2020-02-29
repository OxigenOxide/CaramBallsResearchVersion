package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball_Main;

public class Cannon {
    Vector2 pos;
    Vector2 pos_center;
    float ang;
    Sprite gun;
    float angDest;
    Ball_Main ball_target;
    float count_nextBullet;
    float size = 0;

    public Cannon(float x, float y) {
        x = (int) x;
        y = (int) y;
        pos = new Vector2(x, y);
        gun = new Sprite(Res.tex_cannon_gun);
        gun.setOrigin(6.5f, 6.5f);
        gun.setPosition(x - 6, y - 6.5f + 4);
    }

    public Cannon() {
        pos = Game.getRandomPosOnTable(9, 9);
        pos_center = new Vector2(pos.x, pos.y + 10.5f);
        gun = new Sprite(Res.tex_cannon_gun);
        gun.setOrigin(6.5f, 6.5f);
        gun.setPosition(pos.x - 6, pos.y + 4);
    }

    public void update() {

        if (size < 1) {
            size = Math.min(1, size + .05f * Game.dt_one_slowed);
            gun.setSize(gun.getTexture().getWidth() * size, gun.getTexture().getHeight() * size);
            gun.setPosition(pos.x - 6 * size, pos.y + 4 * size);
        }

        boolean isBallInRange = false;
        float closestDistance = 50;
        for (Ball_Main ball : Game.mainBalls) {
            float distance = Main.distanceBetweenPoints(ball.pos, pos_center);
            if (distance < closestDistance) {
                closestDistance = distance;
                angDest = Main.angleBetweenPoints(pos_center, ball.pos);
                isBallInRange = true;
            }
        }
        float shortest_angle = ((((angDest - ang) % ((float) Math.PI * 2)) + ((float) Math.PI * 3)) % ((float) Math.PI * 2)) - ((float) Math.PI);
        ang = ang + (shortest_angle * .1f) % ((float) Math.PI * 2);
        gun.setRotation((float) Math.toDegrees(ang));
        if (isBallInRange) {
            count_nextBullet -= Game.dt_one_slowed;
            if (count_nextBullet <= 0) {
                count_nextBullet = 50;
                Game.bullets.add(new Bullet(pos.x + 15.5f * (float) Math.cos(ang), pos.y + 10.5f + 15.5f * (float) Math.sin(ang), ang));
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_cannon_base, pos.x - Res.tex_cannon_base.getWidth() / 2 * size, pos.y, size * Res.tex_cannon_base.getWidth(), size * Res.tex_cannon_base.getHeight());
        gun.draw(batch);
        batch.draw(Res.tex_cannon_shine, pos.x - Res.tex_cannon_shine.getWidth() / 2 * size, pos.y + 11 * size, size * Res.tex_cannon_shine.getWidth(), size * Res.tex_cannon_shine.getHeight());
    }

    public void dispose() {
        Game.cannonsToRemove.add(this);
    }
}
