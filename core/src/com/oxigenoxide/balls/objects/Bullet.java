package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;

public class Bullet{
    Sprite sprite;
    Body body;
    public boolean doDispose;
    public Bullet(float x,float y,float ang){
        sprite=new Sprite(Res.tex_bullet);
        sprite.setOriginCenter();
        sprite.setRotation((float)Math.toDegrees(ang));
        body= Game.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.shape_bullet,1);
        body.setTransform(x* Main.METERSPERPIXEL,y*Main.METERSPERPIXEL,ang);
        body.setLinearVelocity(10*(float)Math.cos(ang),10*(float)Math.sin(ang));
        body.setFixedRotation(true);
        body.setUserData(this);
        sprite.setPosition(body.getPosition().x*Main.PIXELSPERMETER-sprite.getWidth()/2,body.getPosition().y*Main.PIXELSPERMETER-sprite.getHeight()/2);
    }
    public void update(){
        sprite.setPosition(body.getPosition().x*Main.PIXELSPERMETER-sprite.getWidth()/2,body.getPosition().y*Main.PIXELSPERMETER-sprite.getHeight()/2);
        if(doDispose)
            dispose();
    }
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
    public void hit(Ball ball){
        System.out.println("HIT");
        ball.destroy(body.getAngle(),5,new Vector2(body.getPosition().x*Main.PIXELSPERMETER,body.getPosition().y*Main.PIXELSPERMETER));
    }

    public void dispose(){
        Game.bulletsToRemove.add(this);
        body=Game.destroyBody(body);
    }
}
