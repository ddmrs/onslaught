package onslaught.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import onslaught.core.interfaces.IKeyboardHandler;
import onslaught.interfaces.IGameWindowCallback;
import onslaught.model.turret.Turret;
import onslaught.model.enemy.Enemy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import onslaught.interfaces.IGameWindow;
import onslaught.interfaces.IMovable;
import onslaught.interfaces.ISelectable;
import onslaught.model.*;

public class Zone extends JPanel implements Runnable, IGameWindow {
   //parent
   private OnslaughtFrame parentFrame;
   private Menu menu;
   //thread
   private Thread animator;
   //graphics
   private Graphics bufferGraphics;
   private Image image;
   //finals
   private static final int PHEIGHT = 520;
   private static final int PWIDTH = 800;
   private static final Color BACKGROUNDCOLOR = new Color(247, 207, 172);
   private static final int FPS = 40; // milliseconds
   private static final int WAVELENGTH = 10;
   private static final int MAXLIVES = 10;
   /* Number of frames with a delay of 0 ms before the animation thread yields
   to other running threads. */
   private final int NO_DELAYS_PER_YIELD = 16;
   /* no. of frames that can be skipped in any one animation loop
   i.e the games state is updated but not rendered*/
   private final int MAX_FRAME_SKIPS = 5;   // was 2;
   private final Point2D startPosition = new Point2D.Double(1, 300);
   // Alle objecten
   private List<Entity> entities = new ArrayList<Entity>();
   // Alle klikbare objecten
   private List<ISelectable> selectables = new ArrayList<ISelectable>();
   // temp objecten die geadd moeten worden.
   private List<Entity> newEntities = new ArrayList<Entity>();
   // Alle enemies op het scherm.
   private List<Enemy> enemies = new ArrayList<Enemy>();
   //loopinfo
   private boolean proceed = true;
   //gamevar's
   private int level = 0;
   private int wastedLives = 0;// Number of wasted lives (nmy reaches end of screen)
   private long timeBetweenEnemies;
   private long waveTime;
   private long periodBetweenFrames;
   private int enemyKillCount;// Counts the number of enemies on screen
   //timevar's
   private long currentTime,  oldTime,  timeDifference,  sleepTime;
   private long overSleepTime = 0L;
   private int noDelays = 0;
   private long excess = 0L;

   public Zone(OnslaughtFrame frame) {
      parentFrame = frame;
      initComponents();

      testingPurposes();
      sendNextWave();

      periodBetweenFrames = (long) 1000.0 / FPS;
      periodBetweenFrames *= 1000000L;// ms --> nanosecs

   }

   public void initComponents() {
      //turn off double buffering, we do it ourselves
      setDoubleBuffered(false);
      //set the background color
      setBackground(BACKGROUNDCOLOR);
      //panelsize
      setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

      setFocusable(true);
      requestFocus();
      this.addMouseListener(new TurretMouseListener());
      this.addKeyListener(this.keybListener);
   }

   // wait for the JPanel to be added to the JFrame before starting
   @Override
   public void addNotify() {
      // creates the peer
      super.addNotify();
      // start the thread
      start();
   }

   public void start() {
      if (animator == null) {
         animator = new Thread(this);
         animator.start();
      }
   }

   public void stop() {
      if (animator != null) {
         proceed = false;
         animator = null;
      }
   }

   // Gaming loop -----------------------------------------------------------//
   public void run() {
      while (proceed) {
         //update components on screen
         update();
         //write graphics to buffer
         buffer();
         //paint the graphics
         paint();
         //sleep a while
         sleep();
      }
   }

   private void update() {
      // Add all new entities to the right collections
      for (Entity newEntity : newEntities) {
         if (newEntity instanceof ISelectable) {
            selectables.add((ISelectable) newEntity);
         } else if (newEntity instanceof Enemy) {
            enemies.add((Enemy) newEntity);
         }
      }
      // And add them to allEntities list
      entities.addAll(newEntities);
      newEntities.clear();

      //Update all sprites and delete the killed one's
      Iterator<Entity> it = entities.iterator();
      while (it.hasNext()) {
         Entity entity = it.next();
         //only update 'alive' sprites
         if (entity.isAlive()) {
            entity.update(currentTime);
         } else {
            // Killed sprites have to be removed
            it.remove();
            selectables.remove(entity);
            enemies.remove(entity);
         }
      }
   }

   private void buffer() {
      if (image == null) {
         image = createImage(PWIDTH, PHEIGHT);
         if (image == null) {
            System.out.println("dbImage is null");
            return;
         } else {
            bufferGraphics = image.getGraphics();
         }
      }
      //clear background
      bufferGraphics.setColor(BACKGROUNDCOLOR);
      bufferGraphics.fillRect(0, 0, PWIDTH, PHEIGHT);

      // Draw game objects
      for (Entity entity : entities) {
         entity.draw(bufferGraphics);
      }
   }

   private void paint() {
      Graphics g;
      try {
         g = this.getGraphics();
         if ((g != null) && (image != null)) {
            g.drawImage(image, 0, 0, null);
         // Sync the display on some systems.
         // (on Linux, this fixes event queue problems)
         }
         Toolkit.getDefaultToolkit().sync();

         g.dispose();
      } catch (Exception e) {
         System.out.println("Graphics context error: " + e);
      }
   }

   private void sleep() {
      currentTime = System.nanoTime();
      timeDifference = currentTime - oldTime;
      sleepTime = (periodBetweenFrames - timeDifference) - overSleepTime;

      if (sleepTime > 0) {   // some time left in this cycle

         try {
            Thread.sleep(sleepTime / 1000000L);  // nano -> ms

            noDelays = 0;
         } catch (InterruptedException ex) {//should never occur
            }
         overSleepTime = (System.nanoTime() - currentTime) - sleepTime;
      } else {    // sleepTime <= 0; the frame took longer than the period

         excess -= sleepTime;  // store excess time value

         overSleepTime = 0L;

         if (++noDelays >= NO_DELAYS_PER_YIELD) {
            Thread.yield();   // give another thread a chance to run

            noDelays = 0;
         }
      }

      oldTime = System.nanoTime();

      /* If frame animation is taking too long, update the game state
      without rendering it, to get the updates/sec nearer to
      the required FPS. */
      int skips = 0;
      while ((excess > periodBetweenFrames) && (skips < MAX_FRAME_SKIPS)) {
         excess -= periodBetweenFrames;
         update();    // update state but don't render

         skips++;
      }
   }

   // Game checks -----------------------------------------------------------//
   public void enemyKilled() {
      enemyKillCount++;
      if (enemyKillCount >= 10) {
         //10 enemies killed, so send in next wave!
         sendNextWave();
         //reset counter
         enemyKillCount = 0;
      }
   }
   // Game functions --------------------------------------------------------//

   public void sendNextWave() {
      for (int i = 0; i < WAVELENGTH; i++) {
         //create a new enemy, should be random
         //Enemy enemy = new EnemyPrinter(startPosition.getX() - i * 20, startPosition.getY(), this);
         //add this enemy to sprites
         //this.addEntity(enemy);

      }
      //increase level per wave
      level++;
   }

   /**
    * Add a turret to the game screen
    * @param turret
    */
   public void addTurret(Turret turret) {
      this.addEntity(turret);
   }

   public void addEntity(Entity entity) {
      newEntities.add(entity);
   }

   /**
    * When a turret shoots, it calls this method to add the bullet to the sprites
    * So they are drawn on the screen
    * @param b
    */
//    public void addBullet(Bullet bullet) {
//        sprites.add(bullet);
//    }
   /**
    * If an enemy reaches the end of the screen or the castle, then a live is wasted.
    */
   public void reachedEnd() {
      wastedLives++;
      if (wastedLives >= MAXLIVES) {
         this.stop();//game ended
      }
   }

   /**
    * @return Current level game is in.
    */
   public int getLevel() {
      return this.level;
   }

   private void testingPurposes() {
      for(int x=100; x<700; x+=100){
         //addEntity(new TurretBlue(x, 190, this));
      }
   }

   public List<Enemy> getEnemies() {
      return enemies;
   }

   public void setWindowTitle(String title) {
      super.setName(title);
   }

   public void setResolution(int widthPx, int heightPx) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }

   public void startRendering() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public boolean isKeyPressed(int keycode) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void setGameWindowCallback(IGameWindowCallback callback) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void setKeyboardHandler(IKeyboardHandler keyboardHandler) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void setBackGroundColour(float red, float green, float blue, float alpha) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void stopRendering() {
      proceed = false;
   }

   private class TurretMouseListener extends MouseAdapter {

      @Override
      public void mousePressed(MouseEvent e) {
         Rectangle2D tinyCursorBox = new Rectangle(e.getX(), e.getY(), 1, 1);
         for (ISelectable selectableItem : selectables) {
            if (selectableItem.getClickZone().intersects(tinyCursorBox)) {
               selectableItem.select();
               if (selectableItem instanceof IMovable) {
                  movableItems.add((IMovable) selectableItem);
               }
            } else {
               selectableItem.deselect();
               if (selectableItem instanceof IMovable) {
                  movableItems.remove((IMovable) selectableItem);
               }
            }
         }
      }

      @Override
      public void mouseMoved(MouseEvent e) {
         System.out.println("Moved: " + e.getX() + "," + e.getY());
      }

      @Override
      public void mouseDragged(MouseEvent e) {
         System.out.println("Dragged: " + e.getX() + "," + e.getY());
      }
   }
   private KeyAdapter keybListener = new TurretKeyboardListener();
   private Collection<IMovable> movableItems = new HashSet<IMovable>();

   private void selectMovables() {
      if (!movableItems.isEmpty()) {
         for (IMovable movableItem : this.movableItems) {
            movableItem.setMoving(true);
         }
      }
   }

   private void undoMove() {
      if (!movableItems.isEmpty()) {
         for (IMovable movableItem : this.movableItems) {
            movableItem.setMoving(false);
         }
      }
   }

   private class TurretKeyboardListener extends KeyAdapter {
      @Override
      public void keyPressed(KeyEvent e) {
         switch (e.getKeyCode()) {
            case KeyEvent.VK_M:
               selectMovables();
               break;
            case KeyEvent.VK_ESCAPE:
               undoMove();
               break;
         }
      }
   }
}
