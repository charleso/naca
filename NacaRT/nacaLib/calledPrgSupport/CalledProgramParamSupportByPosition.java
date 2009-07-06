/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.calledPrgSupport;

import java.util.ArrayList;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CalledProgramParamSupportByPosition.java,v 1.2 2007/09/21 15:11:30 u930bm Exp $
 */
public class CalledProgramParamSupportByPosition
{
	//protected ProgramParamListPositioned m_arrPublicArgs = null;
	protected ArrayList<BaseCalledPrgPublicArgPositioned> m_arrPublicArgs = null;
	
	protected CalledProgramParamSupportByPosition()
	{
		m_arrPublicArgs = new ArrayList<BaseCalledPrgPublicArgPositioned>();
	}
	
	// String
	public void setIn(String csValue)
	{
		CalledPrgPublicArgStringInPositioned arg = new CalledPrgPublicArgStringInPositioned(csValue); 
		m_arrPublicArgs.add(arg);
	}

	public void setInOut(String [] csioValue)
	{
		CalledPrgPublicArgStringOutPositioned arg = new CalledPrgPublicArgStringOutPositioned(csioValue, true); 
		m_arrPublicArgs.add(arg);
	}
	
	public void setOut(String [] csoValue)
	{
		CalledPrgPublicArgStringOutPositioned arg = new CalledPrgPublicArgStringOutPositioned(csoValue, false); 
		m_arrPublicArgs.add(arg);
	}
	
	// Int
	public void setIn(int nValue)
	{
		CalledPrgPublicArgIntInPositioned arg = new CalledPrgPublicArgIntInPositioned(nValue); 
		m_arrPublicArgs.add(arg);
	}

	public void setInOut(int [] nioValue)
	{
		CalledPrgPublicArgIntOutPositioned arg = new CalledPrgPublicArgIntOutPositioned(nioValue, true); 
		m_arrPublicArgs.add(arg);
	}
	
	public void setOut(int [] noValue)
	{
		CalledPrgPublicArgIntOutPositioned arg = new CalledPrgPublicArgIntOutPositioned(noValue, false); 
		m_arrPublicArgs.add(arg);
	}
	
	// Double
	public void setIn(double diValue)
	{
		CalledPrgPublicArgDoubleInPositioned arg = new CalledPrgPublicArgDoubleInPositioned(diValue); 
		m_arrPublicArgs.add(arg);
	}

	public void setInOut(double [] dioValue)
	{
		CalledPrgPublicArgDoubleOutPositioned arg = new CalledPrgPublicArgDoubleOutPositioned(dioValue, true); 
		m_arrPublicArgs.add(arg);
	}
	
	public void setOut(double [] doValue)
	{
		CalledPrgPublicArgDoubleOutPositioned arg = new CalledPrgPublicArgDoubleOutPositioned(doValue, false); 
		m_arrPublicArgs.add(arg);
	}
	
	public void add(BaseCalledPrgPublicArgPositioned arg)
	{
		m_arrPublicArgs.add(arg);
	}
}
