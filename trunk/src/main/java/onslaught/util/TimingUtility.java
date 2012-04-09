
package onslaught.util;

/**
 *
 * @author ethic
 */
public class TimingUtility {
   /**
    * The current time.
    * @return in microsecs.
    */
   public static long time() {
       //1s = 1.000.000.000 ns!
      return System.nanoTime() / 1000;
   }
   
   /**
    * How much is one second ?
    * @return the amount that is needed to pass 1 second.
    */
   public static long getOneSecond(){
       return 1000000L;
   }
   
   public static double calcTimeFactor(long passedTime){
       return passedTime / (double)getOneSecond();
   }
}
