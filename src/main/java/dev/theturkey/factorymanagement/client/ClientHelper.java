package dev.theturkey.factorymanagement.client;

import dev.theturkey.factorymanagement.client.gui.FactoryManagerGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHelper
{
	@SubscribeEvent
	public static void clientStart(FMLClientSetupEvent event)
	{

	}

	public static void openFactoryManagerGUI()
	{
		Minecraft.getInstance().displayGuiScreen(new FactoryManagerGUI());
	}
}
