package dev.theturkey.factorymanagement.client.gui.inputs;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.theturkey.factorymanagement.util.Rectangle2i;
import dev.theturkey.factorymanagement.util.Vector2i;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemSearchInput extends BaseInput
{
	public Vector2i collapsedSize = new Vector2i(20, 20);
	public Vector2i expandedSize = new Vector2i(100, 60);
	public boolean expanded = false;
	private ItemStack stack = new ItemStack(Blocks.OAK_LOG, 1);

	private Rectangle2i searchBox = new Rectangle2i(25, 5, 70, 10);
	public boolean searching = false;
	private String searchString = "";
	private List<Item> searchCache = new ArrayList<>();

	public ItemSearchInput()
	{
	}

	@Override
	public void onClick(int x, int y)
	{
		searching = x >= searchBox.x && x <= searchBox.x + searchBox.width && y >= searchBox.y && y <= searchBox.y + searchBox.height;

		if(y > 20)
		{
			int index = ((x / 20) * 2) + ((y - 20) / 20);
			if(index < searchCache.size())
				stack = new ItemStack(searchCache.get(index));
		}
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		System.out.println(keyCode);
		if(searching)
		{
			boolean doSearch = false;
			if(keyCode >= 65 && keyCode <= 90)
			{
				searchString += String.valueOf((char) keyCode).toLowerCase();
				doSearch = true;
			}
			else if(keyCode == 259 && searchString.length() > 0)
			{
				searchString = searchString.substring(0, searchString.length() - 1);
				doSearch = true;
			}
			else if(keyCode == 32)
			{
				searchString += " ";
				doSearch = true;
			}
			else if(keyCode == 59)
			{
				searchString += ":";
				doSearch = true;
			}

			if(doSearch)
			{
				search();
				return true;
			}
		}
		return false;
	}

	@Override
	public void onFocus()
	{
		super.onFocus();
		expanded = true;
		size.x = expandedSize.x;
		size.y = expandedSize.y;
	}

	@Override
	public void onUnfocus()
	{
		super.onUnfocus();
		expanded = false;
		searching = false;
		size.x = collapsedSize.x;
		size.y = collapsedSize.y;
	}

	@Override
	public void render(MatrixStack matrixStack, int offsetX, int offsetY, int mouseX, int mouseY, float partialTicks)
	{
		super.render(matrixStack, offsetX, offsetY, mouseX, mouseY, partialTicks);

		Matrix4f mat = matrixStack.getLast().getMatrix();
		int x = pos.x + offsetX;
		int y = pos.y + offsetY;

		if(expanded)
		{
			GuiUtils.drawGradientRect(mat, 0, x, y, x + 20, y + 20, 0xff888888, 0xff888888);
			Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, x + 2, y + 2);
			GuiUtils.drawGradientRect(mat, 0, x + searchBox.x, y + searchBox.y, x + searchBox.x + searchBox.width, y + searchBox.y + searchBox.height, searching ? 0xff888888 : 0xff333333, searching ? 0xff888888 : 0xff333333);
			FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
			fontrenderer.drawString(matrixStack, searchString, x + searchBox.x + 3, y + searchBox.y + 1, 0xd1d1d1);
			for(int i = 0; i < 5; i++)
			{
				for(int j = 0; j < 2; j++)
				{
					if(searchCache.size() > (i * 2) + j)
					{
						ItemStack stack = new ItemStack(searchCache.get((i * 2) + j));
						int xx = x + (i * 20);
						int yy = y + 20 + (j * 20);
						if(mouseX >= xx && mouseX <= xx + 20 && mouseY >= yy && mouseY <= yy + 20)
							GuiUtils.drawGradientRect(mat, 0, xx, yy, xx + 20, yy + 20, 0x88888888, 0x88888888);
						Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, xx + 2, yy + 2);
					}
				}
			}
		}
		else
		{
			Minecraft.getInstance().getItemRenderer().renderItemIntoGUI(stack, x + 2, y + 2);
		}
	}

	public void search()
	{
		searchCache.clear();
		for(Map.Entry<RegistryKey<Item>, Item> entry : ForgeRegistries.ITEMS.getEntries())
		{
			Item item = entry.getValue();
			if(entry.getKey().getLocation().toString().contains(searchString))
				searchCache.add(item);
			else if(item.getName().getString().toLowerCase().contains(searchString))
				searchCache.add(item);
		}
	}
}
