
package onslaught.util;

import onslaught.model.Goal;
import onslaught.model.bullet.Bullet;
import onslaught.model.enemy.Enemy;
import onslaught.model.turret.Turret;

/**
 *
 * @author ethic
 */
public interface EntityVisitor {
    void visit(Enemy enemy);
    void visit(Bullet bullet);
    void visit(Turret turret);
    void visit(Goal goal);
}
