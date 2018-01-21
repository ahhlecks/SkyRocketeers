package com.mygdx.skygame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LoadGame {

    public LoadGame() {
        FileHandle file = Gdx.files.local("myfile.txt");
        //file.write();
    }
}
