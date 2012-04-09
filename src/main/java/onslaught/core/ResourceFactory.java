package onslaught.core;

import onslaught.core.lwjgl.LWJGLGameWindow;
import onslaught.core.lwjgl.LWJGLSprite;
import onslaught.interfaces.IGameWindow;
import onslaught.interfaces.Sprite;

/**
 * A central reference point for creating resources for use in the game. The resources
 * return may be implemented in several different rendering contexts but will also
 * work within the GameWindow supplied from this class. For instance, a Sprite retrieved
 * as a resource will draw happily in the GameWindow supplied from this factory
 *
 * @author Kevin Glass
 */
public class ResourceFactory {

   /** The single instance of this class to ever exist <singleton> */
   private static final ResourceFactory single = new ResourceFactory();

   public static enum RenderType {

      OPENGL_LWJGL;
   }

   /**
    * Retrieve the single instance of this class
    *
    * @return The single instance of this class
    */
   public static ResourceFactory get() {
      return single;
   }
   /** The type of rendering that we are currently using */
   private RenderType renderingType = RenderType.OPENGL_LWJGL;
   /** The window the game should use to render */
   private IGameWindow window;

   /**
    * The default contructor has been made private to prevent construction of
    * this class anywhere externally. This is used to enforce the singleton
    * pattern that this class attempts to follow
    */
   private ResourceFactory() {
   }

   /**
    * Set the rendering method that should be used. Note: This can only be done
    * before the first resource is accessed.
    *
    * @param renderingType The type of rendering to use
    */
   public ResourceFactory setRenderingType(RenderType renderType) {
      // If the rendering type is unrecognised tell the caller
      if (window != null) {
         throw new RuntimeException("Attempt to change rendering method at game runtime");
      }

      if (renderType == null) {
         throw new RuntimeException("No rendertype was given.");
      }
      this.renderingType = renderType;
      return this;
   }

   /**
    * Retrieve the game window that should be used to render the game
    *
    * @return The game window in which the game should be rendered
    */
   public IGameWindow getGameWindow() {
      // if we've yet to create the game window, create the appropriate one
      if (window == null) {
         switch (renderingType) {
            case OPENGL_LWJGL: {
               window = new LWJGLGameWindow();
               break;
            }
         }
      }

      return window;
   }

   /**
    * Create or get a sprite which displays the image that is pointed
    * to in the classpath by "ref"
    *
    * @param ref A reference to the image to load
    * @return A sprite that can be drawn onto the current graphics context.
    */
   public Sprite getSprite(String ref) {
      if (window == null) {
         throw new RuntimeException("Attempt to retrieve sprite before game window was created");
      }

      switch (renderingType) {
         case OPENGL_LWJGL: {
            return new LWJGLSprite((LWJGLGameWindow) window, ref);
         }
      }

      throw new RuntimeException("Unknown rendering type: " + renderingType);
   }
}