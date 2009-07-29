/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 27 juil. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author u930di
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

public class VarTypeEnum
{
	public static final VarTypeEnum TypeNone = new VarTypeEnum(' ');
	public static final VarTypeEnum TypeX = new VarTypeEnum('X');
	public static final VarTypeEnum Type9 = new VarTypeEnum('9');
	public static final VarTypeEnum TypeGroup = new VarTypeEnum('G');
	public static final VarTypeEnum TypeEditedNum = new VarTypeEnum('N');
	public static final VarTypeEnum TypeEditedAlphaNum = new VarTypeEnum('A');
	public static final VarTypeEnum TypeFieldEdit = new VarTypeEnum('E');
	
	VarTypeEnum(char c)
	{
		m_c = c;
	}
	
	char m_c = 'X';
}
