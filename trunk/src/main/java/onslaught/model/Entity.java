package onslaught.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import onslaught.core.ResourceFactory;
import onslaught.interfaces.Sprite;
import onslaught.util.MathUtils;
import onslaught.util.TimingUtility;

/**
 *
 * @author EthiC
 */
public abstract class Entity {

    /**
     * The current x location of this entity
     */
    protected double x;
    /**
     * The current y location of this entity
     */
    protected double y;
    /**
     * The current z location of this entity
     */
    protected double z;
    /**
     * The sprite that represents this entity
     */
    protected Sprite sprite;
    /**
     * The current speed of this entity horizontally (pixels/sec)
     */
    protected double dx;
    /**
     * The current speed of this entity vertically (pixels/sec)
     */
    protected double dy;
    /**
     * The angle that the entity has to be rotated *
     */
    protected double rotationAngle;
    /**
     * Speed of the entity *
     */
    protected double speed;
    /**
     * The rectangle used for this entity during collisions resolution
     */
    private Rectangle me = new Rectangle();
    /**
     * The rectangle used for other entities during collision resolution
     */
    private Rectangle him = new Rectangle();
    /**
     * Wheter this ent is alive or not
     */
    private boolean alive = true;
    /**
     * the radius for easy collision detectino
     */
    protected double radius;
    protected boolean locked = false;
    protected double oldAngle = 0;
    protected int startAngle;

    /**
     * Construct an entity based on a sprite image and a location.
     *
     * @param ref The reference to the image to be displayed for this entity
     * @param x The initial x location of this entity
     * @param y The initial y location of this entity
     */
    public Entity(String ref, double x, double y) {
        this(ResourceFactory.get().getSprite(ref), x, y);
    }

    public Entity(Sprite sprite, double x, double y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        radius = MathUtils.calcRadius(sprite.getWidthPart(), sprite.getHeightPart());
    }

    /**
     * Set the horizontal speed of this entity
     *
     * @param dx The horizontal speed of this entity (pixels/sec)
     */
    public void setHorizontalMovement(double dx) {
        this.dx = dx;
    }

    /**
     * Set the vertical speed of this entity
     *
     * @param dx The vertical speed of this entity (pixels/sec)
     */
    public void setVerticalMovement(double dy) {
        this.dy = dy;
    }

    /**
     * Get the horizontal speed of this entity
     *
     * @return The horizontal speed of this entity (pixels/sec)
     */
    public double getHorizontalMovement() {
        return dx;
    }

    /**
     * Get the vertical speed of this entity
     *
     * @return The vertical speed of this entity (pixels/sec)
     */
    public double getVerticalMovement() {
        return dy;
    }

    /**
     * Draw this entity to the graphics context provided
     *
     * @param g The graphics context on which to draw
     */
    public void draw(Graphics graphics) {
        //sprite.draw((int) x, (int) y, rotationAngle);
    }

    /**
     * Draw this entity to the graphics context provided
     *
     * @param g The graphics context on which to draw
     */
    public void draw() {
        sprite.draw((float) x, (float) y, (float) z, rotationAngle);
    }

    /**
     * Do the logic associated with this entity. This method will be called
     * periodically based on game events
     */
    public void doLogic() {
    }

    /**
     * Get the x location of this entity
     *
     * @return The x location of this entity
     */
    public int getX() {
        return (int) x;
    }

    /**
     * Get the y location of this entity
     *
     * @return The y location of this entity
     */
    public int getY() {
        return (int) y;
    }

    public Point2D getMiddle() {
        return new Point2D.Double(x, y);
    }

    /**
     * Check if this entity collised with another.
     *
     * @param other The other entity to check collision against
     * @return True if the entities collide with each other
     */
    public boolean collidesWith(Entity other) {
        me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
        him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());

        return me.intersects(him);
    }

    /**
     * Notification that this entity collided with another.
     *
     * @param other The entity with which this entity collided.
     */
    public abstract void collidedWith(Entity other);

    /**
     * Check if this entity is alive or not. Used for deleting the entity out of
     * the list.
     *
     * @return True if its alive, false if not.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Kill this entity. Remove it from screen.
     */
    public void kill() {
        this.alive = false;
    }

    /**
     * Calculates the center of this entity.
     *
     * @return The center of the entity.
     */
    public Point2D getCenter() {
        return new Point2D.Double(x, y);
    }

    /**
     * Sets the position of this entity.
     *
     * @param newPosition The new position where the entity should be located.
     */
    public void setPosition(Point2D newPosition) {
        this.x = newPosition.getX();
        this.y = newPosition.getY();
    }

    /**
     * Require that every entity implements its own update routine.
     *
     * @param time Time elapsed in milliseconds.
     */
    public abstract void update(long time);

    /**
     * @return Returns the (rectangular) boundary of this entity
     */
    public Rectangle2D getBounds() {
        me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
        return me;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    protected void calcAngleToTarget(Entity target, Entity origin, long elapsedTime, float maxRotation) {
        // Calculate the angle
        double xDist = target.getX() - origin.getX();
        double yDist = target.getY() - origin.getY();
        double targetAngle = Math.atan2(yDist, xDist);//RETURNS FRIGGING RAD, HOW CREWL OF YOU!

        // Convert rad to degrees
        targetAngle = (targetAngle * 180 / MathUtils.getPi());

        // Calc time interval to calculate maxAngle to be rotated for this update
        double diff = (double) elapsedTime / TimingUtility.getOneSecond();
        double maxAngle = maxRotation * diff;//In degrees!

        // Calculate difference with previous angle
        double diffAngle = targetAngle - oldAngle;
        if (diffAngle > 180) {
            diffAngle -= 360;
        } else if (diffAngle < -180) {
            diffAngle += 360;
        }
        if (diffAngle < 0) {
            double temp = oldAngle - maxAngle;
            if (temp < -180) {
                temp += 360;
            }
            if (temp <= targetAngle) {
                rotationAngle = targetAngle;
                locked = true;
            } else {
                locked = false;
                rotationAngle = temp;
            }
        } else {
            double temp = oldAngle + maxAngle;
            if (temp > 180) {
                temp -= 360;
            }
            if (temp >= targetAngle) {
                locked = true;
                rotationAngle = targetAngle;
            } else {
                locked = false;
                rotationAngle = temp;
            }
        }
        oldAngle = rotationAngle;
        rotationAngle -= startAngle;
    }

    protected void calcVelocities(Entity target, long passedTime) {
        calcAngleToTarget(target, this, passedTime, 720);
        double angleRad = rotationAngle / 180 * MathUtils.getPi();
        // 1000 10 ms
        double speedStep = TimingUtility.calcTimeFactor(passedTime) * speed;
        // Update velocities
        dx = Math.cos(angleRad) * speedStep;
        dy = Math.sin(angleRad) * speedStep;
    }
}
