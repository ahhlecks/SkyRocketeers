package com.mygdx.skygame.data;

import com.badlogic.gdx.audio.Sound;
import static com.mygdx.skygame.SkyRocketeers.assets;

/**
 * Created by alexa on 3/10/2017.
 */
public class Gun {
    public String name = "Default";
    public float force = 80f;
    public float accuracy = .8f; // 1.0f is 100% accuracy
    public float precision = 0.1f; // the lower the better, 1 = very imprecise
    public float fireRate = 0.2f;
    public float burstRate = 0;
    public int burstMax = 0;
    public float chargeDelay = 0f;
    public boolean canHold = true;
    public float radius = 2.0f;
    public float density = 1.0f;
    public float friction = .5f;
    public float restitution = .5f;
    public int damage = 1;
    public boolean isProjectile = false;
    public boolean bypassable = true; //means that you can repeatedly push the trigger to get a faster fire rate
    public int maxBounces = 0; //bounces before bullets disappear
    public float killTime = 5f; //seconds before bullets disappear
    public Sound chargeSound;
    public Sound shootSound;

    public Gun() {
        name = "Default";
        force = 90f;
        accuracy = .8f;
        precision = 0.1f;
        fireRate = 0.2f;
        burstRate = 0;
        burstMax = 1;
        chargeDelay = 0f;
        canHold = true;
        radius = 2.0f;
        density = 1.0f;
        friction = .5f;
        restitution = .5f;
        damage = 1;
        isProjectile = false;
        bypassable = true;
        maxBounces = 0;
        killTime = 5f;
        chargeSound = assets.get("sfx/charge.wav",Sound.class);
        shootSound = assets.get("sfx/shoot_default.wav",Sound.class);
    }

    public Gun(String name, float force, float accuracy, float precision, float fireRate, float burstRate, int burstMax,
               float chargeDelay, boolean canHold, float radius, float density, float friction, float restitution,
               int damage, boolean isProjectile, boolean bypassable, int maxBounces, float killTime, Sound chargeSound, Sound shootSound) {
        this.name = name;
        this.force = force;
        this.accuracy = accuracy;
        this.precision = precision;
        this.fireRate = fireRate;
        this.burstRate = burstRate;
        this.burstMax = Math.max(1,burstMax);
        this.chargeDelay = chargeDelay;
        this.canHold = canHold;
        this.radius = radius;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.damage = damage;
        this.isProjectile = isProjectile;
        this.bypassable = bypassable;
        this.maxBounces = maxBounces;
        this.killTime = killTime;
        this.chargeSound = chargeSound;
        this.shootSound = shootSound;
    }

    public void playCharge() {
        chargeSound.play();
    }

    public void stopCharge() {
        chargeSound.stop();
    }
}
