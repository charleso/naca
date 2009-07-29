/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;
import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySQLCursorSelectStatement extends CBaseActionEntity
{
	public CEntitySQLCursorSelectStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		
	}
	public void SetSelect(String csStatement, Vector<CDataEntity> arrParameters, CEntitySQLCursor cur, int nbCol, boolean bWithHold)
	{
		m_csStatement = csStatement ;
		m_arrParameters = arrParameters;
		m_Cursor = cur ;
		m_nbCol = nbCol ;	
		m_bWithHold = bWithHold ;
	}
	protected int m_nbCol = 0 ;
	protected String m_csStatement = "" ;
	protected Vector<CDataEntity> m_arrParameters = null;
	protected CEntitySQLCursor m_Cursor = null;
	protected boolean m_bWithHold = false ;
	public void Clear()
	{
		super.Clear();
		m_arrParameters.clear() ;
		m_Cursor = null ;
	}

	public int GetNbColumns()
	{
		return m_nbCol ;
	}
	public boolean ignore()
	{
		return true ; // the SELECT declaration is ignore at this point, but exported in place of the OPEN statement
	}

	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		int i = m_arrParameters.indexOf(field) ;
		if (i>=0 && i<m_arrParameters.size())
		{
			m_arrParameters.get(i).UnRegisterReadingAction(this) ;
			m_arrParameters.set(i, var) ;
			var.RegisterReadingAction(this) ;
			return true ;
		} 
		return false ;
	}


}

