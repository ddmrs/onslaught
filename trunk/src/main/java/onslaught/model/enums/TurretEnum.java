package onslaught.model.enums;

import onslaught.interfaces.Sprite;
import onslaught.model.turret.MovingTurretSD;
import onslaught.model.turret.RangeTurretSD1;
import onslaught.model.turret.Turret;

/**
 *
 * @author EthiC
 */
public enum TurretEnum{
   OPERATING     , 
   SELECTED(){
       @Override
       public Sprite getDecoratedSprite(Turret turret){
           return new RangeTurretSD1(super.getDecoratedSprite(turret), turret);
       }
   }, 
   MOVABLE(){
       @Override
       public Sprite getDecoratedSprite(Turret turret){
           return new MovingTurretSD(SELECTED.getDecoratedSprite(turret), turret);
       }
   };
   
   public Sprite getDecoratedSprite(Turret turret){
       return turret.getTurretSprite();
   }
}
