package com.DanyFids.Model.Powerups;

import com.DanyFids.Model.Entity;
import com.DanyFids.Model.Powerup;

import java.util.LinkedList;

public class DropTable {
    private int coins;

    public DropTable(int coins){
        this.coins = coins;
    }

    public Powerup[] generateDrops(Entity origin){
        LinkedList<Powerup> hold = new LinkedList<Powerup>();
        int spawnX = origin.getX() + (origin.WIDTH / 2);
        int spawnY = origin.getY();

        for(int c = 0; c < coins; c++){
            hold.add(new Coin(spawnX, spawnY));
        }

        Powerup[] array = new Powerup[hold.size()];

        for(int i = 0; i < hold.size(); i++){
            array[i] = hold.get(i);
        }

        return array;
    }
}
