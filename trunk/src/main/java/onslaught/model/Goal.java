package onslaught.model;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import onslaught.util.EntityVisitor;

/**
 * Defines a goal, which enemies want to invade.
 * @author ethic
 */
public class Goal extends Entity {

   // private int lifes = 10;
    private Rectangle2D rect;
    private static final String[] URLS = {"images/goal.png"};
    private ArrayList<Life> lifes = new ArrayList<Life>();

    public Goal(double x, double y) {
        super(URLS[0], x, y);
        rect = new Rectangle2D.Double(x, y, 10, 10);
        createLifes();
    }
    
    private void createLifes(){
        lifes.add(new Life(x, y-10));
        lifes.add(new Life(x, y-20));
        lifes.add(new Life(x, y));
        lifes.add(new Life(x, y+10));
        lifes.add(new Life(x, y+20));
        lifes.add(new Life(x+20, y-10));
        lifes.add(new Life(x+20, y-20));
        lifes.add(new Life(x+20, y));
        lifes.add(new Life(x+20, y+10));
        lifes.add(new Life(x+20, y+20));        
    }

    /**
     * Try to enter/attack the goal objective
     * @return true if attempt succeeded.
     */
    public boolean enterGoal(Entity entity) {
        boolean worked = entity.getBounds().intersects(rect);
        if (worked && !lifes.isEmpty()) {
            lifes.remove(0).kill();
            //lifes--;
            System.out.println("Lost a live there!");
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
     * Change the lifes.
     * @param lifes
     */
//    public void setLifes(int lifes) {
//        this.lifes = lifes;
//    }

    /**
     * Retrieve the lifes.
     */
    public int getLifes() {
        return lifes.size();
    }

    @Override
    public void draw() {
        super.draw();
        for(Life _life: lifes){
            _life.draw();
        }
    }
    
    void visit(EntityVisitor visitor) {
        visitor.visit(this);
    }
}
