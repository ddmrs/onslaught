package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;

public class BlueBullet extends Bullet {
	private Enemy target;

	private final static int WIDTH = 5;
	private final static int HEIGHT = 5;
	
	
	
	public BlueBullet(Enemy target) {
		this.target = target;
	}
	public void draw(Graphics graphics){
		graphics.setColor(Color.BLUE);
		graphics.fillRect(position.x, position.y, WIDTH, HEIGHT);
	}

	public void move(int x, int y){
		
	}
	public double getSlope(){
		// Slope is the direction of the line between 2 points
		// 1 = 45°
		return ((position.y - target.getPosition().y)/(position.x - target.getPosition().x));
	}
}
