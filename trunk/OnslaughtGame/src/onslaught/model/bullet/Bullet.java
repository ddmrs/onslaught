package onslaught.model.bullet;

import onslaught.model.enemy.Enemy;
import onslaught.model.*;
import java.awt.geom.Point2D;
import onslaught.gui.Zone;

public abstract class Bullet extends Sprite
{
    private int damage;
    private Enemy target;
    private float speed;

    public Bullet(Point2D.Float position, Enemy target, float speed, Zone zone) {
        super(position, zone);
        this.speed = speed;
        this.target = target;
    }
    
    @Override
    public void update(long currentTime){
        checkTarget();
        calcVelocities();
        super.update(currentTime);
    }
    
    public void checkTarget(){
        if(target.isAlive()){
            if(target.getCollisionBox().intersects(this.getCollisionBox())){
                target.takeHit(damage);
                //getZone().removeBullet(this);
                setAlive(false);
            }
        }
        else{
            //if bullet should fly further, other code should come here
            //or method should be overwritten
            setAlive(false);
            
        }
    }
    
    public void calcVelocities(){
        float xDist = target.getMiddlePoint().x - getMiddlePoint().x;
        float yDist = target.getMiddlePoint().y - getMiddlePoint().y;
        float steps = (float) (getDistance(target, this)) / getSpeed();
        setVelocityX(xDist / steps);
        setVelocityY(yDist / steps);        
    }    

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
