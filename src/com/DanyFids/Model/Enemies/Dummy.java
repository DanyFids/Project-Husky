package com.DanyFids.Model.Enemies;

import com.DanyFids.Model.*;
import com.DanyFids.graphics.SpriteSheet;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Daniel on 9/30/2017.
 */
public class Dummy extends Enemy {
    private final int _WIDTH = 38;
    private final int _HEIGHT = 60;
    private final int START_HP = 10;

    private final int MOV_SPD = 2;

    private int origin_x;

    private boolean can_move = true;

    public Dummy(int x, int y){
        this.setX(x);
        this.setY(y);

        this.origin_x = x;

        hp = START_HP;

        this.HEIGHT = _HEIGHT;
        this.WIDTH = _WIDTH;
        sprt = new SpriteSheet("/test_dummy.png");
    }

    @Override
    public void AI() {
        if(this.can_move) {
            if (this.getX() != this.origin_x) {
                if (this.getX() > this.origin_x) {
                    this.xSpeed = -MOV_SPD;
                } else {
                    this.xSpeed = MOV_SPD;
                }
            } else {
                this.xSpeed = 0;
            }
        }
    }

    @Override
    public void update() {
        if(this.ySpeed < Physics.TERMINAL_V){
            this.ySpeed += Physics.GRAVITY;
        }

        if(invuln_timer > 0){
            invuln_timer--;
        }

        AI();
    }

    @Override
    public void hurt(int dmg, Direction d) {
        if(invuln_timer == 0) {
            hp -= dmg;
            ySpeed = -Physics.KNOCK_BACK;
            if (d == Direction.right) {
                xSpeed = Physics.KNOCK_BACK;
            } else if(d == Direction.left){
                xSpeed = -Physics.KNOCK_BACK;
            }
            this.can_move = false;

            invuln_timer = INVULN_TIME;
        }
    }


    @Override
    public void land() {
        this.can_move = true;
    }

    @Override
    public void kill(Vector<Enemy> enemies, Vector<Powerup> powerups, int id){
        enemies.remove(id);

        enemies.add(new Dummy(this.origin_x, 0));
    }

    @Override
    public boolean hitShield(Direction d) {
        return false;
    }

    public void hitDetect(Player p){

    }

    public Enemy copy(){
        return new Dummy(this.getX(), this.getY());
    }
}
