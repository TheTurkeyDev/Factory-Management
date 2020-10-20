package dev.theturkey.factorymanagement.client.gui.inputs;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.theturkey.factorymanagement.util.Vector2i;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class BaseInput
{
	public Vector2i pos = new Vector2i(5, 5);
	public Vector2i size = new Vector2i(30, 10);

	private int color = 0xff000077;

	public void render(MatrixStack matrixStack, int offsetX, int offsetY, int mouseX, int mouseY, float partialTicks)
	{
		int x = pos.x + offsetX;
		int y = pos.y + offsetY;
		Matrix4f mat = matrixStack.getLast().getMatrix();
		GuiUtils.drawGradientRect(mat, 0, x, y, x + size.x, y + size.y, color, color);
	}

	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		return false;
	}

	public void onFocus()
	{
		color = 0xff0000dd;
	}

	public void onUnfocus()
	{
		color = 0xff000044;
	}

	public void onClick(int x, int y)
	{

	}


	public boolean contains(int x, int y)
	{
		return x >= pos.x && x <= pos.x + size.x && y >= pos.y && y <= pos.y + size.y;
	}
}
