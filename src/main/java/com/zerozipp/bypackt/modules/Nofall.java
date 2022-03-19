package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class Nofall extends Module {
    public Nofall(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        mc.player.connection.sendPacket(new CPacketPlayer(true));
    }
}
