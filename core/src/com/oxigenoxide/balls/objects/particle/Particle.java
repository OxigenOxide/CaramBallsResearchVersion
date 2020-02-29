package com.oxigenoxide.balls.objects.particle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;

public class Particle {
    Vector2 pos;
    Vector2 vel;
    Sprite sprite;
    float resistance;
    int blinks;
    int lifeTime = 240;
    float count_life = 0;
    boolean isVisible = true;
    float height = 0;
    float velY;
    float minVelY;
    float fallResistance;

    Particle(float x, float y) {
        pos = new Vector2(x, y);
        vel = new Vector2();
    }

    public void update() {
        pos.add(vel);
        //vel.scl((1 - resistance) * Main.dt_one);
        vel.scl((1 - resistance * Main.dt_one));

        height += velY;
        height = Math.max(0, height);
        if (height == 0) {
            if (velY < 0) {
                velY = -velY * .5f;
                if (velY < 3)
                    velY = 0;
            }
        } else
            velY += Game.GRAVITY_PIXELS * .2f * (1 - fallResistance);
        velY=Math.max(velY,minVelY);

        if (sprite != null)
            setSpritePosition();

        count_life += Main.dt_one;
        if (count_life > lifeTime / 2) {
            isVisible = (int) ((count_life - lifeTime / 2) / 5) % 2 == 0;
        }
        if (count_life > lifeTime) {
            dispose();
        }

    }

    public void render(SpriteBatch batch) {
        if (isVisible)
            sprite.draw(batch);
    }

    public void render(ShapeRenderer sr) {

    }
    public void setSpritePosition(){
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y + height - sprite.getHeight() / 2);
    }

    public void dispose() {
        Game.particlesToRemove.add(this);
        Game.particles_batch.remove(this);
        Game.particles_sr.remove(this);
    }
}
