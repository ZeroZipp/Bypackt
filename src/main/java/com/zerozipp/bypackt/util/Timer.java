package com.zerozipp.bypackt.util;

import net.minecraft.client.Minecraft;

public class Timer {
    public long lastMS;

    public Timer() {
        lastMS = Minecraft.getSystemTime();
    }

    public void reset() {
        lastMS = Minecraft.getSystemTime();
    }

    public boolean hasTime(long time, boolean reset) {
        if(Minecraft.getSystemTime()-lastMS > time) {
            if(reset) {
                reset();
            }
            return true;
        }
        return false;
    }
}