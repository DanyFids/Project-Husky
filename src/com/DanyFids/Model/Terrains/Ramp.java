package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Entity;
import com.DanyFids.Model.Terrain;

/**
 * Created by Daniel on 10/3/2017.
 */
public class Ramp extends Terrain {

    private int[] ramp_start = new int[2];
    private int[] ramp_end = new int[2];

    private double slope;

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

        slope = (float)(ramp_end[1] - ramp_start[1])/(float)(ramp_end[0] - ramp_start[0]);
    }


    @Override
    public void hitDetect(Entity e){
        int halfpoint = e.getX() + (e.getWidth()/2);
        int slopeHeight =  (int) ((this.getY() + this.ramp_start[1]) + ((halfpoint- this.getX() - this.ramp_start[0]) * this.slope));
        int aimSlopeHeight = (int) ((this.getY() + this.ramp_start[1]) + ((halfpoint- this.getX() - this.ramp_start[0] + e.xSpeed) * this.slope));
        int slopeRight;
        int slopeLeft;
        int aimSlopeRight;
        int aimSlopeLeft;

        if(slopeHeight < this.getY()){
            slopeHeight = this.getY();
        }

        if(slope > 0) {
            slopeRight = (int) (this.getX() - this.ramp_start[0] - ((e.getY() + e.getHeight() - this.getY()) * slope));
            slopeLeft = aimSlopeLeft = this.getX();
            aimSlopeRight = (int) (this.getX() - ramp_start[0] - ((e.getY() + e.getHeight() + e.ySpeed - this.getY()) * slope));
        }else if(slope < 0){
            slopeLeft = (int) (this.getX() + this.getWidth() + ramp_end[0] + ((e.getY() + e.getHeight() - this.getY()) * slope));
            slopeRight = aimSlopeRight = this.getX()+this.getWidth();
            aimSlopeLeft = (int) (this.getX() + this.getWidth() + ramp_end[0] + ((e.getY() + e.getHeight() - this.getY() + e.ySpeed) * slope));
        }else{
            slopeRight = aimSlopeRight = this.getX()+this.getWidth();
            slopeLeft = aimSlopeLeft = this.getX();
        }
        /*
        System.out.println("----------------------------------------");
        System.out.println("Foot:   " + (e.getY() + e.getHeight()));
        //System.out.println("Aim Height: " + aimSlopeHeight);
        System.out.println("Height: " + slopeHeight);
        System.out.println("halfpoint:   " + halfpoint);
        System.out.println("Slope Right: " + slopeRight);
        System.out.println("Slope Left:  " + slopeLeft);
        System.out.println("XSpeed: " + e.xSpeed);
        System.out.println("YSpeed: " + e.ySpeed);
        */
        if(halfpoint + e.xSpeed > slopeLeft && halfpoint + e.xSpeed < slopeRight && e.getY() < this.getY() + this.getHeight() && e.getY()+e.getHeight() > this.getY()){
            /*
            while(halfpoint + e.xSpeed > slopeLeft && halfpoint + e.xSpeed < slopeRight){
                if(e.xSpeed > 0){
                    e.xSpeed--;
                }else{
                    e.xSpeed++;
                }

                //System.out.println("XSPEED");
                /*
                System.out.println("Foot:   " + (e.getY() + e.getHeight()));
                System.out.println("Height: " + slopeHeight);
                System.out.println("halfpoint:   " + halfpoint);
                System.out.println("Slope Right: " + slopeRight);
                System.out.println("Slope Left:  " + slopeLeft);
                */

            //}
            /*if(xSpeed != 0) {
                e.ySpeed = (int) e.xSpeed * slope;
            }*/
        }

        if(e.getY() + e.getHeight() + e.ySpeed > slopeHeight &&
                e.getY() + e.ySpeed < this.getY() + this.getHeight() &&
                halfpoint < this.getX() + this.getWidth() &&
                halfpoint > this.getX()){
            /*
            while(e.getY() + e.getHeight() + e.ySpeed > slopeHeight &&
                    e.getY() + e.ySpeed < this.getY() + this.getHeight()){
                if(e.ySpeed > 0){
                    e.ySpeed-= 0.1;
                    e.land();
                }else{
                    e.ySpeed+= 0.1;
                }

                System.out.println("YSPEED");
                /*
                System.out.println("Foot:   " + (e.getY() + e.getHeight()));
                System.out.println("Height: " + slopeHeight);
                System.out.println("halfpoint:   " + halfpoint);
                System.out.println("Slope Right: " + slopeRight);
                System.out.println("Slope Left:  " + slopeLeft);
                */
            //}

            if(e.getY() + e.getHeight() <= this.getY() + this.getHeight()){

                e.ySpeed = aimSlopeHeight - (e.getY() + e.getHeight());
                e.land();
                e.onRamp = true;
                /*
                if(e.xSpeed != 0 && e.ySpeed < 0){
                    e.ySpeed = (float) ((int) e.xSpeed * slope);
                    System.out.println("HOI!!!");
                }
                */
                if(e.getY() + e.getHeight() + e.ySpeed > this.getY() + this.getHeight()){
                    e.ySpeed = (this.getY()+this.getHeight()) - (e.getY() + e.getHeight());
                }else if(e.getY()+e.getHeight()+e.ySpeed < this.getY()){
                    e.ySpeed = (this.getY()) - (e.getY()+e.getHeight());
                }

            }else{
                e.ySpeed = (this.getY() + this.getHeight()) - e.getY();
            }
        }else{
            if(e.onRamp){
                //e.onRamp = false;
            }
        }

        /*
        System.out.println("----------------------------------------");
        System.out.println(e.getY() + e.getHeight() + e.ySpeed > slopeHeight);
        System.out.println("Foot:   " + (e.getY() + e.getHeight() + e.ySpeed));
        System.out.println("Height: " + slopeHeight);
        System.out.println(e.getY() + e.ySpeed < this.getY() + this.getHeight());
        System.out.println(halfpoint + e.xSpeed > slopeLeft);
        System.out.println("halfpoint:   " + (halfpoint + e.xSpeed));
        System.out.println("Slope Left:  " + slopeLeft);
        System.out.println(halfpoint + e.xSpeed < slopeRight);
        */
        float ySpeedInc;
        float xSpeedInc;



        if(xSpeed > 0){
            xSpeedInc = (float) -0.1;
        }else if(xSpeed < 0){
            xSpeedInc = (float) 0.1;
        }else{
            xSpeedInc = 0;
        }


        if(     e.getY() + e.getHeight() + e.ySpeed > aimSlopeHeight &&
                e.getY() + e.ySpeed < this.getY() + this.getHeight() &&
                halfpoint + e.xSpeed > aimSlopeLeft &&
                halfpoint + e.xSpeed < aimSlopeRight){
            while(  e.getY() + e.getHeight() + e.ySpeed > aimSlopeHeight &&
                    e.getY() + e.ySpeed < this.getY() + this.getHeight() &&
                    halfpoint + e.xSpeed > aimSlopeLeft &&
                    halfpoint + e.xSpeed < aimSlopeRight){
                if(ySpeed > 0){
                    e.ySpeed = slopeHeight - (e.getY() + e.getHeight());
                }else if(ySpeed < 0){
                    e.ySpeed = (this.getY() + this.getHeight()) - e.getY();
                }else{
                    ySpeedInc = 0;
                }

                e.xSpeed += xSpeedInc;

                System.out.println("BOTH");

                System.out.println("Foot:   " + (e.getY() + e.getHeight()));
                System.out.println("Height: " + aimSlopeHeight);
                System.out.println("halfpoint:   " + halfpoint);
                System.out.println("Slope Right: " + aimSlopeRight);
                System.out.println("Slope Left:  " + aimSlopeLeft);
                System.out.println("xSpeed: " + e.xSpeed);
                System.out.println("ySpeed: " + e.ySpeed);
                System.out.println("xSpeedInc: " + xSpeedInc);
                //System.out.println("ySpeedInc: " + ySpeedInc);

            }
        }

    }

}
