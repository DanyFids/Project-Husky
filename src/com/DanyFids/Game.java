package com.DanyFids;

import com.DanyFids.Model.Terrains.*;
import com.DanyFids.Model.Enemies.FlyingDummy;
import com.DanyFids.Model.Enemies.ShieldDummy;
import com.DanyFids.Model.Enemy;
import com.DanyFids.Model.Player;
import com.DanyFids.Model.Terrain;
//import com.DanyFids.Model.Terrains.Platform;
//import com.DanyFids.Model.Terrains.Ramp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable{

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

    // Game
    private Player player = new Player();
    private Controls controls = new Controls();

    private Terrain[] terrain = {
            new Block(0, HEIGHT-2, "/test_floor.png"),
            new Block(100, HEIGHT - 34, "/test_block.png"),
            new Block(132, HEIGHT - 34, "/test_block.png"),
            new Platform(0,250,"/test_platform.png")//,
            //new Ramp(164, HEIGHT - 34, 0,0, 32, 32, "/test_ramp.png")
    };
    private LinkedList<Enemy> enemies = new LinkedList<>();


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


        //spawn dummies
        enemies.add(new ShieldDummy(400, HEIGHT - 62, false));
        enemies.add(new FlyingDummy(350, 200, 500));
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

        hitDetection();

        player.move();

        for(int e=0; e < enemies.size(); e++){
            if(enemies.get(e).getHp() <= 0){
                enemies.get(e).kill(enemies, e);
            }else {
                enemies.get(e).move();
            }
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
            terrain[i].draw(screen);
        }

        for(int e=0; e < enemies.size(); e++){
            enemies.get(e).draw(screen);
        }

        player.draw(screen);


        g.drawImage(screen, 0,0, getWidth(), getHeight(), null);

        g.dispose();

        bs.show();

    }

    private void hitDetection(){
        //borders
        if(player.getY() + player.HEIGHT + player.ySpeed > HEIGHT){
            while(player.getY() + player.HEIGHT + player.ySpeed > HEIGHT) {
                player.ySpeed -= 0.1;
            }
            player.land();
        }
        if(player.getY() + player.ySpeed < 0) {
            while (player.getY() + player.ySpeed < 0) {
                player.ySpeed += 0.1;
            }

        }

        //terrain
        for(int i = 0; i < terrain.length; i++) {

            for(int e=0; e < enemies.size(); e++){
                player.hitDetect(enemies.get(e));
                enemies.get(e).hitDetect(player);
                terrain[i].hitDetect(enemies.get(e));
            }

            terrain[i].hitDetect(player);
        }
    }

    private synchronized void start() {
        running = true;
        new Thread(this).start();

    }

    private synchronized void stop() {
        running = false;
    }

    public static void main(String[] args){
        new Game().start();
    }

    private class Controls implements KeyListener{

        private int left = KeyEvent.VK_LEFT;
        private int right = KeyEvent.VK_RIGHT;
        private int jump = KeyEvent.VK_C;
        private int atk = KeyEvent.VK_X;


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
            }

            if(e.getKeyCode() == jump){
                player.jump();
            }

            if(e.getKeyCode() == atk){
                player.attack();
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
            }

            if(e.getKeyCode() == jump){
                if(player.ySpeed < -3){
                    player.ySpeed = -3;
                }
            }
        }
    }
}
