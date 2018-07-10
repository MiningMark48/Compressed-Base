package com.miningmark48.compressedbase.config;

import com.miningmark48.compressedbase.reference.Reference;
import com.miningmark48.mininglib.utility.ModTranslate;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler {

    public static Configuration configuration;

    //Client
    static boolean showPreviewRender;

    //Compressed Base
    static boolean doLoot;

    public static void init(File configFile){

        //Create the configuration object from the given configuration file
        if (configuration == null){
            configuration = new Configuration(configFile);
            loadConfiguration();
        }

    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){

        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)){
            loadConfiguration();
        }

    }

    private static void loadConfiguration(){

        //Categories
        configuration.addCustomCategoryComment(ModTranslate.toLocal("config.category.compressedBase.title"), ModTranslate.toLocal("config.category.compressedBase.desc"));

        //Client
        showPreviewRender = configuration.getBoolean(ModTranslate.toLocal("config.showPreviewRender.title"), Configuration.CATEGORY_CLIENT, true, ModTranslate.toLocal("config.showPreviewRender.desc"));

        //Compressed Base
        doLoot = configuration.getBoolean(ModTranslate.toLocal("config.doLoot.title"), ModTranslate.toLocal("config.category.compressedBase.title"), true, ModTranslate.toLocal("config.doLoot.desc"));

        if (configuration.hasChanged()){
            configuration.save();
        }

    }

}