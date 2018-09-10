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
    public static final int HEART_HEALTH = 4;
    public static final int _WIDTH = 38;
    public static final int _HEIGHT = 60;

    private final int JUMP_ACC = -7;
    public static final int HOP_ACC = -3;
    private final int MOV_SPEED = 3;
    private final int DASH_SPEED = 6;
    private final int INVULN_TIME = 30;
    private final int STUN_TIME = 10;
    private final int NO_STICK_TIME = 15;
    private final int SLIDE_TIME = 20;
    private final int START_LIVES = 3;

    //public float ySpeed = 0;
    //public float xSpeed = 0;


    private final int START_HP = 12;

    public boolean mov_left = false;
    public boolean mov_right = false;
    public boolean look_up = false;
    public boolean look_down = false;
    public char mov_last = 'o';

    public boolean ladder_climb_up = false;
    public boolean ladder_climb_down = false;

    public Direction atk_dir = Direction.right;
    private Direction slide_dir;
    private Direction dash_dir;

    private boolean can_jump = false;
    private boolean can_attack = true;
    private boolean attacking = false;
    private boolean stunned = false;
    private boolean sliding = false;

    private int hp;
    private int max_hp = START_HP;
    private int lives;
    private int gold;

    private LinkedList<Weapon> arsenal = new LinkedList<>();
    private LinkedList<Projectile> projectiles = new LinkedList<>();
    private int equipped_wpn;

    private SpawnPoint respawn = new SpawnPoint(0,0);

    //timers
    private int anim_timer = 0;
    private int invuln_timer = 0;
    //private int stun_timer = 0;
    private int no_stick_timer = 0;
    private int unstick_timer = 0;
    private int dash_timer = 0;

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
        this.lives = START_LIVES;

        this.isPlayer = true;
    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY){
        Graphics g = screen.getGraphics();

        switch(state) {
            case attacking:
                switch (atk_dir) {
                    case right:
                        g.drawImage(sprt.getPage().getSubimage(0, HEIGHT + 1, WIDTH + 20, HEIGHT), this.x - offsetX, this.y - offsetY, null);
                        break;
                    case left:
                        g.drawImage(sprt.getPage().getSubimage(0, HEIGHT + 1, WIDTH + 20, HEIGHT), this.x + WIDTH - offsetX, this.y - offsetY, -(WIDTH + 20), HEIGHT, null);
                        break;
                    case up:
                        g.drawImage(sprt.getPage().getSubimage(0, (HEIGHT + 1) * 2, HEIGHT, HEIGHT + 20), this.x - ((this.getHeight() - this.getWidth()) / 2) - offsetX, this.y - 20 - offsetY, null);
                        break;
                    case down:
                        g.drawImage(sprt.getPage().getSubimage(0, (HEIGHT + 1) * 3 + 20, HEIGHT, HEIGHT + 20), this.x - ((this.getHeight() - this.getWidth()) / 2) - offsetX, this.y - offsetY, null);
                        break;
                }
                break;
            case slide:
                if(face_right) {
                    g.drawImage(sprt.getPage().getSubimage(0, 284, 60, 32), this.x - offsetX, this.y + (_HEIGHT - 32) - offsetY, null);
                    break;
                }else{
                    g.drawImage(sprt.getPage().getSubimage(0, 284, 60, 32), this.x - (_HEIGHT - _WIDTH) - offsetX, this.y + (_HEIGHT - 32) - offsetY, null);
                    break;
                }
            case shield:
                if(dir != Direction.up) {
                    if (face_right){
                        g.drawImage(sprt.getPage().getSubimage(0, 317, 43, 60), this.x - offsetX, this.y - offsetY, null);
                        break;
                    }else{
                        g.drawImage(sprt.getPage().getSubimage(0, 317, 43, 60), this.x - offsetX + _WIDTH, this.y - offsetY, -43, _HEIGHT, null);
                        break;
                    }
                }else{
                    g.drawImage(sprt.getPage().getSubimage(0, 377, 60, 65), this.x - offsetX - 11, this.y - offsetY - 5, null);
                    break;
                }
            default:
                g.drawImage(sprt.getPage().getSubimage(0, 0, WIDTH, HEIGHT), this.x - offsetX, this.y - offsetY, null);
                break;
        }

        for(int p = 0; p < projectiles.size(); p++){
            projectiles.get(p).draw(screen, offsetX, offsetY);
        }

        drawUI(screen);
    }

    public void update(){
        if(ySpeed > 0 && state == State.slide && !onRamp && !onPlatform){
            state = State.jumping;
        }

        /*if(onPlatform && ySpeed < 0){
            ySpeed = 0;
        }*/

        if(onPlatform){
            onPlatform = false;
            ySpeed = 0;
        }

        if(state == State.wall_slide){
            if(ySpeed < Physics.SLIDE_SPEED){
                ySpeed += Physics.GRAVITY;
            }
        }else {
            if (ySpeed < Physics.TERMINAL_V) {
                ySpeed += Physics.GRAVITY;
            }
        }

        if(state == State.ladder){
            if(onLadder) {
                ySpeed = 0;
                if (sliding) {
                    endSlide();
                }
                onLadder = false;
            }else{
                this.state = State.idle;
            }
        }

        if(!onWall && state == State.wall_slide){
            this.state = State.jumping;
        }

        if(onWall){
            onWall = false;
        }

        yMove = 0;

        //System.out.println(onRamp);

        if(onRamp && ySpeed < 0){
            ySpeed = 0;
        }

        if(onRamp){
            onRamp = false;
        }

        if(face_right){
            dir = Direction.right;
        }else{
            dir = Direction.left;
        }

        if(!stunned && this.unstick_timer == 0) {
            if(state != State.ladder) {
                if (mov_left || mov_right) {
                    if (mov_left && mov_right) {
                        if (mov_last == 'l') {
                            xSpeed = -MOV_SPEED;
                            face_right = false;
                            dir = Direction.left;
                        } else if (mov_last == 'r') {
                            xSpeed = MOV_SPEED;
                            face_right = true;
                            dir = Direction.right;
                        } else {
                            xSpeed = 0;
                        }
                    } else {
                        if (mov_left) {
                            xSpeed = -MOV_SPEED;
                            face_right = false;
                            dir = Direction.left;
                        } else if (mov_right) {
                            xSpeed = MOV_SPEED;
                            face_right = true;
                            dir = Direction.right;
                        }
                    }
                } else {
                    xSpeed = 0;
                }
            }else{
                if(!ladder_climb_down && !ladder_climb_up) {
                    if (look_up) {
                        ySpeed = -MOV_SPEED;
                    }

                    if (look_down) {
                        ySpeed = MOV_SPEED;
                    }
                }else{
                    if(ladder_climb_down){
                        if(anim_timer > 0){
                            anim_timer--;
                            this.onLadder = true;
                        }else{
                            this.y += 30;
                            ladder_climb_down = false;
                        }
                    }

                    if(ladder_climb_up){
                        if(anim_timer > 0){
                            anim_timer--;
                            this.onLadder = true;
                        }else{
                            ladder_climb_up = false;
                        }
                    }
                }
            }

            if(look_up){
                this.dir = Direction.up;
            }

            if(look_down){
                this.dir = Direction.down;
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
            case wall_slide:
                if(slide_dir != dir){
                    this.state = State.jumping;
                }
                break;
            case slide:
                break;
        }

        if(invuln_timer > 0){
            invuln_timer--;
        }

        if(no_stick_timer > 0){
            no_stick_timer--;
        }

        if(unstick_timer > 0){
            unstick_timer--;
        }

        if(sliding){
            if(Direction.opposing(dash_dir, dir)){
                endSlide();
            }
        }

        if(dash_timer > 0){
            dash_timer--;
        }else if(can_jump && sliding){
            endSlide();
        }

        if(stuckSliding){
            stuckSliding = false;
        }

        if(sliding && state != State.wall_slide){
            if(dash_dir == Direction.right){
                this.xSpeed = DASH_SPEED;
            }else if(dash_dir == Direction.left){
                this.xSpeed = -DASH_SPEED;
            }
        }

        for(int p = 0; p < projectiles.size(); p++){
            projectiles.get(p).update();

            if(projectiles.get(p).despawn()){
                projectiles.remove(p);
            }
        }
    }

    public void move(){
        if(this.xSpeed < 0.1 && this.xSpeed > 0){
            this.xSpeed = 0;
        }
        if(this.ySpeed < 0.1 && this.ySpeed > 0){
            this.ySpeed = 0;
        }

        this.y += ySpeed;
        this.y += yMove;
        this.x += xSpeed;

        if(this.ySpeed > 0 && this.state != State.wall_slide && !this.onRamp && !this.onPlatform){
            this.can_jump = false;
        }

        for(int p = 0; p < projectiles.size(); p++){
            projectiles.get(p).move();
        }
    }

    public void jump(){
        if(state == State.wall_slide){
            if (this.can_jump) {
                this.ySpeed = this.JUMP_ACC;
                if(this.face_right){
                    this.xSpeed = -MOV_SPEED;
                }else{
                    this.xSpeed = MOV_SPEED;
                }
                this.can_jump = false;
                this.onPlatform = false;
                this.state = State.jumping;

                this.no_stick_timer = this.NO_STICK_TIME;
                this.unstick_timer = this.NO_STICK_TIME;
            }
        }else if(state == State.ladder) {
            this.can_jump = false;
            this.setState(State.jumping);

            this.no_stick_timer = this.NO_STICK_TIME;
        }else if(!stunned && !stuckSliding) {
                if (this.can_jump) {
                    if(dir == Direction.down && onPlatform){
                        this.y += 1;
                        onPlatform = false;
                        can_jump = false;
                    }else {
                        this.ySpeed = this.JUMP_ACC;
                        this.can_jump = false;
                        this.onPlatform = false;
                        state = State.jumping;

                        this.no_stick_timer = this.NO_STICK_TIME;
                        if (this.onRamp) {
                            this.onRamp = false;
                        }
                    }
                }

        }
    }

    @Override
    public void land(){
        this.can_jump = true;
        if(this.state == State.jumping){
            this.state = State.idle;
        }else if(this.state == State.wall_slide){
            this.state = State.idle;
        }else if(this.state == State.ladder){
            this.state = State.idle;
        }
        if(stunned){
            stunned = false;
        }
    }

    @Override
    public void wallSlide(){
        if(!can_jump && state != State.wall_slide && !stunned && no_stick_timer == 0){
            this.state = State.wall_slide;
            this.can_jump = true;
            this.ySpeed = 0;
            this.slide_dir = this.dir;
            //this.onWall = true;
        }
    }

    public void slide(){
        if(can_jump) {
            if(this.state == State.wall_slide) {
                if (face_right) {
                    this.dash_dir = Direction.left;
                } else {
                    this.dash_dir = Direction.right;
                }
                this.dash_timer = 10;
                this.sliding = true;
            }else{
                this.state = State.slide;
                if (face_right) {
                    this.dash_dir = Direction.right;
                } else {
                    this.dash_dir = Direction.left;
                }
                this.dash_timer = SLIDE_TIME;
                this.sliding = true;
            }
        }
    }

    public void endSlide(){
        if(!stuckSliding) {
            this.dash_timer = 0;
            this.sliding = false;
            this.state = State.idle;
        }else{
            if(dash_timer == 0){
                dash_timer = 2;
            }

            if(Direction.opposing(dash_dir, dir)){
                dash_dir = dir;
            }
        }
    }

    @Override
    public void ladderClimbDown() {
        ladder_climb_down = true;
        anim_timer = 10;
    }

    @Override
    public void ladderClimbUp(int ladderY) {
        ladder_climb_up = true;
        anim_timer = 10;
        ySpeed = 0;
        int yDif = this.getY() - (ladderY - this.getHeight());

        this.y -= yDif;
    }

    public boolean checkJump(){
        return this.can_jump;
    }

    public void attack(){
        if(!stunned) {
            if (can_attack) {
                if(!(can_jump && dir == Direction.down)) {
                    state = State.attacking;
                    atk_dir = dir;
                    anim_timer = arsenal.get(equipped_wpn).getAnim_time();
                    arsenal.get(equipped_wpn).attack(this);
                }
            }
        }
    }

    public LinkedList<Projectile> getProjectiles(){
        return this.projectiles;
    }

    public void shield(){
        if(!stunned){
            if(can_attack == can_jump){
                state = State.shield;
            }
        }
    }

    public void endShield(){
        if(state==State.shield){
            state = State.idle;
        }
    }

    public void hitDetect(Enemy e){
        if(state == State.attacking){
            this.arsenal.get(equipped_wpn).hitBox(e, this.atk_dir, this);
        }

        for(int p = 0; p < projectiles.size(); p++){
            projectiles.get(p).hitDetect(e, projectiles, p);
        }
    }

    public void hurt(int dmg, Direction d){
        if(invuln_timer == 0) {
            endSlide();
            this.hp -= dmg;
            invuln_timer = INVULN_TIME;
            stunned = true;


            state = State.hurt;

            this.ySpeed = - Physics.KNOCK_BACK;

            switch(d){
                case right:
                    this.xSpeed = Physics.KNOCK_BACK;
                    face_right = false;
                    break;
                case left:
                    this.xSpeed = - Physics.KNOCK_BACK;
                    face_right = true;
                    break;
                default:
                    if (this.face_right) {
                        this.xSpeed = -Physics.KNOCK_BACK;
                    } else {
                        this.xSpeed = Physics.KNOCK_BACK;
                    }
                    break;
            }
        }

        if(hp <= 0){

        }
    }

    public void heal(int heal){
        if(this.hp + heal > this.max_hp){
            this.hp = this.max_hp;
        }else{
            this.hp += heal;
        }
    }

    public void addGold(int g){
        this.gold += g;
    }

    public void die(){
        this.x = this.respawn.getX();
        this.y = this.respawn.getY();

        this.hp = this.max_hp;

        this.lives--;

        can_jump = false;
        can_attack = true;
        attacking = false;
        stunned = false;
        sliding = false;

        for(int p = 0; p < projectiles.size(); p++){
            projectiles.remove(p);
        }
    }

    public void setLives(int l){
        if(l > 0) {
            this.lives = l;
        }else{
            this.lives = 0;
        }
    }

    public int getLives(){
        return this.lives;
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

    public void setRespawn(SpawnPoint p){
        this.respawn = p;
    }

    public boolean isDead(){
        return this.hp <= 0;
    }

    public void setState(State s){
        this.state = s;
    }

    public void addProjectile(Projectile p){
        this.projectiles.add(p);
    }

    @Override
    public int getDashTime(){
        return dash_timer;
    }

    @Override
    public void setDashTime(int time){
        dash_timer = time;
    }

    public Direction getDir(){return dir;}

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

        g.drawImage(UI_sheet.getPage().getSubimage(0, 95, 24, 24), SCRN_WIDTH - 50, SCRN_HEIGHT - 40, null);

        g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        g.setColor(Color.MAGENTA);
        g.drawString("" + lives, SCRN_WIDTH - 20, SCRN_HEIGHT - 20);

    }
}
