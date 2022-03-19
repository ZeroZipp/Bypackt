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
    private int delay = 0;

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
                {{"Delay"}, {5}},
                {
                    {0, "0"},
                    {1, "1"},
                    {2, "2"},
                    {3, "3"},
                    {4, "4"},
                    {5, "5"},
                    {6, "6"},
                    {7, "7"},
                    {8, "8"},
                    {9, "9"},
                    {10, "10"},
                    {11, "11"},
                    {12, "12"},
                    {13, "13"},
                    {14, "14"},
                    {15, "15"}
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
            if ((int) list[indexIn][0][1][0] < list[indexIn][1].length-1) {
                list[indexIn][0][1][0] = (int) list[indexIn][0][1][0] + 1;
            } else {
                list[indexIn][0][1][0] = 0;
            }
        }else if(indexIn == 2) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }else if(indexIn == 3) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }else if(indexIn == 4) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }
    }

    public void onUpdate() {
        hit = false;
        if(delay == (int)list[1][0][1][0]-1) {
            delay += 1;
        }else{
            delay = 0;
        }
        for(Entity e : mc.world.loadedEntityList) {
            if(((int) list[0][0][1][0] == 1 || ((int) list[0][0][1][0] == 0 && !hit)) && delay == 0) {
                if (e instanceof EntityPlayer && (boolean) list[2][0][1][0]) {
                    attack(e);
                }
                if (e instanceof EntityLiving && !(e instanceof EntityMob) && (boolean) list[3][0][1][0]) {
                    attack(e);
                }
                if (e instanceof EntityMob && (boolean) list[4][0][1][0]) {
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
