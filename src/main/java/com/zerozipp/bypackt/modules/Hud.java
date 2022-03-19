package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.Overlay;
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
        list = new Object[][][][] {
                {
                        {{"Color"}, {7}},
                        {
                                {0, "Red"},
                                {1, "Orange"},
                                {2, "Yellow"},
                                {3, "Treuse"},
                                {4, "Green"},
                                {5, "Spring"},
                                {6, "Cyan"},
                                {7, "Dodge"},
                                {8, "Blue"},
                                {9, "Purple"},
                                {10, "Violet"},
                                {11, "Rose"}
                        }
                },
                {
                        {{"List"}, {true}},
                        {
                                {false, "OFF"},
                                {true, "ON"}
                        }
                },
                {
                        {{"Info"}, {true}},
                        {
                                {false, "OFF"},
                                {true, "ON"}
                        }
                }
        };
    }

    public void runList(int indexIn) {
        if(indexIn == 0) {
            if ((int) list[indexIn][0][1][0] < list[indexIn][1].length-1) {
                list[indexIn][0][1][0] = (int) list[indexIn][0][1][0] + 1;
            } else {
                list[indexIn][0][1][0] = 0;
            }
        }else if(indexIn == 1) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }else if(indexIn == 2) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }
    }

    public void onEnable() {
        Overlay.color = (int)colors[(int)list[0][0][1][0]][0];
        Overlay.list = (boolean)list[1][0][1][0];
        Overlay.info = (boolean)list[2][0][1][0];
    }

    public void onUpdate() {
        Overlay.color = (int)colors[(int)list[0][0][1][0]][0];
        Overlay.list = (boolean)list[1][0][1][0];
        Overlay.info = (boolean)list[2][0][1][0];
    }

    public void onDisable() {
        Overlay.color = 0xf00080ff;
        Overlay.list = true;
        Overlay.info = true;
    }
}
