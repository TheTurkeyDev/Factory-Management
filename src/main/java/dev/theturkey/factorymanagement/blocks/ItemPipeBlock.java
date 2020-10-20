package dev.theturkey.factorymanagement.blocks;

import dev.theturkey.factorymanagement.FMCore;

public class ItemPipeBlock extends BasePipeBlock
{
	public ItemPipeBlock()
	{
		super();
		this.setRegistryName(FMCore.MOD_ID, "pipe");
		this.setDefaultState(this.getDefaultState().with(NORTH, EnumAttachType.NONE).with(EAST, EnumAttachType.NONE).with(SOUTH, EnumAttachType.NONE).with(WEST, EnumAttachType.NONE).with(UP, EnumAttachType.NONE).with(DOWN, EnumAttachType.NONE));
	}
}
