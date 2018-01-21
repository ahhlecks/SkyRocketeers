package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.skygame.utils.Constants.BIT_TERRAIN;

/**
 * Created by alexa on 2/24/2017.
 */
public class KinematicBody extends Box2dObject {
    Array<Vector2> path;
    private int currentNode = 0;
    public float speed = 0f;
    public float velX;
    public float velY;
    public float dx;
    public float dy;
    private boolean onPoint = false;
    private boolean isCircuit = true;

    public KinematicBody(float x, float y, float width, float height, Array<Vector2> path, float speed, boolean isCircuit) {
        super(x, y, width, height);
        this.path = path;
        this.speed = speed;
        this.isCircuit = isCircuit;
    }

    public void initialize() {
        //(boolean isRect, String bodyType, float density, float friction, float restitution)
        setBody(true,"kinematic",density,.8f,restitution,false);
        setFilter(BIT_TERRAIN);
        hit = assetManager.get("sfx/hitmetal.wav",Sound.class);
    }

    public void update(float delta) {
        if(!onPoint){
            moveTo(getNode(currentNode));
        } else {
            if(currentNode < path.size-1) {
                currentNode++;
                onPoint = false;
            } else {
                if(isCircuit) {
                    currentNode = 0;
                    onPoint = false;
                } else {
                    onPoint = true;
                    getBody().setLinearVelocity(0,0);
                    velX = 0;
                }
            }
        }
    }

    public Vector2 getNode(int currentNode) {
        return path.get(currentNode);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setCircuit(boolean isCircuit) {
        this.isCircuit = isCircuit;
    }

    public void moveTo(Vector2 point) {
        float angle = MathUtils.atan2((point.y-getPosition().y),(point.x-getPosition().x));
        //System.out.println(MathUtils.cos(slope));
        velX = speed*MathUtils.cos(angle);
        velY = speed*MathUtils.sin(angle);
        getBody().setLinearVelocity(velX, velY);
        if(point.dst(getPosition()) < 1) {
            setPosition(point.x,point.y);
            dx = velX;
            dy = velY;
            onPoint = true;
        }
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