package com.DanyFids.Model.Weapons;

import com.DanyFids.Model.Direction;
import com.DanyFids.Model.Enemy;
import com.DanyFids.Model.Player;
import com.DanyFids.Model.Projectile;
import com.DanyFids.graphics.SpriteSheet;

/**
 * Created by Daniel on 9/30/2017.
 */
public abstract class Weapon {
    public String name;
    public WpnType type;
    public int dmg;
    public boolean ranged;
    private int anim_time;

    public SpriteSheet img;

    public Weapon(String n, WpnType t, int d, boolean r, int anim_time){
        name = n;
        type = t;
        dmg = d;
        ranged = r;

        this.anim_time = anim_time;
    }

    public int getAnim_time(){
        return anim_time;
    }

    public abstract void hitBox(Enemy e, Direction d, Player p);

    public abstract void attack(Player p);

}
