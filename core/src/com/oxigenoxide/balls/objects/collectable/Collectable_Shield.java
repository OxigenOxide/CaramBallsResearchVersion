package com.oxigenoxide.balls.objects.collectable;

import com.badlogic.gdx.graphics.Texture;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.ball.Ball;

public class Collectable_Shield extends Collectable {
    public Collectable_Shield() {
        super(Res.tex_collectable_shield);
    }

    @Override
    public void pickUp(Ball ball) {
        super.pickUp(ball);
        ball.activateShield();
    }
}
