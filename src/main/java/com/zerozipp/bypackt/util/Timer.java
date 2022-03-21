package com.zerozipp.bypackt.util;

import net.minecraft.client.Minecraft;

public class Timer {
    public long tick;

    public Timer() {
        tick = 0;
    }

    public boolean hasTime(long time) {
        if(tick >= time) {
            tick = 0;
            return true;
        }
        tick += 1;
        return false;
    }
}