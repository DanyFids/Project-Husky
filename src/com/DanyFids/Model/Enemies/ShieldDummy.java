package com.DanyFids.Model.Enemies;

import com.DanyFids.Model.Direction;
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
    //private Direction dir = Direction.left;

    public ShieldDummy(int x, int y, Direction d){
        this.setX(x);
        this.setY(y);

        this.origin_x = x;

        hp = START_HP;

        this.HEIGHT = _HEIGHT;
        this.WIDTH = _WIDTH;
        sprt = new SpriteSheet("/test_shield_dummy.png");

        dir = d;
    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY) {
        Graphics g = screen.getGraphics();

        switch(dir){
            case left:
                g.drawImage(sprt.getPage(), this.getX() - offsetX, this.getY() - offsetY, null);
                break;
            case right:
                g.drawImage(sprt.getPage(), this.getX() + this.getWidth() - offsetX, this.getY() - offsetY, -this.getWidth(), this.getHeight(), null);
                break;

            default:
                break;
        }
    }

    @Override
    public void AI() {
        if(this.can_move) {
            if (this.getX() != this.origin_x) {
                if (this.getX() > this.origin_x) {
                    this.xSpeed = -MOV_SPD;
                    this.dir = Direction.left;
                } else {
                    this.xSpeed = MOV_SPD;
                    this.dir = Direction.right;
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
        if(!Direction.opposing(dir, d)) {
            if (invuln_timer == 0) {
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
        }else{
            if(d == Direction.right){
                xSpeed = Physics.KNOCK_BACK;
            }else{
                xSpeed = -Physics.KNOCK_BACK;
            }
        }
    }

    @Override
    public boolean hitShield(Direction d){
        return Direction.opposing(dir, d);
    }


    @Override
    public void land() {
        this.can_move = true;
    }

    @Override
    public void kill(LinkedList<Enemy> enemies, int id){
        enemies.remove(id);

        enemies.add(new ShieldDummy(this.origin_x, 0, Direction.left));
    }

    @Override
    public void hitDetect(Player p){
        if(p.getX() <= this.getX() + this.getWidth() && p.getX() + p.getWidth() >= this.getX() && p.getY() <= this.getY() + this.getHeight() && p.getY() + p.getHeight() >= this.getY()){
            p.hurt(TOUCH_DAMAGE, Direction.nil);
        }
    }

    public Enemy copy(){
        return new ShieldDummy(this.getX(), this.getY(), this.dir);
    }
}
