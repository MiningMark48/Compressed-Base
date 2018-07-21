package com.miningmark48.compressedbase.tile.renderer;

import com.miningmark48.compressedbase.block.BlockCompressedBase;
import com.miningmark48.compressedbase.client.RenderPreview;
import com.miningmark48.compressedbase.tile.TileEntityCompressedBase;
import com.miningmark48.compressedbase.util.WorldUtil;
import com.miningmark48.mininglib.utility.ModLogger;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static net.minecraft.block.BlockColored.COLOR;

public class TESRCompressedBase extends TileEntitySpecialRenderer<TileEntityCompressedBase> {

    @Override
    public void func_192841_a(TileEntityCompressedBase te, double x, double y, double z, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
        if (te != null) {
            if (!te.getTileData().hasKey("show_preview")) {
                te.getTileData().setBoolean("show_preview", false);
            }

            if (te.getTileData().getBoolean("show_preview")) {
                RenderPreview.doRender(te, 0, 0);

                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().north())) RenderPreview.doRender(te, 0, -12);    //North render
                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().south())) RenderPreview.doRender(te, 0, 12);     //South render
                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().east())) RenderPreview.doRender(te, 12, 0);      //East render
                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().west())) RenderPreview.doRender(te, -12, 0);     //West render

            }
        }

    }

}
