package com.DanyFids.Model.NPCs;

import com.DanyFids.Model.*;
import com.DanyFids.Model.Powerups.DropTable;
import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Vector;

public class Chest extends NPC {
    private static int CHEST_WIDTH = 60;
    private static int CHEST_HEIGHT = 30;
    private Powerup[] loot;
    private DropTable dt;

    private boolean opened = false;


    public Chest(int x, int y, DropTable dt) {
        this.setX(x);
        this.setY(y);

        sprt = new SpriteSheet("/test_chest.png");

        this.HEIGHT = CHEST_HEIGHT;
        this.WIDTH = CHEST_WIDTH;

        this.loot = dt.generateDrops(this);
        this.dt = dt;
    }

    @Override
    public void AI() {

    }

    @Override
    public void update() {
        if(isWillInteract()){
            setWillInteract(false);
        }else{
            hidePrompt();
        }
    }

    @Override
    public void move() {

    }

    @Override
    public NPC copy() {
        return new Chest(getX(),getY(),dt);
    }

    @Override
    public void hitDetect(Player p) {
        if(p.checkJump() && !opened){
            if((p.getX() <= this.getX() + this.getWidth() + DETECT_PADDING && p.getX() + p.getWidth() >= this.getX() - DETECT_PADDING)
                    && (p.getY() <= this.getY() + this.getHeight() && p.getY() + p.getHeight() >= this.getY())){
                p.InteractNPC = this;
                setWillInteract(true);
                p.setNextToNPC(true);
            }
        }
    }

    @Override
    public void interact(Player p, Vector<Powerup> powerups, Vector<Enemy> enemies) {
        if(!opened) {
            for (int l = 0; l < loot.length; l++) {
                powerups.add(loot[l]);
            }

            this.opened = true;
        }
    }

    @Override
    public void hitDetect(Entity e) {

    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY) {
        Graphics g = screen.getGraphics();

        if(!opened) {
            g.drawImage(sprt.getPage().getSubimage(0, 0, getWidth(), getHeight()), this.getX() - offsetX, this.getY() - offsetY, null);
            if(isWillInteract() || getFadeCount() > 0){
                displayPrompt(screen, offsetX, offsetY);
            }
        }else{
            g.drawImage(sprt.getPage().getSubimage(0, getHeight()+1, getWidth()+3, getHeight()+2), this.getX() - offsetX - 3, this.getY() - offsetY - 2, null);
        }
    }
}
