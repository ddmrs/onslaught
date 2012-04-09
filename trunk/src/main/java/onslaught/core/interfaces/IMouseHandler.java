package onslaught.core.interfaces;

import onslaught.core.enums.MouseEnum;
import onslaught.handlers.LwjglMouseHandler;

/**
 * Interface for handling mouse events.
 *
 * @author ethic
 */
public interface IMouseHandler {
    
    IMouseHandler DEFAULT = new LwjglMouseHandler();
    
    /**
     *
     * @return The x position of the mouse.
     */
    int getX();

    /**
     *
     * @return The y position of the mouse.
     */
    int getY();

    /**
     *
     * @param button the mouse button that must be checked.
     * @return True if this key is down at the moment of calling.
     */
    boolean isKeydown(MouseEnum mouseButton);

    /**
     *
     * @return true if mouse is grabbed.
     */
    boolean isGrabbed();

    /**
     *
     * @param grabbed Set the mouse to grabbed or not
     */
    void setGrabbed(boolean grabbed);

    /**
     * Set the new position of the mouse
     *
     * @param newX
     * @param newY
     */
    void setNewPosition(int newX, int newY);
    
}
