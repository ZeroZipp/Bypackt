package com.zerozipp.bypackt;

import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SInteger;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Clickgui extends GuiScreen {
    public static int color = 0xf00080ff;
    public static int size = 90;
    public static boolean[] active = {
            true,
            true,
            true,
            true,
            true,
            true
    };

    public Bypackt bypackt;
    public ArrayList<Module> modules;
    public ArrayList[] list = {
            new ArrayList<Module>(),
            new ArrayList<Module>(),
            new ArrayList<Module>(),
            new ArrayList<Module>(),
            new ArrayList<Module>(),
            new ArrayList<Module>()
    };
    public int xOff, yOff;
    public String[] names = {
            "Movement",
            "Auto",
            "Render",
            "Combat",
            "World",
            "Screen"
    };

    public Clickgui(ArrayList<Module> modulesIn, Bypackt bypacktIn) {
        bypackt = bypacktIn;
        modules = modulesIn;
        xOff = 38;
        yOff = 38;
        for(Module m : modules) {
            if(m.id == Module.MOVEMENT) {
                list[0].add(m);
            }else if(m.id == Module.AUTO) {
                list[1].add(m);
            }else if(m.id == Module.RENDER) {
                list[2].add(m);
            }else if(m.id == Module.COMBAT) {
                list[3].add(m);
            }else if(m.id == Module.WORLD) {
                list[4].add(m);
            }else if(m.id == Module.SCREEN) {
                list[5].add(m);
            }
        }
        for(ArrayList<Module> l : list) {
            Collections.sort(l);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, 0x88000000);

        int c = 0;
        for(String n : names) {
            drawRect(xOff + (c*(size + 5)), yOff, xOff + (c*(size + 5)) + size, yOff + 18, color);
            drawString(bypackt.font, n, xOff + (c*(size + 5)) + 5, yOff + 5, 16777215);
            c += 1;
        }

        for(int i = 0; i < list.length; i++) {
            c = 1;
            if(active[i]) {
                for(Object n : list[i]) {
                    Module m = (Module) n;
                    drawRect(xOff + (i * (size + 5)), yOff + (c * 18), xOff + (i * (size + 5)) + size, yOff + (c * 18) + 18, 0x99000000);
                    drawString(bypackt.font, m.name, xOff + (i * (size + 5)) + 5, yOff + (c * 18) + 5, (m.active) ? color : 16777215);
                    c += 1;
                    if(m.open && m.settings.length > 0) {
                        for(Setting setting : m.settings) {
                            String SValue = "Null";
                            if(setting instanceof SBoolean) {
                                SBoolean b = (SBoolean) setting;
                                if(b.active) {
                                    SValue = "ON";
                                }else{
                                    SValue = "OFF";
                                }
                            }else if(setting instanceof SInteger) {
                                SInteger b = (SInteger) setting;
                                SValue = Integer.toString(b.value);
                            }else if(setting instanceof SString) {
                                SString b = (SString) setting;
                                SValue = b.list[b.value];
                            }
                            drawRect(xOff + (i * (size + 5)), yOff + (c * 18), xOff + (i * (size + 5)) + size, yOff + (c * 18) + 18, 0x99111111);
                            drawString(bypackt.font, setting.name + ": " + SValue, xOff + (i * (size + 5)) + 10, yOff + (c * 18) + 5, 16777215);
                            c += 1;
                        }
                    }
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseButton == 0) {
            int c = 0;
            for(int i = 0; i < active.length; i++) {
                if (mouseX > xOff + (i * (size + 5)) && mouseY > yOff + (c * 18) && mouseX < xOff + (i * (size + 5)) + size && mouseY < yOff + (c * 18) + 18) {
                    active[i] = !active[i];
                    c += 1;
                }
            }

            for(int i = 0; i < list.length; i++) {
                c = 1;
                if(active[i]) {
                    for(Object n : list[i]) {
                        Module m = (Module) n;
                        if(mouseX > xOff + (i*(size + 5)) && mouseY > yOff + (c*18) && mouseX < xOff + (i*(size + 5)) + size && mouseY < yOff + (c*18) + 18) {
                            m.setActive(!m.active);
                        }
                        c += 1;
                        if(m.open && m.settings.length > 0) {
                            int g = 0;
                            for(Setting setting : m.settings) {
                                if(mouseX > xOff + (i*(size + 5)) && mouseY > yOff + (c*18) && mouseX < xOff + (i*(size + 5)) + size && mouseY < yOff + (c*18) + 18) {
                                    m.runList(g);
                                }
                                g += 1;
                                c += 1;
                            }
                        }
                    }
                }
            }
        }
        if(mouseButton == 1) {
            for(int i = 0; i < list.length; i++) {
                int c = 1;
                if(active[i]) {
                    for(Object n : list[i]) {
                        Module m = (Module) n;
                        if(mouseX > xOff + (i*(size + 5)) && mouseY > yOff + (c*18) && mouseX < xOff + (i*(size + 5)) + size && mouseY < yOff + (c*18) + 18) {
                            if(m.settings.length > 0) {
                                m.open = !m.open;
                            }
                        }
                        c += 1;
                        if(m.open) {
                            c += m.settings.length;
                        }
                    }
                }
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void updateScreen() {
        super.updateScreen();
    }
}
