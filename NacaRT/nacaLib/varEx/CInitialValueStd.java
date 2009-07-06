/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.varEx;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CInitialValueStd.java,v 1.1 2006/04/19 09:52:53 cvsadmin Exp $
 */
public class CInitialValueStd
{
	public static CInitialValue Spaces = new CInitialValue(CobolConstant.Space.getValue(), true);
	public static CInitialValue Zero = new CInitialValue(CobolConstant.Zero.getValue(), true);
	public static CInitialValue HighValue = new CInitialValue(CobolConstant.HighValue.getValue(), true);
	public static CInitialValue LowValue = new CInitialValue(CobolConstant.LowValue.getValue(), true);
}
