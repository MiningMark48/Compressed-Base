package com.miningmark48.compressedbase.init;

import com.miningmark48.compressedbase.block.*;
import com.miningmark48.mininglib.base.block.ModBaseBlock;
import com.miningmark48.mininglib.base.block.ModBaseBlockTransparent;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks {

    public static Block compressed_base;
    public static Block compressed_base_extension;

    public static void init() {
        compressed_base = new BlockCompressedBase().setUnlocalizedName("compressed_base").setRegistryName("compressed_base").setCreativeTab(ModCreativeTab.CompressedBase_Tab);
        compressed_base_extension = new BlockCompressedBaseExtension().setUnlocalizedName("compressed_base_extension").setRegistryName("compressed_base_extension").setCreativeTab(ModCreativeTab.CompressedBase_Tab);
    }

}
