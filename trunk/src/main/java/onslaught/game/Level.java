package onslaught.game;

import java.util.ArrayList;
import java.util.List;
import onslaught.model.enemy.Enemy;
import onslaught.model.enemy.EnemyPrinter;

/**
 *
 * @author EthiC
 */
public class Level {

    private int number;
    private List<Enemy> enemies;

    public Level(int number) {
        this.number = number;
        enemies = new ArrayList<Enemy>(GameProperties.enemiesPerLevel);
        double x = Map.getStartPoint().getX();
        for (int i = 0; i < GameProperties.enemiesPerLevel; i++) {
            enemy(x - (i * 20), Map.getStartPoint().getY());
        }
    }

    private void enemy(double x, double y) {
        Enemy enemy = new EnemyPrinter(x, y, (100 * number / 10), Map.getGoal());
        enemy.setLevel(this);
        enemies.add(enemy);
    }

    public Level enemyKilled(Enemy enemy) {
        enemies.remove(enemy);
        return this;
    }

    public boolean isOver() {
        return enemies.isEmpty();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getNumber() {
        return number;
    }
}
