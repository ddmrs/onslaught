package onslaught.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import onslaught.model.turret.Turret;

/**
 *
 * @author DDEHYW9
 */
public class PlaceTurretButton extends JButton{
    private List<Turret> availableTurrets;
    private int selectedTurretIndex = 0;
    
    public PlaceTurretButton() {
        super("Red Turret");        
    }
    
    public PlaceTurretButton(List<Turret> availableTurrets){
        this.availableTurrets = availableTurrets;
    }
    
    private class myActionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
}
