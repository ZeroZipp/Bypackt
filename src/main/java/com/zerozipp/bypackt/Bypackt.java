package com.zerozipp.bypackt;

import com.zerozipp.bypackt.modules.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraftforge.fml.common.eventhandler.Event;

public class Bypackt {
    private Minecraft mc;
    public String name;
    public String version;
    public ArrayList<Module> modules = new ArrayList();
    public FontRenderer font;
    public static ResourceLocation fontLocation;
    private IReloadableResourceManager resourceManager;
    private final MetadataSerializer metadataSerializer = new MetadataSerializer();
    private static Bypackt bypackt = new Bypackt(Minecraft.getMinecraft());

    public static Bypackt getBypackt() {
        return bypackt;
    }

    public Bypackt(Minecraft mcIn) {
        name = "Bypackt";
        version = "1.3";
        mc = mcIn;
    }

    public boolean isObfuscated() {
        String mcClassName = Minecraft.class.getName().replace(".", "/");
        FMLDeobfuscatingRemapper remapper = FMLDeobfuscatingRemapper.INSTANCE;
        return !mcClassName.equals(remapper.unmap(mcClassName));
    }

    public void onLoad() {
        fontLocation = new ResourceLocation("bypackt", "textures/font/bypackt.png");
        font = new FontRenderer(mc.gameSettings, fontLocation, mc.renderEngine, false);
        this.resourceManager = new SimpleReloadableResourceManager(metadataSerializer);
        resourceManager.registerReloadListener(font);

        modules.add(new Airjump(mc, "Airjump", Module.MOVEMENT, false));
        modules.add(new Flight(mc, "Flight", Module.MOVEMENT, false));
        modules.add(new Timer(mc, "Timer", Module.MOVEMENT, false));
        modules.add(new Nofall(mc, "Nofall", Module.MOVEMENT, false));
        modules.add(new Sprint(mc, "Sprint", Module.MOVEMENT, false));
        modules.add(new Sneak(mc, "Sneak", Module.MOVEMENT, false));
        modules.add(new Autoarmor(mc, "Autoarmor", Module.AUTO, false));
        modules.add(new Autojump(mc, "Autojump", Module.AUTO, false));
        modules.add(new Autobreak(mc, "Autobreak", Module.AUTO, false));
        modules.add(new Autototem(mc, "Autototem", Module.AUTO, false));
        modules.add(new Autoaim(mc, "Autoaim", Module.AUTO, false));
        modules.add(new Autowalk(mc, "Autowalk", Module.AUTO, false));
        modules.add(new Autoswim(mc, "Autoswim", Module.AUTO, false));
        modules.add(new Chams(mc, "Chams", Module.RENDER, false));
        modules.add(new Crystalaura(mc, "Crystalaura", Module.COMBAT, false));
        modules.add(new Killaura(mc, "Killaura", Module.COMBAT, false));
        modules.add(new Pvpbot(mc, "Pvpbot", Module.COMBAT, false));
        modules.add(new Trigger(mc, "Trigger", Module.COMBAT, false));
        modules.add(new Scaffold(mc, "Scaffold", Module.WORLD, false));
        modules.add(new Gui(mc, "Gui", Module.SCREEN, true));
        modules.add(new Hud(mc, "Hud", Module.SCREEN, true));
        modules.add(new Tabgui(mc, "Tabgui", Module.SCREEN, false));

        Collections.sort(modules);
    }

    public void onUpdate() {
        if(mc.world != null && mc.player != null) {
            for(Module m : modules) {
                if(m.active) {
                    m.onUpdate();
                }
            }
        }
    }

    public void onEvent(Event event) {
        if(mc.world != null && mc.player != null) {
            for (Module m : modules) {
                if (m.active) {
                    m.onEvent(event);
                }
            }
        }
    }

    public void onOverlay() {
        if(mc.world != null && mc.player != null) {
            onGui();
            for (Module m : modules) {
                if (m.active) {
                    m.onOverlay();
                }
            }
        }
    }

    public void onGui() {
        if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            if(mc.currentScreen == null) {
                mc.displayGuiScreen(new Clickgui(modules));
            }
        }
    }
}
