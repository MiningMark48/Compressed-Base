package com.miningmark48.compressedbase.init;

import com.miningmark48.compressedbase.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ModCreativeTab {

    public static final CreativeTabs CompressedBase_Tab = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.compressed_base);
        }
    };

}
