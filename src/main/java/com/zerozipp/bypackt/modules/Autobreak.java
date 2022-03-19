package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class Autobreak extends Module {
    public Autobreak(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = mc.objectMouseOver.getBlockPos();

            if (mc.world.getBlockState(blockpos).getMaterial() != Material.AIR && mc.playerController.onPlayerDamageBlock(blockpos, mc.objectMouseOver.sideHit))
            {
                mc.effectRenderer.addBlockHitEffects(blockpos, mc.objectMouseOver.sideHit);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
        else
        {
            mc.playerController.resetBlockRemoving();
        }
    }
}
