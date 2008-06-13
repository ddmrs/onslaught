package onslaught.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.*;

public abstract class Bullet extends Sprite {
	private int damage;
	private Enemy target;
	private int speed;
	
	public Bullet(Point2D.Float startPosition) {
		super(new Animation());
		//Point2D.Float position = new Point2D.Float();
		setPosition((Point2D.Float)startPosition.clone());
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
	public void setTarget(Enemy target){
		this.target = target;
	}
	public Enemy getTarget() {
		return target;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	

}
