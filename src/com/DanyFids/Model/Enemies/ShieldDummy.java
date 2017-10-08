package com.DanyFids.Model.Enemies;

import com.DanyFids.Model.Enemy;
import com.DanyFids.Model.Physics;
import com.DanyFids.Model.Player;
import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by Daniel on 10/1/2017.
 */
public class ShieldDummy extends Enemy {
    private final int _WIDTH = 38;
    private final int _HEIGHT = 60;
    private final int START_HP = 10;
    private final int TOUCH_DAMAGE = 1;

    private final int MOV_SPD = 2;

    private int origin_x;

    private boolean can_move = true;
    private boolean facing_right;

    public ShieldDummy(int x, int y, boolean fr){
        this.setX(x);
        this.setY(y);

        this.origin_x = x;

        hp = START_HP;

        this.HEIGHT = _HEIGHT;
        this.WIDTH = _WIDTH;
        sprt = new SpriteSheet("/test_shield_dummy.png");

        facing_right = fr;
    }

    @Override
    public void draw(BufferedImage screen) {
        Graphics g = screen.getGraphics();

        if(!facing_right) {
            g.drawImage(sprt.getPage(), this.getX(), this.getY(), null);
        }else{
            g.drawImage(sprt.getPage(), this.getX() + this.getWidth(), this.getY(), -this.getWidth(), this.getHeight(), null);
        }
    }

    @Override
    public void AI() {
        if(this.can_move) {
            if (this.getX() != this.origin_x) {
                if (this.getX() > this.origin_x) {
                    this.xSpeed = -MOV_SPD;
                    this.facing_right = false;
                } else {
                    this.xSpeed = MOV_SPD;
                    this.facing_right = true;
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
    public void hurt(int dmg, boolean to_right) {
        if(facing_right == to_right) {
            if (invuln_timer == 0) {
                hp -= dmg;
                ySpeed = -Physics.KNOCK_BACK;
                if (to_right) {
                    xSpeed = Physics.KNOCK_BACK;
                } else {
                    xSpeed = -Physics.KNOCK_BACK;
                }
                this.can_move = false;

                invuln_timer = INVULN_TIME;
            }
        }else{
            if(to_right){
                xSpeed = Physics.KNOCK_BACK;
            }else{
                xSpeed = -Physics.KNOCK_BACK;
            }
        }
    }

    @Override
    public boolean hitShield(boolean to_right){
        return facing_right != to_right;
    }


    @Override
    public void land() {
        this.can_move = true;
    }

    @Override
    public void kill(LinkedList<Enemy> enemies, int id){
        enemies.remove(id);

        enemies.add(new ShieldDummy(this.origin_x, 0, false));
    }

    @Override
    public void hitDetect(Player p){
        if(p.getX() <= this.getX() + this.getWidth() && p.getX() + p.getWidth() >= this.getX() && p.getY() <= this.getY() + this.getHeight() && p.getY() + p.getHeight() >= this.getY()){
            p.hurt(TOUCH_DAMAGE);
        }
    }
}
