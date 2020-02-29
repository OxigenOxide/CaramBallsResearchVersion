package com.oxigenoxide.balls.objects.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;

import java.nio.IntBuffer;

import static com.oxigenoxide.balls.Main.sr;

public class Particle_Pulse extends Particle {
    float radius;
    float lineWidth = 4;
    float count;
    float countMax = 30;
    float progress;
    static int number;
    int id;
    float size;

    public Particle_Pulse(float x, float y, float size) {
        super(x, y);
        size=Math.min(size,30);
        this.size=size;
        Game.particles_sr.add(this);
        number++;
        id = number;
        countMax*=size/10;

    }

    public void update() {
        lineWidth = 3 - progress * 3;
        radius = size * progress;
        progress = count / countMax;
        count = Math.min(count + Main.dt_one, countMax);

        if (progress >= 1)
            dispose();
    }

    public void render(ShapeRenderer sr) {
        if(!Main.inScreenShotMode) {
            sr.end();
            Gdx.gl20.glLineWidth(lineWidth * Main.pointsPerPixel);
            sr.begin();
            sr.setColor(1, 1, 1, 1);
            sr.set(ShapeRenderer.ShapeType.Line);
            sr.circle(pos.x, pos.y, radius);
        }
    }
}
