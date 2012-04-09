package onslaught.model.turret;

import onslaught.game.GameOperations;
import onslaught.model.bullet.BulletYellow;
import onslaught.model.enemy.Enemy;

/**
 * The taser.
 * @author Dries
 */
public class TurretYellow extends Turret {
    //TODO: All rates upgradable fields are static now
    // these fields have to be a base that can be multiplied by a factor of upgrde

    private static final int START_DMG = 10;
    private static final String URL = "images/turret-yellow.png";
    private static final int RATE = 999; //This turret shoots 999 times per second

    private static final int RANGE = 50;//TODO: currently pixel dependent
    private boolean start = false;
    private BulletYellow old;

    public TurretYellow(double startX, double startY, GameOperations gameOps) {
        super(URL, startX, startY, RATE, RANGE, gameOps, START_DMG);
        rotationAngle = 0;
        locked = true;
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        if (!hasTarget()) {
            start = false;
        }
    }

    @Override
    public void fire(Enemy enemy) {
        if (old == null || !old.isAlive()) {
            old = new BulletYellow(x, y, enemy, gameOps, damage);
            gameOps.addEntity(old);
        }
    }

    @Override
    protected void targetEnemy(Enemy target, long elapsedTime) {
        //This turret doesnt need to rotate.
    }

    @Override
    protected void targetOutOfRangeEvent() {
        old.kill();
    }

    @Override
    public boolean isAbleToShoot() {
        return !start;
    }
}
