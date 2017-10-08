package com.DanyFids.Model.Weapons;

/**
 * Created by Daniel on 9/30/2017.
 */
public abstract class Weapon {

    public String name;
    public WpnType type;
    public int dmg;
    public boolean ranged;

    public Weapon(String n, WpnType t, int d, boolean r){
        name = n;
        type = t;
        dmg = d;
        ranged = r;
    }

}
