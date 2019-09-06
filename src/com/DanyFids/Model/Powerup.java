package com.DanyFids.Model;

import com.DanyFids.graphics.SpriteSheet;

import java.util.Random;

/**
 * Created by Daniel on 3/17/2018.
 */
public abstract class Powerup extends Entity {

    private boolean remove = false;
    private boolean gravity = true;
    private boolean can_pickup = false;

    public Powerup(int x, int y, int w, int h, String path){
        this.x = x;
        this.y = y;
        this.WIDTH = w;
        this.HEIGHT = h;

        this.sprt = new SpriteSheet(path);

        Spawn();
    }

    public void update(){
        if(gravity){
            if(this.ySpeed < Physics.TERMINAL_V){
                this.ySpeed += Physics.GRAVITY;
            }
        }
    }

    @Override
    public void land() {
        can_pickup = true;
        this.xSpeed = 0;
    }

    public void hitDetect(Player p){
        if(can_pickup) {
            if (p.getX() < this.getX() + this.getWidth() && p.getX() + p.getWidth() > this.getX() &&
                    p.getY() < this.getY() + this.getHeight() && p.getY() + p.getHeight() > this.getY()) {
                this.remove = true;
                effect(p);
            }
        }
    }

    public void move(){
        int x = this.getX();
        int y = this.getY();

        x += xSpeed;
        y += ySpeed;


        this.setX(x);
        this.setY(y);
    }

    public boolean toRemove(){
        return remove;
    }

    private void Spawn(){
        Random gen = new Random();
        int jump = gen.nextInt(7);

        this.ySpeed = -jump;

        float move = gen.nextInt(3) - 1;

        boolean right = gen.nextBoolean();

        if(right){
            this.xSpeed = move;
        }else{
            this.xSpeed = -move;
        }
    }

    public abstract void effect(Player p);

    public abstract Powerup copy();
}
