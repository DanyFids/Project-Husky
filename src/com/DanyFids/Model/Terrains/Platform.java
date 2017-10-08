package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Entity;
import com.DanyFids.Model.Terrain;

/**
 * Created by Daniel on 10/3/2017.
 */
public class Platform extends Terrain{

    public Platform (int x, int y, String path){
        super(x,y,path);
    }

    @Override
    public void land() {

    }

    @Override
    public void hitDetect(Entity e){
        if(e.getY() + e.getHeight() + e.ySpeed > this.getY() && e.getY() + e.getHeight() <= this.getY()
                && (e.getX() + e.WIDTH > this.getX() && e.getX() < this.getX() + this.getWidth())){
            while(((e.getY() + e.ySpeed > this.getY() && e.getY() + e.ySpeed < (this.getY() + this.getHeight()))
                    || ((e.getY() + e.HEIGHT) + e.ySpeed > this.getY() && (e.getY() + e.HEIGHT) + e.ySpeed < (this.getY() + this.getHeight())))){
                if(e.ySpeed > 0){
                    e.ySpeed -= 0.1;
                    e.land();
                }else{
                    e.ySpeed += 0.1;
                }
            }
        }

        if(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth() &&
                e.getY() + e.HEIGHT + e.ySpeed > this.getY() && e.getY() + e.getHeight() <= this.getY()){
            while(e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth() &&
                    e.getY() + e.HEIGHT + e.ySpeed > this.getY() && e.getY() + e.ySpeed < this.getY() + this.getHeight()){
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
}
