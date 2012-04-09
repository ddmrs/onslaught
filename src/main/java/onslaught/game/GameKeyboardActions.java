package onslaught.game;

import java.util.EnumMap;
import java.util.Map.Entry;
import onslaught.core.enums.KeybEnum;
import onslaught.core.interfaces.IKeyboardHandler;
import onslaught.model.action.*;
import onslaught.model.turret.TurretType;

/**
 *
 * @author ethic
 */
public class GameKeyboardActions {

    private IKeyboardHandler keyHandler;
    private GameOperations gameOps;
    private java.util.Map<KeybEnum, GameAction> keyMapping = new EnumMap<KeybEnum, GameAction>(KeybEnum.class);

    public GameKeyboardActions(IKeyboardHandler keyHandler, GameOperations gameOps) {
        this.keyHandler = keyHandler;
        this.gameOps = gameOps;
        initKeys();
    }

    private void initKeys() {
        keyMapping.put(KeybEnum.SPACE, new NextWaveAction(gameOps));
        keyMapping.put(KeybEnum.DELETE, new DeleteTurretAction(gameOps));
        keyMapping.put(KeybEnum.R, new UpgradeRangeAction(gameOps));
        keyMapping.put(KeybEnum.F1, new NewTurretAction(TurretType.GUN, gameOps));
        keyMapping.put(KeybEnum.F2, new NewTurretAction(TurretType.LASER_GUN, gameOps));
        keyMapping.put(KeybEnum.F3, new NewTurretAction(TurretType.ROCKET_LAUNCHER, gameOps));
        keyMapping.put(KeybEnum.F4, new NewTurretAction(TurretType.TASER, gameOps));
        keyMapping.put(KeybEnum.S, new UpgradeRateAction(gameOps));
        keyMapping.put(KeybEnum.D, new UpgradeDamageAction(gameOps));
        keyMapping.put(KeybEnum.ESCAPE, new ExitGameAction(gameOps));
        keyMapping.put(KeybEnum.M, new MoveAction(gameOps));
        keyMapping.put(KeybEnum.PAUSE, new PauseGameAction(gameOps));
    }

    public void listenAndExecute(long delta) {
        for (Entry<KeybEnum, GameAction> mappingForKey : keyMapping.entrySet()) {
            if (keyHandler.isKeyPressed(mappingForKey.getKey())) {
                mappingForKey.getValue().execute();
            }
        }
    }
}
