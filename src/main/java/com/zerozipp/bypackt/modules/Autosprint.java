package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;

public class Autosprint extends Module {
    public Autosprint(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
            mc.player.setSprinting(true);
        }
    }
}