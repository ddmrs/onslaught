package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class Turret implements TurretBehaviour{
	protected Point position;
	
	protected int width;
	protected int height;
	
	private int damage;
	private int range;
	private int rate;
	private int direction; // degrees
	private int shotsFired;
	public Turret(int x, int y)
	{
		position = new Point(x, y);
	}
	public Turret() {
		// Testdata
		position = new Point(10, 10);
		width = 30;
		height = 40;
	}
	public abstract void draw(Graphics g);
	
}
