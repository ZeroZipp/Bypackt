package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Clickgui;
import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.*;
import net.minecraft.client.Minecraft;

public class Gui extends Module {
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

    public Gui(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
            new SString("Color", 7, new String[] {
                "Red", "Orange", "Yellow", "Treuse", "Green", "Spring", "Cyan", "Dodge", "Blue", "Purple", "Violet", "Rose"
            })
        };
    }

    public void onEnable() {
        Clickgui.setColor((int)colors[((SString)settings[0]).value]);
    }

    public void onUpdate() {
        Clickgui.setColor((int)colors[((SString)settings[0]).value]);
    }

    public void onDisable() {
        Clickgui.resetColor();
    }
}
