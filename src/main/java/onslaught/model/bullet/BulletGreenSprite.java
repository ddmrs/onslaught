package onslaught.model.bullet;

import java.awt.geom.Point2D;
import onslaught.interfaces.Sprite;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author ethic
 */
public class BulletGreenSprite implements Sprite {

    private Point2D targetPoint;

    public BulletGreenSprite(Point2D targetPoint) {
        this.targetPoint = targetPoint;
    }

    public void draw(float x, float y, float z, double angle) {
        // store the current model matrix
        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(7f);

        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glColor3f(0f, 1f, 0f);
            GL11.glVertex3f(x, y, z);//turret point?

            GL11.glVertex3f((float) targetPoint.getX(), (float) targetPoint.getY(), z);//target point?

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
