package com.oxigenoxide.balls.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.oxigenoxide.balls.Game;
import com.oxigenoxide.balls.Main;
import com.oxigenoxide.balls.Res;

public class Cat {
    Vector2 pos;
    Body body;

    public Cat(float x, float y) {
        pos = new Vector2(x, y);
        createBody();
        body.setTransform(pos.x * Main.METERSPERPIXEL, (pos.y) * Main.METERSPERPIXEL, 0);
    }

    public void createBody() {
        body = Game.world.createBody(Res.bodyDef_static);
        Res.fixtureDef_circle.shape.setRadius(5 * Main.METERSPERPIXEL);
        body.createFixture(Res.fixtureDef_circle);
        body.setUserData(this);
        Filter filter = new Filter();
        filter.maskBits = (Res.MASK_PASSTHROUGH);
        filter.categoryBits = (Res.MASK_CAT);
        body.getFixtureList().first().setFilterData(filter);
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
        batch.draw(Res.tex_cat, pos.x - Res.tex_cat.getWidth() / 2, pos.y-5);
    }
    public void dispose(){
        Game.catsToRemove.add(this);
        body = Game.destroyBody(body);
    }
}
