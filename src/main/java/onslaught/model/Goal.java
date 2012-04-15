package onslaught.model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import onslaught.util.EntityVisitor;

/**
 * Defines a goal, which enemies want to invade.
 *
 * @author ethic
 */
public class Goal extends Entity {

    private Rectangle2D rect;
    private static final String[] URLS = {"images/goal.png"};
    private ArrayList<Life> lifes = new ArrayList<Life>();

    public Goal(Point2D position) {
        this(position.getX(), position.getY());
    }

    public Goal(double x, double y) {
        super(URLS[0], x, y);
        rect = new Rectangle2D.Double(x, y, 10, 10);
        createLifes();
        z = 0;
    }

    private void createLifes() {
        lifes.add(new Life(x, y - 10));
        lifes.add(new Life(x, y - 20));
        lifes.add(new Life(x, y));
        lifes.add(new Life(x, y + 10));
        lifes.add(new Life(x, y + 20));
        lifes.add(new Life(x + 20, y - 10));
        lifes.add(new Life(x + 20, y - 20));
        lifes.add(new Life(x + 20, y));
        lifes.add(new Life(x + 20, y + 10));
        lifes.add(new Life(x + 20, y + 20));
    }

    /**
     * Try to enter/attack the goal objective
     *
     * @return true if attempt succeeded.
     */
    public boolean enterGoal(Entity entity) {
        boolean worked = entity.getBounds().intersects(rect);
        if (worked && !lifes.isEmpty()) {
            lifes.remove(0).kill();
            System.out.println("Lost a life there!");
        }
        return worked;
    }

    @Override
    public void collidedWith(Entity other) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(long time) {
        if (lifes.size() < 1) {
            super.kill();
        }
    }

    /**
     * Retrieve the lifes.
     */
    public int getLifesLeft() {
        return lifes.size();
    }

    @Override
    public void draw() {
        super.draw();
    }

    public ArrayList<Life> getLifes() {
        return lifes;
    }

    void visit(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
