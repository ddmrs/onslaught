
package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class UpgradeRateAction implements GameAction {

    private final GameOperations gameOps;

    public UpgradeRateAction(GameOperations gameOps) {
        this.gameOps = gameOps;
    }

    public void execute() {
        gameOps.upgradeRateOfSelectedTurret();
    }
}