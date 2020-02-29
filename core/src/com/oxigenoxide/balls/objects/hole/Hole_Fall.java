package com.oxigenoxide.balls.objects.hole;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.objects.ball.Ball;

public class Hole_Fall extends Hole {


    float breathe;
    float count_breathe;
    float radius_base;
    final float FALLINEASIER = 2;
    int lifeTime = 315;
    float breatheProgress;

    public Hole_Fall() {
        super();
        radiusMax = 10;
        setPosition();
    }

    public Hole_Fall(float x, float y) {
        super(x, y);
        radiusMax = 10;
    }

    @Override
    public void update() {
        super.update();

        if (count < lifeTime * 2 / 5f) {
            radius_base = count / (lifeTime * 2 / 5f) * radiusMax;
        }

        if (count > lifeTime * 4 / 5f && count <= lifeTime) {
            radius_base = radiusMax - (count - lifeTime * 4 / 5f) / (lifeTime-lifeTime * 4 / 5f) * radiusMax;
        }

        count_breathe = (float) ((count_breathe + .1f) % (2 * Math.PI));
        breathe = radius_base * .1f * (float) Math.sin(count_breathe);
        breatheProgress=((float) Math.sin(count_breathe)+1)/2;
        if(Main.noFX) {
            breathe = 0;
            breatheProgress=1;
        }
        radius = radius_base + breathe;

        if (count > lifeTime)
            dispose();

        for (Ball ball : Game.balls) {
            if (!ball.fall && !(ball.height>0)&&Main.distanceBetweenPoints(ball.pos, pos) + ball.radius < radius + FALLINEASIER)
                ball.fallInHole(this);
        }
    }

    @Override
    public void render(ShapeRenderer sr) {
        sr.setColor(1,.5f+.5f*breatheProgress,.5f+.5f*breatheProgress, 1);
        sr.circle(pos.x, pos.y, radius * 1.2f);
        sr.setColor(0, 0, 0, 1);
        super.render(sr);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
