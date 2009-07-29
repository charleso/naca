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
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CEntityFileDescriptor;
import utils.CObjectCatalog;

public abstract class CEntityWriteFile extends CBaseActionEntity
{

	public CEntityWriteFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void setFileDescriptor(CEntityFileDescriptor efd, CDataEntity data)
	{
		m_eFileDescriptor = efd ;
		m_eDataFrom = data ;
	}
	
	public void setWriteBeforeAfterPositioningLine(boolean bBefore, CDataEntity eNbLinesPositioning)	
	{
		m_bBefore = bBefore;
		m_bPage = false;
		m_eNbLinesPositioning = eNbLinesPositioning;
	}

	public void setWriteBeforeAfterPage(boolean bBefore)	
	{
		m_bBefore = bBefore;
		m_bPage = true;
		m_eNbLinesPositioning = null;
	}

	
	protected CEntityFileDescriptor m_eFileDescriptor = null  ;
	protected CDataEntity m_eDataFrom = null ;

	protected CDataEntity m_eNbLinesPositioning = null;
	protected boolean m_bPage = false;
	protected boolean m_bBefore = false;
}
