package onslaught.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import onslaught.gui.Zone;
import onslaught.interfaces.ICollidable;
import onslaught.util.CollisionBoxType;

public class Sprite implements ICollidable{
    private boolean alive;
    private Zone zone;
    private Animation animation;
    // position (pixels)
    private Point2D.Float position;
    // velocity (pixels per millisecond)
    private float dx;
    private float dy;
    //store middlewidth and height to reduce calculations
    //TODO: implements this! Problem is that image width is known only after constructor, should be solved
    private float middleWidth;
    private float middleHeight;
    /**
        Creates a new Sprite object with the specified Animation.
    */
    public Sprite(Animation anim, Zone zone) {
        this.animation = anim;
        this.zone = zone;
        alive = true;
    }
    
    public Sprite(Point2D.Float position, Zone zone){
        this.position = position;
        this.animation = new Animation();
        this.zone = zone;
        alive = true;
    }

    /**
        Updates this Sprite's Animation and its position based
        on the velocity.
    */
    public void update(long elapsedTime) {
        position.x += dx;
        position.y += dy;
        //animation.update(elapsedTime);
    }

    /**
        Gets this Sprite's current x position.
    */
    public float getX() {
        return getPosition().x;
    }

    /**
        Gets this Sprite's current y position.
    */
    public float getY() {
        return getPosition().y;
    }

    /**
        Sets this Sprite's current x position.
    */
    public void setX(float x) {
        this.position.x = x;
    }

    /**
        Sets this Sprite's current y position.
    */
    public void setY(float y) {
        this.position.y = y;
    }

    /**
        Gets this Sprite's width, based on the size of the
        current image.
    */
    public int getWidth() {
        return animation.getImage().getWidth(null);
    }

    /**
        Gets this Sprite's height, based on the size of the
        current image.
    */
    public int getHeight() {
        return animation.getImage().getHeight(null);
    }

    /**
        Gets the horizontal velocity of this Sprite in pixels
        per millisecond.
    */
    public float getVelocityX() {
        return dx;
    }

    /**
        Gets the vertical velocity of this Sprite in pixels
        per millisecond.
    */
    public float getVelocityY() {
        return dy;
    }

    /**
        Sets the horizontal velocity of this Sprite in pixels
        per millisecond.
    */
    public void setVelocityX(float dx) {
        this.dx = dx;
    }

    /**
        Sets the vertical velocity of this Sprite in pixels
        per millisecond.
    */
    public void setVelocityY(float dy) {
        this.dy = dy;
    }

    /**
        Gets this Sprite's current image.
    */
    public Image getImage() {
        return animation.getImage();
    }

    /**
        Clones this Sprite. Does not clone position or velocity
        info.
    */
    @Override
    public Object clone() {
        return new Sprite(animation, zone);
    }
    /**
     * Gets the animation  for this sprite
     * */
    public Animation getAnimation() {
        return animation;
    }
    /**
     * Sets the animation for this sprite
     * @param animation
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    /**
     * Gets the postition of this sprite on the frame
     * @return
     */
    public Point2D.Float getPosition() {
        return position;
    }
    /**
     * Sets the postition of this sprite on the frame
     */
    public void setPosition(Point2D.Float position)
    {
        this.position = position;
    }
    /**
     * Gets the middle(center) of this sprite
     * */
    public Point2D.Float getMiddlePoint(){       
        return new Point2D.Float(getPosition().x + getWidth()/2, getPosition().y + getHeight()/2);
    }
    
    /**
     * Calculates the collisionbox, aka the space where a sprite can collide with another sprite
     * @return Returns this collisionbox as a rectangle based on the sprites width and height and position
     */
    public Shape getCollisionBox(){
        return new Rectangle(Math.round(getPosition().x), Math.round(getPosition().y), getWidth(), getHeight());
    }

    public CollisionBoxType getCollisionBoxType(){
        return CollisionBoxType.RECTANGLE;
    }
    
    /**
     * Calculates the distance between 2 sprites
     * @param first First sprite    
     * @param second Second sprite  
     * @return The distance 
     */
    public static double getDistance(Sprite first, Sprite second) {
        return first.getMiddlePoint().distance(second.getMiddlePoint());
    }
    
    public Zone getZone()
    {
        return zone;
    }

    public void setZone(Zone zone)
    {
        this.zone = zone;
    }
    
    /**
     * Checks if a sprite is alive
     * @return
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Set the current alive state
     * @param alive True if the sprite lives and false if it 'dies'
     */
    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }
    
    /**
     * How this sprite should be drawn on screen
     * @param g
     */
    public void draw(Graphics g){
        g.drawImage(animation.getImage(), Math.round(getPosition().x),Math.round(getPosition().y), null);
    }    
}
