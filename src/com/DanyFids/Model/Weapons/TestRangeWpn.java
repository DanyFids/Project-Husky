package com.DanyFids.Model.Weapons;

import com.DanyFids.Model.Direction;
import com.DanyFids.Model.Enemy;
import com.DanyFids.Model.Player;
import com.DanyFids.Model.Projectile;
import com.DanyFids.Model.Projectiles.Arrow;
import com.DanyFids.graphics.SpriteSheet;

import java.util.LinkedList;

/**
 * Created by Daniel on 10/5/2017.
 */
public class TestRangeWpn extends Weapon{
    private static final String NAME = "";
    private static final WpnType TYPE = WpnType.Piercing;
    private static final int DMG = 1;
    private static final boolean RANGED = true;

    //private LinkedList<Projectile> arrows;

    public static final int ANIM_TIME = 2;

    public TestRangeWpn(){
        super(NAME, TYPE, DMG, RANGED, ANIM_TIME);

        img = new SpriteSheet("/test_bow.png");
    }

    @Override
    public void hitBox(Enemy e, Direction d, Player p) {

    }

    @Override
    public void attack(Player p){
        //System.out.println(p.atk_dir);
        switch(p.atk_dir) {
            case right:
                p.addProjectile(new Arrow(p.getX() + (p.getWidth()/2) - (Arrow._WIDTH/2), (p.getY() + (p.getHeight() / 2)) - (Arrow._HEIGHT / 2), ANIM_TIME, p.dir));
                break;
            case left:
                p.addProjectile(new Arrow(p.getX() + (p.getWidth()/2) - (Arrow._WIDTH/2), (p.getY() + (p.getHeight() / 2)) - (Arrow._HEIGHT / 2), ANIM_TIME, p.dir));
                break;
            default:
                p.addProjectile(new Arrow(p.getX() + (p.getWidth() / 2) - (Arrow._HEIGHT/2), (p.getY() + (p.getHeight() / 2)) - (Arrow._HEIGHT / 2), ANIM_TIME, p.dir));
                break;
        }
    }
}
