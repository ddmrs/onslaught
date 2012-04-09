package onslaught.model;

/**
 *
 * @author ethic
 */
public class Life extends Entity {

    public Life(double x, double y) {
        super("images/life.png", x, y);
    }

    
    @Override
    public void collidedWith(Entity other) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(long time) {
        
    }

}
