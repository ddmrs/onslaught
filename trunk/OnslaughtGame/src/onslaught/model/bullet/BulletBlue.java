package onslaught.model.bullet;

import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import onslaught.model.enemy.*;

public class BulletBlue extends Bullet
{
    private final static int SPEED = 2;

    public BulletBlue(Point2D.Float position, Enemy target) {
        super(position, target, SPEED);
        setDamage(50);
        getAnimation().addFrame(new ImageIcon("resources/images/bullet-blue.png").getImage(), 1);
	getAnimation().start();
        calcVelocities();
    }
}
