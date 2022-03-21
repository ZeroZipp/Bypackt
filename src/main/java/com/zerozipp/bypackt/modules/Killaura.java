package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class Killaura extends Module {
    private boolean hit = false;
    private Timer timer;

    public Killaura(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        timer = new Timer();
        settings = new Setting[] {
            new SString("Mode", 1, new String[] {"Single", "Multi"}),
            new SString("Delay", 2, new String[] {"Off", "Fast", "Normal", "Smooth", "Slow", "Lazy"}),
            new SBoolean("Players", true),
            new SBoolean("Entitys", true),
            new SBoolean("Mobs", true)
        };
    }

    public void onUpdate() {
        hit = false;
        for(Entity e : mc.world.loadedEntityList) {
            if(((((SString)settings[0]).value == 1) || (((SString)settings[0]).value == 0 && !hit)) && timer.hasTime(1000/(((SString)settings[1]).value*3), true)) {
                if (e instanceof EntityPlayer && (boolean) ((SBoolean)settings[2]).active) {
                    attack(e);
                }
                if (e instanceof EntityLiving && !(e instanceof EntityMob) && ((SBoolean)settings[3]).active) {
                    attack(e);
                }
                if (e instanceof EntityMob && (boolean) ((SBoolean)settings[4]).active) {
                    attack(e);
                }
            }
        }
    }

    public void attack(Entity e) {
        if(e != mc.player) {
            if(!e.isDead && e.isEntityAlive() && mc.player.getDistance(e) < 4) {
                mc.playerController.attackEntity(mc.player, e);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.resetCooldown();
                hit = true;
            }
        }
    }
}
