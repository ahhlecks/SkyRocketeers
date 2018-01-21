package com.mygdx.skygame.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.skygame.objects.*;
import com.mygdx.skygame.screens.GameScreen;

/**
 * Created by alexa on 2/21/2017.
 */

public class GameContactListener implements ContactListener {

    private GameScreen game;
    private Fixture a;
    private Fixture b;
    private float[] forces;
    private float[] forces2;
    private Player player;
    private Box2dObject object;

    public GameContactListener(GameScreen game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        a = contact.getFixtureA();
        b = contact.getFixtureB();

        if(a == null || b == null) return;
        if(a.getUserData() == null || b.getUserData() == null) return;
        if(a.getBody().getUserData() == null || b.getBody().getUserData() == null) return;

        if(isFootContact(a)){
            player = (Player) a.getBody().getUserData();
            player.footContacts++;
            if(isKinematicContact(b)) {
                object = (KinematicBody) b.getBody().getUserData();
                player.platform = (KinematicBody) object;
            } else {
                player.platformXSpeed = 0;
            }
        }
        if(isFootContact(b)){
            player = (Player) b.getBody().getUserData();
            player.footContacts++;
            if(isKinematicContact(a)) {
                object = (KinematicBody) a.getBody().getUserData();
                player.platform = (KinematicBody) object;
            }  else {
                player.platformXSpeed = 0;
            }
        }
        if(isLeftSideContact(a)) {
            player = (Player) a.getBody().getUserData();
            player.leftContacts++;
            if(isDynamicContact(b)) {
                player.isTouchingDynamic = true;
            }
        }
        if(isLeftSideContact(b)) {
            player = (Player) b.getBody().getUserData();
            player.leftContacts++;
            if(isDynamicContact(a)) {
                player.isTouchingDynamic = true;
            }
        }
        if(isRightSideContact(a)) {
            player = (Player) a.getBody().getUserData();
            player.rightContacts++;
            if(isDynamicContact(b)) {
                player.isTouchingDynamic = true;
            }
        }
        if(isRightSideContact(b)) {
            player = (Player) b.getBody().getUserData();
            player.rightContacts++;
            if(isDynamicContact(a)) {
                player.isTouchingDynamic = true;
            }
        }
        if(isPlayerContact(a)) {
            player = (Player) a.getBody().getUserData();
            if(player.leftContacts == 0 && player.rightContacts == 0 && !isCollectableContact(b)) {
                player.normalX = contact.getWorldManifold().getNormal().x;
                player.normalY = contact.getWorldManifold().getNormal().y;
            }
            if(isCollectableContact(b)) {
                ((Collectable)b.getUserData()).pickUp(player);
            }
        }
        if(isPlayerContact(b)) {
            player = (Player) b.getBody().getUserData();
            if(player.leftContacts == 0 && player.rightContacts == 0 && !isCollectableContact(a)) {
                player.normalX = contact.getWorldManifold().getNormal().x;
                player.normalY = contact.getWorldManifold().getNormal().y;
            }
            if(isCollectableContact(a)) {
                ((Collectable)a.getUserData()).pickUp(player);
            }
        }
        if(isBulletContact(a)) {
            if(isBox2dObject(b)) {
                ((Bullet) a.getUserData()).bounce(((Box2dObject)b.getUserData()).hit);
            }
        }
        if(isBulletContact(b)) {
            if(isBox2dObject(a)) {
                ((Bullet) b.getUserData()).bounce(((Box2dObject)a.getUserData()).hit);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        a = contact.getFixtureA();
        b = contact.getFixtureB();

        if(a == null || b == null) return;
        if(a.getUserData() == null || b.getUserData() == null) return;
        if(a.getBody().getUserData() == null || b.getBody().getUserData() == null) return;

        if(isFootContact(a)){
            player = (Player) a.getBody().getUserData();
            player.footContacts--;
            if(isKinematicContact(b)) {
                player.platform = null;
            }
        }
        if(isFootContact(b)){
            player = (Player) b.getBody().getUserData();
            player.footContacts--;
            if(isKinematicContact(a)) {
                player.platform = null;
            }
        }
        if(isLeftSideContact(a)) {
            player = (Player) a.getBody().getUserData();
            player.leftContacts--;
            if(isDynamicContact(b)) {
                player.isTouchingDynamic = false;
            }
        }
        if(isLeftSideContact(b)) {
            player = (Player) b.getBody().getUserData();
            player.leftContacts--;
            if(isDynamicContact(a)) {
                player.isTouchingDynamic = false;
            }
        }
        if(isRightSideContact(a)) {
            player = (Player) a.getBody().getUserData();
            player.rightContacts--;
            if(isDynamicContact(b)) {
                player.isTouchingDynamic = false;
            }
        }
        if(isRightSideContact(b)) {
            player = (Player) b.getBody().getUserData();
            player.rightContacts--;
            if(isDynamicContact(a)) {
                player.isTouchingDynamic = false;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        if(isPlayerContact(contact.getFixtureB())) {
            player = (Player) contact.getFixtureB().getUserData();
            forces = impulse.getNormalImpulses();
            forces2 = impulse.getTangentImpulses();
            if(forces[0] * Math.abs(player.normalX) > 0.02f) {
                if(player.isBoosting) {
                    if (player.getYVelocity() < 0) {
                        player.terrainSpeedX += player.normalX * forces[0] * 1.2f;
                    } else {
                        player.terrainSpeedX += player.normalX * forces[0] / 3;
                    }
                }
                if (forces[0] * Math.abs(player.normalX) > 8) {
                    player.terrainSpeedX -= player.normalX * forces[0] / 2;
                    if(Math.abs(player.normalX) >= .9f) {
                        player.terrainSpeedX = 0;
                    }
                }
            }
        }
    }

    private boolean isFootContact(Fixture a) {
        return a.getUserData().equals((byte)0);
    }
    private boolean isLeftSideContact(Fixture a) {
        return a.getUserData().equals((byte)1);
    }
    private boolean isRightSideContact(Fixture a) {
        return a.getUserData().equals((byte)2);
    }
    private boolean isPlayerContact(Fixture a) {
        return a.getUserData() instanceof Player;
    }
    private boolean isKinematicContact(Fixture a) {
        return a.getUserData() instanceof KinematicBody;
    }
    private boolean isDynamicContact(Fixture a) {
        return a.getUserData() instanceof DynamicBody;
    }
    private boolean isBulletContact(Fixture a) {
        return a.getUserData() instanceof Bullet;
    }
    private boolean isGunContact(Fixture a) {
        return a.getUserData() instanceof GunCollectable;
    }
    private boolean isCollectableContact(Fixture a) {
        return a.getUserData() instanceof Collectable;
    }
    private boolean isTerrainContact(Fixture a) {
        return a.getUserData() instanceof ChainBody;
    }
    private boolean isBox2dObject(Fixture a) {
        return a.getUserData() instanceof Box2dObject;
    }
}