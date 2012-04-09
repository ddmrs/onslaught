package onslaught.game;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author EthiC
 */
public class Statistics {

   private Integer level = 0;
   private String playerName;
   private int enemiesSent = 0;
   private int enemiesKilled = 0;
   private int score = 0;
   private int money = 0;
   private long startTime;
   //Key = level, value = count of enemies left of that level.
   private Map<Integer, Integer> enemiesLeftPerLevel = new HashMap<Integer, Integer>();
   //Key = enemy id, value = level of that enemy.
   private Map<Integer, Integer> enemyHasLevel = new HashMap<Integer, Integer>();

   public void setStartTime(long startTime) {
      this.startTime = startTime;
   }

   public void incrementLevel() {
      level = level + 1;
   }
}
