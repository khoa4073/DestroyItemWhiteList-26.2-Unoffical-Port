package com.danrus;


public record KeyEventWrapper(int keyCode, int scanCode, int modifiers
                              //? if >=1.21.9
                              /*, net.minecraft.client.input.KeyEvent event*/
) {
    //? if >=1.21.9 {
    /*public static KeyEventWrapper create(net.minecraft.client.input.KeyEvent event) {
        return new KeyEventWrapper(event.key(), event.scancode(), event.modifiers(), event);
    }
    *///?} else {
    public static KeyEventWrapper create(int keyCode, int scanCode, int modifiers) {
        return new KeyEventWrapper(keyCode, scanCode, modifiers);
    }
    //?}
}
