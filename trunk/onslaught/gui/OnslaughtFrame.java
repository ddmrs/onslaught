package onslaught.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class OnslaughtFrame extends JFrame {
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private Zone zone;

	public OnslaughtFrame() {
		super();
		initComponents();
		zone.start();
		this.setVisible(true);
	}

	public void initComponents() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		zone = new Zone();
		this.getContentPane().add(zone, BorderLayout.NORTH);
		this.pack();
	}

	public static void main(String[] args) {
		new OnslaughtFrame();
	}
}
