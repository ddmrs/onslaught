package onslaught.model.bullet;

import onslaught.model.enemy.Enemy;
import onslaught.model.*;
import java.awt.geom.Point2D;

public abstract class Bullet extends Sprite
{
    private int damage;
    private Enemy target;
    private int speed;

    public Bullet(Point2D.Float position, Enemy target, int speed) {
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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Enemy getTarget() {
        return target;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
