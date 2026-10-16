package com.danrus.mixin;

import com.danrus.KeyBindsManager;
import com.danrus.utils.WhiteListUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
//? if >=26.2 {
/*import net.minecraft.world.inventory.ContainerInput;
*///?} else {
import net.minecraft.world.inventory.ClickType;
//?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    //? if >=26.2 {
    /*@WrapOperation(
            method = "clicked",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/AbstractContainerMenu;doClick(IILnet/minecraft/world/inventory/ContainerInput;Lnet/minecraft/world/entity/player/Player;)V")
    )
    private void onMenuClickedMixin(AbstractContainerMenu instance, int slotId, int button, ContainerInput slotActionType, Player player, Operation<Void> original) {
        handleMenuClick(instance, slotId, button, slotActionType, player, original);
    }
    *///?} else {
    @WrapOperation(
            method = "clicked",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/AbstractContainerMenu;doClick(IILnet/minecraft/world/inventory/ClickType;Lnet/minecraft/world/entity/player/Player;)V")
    )
    private void onMenuClickedMixin(AbstractContainerMenu instance, int slotId, int button, ClickType slotActionType, Player player, Operation<Void> original) {
        handleMenuClick(instance, slotId, button, slotActionType, player, original);
    }
    //?}

    private void handleMenuClick(AbstractContainerMenu instance, int slotId, int button, Object slotActionType, Player player, Operation<Void> original) {
        if (KeyBindsManager.isDoToggleWhiteListPressed) {
            if (button == 0) {
                WhiteListUtils.toggle(slotId);}
            else if (button == 1) {
                Item item = instance.getSlot(slotId).getItem().getItem();
                if (item != Items.AIR) {
                    WhiteListUtils.toggle(item);
                }
            }
        } else {
            original.call(instance, slotId, button, slotActionType, player);
        }
    }

}
