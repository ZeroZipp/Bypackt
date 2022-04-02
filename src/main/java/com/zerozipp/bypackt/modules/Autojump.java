package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.Minecraft;

public class Autojump extends Module {
    public Autojump(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
                new SBoolean("Move", false)
        };
    }

    public void onUpdate() {
        if(mc.player.onGround) {
            if(!mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
                if(((SBoolean)settings[0]).active) {
                    if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                        mc.player.jump();
                    }
                }else{
                    mc.player.jump();
                }
            }
        }
    }
}
