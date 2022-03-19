package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;

public class Autojump extends Module {
    public Autojump(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        if(mc.player.onGround) {
            mc.player.jump();
        }
    }
}
