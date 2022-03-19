package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
    public Scaffold(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        Vec3d pvec = mc.player.getPositionVector();
        BlockPos pos = new BlockPos(pvec).down();
        if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            if(mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
                if(!place(pos)) {
                    for(EnumFacing side : EnumFacing.values()) {
                        if(!side.equals(EnumFacing.UP) && !side.equals(EnumFacing.DOWN)) {
                            if(mc.world.getBlockState(pos.offset(side)).getMaterial().isReplaceable()) {
                                if(place(pos.offset(side))) {
                                    place(pos);
                                    return;
                                }
                            }
                        }
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
                    EnumActionResult enumactionresult = mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, Vec3d.ZERO, EnumHand.MAIN_HAND);

                    if(enumactionresult == EnumActionResult.SUCCESS) {
                        mc.player.swingArm(enumhand);

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
