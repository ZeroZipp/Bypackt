package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Bypackt;
import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.RayTraceResult;

public class Distance extends Module {
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

    public Distance(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        bypackt = Bypackt.getBypackt();
        timer = new Timer();
        settings = new Setting[] {
            new SString("Position", 1, new String[] {"Left", "Right"}),
            new SString("Color", 7, new String[] {
                    "Red", "Orange", "Yellow", "Treuse", "Green", "Spring", "Cyan", "Dodge", "Blue", "Purple", "Violet", "Rose"
            }),
            new SBoolean("Rainbow", false)
        };
    }

    public void onOverlay() {
        int color = colors[((SString)settings[1]).value];
        if(timer.hasTime(500)) {
            if (rainbow < colors.length - 1) {
                rainbow += 1;
            } else {
                rainbow = 0;
            }
        }

        ScaledResolution scaled = new ScaledResolution(mc);
        int w = scaled.getScaledWidth();
        int h = scaled.getScaledHeight();

        if(mc.objectMouseOver.entityHit != null) {
            float distance = mc.player.getDistance(mc.objectMouseOver.entityHit);
            String text = "Distance: " + distance;
            int width = bypackt.font.getStringWidth(text);
            if(((SString)settings[0]).value == 0) {
                w = width + 11 + 3;
            }
            mc.ingameGUI.drawRect(w - 3, h - 3, w - width - 11, h - 3 - 16, 0x99000000);
            mc.ingameGUI.drawRect(w - 3, h - 3 - 16, w - width - 11, h - 3 - 16 - 2, ((SBoolean) settings[2]).active ? colors[rainbow] : color);
            mc.ingameGUI.drawString(bypackt.font, text, w - width - 7, h - 3 - 12, 16777215);
        }
    }
}
