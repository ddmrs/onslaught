/**
 * TODO:
 */
package onslaught.model.enemy;

import onslaught.model.enemy.Enemy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import onslaught.gui.Zone;

/**
 *
 * @author Dries Demeurisse
 */
public class EnemyPrinter extends Enemy
{

    private final static int WIDTH = 16;
    private final static int HEIGHT = 16;
    private static final Image enemyPrinter = new ImageIcon("resources/images/enemy-printer.png").getImage();
    
    public EnemyPrinter(Point2D.Float position, int level, Zone zone) {
        super(position, level, zone);
        getAnimation().addFrame(enemyPrinter, 1);
	getAnimation().start();
    }
}
