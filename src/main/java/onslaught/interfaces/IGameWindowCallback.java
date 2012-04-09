
package onslaught.interfaces;

/**
 * An interface describing any class that wishes to be notified as the game window renders.
 * <br/>Based on http://www.cokeandcode.com/node/9 (author Kevin Glass).
 *
 * @author EthiC
 */
public interface IGameWindowCallback {
   /**
    * Notification that the game should initialise any resources it might need to use.
    * This includes loading sprites.
    */
   void initialise();

   /**
    * Notification that the display is being rendered. The implementor should render the scene and
    * update any game logic here.
    */
   void frameRendering();

   /**
    * Notification that the game window has been closed. Any exit game logic should be called here.
    */
   void windowClosed();
}
