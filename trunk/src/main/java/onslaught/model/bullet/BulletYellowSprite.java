package onslaught.model.bullet;

import java.awt.geom.Point2D;
import onslaught.interfaces.Sprite;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author ethic
 */
public class BulletYellowSprite implements Sprite {

    private Point2D targetPoint;

    public BulletYellowSprite(Point2D targetPoint) {
        this.targetPoint = targetPoint;
    }

    public void draw(float x, float y, float z, double angle) {
        // store the current model matrix
        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(4f);
        /**
         * Afstand / 3, 
         */
        Point2D current = new Point2D.Double(x, y);
        double dist = current.distance(targetPoint);
        double dx = targetPoint.getX() - x;
        double dy = targetPoint.getY() - y;
        double tempX = dx / 3;
        double tempY = dy / 4;


        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glColor3f(1f, 0f, 1f);
            //first
            GL11.glVertex3f(x, y, z);//turret point?
            GL11.glVertex3d(x + tempX, y + tempY, z);//temp  
            // second inter
            GL11.glVertex3d(x + tempX, y + tempY, z);//temp  
            GL11.glVertex3d(x + tempX, y - tempY, z);//temp 
            // third
            GL11.glVertex3d(x + tempX, y - tempY, z);//temp 
            GL11.glVertex3d(x + 2 * tempX, y + 2 * tempY, z);//temp  
            // fourth inter
            GL11.glVertex3d(x + 2 * tempX, y + 2 * tempY, z);//temp  
            GL11.glVertex3d(x + 2 * tempX, y - 2 * tempY, z);//temp 

            GL11.glVertex3d(x + 2 * tempX, y - 2 * tempY, z);//temp 
            GL11.glVertex3d(targetPoint.getX(), targetPoint.getY(), z);//target point?

        }
        GL11.glEnd();
        GL11.glLineWidth(1f);
        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }

    public void setTargetPoint(Point2D targetPoint) {
        this.targetPoint = targetPoint;
    }

    public int getHeight() {
        return 0;
    }

    public int getWidth() {
        return 0;
    }

    public float getHeightPart() {
        return 0;
    }

    public float getWidthPart() {
        return 0;
    }
}
