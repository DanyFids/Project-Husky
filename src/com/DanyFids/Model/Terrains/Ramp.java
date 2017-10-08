package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Entity;
import com.DanyFids.Model.Terrain;

/**
 * Created by Daniel on 10/3/2017.
 */
public class Ramp extends Terrain {

    private int[] ramp_start = new int[2];
    private int[] ramp_end = new int[2];

    private int slope;

    public Ramp (int x, int y, int sx, int sy, int dx, int dy, String path){
        super(x, y, path);

        if(sx > dy){
            ramp_start[0] = dx;
            ramp_start[1] = dy;

            ramp_end[0] = sx;
            ramp_end[1] = sy;
        }else {
            ramp_start[0] = sx;
            ramp_start[1] = sy;

            ramp_end[0] = dx;
            ramp_end[1] = dy;
        }

        slope = (ramp_end[1] - ramp_start[1])/(ramp_end[0] - ramp_start[0]);
    }


    @Override
    public void hitDetect(Entity e){
        if(e.getY() + e.getHeight() == (this.getY() + ((e.getX() - (this.getX() + this.ramp_start[0])) * slope)) - 1){
            System.out.println("HOI3!!!");
            if(xSpeed > 0){
                e.ySpeed = slope * this.xSpeed;
            }else if(xSpeed < 0){
                e.ySpeed = - slope * this.xSpeed;
            }
        }else {
            if (e.getY() + e.HEIGHT + e.ySpeed > (this.getY() + ((e.getX() - (this.getX() + this.ramp_start[0])) * slope)) && e.getY() + e.ySpeed < this.getY() + this.getHeight()
                    && (e.getX() + e.WIDTH > this.getX() && e.getX() < this.getX() + this.getWidth())) {
                while (e.getY() + e.HEIGHT + e.ySpeed > (this.getY() + ((e.getX() - (this.getX() + this.ramp_start[0])) * slope)) && e.getY() + e.ySpeed < this.getY() + this.getHeight()
                        && (e.getX() + e.WIDTH > this.getX() && e.getX() < this.getX() + this.getWidth())) {
                    if (e.ySpeed > 0) {
                        e.ySpeed -= 0.1;
                        e.land();
                    } else {
                        e.ySpeed += 0.1;
                    }
                }
            }

            if (e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < (this.getX() + (((e.getY() + e.getHeight()) + (this.getY() + this.ramp_start[1])) * slope))
                    && (e.getY() + e.HEIGHT > (this.getY() + ((e.getX() - (this.getX() + this.ramp_start[0])) * slope)) && e.getY() < this.getY() + this.getHeight())) {
                while (e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < (this.getX() + (((e.getY() + e.getHeight()) + (this.getY() + this.ramp_start[1])) * slope))) {
                    System.out.println("HOI1!!!");
                    if (e.xSpeed > 0) {
                        e.xSpeed -= 0.1;
                    } else {
                        e.xSpeed += 0.1;
                    }
                }
            }

            if (e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth() &&
                    e.getY() + e.HEIGHT + e.ySpeed > (this.getY() + ((e.getX() - (this.getX() + this.ramp_start[0])) * slope)) && e.getY() + e.ySpeed < this.getY() + this.getHeight()) {
                while (e.getX() + e.WIDTH + e.xSpeed > this.getX() && e.getX() + e.xSpeed < this.getX() + this.getWidth() &&
                        e.getY() + e.HEIGHT + e.ySpeed > (this.getY() + ((e.getX() - (this.getX() + this.ramp_start[0])) * slope)) && e.getY() + e.ySpeed < this.getY() + this.getHeight()) {
                    //System.out.println("HOI!!!");
                    if (e.xSpeed > 0) {
                        e.xSpeed -= 0.1;
                    } else {
                        e.xSpeed += 0.1;
                    }

                    if (e.ySpeed > 0) {
                        e.ySpeed -= 0.1;
                        e.land();
                    } else {
                        e.ySpeed += 0.1;
                    }
                }
            }
        }
    }

}
