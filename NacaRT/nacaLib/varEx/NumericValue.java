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
 * Created on 17 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NumericValue
{
	NumericValue()
	{
	}
	
	void set(boolean bSigned, int nNbDigitInteger, int nNbDigitDecimal)
	{
		m_bSigned = bSigned;
		m_nNbDigitInteger = nNbDigitInteger;
		m_nNbDigitDecimal = nNbDigitDecimal;
		m_bSignSeparated = false;
		m_bSignLeading = false;
		m_nComp = 0;
	}
	
	NumericValue(NumericValue master)
	{
		m_bSigned = master.m_bSigned;
		m_nNbDigitInteger = master.m_nNbDigitInteger;
		m_nNbDigitDecimal = master.m_nNbDigitDecimal;
		m_nComp = master.m_nComp;
		m_bSignSeparated = master.m_bSignSeparated;
		m_bSignLeading = master.m_bSignLeading;
	}
	
	VarDefBuffer createVarDefFPacNum(VarDefBase varDefParent, DeclareType9 declareType9)
	{
		if(m_nComp == -3)	// Comp-3 specified: 2 digits by char (1 by nibble), with the sign in the rightmost nibble
		{
			if(m_bSigned)
			{
				if(m_nNbDigitDecimal == 0)
				{
					if(isIntEnough())
						return new VarDefFPacNumIntSignComp3(varDefParent, declareType9, this);
				}
			}
		}
		return createVarDef(varDefParent, declareType9);
	}
	
	VarDefBuffer createVarDef(VarDefBase varDefParent, DeclareType9 declareType9)
	{
		if(m_nComp == 0)	// No Comp-... specified: 1 char is a digit, except maybe the sign that may be embbed in the last char	
		{
			if(m_nNbDigitDecimal == 0)
			{
				if(m_bSigned && m_bSignSeparated)
				{
					if(m_bSignLeading)
					{
						if(isIntEnough())
							return new VarDefNumIntSignLeadingComp0(varDefParent, declareType9, this);
						else
							return new VarDefNumIntSignLeadingComp0Long(varDefParent, declareType9, this);
					}
					else	// Trailing
					{
						if(isIntEnough())
							return new VarDefNumIntSignTrailingComp0(varDefParent, declareType9, this);
						else
							return new VarDefNumIntSignTrailingComp0Long(varDefParent, declareType9, this);
					}
				}
				else if(m_bSigned)
				{
					if(isIntEnough())
						return new VarDefNumIntSignComp0(varDefParent, declareType9, this);
					else
						return new VarDefNumIntSignComp0Long(varDefParent, declareType9, this);
				}
				else
				{
					if(isIntEnough())
						return new VarDefNumIntComp0(varDefParent, declareType9, this);
					else
						return new VarDefNumIntComp0Long(varDefParent, declareType9, this);
				}
			}
			else
			{
				if(m_bSigned && m_bSignSeparated)
				{
					if(m_bSignLeading)
						return new VarDefNumDecSignLeadingComp0(varDefParent, declareType9, this);
					else	// Trailing
						return new VarDefNumDecSignTrailingComp0(varDefParent, declareType9, this);
				}
				else if(m_bSigned)
					return new VarDefNumDecSignComp0(varDefParent, declareType9, this);
				else
					return new VarDefNumDecComp0(varDefParent, declareType9, this);
			}
		}
		else if(m_nComp == -3)	// Comp-3 specified: 2 digits by char (1 by nibble), with the sign in the rightmost nibble
		{
			if(!m_bSigned)
			{
				if(m_nNbDigitDecimal == 0)
				{
					if(isIntEnough())
						return new VarDefNumIntComp3(varDefParent, declareType9, this);
					else
						return new VarDefNumIntComp3Long(varDefParent, declareType9, this);
				}
				else
					return new VarDefNumDecComp3(varDefParent, declareType9, this);
			}
			else
			{
				if(m_nNbDigitDecimal == 0)
				{
					if(isIntEnough())
						return new VarDefNumIntSignComp3(varDefParent, declareType9, this);
					else
						return new VarDefNumIntSignComp3Long(varDefParent, declareType9, this);
				}
				else
					return new VarDefNumDecSignComp3(varDefParent, declareType9, this);
			}
		}
		else if(m_nComp == -4)	// Binary
		{
			if(m_nNbDigitDecimal == 0)
			{
				if(!m_bSigned)
				{
					if(isIntEnough())
						return new VarDefNumIntComp4(varDefParent, declareType9, this);
					else
						return new VarDefNumIntComp4Long(varDefParent, declareType9, this);
				}					
				else
				{
					if(isIntEnough())
						return new VarDefNumIntSignComp4(varDefParent, declareType9, this);
					else
						return new VarDefNumIntSignComp4Long(varDefParent, declareType9, this);
				}
			}
			else 
			{
				if(!m_bSigned)
					return new VarDefNumDecComp4(varDefParent, declareType9, this);
				else
					return new VarDefNumDecSignComp4(varDefParent, declareType9, this);
			}			
		}
		return null;
	}
	
	private boolean isIntEnough()
	{
		return IntLongDeterminator.isIntEnough(m_nNbDigitInteger);
	}
	
	VarNum createVar(DeclareType9 declareType9)
	{		
		if(m_nComp == 0)
		{
			if(!m_bSigned)
			{
				if(isIntEnough())
				{
					if(m_nNbDigitDecimal == 0)
						return new VarNumIntComp0(declareType9);
					else
						return new VarNumDecComp0(declareType9);
				}
				else
				{
					if(m_nNbDigitDecimal == 0)
						return new VarNumIntComp0Long(declareType9);
					else
						return new VarNumDecComp0Long(declareType9);
				}
			}
			else
			{
				if(m_nNbDigitDecimal == 0)
				{
					if(m_bSignSeparated)
					{
						if(m_bSignLeading)
							return new VarNumIntSignLeadingComp0(declareType9);
						else
							return new VarNumIntSignTrailingComp0(declareType9);
					}
					else
						return new VarNumIntSignComp0(declareType9);
				}
				else
				{
					if(m_bSignSeparated)
					{
						if(m_bSignLeading)
							return new VarNumDecSignLeadingComp0(declareType9);
						else
							return new VarNumDecSignTrailingComp0(declareType9);
					}
					else
						return new VarNumDecSignComp0(declareType9);
				}
			}
		}
		else if(m_nComp == -3)
		{
			if(!m_bSigned)
			{
				if(m_nNbDigitDecimal == 0)
					return new VarNumIntComp3(declareType9);
				else
					return new VarNumDecComp3(declareType9);
			}
			else
			{
				if(m_nNbDigitDecimal == 0)
					return new VarNumIntSignComp3(declareType9);
				else
					return new VarNumDecSignComp3(declareType9);
			}
		}
		else if(m_nComp == -4)
		{
			if(m_nNbDigitDecimal == 0)
			{
				if(!m_bSigned)
				{
					if(isIntEnough())
						return new VarNumIntComp4(declareType9);
					else
						return new VarNumIntComp4Long(declareType9);
				}
				else
				{
					if(isIntEnough())
						return new VarNumIntSignComp4(declareType9);
					else
						return new VarNumIntSignComp4Long(declareType9);
				}
			}
			else
			{
				if(!m_bSigned)
					return new VarNumDecComp4(declareType9);
				else
					return new VarNumDecSignComp4(declareType9);
			}
		}
		return null;
	}

	void setSignLeadingSeparated(boolean bLeading)
	{
		m_bSigned = true;
		m_bSignSeparated = true;
		m_bSignLeading = bLeading;
	}
	
	boolean m_bSigned = false;
	boolean m_bSignSeparated = false;
	boolean m_bSignLeading = false;
	int m_nComp = 0;
	int m_nNbDigitInteger = 0; 
	int m_nNbDigitDecimal = 0;
}
