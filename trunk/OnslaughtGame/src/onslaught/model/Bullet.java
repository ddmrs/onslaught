package onslaught.model;

import java.awt.geom.Point2D;

public abstract class Bullet extends Sprite
{
    private int damage;
    private Enemy target;

    public Bullet(Point2D.Float position, Enemy target) {
        super(position);
        this.target = target;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Enemy getTarget() {
        return target;
    }
}
