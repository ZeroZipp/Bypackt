package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class Nofall extends Module {
    public Nofall(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
            new SString("Mode", 1, new String[] {"Normal", "Fixed"}),
            new SString("Distance", 0, new String[] {"Min", "Max"})
        };
    }

    public void onUpdate() {
        if(((SString)settings[0]).value == 0) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }else if(((SString)settings[0]).value == 1) {
            if(mc.player.fallDistance < (((SString)settings[1]).value+1)*10) {
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            }
        }
    }
}
