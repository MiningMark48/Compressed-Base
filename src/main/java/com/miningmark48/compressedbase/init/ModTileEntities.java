package com.miningmark48.compressedbase.init;

import com.miningmark48.compressedbase.tile.TileEntityCompressedBase;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void init() {
        GameRegistry.registerTileEntity(TileEntityCompressedBase.class, "compressed_base");
    }

}
