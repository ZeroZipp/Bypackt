package com.zerozipp.bypackt.util;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class Render {

    public Render() {
    }

    public void enablePolygon() {
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0F, -1000000);
    }

    public void disablePolygon() {
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GlStateManager.doPolygonOffset(1.0F, 1000000);
        GlStateManager.disablePolygonOffset();
    }

    public void enableLighting() {
        GlStateManager.enableLighting();
    }

    public void disableLighting() {
        GlStateManager.disableLighting();
    }
}