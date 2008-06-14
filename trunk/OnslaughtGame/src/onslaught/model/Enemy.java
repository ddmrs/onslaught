package onslaught.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.JLabel;

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
        health = Math.round((hitpoints/damage));
        System.out.println("damage: " + damage);
        System.out.println("hitpoints: " + hitpoints);
        System.out.println(hitpoints/damage);
    }
    
    public boolean isAlive(){
        return (hitpoints > 0);
    }
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setFont(new Font("verdana", 0,10));
        g.setColor(new Color(0,0,0));
        g.drawString(String.valueOf(health), Math.round(getPosition().x), Math.round((float)getPosition().y));
        
    }
}
