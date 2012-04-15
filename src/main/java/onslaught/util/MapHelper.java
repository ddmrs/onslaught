/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onslaught.util;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import onslaught.model.Entity;

/**
 *
 * @author EthiC
 */
public class MapHelper {

    private Point2D startPoint;
    private Point2D targetPoint;
    private Point2D origin1;
    private Point2D origin2;
    private Point2D target1;
    private Point2D target2;
    private Rectangle2D rect;
    private double halfWidth;

    public MapHelper(Point2D origin, Point2D target, double halfWidth) {
        this.startPoint = origin;
        this.targetPoint = target;
        this.halfWidth = halfWidth;
        double rad = MathUtils.calcRadAngle(origin.getX(), origin.getY(), target.getX(), target.getY());
        origin1 = calc(origin, rad + MathUtils.HALF_PI, halfWidth);
        origin2 = calc(origin, rad - MathUtils.HALF_PI, halfWidth);
        target1 = calc(target, rad + MathUtils.HALF_PI, halfWidth);
        target2 = calc(target, rad - MathUtils.HALF_PI, halfWidth);
        rect = new Rectangle2D.Double();
        rect.setFrameFromDiagonal(origin1, target2);
    }

    public static Point2D calc(Point2D point, double amount, double distance) {
        double dx = Math.cos(amount) * distance;
        double dy = Math.sin(amount) * distance;
        return new Point2D.Double(point.getX() + dx, point.getY() + dy);
    }

    public Point2D getOrigin1() {
        return origin1;
    }

    public Point2D getOrigin2() {
        return origin2;
    }

    public Point2D getTarget1() {
        return target1;
    }

    public Point2D getTarget2() {
        return target2;
    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public Point2D getTargetPoint() {
        return targetPoint;
    }

    public boolean reachedEnd(Entity entity, MapHelper next) {
        return (Math.abs(entity.getCenter().distance(next.startPoint)) < entity.getBounds().getWidth() / 2);
    }
}
