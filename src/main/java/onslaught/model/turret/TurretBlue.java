package onslaught.model.turret;

import onslaught.game.GameOperations;
import onslaught.model.enemy.Enemy;
import onslaught.model.bullet.BulletBlue;
import onslaught.model.enemy.HealthSD;

public class TurretBlue extends Turret {
    //TODO: All rates upgradable fields are static now
    // these fields have to be a base that can be multiplied by a factor of upgrde

    private static final int START_DMG = 100;
    private static final int RATE = 2; //This turret shoots 2 times per second
    private static final int RANGE = 60;//TODO: currently pixel dependent
    private static final String URL = "images/turret-blue.png";
    private HealthSD healthSD;

    public TurretBlue(double startX, double startY, GameOperations gameOps) {
        super(URL, startX, startY, RATE, RANGE, gameOps, START_DMG);
        healthSD = new HealthSD(this.sprite);
        this.sprite = healthSD;
    }

    @Override
    public void fire(Enemy enemy) {
        BulletBlue bullet = new BulletBlue(x, y, enemy, gameOps, damage);
        gameOps.addEntity(bullet);
    }

    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        healthSD.setHealth(getInfo());
    }
    

    
    
}
