package onslaught.model.enemy;

import java.awt.Shape;
import onslaught.model.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import onslaught.game.Level;
import onslaught.interfaces.ICollidable;
import onslaught.util.CollisionBoxType;
import onslaught.util.EntityVisitor;

/**
 * Todo: waypoint detection
 * @author EthiC
 */
public abstract class Enemy extends Entity implements ICollidable {

    private int health = 100;
    private int hitpoints;
    private int maxHitpoints;
    private static final double SPEED = 100f;
    private boolean drawHealth = true;
    private Goal goal;
    private Level level;
    private HealthSD healthSD;

    /**
     * Creates an enemy.
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
        calcVelocities(goal, 1);
        this.goal = goal;
        z = -1;
        healthSD = new HealthSD(this.sprite);
        this.sprite = healthSD;
    }

    /**
     * Update the enemy's state
     * @param elapsedTime Time passed by since last update.
     */
    public void update(long elapsedTime) {
        calcVelocities(goal, elapsedTime);
        super.x += dx;
        super.y += dy;
        checkReachedEnd();
        healthSD.setHealth("" + health);
    }

    /**
     * Let this enemy take a hit(from a bullet perhaps?)
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
        }
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);
        
        if (drawHealth) {
            healthSD.setHealth(""+health);
        }
    }

    /**
     * Draw the enemies health.
     * @param graphics
     */
    private void drawHealth(Graphics graphics) {
        graphics.setFont(new Font("verdana", 0, 10));
        graphics.setColor(new Color(0, 0, 0));
        graphics.drawString(String.valueOf(health), (int) x, (int) y);
    }

    @Override
    public void collidedWith(Entity other) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public Shape getCollisionBox() {
        return getBounds();
    }

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
