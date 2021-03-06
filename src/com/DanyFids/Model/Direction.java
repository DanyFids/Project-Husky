package com.DanyFids.Model;

/**
 * Created by Daniel on 10/9/2017.
 */
public enum Direction {
    nil,
    up,
    right,
    down,
    left;

    public int getVal(){
        switch(this){
            case nil:
                return -1;
            case up:
                return 0;
            case right:
                return 1;
            case down:
                return 2;
            case left:
                return 3;
        }

        return -1;
    }

    public static boolean opposing(Direction d1, Direction d2){
        switch(d1){
            case right:
                if(d2 == left){
                    return true;
                }else{
                    return false;
                }
            case left:
                if(d2 == right){
                    return true;
                }else return false;
            case down:
                if(d2 == up) return true;
                else return false;
            case up:
                if(d2 == down) return true;
                else return false;
            case nil:
                return false;
        }

        return false;
    }
}
