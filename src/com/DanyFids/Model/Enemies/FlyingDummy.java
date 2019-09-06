package com.DanyFids.Model.Enemies;

import com.DanyFids.Model.*;
import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by Daniel on 10/2/2017.
 */
public class FlyingDummy extends Enemy{

    private final int _WIDTH = 64;
    private final int _HEIGHT = 32;
    private final int MOV_SPEED = 2;
    private final int ANIM_TIME = 16;
    private final int START_HP = 4;

    private int start_y;
    private int start_x;
    private int dest_x;

    private boolean to_right;
    private boolean start_left;
    private boolean stunned = false;

    private int anim_timer = 6;


    public FlyingDummy(int x, int y, int dest){
        this.WIDTH = _WIDTH;
        this.HEIGHT = _HEIGHT;

        this.setY(y);
        this.setX(x);

        this.start_x = x;
        this.start_y = y;
        this.dest_x = dest;

        if(x > dest){
            to_right = false;
            start_left = false;
            //this.xSpeed = -MOV_SPEED;
        }else{
            to_right = true;
            start_left = true;
            //this.xSpeed = MOV_SPEED;
        }

        this.setHp(START_HP);
        this.sprt = new SpriteSheet("/flying_dummy.png");
    }

    @Override
    public void AI() {
        if (!stunned) {
            if (this.getY() != this.start_y) {
                if (this.getY() > this.start_y) {
                    this.ySpeed = -(this.MOV_SPEED);
                    while(this.getY() + this.ySpeed < this.start_y){
                        this.ySpeed += 1;
                    }
                } else {
                    this.ySpeed = this.MOV_SPEED;
                    while(this.getY() + this.ySpeed > this.start_y){
                        this.ySpeed -= 1;
                    }
                }
            } else {
                this.ySpeed = 0;
            }

            if (to_right) {
                if ((start_left && this.getX() >= this.dest_x) || (!start_left && this.getX() <= this.start_x)) {
                    //System.out.println("HOI!");
                    this.to_right = false;
                }else{
                    this.xSpeed = this.MOV_SPEED;
                }
            } else {
                if ((start_left && this.getX() <= this.start_x) || (!start_left && this.getX() >= this.dest_x)) {
                    this.to_right = true;
                }else{
                    this.xSpeed = -this.MOV_SPEED;
                }
            }
        }
    }

    @Override
    public void update() {
        if(anim_timer <= 0){
            anim_timer = ANIM_TIME;
        }

        if(stunned){
            ySpeed += Physics.GRAVITY;
        }else{
            ySpeed = 0;
        }

        anim_timer--;

        if(invuln_timer <= 0){
            stunned = false;
            /*
            if(to_right){
                xSpeed = MOV_SPEED;
            }else{
                xSpeed = - MOV_SPEED;
            }*/
        }else{
            invuln_timer--;
        }

        AI();
    }

    @Override
    public void hurt(int dmg, Direction d) {
        if (invuln_timer <= 0) {
            hp -= dmg;
            ySpeed = -Physics.KNOCK_BACK;
            if (d == Direction.right) {
                xSpeed = Physics.KNOCK_BACK;
            } else if(d == Direction.left){
                xSpeed = -Physics.KNOCK_BACK;
            }

            invuln_timer = INVULN_TIME;
            stunned = true;

            //this.to_right = to_right;
        }
    }

    @Override
    public void kill(LinkedList<Enemy> enemies, LinkedList<Powerup> powerups, int id) {
        enemies.remove(id);

        enemies.add(new FlyingDummy(start_x, start_y, dest_x));
    }

    @Override
    public boolean hitShield(Direction d) {
        return false;
    }

    @Override
    public void land() {
        // Flying Creature Does not land;
    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY){
        Graphics g = screen.getGraphics();

        int frame = (anim_timer / (ANIM_TIME/2));
        //System.out.println(frame);

        g.drawImage(this.sprt.getPage().getSubimage((this.getWidth() + 1) * frame,0, this.getWidth(), this.getHeight()), this.getX() - offsetX, this.getY() - offsetY,null );

    }

    @Override
    public void hitDetect(Player p){

    }

    public Enemy copy(){
        return new FlyingDummy(start_x, start_y, dest_x);
    }
}
