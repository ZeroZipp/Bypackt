package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Crystalaura extends Module {
    private boolean hit = false;
    private boolean place = false;
    private int delay = 0;

    public Crystalaura(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        list = new Object[][][][] {
            {{{"Mode"}, {1}}, {
                {0, "Single"},
                {1, "Multi"}
            }},
            {{{"Ticks"}, {2}}, {
                {0, "Off"},
                {1, "Fast"},
                {2, "Normal"},
                {3, "Smooth"},
                {4, "Slow"},
                {5, "Lazy"}
            }},
            {{{"Players"}, {true}}, {
                {false, "OFF"},
                {true, "ON"}
            }},
            {{{"Entitys"}, {true}}, {
                {false, "OFF"},
                {true, "ON"}
            }},
            {{{"Mobs"}, {true}}, {
                {false, "OFF"},
                {true, "ON"}
            }},
            {{{"Auto"}, {true}}, {
                {false, "OFF"},
                {true, "ON"}
            }}
        };
    }

    public void onUpdate() {
        hit = false;
        place = false;
        if(delay < (int)list[1][0][1][0]*10) {
            delay += 1;
        }else{
            delay = 0;
        }
        for(Entity e : mc.world.loadedEntityList) {
            if(delay == 0) {
                if((boolean) list[5][0][1][0]) {
                    if(e instanceof EntityEnderCrystal) {
                        if (delay == 0 && ((int) list[0][0][1][0] == 1 || ((int) list[0][0][1][0] == 0 && !hit))) {
                            attack(e);
                        }
                    }
                    if(!e.isDead && e.isEntityAlive()) {
                        if (e != mc.player && e instanceof EntityPlayer && (boolean) list[2][0][1][0]) {
                            autoPlace(e);
                        }
                        if (e instanceof EntityLiving && !(e instanceof EntityMob) && (boolean) list[3][0][1][0]) {
                            autoPlace(e);
                        }
                        if (e instanceof EntityMob && (boolean) list[4][0][1][0]) {
                            autoPlace(e);
                        }
                    }
                }
            }
        }
    }

    public void autoPlace(Entity e) {
        Vec3d pvec = e.getPositionVector();
        BlockPos pos = new BlockPos(pvec);

        if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().equals(Items.END_CRYSTAL)) {
            if(!place && e != mc.player) {
                if(!e.isDead && e.isEntityAlive() && mc.player.getDistance(e) < 4) {
                    for(EnumFacing side : EnumFacing.values()) {
                        if(!side.equals(EnumFacing.UP) && !side.equals(EnumFacing.DOWN)) {
                            for(int i = 0; i < 2; i++) {
                                if(place(pos.down(i).offset(side))) {
                                    place = true;
                                    return;
                                }
                                for(EnumFacing side2 : EnumFacing.values()) {
                                    if(!side2.equals(EnumFacing.UP) && !side2.equals(EnumFacing.DOWN)) {
                                        BlockPos p = pos.down(i).offset(side).offset(side2);
                                        if(p.equals(pos.down(i))) {
                                            if(place(p)) {
                                                place = true;
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean place(BlockPos pos) {
        EnumFacing side = EnumFacing.UP;

        if(mc.world.getBlockState(pos).getMaterial().isSolid()) {
            for (EnumHand enumhand : EnumHand.values()) {
                ItemStack itemstack = mc.player.getHeldItem(enumhand);
                int i = itemstack.getCount();
                EnumActionResult enumactionresult = mc.playerController.processRightClickBlock(mc.player, mc.world, pos, side, Vec3d.ZERO, EnumHand.MAIN_HAND);

                if(enumactionresult == EnumActionResult.SUCCESS) {
                    mc.player.swingArm(enumhand);

                    if(!itemstack.isEmpty() && (itemstack.getCount() != i || mc.playerController.isInCreativeMode())) {
                        mc.entityRenderer.itemRenderer.resetEquippedProgress(enumhand);
                    }

                    return true;
                }
            }
        }
        return false;
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