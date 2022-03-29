package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Bypackt;
import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SInteger;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Keys;
import com.zerozipp.bypackt.util.Rotation;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import java.util.Comparator;
import java.util.List;

public class Autopvp extends Module {
    private boolean done = false;
    private Keys keys;
    private Timer timer;

    public Autopvp(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        timer = new Timer();
        keys = new Keys();
        settings = new Setting[] {
            new SString("Mode", 0, new String[] {"Single", "Distance"}),
            new SString("Delay", 2, new String[] {"Off", "Fast", "Normal", "Smooth", "Slow", "Lazy"}),
            new SInteger("Reach", 4, 1, 8),
            new SBoolean("Jump", false),
            new SBoolean("Players", true),
            new SBoolean("Entitys", true),
            new SBoolean("Mobs", true)
        };
    }

    public void onDisable() {
        keys.resetPressed(mc.gameSettings.keyBindForward);
    }

    public void onUpdate() {
        done = false;
        List<Entity> list = mc.world.loadedEntityList;
        keys.resetPressed(mc.gameSettings.keyBindForward);

        if(mc.player.onGround && ((SBoolean) settings[3]).active) {
            if(!mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
                mc.player.jump();
            }
        }

        if(((SString) settings[0]).value == 1) {
            list.sort(Comparator.comparingDouble((entity -> entity.getDistance(mc.player))));
        }
        Entity e = mc.objectMouseOver.entityHit;
        for(Entity target : mc.world.loadedEntityList) {
            if(!done && target != mc.player) {
                if(target instanceof EntityPlayer && ((SBoolean) settings[4]).active) {
                    rotate(target);
                    keys.setPressed(mc.gameSettings.keyBindForward, true);
                    if(timer.hasTime(((SString)settings[1]).value*250)) {
                        if (e != null && e.equals(target)) {
                            if (mc.player.getDistance(e) < ((SInteger) settings[2]).value) {
                                attack(e);
                            }
                        }
                    }
                }
                if(target instanceof EntityAnimal && ((SBoolean) settings[5]).active) {
                    rotate(target);
                    keys.setPressed(mc.gameSettings.keyBindForward, true);
                    if(timer.hasTime(((SString)settings[1]).value*250)) {
                        if (e != null && e.equals(target)) {
                            if (mc.player.getDistance(e) < ((SInteger) settings[2]).value) {
                                attack(e);
                            }
                        }
                    }
                }
                if(target instanceof EntityMob && ((SBoolean) settings[6]).active) {
                    rotate(target);
                    keys.setPressed(mc.gameSettings.keyBindForward, true);
                    if(timer.hasTime(((SString)settings[1]).value*250)) {
                        if (e != null && e.equals(target)) {
                            if (mc.player.getDistance(e) < ((SInteger) settings[2]).value) {
                                attack(e);
                            }
                        }
                    }
                }
            }
        }
    }

    public void attack(Entity e) {
        if(!e.isDead && e.isEntityAlive()) {
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    public void rotate(Entity e) {
        if(!e.isDead && e.isEntityAlive()) {
            Rotation rot = Rotation.getLook(mc.player, e.getPositionVector().add(new Vec3d(0, e.getEyeHeight(), 0)));
            mc.player.rotationPitch = rot.pitch;
            mc.player.rotationYaw = rot.yaw;
            done = true;
        }
    }
}
