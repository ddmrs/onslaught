package onslaught.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import onslaught.model.Entity;

/**
 *
 * @author EthiC
 */
public class MathUtils {

    public static final double PI = Math.PI;//180°
    public static final double HALF_PI = PI / 2;//90°
    public static final double TWO_PI = PI * 2;//360°

    public static Point2D getMiddlePoint(Rectangle2D rectangle) {
        return new Point2D.Double(rectangle.getCenterX(), rectangle.getCenterY());
    }

    public static double getDistance(Entity first, Entity second) {
        return first.getCenter().distance(second.getCenter());
    }

    public static double calcRadius(double halfWidth, double halfHeight) {
        double wPart = Math.pow(halfWidth, 2);
        double hPart = Math.pow(halfHeight, 2);
        return Math.sqrt(wPart + hPart);
    }

    public static double calcRadAngle(Point2D origin, Point2D target) {
        return calcRadAngle(origin.getX(), origin.getY(), target.getX(), target.getY());
    }

    public static double calcRadAngle(double originX, double originY, double targetX, double targetY) {
        double xDist = targetX - originX;
        double yDist = targetY - originY;
        return Math.atan2(yDist, xDist);
    }
}
