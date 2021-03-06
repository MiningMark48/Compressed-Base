package com.miningmark48.compressedbase.gui;

import com.miningmark48.compressedbase.config.ConfigurationHandler;
import com.miningmark48.compressedbase.reference.Reference;
import com.miningmark48.mininglib.utility.ModTranslate;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import scala.actors.threadpool.Arrays;

public class GuiModConfig extends GuiConfig {

    public GuiModConfig(GuiScreen guiScreen){
        super(guiScreen,
                Arrays.asList(new IConfigElement[]{
                        new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_CLIENT)),
                        new ConfigElement(ConfigurationHandler.configuration.getCategory(ModTranslate.toLocal("config.category.compressedBase.title")))
                }),
                Reference.MOD_ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
    }
}
