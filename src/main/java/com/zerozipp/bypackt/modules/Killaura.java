package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class Killaura extends Module {
    private boolean hit = false;

    public Killaura(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        list = new Object[][][][] {
            {
                {{"Mode"}, {1}},
                {
                    {0, "Single"},
                    {1, "Multi"}
                }
            },
            {
                {{"Players"}, {true}},
                {
                    {false, "OFF"},
                    {true, "ON"}
                }
            },
            {
                {{"Entitys"}, {true}},
                {
                    {false, "OFF"},
                    {true, "ON"}
                }
            },
            {
                {{"Mobs"}, {true}},
                {
                    {false, "OFF"},
                    {true, "ON"}
                }
            }
        };
    }

    public void runList(int indexIn) {
        if(indexIn == 0) {
            if ((int) list[indexIn][0][1][0] < list[indexIn][1].length-1) {
                list[indexIn][0][1][0] = (int) list[indexIn][0][1][0] + 1;
            } else {
                list[indexIn][0][1][0] = 0;
            }
        }else if(indexIn == 1) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }else if(indexIn == 2) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }else if(indexIn == 3) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }
    }

    public void onUpdate() {
        hit = false;
        for(Entity e : mc.world.loadedEntityList) {
            if((int) list[0][0][1][0] == 1 || ((int) list[0][0][1][0] == 0 && !hit)) {
                if (e instanceof EntityPlayer && (boolean) list[1][0][1][0]) {
                    attack(e);
                }
                if (e instanceof EntityLiving && !(e instanceof EntityMob) && (boolean) list[2][0][1][0]) {
                    attack(e);
                }
                if (e instanceof EntityMob && (boolean) list[3][0][1][0]) {
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