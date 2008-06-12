package onslaught.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import onslaught.model.BlueTurret;
import onslaught.model.Turret;

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
	
	private List<Turret> turrets;

	public Zone() {
		turrets = new ArrayList<Turret>();
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

	public void buffer() {
		bufferGraphics.setColor(BACKGROUNDCOLOR);
		bufferGraphics.fillRect(0, 0, this.getWidth(), this.getWidth());
		// Draw game objects
		for(Turret t : turrets){
			t.draw(bufferGraphics);
		}
	}

	public void sleep(long miliseconds){
		try{
			Thread.sleep(miliseconds);
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}

	public void initComponents() {
		Dimension dimension = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(dimension);
		this.setSize(dimension);
		graphics = this.getGraphics();
		image = this.createImage(this.getWidth(), this.getHeight());
		bufferGraphics = image.getGraphics();
		
		proceed = true;
		
		turrets.add(new BlueTurret(10,10));
		turrets.add(new BlueTurret(90,90));
	}

	public void run() {
		while(proceed){
			update();
			buffer();
			paint();
			sleep(SLEEPTIME);
		}
	}

	public void paint() {
		graphics.drawImage(image, 0, 0 ,this);
	}

	public void update() {
		for(Turret t: turrets){
			t.moveTo(1, 0);
		}
	}
}
