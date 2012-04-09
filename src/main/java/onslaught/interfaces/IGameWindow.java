package onslaught.interfaces;

import onslaught.core.interfaces.IKeyboardHandler;

/**
 * Interface describing the window in which the game will be displayed. This interface exposes
 * enough to allow game logic interaction, while maintaining an abstraction away from  any
 * physical implementation of windowing (e.g. AWT, LWJGL,.. ). 
 * <br/>Based on http://www.cokeandcode.com/node/9 (author Kevin Glass).
 * 
 * @author EthiC
 */
public interface IGameWindow {

   /**
    * Set the title of the game window
    * @param title The new title for the game window.
    */
   void setWindowTitle(String title);

   /**
    * Set the resolution of this new windows in pixels.
    * @param widthPx How many pixels wide is the screen.
    * @param heightPx How many pixles heigh is the screen.
    */
   void setResolution(int widthPx, int heightPx);

   /**
    * Start the game window rendering the display.
    */
   void startRendering();

   /**
    *
    * @param callback
    */
   void setGameWindowCallback(IGameWindowCallback callback);

   /**
    * Set the RGB background color.
    * @param red Red value
    * @param green Green value
    * @param blue Blue value
    */
   void setBackGroundColour(float red, float green, float blue, float alpha);

   /**
    * Stop the game.
    */
   void stopRendering();
}
