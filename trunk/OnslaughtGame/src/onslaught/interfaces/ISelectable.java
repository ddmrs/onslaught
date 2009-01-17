package onslaught.interfaces;

/**
 *
 * @author EthiC
 */
public interface ISelectable {

    /**
     * Select an item.
     * @return true if it is selected, false otherwise
     */
    boolean select();

    /**
     * Deselect this item.
     */
    void deselect();
}
