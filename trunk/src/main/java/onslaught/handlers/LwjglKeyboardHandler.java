package onslaught.handlers;

import java.util.HashMap;
import java.util.Map;
import onslaught.core.enums.KeybEnum;
import onslaught.core.interfaces.IKeyboardHandler;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author EthiC
 */
public class LwjglKeyboardHandler implements IKeyboardHandler {

    private KeybEnum lastKeyDown = null;
    private Map<KeybEnum, Integer> mappings = new HashMap<KeybEnum, Integer>(KeybEnum.values().length);

    public LwjglKeyboardHandler() {
        mappings.put(KeybEnum.DELETE, Keyboard.KEY_DELETE);
        mappings.put(KeybEnum.R, Keyboard.KEY_R);
        mappings.put(KeybEnum.ESCAPE, Keyboard.KEY_ESCAPE);
        mappings.put(KeybEnum.SPACE, Keyboard.KEY_SPACE);
        mappings.put(KeybEnum.F1, Keyboard.KEY_F1);
        mappings.put(KeybEnum.F2, Keyboard.KEY_F2);
        mappings.put(KeybEnum.F3, Keyboard.KEY_F3);
        mappings.put(KeybEnum.F4, Keyboard.KEY_F4);
        mappings.put(KeybEnum.S, Keyboard.KEY_S);
        mappings.put(KeybEnum.M, Keyboard.KEY_M);
        mappings.put(KeybEnum.D, Keyboard.KEY_D);
        mappings.put(KeybEnum.PAUSE, Keyboard.KEY_PAUSE);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isKeyDown(KeybEnum key) {
        //System.out.println("key: " + key + " map " + mappings + " mapkey " + mappings.get(key));
        boolean keyIsDown = Keyboard.isKeyDown(mappings.get(key));
        return keyIsDown;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isKeyPressed(KeybEnum key) {
        if (lastKeyDown == null && isKeyDown(key)) {
            lastKeyDown = key;
            return false;
        }
        if (lastKeyDown != null && key.equals(lastKeyDown) && !isKeyDown(key)) {
            lastKeyDown = null;
            return true;
        }
        return false;
    }
}
