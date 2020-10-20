package dev.theturkey.factorymanagement.client.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.theturkey.factorymanagement.client.gui.inputs.BaseInput;
import dev.theturkey.factorymanagement.client.gui.inputs.ItemSearchInput;
import dev.theturkey.factorymanagement.util.Vector2i;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class DraggableWidget
{
	public Vector2i pos = new Vector2i(50, 50);
	public Vector2i size = new Vector2i(80, 40);

	private BaseInput focusedInput = null;
	private List<BaseInput> inputs = new ArrayList<>();

	public DraggableWidget()
	{
		inputs.add(new ItemSearchInput());
	}

	public void render(MatrixStack matrixStack, int offsetX, int offsetY, int mouseX, int mouseY, float partialTicks)
	{
		int x = pos.x + offsetX;
		int y = pos.y + offsetY;

		Matrix4f mat = matrixStack.getLast().getMatrix();
		GuiUtils.drawGradientRect(mat, 0, x, y, x + size.x, y + size.y, 0xff007700, 0xff007700);

		for(BaseInput input : inputs)
		{
			input.render(matrixStack, x, y, mouseX, mouseY, partialTicks);
		}
	}

	public boolean onClick(int x, int y)
	{
		for(BaseInput input : inputs)
		{
			if(input.contains(x, y))
			{
				if(focusedInput != input)
				{
					focusedInput = input;
					focusedInput.onFocus();
				}
				else
				{
					int xOff = x - focusedInput.pos.x;
					int yOff = y - focusedInput.pos.y;
					focusedInput.onClick(xOff, yOff);
				}
				return true;
			}
		}
		if(focusedInput != null)
			focusedInput.onUnfocus();
		focusedInput = null;
		return false;
	}

	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if(focusedInput != null)
			return focusedInput.keyPressed(keyCode, scanCode, modifiers);
		return false;
	}

	public void unfocus()
	{
		if(focusedInput != null)
		{
			focusedInput.onUnfocus();
			focusedInput = null;
		}
	}
}
