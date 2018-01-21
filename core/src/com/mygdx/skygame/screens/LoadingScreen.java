package com.mygdx.skygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.skygame.SkyRocketeers;
import com.mygdx.skygame.data.Guns;

/**
 * Created by alexa on 2/8/2017.
 */

public class LoadingScreen extends AbstractScreen {

    private ShapeRenderer shapeRenderer;
    private Screen nextScreen;

    public LoadingScreen(SkyRocketeers game, Screen nextScreen) {
        super(game);
        this.nextScreen = nextScreen;
        queueAssets();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update();
    }

    public void update() {

        if(!game.assets.update()) {
            //loading bar
        } else {
            Guns.createList();
            game.setScreen(nextScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        super.show();
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

    private void queueAssets() {
        String directory = "images/fonts/";
        FileHandle[] files = Gdx.files.internal(directory).list();
        for(FileHandle file: files) {
            game.assets.load(directory+file.name(),BitmapFont.class);
        }
        directory = "images/backgrounds/";
        files = Gdx.files.internal(directory).list();
        for(FileHandle file: files) {
            if(file.name().startsWith("atlas")) {
                game.assets.load(directory+file.name(),TextureAtlas.class);
            } else {
                game.assets.load(directory+file.name(),Texture.class);
            }
        }
        directory = "images/characters/";
        files = Gdx.files.internal(directory).list();
        for(FileHandle file: files) {
            if(file.name().startsWith("atlas")) {
                game.assets.load(directory+file.name(),TextureAtlas.class);
            } else {
                game.assets.load(directory+file.name(),Texture.class);
            }
        }
        directory = "images/gui/";
        files = Gdx.files.internal(directory).list();
        for(FileHandle file: files) {
            if(file.name().startsWith("atlas")) {
                game.assets.load(directory+file.name(),TextureAtlas.class);
            } else {
                game.assets.load(directory+file.name(),Texture.class);
            }
        }
        directory = "music/";
        files = Gdx.files.internal(directory).list();
        for(FileHandle file: files) {
            game.assets.load(directory+file.name(),Music.class);
        }
        directory = "sfx/";
        files = Gdx.files.internal(directory).list();
        for(FileHandle file: files) {
            game.assets.load(directory+file.name(),Sound.class);
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}