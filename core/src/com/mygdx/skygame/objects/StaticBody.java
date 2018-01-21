package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;

import static com.mygdx.skygame.utils.Constants.BIT_TERRAIN;

/**
 * Created by alexa on 2/21/2017.
 */
public class StaticBody extends Box2dObject {

    public StaticBody(float x, float y, float radius) {
        super(x, y, radius);
    }

    public StaticBody(float x, float y, float radius, boolean isSensor) {
        super(x, y, radius);
        this.isSensor = isSensor;
    }

    public StaticBody(float x, float y, float radius, float density, float friction, float restitution) {
        super(x, y, radius, density, friction, restitution);
    }

    public StaticBody(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public StaticBody(float x, float y, float width, float height, float density, float friction, float restitution) {
        super(x, y, width, height, density, friction, restitution);
    }

    public void initialize() {
        //(boolean isRect, String bodyType, float density, float friction, float restitution)
        setBody(true,"static",density,friction,restitution,isSensor);
        setFilter(BIT_TERRAIN);
        hit = assetManager.get("sfx/hitgrass.wav",Sound.class);
    }

    @Override
    public void kill() {
        if(!isDead) {
            super.kill();
        }
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
