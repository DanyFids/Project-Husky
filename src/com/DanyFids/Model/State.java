package com.DanyFids.Model;

/**
 * Created by Daniel on 11/18/2017.
 */
public enum State {
    idle, attacking, wall_slide, jumping, slide, hurt, shield, ladder;

    public int stateHeight(){
        switch(this){
            case idle:
            case hurt:
            case attacking:
            case jumping:
            case wall_slide:
            case shield:
            case ladder:
                return Player._HEIGHT;
            case slide:
                return 32;
        }

        return Player._HEIGHT;
    }
}
