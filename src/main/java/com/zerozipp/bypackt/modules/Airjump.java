package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;

public class Airjump extends Module {
    public Airjump(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        if(!Flight.isActive && !Motion.isActive) {
            mc.player.onGround = true;
        }
    }
}
