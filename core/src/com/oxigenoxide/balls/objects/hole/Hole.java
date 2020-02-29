package com.oxigenoxide.balls.objects.hole;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.ball.Ball_Main;

public class Hole {
    public float radius;
    public float radiusMax;
    public Vector2 pos;
    float count;
    boolean hasSpewed;
    public boolean isDisposed;

    Hole() {
        pos=new Vector2();
    }
    Hole(float x, float y) {
        pos=new Vector2((int)x+.5f,(int)y+.5f);
    }

    public void update() {
        count += Game.dt_one_slowed;
    }

    public void render(ShapeRenderer sr) {
        sr.circle(pos.x, pos.y, radius,15);
    }

    public void dispose() {
        Game.holesToRemove.add(this);
        isDisposed=true;
    }

    public void setPosition(){
        Vector2 pos_rnd=Game.getFreePosOnTable(Math.round(radiusMax));
        if(pos_rnd==null) {
            dispose();
            pos.set(-100,-100);
        }
        else {
            pos.set(pos_rnd);
        }
    }
}
