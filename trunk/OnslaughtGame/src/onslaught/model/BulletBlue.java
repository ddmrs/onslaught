package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

public class BulletBlue extends Bullet
{
    
    private final static int SPEED = 5;
    private final static int WIDTH = 8;
    private final static int HEIGHT = 8;

    public BulletBlue(Point2D.Float position, Enemy target) {
        super(position, target);
        setDamage(50);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval(Math.round(getPosition().x), Math.round(getPosition().y), WIDTH, HEIGHT);
    }
    
    @Override
    public int getWidth(){
        return WIDTH;
    }
    @Override
    public int getHeight(){
        return HEIGHT;
    }    

    public int getSpeed() {
        return SPEED;
    }
}
