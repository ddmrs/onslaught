/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onslaught.model.bullet;

import onslaught.game.GameOperations;
import onslaught.game.GameProperties;
import onslaught.game.Map;
import onslaught.game.OnslaughtGame;
import onslaught.model.Entity;
import onslaught.model.enemy.Enemy;
import onslaught.util.MathUtils;
import onslaught.util.TimingUtility;

/**
 *
 * @author Dries
 */
public class BulletRed extends Bullet {

    private final float MAX_ROTATION_SPEED = 65;
    private final static int START_ANGLE = -35;
    private final static float SPEED = 400f;
    private static final String URL = "images/bullet-red.png";

    public BulletRed(double startX, double startY, Enemy target, GameOperations gameOps, int damage) {
        super(URL, startX, startY, SPEED, gameOps, target, damage);
        rotationAngle -= START_ANGLE;
        calcVelocities(target, TimingUtility.getOneSecond() / GameProperties.WANTED_FPS);
    }

    @Override
    public void update(long currentTime) {
        if (target != null) {
            super.update(currentTime);
            if (this.x > Map.WIDTH || this.x < 0 || this.y > Map.HEIGHT || this.y < 0) {
                this.kill();
            }
        }
    }

    @Override
    protected void calcVelocities(Entity target, long passedTime) {
        double speedStep = TimingUtility.calcTimeFactor(passedTime) * speed;

        double xDist = target.getX() - getX();
        double yDist = target.getY() - getY();
        double targetAngle = Math.atan2(yDist, xDist);

        dx = Math.cos(targetAngle) * speedStep;
        dy = Math.sin(targetAngle) * speedStep;

        rotationAngle = targetAngle * (180 / MathUtils.PI);
        rotationAngle -= START_ANGLE;
    }

    @Override
    protected void targetDiedEvent() {
        for (Enemy _nmy : gameOps.getEnemies()) {
            if (_nmy.isAlive()) {
                checkTarget(_nmy);
            }
        }
    }
//        rotationAngle -= START_ANGLE;
//        startAngle = START_ANGLE;
//        calcAngleToTarget(target, this, 1000000000L, 100000);
//        setDamage(100);
//    }
//
//    @Override
//    public void update(long elapsedTime) {
//        super.update(elapsedTime);
//    }
//
//    @Override
//    protected void targetDiedEvent() {
//        for (Enemy _nmy : gameOps.getEnemies()) {
//            if (_nmy.isAlive()) {
//                target = _nmy;
//                break;
//            }
//        }
//    }
}
