package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;

public class Nobob extends Module {
    public Nobob(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onEnable() {
        mc.gameSettings.viewBobbing = false;
    }

    public void onUpdate() {
        if(mc.gameSettings.viewBobbing) {
            mc.gameSettings.viewBobbing = false;
        }
    }

    public void onDisable() {
        mc.gameSettings.viewBobbing = true;
    }
}
