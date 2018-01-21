package com.mygdx.skygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.skygame.screens.GameScreen;
import com.mygdx.skygame.screens.LoadingScreen;
import com.mygdx.skygame.screens.TestScreen;

public class SkyRocketeers extends Game {

	// Game Information ---------------------------------------//
	public static final String TITLE = "SkyRocketeers";
	public static final float VERSION = 0.2f;
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public static final float SCALE = 2f;
	public static final float SCALEWIDTH = WIDTH/SCALE;
	public static final float SCALEHEIGHT = HEIGHT/SCALE;
	public static final float ASPECTRATIO = HEIGHT/WIDTH;
	public static int fps = 60;
	public float deadzone = 0.05f; //controller deadzone
	//---------------------------------------------------------//

	public SpriteBatch batch;
	public SpriteBatch guiBatch;
	public OrthographicCamera camera;
	public OrthographicCamera guiCamera;
	public FitViewport viewport;
	public FitViewport guiViewport;
	public static AssetManager assets;
	public FPSLogger fpslog;

	@Override
	public void create () {
		//Init sprite batches
		batch = new SpriteBatch();
		guiBatch = new SpriteBatch();
		fpslog = new FPSLogger();

		//Init Asset Manager
		assets = new AssetManager();

		//Gameplay camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCALEWIDTH, SCALEHEIGHT);
		viewport = new FitViewport(camera.viewportWidth,camera.viewportHeight,camera);
		camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0); // centered

		//GUI camera
		guiCamera = new OrthographicCamera();
		guiCamera.setToOrtho(false, SCALEWIDTH, SCALEHEIGHT);
		guiViewport = new FitViewport(guiCamera.viewportWidth,guiCamera.viewportHeight,guiCamera);
		guiCamera.position.set(guiCamera.viewportWidth/2,guiCamera.viewportHeight/2,0); // centered

		//Set the screen
		loadScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			dispose();
			Gdx.app.exit();
		}
		//fpslog.log();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width,height);
		guiViewport.update(width,height);
	}

	public void loadScreen(Screen nextScreen) {
		setScreen(new LoadingScreen(this,nextScreen));
	}
	
	@Override
	public void dispose () {
		if(batch != null && assets != null) {
			batch.dispose();
			assets.dispose();
			super.dispose();
			getScreen().dispose();
		}
		if(guiBatch != null) {
			guiBatch.dispose();
		}
	}
}