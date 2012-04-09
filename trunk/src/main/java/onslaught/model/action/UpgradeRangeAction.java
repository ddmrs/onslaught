
package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class UpgradeRangeAction implements GameAction {

    private final GameOperations gameOps;

    public UpgradeRangeAction(GameOperations gameOps) {
        this.gameOps = gameOps;
    }

    public void execute() {
        gameOps.upgradeRangeOfSelectedTurret();
    }
}