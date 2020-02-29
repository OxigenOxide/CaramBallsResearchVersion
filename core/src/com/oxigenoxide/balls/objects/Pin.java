package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.ID;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.particle.Particle_BallShard;

public class Pin {
    Vector2 pos;
    Body body;
    boolean doDispose;

    public Pin(float x, float y) {
        pos = new Vector2((int)x, (int)y);
        body = Game.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.shape_pin, 1);
        body.setUserData(this);
        body.setTransform(Main.METERSPERPIXEL * (pos.x), Main.METERSPERPIXEL * (pos.y), 0);
    }

    public void update() {
        if (doDispose)
            dispose();
    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_pin, pos.x - Res.tex_pin.getWidth() / 2, pos.y-5);
    }

    public void destroy(Ball ball) {
        doDispose = true;
        float angle = Main.angleBetweenPoints(ball.pos, pos);
        float impact = 2;
        if (!Main.noFX)
            for (int i = 0; i < 10; i++)
                Game.particles.add(new Particle_BallShard(pos.x, pos.y, (float) (angle + Math.random() * Math.PI * 1.2f - Math.PI * .6f), impact * (.5f + (float) Math.random()), Res.eggPalette));
        Main.addSoundRequest(ID.Sound.GLASS);
        Main.shake();
        Game.throwConfetti(pos.x, pos.y);
        Game.onPinDestroyed();
    }

    public void dispose() {
        Game.destroyBody(body);
        Game.pinsToRemove.add(this);
    }
}
