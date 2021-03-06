package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class Nofall extends Module {
    public Nofall(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
            new SString("Mode", 1, new String[] {"Normal", "Fixed"}),
            new SInteger("Distance", 1, 1, 2)
        };
    }

    public void onUpdate() {
        if(((SString)settings[0]).value == 0) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }else if(((SString)settings[0]).value == 1) {
            if(mc.player.fallDistance > ((SInteger)settings[1]).value) {
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            }
        }
    }
}
