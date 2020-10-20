package dev.theturkey.factorymanagement.items;

import dev.theturkey.factorymanagement.FMCore;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class FMBlockItem extends BlockItem
{
	public FMBlockItem(Block b)
	{
		super(b, getProps(b));
		this.setRegistryName(b.getRegistryName());
	}

	public static Properties getProps(Block b)
	{
		Properties props = new Properties();
		props.group(FMCore.modTab);
		return props;
	}
}
