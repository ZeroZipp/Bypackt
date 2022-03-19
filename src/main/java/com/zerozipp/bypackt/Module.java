package com.zerozipp.bypackt;

import net.minecraft.client.Minecraft;

public class Module implements Comparable<Module> {
    public static int MOVEMENT = 0;
    public static int AUTO = 1;
    public static int RENDER = 2;
    public static int COMBAT = 3;
    public static int WORLD = 4;
    public static int SCREEN = 5;

    public Minecraft mc;
    public String name;
    public int id;
    public String hotkey;
    public boolean active;
    public boolean open;
    public Object[][][][] list = {};

    public Module(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        hotkey = "none";
        name = nameIn;
        id = idIn;
        active = activeIn;
        mc = mcIn;
    }

    public void runList(int indexIn) {
        if(list[indexIn][0][1][0] instanceof Integer) {
            if ((int) list[indexIn][0][1][0] < list[indexIn][1].length-1) {
                list[indexIn][0][1][0] = (int) list[indexIn][0][1][0] + 1;
            } else {
                list[indexIn][0][1][0] = 0;
            }
        }else if(list[indexIn][0][1][0] instanceof Boolean) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }
    }

    public void setActive(boolean activeIn) {
        if(activeIn == active) {
            return;
        }else{
            active = activeIn;
            if(active) {
                onEnable();
            }else{
                onDisable();
            }
        }
    }

    public void onEnable() {
    }

    public void onUpdate() {
    }

    public void onRender() {
    }

    public void onDisable() {
    }

    public void setHotkey(String key) {
        hotkey = key;
    }

    @Override
    public int compareTo(Module o) {
        return this.name.compareTo(o.name);
    }
}