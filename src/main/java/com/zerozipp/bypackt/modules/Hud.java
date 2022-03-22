package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Clickgui;
import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.Overlay;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.Minecraft;

public class Hud extends Module {
    public Object[] colors = {
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
        settings = new Setting[] {
                new SString("Color", 7, new String[] {
                        "Red", "Orange", "Yellow", "Treuse", "Green", "Spring", "Cyan", "Dodge", "Blue", "Purple", "Violet", "Rose"
                }),
                new SBoolean("List", true),
                new SBoolean("Info", true)
        };
    }

    public void onEnable() {
        Overlay.color = (int)colors[((SString)settings[0]).value];
        Overlay.list = ((SBoolean)settings[1]).active;
        Overlay.info = ((SBoolean)settings[2]).active;
    }

    public void onUpdate() {
        Overlay.color = (int)colors[((SString)settings[0]).value];
        Overlay.list = ((SBoolean)settings[1]).active;
        Overlay.info = ((SBoolean)settings[2]).active;
    }

    public void onDisable() {
        Overlay.color = 0xf00080ff;
        Overlay.list = true;
        Overlay.info = true;
    }
}
