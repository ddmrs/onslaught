package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;

public class BlueTurret extends Turret implements TurretBehaviour {
	private final int WIDTH = 30;
	private final int HEIGHT = 30;
	
	public BlueTurret(int x, int y) {
		super(x, y);
		this.width = WIDTH;
		this.height = HEIGHT;
	}

	public void shoot(Enemy ememy) {
		
	}

	public void turn(int degrees) {
		// TODO Auto-generated method stub

	}

	public void upgradeDamage() {
		// TODO Auto-generated method stub

	}

	public void upgradeRange() {
		// TODO Auto-generated method stub

	}

	public void upgradeRate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillOval(position.x,position.y, width,height);
	}

	public void shoot() {
		Bullet bullet = new BlueBullet();
		
	}

}
