package com.miningmark48.compressedbase.tile.renderer;

import com.miningmark48.compressedbase.block.BlockCompressedBase;
import com.miningmark48.compressedbase.tile.TileEntityCompressedBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TESRCompressedBase extends TileEntitySpecialRenderer<TileEntityCompressedBase> {

    @Override
    public void func_192841_a(TileEntityCompressedBase te, double x, double y, double z, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
        doRender(te, 0, 0);

        if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().east())) doRender(te, 12, 0);
        if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().south())) doRender(te, 0, 12);
        if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().west())) doRender(te, -12, 0);
        if (BlockCompressedBase.isCompressedBaseExtension(te.getWorld(), te.getPos().north())) doRender(te, 0, -12);

    }

    private static void doRender(TileEntityCompressedBase te, float xOffset, float zOffset) {
        if (te != null) {
            if (!te.getTileData().hasKey("show_preview")) {
                te.getTileData().setBoolean("show_preview", false);
            }

            if (te.getTileData().getBoolean("show_preview")) {

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

                Color colorBorder = Color.CYAN;

                GlStateManager.pushMatrix();
                GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

                GlStateManager.disableLighting();
                Tessellator tess = Tessellator.getInstance();
                BufferBuilder buffer = tess.getBuffer();

//                GlStateManager.enableBlend();
//                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//                RenderGlobal.renderFilledBox(mx, my, mz, mx + xSize, my + ySize, mz + zSize, 162f / 255f, 240f / 255f, 0f / 255f, 0.25f);

                buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
                GL11.glLineWidth(1.5f);
                RenderGlobal.drawBoundingBox(buffer, mx, my, mz, mx + xSize, my + ySize, mz + zSize, colorBorder.getRed() / 255f, colorBorder.getGreen() / 255f, colorBorder.getBlue() / 255f, 1f);
                tess.draw();



                GlStateManager.popMatrix();
            }
        }
    }


}
