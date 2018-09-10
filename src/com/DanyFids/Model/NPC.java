package com.DanyFids.Model;

/**
 * Created by Daniel on 12/30/2017.
 */
public abstract class NPC extends Entity {
    @Override
    public void land() {

    }

    public abstract void AI();

    public abstract void update();

    public abstract void move();

    public abstract NPC copy();

    public abstract void hitDetect(Player p);

    public abstract void hitDetect(Entity e);
}
