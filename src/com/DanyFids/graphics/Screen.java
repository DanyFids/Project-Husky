package com.DanyFids.graphics;

/**
 * Created by Daniel on 5/26/2017.
 */
public class Screen {
    public static final int MAP_WIDTH=64;
    public static final int MAP_WIDTH_MASK=64;

    public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH];
    public int[] colours = new int[MAP_WIDTH * MAP_WIDTH * 4];
}
