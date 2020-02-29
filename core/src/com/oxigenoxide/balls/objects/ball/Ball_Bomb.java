package com.oxigenoxide.balls.objects.ball;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.particle.Particle_Explosion;

public class Ball_Bomb extends Ball {

    float count;
    float countMax = 200;
    final int BLINKS = 3;
    float whiteness;

    public Ball_Bomb(float x, float y, float height) {
        super(x, y, height, 1);
        setSpriteTexture(Res.tex_bomb);
    }

    @Override
    public void update() {
        super.update();
        count += Game.dt_one_slowed;
        if (count > countMax) {
            explode();
        }
        whiteness = Math.max((float) Math.sin(count / countMax * (BLINKS - .75f) * 2 * (float) Math.PI), 0);
    }

    public void explode() {
        dispose();
        for (Ball ball : Game.balls) {
            if (ball.body != null && ball.isKinetic())
                ball.applyForce(Main.angleBetweenPoints(pos, ball.pos), getForce(Main.distanceBetweenPoints(pos, ball.pos)));
        }
        Game.particles.add(new Particle_Explosion(pos.x,pos.y));
    }

    static float getForce(float distance) {
        if (distance < Main.width)
            return 20;
        else
            return 20 / (distance - Main.width);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_overlay);
        Res.shader_overlay.setUniformf("newColor", 1, 1, 1, whiteness);
        sprite.draw(batch);
        batch.setShader(Res.shader_palette);
    }
}
