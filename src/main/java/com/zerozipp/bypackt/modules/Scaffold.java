package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import com.zerozipp.bypackt.util.Rotation;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
    public Scaffold(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
                new SString("Mode", 0, new String[] {"Normal", "Legit"}),
                new SBoolean("Tower", false)
        };
    }

    public void onUpdate() {
        Vec3d pvec = mc.player.getPositionVector();
        BlockPos pos = new BlockPos(pvec).down();
        if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            if(mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
                if(!place(pos)) {
                    if(((SString)settings[0]).value == 0) {
                        for (EnumFacing side : EnumFacing.values()) {
                            if (!side.equals(EnumFacing.UP) && !side.equals(EnumFacing.DOWN)) {
                                if (mc.world.getBlockState(pos.offset(side)).getMaterial().isReplaceable()) {
                                    if (place(pos.offset(side))) {
                                        place(pos);
                                        return;
                                    }
                                }
                            }
                        }
                    }else if(((SString)settings[0]).value == 1) {
                        return;
                    }
                }
            }
        }
    }

    public boolean place(BlockPos pos) {
        for(EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();

            if(mc.world.getBlockState(neighbor).getMaterial().isSolid()) {
                for (EnumHand enumhand : EnumHand.values()) {
                    ItemStack itemstack = mc.player.getHeldItem(enumhand);
                    int i = itemstack.getCount();

                    Vec3d block = new Vec3d(BlockPos.ORIGIN.offset(side2)).scale(0.5f);
                    Vec3d center = new Vec3d(new BlockPos(1, 1, 1)).scale(0.5f);
                    Rotation rot = Rotation.getLook(mc.player, new Vec3d(neighbor).add(center).add(block));
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rot.yaw, rot.pitch, true));

                    EnumActionResult enumactionresult = mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, Vec3d.ZERO, EnumHand.MAIN_HAND);

                    if(enumactionresult == EnumActionResult.SUCCESS) {
                        mc.player.swingArm(enumhand);
                        if(((SBoolean)settings[1]).active && !Motion.isActive && !Flight.isActive) {
                            if(mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
                                mc.player.onGround = true;
                                mc.player.jump();
                            }
                        }

                        if(!itemstack.isEmpty() && (itemstack.getCount() != i || mc.playerController.isInCreativeMode())) {
                            mc.entityRenderer.itemRenderer.resetEquippedProgress(enumhand);
                        }

                        return true;
                    }
                }
            }
        }
        return false;
    }
}
