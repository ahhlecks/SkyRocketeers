package com.mygdx.skygame.managers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.skygame.objects.*;
import com.mygdx.skygame.screens.GameScreen;

import static com.mygdx.skygame.SkyRocketeers.assets;

/**
 * Created by alexa on 3/5/2017.
 */
public class Box2dObjectManager implements Disposable {

    private GameScreen game;
    private World world;
    private Array<Box2dObject> objects;
    private Array<Bullet> bullets;
    public Array<Box2dObject> quarantineObjects;
    private Player player;
    private int id;

    public Box2dObjectManager(GameScreen game){
        id = 0;
        this.game = game;
        this.world = game.getWorld();
        objects = new Array<Box2dObject>();
        bullets = new Array<Bullet>();
        quarantineObjects = new Array<Box2dObject>();
    }

    public void addBullet(Bullet bullet) {
        bullet.setGame(game);
        bullet.setWorld(world);
        bullet.setAssetManager(assets);
        bullet.setObjectManager(this);
        bullet.initialize();
        bullets.add(bullet);
    }

    public void add(Box2dObject object) {
        object.initialize(game,world,assets,this,id);
        if(object instanceof StaticBody) {
            ((StaticBody) object).initialize();
        }
        if(object instanceof DynamicBody) {
            ((DynamicBody) object).initialize();
        }
        if(object instanceof KinematicBody) {
            ((KinematicBody) object).initialize();
        }
        if(object instanceof ChainBody) {
            ((ChainBody) object).initialize();
        }
        if(object instanceof Player) {
            ((Player) object).initialize();
        }
        if(object instanceof Spawn) {
            ((Spawn) object).initialize();
        }
        objects.add(object);
        id++;
    }

    public void remove(int index) {
        if(objects.get(index) != null) {
            if (objects.get(index) instanceof Player) {
                player = (Player) objects.get(index);
                if (!player.isDead) {
                    player.dispose();
                }
            } else {
                objects.get(index).dispose();
            }
        }
        objects.removeIndex(index);
    }

    public Box2dObject get(int index) {
        if (objects.get(index) != null) {
            return objects.get(index);
        }
        return null;
    }

    public Player getPlayer() {
        for(Box2dObject player: objects) {
            if(player instanceof Player) {
                return (Player) player;
            }
        }
        return null;
    }

    public int size() {
        return objects.size;
    }

    public void update(float delta) {
        for(Box2dObject object : objects) {
            if(object instanceof Player) {
                ((Player) object).update(delta);
            }
            if(object instanceof KinematicBody) {
                ((KinematicBody) object).update(delta);
            }
        }
        for(Box2dObject bullet : bullets) {
            if(bullet instanceof Bullet) {
                ((Bullet) bullet).update(delta);
            }
        }
    }

    public void draw() {
        /*
        for(Box2dObject object : objects) {

        }
        */
    }

    public void emptyQuarantine() {
        if(quarantineObjects.size > 0) {
            for (int i = 0; i < quarantineObjects.size; i++) {
                quarantineObjects.get(i).dispose();
                quarantineObjects.removeIndex(i);
            }
        }
    }

    public void dispose() {
        objects.clear();
        bullets.clear();
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++) {
            if(bodies.get(i) != null) {
                world.destroyBody(bodies.get(i));
            }
        }
    }
}
