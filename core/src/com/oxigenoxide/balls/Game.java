package com.oxigenoxide.balls;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.oxigenoxide.balls.objects.BallSelecter;
import com.oxigenoxide.balls.objects.Bullet;
import com.oxigenoxide.balls.objects.Bumper;
import com.oxigenoxide.balls.objects.Cannon;
import com.oxigenoxide.balls.objects.Cat;
import com.oxigenoxide.balls.objects.Crown;
import com.oxigenoxide.balls.objects.Egg;
import com.oxigenoxide.balls.objects.FloorButton;
import com.oxigenoxide.balls.objects.Honey;
import com.oxigenoxide.balls.objects.Orb;
import com.oxigenoxide.balls.objects.OrbCounter;
import com.oxigenoxide.balls.objects.Pin;
import com.oxigenoxide.balls.objects.SoundRequest;
import com.oxigenoxide.balls.objects.Spike;
import com.oxigenoxide.balls.objects.TutorialSwipeExample;
import com.oxigenoxide.balls.objects.ball.Ball;
import com.oxigenoxide.balls.objects.ball.Ball_Bomb;
import com.oxigenoxide.balls.objects.button.Button;
import com.oxigenoxide.balls.objects.button.Button_Exit;
import com.oxigenoxide.balls.objects.button.Button_Pause;
import com.oxigenoxide.balls.objects.collectable.Collectable;
import com.oxigenoxide.balls.objects.collectable.Collectable_Shield;
import com.oxigenoxide.balls.objects.hole.Hole;
import com.oxigenoxide.balls.objects.Point;
import com.oxigenoxide.balls.objects.Tracer;
import com.oxigenoxide.balls.objects.ball.Ball_Bad;
import com.oxigenoxide.balls.objects.ball.Ball_Main;
import com.oxigenoxide.balls.objects.hole.Hole_Ball;
import com.oxigenoxide.balls.objects.hole.Hole_Fall;
import com.oxigenoxide.balls.objects.particle.Particle;
import com.oxigenoxide.balls.objects.particle.Particle_Confetti;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.oxigenoxide.balls.Main.game;
import static com.oxigenoxide.balls.Main.gameData;
import static com.oxigenoxide.balls.Main.getHypothenuse;
import static com.oxigenoxide.balls.Main.setCamEffects;
import static com.oxigenoxide.balls.Main.setCamNoZoom;
import static com.oxigenoxide.balls.Main.setCamNormal;
import static com.oxigenoxide.balls.Main.setCamShake;
import static com.oxigenoxide.balls.Main.setCamZoom;
import static com.oxigenoxide.balls.Main.setMusic;
import static com.oxigenoxide.balls.Main.setNoCamEffects;
import static com.oxigenoxide.balls.Main.tap;
import static com.oxigenoxide.balls.Res.tex_fullscreen;

public class Game extends Scene {
    public static World world;
    public static final float GRAVITY = -9.81f;
    public static final float GRAVITY_PIXELS = -9.81f * 1 / 4f;
    public static ArrayList<Ball> balls;
    public static ArrayList<Ball_Main> mainBalls;
    public static ArrayList<Ball> ballsToAdd;
    public static ArrayList<Ball> ballsToRemove;
    public static ArrayList<Pin> pins;
    public static ArrayList<Pin> pinsToRemove;
    public static ArrayList<Particle> particles;
    public static ArrayList<Particle> particlesToRemove;
    public static ArrayList<Particle> particlesToAdd;
    public static ArrayList<Particle> particles_batch;
    public static ArrayList<Particle> particles_sr;
    public static ArrayList<Tracer> tracers;
    public static ArrayList<Tracer> tracersToRemove;
    public static ArrayList<Bumper> bumpers;
    public static ArrayList<Hole> holes;
    public static ArrayList<Hole> holesToRemove;
    public static ArrayList<FloorButton> floorButtons;
    public static ArrayList<FloorButton> floorButtonsToRemove;
    public static ArrayList<Spike> spikes;
    public static ArrayList<Spike> spikesToRemove;
    public static ArrayList<Egg> eggs;
    public static ArrayList<Egg> eggsToRemove;
    public static ArrayList<Orb> orbs;
    public static ArrayList<Orb> orbsToRemove;
    public static ArrayList<Cat> cats;
    public static ArrayList<Cat> catsToRemove;
    public static ArrayList<Cannon> cannons;
    public static ArrayList<Cannon> cannonsToRemove;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Bullet> bulletsToRemove;
    public static ArrayList<Honey> honey;
    public static ArrayList<Honey> honeyToRemove;
    public static ArrayList<Collectable> collectables;
    public static ArrayList<Collectable> collectablesToRemove;
    public static Body border;
    public static OrbCounter orbCounter;
    public static Color[] palette_table = new Color[]{new Color(), new Color(), new Color(), new Color()};
    public static Color[] palette_target_table = Res.tableTopPalette[0];
    public static FrameBuffer buffer;
    public static FrameBuffer buffer_slow;
    public static FrameBuffer buffer_trail;
    public static FrameBuffer buffer_table;
    public static Texture tex_buffer;
    public static Texture tex_buffer_trail;
    public static Texture tex_buffer_slow;
    public static Texture tex_buffer_table;
    public static Sprite sprite_buffer_trail;
    public static Vector2 dim_screen;
    public static Point point;
    public static Button button_pause;
    public static Button button_exit;
    public static boolean doPixelate = true;
    public static final float HITSPEEDTHRESHOLD = 5;
    public static final float WIDTHTOHEIGHTRATIO = .8f;
    public static float worldSpeed = 1f;
    public static float slowdown;
    public static float slowdown_effect;
    public static float alpha_darkOverlay;
    public static boolean doDarkOverlay;
    public static Ball_Bad ball_bad;
    public static Crown crown;
    public static Ball_Main ball_king;
    public static int score;
    public static boolean doGameOver;
    public static FirebaseInterface fbm;
    public static int level;
    static boolean doClearTrail;
    static public boolean doWiggle = true;
    static Vector2 pos_clearTrail;
    public static Vector2 pos_zoom;
    float musicVolume;
    boolean playMusic = false;
    float gravityAngle;
    float gravityIntensity = .5f;
    static float countMax_nextEgg = 2000;
    float count_nextEgg = countMax_nextEgg;

    Vector2 gravity;
    Vector2 worldGravity;
    static BallSelecter ballSelecter;
    static TutorialSwipeExample tutorialSwipeExample;

    static int countMax_nextButton = 300;
    float count_nextButton = countMax_nextButton;
    static float countMax_nextSpike = 2000;
    static float count_nextSpike = countMax_nextSpike;
    static int countMax_nextBallSpawnPeriod = 2000;
    static float count_nextBallSpawnPeriod = countMax_nextBallSpawnPeriod;
    static int ballsToSpawn;
    static int ballsToSpawnMax = 4;
    static int countMax_nextBall = 150;
    static float count_nextBall = countMax_nextBall;
    static int countMax_nextFallHole = 150;
    static float count_nextFallHole = countMax_nextFallHole;


    public static int ballType;
    public static int orbsCollected;

    static float worldStepFactor = .75f;

    static float count_hole;
    static float count_collectSound;
    float count;
    float count_trailClear;

    int random0;
    int random1;

    boolean doTilt;

    public static float dt_slowed;
    public static float dt_one_slowed;


    static boolean changeTableColor;
    static boolean doNextTutorial;
    static boolean doOnBallCollide;

    public static boolean doOnMainBallDestroyed;

    public static float collectSoundsToPlay;

    public static boolean isGameOver;
    public static boolean inTutorialMode;
    public static boolean doSetTutorialMode;
    public static boolean ultraSlow;
    public static boolean doGameOverCue;
    public static boolean doReplay;
    public static float count_gameOverCue;
    public static float countMax_gameOverCue = 120;
    public static boolean isPaused;
    BitmapFont font;

    ContactListener contactListener;
    InputProcessor inputProcessor;

    public static GameOver gameOver;

    static final boolean DODEBUGTOOLS = false;



    public Game() {
        createWorld();

        gameOver = new GameOver();
        gravity = new Vector2(9.81f, 0);
        worldGravity = new Vector2();
        balls = new ArrayList<Ball>();
        ballsToAdd = new ArrayList<Ball>();
        ballsToRemove = new ArrayList<Ball>();
        crown = new Crown();
        contactListener = new B2DContactListener();
        world.setContactListener(contactListener);
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        buffer_slow = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        buffer_trail = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        buffer_table = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        dim_screen = new Vector2(Main.width, Main.height);
        point = new Point();
        bumpers = new ArrayList<Bumper>();
        particles = new ArrayList<Particle>();
        particles_batch = new ArrayList<Particle>();
        particles_sr = new ArrayList<Particle>();
        particlesToRemove = new ArrayList<Particle>();
        particlesToAdd = new ArrayList<Particle>();
        mainBalls = new ArrayList<Ball_Main>();
        pins = new ArrayList<Pin>();
        pinsToRemove = new ArrayList<Pin>();
        tracers = new ArrayList<Tracer>();
        eggsToRemove = new ArrayList<Egg>();
        eggs = new ArrayList<Egg>();
        orbs = new ArrayList<Orb>();
        orbsToRemove = new ArrayList<Orb>();
        spikes = new ArrayList<Spike>();
        cats = new ArrayList<Cat>();
        catsToRemove = new ArrayList<Cat>();
        spikesToRemove = new ArrayList<Spike>();
        tracersToRemove = new ArrayList<Tracer>();
        holes = new ArrayList<Hole>();
        holesToRemove = new ArrayList<Hole>();
        floorButtons = new ArrayList<FloorButton>();
        floorButtonsToRemove = new ArrayList<FloorButton>();
        cannons = new ArrayList<Cannon>();
        cannonsToRemove = new ArrayList<Cannon>();
        honey = new ArrayList<Honey>();
        honeyToRemove = new ArrayList<Honey>();
        bullets = new ArrayList<Bullet>();
        bulletsToRemove = new ArrayList<Bullet>();
        collectablesToRemove=new ArrayList<Collectable>();
        collectables=new ArrayList<Collectable>();
        font = new BitmapFont();
        pos_clearTrail = new Vector2();
        sprite_buffer_trail = new Sprite(Res.tex_tabletop);
        ballSelecter = new BallSelecter();
        orbCounter = new OrbCounter();
        palette_dir_table = new Vector3[]{new Vector3(), new Vector3(), new Vector3(), new Vector3()};
        pos_zoom = new Vector2(Main.width / 2, Main.height / 2);
        createInputProcessor();
        button_pause = new Button_Pause(new Vector2(Main.width - 2 - Res.tex_button_pause.getWidth(), Main.height - 2 - Res.tex_button_pause.getHeight()));
        button_exit = new Button_Exit(new Vector2(Main.width - 2 - Res.tex_button_pause.getWidth(), Main.height - 18 - Res.tex_button_pause.getHeight()));
        ballType = Main.gameData.selectedBall;


        for (int i = 0; i < 4; i++) {
            palette_table[i].set(palette_target_table[i]);
        }

        // TEST


    }

    @Override
    public void show() {
        if (doSetTutorialMode) {
            doSetTutorialMode = false;
            setTutorialMode();
        }
        if (!isGameOver)
            setup();
    }

    @Override
    public void update() {

        if (Main.noFlow) {
            worldStepFactor = 1;
            //slowdown = 0;
        }

        if (ultraSlow)
            slowdown = .95f;

        if (isPaused)
            slowdown = 1;
        dt_slowed = Main.dt * (1 - slowdown);
        dt_one_slowed = dt_slowed * 60;

        count += Main.dt_one;
        count_trailClear += dt_one_slowed;

        if (isGameOver || gameOver.alpha > 0)
            gameOver.update();

        if (!isGameOver && !inTutorialMode && !doGameOverCue)
            update_game();


        if (inTutorialMode)
            update_tutorial();

        if (DODEBUGTOOLS) {
            if (Gdx.input.justTouched()) {
                if (Main.distanceBetweenPoints(tap[0], new Vector2(Main.width, Main.height)) < 10) {
                    for (Ball ball : balls)
                        ball.velY += 20;
                }
                if (Main.distanceBetweenPoints(tap[0], new Vector2(0, Main.height)) < 10) {
                    doPixelate = !doPixelate;
                    Main.gm.signOut();
                }
                if (Main.distanceBetweenPoints(tap[0], new Vector2(Main.width, 0)) < 10) {
                    balls.add(new Ball_Bad((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, 0));
                    Main.gm.showLeaderBoards();
                }

                if (Main.distanceBetweenPoints(tap[0], new Vector2(0, 0)) < 10)
                    balls.add(new Ball_Main((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, Ball_Main.getSize(Math.max(0, 0)), Ball_Main.getLevel(Math.max(0, 0))));

                if (Main.distanceBetweenPoints(tap[0], new Vector2(0, 0)) < 10)
                    nextLevel();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                balls.add(new Ball_Main(tap[0].x, tap[0].y, 0, 1, 0));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
                floorButtons.add(new FloorButton(tap[0].x, tap[0].y));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
                eggs.add(new Egg());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
                spikes.add(new Spike());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                Main.shake();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                nextLevel();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
                holes.add(new Hole_Fall(tap[0].x, tap[0].y));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                gameData.orbs += 200;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
                throwRandomBalls();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
                putRandomObstacles();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
                gameOver();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                createEggRing();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                cannons.add(new Cannon());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
                honey.add(new Honey());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                balls.add(new Ball_Bomb(tap[0].x, tap[0].y, 0));
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
                collectables.add(new Collectable_Shield());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            body_gap = destroyBody(body_gap);
        }

        int value = 0;
        for (Ball_Main ball : mainBalls) {
            if (!ball.isUnderGround)
                value += ball.getValue();
        }
        if (value > score) {
            score = value;
        }
        balls.addAll(ballsToAdd);
        ballsToAdd.clear();
        for (Ball ball : balls)
            ball.update();
        balls.removeAll(ballsToRemove);
        ballsToRemove.clear();

        for (Orb orb : orbs) {
            orb.update();
        }
        orbs.removeAll(orbsToRemove);
        orbsToRemove.clear();

        for (Bumper bumper : bumpers)
            bumper.update();

        for (Egg egg : eggs)
            egg.update();
        eggs.removeAll(eggsToRemove);
        eggsToRemove.clear();

        particles.addAll(particlesToAdd);
        particlesToAdd.clear();
        for (Particle particle : particles)
            particle.update();
        particles.removeAll(particlesToRemove);
        particlesToRemove.clear();
        for (Hole hole : holes)
            hole.update();
        holes.removeAll(holesToRemove);
        holesToRemove.clear();
        for (Tracer tracer : tracers)
            tracer.update();
        tracers.removeAll(tracersToRemove);
        tracersToRemove.clear();

        for (Cat cat : cats)
            cat.update();
        cats.removeAll(catsToRemove);
        catsToRemove.clear();

        for (Bullet b : bullets)
            b.update();
        bullets.removeAll(bulletsToRemove);
        bulletsToRemove.clear();

        for (FloorButton fb : floorButtons)
            fb.update();
        floorButtons.removeAll(floorButtonsToRemove);
        floorButtonsToRemove.clear();

        for (Honey h : honey)
            h.update();
        honey.removeAll(honeyToRemove);
        honeyToRemove.clear();

        for (Collectable c : collectables)
            c.update();
        collectables.removeAll(collectablesToRemove);
        collectablesToRemove.clear();

        for (Spike s : spikes)
            s.update();
        spikes.removeAll(spikesToRemove);
        spikesToRemove.clear();

        for (Pin p : pins)
            p.update();
        pins.removeAll(pinsToRemove);
        pinsToRemove.clear();

        for (Cannon c : cannons)
            c.update();
        cannons.removeAll(cannonsToRemove);
        cannonsToRemove.clear();

        //point.update();
        crown.update();
        ballSelecter.update();
        if (tutorialSwipeExample != null)
            tutorialSwipeExample.update();
        orbCounter.update();

        if (!isGameOver && button_pause.isTouching()) {
            button_pause.update();
        }
        if (!isGameOver && button_exit.isTouching()) {
            button_exit.update();
        }

        int ballsCounted = 0;
        for (Ball_Main ball : Game.mainBalls) {
            if (!ball.isUnderGround)
                ballsCounted++;
        }
        //System.out.println("BallsCounted: " +ballsCounted+ "  TotalBallSize: "+Game.getTotalBallSize());


        if (playMusic) {
            if (!point.isActive)
                musicVolume = Math.min(musicVolume + .01f, .75f);
            else {
                musicVolume = Math.max(musicVolume - .08f, .35f);
            }
        }

        if (doGameOver) {
            doGameOver = false;
            gameOver();
        }

        if (!isGameOver)
            world.step(dt_slowed * worldStepFactor, 3, 8);

        if (doTilt) {
            gravityAngle += .01f;
            worldGravity.set(gravity);
            worldGravity.setAngle((float) Math.toDegrees(gravityAngle)).scl(gravityIntensity);
            world.setGravity(worldGravity);
            gravityIntensity = (1 + (float) Math.sin(count / 100)) / 2;
        } else {
            world.setGravity(Vector2.Zero);
            gravityIntensity = 0;
        }
        //gravityIntensity=0;
        if (doOnBallCollide) {
            onBallCollide();
            doOnBallCollide = false;
        }

        int num = (int) (count_trailClear / 3) % 4;
        switch (num) {
            case 0:
                random0 = 0;
                random1 = 0;
                break;
            case 1:
                random0 = 1;
                random1 = 0;
                break;
            case 2:
                random0 = 0;
                random1 = 1;
                break;
            case 3:
                random0 = 1;
                random1 = 1;
                break;
        }

        if (changeTableColor) {

            for (int i = 0; i < 4; i++) {
                palette_table[i].add(palette_dir_table[i].x, palette_dir_table[i].y, palette_dir_table[i].z, 0);
            }
            if (Math.abs(palette_table[0].r - palette_target_table[0].r) < .01f && Math.abs(palette_table[0].g - palette_target_table[0].g) < .01f && Math.abs(palette_table[0].b - palette_target_table[0].b) < .01f)
                changeTableColor = false;
        }

        if (doNextTutorial) {
            setNextTutorial();
            doNextTutorial = false;
        }
        if (doOnMainBallDestroyed) {
            onMainBallDestroyed();
            doOnMainBallDestroyed = false;
        }

        if (doGameOverCue) {
            update_gameOverCue();
            zoom += (ZOOM_MAX - zoom) * .02f;
            zoom = Math.min(ZOOM_MAX, zoom);
        } else {
            if (zoom > 1) {
                zoom -= Main.dt_one * ZOOMOUT_SPEED;
                zoom = Math.max(1, zoom);
            }
        }

        if (doDarkOverlay) {
            alpha_darkOverlay = Math.min(.5f, alpha_darkOverlay + .05f);
        } else {
            alpha_darkOverlay = Math.max(0, alpha_darkOverlay - .05f);
        }
    }

    public void throwRandomBalls() {
        int amount_balls = 10;
        for (int i = 0; i < amount_balls; i++) {
            balls.add(new Ball_Main((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, 0, level).setVelocity((float) Math.random() * 35, (float) Math.random() * 35));
        }
    }

    public void putRandomObstacles() {
        int amount_obstacles = 3;
        for (int i = 0; i < amount_obstacles; i++) {
            createSpikePatch();
            //balls.add(new Ball_Main((float) Math.random() * Main.width, (float) Math.random() * Main.height, 0, 0, level).setVelocity((float) Math.random() * 5, (float) Math.random() * 5));
        }
    }

    public static void update_gameOverCue() {
        count_gameOverCue -= Main.dt_one;

        if (mainBalls.size() != 0) {
            pos_zoom.set(mainBalls.get(0).pos);
            pos_zoom.x = MathUtils.clamp(pos_zoom.x, Main.width / 2 / zoom, Main.width - Main.width / 2 / zoom);
            pos_zoom.y = MathUtils.clamp(pos_zoom.y, Main.height / 2 / zoom, Main.height - Main.height / 2 / zoom);
        }
        if (count_gameOverCue < 0) {
            endGameOverCue();
        }
        if (mainBalls.size() != 0 && count_gameOverCue < countMax_gameOverCue * .5f) {
            Ball_Main ballToDestroy = mainBalls.get(0);
            if (ballToDestroy.height >= 0)
                ballToDestroy.explode(0, 1);
            else
                ballToDestroy.dispose();
        }
    }

    static final float ZOOMIN_SPEED = .01f;
    static final float ZOOMOUT_SPEED = .05f;

    public static void beginGameOverCue(Ball_Main ball) {
        if (!doGameOverCue) {
            for (Hole hole : holes) {
                if (hole.getClass() == Hole_Ball.class)
                    hole.dispose();
            }
            for (Ball ball_ : balls) {
                if (ball_.getClass() == Ball_Main.class && ball_ != ball)
                    ball_.dispose();
            }

            doGameOverCue = true;
            count_gameOverCue = countMax_gameOverCue;
            ultraSlow = true;

            Main.addSoundRequest(ID.Sound.SLOWDOWN);
        }
    }

    static void endGameOverCue() {
        gameOver();
        Main.addSoundRequest(ID.Sound.SPEEDUP);
        doGameOverCue = false;
        ultraSlow = false;
        pos_zoom = new Vector2(Main.width / 2, Main.height / 2);
    }

    public static Color[] getBallPalette(int level) {
        return Res.ballPalette[level % (Res.ballPalette.length)];
    }

    static boolean doFloorButtons = false;
    static boolean doSpikes = false;
    static boolean doSpikePatches = false;
    static boolean doFallHoles = false;

    void update_game() {

        float spawnSpeedFactor = 1;

        if (Main.noFlow)
            spawnSpeedFactor = 2f;

        if (!Main.inScreenShotMode) {
            if (doFloorButtons) {
                if (floorButtons.size() == 0) {
                    count_nextButton -= dt_one_slowed * spawnSpeedFactor;
                    if (count_nextButton <= 0) {
                        count_nextButton = countMax_nextButton;
                        floorButtons.add(new FloorButton());
                    }
                }
            }
        }

        if (getTotalBallSize() < 8) {
            if (Main.noFlow)
                count_nextBallSpawnPeriod -= dt_one_slowed * .5f;
            else
                count_nextBallSpawnPeriod -= dt_one_slowed;

            if (count_nextBallSpawnPeriod <= 0) {
                count_nextBall -= dt_one_slowed;

                if (count_nextBall <= 0) {
                    count_nextBall = countMax_nextBall;
                    holes.add(new Hole_Ball());
                    ballsToSpawn--;

                    if (ballsToSpawn <= 0) {
                        ballsToSpawn = ballsToSpawnMax;
                        count_nextBallSpawnPeriod = countMax_nextBallSpawnPeriod;
                    }
                }
            }
        }

        if (Main.noLevels) {
            count_nextEgg -= Main.dt_one;
            if (count_nextEgg <= 0) {
                count_nextEgg = countMax_nextEgg;
                eggs.add(new Egg());
            }
        }

        if (doFallHoles) {
            count_nextFallHole -= dt_one_slowed * spawnSpeedFactor;
            if (count_nextFallHole < 0) {
                holes.add(new Hole_Fall());
                count_nextFallHole = countMax_nextFallHole;
            }
        }

        if (doSpikes) {
            count_nextSpike -= dt_one_slowed * spawnSpeedFactor;
            if (count_nextSpike <= 0) {
                count_nextSpike = countMax_nextSpike;
                if (doSpikePatches)
                    createSpikePatch();
                else
                    spikes.add(new Spike());
            }
        }

        if (!Main.noFX) {
            collectSoundsToPlay = Math.min(collectSoundsToPlay, 4);
            if (collectSoundsToPlay > 0) {
                count_collectSound -= dt_one_slowed * spawnSpeedFactor;
                if (count_collectSound <= 0) {
                    count_collectSound = 2;
                    collectSoundsToPlay--;
                    Res.sound_collect.play(1, (float) Math.random() * .4f + .8f, 0);
                }
            }
        }
    }

    public static int getTotalBallSize() {
        int totalSize = 0;
        for (Ball_Main ball : mainBalls) {
            totalSize += Math.pow(2, ball.size);
        }
        return totalSize;
    }

    public void createSpikePatch() {
        Vector2 pos_rnd = getFreePosOnTable(12);
        if(pos_rnd!=null)
            createSpikePatch(pos_rnd.x, pos_rnd.y);
    }

    public void createSpikePatch(float x, float y) {
        int amount = 5;
        float increment = (float) Math.PI * 2f / amount;
        float ang = (float) (Math.random() * Math.PI * 2);
        spikes.add(new Spike(x, y, false));
        float r = 9;
        for (int i = 0; i < amount; i++) {
            spikes.add(new Spike(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), false));
            ang += increment;
        }
    }

    boolean first = true;
    public static float zoom = 1;
    public static final float ZOOM_MAX = 2;

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {

        buffer_trail.begin();
        //batch.setBlendFunctionSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl20.glDisable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (doClearTrail) {
            doClearTrail = false;
            sr.setColor(0, 0, 0, 0);
            sr.circle(pos_clearTrail.x, pos_clearTrail.y, 30);
            float angle;
            float dist;
            for (int i = 0; i < 5; i++) {
                angle = (float) (Math.random() * 2 * Math.PI);
                dist = (float) (Math.random() * 45);
                sr.circle(pos_clearTrail.x + (float) Math.cos(angle) * 30, pos_clearTrail.y + (float) Math.sin(angle) * 30, 30 * (float) Math.random() + 5);
            }
        }

        for (Ball ball : balls)
            ball.drawTrail(sr);
        sr.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFuncSeparate(GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.enableBlending();
        batch.setBlendFunctionSeparate(GL20.GL_ZERO, GL20.GL_ONE, GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //if((int)count%3==0) {
        batch.begin();
        batch.setShader(Res.shader_random);
        Res.shader_random.setUniformf("random0", random0);
        Res.shader_random.setUniformf("random1", random1);
        Res.shader_random.setUniformf("height", Main.height);
        batch.draw(Res.tex_random, 0, 0);
        batch.setShader(null);
        batch.end();
        //}
        //batch.setBlendFunction(GL20.GL_ONE,GL20.GL_ONE_MINUS_SRC_ALPHA);

        //sr.begin(ShapeRenderer.ShapeType.Filled);
        //sr.setColor(1, 1, 1, .01f);
        //sr.rect(0, 0, Main.width, Main.height);
        //sr.end();

        buffer_trail.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        tex_buffer_trail = buffer_trail.getColorBufferTexture();
        tex_buffer_trail.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sprite_buffer_trail.setTexture(tex_buffer_trail);
        sprite_buffer_trail.setSize(Main.width, Main.height);
        sprite_buffer_trail.setFlip(false, true);
        //sprite_buffer_trail.setAlpha(.8f);

        buffer.begin();
        Gdx.gl.glClearColor(.5f, .3f, .3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glDisable(GL20.GL_BLEND);


        batch.begin(); // table

        batch.setShader(Res.shader_palette);
        Main.setPalette(palette_table);
        batch.draw(Res.tex_tabletop, Main.width / 2 - Res.tex_tabletop.getWidth() / 2, Main.height / 2 - Res.tex_tabletop.getHeight() / 2);
        batch.setShader(null);
        setNoCamEffects();
        sprite_buffer_trail.draw(batch);
        setCamEffects();
        batch.setShader(Res.shader_tilt);
        Res.shader_tilt.setUniformf("angle", gravityAngle);
        Res.shader_tilt.setUniformf("intensity", gravityIntensity * .9f - .1f);
        batch.draw(Res.tex_tilt, 0, 0);
        batch.setShader(null);


        if (!isGameOver && !inTutorialMode && !Main.noScore) {
            Main.drawNumber(batch, score, new Vector2(Main.width / 2, (int) (Main.height * 3 / 4f)), 1);
        }
        if (inTutorialMode)
            render_tutorial(batch);

        for (FloorButton fb : floorButtons)
            fb.render(batch);
        batch.end();
        Gdx.gl20.glEnable(GL20.GL_BLEND);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(0, 0, 0, .8f);
        for (Ball ball : balls)
            ball.renderShadow(sr);
        sr.setColor(0, 0, 0, 1);
        for (Hole hole : holes)
            hole.render(sr);
        sr.setColor(point.color);
        for (Tracer tracer : tracers)
            tracer.render(sr);
        sr.setColor(0, 0, 0, 1);
        for (Particle particle : particles_sr)
            particle.render(sr);
        sr.set(ShapeRenderer.ShapeType.Filled);
        for (Ball ball : balls)
            ball.render(sr);

        sr.end();

        batch.begin();
        for (Particle particle : particles_batch)
            particle.render(batch);
        for (Spike s : spikes)
            s.render(batch);
        batch.setShader(Res.shader_palette);
        for (Ball ball : balls)
            ball.render(batch);
        batch.setShader(null);
        for (Cannon c : cannons)
            c.render(batch);
        for (Egg egg : eggs)
            egg.render(batch);
        for (Cat cat : cats)
            cat.render(batch);
        for (Pin p : pins)
            p.render(batch);
        for (Bullet b : bullets)
            b.render(batch);
        for (Orb orb : orbs)
            orb.render(batch);
        for (Honey h : honey)
            h.render(batch);
        for (Collectable c : collectables)
            c.render(batch);
        setCamNormal();
        for (Bumper bumper : bumpers)
            bumper.render(batch);
        setCamShake();
        //crown.render(batch);
        if (inTutorialMode)
            batch.draw(Res.tex_tutorialMode, 0, Main.height - 21);
        batch.end();

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if (tutorialSwipeExample != null)
            tutorialSwipeExample.render(sr);
        ballSelecter.render(sr);
        sr.end();

        if (tutorialSwipeExample != null) {
            batch.begin();
            tutorialSwipeExample.render(batch);
            batch.end();
        }
        buffer.end();
        //END SPRITE DRAWING

        tex_buffer = buffer.getColorBufferTexture();
        tex_buffer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        buffer_slow.begin();
        Gdx.gl.glClearColor(.2f, .2f, .1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (!Main.noFX) {
            batch.setShader(Res.shader_slow);
            Res.shader_slow.setUniformf("intensity", slowdown_effect * .75f);
        }
        setNoCamEffects();
        batch.draw(tex_buffer, 0, Main.height, Main.width, -Main.height);
        batch.setShader(null);
        if (!Main.inScreenShotMode)
            orbCounter.render(batch);

        batch.setShader(Res.shader_c);
        Res.shader_c.setUniformf("c", 0, 0, 0, alpha_darkOverlay);
        batch.draw(Res.tex_fullscreen, 0, 0);
        batch.setShader(null);

        setCamEffects();

        if (isPaused) {
            batch.draw(Res.tex_text_paused, Main.width / 2 - Res.tex_text_paused.getWidth() / 2, Main.height / 2);
            button_exit.render(batch);
        }
        if (!isGameOver)
            button_pause.render(batch);
        //font.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 0, Main.height);
        batch.end();

        if (gameOver.alpha > 0)
            gameOver.render(batch, sr);
        buffer_slow.end();

        tex_buffer_slow = buffer_slow.getColorBufferTexture();
        tex_buffer_slow.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Gdx.gl20.glDisable(GL20.GL_BLEND);
        batch.disableBlending();
        batch.begin();
        if (doPixelate) {
            batch.setShader(Res.shader_pixelate);
            Res.shader_pixelate.setUniformf("texDim", dim_screen);
        }
        setNoCamEffects();


        if (zoom != 1)
            batch.draw(tex_buffer_slow, -(zoom - 1) * Main.width / 2 + (-pos_zoom.x + Main.width / 2) * zoom, Main.height + (zoom - 1) * Main.height / 2 + (-pos_zoom.y + Main.height / 2) * zoom, Main.width * zoom, -Main.height * zoom);
        else
            batch.draw(tex_buffer_slow, 0, Main.height, Main.width, -Main.height);
        setCamEffects();
        batch.setShader(null);

        batch.end();
        batch.enableBlending();
        Gdx.gl20.glEnable(GL20.GL_BLEND);

        Gdx.gl20.glLineWidth(1);
        Main.b2dr.render(world, Main.cam.combined);
    }

    public void render_tutorial(SpriteBatch batch) {

        if (tutorialStage == ID.TutorialStage.SLOWDOWN) {
            Main.drawNumber(batch, (int) count_catTutorial, new Vector2(Main.width / 2, Main.height / 2 + 37), 1);
            batch.draw(Res.tex_gap, 26, 68 + Main.height / 2 - 192 / 2);
            batch.draw(Res.tex_text_slowdown, Main.width / 2 - Res.tex_text_slowdown.getWidth() / 2, 50);
            if (slowdown > 0) {
                batch.draw(Res.tex_symbol_checkmark, Main.width / 2 - Res.tex_symbol_checkmark.getWidth() / 2, 35);
            } else {
                batch.draw(Res.tex_symbol_cross, Main.width / 2 - Res.tex_symbol_cross.getWidth() / 2, 35);
            }
        }
        if (tutorialStage == ID.TutorialStage.PIN)
            batch.draw(Res.tex_tutorialHole, 0, 0);
    }

    public static void pause() {
        isPaused = true;
        button_exit.setVisibility(true);
        setDarkOverlay();
    }


    public static void unpause() {
        isPaused = false;
        button_exit.setVisibility(false);
        setNoDarkOverlay();
    }

    static int tutorialStage;

    public static void setTutorialMode() {
        inTutorialMode = true;
        tutorialStage = 0;
        setTutorial(tutorialStage);
    }

    static float count_catTutorial;
    static boolean doRestartTutorial;
    static boolean catTutorialFinished;

    void update_tutorial() {

        switch (tutorialStage) {
            case ID.TutorialStage.SLOWDOWN:
                if (mainBalls.size() > 0)
                    count_catTutorial = Math.max(0, count_catTutorial - Main.dt);
                if ((int) count_catTutorial == 0 && !catTutorialFinished) {
                    catTutorialFinished = true;
                    body_gap = destroyBody(body_gap);
                    throwConfetti(Main.width / 2, Main.height / 2 - 10);
                    transitionToNextTutorial();
                }
                break;
        }
        if (doRestartTutorial) {
            doRestartTutorial = false;
            setTutorial(tutorialStage);
        }


    }

    static Vector2 pos_tutorialBall = new Vector2();
    static boolean hasDroppedBallTutorial;
    static Body body_gap;

    static void setTutorial(int stage) {
        clear();
        float x, y;
        float ang;
        Ball ball;
        switch (stage) {
            case ID.TutorialStage.PIN:
                Vector2 pos_spike = new Vector2();
                pos_spike.set(18, 0);
                for (int i = 0; i < 20; i++) {
                    spikes.add(new Spike(pos_spike.x, pos_spike.y, true));
                    pos_spike.y += Main.height / 20;
                }
                pos_spike.set(56, 0);
                for (int i = 0; i < 20; i++) {
                    spikes.add(new Spike(pos_spike.x, pos_spike.y, true));
                    pos_spike.y += Main.height / 20;
                }
                pins.add(new Pin(38, Main.height / 2 + 30));
                ball = new Ball_Main(37.5f, 30, Main.height, 1, 0).dontSplit();
                ball.setDrainSpeed();
                balls.add(ball);
                pos_tutorialBall.set(37.5f, 30);
                tutorialSwipeExample = new TutorialSwipeExample();
                break;
/*
            case ID.TutorialStage.MOREPINS:
                x = Main.width / 2;
                y = Main.height / 2-20;
                float dx=30*(float) Math.cos(Math.PI * 1 / 3f);
                float dy=30*(float) Math.sin(Math.PI * 1 / 3f);
                pins.add(new Pin(x, y));
                pins.add(new Pin(x+dx, y+dy));
                pins.add(new Pin(x-dx, y+dy));
                pins.add(new Pin(x+dx*2, y+dy*2));
                pins.add(new Pin(x-dx*2, y+dy*2));
                pins.add(new Pin(x, y+dy*2));
                balls.add(new Ball_Main(Main.width / 2, 30, 0, 0, 0));
                break;
                */
            case ID.TutorialStage.MOREPINS:
                x = Main.width / 2;
                y = Main.height / 2 - 20;
                for (int i = 0; i < 8; i++) {
                    ang = i / 8f * (float) Math.PI * 2;
                    pins.add(new Pin(x + 40 * (float) Math.cos(ang), y + 40 * (float) Math.sin(ang)));
                }
                pos_tutorialBall.set(x, y);
                ball = new Ball_Main(x, y, Main.height, 0, 0);
                ball.setDrainSpeed();
                balls.add(ball);
                tutorialSwipeExample = null;
                break;
            case ID.TutorialStage.MERGE:
                x = Main.width / 2;
                y = Main.height / 2;
                balls.add(new Ball_Main(x - 30, y, Main.height, 1, 0).setDrainSpeed());
                balls.add(new Ball_Main(x + 30, y, Main.height, 2, 0).setDrainSpeed());
                //balls.add(new Ball_Main(x + 30 * (float) Math.cos(Math.PI * 1 / 3f), y + 30 * (float) Math.sin(Math.PI * 1 / 3f), Main.height, 0, 0));
                balls.add(new Ball_Main(x, y - 40, Main.height, 0, 0).setDrainSpeed());
                hasDroppedBallTutorial = false;
                break;
/*
            case ID.TutorialStage.SLOWDOWN:
                for (int i = 0; i < 12; i++) {
                    x = Main.width / 11 * i;
                    spikes.add(new Spike(x, Main.height / 2 - 40, true));
                }

                Vector2 pos_badBalls = new Vector2();
                Vector2 pos_cat = new Vector2(Main.width / 2, Main.height / 2 + 10);
                float dst = 80;
                float speed = 1;
                ang = 0;
                int amount_badBalls = 4;
                Ball badBall;
                for (int i = 0; i < amount_badBalls; i++) {
                    pos_badBalls.set(pos_cat.x + (float) Math.cos(ang) * dst, pos_cat.y + (float) Math.sin(ang) * dst);
                    badBall = new Ball_Bad(pos_badBalls.x, pos_badBalls.y, 0, 0);
                    badBall.body.setLinearVelocity(-(float) Math.cos(ang) * speed, -(float) Math.sin(ang) * speed);
                    badBall.ignoreWalls();
                    badBall.lock();
                    balls.add(badBall);
                    ang += Math.PI / (amount_badBalls - 1);
                }
                x = Main.width / 2;
                y = 30;
                pos_tutorialBall.set(x, y);
                balls.add(new Ball_Main(x, y, 0, 1, 0).setDrainSpeed().dontSplit());
                cats.add(new Cat(pos_cat.x, pos_cat.y));
                count_catTutorial = 5;
                catTutorialFinished = false;
                break;
                */
            case ID.TutorialStage.SLOWDOWN:

                System.out.println("SLOWDOWN LEVEL BUILD");

                body_gap = destroyBody(body_gap);

                body_gap = world.createBody(Res.bodyDef_static);
                body_gap.createFixture(Res.fixtureDef_gap);
                body_gap.setTransform(0, (Main.height / 2 - 192 / 2) * Main.METERSPERPIXEL, 0);

                System.out.println("CREATE GAP");

                Vector2 pos_badBalls = new Vector2();
                Vector2 pos_cat = new Vector2(Main.width / 2, Main.height / 2);
                float dst = 80;
                float speed = 1;
                ang = 0;
                int amount_badBalls = 6;
                Ball badBall;
                for (int i = 0; i < amount_badBalls; i++) {
                    pos_badBalls.set(pos_cat.x + (float) Math.cos(ang) * dst, pos_cat.y + (float) Math.sin(ang) * dst);
                    badBall = new Ball_Bad(pos_badBalls.x, pos_badBalls.y, 0, 0);
                    badBall.body.setLinearVelocity(-(float) Math.cos(ang) * speed, -(float) Math.sin(ang) * speed);
                    badBall.ignoreWalls();
                    badBall.lock();
                    balls.add(badBall);
                    ang += Math.PI * 2 / (amount_badBalls);
                }
                pos_tutorialBall.set(pos_cat.x, pos_cat.y);
                balls.add(new Ball_Main(pos_cat.x, pos_cat.y, 0, 1, 0).setDrainSpeed().dontSplit());
                count_catTutorial = 5;
                catTutorialFinished = false;
                break;
        }
    }

    static boolean doNextTutorialLevel;

    public static void onFadePeak() {
        if (inTutorialMode) {
            if (doNextTutorialLevel) {
                doNextTutorialLevel = false;
                doNextTutorial = true;
                resetLevel();
            } else {
                setTutorial(tutorialStage);
            }
        }
        if (doReplay) {
            doReplay = false;
            replay();
            showInterstitialWithChance();
        }
    }

    public static void showInterstitialWithChance() {
        if (!Main.noAds)
            if ((int) (Math.random() * 1) == 0)
                Main.amm.showInterstitial();
    }

    public static void onBallCollide() {
        if (inTutorialMode) {
            if (!hasDroppedBallTutorial && tutorialStage == ID.TutorialStage.MERGE) {
                hasDroppedBallTutorial = true;
                balls.add(new Ball_Main(Main.width / 2, Main.height / 2 + 30, Main.height, 0, 0).setDrainSpeed());
            }
        }
    }


    public static void onBallMerge() {
        if (inTutorialMode) {
            switch (tutorialStage) {
                case ID.TutorialStage.PIN:
                    break;
                case ID.TutorialStage.MOREPINS:
                    break;
                case ID.TutorialStage.MERGE:

                    if (ballsToAdd.get(ballsToAdd.size() - 1).size == 0) {
                        transitionToNextTutorial();
                        for (Ball ball : ballsToAdd)
                            ball.dispose();
                        ballsToAdd.clear();
                        break;
                    }
                    /*
                    ballsToAdd.add(new Ball_Main((float) Math.random() * 108, (float) Math.random() * 192, Main.height, ballsToAdd.get(ballsToAdd.size() - 1).size, 0).setDrainSpeed());
                   */
                    break;
                default:

            }
        }
    }

    static void setNextTutorial() {
        tutorialStage++;
        if (tutorialStage > 3) {
            endTutorial();
            return;
        }
        setTutorial(tutorialStage);
    }

    public static void endTutorial() {
        clear();
        tutorialStage = 0;
        inTutorialMode = false;
        body_gap = destroyBody(body_gap);
        Main.setSceneMenuNow();
    }

    public static void onMainBallDestroyed() {

        if (inTutorialMode) {
            switch (tutorialStage) {
                case ID.TutorialStage.PIN:
                    balls.add(new Ball_Main(pos_tutorialBall.x, pos_tutorialBall.y, Main.height, 1, 0).setDrainSpeed().dontSplit());
                    break;
                case ID.TutorialStage.MOREPINS:
                    break;
                case ID.TutorialStage.MERGE:

                    break;
                case ID.TutorialStage.SLOWDOWN:
                    if (!catTutorialFinished) {
                        if (inTutorialMode) {
                            Main.startFade();
                            System.out.println("STARTFADE ONBALLDESTROYED");
                        }
                        Main.shake();
                    }
                    break;
            }
        }
    }

    public static void transitionToNextTutorial() {
        doNextTutorialLevel = true;
        Main.startFade();
    }

    public static void setHardMode() {
        //worldStepFactor=5f;
    }

    public void setDoWiggle(boolean b) {
        doWiggle = b;
    }

    public static void onPinDestroyed() {
        if (inTutorialMode) {
            switch (tutorialStage) {
                case ID.TutorialStage.PIN:
                    transitionToNextTutorial();
                    break;
                case ID.TutorialStage.MOREPINS:
                    if (pins.size() == 1)
                        transitionToNextTutorial();
                    break;
                case ID.TutorialStage.MERGE:
                    break;
            }
        }
    }

    public static void onCatHit() {
        doRestartTutorial = true;
        Main.shake();
    }

    static void clear() {
        for (Ball ball : balls) {
            ball.dispose();
        }
        balls.clear();

        for (Egg egg : eggs) {
            egg.dispose();
        }
        eggs.clear();

        for (Spike spike : spikes) {
            spike.dispose();
        }
        spikes.clear();

        for (Particle p : particles) {
            p.dispose();
        }
        particles.clear();

        for (Cat cat : cats) {
            cat.dispose();
        }
        cats.clear();

        for (Cannon c : cannons) {
            c.dispose();
        }

        for (Honey h : honey) {
            h.dispose();
        }

        mainBalls.clear();
        System.out.println("bodies: " + world.getBodyCount());
        bumpers.clear();
        floorButtons.clear();
        holes.clear();
    }

    public static void replay() {
        reset();
        gameOver.hide();
        isGameOver = false;
        count_hole = 0;
    }

    public static void reset() {
        clear();
        resetLevel();
        ball_king = null;
        setup();
    }

    public static void nextLevel() {
        level++;
        if (!Main.noLevels)
            setMusic(Res.music[level % 5]);
        changeTableColor();

        onNextLevel();

        if (!Main.inScreenShotMode && !Main.noLevels) {
            int eggsToDrop = level;
            float x = Main.width / 2;
            float y = Main.height / 2;
            float increment = (float) Math.PI * 2f / Math.min(9, eggsToDrop);
            float ang = (float) (Math.random() * Math.PI * 2);
            float r = 20;
            int eggsDropped = 0;
            int i = 0;
            while (eggsDropped < 9 && eggsDropped < eggsToDrop) {
                eggs.add(new Egg(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
                ang += increment;
                eggsDropped++;
                i++;
            }
            increment = (float) Math.PI * 2f / (eggsToDrop - eggsDropped);
            ang = (float) (Math.random() * Math.PI * 2);
            r = 32;

            while (eggsDropped < eggsToDrop) {
                eggs.add(new Egg(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
                ang += increment;
                eggsDropped++;
                i++;
            }
        }
    }

    public static void createEggRing() {
        int eggsToDrop = 7;
        float x = Main.width / 2;
        float y = Main.height / 2;
        float increment = (float) Math.PI * 2f / Math.min(9, eggsToDrop);
        float ang = (float) (Math.random() * Math.PI * 2);
        float r = 20;
        int eggsDropped = 0;
        int i = 0;
        while (eggsDropped < 9 && eggsDropped < eggsToDrop) {
            eggs.add(new Egg(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
            ang += increment;
            eggsDropped++;
            i++;
        }
        increment = (float) Math.PI * 2f / (eggsToDrop - eggsDropped);
        ang = (float) (Math.random() * Math.PI * 2);
        r = 32;

        while (eggsDropped < eggsToDrop) {
            eggs.add(new Egg(x + r * (float) Math.cos(ang), y + r * (float) Math.sin(ang), Main.height + 50 * i));
            ang += increment;
            eggsDropped++;
            i++;
        }
    }

    public static void onNextLevel() {

        bumpers.clear();

        doFloorButtons = false;
        doSpikes = false;
        doSpikePatches = false;
        doFallHoles = false;

        float harderFactor = (1 + .5f * level);

        if (!Main.noLevels)
            switch (level) {
                case 0:
                    doSpikes = true;
                    countMax_nextSpike = 800;
                    countMax_nextBallSpawnPeriod = 150;

                    if (Main.inScreenShotMode) {
                        doSpikes = false;
                        //doSpikePatches = true;
                        doFloorButtons = true;
                        //doFallHoles = true;
                        countMax_nextSpike = 400 / harderFactor;
                        countMax_nextButton = (int) (500 / harderFactor);
                        countMax_nextBallSpawnPeriod = 400;
                        countMax_nextFallHole = (int) (200 / harderFactor);
                    }
                    break;
                case 1:
                    doFloorButtons = true;
                    doSpikes = true;
                    countMax_nextSpike = 800;
                    countMax_nextButton = 500;
                    countMax_nextBallSpawnPeriod = 350;
                    break;
                case 2:
                    doFallHoles = true;
                    countMax_nextBallSpawnPeriod = 500;
                    countMax_nextFallHole = 300;
                    break;
                case 3:
                    doSpikes = true;
                    doSpikePatches = true;
                    //doFallHoles = true;
                    countMax_nextSpike = 200;
                    //countMax_nextFallHole = 350;
                    spawnBumperOnRandomWall();

                    break;
                default:

                    //END GAME
                    int reducedLevel = level % 4;

                    switch (reducedLevel) {
                        case 0:
                            doSpikes = true;
                            countMax_nextSpike = 400 / harderFactor;
                            countMax_nextBallSpawnPeriod = 150;

                            doFallHoles = true;
                            countMax_nextFallHole = (int) (200 / harderFactor);
                            break;
                        case 1:
                            doFloorButtons = true;
                            doSpikes = true;
                            countMax_nextSpike = 400 / harderFactor;
                            countMax_nextButton = (int) (500 / harderFactor);
                            countMax_nextBallSpawnPeriod = 350;
                            spawnBumperOnRandomWall();
                            break;
                        case 2:
                            doSpikes = true;
                            doFloorButtons = true;
                            doFallHoles = true;
                            countMax_nextSpike = 400 / harderFactor;
                            countMax_nextButton = (int) (500 / harderFactor);
                            countMax_nextBallSpawnPeriod = 400;
                            countMax_nextFallHole = (int) (200 / harderFactor);
                            break;
                        case 3:
                            doSpikes = true;
                            doSpikePatches = true;
                            doFallHoles = true;
                            countMax_nextSpike = (int) (400 / harderFactor);
                            countMax_nextFallHole = (int) (200 / harderFactor);
                            countMax_nextBallSpawnPeriod = 400;
                    }
            }

        if (Main.noLevels) {
            countMax_nextSpike = 500 / harderFactor;
            countMax_nextButton = (int) (500 / harderFactor);
            countMax_nextBallSpawnPeriod = (int) (50 * harderFactor);
            countMax_nextFallHole = (int) (1000 / harderFactor);
            doFloorButtons = true;
            doFallHoles = true;
            doSpikes = true;
        }
    }

    public static void spawnBumperOnRandomWall() {
        bumpers.add(new Bumper((int) (Math.random() * 4)));
    }

    public static void resetLevel() {
        level = 0;
        changeTableColor();
    }

    public static void setDarkOverlay() {
        doDarkOverlay = true;
    }

    public static void setNoDarkOverlay() {
        doDarkOverlay = false;
    }

    public static void throwConfetti(float x, float y) {
        if (!Main.noFX) {
            float ang;
            float impact;
            for (int i = 0; i < 10; i++) {
                ang = (float) (Math.random() * 2 * Math.PI);
                impact = (float) (Math.random() * 5);
                particles.add(new Particle_Confetti(x, y, impact * (float) Math.cos(ang), impact * (float) Math.sin(ang)));
            }
        }
    }

    static Vector3[] palette_dir_table;

    public static void changeTableColor() {
        if (!Main.noLevels) {
            changeTableColor = true;
            palette_target_table = getRandomTableColor();
            for (int i = 0; i < 4; i++) {
                palette_dir_table[i].set(palette_target_table[i].r, palette_target_table[i].g, palette_target_table[i].b);
                palette_dir_table[i].add(-palette_table[i].r, -palette_table[i].g, -palette_table[i].b);
                palette_dir_table[i].scl(.01f);
            }
        }
    }

    static float colorPoint = 1;

    public static Color[] getRandomTableColor() {
        Color[] palette = new Color[4];
        colorPoint = getNewColorPoint();
        float r = getColorProx(0, colorPoint);
        float g = getColorProx(1, colorPoint);
        float b = getColorProx(2, colorPoint);
        float baseWhiteness = .5f;
        float colorMul = .4f;
        Color color_primary = new Color(baseWhiteness + r * colorMul, baseWhiteness + g * colorMul, baseWhiteness + b * colorMul, 1);
        r = getColorProx(0, colorPoint + .2f);
        g = getColorProx(1, colorPoint + .2f);
        b = getColorProx(2, colorPoint + .2f);
        float darkMul = .8f;
        Color color_secondary = new Color(baseWhiteness + r * colorMul, baseWhiteness + g * colorMul, baseWhiteness + b * colorMul, 1);
        color_secondary.mul(darkMul);
        palette[0] = color_secondary;
        palette[1] = color_primary;
        palette[2] = color_secondary;
        palette[3] = color_secondary;
        return palette;
    }

    static float getColorProx(float from, float to) {
        float dst = Math.abs(to - from);
        float prox = Math.max(0, 1 - dst);
        if (from < .5f && prox <= 0) {
            from = 3 + from;
            dst = Math.abs(to - from);
            prox = Math.max(0, 1 - dst);
        } else if (from > 2.5f && prox <= 0) {
            from = 3 - from;
            dst = Math.abs(to - from);
            prox = Math.max(0, 1 - dst);
        }
        return prox;
    }

    static float getNewColorPoint() {
        float prev = colorPoint;
        while (getColorProx(prev, colorPoint) > .5f) {
            colorPoint = (float) Math.random() * 3;
        }
        return colorPoint;
    }

    public static Vector2 getRandomPosOnTable(float width, float height) {
        return new Vector2((int) (width / 2 + Math.random() * (Main.width - width)), (int) (height / 2 + Math.random() * (Main.height - height)));
    }

    public static Vector2 getRandomPosOnTable(float radius) {
        return new Vector2((int) (radius + Math.random() * (Main.width - radius * 2)), (int) (radius + Math.random() * (Main.height - radius * 2)));
    }

    public static Vector2 getFreePosOnTable(float radius) {
        int tries = 0;
        Vector2 pos = null;
        outer:
        while (tries < 5) {
            tries++;
            pos = getRandomPosOnTable(radius);
            for (Hole hole : holes) {
                if (Main.distanceBetweenPoints(pos, hole.pos) < radius + hole.radiusMax)
                    continue outer;
            }
            for (Ball ball : balls) {
                if (Main.distanceBetweenPoints(pos, ball.pos) < radius + ball.radius)
                    continue outer;
            }
            for (Spike spike : spikes) {
                if (Main.distanceBetweenPoints(pos, spike.pos) < radius + spike.radius) {
                    continue outer;
                }
            }
            break;
        }
        if (tries >= 5)
            return null;
        return pos;
    }

    static void setup() {
        Main.userData.gamesPlayed++;

        Main.setAdVisibility(false);

        onNextLevel();

        orbsCollected = 0;
        score = 0;
        level = 0;
        count_nextBallSpawnPeriod = 0;
        count_nextBall = 0;
        ballsToSpawn = ballsToSpawnMax;
        count_nextSpike = 400;

        body_gap = destroyBody(body_gap);

        setMusic(Res.music[0]);
    }


    public static void gameOver() {
        if (!inTutorialMode) {
            if (score > gameData.highscore) {
                gameData.highscore = score;
                if (!Main.noScore)
                    Main.gm.submitScore(score);
            }
            Main.setAdVisibility(true);
            isGameOver = true;
            gameOver.show();
            if (Main.shop.canAffordSomething())
                gameOver.button_balls.setNew();
            Main.setNoMusic();
        }
    }

    void createWorld() {
        world = new World(new Vector2(0, 0), true);
        border = world.createBody(Res.bodyDef_static);
        border.createFixture(Res.fixtureDef_border);
    }

    @Override
    public void dispose() {
        buffer.dispose();
        buffer_table.dispose();
        buffer_trail.dispose();
        buffer_slow.dispose();
    }

    public static void clearTrail(float x, float y) {
        pos_clearTrail.set(x, y);
        doClearTrail = true;
    }


    public static Body destroyBody(Body body) {
        if (body != null) {
            int max = body.getFixtureList().size;
            for (int i = 0; i < max; i++) {
                body.destroyFixture(body.getFixtureList().get(0));
            }
            world.destroyBody(body);
        }
        return null;
    }


    class B2DContactListener implements ContactListener {
        @Override
        public void beginContact(Contact contact) {
            Object udA = contact.getFixtureA().getBody().getUserData();
            Object udB = contact.getFixtureB().getBody().getUserData();

            Class classA = getClass(udA);
            Class classB = getClass(udB);

            if (classA == Ball_Main.class && classB == Ball_Main.class) {
                Ball ballA = (Ball) udA;
                Ball ballB = (Ball) udB;
                ballA.contactBall(ballB);
                doOnBallCollide = true;
            }
            if (classA == Ball_Bad.class && classB == Ball_Bad.class) {
                Ball_Bad ball_badA = (Ball_Bad) udB;
                Ball_Bad ball_badB = (Ball_Bad) udA;
                ball_badB.contactBallBad(ball_badA);
                ball_badA.contactBallBad(ball_badB);
            }
            for (int i = 0; i < 2; i++) {
                if (udA instanceof Ball) {
                    Ball ball = (Ball) udA;

                    if (classA == Ball_Main.class) {
                        if (classB == Ball_Bad.class) {
                            Ball_Bad ball_bad = (Ball_Bad) udB;
                            ball_bad.contactBall(ball);
                        } else if (classB == Egg.class) {
                            Egg egg = (Egg) udB;
                            egg.destroy(ball);
                        } else if (classB == Pin.class) {
                            Pin pin = (Pin) udB;
                            pin.destroy(ball);
                        } else if (classB == Bullet.class) {
                            Bullet bullet = (Bullet) udB;
                            bullet.hit(ball);
                        } else if (udB instanceof Collectable) {
                            Collectable c = (Collectable) udB;
                            c.pickUp((Ball)udA);
                            return; // So Ball.contact isn't called and no hit sound is played
                        }
                    }

                    ball.contact(udB, contact.getWorldManifold().getPoints()[0], getHypothenuse(ball.body.getLinearVelocity().x, ball.body.getLinearVelocity().y));
                }

                if (classA == Spike.class && (classB == Ball_Main.class || classB == Ball_Bad.class)) {
                    Spike spike = (Spike) udA;
                    spike.hitBall((Ball) udB);
                }
                if (classA == Cat.class && classB == Ball_Bad.class) {
                    onCatHit();
                }
                if (classA == Bullet.class) {
                    Bullet bullet = (Bullet) udA;
                    bullet.doDispose = true;
                }

                udA = contact.getFixtureB().getBody().getUserData();
                udB = contact.getFixtureA().getBody().getUserData();
                classA = getClass(udA);
                classB = getClass(udB);
            }
        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            Object udA = contact.getFixtureA().getBody().getUserData();
            Object udB = contact.getFixtureB().getBody().getUserData();

            for (int i = 0; i < 2; i++) {
                if (udA instanceof Ball) {
                    Ball ball = (Ball) udA;
                    Main.shake(MathUtils.clamp(.2f * Main.getHypothenuse(ball.body.getLinearVelocity().y, ball.body.getLinearVelocity().x), 0, 4));
                    Main.setShakeAng((float) Math.atan2(contact.getFixtureA().getBody().getLinearVelocity().y, contact.getFixtureA().getBody().getLinearVelocity().x));
                }
                udA = contact.getFixtureB().getBody().getUserData();
                udB = contact.getFixtureA().getBody().getUserData();
            }
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }

        Class getClass(Object ud) {
            if (ud != null)
                return ud.getClass();
            else
                return null;
        }

        Class getSuperClass(Class c) {
            if (c != null)
                return c.getSuperclass();
            else
                return null;
        }
    }

    public void createInputProcessor() {
        inputProcessor = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                //point.onRelease();
                ballSelecter.onRelease();
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
        Gdx.input.setInputProcessor(inputProcessor);
    }
}
