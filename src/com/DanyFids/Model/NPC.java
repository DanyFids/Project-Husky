package com.DanyFids.Model;

import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Daniel on 12/30/2017.
 */
public abstract class NPC extends Entity {
    private static SpriteSheet DISPLAY_PROMPT = new SpriteSheet("/test_key_prompt.png");
    private final int FADE_TIME = 10;
    public final int DETECT_PADDING = 5;

    private boolean willInteract = false;

    private int fadeCount = 0;

    @Override
    public void land() {

    }

    public abstract void AI();

    public abstract void update();

    public abstract void move();

    public abstract NPC copy();

    public abstract void hitDetect(Player p);

    public abstract void interact(Player p, Vector<Powerup> powerups, Vector<Enemy> enemies);

    public abstract void hitDetect(Entity e);

    public boolean isWillInteract(){return willInteract;}

    public void setWillInteract(boolean b){willInteract = b;}

    public void displayPrompt(BufferedImage screen, int offsetX, int offsetY){
        Graphics2D g = screen.createGraphics();

        if(fadeCount < FADE_TIME && willInteract){
            fadeCount++;
        }

        float alpha =((float)fadeCount/(float)FADE_TIME);
        //System.out.println(alpha);

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);

        g.setComposite(ac);

        g.drawImage(DISPLAY_PROMPT.getPage().getSubimage(0,0,19,19), this.getX() + (this.getWidth()/2 - 9) - offsetX, this.getY() - 35 - offsetY, null);
    }

    public void hidePrompt(){
        if(fadeCount > 0){
            fadeCount--;
        }
    }

    public int getFadeCount(){
        return fadeCount;
    }
}
