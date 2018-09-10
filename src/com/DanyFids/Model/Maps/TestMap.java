package com.DanyFids.Model.Maps;

import com.DanyFids.Model.Direction;
import com.DanyFids.Model.Enemies.FlyingDummy;
import com.DanyFids.Model.Enemies.ShieldDummy;
import com.DanyFids.Model.Enemy;
import com.DanyFids.Model.NPC;
import com.DanyFids.Model.Powerups.Coin;
import com.DanyFids.Model.Terrain;
import com.DanyFids.Model.Terrains.*;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Daniel on 10/16/2017.
 */
public class TestMap extends Map {
    private static final int _WIDTH = 2300;
    private static final int _HEIGHT = 800;

    private static final Terrain[] _TERRAIN = {
            new Ramp(831, SCRN_HEIGHT-150, 0,50, 100, 0, "/test_ramp4.png"),
            new Ramp(680, SCRN_HEIGHT-100, 0,100,100,0, "/test_ramp2.png"),
            new Ramp(950, SCRN_HEIGHT-150, 20, 1, 120, 50, "/test_ramp5.png"),
            new Block(0, SCRN_HEIGHT-2, "/test_floor.png"),
            new Block(100, SCRN_HEIGHT - 34, "/test_block.png"),
            new Block(132, SCRN_HEIGHT - 34, "/test_block.png"),
            new Platform(250, 270, "/test_platform.png"),
            new Block(800, SCRN_HEIGHT-100, "/test_block2.png"),
            new Block(900, SCRN_HEIGHT-100, "/test_block2.png"),
            new Block(1000, SCRN_HEIGHT-100, "/test_block2.png"),
            new Block(1100, SCRN_HEIGHT-100, "/test_block2.png"),
            new Block(1100, SCRN_HEIGHT, "/test_block2.png"),
            new Block(1100, SCRN_HEIGHT+100, "/test_block2.png"),
            //new Block(1100, SCRN_HEIGHT+200, "/test_block2.png"),
            new Block(1100, SCRN_HEIGHT+300, "/test_block2.png"),
            new Block(1200, _HEIGHT-100, "/test_block2.png"),
            new Block(1300, _HEIGHT-100, "/test_block2.png"),
            new Block(1400, _HEIGHT-100, "/test_block2.png"),
            new Block(1500, _HEIGHT-100, "/test_block2.png"),
            new Block(2000, _HEIGHT-100, "/test_block2.png"),
            new Block(2100, _HEIGHT-100, "/test_block2.png"),
            new Block(1400, 0, "/test_block2.png"),
            new Block(1400, 100, "/test_block2.png"),
            new Block(1400, 200, "/test_block2.png"),
            new Block(1400, 300, "/test_block2.png"),
            new Block(1400, 500, "/test_block2.png"),
            new Block(1400, 568, "/test_block2.png"),
            new Block(1400, 400, "/test_block2.png"),
            new Block(2200, 700, "/test_block2.png"),
            new Block(2200, 600, "/test_block2.png"),
            new Block(2200, 500, "/test_block2.png"),
            new Block(2200, 400, "/test_block2.png"),
            new Block(2168, 515, "/test_block.png"),
            new Block(2098, 515, "/test_block.png"),
            new Block(2066, 515, "/test_block.png"),
            new Block(2034, 515, "/test_block.png"),
            new Block(800, 400, "/test_block2.png"),
            new Block(900, 400, "/test_block2.png"),
            new Block(1000, 400, "/test_block2.png"),
            new Block(800, 400, "/test_block2.png"),
            new Block(800, 500, "/test_block2.png"),
            new Block(800, 600, "/test_block2.png"),
            new Block(800, 700, "/test_block2.png"),
            new Block(900, 700, "/test_block2.png"),
            new Block(1000, 700, "/test_block2.png"),
            new Doodad(1900, _HEIGHT-100, "/test_pitfall.png"),
            new Doodad(1800, _HEIGHT-100, "/test_pitfall.png"),
            new Doodad(1700, _HEIGHT-100, "/test_pitfall.png"),
            new Doodad(1600, _HEIGHT-100, "/test_pitfall.png"),
            new Checkpoint(1300, _HEIGHT-164),
            new Ladder(2130, 515)

            //new Ramp(164, HEIGHT - 34, 0,0, 32, 32, "/test_ramp.png")
    };

    private static final Terrain[] _FOREGROUND = {
            new HiddenPath(900, 500, "/test_hiddenPath.png")
    };

    private static final NullZone[] _NULLZONE = {
            new NullZone(0,400, 900, 400)
    };

    private static final NPC[] _NPC = {
            new MovingPlatform(1600, 600, "/test_platform.png", new Point[]{new Point(1600, 300)}),
            new MovingPlatform(1600, 400, "/test_platform.png", new Point[]{new Point(1900, 400)})
    };

    public TestMap() {
        super(_WIDTH, _HEIGHT, _TERRAIN, _FOREGROUND, _NULLZONE, _NPC);

        enemies.add(new ShieldDummy(400, SCRN_HEIGHT - 62, Direction.left));
        enemies.add(new FlyingDummy(350, 215, 500));
        enemies.add(new FlyingDummy(1700, _HEIGHT-135, 1700));
        enemies.add(new FlyingDummy(1825, _HEIGHT-135, 1825));
        enemies.add(new BreakableTerrain(1100, 600, 40, 100, 3, "/test_breakableWall.png"));

        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));
        powerups.add(new Coin(100, 0));

        this.P_START_X = 2200;
        this.P_START_Y = 0;
    }
}
