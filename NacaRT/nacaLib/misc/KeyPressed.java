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
package nacaLib.misc;

import nacaLib.varEx.Var;

/*
 * Created on Oct 18, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KeyPressed
{
	private KeyPressed(String cs, String name)
	{
		m_csValue = cs ;
		m_csName = name ;
	}
	public String m_csValue = "" ;
	public String m_csName = "" ; 
	
	public static KeyPressed CLEAR = new KeyPressed("_", "CLEAR") ;
	public static KeyPressed ENTER = new KeyPressed("'", "ENTER") ;
	public static KeyPressed PF1 = new KeyPressed("1", "PF1") ;
	public static KeyPressed PF2 = new KeyPressed("2", "PF2") ;
	public static KeyPressed PF3 = new KeyPressed("3", "PF3") ;
	public static KeyPressed PF4 = new KeyPressed("4", "PF4") ;
	public static KeyPressed PF5 = new KeyPressed("5", "PF5") ;
	public static KeyPressed PF6 = new KeyPressed("6", "PF6") ;
	public static KeyPressed PF7 = new KeyPressed("7", "PF7") ;
	public static KeyPressed PF8 = new KeyPressed("8", "PF8") ;
	public static KeyPressed PF9 = new KeyPressed("9", "PF9") ;
	public static KeyPressed PF10 = new KeyPressed("A", "PF10") ;
	public static KeyPressed PF11 = new KeyPressed("B", "PF11") ;
	public static KeyPressed PF12 = new KeyPressed("C", "PF12") ;
	public static KeyPressed PF13 = new KeyPressed("D", "PF13") ;
	public static KeyPressed PF14 = new KeyPressed("E", "PF14") ;
	public static KeyPressed PF15 = new KeyPressed("F", "PF15") ;
	public static KeyPressed PF16 = new KeyPressed("G", "PF16") ;
	public static KeyPressed PF17 = new KeyPressed("H", "PF17") ;
	public static KeyPressed PF18 = new KeyPressed("I", "PF18") ;
	public static KeyPressed PF19 = new KeyPressed("J", "PF19") ;
	public static KeyPressed PF20 = new KeyPressed("K", "PF20") ;
	public static KeyPressed PF21 = new KeyPressed("L", "PF21") ;
	public static KeyPressed PF22 = new KeyPressed("M", "PF22") ;
	public static KeyPressed PF23 = new KeyPressed("N", "PF23") ;
	public static KeyPressed PF24 = new KeyPressed("O", "PF24") ;
	public static KeyPressed LOG_OUT = new KeyPressed("logout", "LOGOUT") ;
	public static KeyPressed CHANGE_USER = new KeyPressed("chguser", "CHGUSER") ;
	
	public String getName()
	{
		return m_csName;
	}

	public String getValue()
	{
		return m_csValue;
	}
	
	public String getSTCheckValue()
	{
		return m_csName;
	}	
	
	public static KeyPressed getKeyFromHttp(String csKey)
	{	
		if(csKey == null)
			return null ; 
		else if (csKey.equals("0"))
		{
			return KeyPressed.ENTER ;
		}
		else if (csKey.equals("1"))
		{
			return KeyPressed.PF1;
		}
		else if (csKey.equals("2"))
		{
			return KeyPressed.PF2;
		}
		else if (csKey.equals("3"))
		{
			return KeyPressed.PF3;
		}
		else if (csKey.equals("4"))
		{
			return KeyPressed.PF4;
		}
		else if (csKey.equals("5"))
		{
			return KeyPressed.PF5;
		}
		else if (csKey.equals("6"))
		{
			return KeyPressed.PF6;
		}
		else if (csKey.equals("7"))
		{
			return KeyPressed.PF7;
		}
		else if (csKey.equals("8"))
		{
			return KeyPressed.PF8;
		}
		else if (csKey.equals("9"))
		{
			return KeyPressed.PF9;
		}
		else if (csKey.equals("A"))
		{
			return KeyPressed.PF10;
		}
		else if (csKey.equals("B"))
		{
			return KeyPressed.PF11;
		}
		else if (csKey.equals("C"))
		{
			return KeyPressed.PF12;
		}
		else if (csKey.equals("D"))
		{
			return KeyPressed.PF13;
		}
		else if (csKey.equals("E"))
		{
			return KeyPressed.PF14;
		}
		else if (csKey.equals("F"))
		{
			return KeyPressed.PF15;
		}
		else if (csKey.equals("G"))
		{
			return KeyPressed.PF16;
		}
		else if (csKey.equals("H"))
		{
			return KeyPressed.PF17;
		}
		else if (csKey.equals("I"))
		{
			return KeyPressed.PF18;
		}
		else if (csKey.equals("J"))
		{
			return KeyPressed.PF19;
		}
		else if (csKey.equals("K"))
		{
			return KeyPressed.PF20;
		}
		else if (csKey.equals("L"))
		{
			return KeyPressed.PF21;
		}
		else if (csKey.equals("M"))
		{
			return KeyPressed.PF22;
		}
		else if (csKey.equals("N"))
		{
			return KeyPressed.PF23;
		}
		else if (csKey.equals("O"))
		{
			return KeyPressed.PF24;
		}
		else if (csKey.equals("logout"))
		{
			return KeyPressed.LOG_OUT;
		}
		else if (csKey.equals("chguser"))
		{
			return KeyPressed.CHANGE_USER;
		}
		else
		{
			return null ;
		}
	}
	
	public static KeyPressed getKey(Var v)
	{
		String cs = v.getString();
		return getKey(cs);
	}	

	public static KeyPressed getKey(String cs)
	{
		if (cs.equals(CLEAR.m_csValue) || cs.equalsIgnoreCase(CLEAR.m_csName))
		{
			return CLEAR ;
		}
		else if (cs.equals(ENTER.m_csValue) || cs.equalsIgnoreCase(ENTER.m_csName))
		{
			return ENTER ;
		}
		else if (cs.equals(PF1.m_csValue) || cs.equalsIgnoreCase(PF1.m_csName))
		{
			return PF1;
		}
		else if (cs.equals(PF2.m_csValue) || cs.equalsIgnoreCase(PF2.m_csName))
		{
			return PF2;
		}
		else if (cs.equals(PF3.m_csValue) || cs.equalsIgnoreCase(PF3.m_csName))
		{
			return PF3;
		}
		else if (cs.equals(PF4.m_csValue) || cs.equalsIgnoreCase(PF4.m_csName))
		{
			return PF4;
		}
		else if (cs.equals(PF5.m_csValue) || cs.equalsIgnoreCase(PF5.m_csName))
		{
			return PF5;
		}
		else if (cs.equals(PF6.m_csValue) || cs.equalsIgnoreCase(PF6.m_csName))
		{
			return PF6;
		}
		else if (cs.equals(PF7.m_csValue) || cs.equalsIgnoreCase(PF7.m_csName))
		{
			return PF7;
		}
		else if (cs.equals(PF8.m_csValue) || cs.equalsIgnoreCase(PF8.m_csName))
		{
			return PF8;
		}
		else if (cs.equals(PF9.m_csValue) || cs.equalsIgnoreCase(PF9.m_csName))
		{
			return PF9;
		}
		else if (cs.equals(PF10.m_csValue) || cs.equalsIgnoreCase(PF10.m_csName))
		{
			return PF10 ;
		}
		else if (cs.equals(PF11.m_csValue) || cs.equalsIgnoreCase(PF11.m_csName))
		{
			return PF11;
		}
		else if (cs.equals(PF12.m_csValue) || cs.equalsIgnoreCase(PF12.m_csName))
		{
			return PF12;
		}
		else if (cs.equals(PF13.m_csValue) || cs.equalsIgnoreCase(PF13.m_csName))
		{
			return PF13;
		}
		else if (cs.equals(PF14.m_csValue) || cs.equalsIgnoreCase(PF14.m_csName))
		{
			return PF14;
		}
		else if (cs.equals(PF15.m_csValue) || cs.equalsIgnoreCase(PF15.m_csName))
		{
			return PF15;
		}
		else if (cs.equals(PF16.m_csValue) || cs.equalsIgnoreCase(PF16.m_csName))
		{
			return PF16;
		}
		else if (cs.equals(PF17.m_csValue) || cs.equalsIgnoreCase(PF17.m_csName))
		{
			return PF17;
		}
		else if (cs.equals(PF18.m_csValue) || cs.equalsIgnoreCase(PF18.m_csName))
		{
			return PF18;
		}
		else if (cs.equals(PF19.m_csValue) || cs.equalsIgnoreCase(PF19.m_csName))
		{
			return PF19;
		}
		else if (cs.equals(PF20.m_csValue) || cs.equalsIgnoreCase(PF20.m_csName))
		{
			return PF20;
		}
		else if (cs.equals(PF21.m_csValue) || cs.equalsIgnoreCase(PF21.m_csName))
		{
			return PF21;
		}
		else if (cs.equals(PF22.m_csValue) || cs.equalsIgnoreCase(PF22.m_csName))
		{
			return PF22;
		}
		else if (cs.equals(PF23.m_csValue) || cs.equalsIgnoreCase(PF23.m_csName))
		{
			return PF23;
		}
		else if (cs.equals(PF24.m_csValue) || cs.equalsIgnoreCase(PF24.m_csName))
		{
			return PF24;
		}
		else if (cs.equals(LOG_OUT.m_csValue) || cs.equalsIgnoreCase(LOG_OUT.m_csName))
		{
			return LOG_OUT;
		}
		else if (cs.equals(CHANGE_USER.m_csValue) || cs.equalsIgnoreCase(CHANGE_USER.m_csName))
		{
			return CHANGE_USER;
		}
		else
		{
			return null ;
		}
	}
	
	public String toString()
	{
		return m_csName + ": " + m_csValue;
	}

}
