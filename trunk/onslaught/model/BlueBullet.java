package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

public class BlueBullet extends Bullet {
	private final static int WIDTH = 5;
	private final static int HEIGHT = 5;
	private final static int SPEED = 30;
	private final static int DAMAGE = 100;

		
	public BlueBullet(Enemy target, Point2D.Float startPosition) {
		super(startPosition);
		setTarget(target);
		setSpeed(SPEED);
		setDamage(DAMAGE);
		getAnimation().addFrame(new ImageIcon("resources/images/bullet-blue.png").getImage(), 1);
		getAnimation().start();
	}



//
//	public double getSlope() {
//		// Slope is the direction of the line between 2 points
//		// 1 = 45°
//		return ((position.y - target.getPosition().y) / (position.x - target
//				.getPosition().x));
//	}
}
