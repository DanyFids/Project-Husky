package com.DanyFids.Model.Menus;

import com.DanyFids.Model.Direction;
import com.DanyFids.Model.Entity;

public class MenuOption extends Entity {

    private MenuOption nav[] = new MenuOption[4];
    MenuOption getNav(Direction d){
        if(d != Direction.nil){
            return nav[d.getVal()];
        }else{
            return null;
        }
    }

    @Override
    public void land() {
    }
}
