package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author EthiC
 */
public class PauseGameAction implements GameAction {

    private GameOperations gameOperations;
    private boolean pause = false;

    public PauseGameAction(GameOperations gameOperations) {
        this.gameOperations = gameOperations;
    }

    @Override
    public void execute() {
        pause = !pause;
        gameOperations.pauseGame(pause);
    }
}
