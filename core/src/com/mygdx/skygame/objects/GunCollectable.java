package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.skygame.data.Gun;
import com.mygdx.skygame.data.Guns;

/**
 * Created by alexa on 3/10/2017.
 */
public class GunCollectable extends Collectable {

    public Gun gun;

    public GunCollectable(float x, float y, float radius, String name) {
        super(x, y, radius, "gun", name);
    }

    public GunCollectable(float x, float y, String name) {
        super(x, y, 4, "gun", name);
    }

    public void initialize() {
        super.initialize();
        for(int i = 0; i < Guns.Guns.size; i++) {
            if(Guns.Guns.get(i).name == this.name) {
                gun = Guns.Guns.get(i);
            }
        }
        hit = assetManager.get("sfx/hitmetal.wav",Sound.class);
    }

    @Override
    public void pickUp(Player target) {
        target.addGun(gun);
        kill();
    }

    public void kill() {
        super.kill();
    }

    public void dispose() {
        super.dispose();
    }
}
