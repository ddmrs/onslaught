
package onslaught.model.action;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public class UpgradeDamageAction implements GameAction {

    private final GameOperations gameOps;

    public UpgradeDamageAction(GameOperations gameOps) {
        this.gameOps = gameOps;
    }

    public void execute() {
        gameOps.upgradeDamageOfSelectedTurret();
    }
}