package com.DanyFids.Model;

import com.DanyFids.graphics.SpriteSheet;

/**
 * Created by Daniel on 9/30/2017.
 */
public abstract class Terrain extends Entity {

    public Terrain(int x, int y, String path){
        this.setX(x);
        this.setY(y);

        this.sprt = new SpriteSheet(path);
        WIDTH = sprt.getPage().getWidth();
        HEIGHT = sprt.getPage().getHeight();
    }

    public void hitDetect(Entity e){
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
    }

    public void land(){
    }
}
