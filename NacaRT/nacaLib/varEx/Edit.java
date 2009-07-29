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
 * Created on 25 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import jlib.log.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jlib.log.*;
import jlib.misc.StringUtil;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.mapSupport.*;
import nacaLib.mathSupport.MathBase;
import nacaLib.misc.StringAsciiEbcdicUtil;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Edit extends VarAndEdit
{
	Edit(DeclareTypeBase declareTypeBase)
	{
		super(declareTypeBase);
	}	
		
	protected Edit()
	{
		super();
	}
	
	boolean isEdit()
	{
		return true;
	}
	
	public int getAbsolutePosition()
	{
		return m_bufferPos.m_nAbsolutePosition;	// m_varDef.m_nAbsolutePosition;
	}
	
	public int DEBUGgetAbsolutePosition()
	{
		return m_bufferPos.m_nAbsolutePosition;	// m_varDef.m_nAbsolutePosition;
	}
	
	public void assignBufferExt(VarBuffer bufferSource)
	{
		m_bufferPos = new VarBufferPos(bufferSource, m_varDef.m_nDefaultAbsolutePosition);
		getEditAttributManager();	// Must be called for EditInMapRedefine
	}
	

	protected String getAsLoggableString()
	{
		//return m_varDef.getRawStringIncludingHeader(m_bufferPos);
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();
		return cs;
	}
	
	
	public boolean hasType(VarTypeEnum e)
	{
		if(e == VarTypeEnum.TypeFieldEdit)
			return true;
		return false;
	}

	public String getString()
	{
		CStr cstr = m_varDef.getAsDecodedString(m_bufferPos);
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();
		return cs;
	}
	
	public void setAndFill(String csValue)
	{
		if(csValue.length() > 0)
			m_varDef.writeAndFill(m_bufferPos, csValue.charAt(0));
	}

	public String getDottedSignedString()
	{
		String cs = m_varDef.getDottedSignedString(m_bufferPos).getAsString();
		return cs;
	}
	
	public String getDottedSignedStringAsSQLCol()
	{
		String cs = m_varDef.getDottedSignedStringAsSQLCol(m_bufferPos).getAsString();
		return cs;
	}

	public double getDouble()
	{
		return m_varDef.getDouble(m_bufferPos);
	}
	
	public int getInt()
	{
		return m_varDef.getAsDecodedInt(m_bufferPos);
	}
	
	public Dec getDec()
	{
		return m_varDef.getAsDecodedDec(m_bufferPos);
	}

	public Edit getEditAt(Var x)
	{
		return getAt(x);
	};
	public Edit getEditAt(MathBase x)
	{
		return getAt(x);
	};
	public Edit getEditAt(Var x, Var y)
	{
		return getAt(x, y);
	};
	public Edit getEditAt(Var x, int y)
	{
		return getAt(x, y);
	};
	public Edit getEditAt(int x, Var y)
	{
		return getAt(x, y);
	};
	public Edit getEditAt(int x, int y)
	{
		return getAt(x, y);
	};
	public abstract Edit getAt(Var x);
	public Edit getEditAt(int x)
	{
		return getAt(x);
	};
	public abstract Edit getAt(int x);
	public Edit getAt(MathBase Index)
	{
		int nIndex = Index.m_d.intValue() ;
		return getAt(nIndex) ;
	}
	public abstract Edit getAt(int y, int x);
	public Edit getAt(Var vy, Var vx)
	{
		int y = vy.getInt() ;
		int x = vx.getInt() ;
		return getAt(y, x) ;
	}
	public Edit getAt(Var vy, Var vx, Var vz)
	{
		int z = vz.getInt() ;
		int y = vy.getInt() ;
		int x = vx.getInt() ;
		return getAt(y, x, z) ;
	}
	public Edit getAt(Var vy, int x)
	{
		int y = vy.getInt() ;
		return getAt(y, x) ;
	}
	public Edit getAt(int x, Var vy)
	{
		int y = vy.getInt() ;
		return getAt(x, y) ;
	}
	public abstract Edit getAt(int z, int y, int x);
	
	public abstract void transferTo(Var varDest);
	
	public abstract void set(Var varSource);
	public abstract void set(Edit varSource);

	public abstract boolean isEditInMap();
	
	public abstract void transferTo(Edit varDest);
//	public abstract void transferTo(EditInMap varDest);
//	public abstract void transferTo(EditInMapRedefine varDest);
	

	
/////////////////////
	
	

/*	
	public String toString()
	{
		String cs = m_csFullName + "[" + m_VarManager.getValueAbsoluteStartPosition() + "-" + getStorageSize() + "]:" +
			"\"" + getString() + "\";" + 
			m_FieldAttributes.toString(); 
			
		return cs;
	}
*/	

	protected void fillWithValue(Element eField)
	{
		if (eField != null)
		{
			String val = eField.getAttribute("value");
			String upd = eField.getAttribute("updated") ;
			if (upd.equalsIgnoreCase("true"))
			{
				if (val.trim().equals(""))
				{
					set("") ;
					m_attrManager.setCleared() ;
				}
				else
				{
					set(val, true);
					m_attrManager.setModified() ;
				}
			}
			else
			{
				m_attrManager.setUnmodified() ;
			}
//			else if (!val.equals(""))
//			{
//				set(val) ;
//			}
		}
	}
	

	
	public Edit attrib(MapFieldAttrModified Modified)
	{
		m_attrManager.attrib(Modified);
		return this;
	}
	
	public Edit setModified(MapFieldAttrModified Modified)
	{
		m_attrManager.setModified(Modified);
		return this;
	}
	
	public Edit color(MapFieldAttrColor color)
	{
		m_attrManager.color(color);
		return this;
	}
	
	public boolean isColored(MapFieldAttrColor color)
	{
		return m_attrManager.isColored(color);
	}
	
	
	public MapFieldAttrHighlighting getHighlighting()
	{
		return m_attrManager.getHighlighting();
	}
	
	public Edit highLighting(MapFieldAttrHighlighting hl)
	{
		m_attrManager.highLighting(hl);
		return this;
	}
		
	public Edit intensity(MapFieldAttrIntensity intensity)
	{
		m_attrManager.intensity(intensity);
		return this;
	}
		
	
	public Edit protection(MapFieldAttrProtection protection)
	{
		m_attrManager.protection(protection);
		return this;
	}
	
	public Edit setModified()
	{
		m_attrManager.setModified();
		return this;
	}
	
	public void setUnmodified()
	{
		m_attrManager.setUnmodified();
	}
	
	public void setCleared()
	{
		m_attrManager.setCleared();
	}
	
	public boolean isModified()
	{
		return m_attrManager.isModified();
	}

	public boolean isUnmodified()
	{
		return m_attrManager.isUnmodified();
	}

	public boolean isCleared()
	{
		return m_attrManager.isCleared();
	}

	public Edit justify(MapFieldAttrJustify justify)
	{
		m_attrManager.justify(justify);
		return this;
	}
	
	public Edit justifyFill(MapFieldAttrFill fill)
	{
		m_attrManager.justifyFill(fill);
		return this;
	}
	
	public void set(String csValue)
	{
		m_varDef.write(m_bufferPos, csValue);
	}
		
	protected void set(String csValue, boolean bWithPadding)
	{		
		if(csValue != null && csValue.trim().length() != 0)	// Not an empty value
		{
			if (bWithPadding)
			{
				String csChar = "";
				if(m_attrManager.isFillBlank())
					csChar = " ";
				if(m_attrManager.isFillZero())
					csChar = "0";
				
				String csFilling = new String();
				
				int nLength = getLength();
				int nNbChars = nLength - csValue.length();
				while(nNbChars > 0)
				{
					csFilling += csChar;  
					nNbChars--;
				}
				
				if(m_attrManager.isJustifyRight())
				{
					csValue = csFilling + csValue; 
				}
				
				if(m_attrManager.isJustifyLeft())
				{
					csValue += csFilling;  
				}
			}
			m_varDef.write(m_bufferPos, csValue);
		}
//
//			
//			m_varDef.write(m_bufferPos, csValue);
//			if(m_VarTypeFormat != null)
//			{
//				m_VarTypeFormat.set(this, sValue);
//			}
//			else
//			{	
//				if(sValue.length() > m_nMaxStringLength)
//					sValue = sValue.substring(0, m_nMaxStringLength);	// Keep only leftmost chars
//				int nPosition = m_VarManager.getValueAbsoluteStartPosition()+7;
//				
//				if (bWithPadding)
//				{
//					if(m_FieldAttributes.isJustifyRight())
//					{
//						int nNbLeftPadChar = m_nMaxStringLength - sValue.length();
//						if(nNbLeftPadChar > 0)
//						{
//							if(m_FieldAttributes.isFillBlank())
//								nPosition = m_VarManager.writeRepeatingCharAt(nPosition, ' ', nNbLeftPadChar);
//							else if(m_FieldAttributes.isFillZero())
//								nPosition = m_VarManager.writeRepeatingCharAt(nPosition, '0', nNbLeftPadChar);
//						}
//					}
//				}
//				
//				nPosition = m_VarManager.setStringAt(nPosition, sValue);
//				
//				if (bWithPadding)
//				{
//					if(m_FieldAttributes.isJustifyLeft())
//					{
//						int nNbRightPadChar = m_nMaxStringLength - sValue.length();
//						if(nNbRightPadChar > 0)
//						{
//							if(m_FieldAttributes.isFillBlank())
//								m_VarManager.writeRepeatingCharAt(nPosition, ' ', nNbRightPadChar);
//							else if(m_FieldAttributes.isFillZero())
//								m_VarManager.writeRepeatingCharAt(nPosition, '0', nNbRightPadChar);
//						}
//					}
//				}
//			}					
//		}	
	}

	public Edit setCursor(boolean b)
	{
		m_attrManager.setCursor(b);
		return this;
	}

	
	public void setFlag(String cs)
	{
		m_attrManager.setFlag(cs);
	}
	
	
	public void resetFlag()
	{
		m_attrManager.resetFlag();
	}
	public boolean isFlag(String cs)
	{
		return m_attrManager.isFlag(cs);
	}
	
	public boolean isAutoSkip()
	{
		return m_attrManager.isAutoSkip();
	}

	public boolean isDark()
	{
		return m_attrManager.isDark();
	}
	
	public boolean isProtected()
	{
		return m_attrManager.isProtected();
	}	
	
	public boolean isNumericProtected()
	{
		return m_attrManager.isNumericProtected();
	}
	
	public boolean isUnprotected()
	{
		return m_attrManager.isUnmodified();
	}
	
	public boolean IsColored(MapFieldAttrColor col)
	{
		return m_attrManager.isColored(col);
	}
	
	public boolean isUnderlined()
	{	
		return m_attrManager.isUnderlined();
	}

	public boolean isReverse()
	{	
		return m_attrManager.isReverse();
	}
	
	public boolean IsAttribute(MapFieldAttrIntensity intensity)
	{	
		return m_attrManager.IsAttribute(intensity);
	}
	
	public boolean IsAttribute(MapFieldAttrProtection protection)
	{
		return m_attrManager.IsAttribute(protection);
	}
	
	public boolean IsHighlighting(MapFieldAttrHighlighting highlighting)
	{
		return m_attrManager.IsHighlighting(highlighting);
	}
	
	
	public MapFieldAttribute getAttribute()
	{
		return m_attrManager.getAttribute();
	}
	
	public void setAttribute(MapFieldAttribute att)
	{
		m_attrManager.setAttribute(att);
	}
	/*
	public int encodeAndAppend(Var varDest, int nVarDestAbsStartposition, int nTextLength)
	{
		//Custon serialization process uncompatible with direct modification of header bytes form Cobol
		int nAttrEncoded = m_FieldAttributes.getEncodedValue();	// Will use 4 char position
		char cProgrammedSymbolSet = m_Flag.getEncodedValue();	// Will use 1 char
		//int setEncodedEdit(int nAttributes, char cProgrammedSymbolSet, String csText, int nVarDestAbsStartposition)
		int nSize = varDest.m_VarManager.setEncodedEdit(nAttrEncoded, cProgrammedSymbolSet, getString(), nVarDestAbsStartposition, nTextLength);
	
		return nSize; 
	}
	*/
	
	// debug
	public int getEncodedAttr()
	{
		return m_attrManager.getEncodedAttr();
	}
	
	public void setEncodedAttr(int n)
	{
		m_attrManager.setEncodedAttr(n);
	}
	
	public boolean hasCursor()
	{
		return m_attrManager.hasCursor();
	}
	
	public abstract Element exportXML(Document doc, String csLangId);
	
	public boolean isFlagSet()
	{
		return m_attrManager.isFlagSet();
	}
	public MapFieldAttrColor getColor()
	{
		return m_attrManager.getColor();
	}
	
	public boolean isHighlightNormal()
	{
		return m_attrManager.isHighlightNormal();
	}
	
	public void setAttributes(int n)
	{
		m_attrManager.setAttributes(n);
	}

	public String getFlag()
	{
		return m_attrManager.getFlag();
	}
		
	public void setStringAtPosition(String csValue, int nOffsetPosition, int nNbChar)
	{
		m_varDef.write(m_bufferPos, csValue, nOffsetPosition+getVarDef().getHeaderLength(), nNbChar);
	}
	
	public void set(CobolConstantZero cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}

	public void set(CobolConstantSpace cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}

	public void set(CobolConstantHighValue cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}

	public void set(CobolConstantLowValue cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}
	
	public String digits()
	{
		return "";	// m_varDef.digits(); 
	}
	
	public String getValue()
	{
		return getString();
	}
	
	public int encodeIntoCharBuffer(InternalCharBuffer charBuffer, String csText, int nTextLength, int nPos)
	{
		int nAttrEncoded = m_attrManager.getAttributeEncodedValue();	// Will use 4 char position
		nPos = charBuffer.writeInt(nAttrEncoded, nPos);
		if(nPos != -1)
		{
			char cProgrammedSymbolSet = m_attrManager.getEncodedFlag();	// Will use 1 char
			nPos = charBuffer.writeChar(cProgrammedSymbolSet, nPos);
			if(nPos != -1)
			{
				nPos = charBuffer.writeShort((short)nTextLength, nPos);		// Write string length on a shor
				if(nPos != -1)
				{
					String cs = csText.substring(0, nTextLength);
					nPos = charBuffer.writeString(cs, nPos);
					if(nPos != -1)
					{
						if(isLogCESM)
							Log.logDebug("edit encodeIntoCharBuffer cs="+cs+" to edit="+getLoggableValue());
						return nPos;
					}
				}
			}
		}
		return -1;
	}

	public int decodeFromVar(VarBase varSource, int nPos, int nDestLength)
	{
		int nPositionSource = varSource.getBodyAbsolutePosition() + nPos;
		
		int nAttrEncoded = VarDefBuffer.getDecodedEditAttributes(varSource.m_bufferPos, nPositionSource);
		m_attrManager.setAttributeEncodedValue(nAttrEncoded);	// Will use 4 char position
		
		char cProgrammedSymbolSet = VarDefBuffer.getDecodedEditFlag(varSource.m_bufferPos, nPositionSource);
		m_attrManager.setEncodedFlag(cProgrammedSymbolSet);	// Will use 4 char position
		 
		int nPositionDest = getBodyAbsolutePosition();
		nPositionSource += 7;
		m_bufferPos.copyBytesFromSource(nPositionDest, varSource.m_bufferPos, nPositionSource, nDestLength);
			
		if(isLogCESM)
			Log.logDebug("edit decodeFromVar source="+varSource.getLoggableValue()+" to edit="+getLoggableValue());
		return nPos + 7 + nDestLength;
	}
	
	public int decodeFromCharBuffer(InternalCharBuffer charBuffer, int nPos, int nDestLength)
	{
		int nPositionSource = nPos;
		
		int nAttrEncoded = VarDefBuffer.getDecodedEditAttributes(charBuffer, nPositionSource);
		m_attrManager.setAttributeEncodedValue(nAttrEncoded);	// Will use 4 char position
		
		char cProgrammedSymbolSet = VarDefBuffer.getDecodedEditFlag(charBuffer, nPositionSource);
		m_attrManager.setEncodedFlag(cProgrammedSymbolSet);	// Will use 4 char position
		 
		int nPositionDest = getBodyAbsolutePosition();
		nPositionSource += 7;
		m_bufferPos.copyBytesFromSource(nPositionDest, charBuffer, nPositionSource, nDestLength);
			
//		logCesm("edit decodeFromVar source="+varSource.getLoggableValue()+" to edit="+getLoggableValue());
		return nPos + 7 + nDestLength; 
	}
		
	public int getLength()
	{
		return m_varDef.getBodyLength();
	}
	
	public void setLength(int n)
	{
		// TODO fake method
		throw new AssertException("unsupported action : Edit.setLength()");
	}

	public boolean equals(String csValue)	
	{
		if(compareTo(ComparisonMode.Unicode, csValue) == 0)
			return true;
		return false;
	}

	public int compareTo(ComparisonMode mode, VarAndEdit var2)
	{
		String cs1 = getString();
		int n = var2.compareTo(mode, cs1);
		if(n < 0)
			return 1;
		if(n > 0)
			return -1;
		return 0;
	}
	
	public int compareTo(ComparisonMode mode, String sValue)
	{
		String s = getString();
		//s = StringUtil.trimLeftRight(s);
		//sValue = StringUtil.trimLeftRight(sValue);
		
		return StringAsciiEbcdicUtil.compare(mode, s, sValue);
	}
	
	public int compareTo(int n2)
	{
		int n1;
		if (getString().trim().equals(""))
			n1 = -1;
		else
			n1 = getInt();
		if(n1 < n2)	
			return -1;
		if(n1 == n2)
			return 0;
		return 1;
	}
	
	public int compareTo(double dValue)	
	{
		double dVarValue = getDouble();
		double d = dVarValue - dValue;
		if(d < -0.00001)	//Consider epsilon precision at 10 e-5 
			return -1;
		else if(d > 0.00001)	//Consider epsilon precision at 10 e-5
			return 1;
		return 0;			
	}  

	public void initialize(InitializeCache initializeCache)
	{
		m_varDef.write(m_bufferPos, CobolConstant.Space) ;
	
//		//if(m_attrManager != null)
//		//	m_attrManager.initialize();
	}
	
	
	public Edit setDevelopableMark(String string)
	{
		m_attrManager.setDevelopableMark(string);
		return this ;
	}
	
	public Edit setFormat(String string)
	{
		m_attrManager.setFormat(string);
		return this ;
	}
	
	public void initializeAttributes()
	{
		m_attrManager.initialize() ;
	}	
	
	protected EditAttributManager m_attrManager = null;
}


