package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;

public class Motion extends Module {
    public static boolean isActive;

    public Motion(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    @Override
    public void setActive(boolean activeIn) {
        super.setActive(activeIn);
        isActive = active;
    }

    public void onUpdate() {
        mc.player.motionY = 0.0f;
    }
}
