package onslaught.model.bullet;

import onslaught.model.enemy.Enemy;
import onslaught.model.*;
import onslaught.model.bullet.Bullet;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;

public class BulletBlue extends Bullet
{
    
    private final static int SPEED = 5;

    public BulletBlue(Point2D.Float position, Enemy target) {
        super(position, target);
        setDamage(50);
        getAnimation().addFrame(new ImageIcon("resources/images/bullet-blue.png").getImage(), 1);
	getAnimation().start();
    }

    public int getSpeed() {
        return SPEED;
    }
}
