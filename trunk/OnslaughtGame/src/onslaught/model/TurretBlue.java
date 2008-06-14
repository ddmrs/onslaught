package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

public class TurretBlue extends Turret
{

    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;
    private static final int RATE = 2; //This turret shoots 2 times per second
    private static final int RANGE = 50;//TODO: currently pixel dependent
    
    public TurretBlue(Point2D.Float position) {
        super(position, RATE, RANGE);

    }

    public void turn(int degrees) {
    // TODO Auto-generated method stub

    }

    public void upgradeDamage() {
    // TODO Auto-generated method stub

    }

    public void upgradeRange() {
    // TODO Auto-generated method stub

    }

    public void upgradeRate() {
    // TODO Auto-generated method stub

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(Math.round(getPosition().x), Math.round(getPosition().y), WIDTH, HEIGHT);
        g.setColor(Color.RED);
        g.drawOval(Math.round(getPosition().x) - (int)getRange(), Math.round(getPosition().y) - (int)getRange(), getWidth() + (int)getRange() * 2, getHeight() + (int)getRange() * 2);
        g.setColor(Color.GREEN);
        g.drawRect(Math.round(getPosition().x) - (int)getRange(), Math.round(getPosition().y) - (int)getRange(), getWidth() + (int)getRange() * 2, getHeight() + (int)getRange() * 2);
    }

    @Override
    public Bullet shoot(Enemy enemy, long currentTime) {
        if(!isAbleToShoot(currentTime)) {
            return null;
        }

        BulletBlue bullet = new BulletBlue(getMiddlePoint(), enemy);

        float xDist = enemy.getPosition().x - bullet.getMiddlePoint().x;
        float yDist = enemy.getPosition().y - bullet.getMiddlePoint().y;
        float steps = (float) (getDistance(enemy, bullet)) / bullet.getSpeed();
        bullet.setVelocityX(xDist / steps);
        bullet.setVelocityY(yDist / steps);

        setTimeLastShot(currentTime);
        return bullet;
    }

    public double getDistance(Enemy enemy, Bullet bullet) {
        return enemy.getPosition().distance(bullet.getPosition());
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
