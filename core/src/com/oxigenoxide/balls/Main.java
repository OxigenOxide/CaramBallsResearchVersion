package com.oxigenoxide.balls;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.oxigenoxide.balls.objects.SoundRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Main extends ApplicationAdapter {
    static SpriteBatch batch;
    static Game game;
    static Scene menu;
    static Scene welcome;
    static Scene splash;
    static GameOver gameOver;
    static Scene currentScene;
    static Scene overlayScene;
    public static Scene nextScene;
    public static Box2DDebugRenderer b2dr;
    public static OrthographicCamera cam;
    public static ShapeRenderer sr;
    public static float width, height;
    public static Vector2[] tap;
    public static Vector2[] tap_previous;
    public static Vector2[] tap_delta;
    public static Vector2[] tap_begin;
    public static Vector2 pos_middle;
    public static float[] tap_distance;
    public static float tap_speed;
    public static float tap_angle;
    public static float[] speedDistanceLastFrames;
    public static float[] speedTimeLastFrames;
    public static float[] directionLastFrames;
    public static final int TAPSPEEDPRECISION = 10;
    public static final int TAPANGLEPRECISION = 5;
    public static Vector2 pos_cam;
    public static ArrayList<SoundRequest> soundRequests;
    public static SoundRequest[] soundRequestsToPlay;
    public static float dt_one;
    public static float dt;
    public static final float PIXELSPERMETER = 40;
    //public static final float PIXELSPERMETER = 1;

    public static final float METERSPERPIXEL = 1 / PIXELSPERMETER;
    public static float pointsPerPixel;
    public static float pixelsPerPoint;
    static Music currentMusic;
    static Music lastMusic;
    public static AssetManager assets;
    public static FirebaseInterface fbm;
    public static GameInterface gm;
    public static AdMobInterface amm;
    public static boolean resourcesLoaded;
    public static boolean signedIn;
    static boolean inGame;
    public static int testerID = 0;
    public static Shop shop;
    static boolean isLoaded;

    public static final int adHeight = 17;

    public static boolean noAds = false; // DONE
    public static boolean noFX = false; // DONE
    public static boolean noMusic = false; // DONE
    public static boolean noCollection = false; // DONE
    public static boolean noLevels = false; // DONE
    public static boolean noFlow = false; // DONE
    public static boolean noScore = false; // DONE

    public static boolean doFade;
    public static boolean inScreenShotMode;

    public static float shakeAng;
    public static float fadeDir;
    static float fade;

    public static Res res;

    static float shakeIntensity;

    public static UserData userData;

    public static GameData gameData;

    long startTime;

    public static final int SOUNDCAP = 2;
    public static final boolean RESEARCHMODE = true;
    public static final boolean DOSCREENSHOTMODE = false;

 /*
    Every time you release check this:
        - DEBUGTOOLS in Game should be false
        - RESEARCHMODE should be false
        - DOSCREENSHOTMODE should be false
*/
    // GET RELEASE KEY: keytool -list -v -keystore ‪C:\Keys\googlekeys.jks -alias key_ball (Release key is pretty useless)

    public Main(FirebaseInterface fbm, AdMobInterface amm, GameInterface gm) {
        super();
        resetStatics();
        this.fbm = fbm;
        this.amm = amm;
        this.gm = gm;
    }

    void resetStatics() {
        isLoaded = false;
        resourcesLoaded = false;
        userData = null;
        signedIn = false;
    }

    long rank;

    @Override
    public void create() {
        fbm.signIn();
        assets = new AssetManager();
        width = 108;
        height = Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth() * 108;
        pos_middle = new Vector2(width / 2, height / 2);
        soundRequests = new ArrayList<SoundRequest>();
        soundRequestsToPlay = new SoundRequest[SOUNDCAP];

        pointsPerPixel = Gdx.graphics.getWidth() / width;
        pixelsPerPoint = 1 / pointsPerPixel;

        batch = new SpriteBatch();

        cam = new OrthographicCamera(width, height);
        cam.position.set(width / 2, height / 2, 0);
        b2dr = new Box2DDebugRenderer();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);

        //game = new Game();
        menu = new Menu();
        splash = new Splash();

        tap = new Vector2[]{new Vector2(), new Vector2()};
        tap_previous = new Vector2[]{new Vector2(), new Vector2()};
        tap_delta = new Vector2[]{new Vector2(), new Vector2()};
        tap_begin = new Vector2[]{new Vector2(), new Vector2()};
        tap_distance = new float[2];

        pos_cam = new Vector2();
        pos_cam.set(width / 2, height / 2);

        speedDistanceLastFrames = new float[TAPSPEEDPRECISION];
        speedTimeLastFrames = new float[TAPSPEEDPRECISION];
        directionLastFrames = new float[TAPANGLEPRECISION];

        ShaderProgram.pedantic = false;

        setScene(splash);

        DataManager.getInstance().initializeGameData();
        gameData = DataManager.getInstance().gameData;

        startTime = System.currentTimeMillis();

        testerID = gameData.testerID;

        rank = gm.getRank();
    }

    public static void setScreenShotMode() {
        game.setDoWiggle(false);
        inScreenShotMode = true;
    }

    public void update() {

        dt = Gdx.graphics.getDeltaTime();
        dt_one = dt * 60;

        for (int i = 0; i < tap.length; i++) {
            tap[i].set(((Gdx.input.getX(i) / 4f) * (width / (Gdx.graphics.getWidth() / 4f))),
                    ((-(Gdx.input.getY(i) / 4f - Gdx.graphics.getHeight() / 4f) * (height / (Gdx.graphics.getHeight() / 4f)))));

            tap_delta[i].set(tap[i].x - tap_previous[i].x, tap[i].y - tap_previous[i].y);
            tap_distance[i] = getHypothenuse(tap_delta[i].x, tap_delta[i].y);
        }
        tap_angle = angleBetweenPoints(tap_previous[0], tap[0]);

        if(Gdx.input.justTouched())
            for (int i = 0; i < tap.length; i++)
                tap_begin[i].set(tap[i]);

        for (int i = 0; i < tap.length; i++) {
            tap_previous[i].set(tap[i]);
        }

        for (int i = 0; i < TAPSPEEDPRECISION - 1; i++) {
            speedDistanceLastFrames[i] = speedDistanceLastFrames[i + 1];
            speedTimeLastFrames[i] = speedTimeLastFrames[i + 1];
        }
        for (int i = 0; i < TAPANGLEPRECISION - 1; i++) {
            directionLastFrames[i] = directionLastFrames[i + 1];
        }

        speedDistanceLastFrames[TAPSPEEDPRECISION - 1] = tap_distance[0];
        speedTimeLastFrames[TAPSPEEDPRECISION - 1] = dt_one;
        directionLastFrames[TAPANGLEPRECISION - 1] = tap_delta[0].angleRad();

        tap_speed = getSum(speedDistanceLastFrames) / getSum(speedTimeLastFrames);


        currentScene.update();
        if (overlayScene != null)
            overlayScene.update();

        if (!signedIn && fbm.isSignedIn() && fbm.isSnapshotLoaded()) {
            signedIn = true;
            onSignIn();
        }
        if (signedIn && !fbm.isSignedIn()) {
            signedIn = false;
        }

        shakeIntensity = Math.max(0, shakeIntensity - .1f);

        if (doFade) {
            fade += fadeDir * .03f;
            //fade += fadeDir * .0005f;
            if (fade >= 2.2f) {
                fadeDir = -1;
                onFadePeak();
            }
            if (fade <= 0)
                doFade = false;
        }


        int index = 0;
        for (SoundRequest sr : soundRequests) {
            soundRequestsToPlay[index] = sr;
            index++;
            if (index >= SOUNDCAP)
                break;
        }

        if (soundRequests.size() > SOUNDCAP) {
            for (SoundRequest sr : soundRequests) {
                if (soundRequestsToPlay[0] != null && sr.priority > soundRequestsToPlay[0].priority) {
                    soundRequestsToPlay[1] = soundRequestsToPlay[0];
                    soundRequestsToPlay[0] = sr;
                } else if (soundRequestsToPlay[1] != null && sr.priority > soundRequestsToPlay[1].priority)
                    soundRequestsToPlay[1] = sr;
            }
        }
        for (SoundRequest sr : soundRequestsToPlay) {
            if (sr != null)
                playSound(sr.soundID, sr.volume);
        }
        for (int i = 0; i < SOUNDCAP; i++) {
            soundRequestsToPlay[i] = null;
        }
        soundRequests.clear();

        // Music
        if (currentMusic != null)
            currentMusic.setVolume(Math.min(1, currentMusic.getVolume() + .01f));
        if (lastMusic != null) {
            lastMusic.setVolume(Math.max(0, lastMusic.getVolume() - .01f));
            if (lastMusic.getVolume() == 0) {
                lastMusic.stop();
                lastMusic = null;
            }
        }
    }

    public static void addSoundRequest(int soundID, int priority, float volume, float pitch) {
        if (!noFX)
            soundRequests.add(new SoundRequest(soundID, priority, volume, pitch));
    }

    public static void addSoundRequest(int soundID, int priority) {
        if (!noFX)
            soundRequests.add(new SoundRequest(soundID, priority));
    }

    public static void addSoundRequest(int soundID) {
        if (!noFX)
            soundRequests.add(new SoundRequest(soundID));
    }

    public static void setMusic(Music music) {
        if (!Main.noMusic) {
            if (lastMusic != null)
                lastMusic.stop();
            lastMusic = currentMusic;
            currentMusic = music;
            currentMusic.setVolume(0);
            currentMusic.setLooping(true);
            currentMusic.play();
        }
    }

    public static void setNoMusic() {
        if (!Main.noMusic) {
            if (lastMusic != null)
                lastMusic.stop();
            lastMusic = currentMusic;
            currentMusic = null;
        }
    }

    public static void shake() {
        if (!noFX && !inScreenShotMode)
            if (shakeIntensity < 3)
                shakeIntensity = 3;
    }

    public static void shake(float impact) {
        if (!noFX && !inScreenShotMode)
            if (impact > shakeIntensity && impact > 2)
                shakeIntensity = impact;
    }

    public static void shakeSmall() {
        if (!noFX)
            if (shakeIntensity < 2)
                shakeIntensity = 2;
    }

    public static void setShakeAng(float ang) {
        //shakeAng=(float)Math.PI*.5f;
        //shakeAng=(float)Math.PI*.5f;
        //shakeAng=(float)Math.toDegrees(ang);

        shakeAng = ang;
    }


    @Override
    public void render() {
        update();
        //cam.position.set(pos_cam.x + (int) (shakeIntensity * Math.sin(shakeIntensity)),pos_cam.y + (int) (shakeIntensity * Math.sin(shakeIntensity)),0);
        //cam.update();
        //batch.setProjectionMatrix(cam.combined);
        //sr.setProjectionMatrix(cam.combined);
        setCamEffects();
        currentScene.render(batch, sr);
        if (overlayScene != null)
            overlayScene.render(batch, sr);
        setNoCamEffects();
        if (resourcesLoaded) {
            batch.begin();
            if (!inScreenShotMode)
                res.sprite_watermark.draw(batch);
            if (fade > 0) {
                batch.setShader(Res.shader_fade);
                Res.shader_fade.setUniformf("fade", (float) Math.min(fade, 2f));
                Res.shader_fade.setUniformf("screenSize", width, height);
                Res.shader_fade.setUniformf("dir", fadeDir);
                batch.draw(Res.tex_fade, 0, 0);
                batch.setShader(null);
            }
            batch.end();
        }
    }

    public static void setCamEffects() {
        //setCamZoom();
        setCamShake();
    }

    public static void setNoCamEffects() {
        setCamNoZoom();
        setCamNormal();
    }

    public static void setCamZoom() {
        cam.zoom = .5f;
    }

    public static void setCamNoZoom() {
        cam.zoom = 1;
    }

    public static void setCamShake() {
        cam.position.set(pos_cam.x + (int) (Math.cos(shakeAng) * shakeIntensity * Math.sin(shakeIntensity * 10)), pos_cam.y + (int) (Math.sin(shakeAng) * shakeIntensity * Math.sin(shakeIntensity * 10)), 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);
    }

    public static void setCamNormal() {
        cam.position.set(pos_cam.x, pos_cam.y, 0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        sr.setProjectionMatrix(cam.combined);
    }

    public static int drawNumberSign(SpriteBatch batch, int number, Vector2 pos, int font, Texture tex_sign, int yDisposition) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getWidth() + 1;
        }
        width += tex_sign.getWidth() + 2;
        int textWidth = width;
        width--;
        int iWidth = 0;
        batch.draw(tex_sign, pos.x - width / 2 + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getWidth() + 2;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
        return textWidth;
    }

    public static ArrayList<Integer> getDigits(int number) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        return digits;
    }

    public static int getTextWidth(ArrayList<Integer> digits, int font) {
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getWidth() + 1;
        }
        width--;
        return width;
    }

    public static void drawNumberSign(SpriteBatch batch, ArrayList<Integer> digits, Vector2 pos, int font, Texture tex_sign, int yDisposition) {
        int iWidth = 0;
        batch.draw(tex_sign, pos.x + iWidth, pos.y + yDisposition);
        iWidth += tex_sign.getWidth() + 2;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
    }

    public static void drawNumber(SpriteBatch batch, ArrayList<Integer> digits, Vector2 pos, int font) {
        int iWidth = 0;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
    }

    public static void drawNumber(SpriteBatch batch, int number, Vector2 pos, int font) {
        int digitAmount = 0;
        ArrayList<Integer> digits = new ArrayList<Integer>();
        int power = 0;
        while (number >= Math.pow(10, power)) {
            digitAmount++;
            power++;
        }
        if (number == 0) {
            digitAmount = 1;
        }
        int crunchNumber = number;
        for (int i = digitAmount - 1; i >= 0; i--) {
            digits.add((int) (crunchNumber / Math.pow(10, i)));
            crunchNumber %= Math.pow(10, i);
        }
        int width = 0;
        for (int i : digits) {
            width += Res.tex_numbers[font][i].getWidth() + 1;
        }
        width--;
        int iWidth = 0;
        for (int i : digits) {
            batch.draw(Res.tex_numbers[font][i], pos.x - width / 2 + iWidth, pos.y);
            iWidth += Res.tex_numbers[font][i].getWidth() + 1;
        }
    }

    @Override
    public void dispose() {

        batch.dispose();
        if (isLoaded) {
            game.dispose();
            menu.dispose();
            shop.dispose();
            splash.dispose();
            welcome.dispose();
            assets.dispose();
            DataManager.getInstance().saveData();
        }

    }

    public void onPause() {
        if (signedIn) {
            System.out.println("ONPAUSE");
            userData.timePlayed += (System.currentTimeMillis() - startTime) / 1000;
            startTime = System.currentTimeMillis();
            userData.adsClicked += amm.getAdsClicked();
            userData.orbs = Main.gameData.orbs;

            Main.gameData.userData = userData;
            Shop.saveData();

            userData.ballsUnlocked.clear();
            userData.highscore = gameData.highscore;
            for (boolean b : Main.gameData.unlocks) {
                userData.ballsUnlocked.add(b);
            }
            System.out.println("SAVE-REPORT: userData.timePlayed: " + userData.timePlayed);
            fbm.setUserData(userData);
            fbm.leave();

            DataManager.getInstance().saveData();
        }
    }

    public void onResume() {
        System.out.println("ONRESUME");
        startTime = System.currentTimeMillis();
    }

    public static void reset(){
        game.clear();
        game.unpause();
        game=new Game();
        game.replay();
        setNoMusic();
    }

    @Override
    public void pause() {
        super.pause();
    }

    static void onSignIn() {
        fbm.getUID();
        fbm.onSignIn();
        testerID = fbm.getTesterID();
        if (RESEARCHMODE)
            setVersion(testerID);
        gameData.testerID = testerID;
        userData = fbm.getUserData();

        if (userData == null)
            userData = new UserData();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(date);
        boolean isNewDate = true;
        for (String s : userData.daysPlayed) {
            if (s.equals(formattedDate)) {
                isNewDate = false;
                break;
            }
        }
        if (isNewDate)
            userData.daysPlayed.add(formattedDate);
    }

    public static void onLoaded() {
        isLoaded = true;
        game = new Game();
        if (DOSCREENSHOTMODE)
            setScreenShotMode();
        menu = new Menu();
        welcome = new Welcome();
        shop = new Shop();
    }

    public static void setVersion(int id) {
 
        noAds = false;
        noFlow = false;
        noFX = false;
        noScore = false;
        noMusic = false;
        noLevels = false;
        noCollection = false;

        int version = id % 10;
        switch (version) {
            case 0:
                noAds = true;
                break;
            case 1:
                noFlow = true;
                break;
            case 2:
                noFX = true;
                break;
            case 3:
                noScore = true;
                break;
            case 4:
                noMusic = true;
                break;
            case 5:
                noLevels = true;
                break;
            case 6:
                noCollection = true;
                break;
            default:
        }
    }

    public static void startFade(Scene nextScene) {
        System.out.println("Start FADE");
        if (!doFade) {
            System.out.println("Start FADE success");
            Main.nextScene = nextScene;
            startFade();
        }
    }

    public static void startFade() {
        if (!doFade) {
            doFade = true;
            fadeDir = 1;
        }
    }

    public static void onFadePeak() {
        if (nextScene != null) {
            setScene(nextScene);
            System.out.println("SETSCENE: " + nextScene.getClass());
            nextScene = null;
            return;
        }
        Game.onFadePeak();
    }

    public static void setPalette(Color[] colors) {
        Res.shader_palette.setUniformf("color0", colors[0]);
        Res.shader_palette.setUniformf("color1", colors[1]);
        Res.shader_palette.setUniformf("color2", colors[2]);
        Res.shader_palette.setUniformf("color3", colors[3]);
    }

    public static void setOverlayGameOver() {
        overlayScene = gameOver;
    }

    public static void setOverlayNull() {
        overlayScene = null;
    }

    public static void setSceneGame() {

        //gameOver = new GameOver();
        startFade(game);
        inGame = true;
    }

    public static void setSceneMenu() {
        startFade(menu);
    }

    public static void setSceneMenuNow() {
        setScene(menu);
    }

    public static void setSceneWelcome() {
        startFade(welcome);
    }

    public static void setSceneShop() {
        startFade(shop);
    }

    static void setScene(Scene scene) {
        if (currentScene != null)
            currentScene.hide();
        scene.show();
        currentScene = scene;
    }

    public static void setAdVisibility(boolean visibility) {
        if (!noAds) {
            if (visibility) {
                amm.show();
            } else
                amm.hide();
        }
    }

    public static void initializeResources() {
        res = new Res();
        resourcesLoaded = true;
        res.sprite_watermark.setPosition(width - res.sprite_watermark.getWidth() - 2, 2);
    }

    static void playSound(int soundID, float volume) {
        switch (soundID) {
            case ID.Sound.PLOP:
                playPlopSound();
                break;
            case ID.Sound.GLASS:
                playGlassBreakSound();
                break;
            case ID.Sound.SLOWDOWN:
                playSlowDownSound();
                break;
            case ID.Sound.SPEEDUP:
                playSpeedUpSound();
                break;
            case ID.Sound.HIT:
                Res.sound_ballHit.play(volume, (float) Math.random() * .2f + .9f, 0);
                break;
        }
    }

    public static void playPlopSound() {
        Res.sound_plop.play(1, (float) Math.random() * .4f + .8f, 0);
    }

    public static void playGlassBreakSound() {
        Res.sound_glassBreak.play(1, (float) Math.random() * .4f + .9f, 0);
    }

    public static void playSlowDownSound() {
        Res.sound_slowdown.play(1, 1, 0);
    }

    public static void playSpeedUpSound() {
        Res.sound_speedup.play(1, 1, 0);
    }

    public static float getHypothenuse(float x, float y) {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static float distanceBetweenPoints(Vector2 v0, Vector2 v1) {
        return (float) Math.sqrt(Math.pow(v0.x - v1.x, 2) + Math.pow(v0.y - v1.y, 2));
    }

    public static float getSum(float[] floats) {
        float sum = 0;
        for (float f : floats) {
            sum += f;
        }

        return sum;
    }

    public static float angleBetweenPoints(Vector2 v0, Vector2 v1) {
        return correctAtan2Output((float) Math.atan2(v1.y - v0.y, v1.x - v0.x));
    }

    public static float correctAtan2Output(float atan2Output) {
        if (atan2Output >= 0) {
            return atan2Output;
        } else {
            return (float) (Math.PI - Math.abs(atan2Output) + Math.PI);
        }
    }

    public static boolean pointInRectangle(float x, float y, float bx, float by, float bw, float bh) {
        return (x > bx && x < bx + bw && y > by && y < by + bh);
    }

    public static float interpolateAngle(float from, float to, float amount) {
        float shortest_angle = (float) (((((to - from) % (Math.PI * 2)) + (Math.PI * 1.5f)) % (Math.PI * 2)) - (Math.PI));
        return (float) (from + (shortest_angle * amount) % (Math.PI * 2));
    }

    /*
    public static Vector2 intersectCircleLine(float m, float c, float r, float x, float y) {
        float a_ = (float) (Math.pow(m, 2) + 1);
        float b_ = 2 * (m * c - m * y - x);
        float c_ = (float) (Math.pow(y, 2) - Math.pow(r, 2) + Math.pow(x, 2) - 2 * c * y + Math.pow(c, 2));
        float D = (float) (Math.pow(b_, 2) - 4 * a_ * c_);
        float x_ = (-b_ - (float) Math.sqrt(D)) / (2 * a_);

        return new Vector2(x_, m * x_ + c);
    }
    */

    public static boolean intersectCircleLine(float xBegin, float yBegin, float xEnd, float yEnd, float x, float y, float r) {
        Vector2 d = new Vector2(xEnd - xBegin, yEnd - yBegin);
        Vector2 f = new Vector2(xBegin - x, yBegin - y);

        float a = d.dot(d);
        float b = 2 * f.dot(d);
        float c = f.dot(f) - r * r;


        float discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return false;
        } else {
            discriminant = (float) Math.sqrt(discriminant);

            float t1 = (-b - discriminant) / (2 * a);
            float t2 = (-b + discriminant) / (2 * a);

            if (t1 >= 0 && t1 <= 1) {
                return true;
            }

            if (t2 >= 0 && t2 <= 1) {
                return true;
            }

            return false;
        }
    }

    public Main setDevMode() {
        noMusic = true;
        return this;
    }
}
