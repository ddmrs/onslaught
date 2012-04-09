package onslaught.core.interfaces;

import onslaught.core.enums.KeybEnum;

/**
 *
 * @author EthiC
 */
public interface IKeyboardHandler {

   /**
    * Check if given key is pressed down at time of calling.
    * @param key The key to check
    * @return true if currently down.
    */
   public boolean isKeyDown(KeybEnum key);
   
   /**
    * Check if a key was pressed.
    * @param key The key to check
    * @return true if pressed.
    */
   public boolean isKeyPressed(KeybEnum key);
}
