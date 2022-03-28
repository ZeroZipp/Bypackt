package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Bypackt;
import com.zerozipp.bypackt.Clickgui;
import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;

public class Tabgui extends Module {
    public Bypackt bypackt;
    public int[] select = {0, 0};
    public boolean tab = false;
    public boolean pressed = false;
    public int xOff, yOff;
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

    public ArrayList<ArrayList<Module>> list = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    public static Object[][] module = {
            {"Movement"},
            {"Auto"},
            {"Render"},
            {"Combat"},
            {"World"},
            {"Screen"}
    };

    public Tabgui(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        bypackt = Bypackt.getBypackt();
        settings = new Setting[] {
                new SString("Color", 7, new String[] {
                        "Red", "Orange", "Yellow", "Treuse", "Green", "Spring", "Cyan", "Dodge", "Blue", "Purple", "Violet", "Rose"
                })
        };
        xOff = 8;
        yOff = 50;
        ArrayList<Module> modules = bypackt.modules;
        for(Object[] l : module) {
            list.add(new ArrayList<>());
            names.add((String)l[0]);
        }
        for(Module m : modules) {
            if(m.id == Module.MOVEMENT) {
                list.get(0).add(m);
            }else if(m.id == Module.AUTO) {
                list.get(1).add(m);
            }else if(m.id == Module.RENDER) {
                list.get(2).add(m);
            }else if(m.id == Module.COMBAT) {
                list.get(3).add(m);
            }else if(m.id == Module.WORLD) {
                list.get(4).add(m);
            }else if(m.id == Module.SCREEN) {
                list.get(5).add(m);
            }
        }
        for(ArrayList<Module> l : list) {
            Collections.sort(l);
        }
    }

    public void onUpdate() {
        select[1] = tab ? select[1] : 0;
        if(mc.currentScreen == null) {
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                if (!pressed) {
                    pressed = true;
                    if (!tab) {
                        if (select[0] < module.length - 1) {
                            select[0] += 1;
                        }
                    } else {
                        if (select[1] < list.get(select[0]).size() - 1) {
                            select[1] += 1;
                        }
                    }
                }
            } else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                if (!pressed) {
                    pressed = true;
                    if (!tab) {
                        if (select[0] > 0) {
                            select[0] -= 1;
                        }
                    } else {
                        if (select[1] > 0) {
                            select[1] -= 1;
                        }
                    }
                }
            } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                if (!pressed) {
                    pressed = true;
                    tab = false;
                }
            } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                if (!pressed) {
                    pressed = true;
                    tab = true;
                }
            } else if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Keyboard.isKeyDown(Keyboard.KEY_NUMPADENTER)) {
                if (!pressed) {
                    pressed = true;
                    if (tab) {
                        ArrayList<Module> mod = list.get(select[0]);
                        mod.get(select[1]).setActive(!mod.get(select[1]).active);
                    }
                }
            } else {
                pressed = false;
            }
        }
    }

    public void onOverlay() {
        int size = 80;
        int height = 14;
        int fontOff = 3;
        int color = colors[((SString)settings[0]).value];
        select[1] = tab ? select[1] : 0;

        int c = 0;
        mc.ingameGUI.drawRect(xOff-1, yOff - 2, xOff + size, yOff, 0x99000000);
        mc.ingameGUI.drawRect(xOff-1, yOff, xOff + size, yOff + (names.size()*height), 0x99000000);
        mc.ingameGUI.drawRect(xOff-1, yOff + (names.size()*height), xOff + size, yOff + (names.size()*height) + 2, 0x99000000);

        mc.ingameGUI.drawRect(xOff + 1, yOff + (select[0]*height), xOff + 3, yOff + (select[0]*height) + height, color);
        for(String n : names) {
            mc.ingameGUI.drawString(bypackt.font, n, xOff + ((select[0] == c) ? 2 : 0) + 5, yOff + (c*height) + fontOff, (select[0] == c) ? color : 16777215);
            c += 1;
        }

        if(tab) {
            int off = select[0];
            ArrayList<Module> mod = list.get(off);

            c = 0;
            mc.ingameGUI.drawRect(xOff + size, yOff + (off*height) - 2, xOff + size*2, yOff + (off*height), 0x99000000);
            mc.ingameGUI.drawRect(xOff + size, yOff + (off*height), xOff + size*2, yOff + (off*height) + (mod.size()*height), 0x99000000);
            mc.ingameGUI.drawRect(xOff + size, yOff + (off*height) + (mod.size()*height), xOff + size*2, yOff + (off*height) + (mod.size()*height) + 2, 0x99000000);

            mc.ingameGUI.drawRect(xOff + size + 1, yOff + (off*height) + (select[1]*height), xOff + size + 3, yOff + (off*height) + (select[1]*height) + height, color);
            for(Module m : mod) {
                mc.ingameGUI.drawString(bypackt.font, (m.active ? "§n" + m.name : m.name), xOff + size + ((select[1] == c) ? 3 : 0) + 5, yOff + (off*height) + (c*height) + fontOff, (select[1] == c) ? color : 16777215);
                c += 1;
            }
        }
    }
}
