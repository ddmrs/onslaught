package onslaught.game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import onslaught.interfaces.Sprite;
import onslaught.model.Entity;
import onslaught.model.Goal;
import onslaught.util.MapHelper;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author EthiC
 */
public class Map extends Entity {

    private static Map instance;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final float HALF_ROAD_WIDTH = 25;
    private List<Point2D> points = new ArrayList<Point2D>();
    private List<MapHelper> wayPoints = new ArrayList<MapHelper>();
    private Goal goal;

    private Map() {
        super(new MapSprite(), 0, 0);
        z = 0;
        addPoints(p2d(0, 500), p2d(400, 500), p2d(400, 100), p2d(100, 100), p2d(150, 300), p2d(700, 300));
        if (points.size() < 2) {
            throw new IllegalArgumentException("Need at least 2 points");
        } else {
            for (int i = 0; i < points.size()-1;) {
                wayPoints.add(new MapHelper(points.get(i), points.get(++i), HALF_ROAD_WIDTH));
            }
        }
        goal = new Goal(wayPoints.get(wayPoints.size() - 1).getTargetPoint());
    }

    public static Map getCurrent() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    private void addPoints(Point2D... _points) {
        points.addAll(Arrays.asList(_points));
    }

    private static Point2D p2d(int x, int y) {
        return new Point2D.Float(x, y);
    }

    public Point2D getStartPoint() {
        return wayPoints.get(0).getStartPoint();
    }

    public Goal getGoal() {
        return goal;
    }

    public List<MapHelper> getWayPoints() {
        return wayPoints;
    }

    @Override
    public void collidedWith(Entity other) {
        // Map can't collide
    }

    @Override
    public void update(long time) {
        // Map is static.
    }

    @Override
    public void draw() {
        GL11.glColor4f(1, 1, 0, 0.5f);
        // draw a quad rectangle
        for (MapHelper wayPoint : wayPoints) {
            GL11.glBegin(GL11.GL_QUADS);
            {
                glDraw(wayPoint.getOrigin1(), z);
                glDraw(wayPoint.getOrigin2(), z);
                glDraw(wayPoint.getTarget2(), z);
                glDraw(wayPoint.getTarget1(), z);
            }
            GL11.glEnd();
            GL11.glBegin(GL11.GL_TRIANGLE_FAN);
            {
                for (int i = 0; i < 360; i += 2) {
                    GL11.glVertex3f((float) (wayPoint.getStartPoint().getX() + Math.sin(i) * HALF_ROAD_WIDTH), (float) (wayPoint.getStartPoint().getY() + Math.cos(i) * HALF_ROAD_WIDTH), (float) z);
                }
            }
            GL11.glEnd();
        }


    }

    private static void glDraw(Point2D point, double _z) {
        GL11.glVertex3f((float) point.getX(), (float) point.getY(), (float) _z);
    }

    private static class MapSprite implements Sprite {

        @Override
        public int getWidth() {
            return WIDTH;
        }

        @Override
        public int getHeight() {
            return HEIGHT;
        }

        @Override
        public void draw(float x, float y, float z, double angle) {
        }

        @Override
        public float getWidthPart() {
            return getWidth() / 2;
        }

        @Override
        public float getHeightPart() {
            return 0;
        }
    }
}
