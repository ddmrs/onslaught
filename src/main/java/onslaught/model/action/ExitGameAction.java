package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class ExitGameAction implements GameAction {

    private final GameOperations gameOps;

    public ExitGameAction(GameOperations gameOps) {
        this.gameOps = gameOps;
    }

    @Override
    public void execute() {
        gameOps.exitGame();
    }
}
