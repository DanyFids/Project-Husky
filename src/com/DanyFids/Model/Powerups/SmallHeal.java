package com.DanyFids.Model.Powerups;

import com.DanyFids.Model.Player;
import com.DanyFids.Model.Powerup;

/**
 * Created by Daniel on 3/18/2018.
 */
public class SmallHeal extends Powerup {
    public SmallHeal(int x, int y, int w, int h) {
        super(x, y, w, h, "");
    }

    @Override
    public void effect(Player p) {
        p.heal(4);
    }

    @Override
    public Powerup copy() {
        return null;
    }
}
