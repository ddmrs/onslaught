/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package onslaught.model.turret;

import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.ImageIcon;
import onslaught.gui.Zone;
import onslaught.model.bullet.Bullet;
import onslaught.model.bullet.BulletRed;
import onslaught.model.enemy.Enemy;

/**
 *
 * @author Jelle Victoor
 */
public class TurretRed extends Turret{    
    //TODO: All rates upgradable fields are static now
    // these fields have to be a base that can be multiplied by a factor of upgrde
    
    private static final int RATE = 2; //This turret shoots 2 times per second
    private static final int RANGE = 200;//TODO: currently pixel dependent

    public TurretRed(Point2D.Float position, Zone zone, List<Enemy> enemies) {
        super(position, RATE, RANGE, zone, enemies);
        getAnimation().addFrame(new ImageIcon("resources/images/turret-red.png").getImage(), 1);
	getAnimation().start();
    }
    
    @Override
    public void shoot(Enemy enemy) {
        getZone().addBullet(new BulletRed(getMiddlePoint(), enemy, getZone()));
    }
    
}
