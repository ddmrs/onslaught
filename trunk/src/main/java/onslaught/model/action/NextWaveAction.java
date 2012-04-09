package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class NextWaveAction implements GameAction {

    private GameOperations gameOps;

    public NextWaveAction(GameOperations gameOps) {
        this.gameOps = gameOps;
    }

    @Override
    public void execute() {
        gameOps.sendNextWave();
    }
}
