package onslaught.game;

import java.awt.geom.Point2D;

/**
 *
 * @author EthiC
 */
public class GameProperties {
   public static Point2D startPoint;
   private boolean sendWaveImmediately = false;
   private long timeBetweenWaves = 8000000;
   public static int enemiesPerLevel = 10;
   public static int WANTED_FPS = 60;


   public boolean isSendWaveImmediately() {
      return sendWaveImmediately;
   }

   public long getTimeBetweenWaves() {
      return timeBetweenWaves;
   }

   public void setTimeBetweenWaves(long timeBetweenWaves) {
      this.timeBetweenWaves = timeBetweenWaves;
   }
}
