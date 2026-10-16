package com.danrus.mixin;

import com.danrus.KeyBindsManager;
import com.danrus.ForceDeleteManager;
import com.danrus.utils.WhiteListUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
//? if >=26.2 {
/*import net.minecraft.world.inventory.ContainerInput;
*///?} else {
import net.minecraft.world.inventory.ClickType;
//?}
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    @Shadow
    public Slot destroyItemSlot;

    @Shadow
    private List<Slot> originalSlots;

    public CreativeModeInventoryScreenMixin(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    //? if >=1.21.2 {
    /*@WrapOperation(
            method = "slotClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/Slot;set(Lnet/minecraft/world/item/ItemStack;)V"
            )
    )
    private void onSlotClickMixin2(Slot instance, ItemStack stack, Operation<Void> original) {
        if (WhiteListUtils.shouldBeDeleted(instance.getItem().getItem(), instance.index) || ForceDeleteManager.shouldBeForceDeleted()) {
            original.call(instance, stack);
        }
    }
    *///?}

    @WrapOperation(
            method = "slotClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;handleCreativeModeItemAdd(Lnet/minecraft/world/item/ItemStack;I)V",
                    ordinal = 0
            )

    )
    private void computeSlotDestroy(MultiPlayerGameMode instance, ItemStack stack, int slotId, Operation<Void> original) {
        Item item = this.menu.slots.get(slotId).getItem().getItem();
        if (WhiteListUtils.shouldBeDeleted(item,  slotId) || ForceDeleteManager.shouldBeForceDeleted()) {
            original.call(instance, stack, slotId);
        }
    }

    //? if >=26.2 {
    /*@Inject(method = "slotClicked", at = @At("HEAD"))
    private void onShiftAndDIClicked(Slot slot, int slotId, int button, ContainerInput actionType, CallbackInfo ci) {
        if (slot == this.destroyItemSlot && actionType == ContainerInput.QUICK_MOVE) {
            ForceDeleteManager.onClick();
        }
    }
    *///?} else {
    @Inject(method = "slotClicked", at = @At("HEAD"))
    private void onShiftAndDIClicked(Slot slot, int slotId, int button, ClickType actionType, CallbackInfo ci) {
        if (slot == this.destroyItemSlot && actionType == ClickType.QUICK_MOVE) {
            ForceDeleteManager.onClick();
        }
    }
    //?}
}
