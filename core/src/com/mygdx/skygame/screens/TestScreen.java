package com.mygdx.skygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.skygame.SkyRocketeers;

/**
 * Created by alexa on 2/8/2017.
 */

public class TestScreen extends AbstractScreen {

    private GlyphLayout layout;
    private BitmapFont font;

    public TestScreen(SkyRocketeers game) {
        //Game reference + stage init
        super(game);

        //Text
        layout = new GlyphLayout();
        font = game.assets.get("images/fonts/whitefont.fnt",BitmapFont.class);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        game.guiBatch.begin();
        font.draw(game.guiBatch, layout, 0, layout.height);
        game.guiBatch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        super.show();

        String screen = "Test Screen";
        layout.setText(font,screen);

        Gdx.app.log("1", "Test Screen");
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}

