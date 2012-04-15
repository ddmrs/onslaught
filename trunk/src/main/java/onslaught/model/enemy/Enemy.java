package onslaught.model.enemy;

import java.awt.Shape;
import onslaught.model.*;
import onslaught.game.Level;
import onslaught.game.Map;
import onslaught.interfaces.ICollidable;
import onslaught.util.*;

/**
 * Todo: waypoint detection
 *
 * @author EthiC
 */
public abstract class Enemy extends Entity implements ICollidable {

    private Integer health = 100;
    private int hitpoints;
    private int maxHitpoints;
    private static final double SPEED = 100f;
    private Goal goal;
    private Level level;
    private HealthSD healthSD;
    private int mapCount = 0;
    private MapHelper current = Map.getCurrent().getWayPoints().get(0);

    /**
     * Creates an enemy.
     *
     * @param ref The url of the image of this enemy
     * @param position Where the enemy should be located on start.
     * @param zone The zone where it is in.
     */
    public Enemy(String ref, double startX, double startY, int maxHitpoints, Goal goal) {
        super(ref, startX, startY);
        this.maxHitpoints = maxHitpoints;
        hitpoints = maxHitpoints;
        speed = SPEED;
        calcDispHealth();
        calcVelo(1L);
        this.goal = goal;
        z = 1;
        healthSD = new HealthSD(this.sprite);
        this.sprite = healthSD;
    }

    private void calcVelo(long passedTime) {
        double angleRad = MathUtils.calcRadAngle(current.getStartPoint(), current.getTargetPoint());
        // 1000 10 ms
        double speedStep = TimingUtility.calcTimeFactor(passedTime) * speed;
        // Update velocities
        dx = Math.cos(angleRad) * speedStep;
        dy = Math.sin(angleRad) * speedStep;
    }

    /**
     * Update the enemy's state
     *
     * @param elapsedTime Time passed by since last update.
     */
    @Override
    public void update(long elapsedTime) {
        calcVelo(elapsedTime);
        super.x += dx;
        super.y += dy;
        checkReachedEnd();
        healthSD.setHealth(health.toString());
    }

    /**
     * Let this enemy take a hit(from a bullet perhaps?)
     *
     * @param damage The damage that should be done to this enemy.
     */
    public void takeHit(int damage) {
        hitpoints -= damage;
        if (hitpoints < 1) {
            super.kill();
            return;
        }
        calcDispHealth();
    }

    /**
     * Calculates the health to display to user.
     */
    private void calcDispHealth() {
        // cast int's to double to avoid loss of precision
        health = (int) Math.round(((double) hitpoints / (double) maxHitpoints) * 100);
    }

    /**
     * This method checks wether an enemy got through the defense
     */
    private void checkReachedEnd() {
        if (goal.enterGoal(this)) {
            super.kill();
        } else if (mapCount + 1 < Map.getCurrent().getWayPoints().size() && current.reachedEnd(this, Map.getCurrent().getWayPoints().get(mapCount + 1))) {
            current = Map.getCurrent().getWayPoints().get(++mapCount);
        }
    }

    @Override
    public void collidedWith(Entity other) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Shape getCollisionBox() {
        return getBounds();
    }

    @Override
    public CollisionBoxType getCollisionBoxType() {
        return CollisionBoxType.RECTANGLE;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public void visit(EntityVisitor visitor) {
        visitor.visit(this);
    }

    public double getSpeed() {
        return SPEED;
    }
}
