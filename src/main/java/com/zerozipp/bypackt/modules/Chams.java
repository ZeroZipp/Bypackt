package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import com.zerozipp.bypackt.settings.SBoolean;
import com.zerozipp.bypackt.settings.SString;
import com.zerozipp.bypackt.settings.Setting;
import com.zerozipp.bypackt.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class Chams extends Module {
    public Render render;

    public Chams(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        render = new Render();
        settings = new Setting[] {
                new SString("Mode", 0, new String[] {"Normal", "Light"}),
                new SBoolean("Players", true),
                new SBoolean("Entitys", true),
                new SBoolean("Mobs", true)
        };
    }

    public void onEvent(Event event) {
        if(event != null) {
            if (event instanceof RenderLivingEvent.Pre) {
                RenderLivingEvent.Pre livingEvent = (RenderLivingEvent.Pre) event;
                EntityLivingBase base = livingEvent.getEntity();
                if (base instanceof EntityPlayer && ((SBoolean) settings[1]).active) {
                    render.enablePolygon();
                    if (((SString) settings[0]).value == 1) {
                        render.disableLighting();
                    }
                }
                if (base instanceof EntityAnimal && ((SBoolean) settings[2]).active) {
                    render.enablePolygon();
                    if (((SString) settings[0]).value == 1) {
                        render.disableLighting();
                    }
                }
                if (base instanceof EntityMob && ((SBoolean) settings[3]).active) {
                    render.enablePolygon();
                    if (((SString) settings[0]).value == 1) {
                        render.disableLighting();
                    }
                }
            } else if (event instanceof RenderLivingEvent.Post) {
                RenderLivingEvent.Post livingEvent = (RenderLivingEvent.Post) event;
                EntityLivingBase base = livingEvent.getEntity();
                if (base instanceof EntityPlayer && ((SBoolean) settings[1]).active) {
                    render.disablePolygon();
                    if (((SString) settings[0]).value == 1) {
                        render.enableLighting();
                    }
                }
                if (base instanceof EntityAnimal && ((SBoolean) settings[2]).active) {
                    render.disablePolygon();
                    if (((SString) settings[0]).value == 1) {
                        render.enableLighting();
                    }
                }
                if (base instanceof EntityMob && ((SBoolean) settings[3]).active) {
                    render.disablePolygon();
                    if (((SString) settings[0]).value == 1) {
                        render.enableLighting();
                    }
                }
            }
        }
    }
}
