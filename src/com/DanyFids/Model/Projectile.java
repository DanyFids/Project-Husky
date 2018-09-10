package com.DanyFids.Model;

import com.DanyFids.Model.Weapons.WpnType;

import java.util.LinkedList;

/**
 * Created by Daniel on 10/9/2017.
 */
public abstract class Projectile extends Entity {
    public int offset;
    private Direction dir;
    private WpnType type;
    private int dmg;
    private int SPEED;
    private int despawnTimer = 60;

    public Projectile(int x, int y, int offset, Direction dir, int dmg, WpnType t, int spd){
        this.setX(x);
        this.setY(y);
        this.offset = offset;
        this.dir = dir;
        this.dmg = dmg;
        this.type = t;
        this.SPEED = spd;
    }

    @Override
    public void land() {

    }

    public void update() {
        if(offset > 0){
            offset--;
        }else{
            switch(this.getDir()){
                case right:
                    this.xSpeed = SPEED;
                    break;
                case left:
                    this.xSpeed = - SPEED;
                    break;
                case up:
                    this.ySpeed = - SPEED;
                    break;
                case down:
                    this.ySpeed = SPEED;
                    break;
            }

            switch(this.getDir()){
                case right:
                case left:
                    if(this.ySpeed != 0){
                        this.xSpeed = 0;
                    }
                    break;
                case up:
                case down:
                    if(this.xSpeed != 0){
                        this.ySpeed = 0;
                    }
                    break;
            }
        }
    }

    public void move(){
        if(this.offset <= 0){

            if(xSpeed < 0.1 && xSpeed > -0.1){
                xSpeed = 0;
            }

            if(ySpeed < 0.1 && ySpeed > -0.1){
                ySpeed = 0;
            }

            /*if(this.xSpeed == 0 && this.ySpeed == 0){
                despawnTimer--;
            }*/
            //System.out.println(this.ySpeed);

            switch(dir){
                case right:
                case left:
                    if(this.xSpeed == 0){
                        despawnTimer--;
                    }
                    this.x += this.xSpeed;
                    break;
                case up:
                case down:
                    if(this.ySpeed == 0){
                        despawnTimer--;
                    }
                    this.y += this.ySpeed;
            }
        }
    }

    public void hitDetect(Enemy e, LinkedList<Projectile> ps, int p){
        switch(dir) {
            case left:
            case right:
                if (e.getX() + e.WIDTH > this.getX() && e.getX() < this.getX() + this.getWidth()
                        && (e.getY() + e.HEIGHT > this.getY() && e.getY() < this.getY() + this.getHeight())) {
                    e.hitShield(dir);
                    e.hurt(this.dmg, dir);
                    ps.remove(p);
                }
                break;
            case up:
            case down:
                if(e.getY() + e.HEIGHT > this.getY() && e.getY() + e.ySpeed < this.getY() + this.getHeight()
                        && (e.getX() + e.WIDTH > this.getX() && e.getX() < this.getX() + this.getWidth())){
                    e.hitShield(dir);
                    e.hurt(this.dmg, dir);
                    ps.remove(p);
                }
        }
    }

    public void prime(int x, int y, Direction d){
        this.setX(x);
        this.setY(y);

        dir = d;
    }

    public Direction getDir(){
        return dir;
    }

    public boolean despawn(){
        return this.despawnTimer < 0;
    }
}
