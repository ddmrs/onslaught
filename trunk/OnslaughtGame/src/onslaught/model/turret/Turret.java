package onslaught.model.turret;

import onslaught.model.*;
import onslaught.model.enemy.Enemy;
import onslaught.model.bullet.Bullet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public abstract class Turret extends Sprite
{

    private int damage;
    private float range;
    private int rate; //in shots per second
   
    private int shootDirection; // degrees
    private int shotsFired;
    private long timeLastShot;
    private long reloadTime;

    public Turret(Point2D.Float position, int rate, int range) {
        super(position);
        timeLastShot = -1L;
        this.rate = rate;
        this.range = range;
        calcReloadTime(rate);
    }
    
    public void calcReloadTime(int rate){
        reloadTime = (long) 1000.0 / rate;
        reloadTime *= 1000000L;    // ms --> nanosecs 
    }
    
    abstract public Bullet shoot(Enemy enemy, long time);
    
    public Rectangle2D getRangeBox(){
        Ellipse2D.Float ellipse = new Ellipse2D.Float(getPosition().x - getRange(), getPosition().y - getRange(), getWidth() + getRange()*2, getHeight() + getRange()*2);
        return ellipse.getBounds2D();
    }
    
    /**
     * Determines wether a turret is allowed to shoot or not.
     * @param currentTime time of request
     * @return Returns true if this turret can shoot again
     */
    public boolean isAbleToShoot(long currentTime){
        return (currentTime >= timeLastShot+reloadTime);
    }

    public void setTimeLastShot(long timeLastShot) {
        this.timeLastShot = timeLastShot;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public float getRange() {
        return range;
    }
    
    public void targetEnemy(Enemy target){
        
    }
}
