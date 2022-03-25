package com.zerozipp.bypackt;

import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SInteger;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Clickgui extends GuiScreen {
    private static int defColor = 0xf00080ff;
    private static int color = defColor;
    public static int size = 90;
    public Bypackt bypackt;
    public ArrayList<Module> modules;
    public int xOff, yOff;

    public ArrayList<ArrayList<Module>> list = new ArrayList<>();
    public ArrayList<Boolean> active = new ArrayList<>();
    public ArrayList<String> names = new ArrayList<>();
    public static Object[][] module = {
            {"Movement", true},
            {"Auto", true},
            {"Render", true},
            {"Combat", true},
            {"World", true},
            {"Screen", true}
    };

    public static void setColor(int colorIn) {
        color = colorIn;
    }

    public static void resetColor() {
        color = defColor;
    }

    public Clickgui(ArrayList<Module> modulesIn) {
        bypackt = Bypackt.getBypackt();
        modules = modulesIn;
        for(Object[] l : module) {
            list.add(new ArrayList<>());
            names.add((String)l[0]);
            active.add((Boolean)l[1]);
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

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRect(0, 0, width, height, 0x88000000);
        int wSize = ((size+5)*module.length-5)/2;
        xOff = (width/2)-wSize;
        yOff = height/8;

        int c = 0;
        for(String n : names) {
            drawRect(xOff + (c*(size + 5)), yOff, xOff + (c*(size + 5)) + size, yOff + 18, color);
            drawString(bypackt.font, n, xOff + (c*(size + 5)) + 5, yOff + 5, 16777215);
            drawString(bypackt.font, active.get(c) ? ">" : "<", xOff + (c*(size + 5)) + size-10, yOff + 5, 16777215);
            c += 1;
        }

        for(int i = 0; i < list.size(); i++) {
            c = 1;
            if(active.get(i)) {
                for(Object n : list.get(i)) {
                    Module m = (Module) n;
                    drawRect(xOff + (i * (size + 5)), yOff + (c * 18), xOff + (i * (size + 5)) + size, yOff + (c * 18) + 18, 0x99000000);
                    drawString(bypackt.font, m.name, xOff + (i * (size + 5)) + 5, yOff + (c * 18) + 5, (m.active) ? color : 16777215);
                    c += 1;
                    if(m.open && m.settings != null) {
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
                            drawString(bypackt.font, setting.name + ": " + SValue, xOff + (i * (size + 5)) + 8, yOff + (c * 18) + 5, 16777215);
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
            for(int i = 0; i < active.size(); i++) {
                if (mouseX > xOff + (i * (size + 5)) && mouseY > yOff + (c * 18) && mouseX < xOff + (i * (size + 5)) + size && mouseY < yOff + (c * 18) + 18) {
                    active.set(i, !active.get(i));
                    module[i][1] = active.get(i);
                    c += 1;
                }
            }

            for(int i = 0; i < list.size(); i++) {
                c = 1;
                if(active.get(i)) {
                    for(Object n : list.get(i)) {
                        Module m = (Module) n;
                        if(mouseX > xOff + (i*(size + 5)) && mouseY > yOff + (c*18) && mouseX < xOff + (i*(size + 5)) + size && mouseY < yOff + (c*18) + 18) {
                            m.setActive(!m.active);
                        }
                        c += 1;
                        if(m.open && m.settings != null) {
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
            for(int i = 0; i < list.size(); i++) {
                int c = 1;
                if(active.get(i)) {
                    for(Object n : list.get(i)) {
                        Module m = (Module) n;
                        if(mouseX > xOff + (i*(size + 5)) && mouseY > yOff + (c*18) && mouseX < xOff + (i*(size + 5)) + size && mouseY < yOff + (c*18) + 18) {
                            if(m.settings != null) {
                                m.open = !m.open;
                            }
                        }
                        c += 1;
                        if(m.open && m.settings != null) {
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
