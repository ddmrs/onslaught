/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onslaught;

import java.awt.geom.Point2D;
import junit.framework.Assert;
import onslaught.util.MapHelper;
import org.junit.Test;

/**
 *
 * @author EthiC
 */
public class GeneralTests {

    @Test
    public void mapTestHoriz() {
        MapHelper result = new MapHelper(new Point2D.Double(5, 5), new Point2D.Double(15, 5), 2);
        check(5, 7, result.getOrigin1());
        check(5, 3, result.getOrigin2());
        check(15, 7, result.getTarget1());
        check(15, 3, result.getTarget2());
    }

    @Test
    public void mapTestVert() {
        MapHelper result = new MapHelper(new Point2D.Double(5, 5), new Point2D.Double(5, 15), 2);
        check(3, 5, result.getOrigin1());
        check(7, 5, result.getOrigin2());
        check(3, 15, result.getTarget1());
        check(7, 15, result.getTarget2());
    }

    private static void check(int x, int y, Point2D result) {
        Assert.assertTrue(result.toString(), new Double(x).compareTo(result.getX()) == 0);
        Assert.assertTrue(result.toString(), new Double(y).compareTo(result.getY()) == 0);
    }
}
