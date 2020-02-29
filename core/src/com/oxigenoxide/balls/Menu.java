package com.oxigenoxide.balls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.oxigenoxide.balls.objects.TTPText;
import com.oxigenoxide.balls.objects.Title;
import com.oxigenoxide.balls.objects.button.Button;
import com.oxigenoxide.balls.objects.button.Button_Exit;
import com.oxigenoxide.balls.objects.button.Button_Options;
import com.oxigenoxide.balls.objects.button.Button_Tutorial;

public class Menu extends Scene {
    Title title;
    TTPText ttpText;
    Button button_options;
    Button button_tutorial;
    Button button_exit;
    boolean buttonTouched;
    public Menu(){
        title=new Title();
        ttpText=new TTPText(Main.height/2);
        button_options=new Button_Options(new Vector2(2,2));
        button_tutorial=new Button_Tutorial(new Vector2(12,30));
        button_exit=new Button_Exit(new Vector2(2,Main.height-17));
    }

    @Override
    public void show() {
        Main.setAdVisibility(true);
    }

    @Override
    public void update() {
        title.update();
        ttpText.update();
        buttonTouched=false;

        if(button_options.isTouching()) {
            button_options.update();
            buttonTouched=true;
        }

        if(button_exit.isTouching()) {
            button_exit.update();
            buttonTouched=true;
        }

        if(button_tutorial.isTouching()) {
            button_tutorial.update();
            buttonTouched=true;
        }

        if(!button_options.isTouched && !buttonTouched && Gdx.input.justTouched()) {
            Main.amm.hide();
            Main.setSceneGame();
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer sr) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setShader(Res.shader_palette);
        Main.setPalette(Res.tableTopPalette[0]);
        batch.draw(Res.tex_tabletop, Main.width/2-Res.tex_tabletop.getWidth()/2, Main.height/2-Res.tex_tabletop.getHeight()/2);
        batch.setShader(null);
        batch.draw(Res.tex_hleditie,2,2);
        ttpText.render(batch);
        title.render(batch);
        button_exit.render(batch);
        button_tutorial.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
