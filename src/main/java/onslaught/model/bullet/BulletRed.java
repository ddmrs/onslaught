/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onslaught.model.bullet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import onslaught.game.GameOperations;
import onslaught.model.enemy.Enemy;

/**
 *
 * @author Jelle Victoor
 * @author Dries
 */
public class BulletRed extends Bullet {

    private final float MAX_ROTATION_SPEED = 65;
    private final static int START_ANGLE = -45;
    private final static float SPEED = 2500f;
    private static final String URL = "images/bullet-red.png";
    private AffineTransform affineTransform = new AffineTransform();
    private double oldShootAngle = Math.toRadians(45);
    //private Enemy target;

    public BulletRed(double startX, double startY, Enemy target, GameOperations gameOps, int damage) {
        super(URL, startX, startY, SPEED, gameOps, target, damage);
        //this.target = target;
        rotationAngle -= START_ANGLE;
        startAngle = START_ANGLE;
        calcAngleToTarget(target, this, 1000000000L, 100000);
        setDamage(100);
    }

    @Override
    public void update(long elapsedTime) {
        //The bulletRed is not so fast so the dx and dy should be adjusted every update
        //checkTarget();

        //calcAngleToTarget(target, this, elapsedTime, MAX_ROTATION_SPEED);
        super.update(elapsedTime);
    }

    @Override
    protected void targetDiedEvent() {
        for (Enemy _nmy : gameOps.getEnemies()) {
            if (_nmy.isAlive()) {
                target = _nmy;
                break;
            }
        }
    }
//
//    public void targetEnemy() {
//        //calculate sin(alpha) = overstaande rechthoekzijde / schuine zijde
//        double xDist = target.getX() - super.getX();
//        double yDist = target.getY() - super.getY();
//        double distance = Math.sqrt(xDist * xDist + yDist * yDist);
//        double sinusAngle = xDist / distance;
//        //now get the angle by inverse sinus(arch sinus)
//        double targetAngle = Math.asin(sinusAngle);//RETURNS FRIGGING RAD, HOW CREWL OF YOU!
//        //enemies are below the turret: quadrant I and IV
//
//        double newShootAngle = Math.PI - targetAngle;
//        if (yDist < 0) {//enemies are above the turret: quadrant II and III
//
//            newShootAngle = 0 + targetAngle;
//        }
//        double turnAngle = newShootAngle - oldShootAngle;//doesnt measure shortest turnAngle yet
//        //to new angle
//
//        //affineTransform.rotate(turnAngle, rotationCenter.x, rotationCenter.y);
//        affineTransform.translate(dx, dy);
//        //affineTransform.
//        oldShootAngle = newShootAngle;
//    }

}
