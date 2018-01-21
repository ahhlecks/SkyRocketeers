package com.mygdx.skygame.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.mygdx.skygame.managers.Box2dObjectManager;
import com.mygdx.skygame.screens.GameScreen;

import static com.mygdx.skygame.utils.Constants.PPM;

/**
 * Created by alexa on 2/20/2017.
 */
public abstract class Box2dObject {
    protected GameScreen game;
    protected AssetManager assetManager;
    protected Box2dObjectManager objectManager;
    protected World world;
    protected Body body;
    private Vector2 pos;
    private int index;
    private float width;
    private float height;
    private float radius;
    protected String material = "grass";
    public FixtureDef fDef;
    public boolean isDead;
    public boolean isCollectable = false;
    protected String bodyType = "static";
    public boolean isSensor = false;
    public Sprite sprite;
    public Sound hit;

    public final float DEFAULT_DENSITY = 1.0f;
    public final float DEFAULT_FRICTION = 0.5f;
    public final float DEFAULT_RESTITUTION = 0f;

    public float density = DEFAULT_DENSITY;
    public float friction = DEFAULT_FRICTION;
    public float restitution = DEFAULT_RESTITUTION;

    public Box2dObject(float x, float y, float width, float height) {
        this.pos = new Vector2();
        setPosition(x,y);
        this.width = width / PPM;
        this.height = height / PPM;
    }

    public Box2dObject(float x, float y, float width, float height, String material) {
        this.pos = new Vector2();
        setPosition(x,y);
        this.width = width / PPM;
        this.height = height / PPM;
        setMaterial(material);
    }

    public Box2dObject(float x, float y, float radius, String material) {
        this.pos = new Vector2();
        setPosition(x,y);
        this.radius = radius / PPM;
        setMaterial(material);
    }

    public Box2dObject(float x, float y, float width, float height, float density, float friction, float restitution) {
        this.pos = new Vector2();
        setPosition(x,y);
        this.width = width / PPM;
        this.height = height / PPM;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    public Box2dObject(float x, float y, float radius) {
        this.pos = new Vector2();
        setPosition(x,y);
        this.radius = radius / PPM;
    }

    public Box2dObject(float x, float y, float radius, float density, float friction, float restitution) {
        this.pos = new Vector2();
        setPosition(x,y);
        this.radius = radius / PPM;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    public Box2dObject(ChainShape chainShape, String material) {
        this.pos = new Vector2();
        Vector2 startVertex = new Vector2();
        chainShape.getVertex(0,startVertex);
        setPosition(startVertex.x,startVertex.y);
        setMaterial(material);
    }

    public int getIndex() {
        return index;
    }

    public void initialize(GameScreen game, World world, AssetManager am, Box2dObjectManager om, int index) {
        this.game = game;
        this.world = world;
        this.assetManager = am;
        this.objectManager = om;
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setGame(GameScreen game) {
        this.game = game;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setAssetManager(AssetManager am) {
        this.assetManager = am;
    }

    public void setObjectManager(Box2dObjectManager om) {
        this.objectManager = om;
    }

    public Vector2 getPosition() {
        pos.x = body.getPosition().x * PPM;
        pos.y = body.getPosition().y * PPM;
        return pos;
    }

    public float getWidth() {
        return this.width*PPM;
    }

    public float getHeight() {
        return this.height*PPM;
    }

    public float getRadius() {
        return this.radius*PPM;
    }

    public Body getBody() {
        return body;
    }

    public void setPosition(float x, float y) {
        pos.x = x / PPM;
        pos.y = y / PPM;
    }

    public void setDensity(float density) {
        if(body.getFixtureList().size != 0) {
            body.getFixtureList().first().setDensity(density);
        }
    }

    public void setFriction(float friction) {
        if(body.getFixtureList().size != 0) {
            body.getFixtureList().first().setFriction(friction);
        }
    }

    public float getFriction() {
        if(body.getFixtureList().size != 0) {
            return body.getFixtureList().first().getFriction();
        }
        return 0;
    }

    public void setRestitution(float restitution) {
        if(body.getFixtureList().size != 0) {
            body.getFixtureList().first().setRestitution(restitution);
        }
    }

    private void setMaterial(String material) {
        this.material = material;
        if(material.equals("void")) {
            density = 0; //1
            friction = 0; //0.4
            restitution = 0; //0
        }
        if(material.equals("grass")) {
            density = DEFAULT_DENSITY; //1
            friction = DEFAULT_FRICTION; //0.4
            restitution = DEFAULT_RESTITUTION; //0
        }
        if(material.equals("metal")) {
            density = 1.2f;
            friction = 0.2f;
            restitution = 0.1f;
        }
    }

    protected void setHitSound(Sound hit) {
        this.hit = hit;
    }

    public void setVelocity(float x, float y) {
        body.setLinearVelocity(x,y);
    }

    public float getXVelocity() {
        return body.getLinearVelocity().x;
    }

    public float getYVelocity() {
        return body.getLinearVelocity().y;
    }

    public float getXForce() {
        return body.getMass() * getXVelocity() / game.timestep;
    }

    public float getYForce() {
        return body.getMass() * getYVelocity() / game.timestep;
    }

    public void setFilter() {
        Filter filterData = new Filter();
        filterData.categoryBits = 0x00;
        filterData.maskBits = 0xFFF;
        filterData.groupIndex = 0;
        body.getFixtureList().first().setFilterData(filterData);
    }

    public void setFilter(byte category) {
        Filter filterData = new Filter();
        filterData.categoryBits = category;
        filterData.maskBits = 0xFFF;
        filterData.groupIndex = 0;
        body.getFixtureList().first().setFilterData(filterData);
    }

    public void setFilter(byte category, byte mask) {
        Filter filterData = new Filter();
        filterData.categoryBits = category;
        filterData.maskBits = mask;
        filterData.groupIndex = 0;
        body.getFixtureList().first().setFilterData(filterData);
    }

    public void setFilter(byte category, byte mask, short groupIndex) {
        Filter filterData = new Filter();
        filterData.categoryBits = category;
        filterData.maskBits = mask;
        filterData.groupIndex = groupIndex;
        body.getFixtureList().first().setFilterData(filterData);
    }

    public void setFilter(Filter filter) {
        body.getFixtureList().first().setFilterData(filter);
    }

    /* Group Index //
    0 = use category and mask
    0 / + or - = use category and mask
    same + / same + = collide
    same - / same - = don't collide
    */

    public void applyForce(float x, float y) {
        body.applyForceToCenter(x,y,false);
    }

    protected void setBody(boolean isRect, String bodyType, float density, float friction, float restitution, boolean isSensor) {
        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        if(bodyType.contains("static")) {
            def.type = BodyDef.BodyType.StaticBody;
        }
        if(bodyType.contains("dynamic")) {
            def.type = BodyDef.BodyType.DynamicBody;
            def.allowSleep = true;
            if (isRect) def.fixedRotation = false;
        }
        if(bodyType.contains("kinematic")) {
            def.type = BodyDef.BodyType.KinematicBody;
        }
        if(bodyType.contains("chain")) {
            def.type = BodyDef.BodyType.StaticBody;
        }
        def.position.set(pos.x,pos.y);
        body = world.createBody(def);
        body.setUserData(this);

        fDef = new FixtureDef();
        fDef.isSensor = isSensor;
        fDef.density = density;
        fDef.friction = friction;
        fDef.restitution = restitution;

        if (isRect) {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width,height);
            fDef.shape = shape;
            body.createFixture(fDef).setUserData(this);
            shape.dispose();
        } else {
            CircleShape shape = new CircleShape();
            shape.setRadius(radius);
            fDef.shape = shape;
            body.createFixture(fDef).setUserData(this);
            shape.dispose();
        }
    }

    protected void setChainBody(Shape cs, float density, float friction, float restitution) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);
        body.setUserData(this);
        fDef = new FixtureDef();
        fDef.shape = cs;
        fDef.density = density;
        fDef.friction = friction;
        fDef.restitution = restitution;
        body.createFixture(fDef).setUserData(this);
    }

    public void hit() {
        System.out.println(index + ": I've been hit!");
    }

    public void kill() {
        if(!isDead) {
            objectManager.quarantineObjects.add(this);
            isDead = true;
        }
    }

    public void dispose() {
        if(getBody().getFixtureList() != null) {
            for (int i = 0; i < getBody().getFixtureList().size; i++) {
                getBody().destroyFixture(getBody().getFixtureList().get(i));
            }
        }
        getBody().getWorld().destroyBody(getBody());
    }
}
