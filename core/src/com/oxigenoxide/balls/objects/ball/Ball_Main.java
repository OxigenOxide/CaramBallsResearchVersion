package com.oxigenoxide.balls.objects.ball;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.ID;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.hole.Hole;
import com.oxigenoxide.balls.objects.particle.Particle_BallShard;

import static com.oxigenoxide.balls.Game.ball_king;

public class Ball_Main extends Ball {

    public int level;
    float count_noPull;
    Color[] palette;

    public Ball_Main(float x, float y, float height, int size, int level) {
        super(x, y, height, size);


        if (Game.level < level) {
            Game.nextLevel();
            Game.throwConfetti(pos.x, pos.y);
            for (Ball_Main ball_main : Game.mainBalls)
                if (ball_main != this && !ball_main.doDispose)
                    ball_main.explode(0, 1);
        }


        this.level = level;
        palette = Game.getBallPalette(level);
        setSpriteTexture(Res.tex_ball[Game.ballType][size]);

        Game.mainBalls.add(this);

        isBallKing();
        setSpriteUnderGround();

    }

    @Override
    public void update() {
        super.update();
        if (!isDisposed) {
            if (ballmain_hit != null && !ballmain_hit.isDisposed) {

                doDispose = true;
                ballmain_hit.doDispose = true;

                Ball ball_new;
                if (size + 1 < 3)
                    ball_new = new Ball_Main((pos.x + ballmain_hit.pos.x) / 2, (pos.y + ballmain_hit.pos.y) / 2, 0, (size + 1), level);
                else
                    ball_new = new Ball_Main((pos.x + ballmain_hit.pos.x) / 2, (pos.y + ballmain_hit.pos.y) / 2, 0, 0, level + 1);

                ball_new.body.setLinearVelocity(body.getLinearVelocity().add(ballmain_hit.body.getLinearVelocity()).scl(.5f));
                Game.ballsToAdd.add(ball_new);

                ballmain_hit = null;
                Main.shake();
                Main.fbm.writeMerges();
                Main.addSoundRequest(ID.Sound.PLOP, 6);
                Game.onBallMerge();
            }

            if (count_noPull <= 0) {
                for (Ball ball : Game.balls) {
                    if (ball != this && ball.getClass() == Ball_Main.class && !ball.isStuck && ball.isKinetic() && !ball.isPassthrough && isKinetic() && !isPassthrough) {
                        Ball_Main bm = (Ball_Main) ball;
                        if (bm.level == level && bm.size == size && Main.distanceBetweenPoints(ball.pos, pos) - ball.radius - radius < 10) {
                            float ang = Main.angleBetweenPoints(pos, ball.pos);
                            body.setLinearVelocity(body.getLinearVelocity().x + (float) Math.cos(ang) * 2, body.getLinearVelocity().y + (float) Math.sin(ang) * 2);
                        }
                    }
                }
            } else
                count_noPull -= Main.dt_one;

            if(doBeginGameOverCue){
                Game.beginGameOverCue(this);
                doBeginGameOverCue=false;
            }
            if(doSplit) {
                split(pos_hitBy);
                doSplit=false;
            }
        }
    }

    @Override
    public boolean testHit() {
        return super.testHit();
    }

    @Override
    public void hit(float angle, float speed) {
        super.hit(angle, speed);
    }

    @Override
    public void contact(Object ud, Vector2 p, float impact) {
        super.contact(ud, p, impact);
    }

    @Override
    public void contactBall(Ball ball) {


        if (ball.getClass() == Ball_Main.class) {
            Ball_Main ball_main = (Ball_Main) ball;
            if (!ball_main.isUnderGround && ball_main.size == size && ball_main.level == level) {
                ballmain_hit = ball_main;
                return;
            }
        }
        super.contactBall(ball);
    }

    @Override
    void dropPulseParticle(float x, float y, float size) {
        super.dropPulseParticle(x, y, size);
    }

    @Override
    public void wiggle() {
        super.wiggle();
    }

    @Override
    public void createBody() {
        super.createBody();
    }

    public void isBallKing() {

        if (Game.inTutorialMode)
            return;

        if (ball_king == null) {
            Game.crown.newOwner(this);
            return;
        }
        if (size > ball_king.size || level > ball_king.level) {
            if (level >= ball_king.level) {
                Game.crown.newOwner(this);
            }
        }
    }

    public void render(SpriteBatch batch) {
        //batch.end();
        //batch.begin();
        batch.setShader(null);
        super.render(batch);
        batch.setShader(Res.shader_palette);
        Res.shader_palette.setUniformf("color0", palette[0].r, palette[0].g, palette[0].b, 1);
        Res.shader_palette.setUniformf("color1", palette[1].r, palette[1].g, palette[1].b, 1);
        Res.shader_palette.setUniformf("color2", palette[2].r, palette[2].g, palette[2].b, 1);
        Res.shader_palette.setUniformf("color3", palette[3].r, palette[3].g, palette[3].b, 1);
        sprite.draw(batch);
        batch.setShader(null);
        render_shield_shine(batch);
    }

    @Override
    public void destroy(float angle, float impact, Vector2 pos_danger) {
        if(!Main.inScreenShotMode) {
            if(!isShielded) {
                if (!Game.inTutorialMode && size == 0) {
                    int ballsCounted = 0;

                    for (Ball_Main ball : Game.mainBalls) {
                        if (!ball.isUnderGround)
                            ballsCounted++;
                    }
                    if (ballsCounted <= 1) {
                        doBeginGameOverCue = true;
                        return;
                    }
                }
                if (size == 0 || dontSplit) {
                    explode(angle, impact);
                } else if (pos_danger != null) {
                    pos_hitBy = pos_danger;
                    doSplit = true;
                }
            } else {
                destroyShield();
            }
        }

    }

    boolean doBeginGameOverCue;
    boolean doSplit;
    Vector2 pos_hitBy;
    boolean hasExeploded;

    public void explode(float angle, float impact) {
        if (!hasExeploded) {
            hasExeploded = true;
            impact = Math.min(impact, 4);
            if (!Main.noFX) {
                for (int i = 0; i < getParticleAmount(); i++) {
                    Game.particlesToAdd.add(new Particle_BallShard(pos.x, pos.y, (float) (angle + Math.random() * Math.PI * 1.2f - Math.PI * .6f), impact * (.5f + (float) Math.random()), palette));
                }
            }
            super.explode(angle, impact);
            Game.doOnMainBallDestroyed = true;
        }
    }


    static final float SPLITANGLE = (float) Math.PI;
    //static final float SPLITANGLE=(float)Math.PI*1/2f;
    boolean isSplit;

    public void split(Vector2 pos_danger) {

        if (!isSplit) {
            isSplit = true;
            //Vector2 vel = new Vector2(body.getLinearVelocity());
            //float ang= (float)Math.atan2(vel.y,vel.x);

            float ang = Main.angleBetweenPoints(pos_danger, pos);
            Vector2 vel = new Vector2((float) Math.cos(ang) * 5, (float) Math.sin(ang) * 5);
            int size = this.size - 1;
            float r = Res.ballRadius[size] + 2;
            Ball_Main ball_1 = new Ball_Main(pos.x + (float) Math.cos(ang + SPLITANGLE / 2) * r, pos.y + (float) Math.sin(ang + SPLITANGLE / 2) * r, 0, size, this.level);
            Ball_Main ball_2 = new Ball_Main(pos.x + (float) Math.cos(ang - SPLITANGLE / 2) * r, pos.y + (float) Math.sin(ang - SPLITANGLE / 2) * r, 0, size, this.level);
            ball_1.body.setLinearVelocity(vel.rotateRad(SPLITANGLE / 2));
            ball_2.body.setLinearVelocity(vel.rotateRad(-SPLITANGLE));

            Game.ballsToAdd.add(ball_1);
            Game.ballsToAdd.add(ball_2);
            ball_1.setNoPull();
            ball_2.setNoPull();
            doDispose = true;
        }
    }

    public int getValue() {
        return (int) Math.pow(2, getNum());
    }

    public void setNoPull() {
        count_noPull = 30;
    }

    public int getNum() {
        return level * 3 + size;
    }

    public static int getLevel(int num) {
        return num / 3;
    }

    public static int getSize(int num) {
        return num % 3;
    }

    @Override
    public void fallInHole(Hole hole) {
        super.fallInHole(hole);
        int ballsCounted = 0;

        for (Ball_Main ball : Game.mainBalls)
            if (!ball.isUnderGround)
                ballsCounted++;
        if (ballsCounted <= 1) {
            Game.beginGameOverCue(this);
            System.out.println("DOGAMEOVERCUE");
            return;
        } else {
            Game.mainBalls.remove(this);
        }
    }

    @Override
    public void drawTrail(ShapeRenderer sr) {
        sr.setColor(palette[1].r, palette[1].g, palette[1].b, 1);
        super.drawTrail(sr);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this == ball_king)
            ball_king = null;
        Game.mainBalls.remove(this);
    }
}
