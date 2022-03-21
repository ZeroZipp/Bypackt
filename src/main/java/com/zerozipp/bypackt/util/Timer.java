package com.zerozipp.bypackt.util;

public class Timer {
    public long lastMS;

    public Timer() {
        lastMS = System.currentTimeMillis();
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean hasTime(long time, boolean reset) {
        if(System.currentTimeMillis()-lastMS > time) {
            if(reset) {
                reset();
            }
            return true;
        }
        return false;
    }
}