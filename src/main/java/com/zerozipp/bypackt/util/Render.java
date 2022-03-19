package com.zerozipp.bypackt.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;

public class Render {
    public Minecraft mc;

    public Render(Minecraft mcIn) {
        mc = mcIn;
    }

    public void boundingBox(Entity entityIn, boolean tracers) {
        if(entityIn == mc.player) {
            return;
        }
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(0.5f);

        double x = mc.getRenderManager().viewerPosX;
        double y = mc.getRenderManager().viewerPosY;
        double z = mc.getRenderManager().viewerPosZ;
        int render = 1;
        if(mc.gameSettings.thirdPersonView == 2) {
            render = -1;
        }

        AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
        RenderGlobal.drawBoundingBox(axisalignedbb.minX - x, axisalignedbb.minY - y, axisalignedbb.minZ - z, axisalignedbb.maxX - x, axisalignedbb.maxY - y, axisalignedbb.maxZ - z, 0.0F, 1.0F, 0.0F, 1.0F);
        drawTracer(axisalignedbb.getCenter().x - x, axisalignedbb.getCenter().y - y, axisalignedbb.getCenter().z - z, mc.player.getLookVec().x*render, mc.player.getEyeHeight() + mc.player.getLookVec().y*render, mc.player.getLookVec().z*render, 0.0F, 1.0F, 0.0F, tracers ? 1.0F : 0.0F);

        GlStateManager.glLineWidth(2);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void drawTracer(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);

        buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();

        tessellator.draw();
    }
}
