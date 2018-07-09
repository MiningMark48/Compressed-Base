package com.miningmark48.compressedbase.util;

import com.miningmark48.compressedbase.tile.TileEntityCompressedBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedList;

public class WorldUtil {

    public static LinkedList<BlockPos> getBlocksWithinArea(BlockPos centralPos, int horizontal, int vertical) {
        return getBlocksWithinArea(centralPos, horizontal, vertical, vertical);
    }

    public static LinkedList<BlockPos> getBlocksWithinArea(BlockPos centralPos, int horizontal, int up, int down) {
        BlockPos from = centralPos.add(-horizontal, -down, -horizontal);
        BlockPos to = centralPos.add(horizontal, up, horizontal);
        LinkedList<BlockPos> blockList = new LinkedList<>();
        BlockPos.getAllInBox(from, to).forEach(blockList::add);
        return blockList;
    }

    public static boolean doesAreaContainUnbreakable(World world, LinkedList<BlockPos> blockList) {
        for (BlockPos blockPos : blockList) {
            IBlockState block = world.getBlockState(blockPos);
            if (block.getBlockHardness(world, blockPos) < 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean doesAreaContainTileEntity(World world, LinkedList<BlockPos> blockList) {
        for (BlockPos blockPos : blockList) {
            if (world.getTileEntity(blockPos) != null && !(world.getTileEntity(blockPos) instanceof TileEntityCompressedBase)) {
                return true;
            }
        }
        return false;
    }

}
