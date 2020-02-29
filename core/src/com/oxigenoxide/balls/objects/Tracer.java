package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.objects.particle.Particle;

public class Tracer {
    float radius;
    Tracer previous;
    Vector2 pos;

    public Tracer(float x, float y, float radius) {
        pos=new Vector2(x,y);
        this.radius = radius;
        if (Game.tracers.size() - 1 >= 0) {
            previous = Game.tracers.get(Game.tracers.size() - 1);
        }
    }

    public void update() {
        radius = Math.max(0, radius - Main.dt_one * .2f);
        if (radius == 0)
            dispose();
    }

    public void render(ShapeRenderer sr) {
        sr.circle(pos.x, pos.y, radius);
        if (previous != null) {
            float angle = Main.angleBetweenPoints(pos, previous.pos);
            sr.triangle(pos.x + (float) Math.cos(angle - Math.PI * .5f) * radius, pos.y + (float) Math.sin(angle - Math.PI * .5f) * radius, pos.x + (float) Math.cos(angle + Math.PI * .5f) * radius, pos.y + (float) Math.sin(angle + Math.PI * .5f) * radius, previous.pos.x + (float) Math.cos(angle + Math.PI * .5f) * previous.radius, previous.pos.y + (float) Math.sin(angle + Math.PI * .5f) * previous.radius);
            sr.triangle(pos.x + (float) Math.cos(angle - Math.PI * .5f) * radius, pos.y + (float) Math.sin(angle - Math.PI * .5f) * radius, previous.pos.x + (float) Math.cos(angle - Math.PI * .5f) * previous.radius, previous.pos.y + (float) Math.sin(angle - Math.PI * .5f) * previous.radius, previous.pos.x + (float) Math.cos(angle + Math.PI * .5f) * previous.radius, previous.pos.y + (float) Math.sin(angle + Math.PI * .5f) * previous.radius);
        }
    }
    public void dispose(){
        Game.tracersToRemove.add(this);
    }
}