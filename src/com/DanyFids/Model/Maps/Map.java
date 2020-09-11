package com.DanyFids.Model.Maps;

import com.DanyFids.Game;
import com.DanyFids.Model.Enemy;
import com.DanyFids.Model.NPC;
import com.DanyFids.Model.Powerup;
import com.DanyFids.Model.Terrain;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Daniel on 10/16/2017.
 */
public abstract class Map {
    public static final int SCRN_HEIGHT = Game.HEIGHT;
    public static final int SCRN_WIDTH = Game.WIDTH;

    private int HEIGHT;
    private int WIDTH;

    private int offsetX = 0;
    private int offsetY = 0;

    private Terrain[] terrain;
    private Terrain[] foreground;
    private NullZone[] nullzones;
    private NPC[] NPCs;
    public Vector<Enemy> enemies = new Vector<>();
    public Vector<Powerup> powerups = new Vector<>();

    public int P_START_X = 0;
    public int P_START_Y = 0;

    public Map(int w, int h, Terrain[] t, Terrain[] f, NullZone[] n, NPC[] npc){
        if(w > SCRN_WIDTH){
            WIDTH = w;
        }else{
            WIDTH = SCRN_WIDTH;
        }

        if(h > SCRN_HEIGHT){
            HEIGHT = h;
        }else{
            HEIGHT = SCRN_HEIGHT;
        }

        terrain = t;
        foreground = f;
        nullzones = n;
        NPCs = npc;
    }

    public void updateOffsetX(int x){
        if(x > 0) {
            if(x + SCRN_WIDTH < WIDTH) {
                boolean hits_nullzone = false;
                for(int n = 0; n < nullzones.length; n++) {
                    if(nullzones[n].hitsNullzone(x, offsetY)){
                        hits_nullzone = true;
                    }
                }

                if(!hits_nullzone){
                    offsetX = x;
                }
            }else{
                offsetX = WIDTH - SCRN_WIDTH;
            }
        }else{
            offsetX = 0;
        }
    }

    public void updateOffsetY(int y){
        if(y > 0) {
            if(y + SCRN_HEIGHT < HEIGHT) {
                boolean hits_nullzone = false;
                for(int n = 0; n < nullzones.length; n++) {
                    if(nullzones[n].hitsNullzone(offsetX, y)){
                        hits_nullzone = true;
                    }
                }

                if(!hits_nullzone){
                    offsetY = y;
                }
            }else{
                offsetY = HEIGHT - SCRN_HEIGHT;
            }
        }else{
            offsetY = 0;
        }
    }

    public Terrain[] getTerrain(){
        return this.terrain;
    }

    public Terrain[] getForeground(){return this.foreground;}

    public Vector<Enemy> getEnemies(){
    	Vector<Enemy> e = new Vector<>();

        for(int i = 0; i < enemies.size(); i++){
            e.add(enemies.get(i).copy());
        }

        return e;
    }

    public Vector<Powerup> getPowerups(){
        Vector<Powerup> p = new Vector<>();

        for(int i =0; i < powerups.size(); i++){
            p.add(powerups.get(i).copy());
        }

        return p;
    }

    public NPC[] getNPCs(){
        NPC[] npcs = new NPC[NPCs.length];
        for(int n = 0; n < NPCs.length; n++){
            npcs[n] = NPCs[n].copy();
        }

        return npcs;
    }

    public int getOffsetX(){
        return offsetX;
    }

    public int getOffsetY(){
        return offsetY;
    }

    public int getHEIGHT(){
        return HEIGHT;
    }

    public int getWIDTH(){
        return WIDTH;
    }

}
