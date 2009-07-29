/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.DCLGenConverter;

import generate.SQLDumper;

import java.util.ArrayList;

import semantic.SQL.CEntitySQLDeclareTable;
import utils.PathsManager;
import jlib.misc.FileSystem;
import jlib.xml.Tag;

public class DCLGenConverter
{
	private DCLGenConverterTarget m_target = null;
	private String m_csOutputPath = null;
	private SQLDumper m_sqlDumper = null;		// Optional SQL dumper   
	private ArrayList<String> m_arrMissingTables = null;
	
	public DCLGenConverter()
	{
	}
	
	public void fill(Tag tagDCLGDENConverter)
	{
		if(tagDCLGDENConverter == null)
			return ;
		String csTarget = tagDCLGDENConverter.getVal("Target");
		m_target = DCLGenConverterTarget.getTarget(csTarget);
		
		m_csOutputPath = tagDCLGDENConverter.getVal("OutputPath");
		m_csOutputPath = PathsManager.adjustPath(m_csOutputPath);
		m_csOutputPath = FileSystem.normalizePath(m_csOutputPath);
		
		
		FileSystem.createPath(m_csOutputPath);
		
		String csSQLDumpFile = tagDCLGDENConverter.getVal("SQLDumpFile");
		if(csSQLDumpFile != null)
		{
			m_sqlDumper = new SQLDumper();
			m_sqlDumper.setOutputPathFileName(csSQLDumpFile);			
		}
	}
	
	public void generate(CEntitySQLDeclareTable declareTable)
	{
		if(m_target != null)
			m_target.generate(declareTable, m_csOutputPath);		
	}
	
	public void addMissingTable(String csName)
	{
		if(m_arrMissingTables == null)
			m_arrMissingTables = new ArrayList<String>();
		m_arrMissingTables.add(csName);
	}
	
	public SQLDumper getSQLDumper()
	{
		return m_sqlDumper;
	}
	
	public void close()
	{
		if(m_sqlDumper != null)
			m_sqlDumper.close();
	}
	
	public boolean isOracleTarget()
	{
		if(m_target != null)
			return m_target.isOracle();
		return false;
	}
}
