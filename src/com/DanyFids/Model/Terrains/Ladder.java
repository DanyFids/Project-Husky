package com.DanyFids.Model.Terrains;

import com.DanyFids.Model.Direction;
import com.DanyFids.Model.Entity;
import com.DanyFids.Model.State;
import com.DanyFids.Model.Terrain;

/**
 * Created by Daniel on 3/4/2018.
 */
public class Ladder extends Terrain {
    private final int _WIDTH = 38;
    private final int _HEIGHT = 185;
    private static final String TEST_PATH = "/testladder.png";

    public Ladder(int x, int y) {
        super(x, y, TEST_PATH);
        this.WIDTH = _WIDTH;
        this.HEIGHT = _HEIGHT;
    }

    public void hitDetect(Entity e){
        if(e.getY() + e.getHeight() <= this.getY() && e.getY() + e.getHeight() + e.ySpeed > this.getY() && e.getX() + e.getWidth() > this.getX() && e.getX() < this.getX() + this.getWidth()){
            e.ySpeed = this.getY() - (e.getY()+e.getHeight());
            e.land();

            if(e.getX() >= this.getX() - (this.getWidth()/3) && e.getX() + e.getWidth() <= this.getX()+this.getWidth() + (this.getWidth()/3) && e.dir == Direction.down){
                e.state = State.ladder;
                e.setX(this.getX());
                e.xSpeed = 0;

                e.onLadder = true;
                e.ladderClimbDown();
            }
        }else if(e.getY() + e.getHeight() == this.getY() && e.getX() >= this.getX() - (this.getWidth()/3) && e.getX() + e.getWidth() <= this.getX()+this.getWidth() + (this.getWidth()/3) && e.dir == Direction.down){
            e.state = State.ladder;
            e.setX(this.getX());
            e.xSpeed = 0;
            e.onLadder = true;
        }else{
            if(e.getX() >= this.getX() - (this.getWidth()/3) && e.getX() + e.getWidth() <= this.getX()+this.getWidth() + (this.getWidth()/3) &&
                    e.getY() < this.getY() + this.getHeight() - 30 && e.getY() > this.getY() &&
                    e.dir == Direction.up){
                e.state = State.ladder;
                e.setX(this.getX());
                e.xSpeed=0;

                e.onLadder = true;
            }
        }

        if(e.state == State.ladder){
            if(e.getX() >= this.getX() &&
                    e.getX() + e.getWidth() <= this.getX()+this.getWidth() &&
                    e.getY() <= this.getY() + this.getHeight() - 30 &&
                    e.getY() >= this.getY()-30){
                e.onLadder = true;

                if(e.getY() + e.ySpeed < this.getY() - 30 && e.dir == Direction.up){
                    e.ladderClimbUp(this.getY());
                }
            }
        }
    }
}
