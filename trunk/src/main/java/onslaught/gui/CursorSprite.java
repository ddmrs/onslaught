package onslaught.gui;

import onslaught.interfaces.Sprite;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author ethic
 */
public class CursorSprite implements Sprite {

    private int width = 10;
    private int height = 10;
    float widthPart = width / 2.0f;
    float heightPart = height / 2.0f;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getWidthPart() {
        return widthPart;
    }

    public float getHeightPart() {
        return heightPart;
    }

    public void draw(float x, float y, float z, double angle) {
        // store the current model matrix
        GL11.glPushMatrix();
        // translate to the right location and prepare to draw
        GL11.glTranslatef((int) x, (int) y, 0);
        GL11.glColor3f(1, 0, 1);
        //GL11.glColor4f(0, 1, 0, 0f);
        GL11.glDisable(GL11.GL_BLEND);

        // draw a quad rectangle
        GL11.glBegin(GL11.GL_LINE_LOOP);
        {
            GL11.glVertex3f(-widthPart, -heightPart, z);
            GL11.glVertex3f(widthPart, -heightPart, z);
            GL11.glVertex3f(widthPart, heightPart, z);
            GL11.glVertex3f(-widthPart, heightPart, z);
        }
        GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }
}
