package onslaught.model.turret;

import onslaught.interfaces.Sprite;
import onslaught.interfaces.SpriteDecorator;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author ethic
 */
public class RangeTurretSD1 extends SpriteDecorator {

    private Sprite sprite;
    private Turret turret;

    public RangeTurretSD1(Sprite sprite, Turret turret) {
        this.sprite = sprite;
        this.turret = turret;
    }

    @Override
    public void draw(float x, float y, float z, double angle) {
        // store the current model matrix
        GL11.glPushMatrix();
        // translate to the right location and prepare to draw
        GL11.glTranslatef((int) x, (int) y, 0);
        GL11.glColor3f(1f, 0.1f, 0.1f);
        //GL11.glLineWidth(0.1f);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        {
            for (float anglez = 0; anglez < 360; anglez += 1) {
                GL11.glVertex3d(Math.sin(anglez) * turret.getRadius(), Math.cos(anglez) * turret.getRadius(), z);
            }
        }
        GL11.glEnd();

        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();

        sprite.draw(x, y, z, angle);
    }

    @Override
    public int getWidth() {
        return sprite.getWidth();
    }

    @Override
    public int getHeight() {
        return sprite.getHeight();
    }

    @Override
    public float getWidthPart() {
        return sprite.getWidthPart();
    }

    @Override
    public float getHeightPart() {
        return sprite.getHeightPart();
    }
}
