package onslaught.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Sprite {

    private Animation animation;
    // position (pixels)
    private Point2D.Float position;
    // velocity (pixels per millisecond)
    private float dx;
    private float dy;

    /**
        Creates a new Sprite object with the specified Animation.
    */
    public Sprite(Animation anim) {
        this.animation = anim;
    }
    
    public Sprite(Point2D.Float position){
        this.position = position;
        this.animation = new Animation();
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
        return new Sprite(animation);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
    
    public void draw(Graphics g){
        g.drawImage(animation.getImage(), Math.round(getPosition().x),Math.round(getPosition().y), null);
    }

    public Point2D.Float getPosition() {
        return position;
    }
    
    public Point2D.Float getMiddlePoint(){       
        return new Point2D.Float(position.x + getWidth()/2, position.y + getHeight()/2);
    }
    
    public Rectangle2D getCollisionBox(){
        return new Rectangle(Math.round(position.x), Math.round(position.y), getWidth(), getHeight());
    }
    
    public double getDistance(Sprite first, Sprite second) {
        return first.getMiddlePoint().distance(second.getMiddlePoint());
    }
}
