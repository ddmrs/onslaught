package onslaught.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import onslaught.gui.MouseDummy;
import onslaught.interfaces.ISelectable;
import onslaught.model.Entity;
import onslaught.model.enemy.Enemy;
import onslaught.model.turret.Turret;
import onslaught.model.turret.TurretType;

/**
 *
 * @author ethic
 */
public class GameOperations {

    private boolean gameRunning = true;
    private boolean paused = false;
    // Alle objecten
    private List<Entity> entities = new ArrayList<Entity>();
    // Alle klikbare objecten
    private List<ISelectable> selectables = new ArrayList<ISelectable>();
    // temp objecten die geadd moeten worden.
    private List<Entity> newEntities = new ArrayList<Entity>();
    // Alle enemies op het scherm.
    private List<Enemy> enemies = new ArrayList<Enemy>();
    // Alle kanonnen.
    private List<Turret> turrets = new ArrayList<Turret>();
    // Should keep statistics of the current game.
    private Statistics stats;
    private GameProperties props;
    private List<Level> levels = new ArrayList<Level>();
    private long totalTimepassed = 0;
    private MouseDummy mousy;

    public void prepareNewGame() {
        entities.clear();
        stats = new Statistics();
        props = new GameProperties();
        // Add the goal for the enemies
        entities.add(onslaught.game.Map.getGoal());
        // Create the mouse
        mousy = new MouseDummy(this);
        mousy.setGrabbed(true);
        entities.add(mousy);
    }

    // Game functions --------------------------------------------------------//
    public void sendNextWave() {
        if (!paused) {
            Level newLevel = new Level(levels.size() + 1);
            newEntities.addAll(newLevel.getEnemies());
            //stats.increaseEnemiesSent();
            levels.add(newLevel);
        }
    }

    public void pauseGame(boolean pause) {
        paused = pause;
        mousy.setGrabbed(!pause);
    }

    /**
     * Delete a turret from the game.(or Sell one)
     */
    public void deleteTurret() {
        if (!paused) {
            if (mousy.getSelected() != null && mousy.getSelected() instanceof Turret) {
                Turret turrToSell = (Turret) mousy.getSelected();
                turrToSell.sell();
            }
        }
    }

    /**
     * Add a turret to the game screen
     *
     * @param turret
     */
    public void addTurret(TurretType turretType) {
        if (!paused) {
            mousy.setTurret(turretType.buildTurret(0, 0, this));
        }
    }

    /**
     * Upgrade the range of the selected turret.
     */
    public void upgradeRangeOfSelectedTurret() {
        if (!paused) {
            if (mousy.getSelected() instanceof Turret) {
                Turret sel = (Turret) mousy.getSelected();
                sel.upgradeRange();
            }
        }
    }

    /**
     * Upgrade the range of the selected turret.
     */
    public void upgradeRateOfSelectedTurret() {
        if (!paused) {
            if (mousy.getSelected() instanceof Turret) {
                Turret sel = (Turret) mousy.getSelected();
                sel.upgradeRate();
            }
        }
    }

    /**
     * Upgrade the range of the selected turret.
     */
    public void upgradeDamageOfSelectedTurret() {
        if (!paused) {
            if (mousy.getSelected() instanceof Turret) {
                Turret sel = (Turret) mousy.getSelected();
                sel.upgradeDamage();
            }
        }
    }

    /**
     * Exit the game.
     */
    public void exitGame() {
        gameRunning = false;
    }

    public void addEntity(Entity entity) {
        newEntities.add(entity);
    }

    /**
     * Update all the game stuff like entities and events.
     *
     * @param timePassed The time passed since last update.
     */
    public void update(long timePassed) {
        if (!paused) {
            updateGameStates(timePassed);
            // Add all new entities to the right collections
            for (Entity newEntity : newEntities) {
                if (newEntity instanceof ISelectable) {
                    selectables.add((ISelectable) newEntity);
                } else if (newEntity instanceof Enemy) {
                    enemies.add((Enemy) newEntity);
                } else if (newEntity instanceof Turret) {
                    turrets.add((Turret) newEntity);
                }
            }
            // And add them to allEntities list
            entities.addAll(newEntities);
            newEntities.clear();

            //Update all entities and delete the killed one's
            Iterator<Entity> it = entities.iterator();
            while (it.hasNext()) {
                Entity entity = it.next();
                //only update 'alive' entities
                if (entity.isAlive()) {
                    entity.update(timePassed);
                } else {
                    // Killed entities have to be removed
                    it.remove();
                    selectables.remove(entity);
                    if (enemies.remove(entity)) {
                        Enemy enemy = (Enemy) entity;
                        enemy.getLevel().enemyKilled(enemy);
                        if (enemy.getLevel().isOver() && props.isSendWaveImmediately()) {
                            sendNextWave();
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * Updates the time, looks if the end is reached, sends next waves etc.
     *
     * @param timePassed
     */
    private void updateGameStates(long timePassed) {
        totalTimepassed += timePassed;
        if (totalTimepassed >= props.getTimeBetweenWaves()) {
            sendNextWave();
            totalTimepassed = 0;
        }
        if (!onslaught.game.Map.getGoal().isAlive()) {
            gameRunning = false;
        }
    }

    // GETTERS AND SETTERS !-----------------------------------------------
    /**
     *
     * @return
     */
    public List<Entity> getEntities() {
        return entities;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Statistics getStats() {
        return stats;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public List<Turret> getTurrets() {
        return turrets;
    }

    public List<Entity> getNewEntities() {
        return newEntities;
    }

    public List<ISelectable> getSelectables() {
        return selectables;
    }

    public MouseDummy getMousy() {
        return mousy;
    }
}
