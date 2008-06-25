package onslaught.model.bullet;

import java.awt.Image;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import onslaught.gui.Zone;
import onslaught.model.enemy.*;

public class BulletBlue extends Bullet
{
    private final static float SPEED = 5f;
    private static final Image bulletBlue = new ImageIcon("resources/images/bullet-blue.png").getImage();
    
    public BulletBlue(Point2D.Float position, Enemy target, Zone zone) {
        super(position, target, SPEED, zone);
        setDamage(50);
        getAnimation().addFrame(bulletBlue, 1);
	getAnimation().start();
        calcVelocities();
    }
}
