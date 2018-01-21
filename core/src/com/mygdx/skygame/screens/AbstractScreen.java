package com.mygdx.skygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.skygame.SkyRocketeers;

import static com.mygdx.skygame.utils.Constants.PPM;

/**
 * Created by alexa on 2/13/2017.
 */
public abstract class AbstractScreen implements Screen {

    public SkyRocketeers game;

    public AbstractScreen(SkyRocketeers game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(game.camera.combined.scl(1/PPM));

        game.camera.update();
        game.guiCamera.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        game.viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.guiViewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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
