package dev.theturkey.factorymanagement.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.theturkey.factorymanagement.client.gui.widgets.DraggableWidget;
import dev.theturkey.factorymanagement.util.Vector2i;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FactoryManagerGUI extends Screen
{
	private List<DraggableWidget> widgets = new ArrayList<>();
	public DraggableWidget focusedWidget = null;
	public boolean dragging = false;
	private Vector2i offset = new Vector2i(0, 0);

	public FactoryManagerGUI()
	{
		super(new StringTextComponent("Factory Manager"));
	}

	@Override
	public void init(Minecraft minecraft, int width, int height)
	{
		super.init(minecraft, width, height);
		addButton(new Button(width - 30, 15, 20, 20, new StringTextComponent("+"), (b) ->
		{
			widgets.add(new DraggableWidget());
		}));
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		boolean widgetClicked = false;
		dragging = false;
		if(button == 0)
		{
			if(focusedWidget != null && focusedWidget.onClick((int) mouseX - focusedWidget.pos.x, (int) mouseY - focusedWidget.pos.y))
				return true;

			for(DraggableWidget widget : widgets)
			{
				Vector2i pos = widget.pos;
				if(pos.x < mouseX && pos.x + widget.size.x > mouseX && pos.y < mouseY && pos.y + widget.size.y > mouseY)
				{
					widgetClicked = true;
					focusedWidget = widget;
					if(!focusedWidget.onClick((int) mouseX - pos.x, (int) mouseY - pos.y))
					{
						dragging = true;
						offset.x = (int) mouseX - pos.x;
						offset.y = (int) mouseY - pos.y;
						pos.x += offset.x;
						pos.y += offset.y;
					}
					break;
				}
			}
		}

		if(!widgetClicked)
		{
			if(focusedWidget != null)
				focusedWidget.unfocus();
			focusedWidget = null;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	public boolean mouseReleased(double mouseX, double mouseY, int button)
	{
		if(focusedWidget != null && dragging)
		{
			focusedWidget.pos.x -= offset.x;
			focusedWidget.pos.y -= offset.y;
			offset.x = 0;
			offset.y = 0;
		}
		dragging = false;
		return super.mouseReleased(mouseX, mouseY, button);
	}

	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
	{
		if(focusedWidget != null && dragging)
		{
			focusedWidget.pos.x = (int) mouseX;
			focusedWidget.pos.y = (int) mouseY;
		}
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if(focusedWidget != null)
			return focusedWidget.keyPressed(keyCode, scanCode, modifiers);
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		Matrix4f mat = matrixStack.getLast().getMatrix();
		GuiUtils.drawGradientRect(mat, 0, 10, 10, width - 10, height - 10, 0xff330000, 0xff330000);

		for(DraggableWidget widget : widgets)
		{
			int offX = widget == focusedWidget ? -offset.x : 0;
			int offY = widget == focusedWidget ? -offset.y : 0;
			widget.render(matrixStack, offX, offY, mouseX, mouseY, partialTicks);
		}
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
}
