package onslaught.model;

import java.awt.Graphics;
import java.awt.Image;

public class SpriteImpl {

    /**
     * The image to be drawn for this sprite
     */
    private Image image;

    /**
     * Create a new sprite based on an image
     *
     * @param image The image that is this sprite
     */
    public SpriteImpl(Image image) {
        this.image = image;
    }

    /**
     * Get the width of the drawn sprite
     *
     * @return The width in pixels of this sprite
     */
    public int getWidth() {
        return image.getWidth(null);
    }

    /**
     * Get the height of the drawn sprite
     *
     * @return The height in pixels of this sprite
     */
    public int getHeight() {
        return image.getHeight(null);
    }

    /**
     * Draw the sprite onto the graphics context provided
     *
     * @param g The graphics context on which to draw the sprite
     * @param x The x location at which to draw the sprite
     * @param y The y location at which to draw the sprite
     */
    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    /**
     * Gets the image of this sprite.
     *
     * @return
     */
    public Image getImage() {
        return image;
    }
}
