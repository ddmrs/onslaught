package onslaught.model.turret;

import onslaught.interfaces.Sprite;
import onslaught.interfaces.SpriteDecorator;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author ethic
 */
public class MovingTurretSD extends SpriteDecorator {

    private Sprite sprite;

    public MovingTurretSD(Sprite sprite) {
        this.sprite = sprite;
    }

    public void draw(float x, float y, float z, double angle) {
        sprite.draw(x, y,z, angle);

        // store the current model matrix
        GL11.glPushMatrix();
        // translate to the right location and prepare to draw
        GL11.glTranslatef((int) x, (int) y, 0);
        //GL11.glColor3f(1, 0, 1);
        GL11.glColor4f(0, 1, 0, 0.5f);

        float widthPart = sprite.getWidthPart();
        float heightPart = sprite.getHeightPart();

        // draw a quad rectangle
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2f(-widthPart, -heightPart);
            GL11.glVertex2f(widthPart, -heightPart);
            GL11.glVertex2f(widthPart, heightPart);
            GL11.glVertex2f(-widthPart, heightPart);
        }
        GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public float getWidthPart() {
        return sprite.getWidthPart();
    }

    public float getHeightPart() {
        return sprite.getHeightPart();
    }
}
