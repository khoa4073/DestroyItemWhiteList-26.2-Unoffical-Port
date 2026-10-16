package com.danrus.mixin;

import com.danrus.KeyBindsManager;
import com.danrus.utils.DrawUtils;
import com.danrus.utils.WhiteListUtils;
import com.danrus.config.ModConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >=26.2 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.inventory.ContainerInput;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.ClickType;
//?}
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    //? if >=26.2 {
    /*@Inject(method = "extractSlot", at = @At("TAIL"))
    private void drawSlotMixin(GuiGraphicsExtractor context, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        drawSlotMarkers(context, slot);
    }
    *///?} else {
    @Inject(method = "renderSlot", at = @At("TAIL"))
    private void drawSlotMixin(GuiGraphics context, Slot slot, CallbackInfo ci) {
        drawSlotMarkers(context, slot);
    }
    //?}

    private void drawSlotMarkers(Object context, Slot slot) {
        if (!WhiteListUtils.isNotInSlots(slot.getContainerSlot()) && ((!ModConfig.get().hideWhiteListedSlots || (ModConfig.get().showWhenBindPressed && KeyBindsManager.isDoToggleWhiteListPressed)) || (!ModConfig.get().hideWhiteListedSlots || (ModConfig.get().showWhenDIHovered && WhiteListUtils.isDestroyItemSlotFocused())))) {
            DrawUtils.drawLocked(context, slot.x, slot.y);
        }
        if (!WhiteListUtils.isNotInItems(slot.getItem().getItem()) && ((!ModConfig.get().hideWhiteListedItems || (ModConfig.get().showWhenBindPressed && KeyBindsManager.isDoToggleWhiteListPressed)) || (!ModConfig.get().hideWhiteListedItems || (ModConfig.get().showWhenDIHovered && WhiteListUtils.isDestroyItemSlotFocused())))) {
            DrawUtils.drawLockedItem(context, slot.x, slot.y);
        }
    }

    //? if >=26.2 {
    /*@WrapOperation(
            method = "keyPressed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;slotClicked(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ContainerInput;)V", ordinal = 1)
    )
    private void cancelKeyPressIfNeeded(AbstractContainerScreen instance, Slot slot, int slotId, int mouseButton, ContainerInput type, Operation<Void> original) {
        if (WhiteListUtils.isNotInSlots(slot.index) && WhiteListUtils.isNotInItems(slot.getItem().getItem())) {
            original.call(instance, slot, slotId, mouseButton, type);
        }
    }
    *///?} else {
    @WrapOperation(
            method = "keyPressed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;slotClicked(Lnet/minecraft/world/inventory/Slot;IILnet/minecraft/world/inventory/ClickType;)V", ordinal = 1)
    )
    private void cancelKeyPressIfNeeded(AbstractContainerScreen instance, Slot slot, int slotId, int mouseButton, ClickType type, Operation<Void> original) {
        if (WhiteListUtils.isNotInSlots(slot.index) && WhiteListUtils.isNotInItems(slot.getItem().getItem())) {
            original.call(instance, slot, slotId, mouseButton, type);
        }
    }
    //?}
}