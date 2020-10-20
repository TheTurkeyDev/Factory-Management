package dev.theturkey.factorymanagement.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BasePipeBlock extends Block
{

	public static final EnumProperty<EnumAttachType> NORTH = EnumProperty.create("north", EnumAttachType.class);
	public static final EnumProperty<EnumAttachType> EAST = EnumProperty.create("east", EnumAttachType.class);
	public static final EnumProperty<EnumAttachType> SOUTH = EnumProperty.create("south", EnumAttachType.class);
	public static final EnumProperty<EnumAttachType> WEST = EnumProperty.create("west", EnumAttachType.class);
	public static final EnumProperty<EnumAttachType> UP = EnumProperty.create("up", EnumAttachType.class);
	public static final EnumProperty<EnumAttachType> DOWN = EnumProperty.create("down", EnumAttachType.class);

	public static final VoxelShape BASE_SHAPE = Block.makeCuboidShape(7, 7, 7, 9, 9, 9);

	public static final Map<Direction, CustomFacingHolder> FACING_MAPPING = new HashMap<Direction, CustomFacingHolder>()
	{
		{
			put(Direction.NORTH, new CustomFacingHolder(NORTH, Block.makeCuboidShape(7, 7, 0, 9, 9, 8)));
			put(Direction.EAST, new CustomFacingHolder(EAST, Block.makeCuboidShape(8, 7, 7, 16, 9, 9)));
			put(Direction.SOUTH, new CustomFacingHolder(SOUTH, Block.makeCuboidShape(7, 7, 8, 9, 9, 16)));
			put(Direction.WEST, new CustomFacingHolder(WEST, Block.makeCuboidShape(0, 7, 7, 8, 9, 9)));
			put(Direction.UP, new CustomFacingHolder(UP, Block.makeCuboidShape(7, 8, 7, 9, 16, 9)));
			put(Direction.DOWN, new CustomFacingHolder(DOWN, Block.makeCuboidShape(7, 0, 7, 9, 8, 9)));
		}
	};

	public BasePipeBlock()
	{
		super(Properties.create(Material.EARTH).hardnessAndResistance(0.5f, 0.5f));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		VoxelShape shape = BASE_SHAPE;
		for(Direction side : Direction.values())
			if(state.get(FACING_MAPPING.get(side).direction).isSegment())
				shape = VoxelShapes.or(shape, FACING_MAPPING.get(side).voxelShape);
		return shape;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		return this.getShape(state, world, pos, context);
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean p_220069_6_)
	{
		BlockState updatedState = getState(world.getBlockState(pos), world, pos);
		if(updatedState.equals(state))
			return;

		world.setBlockState(pos, updatedState);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return getState(this.getDefaultState(), context.getWorld(), context.getPos());
	}

	public BlockState getState(BlockState state, World world, BlockPos pos)
	{
		if(!state.getBlock().equals(this))
			return state;

		for(Direction side : Direction.values())
			state = state.with(FACING_MAPPING.get(side).direction, world.getBlockState(pos.offset(side)).getBlock().equals(this) ? EnumAttachType.PIPE : EnumAttachType.NONE);

		return state;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
		builder.add(BlockStateProperties.WATERLOGGED);
	}

	public enum EnumAttachType implements IStringSerializable
	{
		NONE, PIPE, INVENTORY;

		public boolean isSegment()
		{
			return this == PIPE || this == INVENTORY;
		}

		@Override
		public String getString()
		{
			return this.name().toLowerCase();
		}
	}

	public static class CustomFacingHolder
	{
		public EnumProperty<EnumAttachType> direction;
		public VoxelShape voxelShape;

		public CustomFacingHolder(EnumProperty<EnumAttachType> direction, VoxelShape boundingBox)
		{
			this.direction = direction;
			this.voxelShape = boundingBox;
		}

	}
}