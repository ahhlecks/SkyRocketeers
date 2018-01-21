package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import static com.mygdx.skygame.utils.Constants.*;

/**
 * Created by alexa on 3/12/2017.
 */
public class Collectable extends DynamicBody {

    protected String type;
    protected String name;
    private Sound select;

    public Collectable(float x, float y, float radius, String type, String name) {
        super(x, y, radius);
        this.type = type;
        this.name = name;
    }

    public void initialize() {
        isCollectable = true;
        setBody(false,"dynamic",density,friction,restitution,false);
        FixtureDef sensorDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(getRadius()/PPM+2/PPM);
        sensorDef.shape = shape;
        sensorDef.isSensor = true;
        sensorDef.filter.categoryBits = BIT_SENSOR;
        sensorDef.filter.maskBits = BIT_TERRAIN | BIT_PLAYER;
        getBody().createFixture(sensorDef).setUserData(this);
        hit = assetManager.get("sfx/hitgrass.wav",Sound.class);
        select = assetManager.get("sfx/select.wav",Sound.class);
    }

    public void pickUp(Player target) { //make the extending class @Override pickUp or else nothing will happen
        kill();
    }

    public void kill() {
        select.play();
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
