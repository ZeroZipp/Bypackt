package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.util.Keys;
import net.minecraft.client.Minecraft;

public class Autowalk extends Module {
    private Keys keys;

    public Autowalk(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        keys = new Keys();
    }

    public void onDisable() {
        keys.resetPressed(mc.gameSettings.keyBindForward);
    }

    public void onUpdate() {
        keys.setPressed(mc.gameSettings.keyBindForward, true);
    }
}
