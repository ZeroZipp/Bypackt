package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class Glide extends Module {
    public Glide(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
            new SString("Speed", 1, new String[] {"Slow", "Normal", "Fast"}),
            new SInteger("Velocity", 1, 1, 4),
            new SBoolean("Nodamage", true)
        };
    }

    public void onUpdate() {
        if(!mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
            if(mc.player.motionY < -0.1f) {
                mc.player.motionX = 0.0f;
                mc.player.motionY = -((SInteger) settings[1]).value * 0.1f;
                mc.player.motionZ = 0.0f;

                float walk = (((SString) settings[0]).value + 1) * 0.15f;
                if(mc.player.isSprinting()) {
                    walk *= 1.5f;
                }

                float forward = walk;

                float strafe = 0.0f;
                if(mc.gameSettings.keyBindLeft.isKeyDown()) {
                    strafe += walk / 2;
                }
                if(mc.gameSettings.keyBindRight.isKeyDown()) {
                    strafe -= walk / 2;
                }

                float yaw = mc.player.rotationYaw;
                double cos = Math.cos(Math.toRadians(yaw + 90.0F));
                double sin = Math.sin(Math.toRadians(yaw + 90.0F));
                mc.player.motionX = (forward * cos + strafe * sin);
                mc.player.motionZ = (forward * sin - strafe * cos);

                if(((SBoolean) settings[2]).active) {
                    mc.player.connection.sendPacket(new CPacketPlayer(true));
                }
            }
        }
    }
}
