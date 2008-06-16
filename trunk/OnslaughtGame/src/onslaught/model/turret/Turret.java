package onslaught.model.turret;

import onslaught.model.*;
import onslaught.model.enemy.Enemy;
import onslaught.model.bullet.Bullet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public abstract class Turret extends Sprite
{
    private static final double PI = Math.PI;
    private int damage;
    private float range;
    private int rate; //in shots per second
    private AffineTransform affineTransform;
    private boolean enemyInRange = false;
   
    private double oldShootAngle; // IN RAD FUCKING RAD YEAH
    private int shotsFired;
    private long timeLastShot;
    private long reloadTime;
    private Enemy target;

    public Turret(Point2D.Float position, int rate, int range) {
        super(position);
        timeLastShot = -1L;
        this.rate = rate;
        this.range = range;
        this.oldShootAngle = Math.toRadians(45); //image starts with an agle of 45 degrees
        calcReloadTime(rate);
        affineTransform = new AffineTransform();
        affineTransform.translate(getPosition().x, getPosition().y);
    }
    
    public void calcReloadTime(int rate){
        reloadTime = (long) 1000.0 / rate;
        reloadTime *= 1000000L;    // ms --> nanosecs 
    }
    
    abstract public Bullet shoot();
    
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
    @Override
    public void update(long elapsedTime) {
        //a turret doesnt really update, it sits duck on screen
    }
    
    /**
     * Function that tracks an enemy, updates the image to look in enemy direction
     * TODO: calculate rotation angle instead of resetting, dno maybe more performance then
     * TODO: turret instantly points at enemy, ist very realistic ;-)
     * @param target Enemy to track
     */
    public void targetEnemy(Enemy target){
        //calculate sin(alpha) = overstaande rechthoekzijde / schuine zijde
        float xDist = target.getMiddlePoint().x - getMiddlePoint().x;
        float yDist = target.getMiddlePoint().y - getMiddlePoint().y;
        double distance = Math.sqrt(xDist*xDist + yDist*yDist);
        double sinusAngle = xDist/distance;
        //now get the angle by inverse sinus(arch sinus)
        double targetAngle = Math.asin(sinusAngle);//RETURNS FRIGGING RAD, HOW CREWL OF YOU!
        //enemies are below the turret: quadrant I and IV
        double newShootAngle = PI - targetAngle;
        if(yDist<0){//enemies are above the turret: quadrant II and III
            newShootAngle = 0 + targetAngle;
        }
        //reset to zero
        affineTransform.rotate(-oldShootAngle, getWidth()/2, getWidth()/2);
        //to new angle
        affineTransform.rotate(newShootAngle, getWidth()/2, getWidth()/2);
        oldShootAngle = newShootAngle;
        //set Target
        this.setTarget(target);
    }
    
    @Override
    public void draw(Graphics graphics){
        final Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(getAnimation().getImage(), affineTransform, null);
    }

    public boolean isEnemyInRange() {
        return enemyInRange;
    }

    public void setEnemyInRange(boolean enemyInRange) {
        this.enemyInRange = enemyInRange;
    }

    public Enemy getTarget()
    {
        return target;
    }

    public void setTarget(Enemy target)
    {
        this.target = target;
    }
}
