package onslaught.model.enemy;

import onslaught.model.Goal;

/**
 *
 * @author Dries Demeurisse
 */
public class EnemyPrinter extends Enemy {

    private final static String imageUrl = "images/enemy-printer.png";

    public EnemyPrinter(double startX, double startY, int maxHitpoints, Goal goal) {
        super(imageUrl, startX, startY, maxHitpoints, goal);
    } 
       
}
