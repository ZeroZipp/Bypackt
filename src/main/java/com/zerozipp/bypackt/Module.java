package com.zerozipp.bypackt;

import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SInteger;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
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
    public Setting[] settings;

    public Module(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        hotkey = "none";
        name = nameIn;
        id = idIn;
        active = activeIn;
        mc = mcIn;
    }

    public void runList(int indexIn) {
        if(settings[indexIn] instanceof SBoolean) {
            SBoolean b = (SBoolean) settings[indexIn];
            b.setBoolean(!b.active);
        }else if(settings[indexIn] instanceof SInteger) {
            SInteger b = (SInteger) settings[indexIn];
            b.updateInt(false);
        }else if(settings[indexIn] instanceof SString) {
            SString b = (SString) settings[indexIn];
            b.updateStr(false);
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

    public boolean setHotkey(String key) {
        hotkey = key;
        return true;
    }

    @Override
    public int compareTo(Module o) {
        return this.name.compareTo(o.name);
    }
}