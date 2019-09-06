package com.DanyFids.Model.Powerups;

import com.DanyFids.Model.Player;
import com.DanyFids.Model.Powerup;
import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Daniel on 4/27/2018.
 */
public class Coin extends Wealth{
    private static final int VALUE = 1;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 10;
    private final int ANIM_FRAMES = 12;
    private final int ANIM_TIME = 48;

    private int animcounter = 0;

    public Coin(int x, int y) {
        super(VALUE, x, y, WIDTH, HEIGHT, "/coin.png");

        animcounter = (new Random()).nextInt(ANIM_TIME);
    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY) {
        Graphics g = screen.getGraphics();

        int frametime = ANIM_TIME/ANIM_FRAMES;

        int frame = animcounter/frametime;
        //System.out.println(frame);
        g.drawImage(sprt.getPage().getSubimage(18*frame,0,18,20), this.getX() - offsetX, this.getY() - offsetY, WIDTH, HEIGHT,null);

        animcounter++;

        if(animcounter >= ANIM_TIME){
            animcounter = 0;
        }
    }

    @Override
    public Powerup copy() {
        return new Coin(this.getX(), this.getY());
    }
}
