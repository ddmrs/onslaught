package onslaught.model.bullet;

import java.awt.Shape;
import onslaught.game.GameOperations;
import onslaught.model.enemy.Enemy;
import onslaught.model.*;
import onslaught.interfaces.ICollidable;
import onslaught.interfaces.Sprite;
import onslaught.util.CollisionBoxType;
import onslaught.util.TimingUtility;

public abstract class Bullet extends Entity implements ICollidable {

    protected GameOperations gameOps;
    protected int damage;
    protected Enemy target;

    /**
     * Creates a bullet.
     * @param ref Thelocation of the image
     * @param position Where the bullet should be.
     * @param speed The speed at wich he is flying
     * @param zone The zone where he is in.
     */
    public Bullet(String ref, double startX, double startY, double speed, GameOperations gameOps, Enemy target, int damage) {
        super(ref, startX, startY);
        z = -1;
        this.speed = speed;
        this.gameOps = gameOps;
        this.target = target;
        //this.calcVelocities(target, 1);
        this.damage = damage;
    }

    /**
     * Creates a bullet.
     * @param ref Thelocation of the image
     * @param position Where the bullet should be.
     * @param speed The speed at wich he is flying
     * @param zone The zone where he is in.
     */
    public Bullet(Sprite sprite, double startX, double startY, double speed, GameOperations gameOps, Enemy target, int damage) {
        super(sprite, startX, startY);
        this.speed = speed;
        this.gameOps = gameOps;
        this.target = target;
        this.damage = damage;
        this.calcAngleToTarget(target, this, TimingUtility.getOneSecond(), 360);
    }

    public void update(long passedTime) {
        if(checkTarget(target)){
            calcVelocities(target, passedTime);    
        }else{
            targetDiedEvent();
        }
        super.x += dx;
        super.y += dy;
    }

    protected boolean checkTarget(Enemy currTarget) {
        boolean hasAlifeTarget = true;
        if (currTarget != null && currTarget.isAlive()) {
            if (currTarget.collidesWith(this)) {
                currTarget.takeHit(damage);
                this.kill();
            }
        } else{
            hasAlifeTarget = false;
        }
        return hasAlifeTarget;
    }

    protected void targetDiedEvent() {
        //if bullet should fly further, other code should come here
        this.kill();
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void collidedWith(Entity other) {
        // Do nothing yet.
    }

    public Shape getCollisionBox() {
        return getBounds();
    }

    public CollisionBoxType getCollisionBoxType() {
        return CollisionBoxType.RECTANGLE;
    }

    public Enemy getTarget() {
        return target;
    }
}
