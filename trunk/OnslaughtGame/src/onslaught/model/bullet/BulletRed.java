/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package onslaught.model.bullet;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import onslaught.model.enemy.Enemy;

/**
 *
 * @author Jelle Victoor
 */
public class BulletRed extends Bullet{
private final static int SPEED = 2;

    public BulletRed(Point2D.Float position, Enemy target) {
        super(position, target);
        setDamage(100);
        getAnimation().addFrame(new ImageIcon("resources/images/bullet-blue.png").getImage(), 1);
	getAnimation().start();
    }
    @Override
    public void draw(Graphics g){
        //The bulletRed is not so fast so the dx and dy should be adjusted every draw
        float xDist = getTarget().getPosition().x - getMiddlePoint().x;
        float yDist = getTarget().getPosition().y - getMiddlePoint().y;
        setVelocityX(xDist);
        setVelocityY(yDist);
        super.draw(g);
    }
    public int getSpeed() {
        return SPEED;
    }
}
