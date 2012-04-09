package onslaught.interfaces;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author EthiC
 */
public interface ISelectable {

    /**
     * Select an item.
     */
    void select();

    /**
     * Deselect this item.
     */
    void deselect();

    /**
     * @return Zone where this object can be clicked on.
     */
    Rectangle2D getClickZone();
}
