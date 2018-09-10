package com.DanyFids.Model;

import com.DanyFids.Model.Weapons.WpnType;

/**
 * Created by Daniel on 10/15/2017.
 */
public enum ArmorType {
    light, medium, heavy;

    public int resistance(WpnType w){
        switch(this){
            case light:
                switch(w){
                    case Blunt:
                        return 1;
                    case Piercing:
                        return 1;
                    case Slashing:
                        return 1;
                }
                break;
            case medium:
                switch(w){
                    case Blunt:
                        return 1;
                    case Piercing:
                        return 1;
                    case Slashing:
                        return 1;
                }
                break;
            case heavy:
                switch(w){
                    case Blunt:
                        return 1;
                    case Piercing:
                        return 1;
                    case Slashing:
                        return 1;
                }
                break;
        }

        return 1;
    }
}
