package com.oxigenoxide.balls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.objects.button.Button;
import com.oxigenoxide.balls.objects.button.Button_Info;
import com.oxigenoxide.balls.objects.button.Button_ToGame;

public class Welcome extends Scene {
    Button button_toGame;
    int y;
    ResultsWindow rw;
    Button button_info;

    public Welcome() {
        y = (int) ((Main.height - 192) / 2);
        button_toGame = new Button_ToGame(new Vector2(11, Main.height - y - 92));
        rw = new ResultsWindow();
        button_info = new Button_Info(new Vector2(74, 84 + y));

    }

    @Override
    public void show() {
        Main.amm.hide();
    }

    @Override
    public void update() {
        if (button_toGame.isTouching())
            button_toGame.update();
        if (button_info.isTouching())
            button_info.update();
        rw.update();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.WHITE);
        sr.rect(0, y, Main.width, Res.tex_statisticsBackground.getHeight()); // White background behind results;
        sr.end();
        batch.begin();
        rw.render(batch, sr);
        batch.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);
        sr.rect(0, 0, Main.width, y); // Covers the bottom for longer displays;
        sr.rect(0, y + Res.tex_statisticsBackground.getHeight(), Main.width, Main.height); // Covers the top;
        sr.end();
        batch.begin();
        batch.draw(Res.tex_statisticsBackground, 0, y);
        button_info.render(batch);
        //batch.draw(Res.tex_text_comingsoon,Main.width/2-Res.tex_text_comingsoon.getWidth()/2,y+42);
        button_toGame.render(batch);
        batch.draw(Res.tex_text_welcome, 0, Main.height - Res.tex_text_welcome.getHeight() - 5 - y);
        Main.drawNumberSign(batch, Main.testerID, new Vector2(Main.width / 2, Main.height - 35 - y), 0, Res.tex_numberSign, 0);
        batch.end();

    }

    @Override
    public void dispose() {
    }

    class ResultsWindow {
        float[] fractions;
        int versions = 8;
        int selectedVersion = 0;
        float scrolledFraction;
        float scrolled;
        float scrolledMax;


        public ResultsWindow() {
            //fractions = new float[]{0.61f, 0.52f, 0.56f, 0.24f, 0.29f, 1, 0.31f, .47f};
            //fractions = new float[]{0.61f, 0.52f, 0.56f, 0.24f, 0.29f, 1, 0.31f, .47f};

            fractions = new float[8];
            float[] fractions_unsorted = new float[]{};
            fractions_unsorted=new float[]{1.00f,3.41f,0.96f,0.96f,0.62f,1.62f,1.49f,2.80f};
            for(int i=0;i<8;i++){
                fractions_unsorted[i]/=3.41f;
            }

            fractions[0]=fractions_unsorted[0];
            fractions[1]=fractions_unsorted[1];
            fractions[2]=fractions_unsorted[5];
            fractions[3]=fractions_unsorted[2];
            fractions[4]=fractions_unsorted[7];
            fractions[5]=fractions_unsorted[6];
            fractions[6]=fractions_unsorted[3];
            fractions[7]=fractions_unsorted[4];

            /*
            1,00
            3,41
            0,96
            0,96
            0,62
            1,62
            1,49
            2,80
            */
            //fractions = new float[]{0.61f, 0.52f, 0.56f, 0.24f, 0.29f, 1, 0.31f, .47f};

            //fractions = new float[]{0.61f, 0.52f, 1, 0.56f, .47f, 0.31f, 0.24f, 0.29f};
            scrolledMax = (versions - 4) * 16;
            selectedVersion = getVersionInTextureArray(Main.testerID % 10);
        }

        void update() {
            if(Gdx.input.isTouched() && !Gdx.input.justTouched()) {
                if (Main.pointInRectangle(Main.tap_begin[0].x, Main.tap_begin[0].y, 5, 6 + y, 87, 76)) {
                    scrolled -= Main.tap_delta[0].y;
                    scrolled = MathUtils.clamp(scrolled, -scrolledMax, 0);
                } else if (Main.pointInRectangle(Main.tap_begin[0].x, Main.tap_begin[0].y, 92, 6 + y, 11, 76)) {
                    scrolled = MathUtils.clamp((-1+(Main.tap[0].y - (6+y+6))/64f) * scrolledMax, -scrolledMax, 0);
                }
            }
            scrolledFraction = -scrolled / scrolledMax;
            if (Main.pointInRectangle(Main.tap[0].x, Main.tap[0].y, 11, y + 6, 78, 76))
                for (int i = 0; i < versions; i++) {
                    if (Gdx.input.justTouched())
                        if (Main.pointInRectangle(Main.tap[0].x, Main.tap[0].y, 11, y + 66 - i * 16 - scrolled, 78, 11)) {
                            selectedVersion = i;
                            Main.setVersion(getVersionID(selectedVersion));
                        }
                }
        }

        int getVersionID(int versionInTextureArray) {
            switch (versionInTextureArray) {
                case 0:
                    return 7;
                case 1:
                    return 0;
                case 2:
                    return 5;
                case 3:
                    return 6;
                case 4:
                    return 4;
                case 5:
                    return 3;
                case 6:
                    return 2;
                case 7:
                    return 1;
                default:
                    return -1;
            }
        }
        int getVersionInTextureArray(int versionID) {
            switch (versionID) {
                case 0:
                    return 1;
                case 1:
                    return 7;
                case 2:
                    return 6;
                case 3:
                    return 5;
                case 4:
                    return 4;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 0;
                case 8:
                    return 0;
                case 9:
                    return 0;
                default:
                    return -1;
            }
        }

        void render(SpriteBatch batch, ShapeRenderer sr) {
            //Bars color
            batch.end();
            sr.begin(ShapeRenderer.ShapeType.Filled);
            for (int i = 0; i < versions; i++) {
                sr.setColor(61 / 255f, 202 / 255f, 32 / 255f, 1);
                sr.rect(11, y + 66 - i * 16 - scrolled, Math.round(78 * fractions[i]), 11);
                sr.setColor(221 / 255f, 0, 0, 1);
                sr.rect(11 + Math.round(78 * fractions[i]), y + 66 - i * 16 - scrolled, Math.round(78 * (1 - fractions[i])), 11);
            }
            sr.end();
            //Bars shade and name
            batch.begin();
            for (int i = 0; i < versions; i++) {
                batch.draw(Res.tex_text_v[i], 11 + 39 - Res.tex_text_v[i].getWidth() / 2, y + 66 - i * 16 - scrolled + 3);
                if (selectedVersion == i)
                    batch.draw(Res.tex_versionBarOutline, 9, y + 64 - i * 16 - scrolled);
                else
                    batch.draw(Res.tex_versionBarShade, 11, y + 66 - i * 16 - scrolled);
            }
            //Scrollbar
            batch.draw(Res.tex_scrollGroove, 95, y + 9);
            batch.draw(Res.tex_scrollNotch, 94, y + 69 - 61 * scrolledFraction);
        }
    }
}
