package com.zerozipp.bypackt.util;

import net.minecraft.client.Minecraft;

public class Timer {
    public long lastMS;

    public Timer() {
        lastMS = Minecraft.getSystemTime();
    }

    public boolean hasTime(int time) {
        if(Minecraft.getSystemTime()-lastMS > time) {
            lastMS = Minecraft.getSystemTime();
            return true;
        }else{
            return false;
        }
    }
}