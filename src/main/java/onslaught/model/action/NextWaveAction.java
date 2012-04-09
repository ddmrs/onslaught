package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class NextWaveAction implements GameAction {

    private GameOperations gameOps;

    public NextWaveAction(GameOperations gameOps) {
        setGameOps(gameOps);
    }

    public void execute() {
        gameOps.sendNextWave();
    }

    public void setGameOps(GameOperations gameOps) {
        this.gameOps = gameOps;
    }
}
