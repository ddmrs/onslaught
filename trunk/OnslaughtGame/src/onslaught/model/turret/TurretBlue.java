package onslaught.model.turret;

import java.awt.Color;
import java.awt.Graphics;
import onslaught.model.enemy.Enemy;
import onslaught.model.bullet.BulletBlue;
import onslaught.model.bullet.Bullet;
import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.ImageIcon;
import onslaught.gui.Zone;

public class TurretBlue extends Turret
{
    //TODO: All rates upgradable fields are static now
    // these fields have to be a base that can be multiplied by a factor of upgrde
    private static final int RATE = 6; //This turret shoots 2 times per second
    private static final int RANGE = 150;//TODO: currently pixel dependent
    public TurretBlue(Point2D.Float position, Zone zone, List<Enemy> enemies)
    {
        super(position, RATE, RANGE, zone, enemies);
        getAnimation().addFrame(new ImageIcon("resources/images/turret-blue.png").getImage(), 1);
        getAnimation().start();
    }

    @Override
    public void draw(Graphics g)
    {
        super.draw(g);
        //g.setColor(Color.GREEN);
        //g.drawRect(Math.round(getPosition().x) - (int) getRange(), Math.round(getPosition().y) - (int) getRange(), getWidth() + (int) getRange() * 2, getHeight() + (int) getRange() * 2);
    }

    @Override
    public void shoot(Enemy enemy)
    {
        getZone().addBullet(new BulletBlue(getMiddlePoint(), enemy, getZone()));
    }
}
