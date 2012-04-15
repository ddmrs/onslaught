package onslaught.game;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import onslaught.core.ResourceFactory;
import onslaught.core.interfaces.IKeyboardHandler;
import onslaught.handlers.LwjglKeyboardHandler;
import onslaught.interfaces.IGameWindow;
import onslaught.interfaces.IGameWindowCallback;
import onslaught.model.Entity;
import onslaught.util.TimingUtility;

/**
 *
 * @author EthiC
 */
public class OnslaughtGame implements IGameWindowCallback {
    // Core stuff
    private IGameWindow gameWindow;
    private IKeyboardHandler keyboardHandler;
    
    // GameOperator
    private GameOperations gameOps;
    private GameKeyboardActions keybActions;
    
    // Timing
    private long lastFpsTime = TimingUtility.time();
    private long lastLoopTime = TimingUtility.time();
    private int wantedFps = GameProperties.WANTED_FPS;
    private long fpsDuration = TimingUtility.getOneSecond() / wantedFps;
    private int fps = 0;
    
    // Screen properties
    private static final String WINDOW_TITLE = "Java Onslaught FTW!";
    private static final float BG_RED = 1.0f;
    private static final float BG_GREEN = 1.0f;
    private static final float BG_BLUE = 1.0f;

    public OnslaughtGame(ResourceFactory.RenderType renderType) {
        this.keyboardHandler = new LwjglKeyboardHandler();
        this.gameWindow = ResourceFactory.get().setRenderingType(renderType).getGameWindow();
        this.gameWindow.setGameWindowCallback(this);
        this.gameWindow.setWindowTitle(WINDOW_TITLE);
        this.gameWindow.setResolution(Map.WIDTH, Map.HEIGHT);
        this.gameWindow.setBackGroundColour(BG_RED, BG_GREEN, BG_BLUE, 0.0f);
    }

    public static void main(String[] args) {
        OnslaughtGame game = new OnslaughtGame(ResourceFactory.RenderType.OPENGL_LWJGL);
        game.startGame();
    }

    public void startGame() {
        gameOps = new GameOperations();
        this.keybActions = new GameKeyboardActions(keyboardHandler, gameOps);
        //gameOps.getStats().setStartTime(this.time());
        this.gameWindow.startRendering();

    }

    @Override
    public void initialise() {
        gameOps.prepareNewGame();
    }

    @Override
    public void frameRendering() {
        // Regulate the frames + rendering + sleeping:
        long timePassed = 0;
        try {
            timePassed = (lastLoopTime + fpsDuration) - TimingUtility.time();
            if (timePassed > 0) {
                Thread.sleep(timePassed / 1000);
            } else {
                System.out.println("Time: " + timePassed / 1000 + " ms.");
            }
        } catch (Exception ex) {
            Logger.getLogger("render").log(Level.SEVERE, "render fault time: " + timePassed / 1000, ex);
            System.exit(0);
        }

        /**
         * Calculate how long its been since last update. Could be used to eg.
         * calculate how far the entities should move every loop
         */
        long delta = TimingUtility.time() - lastLoopTime;
        keybActions.listenAndExecute(delta);
        drawFPS(delta);
        drawAllEntities(gameOps.getEntities());
        gameOps.update(delta);
        if (!gameOps.isGameRunning()) {
            gameWindow.stopRendering();
        }
    }

    /**
     * Update the fps counter if needed, happens once every second
     *
     * @param delta The passed time in microseconds
     */
    private void drawFPS(long delta) {
        lastLoopTime = TimingUtility.time();
        lastFpsTime += delta;
        fps++;
        // update our FPS counter every second
        if (lastFpsTime >= TimingUtility.getOneSecond()) {
            gameWindow.setWindowTitle(WINDOW_TITLE + " (FPS: " + fps + ")" + " Drawed: " + gameOps.getEntities().size() + " things.");
            lastFpsTime = 0;
            fps = 0;
        }
    }

    /**
     * Draw all entities on screen.
     */
    private void drawAllEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            entity.draw();
        }
    }

    @Override
    public void windowClosed() {
        System.out.println("Window closed / Game exited.");
    }
}
