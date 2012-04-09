package onslaught.interfaces;

/**
 * A sprite to be displayed on the screen. A sprite does not contain any state information.
 * It contains the image but not where it's located. This allows us to use a single sprite in lots
 * of different places without having to store multiple copies of the image.
 * <br/>Based on http://www.cokeandcode.com/node/9 (author Kevin Glass).
 *
 * @author EthiC
 */
public interface Sprite {

   /**
    * Gets the width of the sprite.
    * @return The width in pixels of this sprite.
    */
   int getWidth();

   /**
    * Gets the height of the sprite.
    * @return The height in pixels of this sprite.
    */
   int getHeight();

   /**
    * Draw the sprite onto the graphics context provided.
    * @param x The x location where to draw the sprite.
    * @param y The y location where to draw the sprite.
    * @param angle The angle to rotate.
    */
   void draw(float x, float y, float z, double angle);
   
   float getWidthPart();
   float getHeightPart();
}
