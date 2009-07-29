/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;



public class SQLClauseSPCall extends SQLClauseSPCallBase 
{
	SQLClauseSPCall(String csName, boolean bCheckParams)
	{
		super(csName, bCheckParams);
	}
	
	// Parameters IN
	public SQLClauseSPCall paramIn(String csVal)
	{
		SQLClauseSPParamInString param = new SQLClauseSPParamInString(csVal);
		addParam(param);
		 
		return this;
	}
	
	public SQLClauseSPCall paramIn(int nVal)
	{
		SQLClauseSPParamInInt param = new SQLClauseSPParamInInt(nVal);
		addParam(param);
		 
		return this;
	}	
	
	public SQLClauseSPCall paramIn(short sVal)
	{
		SQLClauseSPParamInShort param = new SQLClauseSPParamInShort(sVal);
		addParam(param);
		 
		return this;
	}	
	
	public SQLClauseSPCall paramIn(double dVal)
	{
		SQLClauseSPParamInDouble param = new SQLClauseSPParamInDouble(dVal);
		addParam(param);
		 
		return this;
	}
	
	// Parameters IN-OUT
	public SQLClauseSPCall paramInOut(String tcsVal[])
	{
		SQLClauseSPParamInOutString param = new SQLClauseSPParamInOutString(SQLClauseSPParamWay.InOut, tcsVal);
		addParam(param);
		 
		return this;
	}
	
	public SQLClauseSPCall paramInOut(int tnVal[])
	{
		SQLClauseSPParamInOutInt param = new SQLClauseSPParamInOutInt(SQLClauseSPParamWay.InOut, tnVal);
		addParam(param);
		 
		return this;
	}
	
	public SQLClauseSPCall paramInOut(double tdVal[])
	{
		SQLClauseSPParamInOutDouble param = new SQLClauseSPParamInOutDouble(SQLClauseSPParamWay.InOut, tdVal);
		addParam(param);
		 
		return this;
	}
	
	// Parameters OUT
	public SQLClauseSPCall paramOut(String tcsVal[])
	{
		SQLClauseSPParamInOutString param = new SQLClauseSPParamInOutString(SQLClauseSPParamWay.Out, tcsVal);
		addParam(param);
		 
		return this;
	}
	
	public SQLClauseSPCall paramOut(int tnVal[])
	{
		SQLClauseSPParamInOutInt param = new SQLClauseSPParamInOutInt(SQLClauseSPParamWay.Out, tnVal);
		addParam(param);
		 
		return this;
	}
	
	public SQLClauseSPCall paramOut(double tdVal[])
	{
		SQLClauseSPParamInOutDouble param = new SQLClauseSPParamInOutDouble(SQLClauseSPParamWay.Out, tdVal);
		addParam(param);
		 
		return this;
	}

//	
//	void retrieveOutValues(PreparedCallableStatement preparedCallableStatement, CSQLStatus sqlStatus)
//	{
//		if(preparedCallableStatement != null)
//		{
//			for(int n=0; n<m_arrParamDesc.size(); n++)
//			{
//				StoredProcParamDesc paramDesc = m_arrParamDesc.get(n);
//				paramDesc.retrieveOutValues(n, preparedCallableStatement, sqlStatus);
//			}
//		}
//	}

}
