package com.miningmark48.compressedbase.event;

import com.miningmark48.compressedbase.client.RenderPreview;
import com.miningmark48.compressedbase.item.ItemBasePreviewer;
import com.miningmark48.compressedbase.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MOD_ID)
public class EventRenderBasePreview {

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer p = mc.player;
        ItemStack heldItem = p.getHeldItemMainhand();

        if (!(heldItem.getItem() instanceof ItemBasePreviewer)) {
            heldItem = p.getHeldItemOffhand();
            if (!(heldItem.getItem() instanceof ItemBasePreviewer)) {
                return;
            }
        }

        if (heldItem.getItem() instanceof ItemBasePreviewer) {
            RenderPreview.doRenderItem(p, heldItem);
        }
    }

}
