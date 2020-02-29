package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.ball.Ball_Main;

public class Orb {
    Vector2 pos;
    float ang;
    float vel;
    boolean isFollowing;
    Ball ball_following;
    float lerpAlpha;

    public Orb(float x, float y) {
        pos = new Vector2(x, y);
        ang = (float) (Math.random() * Math.PI * 2);
        vel = 2;
        Game.orbCounter.show();
    }

    public void update() {
        vel = Math.max(0, vel - .1f);
        pos.add((float) Math.cos(ang) * vel, (float) Math.sin(ang) * vel);
        if (!isFollowing && vel == 0) {

            float closestDistance = -1;
            Ball closestBall=null;
            for (Ball ball : Game.balls) {
                if(ball.isKinetic() && ball.getClass()== Ball_Main.class) {
                    float dst = Main.distanceBetweenPoints(ball.pos, pos);
                    if (closestDistance == -1) {
                        closestDistance = dst;
                        closestBall = ball;
                    }
                    if (dst < closestDistance) {
                        closestBall = ball;
                        closestDistance = dst;
                    }
                }
            }
            if(closestBall!=null) {
                isFollowing=true;
                ball_following = closestBall;
            }
        }
        if (isFollowing && ball_following!=null) {
            lerpAlpha=Math.min(1,lerpAlpha+.02f);
            pos.lerp(ball_following.pos, lerpAlpha);
            if(Main.distanceBetweenPoints(pos,ball_following.pos)<ball_following.radius)
                collect();
        }
    }
    public void collect(){
        Game.orbsToRemove.add(this);
        Main.gameData.orbs++;
        Game.orbCounter.onOrbCollected();
        Game.collectSoundsToPlay++;
        Game.orbsCollected++;
        Main.userData.orbsCollected++;
    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_orb, pos.x, pos.y);
    }
}
