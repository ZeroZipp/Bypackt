package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;

public class Fullbright extends Module {
    public Fullbright(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    public void onUpdate() {
        if(mc.gameSettings.gammaSetting != 100) {
            mc.gameSettings.gammaSetting = 100;
        }
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 0;
    }
}
