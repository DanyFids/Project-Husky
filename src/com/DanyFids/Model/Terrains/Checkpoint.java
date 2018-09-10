package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Entity;
import com.DanyFids.Model.Player;
import com.DanyFids.Model.SpawnPoint;
import com.DanyFids.Model.Terrain;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Daniel on 11/22/2017.
 */
public class Checkpoint extends Terrain {
    private boolean activated = false;
    private final int FRAMES = 1;
    private final int FRAME_DELAY = 10;
    private int frame = 0;
    private int anim_timer = 0;
    private SpawnPoint point;

    private boolean send =true;

    public Checkpoint(int x, int y) {
        super(x, y, "/test_checkpoint.png");

        super.HEIGHT = 64;
        super.WIDTH = 32;

        point = new SpawnPoint(x,y);
    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY){
        Graphics g = screen.getGraphics();

        if(activated){
            g.drawImage(sprt.getPage().getSubimage((33 * frame), 65, this.WIDTH, this.HEIGHT), this.getX() - offsetX, this.getY() - offsetY, null);

            anim_timer++;
            if(anim_timer == FRAME_DELAY){
                anim_timer = 0;
                frame++;
                if(frame == FRAMES){
                    frame = 0;
                }
            }
        }else {
            g.drawImage(sprt.getPage().getSubimage(0, 0, this.WIDTH, this.HEIGHT), this.getX() - offsetX, this.getY() - offsetY, null);
        }
    }

    @Override
    public void hitDetect(Entity e){
        /*
        if(send){
            send = false;
            hitDetect(e);
        }else{
            send = true;
        }
        */

        if(e.getX() < this.getX() + this.getWidth() && e.getX() + e.getWidth() > this.getX() && e.getY() < this.getY() + this.getHeight() && e.getY() + e.getHeight() > this.getY() && e.isPlayer){
            this.activated = true;
            Player p = (Player) e;

            p.setRespawn(this.point);
        }
    }
}
