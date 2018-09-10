package com.DanyFids.Model.Powerups;

import com.DanyFids.Model.Player;
import com.DanyFids.Model.Powerup;

/**
 * Created by Daniel on 4/27/2018.
 */
public abstract class Wealth extends Powerup{
    int value;

    public Wealth(int v, int x, int y, int w, int h, String path){
        super(x, y, w, h, path);
        this.value = v;
    }

    @Override
    public void effect(Player p){
        p.addGold(value);
    }
}
