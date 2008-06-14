package onslaught.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import onslaught.model.*;

public class Zone extends JPanel implements Runnable
{
    //thread
    private Thread animator;
    //graphics
    private Graphics graphics;
    private Graphics bufferGraphics;
    private Image image;
    //finals
    private final int PHEIGHT = 520;
    private final int PWIDTH = 800;
    private final Color BACKGROUNDCOLOR = new Color(247, 207, 172);
    private final int FPS = 40; // milliseconds
        /* Number of frames with a delay of 0 ms before the animation thread yields
    to other running threads. */
    private final int NO_DELAYS_PER_YIELD = 16;
    /* no. of frames that can be skipped in any one animation loop
    i.e the games state is updated but not rendered*/
    private final int MAX_FRAME_SKIPS = 5;   // was 2;

    //lists
    private List<Turret> turrets;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    //loopinfo
    private boolean proceed;
    //gamevar's
    private int level;
    private long timeBetweenEnemies;
    private long waveTime;
    private long periodBetweenFrames;
    private Point2D.Float turretPosition = new Point2D.Float(450,35);

    public Zone() {
        periodBetweenFrames = (long) 1000.0 / FPS;
        periodBetweenFrames *= 1000000L;// ms --> nanosecs 
    }

    public void initComponents() {
        //panelsize
        Dimension dimension = new Dimension(PWIDTH, PHEIGHT);
        this.setPreferredSize(dimension);
        this.setSize(dimension);
        //load graphics
        graphics = this.getGraphics();
        image = this.createImage(PWIDTH, PHEIGHT);
        bufferGraphics = image.getGraphics();
        //setDefaults
        proceed = true;
        level = 1;
        //initialise lists
        turrets = new ArrayList<Turret>();
        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
    //gameComponent creation
    }

    public void start() {
        if(animator == null) {
            initComponents();
            animator = new Thread(this);
            animator.start();
            sendNextWave(0L);
            addTurret(turretPosition);
        }
    }

    public void stop() {
        if(animator != null) {
            proceed = false;
            animator = null;
        }
    }

    // Gaming loop -----------------------------------------------------------//
    public void run() {
        while(proceed) {
            update();
            buffer();
            paint();
            sleep();
        }
    }

    public void update() {
        //first update enemies, so new location will be known to turrets
        for(Sprite sprite : enemies) {
            sprite.update(0L);
        }
        //then check for enemies entering turrets range
        checkAllTurrets();
        //afterwards update turrets, which should have new angle to shoot and fire new bullets
        for(Sprite sprite : turrets) {
            sprite.update(0L);
        }
        //finally update all bullets, including newly fired ones
        for(Sprite sprite : bullets) {
            sprite.update(0L);
        }
        //check if bullets hit an enemy
        checkBulletCollision();
        //check if any enemy died
        checkEnemiesHealth();
    }

    public void buffer() {
        //clear background
        bufferGraphics.setColor(BACKGROUNDCOLOR);
        bufferGraphics.fillRect(0, 0, PWIDTH, PHEIGHT);

        // Draw game objects
        for(Sprite sprite : turrets) {
            sprite.draw(bufferGraphics);
        }
        for(Sprite sprite : enemies) {
            sprite.draw(bufferGraphics);
        }
        for(Sprite sprite : bullets) {
            sprite.draw(bufferGraphics);
        }
    }

    public void paint() {
        graphics.drawImage(image, 0, 0, this);
    }
    
    private long currentTime,  oldTime,  timeDifference,  sleepTime;
    private long overSleepTime = 0L;
    private int noDelays = 0;
    private long excess = 0L;

    public void sleep() {
        currentTime = System.nanoTime();
        timeDifference = currentTime - oldTime;
        sleepTime = (periodBetweenFrames - timeDifference) - overSleepTime;

        if(sleepTime > 0) {   // some time left in this cycle
            try {
                Thread.sleep(sleepTime / 1000000L);  // nano -> ms
            }
            catch(InterruptedException ex) {//should never occur
            }
            overSleepTime = (System.nanoTime() - currentTime) - sleepTime;
        }
        else {    // sleepTime <= 0; the frame took longer than the period
            excess -= sleepTime;  // store excess time value
            overSleepTime = 0L;

            if(++noDelays >= NO_DELAYS_PER_YIELD) {
                Thread.yield();   // give another thread a chance to run
                noDelays = 0;
            }
        }

        oldTime = System.nanoTime();

        /* If frame animation is taking too long, update the game state
        without rendering it, to get the updates/sec nearer to
        the required FPS. */
        int skips = 0;
        while((excess > periodBetweenFrames) && (skips < MAX_FRAME_SKIPS)) {
            excess -= periodBetweenFrames;
            update();    // update state but don't render
            skips++;
        }
    }

    // Game checks -----------------------------------------------------------//
    /**
     * TODO: update method to be appropriate for each type of bullet 
     */
    private void checkBulletCollision() {
        Iterator<Bullet> it = bullets.iterator();
        while(it.hasNext()) {
            Bullet sprite = it.next();
            if(isCollided(sprite, sprite.getTarget())) {
                sprite.getTarget().takeHit(sprite.getDamage());
                it.remove();
            }
        }
    }

    private boolean isCollided(Sprite spriteOne, Sprite spriteTwo) {
        return spriteOne.getCollisionBox().intersects(spriteTwo.getCollisionBox());
    }

    /**
     * Perform a cleanup method to remove all dead enemies from screen
     * 
     */
    private void checkEnemiesHealth() {
        boolean killedOne = false;
        Iterator<Enemy> it = enemies.iterator();
        while(it.hasNext()) {
            Enemy sprite = it.next();
            if(!sprite.isAlive()) {
                it.remove();
                killedOne = true;
            }
        }
        if(killedOne){
            sendNextWave(currentTime);
            killedOne = false;
        }
    }

    private void checkAllTurrets() {
        for(Turret turret : turrets) {
            checkEnemyInRange(turret);
        }
    }

    private void checkEnemyInRange(Turret turret) {
        Rectangle2D rangeBox = turret.getRangeBox();

        for(Enemy enemy : enemies) {
            if(enemy.getCollisionBox().intersects(rangeBox)) {
                Bullet bullet = turret.shoot(enemy, System.nanoTime());
                if(bullet != null) {
                    bullets.add(bullet);
                }
            }
        }
    }

    // Game functions --------------------------------------------------------//
    public void sendNextWave(long currentTime) {
        Point2D.Float startPosition = new Point2D.Float(20, 100);
        Enemy enemy = new EnemyPrinter(startPosition, level);
        enemies.add(enemy);
        level++;
    }
    
    public void addTurret(Point2D.Float position){
        Turret turret = new TurretBlue(position);
        turrets.add(turret);
    }
}
