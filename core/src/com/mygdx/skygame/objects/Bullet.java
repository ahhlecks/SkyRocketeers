package com.mygdx.skygame.objects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.skygame.data.Gun;

import static com.mygdx.skygame.SkyRocketeers.SCALEWIDTH;
import static com.mygdx.skygame.utils.Constants.BIT_ENEMY;
import static com.mygdx.skygame.utils.Constants.BIT_FRIENDLY_BULLET;
import static com.mygdx.skygame.utils.Constants.BIT_TERRAIN;

/**
 * Created by alexa on 3/4/2017.
 */
public class Bullet extends DynamicBody {
    private Player player;
    private float killTime = 5.0f;
    public int damage = 1;
    private boolean isProjectile = false;
    private int bounces = 0;
    private int maxBounces = 0;
    private float bulletXForce;
    private float bulletYForce;
    private float playerXForce;
    private float playerYForce;
    private Gun gun;
    private float timeElapsed;

    public Bullet(float x, float y, float radius, float dirX, float dirY, float force, Player player, Gun gun) {
            super(x, y, radius);
            this.player = player;
            this.gun = gun;
            this.density = gun.density;
            this.friction = gun.friction;
            this.restitution = gun.restitution;
            this.damage = gun.damage;
            this.isProjectile = gun.isProjectile;
            this.maxBounces = gun.maxBounces;
            this.killTime = gun.killTime;

            float angle = MathUtils.atan2(dirY,dirX) + gunAccuracy();
            bulletXForce = MathUtils.cos(angle) * force;
            bulletYForce = MathUtils.sin(angle) * force;
    }

    public void initialize() {
        setBody(false,"dynamic",density,friction,restitution,false);
        setFilter(BIT_FRIENDLY_BULLET, (byte)(BIT_ENEMY | BIT_TERRAIN));
        getBody().setBullet(true);
        if(!isProjectile) {
            getBody().setGravityScale(0);
        }
        setupSounds();
        playerXForce = 0;//body.getMass() * player.getXVelocity() / game.timestep;
        playerYForce = 0;//body.getMass() * player.getYVelocity() / game.timestep;
        applyForce(bulletXForce+playerXForce,bulletYForce+playerYForce);
    }

    public void update(float delta) {
        timeElapsed+=delta;
        if(timeElapsed > killTime) {
            kill();
        }
        if(bounces > maxBounces && !isDead) {
            kill();
        }
    }

    private void setupSounds() {
        long id = gun.shootSound.play();
        gun.shootSound.setPitch(id, MathUtils.random(.2f)+.9f);
        gun.chargeSound.stop();
    }

    public void bounce(Sound hit) {
        bounces++;
        this.hit = hit;
        long id = hit.play();
        float xDif = (getPosition().x - player.getPosition().x)/SCALEWIDTH*2;
        if (xDif < 0) {
            xDif = Math.max(-.95f,xDif);
        } else {
            xDif = Math.min(.95f,xDif);
        }
        float distance = ((Math.abs(getXVelocity()) + Math.abs(getYVelocity()))/(player.getPosition().dst(getPosition())));
        if(distance > 1) distance = 1;
        hit.setPan(id,xDif,distance);
    }
    private float gunAccuracy() {
        float random = Math.max(0,MathUtils.random() - gun.accuracy) * MathUtils.randomSign();
        return (random*gun.precision);
    }

    public void kill() {
        super.kill();
    }

    public void dispose() {
        super.dispose();
    }
}
