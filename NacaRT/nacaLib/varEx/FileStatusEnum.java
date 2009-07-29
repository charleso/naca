/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;


public class FileStatusEnum
{
	public static FileStatusEnum OK = new FileStatusEnum("OK", "00");
	public static FileStatusEnum END_OF_FILE = new FileStatusEnum("END_OF_FILE", "10");
	public static FileStatusEnum COULD_NOT_OPEN_FILE = new FileStatusEnum("COULD_NOT_OPEN_FILE", "41");
	public static FileStatusEnum COULD_NOT_CLOSE_FILE = new FileStatusEnum("COULD_NOT_CLOSE_FILE", "42");

	private String m_csDescription =  null;
	private String m_csValue = "00";
	
	private FileStatusEnum(String csDescrption, String csValue)
	{
		m_csDescription = csDescrption;
		m_csValue = csValue;
	}
	
	public String getCode()
	{
		return m_csValue;
	}
}