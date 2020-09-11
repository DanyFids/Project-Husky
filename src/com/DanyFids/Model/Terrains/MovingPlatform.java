package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.*;
import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Daniel on 12/26/2017.
 */
public class MovingPlatform extends NPC {
    private final int SPEED = 2;

    private LinkedList<Point> points;
    private Point dest;
    private int currentId;
    private String spritePath;

    private boolean forward;
    private boolean stop = false;

    public MovingPlatform(int x, int y, String sprite, Point[] path){
        this.setX(x);
        this.setY(y);

        spritePath = sprite;

        points = new LinkedList<Point>();

        points.add(new Point(x,y));

        for(int i = 0; i < path.length; i++){
            points.add(path[i]);
        }

        currentId = 0;

        if(points.size() > 1) {
            dest = points.get(1);
        }else{
            dest = points.get(0);
        }

        this.sprt = new SpriteSheet(sprite);

        this.WIDTH = sprt.getPage().getWidth();
        this.HEIGHT = sprt.getPage().getHeight();

        forward = true;
    }

    @Override
    public void hitDetect(Entity e){
        hitResult(e);
    }

    @Override
    public void land() {

    }

    @Override
    public void AI() {
        int pathX = dest.x - this.getX();
        int pathY = dest.y - this.getY();

        if(pathX == 0 && pathY == 0){
            nextPoint();
        }else{
            if(pathX > 0){
                this.xSpeed = SPEED;
            }else if(pathX < 0){
                this.xSpeed = -SPEED;
            }
            if(pathY > 0){
                this.ySpeed = SPEED;
            }else if(pathY < 0){
                this.ySpeed = -SPEED;
            }
        }


    }

    @Override
    public void update() {
        this.xSpeed = 0;
        this.ySpeed = 0;

        AI();
    }

    @Override
    public void move(){
        this.setX((int) (this.getX() + this.xSpeed));
        this.setY((int) (this.getY() + this.ySpeed));
    }

    @Override
    public NPC copy() {
        Point start = this.points.getFirst();

        int x = start.x;
        int y = start.y;

        Point[] path = new Point[this.points.size() - 1];
        for( int i=1; i < points.size(); i++){
            path[i-1] = points.get(i);
        }

        return new MovingPlatform(x, y, this.spritePath, path);
    }

    @Override
    public void hitDetect(Player p) {
        hitResult(p);
    }

    @Override
    public void interact(Player p, Vector<Powerup> powerups, Vector<Enemy> enemies) {

    }

    private void hitResult(Entity e){
        boolean landed = false;

        int eFoot = e.getY() + e.getHeight();

        /*while((eFoot < this.getY() && eFoot + e.ySpeed >= this.getY() + this.ySpeed) &&(e.getX() < this.getX() + this.getWidth() && e.getX() + e.getWidth() > this.getX())){
            e.ySpeed = eFoot
        }*/


        if((eFoot + e.ySpeed > this.getY() + this.ySpeed || eFoot + e.ySpeed > this.getY()) && e.getY() + e.getHeight() <= this.getY()
                && (e.getX() + e.WIDTH > this.getX() && e.getX() < this.getX() + this.getWidth())){
            e.ySpeed = this.getY() - eFoot;
            e.land();
            landed = true;
            e.onPlatform = true;
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

        if(landed){
            e.ySpeed += this.ySpeed;
            e.xSpeed += this.xSpeed;
        }
    }

    private void nextPoint(){
        if(forward){
            currentId++;
            if(currentId >= points.size() -1){
                forward = false;
                dest = points.get(currentId - 1);
            }else{
                dest = points.get(currentId + 1);
            }
        }else{
            currentId--;
            if(currentId <= 0){
                forward = true;
                dest = points.get(currentId + 1);
            }else{
                dest = points.get(currentId - 1);
            }
        }
    }
}
