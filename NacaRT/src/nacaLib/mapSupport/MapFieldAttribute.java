/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 23 sept. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.mapSupport;

import nacaLib.base.*;

import org.w3c.dom.*;

public class MapFieldAttribute extends CJMapObject
{
	private MapFieldAttrProtection m_Protection = null ; //MapFieldAttrProtection.AUTOSKIP;
	private MapFieldAttrIntensity m_Intensity = null ; //MapFieldAttrIntensity.NORMAL;
	private MapFieldAttrModified m_Modified = null ; //MapFieldAttrModified.UNMODIFIED;
	
	// Stand alone field
	private MapFieldAttrColor m_Color = null ; //MapFieldAttrColor.DEFAULT;

	// Stand alone field
	private MapFieldAttrHighlighting m_Highlighting = null ; //MapFieldAttrHighlighting.OFF ;
	
	// Into Encoded bit field
	private MapFieldAttrJustify m_Justify = MapFieldAttrJustify.LEFT;
	private MapFieldAttrFill m_Fill = MapFieldAttrFill.BLANK;
	
	public MapFieldAttribute()
	{
		setJustify(MapFieldAttrJustify.LEFT);
	}
	
	public void resetDefaultValues()
	{
		m_Protection = null ;
		m_Intensity = null ;
		m_Modified = null ;
		m_Color = null ;
		m_Highlighting = null ;
		m_Justify = MapFieldAttrJustify.LEFT;
		m_Fill = MapFieldAttrFill.BLANK;
	}
	
	public void set(MapFieldAttribute att)
	{
		m_Protection = att.m_Protection ;
		m_Intensity = att.m_Intensity ;
		m_Modified = att.m_Modified ;
		m_Color = att.m_Color;
		m_Justify = att.m_Justify;
		m_Fill = att.m_Fill;
		m_Highlighting = att.m_Highlighting;
	}
	
	public MapFieldAttribute duplicate()
	{
		MapFieldAttribute copy = new MapFieldAttribute();
		copy.set(this);
		return copy;
	}
	
	public String getLoggableValue()
	{
		return toString();
	}
	
	public String getSTCheckValue()
	{
		return toString();
	}
	
	public String toString()
	{
		String cs = new String();
		if(m_Protection != null)
			cs += m_Protection.toString() + ";"; 
		if(m_Intensity != null)
			cs += m_Intensity.toString() + ";"; 
		if(m_Modified != null)
			cs += m_Modified.toString() + ";"; 
		if(m_Color != null)
			cs += m_Color.toString() + ";"; 
		if(m_Justify != null)
			cs += m_Justify.toString() + ";"; 
		if(m_Fill != null)
			cs += m_Fill.toString() + ";"; 
		if(m_Highlighting != null)
			cs += m_Highlighting.toString(); 
		return cs;
	}

//	void setLineCol(int nLine, int nCol)	
//	{
//		m_nCol = nCol;
//		m_nLine = nLine;
//	}
//	
	public void setProtection(MapFieldAttrProtection Protection)
	{
		m_Protection = Protection;
		if (m_Protection == MapFieldAttrProtection.NUMERIC)
		{
			if (m_Justify == null)
			{
				m_Justify = MapFieldAttrJustify.RIGHT ;
			}
			if (m_Fill == null)
			{
				m_Fill = MapFieldAttrFill.ZERO ;
			}
		}
	}
	
	public MapFieldAttrProtection getProtection()
	{
		return m_Protection;
	}

	// Intensity
	public void setIntensity(MapFieldAttrIntensity Intensity)
	{
		m_Intensity = Intensity;
	}
	
	public MapFieldAttrIntensity getIntensity()
	{
		return m_Intensity;
	} 
	
	// Highlight
	public void setHighlighting(MapFieldAttrHighlighting hl)
	{
		m_Highlighting = hl ;
	}
	
	public MapFieldAttrHighlighting getHighlighting()
	{
		return m_Highlighting; 
	}	
	
	// Attribut modified
	public void setAttrModified(MapFieldAttrModified Modified)
	{
		m_Modified = Modified;
	}

	public MapFieldAttrModified getAttrModified()
	{
		return m_Modified;
	}


	public void setColor(MapFieldAttrColor Color)
	{
		m_Color = Color;
	}
	
	public MapFieldAttrColor getColor()
	{
		return m_Color;
	}
	
	public void exportAllAttributes(Element eEdit)
	{	
		exportColor(eEdit);
		exportIntensity(eEdit);
		exportHighlighting(eEdit);
		exportProtection(eEdit);
		exportModified(eEdit);
	}
	
	private void exportColor(Element eEdit)
	{
		if (m_Color != null)
 		{
			String csFieldColor = m_Color.getText();
			eEdit.setAttribute("color", csFieldColor) ;
 		}
 	}

	private void exportIntensity(Element eEdit)
	{
 		if (m_Intensity != null)
 		{
			String csIntens = m_Intensity.getText();
			eEdit.setAttribute("intensity", csIntens) ;
 		}
	}
	
	private void exportHighlighting(Element eEdit)
	{
		if (m_Highlighting != null)
		{
			String csHL = m_Highlighting.getText();
			eEdit.setAttribute("highlighting", csHL) ;
		}
	}
	 
	private void exportProtection(Element eEdit)
	{
		if (m_Protection != null)
		{
			String csProtect = m_Protection.getText();
			eEdit.setAttribute("protection", csProtect) ;
		}
	}
	
	private void exportModified(Element eEdit)
	{
		if (m_Modified != null)
		{
			if (m_Modified == MapFieldAttrModified.TO_BE_MODIFIED)
			{
				eEdit.setAttribute("modified", "true");
			}
			else
			{
				eEdit.setAttribute("modified", "false");
			}
		}
	}
	




	public void setJustify(MapFieldAttrJustify Justify)
	{
		m_Justify = Justify;
		if (m_Fill == null)
		{
			if (m_Justify == MapFieldAttrJustify.LEFT)
			{
				m_Fill = MapFieldAttrFill.BLANK ;
			}
			else if (m_Justify == MapFieldAttrJustify.RIGHT)
			{
				m_Fill = MapFieldAttrFill.ZERO ;
			}
		}
	}

	public void setFill(MapFieldAttrFill Fill)
	{
		m_Fill = Fill;
		if (m_Justify == null)
		{
			if (m_Fill == MapFieldAttrFill.BLANK)
			{
				m_Justify = MapFieldAttrJustify.LEFT ;
			}
			else if (m_Fill == MapFieldAttrFill.ZERO)
			{
				m_Justify = MapFieldAttrJustify.RIGHT ;
			}
		} 
	}
	
	public boolean isFillZero()
	{
		if(m_Fill != null && m_Fill.isFillZero())
			return true;
		return false;
	}
	
	public boolean isFillBlank()
	{
		if(m_Fill != null)
		{
			if(m_Fill.isFillBlank())
				return true;
			else
				return false;
		}
		return true;	// By default
	} 
	
	public boolean isJustifyRight()
	{
		if(m_Justify != null && m_Justify.isJustifyRight())
			return true;
		return false;
	}
	
	public boolean isJustifyLeft()
	{
		if(m_Justify != null)
		{
			if(m_Justify.isJustifyLeft())
				return true;
			else
				return false;
		}
		return true;	// by default
	}
	
	public int getEncodedValue()
	{
		int nEncodedValue = 0;
		
		nEncodedValue = nEncodedValue << MapFieldAttrProtection.getNbBitsEncoding();
		if (m_Protection != null)
			nEncodedValue |= m_Protection.getBitEncoding();

		nEncodedValue = nEncodedValue << MapFieldAttrIntensity.getNbBitsEncoding();
		if (m_Intensity != null)
			nEncodedValue |= m_Intensity.getBitEncoding();

		nEncodedValue = nEncodedValue << MapFieldAttrModified.getNbBitsEncoding();
		if (m_Modified != null)
			nEncodedValue |= m_Modified.getBitEncoding();
		
		nEncodedValue = nEncodedValue << MapFieldAttrColor.getNbBitsEncoding();
		if (m_Color != null)
			nEncodedValue |= m_Color.getBitEncoding();

		nEncodedValue = nEncodedValue << MapFieldAttrHighlighting.getNbBitsEncoding();
		if (m_Highlighting != null)
			nEncodedValue |= m_Highlighting.getBitEncoding();
				
		nEncodedValue = nEncodedValue << MapFieldAttrJustify.getNbBitsEncoding();
		if (m_Justify != null)
			nEncodedValue |= m_Justify.getBitEncoding();

		nEncodedValue = nEncodedValue << MapFieldAttrFill.getNbBitsEncoding();
		if (m_Fill != null)
			nEncodedValue |= m_Fill.getBitEncoding();
		
		return nEncodedValue;
	}
	
	public void setEncodedValue(int nEncodedValue)
	{
		int nValue;
		
		nValue = nEncodedValue & MapFieldAttrFill.getMask();
		m_Fill = MapFieldAttrFill.Select(nValue);		
		nEncodedValue = nEncodedValue >> MapFieldAttrFill.getNbBitsEncoding();

		nValue = nEncodedValue & MapFieldAttrJustify.getMask();
		m_Justify = MapFieldAttrJustify.Select(nValue);		
		nEncodedValue = nEncodedValue >> MapFieldAttrJustify.getNbBitsEncoding();

		nValue = nEncodedValue & MapFieldAttrHighlighting.getMask();
		m_Highlighting = MapFieldAttrHighlighting.Select(nValue);		
		nEncodedValue = nEncodedValue >> MapFieldAttrHighlighting.getNbBitsEncoding();

		nValue = nEncodedValue & MapFieldAttrColor.getMask();
		m_Color = MapFieldAttrColor.Select(nValue);		
		nEncodedValue = nEncodedValue >> MapFieldAttrColor.getNbBitsEncoding();		

		nValue = nEncodedValue & MapFieldAttrModified.getMask();
		m_Modified = MapFieldAttrModified.Select(nValue);		
		nEncodedValue = nEncodedValue >> MapFieldAttrModified.getNbBitsEncoding();		

		nValue = nEncodedValue & MapFieldAttrIntensity.getMask();
		m_Intensity = MapFieldAttrIntensity.Select(nValue);		
		nEncodedValue = nEncodedValue >> MapFieldAttrIntensity.getNbBitsEncoding();

		nValue = nEncodedValue & MapFieldAttrProtection.getMask();
		m_Protection = MapFieldAttrProtection.Select(nValue);		
		nEncodedValue = nEncodedValue >> MapFieldAttrProtection.getNbBitsEncoding();
	}

	public void initialize()
	{
		m_Protection = null ; //MapFieldAttrProtection.AUTOSKIP;
		m_Intensity = null ; //MapFieldAttrIntensity.NORMAL;
		m_Modified = null ; //MapFieldAttrModified.UNMODIFIED;
		m_Color = null ; //vMapFieldAttrColor.DEFAULT;
		m_Highlighting = null ; //MapFieldAttrHighlighting.OFF ;
//		m_Justify = MapFieldAttrJustify.LEFT;
//		m_Fill = MapFieldAttrFill.BLANK;
	}
}

