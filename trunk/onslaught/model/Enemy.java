package onslaught.model;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Enemy extends Sprite {
	private int health;
	private int hitpoints;
	
	public Enemy(int level) {
		super(new Animation());
		health = 100;
		hitpoints = health * level;
	}
	public void takeHit(Bullet bullet){
		hitpoints -= bullet.getDamage();
		System.out.println("My hitpoints: " + hitpoints);
	}
	public boolean isAlive(){
		return (hitpoints > 0);
	}
}
