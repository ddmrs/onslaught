package onslaught.model.turret;

import onslaught.game.GameOperations;

/**
 *
 * @author ethic
 */
public enum TurretType {

    GUN() {

        public Turret buildTurret(double startX, double startY, GameOperations gameOps) {
            return new TurretBlue(startX, startY, gameOps);
        }
    }, ROCKET_LAUNCHER() {

        public Turret buildTurret(double startX, double startY, GameOperations gameOps) {
            return new TurretRed(startX, startY, gameOps);
        }
    }, LASER_GUN() {

        public Turret buildTurret(double startX, double startY, GameOperations gameOps) {
            return new TurretGreen(startX, startY, gameOps);
        }
    }, TASER() {

        public Turret buildTurret(double startX, double startY, GameOperations gameOps) {
            return new TurretYellow(startX, startY, gameOps);
        }
    };

    public abstract Turret buildTurret(double startX, double startY, GameOperations gameOps);
}
