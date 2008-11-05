/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onslaught.model.bullet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import onslaught.gui.Zone;
import onslaught.model.enemy.Enemy;

/**
 *
 * @author Jelle Victoor
 */
public class BulletRed extends Bullet {
    private final static float SPEED = 0.4f;
    private boolean start = true;
    private Point2D.Float rotationCenter;
    //private Point2D.Float middlePoint;//store the center, stays the same unless moved    
    private AffineTransform affineTransform = new AffineTransform();
    private double oldShootAngle = Math.toRadians(45);

    public BulletRed(Point2D.Float position, Enemy target, Zone zone) {
        super(position, target, SPEED, zone);
        
        //move bullet to turret
        affineTransform.translate(getPosition().x, getPosition().y);
        
        setDamage(100);

        getAnimation().addFrame(new ImageIcon("resources/images/bullet-red.png").getImage(), 1);
        getAnimation().start();
    }

    @Override
    public void update(long elapsedTime) {
//        if (start) {
//            start = false;
//            rotationCenter = new Point2D.Float(getWidth() / 2, getWidth() / 2);
//        }
        rotationCenter = new Point2D.Float(getWidth() / 2, getWidth() / 2);
        //The bulletRed is not so fast so the dx and dy should be adjusted every update
        checkTarget();
        //setVelocityX(1);
        //setVelocityY(-1);
        super.update(elapsedTime);
        //calcVelocities();
        targetEnemy();

        //super.update(elapsedTime);
    }
    private Point2D.Float oldPos;
    
    public void targetEnemy() {
        //calculate sin(alpha) = overstaande rechthoekzijde / schuine zijde
        float xDist = target.getMiddlePoint().x - getMiddlePoint().x;
        float yDist = target.getMiddlePoint().y - getMiddlePoint().y;
        double distance = Math.sqrt(xDist * xDist + yDist * yDist);
        double sinusAngle = xDist / distance;
        //now get the angle by inverse sinus(arch sinus)
        double targetAngle = Math.asin(sinusAngle);//RETURNS FRIGGING RAD, HOW CREWL OF YOU!
        //enemies are below the turret: quadrant I and IV

        double newShootAngle = Math.PI - targetAngle;
        if (yDist < 0) {//enemies are above the turret: quadrant II and III

            newShootAngle = 0 + targetAngle;
        }
        double turnAngle = newShootAngle - oldShootAngle;//doesnt measure shortest turnAngle yet
        //to new angle
        
        //affineTransform.rotate(turnAngle, rotationCenter.x, rotationCenter.y);
        affineTransform.translate(getVelocityX(), getVelocityY());
        //affineTransform.
        oldShootAngle = newShootAngle;
    }
    
    @Override
    public void checkTarget(){
        for(Enemy enem: getZone().getEnemies()){
            if(enem.isAlive()){
                if(enem.getCollisionBox().intersects(this.getCollisionBox())){
                    enem.takeHit(damage);
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
    }
    
//    @Override
//    public Rectangle2D getCollisionBox(){
//        getImage().
//        return new Rectangle(Math.round(getPosition().x), Math.round(getPosition().y), getWidth(), getHeight());
//    }
    
    @Override
    public void draw(Graphics graphics)
    {
        final Graphics2D graphics2D = (Graphics2D) graphics;
        //graphics2D.dra
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(Math.round(getPosition().x), Math.round(getPosition().y), getWidth(), getHeight());
        
        graphics2D.drawImage(getAnimation().getImage(), affineTransform, null);
    }    
}
