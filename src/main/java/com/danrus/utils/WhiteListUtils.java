package com.danrus.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.Item;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Type;

public class WhiteListUtils {

    public static Set<Integer> whitelistedSlotIds;
    
    public static Set<String> whitelistedItems;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path SLOTS_PATH = FabricLoader.getInstance().getConfigDir().resolve("diwl_slots.json");
    private static final Path ITEMS_PATH = FabricLoader.getInstance().getConfigDir().resolve("diwl_items.json");

    public static void init() {
        loadConfig();
        if (whitelistedSlotIds == null) {
            whitelistedSlotIds = new HashSet<Integer>();
            saveConfig();
        }
    }

    public static void loadConfig() {
        File slotsPathFile = SLOTS_PATH.toFile();
        File itemsPathFile = ITEMS_PATH.toFile();
        if (slotsPathFile.exists()) {
            try (FileReader reader = new FileReader(slotsPathFile)) {
                Type collectionType = new TypeToken<HashSet<Integer>>(){}.getType();
                Set<Integer> loadedIds = GSON.fromJson(reader, collectionType);
                if (loadedIds != null) {
                    whitelistedSlotIds = new HashSet<Integer>(loadedIds);
                } else {
                    whitelistedSlotIds = new HashSet<Integer>();
                }
            } catch (IOException e) {
                System.err.println("Failed to load diwl_slots.json: " + e.getMessage());
                e.printStackTrace();
                whitelistedSlotIds = new HashSet<Integer>();
            }
        } else {
            whitelistedSlotIds = new HashSet<Integer>();
        }

        if (itemsPathFile.exists()) {
            try (FileReader reader = new FileReader(itemsPathFile)) {
                Type collectionType = new TypeToken<HashSet<String>>(){}.getType();
                Set<String> loadedItems = GSON.fromJson(reader, collectionType);
                if (loadedItems != null) {
                    whitelistedItems = new HashSet<String>(loadedItems);
                } else {
                    whitelistedItems = new HashSet<String>();
                }
            } catch (IOException e) {
                System.err.println("Failed to load diwl_items.json: " + e.getMessage());
                e.printStackTrace();
                whitelistedItems = new HashSet<String>();
            }
        } else {
            whitelistedItems = new HashSet<String>();
        }
    }


    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(SLOTS_PATH.toFile())) {
            GSON.toJson(whitelistedSlotIds, writer);
        } catch (IOException e) {
            System.err.println("Failed to save diwl_slots.json: " + e.getMessage());
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter(ITEMS_PATH.toFile())) {
            GSON.toJson(whitelistedItems, writer);
        } catch (IOException e) {
            System.err.println("Failed to save diwl_items.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void add(int id) {
        whitelistedSlotIds.add(id);
        saveConfig();
    }

    public static void add(Item item){add(item.toString());}

    public static void add(String name) {
        whitelistedItems.add(name);
        saveConfig();
    }

    public static void rm(int id) {
        whitelistedSlotIds.remove(id);
        saveConfig();
    }

    public static void rm(Item item) {rm(item.toString());}

    public static void rm(String name) {
        whitelistedItems.remove(name);
        saveConfig();
    }

    public static void toggle(int id) {
        if (whitelistedSlotIds.contains(id)) {
            rm(id);
        } else {
            add(id);
        }
    }

    public static void toggle(Item item){toggle(item.toString());}

    public static void toggle(String name){
        if (whitelistedItems.contains(name)) {
            rm(name);
        } else {
            add(name);
        }
    }

    public static boolean isNotInSlots(int id) {
        return !whitelistedSlotIds.contains(id);
    }

    public static boolean isNotInItems(Item item) {return isNotInItems(item.toString());}

    public static boolean isNotInItems(String name) {
        return !whitelistedItems.contains(name);
    }

    public static boolean shouldBeDeleted(Item item, int slot){
        return isNotInItems(item) && isNotInSlots(slot);
    }

    public static boolean shouldBeDeleted(String name, int slot) {
        return isNotInSlots(slot) && isNotInItems(name);
    }

    public static boolean isDestroyItemSlotFocused() {
        //? if >=26.2 {
        /*Screen screen = Minecraft.getInstance().gui.screen();
        *///?} else {
        Screen screen = Minecraft.getInstance().screen;
        //?}
        if (screen instanceof CreativeModeInventoryScreen ciScreen) {
            return ciScreen.hoveredSlot == ciScreen.destroyItemSlot;
        }
        return false;
    }

}
