package com.miningmark48.compressedbase.proxy;

import com.miningmark48.compressedbase.init.ModRegistry;
import com.miningmark48.compressedbase.tile.TileEntityCompressedBase;
import com.miningmark48.compressedbase.tile.renderer.TESRCompressedBase;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event){

    }

    @Override
    public void registerRenders(){
        ModRegistry.registerRenderItems();
        ModRegistry.registerRenderBlocks();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCompressedBase.class, new TESRCompressedBase());

    }

}
