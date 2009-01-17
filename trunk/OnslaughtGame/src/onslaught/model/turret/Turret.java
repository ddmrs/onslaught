package onslaught.model.turret;

import java.awt.Color;
import onslaught.model.*;
import onslaught.model.enemy.Enemy;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import onslaught.gui.Zone;
import onslaught.interfaces.ISelectable;
import onslaught.model.bullet.Bullet;

public abstract class Turret extends Sprite implements ISelectable {

    private static final double PI = Math.PI;
    private int damage;
    private float range;
    private int rate; //in shots per second
    private AffineTransform affineTransform = new AffineTransform();
    private double oldShootAngle = Math.toRadians(45); // IN RAD FUCKING RAD YEAH //image starts with an agle of 45 degrees
    private long shotsFired;
    private boolean start = true;
    private long currentTime;
    private long timeLastShot;
    private long reloadTime;
    private Point2D.Float rotationCenter;
    private Point2D.Float middlePoint;//store the center, stays the same unless moved
    private boolean selected = false;
    private List<Enemy> targets;
    private List<Bullet> bullets = new ArrayList<Bullet>();

    public Turret(Point2D.Float position, int rate, int range, Zone zone, List<Enemy> enemies) {
        super(position, zone);
        //this.currentTime = 0;
        this.timeLastShot = -1L;
        this.targets = enemies;
        this.rate = rate;
        this.range = range;

        affineTransform.translate(getPosition().x, getPosition().y);
        calcReloadTime(rate);
    }

    public void calcReloadTime(int rate) {
        reloadTime = (long) 1000.0 / rate;
        reloadTime *= 1000000L;    // ms --> nanosecs 
    }

    @Override
    public void update(long elapsedTime) {
        if (start) {
            start = false;
            rotationCenter = new Point2D.Float(getWidth() / 2, getWidth() / 2);
            middlePoint = super.getMiddlePoint();
        }
        currentTime = elapsedTime;
        trackEnemies();
    }

    abstract public void shoot(Enemy enemy);

    public Rectangle2D getRangeBox() {
        Ellipse2D.Float ellipse = new Ellipse2D.Float(getPosition().x - getRange(), getPosition().y - getRange(), getWidth() + getRange() * 2, getHeight() + getRange() * 2);
        return ellipse.getBounds2D();
    }

    /**
     * Determines wether a turret is allowed to shoot or not.
     * @param currentTime time of request
     * @return Returns true if this turret can shoot again
     */
    public boolean isAbleToShoot() {
        return (currentTime >= timeLastShot + reloadTime);
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void trackEnemies() {
        //loop every enemy
        for (Enemy enemy : getZone().getEnemies()) {
            //check if enemy is alive and look if enemy comes in range
            if (enemy.isAlive() && enemy.getCollisionBox().intersects(this.getRangeBox())) {
                //target the gun at the enemy
                targetEnemy(enemy);
                //shoot only when a bullet is loaded
                if (isAbleToShoot()) {
                    shoot(enemy);
                    setTimeLastShot(currentTime);
                }
                //cannot shoot at multiple targets at once
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
    public void targetEnemy(Enemy target) {
        //calculate sin(alpha) = overstaande rechthoekzijde / schuine zijde
        float xDist = target.getMiddlePoint().x - middlePoint.x;
        float yDist = target.getMiddlePoint().y - middlePoint.y;
        double distance = Math.sqrt(xDist * xDist + yDist * yDist);
        double sinusAngle = xDist / distance;
        //now get the angle by inverse sinus(arch sinus)
        double targetAngle = Math.asin(sinusAngle);//RETURNS FRIGGING RAD, HOW CREWL OF YOU!
        //enemies are below the turret: quadrant I and IV
        double newShootAngle = PI - targetAngle;
        if (yDist < 0) {//enemies are above the turret: quadrant II and III
            newShootAngle = 0 + targetAngle;
        }
        double turnAngle = newShootAngle - oldShootAngle;//doesnt measure shortest turnAngle yet
        //to new angle
        affineTransform.rotate(turnAngle, rotationCenter.x, rotationCenter.y);
        oldShootAngle = newShootAngle;
    }

    public void sell() {
        setAlive(false);
    }

    public void addBullet(Bullet bullet) {
        getBullets().add(bullet);
    }

    @Override
    public void draw(Graphics graphics) {
        if (selected) {
            graphics.setColor(Color.MAGENTA);
            graphics.drawOval((int) (super.getMiddlePoint().x - range), (int) (super.getMiddlePoint().y - range), (int) (range * 2), (int) (range * 2));
        }
        final Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(getAnimation().getImage(), affineTransform, null);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void clearBullets() {
        bullets.clear();
    }

    public boolean select() {
        if(selected){
            selected = false;
        }else{
            selected = true;
        }
        return selected;
    }

    public void deselect() {
        selected = false;
    }
}
