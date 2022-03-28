package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SInteger;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Rotation;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Crystalaura extends Module {
    private boolean hit = false;
    private boolean place = false;
    private Timer timer;

    public Crystalaura(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        timer = new Timer();
        settings = new Setting[] {
            new SString("Mode", 0, new String[] {"Single", "Multi", "Distance"}),
            new SString("Delay", 2, new String[] {"Off", "Fast", "Normal", "Smooth", "Slow", "Lazy"}),
            new SInteger("Reach", 4, 1, 8),
            new SBoolean("Players", true),
            new SBoolean("Entitys", true),
            new SBoolean("Mobs", true),
            new SBoolean("Auto", true)
        };
    }

    public void onUpdate() {
        hit = false;
        place = false;

        ArrayList<EntityEnderCrystal> crystals = new ArrayList<EntityEnderCrystal>();
        ArrayList<Entity> others = new ArrayList<Entity>();

        List<Entity> list = mc.world.loadedEntityList;
        if(((SString) settings[0]).value == 2) {
            list.sort(Comparator.comparingDouble((entity -> entity.getDistance(mc.player))));
        }

        for(Entity entity : list) {
            if(entity instanceof EntityEnderCrystal) {
                crystals.add((EntityEnderCrystal)entity);
            }else{
                others.add(entity);
            }
        }

        if(timer.hasTime(((SString)settings[1]).value*250)) {
            for(EntityEnderCrystal e : crystals) {
                if(mc.player.getDistance(e) < 4) {
                    if(!hit && !place) {
                        attack(e);
                    }
                }
            }
            if(((SBoolean) settings[5]).active) {
                for (Entity e : others) {
                    if(mc.player.getDistance(e) < ((SInteger) settings[2]).value) {
                        if (e != mc.player && !hit) {
                            if (e instanceof EntityPlayer && ((SBoolean) settings[3]).active) {
                                autoPlace(e);
                            }
                            if (e instanceof EntityAnimal && ((SBoolean) settings[4]).active) {
                                autoPlace(e);
                            }
                            if (e instanceof EntityMob && ((SBoolean) settings[5]).active) {
                                autoPlace(e);
                            }
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
            if(!place && !e.isDead && e.isEntityAlive()) {
                for(EnumFacing side : EnumFacing.values()) {
                    if(!side.equals(EnumFacing.UP) && !side.equals(EnumFacing.DOWN)) {
                        for(int i = -1; i < 2; i++) {
                            if(place(pos.down(i).offset(side))) {
                                place = true;
                                return;
                            }
                            for(EnumFacing side2 : EnumFacing.values()) {
                                if(!side2.equals(EnumFacing.UP) && !side2.equals(EnumFacing.DOWN)) {
                                    BlockPos p = pos.down(i).offset(side).offset(side2);
                                    if(!p.equals(pos.down(i))) {
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

    public boolean place(BlockPos pos) {
        EnumFacing side = EnumFacing.UP;

        if(mc.world.getBlockState(pos).getMaterial().isSolid() && mc.player.getDistance(pos.getX(), pos.getY(), pos.getZ()) < ((SInteger) settings[2]).value) {
            for (EnumHand enumhand : EnumHand.values()) {
                ItemStack itemstack = mc.player.getHeldItem(enumhand);
                int i = itemstack.getCount();

                Rotation rot = Rotation.getLook(mc.player, new Vec3d(pos));
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rot.yaw, rot.pitch, true));

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
        if(!e.isDead && e.isEntityAlive()) {
            Rotation rot = Rotation.getLook(mc.player, e.getPositionVector().add(new Vec3d(0, 1, 0)));
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rot.yaw, rot.pitch, true));

            hit = (((SString) settings[0]).value == 0 || ((SString) settings[0]).value == 2) ? true : hit;
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}