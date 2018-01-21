package com.mygdx.skygame.data;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import static com.mygdx.skygame.SkyRocketeers.assets;

/**
 * Created by alexa on 3/10/2017.
 */
public class Guns {
    public static Array<Gun> Guns = new Array<Gun>();

    public static void createList() {
        //String name, float force, float accuracy, float precision, float fireRate, float burstRate, int burstMax,
        //float chargeDelay, boolean canHold, float radius, float density, float friction, float restitution,
        //int damage, boolean isProjectile, boolean bypassable, int maxBounces, float killTime,
        //Sound charge,
        //Sound hit

        // 1.0f accuracy is 100% accuracy
        // the lower the precision value, the more precise the weapon will be (0 is total precision and 1 is very imprecise)
        // the bypassable variable describes if a gun can be single shot faster than it's fire rate

        Guns.add(new Gun("Default",90,.6f,.05f,0.2f,0,0,
                0,true,2.0f,1,0.5f,0.5f,
                1,false,false,0,6.0f,
                assets.get("sfx/charge.wav",Sound.class),
                assets.get("sfx/shoot_default.wav",Sound.class)));
        Guns.add(new Gun("SuperGun",110,.5f,.08f,0.06f,0.2f,4,
                0,true,2.0f,1,0f,1f,
                5,false,true,0,6.0f,
                assets.get("sfx/charge.wav",Sound.class),
                assets.get("sfx/shoot_default.wav",Sound.class)));
        Guns.add(new Gun("ChargeWeapon",170,1f,0f,0.08f,.5f,3,
                .8f,false,3.0f,1,0f,1f,
                10,false,false,1,8.0f,
                assets.get("sfx/charge.wav",Sound.class),
                assets.get("sfx/shoot_chargeweapon.wav",Sound.class)));
    }
}
