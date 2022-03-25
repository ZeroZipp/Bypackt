package com.zerozipp.bypackt;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = "bypackt", name = "Bypackt", version = "1.3")
public class Main {
    private static Logger logger;
    public static Minecraft mc;
    public static Bypackt bypackt;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        mc = Minecraft.getMinecraft();
        bypackt = Bypackt.getBypackt();
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        logger.info("Hello from: " + bypackt.name);
        bypackt.onLoad();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        bypackt.onUpdate();
    }

    @SubscribeEvent
    public void onPreRenderLiving(RenderLivingEvent.Pre event) {
        bypackt.onEvent(event);
    }

    @SubscribeEvent
    public void onPostRenderLiving(RenderLivingEvent.Post event) {
        bypackt.onEvent(event);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if(event.getType() != RenderGameOverlayEvent.ElementType.ALL || mc.gameSettings.showDebugInfo) {
            return;
        }else{
            bypackt.onOverlay();
        }
    }
}
