package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;

public class Timer extends Module {
    public Timer(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }
    public void onUpdate() {
        mc.player.onUpdate();
    }
}