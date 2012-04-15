package onslaught.model.bullet;

import onslaught.game.GameOperations;
import onslaught.game.GameProperties;
import onslaught.game.Map;
import onslaught.game.OnslaughtGame;
import onslaught.model.Entity;
import onslaught.model.enemy.Enemy;
import onslaught.util.TimingUtility;

public class BulletBlue extends Bullet {

    private final static double SPEED = 700f;
    private static final String URL = "images/bullet-blue.png";

    public BulletBlue(double startX, double startY, Enemy target, GameOperations gameOps, int damage) {
        super(URL, startX, startY, SPEED, gameOps, target, damage);
        calcVelocities(target, TimingUtility.getOneSecond() / GameProperties.WANTED_FPS);
    }

    @Override
    public void update(long currentTime) {
        super.update(currentTime);
        if (this.x > Map.WIDTH || this.x < 0 || this.y > Map.HEIGHT || this.y < 0) {
            this.kill();
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
    }

    @Override
    protected void targetDiedEvent() {
        for (Enemy _nmy : gameOps.getEnemies()) {
            if (_nmy.isAlive()) {
                checkTarget(_nmy);
            }
        }
    }
}
