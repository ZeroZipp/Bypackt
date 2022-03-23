package com.zerozipp.bypackt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;

public class Overlay {
    public static int color = 0xf00080ff;
    public static boolean list = true;
    public static boolean info = false;

    public Minecraft mc;
    public Bypackt bypackt;

    public Overlay(Minecraft mcIn, Bypackt bypacktIn) {
        mc = mcIn;
        bypackt = bypacktIn;
    }

    public void onUpdate() {
        ScaledResolution scaled = new ScaledResolution(mc);
        int w = scaled.getScaledWidth();

        int fps = Minecraft.getDebugFPS();
        Vec3d pvec = mc.player.getPositionVector();
        BlockPos pos = new BlockPos(pvec);

        if(info) {
            mc.ingameGUI.drawString(bypackt.font, bypackt.name, 5, 5, color);
            mc.ingameGUI.drawString(bypackt.font, bypackt.version, bypackt.font.getStringWidth(bypackt.name) + 8, 5, 16777215);
            mc.ingameGUI.drawString(bypackt.font, "X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ(), 5, 15, 16777215);
            mc.ingameGUI.drawString(bypackt.font, "Fps: " + fps, 5, 25, 16777215);
        }

        int c = 0;
        ArrayList<Module> actives = bypackt.modules;
        actives.sort((z, y) -> -(
            bypackt.font.getStringWidth(z.name) - bypackt.font.getStringWidth(y.name)
        ));
        if(list) {
            for (Module m : actives) {
                if (m.active) {
                    String text = m.name;
                    mc.ingameGUI.drawString(bypackt.font, text, w - bypackt.font.getStringWidth(text) - 5, 5 + (c * 10), 16777215);
                    c += 1;
                }
            }
        }
    }
}
