package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.skygame.data.Gun;

import static com.mygdx.skygame.utils.Constants.*;

/**
 * Created by alexa on 2/20/2017.
 */
public class Player extends Box2dObject{
    public float jumpSpeed = 120f;//120
    public float speed = 20f; // normal walking speed
    public float maxWalkSpeed = 8f; // Max walking velocity
    public float maxBoostSpeed = 0f; // Don't change this setting
    public float terrainSpeedX;
    public float maxTerrainSpeedX = 20f; // Added speed from skiing
    public float maxSpeedX;
    public float boost = 4f; // Max boost velocity
    public float boostX = 1.4f; // 140% acceleration
    public float boostY = 6f; // Force against gravity 14N
    public float turnSpeed = 0;
    public float normalTurnSpeed = 10f;
    public float boostTurnSpeed = 11f;
    public float boostFriction = 0.01f;
    public float normalFriction = 1f;
    public float normalX = 0f;
    public float normalY = 0f;
    public KinematicBody platform;
    public float platformXSpeed = 0f;
    public float platformYSpeed = 0f;
    public boolean isBoosting = false;

    public float leftVerticalAxis = 0;
    public float leftHorizontalAxis = 0;
    public float rightVerticalAxis = 0;
    public float rightHorizontalAxis = 0;
    public float l2Axis = 0;
    public float r2Axis = 0;
    public boolean l2JustPressed;
    public boolean r2JustPressed;

    //Gun properties
    private Gun gun;
    private float fireTime;
    private int burst;
    private float burstTime;
    private float chargeTime;

    //Velocities
    public float xForce = 0;
    public float yForce = 0;
    public float x2Force = 0;
    public float y2Force = 0;

    //Contact Variables
    public boolean isTouchingDynamic = false;
    public int footContacts = 0;
    public int leftContacts = 0;
    public int rightContacts = 0;
    private byte foot = 0;
    private byte leftSide = 1;
    private byte rightSide = 2;
    private byte upper = 4;

    public Sound jump;
    public Sprite line;

    public Player(float x, float y, float radius) {
        super(x,y,radius);
    }

    public void initialize() {
        //(boolean isRect, String bodyType, float density, float friction, float restitution)
        setBody(false,"dynamic",density,friction,0,false);
        setFilter(BIT_PLAYER, (byte)(BIT_ENEMY_BULLET | BIT_ENEMY | BIT_TERRAIN | BIT_SENSOR));
        createSensors();
        jump = assetManager.get("sfx/jump.wav",Sound.class);
        line = new Sprite(assetManager.get("images/gui/line.png",Texture.class));
        gun = new Gun();
        fireTime = 0;
        burstTime = gun.burstRate;
        chargeTime = gun.chargeDelay;
    }

    public void createSensors() {
        FixtureDef sensorDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //create UpperBody
        shape.setAsBox(getRadius()/PPM,8f/PPM, new Vector2(0,8/PPM),0);
        sensorDef.shape = shape;
        sensorDef.density = 0;
        sensorDef.isSensor = false;
        sensorDef.filter.categoryBits = BIT_PLAYER;
        sensorDef.filter.maskBits = (byte)(BIT_ENEMY_BULLET | BIT_ENEMY | BIT_TERRAIN | BIT_SENSOR);
        getBody().createFixture(sensorDef).setUserData(upper);

        //create Foot
        shape.setAsBox(getRadius()/PPM-1/PPM,2f/PPM, new Vector2(0,-8/PPM),0);
        sensorDef.shape = shape;
        sensorDef.isSensor = true;
        sensorDef.filter.categoryBits = BIT_SENSOR;
        sensorDef.filter.maskBits = BIT_TERRAIN;
        getBody().createFixture(sensorDef).setUserData(foot);

        //create Left Side
        shape.setAsBox(2.5f/PPM,2.5f/PPM, new Vector2(-7/PPM,6/PPM),0);
        sensorDef.shape = shape;
        sensorDef.isSensor = true;
        sensorDef.filter.categoryBits = BIT_SENSOR;
        sensorDef.filter.maskBits = BIT_TERRAIN;
        getBody().createFixture(sensorDef).setUserData(leftSide);

        //create Right Side
        shape.setAsBox(2.5f/PPM,2.5f/PPM, new Vector2(7/PPM,6/PPM),0);
        sensorDef.shape = shape;
        sensorDef.isSensor = true;
        sensorDef.filter.categoryBits = BIT_SENSOR;
        sensorDef.filter.maskBits = BIT_TERRAIN;
        getBody().createFixture(sensorDef).setUserData(rightSide);

        shape.dispose();
    }

    public void jump(boolean input) {
        if (input) {
            if (footContacts > 0) {
                    applyForce(jumpSpeed * normalX,
                            (jumpSpeed+(Math.max(1,Math.abs(xForce*.2f)))+platformYSpeed) * (Math.max(0.25f,normalY)));
            }
        }
    }

    public void shoot(float delta) {
        if((rightHorizontalAxis != 0 || rightVerticalAxis != 0)) {
            //Charging Feature
            if(gun.chargeDelay != 0) {
                if(gun.chargeDelay == chargeTime) {
                    gun.playCharge();
                }
                chargeTime -= delta;
                if(chargeTime < 0) {
                    chargeTime = 0;
                }
            }
            //Single Shots
            if (r2JustPressed && gun.fireRate == 0 && chargeTime == 0) {
                objectManager.addBullet(new Bullet(getPosition().x + rightHorizontalAxis * 10, getPosition().y + 8 + rightVerticalAxis * 10,
                        gun.radius,
                        rightHorizontalAxis,
                        rightVerticalAxis,
                        gun.force,
                        this,
                        gun
                        ));
            }
            //Hold Down Shots with optional burst
            if (gun.fireRate > 0) {
                fireTime -= delta;
                if (burst < gun.burstMax) {
                    if (fireTime <= 0 && chargeTime == 0 && !r2JustPressed) {
                        objectManager.addBullet(new Bullet(getPosition().x + rightHorizontalAxis * 10, getPosition().y + 8 + rightVerticalAxis * 10,
                                gun.radius,
                                rightHorizontalAxis,
                                rightVerticalAxis,
                                gun.force,
                                this,
                                gun
                        ));
                        fireTime = gun.fireRate;
                        burst++;
                    }
                } else {
                    burstTime -= delta;
                    if (burstTime <= 0) {
                        if(gun.canHold) {
                            burstTime = gun.burstRate;
                            burst = 0;
                        }
                    }
                }
            }
        }
    }

    public void update(float delta) {
        moveX();
        terrainSpeedCalculator();
        yForce = leftVerticalAxis * (l2Axis * boostY);

        if(leftHorizontalAxis == 0) {
            if (footContacts > 0) {
                xForce = 0;
            } else {
                //Gradual air drag
                if (xForce > 0.01f) {
                    xForce -= 0.01f;
                } else {
                    xForce = 0;
                }
            }
        }

        if(footContacts > 0) {
            //Turning physics while on the ground
            if (getBody().getLinearVelocity().x > 0 && xForce < 0) {
                x2Force = -turnSpeed;
            } else {
                if (getBody().getLinearVelocity().x < 0 && xForce > 0) {
                    x2Force = turnSpeed;
                } else {
                    x2Force = 0;
                }
            }
        } else {
            x2Force = 0;
            //add weightiness to the jump when not boosting
            if(l2Axis == 0) {
                if (getBody().getLinearVelocity().y >= -0.09f && getBody().getLinearVelocity().y <= 0.09f) {
                    y2Force = -80f;
                }
            }
            if(y2Force == -80f) {
                y2Force = 0;
            }
            if((xForce > 0 && platformXSpeed < 0) || (xForce < 0 && platformXSpeed > 0)) {
                platformXSpeed = 0;
            }
        }

        if(platform != null) {
            platformYSpeed = platform.velY;
            if((xForce >= 0 && platform.velX >0) || (xForce <= 0 && platform.velX < 0)) {
                platformXSpeed = platform.velX;
            } else {
                platformXSpeed = 0;
            }
        } else {
            platformYSpeed = 0;
        }

        applyForce(xForce + x2Force, yForce + y2Force);

        //Max speed calculations
        maxSpeedX = maxWalkSpeed + maxBoostSpeed + Math.abs(platformXSpeed) + Math.abs(terrainSpeedX);

        if(getXVelocity() < -maxSpeedX) {
            setVelocity(-maxSpeedX,getYVelocity());
        }
        if(getXVelocity() > maxSpeedX) {
            setVelocity(maxSpeedX,getYVelocity());
        }
        if(getYVelocity() > 22) {
            setVelocity(getXVelocity(),22);
        }

        //Reset the Gun
        if(r2Axis == 0 || (rightHorizontalAxis == 0 && rightVerticalAxis == 0)) {
            resetGunTimers();
            gun.stopCharge();
        }
        //System.out.println(leftHorizontalAxis);
    }

    public void moveX() {
        if (l2Axis > 0) {
            isBoosting = true;
            turnSpeed = boostTurnSpeed;
            maxBoostSpeed = boost;
            if ((leftContacts > 0 && leftHorizontalAxis < -0.2f) || (rightContacts > 0 && leftHorizontalAxis > 0.2f)) {
                if (!isTouchingDynamic) {
                    xForce = 0;
                    setFriction(0);
                } else {
                    xForce = (leftHorizontalAxis * speed) * (l2Axis * boostX);
                    setFriction(boostFriction);
                }
            } else {
                xForce = (leftHorizontalAxis * speed) * (l2Axis * boostX);
                setFriction(boostFriction);
            }
        } else {
            isBoosting = false;
            turnSpeed = normalTurnSpeed;
            if (maxBoostSpeed > 0) {
                maxBoostSpeed -= 0.08f;
                if (maxBoostSpeed < 0.01f) maxBoostSpeed = 0;
            }
            if ((leftContacts > 0 && leftHorizontalAxis < -0.2f) || (rightContacts > 0 && leftHorizontalAxis > 0.2f)) {
                if (!isTouchingDynamic) {
                    xForce = 0;
                    setFriction(0);
                } else {
                    xForce = (leftHorizontalAxis * speed) * (l2Axis * boostX);
                    setFriction(normalFriction);
                }
            } else {
                xForce = (leftHorizontalAxis * speed);
                setFriction(normalFriction);
            }
        }
    }


    public void terrainSpeedCalculator() {
        if((leftHorizontalAxis > -0.1f || !isBoosting) && terrainSpeedX < 0) {
            terrainSpeedX += 0.2f;
            if(terrainSpeedX > 0) {
                terrainSpeedX = 0;
            }
        }
        if((leftHorizontalAxis < 0.1f || !isBoosting) && terrainSpeedX > 0) {
            terrainSpeedX -= 0.2f;
            if(terrainSpeedX < 0) {
                terrainSpeedX = 0;
            }
        }
        if(leftHorizontalAxis == 0 && leftVerticalAxis == 0 && footContacts == 0) {
            if(terrainSpeedX > 0) {
                terrainSpeedX -= .02f;
            } else {
                terrainSpeedX += .02f;
            }
        }
        if(terrainSpeedX < -maxTerrainSpeedX) {
            terrainSpeedX = -maxTerrainSpeedX;
        }
        if(terrainSpeedX > maxTerrainSpeedX) {
            terrainSpeedX = maxTerrainSpeedX;
        }
    }

    public void resetGunTimers() {
        if(gun != null) {
            fireTime = 0;
            burstTime = gun.burstRate;
            if (gun.bypassable) {
                burst = 0;
            } else {
                burst = gun.burstMax;
            }
            if (gun.chargeDelay != 0) {
                burst = 0;
                chargeTime = gun.chargeDelay;
            } else {
                chargeTime = 0;
            }
        }
    }

    public void addGun(Gun gun) {
        this.gun = gun;
        resetGunTimers();
    }

    public void addItem() {

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
        if(getBody().getFixtureList() != null) {
            for (int i = 0; i < getBody().getFixtureList().size; i++) {
                getBody().destroyFixture(getBody().getFixtureList().get(i));
            }
        }
        super.dispose();
    }
}
