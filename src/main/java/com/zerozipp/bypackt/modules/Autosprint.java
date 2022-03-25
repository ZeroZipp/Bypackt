package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.Minecraft;

public class Autosprint extends Module {
    public Autosprint(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
                new SString("Mode", 0, new String[] {"Normal", "All"})
        };
    }

    public void onUpdate() {
        if(mc.gameSettings.keyBindForward.isKeyDown()) {
            mc.player.setSprinting(true);
        }
        if(((SString)settings[0]).value == 1) {
            if(mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                mc.player.setSprinting(true);
            }
        }
    }
}