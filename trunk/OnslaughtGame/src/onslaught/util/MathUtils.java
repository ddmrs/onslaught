package onslaught.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author EthiC
 */
public class MathUtils {
    public static Point2D getMiddlePoint(Rectangle2D rectangle){
        return new Point2D.Double(rectangle.getCenterX(), rectangle.getCenterY());
    }
}
