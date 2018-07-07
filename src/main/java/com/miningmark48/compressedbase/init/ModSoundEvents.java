package com.miningmark48.compressedbase.init;

import com.miningmark48.compressedbase.reference.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.LinkedList;
import java.util.List;

public class ModSoundEvents {

    private static List<SoundEvent> soundsToRegister = new LinkedList<>();

    public static SoundEvent BLOCK_COMPRESSEDBASE_DW20;

    public static void registerSounds(){
        BLOCK_COMPRESSEDBASE_DW20 = createSound("block.compressed_base.dw20");

        registerSound(BLOCK_COMPRESSEDBASE_DW20);
    }

    private static SoundEvent createSound(String soundName){
        final ResourceLocation soundID = new ResourceLocation(Reference.MOD_ID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    private static void registerSound(SoundEvent event){
        soundsToRegister.add(event);
    }

    @SubscribeEvent
    public void registerSoundEvent(RegistryEvent.Register<SoundEvent> event){
        soundsToRegister.forEach(event.getRegistry()::register);
    }

}
