package com.DanyFids.Model.Maps;

/**
 * Created by Daniel on 10/16/2017.
 */
public class NullZone {
    private int x;
    private int y;
    private int width;
    private int height;

    public NullZone(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.height = h;
        this.width = w;
    }

    public boolean hitsNullzone(int offsetX, int offsetY){
        return (offsetX < this.x + this.width && offsetX + Map.SCRN_WIDTH > this.x && offsetY < this.y + height && offsetY + Map.SCRN_HEIGHT > this.y);
    }
}
