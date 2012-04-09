/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onslaught.model.bullet;

import java.awt.geom.AffineTransform;
import onslaught.game.GameOperations;
import onslaught.model.enemy.Enemy;

/**
 *
 * @author Dries
 */
public class BulletRed extends Bullet {

    private final float MAX_ROTATION_SPEED = 65;
    private final static int START_ANGLE = -45;
    private final static float SPEED = 400f;
    private static final String URL = "images/bullet-red.png";
    private AffineTransform affineTransform = new AffineTransform();
    private double oldShootAngle = Math.toRadians(45);

    public BulletRed(double startX, double startY, Enemy target, GameOperations gameOps, int damage) {
        super(URL, startX, startY, SPEED, gameOps, target, damage);
        rotationAngle -= START_ANGLE;
        startAngle = START_ANGLE;
        calcAngleToTarget(target, this, 1000000000L, 100000);
        setDamage(100);
    }

    @Override
    public void update(long elapsedTime) {
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

}
