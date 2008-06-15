package onslaught.model.turret;

import java.awt.Graphics;
import onslaught.model.enemy.Enemy;
import onslaught.model.bullet.BulletBlue;
import onslaught.model.bullet.Bullet;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;

public class TurretBlue extends Turret
{
    //TODO: All rates upgradable fields are static now
    // these fields have to be a base that can be multiplied by a factor of upgrde
    
    private static final int RATE = 2; //This turret shoots 2 times per second
    private static final int RANGE = 150;//TODO: currently pixel dependent
    
    public TurretBlue(Point2D.Float position) {
        super(position, RATE, RANGE);
        getAnimation().addFrame(new ImageIcon("resources/images/turret-blue.png").getImage(), 1);
	getAnimation().start();
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
//        g.setColor(Color.BLUE);
//        g.fillOval(Math.round(getPosition().x), Math.round(getPosition().y), WIDTH, HEIGHT);
//        g.setColor(Color.RED);
//        g.drawOval(Math.round(getPosition().x) - (int)getRange(), Math.round(getPosition().y) - (int)getRange(), getWidth() + (int)getRange() * 2, getHeight() + (int)getRange() * 2);
        super.draw(g);
       // g.setColor(Color.GREEN);
      //  g.drawRect(Math.round(getPosition().x) - (int)getRange(), Math.round(getPosition().y) - (int)getRange(), getWidth() + (int)getRange() * 2, getHeight() + (int)getRange() * 2);
    }

    @Override
    public Bullet shoot(Enemy enemy, long currentTime) {
        if(!isAbleToShoot(currentTime)) {
            return null;
        }
        setTimeLastShot(currentTime);
        return  new BulletBlue(getMiddlePoint(), enemy);
    }

}
