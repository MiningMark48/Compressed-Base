package com.miningmark48.compressedbase;

import com.miningmark48.compressedbase.command.CommandSpawnBase;
import com.miningmark48.compressedbase.config.ConfigurationHandler;
import com.miningmark48.compressedbase.event.EventRenderBasePreview;
import com.miningmark48.compressedbase.init.*;
import com.miningmark48.compressedbase.proxy.CommonProxy;
import com.miningmark48.compressedbase.reference.Reference;
import com.miningmark48.mininglib.utility.ModLogger;
import com.miningmark48.mininglib.utility.ModTranslate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid= Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS, dependencies = Reference.MOD_DEPENDENCIES, acceptedMinecraftVersions = Reference.GAME_VERSION)
public class CompressedBase {

    @Mod.Instance(Reference.MOD_ID)
    public static CompressedBase instance;

    @SidedProxy(clientSide=Reference.CLIENT_PROXY_CLASS, serverSide=Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());

        ModBlocks.init();
        ModTileEntities.init();
        ModItems.init();
        ModRegistry.init();

        MinecraftForge.EVENT_BUS.register(new ModRegistry());

        proxy.preInit(event);

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){

        proxy.registerRenders();
        proxy.init(event);

        ModRecipes.init();
        ModSoundEvents.registerSounds();

        MinecraftForge.EVENT_BUS.register(new ModSoundEvents());

        ModLogger.info(ModTranslate.toLocal("log.info.init"));

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

        ModLogger.info(ModTranslate.toLocal("log.info.postinit"));

    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandSpawnBase());
    }

}
