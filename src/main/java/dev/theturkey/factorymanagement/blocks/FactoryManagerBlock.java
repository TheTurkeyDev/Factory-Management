package dev.theturkey.factorymanagement.blocks;

import dev.theturkey.factorymanagement.FMCore;
import dev.theturkey.factorymanagement.client.ClientHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class FactoryManagerBlock extends Block
{
	public FactoryManagerBlock()
	{
		super(Properties.create(Material.EARTH).hardnessAndResistance(0.5f, 0.5f));
		this.setRegistryName(FMCore.MOD_ID, "factory_manager");
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
	{
		ClientHelper.openFactoryManagerGUI();
		return ActionResultType.PASS;
	}
}
