/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package onslaught.model.turret;

import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
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

    public TurretRed(Point2D.Float position) {
        super(position, RATE, RANGE);
        getAnimation().addFrame(new ImageIcon("resources/images/turret-red.png").getImage(), 1);
	getAnimation().start();
    }
    
    @Override
    public Bullet shoot() {
        return new BulletRed(getMiddlePoint(), getTarget());
    }
    
}
