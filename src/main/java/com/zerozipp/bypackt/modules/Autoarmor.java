package com.zerozipp.bypackt.modules;

import com.zerozipp.bypackt.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;

public class Autoarmor extends Module {
    public Autoarmor(Minecraft mcIn, String nameIn, int idIn, boolean activeIn) {
        super(mcIn, nameIn, idIn, activeIn);
        list = new Object[][][][] {
            {
                {{"Elytra"}, {false}},
                {
                    {false, "OFF"},
                    {true, "ON"}
                }
            }
        };
    }

    public void runList(int indexIn) {
        if(indexIn == 0) {
            list[indexIn][0][1][0] = !((boolean) list[indexIn][0][1][0]);
        }
    }

    public void onUpdate() {
        if(!mc.player.hasItemInSlot(EntityEquipmentSlot.CHEST)) {
            if((boolean) list[0][0][1][0]) {
                for(Slot s : mc.player.inventoryContainer.inventorySlots) {
                    if(s.getStack().getItem() == Items.ELYTRA) {
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, s.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
                        return;
                    }
                }
            }else for(Slot s : mc.player.inventoryContainer.inventorySlots) {
                if(s.getStack().getItem() == Items.CHAINMAIL_CHESTPLATE || s.getStack().getItem() == Items.DIAMOND_CHESTPLATE || s.getStack().getItem() == Items.GOLDEN_CHESTPLATE || s.getStack().getItem() == Items.IRON_CHESTPLATE || s.getStack().getItem() == Items.LEATHER_CHESTPLATE) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, s.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
                    return;
                }
            }
        }
        if(!mc.player.hasItemInSlot(EntityEquipmentSlot.FEET)) {
            for(Slot s : mc.player.inventoryContainer.inventorySlots) {
                if(s.getStack().getItem() == Items.CHAINMAIL_BOOTS || s.getStack().getItem() == Items.DIAMOND_BOOTS || s.getStack().getItem() == Items.GOLDEN_BOOTS || s.getStack().getItem() == Items.IRON_BOOTS || s.getStack().getItem() == Items.LEATHER_BOOTS) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, s.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
                    return;
                }
            }
        }
        if(!mc.player.hasItemInSlot(EntityEquipmentSlot.HEAD)) {
            for(Slot s : mc.player.inventoryContainer.inventorySlots) {
                if(s.getStack().getItem() == Items.CHAINMAIL_HELMET || s.getStack().getItem() == Items.DIAMOND_HELMET || s.getStack().getItem() == Items.GOLDEN_HELMET || s.getStack().getItem() == Items.IRON_HELMET || s.getStack().getItem() == Items.LEATHER_HELMET) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, s.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
                    return;
                }
            }
        }
        if(!mc.player.hasItemInSlot(EntityEquipmentSlot.LEGS)) {
            for(Slot s : mc.player.inventoryContainer.inventorySlots) {
                if(s.getStack().getItem() == Items.CHAINMAIL_LEGGINGS || s.getStack().getItem() == Items.DIAMOND_LEGGINGS || s.getStack().getItem() == Items.GOLDEN_LEGGINGS || s.getStack().getItem() == Items.IRON_LEGGINGS || s.getStack().getItem() == Items.LEATHER_LEGGINGS) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, s.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
                    return;
                }
            }
        }
    }
}
