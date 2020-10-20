package dev.theturkey.factorymanagement.blocks;

import dev.theturkey.factorymanagement.FMCore;
import dev.theturkey.factorymanagement.tileentities.FactoryManagerTile;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FMCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FMBlocks
{
	public static FactoryManagerBlock factoryManager;
	public static ItemPipeBlock pipe;

	public static TileEntityType<FactoryManagerTile> tileFactoryManager;

	@SubscribeEvent
	public static void onBlockRegistry(RegistryEvent.Register<Block> e)
	{
		e.getRegistry().register(factoryManager = new FactoryManagerBlock());
		e.getRegistry().register(pipe = new ItemPipeBlock());
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void onTileEntityRegistry(RegistryEvent.Register<TileEntityType<?>> event)
	{
		event.getRegistry().register(tileFactoryManager = (TileEntityType<FactoryManagerTile>) TileEntityType.Builder.create(FactoryManagerTile::new, FMBlocks.factoryManager).build(null).setRegistryName(FMCore.MOD_ID, "tile_factory_manager"));
	}
}
