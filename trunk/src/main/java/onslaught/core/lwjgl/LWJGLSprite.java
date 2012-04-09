package onslaught.core.lwjgl;

import java.io.IOException;
import onslaught.interfaces.Sprite;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author EthiC
 */
public class LWJGLSprite implements Sprite {

    private LWJGLTexture texture;
    private int width;
    private int height;
    private float widthPart;
    private float heightPart;

    public LWJGLSprite(LWJGLGameWindow window, String ref) {
        try {
            texture = window.getTextureLoader().getTexture(ref);

            width = texture.getImageWidth();
            height = texture.getImageHeight();
            widthPart = width / 2.0f;
            heightPart = height / 2.0f;
        } catch (IOException e) {
            // a tad abrupt, but our purposes if you can't find a
            // sprite's image you might as well give up.
            System.err.println("Unable to load texture: " + ref);
            System.exit(0);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void draw(float x, float y, float z, double angle) {
        // store the current model matrix
        GL11.glPushMatrix();

        // Enable 2D textures because we need these
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // bind to the appropriate texture for this sprite
        texture.bind();

        // translate to the right location and prepare to draw
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef((float) angle, 0.0f, 0.0f, 1.0f);
        GL11.glColor3f(1, 1, 1);

        // draw a quad textured to match the sprite
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(-widthPart, -heightPart, z);

            GL11.glTexCoord2f(0, texture.getHeight());
            GL11.glVertex3f(-widthPart, heightPart, z);

            GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
            GL11.glVertex3f(widthPart, heightPart, z);

            GL11.glTexCoord2f(texture.getWidth(), 0);
            GL11.glVertex3f(widthPart, -heightPart, z);
        }
        GL11.glEnd();

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        // restore the model view matrix to prevent contamination
        GL11.glPopMatrix();
    }

    public float getWidthPart() {
        return widthPart;
    }

    public float getHeightPart() {
        return heightPart;
    }
}
