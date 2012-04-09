package onslaught.game;

import java.awt.geom.Point2D;
import onslaught.model.Goal;

/**
 *
 * @author EthiC
 */
public class Map {

    private final static int Y = 250;
    private static Goal goal = new Goal(700.0, Y);

    public static Point2D getStartPoint() {
        return new Point2D.Double(0.0, Y);
    }

    public static Goal getGoal() {
        return goal;
    }
}
