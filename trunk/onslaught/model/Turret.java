package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

public abstract class Turret extends Sprite implements TurretBehaviour {

	protected int width;
	protected int height;

	private int damage;
	private int range;
	private int rate;
	private int direction; // degrees
	private int shotsFired;

	public Turret(int x, int y) {
		super(new Animation());
		setPosition(new Point2D.Float(x, y));
	}
	public double getDistance(Enemy target){
		return getPosition().distance(target.getPosition());
	}
	public abstract Bullet shoot(Enemy target);
}
