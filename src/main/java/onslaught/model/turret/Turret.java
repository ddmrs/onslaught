package onslaught.model.turret;

import java.awt.Rectangle;
import java.awt.Shape;
import onslaught.model.*;
import onslaught.model.enemy.Enemy;
import java.awt.geom.Rectangle2D;
import onslaught.game.GameOperations;
import onslaught.game.Map;
import onslaught.interfaces.ICollidable;
import onslaught.interfaces.IMovable;
import onslaught.interfaces.Sprite;
import onslaught.model.enums.TurretEnum;
import onslaught.util.CollisionBoxType;
import onslaught.util.CollisionUtils;
import onslaught.util.EntityVisitor;

public abstract class Turret extends Entity implements IMovable, ICollidable {
    // TURRET TEXTURE's INITIAL DIRECTION = -45°

    private final static int START_ANGLE = -45;
    //private double oldAngle;
    protected GameOperations gameOps;
    protected int damage;
    protected double rate; //in shots per second
    protected int dmgLvl = 1;
    protected int rangeLvl = 1;
    protected int rateLvl = 1;
    private long currentTime = 0;
    private long reloadTime;
    private TurretEnum state;
    private Rectangle2D rangeBox;
    private Rectangle2D clickableZone;
    private Enemy target;
    private Sprite turretSprite;
    //max 270° degrees per second
    private final float MAX_ROTATION_SPEED = 180;

    public Turret(String ref, double startX, double startY, int rate, int range, GameOperations gameOps, int damage) {
        super(ref, startX, startY);
        turretSprite = sprite;
        this.rate = rate;
        radius = range;
        calcReloadTime();
        calcRangeBox();

        this.gameOps = gameOps;
        currentTime = reloadTime;
        rotationAngle -= START_ANGLE;
        startAngle = START_ANGLE;
        this.damage = damage;
        setState(TurretEnum.MOVABLE);
    }

    public boolean isPlaceable(int newX, int newY) {
        Rectangle newBounds = new Rectangle(newX, newY, getSprite().getWidth(), getSprite().getHeight());
        if (Map.getGoal().getBounds().intersects(newBounds)) {
            return false;
        }
        for (Turret other : gameOps.getTurrets()) {
            if (other != this && other.getBounds().intersects(newBounds)) {
                return false;
            }
        }
        for (Entity entity : gameOps.getNewEntities()) {
            if (entity instanceof Turret && entity.getBounds().intersects(newBounds)) {
                return false;
            }
        }
        return true;
    }

    private void calcRangeBox() {
        this.clickableZone = new Rectangle2D.Double(x - sprite.getWidthPart(), y - sprite.getHeightPart(), sprite.getWidth(), sprite.getHeight());
    }

    private void calcReloadTime() {
        this.reloadTime = (long) (1000000.0 / rate);
    }

    @Override
    public void update(long elapsedTime) {
        currentTime += elapsedTime;
        if (this.state != TurretEnum.MOVABLE) {
            this.trackEnemies(elapsedTime);
        }
    }

    public void trackEnemies(long elapsedTime) {
        if (hasTarget() && !CollisionUtils.inRange(this, target)) {
            target = null;
            targetOutOfRangeEvent();
        }
        if (!hasTarget()) {
            //loop every enemy
            for (Enemy enemy : gameOps.getEnemies()) {
                //check if enemy is alive and look if enemy comes in range
                if (enemy.isAlive() && CollisionUtils.inRange(this, enemy)) {
                    target = enemy;
                    //cannot shoot at multiple targets at once
                    break;
                }
            }
        }
        if (hasTarget()) {
            targetInRangeEvent(elapsedTime);
        }
    }

    /**
     * Called when a target went out of range.
     */
    protected void targetOutOfRangeEvent() {
    }

    /**
     * Called when a target is in range, default turns the turret at the enemy
     * and tries to shoot at it.
     *
     * @param elapsedTime The elapsed time from last update.
     */
    protected void targetInRangeEvent(long elapsedTime) {
        //target the gun at the enemy
        targetEnemy(target, elapsedTime);
        //shoot only when a bullet is loaded
        if (isAbleToShoot() && locked) {
            this.fire(target);
            currentTime = 0;
        }
    }

    /**
     * Function that tracks an enemy, updates the image to look in enemy
     * direction
     *
     * @param target Enemy to track
     */
    protected void targetEnemy(Enemy target, long elapsedTime) {
        super.calcAngleToTarget(target, this, elapsedTime, MAX_ROTATION_SPEED);
    }

    /**
     * Determines if this turret is allowed to shoot or not.
     *
     * @param currentTime time of request
     * @return Returns true if this turret can shoot again
     */
    public boolean isAbleToShoot() {
        return (currentTime >= reloadTime);
    }

    abstract public void fire(Enemy enemy);

    public Rectangle2D getRangeBox() {
        return this.rangeBox;
    }

    @Override
    public Shape getCollisionBox() {
        return this.getRangeBox();
    }

    @Override
    public CollisionBoxType getCollisionBoxType() {
        return CollisionBoxType.CIRCLE;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isSelected() {
        return (state == TurretEnum.SELECTED);
    }

    public void sell() {
        this.kill();
    }

    public void upgradeDamage() {
        if (dmgLvl < 5) {
            dmgLvl++;
            damage = damage * dmgLvl;
        }
        System.out.println(getInfo());
    }

    public void upgradeRange() {
        if (rangeLvl < 5) {
            rangeLvl++;
            radius += (radius / 2);
        }
        System.out.println(getInfo());
    }

    public void upgradeRate() {
        if (rateLvl < 5) {
            rateLvl++;
            rate += rate / 2;
            calcReloadTime();
        }
        System.out.println(getInfo());
    }

    @Override
    public void select() {
        setState(TurretEnum.SELECTED);
    }

    @Override
    public void deselect() {
        setState(TurretEnum.OPERATING);
    }

    public TurretEnum getState() {
        return state;
    }

    @Override
    public void draw() {
        // Don't keep drawing if mouse is already drawing you
        if (state != TurretEnum.MOVABLE) {
            super.draw();
        }
    }

    @Override
    public void mouseMoves(int x, int y) {
    }

    @Override
    public void replace(int x, int y) {
        super.x = (double) x;
        super.y = (double) y;
        this.state = TurretEnum.SELECTED;
    }

    @Override
    public void setMoving(boolean moving) {
        if (moving) {
            this.state = TurretEnum.MOVABLE;
        } else {
            this.state = TurretEnum.SELECTED;
        }
    }

    @Override
    public Rectangle2D getClickZone() {
        return this.clickableZone;
    }

    @Override
    public void collidedWith(Entity other) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * check if we already have a target.
     *
     * @return true if we have a target already.
     */
    protected boolean hasTarget() {
        return target != null && target.isAlive();
    }

    void visit(EntityVisitor visitor) {
        visitor.visit(this);
    }

    public void setState(TurretEnum state) {
        this.state = state;
        this.sprite = state.getDecoratedSprite(this);
        calcRangeBox();
    }

    public Sprite getTurretSprite() {
        return turretSprite;
    }

    public void setTurretSprite(Sprite turretSprite) {
        this.turretSprite = turretSprite;
        this.sprite = turretSprite;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("X en Y : ").append(x).append(",").append(y);
        sb.append(" ;Width: ").append(sprite.getWidth()).append(",").append(sprite.getHeight());
        return sb.toString();
    }

    protected String getInfo() {
        StringBuilder sb = new StringBuilder("D: ").append(damage);
        sb.append(" RG: ").append(radius).append(" RT ").append(rate);
        return sb.toString();
    }
}
