package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.util.Keys;
import net.minecraft.client.Minecraft;

public class Autoswim extends Module {
    private Keys keys;

    public Autoswim(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        keys = new Keys();
    }

    public void onDisable() {
        keys.resetPressed(mc.gameSettings.keyBindJump);
    }

    public void onUpdate() {
        keys.resetPressed(mc.gameSettings.keyBindJump);
        if(mc.player.isInWater()) {
            keys.setPressed(mc.gameSettings.keyBindJump, true);
        }
    }
}
