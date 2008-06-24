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
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import onslaught.model.*;
import onslaught.model.turret.TurretRed;

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
    private final int FPS = 80; // milliseconds
        /* Number of frames with a delay of 0 ms before the animation thread yields
    to other running threads. */
    private final int NO_DELAYS_PER_YIELD = 16;
    /* no. of frames that can be skipped in any one animation loop
    i.e the games state is updated but not rendered*/
    private final int MAX_FRAME_SKIPS = 5;   // was 2;

    //lists
    private List<Sprite> sprites;
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
    private int enemyCount;//counts how many enemies are on screen
    //timevars
    private long currentTime,  oldTime,  timeDifference,  sleepTime;
    private long overSleepTime = 0L;
    private int noDelays = 0;
    private long excess = 0L;    

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
        enemyCount = 0;
        //initialise lists
        sprites = new ArrayList<Sprite>();
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
            sendNextWave();
            
            addTurret(new TurretBlue(new Point2D.Float(100, 150),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(200, 150),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(300, 150),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(400, 150),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(500, 150),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(600, 150),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(700, 150),this, enemies));
            
            addTurret(new TurretBlue(new Point2D.Float( 50, 450),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(150, 450),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(250, 450),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(350, 450),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(450, 450),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(550, 450),this, enemies));
            addTurret(new TurretBlue(new Point2D.Float(550, 450),this, enemies));      
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
//        //first update enemies, so new location will be known to turrets
//        for(Sprite sprite : enemies) {
//            sprite.update(currentTime);
//        }
//        //afterwards update turrets, which should have new angle to shoot and fire new bullets
//        for(Sprite sprite : turrets) {
//            sprite.update(currentTime);
//        }
//        //finally update all bullets, including newly fired ones
//        for(Sprite sprite : bullets) {
//            sprite.update(currentTime);
//        }
        Iterator<Sprite> it = sprites.iterator();
        while(it.hasNext()){
            Sprite s = it.next();
            if(s.isAlive()){
                s.update(currentTime);
            }
            else{
                if(s instanceof Enemy){
                    enemyCount++;
                    enemies.remove(s);
                }
                it.remove();
                
            }
        }
        sprites.addAll(bullets);
        bullets.clear();
        
        if(enemyCount >= 40){
            sendNextWave();
            enemyCount = 0;
        }
    }

    public void buffer() {
        //clear background
        bufferGraphics.setColor(BACKGROUNDCOLOR);
        bufferGraphics.fillRect(0, 0, PWIDTH, PHEIGHT);

        // Draw game objects
//        for(Sprite sprite : turrets) {
//            sprite.draw(bufferGraphics);
//        }
//        for(Sprite sprite : enemies) {
//            sprite.draw(bufferGraphics);
//        }
//        for(Sprite sprite : bullets) {
//            sprite.draw(bufferGraphics);
//        }
        for(Sprite sprite : sprites) {
            sprite.draw(bufferGraphics);
        }
    }

    public void paint() {
        graphics.drawImage(image, 0, 0, this);
    }

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

    // Game functions --------------------------------------------------------//
    public void sendNextWave() {
        Point2D.Float startPosition = new Point2D.Float(1, 300);
        for(int i = 0; i<40; i++){
           Enemy enemy = new EnemyPrinter(new Point2D.Float(startPosition.x-i*20, startPosition.y), level, this);
           sprites.add(enemy); 
           //enemyCount++;
           enemies.add(enemy);
           if(i ==10 || i==20 || i==30){
               level++;
           }
        } 
        level++;
    }
    
    public void addTurret(Turret turret){
        sprites.add(turret);
        Turret t = new TurretBlue(new Point2D.Float(turret.getPosition().x + 30, turret.getPosition().y), this, enemies);
        sprites.add(t);
        t = new TurretBlue(new Point2D.Float(t.getPosition().x + 30, t.getPosition().y), this, enemies);
        sprites.add(t);        
    }
    
    public void removeBullet(Bullet b){
        //bullets.remove(b);
    }
    
    public void removeEnemy(Enemy e){
//        enemies.remove(e);
//        if(enemies.size() < 1){
//            sendNextWave();
//        }
    }
    
    public void addBullet(Bullet b){
        bullets.add(b);
    }
}
