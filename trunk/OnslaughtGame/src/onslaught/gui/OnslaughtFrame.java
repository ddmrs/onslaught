package onslaught.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
/**
 * The frame is used to hold all the panels and contains the game loop
 * @author Jelle
 */
public class OnslaughtFrame extends JFrame implements WindowListener {

    private final int FWIDTH = 800;
    private final int FHEIGHT = 600;
    private Menu menu;
    private Zone zone;
    /**
     * Constructor, init components, add listenerers and start loop
     */
    public OnslaughtFrame() {
        super("Java Onslaught");
        initComponents();
    }
    /**
     * set size of frame, make the zone, add a zone and menu
     */
    public void initComponents() {
        zone = new Zone(this);
        getContentPane().add(zone, BorderLayout.NORTH);
        
        addWindowListener(this);
        pack();  
        setResizable(false);
        setVisible(true);        
    }
    /**
     * Runs the game
     * @param args
     */
    public static void main(String[] args) {
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
