package utils;

public class CobolNameUtil
{
	public static String fixJavaName(String name)
	{
		return Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
	}
}
