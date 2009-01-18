
package onslaught.interfaces;

import java.awt.Shape;
import java.awt.geom.Point2D;
import onslaught.util.CollisionBoxType;

/**
 *
 * @author EthiC
 */
public interface ICollidable {
    Shape getCollisionBox();
    CollisionBoxType getCollisionBoxType();
}
