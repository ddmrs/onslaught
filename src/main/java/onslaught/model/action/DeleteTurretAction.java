package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class DeleteTurretAction implements GameAction {

    private final GameOperations gameOps;

    public DeleteTurretAction(GameOperations gameOps) {
        this.gameOps = gameOps;
    }

    @Override
    public void execute() {
        gameOps.deleteTurret();
    }
}
