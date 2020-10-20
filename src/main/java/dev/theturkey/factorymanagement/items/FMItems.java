package dev.theturkey.factorymanagement.items;

import dev.theturkey.factorymanagement.FMCore;
import dev.theturkey.factorymanagement.blocks.FMBlocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FMCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FMItems
{
	public static FMBlockItem factoryManager;
	public static FMBlockItem pipe;

	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> e)
	{
		e.getRegistry().register(factoryManager = new FMBlockItem(FMBlocks.factoryManager));
		e.getRegistry().register(pipe = new FMBlockItem(FMBlocks.pipe));
	}
}
