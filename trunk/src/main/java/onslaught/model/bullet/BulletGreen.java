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
public class BulletGreen extends Bullet {
    private final static float SPEED = 2f;
    private BulletGreenSprite refSprite;
    private static final int DMG = 1;

    public BulletGreen(double startX, double startY, Enemy target, GameOperations gameOps, int damage) {
        super(new BulletGreenSprite(target.getCenter()), startX, startY, 0, gameOps, target, damage);
        z=-1;
        refSprite = (BulletGreenSprite) getSprite();
        setDamage(DMG);
    }

    @Override
    public void update(long elapsedTime) {
        if (getTarget().isAlive()) {
            getTarget().takeHit(damage);
            refSprite.setTargetPoint(getTarget().getCenter());
        } else {
            this.kill();
        }
    }
}
