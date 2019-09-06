package com.DanyFids.Model.NPCs;

public class Dialog {
    private static String[] Signs = {
            "Hello world!"
    };

    private static String[] Names = {
            "Test NPC",
            "Sir Husk"
    };

    private static DialogEntry[][] DialogTrees = {
            {new DialogEntry(0,0,"Welcome to the game! I'm a totally innocent NPC!", true), new DialogEntry(0,1, "Right....", false)}
    };


    public static String SignDialog(int id){
        if(id >=0 && id < Signs.length){
            return Signs[id];
        }else{
            return "";
        }
    }

    private static DialogEntry[] getDialogTree(int id){
        if(id >= 0 && id < DialogTrees.length) {
            return DialogTrees[id];
        }else{
            DialogEntry[] dt = {};
            return dt;
        }
    }

    public static class DialogEntry{
        public int pictureId;
        public int nameId;
        public String text;
        public boolean right;

        public DialogEntry(int pId, int nId, String t, boolean r){
            this.pictureId = pId;
            this.nameId = nId;
            this.text = t;
            this.right = r;
        }
    }
}
