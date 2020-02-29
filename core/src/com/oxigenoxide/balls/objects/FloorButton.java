package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.ball.Ball_Bad;

public class FloorButton {
    Vector2 pos;
    boolean isPressed;
    static int width = 15, height = 15;
    Texture texture;
    Sprite sprite;
    boolean doImpactFrames;
    int impactFrames;
    float alpha = 1;
    float timeAfterPress;

    public FloorButton(float x, float y) {
        pos = new Vector2((int) x, (int) y);
        texture = Res.tex_floorButton_danger;
        sprite = new Sprite(texture);
        sprite.setPosition(pos.x - width / 2, pos.y - height / 2);
    }

    public FloorButton() {
        pos = Game.getRandomPosOnTable(width, height);
        texture = Res.tex_floorButton_danger;
        sprite = new Sprite(texture);
        sprite.setPosition(pos.x - width / 2, pos.y - height / 2);
    }

    public void update() {
        for (Ball ball : Game.balls)
            if (Main.distanceBetweenPoints(ball.pos, pos) - ball.radius < width / 2)
                press();

        if (isPressed) {
            timeAfterPress += Main.dt_one;
            if (timeAfterPress > 120)
                alpha = Math.max(0, alpha - .05f);
        }

        if (alpha == 0)
            dispose();

        sprite.setAlpha(alpha);
    }

    public void press() {
        if (!isPressed) {
            isPressed = true;
            sprite.setTexture(Res.tex_floorButtonPressed_danger);
            doImpactFrames = true;
            Game.ballsToAdd.add(new Ball_Bad(pos.x, pos.y + 3, Main.height, 0));
        }
    }

    public void render(SpriteBatch batch) {

        if (doImpactFrames) {
            batch.setShader(Res.shader_c);
            Res.shader_c.setUniformf("c", 1, 1, 1, 1);
        }
        sprite.draw(batch);
        if (doImpactFrames) {
            batch.setShader(null);
            impactFrames++;
            if (impactFrames > 4)
                doImpactFrames = false;
        }
    }

    public void dispose() {
        Game.floorButtonsToRemove.add(this);
    }
}
