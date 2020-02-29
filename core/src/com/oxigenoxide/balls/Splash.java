package com.oxigenoxide.balls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.oxigenoxide.balls.objects.button.Button;

public class Splash extends Scene {
    Texture tex_splash;
    Texture tex_noInternet;
    Texture tex_wifiSymbol;
    Sprite sprite_wifiSymbol;
    Sprite sprite_noInternet;
    Sprite sprite;
    float alpha;
    float alpha_noInternet;
    int timeNoInternet;
    boolean goDown;
    int count;
    int dotRowLength = (int) Main.width / 2;

    public Splash() {
        tex_splash = new Texture("images/oxigenoxide.png");
        tex_wifiSymbol= new Texture("images/wifiSymbol.png");
        sprite_wifiSymbol=new Sprite(tex_wifiSymbol);
        sprite_wifiSymbol.setOrigin(8,2);
        sprite_wifiSymbol.setPosition(Main.width/2-sprite_wifiSymbol.getWidth()/2,Main.height/2+20);
        sprite_wifiSymbol.setAlpha(alpha_noInternet);
        tex_noInternet= new Texture("images/nointernet.png");
        sprite_noInternet=new Sprite(tex_noInternet);
        sprite_noInternet.setPosition(Main.width/2-tex_noInternet.getWidth()/2,Main.height/2 - 10);
        sprite_noInternet.setAlpha(alpha_noInternet);
        Res.queueAssets();
        sprite = new Sprite(tex_splash);
        sprite.setPosition(Main.width / 2 - tex_splash.getWidth() / 2, Main.height / 2 - tex_splash.getHeight() / 2);
    }

    @Override
    public void show() {
        Main.setAdVisibility(false);
    }

    @Override
    public void update() {
        Main.assets.update();

        if (Main.assets.isFinished() && Main.fbm.isSignedIn() && alpha == 0 && !Main.isLoaded && Main.fbm.isUpToDate()) {
            if(Main.signedIn) {
                Main.initializeResources();
                Main.onLoaded();
                Main.setSceneWelcome();
            }
        }
        if(!Main.signedIn) {
            if (timeNoInternet > 200) {
                alpha_noInternet = Math.min(alpha_noInternet + .05f, 1);
                sprite_noInternet.setAlpha(alpha_noInternet);
                sprite_wifiSymbol.setAlpha(alpha_noInternet);
                sprite_wifiSymbol.setRotation(sprite_wifiSymbol.getRotation() + 1);
            }
            timeNoInternet++;
        }
        if (!goDown)
            alpha = Math.min(1, alpha + .02f);
        else {
            alpha = Math.max(0, alpha - .02f);
        }
        if (alpha == 1) {
            count++;
            if (count > 60)
                goDown = true;
        }
        sprite.setAlpha(alpha);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(88 / 255f, 179 / 255f, 194 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        sprite_wifiSymbol.draw(batch);
        sprite_noInternet.draw(batch);
        batch.end();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    setColor(sr, Main.assets.isFinished());
                    break;
                case 1:
                    setColor(sr, Main.fbm.isSignedIn());
                    break;
                case 2:
                    setColor(sr, alpha == 0);
                    break;
                case 3:
                    setColor(sr, Main.isLoaded);
                    break;
                case 4:
                    setColor(sr, Main.signedIn);
                    break;
            }
            sr.circle(Main.width / 2 - dotRowLength / 2 + dotRowLength / 4 * i, 5, 2,15);
        }
        sr.end();
    }

    void setColor(ShapeRenderer sr, boolean b) {
        if (b)
            sr.setColor(1, 1, 1, 1);
        else
            sr.setColor(88/255f, 96/255f, 194/255f, 1);
    }

    @Override
    public void dispose() {
    }
}
