package onslaught.model.action;

import onslaught.game.GameOperations;
import onslaught.model.turret.TurretType;

/**
 *
 * @author ethic
 */
public class NewTurretAction implements GameAction {

    private final TurretType turretType;
    private final GameOperations gameOps;

    public NewTurretAction(TurretType turretType, GameOperations gameOps) {
        this.turretType = turretType;
        this.gameOps = gameOps;
    }

    @Override
    public void execute() {
        gameOps.addTurret(turretType);
    }
}
