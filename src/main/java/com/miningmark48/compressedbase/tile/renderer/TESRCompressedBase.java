package com.miningmark48.compressedbase.tile.renderer;

import com.miningmark48.compressedbase.block.BlockCompressedBase;
import com.miningmark48.compressedbase.tile.TileEntityCompressedBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.awt.*;

public class TESRCompressedBase extends TileEntitySpecialRenderer<TileEntityCompressedBase> {

    @Override
    public void func_192841_a(TileEntityCompressedBase te, double x, double y, double z, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
        if (te != null) {
            if (!te.getTileData().hasKey("show_preview")) {
                te.getTileData().setBoolean("show_preview", false);
            }

            if (te.getTileData().getBoolean("show_preview")) {
                doRender(te, 0, 0);

                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().north())) doRender(te, 0, -12);    //North render
                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().south())) doRender(te, 0, 12);     //South render
                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().east())) doRender(te, 12, 0);      //East render
                if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().west())) doRender(te, -12, 0);     //West render

            }
        }

    }

    private static void doRender(TileEntityCompressedBase te, float xOffset, float zOffset) {
        IBlockState renderState = Blocks.COBBLESTONE.getDefaultState();
        EntityPlayer player = Minecraft.getMinecraft().player;
        double doubleX = player.posX - 0.5;
        double doubleY = player.posY + 0.1;
        double doubleZ = player.posZ - 0.5;

        float offset = 0.25f;

        float xSize = 9f + offset;
        float ySize = 6f + offset;
        float zSize = 9f + offset;

        float mx = te.getPos().getX() + xOffset + (-xSize / 2);
        float my = te.getPos().getY();
        float mz = te.getPos().getZ() + zOffset + (-zSize / 2);

        int horizontalSize = 8;
        int vertSize = 4;

        GlStateManager.disableLighting();
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        for (int j = 0; j <= vertSize; j++) {
            for (int i = 0; i <= horizontalSize; i++) {
                renderBlock(doubleX, doubleY, doubleZ, renderState, new BlockPos(mx, my, mz).east(i).up(j), te.getWorld(), buffer, tess, dispatcher);
            }
            for (int i = 0; i <= horizontalSize; i++) {
                renderBlock(doubleX, doubleY, doubleZ, renderState, new BlockPos(mx, my, mz).south(i).up(j), te.getWorld(), buffer, tess, dispatcher);
            }
            for (int i = 0; i <= horizontalSize; i++) {
                renderBlock(doubleX, doubleY, doubleZ, renderState, new BlockPos(mx + horizontalSize, my, mz + horizontalSize).east(-i).up(j), te.getWorld(), buffer, tess, dispatcher);
            }
            for (int i = 0; i <= horizontalSize; i++) {
                renderBlock(doubleX, doubleY, doubleZ, renderState, new BlockPos(mx + horizontalSize, my, mz + horizontalSize).south(-i).up(j), te.getWorld(), buffer, tess, dispatcher);
            }
        }

        for (int i = 0; i <= horizontalSize; i++) {
            for (int j = 0; j <= horizontalSize; j++) {
                renderBlock(doubleX, doubleY, doubleZ, renderState, new BlockPos(mx, my, mz).up(vertSize).east(i).south(j), te.getWorld(), buffer, tess, dispatcher);
            }
        }

    }

    private static void renderBlock(double doubleX, double doubleY, double doubleZ, IBlockState state, BlockPos pos, World world, BufferBuilder buffer, Tessellator tess, BlockRendererDispatcher dispatcher) {
        BlockPos newPos = pos.add(1, 0, 1);

        if (!(world.getBlockState(newPos).getBlock() == Blocks.AIR)) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_CONSTANT_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);
        buffer.begin(7, DefaultVertexFormats.BLOCK);

        IBlockState newState = state.getActualState(world, newPos);

        GL14.glBlendColor(1f, 1f, 1f, 0.5f);

        dispatcher.renderBlock(newState, newPos, world, buffer);
        GlStateManager.translate(-0.5, 0.1, -0.5);

        tess.draw();
        GlStateManager.popMatrix();
    }


}
