package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

public class Flight extends Module {
    public float speed = 0.3f;

    public Flight(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        mc.player.motionX = 0.0f;
        mc.player.motionY = 0.0f;
        mc.player.motionZ = 0.0f;

        float walk = speed;
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
        mc.player.motionX = (forward * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * Math.sin(Math.toRadians(yaw + 90.0F)));
        mc.player.motionZ = (forward * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * Math.cos(Math.toRadians(yaw + 90.0F)));
        mc.player.connection.sendPacket(new CPacketPlayer(true));
    }
}
