package com.DanyFids.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by Daniel on 5/24/2017.
 */

public class SpriteSheet {
    private String path;

    private int width;
    private int height;

    private int[] pixels;
    
    Vector<Vector<Sprite>> animations;

    private BufferedImage page;

    public SpriteSheet(String path){
        BufferedImage image = null;

        try {
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(image == null){
            return;
        }

        this.path = path;
        this.width = image.getWidth();
        this.height = image.getHeight();

        this.pixels = image.getRGB(0,0, width, height, null, 0, width);

        for(int i = 0; i < pixels.length; i++){
            pixels[i] = (pixels[i] & 0xff);
        }

        this.page = image;
    }

    public BufferedImage getPage(){
        return this.page;
    }

    public BufferedImage getSprite(int x, int y, int w, int h){
        return this.page.getSubimage(x,y,w,h);
    }
}
