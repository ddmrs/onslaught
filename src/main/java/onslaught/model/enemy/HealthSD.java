package onslaught.model.enemy;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import onslaught.interfaces.Sprite;
import onslaught.interfaces.SpriteDecorator;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 *
 * @author ethic
 */
public class HealthSD extends SpriteDecorator {

    private Sprite sprite;
    private String health;
    private static final Font font = new Font("Verdana", Font.BOLD, 8);
    private static final UnicodeFont ttFont = new UnicodeFont(font, 10, false, false);
    private final int xOffset;
    private final int yOffset;

    static {
        ttFont.addAsciiGlyphs();
        ttFont.addGlyphs(19, 93);
        ttFont.getEffects().add(new ColorEffect(java.awt.Color.RED));
        try {
            ttFont.loadGlyphs();
        } catch (SlickException ex) {
            Logger.getLogger(HealthSD.class.getName()).log(Level.SEVERE, "Text shit!", ex);
        }
    }

    public HealthSD(Sprite sprite) {
        this.sprite = sprite;
        health = "100";
        xOffset = sprite.getWidth() / 2;
        yOffset = sprite.getHeight() / 2;
    }

    public void draw(float x, float y, float z, double angle) {
        ttFont.drawString(x - xOffset, y + yOffset, health, org.newdawn.slick.Color.red);
        sprite.draw(x, y, z, angle);
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public float getWidthPart() {
        return sprite.getWidthPart();
    }

    public float getHeightPart() {
        return sprite.getHeightPart();
    }

    public void setHealth(String health) {
        this.health = health;
    }
}
