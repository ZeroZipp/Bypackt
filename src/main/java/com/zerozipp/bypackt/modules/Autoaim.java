package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import com.zerozipp.bypackt.util.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import java.util.Comparator;
import java.util.List;

public class Autoaim extends Module {
    private boolean done = false;

    public Autoaim(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
                new SString("Mode", 0, new String[] {"Single", "Distance"}),
                new SInteger("Reach", 4, 1, 8),
                new SBoolean("Players", true),
                new SBoolean("Entitys", true),
                new SBoolean("Mobs", true)
        };
    }

    public void onUpdate() {
        done = false;
        List<Entity> list = mc.world.loadedEntityList;

        if(((SString) settings[0]).value == 1) {
            list.sort(Comparator.comparingDouble((entity -> entity.getDistance(mc.player))));
        }
        for(Entity e : mc.world.loadedEntityList) {
            if(!done && e != mc.player) {
                if(mc.player.getDistance(e) < ((SInteger) settings[1]).value) {
                    if(e instanceof EntityPlayer && ((SBoolean) settings[2]).active) {
                        rotate(e);
                    }
                    if(e instanceof EntityAnimal && ((SBoolean) settings[3]).active) {
                        rotate(e);
                    }
                    if(e instanceof EntityMob && ((SBoolean) settings[4]).active) {
                        rotate(e);
                    }
                }
            }
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
