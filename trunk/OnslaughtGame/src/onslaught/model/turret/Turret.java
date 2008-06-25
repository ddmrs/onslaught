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
import java.util.List;
import onslaught.gui.Zone;

public abstract class Turret extends Sprite
{
    private static final double PI = Math.PI;
    private int damage;
    private float range;
    private int rate; //in shots per second
    private AffineTransform affineTransform;
    private double oldShootAngle; // IN RAD FUCKING RAD YEAH
    private long shotsFired;
    
    private long currentTime;
    private long timeLastShot;
    private long reloadTime;
    private Point2D.Float rotationCenter;
    private Point2D.Float middlePoint;//store the center, stays the same unless moved
    
    private List<Enemy> targets;

    public Turret(Point2D.Float position, int rate, int range, Zone zone, List<Enemy> enemies)
    {
        super(position, zone);
        //this.currentTime = 0;
        this.timeLastShot = -1L;
        this.targets = enemies;
        this.rate = rate;
        this.range = range;
        this.oldShootAngle = Math.toRadians(45); //image starts with an agle of 45 degrees
        calcReloadTime(rate);
        affineTransform = new AffineTransform();
        affineTransform.translate(getPosition().x, getPosition().y);
    }

    public void calcReloadTime(int rate)
    {
        reloadTime = (long) 1000.0 / rate;
        reloadTime *= 1000000L;    // ms --> nanosecs 
    }
    private boolean start = true;
    
    @Override
    public void update(long elapsedTime)
    {
        if(start){
            start = false;
            rotationCenter = new Point2D.Float(getWidth()/2, getWidth()/2);
            middlePoint = super.getMiddlePoint();
        }
        currentTime = elapsedTime;
        trackEnemies();
    }

    abstract public void shoot(Enemy enemy);

    public Rectangle2D getRangeBox()
    {
        Ellipse2D.Float ellipse = new Ellipse2D.Float(getPosition().x - getRange(), getPosition().y - getRange(), getWidth() + getRange() * 2, getHeight() + getRange() * 2);
        return ellipse.getBounds2D();
    }

    /**
     * Determines wether a turret is allowed to shoot or not.
     * @param currentTime time of request
     * @return Returns true if this turret can shoot again
     */
    public boolean isAbleToShoot()
    {
        return (currentTime >= timeLastShot + reloadTime);
    }

    public void setTimeLastShot(long timeLastShot)
    {
        this.timeLastShot = timeLastShot;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public float getRange()
    {
        return range;
    }
    
    public void trackEnemies(){
        for(Enemy enemy: targets){
            if(enemy.isAlive() && enemy.getCollisionBox().intersects(this.getRangeBox())){
                targetEnemy(enemy);
                if(isAbleToShoot()){
                    shoot(enemy);
                    setTimeLastShot(currentTime);
                    
                }
                break;
            }
        }
    }
    /**
     * Function that tracks an enemy, updates the image to look in enemy direction
     * TODO: calculate rotation angle instead of resetting, dno maybe more performance then
     * TODO: turret instantly points at enemy, ist very realistic ;-)
     * @param target Enemy to track
     */
    public void targetEnemy(Enemy target)
    {
        //calculate sin(alpha) = overstaande rechthoekzijde / schuine zijde
        float xDist = target.getMiddlePoint().x - middlePoint.x;
        float yDist = target.getMiddlePoint().y - middlePoint.y;
        double distance = Math.sqrt(xDist * xDist + yDist * yDist);
        double sinusAngle = xDist / distance;
        //now get the angle by inverse sinus(arch sinus)
        double targetAngle = Math.asin(sinusAngle);//RETURNS FRIGGING RAD, HOW CREWL OF YOU!
        //enemies are below the turret: quadrant I and IV
        double newShootAngle = PI - targetAngle;
        if(yDist < 0)
        {//enemies are above the turret: quadrant II and III
            newShootAngle = 0 + targetAngle;
        }
        double turnAngle = newShootAngle - oldShootAngle;//doesnt measure shortest turnAngle yet
        //reset to zero
        //affineTransform.rotate(-oldShootAngle, getWidth() / 2, getWidth() / 2);
        //to new angle
        affineTransform.rotate(turnAngle, rotationCenter.x, rotationCenter.y);
        oldShootAngle = newShootAngle;
    //set Target
    }
    
    public void sell(){
        setAlive(false);
    }
    
    @Override
    public void draw(Graphics graphics)
    {
        final Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(getAnimation().getImage(), affineTransform, null);
    }   
}
