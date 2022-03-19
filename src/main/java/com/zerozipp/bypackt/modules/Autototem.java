package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;

public class Autototem extends Module {
    public Autototem(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
    }

    public void onUpdate() {
        if(!mc.player.hasItemInSlot(EntityEquipmentSlot.OFFHAND)) {
            for(Slot s : mc.player.inventoryContainer.inventorySlots) {
                if(s.getStack().getItem() == Items.TOTEM_OF_UNDYING) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, s.slotNumber, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
                    return;
                }
            }
        }
    }
}
