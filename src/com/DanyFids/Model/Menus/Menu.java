package com.DanyFids.Model.Menus;

import com.DanyFids.Model.Direction;
import com.DanyFids.Model.Entity;

public abstract class Menu extends Entity {
    int curOption = -1;
    MenuOption[] options;

    public abstract void Open();

    public void Navigate(Direction d) {
        if(curOption > 0 && curOption < options.length){
            if(options[curOption].getNav(d) != null) {

            }
        }
    }

    public void Select(){

    };
}
