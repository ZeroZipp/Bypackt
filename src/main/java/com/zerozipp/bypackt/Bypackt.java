package com.zerozipp.bypackt;

import com.zerozipp.bypackt.modules.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Bypackt {
    private Minecraft mc;
    public String name;
    public String version;
    public ArrayList<Module> modules = new ArrayList<Module>();
    public Overlay overlay;
    public FontRenderer font;
    public static ResourceLocation fontLocation;
    private IReloadableResourceManager resourceManager;
    private final MetadataSerializer metadataSerializer = new MetadataSerializer();

    public Bypackt getBypackt() {
        return this;
    }

    public Bypackt(Minecraft mcIn) throws IOException {
        name = "Bypackt";
        version = "1.3";
        mc = mcIn;
        overlay = new Overlay(mc, getBypackt());
    }

    public void onLoad() throws IOException {
        fontLocation = new ResourceLocation("bypackt", "textures/font/bypackt.png");
        font = new FontRenderer(mc.gameSettings, fontLocation, mc.renderEngine, false);
        this.resourceManager = new SimpleReloadableResourceManager(metadataSerializer);
        resourceManager.registerReloadListener(font);

        modules.add(new Airjump(mc, "Airjump", Module.MOVEMENT, false));
        modules.add(new Flight(mc, "Flight", Module.MOVEMENT, false));
        modules.add(new Timer(mc, "Timer", Module.MOVEMENT, false));
        modules.add(new Nofall(mc, "Nofall", Module.MOVEMENT, false));
        modules.add(new Autoarmor(mc, "Autoarmor", Module.AUTO, false));
        modules.add(new Autojump(mc, "Autojump", Module.AUTO, false));
        modules.add(new Autobreak(mc, "Autobreak", Module.AUTO, false));
        modules.add(new Autosprint(mc, "Autosprint", Module.AUTO, false));
        modules.add(new Autototem(mc, "Autototem", Module.AUTO, false));
        modules.add(new Fullbright(mc, "Fullbright", Module.RENDER, false));
        modules.add(new Nobob(mc, "Nobob", Module.RENDER, false));
        modules.add(new Crystalaura(mc, "Crystalaura", Module.COMBAT, false));
        modules.add(new Killaura(mc, "Killaura", Module.COMBAT, false));
        modules.add(new Scaffold(mc, "Scaffold", Module.WORLD, false));
        modules.add(new Gui(mc, "Gui", Module.SCREEN, true));
        modules.add(new Hud(mc, "Hud", Module.SCREEN, true));

        Collections.sort(modules);
    }

    public void onUpdate() {
        if(mc.world != null) {
            for(Module m : modules) {
                if(m.active) {
                    m.onUpdate();
                }
            }
        }
    }

    public void onRender() {
        if(mc.world != null) {
            for (Module m : modules) {
                if (m.active) {
                    m.onRender();
                }
            }
        }
    }

    public void onOverlay() {
        if(mc.world != null) {
            overlay.onUpdate();
            onGui();
        }
    }

    public void onGui() {
        if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            if(mc.currentScreen == null) {
                mc.displayGuiScreen(new Clickgui(modules, getBypackt()));
            }
        }
    }
}
