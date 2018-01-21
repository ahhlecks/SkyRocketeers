package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;

import static com.mygdx.skygame.utils.Constants.BIT_SENSOR;

/**
 * Created by alexa on 4/14/2017.
 */
public class Spawn extends CircleBody{

    public Spawn(float x, float y) {
        super(x, y, 1, "void", "static", true);
    }

    @Override
    public void initialize() {
        setBody(false,bodyType,density,friction,restitution,isSensor);
        setFilter(BIT_SENSOR, (byte) 0);
    }

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