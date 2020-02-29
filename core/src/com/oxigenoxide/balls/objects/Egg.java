package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.ID;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.particle.Particle_BallShard;

public class Egg {
    Vector2 pos;
    float height;
    Texture tex;
    Body body;
    float velY;
    boolean doDispose;
    boolean isPassthrough;

    public Egg() {
        tex = Res.tex_blueEgg;
        pos =Game.getRandomPosOnTable(tex.getWidth(),tex.getHeight());
        height = Main.height;
        createBody();
        body.setTransform(pos.x * Main.METERSPERPIXEL, (pos.y+3) * Main.METERSPERPIXEL, 0);
        setPassthrough(true);
    }
    public Egg(float x,float y) {
        tex = Res.tex_blueEgg;
        pos = new Vector2(x,y);
        height = Main.height;
        createBody();
        body.setTransform(pos.x * Main.METERSPERPIXEL, (pos.y+3) * Main.METERSPERPIXEL, 0);
        setPassthrough(true);
    }
    public Egg(float x,float y,float height) {
        tex = Res.tex_blueEgg;
        pos = new Vector2(x,y);
        this.height = height;
        createBody();
        body.setTransform(pos.x * Main.METERSPERPIXEL, (pos.y+3) * Main.METERSPERPIXEL, 0);
        setPassthrough(true);
    }

    public void createBody() {
        body = Game.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.fixtureDef_egg);
        body.setUserData(this);
    }

    public void update() {
        height += velY;
        height = Math.max(0, height);
        if (height == 0) {
            if (velY < 0) {
                velY = -velY * .5f;
                if (velY < 3) {
                    velY = 0;
                    setPassthrough(false);
                }
            }
        } else
            velY += Game.GRAVITY_PIXELS * .2f;
        if(doDispose)
            dispose();
    }

    public void destroy(Ball ball){
        doDispose=true;
        float angle=Main.angleBetweenPoints(ball.pos,pos);
        float impact=1;
        if(!Main.noFX) {
            for (int i = 0; i < 10; i++) {
                Game.particles.add(new Particle_BallShard(pos.x, pos.y, (float) (angle + Math.random() * Math.PI * 1.2f - Math.PI * .6f), impact * (.5f + (float) Math.random()), Res.eggPalette));
            }
        }
        if (!Main.noFX)
            Main.addSoundRequest(ID.Sound.GLASS,5,.5f,.5f);

        if(Main.noFX){
            Game.orbsCollected+=10;
            Main.userData.orbsCollected+=10;
            Main.gameData.orbs+=10;
            Game.orbCounter.show();
        }
        else {
            for (int i = 0; i < 10; i++) {
                Game.orbs.add(new Orb(pos.x, pos.y));
            }
        }
        Main.shake();
        Game.throwConfetti(pos.x,pos.y);
    }
    public void render(SpriteBatch batch) {
        batch.draw(tex, (int)(pos.x - tex.getWidth()/2), (int)(pos.y - 3 + height));
    }
    public void dispose(){
        Game.destroyBody(body);
        body=null;
        Game.eggsToRemove.add(this);
    }

    public void setPassthrough(boolean b) {
        if (b) {
            isPassthrough = true;
            Filter filter = new Filter();
            filter.maskBits = Res.MASK_ZERO;
            filter.categoryBits = (Res.MASK_PASSTHROUGH);
            body.getFixtureList().first().setFilterData(filter);
        } else {
            isPassthrough = false;
            Filter filter = new Filter();
            filter.maskBits = Res.MASK_ZERO;
            filter.categoryBits = (short) (Res.MASK_PASSTHROUGH | Res.MASK_ZERO);
            body.getFixtureList().first().setFilterData(filter);
        }
    }
}
