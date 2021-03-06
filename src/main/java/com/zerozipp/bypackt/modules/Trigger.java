package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class Trigger extends Module {
    private Timer timer;

    public Trigger(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        timer = new Timer();
        settings = new Setting[] {
            new SString("Delay", 2, new String[] {"Off", "Fast", "Normal", "Smooth", "Slow", "Lazy"}),
            new SInteger("Reach", 4, 1, 8),
            new SBoolean("Players", true),
            new SBoolean("Animals", true),
            new SBoolean("Mobs", true)
        };
    }

    public void onUpdate() {
        Entity e = mc.objectMouseOver.entityHit;
        if(timer.hasTime(((SString)settings[0]).value*250)) {
            if (e != null && e != mc.player) {
                if (mc.player.getDistance(e) < ((SInteger) settings[1]).value) {
                    if (e instanceof EntityPlayer && ((SBoolean) settings[2]).active) {
                        attack(e);
                    }
                    if (e instanceof EntityAnimal && ((SBoolean) settings[3]).active) {
                        attack(e);
                    }
                    if (e instanceof EntityMob && ((SBoolean) settings[4]).active) {
                        attack(e);
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
}
