package com.DanyFids.Model;

import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Daniel on 5/26/2017.
 */
public abstract class Entity {
    int x;
    int y;

    public float xSpeed = 0;
    public float ySpeed = 0;

    public int yMove = 0;
    
    int animation = 0;
    int frame = 0;

    public boolean onRamp = false;
    public boolean stuckSliding = false;
    public boolean onWall = false;
    public boolean face_right = true;
    public boolean onPlatform = false;
    public boolean onLadder = false;

    public boolean isPlayer = false;

    public State state = State.idle;

    private SpawnPoint respawn;

    public Direction dir = Direction.right;

    public int HEIGHT;
    public int WIDTH;

    public SpriteSheet sprt;

    public abstract void land();

    public void wallSlide(){}

    public void endSlide(){}

    public void draw(BufferedImage screen, int offsetX, int offsetY){
        Graphics g = screen.getGraphics();

        g.drawImage(sprt.getPage(), this.x - offsetX, this.y - offsetY, null);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getDashTime(){ return 0;}

    public void setDashTime(int time){}

    public void ladderClimbDown(){}

    public void ladderClimbUp(int ladderY){}
}
