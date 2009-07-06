/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 14 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.mapSupport.*;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EditAttributManager
{
	EditAttributManager()
	{
		int n = 0;
	}
	
	void allocAttributes(DeclareTypeEditInMap declareTypeEdit)
	{
		m_mapFieldAttribute = declareTypeEdit.m_mapFieldAttribute.duplicate();
		m_localizedString = declareTypeEdit.m_localizedString;
		setCursor(declareTypeEdit.m_bHasCursor);
		m_csDevelopableMark = declareTypeEdit.m_csDevelopableMark;
		m_csFormat = declareTypeEdit.m_csFormat;
		//m_csSemanticContext = declareTypeEdit.m_csSemanticContextValue;
	}
	
	void initialize()
	{
		m_bHasCursor = false ;
		m_mapFieldAttribute.initialize();
		m_Flag.reset() ;
	}
	
	void copyInto(EditAttributManager attrManagerDest)
	{
		attrManagerDest.m_mapFieldAttribute = m_mapFieldAttribute.duplicate();
		attrManagerDest.m_localizedString = m_localizedString;	// Not copied; keep the original value as it is never modified
		attrManagerDest.m_bHasCursor = m_bHasCursor;
		attrManagerDest.m_csDevelopableMark = m_csDevelopableMark;
		attrManagerDest.m_csFormat = m_csFormat;
		attrManagerDest.m_Flag = m_Flag.duplicate();
//		if(m_csSemanticContext != null)
//			attrManagerDest.m_csSemanticContext = new String(m_csSemanticContext);
//		else
//			attrManagerDest.m_csSemanticContext = null;
	}

	public String toString()
	{
		String cs;
		if(m_mapFieldAttribute != null)
			cs = m_mapFieldAttribute.getLoggableValue();
		else
			cs = "NoMapFieldAtribute ";
		return cs;
	}
	
	public void attrib(MapFieldAttrModified Modified)
	{
		setModified(Modified);
	}
	
	public void setModified(MapFieldAttrModified Modified)
	{
		m_mapFieldAttribute.setAttrModified(Modified);
	}

	// Color
	public void color(MapFieldAttrColor color)
	{
		m_mapFieldAttribute.setColor(color) ;
	}

	// Highligth
	public MapFieldAttrHighlighting getHighlighting()
	{
		return m_mapFieldAttribute.getHighlighting();
	}
	
	public void highLighting(MapFieldAttrHighlighting hl)
	{
		m_mapFieldAttribute.setHighlighting(hl) ;
	}
	
	public void intensity(MapFieldAttrIntensity intensity)
	{
		m_mapFieldAttribute.setIntensity(intensity);
	
	}
		
	public void protection(MapFieldAttrProtection protection)
	{
		m_mapFieldAttribute.setProtection(protection);
	}

	public void setModified()
	{
		m_mapFieldAttribute.setAttrModified(MapFieldAttrModified.MODIFIED);
	}
	
	public void setUnmodified()
	{
		m_mapFieldAttribute.setAttrModified(MapFieldAttrModified.UNMODIFIED);
	}

	public void setCleared()
	{
		m_mapFieldAttribute.setAttrModified(MapFieldAttrModified.CLEARED);
	}
	
	public boolean isModified()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrModified attrModified = m_mapFieldAttribute.getAttrModified();
			return attrModified == MapFieldAttrModified.MODIFIED  || attrModified == MapFieldAttrModified.TO_BE_MODIFIED;
		}
		return false ;
	}
	
	
	public boolean isUnmodified()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrModified attrModified = m_mapFieldAttribute.getAttrModified();
			return attrModified == MapFieldAttrModified.UNMODIFIED ;
		}
		return false ;
	}	
	
	public boolean isCleared()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrModified attrModified = m_mapFieldAttribute.getAttrModified();
			return attrModified == MapFieldAttrModified.CLEARED ;
		}
		return false ;
	}	
		
	public void justify(MapFieldAttrJustify justify)
	{
		m_mapFieldAttribute.setJustify(justify) ;
	}
	
	public void justifyFill(MapFieldAttrFill fill)
	{
		m_mapFieldAttribute.setFill(fill) ;
	}
	
		
	public void setFlag(String cs)
	{
		if(m_Flag == null)
			m_Flag = new MapFieldFlag();
		m_Flag.set(cs);		
	}

	public void resetFlag()
	{
		if(m_Flag == null)
			m_Flag = new MapFieldFlag();
		m_Flag.reset();		
	}

	public boolean isFlag(String cs)
	{
		if(m_Flag != null)
			return m_Flag.isFlag(cs);
		return false;
	}

	// Protection
	public boolean isAutoSkip()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrProtection attrProtection = m_mapFieldAttribute.getProtection();
			return attrProtection == MapFieldAttrProtection.AUTOSKIP;
		}
		return false ;
	}
	
	public boolean isDark()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrIntensity attr = m_mapFieldAttribute.getIntensity();
			return attr == MapFieldAttrIntensity.DARK;
		}
		return false ;
	}
	
	public boolean isProtected()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrProtection attrProtection = m_mapFieldAttribute.getProtection();
			return attrProtection == MapFieldAttrProtection.PROTECTED;
		}
		return false ;
	}
	
	public boolean isNumericProtected()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrProtection attrProtection = m_mapFieldAttribute.getProtection();
			return attrProtection == MapFieldAttrProtection.NUMERIC;
		}
		return false ;
	}

	public boolean isUnprotected()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrProtection attrProtection = m_mapFieldAttribute.getProtection();
			return attrProtection == MapFieldAttrProtection.UNPROTECTED;
		}
		return false ;
	}
		
	public boolean isColored(MapFieldAttrColor col)
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrColor color = m_mapFieldAttribute.getColor();
			return color == col ;
		}
		return false ;
	}

	public boolean isUnderlined()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrHighlighting highlighting = m_mapFieldAttribute.getHighlighting();
			return highlighting == MapFieldAttrHighlighting.UNDERLINE ;
		}
		return false ;
	}

	public boolean isReverse()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrHighlighting highlighting = m_mapFieldAttribute.getHighlighting();
			return highlighting == MapFieldAttrHighlighting.REVERSE ;
		}
		return false ;
	}
	
	public boolean IsAttribute(MapFieldAttrIntensity intensity)
	{
		if (m_mapFieldAttribute != null)
		{
			return m_mapFieldAttribute.getIntensity() == intensity;
		}
		return false ;
	}

	public boolean IsAttribute(MapFieldAttrProtection protection)
	{
		if (m_mapFieldAttribute != null)
		{
			return m_mapFieldAttribute.getProtection() == protection;
		}
		return false ;
	}
	public boolean IsHighlighting(MapFieldAttrHighlighting highlighting)
	{
		if (m_mapFieldAttribute != null)
		{
			return m_mapFieldAttribute.getHighlighting() == highlighting;
		}
		return false ;
	}
	
	public MapFieldAttribute getAttribute()
	{
		return m_mapFieldAttribute ;
	}
	
	public void setAttribute(MapFieldAttribute att)
	{
		m_mapFieldAttribute.set(att) ;
	}

	public int getEncodedAttr()
	{
		int n = m_mapFieldAttribute.getEncodedValue();
		return n;
	}
	
	public void setEncodedAttr(int n)
	{
		m_mapFieldAttribute.setEncodedValue(n);
	}
	
	public void setCursor(boolean b)
	{
		m_bHasCursor = b;	
	}
	
	public boolean hasCursor()
	{
		return m_bHasCursor ;
	}

	public String getFlag()
	{
		if(m_Flag != null)
			return m_Flag.get();
		return "" ;
	}
	
		/**
	 * @return
	 */
	public boolean isFlagSet()
	{
		return m_Flag != null && m_Flag.isSet() ;
	}

	/**
	 * @return
	 */
	public MapFieldAttrColor getColor()
	{
		return m_mapFieldAttribute.getColor() ;
	}

	
	public boolean isHighlightNormal()
	{
		if (m_mapFieldAttribute != null)
		{
			MapFieldAttrHighlighting highlighting = m_mapFieldAttribute.getHighlighting();
			return highlighting == MapFieldAttrHighlighting.OFF ;
		}
		return true ;
	}
	
	public void setAttributes(int n)
	{
	}
	
	MapFieldAttribute getMapFieldAttribute()
	{
		return m_mapFieldAttribute;
	}
	
	int getAttributeEncodedValue()	// Will use 4 char position
	{
		return m_mapFieldAttribute.getEncodedValue();	// Will use 4 char position
	}
	
	void setAttributeEncodedValue(int nAttrEncoded)	// Will use 4 char position
	{
		m_mapFieldAttribute.setEncodedValue(nAttrEncoded);
	}

	
	char getEncodedFlag()	// Will use 4 char position
	{
		char cFlag = m_Flag.getEncodedValue();	// Will use 1 char
		return cFlag;
	}
	
	void setEncodedFlag(char cFlag)	// Will use 4 char position
	{
		m_Flag.setEncodedValue(cFlag);
	}
	
	void setDevelopableMark(String cs)
	{
		m_csDevelopableMark = cs;
	}
	
	void setFormat(String cs)
	{
		m_csFormat = cs;
	}
	
	LocalizedString getLocalizedString()
	{
		return m_localizedString;
	}
	
	
	boolean isFillBlank()
	{
		return m_mapFieldAttribute.isFillBlank();
	}

	boolean isFillZero()
	{
		return m_mapFieldAttribute.isFillZero();
	}
	
	boolean isJustifyLeft()
	{
		return m_mapFieldAttribute.isJustifyLeft();
	}
	
	boolean isJustifyRight()
	{
		return m_mapFieldAttribute.isJustifyRight();
	}


	MapFieldAttribute m_mapFieldAttribute = null;	
	MapFieldFlag m_Flag = new MapFieldFlag();	
	LocalizedString m_localizedString = null;		// Encoded in commarea
	String m_csDevelopableMark = null;
	String m_csFormat = null;
	boolean m_bHasCursor = false ;
	//String m_csSemanticContext = null;
}
