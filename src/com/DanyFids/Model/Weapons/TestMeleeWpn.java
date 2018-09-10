package com.DanyFids.Model.Weapons;

import com.DanyFids.Model.*;
import com.DanyFids.graphics.SpriteSheet;

/**
 * Created by Daniel on 10/7/2017.
 */
public class TestMeleeWpn extends Weapon {
    private final int  HB_WIDTH = 20;
    private final int HB_HEIGHT = 60;

    private static final String NAME = "";
    private static final WpnType TYPE = WpnType.Slashing;
    private static final int DMG = 1;
    private static final boolean RANGED = false;

    public static final int ANIM_TIME = 10;

    public TestMeleeWpn() {
        super(NAME, TYPE, DMG, RANGED, ANIM_TIME);

        img = new SpriteSheet("/test_sword.png");
    }

    @Override
    public void hitBox(Enemy e, Direction d, Player p) {
        switch(d){
            case right:
                if(e.getX() < p.getX() + p.WIDTH + this.HB_WIDTH && e.getX() + e.getWidth() > p.getX() + p.WIDTH && e.getY() <= p.getY() + p.HEIGHT && e.getY() + e.getHeight() >= p.getY()){
                    if(e.hitShield(Direction.right)){
                        p.xSpeed = -Physics.KNOCK_BACK;
                        p.setState(State.idle);
                    }
                    e.hurt(this.dmg, Direction.right);
                }
                break;
            case left:
                if(e.getX() < p.getX() && e.getX() + e.getWidth() > p.getX() - (p.WIDTH/2) && e.getY() <= p.getY() + p.HEIGHT && e.getY() + e.getHeight() >= p.getY()){
                    if(e.hitShield(Direction.left)){
                        p.xSpeed = Physics.KNOCK_BACK;
                        p.setState(State.idle);
                    }
                    e.hurt(this.dmg, Direction.left);
                }
                break;
            case up:
                if(e.getY() + e.getHeight() > p.getY() - HB_WIDTH && e.getY() < p.getY() && e.getX() + e.getWidth() > p.getX() - (HB_HEIGHT - p.getWidth())/2 && e.getX() < p.getX() + p.getWidth() + (HB_HEIGHT - p.getWidth())/2){
                    if(e.hitShield(Direction.up)){
                        p.xSpeed = Physics.KNOCK_BACK;
                        p.setState(State.idle);
                    }
                    e.hurt(this.dmg, Direction.up);
                }
                break;
            case down:
                if(e.getY() + e.getHeight() > p.getY() + p.getWidth() && e.getY() < p.getY() + p.getHeight() + this.HB_WIDTH && e.getX() + e.getWidth() > p.getX() - (HB_HEIGHT - p.getWidth())/2 && e.getX() < p.getX() + p.getWidth() + (HB_HEIGHT - p.getWidth())/2){
                    if(e.hitShield(Direction.down)){
                        p.xSpeed = Physics.KNOCK_BACK;
                        p.setState(State.idle);
                    }
                    e.hurt(this.dmg, Direction.down);
                    p.ySpeed = Player.HOP_ACC;
                }
                break;
        }
    }

    @Override
    public void attack(Player p){

    }
}
