package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Keys;
import net.minecraft.client.Minecraft;

public class Sneak extends Module {
    private Keys keys;

    public Sneak(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        keys = new Keys();
        settings = new Setting[] {
            new SBoolean("Ground", false)
        };
    }

    public void onDisable() {
        keys.resetPressed(mc.gameSettings.keyBindSneak);
    }

    public void onUpdate() {
        if(((SBoolean)settings[0]).active) {
            if(mc.player.onGround) {
                keys.setPressed(mc.gameSettings.keyBindSneak, true);
            }else {
                keys.setPressed(mc.gameSettings.keyBindSneak, false);
            }
        }else{
            keys.setPressed(mc.gameSettings.keyBindSneak, true);
        }
    }
}
