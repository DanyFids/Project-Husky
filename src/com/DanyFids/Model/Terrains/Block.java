package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Terrain;
//import com.DanyFids.graphics.SpriteSheet;

/**
 * Created by Daniel on 5/26/2017.
 */
public class Block extends Terrain {
    private int width;
    private int height;

    public Block(int x, int y, String path){
        super(x, y, path);

        width = sprt.getPage().getWidth();
        height = sprt.getPage().getHeight();
    }

    @Override
    public void land() {

    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

}
