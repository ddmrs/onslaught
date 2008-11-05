package onslaught.gui;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author DDEHYW9
 */
public class Menu extends JPanel{
    private final int PHEIGHT = 80;
    private final int PWIDTH = 800;

    public Menu() {
        super();
        initComponents();
        
    }
    
    public void initComponents(){
        //panelsize
        Dimension dimension = new Dimension(PWIDTH, PHEIGHT);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        //this.setBackground(Color.BLACK);
        //components
        JButton mybutt = new JButton("test");
        this.add(mybutt);
        mybutt.setVisible(true);
        this.setVisible(true);
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        System.out.println("CAlled");
//    }
    
}
