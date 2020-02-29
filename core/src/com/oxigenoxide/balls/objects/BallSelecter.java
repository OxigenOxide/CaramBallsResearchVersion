package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.objects.ball.Ball;

import static com.oxigenoxide.balls.Main.tap;

public class BallSelecter {

    Vector2 v0;
    Vector2 v1;
    Vector2 v2;
    Vector2 v_pointer;
    float radiusMax = 10;
    float radius;
    Ball ball_selected;
    boolean active;
    float cang;
    float distanceMax = 50;
    float distanceToSelect = 40;
    float stretched;

    public BallSelecter() {
        v0 = new Vector2();
        v1 = new Vector2();
        v2 = new Vector2();
        v_pointer = new Vector2();
    }

    public void update() {
        if (Gdx.input.justTouched() && !Game.isGameOver && !Game.isPaused) {
            float distance;
            float closestDistance = distanceToSelect;
            Ball closestBall = null;
            for (Ball ball : Game.balls) {
                if (!ball.isLocked && ball.isKinetic() && ball.height<=20) {
                    distance = Main.distanceBetweenPoints(ball.pos, tap[0]);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestBall = ball;
                    }
                }
            }
            if (closestBall != null)
                ball_selected = closestBall;
        }


        active = ball_selected != null && Gdx.input.isTouched() && !ball_selected.isDisposed && !Game.doGameOverCue;
        if (!active) {
            ball_selected = null;
            //Game.slowdown = Math.max(Game.slowdown - Main.dt_one * .05f, 0);
            Game.slowdown_effect = Math.max(Game.slowdown_effect - Main.dt_one * .05f, 0);
            Game.slowdown = 0;
        }
        if (active) {
            if(Main.noFlow)
                Game.slowdown_effect = Math.min(Game.slowdown_effect + Main.dt_one * .05f, .8f);
            else
                Game.slowdown_effect = Math.min(Game.slowdown_effect + Main.dt_one * .05f, .9f);

            Game.slowdown = Game.slowdown_effect;

            cang = Main.angleBetweenPoints(ball_selected.pos, tap[0]);
            v0.set(ball_selected.radius + 2, 0);
            v0.rotateRad(cang);
            v0.add(ball_selected.pos);

            float distanceToPointer = Main.distanceBetweenPoints(ball_selected.pos, tap[0]);
            stretched = Math.min(distanceMax, distanceToPointer) / distanceMax;
            if (distanceToPointer < distanceMax) {
                v_pointer.set(tap[0]);
            } else {
                v_pointer.set(distanceMax, 0);
                v_pointer.rotateRad(cang);
                v_pointer.add(ball_selected.pos);
            }

            float a = Main.distanceBetweenPoints(v0, v_pointer);
            float ang = Main.angleBetweenPoints(v0, v_pointer);
            float c = (float) Math.sqrt(-Math.pow(radius, 2) + Math.pow(a, 2));
            float bang = (float) Math.acos(c / a);
            v1.set(c, 0);
            v1.rotateRad(ang + bang);
            v1.add(v0);
            v2.set(c, 0);
            v2.rotateRad(ang - bang);
            v2.add(v0);
            radius = radiusMax / (1 + (Main.distanceBetweenPoints(ball_selected.pos, tap[0])) / distanceMax);
        }
    }

    public void onRelease() {
        if (active && !ball_selected.isDisposed)
            ball_selected.hit(cang, 15 * stretched);
    }

    public void render(ShapeRenderer sr) {

        if (active) {
            sr.setColor(1, 1, 1, 1);
            sr.circle(v_pointer.x, v_pointer.y, radius);
            sr.triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y);
        }
        //sr.triangle(0,0,0,20,20,0);
    }
}
