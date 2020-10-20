package dev.theturkey.factorymanagement.util;

public class Vector2i
{
	public int x;
	public int y;

	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object p_equals_1_)
	{
		if(this == p_equals_1_)
		{
			return true;
		}
		else if(!(p_equals_1_ instanceof Vector2i))
		{
			return false;
		}
		else
		{
			Vector2i vec = (Vector2i) p_equals_1_;
			if(this.x != vec.x)
				return false;
			else
				return this.y != vec.y;
		}
	}

	@Override
	public String toString()
	{
		return "(" + this.x + "," + this.y + ")";
	}
}
