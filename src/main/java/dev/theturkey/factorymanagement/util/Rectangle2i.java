package dev.theturkey.factorymanagement.util;

public class Rectangle2i
{
	public int x;
	public int y;
	public int width;
	public int height;

	public Rectangle2i(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean equals(Object p_equals_1_)
	{
		if(this == p_equals_1_)
		{
			return true;
		}
		else if(!(p_equals_1_ instanceof Rectangle2i))
		{
			return false;
		}
		else
		{
			Rectangle2i rect = (Rectangle2i) p_equals_1_;
			if(this.x != rect.x)
				return false;
			else if(this.y != rect.y)
				return false;
			else if(this.width != rect.width)
				return false;
			else
				return this.height != rect.height;
		}
	}

	@Override
	public String toString()
	{
		return "(" + this.x + "," + this.y + "," + this.width + "," + this.height + ")";
	}
}
