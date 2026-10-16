package com.danrus;

import com.danrus.utils.KeyMapsUtils;
//? if >=26.2 {
/*import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
*///?} else {
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
//?}
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import org.lwjgl.glfw.GLFW;

public class KeyBindsManager {
    public static KeyMapping doToggleWhiteList;
    public static boolean isDoToggleWhiteListPressed = false;

    public static void register() {
        doToggleWhiteList = KeyMapsUtils.create(
            "key.diwl.do_toggle_whitelist",
            GLFW.GLFW_KEY_GRAVE_ACCENT
        );
        //? if >=26.2 {
        /*KeyMappingHelper.registerKeyMapping(doToggleWhiteList);
        *///?} else {
        KeyBindingHelper.registerKeyBinding(doToggleWhiteList);
        //?}
    }

    public static void handle(Minecraft mc) {

        //? if >=26.2 {
        /*Screen screen = mc.gui.screen();
        *///?} else {
        Screen screen = mc.screen;
        //?}
        if (screen != null) {
            KeyMapsUtils.registerAfterPress(screen, (event) -> {
                if (KeyMapsUtils.matches(doToggleWhiteList, event)) {
                    isDoToggleWhiteListPressed = true;
                }
            });
            KeyMapsUtils.registerAfterRelease(screen, (event) -> {
                if (KeyMapsUtils.matches(doToggleWhiteList, event)) {
                    isDoToggleWhiteListPressed = false;
                }
            });
        }
    }
}
