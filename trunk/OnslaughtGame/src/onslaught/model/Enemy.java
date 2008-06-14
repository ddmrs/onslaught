package onslaught.model;

import java.awt.Graphics;
import java.awt.geom.Point2D;

public abstract class Enemy extends Sprite
{
    private int health = 100;
    private int hitpoints;
    private int level;

    public Enemy(Point2D.Float position, int level) {
        super(position);
        this.level = level;
        hitpoints = health * level;
        setVelocityX(0.5f);
        setVelocityY(0);
    }
    
    public void takeHit(int damage){
        hitpoints -= damage;
    }
    
    public boolean isAlive(){
        return (hitpoints > 0);
    }
}
