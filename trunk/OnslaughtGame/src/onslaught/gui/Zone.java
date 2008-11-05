package onslaught.gui;

import onslaught.model.turret.TurretBlue;
import onslaught.model.turret.Turret;
import onslaught.model.enemy.EnemyPrinter;
import onslaught.model.enemy.Enemy;
import onslaught.model.bullet.Bullet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import onslaught.model.*;
import onslaught.model.turret.TurretRed;

public class Zone extends JPanel implements Runnable {
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

    private final Point2D.Float startPosition = new Point2D.Float(1, 300);
    //lists
    private List<Sprite> sprites = new ArrayList<Sprite>();
    //loopinfo
    private boolean proceed = true;
    //gamevar's
    private int level = 0;
    private int wastedLives = 0;//number of wasted lives (nmy reaches en of screen)
    private long timeBetweenEnemies;
    private long waveTime;
    private long periodBetweenFrames;
    private int enemyKillCount;//counts how many enemies are on screen
    //timevars
    private long currentTime,  oldTime,  timeDifference,  sleepTime;
    private long overSleepTime = 0L;
    private int noDelays = 0;
    private long excess = 0L;
    
    private List<Sprite> tempSprites = new ArrayList<Sprite>();

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
    }

    // wait for the JPanel to be added to the JFrame before starting
    @Override
    public void addNotify() {
        super.addNotify();   // creates the peer

        start();         // start the thread

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
        Iterator<Sprite> it = sprites.iterator();
        //loop through all sprites
        while (it.hasNext()) {
            Sprite sprite = it.next();
            //only update 'alive' sprites
            if (sprite.isAlive()) {
                sprite.update(currentTime);
                if (sprite instanceof Turret) {
                    Turret turret = (Turret) sprite;
                    tempSprites.addAll(turret.getBullets());
                    turret.clearBullets();
                }
            } else {
                if (sprite instanceof Enemy) {
                    enemyKilled();
                }
                //sprites wich aren't alive have to be removed
                it.remove();
            }
        }
        sprites.addAll(tempSprites);
        tempSprites.clear();
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
        for (Sprite sprite : sprites) {
            sprite.draw(bufferGraphics);
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
    private void enemyKilled() {
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
            Enemy enemy = new EnemyPrinter(new Point2D.Float(startPosition.x - i * 20, startPosition.y), level, this);
            //add this enemy to sprites
            tempSprites.add(enemy);

        }
        //increase level per wave
        level++;
    }

    /**
     * Add a turret to the game screen
     * @param turret
     */
    public void addTurret(Turret turret) {
        tempSprites.add(turret);
//        Turret t = new TurretBlue(new Point2D.Float(turret.getPosition().x + 30, turret.getPosition().y), this, getEnemies());
//        sprites.add(t);
//        t = new TurretBlue(new Point2D.Float(t.getPosition().x + 30, t.getPosition().y), this, getEnemies());
//        sprites.add(t);
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
            stop();//game ended

        }
    }

    private void testingPurposes() {
        addTurret(new TurretBlue(new Point2D.Float(100, 150), this, getEnemies()));
        addTurret(new TurretBlue(new Point2D.Float(200, 150), this, getEnemies()));
        addTurret(new TurretBlue(new Point2D.Float(300, 150), this, getEnemies()));
        addTurret(new TurretBlue(new Point2D.Float(400, 150), this, getEnemies()));
        addTurret(new TurretBlue(new Point2D.Float(500, 150), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(600, 150), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(700, 150), this, getEnemies()));

        addTurret(new TurretRed(new Point2D.Float(50, 450), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(150, 450), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(250, 450), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(350, 450), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(450, 450), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(550, 450), this, getEnemies()));
        addTurret(new TurretRed(new Point2D.Float(550, 450), this, getEnemies()));
    }

    public List<Enemy> getEnemies() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        for (Sprite sprite : sprites) {
            if (sprite instanceof Enemy) {
                enemies.add((Enemy) sprite);
            }
        }
        return enemies;
    }
}
