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

    public int HEIGHT;
    public int WIDTH;

    public SpriteSheet sprt;

    public abstract void land();

    public void draw(BufferedImage screen){
        Graphics g = screen.getGraphics();

        g.drawImage(sprt.getPage(), this.x, this.y, null);
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
}
