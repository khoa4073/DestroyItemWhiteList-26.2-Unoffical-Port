package com.danrus.utils;
//? if >=26.2 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
*///?} else {
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
//?}
//? if >=1.21.6 {
/*import net.minecraft.client.renderer.RenderPipelines;
*///?} elif >=1.21.2 {
/*import net.minecraft.client.renderer.RenderType;
*///?}

public class DrawUtils {
    public static void drawLocked(Object context, int x, int y) {
        drawTexture(context, "locked", x, y);
    }

    public static void drawLockedItem(Object context, int x, int y) {
        drawTexture(context, "locked_item", x, y);
    }

    public static void drawTexture(Object context, String id, int x, int y) {
        //? if >=26.2 {
        /*((GuiGraphicsExtractor) context).blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath("diwl", id), x - 1, y - 1, 18, 18);
        *///?} elif >=1.21.6 {
        /*((GuiGraphics) context).blitSprite(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath("diwl", id), x - 1, y - 1, 18, 18);
        *///?} elif >=1.21.2 {
        /*((GuiGraphics) context).blitSprite(RenderType::guiTextured, ResourceLocation.fromNamespaceAndPath("diwl", id), x - 1, y - 1, 18, 18);
        *///?} else {
        ((GuiGraphics) context).blitSprite(ResourceLocation.fromNamespaceAndPath("diwl", id), x - 1, y - 1, 18, 18);
        //?}
    }
}
