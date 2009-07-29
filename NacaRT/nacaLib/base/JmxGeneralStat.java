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
/**
 * 
 */
package nacaLib.base;

import java.util.ArrayList;

import jlib.misc.BaseJmxGeneralStat;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.basePrgEnv.AsyncThreadJmxManager;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.TransThreadManager;
import nacaLib.program.CopyManager;
import nacaLib.programPool.ProgramPoolManager;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class JmxGeneralStat extends BaseJmxGeneralStat
{
	public JmxGeneralStat()
	{
		super("# GeneralStat", "# GeneralStat");
	}
	
	private static int ms_tTransaction[] = null;
	
	protected void buildDynamicMBeanInfo()
	{
		addAttribute("NbConnection_Active", getClass(), "A_NbConnection_Active", int.class);
		addAttribute("NbConnection_NonFinalized", getClass(), "A_NbConnection_NonFinalized", int.class);
		addAttribute("NbPreparedStatement_Active", getClass(), "A_NbPreparedStatement_Active", int.class);
		addAttribute("NbPreparedStatement_NonFinalized", getClass(), "A_NbPreparedStatement_NonFinalized", int.class);
		addAttribute("NbWaitDuringConnectionCreate", getClass(), "A_NbWaitDuringConnectionCreate", int.class);
		//addAttribute("NbSQLObjects", getClass(), "B_NbSQLObjects", int.class);
		//addAttribute("NbSQLObjectsReuse", getClass(), "B_NbSQLObjectsReuse", int.class);
		//addAttribute("NbSelect", getClass(), "C_NbSelect", int.class);
		//addAttribute("NbInsert", getClass(), "C_NbInsert", int.class);
		//addAttribute("NbUpdate", getClass(), "C_NbUpdate", int.class);
		//addAttribute("NbDelete", getClass(), "C_NbDelete", int.class);
		//addAttribute("NbOpenCursor", getClass(), "D_NbOpenCursor", int.class);
		//addAttribute("NbFetchCursor", getClass(), "D_NbFetchCursor", int.class);
		addAttribute("NbProgramClassLoaded", getClass(), "E_NbProgramClassLoaded", int.class);  
		addAttribute("NbCopyClassLoaded", getClass(), "E_NbCopyClassLoaded", int.class);
		addAttribute("NbProgramInstance_Stacked", getClass(), "F_NbProgramInstance_Stacked", int.class);
		addAttribute("NbProgramInstance_NonFinalized", getClass(), "F_NbProgramInstance_NonFinalized", int.class);
		//addAttribute("InternalCharBufferSize", getClass(), "G_InternalCharBufferSize", int.class);
		addAttribute("H_NbTransactions_Normal", getClass(), "H_NbTransactions_Normal", int.class);
		addAttribute("H_NbTransactions_Return", getClass(), "H_NbTransactions_Return", int.class);
		addAttribute("H_NbTransactions_Exit", getClass(), "H_NbTransactions_Exit", int.class);
		addAttribute("H_NbTransactions_StopRun", getClass(), "H_NbTransactions_StopRun", int.class);
		addAttribute("H_NbTransactions_XCtl", getClass(), "H_NbTransactions_XCtl", int.class);
		addAttribute("H_NbTransactions_Abort", getClass(), "H_NbTransactions_Abort", int.class);
		addAttribute("H_NbTransactions_Dump", getClass(), "H_NbTransactions_Dump", int.class);
		//addAttribute("I_NbSession_NonFinalized", getClass(), "I_NbSession_NonFinalized", int.class);
		addAttribute("J_NbMemoryForcedGC", getClass(), "J_NbMemoryForcedGC", int.class);
		addAttribute("J_NbRunThreadGC", getClass(), "J_NbRunThreadGC", int.class);
		//addAttribute("NbVar", getClass(), "K_NbVar", int.class);
		//addAttribute("NbVarGetAt", getClass(), "K_NbVarGetAt", int.class);
		//addAttribute("NbVarDef", getClass(), "L_NbVarDef", int.class);
		//addAttribute("NbVarDefGetAt", getClass(), "L_NbVarDefGetAt", int.class);
		//addAttribute("NbCurrentOnlineSession", getClass(), "M0_NbCurrentOnlineSession", int.class);
		//addAttribute("NbCurrentAsyncStartSession", getClass(), "M1_NbCurrentAsyncStartSession", int.class);
		//addAttribute("TimeMaxDbIO_ms", getClass(), "NDB0_TimeMaxDbIO_ms", long.class);
		//addAttribute("TimeMoyDbIO_ms", getClass(), "NDB1_TimeMoyDbIO_ms", long.class);
		//addAttribute("TimeSumDbIO_ms", getClass(), "NDB2_TimeSumDbIO_ms", long.class);
		//addAttribute("NbTotalDbIO", getClass(), "NDB3_NbTotalDbIO", int.class);		

		//addAttribute("TimeMaxTransaction_ms", getClass(), "NTR0_TimeMaxTransaction_ms", long.class);
		//addAttribute("TimeMoyTransaction_ms", getClass(), "NTR1_TimeMoyTransaction_ms", long.class);
		//addAttribute("TimeSumTransaction_ms", getClass(), "NTR2_TimeSumTransaction_ms", long.class);
		//addAttribute("NbTotalTransaction", getClass(), "NTR3_NbTotalTransaction", int.class);
		//addAttribute("NbCurrentRunningTrans", getClass(), "NTR4_NbCurrentRunningTrans", int.class);
		
		//addAttribute("TimeMoyTransExceptDbIO_ms", getClass(), "O_TimeMoyTransExceptDbIO_ms", long.class);

		//addAttribute("PermanentDataHep_TenuredGen", getClass(), "P_PermanentDataHep_TenuredGen_Mb", String.class);
		addAttribute("ParamMaxPermanentHeap_Mo", getClass(), "W_ParamMaxPermanentHeap_Mo", Integer.class);
		addAttribute("MinTransactionExecTime_s", getClass(), "Z_MinTransExecTime_s", Integer.class);

		addOperation("ShowCopyBeans", getClass(), "setShowCopyBeans");
		addOperation("ShowProgramBeans", getClass(), "setShowProgramBeans");
		addOperation("ViewEnvironments", getClass(), "setViewEnvironments");
		addOperation("HideEnvironments", getClass(), "setHideEnvironments");
		//addOperation("ResetCounter_N_O", getClass(), "setResetCounter_N_O");
		addOperation("ViewAsynchronousThreadsO", getClass(), "setViewAsynchronousThreads");
		addOperation("HideAsynchronousThreadsO", getClass(), "setHideAsynchronousThreads");

		addOperation("UnloadAllPrograms", getClass(), "setUnloadAllPrograms");		
		addOperation("UnloadAllProgramsWithGCAfterEachPrg", getClass(), "setUnloadAllProgramsWithGCAfterEachPrg");
		addOperation("RemoveAllDBConnections", getClass(), "setRemoveAllDBConnections");
		//addOperation("ShowSQLMBeans", getClass(), "setShowSQLMBeans", boolean.class);
		
		ms_tTransaction = new int[CriteriaEndRunMain.getNbIndex()];
	}

	public synchronized static void incNbProgramInstanceLoaded(int nStep)
	{
		ms_nNbProgramInstanceNonFinalized += nStep;
	}
	
//	public synchronized static void incNbVar()
//	{
//		ms_nNbVar++;
//	}
//	
//
//	public synchronized static void decNbVar()
//	{
//		ms_nNbVar--;
//	}
	
//	public synchronized static void incNbVarGetAt()
//	{
//		ms_nNbVarGetAt++;
//	}
		
//	public synchronized static void incNbVarDef()
//	{
//		ms_nNbVarDef++;
//	}
//
//	public synchronized static void decNbVarDef()
//	{
//		ms_nNbVarDef--;
//	}
//	
//	public synchronized static void incNbVarDefGetAt()
//	{
//		ms_nNbVarDefGetAt++;
//	}
//	
//	public synchronized static void decNbVarDefGetAt()
//	{
//		ms_nNbVarDefGetAt--;
//	}
	
//	public synchronized static void decNbVarGetAt()
//	{
//		ms_nNbVarGetAt--;
//	}
	
	
	public synchronized static void incProgramClassLoaded(int nStep)
	{
		ms_nNbProgramClassLoaded += nStep;
	}
	
	public synchronized static void incCopyClassLoaded(int nStep)
	{
		ms_nNbCopyClassLoaded += nStep;
	}

	public synchronized static void decNbActivePreparedStatement(int nStep)
	{
		ms_nNbActivePreparedStatement -= nStep;
	}

	public synchronized static void decNbNonFinalizedPreparedStatement(int nStep)
	{
		ms_nNbNonFinalizedPreparedStatement -= nStep;
	}
	
	public synchronized static void incNbPreparedStatement(int nStep)
	{
		ms_nNbActivePreparedStatement += nStep;
		ms_nNbNonFinalizedPreparedStatement += nStep;
	}

//	public synchronized static void incNbNonFinalizedPreparedStatement(int nStep)
//	{
//		ms_nNbNonFinalizedPreparedStatement += nStep;
//	}
	
//	public synchronized static void incNbSelect(int nStep)
//	{
//		ms_nNbSelect += nStep;
//	}
	
//	public synchronized static void incNbInsert(int nStep)
//	{
//		ms_nNbInsert += nStep;
//	}
	
//	public synchronized static void incNbDelete(int nStep)
//	{
//		ms_nNbDelete += nStep;
//	}
	
//	public synchronized static void incNbUpdate(int nStep)
//	{
//		ms_nNbUpdate += nStep;
//	}
	
//	public synchronized static void incOpenCursor(int nStep)
//	{
//		ms_nNbOpenCursor += nStep;
//	}
	
//	public synchronized static void incFetchCursor(int nStep)
//	{
//		ms_nNbFetchCursor += nStep;
//	}
	
//	public synchronized static void incInternalCharBufferSize(int nStep)
//	{
//		ms_nInternalCharBufferSize += nStep;
//	}
	
//	public synchronized static void incNbSQLObjects(int nStep)
//	{
//		ms_nNbSQLObjects += nStep;
//	}
	
//	public synchronized static void incNbSQLObjectsReuse(int nStep)
//	{
//		ms_nNbSQLObjectsReuse += nStep;
//	}
	
//	public synchronized static void startRunTransaction()
//	{
//		ms_nNbCurrentRunningTrans++;
//	}
	
	public synchronized static void endRunTransaction(CriteriaEndRunMain criteria, long lRuntimeTrans_ms, long lSumDbTimeIOForATrans_ms)
	{
		ms_tTransaction[criteria.getIndex()]++;
		//m_lSumTimeTransaction_ms += lRuntimeTrans_ms;
//		if(m_lMaxTimeTransaction_ms < lRuntimeTrans_ms)
//			m_lMaxTimeTransaction_ms = lRuntimeTrans_ms;
		//m_nNbTransaction++;
		//ms_nNbCurrentRunningTrans--;
	}
	
//	public synchronized static void incNbSession()
//	{
//		ms_nNbSessionNonFinalized++;
//	}
//	
//	public synchronized static void decNbSession()
//	{
//		ms_nNbSessionNonFinalized++;
//	}
	
	public int getE_NbProgramClassLoaded()
	{
		return ms_nNbProgramClassLoaded;
	}
	
	public int getF_NbProgramInstance_NonFinalized()
	{
		return ms_nNbProgramInstanceNonFinalized;
	}
	
	public int getF_NbProgramInstance_Stacked()
	{
		int nNbProgramStacked = 0;
		
		for(int n=0; n<ms_arrProgramPoolManager.size(); n++)
		{
			ProgramPoolManager p = ms_arrProgramPoolManager.get(n);
			nNbProgramStacked += p.getNbProgramStacked();
		}
			
		return nNbProgramStacked;
	}
	
	public int getE_NbCopyClassLoaded()  
	{
		return ms_nNbCopyClassLoaded;
	}
	
	public int getA_NbConnection_Active()
	{
		return ms_tnCounters[COUNTER_INDEX_NbActiveConnection];
	}
	
	public int getA_NbWaitDuringConnectionCreate()
	{
		return ms_tnCounters[COUNTER_INDEX_NbWaitDuringConnectionCreate];
	}
	
	public int getA_NbConnection_NonFinalized()
	{
		return ms_tnCounters[COUNTER_INDEX_NbNonFinalizedConnection];
	}
	
	public int getA_NbPreparedStatement_Active()
	{
		return ms_nNbActivePreparedStatement;
	}
	
	public int getA_NbPreparedStatement_NonFinalized()
	{
		return ms_nNbNonFinalizedPreparedStatement;
	}

//	public int getC_NbSelect()
//	{
//		return ms_nNbSelect;
//	}
	
//	public int getC_NbInsert()
//	{
//		return ms_nNbInsert;
//	}
	
//	public int getC_NbUpdate()
//	{
//		return ms_nNbUpdate;
//	}
	
//	public int getC_NbDelete()
//	{
//		return ms_nNbDelete;
//	}
	
//	public int getD_NbOpenCursor()
//	{
//		return ms_nNbOpenCursor;
//	}
	
//	public int getD_NbFetchCursor()
//	{
//		return ms_nNbFetchCursor;
//	}
	
//	public int getK_NbVar()
//	{
//		return ms_nNbVar;
//	}
	
//	public int getK_NbVarGetAt()
//	{
//		return ms_nNbVarGetAt;
//	}
	
//	public int getL_NbVarDef()
//	{
//		return ms_nNbVarDef;
//	}
//	
//	public int getL_NbVarDefGetAt()
//	{
//		return ms_nNbVarDefGetAt;
//	}
	
//	public int getG_InternalCharBufferSize()
//	{
//		return ms_nInternalCharBufferSize;
//	}
	
	public int getH_NbTransactions_Normal()
	{
		return ms_tTransaction[CriteriaEndRunMain.Normal.getIndex()];
	}
	
	public int getH_NbTransactions_Return()
	{
		return ms_tTransaction[CriteriaEndRunMain.Return.getIndex()];
	}
	
	public int getH_NbTransactions_Exit()
	{
		return ms_tTransaction[CriteriaEndRunMain.Exit.getIndex()];
	}
	
	public int getH_NbTransactions_StopRun()
	{
		return ms_tTransaction[CriteriaEndRunMain.StopRun.getIndex()];
	}
	
	public int getH_NbTransactions_XCtl()
	{
		return ms_tTransaction[CriteriaEndRunMain.XCtl.getIndex()];
	}
	
	public int getH_NbTransactions_Abort()
	{
		return ms_tTransaction[CriteriaEndRunMain.Abort.getIndex()];
	}
	
	public int getH_NbTransactions_Dump()
	{
		return ms_tTransaction[CriteriaEndRunMain.Dump.getIndex()];
	}
		
//	public int getI_NbSession_NonFinalized()
//	{
//		return ms_nNbSessionNonFinalized;
//	}
	
	public int getJ_NbMemoryForcedGC()
	{
		return ms_tnCounters[COUNTER_INDEX_NbAggressiveGC];
	}
	
	public int getJ_NbRunThreadGC()
	{
		return ms_tnCounters[COUNTER_INDEX_NbRunThreadGC];
	}
	
//	public int getB_NbSQLObjects()
//	{
//		return ms_nNbSQLObjects;
//	}
	
//	public int getB_NbSQLObjectsReuse()
//	{
//		return ms_nNbSQLObjectsReuse;
//	}
			
//	public String getP_PermanentDataHep_TenuredGen_Mb()
//	{
//		List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
//		for (MemoryPoolMXBean p: pools)
//		{
//			if(p.getType().compareTo(MemoryType.HEAP) == 0)
//			{
//				String cs = p.getName();
//				if(cs.equalsIgnoreCase("Tenured gen"))
//				{
//					MemoryUsage mem = p.getUsage();
//					cs = "Current="+mem.getUsed()/Mb + " Committed="+mem.getCommitted()/Mb;
//					return cs;
//				}				
//			}
//		}
//		return "";
//	}
	
	private final static int Mb = 1024 * 1024; 
	
		
	public void setShowCopyBeans()
	{
		ms_bShowProgramBeans = !ms_bShowProgramBeans;
		CopyManager.showBeans(ms_bShowProgramBeans);
	}
	
	public static boolean showProgramBeans()
	{
		return ms_bShowProgramBeans;
	}
	
	public void setShowProgramBeans()
	{
		ms_bShowProgramBeans = !ms_bShowProgramBeans;
		for(int n=0; n<ms_arrProgramPoolManager.size(); n++)
		{
			ProgramPoolManager p = ms_arrProgramPoolManager.get(n);
			p.setShowProgramBeans(ms_bShowProgramBeans);
		}
	}
	
	public void setUnloadAllPrograms()
	{
		for(int n=0; n<ms_arrProgramPoolManager.size(); n++)
		{
			ProgramPoolManager p = ms_arrProgramPoolManager.get(n);
			p.unloadAllPrograms(false);
		}
	}
	
	public void setUnloadAllProgramsWithGCAfterEachPrg()
	{
		for(int n=0; n<ms_arrProgramPoolManager.size(); n++)
		{
			ProgramPoolManager p = ms_arrProgramPoolManager.get(n);
			p.unloadAllPrograms(true);
		}
	}
	
	public void setRemoveAllDBConnections()
	{
		BaseResourceManager.removeAllDBConnections();
	}
	
	public void setHideEnvironments()
	{
		m_bshowTransThreadBeans = false;
		TransThreadManager.hide();
	}
	
	public void setZ_MinTransExecTime_s(Integer iMinTransExecTime_s)
	{
		ms_nMinTransExecTime_s = iMinTransExecTime_s;
	}
	
	public void setW_ParamMaxPermanentHeap_Mo(Integer iMaxPermanentHeap_Mo)
	{
		BaseResourceManager.setCurrentMaxPermanentHeap_Mo(iMaxPermanentHeap_Mo);
	}
	
	public Integer getW_ParamMaxPermanentHeap_Mo()
	{
		return BaseResourceManager.getCurrentMaxPermanentHeap_Mo();
	}

	public Integer getZ_MinTransExecTime_s()
	{
		return ms_nMinTransExecTime_s;
	}
	
	
	public void setViewEnvironments()
	{
		m_bshowTransThreadBeans = true;
		TransThreadManager.view(ms_nMinTransExecTime_s);
	}
	
	public static boolean showCopyBeans()
	{
		return ms_bShowCopyBeans;
	}
		
	public static void addProgramPoolManager(ProgramPoolManager p)
	{
		ms_arrProgramPoolManager.add(p);
	}
	
//	public static void incNbCurrentOnlineSession(int nStep)
//	{
//		ms_nNbCurrentOnlineSession += nStep;
//	}
	
//	public static void incNbCurrentAsyncStartSession(int nStep)
//	{
//		ms_nNbCurrentAsyncStartSession += nStep;
//	}
	
	
//	public int getM0_NbCurrentOnlineSession()
//	{
//		return ms_nNbCurrentOnlineSession;
//	}
	
//	public int getM1_NbCurrentAsyncStartSession()
//	{
//		return ms_nNbCurrentAsyncStartSession;
//	}
	
//	public static synchronized void reportDbTimeIo_ns(long lDbTimeIo_ms)
//	{
//		if(m_lMaxDbTimeIo_ms < lDbTimeIo_ms)
//			m_lMaxDbTimeIo_ms = lDbTimeIo_ms;
//		m_nNbDbIo++;
//		m_lSumDbTimeIo_ms += lDbTimeIo_ms;
//	}
	

//	public long getNDB0_TimeMaxDbIO_ms()
//	{
//		return m_lMaxDbTimeIo_ms;
//	}
	
//	public long getNDB1_TimeMoyDbIO_ms()
//	{
//		if(m_nNbDbIo != 0)
//			return m_lSumDbTimeIo_ms / m_nNbDbIo;
//		return 0L;
//	}
	
//	public long getNDB2_TimeSumDbIO_ms()
//	{
//		return m_lSumDbTimeIo_ms; 
//	}
	
//	public int getNDB3_NbTotalDbIO()
//	{
//		return m_nNbDbIo;
//	}
	
//	public long getNTR0_TimeMaxTransaction_ms()
//	{
//		return m_lMaxTimeTransaction_ms;
//	}
	
//	public long getNTR1_TimeMoyTransaction_ms()
//	{
//		if(m_nNbTransaction != 0)
//			return m_lSumTimeTransaction_ms / m_nNbTransaction;
//		return 0L;
//	}
	
//	public long getNTR2_TimeSumTransaction_ms()
//	{
//		return m_lSumTimeTransaction_ms;
//	}
	
//	public int getNTR3_NbTotalTransaction()
//	{
//		return m_nNbTransaction;
//	}
	
//	public int getNTR4_NbCurrentRunningTrans()
//	{
//		return ms_nNbCurrentRunningTrans;
//	}

//	public long getO_TimeMoyTransExceptDbIO_ms()
//	{
//		if(m_nNbTransaction != 0)
//			return (m_lSumTimeTransaction_ms - m_lSumDbTimeIo_ms) / m_nNbTransaction;
//		return 0;						
//	}
	
//	public void setResetCounter_N_O()
//	{
//		//m_lMaxDbTimeIo_ms = 0;
//		//m_nNbDbIo = 0;
//		//m_lSumDbTimeIo_ms = 0;
//		
//		//m_lMaxTimeTransaction_ms = 0;
////		m_nNbTransaction = 0;
//		//m_lSumTimeTransaction_ms = 0;
//	}
		
	public static boolean showTransThreadBeans()
	{
		return m_bshowTransThreadBeans;
	}
	
	public static boolean showAsyncThreadBeans()
	{
		return m_bshowAsyncThreadBeans;
	}
	
	public void setViewAsynchronousThreads()
	{
		m_bshowAsyncThreadBeans = true;
		AsyncThreadJmxManager.view();
	}
	
	public void setHideAsynchronousThreads()
	{
		m_bshowAsyncThreadBeans = false;
		AsyncThreadJmxManager.hide();
	}

	
	private static ArrayList<ProgramPoolManager> ms_arrProgramPoolManager = new ArrayList<ProgramPoolManager>();

	private static int ms_nNbProgramClassLoaded = 0;
	private static  int ms_nNbProgramInstanceNonFinalized = 0;
	private static  int ms_nNbCopyClassLoaded = 0;
	private static int ms_nNbActivePreparedStatement = 0;
	private static int ms_nNbNonFinalizedPreparedStatement = 0;
	//private static int ms_nNbSelect = 0;
	//private static int ms_nNbInsert = 0;
	//private static int ms_nNbUpdate = 0;
	//private static int ms_nNbDelete = 0;
	//public static int ms_nNbOpenCursor = 0;
	//public static int ms_nNbFetchCursor = 0;
	//public static int ms_nNbVar = 0;
	//public static int ms_nNbVarGetAt = 0;
	//public static int ms_nNbVarDef = 0;
	//public static int ms_nNbVarDefGetAt = 0;
	//public static int ms_nNbSQLObjects = 0;	
	//public static int ms_nNbSQLObjectsReuse = 0;
	//public static int ms_nInternalCharBufferSize = 0;
	public static boolean ms_bShowCopyBeans = false;
	public static boolean ms_bShowProgramBeans = false;
	//public static int ms_nNbSessionNonFinalized = 0;
	//public static int ms_nNbCurrentOnlineSession = 0;
	//public static int ms_nNbCurrentAsyncStartSession = 0;
	//public static long m_lMaxDbTimeIo_ms = 0;
	//public static int m_nNbDbIo = 0;
	//public static long m_lSumDbTimeIo_ms = 0;
	
	//public static long m_lMaxTimeTransaction_ms = 0;
	//public static int m_nNbTransaction = 0;
	//public static long m_lSumTimeTransaction_ms = 0;
	//public static int ms_nNbCurrentRunningTrans = 0;
	public static boolean m_bshowTransThreadBeans = false;
	private static int ms_nMinTransExecTime_s = 0;
	public static boolean m_bshowAsyncThreadBeans = false;
}
