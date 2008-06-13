package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

public class BlueTurret extends Turret {

	private TurretBehaviour turretBehaviour;
	
	public BlueTurret(int x, int y) {
		super(x, y);

		getAnimation().addFrame(new ImageIcon("resources/images/turret-blue.png").getImage(), 1);
		getAnimation().start();
	}
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public Bullet shoot(Enemy target) {
		Bullet bullet = new BlueBullet(target, getPosition());
		double steps = getDistance(target)/bullet.getSpeed();
		bullet.setDx((float)((target.getPosition().x - getPosition().x)/steps));
		bullet.setDy((float)((target.getPosition().y - getPosition().y)/steps));
		//System.out.println("Steps : " + steps);
		//System.out.println("speed : " + getSpeed());
		//System.out.println("target.getPosition().x - getPosition().x : " + bullet.getDx());
		//System.out.println("target.getPosition().y - getPosition().y : " + bullet.getDy());
		//System.out.println("DX : " + Math.round((target.getPosition().x - getPosition().x)/steps));
		//System.out.println("DY : " + Math.round((target.getPosition().y - getPosition().y)/steps));
		
		return bullet;
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

	public void shoot() {
		// TODO Auto-generated method stub
		
	}

}
