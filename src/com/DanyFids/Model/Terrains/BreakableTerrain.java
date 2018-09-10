package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.*;
import com.DanyFids.graphics.SpriteSheet;

/**
 * Created by Daniel on 3/9/2018.
 */
public class BreakableTerrain extends Enemy{
    private int START_HP;
    private String SPRT_PATH;

    public BreakableTerrain(int x, int y, int w, int h, int hp, String path){
        this.setX(x);
        this.setY(y);
        this.HEIGHT =  h;
        this.WIDTH = w;
        this.START_HP = hp;
        this.hp = hp;

        this.SPRT_PATH = path;
        this.sprt = new SpriteSheet(path);
    }

    @Override
    public void AI() {}

    @Override
    public void update() {
        if(invuln_timer > 0){
            invuln_timer--;
        }
    }

    @Override
    public void hurt(int dmg, Direction d) {
        if(invuln_timer <= 0) {
            this.hp -= dmg;
            primeInvuln();
        }
    }

    @Override
    public boolean hitShield(Direction d) {
        return false;
    }

    @Override
    public void hitDetect(Player e) {
        if(((e.getY() + e.ySpeed < this.getY() + this.getHeight() && e.state != State.slide) || (e.getY() + (e.HEIGHT - 32) + e.ySpeed < this.getY() + this.getHeight() && e.state == State.slide)) &&
                e.getY() + e.getHeight() + e.ySpeed > this.getY() && e.getX() < this.getX() + this.getWidth() && e.getX() + e.getWidth() > this.getX()){
            while(((e.getY() + e.ySpeed < this.getY() + this.getHeight() && e.state != State.slide) || (e.getY() + (e.HEIGHT - 32) + e.ySpeed < this.getY() + this.getHeight() && e.state == State.slide)) &&
                    e.getY() + e.getHeight() + e.ySpeed > this.getY() && e.getX() < this.getX() + this.getWidth() && e.getX() + e.getWidth() > this.getX()){
                if(e.ySpeed > 0){
                    e.ySpeed -= 0.1;
                    e.land();
                }else{
                    e.ySpeed += 0.1;
                }
            }
        }

        if(e.state != State.slide){
            if(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth()
                    && (e.getY() + e.HEIGHT > this.getY() && e.getY() < this.getY() + this.getHeight())){
                while(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth()){
                    if(e.xSpeed > 0){
                        e.xSpeed -= 0.1;
                    }else{
                        e.xSpeed += 0.1;
                    }
                }
                e.wallSlide();
                e.onWall = true;
            }
        }else{
            if(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth()
                    && (e.getY() + e.HEIGHT > this.getY() && e.getY() + (e.HEIGHT - 32) < this.getY() + this.getHeight())){
                while(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth()){
                    if(e.xSpeed > 0){
                        e.xSpeed -= 0.1;
                    }else{
                        e.xSpeed += 0.1;
                    }
                }
                e.endSlide();
            }

            if(e.face_right) {
                if (e.getX() < this.getX() + this.getWidth() && e.getX() + e.getHeight() > this.getX() && e.getY() > this.getY() && e.getY() < this.getY() + this.getHeight()) {
                    e.stuckSliding = true;
                }
            }else{
                if (e.getX() < this.getX() + this.getWidth() && e.getX() + e.getHeight() > this.getX() && e.getY() > this.getY() && e.getY() < this.getY() + this.getHeight()) {
                    e.stuckSliding = true;
                }
            }
        }

        if(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth() &&
                e.getY() + e.HEIGHT + e.ySpeed > this.getY() && ((e.getY() + e.ySpeed < this.getY() + this.getHeight() && e.state != State.slide) ||
                (e.getY() + (e.HEIGHT - 32) + e.ySpeed < this.getY() + this.getHeight() && e.state == State.slide))){
            while(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth() &&
                    e.getY() + e.HEIGHT + e.ySpeed > this.getY() && ((e.getY() + e.ySpeed < this.getY() + this.getHeight() && e.state != State.slide) ||
                    (e.getY() + (e.HEIGHT - 32) + e.ySpeed < this.getY() + this.getHeight() && e.state == State.slide))){
                if(e.xSpeed > 0){
                    e.xSpeed -= 0.1;
                }else{
                    e.xSpeed += 0.1;
                }

                if(e.ySpeed > 0){
                    e.ySpeed -= 0.1;
                    e.land();
                }else{
                    e.ySpeed += 0.1;
                }
            }

        }


        /*
        if(p.getX() + p.xSpeed < this.getX() + this.getWidth() && p.getX() + p.getWidth() + p.xSpeed > this.getX() &&
                p.getY() +  p.getHeight() > this.getY() && p.getY() < this.getY() + this.getHeight()){
            if(p.xSpeed > 0){
                p.xSpeed = this.getX() - (p.getX() + p.getWidth());
            }else{
                p.xSpeed = this.getX() + this.getWidth() - p.getX();
            }
        }
        */
    }



    @Override
    public Enemy copy() {
        return new BreakableTerrain(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.START_HP, SPRT_PATH);
    }

    @Override
    public void land() {

    }
}
