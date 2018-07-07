package com.miningmark48.compressedbase.block;

import com.miningmark48.compressedbase.init.ModSoundEvents;
import com.miningmark48.compressedbase.tile.TileEntityCompressedBase;
import com.miningmark48.compressedbase.world.StructureBase;
import com.miningmark48.compressedbase.world.StructureBase.Directions;
import com.miningmark48.mininglib.utility.KeyChecker;
import com.miningmark48.mininglib.utility.ModTranslate;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockCompressedBase extends BlockContainer {

    private static AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.21875D, 0.0D, 0.21875D, 0.78125D, 0.3125D, 0.78125D);

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockCompressedBase() {
        super(Material.ROCK, MapColor.GRAY);
        this.setHardness(2.0f);
        this.setResistance(1.0f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        { IBlockState iblockstate = worldIn.getBlockState(pos.north()); IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west()); IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);
            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    { return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = state.getValue(FACING).getIndex();


        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.isSneaking()) {
            // Yeah, I know. This logic is bad, but it works :D
            if (!worldIn.isRemote) {
                Rotation rotation = Rotation.NONE;
                EnumFacing chestOrientation = EnumFacing.NORTH;
                Directions directions = Directions.NONE;

                if (state.getValue(FACING) == EnumFacing.NORTH) {
                    rotation = Rotation.NONE;
                    chestOrientation = EnumFacing.NORTH;
                } else if (state.getValue(FACING) == EnumFacing.SOUTH) {
                    rotation = Rotation.CLOCKWISE_180;
                    chestOrientation = EnumFacing.SOUTH;
                } else if (state.getValue(FACING) == EnumFacing.EAST) {
                    rotation = Rotation.CLOCKWISE_90;
                    chestOrientation = EnumFacing.EAST;
                } else if (state.getValue(FACING) == EnumFacing.WEST) {
                    rotation = Rotation.COUNTERCLOCKWISE_90;
                    chestOrientation = EnumFacing.WEST;
                }

                if (isCompressedBaseExtension(worldIn, pos.north())) {
                    if (isCompressedBaseExtension(worldIn, pos.south())) {
                        if (isCompressedBaseExtension(worldIn, pos.east())) {
                            if (isCompressedBaseExtension(worldIn, pos.west())) {
                                directions = Directions.ALL;
                            } else {
                                directions = Directions.NORTH_SOUTH_EAST;
                            }
                        } else if (isCompressedBaseExtension(worldIn, pos.west())) {
                            directions = Directions.NORTH_SOUTH_WEST;
                        } else {
                            directions = Directions.NORTH_SOUTH;
                        }
                    } else if (isCompressedBaseExtension(worldIn, pos.east())) {
                        if (isCompressedBaseExtension(worldIn, pos.west())) {
                            directions = Directions.NORTH_EAST_WEST;
                        } else {
                            directions = Directions.NORTH_EAST;
                        }
                    } else if (isCompressedBaseExtension(worldIn, pos.west())) {
                        directions = Directions.NORTH_WEST;
                    } else {
                        directions = Directions.NORTH;
                    }
                } else if (isCompressedBaseExtension(worldIn, pos.south())) {
                    if (isCompressedBaseExtension(worldIn, pos.east())) {
                        if (isCompressedBaseExtension(worldIn, pos.west())) {
                            directions = Directions.SOUTH_EAST_WEST;
                        } else {
                            directions = Directions.SOUTH_EAST;
                        }
                    } else if (isCompressedBaseExtension(worldIn, pos.west())) {
                        directions = Directions.SOUTH_WEST;
                    } else {
                        directions = Directions.SOUTH;
                    }
                } else if (isCompressedBaseExtension(worldIn, pos.east())) {
                    if (isCompressedBaseExtension(worldIn, pos.west())) {
                        directions = Directions.EAST_WEST;
                    } else {
                        directions = Directions.EAST;
                    }
                } else if (isCompressedBaseExtension(worldIn, pos.west())) {
                    directions = Directions.WEST;
                }

                StructureBase.generateStructure((WorldServer) worldIn, pos, new Random(), rotation, chestOrientation, directions);
                return true;
            }

            Random rand = new Random();
            if (rand.nextInt(2) == 0) {
                playerIn.playSound(ModSoundEvents.BLOCK_COMPRESSEDBASE_DW20, 1.0f, 1.0f);
            } else {
                playerIn.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
            }
            worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, pos.getX(), pos.getY() + 2.5, pos.getZ(), 0D, 0D, 0D);

            return true;
        } else {
            if (worldIn.getTileEntity(pos) instanceof TileEntityCompressedBase) {
                TileEntityCompressedBase te = (TileEntityCompressedBase) worldIn.getTileEntity(pos);
                assert te != null;
                if (worldIn.isRemote) te.startPreview();
                return true;
            }
            return false;
        }
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, @Nullable World world, List par3List, ITooltipFlag par4) {
        if (KeyChecker.isHoldingShift()) {
            par3List.add(TextFormatting.GRAY + ModTranslate.toLocal("tooltip.block.compressed_base.line2"));
            par3List.add(TextFormatting.GRAY + ModTranslate.toLocal("tooltip.block.compressed_base.line3"));
            par3List.add(TextFormatting.GRAY + ModTranslate.toLocal("tooltip.block.compressed_base.line4"));
        } else {
            par3List.add(TextFormatting.AQUA + ModTranslate.toLocal("tooltip.block.compressed_base.line1.p1") + " " + TextFormatting.DARK_GREEN + ModTranslate.toLocal("tooltip.block.compressed_base.line1.p2"));
            par3List.add(TextFormatting.LIGHT_PURPLE + ModTranslate.toLocal("tooltip.block.compressed_base.line5"));
            par3List.add(ModTranslate.toLocal("tooltip.item.hold") + " " + TextFormatting.YELLOW + TextFormatting.ITALIC + ModTranslate.toLocal("tooltip.item.shift"));
        }
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    public static boolean isCompressedBaseExtension(World world, BlockPos pos){
        return world.getBlockState(pos).getBlock() instanceof BlockCompressedBaseExtension;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCompressedBase();
    }
}