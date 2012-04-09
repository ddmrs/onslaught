package onslaught.handlers;

import onslaught.core.enums.MouseEnum;
import onslaught.core.interfaces.IMouseHandler;
import onslaught.game.OnslaughtGame;
import org.lwjgl.input.Mouse;

/**
 *
 * @author ethic
 */
public class LwjglMouseHandler implements IMouseHandler {

    /**
     * {@inheritDoc}
     */
    public boolean isKeydown(MouseEnum mouseButton) {
        int keyIndex = -1;
        switch (mouseButton) {
            case LEFT_BUTTON:
                keyIndex = 0;
                break;
            case RIGHT_BUTTON:
                keyIndex = 1;
                break;
        }
        return Mouse.isButtonDown(keyIndex);
    }

    /**
     * {@inheritDoc}
     */
    public int getY() {
        return OnslaughtGame.SCREEN_HEIGHT - Mouse.getY();
    }

    /**
     * {@inheritDoc}
     */
    public int getX() {
        return Mouse.getX();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isGrabbed() {
        return Mouse.isGrabbed();
    }

    /**
     * {@inheritDoc}
     */
    public void setGrabbed(boolean grabbed) {
        Mouse.setGrabbed(grabbed);
    }

    /**
     * {@inheritDoc}
     */
    public void setNewPosition(int newX, int newY) {
        Mouse.setCursorPosition(newX, OnslaughtGame.SCREEN_HEIGHT - newY);
    }

    @Override
    public void setX(int x) {
        Mouse.setCursorPosition(x, Mouse.getY());
    }

    @Override
    public void setY(int y) {
        Mouse.setCursorPosition(Mouse.getX(), OnslaughtGame.SCREEN_HEIGHT - y);
    }
}
