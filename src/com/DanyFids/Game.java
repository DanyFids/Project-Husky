package com.DanyFids;

import com.DanyFids.Model.*;
import com.DanyFids.Model.Maps.Map;
import com.DanyFids.Model.Maps.TestMap;
import com.DanyFids.Model.Menus.DialogBox;
import com.DanyFids.Model.Terrains.*;
import com.DanyFids.Model.Enemies.FlyingDummy;
import com.DanyFids.Model.Enemies.ShieldDummy;
//import com.DanyFids.Model.Terrains.Platform;
//import com.DanyFids.Model.Terrains.Ramp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable{
    enum GameState{
        play,
        paused,
        dialog,
        cutscene,
        menu;

        public boolean isPaused(){
            switch(this){
                case play:
                case dialog:
                case cutscene:
                    return false;
                case paused:
                case menu:
                    return true;
            }
            return false;
        }
    }


    private static final long serialVersionUID = 1L;

    // Size and Resolution
    public static final int HEIGHT = 400;
    public static final int WIDTH = 600;
    public static final int SCALE = 2;

    // Game Name
    public static final String NAME = "PROJECT HUSKY";

    // Frame
    private JFrame frame;

    public int tickCount = 0;

    // Display
    private BufferedImage screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) screen.getRaster().getDataBuffer()).getData();

    private boolean running;
    private boolean paused = false;

    // Game
    private Player player = new Player();
    private Controls controls = new Controls();

    private Map map;

    private Terrain[] terrain;
    private Terrain[] foreground;
    private NPC[] NPCs;
    private LinkedList<Enemy> enemies = new LinkedList<>();
    private LinkedList<Powerup> powerups = new LinkedList<>();
    private Menu MenuDisplay;


    public Game(){
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.setFocusable(true);
        this.addKeyListener(controls);

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        loadMap(new TestMap());
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nspt = 1000000000D/60D;

        int ticks = 0;
        int frames = 0;

        long lasTimer = System.currentTimeMillis();
        double delta=0;

        while(running){
            boolean shouldRender = false;
            long now = System.nanoTime();
            delta += (now - lastTime)/nspt;
            lastTime = now;

            while (delta >= 1) {
                ticks++;
                tick();

                delta-=1;
                shouldRender = true;
            }

            if(shouldRender) {
                frames++;
                render();
            }

            long curTimer = System.currentTimeMillis();

            if(curTimer - lasTimer >= 1000){
                lasTimer += (curTimer - lasTimer);

                System.out.println(ticks + "ticks, " + frames + "frames");
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick(){
        tickCount++;

        player.update();

        for(int e=0; e < enemies.size(); e++){
            enemies.get(e).update();
        }

        for(int n=0; n < NPCs.length; n++){
            NPCs[n].update();
        }

        for(int pu = 0; pu < powerups.size(); pu++){
            powerups.get(pu).update();

            if(powerups.get(pu).toRemove()){
                powerups.remove(pu);
            }
        }

        hitDetection();

        if(player.isDead()){
            killPlayer();
        }

        player.move();

        //if(player.getX() + (player.getWidth() / 2) > WIDTH/2){
            map.updateOffsetX((player.getX() + (player.getWidth() / 2)) - (WIDTH/2));
        //}

        //if(player.getY() + (player.getHeight()/2) > HEIGHT - 150){
            map.updateOffsetY((player.getY() + (player.getHeight()/2)) - (HEIGHT - 125));
        //}

        for(int e=0; e < enemies.size(); e++){
            if(enemies.get(e).getHp() <= 0){
                enemies.get(e).kill(enemies, powerups, e);
            }else {
                enemies.get(e).move();
            }
        }

        for(int n=0; n < NPCs.length; n++){
            NPCs[n].move();
        }

        for(int pu = 0; pu < powerups.size(); pu++){
            powerups.get(pu).move();
        }


        for(int i = 0; i< pixels.length; i++){
            pixels[i] = 0xffffff;
        }
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        for(int i = 0; i < terrain.length; i++){
            terrain[i].draw(screen, map.getOffsetX(), map.getOffsetY());
        }


        for(int e=0; e < enemies.size(); e++){
            enemies.get(e).draw(screen, map.getOffsetX(), map.getOffsetY());
        }

        for(int n=0; n < NPCs.length; n++){
            NPCs[n].draw(screen, map.getOffsetX(), map.getOffsetY());
        }

        for(int pu = 0; pu < powerups.size(); pu++){
            powerups.get(pu).draw(screen, map.getOffsetX(), map.getOffsetY());
        }

        player.draw(screen, map.getOffsetX(), map.getOffsetY());


        for(int f = 0; f < foreground.length; f++){
            foreground[f].draw(screen, map.getOffsetX(), map.getOffsetY());
        }

        g.drawImage(screen, 0,0, getWidth(), getHeight(), null);

        g.dispose();

        bs.show();

    }

    private void hitDetection(){
        //borders
        if(player.getY() > map.getHEIGHT()){
            killPlayer();
        }
        if(player.getY() + player.ySpeed < 0) {
            while (player.getY() + player.ySpeed < 0) {
                player.ySpeed += 0.1;
            }

        }
        if(player.getX() + player.xSpeed < 0){
            while(player.getX() + player.xSpeed < 0){
                player.xSpeed += 0.1;
            }
        }
        if(player.getX() + player.getWidth() + player.xSpeed > map.getWIDTH()){
            while(player.getX() + player.getWidth() + player.xSpeed > map.getWIDTH()){
                player.xSpeed -= 0.1;
            }
        }

        //terrain
        for(int i = 0; i < terrain.length; i++) {

            for(int e=0; e < enemies.size(); e++){
                player.hitDetect(enemies.get(e));
                enemies.get(e).hitDetect(player);
                terrain[i].hitDetect(enemies.get(e));
                for(int n = 0; n < NPCs.length; n++){
                    NPCs[n].hitDetect(enemies.get(e));
                }
            }

            for(int n = 0; n < NPCs.length; n++) {
                terrain[i].hitDetect(NPCs[n]);
            }

            for(int pu = 0; pu < powerups.size();pu++){
                terrain[i].hitDetect(powerups.get(pu));
            }

            terrain[i].hitDetect(player);

            LinkedList<Projectile> playerProjectiles = player.getProjectiles();

            for(int p = 0; p < playerProjectiles.size(); p++){
                terrain[i].hitDetect(playerProjectiles.get(p));
            }

        }

        for(int f=0; f < foreground.length; f++){
            foreground[f].hitDetect(player);
        }

        for(int n = 0; n < NPCs.length; n++){
            NPCs[n].hitDetect(player);
        }

        for(int pu = 0; pu < powerups.size();pu++){
            powerups.get(pu).hitDetect(player);
        }
    }

    private void loadMap(Map m){
        this.terrain = m.getTerrain();
        this.foreground = m.getForeground();
        this.enemies = m.getEnemies();
        this.NPCs = m.getNPCs();
        this.powerups = m.getPowerups();
        this.map = m;
        this.player.setX(m.P_START_X);
        this.player.setY(m.P_START_Y);
    }

    private void killPlayer(){
        player.die();
        while (enemies.size() > 0){
            enemies.removeLast();
        }
        this.enemies = map.getEnemies();
        this.NPCs = map.getNPCs();

        if(player.getLives() < 0){
            gameOver();
        }
    }

    private void gameOver(){
        loadMap(map);
        player.setLives(3);
    }

    private synchronized void start() {
        running = true;
        new Thread(this).start();

    }

    private void printMap(){
        if(map != null) {
            File output = new File("printed_map.png");
            BufferedImage screen = new BufferedImage(map.getWIDTH(), map.getHEIGHT(), BufferedImage.TYPE_INT_RGB);

            for(int i = 0; i < map.getWIDTH();i++){
                for(int j = 0; j < map.getHEIGHT(); j++){
                    screen.setRGB(i, j, Color.white.getRGB());
                }
            }

            for(int i = 0; i < terrain.length; i++){
                terrain[i].draw(screen, 0, 0);
            }

            try{
                ImageIO.write(screen, "png", output);
                System.out.println("Map Printed!");
            }catch(IOException e){
                System.out.println("ERROR: Issue Loading Map.");
            }
        }else{
            System.out.println("ERROR: No Map Loaded.");
        }
    }

    private synchronized void stop() {
        running = false;
    }

    public static void main(String[] args){
        new Game().start();
    }

    private class Controls implements KeyListener{

        // Debug Controls
        private int print_map = KeyEvent.VK_PAGE_DOWN;

        private boolean print_map_pressed = false;
        //

        private int left = KeyEvent.VK_LEFT;
        private int right = KeyEvent.VK_RIGHT;
        private int up = KeyEvent.VK_UP;
        private int down = KeyEvent.VK_DOWN;
        private int jump = KeyEvent.VK_C;
        private int atk = KeyEvent.VK_X;
        private int wpn_next = KeyEvent.VK_Z;
        private int wpn_prev = KeyEvent.VK_A;
        private int slide = KeyEvent.VK_D;
        private int shield = KeyEvent.VK_S;

        private boolean jump_pressed = false;
        private boolean atk_pressed = false;
        private boolean wpn_next_pressed = false;
        private boolean wpn_prev_pressed = false;
        private boolean slide_pressed = false;
        private boolean shield_pressed = false;


        @Override
        public void keyTyped(KeyEvent e) {
            /*if(e.getKeyCode() == left){
                player.xSpeed = MOV_SPEED;
            }else if(e.getKeyCode() == right){
                player.xSpeed = -MOV_SPEED;
            }*/
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == left){
                player.mov_left = true;
                player.mov_last = 'l';
            }else if(e.getKeyCode() == right){
                player.mov_right = true;
                player.mov_last = 'r';
            }else if(e.getKeyCode() == up){
                player.look_up = true;
                player.look_down = false;
            }else if(e.getKeyCode() == down){
                player.look_down = true;
                player.look_up = false;
            }

            if(e.getKeyCode() == jump){
                if(!jump_pressed){
                    player.jump();
                    jump_pressed = true;
                }
            }

            if(e.getKeyCode() == atk){
                if(!atk_pressed){
                    if(player.isNextToNPC()){
                        player.InteractNPC.interact(player, powerups, enemies);
                        atk_pressed = true;
                    }else {
                        player.attack();
                        atk_pressed = true;
                    }
                }
            }

            if(e.getKeyCode() == wpn_next){
                if(!wpn_next_pressed) {
                    player.nextWpn();
                    wpn_next_pressed = true;
                }
            }
            if(e.getKeyCode() == wpn_prev){
                if(!wpn_prev_pressed) {
                    player.prevWpn();
                    wpn_prev_pressed = true;
                }
            }

            if(e.getKeyCode() == slide){
                if(!slide_pressed){
                    player.slide();
                    slide_pressed = true;
                }
            }

            if(e.getKeyCode() == shield){
                if(!shield_pressed){
                    player.shield();
                    shield_pressed = true;
                }
            }





            // Debug Keys
            if(e.getKeyCode() == print_map){
                if(!print_map_pressed){
                    printMap();
                    print_map_pressed = true;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode() == left){
                player.mov_left = false;
                if(player.mov_last == 'l'){
                    player.mov_last = 'o';
                }
            }else if(e.getKeyCode() == right){
                player.mov_right = false;
                if(player.mov_last == 'r'){
                    player.mov_last = 'o';
                }
            }else if(e.getKeyCode() == up){
                player.look_up = false;
            }else if(e.getKeyCode() == down){
                player.look_down = false;
            }

            if(e.getKeyCode() == jump){
                if(player.ySpeed < Player.HOP_ACC){
                    player.ySpeed = Player.HOP_ACC;
                }
                jump_pressed = false;
            }

            if(e.getKeyCode() == atk){
                atk_pressed = false;
            }

            if(e.getKeyCode() == wpn_next){
                wpn_next_pressed = false;
            }
            if(e.getKeyCode() == wpn_prev){
                wpn_prev_pressed = false;
            }

            if(e.getKeyCode() == slide){
                slide_pressed = false;
            }

            if(e.getKeyCode() == shield){
                shield_pressed = false;
                player.endShield();
            }




            // Debug Keys
            if(e.getKeyCode() == print_map){
                print_map_pressed = false;
            }
        }
    }
}
