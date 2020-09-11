package com.DanyFids.Model.Menus;

//import com.DanyFids.Model.Entity;
import com.DanyFids.Model.NPCs.Dialog;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DialogBox extends Menu {
    private static int WIDTH_PERC = 80;
    private static int HEIGHT_PERC = 25;

    private Dialog.DialogEntry dialog;

    public DialogBox(Dialog.DialogEntry d){
        dialog = d;
    }

    @Override
    public void land() {
    }

    @Override
    public void draw(BufferedImage screen, int offsetX, int offsetY){
        WIDTH = (screen.getWidth() / WIDTH_PERC) * 100;
        HEIGHT = (screen.getHeight() / HEIGHT_PERC) * 100;

        Graphics g = screen.createGraphics();

        g.setColor(Color.LIGHT_GRAY);

        g.fillRect(0,0,WIDTH,HEIGHT);

        g.setColor(Color.BLACK);

        g.drawString(dialog.text, 3, 3);
    }

	@Override
	public void Open() {
		// TODO Auto-generated method stub
		
	}
}
