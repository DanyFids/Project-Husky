package com.DanyFids.Model.Weapons;

/**
 * Created by Daniel on 10/5/2017.
 */
public class TestRangeWpn extends Weapon{
    private static final String NAME = "";
    private static final WpnType TYPE = WpnType.Piercing;
    private static final int DMG = 1;
    private static final boolean RANGED = true;

    public TestRangeWpn(){
        super(NAME, TYPE, DMG, RANGED);
    }
}
