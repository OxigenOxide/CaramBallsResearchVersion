package com.oxigenoxide.balls.objects.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Res;

public class Particle_BallShard extends Particle {
    int level;
    Color[] palette;
    public Particle_BallShard(float x, float y, float angle, float speed, int level) {
        super(x, y);
        this.level=level;
        Game.particles_batch.add(this);
        vel.set(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
        sprite=new Sprite(Res.tex_ballShard[(int)(Math.random()*3)]);
        resistance=.05f;
        lifeTime=100;
        sprite.setRotation((int)(Math.random()*4)*90);
        setSpritePosition();
    }
    public Particle_BallShard(float x, float y, float angle, float speed, Color[] palette) {
        super(x, y);
        this.palette=palette;
        Game.particles_batch.add(this);
        vel.set(speed * (float) Math.cos(angle), speed * (float) Math.sin(angle));
        sprite=new Sprite(Res.tex_ballShard[(int)(Math.random()*3)]);
        resistance=.05f;
        lifeTime=100;
        sprite.setRotation((int)(Math.random()*4)*90);
        setSpritePosition();
    }

    public void update() {
        super.update();
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_palette);
        //Res.shader_palette.setUniformf("color0", Res.ballPalette[level][0].r, Res.ballPalette[level][0].g, Res.ballPalette[level][0].b, 1);
        {
            Res.shader_palette.setUniformf("color1", palette[1].r, palette[1].g, palette[1].b, 1);
            Res.shader_palette.setUniformf("color2", palette[2].r, palette[2].g, palette[2].b, 1);
        }
        //Res.shader_palette.setUniformf("color3", Res.ballPalette[level][3].r, Res.ballPalette[level][3].g, Res.ballPalette[level][3].b, 1);
        super.render(batch);
        batch.setShader(null);
    }
}
