package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Entity;
import com.DanyFids.Model.Terrain;

/**
 * Created by Daniel on 11/21/2017.
 */
public class Doodad extends Terrain{
    public Doodad(int x, int y, String path) {
        super(x, y, path);
    }

    @Override
    public void hitDetect(Entity e){

    }
}
