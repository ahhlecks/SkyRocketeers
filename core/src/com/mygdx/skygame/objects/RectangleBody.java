package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;

import static com.mygdx.skygame.utils.Constants.BIT_TERRAIN;

/**
 * Created by alexa on 4/14/2017.
 */
public class RectangleBody extends Box2dObject {

    public RectangleBody(float x, float y, float width, float height) {
        super(x, y, width, height, "grass");
        this.bodyType = "static";
        this.isSensor = false;
    }

    public RectangleBody(float x, float y, float width, float height, String material) {
        super(x, y, width, height, material);
        this.bodyType = "static";
        this.isSensor = false;
    }

    public RectangleBody(float x, float y, float width, float height, String material, String bodyType) {
        super(x, y, width, height, material);
        this.bodyType = bodyType;
        this.isSensor = false;
    }

    public RectangleBody(float x, float y, float width, float height, String material, String bodyType, boolean isSensor) {
        super(x, y, width, height, material);
        this.bodyType = bodyType;
        this.isSensor = isSensor;
    }

    public void initialize() {
        setHitSound(assetManager.get("sfx/hit" + material + ".wav",Sound.class));
        setBody(true,bodyType,density,friction,restitution,isSensor);
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
