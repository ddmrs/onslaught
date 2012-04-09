
package onslaught.interfaces;

/**
 *
 * @author EthiC
 */
public interface IMovable extends ISelectable{
   /**
    * Indicate if the object is in moving mode or not.
    * @param moving True if this object is in a moving state or false if not.
    */
   void setMoving(boolean moving);

   /**
    * Give the object a new place.
    * @param x
    * @param y
    */
   void replace(int x, int y);

   /**
    * Implement what should happen when the mouse moves here.
    * @param x Current mouse x.
    * @param y Current mouse y.
    */
   void mouseMoves(int x, int y);
}
