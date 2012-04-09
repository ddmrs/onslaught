package onslaught.model.turret;

import java.awt.Color;
import java.awt.Shape;
import onslaught.model.*;
import onslaught.model.enemy.Enemy;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import onslaught.game.GameOperations;
import onslaught.game.Map;
import onslaught.interfaces.ICollidable;
import onslaught.interfaces.IMovable;
import onslaught.interfaces.Sprite;
import onslaught.model.enums.TurretEnum;
import onslaught.util.CollisionBoxType;
import onslaught.util.CollisionUtils;
import onslaught.util.EntityVisitor;
import static onslaught.model.enums.TurretEnum.SELECTED;
import static onslaught.model.enums.TurretEnum.MOVABLE;
import static onslaught.model.enums.TurretEnum.OPERATING;

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
    private long timeLastShot;
    private long reloadTime;
    private List<Enemy> targets;
    private TurretEnum state;
    //private boolean locked = false;
    private Rectangle2D rangeBox;
    private Rectangle2D clickableZone;
    private Enemy target;
    private Sprite turretSprite;
    //max 270° degrees per second
    private final float MAX_ROTATION_SPEED = 180;

    public Turret(String ref, double startX, double startY, int rate, int range, GameOperations gameOps, int damage) {
        super(ref, startX, startY);
        turretSprite = sprite;
        //this.currentTime = 0;
        this.timeLastShot = -1L;
        this.targets = gameOps.getEnemies();
        this.rate = rate;
        radius = range;
        calcReloadTime();
        calcRangeBox();

        this.gameOps = gameOps;
        currentTime = reloadTime;
        rotationAngle -= START_ANGLE;
        startAngle = START_ANGLE;
        this.damage = damage;
        //oldAngle = 0;
        setState(TurretEnum.MOVABLE);
    }

    private boolean isPlaceable() {
        boolean placeable = true;
        for (Turret other : gameOps.getTurrets()) {
            if (other != this && other.getBounds().intersects(this.getBounds())) {
                placeable = false;
            }
        }
        if (!placeable && Map.getGoal().getBounds().intersects(this.getBounds())) {
            placeable = false;
        }
        for (Entity entity : gameOps.getNewEntities()) {
            if (!placeable || entity instanceof Turret) {
                if (entity.getBounds().intersects(this.getBounds())) {
                    placeable = false;
                    break;
                }
            }
        }
        return placeable;
    }

    private void calcRangeBox() {
        // this.rangeBox = new Rectangle2D.Double(x - range, y - range, range * 2, range * 2);
        this.clickableZone = new Rectangle2D.Double(x - sprite.getWidthPart(), y - sprite.getHeightPart(), sprite.getWidth(), sprite.getHeight());
    }

    private void calcReloadTime() {
        this.reloadTime = (long) (1000000.0 / rate);
    }

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
     * direction TODO: calculate rotation angle instead of resetting, dno maybe
     * more performance then TODO: turret instantly points at enemy, ist very
     * realistic ;-)
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

    public void setTimeLastShot(long timeLastShot) {
        this.timeLastShot = timeLastShot;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public boolean isSelected() {
        return (state == SELECTED);
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
            rate += rate /2;
            calcReloadTime();
        }
        System.out.println(getInfo());
    }

    @Override
    public void draw(Graphics graphics) {
        switch (state) {
            case SELECTED:
//            graphics.setColor(Color.MAGENTA);
//            graphics.drawOval((int) (super.getMiddlePoint().x - range), (int) (super.getMiddlePoint().y - range), (int) (range * 2), (int) (range * 2));
                graphics.setColor(Color.GREEN);
                graphics.drawRect((int) rangeBox.getX(), (int) rangeBox.getY(), (int) rangeBox.getWidth(), (int) rangeBox.getHeight());
                break;
            case MOVABLE:
                break;
            case OPERATING:
                break;
        }
        final Graphics2D graphics2D = (Graphics2D) graphics;
        //graphics2D.drawImage(super.sprite.getImage(), affineTransform, null);
    }

    public void select() {
        setState(TurretEnum.SELECTED);
    }

    public void deselect() {
        setState(TurretEnum.OPERATING);
    }

    public TurretEnum getState() {
        return state;
    }

    public void mouseMoves(int x, int y) {
    }

    public void replace(int x, int y) {
        super.x = (double) x;
        super.y = (double) y;
        this.state = TurretEnum.SELECTED;
    }

    public void setMoving(boolean moving) {
        if (moving) {
            this.state = TurretEnum.MOVABLE;
        } else {
            this.state = TurretEnum.SELECTED;
        }
    }

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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("X en Y : " + x + ',' + y);
        sb.append(" ;Width: " + sprite.getWidth() + ',' + sprite.getHeight());
        return sb.toString();
    }

    protected String getInfo() {
        StringBuilder sb = new StringBuilder("D: ").append(damage);
        sb.append(" RG: ").append(radius).append(" RT ").append(rate);
        return sb.toString();
    }
}
