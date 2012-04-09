package onslaught.model.turret;

import onslaught.game.GameOperations;
import onslaught.model.bullet.BulletRed;
import onslaught.model.enemy.Enemy;

/**
 *
 */
public class TurretRed extends Turret {
    //TODO: All rates upgradable fields are static now
    // these fields have to be a base that can be multiplied by a factor of upgrde

    private static final String URL = "images/turret-red.png";
    private static final int RATE = 1; //This turret shoots 1 time per second
    private static final int RANGE = 80; //TODO: currently pixel dependent
    private static final int START_DMG = 150;

    public TurretRed(double startX, double startY, GameOperations gameOps) {
        super(URL, startX, startY, RATE, RANGE, gameOps, START_DMG);
    }

    @Override
    public void fire(Enemy enemy) {
        gameOps.addEntity(new BulletRed(x, y, enemy, gameOps, damage));
    }
}
