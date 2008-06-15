package onslaught.model.enemy;

import onslaught.model.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.JLabel;

public abstract class Enemy extends Sprite
{
    private int health = 100;
    private int hitpoints;
    private int maxHitpoints;
    private int level;

    public Enemy(Point2D.Float position, int level) {
        super(position);
        this.level = level;
        maxHitpoints = (health * level/10);
        hitpoints = maxHitpoints;
        setVelocityX(0.2f);
        setVelocityY(0);
    }
    
    public void takeHit(int damage){
        hitpoints -= damage;
        // cast int's to double to avoid loss of precision
        health = (int)Math.round(((double)hitpoints/(double)maxHitpoints) * 100);
    }
    
    public boolean isAlive(){
        return (hitpoints > 0);
    }
    
    @Override
    public void draw(Graphics g){
        super.draw(g);
        g.setFont(new Font("verdana", 0,10));
        g.setColor(new Color(0,0,0));
        g.drawString(String.valueOf(health), Math.round(getPosition().x), Math.round(getPosition().y));
        
    }
}
