package onslaught.model.bullet;

import onslaught.model.enemy.Enemy;
import onslaught.model.*;
import java.awt.geom.Point2D;

public abstract class Bullet extends Sprite
{
    private int damage;
    private Enemy target;
    private float speed;

    public Bullet(Point2D.Float position, Enemy target, float speed) {
        super(position);
        this.speed = speed;
        this.target = target;
    }
    
    public void calcVelocities(){
        float xDist = target.getMiddlePoint().x - getMiddlePoint().x;
        float yDist = target.getMiddlePoint().y - getMiddlePoint().y;
        float steps = (float) (getDistance(target, this)) / getSpeed();
        setVelocityX(xDist / steps);
        setVelocityY(yDist / steps);        
    }
    
    @Override
    public void update(long currentTime){
        calcVelocities();
        super.update(currentTime);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Enemy getTarget() {
        return target;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
