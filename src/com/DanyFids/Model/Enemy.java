package com.DanyFids.Model;

import java.util.LinkedList;

/**
 * Created by Daniel on 9/30/2017.
 */
public abstract class Enemy extends Entity {
    public final int INVULN_TIME = 20;

    public int hp;

    public int invuln_timer = 0;
    //private int width;
    //private int height;

    //public float xSpeed = 0;
    //public float ySpeed = 0;

    public abstract void AI();

    public abstract void update();

    public abstract void hurt(int dmg, Direction d);

    public void primeInvuln(){
        this.invuln_timer = INVULN_TIME;
    }

    public void kill(LinkedList<Enemy> enemies, int id){
        enemies.remove(id);
    }

    public abstract boolean hitShield(Direction d);

    public abstract void hitDetect(Player p);

    public void move(){
        int x = this.getX();
        int y = this.getY();

        x += xSpeed;
        y += ySpeed;


        this.setX(x);
        this.setY(y);
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public abstract Enemy copy();
}
