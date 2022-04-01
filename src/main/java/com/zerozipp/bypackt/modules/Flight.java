package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class Flight extends Module {
    public Flight(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
                new SString("Speed", 1, new String[] {"Slow", "Normal", "Fast"}),
                new SBoolean("Nodamage", true)
        };
    }

    public void onUpdate() {
        mc.player.motionX = 0.0f;
        mc.player.motionY = 0.0f;
        mc.player.motionZ = 0.0f;

        float walk = (((SString)settings[0]).value+1)*0.15f;
        if (mc.player.isSprinting()) {
            walk *= 1.5f;
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += walk;
        }
        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= walk;
        }

        float forward = 0.0f;
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            forward += walk;
        }
        if (mc.gameSettings.keyBindBack.isKeyDown()) {
            forward -= walk;
        }

        float strafe = 0.0f;
        if (mc.gameSettings.keyBindLeft.isKeyDown()) {
            strafe += walk;
        }
        if (mc.gameSettings.keyBindRight.isKeyDown()) {
            strafe -= walk;
        }

        float yaw = mc.player.rotationYaw;
        double cos = Math.cos(Math.toRadians(yaw + 90.0F));
        double sin = Math.sin(Math.toRadians(yaw + 90.0F));
        mc.player.motionX = (forward * cos + strafe * sin);
        mc.player.motionZ = (forward * sin - strafe * cos);

        if(((SBoolean)settings[1]).active) {
            mc.player.connection.sendPacket(new CPacketPlayer(true));
        }
    }
}
