package com.oxigenoxide.balls.objects.hole;

import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.ball.Ball_Main;

public class Hole_Ball extends Hole {
    public Hole_Ball(){
        super();
        //radiusMax = Res.tex_ball[0][Ball_Main.getSize(getBallNum())].getWidth() / 2 + 1;
        radiusMax = (int)Res.ballRadius[0] + .75f;
        setPosition();
    }
    public void update(){
        super.update();

        if (count < 100) {
            radius = count / 100 * radiusMax;
        }
        else{
            radius=radiusMax;
        }
        if (count > 120 && count < 130) {
            if (!hasSpewed)
                spew();
        }
        if (count > 200 && count <= 250) {
            radius = radiusMax - (count - 200) / 50 * radiusMax;
        }
        if (count > 250)
            dispose();

    }

    void spew() {

        if (Game.getTotalBallSize() < 8) {
            Ball ball = new Ball_Main(pos.x, pos.y, -10, 0, Game.level);
            float angle = (float) (Math.random() * Math.PI * 2);
            Game.balls.add(ball);
            ball.body.setLinearVelocity((float) Math.cos(angle) * 10, (float) Math.sin(angle) * 10);
            ball.velY = 4;
            ball.hole_spawn = this;
            hasSpewed = true;
        }
    }

    int getBallNum() {
        if (Game.ball_king != null)
            return Math.max(0, Game.ball_king.getNum());
        return 0;
    }
}
