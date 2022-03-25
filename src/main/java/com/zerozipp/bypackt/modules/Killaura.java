package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Rotation;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;

public class Killaura extends Module {
    private boolean hit = false;
    private Timer timer;

    public Killaura(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        timer = new Timer();
        settings = new Setting[] {
            new SString("Mode", 0, new String[] {"Single", "Multi", "Distance"}),
            new SString("Delay", 2, new String[] {"Off", "Fast", "Normal", "Smooth", "Slow", "Lazy"}),
            new SBoolean("Players", true),
            new SBoolean("Entitys", true),
            new SBoolean("Mobs", true)
        };
    }

    public void onUpdate() {
        hit = false;
        List<Entity> list = mc.world.loadedEntityList;

        if(((SString) settings[0]).value == 2) {
            list.sort(Comparator.comparingDouble((entity -> entity.getDistance(mc.player))));
        }
        if(timer.hasTime(((SString)settings[1]).value*250)) {
            for(Entity e : mc.world.loadedEntityList) {
                if(!hit && e != mc.player) {
                    if(mc.player.getDistance(e) < 4) {
                        if(e instanceof EntityPlayer && ((SBoolean) settings[2]).active) {
                            attack(e);
                        }
                        if(e instanceof EntityAnimal && ((SBoolean) settings[3]).active) {
                            attack(e);
                        }
                        if(e instanceof EntityMob && ((SBoolean) settings[4]).active) {
                            attack(e);
                        }
                    }
                }
            }
        }
    }

    public void attack(Entity e) {
        if(!e.isDead && e.isEntityAlive()) {
            Rotation rot = Rotation.getLook(mc.player, e.getPositionVector().add(new Vec3d(0, e.getEyeHeight(), 0)));
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rot.yaw, rot.pitch, true));

            hit = (((SString) settings[0]).value == 0 || ((SString) settings[0]).value == 2) ? true : hit;
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
