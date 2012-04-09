/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onslaught.model.bullet;

import onslaught.game.GameOperations;
import onslaught.model.enemy.Enemy;

/**
 *
 * @author Jelle Victoor
 */
public class BulletYellow extends Bullet {

    private final static float SPEED = 2f;
    private BulletYellowSprite refSprite;
    private double oldDx;

    public BulletYellow(double startX, double startY, Enemy target, GameOperations gameOps, int damage) {
        super(new BulletYellowSprite(target.getCenter()), startX, startY, 0, gameOps, target, damage);
        z = 0;
        refSprite = (BulletYellowSprite) getSprite();
    }

    @Override
    public void update(long elapsedTime) {
        if (getTarget().isAlive()) {
            getTarget().takeHit(damage);
            double newDx = getTarget().getHorizontalMovement() / 2;
            getTarget().setHorizontalMovement(newDx);
            refSprite.setTargetPoint(getTarget().getCenter());
        } else {
            this.kill();
        }
    }

    @Override
    public void kill() {
        getTarget().setHorizontalMovement(getTarget().getSpeed());
        super.kill();
    }
}
