package com.danrus;

import com.danrus.config.ModConfig;
import net.minecraft.client.Minecraft;

public class ForceDeleteManager {

    public static ForceDeleteManager INSTANCE = new ForceDeleteManager();
    public int ticks = 0;
    public int clicks = 0;

    public static int getTimer() {
        return INSTANCE.ticks;
    }

    public static void onClick() {
        INSTANCE.ticks = ModConfig.get().forceDeleteClickDelay;
        INSTANCE.clicks += 1;
    }

    public static boolean shouldBeForceDeleted() {
        if (ModConfig.get().clicksForForceDelete <= 1) {return false;}
        return INSTANCE.clicks > ModConfig.get().clicksForForceDelete - 1 && INSTANCE.ticks > 0;
    }

    public static void tick(Minecraft mc) {
        INSTANCE.tick();
    }

    public void tick() {
        if (ticks > 0) {
            ticks -= 1;
        }

        if (ticks == 0) {
            clicks = 0;
        }
    }

}
