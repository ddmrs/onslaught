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

    public void setWindowTitle(String title) {
        this.screenTitle = title;
        if (org.lwjgl.opengl.Display.isCreated()) {
            org.lwjgl.opengl.Display.setTitle(screenTitle);
        }

    }

    public void setResolution(int widthPx, int heightPx) {
        this.screenWidth = widthPx;
        this.screenHeight = heightPx;
    }

    public void startRendering() {
        try {
            this.setDisplayMode();
            org.lwjgl.opengl.Display.create();
            //initGL();
            //resizeWindow(screenWidth, screenHeight);
            GL11.glShadeModel(GL11.GL_SMOOTH);

            GL11.glClearDepth(1.0);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();

            GL11.glOrtho(0, screenWidth, screenHeight, 0, -1, 1);
            //GL11.glMatrixMode(GL11.GL_MODELVIEW);
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

    private void resizeWindow(int width, int height) {
        if (height == 0) {
            height = 1;
        }
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        //GLU.gluPerspective(45.0f, width / height, 0.1f, 100.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    private void initGL() {
        GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading

        GL11.glClearColor(red, green, blue, alpha);
        // Black Background
        GL11.glClearDepth(1.0); // Depth Buffer Setup

        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
//
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
//        //GL11.glEnable(GL11.GL_CULL_FACE);
//        // The Type Of Depth Testing To Do
//
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
//        // Select The Projection Matrix
//        GL11.glLoadIdentity(); // Reset The Projection Matrix
//
//        // Calculate The Aspect Ratio Of The Window
//        GLU.gluPerspective(45.0f,
//                (float) 800 / (float) 600,
//                0.1f, 100.0f);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        // Select The Modelview Matrix

        // Really Nice Perspective Calculations
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
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

//            DisplayMode[] displayModes = Display.getAvailableDisplayModes(screenWidth, screenHeight, -1, -1, -1, -1, 60, -1);
//            Display.setDisplayMode(displayModes, wantedDispMode);
            Display.setDisplayMode(new DisplayMode(screenWidth,screenHeight));

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
            //GL11.glClearColor(red, green, blue, alpha);
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

    public void setGameWindowCallback(IGameWindowCallback callback) {
        this.gameCallback = callback;
    }

    public void setBackGroundColour(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public void stopRendering() {
        gameRunning = false;
    }
}
