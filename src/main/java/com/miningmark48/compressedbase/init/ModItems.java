package com.miningmark48.compressedbase.init;

import com.miningmark48.compressedbase.item.ItemBasePreviewer;
import com.miningmark48.mininglib.base.item.ModBaseItem;
import net.minecraft.item.Item;

public class ModItems {

    public static Item component_amenity;
    public static Item component_base;
    public static Item component_ceiling;
    public static Item component_wall;

    public static Item base_previewer;


    public static void init() {
        component_amenity = new ModBaseItem().setUnlocalizedName("component_amenity").setRegistryName("component_amenity").setCreativeTab(ModCreativeTab.CompressedBase_Tab);
        component_base = new ModBaseItem().setUnlocalizedName("component_base").setRegistryName("component_base").setCreativeTab(ModCreativeTab.CompressedBase_Tab);
        component_ceiling = new ModBaseItem().setUnlocalizedName("component_ceiling").setRegistryName("component_ceiling").setCreativeTab(ModCreativeTab.CompressedBase_Tab);
        component_wall = new ModBaseItem().setUnlocalizedName("component_wall").setRegistryName("component_wall").setCreativeTab(ModCreativeTab.CompressedBase_Tab);

        base_previewer = new ItemBasePreviewer().setUnlocalizedName("base_previewer").setRegistryName("base_previewer").setCreativeTab(ModCreativeTab.CompressedBase_Tab);
    }

}
