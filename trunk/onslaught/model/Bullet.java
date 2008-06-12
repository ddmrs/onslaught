package onslaught.model;

import java.awt.Point;

public abstract class Bullet {
	protected int damage;
	protected Point position;
	protected int speed;
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getDamage() {
		return damage;
	}
}
