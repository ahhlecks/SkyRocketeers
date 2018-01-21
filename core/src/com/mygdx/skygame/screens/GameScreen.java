package com.mygdx.skygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.skygame.SkyRocketeers;
import com.mygdx.skygame.controllers.Ps4PlayerController;
import com.mygdx.skygame.managers.Box2dObjectManager;
import com.mygdx.skygame.listeners.GameContactListener;
import com.mygdx.skygame.objects.*;
import com.mygdx.skygame.utils.TiledObjectUtil;

import static com.mygdx.skygame.SkyRocketeers.HEIGHT;
import static com.mygdx.skygame.SkyRocketeers.assets;
import static com.mygdx.skygame.utils.Constants.PPM;

/**
 * Created by alexa on 2/7/2017.
 * DONE: implement an object manager class to handle all of the world's Box2dObjects
 * DONE: implement a fixed timestep
 * TODO: implement asset manager
 * DONE: fix the pausing feature (pressing pause needs to be outside the timestep)
 * TODO: find out what is causing the batch to be laggy (batch needs to be drawn within timestep aka in the object manager draw function)
 * TODO: tmx object parser to create maps
 */

public class GameScreen extends AbstractScreen{

    private World world;
    public Box2dObjectManager objectManager;
    private Box2DDebugRenderer b2dr;
    private TiledMap map;

    public Player player;
    private float accumulator = 0;
    public float timestep = 1.0f / (float) SkyRocketeers.fps;
    public boolean isPaused = false;

    private BitmapFont font;
    private GlyphLayout layout;
    private GlyphLayout layout2;
    private Music music;

    private Ps4PlayerController controller;

    public GameScreen(SkyRocketeers game) {
        //Game reference + stage init
        super(game);
    }

    @Override
    public void render (float delta) {
        if(!isPaused) {
            doTimeStep(delta);
        }

        controller.update(delta,player);

        super.render(delta);
        game.batch.begin();
        objectManager.draw();
        game.batch.end();

        b2dr.render(world,game.camera.combined.scl(PPM));

        if (Gdx.input.justTouched()) {
            game.setScreen(new SplashScreen(game));
            dispose();
        }

        layout.setText(font,"Java Heap: " + Gdx.app.getJavaHeap());
        layout2.setText(font,"Native Heap: " + Gdx.app.getNativeHeap());
        game.guiBatch.begin();
        font.draw(game.guiBatch, layout, 0, HEIGHT);
        font.draw(game.guiBatch, layout2, 0, HEIGHT-layout2.height);
        game.guiBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        super.show();
        //Create World
        world = new World(new Vector2(0,-14f), false);
        world.setContactListener(new GameContactListener(this));
        world.setVelocityThreshold(0f);
        objectManager = new Box2dObjectManager(this);
        b2dr = new Box2DDebugRenderer();

        //Tiled Map Loader (New create object in world)
        map = new TmxMapLoader().load("maps/level1.tmx");

        //Create Box2dObjects in world
        //objectManager.add(player);
        TiledObjectUtil.parseTiledObjectLayer(map,"terrain",objectManager);
        TiledObjectUtil.parseTiledObjectLayer(map,"entities",objectManager);
        player = objectManager.getPlayer();
        //player.setRestitution(0.05f);
        //objectManager.add(new StaticBody(0,-8,1024,8));
        //objectManager.add(new DynamicBody(600,128,16,16,1.0f,0.1f,0f));
        /*
        Array path = new Array<Vector2>();
        path.add(new Vector2(-128,64));
        path.add(new Vector2(128,64));
        objectManager.add(new KinematicBody(0,64,16,16,path,4,true));
        */
        //objectManager.add(new GunCollectable(406,64,"SuperGun"));
        //objectManager.add(new GunCollectable(512,90,"ChargeWeapon"));

        //Text
        font = assets.get("images/fonts/whitefont.fnt",BitmapFont.class);
        layout = new GlyphLayout();
        layout2 = new GlyphLayout();


        //Audio
        music = assets.get("music/towardevening.mp3", Music.class);
        music.setVolume(0.5f);
        music.play();

        //Controller init
        controller = new Ps4PlayerController(game.deadzone, this);

        Gdx.app.log("3", "Game Screen");
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        //Called before the window closes
    }

    @Override
    public void resume() {
    }

    // Old TimeStep
    private void doTimeStep(float delta) {
        float frameTime = Math.min(delta,0.25f);
        accumulator += frameTime;

        while (accumulator >= timestep) {

            world.step(timestep, 6,2);

            objectManager.update(timestep);

            cameraUpdate();

            objectManager.emptyQuarantine();
            accumulator -= timestep;
        }
    }

    private void cameraUpdate() {
        Vector3 pos = game.camera.position;
        pos.x = player.getPosition().x;
        pos.y = player.getPosition().y+8/PPM;
        game.camera.position.set(pos);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose () {
        b2dr.dispose();
        objectManager.dispose();
        map.dispose();
        if(world != null) {
            world.dispose();
        }
    }
}