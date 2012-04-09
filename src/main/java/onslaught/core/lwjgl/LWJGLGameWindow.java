package onslaught.core.lwjgl;

import onslaught.core.TextureLoader;
import onslaught.interfaces.IGameWindow;
import onslaught.interfaces.IGameWindowCallback;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author EthiC
 */
public class LWJGLGameWindow implements IGameWindow {

    private IGameWindowCallback gameCallback;
    private boolean gameRunning = true;
    // Screen props
    private int screenWidth;
    private int screenHeight;
    private int frequention = 60;
    private String screenTitle;
    private float red = 1.0f, green = 0.0f, blue = 1.0f, alpha = 1.0f;
    // Dif
    private TextureLoader textureLoader;

    public LWJGLGameWindow() {
    }

    @Override
    public void setWindowTitle(String title) {
        this.screenTitle = title;
        if (org.lwjgl.opengl.Display.isCreated()) {
            org.lwjgl.opengl.Display.setTitle(screenTitle);
        }

    }

    @Override
    public void setResolution(int widthPx, int heightPx) {
        this.screenWidth = widthPx;
        this.screenHeight = heightPx;
    }

    @Override
    public void startRendering() {
        try {
            this.setDisplayMode();
            org.lwjgl.opengl.Display.create();

            GL11.glShadeModel(GL11.GL_SMOOTH);

            GL11.glClearDepth(1.0);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();

            GL11.glOrtho(0, screenWidth, screenHeight, 0, -1, 1);
            this.textureLoader = new TextureLoader();

            if (gameCallback != null) {
                gameCallback.initialise();
            }

            GL11.glClearColor(red, green, blue, alpha);

        } catch (LWJGLException le) {
            System.out.println("error happend when starting redering:" + le.getMessage());
            gameCallback.windowClosed();
        }
        //run gameloop
        this.gameLoop();
    }

    /**
     * Sets the display at fullscreen.
     *
     * @return True if fullscreen successfull.
     */
    private boolean setDisplayMode() {
        boolean fullscreen = false;
        try {
            final String[] wantedDispMode = new String[]{
                "width=" + screenWidth,
                "height=" + screenHeight,
                "freq=" + frequention,
                "bpp=" + org.lwjgl.opengl.Display.getDisplayMode().getBitsPerPixel()
            };

            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));

            System.out.println("fullscreen: " + wantedDispMode[0] + "*" + wantedDispMode[1] + ":" + wantedDispMode[2] + "@" + wantedDispMode[3]);

            fullscreen = true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to enter fullscreen, continuing in windowed mode");
        }
        return fullscreen;
    }

    /**
     * Run the game, draw content and update gamelogic.
     */
    private void gameLoop() {
        while (gameRunning) {
            //Clear screen
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            // let subsystem paint and do gamelogic
            if (gameCallback != null) {
                gameCallback.frameRendering();
            }

            // update window contents
            org.lwjgl.opengl.Display.update();

            if (org.lwjgl.opengl.Display.isCloseRequested()) {
                gameRunning = false;
            }
        }
        org.lwjgl.opengl.Display.destroy();
        gameCallback.windowClosed();
    }

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    @Override
    public void setGameWindowCallback(IGameWindowCallback callback) {
        this.gameCallback = callback;
    }

    @Override
    public void setBackGroundColour(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    @Override
    public void stopRendering() {
        gameRunning = false;
    }
}
