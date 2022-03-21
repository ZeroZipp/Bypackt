package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Clickgui;
import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.Overlay;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.Minecraft;

public class Hud extends Module {
    public Object[][] colors = {
            {0xf0ff0000, "Red"},
            {0xf0ff8000, "Orange"},
            {0xf0ffff00, "Yellow"},
            {0xf080ff00, "Treuse"},
            {0xf000ff00, "Green"},
            {0xf000ff80, "Spring"},
            {0xf000ffff, "Cyan"},
            {0xf00080ff, "Dodger"},
            {0xf00000ff, "Blue"},
            {0xf08000ff, "Purple"},
            {0xf0ff00ff, "Violet"},
            {0xf0ff0080, "Magenta"}
    };

    public Hud(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        settings = new Setting[] {
                new SString("Color", 1, new String[] {
                        "Red", "Orange", "Yellow", "Treuse", "Green", "Spring", "Cyan", "Dodge", "Blue", "Purple", "Violet", "Rose"
                }),
                new SBoolean("List", true),
                new SBoolean("Info", true)
        };
    }

    public void onEnable() {
        Clickgui.color = (int)colors[((SString)settings[0]).value][0];
        Overlay.list = ((SBoolean)settings[1]).active;
        Overlay.info = ((SBoolean)settings[2]).active;
    }

    public void onUpdate() {
        Clickgui.color = (int)colors[((SString)settings[0]).value][0];
        Overlay.list = ((SBoolean)settings[1]).active;
        Overlay.info = ((SBoolean)settings[2]).active;
    }

    public void onDisable() {
        Overlay.color = 0xf00080ff;
        Overlay.list = true;
        Overlay.info = true;
    }
}
