package com.DanyFids.Model.NPCs;

import com.DanyFids.Model.*;
import com.DanyFids.graphics.SpriteSheet;

import java.util.LinkedList;

public class Sign extends NPC {
    private int dialogueId;

    public Sign(int x, int y, int id){
        this.setX(x);
        this.setY(y);

        this.HEIGHT = 32;
        this.WIDTH = 32;

        this.sprt = new SpriteSheet("/test_sign.png");

        dialogueId = id;
    }

    @Override
    public void AI() {

    }

    @Override
    public void update() {

    }

    @Override
    public void move() {

    }

    @Override
    public NPC copy() {
        return null;
    }

    @Override
    public void hitDetect(Player p) {

    }

    @Override
    public void interact(Player p, LinkedList<Powerup> powerups, LinkedList<Enemy> enemies) {

    }

    @Override
    public void hitDetect(Entity e) {

    }
}
