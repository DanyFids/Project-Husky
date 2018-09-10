package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Entity;
import com.DanyFids.Model.Terrain;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Daniel on 3/8/2018.
 */
public class HiddenPath extends Terrain{
    private final int FADE_TIME = 20;

    private boolean reveal = false;
    private int fade_counter = 0;

    public HiddenPath(int x, int y, String path) {
        super(x, y, path);
    }

    @Override
    public void hitDetect(Entity e) {
        if(e.isPlayer){
            if(e.getX() < this.getX() + this.getWidth() && e.getX() + e.getWidth() > this.getX() &&
                    e.getY() < this.getY() + this.getHeight() && e.getY() + e.getHeight() > this.getY()){
                if(reveal){
                    if(fade_counter > 0){
                        fade_counter--;
                    }
                }else{
                    reveal =true;
                    fade_counter = FADE_TIME - fade_counter;
                }
            }else{
                if(reveal) {
                    reveal = false;
                    fade_counter = FADE_TIME - fade_counter;
                }

                if(fade_counter > 0){
                    fade_counter--;
                }
            }
        }
    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY) {
        if(reveal){
            Graphics2D g = screen.createGraphics();
            float alpha = ((float)fade_counter)/((float)FADE_TIME);

            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

            g.setComposite(ac);
            g.drawImage(sprt.getPage(), this.getX() - offsetX, this.getY() - offsetY,null);
        }else {
            Graphics2D g = screen.createGraphics();
            float alpha = 1f - (((float)fade_counter)/((float)FADE_TIME));

            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

            g.setComposite(ac);
            g.drawImage(sprt.getPage(), this.getX() - offsetX, this.getY() - offsetY,null);
        }
    }
}
