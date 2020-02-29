package com.oxigenoxide.balls.objects.ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.ID;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;
import com.oxigenoxide.balls.objects.hole.Hole;
import com.oxigenoxide.balls.objects.particle.Particle_Pulse;

import static com.oxigenoxide.balls.Game.ball_king;

public class Ball {
    public Sprite sprite;
    public Vector2 pos;
    public Vector2 pos_last;
    public Body body;
    float wiggle;
    public float height;
    public float velY;
    public float radius;
    float count_hitCooldown;
    static final float COUNTMAX_HITCOOLDOWN = 30;

    float maxSpeedMinimum = 6;
    float maxSpeed = maxSpeedMinimum;

    public int size = 0;

    public boolean isUnderGround;
    public boolean doDispose;
    Ball_Main ballmain_hit;
    boolean doDestroy;

    float angleToMid;
    float distToMid;
    float time_off;

    float destroyAngle;
    float destroyImpact;

    public boolean fall;
    Hole hole_fall;
    public Hole hole_spawn;

    public float count_recentlyHit;
    float countMax_recentlyHit = 30;
    public boolean isDisposed;
    public boolean isPassthrough;
    public boolean dontSplit;
    public boolean isLocked;
    boolean doDrainSpeed;
    public float count_cantGetStuck;

    Ball(float x, float y, float height, int size) {
        this.height = height;
        this.size = size;

        pos = new Vector2(x, y);
        pos_last = new Vector2(x, y);
        sprite = new Sprite(Res.tex_ball[0][0]);

        createBody();
        body.setTransform(x * Main.METERSPERPIXEL, y * Main.METERSPERPIXEL, 0);
        radius = body.getFixtureList().first().getShape().getRadius() * Main.PIXELSPERMETER;
        if (height < 0) {
            body.setActive(false);
            isUnderGround = true;
        }
        if (height > 10)
            setPassthrough(true);

        //activateShield();
    }

    public void update() {
        if (!isDisposed) {
            if (height >= 0 && isUnderGround) {
                isUnderGround = false;
                body.setActive(true);
            }

            if (height < 0 || height > 20) {
                //body.setActive(false);
                if (!isPassthrough)
                    setPassthrough(true);
            } else if (isPassthrough)
                setPassthrough(false);
            //body.setActive(true);

            if (fall)
                fall();

            if (isUnderGround)
                height += Game.dt_one_slowed * .2f;

            if (setSpriteUnderGround()) {
                if (doDispose)
                    dispose();
                return;
            }

            pos_last.set(pos);
            pos.set(body.getPosition());
            pos.scl(Main.PIXELSPERMETER);

            sprite.setSize((float) (sprite.getTexture().getWidth() * (1 + wiggle * .5f * -Math.sin(wiggle * 15))), (float) (sprite.getTexture().getHeight() * (1 + wiggle * .5f * -Math.cos(wiggle * 15))));
            sprite.setPosition((int) (pos.x - sprite.getWidth() / 2f), (int) (pos.y + height - sprite.getHeight() / 2f));

            if (Main.getHypothenuse(body.getLinearVelocity().x, body.getLinearVelocity().y) > maxSpeed)
                body.setLinearVelocity(body.getLinearVelocity().x * .75f, body.getLinearVelocity().y * .75f);
            else if (doDrainSpeed)
                body.setLinearVelocity(body.getLinearVelocity().x * .95f, body.getLinearVelocity().y * .95f);

            wiggle = Math.max(0, wiggle -= .05 * Game.dt_one_slowed);

            height += velY * Game.dt_one_slowed;
            height = Math.max(0, height);
            if (height == 0) {
                if (velY < 0) {
                    velY = -velY * .5f;
                    if (velY < .5f)
                        velY = 0;
                    wiggle(velY * .2f);
                    onBounce();
                }
            } else
                velY += Game.GRAVITY_PIXELS * .2f * Game.dt_one_slowed;

            count_hitCooldown = Math.max(0, count_hitCooldown - Main.dt_one);

            //maxSpeed = Math.max(maxSpeedMinimum, maxSpeed - Main.dt_one * .75f);
            maxSpeed = Math.max(maxSpeedMinimum, maxSpeed * (float) Math.pow(.99f, Game.dt_one_slowed));

            distToMid = (float) Math.sqrt(Math.pow(pos.x - Main.width / 2, 2) + Math.pow((pos.y - Main.height / 2) * Main.width / Main.height, 2));
            if (distToMid > Main.width / 2 - 10) {

                time_off += Game.dt_one_slowed;
                //System.out.println("THE BALL IS OFF!! time: "+time_off);
                if (time_off > 60) {
                    angleToMid = Main.angleBetweenPoints(pos, Main.pos_middle);
                    body.setLinearVelocity((float) Math.cos(angleToMid) * .025f + body.getLinearVelocity().x, (float) Math.sin(angleToMid) * .025f + body.getLinearVelocity().y);
                }
            } else {
                time_off = 0;
            }

            if (Main.getHypothenuse(body.getLinearVelocity().x, body.getLinearVelocity().y) < .1f)
                body.setLinearVelocity(0, 0);

            count_recentlyHit = Math.max(count_recentlyHit - Main.dt_one, 0);
            count_cantGetStuck = Math.max(0, count_cantGetStuck - Game.dt_one_slowed);

            //THIS ALWAYS LAST
            if (doDispose) {
                dispose();
            }
        }
    }

    boolean isShielded;

    public void activateShield() {
        if (!isShielded) {
            Res.fixtureDef_shield.shape.setRadius((radius + 2) * Main.METERSPERPIXEL);
            body.createFixture(Res.fixtureDef_shield);
            isShielded = true;
        }
    }

    boolean setSpriteUnderGround() {
        if (height < 0 || fall && height == 0) {
            sprite.setSize((float) (sprite.getTexture().getWidth() / (1 + Math.abs(height))), (float) (sprite.getTexture().getHeight() / (1 + Math.abs(height))));
            sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y - sprite.getHeight() / 2);
            return true;
        }
        return false;
    }

    void onBounce() {

    }

    public boolean isStuck;

    public void getStuck() {
        if (count_cantGetStuck <= 0) {
            isStuck = true;
            //body.setActive(false);
            body.setType(BodyDef.BodyType.StaticBody);
        }
    }

    public void lock() {
        isLocked = true;
    }

    public void ignoreWalls() {
        Filter filter = new Filter();
        filter.maskBits = (Res.MASK_ZERO);
        filter.categoryBits = (Res.MASK_ZERO);
        body.getFixtureList().first().setFilterData(filter);
    }

    public Ball dontSplit() {
        dontSplit = true;
        return this;
    }

    public void setPassthrough(boolean b) {
        if (b) {
            isPassthrough = true;
            Filter filter = new Filter();
            filter.maskBits = (Res.MASK_ZERO | Res.MASK_WALL);
            filter.categoryBits = (Res.MASK_PASSTHROUGH);
            body.getFixtureList().first().setFilterData(filter);
        } else {
            isPassthrough = false;
            Filter filter = new Filter();
            filter.maskBits = (short) (Res.MASK_ZERO | Res.MASK_WALL);
            filter.categoryBits = (short) (Res.MASK_ZERO);
            body.getFixtureList().first().setFilterData(filter);
        }
    }

    public boolean isKinetic() {
        return !fall && height >= 0;
    }

    public void fall() {
        if (Main.distanceBetweenPoints(pos, hole_fall.pos) > hole_fall.radiusMax - radius) {
            pos.set(pos.lerp(hole_fall.pos, .1f));
        } else
            height -= Main.dt_one * .1f;

        if (sprite.getWidth() < 2 || hole_fall.isDisposed) {
            doDispose = true;
        }
    }

    public void fallInHole(Hole hole) {
        if (hole == hole_spawn)
            return;

        if (!fall) {
            body.setActive(false);
            hole_fall = hole;
            fall = true;
        }
    }

    public Ball setDrainSpeed() {
        doDrainSpeed = true;
        return this;
    }

    public void render(SpriteBatch batch) {
        batch.setShader(Res.shader_a);
        Res.shader_a.setUniformf("a",.75f);
        if (isShielded)
            batch.draw(Res.tex_shield, (int) (pos.x - 2 - sprite.getWidth()/2), (int) (pos.y - 2 - sprite.getHeight()/2+height),sprite.getWidth()+4,sprite.getHeight()+4);
        batch.setShader(null);
    }
    public void render_shield_shine(SpriteBatch batch){
        if(isShielded)
            batch.draw(Res.tex_shield_shine, (int) (pos.x - 2 - sprite.getWidth()/2), (int) (pos.y - 2 - sprite.getHeight()/2+height),sprite.getWidth()+4,sprite.getHeight()+4);
    }

    public void render(ShapeRenderer sr) {

    }

    public boolean testHit() {
        if (isKinetic())
            if (Main.tap_speed > Game.HITSPEEDTHRESHOLD && Gdx.input.isTouched() && Main.distanceBetweenPoints(Main.tap[0], pos) <= Math.max(20, radius) && count_hitCooldown == 0) {
                hit(Main.tap_angle, Main.tap_speed * 3);
                return true;
            }
        return false;
    }

    public Ball setVelocity(float vx, float vy) {
        body.setLinearVelocity(vx, vy);
        return this;
    }

    public void hit(float angle, float speed) {
        body.setType(BodyDef.BodyType.DynamicBody);
        isStuck = false;
        count_cantGetStuck = 10;
        body.setLinearVelocity((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
        maxSpeed = speed;
        if (!Main.noFX)
            velY = speed * .2f;
        count_hitCooldown = COUNTMAX_HITCOOLDOWN;
        dropPulseParticle(pos.x - radius * (float) Math.cos(angle), pos.y - radius * (float) Math.sin(angle), 7.5f);
        Main.addSoundRequest(ID.Sound.HIT);
        time_off = 0;
    }

    public void renderShadow(ShapeRenderer sr) {
        if (!isUnderGround && !fall) {
            float smallFactor = 1 / (1 + height * .02f);
            sr.ellipse((int) pos.x - sprite.getWidth()/2 * smallFactor, (int) pos.y - radius * Game.WIDTHTOHEIGHTRATIO * smallFactor, sprite.getWidth()/2 * smallFactor * 2, radius * smallFactor * 2 * Game.WIDTHTOHEIGHTRATIO);
        }
    }

    public void contact(Object ud, Vector2 p, float impact) {
        wiggle();
        p.scl(Main.PIXELSPERMETER);
        dropPulseParticle(p.x, p.y, 1.5f * impact);
        Main.addSoundRequest(ID.Sound.HIT, 5, impact / 10, 1);
    }

    public void contactBall(Ball ball) {
        Main.addSoundRequest(ID.Sound.HIT);
    }

    void dropPulseParticle(float x, float y, float size) {
        if (!Main.noFX)
            Game.particles.add(new Particle_Pulse(x, y, size));
    }

    public void wiggle() {
        if (!Main.noFX && Game.doWiggle) {
            wiggle = 1;
        }
    }

    public void wiggle(float intensity) {
        if (!Main.noFX && Game.doWiggle) {
            wiggle = intensity;
        }
    }

    public void createBody() {
        body = Game.world.createBody(Res.bodyDef_dynamic);
        body.createFixture(Res.fixtureDef_ball[size]);
        body.setUserData(this);
    }

    public void destroy(float angle, float impact, Vector2 pos_danger) {

    }

    public void destroyShield() {
        if (isShielded && !isDisposed) {
            isShielded = false;
            body.destroyFixture(body.getFixtureList().get(1));
        }
    }

    public void explode(float angle, float impact) {
        Main.addSoundRequest(ID.Sound.GLASS);
        doDispose = true;
    }

    int getParticleAmount() {
        if (size == 0)
            return 3;
        else if (size == 1)
            return 5;
        else
            return 10;
    }

    public void applyForce(float angle, float speed) {
        body.applyForceToCenter((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed, true);
        System.out.println("speed: " + speed);
        maxSpeed = speed;
        dropPulseParticle(pos.x, pos.y, 7.5f);
        Main.addSoundRequest(ID.Sound.HIT, 5, 1, (float) Math.random() * .2f + .9f);
        count_recentlyHit = countMax_recentlyHit;
    }


    public void dispose() {
        Game.ballsToRemove.add(this);
        body = Game.destroyBody(body);
        isDisposed = true;
    }

    public void drawTrail(ShapeRenderer sr) {
        if (!Main.noFX) {
            if (height == 0) {
                float distance = Main.distanceBetweenPoints(pos, pos_last);
                float angle = Main.angleBetweenPoints(pos, pos_last);
                float angle_random = (float) (Math.random() * Math.PI * 2);

                for (int i = 0; i < distance; i++) {
                    sr.circle(pos.x + (float) Math.cos(angle) * i * 1 + radius / 2 * (float) Math.cos(angle_random), pos.y + (float) Math.sin(angle) * i * 1 + radius / 2 * (float) Math.sin(angle_random), 2 + (float) Math.random() * 3);
                }
            }
        }
        //float angle=(float)(Math.random()*Math.PI*2);
        //sr.circle(pos.x + radius/2*(float)Math.cos(angle), pos.y + radius/2*(float)Math.sin(angle), 5);
    }

    public void setSpriteTexture(Texture texture) {
        sprite.setTexture(texture);
        sprite.setSize(texture.getWidth(), texture.getHeight());
        sprite.setPosition(pos.x - sprite.getWidth() / 2, pos.y + height - sprite.getHeight() / 2);
    }
}
