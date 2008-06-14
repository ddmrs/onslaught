/**
 * TODO:
 */
package onslaught.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

/**
 *
 * @author Dries Demeurisse
 */
public class EnemyPrinter extends Enemy
{

    private final static int WIDTH = 16;
    private final static int HEIGHT = 16;

    public EnemyPrinter(Point2D.Float position, int level) {
        super(position, level);
    }

    @Override
    public void draw(Graphics g) {
        if(isAlive()) {//only draw when alive
            g.setColor(Color.WHITE);
            g.fillRect(Math.round(getPosition().x), Math.round(getPosition().y), WIDTH, HEIGHT);
        }
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }
}
