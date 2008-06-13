package onslaught.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import onslaught.model.*;
import java.util.Iterator;

public class Zone extends JPanel implements Runnable {
	private Thread animator;
	private boolean proceed;
	private Graphics graphics;
	private Graphics bufferGraphics;
	private Image image;

	private final int HEIGHT = 520;
	private final int WIDTH = 800;
	private final Color BACKGROUNDCOLOR = new Color(247, 207, 172);
	private final int SLEEPTIME = 10; // milliseconds

	private List<Sprite> sprites;
	
	private int gameLevel;

	public Zone() {
		sprites = new ArrayList<Sprite>();
	}
	
	public void initComponents() {
		gameLevel = 1;
		Dimension dimension = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(dimension);
		this.setSize(dimension);
		graphics = this.getGraphics();
		image = this.createImage(this.getWidth(), this.getHeight());
		bufferGraphics = image.getGraphics();

		proceed = true;
		BlueTurret bt = new BlueTurret(35,26);
		Enemy e = new PrinterEnemy(10,220, gameLevel);
		e.setSpeed(1);
		e.setDx(1);
		e.setDy(0);
		sprites.add(bt);
		sprites.add(e);

		sprites.add(bt.shoot(e));
	}	

	public void start() {
		if (animator == null) {
			initComponents();
			animator = new Thread(this);
			animator.start();
		}
	}

	public void stop() {
		if (animator != null) {
			proceed = false;
			animator = null;
		}
	}

	public void run() {
		while (proceed) {
			update();
			buffer();
			paint();
			sleep(SLEEPTIME);
		}
	}
	
	public void buffer() {
		bufferGraphics.setColor(BACKGROUNDCOLOR);
		bufferGraphics.fillRect(0, 0, this.getWidth(), this.getWidth());
		// Draw game objects
		for (Sprite sprite : sprites) {
			sprite.draw(bufferGraphics);
		}
	}

	public void sleep(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void paint() {
		graphics.drawImage(image, 0, 0, this);
	}

	public void update() {
		checkSpriteCollision();
		for(Sprite s: sprites){
			s.update();
		}
		
	}
	
	public void checkSpriteCollision(){
		Iterator<Sprite> it = sprites.iterator();
		
		while(it.hasNext()){
			Sprite sprite = it.next();
			if(sprite instanceof Bullet) {
				Bullet bullet = (Bullet) sprite;
				if(collides(bullet, bullet.getTarget())){
					System.out.println("BOEM!!!!!");
					bullet.getTarget().takeHit(bullet);
					it.remove();
				}
			}else
			{
				if(sprite instanceof Enemy){
					Enemy enemy= (Enemy)sprite; 
					if(!enemy.isAlive())
						it.remove();
				}
			}
		}
	}
	
	public boolean collides(Sprite sprite, Sprite nextSprite){
		return sprite.getColissionBox().intersects(nextSprite.getColissionBox());
	}
}
