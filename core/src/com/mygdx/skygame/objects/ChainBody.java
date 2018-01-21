package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.skygame.utils.Constants.BIT_TERRAIN;

/**
 * Created by alexa on 3/8/2017.
 */
public class ChainBody extends Box2dObject{

    private Shape cs;

    public ChainBody(ChainShape cs, String material) {
        super(cs,material);
        this.cs = cs;
    }

    public void initialize() {
        setHitSound(assetManager.get("sfx/hit" + material + ".wav",Sound.class));
        setChainBody(cs,density,friction,restitution);
        setFilter(BIT_TERRAIN);
    }

    public void kill() {
        super.kill();
    }

    public void dispose() {
        cs.dispose();
        if(getBody().getFixtureList() != null) {
            for (int i = 0; i < getBody().getFixtureList().size; i++) {
                getBody().destroyFixture(getBody().getFixtureList().get(i));
            }
        }
        super.dispose();
    }
}