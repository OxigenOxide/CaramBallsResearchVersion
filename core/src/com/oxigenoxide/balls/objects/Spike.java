package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;

public class Spike {
    Texture tex;
    public Vector2 pos;
    int tex_index;
    float state = 0;
    boolean open;
    boolean close;
    Body body;
    float count;
    float countMax=480;
    boolean isPermanent;
    Ball ballHit;
    public float radius;
    public Spike() {
        tex = Res.tex_spike[tex_index];
        pos = Game.getFreePosOnTable(tex.getWidth()/2f);
        if(pos==null)
            pos=new Vector2(-100,-100);
        construct();
    }

    public Spike(float x,float y, boolean isPermanent){
        this.isPermanent=isPermanent;
        pos=new Vector2(x,y);
        construct();
    }

    private void construct(){
        open();
        tex = Res.tex_spike[tex_index];
        body=Game.world.createBody(Res.bodyDef_static);
        body.createFixture(Res.shape_spike,1);
        body.setUserData(this);
        body.setTransform(Main.METERSPERPIXEL*(pos.x+.5f),Main.METERSPERPIXEL*(pos.y+3),0);
        radius=tex.getWidth()/2f;
    }
    public void update() {
        if(open)
            state+=.05f;
        if(close)
            state-=.05f;
        state = MathUtils.clamp(state, 0, 1);
        tex_index = (int) (4 - state * 4);
        tex = Res.tex_spike[tex_index];

        if(!isPermanent) {
            count += Main.dt_one;
            if (count >= countMax)
                close();
        }
        if(state==0)
            dispose();

        if(ballHit!=null) {
            ballHit.destroy(0, 1,pos);
            ballHit=null;
        }
    }

    public void hitBall(Ball ball){
        ballHit=ball;
    }

    public void open(){
        open=true;
        close=false;
    }
    public void close(){
        open=false;
        close=true;
    }

    public void render(SpriteBatch batch) {
        batch.draw(tex, (int)(pos.x-tex.getWidth()/2), (int)pos.y);
    }

    public void dispose(){
        Game.destroyBody(body);
        Game.spikesToRemove.add(this);
    }
}
