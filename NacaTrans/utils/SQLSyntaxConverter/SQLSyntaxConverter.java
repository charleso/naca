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
package utils.SQLSyntaxConverter;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.zip.ZipEntry;

import generate.SQLDumper;
import jlib.log.Log;
import jlib.misc.FileSystem;
import jlib.misc.StringUtil;
import jlib.xml.Tag;
import jlib.xml.TagCursor;
import lexer.CBaseToken;
import lexer.CTokenList;
import lexer.CTokenType;
import utils.Transcoder;
import utils.TranscoderEngine;
import utils.DCLGenConverter.DCLGenConverterTarget;

public class SQLSyntaxConverter
{
	private DCLGenConverterTarget m_target = null;
	//private String m_csGroup = null;
	private Hashtable<String, SQLFunctionConvertion> m_hashSQLFunctions = null;	
	private Hashtable<String, ArrayConvertibleSQLStatement> m_hashStatementByProgram = new Hashtable<String, ArrayConvertibleSQLStatement>();   
	
	
	/*
	 <Engines>
	 	<SQLSyntaxConverter Target="DB2|Oracle">
	 		<SQLFunctions>
	 			<SQLFunction Source="CURRENT TIMESTAMP" TargetDB2="CURRENT TIMESTAMP" TargetOracle="CURRENT_TIMESTAMP"/>  
	 		</SQLFunctions>
	 	</SQLSyntaxConverter>
	 </Engines>
	*/ 
	 
	public SQLSyntaxConverter()
	{
	}
	
	public boolean fill(Tag tag)
	{
		if(tag == null)
			return false;
					
		String csTarget = tag.getVal("Target");
		m_target = DCLGenConverterTarget.getTarget(csTarget);
		
		Tag tagSQLFunctions = tag.getChild("SQLFunctions");
		if(tagSQLFunctions != null)
		{
			TagCursor cur = new TagCursor();
			Tag tagSQLFunction = tagSQLFunctions.getFirstChild(cur, "SQLFunction");
			while(tagSQLFunction != null)
			{
				SQLFunctionConvertion s = new SQLFunctionConvertion();
				String csSource = s.fill(tagSQLFunction);
				Transcoder.logInfo("Loading SQLSyntax converter settings definition for: "+csSource);
				if(m_hashSQLFunctions == null)
					m_hashSQLFunctions = new Hashtable<String, SQLFunctionConvertion>(); 
				m_hashSQLFunctions.put(csSource, s);
				
				tagSQLFunction = tagSQLFunctions.getNextChild(cur);
			}
		}
		
		String csGroup = tag.getVal("Group");
		String csFileStatementsToReplace = tag.getVal("FileStatementsToReplace");
		boolean b = manageStatementsToReplace(csGroup, csFileStatementsToReplace);		
		return b;
	}
	
	public String resolve(String csSource)
	{
		String csSourceTrimmed = csSource.trim();
		
		if(csSourceTrimmed.equalsIgnoreCase("CURRENT TIMESTAMP"))	// Normalize
			csSourceTrimmed = "CURRENT_TIMESTAMP";
		if(csSourceTrimmed.equalsIgnoreCase("CURRENT DATE"))
			csSourceTrimmed = "CURRENT_DATE";
		
		if(m_hashSQLFunctions == null)
			return csSource;
		SQLFunctionConvertion sqlFunctionConvertion = m_hashSQLFunctions.get(csSourceTrimmed);
		if(sqlFunctionConvertion == null)
			return csSource;
			
		String cs = sqlFunctionConvertion.getValueForTarget(m_target);
		int nNbLeadingSpaces = csSource.indexOf(csSourceTrimmed);
		cs = StringUtil.addLeadingSpaces(cs, nNbLeadingSpaces);
		
		int nNbTrailingSpaces = csSource.length() - csSourceTrimmed.length() - nNbLeadingSpaces;
		cs = StringUtil.addTrailingSpaces(cs, nNbTrailingSpaces);
		
		return cs;
	}
	
	private boolean manageStatementsToReplace(String csGroup, String csFile)
	{
		if(StringUtil.isEmpty(csFile))
			return false;
		Tag tag = Tag.createFromFile(csFile);
		if(tag == null)
			return false;
		
		//m_csGroup = csGroup;
		TagCursor cur = new TagCursor();
		Tag tagDefinition = tag.getFirstChild(cur, "Definition");
		while(tagDefinition != null)
		{
			String csProgram = tagDefinition.getVal("Program", "*");
			String csTranslationFile = tagDefinition.getVal("File");
			
			Transcoder.logInfo("Loading SQLSyntax converter convertion settings: "+ csTranslationFile);
			
			ArrayConvertibleSQLStatement arrayConvertibleSQLStatement = m_hashStatementByProgram.get(csProgram);
			if(arrayConvertibleSQLStatement == null)
			{
				arrayConvertibleSQLStatement = new ArrayConvertibleSQLStatement(); 
				m_hashStatementByProgram.put(csProgram, arrayConvertibleSQLStatement);
			}
			arrayConvertibleSQLStatement.manageProgramStatementsToReplace(csProgram, csGroup, csTranslationFile);
			
			tagDefinition = tag.getNextChild(cur);
		}
		return true;
	}
	
	public void doLexing(Transcoder transcoder)
	{
		Enumeration<ArrayConvertibleSQLStatement> e = m_hashStatementByProgram.elements();
	    while (e.hasMoreElements())
		{
	    	ArrayConvertibleSQLStatement arrayConvertibleSQLStatement = e.nextElement();
	    	arrayConvertibleSQLStatement.lexStatements(transcoder);	    	
		}	
	}
	
	public CTokenList updateSQLStatements(String csFilename, CTokenList tokLstSource)
	{
		CTokenList lst = doUpdateSQLStatements(csFilename, tokLstSource);	// specific definitions first
		lst = doUpdateSQLStatements("*", lst);	// remaining generic definitions at last
		return lst;		
	}
	
	private CTokenList doUpdateSQLStatements(String csFilename, CTokenList tokLstSource)
	{
		if(tokLstSource ==  null)
			return tokLstSource;
		
		ArrayConvertibleSQLStatement arrayConvertibleSQLStatement = m_hashStatementByProgram.get(csFilename);
		if(arrayConvertibleSQLStatement != null)
		{
			CTokenList lst = arrayConvertibleSQLStatement.updateProgramSQLStatements(tokLstSource);
			return lst;
		}
		return tokLstSource;
	}
}
