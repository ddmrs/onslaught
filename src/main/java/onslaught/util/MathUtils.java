package onslaught.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import onslaught.model.Entity;

/**
 *
 * @author EthiC
 */
public class MathUtils {

   private static final double PI = Math.PI;

   public static double getPi() {
      return PI;
   }

   public static Point2D getMiddlePoint(Rectangle2D rectangle) {
      return new Point2D.Double(rectangle.getCenterX(), rectangle.getCenterY());
   }

   public static double getDistance(Entity first, Entity second) {
      return first.getCenter().distance(second.getCenter());
   }
   
   public static double calcRadius(double halfWidth, double halfHeight){
       double wPart = Math.pow(halfWidth, 2);
       double hPart = Math.pow(halfHeight, 2);
       return Math.sqrt(wPart + hPart);
   }
}
