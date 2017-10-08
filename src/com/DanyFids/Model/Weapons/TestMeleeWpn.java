package com.DanyFids.Model.Weapons;

/**
 * Created by Daniel on 10/7/2017.
 */
public class TestMeleeWpn extends Weapon {
    private static final String NAME = "";
    private static final WpnType TYPE = WpnType.Slashing;
    private static final int DMG = 1;
    private static final boolean RANGED = false;

    public TestMeleeWpn() {
        super(NAME, TYPE, DMG, RANGED);
    }
}
