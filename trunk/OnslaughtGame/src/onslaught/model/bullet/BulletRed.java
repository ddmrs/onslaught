/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package onslaught.model.bullet;

import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import onslaught.gui.Zone;
import onslaught.model.enemy.Enemy;

/**
 *
 * @author Jelle Victoor
 */
public class BulletRed extends Bullet{
    private final static float SPEED = 0.3f;

    public BulletRed(Point2D.Float position, Enemy target, Zone zone) {
        super(position, target, SPEED, zone);
        setDamage(100);
        getAnimation().addFrame(new ImageIcon("resources/images/bullet-blue.png").getImage(), 1);
	getAnimation().start();
    }
    
    @Override
    public void update(long elapsedTime) {
        //The bulletRed is not so fast so the dx and dy should be adjusted every update
        calcVelocities();
        super.update(elapsedTime);
    }
}
