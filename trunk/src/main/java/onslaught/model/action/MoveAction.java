package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class MoveAction implements GameAction {

    private final GameOperations gameOps;

    public MoveAction(GameOperations gameOps) {
        this.gameOps = gameOps;
    }

    public void execute() {
    }
}
