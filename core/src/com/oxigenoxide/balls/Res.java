package com.oxigenoxide.balls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Res {

    public static Texture[][] tex_ball;
    public static Texture[] tex_ballShard;
    public static Texture[] tex_title;
    public static Texture[] tex_text_v;
    public static Texture[][] tex_numbers;
    public static Texture tex_ball_bad;
    public static Texture tex_crown;
    public static Texture tex_tabletop;
    public static Texture tex_fade;
    public static Texture tex_ttptext;
    public static Texture tex_hleditie;
    public static Texture tex_text_slowdown;
    public static Texture tex_bumper;
    public static Texture tex_tilt;
    public static Texture tex_bomb_white;
    public static Texture tex_text_player;
    public static Texture tex_tutorialMode;
    public static Texture tex_random;
    public static Texture tex_gap;
    public static Texture[] tex_confetti;
    public static Texture tex_buttonPressed_balls;
    public static Texture tex_buttonPressed_leaderBoards;
    public static Texture tex_button_balls;
    public Texture tex_watermark;
    public static Texture tex_text_welcome;
    public static Texture tex_text_highscore;
    public static Texture tex_buttonPressed_toGame;
    public static Texture[] tex_spike;
    public static Texture tex_shield;
    public static Texture tex_shield_shine;
    public static Texture tex_button_toGame;
    public static Texture tex_buttonPressed_options;
    public static Texture tex_button_options;
    public static Texture tex_button_tutorial;
    public static Texture tex_buttonPressed_tutorial;
    public static Texture tex_buttonPressed_replay;
    public static Texture tex_button_replay;
    public static Texture tex_button_leaderBoards;
    public static Texture tex_button_cross;
    public static Texture tex_bomb;
    public static Texture tex_buttonPressed_cross;
    public static Texture tex_youlost;
    public static Texture tex_cannon_shine;
    public static Texture tex_darkLayer;
    public static Texture tex_orbCountBackground;
    public static Texture tex_floorButton_danger;
    public static Texture tex_floorButtonPressed_danger;
    public static Texture tex_statisticsBackground;
    public static Texture tex_numberSign;
    public static Texture tex_shopSpot;
    public static Texture tex_shopSpotPressed;
    public static Texture tex_underTitle;
    public static Texture tex_cannon_gun;
    public static Texture tex_cannon_base;
    public static Texture tex_orbCounter;
    public static Texture tex_button_pause;
    public static Texture tex_buttonPressed_pause;
    public static Texture tex_button_resume;
    public static Texture tex_buttonPressed_resume;
    public static Texture tex_button_balls_new;
    public static Texture tex_buttonPressed_balls_new;
    public static Texture tex_button_info;
    public static Texture tex_buttonPressed_info;
    public static Texture tex_button_exit;
    public static Texture tex_buttonPressed_exit;
    public static Texture tex_text_paused;
    public static Texture tex_lockedBall;
    public static Texture tex_blueEgg;
    public static Texture tex_fullscreen;
    public static Texture tex_symbolOrb;
    public static Texture tex_oxigenoxide;
    public static Texture tex_symbolSelected;
    public static Texture tex_text_comingsoon;
    public static Texture tex_collectable_shield;
    public static Texture tex_goldenEgg;
    public static Texture tex_bullet;
    public static Texture[] tex_badSmile;
    public static Texture[] tex_explosion;
    public static Texture tex_cat;
    public static Texture tex_honey;
    public static Texture tex_tutorialHole;
    public static Texture tex_pin;
    public static Texture[] tex_hand;
    public static float[] ballRadius;
    public static Texture tex_orb;
    public static Texture tex_text_orbs;
    public static Texture tex_text_youare;
    public static Texture tex_symbolPlus;
    public static Texture tex_symbol_checkmark;
    public static Texture tex_symbol_cross;
    public static Texture tex_text_score;
    public static Texture tex_sign_number_small;
    public static Texture tex_versionBarOutline;
    public static Texture tex_versionBarShade;
    public static Texture tex_scrollGroove;
    public static Texture tex_scrollNotch;


    public Sprite sprite_watermark;
    public static BodyDef bodyDef_dynamic;
    public static BodyDef bodyDef_static;
    public static ShaderProgram shader_pixelate;
    public static ShaderProgram shader_palette;
    public static ShaderProgram shader_slow;
    public static ShaderProgram shader_trailFade;
    public static ShaderProgram shader_tilt;
    public static ShaderProgram shader_random;
    public static ShaderProgram shader_c;
    public static ShaderProgram shader_a;
    public static ShaderProgram shader_fade;
    public static ShaderProgram shader_overlay;

    public static FixtureDef fixtureDef_border;
    public static FixtureDef fixtureDef_circle;
    public static FixtureDef fixtureDef_badBall_opponents;
    public static FixtureDef fixtureDef_badBall_normal;
    public static FixtureDef fixtureDef_egg;
    public static FixtureDef fixtureDef_gap;
    public static FixtureDef fixtureDef_shield;
    public static FixtureDef fixtureDef_collectable;
    public static FixtureDef[] fixtureDef_ball;
    public static Color[][] ballPalette;
    public static Color[][] tableTopPalette;
    public static Color[] ballBadPalette;
    public static Color[] eggPalette;

    public static CircleShape[] shape_ball;
    public static final Color COLOR_HOT = new Color(1, 130 / 255f, 26 / 255f, 1);
    public static final Color COLOR_RED = new Color(113 / 255f, 9 / 255f, 56 / 255f, 1);

    public static Sound sound_ballHit;
    public static Sound sound_speedup;
    public static Sound sound_buttonClick1;
    public static Sound sound_buttonClick2;
    public static Sound sound_slowdown;
    public static Sound sound_collect;
    public static Sound sound_glassBreak;
    public static Sound sound_plop;
    public static Music[] music;
    public static Shape shape_spike;
    public static Shape shape_pin;
    public static Shape shape_bullet;

    public static long soundID_music;
    public static final short MASK_PASSTHROUGH = 0x0002;
    public static final short MASK_BORDER = 0x0004;
    public static final short MASK_ZERO = 0x0001;
    public static final short MASK_BAD_BAD = 0x0008;
    public static final short MASK_MAINBALL = 0x0016;
    public static final short MASK_CAT = 0x0064;
    public static final short MASK_GAP = 0x0032;
    public static final short MASK_WALL = 0x0128;

    public Res() {
        sound_ballHit = Gdx.audio.newSound(Gdx.files.internal("sounds/ballHit.wav"));
        sound_speedup = Gdx.audio.newSound(Gdx.files.internal("sounds/speedup.wav"));
        sound_slowdown = Gdx.audio.newSound(Gdx.files.internal("sounds/slowdown.wav"));
        sound_glassBreak = Gdx.audio.newSound(Gdx.files.internal("sounds/glassBreak.wav"));
        sound_buttonClick1 = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonClick.wav"));
        sound_plop = Gdx.audio.newSound(Gdx.files.internal("sounds/plop.mp3"));
        sound_buttonClick2 = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonClick2.wav"));
        sound_collect = Gdx.audio.newSound(Gdx.files.internal("sounds/collect.wav"));
        music = new Music[]{
                Gdx.audio.newMusic(Gdx.files.internal("music/1.mp3")),
                Gdx.audio.newMusic(Gdx.files.internal("music/2.mp3")),
                Gdx.audio.newMusic(Gdx.files.internal("music/3.mp3")),
                Gdx.audio.newMusic(Gdx.files.internal("music/4.mp3")),
                Gdx.audio.newMusic(Gdx.files.internal("music/5.mp3")),
        };

        shader_pixelate = new ShaderProgram(Gdx.files.internal("shaders/shader_pixelate.vert"), Gdx.files.internal("shaders/shader_pixelate.frag"));
        shader_palette = new ShaderProgram(Gdx.files.internal("shaders/shader_palette.vert"), Gdx.files.internal("shaders/shader_palette.frag"));
        shader_slow = new ShaderProgram(Gdx.files.internal("shaders/shader_slow.vert"), Gdx.files.internal("shaders/shader_slow.frag"));
        shader_trailFade = new ShaderProgram(Gdx.files.internal("shaders/shader_trailFade.vert"), Gdx.files.internal("shaders/shader_trailFade.frag"));
        shader_tilt = new ShaderProgram(Gdx.files.internal("shaders/shader_tilt.vert"), Gdx.files.internal("shaders/shader_tilt.frag"));
        shader_random = new ShaderProgram(Gdx.files.internal("shaders/shader_random.vert"), Gdx.files.internal("shaders/shader_random.frag"));
        shader_c = new ShaderProgram(Gdx.files.internal("shaders/shader_c.vert"), Gdx.files.internal("shaders/shader_c.frag"));
        shader_a = new ShaderProgram(Gdx.files.internal("shaders/shader_a.vert"), Gdx.files.internal("shaders/shader_a.frag"));
        shader_fade = new ShaderProgram(Gdx.files.internal("shaders/shader_fade.vert"), Gdx.files.internal("shaders/shader_fade.frag"));
        shader_overlay = new ShaderProgram(Gdx.files.internal("shaders/shader_overlay.vert"), Gdx.files.internal("shaders/shader_overlay.frag"));

        /*
        tex_ball = new Texture[]{new Texture("images/ball_small.png"), new Texture("images/ball_medium.png"), new Texture("images/ball_large.png")};
        tex_ball_bad = new Texture("images/ball_bad.png");
        tex_crown = new Texture("images/crown.png");
        tex_bumper = new Texture("images/bumper.png");
        tex_tabletop = new Texture("images/tabletop.png");
        tex_ballShard = new Texture[]{new Texture("images/ballShard_0.png"), new Texture("images/ballShard_1.png"), new Texture("images/ballShard_2.png")};
        */

        tex_badSmile = new Texture[]{Main.assets.get("images/badSmile_1.png"), Main.assets.get("images/badSmile_2.png"), Main.assets.get("images/badSmile_3.png"), Main.assets.get("images/badSmile_4.png"), Main.assets.get("images/badSmile_5.png"),};
        tex_ball = new Texture[9][];
        tex_ball[0] = new Texture[]{Main.assets.get("images/ball_small.png"), Main.assets.get("images/ball_medium.png"), Main.assets.get("images/ball_large.png")};
        tex_ball[1] = new Texture[]{Main.assets.get("images/ball_face_small.png"), Main.assets.get("images/ball_face_medium.png"), Main.assets.get("images/ball_face_large.png")};
        tex_ball[2] = new Texture[]{Main.assets.get("images/ball_square_small.png"), Main.assets.get("images/ball_square_medium.png"), Main.assets.get("images/ball_square_large.png")};
        tex_ball[3] = new Texture[]{Main.assets.get("images/ball_brain_small.png"), Main.assets.get("images/ball_brain_medium.png"), Main.assets.get("images/ball_brain_large.png")};
        tex_ball[4] = new Texture[]{Main.assets.get("images/ball_egg_small.png"), Main.assets.get("images/ball_egg_medium.png"), Main.assets.get("images/ball_egg_large.png")};
        tex_ball[5] = new Texture[]{Main.assets.get("images/ball_apple_small.png"), Main.assets.get("images/ball_apple_medium.png"), Main.assets.get("images/ball_apple_large.png")};
        tex_ball[6] = new Texture[]{Main.assets.get("images/ball_rasp_small.png"), Main.assets.get("images/ball_rasp_medium.png"), Main.assets.get("images/ball_rasp_large.png")};
        tex_ball[7] = new Texture[]{Main.assets.get("images/ball_hl_small.png"), Main.assets.get("images/ball_hl_medium.png"), Main.assets.get("images/ball_hl_large.png")};
        tex_ball[8] = new Texture[]{Main.assets.get("images/ball_moon_small.png"), Main.assets.get("images/ball_moon_medium.png"), Main.assets.get("images/ball_moon_large.png")};
        tex_ball_bad = Main.assets.get("images/ball_bad.png");
        tex_bomb = Main.assets.get("images/bomb.png");
        tex_bomb_white = Main.assets.get("images/bomb_white.png");
        tex_crown = Main.assets.get("images/crown.png");
        tex_bumper = Main.assets.get("images/bumper.png");
        tex_tabletop = Main.assets.get("images/tabletop.png");
        tex_watermark = Main.assets.get("images/watermark.png");
        tex_buttonPressed_toGame = Main.assets.get("images/buttonPressed_toGame.png");
        tex_button_toGame = Main.assets.get("images/button_toGame.png");
        tex_buttonPressed_replay = Main.assets.get("images/buttonPressed_replay.png");
        tex_button_replay = Main.assets.get("images/button_replay.png");
        tex_button_options = Main.assets.get("images/button_options.png");
        tex_buttonPressed_options = Main.assets.get("images/buttonPressed_options.png");
        tex_buttonPressed_balls = Main.assets.get("images/buttonPressed_shop.png");
        tex_buttonPressed_cross = Main.assets.get("images/buttonPressed_cross.png");
        tex_button_cross = Main.assets.get("images/button_cross.png");
        tex_button_balls = Main.assets.get("images/button_shop.png");
        tex_button_exit = Main.assets.get("images/button_exit.png");
        tex_buttonPressed_exit = Main.assets.get("images/buttonPressed_exit.png");
        tex_text_welcome = Main.assets.get("images/text_welcome.png");
        tex_symbolPlus = Main.assets.get("images/symbol_plus.png");
        tex_text_player = Main.assets.get("images/text_player.png");
        tex_cannon_gun = Main.assets.get("images/cannon_gun.png");
        tex_cannon_base = Main.assets.get("images/cannon_base.png");
        tex_ttptext = Main.assets.get("images/ttptext.png");
        tex_text_highscore = Main.assets.get("images/text_highscore.png");
        tex_button_tutorial = Main.assets.get("images/button_tutorial.png");
        tex_text_youare = Main.assets.get("images/text_youare.png");
        tex_cannon_shine = Main.assets.get("images/cannon_shine.png");
        tex_buttonPressed_tutorial = Main.assets.get("images/buttonPressed_tutorial.png");
        tex_lockedBall = Main.assets.get("images/lockedBall.png");
        tex_statisticsBackground = Main.assets.get("images/statisticsBackground.png");
        tex_ballShard = new Texture[]{Main.assets.get("images/ballShard_0.png"), Main.assets.get("images/ballShard_1.png"), Main.assets.get("images/ballShard_2.png")};
        tex_numbers = new Texture[5][];
        tex_numbers[0] = new Texture[]{Main.assets.get("images/numbers/number_0.png"), Main.assets.get("images/numbers/number_1.png"), Main.assets.get("images/numbers/number_2.png"), Main.assets.get("images/numbers/number_3.png"), Main.assets.get("images/numbers/number_4.png"), Main.assets.get("images/numbers/number_5.png"), Main.assets.get("images/numbers/number_6.png"), Main.assets.get("images/numbers/number_7.png"), Main.assets.get("images/numbers/number_8.png"), Main.assets.get("images/numbers/number_9.png")};
        tex_numbers[1] = new Texture[]{Main.assets.get("images/numbers/number_s_0.png"), Main.assets.get("images/numbers/number_s_1.png"), Main.assets.get("images/numbers/number_s_2.png"), Main.assets.get("images/numbers/number_s_3.png"), Main.assets.get("images/numbers/number_s_4.png"), Main.assets.get("images/numbers/number_s_5.png"), Main.assets.get("images/numbers/number_s_6.png"), Main.assets.get("images/numbers/number_s_7.png"), Main.assets.get("images/numbers/number_s_8.png"), Main.assets.get("images/numbers/number_s_9.png")};
        tex_numbers[2] = new Texture[]{Main.assets.get("images/numbers/number_m_0.png"), Main.assets.get("images/numbers/number_m_1.png"), Main.assets.get("images/numbers/number_m_2.png"), Main.assets.get("images/numbers/number_m_3.png"), Main.assets.get("images/numbers/number_m_4.png"), Main.assets.get("images/numbers/number_m_5.png"), Main.assets.get("images/numbers/number_m_6.png"), Main.assets.get("images/numbers/number_m_7.png"), Main.assets.get("images/numbers/number_m_8.png"), Main.assets.get("images/numbers/number_m_9.png")};
        tex_numbers[3] = new Texture[]{Main.assets.get("images/numbers/number_h_0.png"), Main.assets.get("images/numbers/number_h_1.png"), Main.assets.get("images/numbers/number_h_2.png"), Main.assets.get("images/numbers/number_h_3.png"), Main.assets.get("images/numbers/number_h_4.png"), Main.assets.get("images/numbers/number_h_5.png"), Main.assets.get("images/numbers/number_h_6.png"), Main.assets.get("images/numbers/number_h_7.png"), Main.assets.get("images/numbers/number_h_8.png"), Main.assets.get("images/numbers/number_h_9.png")};
        tex_numbers[4] = new Texture[]{Main.assets.get("images/numbers/number_b_0.png"), Main.assets.get("images/numbers/number_b_1.png"), Main.assets.get("images/numbers/number_b_2.png"), Main.assets.get("images/numbers/number_b_3.png"), Main.assets.get("images/numbers/number_b_4.png"), Main.assets.get("images/numbers/number_b_5.png"), Main.assets.get("images/numbers/number_b_6.png"), Main.assets.get("images/numbers/number_b_7.png"), Main.assets.get("images/numbers/number_b_8.png"), Main.assets.get("images/numbers/number_b_9.png")};
        tex_numberSign = Main.assets.get("images/number_sign.png");
        tex_floorButton_danger = Main.assets.get("images/button_danger.png");
        tex_floorButtonPressed_danger = Main.assets.get("images/buttonPressed_danger.png");
        tex_title = new Texture[]{
                Main.assets.get("images/title_c.png"),
                Main.assets.get("images/title_a.png"),
                Main.assets.get("images/title_r.png"),
                Main.assets.get("images/title_a.png"),
                Main.assets.get("images/title_m.png"),
        };
        tex_shield = Main.assets.get("images/shield.png");
        tex_shield_shine = Main.assets.get("images/shield_shine.png");
        tex_underTitle = Main.assets.get("images/title_balls.png");
        tex_ttptext = Main.assets.get("images/ttptext.png");
        tex_collectable_shield = Main.assets.get("images/collectable_shield.png");
        tex_tutorialHole = Main.assets.get("images/tutorialHole.png");
        tex_hleditie = Main.assets.get("images/HLeditie.png");
        tex_button_leaderBoards = Main.assets.get("images/button_leaderBoards.png");
        tex_buttonPressed_leaderBoards = Main.assets.get("images/buttonPressed_leaderBoards.png");
        tex_youlost = Main.assets.get("images/youlost.png");
        tex_symbolOrb = Main.assets.get("images/symbol_orb.png");
        tex_shopSpot = Main.assets.get("images/shopSpot.png");
        tex_shopSpotPressed = Main.assets.get("images/shopSpotPressed.png");
        tex_symbolSelected = Main.assets.get("images/symbol_selected.png");
        tex_pin = Main.assets.get("images/pin.png");
        tex_text_slowdown = Main.assets.get("images/text_slowdown.png");
        tex_oxigenoxide = Main.assets.get("images/oxigenoxide.png");
        tex_orbCountBackground = Main.assets.get("images/orbCountBackground.png");
        tex_goldenEgg = Main.assets.get("images/goldenEgg.png");
        tex_blueEgg = Main.assets.get("images/blueEgg.png");
        tex_orb = Main.assets.get("images/orb.png");
        tex_text_comingsoon = Main.assets.get("images/text_comingsoon.png");
        tex_orbCounter = Main.assets.get("images/orbCounter.png");
        tex_text_orbs = Main.assets.get("images/text_orbs.png");
        tex_bullet = Main.assets.get("images/bullet.png");
        tex_symbol_checkmark = Main.assets.get("images/symbol_checkmark.png");
        tex_symbol_cross = Main.assets.get("images/symbol_cross.png");
        tex_tutorialMode = Main.assets.get("images/tutorialMode.png");
        tex_sign_number_small = Main.assets.get("images/sign_number_small.png");
        tex_text_score = Main.assets.get("images/text_score.png");
        tex_honey = Main.assets.get("images/honey.png");
        tex_button_pause = Main.assets.get("images/button_pause.png");
        tex_buttonPressed_pause = Main.assets.get("images/buttonPressed_pause.png");
        tex_button_resume = Main.assets.get("images/button_resume.png");
        tex_buttonPressed_resume = Main.assets.get("images/buttonPressed_resume.png");
        tex_button_balls_new = Main.assets.get("images/button_shop_new.png");
        tex_buttonPressed_balls_new = Main.assets.get("images/buttonPressed_shop_new.png");
        tex_text_paused = Main.assets.get("images/text_paused.png");
        tex_gap = Main.assets.get("images/gap.png");
        tex_cat = Main.assets.get("images/cat.png");
        tex_versionBarOutline=Main.assets.get("images/versionBarOutline.png");
        tex_versionBarShade=Main.assets.get("images/versionBarShade.png");
        tex_scrollGroove=Main.assets.get("images/scrollGroove.png");
        tex_scrollNotch=Main.assets.get("images/scrollNotch.png");
        tex_button_info=Main.assets.get("images/button_info.png");
        tex_buttonPressed_info=Main.assets.get("images/buttonPressed_info.png");
        tex_hand = new Texture[]{
                Main.assets.get("images/hand_0.png"),
                Main.assets.get("images/hand_1.png"),
        };
        tex_confetti = new Texture[]{
                Main.assets.get("images/confetti_red.png"),
                Main.assets.get("images/confetti_blue.png"),
                Main.assets.get("images/confetti_green.png"),
        };


        tex_spike = new Texture[]{
                Main.assets.get("images/spike_1.png"),
                Main.assets.get("images/spike_2.png"),
                Main.assets.get("images/spike_3.png"),
                Main.assets.get("images/spike_4.png"),
                Main.assets.get("images/spike_4.png"),
        };

        tex_explosion = new Texture[]{
                Main.assets.get("images/explosion_0.png"),
                Main.assets.get("images/explosion_1.png"),
                Main.assets.get("images/explosion_2.png"),
                Main.assets.get("images/explosion_3.png"),
                Main.assets.get("images/explosion_4.png"),
                Main.assets.get("images/explosion_5.png"),
                Main.assets.get("images/explosion_6.png"),
                Main.assets.get("images/explosion_7.png"),
                Main.assets.get("images/explosion_8.png"),
                Main.assets.get("images/explosion_9.png"),
                Main.assets.get("images/explosion_10.png"),
                Main.assets.get("images/explosion_11.png"),
                Main.assets.get("images/explosion_12.png"),
        };
        tex_text_v=new Texture[]{
                Main.assets.get("images/text_v_standard.png"),
                Main.assets.get("images/text_v_noAds.png"),
                Main.assets.get("images/text_v_noLevels.png"),
                Main.assets.get("images/text_v_noShop.png"),
                Main.assets.get("images/text_v_noMusic.png"),
                Main.assets.get("images/text_v_noScore.png"),
                Main.assets.get("images/text_v_noEffects.png"),
                Main.assets.get("images/text_v_hard.png"),
        };

        sprite_watermark = new Sprite(tex_watermark);
        sprite_watermark.setAlpha(.5f);

        bodyDef_dynamic = new BodyDef();
        bodyDef_dynamic.type = BodyDef.BodyType.DynamicBody;

        bodyDef_static = new BodyDef();
        bodyDef_static.type = BodyDef.BodyType.StaticBody;

        new World(new Vector2(0, 0), false);

        shape_ball = new CircleShape[]{new CircleShape(), new CircleShape(), new CircleShape()};
        shape_ball[0].setRadius(4 * Main.METERSPERPIXEL);
        shape_ball[1].setRadius(7 * Main.METERSPERPIXEL);
        shape_ball[2].setRadius(9.5f * Main.METERSPERPIXEL);


        fixtureDef_ball = new FixtureDef[]{new FixtureDef(), new FixtureDef(), new FixtureDef()};
        for (int i = 0; i < 3; i++) { // ONLY FOR MAINBALLS
            fixtureDef_ball[i].density = 1;
            fixtureDef_ball[i].shape = shape_ball[i];
            fixtureDef_ball[i].restitution = 1;
            fixtureDef_ball[i].filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
            fixtureDef_ball[i].filter.categoryBits = (MASK_ZERO);
        }

        fixtureDef_shield = new FixtureDef();
        fixtureDef_shield.density = 1;
        fixtureDef_shield.restitution = 1;
        fixtureDef_shield.shape = new CircleShape();
        fixtureDef_shield.filter.maskBits = MASK_ZERO;
        fixtureDef_shield.filter.categoryBits = MASK_ZERO;

        fixtureDef_circle = new FixtureDef();
        fixtureDef_circle.density = 1;
        fixtureDef_circle.shape = new CircleShape();
        fixtureDef_circle.filter.maskBits = MASK_ZERO;
        fixtureDef_circle.filter.categoryBits = MASK_ZERO;

        fixtureDef_egg = new FixtureDef();
        fixtureDef_egg.density = 1;
        fixtureDef_egg.restitution = 1;
        fixtureDef_egg.shape = new CircleShape();
        fixtureDef_egg.shape.setRadius(5 * Main.METERSPERPIXEL);
        fixtureDef_egg.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_egg.filter.categoryBits = MASK_ZERO;

        fixtureDef_badBall_opponents = new FixtureDef();
        fixtureDef_badBall_opponents.density = 1;
        fixtureDef_badBall_opponents.shape = new CircleShape();
        fixtureDef_badBall_opponents.shape.setRadius(2 * Main.METERSPERPIXEL);
        fixtureDef_badBall_opponents.filter.maskBits = (short) (MASK_ZERO);
        fixtureDef_badBall_opponents.filter.categoryBits = MASK_ZERO;

        fixtureDef_badBall_normal = new FixtureDef();
        fixtureDef_badBall_normal.density = 1;
        fixtureDef_badBall_normal.shape = new CircleShape();
        fixtureDef_badBall_normal.shape.setRadius(7 * Main.METERSPERPIXEL);
        fixtureDef_badBall_normal.filter.maskBits = (MASK_ZERO);
        fixtureDef_badBall_normal.filter.categoryBits = MASK_ZERO;

        fixtureDef_collectable = new FixtureDef();
        fixtureDef_collectable.density = 1;
        fixtureDef_collectable.isSensor = true;
        fixtureDef_collectable.shape = new CircleShape();
        fixtureDef_collectable.shape.setRadius(7 * Main.METERSPERPIXEL);
        fixtureDef_collectable.filter.maskBits = (MASK_ZERO);
        fixtureDef_collectable.filter.categoryBits = MASK_ZERO;

        ChainShape chainShape_gap = new ChainShape();
        chainShape_gap.createLoop(new float[]{Main.METERSPERPIXEL * 29, Main.METERSPERPIXEL * 71, Main.METERSPERPIXEL * 29, Main.METERSPERPIXEL * 121, Main.METERSPERPIXEL * 79, Main.METERSPERPIXEL * 121, Main.METERSPERPIXEL * 79, Main.METERSPERPIXEL * 71});
        fixtureDef_gap = new FixtureDef();
        fixtureDef_gap.density = 1;
        fixtureDef_gap.shape = chainShape_gap;
        fixtureDef_gap.filter.maskBits = (short) (MASK_ZERO | MASK_WALL);
        fixtureDef_gap.filter.categoryBits = MASK_WALL;

        shape_spike = new CircleShape();
        shape_spike.setRadius(3.5f * Main.METERSPERPIXEL);

        shape_pin = new CircleShape();
        shape_pin.setRadius(5 * Main.METERSPERPIXEL);

        shape_bullet = new PolygonShape();
        ((PolygonShape) shape_bullet).setAsBox(3.5f * Main.METERSPERPIXEL, 2.5f * Main.METERSPERPIXEL);

        fixtureDef_border = new FixtureDef();
        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new float[]{0, 0, Main.width * Main.METERSPERPIXEL, 0, Main.width * Main.METERSPERPIXEL, Main.height * Main.METERSPERPIXEL, 0, Main.height * Main.METERSPERPIXEL});
        fixtureDef_border.shape = chainShape;
        fixtureDef_border.density = 1;
        fixtureDef_border.restitution = 1;
        fixtureDef_border.friction = 0;
        fixtureDef_border.filter.maskBits = (short) (MASK_ZERO | MASK_PASSTHROUGH);
        fixtureDef_border.filter.categoryBits = MASK_WALL;


        tableTopPalette = new Color[][]{
                new Color[4], new Color[4], new Color[4], new Color[4]
        };

        tableTopPalette[0][0] = new Color(75 / 255f, 142 / 255f, 108 / 255f, 1);
        tableTopPalette[0][1] = new Color(98 / 255f, 158 / 255f, 128 / 255f, 1);
        tableTopPalette[0][2] = new Color(98 / 255f, 158 / 255f, 128 / 255f, 1);
        tableTopPalette[0][3] = new Color(75 / 255f, 142 / 255f, 108 / 255f, 1);
        tableTopPalette[1][0] = new Color(142 / 255f, 94 / 255f, 75 / 255f, 1);
        tableTopPalette[1][1] = new Color(158 / 255f, 124 / 255f, 98 / 255f, 1);
        tableTopPalette[1][2] = new Color(158 / 255f, 124 / 255f, 98 / 255f, 1);
        tableTopPalette[1][3] = new Color(142 / 255f, 94 / 255f, 75 / 255f, 1);
        tableTopPalette[2][0] = new Color(75 / 255f, 89 / 255f, 142 / 255f, 1);
        tableTopPalette[2][1] = new Color(75 / 255f, 128 / 255f, 142 / 255f, 1);
        tableTopPalette[2][2] = new Color(75 / 255f, 128 / 255f, 142 / 255f, 1);
        tableTopPalette[2][3] = new Color(75 / 255f, 89 / 255f, 142 / 255f, 1);

        ballBadPalette = new Color[]{
                new Color(0, 0, 0, 1),
                COLOR_RED,
                new Color(206 / 255f, 39 / 255f, 39 / 255f, 1),
                new Color(0, 0, 0, 1)
        };
        eggPalette = new Color[]{
                new Color(0, 0, 0, 1),
                new Color(0, 200 / 255f, 1, 1),
                new Color(131 / 255f, 1, 228 / 255f, 1),
                new Color(1, 1, 1, 1),
        };

        ballPalette = new Color[][]{
                new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4], new Color[4]
        };

        ballPalette[0][0] = new Color(0, 0, 0, 1);
        ballPalette[0][1] = new Color(62 / 255f, 180 / 255f, 227 / 255f, 1);
        ballPalette[0][2] = new Color(1, 1, 1, 1);
        ballPalette[0][3] = new Color(1, 1, 1, 1);

        ballPalette[1][0] = new Color(0, 0, 0, 1);
        ballPalette[1][1] = new Color(191 / 255f, 0, 0, 1);
        ballPalette[1][2] = new Color(255 / 255f, 108 / 255f, 0, 1);
        ballPalette[1][3] = new Color(255 / 255f, 108 / 255f, 0, 1);

        ballPalette[2][0] = new Color(0, 0, 0, 1);
        ballPalette[2][1] = new Color(16 / 255f, 148 / 255f, 63 / 255f, 1);
        ballPalette[2][2] = new Color(173 / 255f, 247 / 255f, 41 / 255f, 1);
        ballPalette[2][3] = new Color(173 / 255f, 247 / 255f, 41 / 255f, 1);

        ballPalette[3][0] = new Color(0, 0, 0, 1);
        ballPalette[3][1] = new Color(24 / 255f, 13 / 255f, 157 / 255f, 1);
        ballPalette[3][2] = new Color(146 / 255f, 71 / 255f, 221 / 255f, 1);
        ballPalette[3][3] = new Color(146 / 255f, 71 / 255f, 221 / 255f, 1);

        ballPalette[4][0] = new Color(0, 0, 0, 1);
        ballPalette[4][1] = new Color(19 / 255f, 123 / 255f, 202 / 255f, 1);
        ballPalette[4][2] = new Color(52 / 255f, 187 / 255f, 209 / 255f, 1);
        ballPalette[4][3] = new Color(1, 1, 1, 1);

        ballPalette[5][0] = new Color(0, 0, 0, 1);
        ballPalette[5][1] = new Color(221 / 255f, 93 / 255f, 4 / 255f, 1);
        ballPalette[5][2] = new Color(227 / 255f, 218 / 255f, 30 / 255f, 1);
        ballPalette[5][3] = new Color(1, 1, 1, 1);

        ballPalette[6][0] = new Color(0, 0, 0, 1);
        ballPalette[6][1] = new Color(53 / 255f, 59 / 255f, 72 / 255f, 1);
        ballPalette[6][2] = new Color(130 / 255f, 167 / 255f, 157 / 255f, 1);
        ballPalette[6][3] = new Color(1, 1, 1, 1);

        ballPalette[7][0] = new Color(0, 0, 0, 1);
        ballPalette[7][1] = new Color(29 / 255f, 177 / 255f, 82 / 255f, 1);
        ballPalette[7][2] = new Color(71 / 255f, 221 / 255f, 189 / 255f, 1);
        ballPalette[7][3] = new Color(1, 1, 1, 1);

        ballPalette[8][0] = new Color(0, 0, 0, 1);
        ballPalette[8][1] = new Color(139 / 255f, 8 / 255f, 60 / 255f, 1);
        ballPalette[8][2] = new Color(204 / 255f, 49 / 255f, 255 / 255f, 1);
        ballPalette[8][3] = new Color(1, 1, 1, 1);

        ballPalette[9][0] = new Color(0, 0, 0, 1);
        ballPalette[9][1] = new Color(14 / 255f, 108 / 255f, 44 / 255f, 1);
        ballPalette[9][2] = new Color(0 / 255f, 221 / 255f, 32 / 255f, 1);
        ballPalette[9][3] = new Color(1, 1, 1, 1);
        /*
        ballPalette[1][0] = new Color(0, 0, 0, 1);
        ballPalette[1][1] = new Color(221 / 255f, 93 / 255f, 4 / 255f, 1);
        ballPalette[1][2] = new Color(227 / 255f, 218 / 255f, 30 / 255f, 1);
        ballPalette[1][3] = new Color(1, 1, 1, 1);
        ballPalette[2][0] = new Color(0, 0, 0, 1);
        ballPalette[2][1] = new Color(8 / 255f, 100 / 255f, 38 / 255f, 1);
        ballPalette[2][2] = new Color(41 / 255f, 199 / 255f, 156 / 255f, 1);
        ballPalette[2][3] = new Color(135 / 255f, 228 / 255f, 247 / 255f, 1);
        ballPalette[3][0] = new Color(0, 0, 0, 1);
        ballPalette[3][1] = new Color(139 / 255f, 8 / 255f, 60 / 255f, 1);
        ballPalette[3][2] = new Color(208 / 255f, 8 / 255f, 251 / 255f, 1);
        ballPalette[3][3] = new Color(229 / 255f, 192 / 255f, 1, 1);
        */

        Pixmap pixmap_fullscreen = new Pixmap((int) Main.width, (int) Main.height + 3, Pixmap.Format.RGBA8888);

        tex_tilt = new Texture(pixmap_fullscreen);
        tex_random = new Texture(pixmap_fullscreen);
        tex_fade = new Texture(pixmap_fullscreen);
        pixmap_fullscreen.setColor(0, 0, 0, 1);
        pixmap_fullscreen.fill();
        tex_fullscreen = new Texture(pixmap_fullscreen);

        ballRadius = new float[]{4, 7, 9.5f};
    }

    public static void queueAssets() {
        Main.assets.load("images/ball_small.png", Texture.class);
        Main.assets.load("images/ball_medium.png", Texture.class);
        Main.assets.load("images/ball_large.png", Texture.class);
        Main.assets.load("images/ball_square_small.png", Texture.class);
        Main.assets.load("images/ball_square_medium.png", Texture.class);
        Main.assets.load("images/ball_square_large.png", Texture.class);
        Main.assets.load("images/ball_face_small.png", Texture.class);
        Main.assets.load("images/ball_face_medium.png", Texture.class);
        Main.assets.load("images/ball_face_large.png", Texture.class);
        Main.assets.load("images/ball_bad.png", Texture.class);
        Main.assets.load("images/ball_egg_small.png", Texture.class);
        Main.assets.load("images/ball_egg_medium.png", Texture.class);
        Main.assets.load("images/ball_egg_large.png", Texture.class);
        Main.assets.load("images/ball_brain_small.png", Texture.class);
        Main.assets.load("images/ball_brain_medium.png", Texture.class);
        Main.assets.load("images/ball_brain_large.png", Texture.class);
        Main.assets.load("images/ball_rasp_small.png", Texture.class);
        Main.assets.load("images/ball_rasp_medium.png", Texture.class);
        Main.assets.load("images/ball_rasp_large.png", Texture.class);
        Main.assets.load("images/ball_hl_small.png", Texture.class);
        Main.assets.load("images/ball_hl_medium.png", Texture.class);
        Main.assets.load("images/ball_hl_large.png", Texture.class);
        Main.assets.load("images/ball_apple_small.png", Texture.class);
        Main.assets.load("images/ball_apple_medium.png", Texture.class);
        Main.assets.load("images/ball_apple_large.png", Texture.class);
        Main.assets.load("images/ball_moon_small.png", Texture.class);
        Main.assets.load("images/ball_moon_medium.png", Texture.class);
        Main.assets.load("images/ball_moon_large.png", Texture.class);
        Main.assets.load("images/crown.png", Texture.class);
        Main.assets.load("images/bumper.png", Texture.class);
        Main.assets.load("images/tabletop.png", Texture.class);
        Main.assets.load("images/explosion_0.png", Texture.class);
        Main.assets.load("images/explosion_1.png", Texture.class);
        Main.assets.load("images/explosion_2.png", Texture.class);
        Main.assets.load("images/explosion_3.png", Texture.class);
        Main.assets.load("images/explosion_4.png", Texture.class);
        Main.assets.load("images/explosion_5.png", Texture.class);
        Main.assets.load("images/explosion_6.png", Texture.class);
        Main.assets.load("images/explosion_7.png", Texture.class);
        Main.assets.load("images/explosion_8.png", Texture.class);
        Main.assets.load("images/explosion_9.png", Texture.class);
        Main.assets.load("images/explosion_10.png", Texture.class);
        Main.assets.load("images/explosion_11.png", Texture.class);
        Main.assets.load("images/explosion_12.png", Texture.class);
        Main.assets.load("images/ballShard_0.png", Texture.class);
        Main.assets.load("images/ballShard_1.png", Texture.class);
        Main.assets.load("images/ballShard_2.png", Texture.class);
        Main.assets.load("images/sign_number_small.png", Texture.class);
        Main.assets.load("images/watermark.png", Texture.class);
        Main.assets.load("images/text_youare.png", Texture.class);
        Main.assets.load("images/bullet.png", Texture.class);
        Main.assets.load("images/text_welcome.png", Texture.class);
        Main.assets.load("images/buttonPressed_toGame.png", Texture.class);
        Main.assets.load("images/text_comingsoon.png", Texture.class);
        Main.assets.load("images/button_toGame.png", Texture.class);
        Main.assets.load("images/button_leaderBoards.png", Texture.class);
        Main.assets.load("images/buttonPressed_leaderBoards.png", Texture.class);
        Main.assets.load("images/buttonPressed_shop.png", Texture.class);
        Main.assets.load("images/button_pause.png", Texture.class);
        Main.assets.load("images/buttonPressed_pause.png", Texture.class);
        Main.assets.load("images/button_resume.png", Texture.class);
        Main.assets.load("images/buttonPressed_resume.png", Texture.class);
        Main.assets.load("images/button_shop_new.png", Texture.class);
        Main.assets.load("images/buttonPressed_shop_new.png", Texture.class);
        Main.assets.load("images/button_exit.png", Texture.class);
        Main.assets.load("images/buttonPressed_exit.png", Texture.class);
        Main.assets.load("images/text_paused.png", Texture.class);
        Main.assets.load("images/statisticsBackground.png", Texture.class);
        Main.assets.load("images/text_slowdown.png", Texture.class);
        Main.assets.load("images/symbol_cross.png", Texture.class);
        Main.assets.load("images/symbol_checkmark.png", Texture.class);
        Main.assets.load("images/honey.png", Texture.class);
        Main.assets.load("images/numbers/number_0.png", Texture.class);
        Main.assets.load("images/numbers/number_1.png", Texture.class);
        Main.assets.load("images/numbers/number_2.png", Texture.class);
        Main.assets.load("images/numbers/number_3.png", Texture.class);
        Main.assets.load("images/numbers/number_4.png", Texture.class);
        Main.assets.load("images/numbers/number_5.png", Texture.class);
        Main.assets.load("images/numbers/number_6.png", Texture.class);
        Main.assets.load("images/numbers/number_7.png", Texture.class);
        Main.assets.load("images/numbers/number_8.png", Texture.class);
        Main.assets.load("images/numbers/number_9.png", Texture.class);
        Main.assets.load("images/numbers/number_s_0.png", Texture.class);
        Main.assets.load("images/numbers/number_s_1.png", Texture.class);
        Main.assets.load("images/numbers/number_s_2.png", Texture.class);
        Main.assets.load("images/numbers/number_s_3.png", Texture.class);
        Main.assets.load("images/numbers/number_s_4.png", Texture.class);
        Main.assets.load("images/numbers/number_s_5.png", Texture.class);
        Main.assets.load("images/numbers/number_s_6.png", Texture.class);
        Main.assets.load("images/numbers/number_s_7.png", Texture.class);
        Main.assets.load("images/numbers/number_s_8.png", Texture.class);
        Main.assets.load("images/numbers/number_s_9.png", Texture.class);
        Main.assets.load("images/numbers/number_m_0.png", Texture.class);
        Main.assets.load("images/numbers/number_m_1.png", Texture.class);
        Main.assets.load("images/numbers/number_m_2.png", Texture.class);
        Main.assets.load("images/numbers/number_m_3.png", Texture.class);
        Main.assets.load("images/numbers/number_m_4.png", Texture.class);
        Main.assets.load("images/numbers/number_m_5.png", Texture.class);
        Main.assets.load("images/numbers/number_m_6.png", Texture.class);
        Main.assets.load("images/numbers/number_m_7.png", Texture.class);
        Main.assets.load("images/numbers/number_m_8.png", Texture.class);
        Main.assets.load("images/numbers/number_m_9.png", Texture.class);
        Main.assets.load("images/numbers/number_h_0.png", Texture.class);
        Main.assets.load("images/numbers/number_h_1.png", Texture.class);
        Main.assets.load("images/numbers/number_h_2.png", Texture.class);
        Main.assets.load("images/numbers/number_h_3.png", Texture.class);
        Main.assets.load("images/numbers/number_h_4.png", Texture.class);
        Main.assets.load("images/numbers/number_h_5.png", Texture.class);
        Main.assets.load("images/numbers/number_h_6.png", Texture.class);
        Main.assets.load("images/numbers/number_h_7.png", Texture.class);
        Main.assets.load("images/numbers/number_h_8.png", Texture.class);
        Main.assets.load("images/numbers/number_h_9.png", Texture.class);
        Main.assets.load("images/numbers/number_b_0.png", Texture.class);
        Main.assets.load("images/numbers/number_b_1.png", Texture.class);
        Main.assets.load("images/numbers/number_b_2.png", Texture.class);
        Main.assets.load("images/numbers/number_b_3.png", Texture.class);
        Main.assets.load("images/numbers/number_b_4.png", Texture.class);
        Main.assets.load("images/numbers/number_b_5.png", Texture.class);
        Main.assets.load("images/numbers/number_b_6.png", Texture.class);
        Main.assets.load("images/numbers/number_b_7.png", Texture.class);
        Main.assets.load("images/numbers/number_b_8.png", Texture.class);
        Main.assets.load("images/numbers/number_b_9.png", Texture.class);
        Main.assets.load("images/number_sign.png", Texture.class);
        Main.assets.load("images/bomb_white.png", Texture.class);
        Main.assets.load("images/button_danger.png", Texture.class);
        Main.assets.load("images/buttonPressed_danger.png", Texture.class);
        Main.assets.load("images/button_replay.png", Texture.class);
        Main.assets.load("images/buttonPressed_replay.png", Texture.class);
        Main.assets.load("images/title_o.png", Texture.class);
        Main.assets.load("images/title_r.png", Texture.class);
        Main.assets.load("images/title_b.png", Texture.class);
        Main.assets.load("images/title_a.png", Texture.class);
        Main.assets.load("images/title_l.png", Texture.class);
        Main.assets.load("images/title_s.png", Texture.class);
        Main.assets.load("images/title_c.png", Texture.class);
        Main.assets.load("images/title_m.png", Texture.class);
        Main.assets.load("images/title_balls.png", Texture.class);
        Main.assets.load("images/ttptext.png", Texture.class);
        Main.assets.load("images/HLeditie.png", Texture.class);
        Main.assets.load("images/youlost.png", Texture.class);
        Main.assets.load("images/button_options.png", Texture.class);
        Main.assets.load("images/buttonPressed_options.png", Texture.class);
        Main.assets.load("images/button_shop.png", Texture.class);
        Main.assets.load("images/blueEgg.png", Texture.class);
        Main.assets.load("images/shopSpot.png", Texture.class);
        Main.assets.load("images/shopSpotPressed.png", Texture.class);
        Main.assets.load("images/buttonPressed_cross.png", Texture.class);
        Main.assets.load("images/text_highscore.png", Texture.class);
        Main.assets.load("images/button_cross.png", Texture.class);
        Main.assets.load("images/lockedBall.png", Texture.class);
        Main.assets.load("images/symbol_orb.png", Texture.class);
        Main.assets.load("images/symbol_selected.png", Texture.class);
        Main.assets.load("images/orbCountBackground.png", Texture.class);
        Main.assets.load("images/orbCounter.png", Texture.class);
        Main.assets.load("images/oxigenoxide.png", Texture.class);
        Main.assets.load("images/goldenEgg.png", Texture.class);
        Main.assets.load("images/orb.png", Texture.class);
        Main.assets.load("images/spike_1.png", Texture.class);
        Main.assets.load("images/spike_2.png", Texture.class);
        Main.assets.load("images/spike_3.png", Texture.class);
        Main.assets.load("images/spike_4.png", Texture.class);
        Main.assets.load("images/text_orbs.png", Texture.class);
        Main.assets.load("images/symbol_plus.png", Texture.class);
        Main.assets.load("images/text_score.png", Texture.class);
        Main.assets.load("images/confetti_red.png", Texture.class);
        Main.assets.load("images/confetti_blue.png", Texture.class);
        Main.assets.load("images/confetti_green.png", Texture.class);
        Main.assets.load("images/tutorialMode.png", Texture.class);
        Main.assets.load("images/hand_0.png", Texture.class);
        Main.assets.load("images/hand_1.png", Texture.class);
        Main.assets.load("images/pin.png", Texture.class);
        Main.assets.load("images/button_tutorial.png", Texture.class);
        Main.assets.load("images/text_player.png", Texture.class);
        Main.assets.load("images/buttonPressed_tutorial.png", Texture.class);
        Main.assets.load("images/tutorialHole.png", Texture.class);
        Main.assets.load("images/cat.png", Texture.class);
        Main.assets.load("images/badSmile_1.png", Texture.class);
        Main.assets.load("images/badSmile_2.png", Texture.class);
        Main.assets.load("images/badSmile_3.png", Texture.class);
        Main.assets.load("images/badSmile_4.png", Texture.class);
        Main.assets.load("images/badSmile_5.png", Texture.class);
        Main.assets.load("images/gap.png", Texture.class);
        Main.assets.load("images/cannon_base.png", Texture.class);
        Main.assets.load("images/shield.png", Texture.class);
        Main.assets.load("images/bomb.png", Texture.class);
        Main.assets.load("images/shield_shine.png", Texture.class);
        Main.assets.load("images/cannon_gun.png", Texture.class);
        Main.assets.load("images/cannon_shine.png", Texture.class);
        Main.assets.load("images/collectable_shield.png", Texture.class);
        Main.assets.load("images/versionBarShade.png", Texture.class);
        Main.assets.load("images/versionBarOutline.png", Texture.class);
        Main.assets.load("images/scrollGroove.png", Texture.class);
        Main.assets.load("images/scrollNotch.png", Texture.class);
        Main.assets.load("images/text_v_standard.png", Texture.class);
        Main.assets.load("images/text_v_noAds.png", Texture.class);
        Main.assets.load("images/text_v_noEffects.png", Texture.class);
        Main.assets.load("images/text_v_noShop.png", Texture.class);
        Main.assets.load("images/text_v_noMusic.png", Texture.class);
        Main.assets.load("images/text_v_hard.png", Texture.class);
        Main.assets.load("images/text_v_noLevels.png", Texture.class);
        Main.assets.load("images/text_v_noScore.png", Texture.class);
        Main.assets.load("images/buttonPressed_info.png", Texture.class);
        Main.assets.load("images/button_info.png", Texture.class);
    }
}
