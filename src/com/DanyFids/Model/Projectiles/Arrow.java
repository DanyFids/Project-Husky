package com.DanyFids.Model.Projectiles;

import com.DanyFids.Model.Direction;
import com.DanyFids.Model.Projectile;
import com.DanyFids.Model.Weapons.WpnType;
import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Daniel on 10/9/2017.
 */
public class Arrow extends Projectile {
    private static final int SPEED = 6;
    private static final int DAMAGE = 1;
    private static final WpnType TYPE = WpnType.Piercing;

    public static final int _WIDTH = 32;
    public static final int _HEIGHT = 7;

    public Arrow(int x, int y, int offset, Direction dir) {
        super(x, y, offset, dir, DAMAGE, TYPE, SPEED);

        this.sprt = new SpriteSheet("/test_arrow.png");


        switch(this.getDir()) {
            case right:
            case left:
                this.WIDTH = _WIDTH;
                this.HEIGHT = _HEIGHT;
                break;
            case down:
            case up:
                this.WIDTH = _HEIGHT;
                this.HEIGHT = _WIDTH;
                break;
        }
    }

    public void draw(BufferedImage screen, int offsetX, int offsetY){
        Graphics g = screen.getGraphics();

        if(this.offset <= 0){
            switch(this.getDir()){
                case right:
                    g.drawImage(this.sprt.getSprite(0,0, _WIDTH,_HEIGHT ), this.getX() - offsetX, this.getY() - offsetY, this.WIDTH, this.HEIGHT, null);
                    break;
                case left:
                    g.drawImage(this.sprt.getSprite(0,0, _WIDTH, _HEIGHT), this.getX() + this.getWidth() - offsetX, this.getY() - offsetY, -this.WIDTH, this.HEIGHT, null);
                    break;
                case up:
                    g.drawImage(this.sprt.getSprite(0,8, _HEIGHT, _WIDTH), this.getX() - offsetX, this.getY() - offsetY, null);
                    break;
                case down:
                    g.drawImage(this.sprt.getSprite(0,8, _HEIGHT, _WIDTH), this.getX() - offsetX, this.getY() - offsetY  + this.getHeight(), this.getWidth(), -this.getHeight(),  null);
                    break;
            }
        }
    }

}
