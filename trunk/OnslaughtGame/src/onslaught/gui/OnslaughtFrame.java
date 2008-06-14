package onslaught.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class OnslaughtFrame extends JFrame implements WindowListener {
	private final int FWIDTH = 800;
	private final int FHEIGHT = 600;
	private Zone zone;
	
	public OnslaughtFrame() {
		super();
		initComponents();
                addWindowListener(this);
		zone.start();
		this.setVisible(true);
	}
	
	public void initComponents(){
		this.setPreferredSize(new Dimension(FWIDTH, FHEIGHT));
		zone = new Zone();
		this.getContentPane().add(zone, BorderLayout.NORTH);
		this.pack();
	}
	
	public static void main(String[] args){
		new OnslaughtFrame();
	}

    public void windowOpened(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowClosing(WindowEvent e) {
        zone.stop();
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowIconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowDeiconified(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowActivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void windowDeactivated(WindowEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
