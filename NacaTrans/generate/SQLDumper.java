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
package generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import jlib.misc.FileSystem;
import jlib.misc.StringRef;
import utils.Transcoder;

public class SQLDumper
{
	private PrintStream m_output ;
	private StringBuilder m_sb = null;
	private String m_csFileName = null;
	private String m_csLastFileName = null;
	private int m_nNbParam = 0;
	private int m_nNbInto = 0;
	private int m_nNbValues = 0; 
	private ArrayList<String> m_arrParag = new ArrayList<String>(); 
	private ArrayList<String> m_arrCursors = new ArrayList<String>();
	private int m_nNbStatements = 0;
	private int m_nNbFiles = 0;
	private int m_nNbSelect = 0;
	private int m_nNbInsert = 0;
	private int m_nNbUpdate = 0;
	private int m_nNbDelete = 0;
	private int m_nNbCursorOpen = 0;
	private int m_nNbCursorClose = 0;
	
	public SQLDumper()
	{
	}
	
	public void setFileName(String csFileName)
	{
		m_csFileName = csFileName;
	}
	
	public boolean setOutputPathFileName(String csOutFilePathName)
	{
		StringRef rcsPath = new StringRef(); 
		StringRef rcsExt = new StringRef();
		FileSystem.splitFilePathExt(csOutFilePathName, rcsPath, rcsExt);
		
		FileSystem.createPath(rcsPath.get());
		File f = new File(csOutFilePathName);
	
		try
		{
			//OutputStream out = new FileOutputStream(cs);
			m_output = new PrintStream(f, "ISO-8859-1") ;
			m_sb = new StringBuilder();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			Transcoder.logError("Can't create file " + f.getAbsolutePath()) ;
			return false;
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return false;
		}	
		return true;
	}
	
	public void startFile()
	{
		if(!m_csFileName.equals(m_csLastFileName))
		{
			endLastParag();
			startParag(m_csFileName);
			m_sb.append("\n\t\t");
			m_csLastFileName = m_csFileName;
			m_nNbFiles++;
		}
	}
	
	private void endLastParag()
	{
		if(m_arrParag.size() > 0)
		{
			m_sb.append("\n\t}\n");	
		}
	}
	
	private void startParag(String csName)
	{
		m_arrParag.add(csName);
		m_sb.append("\tParagraph " + csName + "= new Paragraph(this);\n");                                                
		m_sb.append("\tpublic void " + csName + "() {");
		m_sb.append("\n\t");
	}
	
	public void startStatement(int nLine)
	{
		startFile();
		m_sb.append("\n\t\t");
		m_sb.append("// FileName: " + m_csFileName + " (" + nLine + ") :\n\t\t") ;
		m_nNbStatements++;
	}
			
	public void append(String cs)
	{
		m_sb.append(cs);
	}
	
	public void appendLineFeed()
	{
		m_sb.append("\n\t\t");
	}
	
	public void close()
	{
		startClass();		
		startWorking();		
		startProcedure();
		m_output.print(m_sb);
		closeProcedure();
		closeClass();
		
		m_output.println("// Generated " + m_nNbStatements + " SQL Statements in " + m_nNbFiles + " files");
		m_output.println("// Nb Select:" + m_nNbSelect);
		m_output.println("// Nb Insert:" + m_nNbInsert);
		m_output.println("// Nb Update:" + m_nNbUpdate);
		m_output.println("// Nb Delete:" + m_nNbDelete);
		m_output.println("// Nb CursorOpen:" + m_nNbCursorOpen);
		m_output.println("// Nb CursorClose:" + m_nNbCursorClose);
		
		m_output.flush();
		m_output.close() ;
		m_output = null;
		
		Transcoder.logInfo("SQLDumper: Generated " + m_nNbStatements + " SQL Statements in " + m_nNbFiles + " files");
		Transcoder.logInfo("SQLDumper: Nb Select:" + m_nNbSelect);
		Transcoder.logInfo("SQLDumper: Nb Insert:" + m_nNbInsert);
		Transcoder.logInfo("SQLDumper: Nb Update:" + m_nNbUpdate);
		Transcoder.logInfo("SQLDumper: Nb Delete:" + m_nNbDelete);
		Transcoder.logInfo("SQLDumper: Nb CursorOpen:" + m_nNbCursorOpen);
		Transcoder.logInfo("SQLDumper: Nb CursorClose:" + m_nNbCursorClose);
	}
	
	public void setNbInto(int n)
	{
		m_nNbInto = Math.max(n, m_nNbInto); 
	}
	
	public void setNbParam(int n)
	{
		m_nNbParam = Math.max(n, m_nNbParam); 
	}
	
	public void setNbValues(int n)
	{
		m_nNbValues = Math.max(n, m_nNbValues); 
	}
	
	public void registerCursorName(String csCurName)
	{
		for(int n=0; n<m_arrCursors.size(); n++)
		{
			String cs = m_arrCursors.get(n);
			if(cs.equals(csCurName))
				return ;
		}
		m_arrCursors.add(csCurName);
	}
	
	private void startClass()
	{
		m_output.println("import nacaLib.program.Paragraph;");
		m_output.println("import nacaLib.sqlSupport.SQL;");
		m_output.println("import nacaLib.sqlSupport.CSQLStatus;");
		m_output.println("import nacaLib.varEx.DataSection;");
		m_output.println("import nacaLib.varEx.Var;");
		m_output.println("import nacaLib.sqlSupport.SQLCursor;");
		m_output.println("import idea.onlinePrgEnv.OnlineProgram;");
		m_output.println("import jlib.log.Log;");
		m_output.println("import jlib.misc.DataFileWrite;");
		m_output.println("");

		m_output.println("public class TestSQLDump extends OnlineProgram");
		m_output.println("{");
		
		m_output.println("\tSQL m_sql = null;");
		m_output.println("\tSQLCursor m_cur = null;");
		m_output.println("\tDataFileWrite m_successWriter = null;");
		m_output.println("\tDataFileWrite m_failureWriter = null;");
		m_output.println("\tint m_nNbSuccess = 0;");
		m_output.println("\tint m_nNbFailures = 0;");

		
		if(m_arrCursors.size() > 0)
		{
			m_output.println("\tDataSection sqlcursorsection = declare.cursorSection() ;");
			for(int n=0; n<m_arrCursors.size(); n++)
			{
				String cs = m_arrCursors.get(n);
				m_output.println("\tSQLCursor " + cs + "= declare.cursor() ;");
			}
			m_output.println();
		}
	}

	private void startWorking()	
	{
		m_output.println("\tDataSection WorkingStorage = declare.workingStorageSection();");
		m_output.println();
		for(int n=0; n<m_nNbInto; n++)
			m_output.println("\tVar varInto" + n + " = declare.level(1).picX(10).var();");
		for(int n=0; n<m_nNbParam; n++)
			m_output.println("\tVar varParam" + n + " = declare.level(1).picX(10).var();");
		for(int n=0; n<m_nNbValues; n++)
			m_output.println("\tVar varValue" + n + " = declare.level(1).picX(10).var();");
		m_output.println();
	}
	
	private void startProcedure()
	{
		m_output.println("\tpublic void procedureDivision()");
		m_output.println("\t{");
		
		m_output.println("\t\tm_successWriter = new DataFileWrite(\"sqlDumpSuccessResult.txt\", false);");
		m_output.println("\t\tm_successWriter.open();");

		m_output.println("\t\tm_failureWriter = new DataFileWrite(\"sqlDumpFailureResult.txt\", false);");
		m_output.println("\t\tm_failureWriter.open();");
		m_output.println("");
		
		for(int n=0; n<m_arrParag.size(); n++)
		{
			String csParagName = m_arrParag.get(n);
			m_output.println("\t\tperform(" + csParagName + ");");			
		}
		m_output.println("");
		m_output.println("\t\tm_successWriter.writeRecord(\"Nb Success: \" + m_nNbSuccess);");
		m_output.println("\t\tm_successWriter.close();");
		
		m_output.println("\t\tm_failureWriter.writeRecord(\"Nb Failures: \" + m_nNbFailures);");
		m_output.println("\t\tm_failureWriter.close();");
		m_output.println("\t\tstopRun();");		
		m_output.println("\t}\n");	
		
		m_output.println("\tprivate void handleSqlStatus()");
		m_output.println("\t{");
		m_output.println("\t\tif(m_sql == null)");
		m_output.println("\t\t{");
		m_output.println("\t\t\tm_failureWriter.writeRecord(\"SQL is NULL\");");
		m_output.println("\t\t\tm_nNbFailures++;");
		m_output.println("\t\t\treturn ;");
		m_output.println("\t\t}");
		m_output.println("\t\tCSQLStatus status = m_sql.getDebugSQLStatus();");
		m_output.println("\t\tint nCode = status.getSQLCode();");
		m_output.println("\t\tString cs;");
		m_output.println("\t\tif(nCode != 0 && nCode != 100 && nCode != 1722) // These codes are success");
		m_output.println("\t\t{");
		m_output.println("\t\t\tcs = \"Failed: \"+m_sql.getQuery() + \"; Status=\"+status.getReason() + \"; Code=\" + status.getSQLCode();");
		m_output.println("\t\t\tm_failureWriter.writeRecord(cs);");
		m_output.println("\t\t\tm_nNbFailures++;");
		m_output.println("\t\t}");
		m_output.println("\t\telse");
		m_output.println("\t\t{");
		m_output.println("\t\t\tcs = \"Success: \"+m_sql.getQuery();");
		m_output.println("\t\t\tm_successWriter.writeRecord(cs);");
		m_output.println("\t\t\tm_nNbSuccess++;");
		m_output.println("\t\t}");
		m_output.println("\t\tLog.logNormal(\"Nb Failures=\"+m_nNbFailures + \" Nb Success=\"+m_nNbSuccess);");
		m_output.println("\t}");
		m_output.println("");		
		
		m_output.println("\tprivate void handleSqlStatus(SQLCursor cur)");
		m_output.println("\t{");
		m_output.println("\t\tif(cur != null)");
		m_output.println("\t\t{");
		m_output.println("\t\t\tm_sql = cur.m_SQL;");
		m_output.println("\t\t\thandleSqlStatus();");
		m_output.println("\t\t}");
		m_output.println("\t\telse");
		m_output.println("\t\t\tm_failureWriter.writeRecord(\"Cursor is NULL\");");
		m_output.println("\t}");
		
		m_output.println("\tprivate void handleExceptionCursor(Exception e)");
		m_output.println("\t{");
		m_output.println("\t\tString cs = \"Failed Exception: \"+e.toString();");
		m_output.println("\t\tm_failureWriter.writeRecord(cs);");
		m_output.println("\t\tm_nNbFailures++;");
		m_output.println("\t}");

		
		m_output.println("");
	}
	
	private void closeProcedure()
	{
		m_output.println("\t}");
	}
	
	private void closeClass()
	{
		m_output.println("}");
	}
	
	public void incNbSelect()
	{
		m_nNbSelect++;
	}
	
	public void incNbInsert()
	{
		m_nNbInsert++;
	}
	
	public void incNbUpdate()
	{
		m_nNbUpdate++;
	}
	
	public void incNbDelete()
	{
		m_nNbDelete++;
	}
	
	public void incNbCursorOpen()
	{
		m_nNbCursorOpen++;
	}
	
	public void incNbCursorClose()
	{
		m_nNbCursorClose++;
	}
}

