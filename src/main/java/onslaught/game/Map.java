package onslaught.game;

import java.awt.geom.Point2D;
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

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int Z = 0;
    private static final float HALF_ROAD_WIDTH = 25;
    public static final MapHelper[] WAY_POINTS = {new MapHelper(p2d(0, 500), p2d(400, 500), HALF_ROAD_WIDTH), new MapHelper(p2d(400, 500), p2d(400, 200), HALF_ROAD_WIDTH), new MapHelper(p2d(400, 200), p2d(700, 200), HALF_ROAD_WIDTH)};
    private static Goal goal = new Goal(WAY_POINTS[WAY_POINTS.length - 1].getTargetPoint());

    public Map() {
        super(new MapSprite(), 0, 0);
    }

    private static Point2D p2d(int x, int y) {
        return new Point2D.Float(x, y);
    }

    public static Point2D getStartPoint() {
        return WAY_POINTS[0].getStartPoint();
    }

    public static Goal getGoal() {
        return goal;
    }

    @Override
    public void collidedWith(Entity other) {
        // Map can't collide
    }

    @Override
    public void update(long time) {
        // Map is static.
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
            GL11.glColor4f(1, 1, 0, 0.5f);
            // draw a quad rectangle
            for (MapHelper wayPoint : WAY_POINTS) {
                GL11.glBegin(GL11.GL_QUADS);
                {
                    glDraw(wayPoint.getOrigin1());
                    glDraw(wayPoint.getOrigin2());
                    glDraw(wayPoint.getTarget2());
                    glDraw(wayPoint.getTarget1());
                }
                GL11.glEnd();
            }
        }

        private static void glDraw(Point2D point) {
            GL11.glVertex3f((float) point.getX(), (float) point.getY(), Z);
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
