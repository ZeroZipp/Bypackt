package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Bypackt;
import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Timer;
import com.zerozipp.bypackt.util.Version;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.util.ArrayList;

public class Hud extends Module {
    public Bypackt bypackt;
    public int rainbow = 0;
    private Timer timer;
    public int[] colors = {
            0xf0ff0000,
            0xf0ff8000,
            0xf0ffff00,
            0xf080ff00,
            0xf000ff00,
            0xf000ff80,
            0xf000ffff,
            0xf00080ff,
            0xf00000ff,
            0xf08000ff,
            0xf0ff00ff,
            0xf0ff0080
    };

    public Hud(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        bypackt = Bypackt.getBypackt();
        timer = new Timer();
        settings = new Setting[] {
                new SString("Color", 7, new String[] {
                        "Red", "Orange", "Yellow", "Treuse", "Green", "Spring", "Cyan", "Dodge", "Blue", "Purple", "Violet", "Rose"
                }),
                new SBoolean("Rainbow", false),
                new SBoolean("List", true),
                new SBoolean("Info", true)
        };
    }

    public int getRainbow(int offsetIn) {
        int color = rainbow+offsetIn;
        while(color > colors.length-1) {
            color -= colors.length-1;
        }
        return color;
    }

    public void onOverlay() {
        if(timer.hasTime(500)) {
            if (rainbow < colors.length - 1) {
                rainbow += 1;
            } else {
                rainbow = 0;
            }
        }

        int color = colors[((SString)settings[0]).value];
        boolean list = ((SBoolean)settings[2]).active;
        boolean info = ((SBoolean)settings[3]).active;

        ScaledResolution scaled = new ScaledResolution(mc);
        int w = scaled.getScaledWidth();

        int fps = Minecraft.getDebugFPS();
        Vec3d pvec = mc.player.getPositionVector();
        BlockPos pos = new BlockPos(pvec);

        if(info) {
            Version version = bypackt.getVersion();
            String text = version.getVersion() + " : " + (version.isStable() ? "stable" : "nightly");
            int width = bypackt.font.getStringWidth(bypackt.name) + 2 + bypackt.font.getStringWidth(text);
            mc.ingameGUI.drawRect(3, 3, width + 11, 3 + 16, 0x99000000);
            mc.ingameGUI.drawRect(3, 3 + 16, width + 11, 3 + 16 + 2, ((SBoolean)settings[1]).active ? colors[getRainbow(0)] : color);
            mc.ingameGUI.drawString(bypackt.font, bypackt.name, 7, 7, color);
            mc.ingameGUI.drawString(bypackt.font, text, bypackt.font.getStringWidth(bypackt.name) + 9, 7, 16777215);

            mc.ingameGUI.drawString(bypackt.font, "X: " + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ(), 5, 24, 16777215);
            mc.ingameGUI.drawString(bypackt.font, "Fps: " + fps, 5, 34, 16777215);
        }

        int c = 0;
        ArrayList<Module> actives = (ArrayList<Module>) bypackt.modules.clone();
        actives.sort((z, y) -> -(
                bypackt.font.getStringWidth(z.name) - bypackt.font.getStringWidth(y.name)
        ));
        if(list) {
            for (Module m : actives) {
                if (m.active) {
                    String text = m.name;
                    mc.ingameGUI.drawRect(w - 3, 3 + (c * 16), w - bypackt.font.getStringWidth(text) - 11, 3 + (c * 16) + 16, 0x99000000);
                    mc.ingameGUI.drawRect(w - bypackt.font.getStringWidth(text) - 13, 3 + (c * 16), w - bypackt.font.getStringWidth(text) - 11, 3 + (c * 16) + 16, ((SBoolean)settings[1]).active ? colors[getRainbow(c)] : color);
                    mc.ingameGUI.drawString(bypackt.font, text, w - bypackt.font.getStringWidth(text) - 7, 7 + (c * 16), 16777215);
                    c += 1;
                }
            }
        }
    }
}
