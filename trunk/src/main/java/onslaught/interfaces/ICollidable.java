
package onslaught.interfaces;

import java.awt.Shape;
import onslaught.util.CollisionBoxType;

/**
 *
 * @author EthiC
 */
public interface ICollidable {
    Shape getCollisionBox();
    CollisionBoxType getCollisionBoxType();
}
