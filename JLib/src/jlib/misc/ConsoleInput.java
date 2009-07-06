/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ConsoleInput
{
	private static BufferedReader ms_keyboardInput = null;

	public static String getKeyboardLine()
	{
		if(ms_keyboardInput == null)
			ms_keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		
		try
		{
			return ms_keyboardInput.readLine();
		}
		catch (IOException e)
		{
		}
		return "";  
	}
	
	public static char getKeyboardChar()
	{
		try
		{
			int n = ms_keyboardInput.read();
			char c = (char)n;
			return c;
		}
		catch (IOException e)
		{
		}
		return 0;  
	}
}
