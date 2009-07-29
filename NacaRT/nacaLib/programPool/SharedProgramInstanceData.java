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
 * Created on 28 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.programPool;


import jlib.log.Log;
import jlib.misc.*;

import nacaLib.base.CJMapObject;
import nacaLib.sqlSupport.SQLCursor;
import nacaLib.varEx.CInitialValue;
import nacaLib.varEx.EditInMap;
import nacaLib.varEx.InternalCharBuffer;
import nacaLib.varEx.VarDefBase;
import nacaLib.varEx.VarDefBuffer;
import nacaLib.varEx.*;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SharedProgramInstanceData extends CJMapObject
{
	private ArrayFixDyn<String> m_arrCursorName = null;
	private String m_csProgramName = null;
	private ArrayFixDyn<String> m_arrCopyNames = null;
	private ArrayFixDyn<String> m_arrVarName = null;	// Array of the vars' name, indexed by var def id 
	private ArrayFixDyn<CInitialValue> m_arrInitialValue = null;	// Array of the vars' name, indexed by var def id
	private ArrayFixDyn<VarDefBuffer> m_arrVarDef = new ArrayDyn<VarDefBuffer>();	
	private ArrayFixDyn<VarDefForm> m_arrVarDefForm = null;	// Array of all VarDefForm
	private InternalCharBufferCompressedBackup m_internalCharBufferCompressedBackup = null;
	
	public SharedProgramInstanceData()
	{
		int n = 0;
	}
	
	synchronized public void prepareAutoRemoval()
	{
		// Do not manager m_bCanWrite, as we are in unloading phase, and we don't care about catalog at this stage
		if(m_arrVarDef != null)
		{
			for(int n=0; n<m_arrVarDef.size(); n++)
			{
				VarDefBuffer varDef = m_arrVarDef.get(n);
				varDef.prepareAutoRemoval();
				varDef = null;
			}
			m_arrVarDef = null;
		}
		
		if(m_arrVarDefForm != null)
		{
			for(int n=0; n<m_arrVarDefForm.size(); n++)
			{
				VarDefForm v = m_arrVarDefForm.get(n);
				v.prepareAutoRemoval();
				v = null;
			}
			m_arrVarDefForm = null;
		}
		
		if(m_internalCharBufferCompressedBackup != null)
		{
			m_internalCharBufferCompressedBackup.prepareAutoRemoval();
			m_internalCharBufferCompressedBackup = null;
		}
	}
	
	synchronized public VarDefBuffer getVarDef(int nId)
	{
		if(nId == VarDefBase.NULL_ID)
			return null;
		if(nId < m_arrVarDef.size())
		{
			VarDefBuffer varDef = m_arrVarDef.get(nId);
			return varDef;
		}
		return null;		
	}
		
	synchronized public void addVarDef(VarDefBuffer varDef)
	{
		m_arrVarDef.add(varDef);
	}
	
	synchronized public void addVarDefForm(VarDefForm varDefForm)
	{
		if(m_arrVarDefForm == null)
			m_arrVarDefForm = new ArrayDyn<VarDefForm>();
		m_arrVarDefForm.add(varDefForm);
	}
	
	public void saveOriginalValues(InternalCharBuffer internalCharBufferOrigin, ArrayFixDyn<EditInMap> arrEditInMap)
	{
		m_internalCharBufferCompressedBackup = new InternalCharBufferCompressedBackup(internalCharBufferOrigin);
		if(arrEditInMap != null)
		{
			int nNbEditInMap = arrEditInMap.size();
			for(int n=0; n<nNbEditInMap; n++)
			{
				EditInMap edit = arrEditInMap.get(n);
				edit.saveEditAttributesInVarDef();
			}
		}
		// The edit in mapRedefine attributes must also point to the correct value
	}
	
	public void restoreOriginalValues(InternalCharBuffer internalCharBufferDest)
	{
		// @See http://www.sourcecodesworld.com/faqs/cobol-faq-part1.asp
		/*
		10. A statically bound subprogram is called twice. What happens to working-storage variables?
		Ans: The working-storage section is allocated at the start of the run-unit and any data items with VALUE clauses are initialized to the appropriate value at the time. When the subprogram is called the second time, a working-storage items persist in their last used state. However, if the program is specified with INITIAL on the PROGRAM-ID, working-storage section is reinitialized each time the program is entered. 
		PROGRAM-ID. is INITIAL PROGRAM. Other verbs used with PROGRAM-ID are RECURSIVE and COMMON. 
		*/
		// Do not apply values when a program is run for the 2nd time. 
		// This should be done for 1st CICS program 
		internalCharBufferDest.copyFrom(m_internalCharBufferCompressedBackup);
	}
	
	public void restoreOriginalEdits(ArrayFixDyn<EditInMap> arrEditInMap)
	{
		// Do not alter content of this
		if(arrEditInMap != null)
		{
			int nNbEditInMap = arrEditInMap.size();
			for(int n=0; n<nNbEditInMap; n++)
			{
				EditInMap edit = arrEditInMap.get(n);
				edit.restoreEditAttributesInVarDef();
			}
		}
	}
	
//	synchronized public void saveStat(PooledProgramInstanceStat pooledProgramInstanceStat)
//	{
//		if(pooledProgramInstanceStat != null)
//		{
//			pooledProgramInstanceStat.m_nNbVarDef = 0;
//			pooledProgramInstanceStat.m_nNbVarDefForm = 0;
//			pooledProgramInstanceStat.m_nNbEditAttributes = 0;
//			pooledProgramInstanceStat.m_nBufferSize = 0;
//			
//			if(m_arrVarDef != null)
//				pooledProgramInstanceStat.m_nNbVarDef = m_arrVarDef.size(); 
//			if(m_arrVarDefForm != null)
//				pooledProgramInstanceStat.m_nNbVarDefForm = m_arrVarDefForm.size(); 
//			if(m_arrVarDef != null)
//				pooledProgramInstanceStat.m_nBufferSize = m_internalCharBufferOriginal.getBufferSize();
//		}
//	}
	
	
	
	synchronized public void addCopy(String csCopyName)
	{
		if(m_arrCopyNames == null)
			m_arrCopyNames = new ArrayDyn<String>();
		m_arrCopyNames.add(csCopyName);
	}
	
	synchronized public int getNbCopy()
	{		
		if(m_arrCopyNames == null)
			return 0;
		return m_arrCopyNames.size();
	}
	
	synchronized public String getCopy(int n)
	{
		if(m_arrCopyNames != null && n < m_arrCopyNames.size())
			return m_arrCopyNames.get(n);
		return "";
	}
	
	synchronized public void compress()
	{
		m_arrInitialValue = null;	// No more initial values
		
		if(m_arrVarName != null)
		{		
			int nSize = m_arrVarName.size();
			String arr[] = new String[nSize];
			m_arrVarName.transferInto(arr);
			ArrayFix<String> arrVarDefFix = new ArrayFix<String>(arr);
			m_arrVarName = arrVarDefFix;	// replace by a fix one (uning less memory)
		}
		
		if(m_arrVarDef != null)
		{		
			int nSize = m_arrVarDef.size();
			VarDefBuffer arr[] = new VarDefBuffer[nSize];
			m_arrVarDef.transferInto(arr);
			ArrayFix<VarDefBuffer> arrVarDefFix = new ArrayFix<VarDefBuffer>(arr);
			m_arrVarDef = arrVarDefFix;	// replace by a fix one (uning less memory)
		}
		
		if(m_arrVarDefForm != null)
		{		
			int nSize = m_arrVarDefForm.size();
			VarDefForm arr[] = new VarDefForm[nSize];
			m_arrVarDefForm.transferInto(arr);
			ArrayFix<VarDefForm> arrVarDefFormFix = new ArrayFix<VarDefForm>(arr);
			m_arrVarDefForm = arrVarDefFormFix;	// replace by a fix one (uning less memory)
		}

		if(m_arrCopyNames != null)
		{		
			int nSize = m_arrCopyNames.size();
			String arr[] = new String[nSize];
			m_arrCopyNames.transferInto(arr);
			ArrayFix<String> arrFix = new ArrayFix<String>(arr);
			m_arrCopyNames = arrFix;	// replace by a fix one (uning less memory)
		}
		
		if(m_arrCursorName != null)
		{
			int nSize = m_arrCursorName.size();
			String arr[] = new String[nSize];
			m_arrCursorName.transferInto(arr);
			ArrayFix<String> arrFix = new ArrayFix<String>(arr);
			m_arrCursorName = arrFix;	// replace by a fix one (uning less memory)
		}
	}
	
//	public void serialize(String csVarDefCatalogueSerilizationPath, String csFileName)
//	{
//		String csFullFileName = csVarDefCatalogueSerilizationPath + csFileName;
//		FileOutputStream fos = null;
//		try
//		{
//			fos = new FileOutputStream(csFullFileName);
//			ObjectOutputStream out = new ObjectOutputStream(fos);
//			serialize(out);
//			out.close();			
//		} 
//		catch (FileNotFoundException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//		
//	private void serialize(ObjectOutputStream out)  throws IOException
//	{
//		Hashtable<VarDefBase, Integer> hashVarDefById = new Hashtable<VarDefBase, Integer>();
//		out.writeInt(1);
//		if(m_arrVarDef != null)
//			out.writeInt(m_arrVarDef.size());
//		else
//			out.writeInt(0);
//		
//		// Serialized object themselves; used for correct creation at deserialization time
//		for(int nId=0; nId<m_arrVarDef.size(); nId++)
//		{
//			VarDefBase varDef = m_arrVarDef.get(nId);
//			out.writeObject(varDef);
//			hashVarDefById.put(m_arrVarDef.get(nId), nId);
//		}
//		
//		// Serialize object details
//		for(int nId=0; nId<m_arrVarDef.size(); nId++)
//		{
//			VarDefBuffer varDefBuffer = m_arrVarDef.get(nId);
//			varDefBuffer.serializeDetails(out, hashVarDefById, new Integer(nId));
//		}
//	}
//	
//	public boolean deserialize(String csVarDefCatalogueSerilizationPath, String csFileName)
//	{
//		String csFullFileName = csVarDefCatalogueSerilizationPath + csFileName;
//		try
//		{
//			FileInputStream fis = new FileInputStream(csFullFileName);
//			ObjectInputStream in = new ObjectInputStream(fis);
//			int nVersion = in.readInt();
//			if(nVersion == 1)
//			{
//				// Create objects
//				int nNbVarDef = in.readInt();
//				for(int nId=0; nId<nNbVarDef; nId++)
//				{
//					VarDefBuffer varDef = (VarDefBuffer)in.readObject(); 
//					m_arrVarDef.add(varDef);		
//				}
//				
//				// Read details
//				for(int nId=0; nId<nNbVarDef; nId++)
//				{
//					VarDefBuffer varDefBuffer = m_arrVarDef.get(nId);
//					varDefBuffer.deserializeDetails(in, m_arrVarDef, new Integer(nId));
//				}				
//				
//			}
//			
//			in.close();
//			return true;
//		} 
//		catch (FileNotFoundException e)
//		{
//			// No serialized file
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (ClassNotFoundException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	public void saveCursorName(String csCursorName)
	{
		if(m_arrCursorName == null)
			m_arrCursorName = new ArrayDyn<String>();
		m_arrCursorName.add(csCursorName);
	}
	
	public void restoreCursorNames(ArrayFixDyn<SQLCursor> arrCursor)
	{
		if(arrCursor != null && m_arrCursorName != null)
		{
			int nNbCursor = m_arrCursorName.size();
			if(nNbCursor == arrCursor.size())
			{
				for(int n=0; n<nNbCursor; n++)
				{
					String csName = m_arrCursorName.get(n);
					SQLCursor cursor = arrCursor.get(n);
					cursor.setName(m_csProgramName, csName);			
				}
			}
			else
			{
				Log.logCritical("Pb during cursor name restoration: Qty of cursor doesn't match qty defined");
			}
		}
	}
	
	public void setProgramName(String csProgramName)
	{
		m_csProgramName = csProgramName;
	}
	
	public String getProgramName()
	{
		return m_csProgramName; 
	}
	
	public int getNbCursor()
	{
		if(m_arrCursorName != null)
			return m_arrCursorName.size();
		return 0;
	}
	
	public int getBufferSize()
	{
		if(m_internalCharBufferCompressedBackup != null)
			return m_internalCharBufferCompressedBackup.getBufferSize();
		return 0;		
	}
	
	public int getNbVarDef()
	{
		if(m_arrVarDef != null)
			return m_arrVarDef.size();
		return 0;
	}
	
	public int getNbVarDefForm()
	{
		if(m_arrVarDefForm != null)
			return m_arrVarDefForm.size();
		return 0;
	}

	public String getFormName(int n)
	{
		VarDefForm varDef = m_arrVarDefForm.get(n);
		if(varDef != null)
		{
			String csName = varDef.getFullName(this).toUpperCase(); 
			int nPosSep = csName.indexOf('.');
			if(nPosSep != -1)
				return csName.substring(0, nPosSep);
			return csName;
		}
		return null;
	}
	
	public void setVarFullName(int nId, String csFullName)
	{
		if(m_arrVarName == null)
			m_arrVarName = new VectorDyn<String>();
		if(nId+1 > m_arrVarName.size())
			m_arrVarName.setSize(nId+1);
		m_arrVarName.set(nId, csFullName);
		//m_arrVarName.add(csFullName);
	}
	
	public String getVarFullName(int nId)
	{
		if(m_arrVarName != null && nId < m_arrVarName.size())
		{
			return m_arrVarName.get(nId);
		}
		return null;
	}
	
	public void setInitialValue(int nId, CInitialValue initialValue)
	{
		if(m_arrInitialValue == null)
			m_arrInitialValue = new VectorDyn<CInitialValue>();
		if(nId+1 > m_arrInitialValue.size())
			m_arrInitialValue.setSize(nId+1);
		m_arrInitialValue.set(nId, initialValue);		
	}
	
	public CInitialValue getInitialValue(int nId)
	{
		if(m_arrInitialValue != null)
			return m_arrInitialValue.get(nId);
		return null;
	}

	public String dumpAll()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("ProgramName="+m_csProgramName);

		sb.append("\r\nm_arrCursorName:\r\n");
		if(m_arrCursorName != null)
		{
			for(int n=0; n<m_arrCursorName.size(); n++)
			{
				sb.append(m_arrCursorName.get(n)+"\r\n");
			}
		}
		
		sb.append("\r\nm_arrCopyNames:\r\n");
		if(m_arrCopyNames != null)
		{
			for(int n=0; n<m_arrCopyNames.size(); n++)
			{
				sb.append(m_arrCopyNames.get(n)+"\r\n");
			}
		}
		
		sb.append("\r\nm_arrVarName:\r\n");
		if(m_arrVarName != null)
		{
			for(int n=0; n<m_arrVarName.size(); n++)
			{
				sb.append(m_arrVarName.get(n)+"\r\n");
			}
		}
		
		sb.append(":m_arrInitialValue:\r\n");
		if(m_arrInitialValue != null)
		{
			for(int n=0; n<m_arrInitialValue.size(); n++)
			{
				//System.out.println(n);
				if(m_arrInitialValue.get(n) != null)
					sb.append(m_arrInitialValue.get(n).toString()+"\r\n");
			}
		}
		
		sb.append("\r\nm_arrVarDef:\r\n");
		if(m_arrVarDef != null)
		{
			for(int n=0; n<m_arrVarDef.size(); n++)
			{
				sb.append(m_arrVarDef.get(n).toString()+"\r\n");
			}
		}

		sb.append("\r\nm_arrVarDefForm:\r\n");
		if(m_arrVarDefForm != null)
		{
			for(int n=0; n<m_arrVarDefForm.size(); n++)
			{
				sb.append(m_arrVarDefForm.get(n).toString()+"\r\n");
			}
		}
		
		sb.append("\r\nm_internalCharBufferCompressedBackup:\r\n");
		if(m_internalCharBufferCompressedBackup != null)
		{
			sb.append("length="+m_internalCharBufferCompressedBackup.getBufferSize()+"\r\n");
		}
				
		return sb.toString();		
	}
}
