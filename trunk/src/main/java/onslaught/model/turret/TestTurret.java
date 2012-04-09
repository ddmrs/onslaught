package onslaught.model.turret;

import onslaught.model.Entity;

/**
 *
 * @author EthiC
 */
public class TestTurret extends Entity {

   public TestTurret(double x, double y) {
      super("resources/images/turret-blue.png", x, y);
   }

   @Override
   public void collidedWith(Entity other) {
      System.out.println("Blaat!");
   }

   @Override
   public void update(long time) {
      this.move(time);
   }
   
      /**
    * Request that this entity move itself based on a certain ammount
    * of time passing.
    *
    * @param delta The ammount of time that has passed in milliseconds
    */
   private void move(long delta) {
      // update the location of the entity based on move speeds
      x += (delta * dx) / 1000;
      y += (delta * dy) / 1000;
   }
}
