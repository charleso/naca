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
 * Created on Sep 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.utils.IntVector;
import com.sun.org.apache.xml.internal.utils.StringToIntTable;
import com.sun.org.apache.xml.internal.utils.StringVector;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CGlobalEntityCounter
{
	private static String NB_LINES = "Lines" ;
	private static String NB_LINES_COMMENTS = "CommentLines" ;
	private static String NB_LINES_CODE = "CodeLines" ;
	private static String NB_COBOL_FILES = "CobolFiles";
	private static String NB_BMS_FILES = "BMSFiles";
	private static String NB_COPY_FILES = "CopyFiles";
	private static String NB_DATA_TABLES = "DataTables";

	public class CItemCounter
	{
		public String m_ItemName = "" ;
		public int m_nItemMin = 0;
		public int m_nItemMax = 0;
		public int m_nItemCount = 0;
		public int m_nItemTotal = 0 ;
		public Hashtable<String, Integer> m_tabOptions = new Hashtable<String, Integer>() ;
	}
	public class CDepCounter
	{
		public String m_ItemName = "" ;
		public StringToIntTable m_tabCount = new StringToIntTable() ;
		public StringVector m_arrDeps = new StringVector();
	}
	
	protected Hashtable<String, CItemCounter> m_tabProperties = new Hashtable<String, CItemCounter>() ;
	protected Hashtable<String, CItemCounter> m_tabCobolVerbs = new Hashtable<String, CItemCounter>();
	protected Hashtable<String, CItemCounter> m_tabCICSCommands = new Hashtable<String, CItemCounter>();
	protected Hashtable<String, CItemCounter> m_tabSQLCommands = new Hashtable<String, CItemCounter>();
	protected Hashtable<String, CItemCounter> m_tabDataTables = new Hashtable<String, CItemCounter>();
	
	// dependences
	protected Hashtable<String, CDepCounter> m_tabCopyForPrograms = new Hashtable<String, CDepCounter>();
	protected Hashtable<String, CDepCounter> m_tabDeepCopyForPrograms = new Hashtable<String, CDepCounter>();
	protected Hashtable<String, CDepCounter> m_tabProgramsUsingCopy = new Hashtable<String, CDepCounter>();
	protected Hashtable<String, CDepCounter> m_tabMissingCopy = new Hashtable<String, CDepCounter>();
	protected Hashtable<String, CDepCounter> m_tabProgramCalled = new Hashtable<String, CDepCounter>();
	protected Hashtable<String, CDepCounter> m_tabSubProgramCalls = new Hashtable<String, CDepCounter>();
	protected Hashtable<String, CDepCounter> m_tabMissingSubProgram = new Hashtable<String, CDepCounter>();
	
	protected static CGlobalEntityCounter ms_Instance = null ;
	
	public static CGlobalEntityCounter GetInstance()
	{
		if (ms_Instance == null)
		{
			ms_Instance = new CGlobalEntityCounter() ;
		}
		return ms_Instance ;
	}
	protected CGlobalEntityCounter()
	{
	}
	
	public void Export(String path)
	{
		try
		{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			Element elem = MakeXML(doc) ;
			Source source = new DOMSource(doc);
			FileOutputStream file = new FileOutputStream(path+".xml");
			StreamResult res = new StreamResult(file) ;
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.ENCODING, "ISO8859-1");
			xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.transform(source, res);
			
			File fSS = new File(path+".xsl");
			Source stylesheet = new StreamSource(fSS) ; 
			Templates templ = TransformerFactory.newInstance().newTemplates(stylesheet) ;
			Transformer xformer2 = templ.newTransformer() ;			

			FileOutputStream file2 = new FileOutputStream(path+".html");
			StreamResult result = new StreamResult(file2) ;
			xformer2.transform(source, result);
			
		}
		catch (TransformerConfigurationException e)
		{
			e.printStackTrace() ;
			int n =0;
		}
		catch (TransformerException e)
		{
			e.printStackTrace() ;
			int n =0;
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace() ;
			int n =0;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace() ;
			int n =0;
		}
	}
	
	protected Object GetNextCount(Enumeration enumere)
	{
		try
		{
			return enumere.nextElement() ;
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
			return null ;
		}
	}
	protected CDepCounter GetNextDep(Enumeration enumere)
	{
		try
		{
			return (CDepCounter)enumere.nextElement() ;
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
			return null ;
		}
	}
	protected Element MakeXML(Document root)
	{
		Element eItemCount = root.createElement("ItemCount");
		root.appendChild(eItemCount) ;
		if (m_tabProperties != null)
		{
			Element eProperties = root.createElement("Category");
			eProperties.setAttribute("Name", "GeneralProperties");
			eItemCount.appendChild(eProperties);
			Enumeration enumere = m_tabProperties.elements() ;
			CItemCounter ic = (CItemCounter)GetNextCount(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Item");
				e.setAttribute("Name", ic.m_ItemName);
				eProperties.appendChild(e);
				if (ic.m_nItemMin>0)
				{
					e.setAttribute("Min", String.valueOf(ic.m_nItemMin));
				}
				if (ic.m_nItemMax>0)
				{
					e.setAttribute("Max", String.valueOf(ic.m_nItemMax));
				}
				if (ic.m_nItemTotal>0)
				{
					e.setAttribute("Total", String.valueOf(ic.m_nItemTotal));
				}
				if (ic.m_nItemCount>0)
				{
					e.setAttribute("Count", String.valueOf(ic.m_nItemCount));
				}
				
				Enumeration enumopt = ic.m_tabOptions.keys();
				try
				{
					String cs = (String)enumopt.nextElement();
					while (cs != null)
					{
						Element eOpt = root.createElement("SubItem");
						eOpt.setAttribute("Name", cs);
						e.appendChild(eOpt);
						Integer i = ic.m_tabOptions.get(cs) ;
						eOpt.setAttribute("Count", i.toString());
						cs = (String)enumopt.nextElement();
					}
				}
				catch (NoSuchElementException exp)
				{
					//exp.printStackTrace();
				}
				ic = (CItemCounter)GetNextCount(enumere);
			}
		}
		if (m_tabCobolVerbs != null)
		{
			Element eProperties = root.createElement("Category");
			eProperties.setAttribute("Name", "CobolVerbs");
			eItemCount.appendChild(eProperties);
			Enumeration enumere = m_tabCobolVerbs.elements() ;
			CItemCounter ic = (CItemCounter)GetNextCount(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Item");
				e.setAttribute("Name", ic.m_ItemName);
				eProperties.appendChild(e);
				if (ic.m_nItemCount>0)
				{
					e.setAttribute("Count", String.valueOf(ic.m_nItemCount));
				}
				
				Enumeration enumopt = ic.m_tabOptions.keys();
				try
				{
					String cs = (String)enumopt.nextElement();
					while (cs != null)
					{
						Element eOpt = root.createElement("SubItem");
						eOpt.setAttribute("Name", cs);
						e.appendChild(eOpt);
						Integer i = ic.m_tabOptions.get(cs) ;
						eOpt.setAttribute("Count", i.toString());
						cs = (String)enumopt.nextElement();
					}
				}
				catch (NoSuchElementException exp)
				{
					//exp.printStackTrace();
				}
				ic = (CItemCounter)GetNextCount(enumere);
			}
		}
		if (m_tabCICSCommands != null)
		{
			Element eProperties = root.createElement("Category");
			eProperties.setAttribute("Name", "CICSCommands");
			eItemCount.appendChild(eProperties);
			Enumeration enumere = m_tabCICSCommands.elements() ;
			CItemCounter ic = (CItemCounter)GetNextCount(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Item");
				e.setAttribute("Name", ic.m_ItemName);
				eProperties.appendChild(e);
				if (ic.m_nItemCount>0)
				{
					e.setAttribute("Count", String.valueOf(ic.m_nItemCount));
				}
				
				Enumeration enumopt = ic.m_tabOptions.keys();
				try
				{
					String cs = (String)enumopt.nextElement();
					while (cs != null)
					{
						Element eOpt = root.createElement("SubItem");
						eOpt.setAttribute("Name", cs);
						e.appendChild(eOpt);
						Integer i = ic.m_tabOptions.get(cs) ;
						eOpt.setAttribute("Count", i.toString());
						cs = (String)enumopt.nextElement();
					}
				}
				catch (NoSuchElementException exp)
				{
					//exp.printStackTrace();
				}
				ic = (CItemCounter)GetNextCount(enumere);
			}
		}
		if (m_tabSQLCommands != null)
		{
			Element eProperties = root.createElement("Category");
			eProperties.setAttribute("Name", "SQLCommands");
			eItemCount.appendChild(eProperties);
			Enumeration enumere = m_tabSQLCommands.elements() ;
			CItemCounter ic = (CItemCounter)GetNextCount(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Item");
				e.setAttribute("Name", ic.m_ItemName);
				eProperties.appendChild(e);
				if (ic.m_nItemCount>0)
				{
					e.setAttribute("Count", String.valueOf(ic.m_nItemCount));
				}
				
				Enumeration enumopt = ic.m_tabOptions.keys();
				try
				{
					String cs = (String)enumopt.nextElement();
					while (cs != null)
					{
						Element eOpt = root.createElement("SubItem");
						eOpt.setAttribute("Name", cs);
						e.appendChild(eOpt);
						Integer i = ic.m_tabOptions.get(cs) ;
						eOpt.setAttribute("Count", i.toString());
						cs = (String)enumopt.nextElement();
					}
				}
				catch (NoSuchElementException exp)
				{
					//exp.printStackTrace();
				}
				ic = (CItemCounter)GetNextCount(enumere);
			}
		}
		if (m_tabSQLTableAccess != null)
		{
			Element eProperties = root.createElement("Category");
			eProperties.setAttribute("Name", "SQLTableAccess");
			eItemCount.appendChild(eProperties);
			Enumeration enumere = m_tabSQLTableAccess.elements() ;
			CSQLTableAccessCounter tc = (CSQLTableAccessCounter)GetNextCount(enumere);
			while (tc != null)
			{
				Element e = root.createElement("Item");
				e.setAttribute("Name", tc.m_csTableName);
				eProperties.appendChild(e);
				e.setAttribute("DELETE", String.valueOf(tc.m_nbDelete));
				e.setAttribute("SELECT", String.valueOf(tc.m_nbSelect));
				e.setAttribute("UPDATE", String.valueOf(tc.m_nbUpdate));
				e.setAttribute("INSERT", String.valueOf(tc.m_nbInsert));
				e.setAttribute("CURSOR", String.valueOf(tc.m_nbSelectCursor));
				Enumeration enumereProgram = tc.m_tabSQLTableAccesProgram.elements() ;
				CSQLTableAccessCounterProgram tcProgram = (CSQLTableAccessCounterProgram)GetNextCount(enumereProgram);
				while (tcProgram != null)
				{
					Element eProgram = root.createElement("Program");
					eProgram.setAttribute("Name", tcProgram.m_csProgramName);
					e.appendChild(eProgram);
					eProgram.setAttribute("DELETE", String.valueOf(tcProgram.m_nbDelete));
					eProgram.setAttribute("SELECT", String.valueOf(tcProgram.m_nbSelect));
					eProgram.setAttribute("UPDATE", String.valueOf(tcProgram.m_nbUpdate));
					eProgram.setAttribute("INSERT", String.valueOf(tcProgram.m_nbInsert));
					eProgram.setAttribute("CURSOR", String.valueOf(tcProgram.m_nbSelectCursor));
					tcProgram = (CSQLTableAccessCounterProgram)GetNextCount(enumereProgram);
				}
				tc = (CSQLTableAccessCounter)GetNextCount(enumere);
			}
		}
		if (m_tabCopyForPrograms != null)
		{
			Element eCopy = root.createElement("CopyForPrograms");
			eItemCount.appendChild(eCopy);
			Enumeration enumere = m_tabCopyForPrograms.elements() ;
			CDepCounter ic = GetNextDep(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Copy");
				e.setAttribute("Name", ic.m_ItemName);
				eCopy.appendChild(e);
				for (int i=0; i<ic.m_arrDeps.size();i++)
				{
					String cs = ic.m_arrDeps.elementAt(i);
					Element eOpt = root.createElement("Program");
					eOpt.setAttribute("Name", cs);
					e.appendChild(eOpt);
				}
				ic = GetNextDep(enumere);
			}
		}
		if (m_tabDeepCopyForPrograms != null)
		{
			Element eCopy = root.createElement("DeepCopyForPrograms");
			eItemCount.appendChild(eCopy);
			Enumeration enumere = m_tabDeepCopyForPrograms.elements() ;
			CDepCounter ic = GetNextDep(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Copy");
				e.setAttribute("Name", ic.m_ItemName);
				eCopy.appendChild(e);
				for (int i=0; i<ic.m_arrDeps.size();i++)
				{
					String cs = ic.m_arrDeps.elementAt(i);
					Element eOpt = root.createElement("Program");
					eOpt.setAttribute("Name", cs);
					e.appendChild(eOpt);
				}
				ic = GetNextDep(enumere);
			}
		}
		if (m_tabMissingCopy != null)
		{
			Element eCopy = root.createElement("MissingCopy");
			eItemCount.appendChild(eCopy);
			Enumeration enumere = m_tabMissingCopy.elements() ;
			CDepCounter ic = GetNextDep(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Copy");
				e.setAttribute("Name", ic.m_ItemName);
				eCopy.appendChild(e);
				for (int i=0; i<ic.m_arrDeps.size();i++)
				{
					String cs = ic.m_arrDeps.elementAt(i);
					Element eOpt = root.createElement("Program");
					eOpt.setAttribute("Name", cs);
					e.appendChild(eOpt);
				}
				ic = GetNextDep(enumere);
			}
		}
		if (m_tabProgramsUsingCopy != null)
		{
			Element eCopy = root.createElement("ProgramUsingCopy");
			eItemCount.appendChild(eCopy);
			Enumeration enumere = m_tabProgramsUsingCopy.elements() ;
			CDepCounter ic = GetNextDep(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Program");
				e.setAttribute("Name", ic.m_ItemName);
				eCopy.appendChild(e);
				for (int i=0; i<ic.m_arrDeps.size();i++)
				{
					String cs = ic.m_arrDeps.elementAt(i);
					Element eOpt = root.createElement("Copy");
					eOpt.setAttribute("Name", cs);
					e.appendChild(eOpt);
				}
				ic = GetNextDep(enumere);
			}
		}
		if (m_tabSubProgramCalls != null)
		{
			Element eCopy = root.createElement("SubProgramCalls");
			eItemCount.appendChild(eCopy);
			Enumeration enumere = m_tabSubProgramCalls.elements() ;
			CDepCounter ic = GetNextDep(enumere);
			while (ic != null)
			{
				Element e = root.createElement("Program");
				e.setAttribute("Name", ic.m_ItemName);
				eCopy.appendChild(e);
				for (int i=0; i<ic.m_arrDeps.size();i++)
				{
					String cs = ic.m_arrDeps.elementAt(i);
					Element eOpt = root.createElement("SubProgram");
					eOpt.setAttribute("Name", cs);
//					int n = ic.m_tabCount.get(cs) ;
//					eOpt.setAttribute("Count", ""+n);
					e.appendChild(eOpt);
				}
				ic = GetNextDep(enumere);
			}
		}
		if (m_tabProgramCalled != null)
		{
			Element eCopy = root.createElement("ProgramCalled");
			eItemCount.appendChild(eCopy);
			Enumeration enumere = m_tabProgramCalled.elements() ;
			CDepCounter ic = GetNextDep(enumere);
			while (ic != null)
			{
				Element e = root.createElement("SubProgram");
				e.setAttribute("Name", ic.m_ItemName);
				eCopy.appendChild(e);
				for (int i=0; i<ic.m_arrDeps.size();i++)
				{
					String cs = ic.m_arrDeps.elementAt(i);
					Element eOpt = root.createElement("Program");
					eOpt.setAttribute("Name", cs);
//					int n = ic.m_tabCount.get(cs) ;
//					eOpt.setAttribute("Count", ""+n);
					e.appendChild(eOpt);
				}
				ic = GetNextDep(enumere);
			}
		}
		if (m_tabMissingSubProgram != null)
		{
			Element eCopy = root.createElement("MissingCalls");
			eItemCount.appendChild(eCopy);
			Enumeration enumere = m_tabMissingSubProgram.elements() ;
			CDepCounter ic = GetNextDep(enumere);
			while (ic != null)
			{
				Element e = root.createElement("SubProgram");
				e.setAttribute("Name", ic.m_ItemName);
				eCopy.appendChild(e);
				for (int i=0; i<ic.m_arrDeps.size();i++)
				{
					String cs = ic.m_arrDeps.elementAt(i);
					Element eOpt = root.createElement("Program");
					eOpt.setAttribute("Name", cs);
//					int n = ic.m_tabCount.get(cs) ;
//					eOpt.setAttribute("Count", ""+n);
					e.appendChild(eOpt);
				}
				ic = GetNextDep(enumere);
			}
		}
		if (m_arrProgramToRewrite.size()>0)
		{
			Element eRew = root.createElement("ProgramsToRewrite");
			eItemCount.appendChild(eRew);
			for (int i=0; i<m_arrProgramToRewrite.size(); i++)
			{
				Element e = root.createElement("Program");
				e.setAttribute("Name", m_arrProgramToRewrite.elementAt(i));
				e.setAttribute("Line", ""+m_arrProgramLinesToRewrite.elementAt(i));
				e.setAttribute("Reason", m_arrProgramToRewriteReason.elementAt(i));
				eRew.appendChild(e);
			}
		}
		return eItemCount ;
	}
	
	protected CItemCounter GetIC(Hashtable<String, CItemCounter> tab, String cs)
	{
		CItemCounter ic = tab.get(cs);
		if (ic == null)
		{
			ic = new CItemCounter();
			ic.m_ItemName = cs ;
			tab.put(cs, ic);
		}
		return ic ;
	}
	public void CountCobolFile()
	{
		CItemCounter ic = GetIC(m_tabProperties, NB_COBOL_FILES);
		ic.m_nItemCount ++ ;
	}
	public void CountBMSFile()
	{
		CItemCounter ic = GetIC(m_tabProperties, NB_BMS_FILES);
		ic.m_nItemCount ++ ;
	}
	public void CountDataTable(String table)
	{
		CItemCounter ic1 = GetIC(m_tabDataTables, table) ;
		if (ic1.m_nItemCount == 0)
		{
			CItemCounter ic = GetIC(m_tabProperties, NB_DATA_TABLES);
			ic.m_nItemCount ++ ;
		}
		ic1.m_nItemCount ++ ;
	}
	public void CountSQLCommand(String cmd)
	{
		CItemCounter ic = GetIC(m_tabSQLCommands, cmd);
		ic.m_nItemCount ++ ;
	}
	public void CountSQLTableAccess(String cmd, String table, String programName)
	{
		CSQLTableAccessCounter tc = m_tabSQLTableAccess.get(table);
		if (tc == null)
		{
			tc = new CSQLTableAccessCounter() ;
			tc.m_csTableName = table ;
			m_tabSQLTableAccess.put(table, tc) ;
		}
		CSQLTableAccessCounterProgram tcProgram = tc.m_tabSQLTableAccesProgram.get(programName);
		if (tcProgram == null)
		{
			tcProgram = new CSQLTableAccessCounterProgram() ;
			tcProgram.m_csProgramName = programName ;
			tc.m_tabSQLTableAccesProgram.put(programName, tcProgram) ;
		}
		if (cmd.equals("SELECT"))
		{
			tc.m_nbSelect ++ ;
			tcProgram.m_nbSelect ++ ;
		}
		else if (cmd.equals("SELECT_CURSOR"))
		{
			tc.m_nbSelectCursor ++ ;
			tcProgram.m_nbSelectCursor ++ ;
		}
		else if (cmd.equals("UPDATE"))
		{
			tc.m_nbUpdate ++ ;
			tcProgram.m_nbUpdate ++ ;
		}
		else if (cmd.equals("DELETE"))
		{
			tc.m_nbDelete ++ ;
			tcProgram.m_nbDelete ++ ;
		}
		else if (cmd.equals("INSERT"))
		{
			tc.m_nbInsert ++ ;
			tcProgram.m_nbInsert ++ ;
		}
		else
		{
			int n=0 ; 
		}
	}
	protected Hashtable<String, CSQLTableAccessCounter> m_tabSQLTableAccess = new Hashtable<String, CSQLTableAccessCounter>() ;
	protected class CSQLTableAccessCounter
	{
		public String m_csTableName = "" ;
		public int m_nbSelect = 0;
		public int m_nbSelectCursor = 0 ;
		public int m_nbInsert = 0;
		public int m_nbDelete = 0;
		public int m_nbUpdate = 0 ;
		public Hashtable<String, CSQLTableAccessCounterProgram> m_tabSQLTableAccesProgram = new Hashtable<String, CSQLTableAccessCounterProgram>();
	}
	protected class CSQLTableAccessCounterProgram
	{
		public String m_csProgramName = "" ;
		public int m_nbSelect = 0;
		public int m_nbSelectCursor = 0 ;
		public int m_nbInsert = 0;
		public int m_nbDelete = 0;
		public int m_nbUpdate = 0 ; 
	}
	public void CountCopyFile()
	{
		CItemCounter ic = GetIC(m_tabProperties, NB_COPY_FILES);
		ic.m_nItemCount ++ ;
	}
	public void CountLines(int nbLines, int nbLinesComments, int nbLinesCode)
	{
		CItemCounter ic = GetIC(m_tabProperties, NB_LINES);
		SetMinMaxValue(nbLines, ic) ;

		ic = GetIC(m_tabProperties, NB_LINES_COMMENTS);
		SetMinMaxValue(nbLinesComments, ic) ;

		ic = GetIC(m_tabProperties, NB_LINES_CODE);
		SetMinMaxValue(nbLinesCode, ic) ;
	}
	
	protected void SetMinMaxValue(int val, CItemCounter ic)
	{
		ic.m_nItemCount ++ ;
		if (ic.m_nItemMin == 0 || ic.m_nItemMin>val)
		{
			ic.m_nItemMin = val ;
		}
		if (ic.m_nItemMax == 0 || ic.m_nItemMax<val)
		{
			ic.m_nItemMax = val ;
		}
		ic.m_nItemTotal += val ;
	}
	
	public void CountCobolVerb(String vb)
	{
		if (!vb.equals(""))
		{
			CItemCounter ic = GetIC(m_tabCobolVerbs, vb) ;
			ic.m_nItemCount ++ ;
		}
	}
	public void CountCobolVerbOptions(String vb, String option)
	{
		if (!vb.equals("") && !option.equals(""))
		{
			CItemCounter ic = GetIC(m_tabCobolVerbs, vb) ;
			Integer i = ic.m_tabOptions.get(option);
			Integer i2 ;
			if (i == null)
			{
				i2 = new Integer(1) ;
			}
			else
			{
				i2 = new Integer(i.intValue()+1) ;	
			}
			ic.m_tabOptions.put(option, i2);
		}
	}
	public void CountCICSCommand(String vb)
	{
		if (!vb.equals(""))
		{
			CItemCounter ic = GetIC(m_tabCICSCommands, vb) ;
			ic.m_nItemCount ++ ;
		}
	}
	public void CountCICSCommandOptions(String vb, String option)
	{
		if (!vb.equals("") && !option.equals(""))
		{
			CItemCounter ic = GetIC(m_tabCICSCommands, vb) ;
			Integer i = ic.m_tabOptions.get(option);
			Integer i2 ;
			if (i == null)
			{
				i2 = new Integer(1) ;
			}
			else
			{
				i2 = new Integer(i.intValue()+1) ;	
			}
			ic.m_tabOptions.put(option, i2);
		}
	}

	public void RegisterCopy(String programName, String copyName)
	{
		// register program for copy
		CDepCounter dep = m_tabCopyForPrograms.get(copyName);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = copyName ;
			m_tabCopyForPrograms.put(copyName, dep);
		}
		if (dep.m_arrDeps.contains(programName))
		{
			int n = dep.m_tabCount.get(programName);
			dep.m_tabCount.put(programName, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(programName) ;
			dep.m_tabCount.put(programName, 1) ;
		}

		//register COPY for PROGRAM
		dep = m_tabProgramsUsingCopy.get(programName);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = programName ;
			m_tabProgramsUsingCopy.put(programName, dep);
		}
		if (dep.m_arrDeps.contains(copyName))
		{
			int n = dep.m_tabCount.get(copyName);
			dep.m_tabCount.put(copyName, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(copyName) ;
			dep.m_tabCount.put(copyName, 1) ;
		}
	}
	public void RegisterDeepCopy(String programName, String copyName, String csNewCopyReference)
	{
		// register program for copy
		CDepCounter dep = m_tabDeepCopyForPrograms.get(copyName);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = copyName ;
			m_tabDeepCopyForPrograms.put(copyName, dep);
		}
		if (dep.m_arrDeps.contains(programName))
		{
			int n = dep.m_tabCount.get(programName);
			dep.m_tabCount.put(programName, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(programName) ;
			dep.m_tabCount.put(programName, 1) ;
		}

		//register COPY for PROGRAM
		dep = m_tabProgramsUsingCopy.get(programName);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = programName ;
			m_tabProgramsUsingCopy.put(programName, dep);
		}
		if (dep.m_arrDeps.contains(copyName))
		{
			int n = dep.m_tabCount.get(copyName);
			dep.m_tabCount.put(copyName, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(copyName) ;
			dep.m_tabCount.put(copyName, 1) ;
		}
	}
	public void RegisterMissingCopy(String programName, String copyName)
	{
		CDepCounter dep = m_tabMissingCopy.get(copyName);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = copyName ;
			m_tabMissingCopy.put(copyName, dep);
		}
		if (dep.m_arrDeps.contains(programName))
		{
			int n = dep.m_tabCount.get(programName);
			dep.m_tabCount.put(programName, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(programName) ;
			dep.m_tabCount.put(programName, 1) ;
		}
	}
	public void RegisterMissingSubProgram(String programName, String prg)
	{
		CDepCounter dep = m_tabMissingSubProgram.get(prg);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = prg ;
			m_tabMissingSubProgram.put(prg, dep);
		}
		if (dep.m_arrDeps.contains(programName))
		{
			int n = dep.m_tabCount.get(programName);
			dep.m_tabCount.put(programName, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(programName) ;
			dep.m_tabCount.put(programName, 1) ;
		}
	}
	public void RegisterSubProgram(String programName, String prg)
	{
		if (prg.equals("") || programName.equals(""))
		{
			return ;
		}
		// register program for copy
		CDepCounter dep = m_tabProgramCalled.get(prg);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = prg ;
			m_tabProgramCalled.put(prg, dep);
		}
		if (dep.m_arrDeps.contains(programName))
		{
			int n = dep.m_tabCount.get(programName);
			dep.m_tabCount.put(programName, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(programName) ;
			dep.m_tabCount.put(programName, 1) ;
		}

		//register COPY for PROGRAM
		dep = m_tabSubProgramCalls.get(programName);
		if (dep == null)
		{
			dep = new CDepCounter();
			dep.m_ItemName = programName ;
			m_tabSubProgramCalls.put(programName, dep);
		}
		if (dep.m_arrDeps.contains(prg))
		{
			int n = dep.m_tabCount.get(prg);
			dep.m_tabCount.put(prg, n+1) ;
		}
		else
		{
			dep.m_arrDeps.addElement(prg) ;
			dep.m_tabCount.put(prg, 1) ;
		}
	}
	
	public void RegisterProgramToRewrite(String progName, int line, String reason)
	{
		m_arrProgramLinesToRewrite.addElement(line);
		m_arrProgramToRewrite.addElement(progName);
		m_arrProgramToRewriteReason.addElement(reason);
	}
	protected StringVector m_arrProgramToRewrite = new StringVector();
	protected StringVector m_arrProgramToRewriteReason = new StringVector();
	protected IntVector m_arrProgramLinesToRewrite = new IntVector() ;
}
