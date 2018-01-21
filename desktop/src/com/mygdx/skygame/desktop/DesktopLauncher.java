package com.mygdx.skygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.skygame.SkyRocketeers;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = SkyRocketeers.TITLE + " v" + SkyRocketeers.VERSION;
		config.width = SkyRocketeers.WIDTH;
		config.height = SkyRocketeers.HEIGHT;
		config.backgroundFPS = -1;
		config.foregroundFPS = 0;
		config.audioDeviceSimultaneousSources = 8;
		config.initialBackgroundColor = Color.BLACK;
		config.allowSoftwareMode = true;
		new LwjglApplication(new SkyRocketeers(), config);
	}
}
