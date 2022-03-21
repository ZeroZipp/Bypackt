package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
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
    private Timer timer;

    public Crystalaura(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        timer = new Timer();
        settings = new Setting[] {
            new SString("Mode", 1, new String[] {"Single", "Multi"}),
            new SString("Delay", 2, new String[] {"Off", "Fast", "Normal", "Smooth", "Slow", "Lazy"}),
            new SBoolean("Players", true),
            new SBoolean("Entitys", true),
            new SBoolean("Mobs", true),
            new SBoolean("Auto", true)
        };
    }

    public void onUpdate() {
        hit = false;
        place = false;
        for(Entity e : mc.world.loadedEntityList) {
            if(timer.hasTime(((SString)settings[1]).value, true)) {
                if(e instanceof EntityEnderCrystal) {
                    if (((SString)settings[0]).value == 1 || (((SString)settings[0]).value == 0 && !hit)) {
                        attack(e);
                    }
                }
                if(((SBoolean)settings[5]).active) {
                    if(!e.isDead && e.isEntityAlive()) {
                        if (e instanceof EntityPlayer && ((SBoolean)settings[2]).active) {
                            autoPlace(e);
                        }
                        if (e instanceof EntityAnimal && ((SBoolean)settings[3]).active) {
                            autoPlace(e);
                        }
                        if (e instanceof EntityMob && ((SBoolean)settings[4]).active) {
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
        if(!e.isDead && e.isEntityAlive() && mc.player.getDistance(e) < 4) {
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.resetCooldown();
            hit = true;
        }
    }
}