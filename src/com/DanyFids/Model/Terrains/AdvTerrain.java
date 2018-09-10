package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Terrain;

/**
 * Created by Daniel on 12/26/2017.
 */
public abstract class AdvTerrain extends Terrain {

    public AdvTerrain(int x, int y, String path) {
        super(x, y, path);
    }

    public abstract void update();
}
