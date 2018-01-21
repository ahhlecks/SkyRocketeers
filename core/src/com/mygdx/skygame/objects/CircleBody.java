package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;

import static com.mygdx.skygame.utils.Constants.BIT_TERRAIN;

/**
 * Created by alexa on 4/14/2017.
 */
public class CircleBody extends Box2dObject {

    public CircleBody(float x, float y, float radius) {
        super(x, y, radius, "grass");
        this.bodyType = "static";
        this.isSensor = false;
    }

    public CircleBody(float x, float y, float radius, String material) {
        super(x, y, radius, material);
        this.bodyType = "static";
        this.isSensor = false;
    }

    public CircleBody(float x, float y, float radius, String material, String bodyType) {
        super(x, y, radius, material);
        this.bodyType = bodyType;
        this.isSensor = false;
    }

    public CircleBody(float x, float y, float radius, String material, String bodyType, boolean isSensor) {
        super(x, y, radius, material);
        this.bodyType = bodyType;
        this.isSensor = isSensor;
    }

    public void initialize() {
        setHitSound(assetManager.get("sfx/hit" + material + ".wav",Sound.class));
        setBody(false,bodyType,density,friction,restitution,isSensor);
        setFilter(BIT_TERRAIN);
    }

    @Override
    public void kill() {
        super.kill();
    }

    public void dispose() {
        if(getBody().getFixtureList() != null) {
            for (int i = 0; i < getBody().getFixtureList().size; i++) {
                getBody().destroyFixture(getBody().getFixtureList().get(i));
            }
        }
        super.dispose();
    }
}
