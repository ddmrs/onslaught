package onslaught.util;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import onslaught.interfaces.ICollidable;
import onslaught.model.Entity;
import onslaught.model.turret.Turret;
import static onslaught.util.CollisionBoxType.RECTANGLE;
import static onslaught.util.CollisionBoxType.CIRCLE;

/**
 *
 * @author EthiC
 */
public class CollisionUtils {

    public static boolean inRange(Turret turret, Entity entity) {
        double distance = turret.getMiddle().distance(entity.getMiddle());
        boolean inRange = (distance <= (turret.getRadius() + entity.getRadius()));
        return inRange;
    }

    public static boolean isCollided(ICollidable sprite1, ICollidable sprite2) {
        boolean collided = false;
        CollisionBoxType first = sprite1.getCollisionBoxType();
        CollisionBoxType second = sprite2.getCollisionBoxType();
        Rectangle2D rectCollBox1 = sprite1.getCollisionBox().getBounds2D();
        Rectangle2D rectCollBox2 = sprite2.getCollisionBox().getBounds2D();
        if (first == RECTANGLE && second == RECTANGLE) {
            collided = isCollidedRectToRect(rectCollBox1, rectCollBox2);
        } else if (first == RECTANGLE && second == CIRCLE) {
            collided = isCollidedRectToCirc(rectCollBox1, rectCollBox2);
        } else if (first == CIRCLE && second == RECTANGLE) {
            collided = isCollidedRectToCirc(rectCollBox2, rectCollBox1);
        } else if (first == CIRCLE && second == CIRCLE) {
            collided = isCollidedCircToCirc(rectCollBox1, rectCollBox2);
        }
        return collided;
    }

    private static boolean isCollidedRectToRect(Rectangle2D rect1, Rectangle2D rect2) {
        return rect1.intersects(rect2);
    }

    private static boolean isCollidedRectToCirc(Rectangle2D rect, Rectangle2D circle) {
        double radius = circle.getWidth() / 2;
        Point2D middle = MathUtils.getMiddlePoint(circle);
        // System.out.println("Cirkel: " + circle);
        // System.out.println("Rechthoek: " + rect);

        Point2D upperLeft = new Point2D.Double(rect.getMinX(), rect.getMinY());
        Point2D upperRight = new Point2D.Double(rect.getMaxX(), rect.getMinY());
        Point2D downLeft = new Point2D.Double(rect.getMinX(), rect.getMaxY());
        Point2D downRight = new Point2D.Double(rect.getMaxX(), rect.getMaxY());

        boolean collided = true;
        if (!isCollidedPointToLine(upperLeft, upperRight, middle, radius)) {
            if (!isCollidedPointToLine(upperRight, downRight, middle, radius)) {
                if (!isCollidedPointToLine(upperLeft, downLeft, middle, radius)) {
                    if (!isCollidedPointToLine(downLeft, downRight, middle, radius)) {
                        collided = false;
                    }
                }
            }
        }
        return collided;
    }

    private static boolean isCollidedPointToLine(Point2D point1, Point2D point2, Point2D middle, double radius) {
        Line2D line = new Line2D.Double(point1, point2);
        double distance = line.ptLineDist(middle);
        return distance < radius;
    }

    private static boolean isCollidedCircToCirc(Rectangle2D circle1, Rectangle2D circle2) {
        Point2D middle1 = MathUtils.getMiddlePoint(circle1);
        Point2D middle2 = MathUtils.getMiddlePoint(circle2);
        double distance = middle1.distance(middle2);
        double radius1 = circle1.getWidth() / 2;
        double radius2 = circle2.getWidth() / 2;

        return (distance - radius2) < radius1;
    }
}
