package com.onslaught;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import onslaught.interfaces.ICollidable;
import onslaught.util.CollisionBoxType;
import org.junit.Assert;
import org.junit.Test;
import static onslaught.util.CollisionBoxType.CIRCLE;
import static onslaught.util.CollisionBoxType.RECTANGLE;
import onslaught.util.CollisionUtils;

/**
 *
 * @author EthiC
 */
public class TestCollisionUtils {

    @Test
    public void rechthoeken() {
        ICollidable col1 = new Collided(1, 1, 10, 10, RECTANGLE);
        ICollidable col2 = new Collided(4, 4, 10, 10, RECTANGLE);
        Assert.assertTrue(CollisionUtils.isCollided(col1, col2));

        col1 = new Collided(1, 1, 1, 1, RECTANGLE);
        col2 = new Collided(2, 1, 1, 1, RECTANGLE);
        Assert.assertFalse(CollisionUtils.isCollided(col1, col2));
    }

    @Test
    public void cirkels() {
        ICollidable col1 = new Collided(1, 1, 10, 10, CIRCLE);
        ICollidable col2 = new Collided(4, 4, 10, 10, CIRCLE);
        Assert.assertTrue(CollisionUtils.isCollided(col1, col2));
    }

    @Test
    public void rectCirkel() {
        ICollidable col1 = new Collided(1, 1, 10, 10, RECTANGLE);
        ICollidable col2 = new Collided(4, 4, 10, 10, CIRCLE);
        Assert.assertTrue(CollisionUtils.isCollided(col1, col2));
    }

    public class Collided implements ICollidable {

        private Rectangle2D rect;
        private CollisionBoxType type;

        public Collided(int x, int y, int width, int height, CollisionBoxType type) {
            this.rect = new Rectangle(x, y, width, height);
            this.type = type;
        }

        public Shape getCollisionBox() {
            return rect;
        }

        public CollisionBoxType getCollisionBoxType() {
            return type;
        }
    }
}
