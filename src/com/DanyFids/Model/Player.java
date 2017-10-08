package com.DanyFids.Model;

import com.DanyFids.Model.Weapons.TestMeleeWpn;
import com.DanyFids.Model.Weapons.*;
import com.DanyFids.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by Daniel on 5/24/2017.
 */
public class Player extends Entity{
    public enum State {
        idle, attacking
    }

    private final int JUMP_ACC = -7;
    private final int MOV_SPEED = 3;
    private final int INVULN_TIME = 30;
    private final int STUN_TIME = 10;

    private State state = State.idle;

    //public float ySpeed = 0;
    //public float xSpeed = 0;

    public final int _WIDTH = 38;
    public final int _HEIGHT = 60;
    private final int START_HP = 12;

    private boolean face_right = true;
    public boolean mov_left = false;
    public boolean mov_right = false;
    public char mov_last = 'o';

    private boolean can_jump = false;
    private boolean can_attack = true;
    private boolean attacking = false;
    private boolean stunned = false;

    private int hp;
    private int max_hp = START_HP;

    private LinkedList<Weapon> arsenal = new LinkedList<>();
    private int equipped_wpn;

    //timers
    private int anim_timer = 0;
    private int invuln_timer = 0;
    //private int stun_timer = 0;

    public Player(){
        this.x = 0;
        this.y = 0;

        sprt = new SpriteSheet("/test_player.png");
        arsenal.add(new TestMeleeWpn());
        arsenal.add(new TestRangeWpn());
        equipped_wpn = 0;

        this.WIDTH = _WIDTH;
        this.HEIGHT = _HEIGHT;

        this.hp = max_hp;
    }

    @Override
    public void draw(BufferedImage screen){
        Graphics g = screen.getGraphics();

        if(state == State.attacking){
            if(face_right) {
                g.drawImage(sprt.getPage().getSubimage(0, HEIGHT + 1, WIDTH + (WIDTH/2), HEIGHT), this.x, this.y, null);
            }else{
                g.drawImage(sprt.getPage().getSubimage(0, HEIGHT + 1, WIDTH + (WIDTH/2), HEIGHT), this.x + WIDTH, this.y, -(WIDTH + (WIDTH/2)), HEIGHT, null);
            }
        }else{
            g.drawImage(sprt.getPage().getSubimage(0, 0, WIDTH, HEIGHT), this.x, this.y, null);
        }

        drawUI(screen);
    }

    public void update(){
        if(ySpeed < Physics.TERMINAL_V){
            ySpeed += Physics.GRAVITY;
        }
        if(!stunned) {
            if (mov_left || mov_right) {
                if (mov_left && mov_right) {
                    if (mov_last == 'l') {
                        xSpeed = -MOV_SPEED;
                        face_right = false;
                    } else if (mov_last == 'r') {
                        xSpeed = MOV_SPEED;
                        face_right = true;
                    } else {
                        xSpeed = 0;
                    }
                } else {
                    if (mov_left) {
                        xSpeed = -MOV_SPEED;
                        face_right = false;
                    } else if (mov_right) {
                        xSpeed = MOV_SPEED;
                        face_right = true;
                    }
                }
            } else {
                xSpeed = 0;
            }
        }

        switch(state){
            case idle:
                break;
            case attacking:
                if(anim_timer == 0){
                    state = State.idle;
                }else{
                    anim_timer--;
                }
                break;
        }

        if(invuln_timer > 0){
            invuln_timer--;
        }
    }

    public void move(){
        this.y += ySpeed;
        this.x += xSpeed;
    }

    public void jump(){
        if(!stunned) {
            if (this.can_jump) {
                this.ySpeed = this.JUMP_ACC;
                this.can_jump = false;
            }
        }
    }

    @Override
    public void land(){
        this.can_jump = true;
        if(stunned){
            stunned = false;
        }
    }

    public boolean checkJump(){
        return this.can_jump;
    }

    public void attack(){
        if(!stunned) {
            if (can_attack) {
                state = State.attacking;
                anim_timer = 10;
            }
        }
    }

    public void hitDetect(Enemy e){
        if(state == State.attacking){
            if(face_right){
                if(e.x < this.x + this.WIDTH + (this.WIDTH/2) && e.x + e.getWidth() > this.x + this.WIDTH && e.y <= this.y + this.HEIGHT && e.y + e.getHeight() >= this.y){
                    if(e.hitShield(true)){
                        this.xSpeed = -Physics.KNOCK_BACK;
                        this.state = State.idle;
                    }
                    e.hurt(this.arsenal.get(equipped_wpn).dmg, true);
                }
            }else{
                if(e.x < this.x && e.x + e.getWidth() > this.x - (this.WIDTH/2) && e.y <= this.y + this.HEIGHT && e.y + e.getHeight() >= this.y){
                    if(e.hitShield(false)){
                        this.xSpeed = Physics.KNOCK_BACK;
                        this.state = State.idle;
                    }
                    e.hurt(this.arsenal.get(equipped_wpn).dmg, false);
                }
            }
        }
    }

    public void hurt(int dmg){
        if(invuln_timer == 0) {
            this.hp -= dmg;
            invuln_timer = INVULN_TIME;
            stunned = true;

            this.ySpeed = - Physics.KNOCK_BACK;
            if(this.face_right){
                this.xSpeed = - Physics.KNOCK_BACK;
            }else{
                this.xSpeed = Physics.KNOCK_BACK;
            }
        }
    }

    public void nextWpn(){
        this.equipped_wpn++;
        if(this.equipped_wpn >= this.arsenal.size()){
            this.equipped_wpn = 0;
        }
    }

    public void prevWpn(){
        this.equipped_wpn--;
        if(this.equipped_wpn < 0){
            this.equipped_wpn = this.arsenal.size() - 1;
        }
    }


    // In Game Player UI
    public static final int SCRN_HEIGHT = 400;
    public static final int SCRN_WIDTH = 600;

    private final int HEART_W = 15;
    private final int HEART_H = 14;

    private SpriteSheet UI_sheet = new SpriteSheet("/UI.png");

    public void drawUI(BufferedImage screen){
        Graphics g = screen.getGraphics();


        // Drawing HP
        int full_hearts = this.hp/4;
        int incomplete_heart = this.hp % 4;
        int lost_hearts = (this.max_hp - this.hp)/4;
        int half_hearts = 0;

        for (int h = 0; h < full_hearts; h++) {
            g.drawImage(UI_sheet.getPage().getSubimage(0,0, HEART_W, HEART_H), ((HEART_W + 1) * h),0, null);
        }

        if(incomplete_heart > 0){
            g.drawImage(UI_sheet.getPage().getSubimage((16)*incomplete_heart,0, HEART_W, HEART_H), ((HEART_W + 1) * full_hearts),0, null);
            half_hearts++;
        }

        for(int e = 0; e < lost_hearts; e++){
            g.drawImage(UI_sheet.getPage().getSubimage(64,0, HEART_W, HEART_H), ((HEART_W + 1) * (full_hearts + half_hearts + e)),0, null);
        }


        g.drawImage(UI_sheet.getPage().getSubimage(0, 30, 64,64), SCRN_WIDTH - 70, 4, null);
        g.drawImage(arsenal.get(equipped_wpn).img.getPage(), SCRN_WIDTH - 55, 19, null);
    }
}
