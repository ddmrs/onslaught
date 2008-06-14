package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;//ellipse2D.Float
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public abstract class Turret extends Sprite
{

    private int damage;
    private float range;
    private int rate; //in shots per second
   
    private int direction; // degrees
    private int shotsFired;
    private long timeLastShot;
    private long timeBetweenShots;

    public Turret(Point2D.Float position, int rate, int range) {
        super(position);
        timeLastShot = -1L;
        this.rate = rate;
        this.range = range;
        calcTimeBetweenShots(rate);
    }
    
    public void calcTimeBetweenShots(int rate){
        timeBetweenShots = (long) 1000.0 / rate;
        timeBetweenShots *= 1000000L;    // ms --> nanosecs 
    }
    
    abstract public Bullet shoot(Enemy enemy, long time);
    
    public Rectangle2D getRangeBox(){
        Ellipse2D.Float ellipse = new Ellipse2D.Float(getPosition().x - getRange(), getPosition().y - getRange(), getWidth() + getRange()*2, getHeight() + getRange()*2);
        return ellipse.getBounds2D();
    }
    
    public boolean isAbleToShoot(long currentTime){
        return (currentTime >= timeLastShot+timeBetweenShots);
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
   
}
