/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.program.Paragraph;
import nacaLib.sqlSupport.SQL;
import nacaLib.sqlSupport.CSQLStatus;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;
import nacaLib.sqlSupport.SQLCursor;
import idea.onlinePrgEnv.OnlineProgram;
import jlib.log.Log;
import jlib.misc.DataFileWrite;

public class TestSQLDump extends OnlineProgram
{
	SQL m_sql = null;
	SQLCursor m_cur = null;
	DataFileWrite m_successWriter = null;
	DataFileWrite m_failureWriter = null;
	int m_nNbSuccess = 0;
	int m_nNbFailures = 0;
	DataSection sqlcursorsection = declare.cursorSection() ;
	SQLCursor eb2000= declare.cursor() ;
	SQLCursor cintamo= declare.cursor() ;
	SQLCursor ebc000= declare.cursor() ;
	SQLCursor cur_Profparul3= declare.cursor() ;
	SQLCursor cur_Cdparul3l5= declare.cursor() ;
	SQLCursor camo6= declare.cursor() ;
	SQLCursor camo61= declare.cursor() ;
	SQLCursor curgtlig= declare.cursor() ;
	SQLCursor cnoseq= declare.cursor() ;
	SQLCursor cmvtrej= declare.cursor() ;
	SQLCursor crejets= declare.cursor() ;
	SQLCursor cmvrec= declare.cursor() ;
	SQLCursor cuavs= declare.cursor() ;
	SQLCursor cur_Trederr= declare.cursor() ;
	SQLCursor cterror= declare.cursor() ;
	SQLCursor cterr= declare.cursor() ;
	SQLCursor ctinsig= declare.cursor() ;
	SQLCursor cur_Tmotdou= declare.cursor() ;
	SQLCursor c1= declare.cursor() ;
	SQLCursor c2= declare.cursor() ;
	SQLCursor cint= declare.cursor() ;
	SQLCursor czone= declare.cursor() ;
	SQLCursor cseqred= declare.cursor() ;
	SQLCursor czs= declare.cursor() ;
	SQLCursor tgzs= declare.cursor() ;
	SQLCursor cmvtprec= declare.cursor() ;
	SQLCursor cthistdt= declare.cursor() ;
	SQLCursor cthistbe= declare.cursor() ;
	SQLCursor chistop= declare.cursor() ;
	SQLCursor cur$csiret= declare.cursor() ;
	SQLCursor cur$csiret2= declare.cursor() ;
	SQLCursor cur$residentiel= declare.cursor() ;
	SQLCursor cur$residentiel2= declare.cursor() ;
	SQLCursor curblo= declare.cursor() ;
	SQLCursor curaut= declare.cursor() ;
	SQLCursor curs$poda= declare.cursor() ;
	SQLCursor csrblo= declare.cursor() ;
	SQLCursor cur$dm11= declare.cursor() ;
	SQLCursor cvalreg= declare.cursor() ;
	SQLCursor curs_Trefpar3= declare.cursor() ;
	SQLCursor curs_Trefpar4= declare.cursor() ;
	SQLCursor curs_Trefpar5= declare.cursor() ;
	SQLCursor curs_Trefpar6= declare.cursor() ;
	SQLCursor curs_Trefpar7= declare.cursor() ;
	SQLCursor curs_Trefpar8= declare.cursor() ;
	SQLCursor curstric$crprof= declare.cursor() ;
	SQLCursor curs$lar$crprof= declare.cursor() ;
	SQLCursor bloc21= declare.cursor() ;
	SQLCursor csrlig= declare.cursor() ;
	SQLCursor csrurg= declare.cursor() ;
	SQLCursor cimpblo= declare.cursor() ;
	SQLCursor cinsbl= declare.cursor() ;
	SQLCursor ctvalreg3= declare.cursor() ;
	SQLCursor ctvalreg4= declare.cursor() ;
	SQLCursor cursab= declare.cursor() ;
	SQLCursor curstmp= declare.cursor() ;
	SQLCursor csrtri= declare.cursor() ;
	SQLCursor ctdenom= declare.cursor() ;
	SQLCursor cur_Tgestid= declare.cursor() ;

	DataSection WorkingStorage = declare.workingStorageSection();

	Var varInto0 = declare.level(1).picX(10).var();
	Var varInto1 = declare.level(1).picX(10).var();
	Var varInto2 = declare.level(1).picX(10).var();
	Var varInto3 = declare.level(1).picX(10).var();
	Var varInto4 = declare.level(1).picX(10).var();
	Var varInto5 = declare.level(1).picX(10).var();
	Var varInto6 = declare.level(1).picX(10).var();
	Var varInto7 = declare.level(1).picX(10).var();
	Var varInto8 = declare.level(1).picX(10).var();
	Var varInto9 = declare.level(1).picX(10).var();
	Var varInto10 = declare.level(1).picX(10).var();
	Var varInto11 = declare.level(1).picX(10).var();
	Var varInto12 = declare.level(1).picX(10).var();
	Var varInto13 = declare.level(1).picX(10).var();
	Var varInto14 = declare.level(1).picX(10).var();
	Var varInto15 = declare.level(1).picX(10).var();
	Var varInto16 = declare.level(1).picX(10).var();
	Var varInto17 = declare.level(1).picX(10).var();
	Var varInto18 = declare.level(1).picX(10).var();
	Var varInto19 = declare.level(1).picX(10).var();
	Var varInto20 = declare.level(1).picX(10).var();
	Var varInto21 = declare.level(1).picX(10).var();
	Var varInto22 = declare.level(1).picX(10).var();
	Var varInto23 = declare.level(1).picX(10).var();
	Var varInto24 = declare.level(1).picX(10).var();
	Var varInto25 = declare.level(1).picX(10).var();
	Var varInto26 = declare.level(1).picX(10).var();
	Var varInto27 = declare.level(1).picX(10).var();
	Var varInto28 = declare.level(1).picX(10).var();
	Var varInto29 = declare.level(1).picX(10).var();
	Var varInto30 = declare.level(1).picX(10).var();
	Var varInto31 = declare.level(1).picX(10).var();
	Var varInto32 = declare.level(1).picX(10).var();
	Var varInto33 = declare.level(1).picX(10).var();
	Var varInto34 = declare.level(1).picX(10).var();
	Var varInto35 = declare.level(1).picX(10).var();
	Var varInto36 = declare.level(1).picX(10).var();
	Var varInto37 = declare.level(1).picX(10).var();
	Var varInto38 = declare.level(1).picX(10).var();
	Var varInto39 = declare.level(1).picX(10).var();
	Var varInto40 = declare.level(1).picX(10).var();
	Var varInto41 = declare.level(1).picX(10).var();
	Var varInto42 = declare.level(1).picX(10).var();
	Var varInto43 = declare.level(1).picX(10).var();
	Var varInto44 = declare.level(1).picX(10).var();
	Var varInto45 = declare.level(1).picX(10).var();
	Var varInto46 = declare.level(1).picX(10).var();
	Var varInto47 = declare.level(1).picX(10).var();
	Var varInto48 = declare.level(1).picX(10).var();
	Var varInto49 = declare.level(1).picX(10).var();
	Var varInto50 = declare.level(1).picX(10).var();
	Var varInto51 = declare.level(1).picX(10).var();
	Var varInto52 = declare.level(1).picX(10).var();
	Var varInto53 = declare.level(1).picX(10).var();
	Var varInto54 = declare.level(1).picX(10).var();
	Var varInto55 = declare.level(1).picX(10).var();
	Var varInto56 = declare.level(1).picX(10).var();
	Var varInto57 = declare.level(1).picX(10).var();
	Var varInto58 = declare.level(1).picX(10).var();
	Var varInto59 = declare.level(1).picX(10).var();
	Var varInto60 = declare.level(1).picX(10).var();
	Var varInto61 = declare.level(1).picX(10).var();
	Var varInto62 = declare.level(1).picX(10).var();
	Var varInto63 = declare.level(1).picX(10).var();
	Var varParam0 = declare.level(1).picX(10).var();
	Var varParam1 = declare.level(1).picX(10).var();
	Var varParam2 = declare.level(1).picX(10).var();
	Var varParam3 = declare.level(1).picX(10).var();
	Var varParam4 = declare.level(1).picX(10).var();
	Var varParam5 = declare.level(1).picX(10).var();
	Var varParam6 = declare.level(1).picX(10).var();
	Var varParam7 = declare.level(1).picX(10).var();
	Var varParam8 = declare.level(1).picX(10).var();
	Var varParam9 = declare.level(1).picX(10).var();
	Var varParam10 = declare.level(1).picX(10).var();
	Var varParam11 = declare.level(1).picX(10).var();
	Var varParam12 = declare.level(1).picX(10).var();
	Var varParam13 = declare.level(1).picX(10).var();
	Var varParam14 = declare.level(1).picX(10).var();
	Var varParam15 = declare.level(1).picX(10).var();
	Var varParam16 = declare.level(1).picX(10).var();
	Var varParam17 = declare.level(1).picX(10).var();
	Var varParam18 = declare.level(1).picX(10).var();
	Var varParam19 = declare.level(1).picX(10).var();
	Var varParam20 = declare.level(1).picX(10).var();
	Var varParam21 = declare.level(1).picX(10).var();
	Var varParam22 = declare.level(1).picX(10).var();
	Var varParam23 = declare.level(1).picX(10).var();
	Var varParam24 = declare.level(1).picX(10).var();
	Var varParam25 = declare.level(1).picX(10).var();
	Var varParam26 = declare.level(1).picX(10).var();
	Var varParam27 = declare.level(1).picX(10).var();
	Var varParam28 = declare.level(1).picX(10).var();
	Var varParam29 = declare.level(1).picX(10).var();
	Var varParam30 = declare.level(1).picX(10).var();
	Var varParam31 = declare.level(1).picX(10).var();
	Var varParam32 = declare.level(1).picX(10).var();
	Var varParam33 = declare.level(1).picX(10).var();
	Var varParam34 = declare.level(1).picX(10).var();
	Var varParam35 = declare.level(1).picX(10).var();
	Var varParam36 = declare.level(1).picX(10).var();
	Var varParam37 = declare.level(1).picX(10).var();
	Var varParam38 = declare.level(1).picX(10).var();
	Var varParam39 = declare.level(1).picX(10).var();
	Var varParam40 = declare.level(1).picX(10).var();
	Var varParam41 = declare.level(1).picX(10).var();
	Var varParam42 = declare.level(1).picX(10).var();
	Var varParam43 = declare.level(1).picX(10).var();
	Var varParam44 = declare.level(1).picX(10).var();
	Var varParam45 = declare.level(1).picX(10).var();
	Var varParam46 = declare.level(1).picX(10).var();
	Var varParam47 = declare.level(1).picX(10).var();
	Var varParam48 = declare.level(1).picX(10).var();
	Var varParam49 = declare.level(1).picX(10).var();
	Var varValue0 = declare.level(1).picX(10).var();
	Var varValue1 = declare.level(1).picX(10).var();
	Var varValue2 = declare.level(1).picX(10).var();
	Var varValue3 = declare.level(1).picX(10).var();
	Var varValue4 = declare.level(1).picX(10).var();
	Var varValue5 = declare.level(1).picX(10).var();
	Var varValue6 = declare.level(1).picX(10).var();
	Var varValue7 = declare.level(1).picX(10).var();
	Var varValue8 = declare.level(1).picX(10).var();
	Var varValue9 = declare.level(1).picX(10).var();
	Var varValue10 = declare.level(1).picX(10).var();
	Var varValue11 = declare.level(1).picX(10).var();
	Var varValue12 = declare.level(1).picX(10).var();
	Var varValue13 = declare.level(1).picX(10).var();
	Var varValue14 = declare.level(1).picX(10).var();
	Var varValue15 = declare.level(1).picX(10).var();
	Var varValue16 = declare.level(1).picX(10).var();
	Var varValue17 = declare.level(1).picX(10).var();
	Var varValue18 = declare.level(1).picX(10).var();
	Var varValue19 = declare.level(1).picX(10).var();
	Var varValue20 = declare.level(1).picX(10).var();
	Var varValue21 = declare.level(1).picX(10).var();
	Var varValue22 = declare.level(1).picX(10).var();
	Var varValue23 = declare.level(1).picX(10).var();
	Var varValue24 = declare.level(1).picX(10).var();
	Var varValue25 = declare.level(1).picX(10).var();
	Var varValue26 = declare.level(1).picX(10).var();
	Var varValue27 = declare.level(1).picX(10).var();
	Var varValue28 = declare.level(1).picX(10).var();
	Var varValue29 = declare.level(1).picX(10).var();
	Var varValue30 = declare.level(1).picX(10).var();
	Var varValue31 = declare.level(1).picX(10).var();
	Var varValue32 = declare.level(1).picX(10).var();
	Var varValue33 = declare.level(1).picX(10).var();
	Var varValue34 = declare.level(1).picX(10).var();
	Var varValue35 = declare.level(1).picX(10).var();
	Var varValue36 = declare.level(1).picX(10).var();
	Var varValue37 = declare.level(1).picX(10).var();
	Var varValue38 = declare.level(1).picX(10).var();
	Var varValue39 = declare.level(1).picX(10).var();
	Var varValue40 = declare.level(1).picX(10).var();
	Var varValue41 = declare.level(1).picX(10).var();
	Var varValue42 = declare.level(1).picX(10).var();
	Var varValue43 = declare.level(1).picX(10).var();
	Var varValue44 = declare.level(1).picX(10).var();
	Var varValue45 = declare.level(1).picX(10).var();
	Var varValue46 = declare.level(1).picX(10).var();
	Var varValue47 = declare.level(1).picX(10).var();
	Var varValue48 = declare.level(1).picX(10).var();
	Var varValue49 = declare.level(1).picX(10).var();
	Var varValue50 = declare.level(1).picX(10).var();
	Var varValue51 = declare.level(1).picX(10).var();
	Var varValue52 = declare.level(1).picX(10).var();
	Var varValue53 = declare.level(1).picX(10).var();
	Var varValue54 = declare.level(1).picX(10).var();
	Var varValue55 = declare.level(1).picX(10).var();
	Var varValue56 = declare.level(1).picX(10).var();
	Var varValue57 = declare.level(1).picX(10).var();
	Var varValue58 = declare.level(1).picX(10).var();
	Var varValue59 = declare.level(1).picX(10).var();
	Var varValue60 = declare.level(1).picX(10).var();
	Var varValue61 = declare.level(1).picX(10).var();
	Var varValue62 = declare.level(1).picX(10).var();
	Var varValue63 = declare.level(1).picX(10).var();
	Var varValue64 = declare.level(1).picX(10).var();
	Var varValue65 = declare.level(1).picX(10).var();
	Var varValue66 = declare.level(1).picX(10).var();
	Var varValue67 = declare.level(1).picX(10).var();
	Var varValue68 = declare.level(1).picX(10).var();
	Var varValue69 = declare.level(1).picX(10).var();
	Var varValue70 = declare.level(1).picX(10).var();
	Var varValue71 = declare.level(1).picX(10).var();
	Var varValue72 = declare.level(1).picX(10).var();
	Var varValue73 = declare.level(1).picX(10).var();
	Var varValue74 = declare.level(1).picX(10).var();
	Var varValue75 = declare.level(1).picX(10).var();
	Var varValue76 = declare.level(1).picX(10).var();
	Var varValue77 = declare.level(1).picX(10).var();
	Var varValue78 = declare.level(1).picX(10).var();
	Var varValue79 = declare.level(1).picX(10).var();
	Var varValue80 = declare.level(1).picX(10).var();
	Var varValue81 = declare.level(1).picX(10).var();
	Var varValue82 = declare.level(1).picX(10).var();
	Var varValue83 = declare.level(1).picX(10).var();
	Var varValue84 = declare.level(1).picX(10).var();
	Var varValue85 = declare.level(1).picX(10).var();
	Var varValue86 = declare.level(1).picX(10).var();
	Var varValue87 = declare.level(1).picX(10).var();
	Var varValue88 = declare.level(1).picX(10).var();
	Var varValue89 = declare.level(1).picX(10).var();
	Var varValue90 = declare.level(1).picX(10).var();
	Var varValue91 = declare.level(1).picX(10).var();
	Var varValue92 = declare.level(1).picX(10).var();
	Var varValue93 = declare.level(1).picX(10).var();
	Var varValue94 = declare.level(1).picX(10).var();
	Var varValue95 = declare.level(1).picX(10).var();
	Var varValue96 = declare.level(1).picX(10).var();
	Var varValue97 = declare.level(1).picX(10).var();
	Var varValue98 = declare.level(1).picX(10).var();
	Var varValue99 = declare.level(1).picX(10).var();
	Var varValue100 = declare.level(1).picX(10).var();
	Var varValue101 = declare.level(1).picX(10).var();
	Var varValue102 = declare.level(1).picX(10).var();
	Var varValue103 = declare.level(1).picX(10).var();
	Var varValue104 = declare.level(1).picX(10).var();
	Var varValue105 = declare.level(1).picX(10).var();
	Var varValue106 = declare.level(1).picX(10).var();
	Var varValue107 = declare.level(1).picX(10).var();
	Var varValue108 = declare.level(1).picX(10).var();
	Var varValue109 = declare.level(1).picX(10).var();
	Var varValue110 = declare.level(1).picX(10).var();
	Var varValue111 = declare.level(1).picX(10).var();
	Var varValue112 = declare.level(1).picX(10).var();
	Var varValue113 = declare.level(1).picX(10).var();
	Var varValue114 = declare.level(1).picX(10).var();
	Var varValue115 = declare.level(1).picX(10).var();
	Var varValue116 = declare.level(1).picX(10).var();
	Var varValue117 = declare.level(1).picX(10).var();
	Var varValue118 = declare.level(1).picX(10).var();
	Var varValue119 = declare.level(1).picX(10).var();
	Var varValue120 = declare.level(1).picX(10).var();

	public void procedureDivision()
	{
		m_successWriter = new DataFileWrite("sqlDumpSuccessResult.txt", false);
		m_successWriter.open();
		m_failureWriter = new DataFileWrite("sqlDumpFailureResult.txt", false);
		m_failureWriter.open();

		perform(F0BEB01);
		perform(F0BMAJBE);
		perform(F0BRDBDE);
		perform(F0BRDBSI);
		perform(F0BRDCIG);
		perform(F0BRDCVA);
		perform(F0BRDFPV);
		perform(F0BRDMER);
		perform(F0BRDMIG);
		perform(F0BRDMTD);
		perform(F0BRDVF1);
		perform(F0BRDVRG);
		perform(F0BRE010);
		perform(F0BRE020);
		perform(F0BRE050);
		perform(F0BZZ02);
		perform(F0BZZ03);
		perform(F0BZZ10);
		perform(F0BZZ65);
		perform(F0XMAJCI);
		perform(F0XMAJGI);
		perform(F0XMAJMI);
		perform(F0XMAJRI);
		perform(F0XMAJSI);
		perform(F0XZZ02);
		perform(F0XZZ04);
		perform(F0XZZ05);
		perform(F0XZZ10);
		perform(F0XZZ14);
		perform(F0XZZ21);
		perform(F0XZZ22);
		perform(F0XZZ24);
		perform(F0XZZ28);
		perform(F0XZZ29);
		perform(F0XZZ36);
		perform(F0XZZ37);
		
		// split
		perform(F0XZZ40);
		perform(F0XZZ41);
		perform(F0XZZ42);
		perform(F0XZZ44);
		perform(F0XZZ88);
		perform(F0XZZID);

		m_successWriter.writeRecord("Nb Success: " + m_nNbSuccess);
		m_successWriter.close();
		m_failureWriter.writeRecord("Nb Failures: " + m_nNbFailures);
		m_failureWriter.close();
		stopRun();
	}

	private void handleSqlStatus()
	{
		if(m_sql == null)
		{
			m_failureWriter.writeRecord("SQL is NULL");
			m_nNbFailures++;
			return ;
		}
		CSQLStatus status = m_sql.getDebugSQLStatus();
		int nCode = status.getSQLCode();
		String cs;
		if(nCode != 0 && nCode != 100 && nCode != 1722) // These codes are success
		{
			cs = "Failed: "+m_sql.getQuery() + "; Status="+status.getReason() + "; Code=" + status.getSQLCode();
			m_failureWriter.writeRecord(cs);
			m_nNbFailures++;
		}
		else
		{
			cs = "Success: "+m_sql.getQuery();
			m_successWriter.writeRecord(cs);
			m_nNbSuccess++;
		}
		Log.logNormal("Nb Failures="+m_nNbFailures + " Nb Success="+m_nNbSuccess);
	}

	private void handleSqlStatus(SQLCursor cur)
	{
		if(cur != null)
		{
			m_sql = cur.m_SQL;
			handleSqlStatus();
		}
		else
			m_failureWriter.writeRecord("Cursor is NULL");
	}
	private void handleExceptionCursor(Exception e)
	{
		String cs = "Failed Exception: "+e.toString();
		m_failureWriter.writeRecord(cs);
		m_nNbFailures++;
	}

	Paragraph F0BEB01= new Paragraph(this);
	public void F0BEB01() {
	
		
		// FileName: F0BEB01 (974) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'VRGS'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BEB01 (1084) :
		try
		{
			m_cur = cursorOpen(eb2000, "SELECT A.NNAT, A.NSIM, A.CDPARU, A.CLPARU, A.CVCPA, B.BLOC_SEQ, A.CODE_POSTAL, A.NUM_RUE, A.D_EFFET, A.MC, A.ADR_L2, A.ADR_L3, A.ADR_L5, A.CRPROF, A.PAR_NAT, B.ID_BLOC, A.NNATN, A.OPP_COMM, A.CATPAR, A.CNUM, A.TDVR, A.ARD, A.BP, A.NOM, A.PRENOM, A.CDN, A.C_SERV_TEL, A.PAR_DEP, A.OPP_PARUT, A.C_TYPE_CLIENT, A.FORME_ADR, A.FORME_INTIT, A.C_EQUIP_TEL, A.DDMAJO, A.DDMAJE, A.CEDEX, A.NOMV, A.DL, A.MDET, A.NAF, A.FORME_NUM, A.CDINSTAL, A.CLINSTAL, A.ARP, A.COP_ART, A.NIG, A.NIGSEQ, C.ORIG_BLOC, A.EMAIL, A.C_TARIF_TEL FROM TLIGPAR A, TINSBL B, TBLOPAR C  WHERE B.ID_BLOC = C.ID_BLOC AND A.ID_LIGPAR = B.ID_LIGPAR AND A.CDPARU = #1 AND A.COP_ART NOT IN(SELECT VAL_REGLE FROM TVALREG WHERE NOM_REGLE = 'OPER_REFUS' AND SENS_REGLE = 'R') UNION ALL SELECT A.NNAT, A.NSIM, A.CDPARU, A.CLPARU, A.CVCPA, 0, A.CODE_POSTAL, A.NUM_RUE, A.D_EFFET, A.MC, A.ADR_L2, A.ADR_L3, A.ADR_L5, A.CRPROF, A.PAR_NAT, 0, A.NNATN, A.OPP_COMM, A.CATPAR, A.CNUM, A.TDVR, A.ARD, A.BP, A.NOM, A.PRENOM, A.CDN, A.C_SERV_TEL, A.PAR_DEP, A.OPP_PARUT, A.C_TYPE_CLIENT, A.FORME_ADR, A.FORME_INTIT, A.C_EQUIP_TEL, A.DDMAJO, A.DDMAJE, A.CEDEX, A.NOMV, A.DL, A.MDET, A.NAF, A.FORME_NUM, A.CDINSTAL, A.CLINSTAL, A.ARP, A.COP_ART, ' ', 0, ' ', A.EMAIL, A.C_TARIF_TEL FROM TLIGPAR A  WHERE A.CATPAR = 'SR' AND A.CDPARU = #2 AND A.COP_ART NOT IN(SELECT VAL_REGLE FROM TVALREG WHERE NOM_REGLE = 'OPER_REFUS' AND SENS_REGLE = 'R')")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0BEB01 (1214) :
		m_sql = sql("SELECT NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		
		// FileName: F0BEB01 (1355) :
		cursorClose(eb2000);
		
		
		// FileName: F0BEB01 (1624) :
		//cursorClose(eb3000);
		
		// FileName: F0BEB01 (1679) :
		m_sql = sql("UPDATE THTRAIT SET NITDF = CURRENT TIMESTAMP, NITSTAT = 'TERMINE', NITVR = #1, NITVT = #2 WHERE NIT = #3 AND NITUF = #4 AND ETAPE = #5 AND ID_INTER_AMONT = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.param(3, varParam0)
			.param(4, varParam1)
			.param(5, varParam2)
			.param(6, varParam3);
		handleSqlStatus();
		
		// FileName: F0BEB01 (2445) :
		try
		{
			m_cur = cursorOpen(cintamo, "SELECT ID_INTER_AMONT, ID_INTERL, LABEL_FICHIER, NAT_INTERF FROM TINTAMO  WHERE NAT_INTERF = 'EXTRACTION 14N' AND STATUT = 'A TRAITER' ORDER BY DATE_RECEPT") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BEB01 (2475) :
		cursorClose(cintamo);
		
		// FileName: F0BEB01 (2511) :
		m_sql = sql("UPDATE TINTAMO SET C_ERREUR = '    ', STATUT = 'TRAITE', NO_SEQ = 0 WHERE ID_INTER_AMONT = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
		// FileName: F0BEB01 (2879) :
		try
		{
			m_cur = cursorOpen(ebc000, "SELECT VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE LIKE 'RCPTLAIMP%' ORDER BY NOM_REGLE") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BEB01 (2916) :
		cursorClose(ebc000);
		
		// FileName: F0BEB01 (3020) :
		try
		{
			m_cur = cursorOpen(cur_Profparul3, "SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'PROFPARUL3' AND SENS_REGLE = 'A' ORDER BY VAL_REGLE") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BEB01 (3057) :
		cursorClose(cur_Profparul3);
		
		// FileName: F0BEB01 (3088) :
		try
		{
			m_cur = cursorOpen(cur_Cdparul3l5, "SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'CDPARUL3L5' AND SENS_REGLE = 'A' ORDER BY VAL_REGLE") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BEB01 (3125) :
		cursorClose(cur_Cdparul3l5);
		
	}
	Paragraph F0BMAJBE= new Paragraph(this);
	public void F0BMAJBE() {
	
		
		// FileName: F0BMAJBE (1433) :
		m_sql = sql("SELECT LONG_REGLE, VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = #1 AND SENS_REGLE = #2")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1466) :
		m_sql = sql("SELECT DAYOFWEEK_ISO(CHAR(CURRENT DATE, EUR)) FROM SYSIBM.SYSDUMMY1")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1482) :
		m_sql = sql("SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'IND_HLIM'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1495) :
		m_sql = sql("SELECT VAL_REGLE2 FROM TVALREG  WHERE VAL_REGLE = #1 AND NOM_REGLE = 'HLIM_MAJBE'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1513) :
		m_sql = sql("SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'IND_HFIN'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1526) :
		m_sql = sql("SELECT VAL_REGLE2 FROM TVALREG  WHERE VAL_REGLE = #1 AND NOM_REGLE = 'HFIN_MAJBE'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1592) :
		m_sql = sql("SELECT MAX(NIT) FROM TIFSBE  WHERE NIT = 0")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1610) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'VRGS'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1621) :
		m_sql = sql("SELECT PER_HIST FROM TPEHIST  WHERE PER_ACT = 'O'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1175) :
		try
		{
			m_cur = cursorOpen(cintamo, "SELECT A.ID_INTER_AMONT, A.ID_INTERL, LABEL_FICHIER, A.NAT_INTERF, MEDIA, STATUT, A.ORIG_INTERF, A.DATE_RECEPT, COMMENTAIRE, B.COP_ART, A.NO_SEQ, E.NBMVT FROM TINTAMO A INNER JOIN TINTBE B ON A.ID_INTERL = B.ID_INTERL INNER JOIN TORIGINT C ON A.ORIG_INTERF = C.ORIG_INTERF LEFT JOIN TFICMVT E ON A.ID_INTER_AMONT = E.ID_INTER_AMONT LEFT JOIN(SELECT DATE_RECEPT, ID_INTERL, MAX(NBMVT) AS NBMVTS FROM TFICMVT  GROUP BY DATE_RECEPT, ID_INTERL) D ON DATE(A.DATE_RECEPT) = D.DATE_RECEPT AND A.ID_INTERL = D.ID_INTERL WHERE A.NAT_INTERF IN('RECYCLAGE BE', 'MISE A JOUR FT V2', 'MISE A JOUR TIERS V2', 'MISE A JOUR AU V1', 'MISE A JOUR ARCEP', 'MISE A JOUR ARCEP FT') AND STATUT IN('A TRAITER', 'EN COURS') ORDER BY STATUT DESC, DATE(A.DATE_RECEPT), D.NBMVTS DESC, A.ID_INTER_AMONT") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (1659) :
		cursorClose(cintamo);
		
		// FileName: F0BMAJBE (1681) :
		m_sql = sql("SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'STOP_MAJBE'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1699) :
		m_sql = sql("SELECT DAYOFWEEK_ISO(CHAR(CURRENT DATE, EUR)) FROM SYSIBM.SYSDUMMY1")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1210) :
		try
		{
			m_cur = cursorOpen(camo6, "SELECT NO_SEQ, MAX(DATE_RECEPT) FROM TINTAMO  WHERE NAT_INTERF LIKE 'MISE A JOUR%' AND STATUT = 'TRAITE' AND ID_INTERL = #1 AND SUBSTR(MEDIA, 1, 10) = #2 GROUP BY NO_SEQ ORDER BY NO_SEQ DESC")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0BMAJBE (1222) :
		try
		{
			m_cur = cursorOpen(camo61, "SELECT NO_SEQ, MAX(DATE_RECEPT) FROM TINTAMO  WHERE NAT_INTERF LIKE 'MISE A JOUR%' AND STATUT = 'TRAITE' AND ID_INTERL = #1 AND SUBSTR(MEDIA, 1, 10) <> #2 GROUP BY NO_SEQ ORDER BY NO_SEQ DESC")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (1880) :
		cursorClose(camo6);
		
		
		// FileName: F0BMAJBE (1883) :
		cursorClose(camo61);
		
		// FileName: F0BMAJBE (1981) :
		m_sql = sql("SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'LR' AND SENS_REGLE = 'A' AND VAL_REGLE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (2113) :
		m_sql = sql("SELECT (DAYS(#1)) FROM SYSIBM.SYSDUMMY1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (2166) :
		m_sql = sql("SELECT (DAYS(#1)) FROM SYSIBM.SYSDUMMY1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (2193) :
		m_sql = sql("SELECT COP_ART FROM TOPTEL  WHERE COP_ART = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (2210) :
		m_sql = sql("SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'LR' AND SENS_REGLE = 'A' AND VAL_REGLE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (2321) :
		m_sql = sql("SELECT ID_MVT FROM TMVREC  WHERE ID_MVT = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (2986) :
		m_sql = sql("INSERT INTO GTERR (C_ERREUR, DEST_ERR, C_TYPE_ERR) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (3114) :
		m_sql = sql("SELECT C_ERREUR FROM GTERR  WHERE C_ERREUR NOT IN('RE05', 'RE06', ' ') AND C_TYPE_ERR = 'R' AND DEST_ERR = 'CED' FETCH FIRST 1 ROWS ONLY")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (3135) :
		m_sql = sql("SELECT C_ERREUR FROM GTERR  WHERE C_ERREUR IN('RE05', 'RE06') AND C_TYPE_ERR = 'R' AND DEST_ERR = 'CED' FETCH FIRST 1 ROWS ONLY")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (3598) :
		m_sql = sql("SELECT ID_FICHIER, DATE_RECEPT FROM TFICMVT  WHERE ID_INTERL = #1 AND ID_INTER_AMONT = #2 FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (3629) :
		m_sql = sql("SELECT LONG_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = 'RENUMVOIP' AND SENS_REGLE = 'A' AND VAL_REGLE = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (3670) :
		m_sql = sql("INSERT INTO GTERR (C_ERREUR, DEST_ERR, C_TYPE_ERR) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (3746) :
		m_sql = sql("SELECT NIT, ID_INTER_AMONT FROM TREJETS  WHERE TRAITE_PB_INSC = 'N' AND NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (3773) :
		m_sql = sql("SELECT ID_MVT FROM TMVATT  WHERE ID_INTER_AMONT = #1 AND NNAT = #2 AND NSIM = #3 AND NUM_ENREG = #4")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1256) :
		try
		{
			m_cur = cursorOpen(curgtlig, "SELECT ID_LIGPAR, ARP, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, CELOC, CATPAR, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, ARD_PARU, CPPARU FROM TLIGPAR  WHERE NNAT = #1 AND NSIM <> #2 AND COP_ART <> #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (3917) :
		cursorClose(curgtlig);
		
		// FileName: F0BMAJBE (4080) :
		m_sql = sql("SELECT NNAT FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (4152) :
		m_sql = sql("SELECT ID_LIGPAR FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (4200) :
		m_sql = sql("UPDATE THTRAIT SET NITDPC1 = 1, NITVT = #1, NITVR = #2, NITCPT3 = #3 WHERE NIT = #4 AND NITUF = #5 AND ETAPE = #6 AND ID_INTER_AMONT = #7")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0)
			.param(5, varParam1)
			.param(6, varParam2)
			.param(7, varParam3);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (4221) :
		m_sql = sql("SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'STOP_MAJBE'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (4239) :
		m_sql = sql("SELECT DAYOFWEEK_ISO(CHAR(CURRENT DATE, EUR)) FROM SYSIBM.SYSDUMMY1")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (4271) :
		m_sql = sql("UPDATE THTRAIT SET NITDPC1 = #1, NITVT = #2, NITVR = #3, NITCPT3 = #4 WHERE NIT = #5 AND NITUF = #6 AND ETAPE = #7 AND ID_INTER_AMONT = #8")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.param(5, varParam0)
			.param(6, varParam1)
			.param(7, varParam2)
			.param(8, varParam3);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (4341) :
		m_sql = sql("SELECT C_ERREUR FROM GTERR  WHERE C_ERREUR = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (4370) :
		m_sql = sql("INSERT INTO GTERR (C_ERREUR, DEST_ERR, C_TYPE_ERR) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (4518) :
		m_sql = sql("INSERT INTO GTERR (C_ERREUR, DEST_ERR, C_TYPE_ERR) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (4909) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'ID_INTER_AMONT'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (4926) :
		m_sql = sql("INSERT INTO TINTAMO (ID_INTER_AMONT, ID_INTERL, NAT_INTERF, DATE_RECEPT, LABEL_FICHIER, STATUT, MEDIA, NO_SEQ, C_ERREUR, ORIG_INTERF, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, 'A TRAITER', #7, #8, '    ', ' ', #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (4958) :
		m_sql = sql("UPDATE TGESTID SET V_ID = #1 WHERE C_TYPE_ID = 'ID_INTER_AMONT'")
			.value(1, varValue0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (4989) :
		m_sql = sql("UPDATE THTRAIT SET NITDF = CURRENT TIMESTAMP, NITSTAT = 'TERMINE', NITVR = #1, NITVT = #2, NITDPC1 = 0, NITCPT3 = #3 WHERE NIT = #4 AND NITUF = #5 AND ETAPE = #6 AND ID_INTER_AMONT = #7")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0)
			.param(5, varParam1)
			.param(6, varParam2)
			.param(7, varParam3);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5013) :
		m_sql = sql("UPDATE TINTAMO SET C_ERREUR = '    ', STATUT = 'TRAITE', NO_SEQ = #1 WHERE ID_INTER_AMONT = #2")
			.value(1, varValue0)
			.param(2, varParam0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5047) :
		m_sql = sql("UPDATE TMVATT SET TRAITE_PB_MVT = 'O' WHERE NNAT = #1 AND NSIM = #2 AND NIT = #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (1163) :
		try
		{
			m_cur = cursorOpen(cnoseq, "SELECT NO_SEQ FROM TINTAMO  WHERE NAT_INTERF LIKE 'MISE A JOUR%' AND ID_INTERL = #1 AND SUBSTR(MEDIA, 1, 10) = #2 GROUP BY NO_SEQ ORDER BY NO_SEQ DESC")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (5099) :
		cursorClose(cnoseq);
		
		// FileName: F0BMAJBE (5185) :
		m_sql = sql("UPDATE TINTAMO SET C_ERREUR = #1, STATUT = #2, COMMENTAIRE = #3 WHERE ID_INTER_AMONT = #4")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5200) :
		m_sql = sql("UPDATE THTRAIT SET NITSTAT = 'ABANDON' WHERE NIT = #1 AND NITUF = #2 AND ETAPE = #3 AND ID_INTER_AMONT = #4")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5215) :
		m_sql = sql("UPDATE TINTAMO SET C_ERREUR = #1, COMMENTAIRE = #2 WHERE ID_INTER_AMONT = #3")
			.value(1, varValue0)
			.value(2, varValue1)
			.param(3, varParam0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5239) :
		m_sql = sql("SELECT COP_ART, CRPROF, NOM, PRENOM, CSIRET, MARQ_PROT, TYPE_PROT, CDINSTAL, CLINSTAL, C_TYPE_CLIENT FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5255) :
		m_sql = sql("SELECT VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'LR' AND SENS_REGLE = 'A' AND VAL_REGLE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (1155) :
		try
		{
			m_cur = cursorOpen(cmvtrej, "SELECT NUM_ENREG, NNAT, NSIM FROM TMVATT  WHERE NIT = #1 ORDER BY NUM_ENREG")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (5307) :
		cursorClose(cmvtrej);
		
		// FileName: F0BMAJBE (1147) :
		try
		{
			m_cur = cursorOpen(crejets, "SELECT NNAT, NSIM, TRAITE_PB_INSC FROM TREJETS  WHERE TRAITE_PB_INSC = 'N' FOR UPDATE OF TRAITE_PB_INSC") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (5343) :
		cursorClose(crejets);
		
		// FileName: F0BMAJBE (5360) :
		m_sql = sql("SELECT NNAT, NSIM FROM TMVATT  WHERE TRAITE_PB_MVT = 'N' AND NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5371) :
		cursorUpdateCurrent(crejets, "UPDATE TREJETS SET TRAITE_PB_INSC = 'O'");
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5410) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, CELOC, CATPAR, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, ARD_PARU, CPPARU FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5457) :
		m_sql = sql("SELECT ORIG_INSC FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5478) :
		m_sql = sql("SELECT C_ERREUR FROM GTERR  WHERE DEST_ERR = #1 AND C_TYPE_ERR = 'R' AND C_ERREUR <> ' ' FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5505) :
		m_sql = sql("SELECT COUNT(C_ERREUR) FROM GTERR  WHERE DEST_ERR = 'OPERATEUR' AND C_TYPE_ERR = 'R'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5528) :
		m_sql = sql("SELECT DEST_ERR, C_TYPE_ERR FROM TLERROR  WHERE C_ERREUR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5553) :
		m_sql = sql("SELECT COUNT(* ) FROM TREJETS  WHERE ID_INTER_AMONT = #1 AND TRAITE_PB_INSC = 'N'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5765) :
		m_sql = sql("UPDATE THISTBE SET COMMENTAIRE = CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT('Rejet supprimé le ', SUBSTR( CHAR( CURRENT_DATE ), 9, 2 )), '/'), SUBSTR( CHAR( CURRENT_DATE ), 6, 2 )), '/'), SUBSTR( CHAR( CURRENT_DATE ), 1, 4 )), ' pour reprise en codification') , IND_EFFICACE = 'X' WHERE ID_MVT IN( SELECT ID_MVT FROM TMVREC WHERE NNAT = #1 AND NSIM = #2 AND IND_VALIDE = 'R' AND IND_EFFICACE = 'I' AND NIT < #3 )")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5801) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5826) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5851) :
		m_sql = sql("UPDATE TINTAMO SET C_ERREUR = #1, STATUT = #2, COMMENTAIRE = #3 WHERE ID_INTER_AMONT = #4")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5879) :
		m_sql = sql("SELECT LOPER FROM TOPTEL  WHERE COP_ART = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5900) :
		m_sql = sql("SELECT COUNT(* ) FROM THISTBE A INNER JOIN TLERROR B ON A.C_ERREUR = B.C_ERREUR  WHERE NIT = #1 AND DEST_ERR = 'CED' AND IND_VALIDE = 'R' AND IND_EFFICACE <> 'X'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5917) :
		m_sql = sql("SELECT COUNT(* ) FROM THISTBE A INNER JOIN TLERROR B ON A.C_ERREUR = B.C_ERREUR  WHERE NIT = #1 AND DEST_ERR = 'OPERATEUR' AND IND_VALIDE = 'R' AND IND_EFFICACE <> 'X'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BMAJBE (5934) :
		m_sql = sql("UPDATE THTRAIT SET NITCPT1 = #1, NITCPT2 = #2 WHERE NIT = #3 AND NITUF = #4 AND ETAPE = #5 AND ID_INTER_AMONT = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.param(3, varParam0)
			.param(4, varParam1)
			.param(5, varParam2)
			.param(6, varParam3);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (5980) :
		m_sql = sql("INSERT INTO TMAILOP (ID_INTERL, NIT, TYPE_INFO, TEXTE_INFO) VALUES (#1, #2, #3, #4)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (1234) :
		try
		{
			m_cur = cursorOpen(cmvrec, "SELECT A.NNAT, A.NSIM, B.C_ERREUR FROM TMVREC A INNER JOIN TLERROR B ON A.C_ERREUR = B.C_ERREUR  WHERE NIT = #1 AND DEST_ERR = 'OPERATEUR'")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (6017) :
		cursorClose(cmvrec);
		
		// FileName: F0BMAJBE (6075) :
		m_sql = sql("INSERT INTO TMVATT (ID_MVT, NNAT, NSIM, NIT, ID_INTER_AMONT, ID_INTERL, COP_ART, NAT_INTERF, LABEL_FICHIER, DATE_RECEPT, NITVT, NITVR, NITDPC1, NITCPT1, NITCPT2, NUM_ENREG, DATE_CREATION, TRAITE_PB_MVT) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6087) :
		m_sql = sql("UPDATE THTRAIT SET NITCPT3 = #1 WHERE NIT = #2 AND NITUF = #3 AND ETAPE = #4 AND ID_INTER_AMONT = #5")
			.value(1, varValue0)
			.param(2, varParam0)
			.param(3, varParam1)
			.param(4, varParam2)
			.param(5, varParam3);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6114) :
		m_sql = sql("INSERT INTO TMVREC (ID_MVT, NIT, IND_VALIDE, IND_EFFICACE, C_ERREUR, ID_INTERL, DATE_MVT, IND_MODIF, C_MVT, ARP, NNAT, NSIM, CDPARUX, LPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTALX, LINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, LRPROF, NAF, CSIRET, C_TYPE_CLIENT, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, ADRTS, DEF, DFVAL8, PAR_NAT, PAR_DEP, FORME_NUM, LFORMAT, FORME_INTIT, FORME_ADR, CELOC, CATPAR, OPP_COMM, OPP_PARUT, OPP_RECH, COP_PORT, IND_PORT, ID_FICHIER, ID_FICMVT, ARD_PARU, CPPARU, DENOM_SUP, DENOM_ADD, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6138) :
		m_sql = sql("DELETE FROM TMVREC WHERE NNAT = #1 AND NSIM = #2 AND IND_VALIDE = 'R' AND NIT < #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6180) :
		m_sql = sql("UPDATE THISTBE A SET IND_VALIDE = 'X', COMMENTAIRE =( CASE WHEN( IND_VALIDE = 'R' AND IND_EFFICACE = 'I' ) THEN #1 WHEN( IND_EFFICACE = 'Q' ) THEN #2 END ) WHERE A.ID_MVT IN( SELECT B.ID_MVT FROM TMVREC B, TLERROR C WHERE B.NNAT = #3 AND B.NSIM = #4 AND B.C_ERREUR = C.C_ERREUR AND C.DEST_ERR = 'CED' )")
			.value(1, varValue0)
			.value(2, varValue1)
			.param(3, varParam0)
			.param(4, varParam1);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6205) :
		m_sql = sql("DELETE FROM TMVREC A WHERE A.NNAT = #1 AND A.NSIM = #2 AND ( ( A.IND_VALIDE = 'R' ) OR ( A.IND_VALIDE = 'V' AND A.IND_EFFICACE = 'Q' ) ) AND EXISTS ( SELECT B.C_ERREUR FROM TLERROR B WHERE A.C_ERREUR = B.C_ERREUR AND B.DEST_ERR = 'CED' )")
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6232) :
		m_sql = sql("INSERT INTO TREJETS (NNAT, NSIM, NIT, ID_INTER_AMONT, TRAITE_PB_INSC, MODULE, MOTIF, SQLCODE, C_ERREUR, DATE_REJET) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6256) :
		m_sql = sql("INSERT INTO TMAILOP (ID_INTERL, NIT, TYPE_INFO, TEXTE_INFO) VALUES (#1, #2, #3, #4)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6275) :
		m_sql = sql("UPDATE THFAU1 SET IND_REJET_OPER = #1 WHERE ID_FICHIER = #2 AND ID_FICMVT = #3")
			.value(1, varValue0)
			.param(2, varParam0)
			.param(3, varParam1);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (6296) :
		m_sql = sql("UPDATE TFICMVT SET NBRE_RUB_OPER = #1, NBRE_DERUB_OPER = #2, NBRE_RUB_ID = #3, NBRE_RUB_ORIG_INSC = #4, NBRE_RUB_ORIG_HIST = #5, NBRE_RUB_SUP = #6, NBRE_RUB_NS_ID = #7 WHERE ID_FICHIER = #8")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.param(8, varParam0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (1276) :
		try
		{
			m_cur = cursorOpen(cuavs, "SELECT ID_LIGPAR, ARP, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, CELOC, CATPAR, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, ARD_PARU, CPPARU FROM TLIGPAR  WHERE NNAT = #1")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (6335) :
		cursorClose(cuavs);
		
		// FileName: F0BMAJBE (1293) :
		try
		{
			m_cur = cursorOpen(cur_Trederr, "SELECT A.C_ERREUR, DEST_ERR, C_TYPE_ERR FROM TREDERR A, TLERROR B  WHERE ID_FICHIER = #1 AND ID_FICMVT = #2 AND A.C_ERREUR = B.C_ERREUR")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0BMAJBE (6418) :
		m_sql = sql("INSERT INTO GTERR SELECT #1, DEST_ERR, C_TYPE_ERR FROM TLERROR  WHERE C_ERREUR = #2")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		
		// FileName: F0BMAJBE (6441) :
		cursorClose(cur_Trederr);
		
		// FileName: F0BMAJBE (1243) :
		try
		{
			m_cur = cursorOpen(cterror, "SELECT DISTINCT C_ERREUR, DEST_ERR, C_TYPE_ERR FROM GTERR  WHERE DEST_ERR = #1")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (6482) :
		cursorClose(cterror);
		
		// FileName: F0BMAJBE (6541) :
		m_sql = sql("DELETE FROM GTERR") ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (8703) :
		m_sql = sql("UPDATE TINTAMO SET C_ERREUR = 'ZFPL', STATUT = 'TRAITE', COMMENTAIRE = #1 WHERE ID_INTER_AMONT = #2")
			.value(1, varValue0)
			.param(2, varParam0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (8711) :
		m_sql = sql("UPDATE TINTAMO SET C_ERREUR = 'ZFPL', STATUT = 'EN COURS', COMMENTAIRE = #1 WHERE ID_INTER_AMONT = #2")
			.value(1, varValue0)
			.param(2, varParam0);
		handleSqlStatus();
		
		// FileName: F0BMAJBE (8745) :
		m_sql = sql("INSERT INTO THDENOM (ID_MVT, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (8778) :
		m_sql = sql("INSERT INTO TERRHIS (ID_MVT, C_ERREUR) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0BMAJBE (1250) :
		try
		{
			m_cur = cursorOpen(cterr, "SELECT DISTINCT C_ERREUR, DEST_ERR, C_TYPE_ERR FROM GTERR") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BMAJBE (8903) :
		cursorClose(cterr);
		
		// FileName: F0BMAJBE (8956) :
		m_sql = sql("INSERT INTO TDIFFUS (ID_MVT, C_ERREUR, ID_BLOC, BLOC_SEQ, DT_DIFFUS, ORIG_BLOC, DDMAJB, NBRE_INSC, NBRE_OPER) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8) ;
		handleSqlStatus();
		
	}
	Paragraph F0BRDBDE= new Paragraph(this);
	public void F0BRDBDE() {
	
		
		// FileName: F0BRDBDE (215) :
		m_sql = sql("SELECT ID_FICHIER FROM TREDERR  WHERE ID_FICHIER = #1 AND ID_FICMVT = #2 AND C_ERREUR = #3 AND NNAT = #4 AND NSIM = #5")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
		handleSqlStatus();

		// FileName: F0BRDBDE (234) :
		m_sql = sql("INSERT INTO TREDERR (ID_FICHIER, ID_FICMVT, C_ERREUR, NNAT, NSIM) VALUES (#1, #2, #3, #4, #5)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4) ;
		handleSqlStatus();
		
	}
	Paragraph F0BRDBSI= new Paragraph(this);
	public void F0BRDBSI() {
	
		
		// FileName: F0BRDBSI (187) :
		m_sql = sql("SELECT ID_FICHIER FROM TREDERR  WHERE ID_FICHIER = #1 AND ID_FICMVT = #2 AND C_ERREUR = #3 AND NNAT = #4 AND NSIM = #5")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
		handleSqlStatus();

		// FileName: F0BRDBSI (206) :
		m_sql = sql("INSERT INTO TREDERR (ID_FICHIER, ID_FICMVT, C_ERREUR, NNAT, NSIM) VALUES (#1, #2, #3, #4, #5)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4) ;
		handleSqlStatus();
		
	}
	Paragraph F0BRDCIG= new Paragraph(this);
	public void F0BRDCIG() {
	
		
		// FileName: F0BRDCIG (211) :
		m_sql = sql("SELECT NNAT FROM TINSIG  WHERE COP_ART = #1 AND NNAT = #2 AND NSIM = #3 AND NIG = #4 AND NIGSEQ = #5")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
		handleSqlStatus();

		// FileName: F0BRDCIG (226) :
		m_sql = sql("SELECT DISTINCT(NIG) FROM TINSIG  WHERE COP_ART = #1 AND NIG = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BRDCIG (235) :
		m_sql = sql("SELECT DISTINCT(NIG) FROM TINSIG  WHERE COP_ART = #1 AND NNAT = #2 AND NSIM = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0BRDCIG (293) :
		m_sql = sql("SELECT NNAT FROM TINSIG  WHERE COP_ART = #1 AND NNAT = #2 AND NSIM = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0BRDCIG (109) :
		try
		{
			m_cur = cursorOpen(ctinsig, "SELECT NNAT, NSIM, NIGSEQ FROM TINSIG  WHERE COP_ART = #1 AND NIG = #2 ORDER BY NIGSEQ")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRDCIG (382) :
		cursorClose(ctinsig);
		
	}
	Paragraph F0BRDCVA= new Paragraph(this);
	public void F0BRDCVA() {
	
		
		// FileName: F0BRDCVA (133) :
		m_sql = sql("SELECT CTIC FROM TCIVIL  WHERE CTIC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

	}
	Paragraph F0BRDFPV= new Paragraph(this);
	public void F0BRDFPV() {
	
		
		// FileName: F0BRDFPV (154) :
		m_sql = sql("SELECT LFORMAT FROM TFORNUM  WHERE LFORMAT = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRDFPV (185) :
		m_sql = sql("INSERT INTO TREDERR (ID_FICHIER, ID_FICMVT, C_ERREUR, NNAT, NSIM) VALUES (#1, #2, 'C026', #4, #5)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(4, varValue3)
			.value(5, varValue4) ;
		handleSqlStatus();
		
	}
	Paragraph F0BRDMER= new Paragraph(this);
	public void F0BRDMER() {
	
		
		// FileName: F0BRDMER (124) :
		m_sql = sql("SELECT ID_FICHIER FROM TREDERR  WHERE ID_FICHIER = #1 AND ID_FICMVT = #2 AND C_ERREUR = #3 AND NNAT = #4 AND NSIM = #5")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
		handleSqlStatus();

		// FileName: F0BRDMER (143) :
		m_sql = sql("INSERT INTO TREDERR (ID_FICHIER, ID_FICMVT, C_ERREUR, NNAT, NSIM) VALUES (#1, #2, #3, #4, #5)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4) ;
		handleSqlStatus();
		
	}
	Paragraph F0BRDMIG= new Paragraph(this);
	public void F0BRDMIG() {
	
		
		// FileName: F0BRDMIG (185) :
		m_sql = sql("SELECT NNAT FROM TINSIG  WHERE COP_ART = #1 AND NNAT = #2 AND NSIM = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0BRDMIG (238) :
		m_sql = sql("DELETE FROM TINSIG WHERE COP_ART = #1 AND NNAT = #2 AND NSIM = #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();
		
		// FileName: F0BRDMIG (267) :
		m_sql = sql("SELECT NNAT FROM TINSIG  WHERE COP_ART = #1 AND NIG = #2 AND NIGSEQ = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0BRDMIG (276) :
		m_sql = sql("DELETE FROM TINSIG WHERE COP_ART = #1 AND NIG = #2 AND NIGSEQ = #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();
		
		// FileName: F0BRDMIG (324) :
		m_sql = sql("INSERT INTO TINSIG (COP_ART, NNAT, NSIM, NIG, NIGSEQ, DATE_DER_MAJ) VALUES (#1, #2, #3, #4, #5, #6)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5) ;
		handleSqlStatus();
		
	}
	Paragraph F0BRDMTD= new Paragraph(this);
	public void F0BRDMTD() {
	
		
		// FileName: F0BRDMTD (231) :
		try
		{
			m_cur = cursorOpen(cur_Tmotdou, "SELECT MOT_1, MOT_2 FROM TMOTDOU  ORDER BY MOT_1") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRDMTD (267) :
		cursorClose(cur_Tmotdou);
		
		// FileName: F0BRDMTD (359) :
		m_sql = sql("INSERT INTO TREDERR (ID_FICHIER, ID_FICMVT, C_ERREUR, NNAT, NSIM) VALUES (#1, #2, 'C145', #4, #5)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(4, varValue3)
			.value(5, varValue4) ;
		handleSqlStatus();
		
	}
	Paragraph F0BRDVF1= new Paragraph(this);
	public void F0BRDVF1() {
	
		
		// FileName: F0BRDVF1 (146) :
		m_sql = sql("SELECT LFORMAT FROM TFORNUM  WHERE FORME_NUM = ' '")
			.into(varInto0) ;
		handleSqlStatus();

	}
	Paragraph F0BRDVRG= new Paragraph(this);
	public void F0BRDVRG() {
	
		
		// FileName: F0BRDVRG (197) :
		m_sql = sql("SELECT NOM_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND VAL_REGLE = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BRDVRG (212) :
		m_sql = sql("SELECT NOM_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND VAL_REGLE2 = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BRDVRG (228) :
		m_sql = sql("SELECT NOM_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND VAL_REGLE = #2 AND VAL_REGLE2 = #3")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0BRDVRG (275) :
		m_sql = sql("SELECT NOM_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRDVRG (93) :
		try
		{
			m_cur = cursorOpen(c1, "SELECT NOM_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND VAL_REGLE >= #2 ORDER BY NOM_REGLE, VAL_REGLE, VAL_REGLE2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0BRDVRG (105) :
		try
		{
			m_cur = cursorOpen(c2, "SELECT NOM_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE LIKE #1 ORDER BY NOM_REGLE, VAL_REGLE, VAL_REGLE2")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRDVRG (389) :
		cursorClose(c1);
		
		
		// FileName: F0BRDVRG (391) :
		cursorClose(c2);
		
	}
	Paragraph F0BRE010= new Paragraph(this);
	public void F0BRE010() {
	
		
		// FileName: F0BRE010 (414) :
		try
		{
			m_cur = cursorOpen(cint, "SELECT A.ID_INTERL, A.NOM_FLUX_GENER FROM TINTBE A  WHERE A.NOM_FLUX_GENER <> '' ORDER BY A.ID_INTERL ASC") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRE010 (649) :
		cursorClose(cint);
		
		// FileName: F0BRE010 (702) :
		m_sql = sql("SELECT A.ID_INTERL, A.NOM_INTERL, A.COP_ART, A.ORIG_INTERF, A.IND_FLUX_AUTO, A.IND_RED_DENOM, A.IND_RED_ADR, A.DELIM_CHAMPS, A.DELIM_INTRA_CHAMPS, A.FMT_FLUX_OPER, A.C_TYPE_NOMEN, A.C_REF_REG_OPE, A.ID_SEQRED, A.NAT_INTERF_MAJBE, B.FMT_TECH_FLUX, B.IND_ENR_PRE, B.IND_ENR_SUF, B.NOM_TECH_ENR_PRE, B.NOM_TECH_ENR_DET, B.NOM_TECH_ENR_SUF, B.NB_DELIM_ENR_PRE, B.NB_DELIM_ENR_DET, B.NB_DELIM_ENR_SUF FROM TINTBE A, TFMTFLU B  WHERE A.ID_INTERL = #1 AND B.FMT_FLUX_OPER = A.FMT_FLUX_OPER")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE010 (889) :
		m_sql = sql("SELECT VALUE(MAX(NO_SEQ), 0) FROM TINTAMO  WHERE ID_INTERL = #1 AND NAT_INTERF LIKE 'MISE A JOUR%'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE010 (921) :
		m_sql = sql("SELECT (DAYS(#1)) FROM SYSIBM.SYSDUMMY1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE010 (958) :
		m_sql = sql("SELECT LONG_REGLE FROM TVALREG  WHERE VAL_REGLE = #1 AND NOM_REGLE = 'SELPROPART' AND SENS_REGLE = 'A'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE010 (990) :
		m_sql = sql("SELECT 1 FROM TVALREG  WHERE VAL_REGLE = #1 AND NOM_REGLE = #2 AND SENS_REGLE = 'A'")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BRE010 (1049) :
		m_sql = sql("SELECT VALUE(MAX(NO_SEQ), 0) FROM TINTAMO  WHERE ID_INTERL = #1 AND NAT_INTERF LIKE 'MISE A JOUR%'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE010 (1163) :
		m_sql = sql("SELECT L_ERREUR FROM TLERROR  WHERE C_ERREUR = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE010 (1270) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'ID_FICHIER'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BRE010 (1282) :
		m_sql = sql("UPDATE TGESTID SET V_ID = #1 WHERE C_TYPE_ID = 'ID_FICHIER'")
			.value(1, varValue0);
		handleSqlStatus();
		
		// FileName: F0BRE010 (1382) :
		m_sql = sql("INSERT INTO TFICMVT (ID_FICHIER, ID_INTERL, ID_INTER_AMONT, VERSION, NBMVT, DDMAJ, NUMENV, TYPE_FICHIER, COP_ART, DATE_RECEPT, LABEL_FICH_RECU, LABEL_FICH_REDRES, IND_HIST_RECU, IND_HIST_REDRES, IND_TRT_PROT, NOMDEST, NOMFIC, VRGSX, VCRPX, COP_ART_SI, NAT_INTERF, NBRE_C, NBRE_M, NBRE_S, NBRE_PORT_E, NBRE_PORT_S, C_ERREUR) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26) ;
		handleSqlStatus();
		
		// FileName: F0BRE010 (1798) :
		m_sql = sql("SELECT COUNT(* ) FROM TTECRED A  WHERE A.NOM_TECH = #1 OR A.NOM_TECH = #2 OR A.NOM_TECH = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0BRE010 (424) :
		try
		{
			m_cur = cursorOpen(czone, "SELECT A.NOM_TECH, A.CPL_TECH, A.NOM_ZONE, A.FMT_ZONE, A.POS_ZONE, A.LONG_ZONE FROM TTECRED A  WHERE A.NOM_TECH = #1 OR A.NOM_TECH = #2 OR A.NOM_TECH = #3 ORDER BY A.NOM_TECH, A.CPL_TECH, A.NOM_ZONE ASC")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRE010 (1875) :
		cursorClose(czone);
		
	}
	Paragraph F0BRE020= new Paragraph(this);
	public void F0BRE020() {
	
		
		// FileName: F0BRE020 (319) :
		m_sql = sql("SELECT DELIM_INTRA_CHAMPS, C_TYPE_NOMEN, C_CRITERE_1, C_CRITERE_2, C_CRITERE_3 FROM TINTBE  WHERE ID_INTERL = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE020 (354) :
		m_sql = sql("SELECT COUNT(* ) FROM TSEQRED A, TINTBE B  WHERE B.ID_INTERL = #1 AND A.ID_SEQRED = B.ID_SEQRED")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BRE020 (222) :
		try
		{
			m_cur = cursorOpen(cseqred, "SELECT A.CODE_REDR, PGM_REDR, TYPE_REDR, NOM_ZONE, IND_DECOUPE, IND_ERREUR FROM TSEQRED A, TREDRES B, TINTBE C  WHERE C.ID_INTERL = #1 AND A.ID_SEQRED = C.ID_SEQRED AND B.CODE_REDR = A.CODE_REDR ORDER BY NUM_ORDRE ASC")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRE020 (405) :
		cursorClose(cseqred);
		
		// FileName: F0BRE020 (443) :
		m_sql = sql("SELECT COUNT(* ) FROM TTECRED  WHERE NOM_TECH = 'FLUX-D' AND CPL_TECH = '    '")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BRE020 (233) :
		try
		{
			m_cur = cursorOpen(czone, "SELECT A.NOM_ZONE, FMT_ZONE, POS_ZONE, LONG_ZONE FROM TTECRED A  WHERE NOM_TECH = 'FLUX-D' AND CPL_TECH = '    ' ORDER BY NOM_ZONE ASC") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRE020 (488) :
		cursorClose(czone);
		
	}
	Paragraph F0BRE050= new Paragraph(this);
	public void F0BRE050() {
	
		
		// FileName: F0BRE050 (344) :
		m_sql = sql("SELECT PER_HIST FROM TPEHIST  WHERE PER_ACT = 'O'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BRE050 (378) :
		try
		{
			m_cur = cursorOpen(czs, "SELECT NOM_GROUPE, NOM, POS_DEB, LNG, POS_IND FROM TZS  ORDER BY NOM_GROUPE") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRE050 (407) :
		cursorClose(czs);
		
		// FileName: F0BRE050 (440) :
		try
		{
			m_cur = cursorOpen(tgzs, "SELECT NOM_GROUPE FROM TGZS  ORDER BY NOM_GROUPE") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRE050 (466) :
		cursorClose(tgzs);
		
		// FileName: F0BRE050 (544) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'ID_INTER_AMONT'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BRE050 (567) :
		m_sql = sql("INSERT INTO TINTAMO (ID_INTER_AMONT, ID_INTERL, NAT_INTERF, DATE_RECEPT, LABEL_FICHIER, STATUT, MEDIA, NO_SEQ, C_ERREUR, ORIG_INTERF, DELIMITEUR, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11) ;
		handleSqlStatus();
		
		// FileName: F0BRE050 (601) :
		m_sql = sql("UPDATE TGESTID SET V_ID = #1 WHERE C_TYPE_ID = 'ID_INTER_AMONT'")
			.value(1, varValue0);
		handleSqlStatus();
		
		// FileName: F0BRE050 (655) :
		m_sql = sql("INSERT INTO TFICMVT (ID_FICHIER, ID_INTERL, ID_INTER_AMONT, VERSION, NBMVT, DDMAJ, NUMENV, TYPE_FICHIER, COP_ART, DATE_RECEPT, LABEL_FICH_RECU, LABEL_FICH_REDRES, IND_HIST_RECU, IND_HIST_REDRES, IND_TRT_PROT, NOMDEST, NOMFIC, VRGSX, VCRPX, COP_ART_SI, NAT_INTERF, NBRE_C, NBRE_M, NBRE_S, NBRE_PORT_E, NBRE_PORT_S, C_ERREUR) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26) ;
		handleSqlStatus();
		
		// FileName: F0BRE050 (1016) :
		m_sql = sql("INSERT INTO THFAU1 (ID_FICHIER, ID_FICMVT, PER_HIST, ADN, ADR_L2, ADR_L3, ADR_L4, ADR_L5, ARD, BP, C_EQUIP, C_MVT, C_SERV, C_TARIF, CDINSTAL, CDN, CDPARU, CEDEX, CELOC, CI, CNIG, CNUM, CODE_POSTAL, CODPROD, COP_ART, COP_PORT, CRPROF, CSIRET, CSM, CTIC, DATE_SI_OP, DENOM, DET, DL, EMAIL, ENBA30, IND_ADR, IND_AI, IND_AT, IND_INT, IND_IPR, IND_LC, IND_LOR, IND_LR, IND_LRI, IND_LSAF, IND_LX1, IND_LX2, IND_LX3, IND_LX4, IND_LX5, IND_PORT, IND_PROT_ADR, IND_SO, IPAYS, LDA, LINSTAL, LPARU, LRPROF, MC, MDET, MNEMO, NAF, NAP, NNAP, NNAT, NNATI, NNATR, NOM, NOM_VOIE, NSIM, NSIMI, NSIMR, NUM_RUE, PAR_DEP, PAR_NAT, PRENOM, TDVR, IND_REJET_OPER, IND_PROT, ARD_PARU, CLPARU, DENOM_ADD, CPINSTAL_GEO, CPPARU_GEO, CRPROF_OP, LRPROF_OP, PROFESSION, NIG, NIGSEQ) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89) ;
		handleSqlStatus();
		
		// FileName: F0BRE050 (1338) :
		m_sql = sql("INSERT INTO THFAU2 (ID_FICHIER, ID_FICMVT, PER_HIST, ADN, ADR_L2, ADR_L3, ADR_L4, ADR_L5, ARD, BP, C_EQUIP, C_MVT, C_SERV, C_TARIF, CDINSTAL, CDN, CDPARU, CEDEX, CELOC, CI, CNIG, CNUM, CODE_POSTAL, CODPROD, COP_ART, COP_PORT, CRPROF, CSIRET, CSM, CTIC, DATE_SI_OP, DENOM, DET, DL, EMAIL, ENBA30, IND_ADR, IND_AI, IND_AT, IND_INT, IND_IPR, IND_LC, IND_LOR, IND_LR, IND_LRI, IND_LSAF, IND_LX1, IND_LX2, IND_LX3, IND_LX4, IND_LX5, IND_PORT, IND_SO, IPAYS, LDA, LINSTAL, LPARU, LRPROF, MC, MDET, MNEMO, NAF, NAP, NNAP, NNAT, NNATI, NNATR, NOM, NOM_VOIE, NSIM, NSIMI, NSIMR, NUM_RUE, PAR_DEP, PAR_NAT, PRENOM, TDVR, ARD_PARU, CLPARU, DENOM_ADD, DENOM_SUP, CPINSTAL_GEO, CPPARU_GEO, CRPROF_OP, LRPROF_OP, PROFESSION, NIG, NIGSEQ) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87) ;
		handleSqlStatus();
		
		// FileName: F0BRE050 (1545) :
		try
		{
			m_cur = cursorOpen(cmvtprec, "SELECT H.ID_FICHIER, H.ID_FICMVT, H.PER_HIST, H.ADN, H.ADR_L2, H.ADR_L3, H.ADR_L4, H.ADR_L5, H.ARD, H.BP, H.C_EQUIP, H.C_MVT, H.C_SERV, H.C_TARIF, H.CDINSTAL, H.CDN, H.CDPARU, H.CEDEX, H.CELOC, H.CI, H.CNIG, H.CNUM, H.CODE_POSTAL, H.CODPROD, H.COP_ART, H.COP_PORT, H.CRPROF, H.CSIRET, H.CSM, H.CTIC, H.DATE_SI_OP, H.DENOM, H.DET, H.DL, H.EMAIL, H.ENBA30, H.IND_ADR, H.IND_AI, H.IND_AT, H.IND_INT, H.IND_IPR, H.IND_LC, H.IND_LOR, H.IND_LR, H.IND_LRI, H.IND_LSAF, H.IND_LX1, H.IND_LX2, H.IND_LX3, H.IND_LX4, H.IND_LX5, H.IND_PORT, H.IND_PROT_ADR, H.IND_SO, H.IPAYS, H.LDA, H.LINSTAL, H.LPARU, H.LRPROF, H.MC, H.MDET, H.MNEMO, H.NAF, H.NAP, H.NNAP, H.NNAT, H.NNATI, H.NNATR, H.NOM, H.NOM_VOIE, H.NSIM, H.NSIMI, H.NSIMR, H.NUM_RUE, H.PAR_DEP, H.PAR_NAT, H.PRENOM, H.TDVR, H.ARD_PARU, H.CLPARU, H.DENOM_ADD, H.CPINSTAL_GEO, H.CPPARU_GEO, H.CRPROF_OP, H.LRPROF_OP, H.PROFESSION, VALUE(R.ID_FICHIER, 0), VALUE(R.ID_FICMVT, 0), R.PER_HIST, R.ADN, R.ADR_L2, R.ADR_L3, R.ADR_L4, R.ADR_L5, R.ARD, R.BP, R.C_EQUIP, R.C_MVT, R.C_SERV, R.C_TARIF, R.CDINSTAL, R.CDN, R.CDPARU, R.CEDEX, R.CELOC, R.CI, R.CNIG, R.CNUM, R.CODE_POSTAL, R.CODPROD, R.COP_ART, R.COP_PORT, R.CRPROF, R.CSIRET, R.CSM, R.CTIC, R.DATE_SI_OP, R.DENOM, R.DET, R.DL, R.EMAIL, R.ENBA30, R.IND_ADR, R.IND_AI, R.IND_AT, R.IND_INT, R.IND_IPR, R.IND_LC, R.IND_LOR, R.IND_LR, R.IND_LRI, R.IND_LSAF, R.IND_LX1, R.IND_LX2, R.IND_LX3, R.IND_LX4, R.IND_LX5, R.IND_PORT, R.IND_SO, R.IPAYS, R.LDA, R.LINSTAL, R.LPARU, R.LRPROF, R.MC, R.MDET, R.MNEMO, R.NAF, R.NAP, R.NNAP, R.NNAT, R.NNATI, R.NNATR, R.NOM, R.NOM_VOIE, R.NSIM, R.NSIMI, R.NSIMR, R.NUM_RUE, R.PAR_DEP, R.PAR_NAT, R.PRENOM, R.TDVR, R.ARD_PARU, R.CLPARU, R.DENOM_ADD, R.CPINSTAL_GEO, R.CPPARU_GEO, R.CRPROF_OP, R.LRPROF_OP, R.PROFESSION, VALUE(F.ID_INTER_AMONT, 0), VALUE(F.NAT_INTERF, 'MISE A JOUR AU V1') FROM TFICMVT F, TINTAMO T, THFAU1 H LEFT OUTER JOIN THFAU2 R ON(R.ID_FICHIER = H.ID_FICHIER AND R.ID_FICMVT = H.ID_FICMVT)  WHERE H.NNAT = #1 AND H.NSIM = #2 AND F.ID_INTER_AMONT = T.ID_INTER_AMONT AND H.IND_REJET_OPER <> 'O' AND H.C_MVT <> 'S' AND H.ID_FICHIER = F.ID_FICHIER AND((H.ID_FICHIER = #3 AND H.ID_FICMVT < #4) OR(H.ID_FICHIER < #5)) ORDER BY F.ID_INTER_AMONT DESC, H.ID_FICHIER DESC, H.ID_FICMVT DESC")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BRE050 (2009) :
		cursorClose(cmvtprec);
		
		// FileName: F0BRE050 (2282) :
		m_sql = sql("INSERT INTO TCPTGZS (ID_FICHIER, NOM_GROUPE, NBRE_MDF, NBRE_ID) VALUES (#1, #2, #3, #4)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3) ;
		handleSqlStatus();
		
		// FileName: F0BRE050 (2331) :
		m_sql = sql("INSERT INTO TSTATRD (ID_FICHIER, CODE_REDR, NB_REDR, TYPE_CPT) VALUES (#1, #2, #3, #4)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3) ;
		handleSqlStatus();
		
		// FileName: F0BRE050 (2488) :
		m_sql = sql("SELECT ID_INTERL, COP_ART, NOM_INTERL, FMT_FLUX_OPER, ID_SEQRED, NAT_INTERF_MAJBE, C_REF_REG_OPE FROM TINTBE  WHERE ID_INTERL = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.param(1, varParam0) ;
		handleSqlStatus();

	}
	Paragraph F0BZZ02= new Paragraph(this);
	public void F0BZZ02() {
	
		
		// FileName: F0BZZ02 (274) :
		m_sql = sql("SELECT NOM_REGLE FROM TVALREG  WHERE NOM_REGLE = 'PROFARISK' AND VAL_REGLE = #1 FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BZZ02 (373) :
		try
		{
			m_cur = cursorOpen(cthistdt, "SELECT NNAT FROM THISTBE  WHERE CRPROF > '000003' AND IND_VALIDE = 'V' AND NNAT = #1 AND NSIM = #2 AND CDINSTALX = #3 AND(LINSTAL = #4 OR LINSTAL = #5) ORDER BY ID_MVT DESC")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BZZ02 (401) :
		cursorClose(cthistdt);
		
		// FileName: F0BZZ02 (420) :
		try
		{
			m_cur = cursorOpen(cthistbe, "SELECT CRPROF, TYPE_PROT, MARQ_PROT, CSIRET, C_TYPE_CLIENT, NAF FROM THISTBE  WHERE CRPROF > '000003' AND IND_VALIDE = 'V' AND NNAT = #1 AND NSIM = #2 AND((CSIRET LIKE #3 AND CSIRET <> ' ') OR(CDINSTALX = #4 AND(LINSTAL = #5 OR LINSTAL = #6) AND NOM_EPURE = #7 AND PRENOM_EPURE = #8)) ORDER BY ID_MVT DESC")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BZZ02 (463) :
		cursorClose(cthistbe);
		
		// FileName: F0BZZ02 (493) :
		m_sql = sql("SELECT NOM, PRENOM, CRPROF, MARQ_PROT, TYPE_PROT, CSIRET, C_TYPE_CLIENT, CDINSTAL, CLINSTAL, COP_ART, ORIG_INSC, NAF FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0BZZ02 (645) :
		m_sql = sql("SELECT NOM_REGLE FROM TVALREG  WHERE NOM_REGLE = 'PROFARISK' AND VAL_REGLE = #1 FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BZZ02 (808) :
		try
		{
			m_cur = cursorOpen(chistop, "SELECT F.ID_INTER_AMONT, CAST(VALUE(H1.CRPROF, ' ') AS CHAR(6)), CAST(VALUE(H2.CRPROF, ' ') AS CHAR(6)), H1.ID_FICHIER, H1.ID_FICMVT FROM THFAU1 H1 LEFT OUTER JOIN THFAU2 H2 ON(H1.ID_FICHIER = H2.ID_FICHIER AND H1.ID_FICMVT = H2.ID_FICMVT), TFICMVT F  WHERE H1.NNAT = #1 AND H1.NSIM = #2 AND F.ID_INTER_AMONT <= #3 AND H1.IND_REJET_OPER <> 'O' AND H1.C_MVT <> 'S' AND H1.ID_FICHIER = F.ID_FICHIER AND((H1.ID_FICHIER = #4 AND H1.ID_FICMVT < #5) OR(H1.ID_FICHIER <> #6)) ORDER BY F.ID_INTER_AMONT DESC, H1.ID_FICHIER DESC, H1.ID_FICMVT DESC")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0BZZ02 (863) :
		cursorClose(chistop);
		
	}
	Paragraph F0BZZ03= new Paragraph(this);
	public void F0BZZ03() {
	
		
		// FileName: F0BZZ03 (90) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'NIT'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BZZ03 (117) :
		m_sql = sql("UPDATE TGESTID SET V_ID = #1 WHERE C_TYPE_ID = 'NIT'")
			.value(1, varValue0);
		handleSqlStatus();
		
	}
	Paragraph F0BZZ10= new Paragraph(this);
	public void F0BZZ10() {
	
		
		// FileName: F0BZZ10 (127) :
		m_sql = sql("SELECT NIT, NITSTAT, NITDPC1, NITDPCIV, NITVT, NITVR, NITCPT1, NITCPT2, NITCPT3 FROM THTRAIT A  WHERE NITUF = #1 AND ETAPE = #2 AND A.ID_INTER_AMONT = #3 AND A.NIT =(SELECT MAX(B.NIT) FROM THTRAIT B WHERE A.NITUF = B.NITUF AND A.ETAPE = B.ETAPE AND B.ID_INTER_AMONT = #4)")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0BZZ10 (167) :
		m_sql = sql("INSERT INTO THTRAIT (NIT, ID_INTER_AMONT, NITUF, ETAPE, NITDD, NITDF, NITVT, NITVR, NITCPT1, NITCPT2, NITCPT3, NITSTAT, NITDPC1, NITDPCIV) VALUES (#1, #2, #3, #4, #5, NULL, 0, 0, 0, 0, 0, 'EN COURS', 0, 0)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4) ;
		handleSqlStatus();
		
	}
	Paragraph F0BZZ65= new Paragraph(this);
	public void F0BZZ65() {
	
		
		// FileName: F0BZZ65 (178) :
		m_sql = sql("SELECT MAX(NUMSEQ) FROM TINTAVA  WHERE ID_INTERL = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0BZZ65 (240) :
		m_sql = sql("INSERT INTO TINTAVA (ID_INTER_AVAL, ID_INTERL, NAT_INTERF, NUMSEQ) VALUES (#1, #2, #3, #4)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3) ;
		handleSqlStatus();
		
		// FileName: F0BZZ65 (260) :
		m_sql = sql("INSERT INTO TINTAVA (ID_INTER_AVAL, ID_INTERL, NITUF, NIT, ETAPE, NAT_INTERF, DATE_ENVOI, VRGS, NBENR, NUMSEQ) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9) ;
		handleSqlStatus();
		
		// FileName: F0BZZ65 (289) :
		m_sql = sql("UPDATE TINTAVA SET NITUF = #1, NIT = #2, ETAPE = #3, DATE_ENVOI = CURRENT TIMESTAMP, VRGS = #4, NBENR = #5 WHERE ID_INTER_AVAL = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.param(6, varParam0);
		handleSqlStatus();
		
		// FileName: F0BZZ65 (390) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'ID_INTER_AVAL'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0BZZ65 (395) :
		m_sql = sql("UPDATE TGESTID SET V_ID = #1 WHERE C_TYPE_ID = 'ID_INTER_AVAL'")
			.value(1, varValue0);
		handleSqlStatus();
		
	}
	Paragraph F0XMAJCI= new Paragraph(this);
	public void F0XMAJCI() {
	
		
		// FileName: F0XMAJCI (1313) :
		m_sql = sql("INSERT INTO TINTERF (ID_MVT, NIT, VRGS, COPBE, TYPIMA, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, ID_BLOC, BLOC_SEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, OD_NNAT, OD_NSIM, OD_DFVAL, OD_L4, OD_CDPARU, OD_CLPARU, OD_SERV_TEL, OD_EQUIP_TEL, COMMENTAIRE, DENOM_SUP, DENOM_ADD) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0XMAJCI (1335) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XMAJCI (1370) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0XMAJCI (1426) :
		m_sql = sql("INSERT INTO TLIGPAR (ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63) ;
		handleSqlStatus();
		
		// FileName: F0XMAJCI (1510) :
		m_sql = sql("SELECT ORIG_BLOC, CDPARU, CLPARU, C_TYPE_CLIENT, IND_PROT, NBRE_INSC, NBRE_OPER FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (1530) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (1555) :
		m_sql = sql("SELECT ORIG_BLOC, CDPARU, CLPARU, C_TYPE_CLIENT, IND_PROT, NBRE_INSC, NBRE_OPER FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (1694) :
		m_sql = sql("SELECT NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (1707) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (1858) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = 'REGLE AUTO' WHERE ID_BLOC = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJCI (2136) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2183) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2230) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2287) :
		m_sql = sql("DELETE FROM TLIGPAR WHERE ID_LIGPAR = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XMAJCI (2325) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2340) :
		m_sql = sql("SELECT ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2355) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2485) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2500) :
		m_sql = sql("SELECT ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2514) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2696) :
		m_sql = sql("SELECT COUNT(* ) FROM TINSBL A INNER JOIN TLIGPAR B ON A.ID_LIGPAR = B.ID_LIGPAR  WHERE A.ID_BLOC = #1 AND B.PODA = 'O'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJCI (2712) :
		m_sql = sql("UPDATE TLIGPAR SET PODA = #1 WHERE ID_LIGPAR = #2")
			.value(1, varValue0)
			.param(2, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJCI (2749) :
		m_sql = sql("UPDATE TLIGPAR SET ARP = #1, NNAT = #2, NSIM = #3, NNATN = ' ', DDMAJO = #4, COPO = #5, DDMAJE = #6 #7, NIG = ' ', NIGSEQ = 0 WHERE ID_LIGPAR = #8")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.param(8, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJCI (2826) :
		m_sql = sql("INSERT INTO THDENOM (ID_MVT, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XMAJCI (2899) :
		m_sql = sql("INSERT INTO TDIFFUS (ID_MVT, C_ERREUR, ID_BLOC, BLOC_SEQ, DT_DIFFUS, ORIG_BLOC, DDMAJB, NBRE_INSC, NBRE_OPER) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8) ;
		handleSqlStatus();
		
		// FileName: F0XMAJCI (2946) :
		m_sql = sql("INSERT INTO TERRHIS (ID_MVT, C_ERREUR) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
	}
	Paragraph F0XMAJGI= new Paragraph(this);
	public void F0XMAJGI() {
	
		
		// FileName: F0XMAJGI (350) :
		m_sql = sql("SELECT A.ID_BLOC, BLOC_SEQ, ORIG_BLOC, NBRE_INSC, NBRE_OPER, IND_PROT FROM TINSBL A, TBLOPAR B, TLIGPAR C  WHERE C.CDPARU = #1 AND C.CLPARU = #2 AND C.C_TYPE_CLIENT = 'O' AND A.ID_BLOC = B.ID_BLOC AND A.ID_LIGPAR = C.ID_LIGPAR AND BLOC_SEQ = 1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XMAJGI (543) :
		m_sql = sql("SELECT ID_GROUPE FROM TREGINS  WHERE ID_GROUPE = #1 AND CDINSTAL = #2 AND CLINSTAL = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XMAJGI (640) :
		try
		{
			m_cur = cursorOpen(cur$csiret, "SELECT BL.ID_BLOC, BL.ORIG_BLOC, BL.NBRE_OPER, BL.NBRE_INSC, BL.IND_PROT FROM TLIGPAR LI, TINSBL IB, TBLOPAR BL  WHERE LI.ID_GROUPE = #1 AND LI.ID_LIGPAR <> #2 AND LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = BL.ID_BLOC AND LI.FORME_INTIT = #3 AND REPLACE(REPLACE(TRANSLATE(LI.NOM, #4, #5), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#6, #7, #8), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.PRENOM, #9, #10), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#11, #12, #13), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.CDN, #14, #15), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#16, #17, #18), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.MC, #19, #20), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#21, #22, #23), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.MDET, #24, #25), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#26, #27, #28), '   ', ' '), '  ', ' ') AND LI.C_TYPE_CLIENT = #29 AND LI.CDPARU = #30 AND LI.CLPARU = #31 AND((REPLACE(REPLACE(TRANSLATE(LI.ADR_L3, #32, #33), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#34, #35, #36), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L4, #37, #38), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#39, #40, #41), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L5, #42, #43), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#44, #45, #46), '   ', ' '), '  ', ' ')) OR(SUBSTR(LI.CSIRET, 1, 9) = #47)) AND NOT EXISTS(SELECT XLI.ID_LIGPAR FROM TLIGPAR XLI, TINSBL XIB WHERE XIB.ID_BLOC = BL.ID_BLOC AND XIB.ID_LIGPAR = XLI.ID_LIGPAR AND XLI.ID_LIGPAR <> #48 AND((XLI.PAR_NAT <> #49) OR(XLI.PAR_DEP <> #50)))")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7)
			.param(9, varParam8)
			.param(10, varParam9)
			.param(11, varParam10)
			.param(12, varParam11)
			.param(13, varParam12)
			.param(14, varParam13)
			.param(15, varParam14)
			.param(16, varParam15)
			.param(17, varParam16)
			.param(18, varParam17)
			.param(19, varParam18)
			.param(20, varParam19)
			.param(21, varParam20)
			.param(22, varParam21)
			.param(23, varParam22)
			.param(24, varParam23)
			.param(25, varParam24)
			.param(26, varParam25)
			.param(27, varParam26)
			.param(28, varParam27)
			.param(29, varParam28)
			.param(30, varParam29)
			.param(31, varParam30)
			.param(32, varParam31)
			.param(33, varParam32)
			.param(34, varParam33)
			.param(35, varParam34)
			.param(36, varParam35)
			.param(37, varParam36)
			.param(38, varParam37)
			.param(39, varParam38)
			.param(40, varParam39)
			.param(41, varParam40)
			.param(42, varParam41)
			.param(43, varParam42)
			.param(44, varParam43)
			.param(45, varParam44)
			.param(46, varParam45)
			.param(47, varParam46)
			.param(48, varParam47)
			.param(49, varParam48)
			.param(50, varParam49) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJGI (756) :
		cursorClose(cur$csiret);
		
		// FileName: F0XMAJGI (846) :
		try
		{
			m_cur = cursorOpen(cur$csiret2, "SELECT BL.ID_BLOC, BL.ORIG_BLOC, BL.NBRE_OPER, BL.NBRE_INSC, BL.IND_PROT, LI.MDET FROM TLIGPAR LI, TINSBL IB, TBLOPAR BL  WHERE LI.ID_GROUPE = #1 AND LI.ID_LIGPAR <> #2 AND LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = BL.ID_BLOC AND LI.FORME_INTIT = #3 AND REPLACE(REPLACE(TRANSLATE(LI.NOM, #4, #5), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#6, #7, #8), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.PRENOM, #9, #10), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#11, #12, #13), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.CDN, #14, #15), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#16, #17, #18), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.MC, #19, #20), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#21, #22, #23), '   ', ' '), '  ', ' ') AND LI.C_TYPE_CLIENT = #24 AND LI.CDPARU = #25 AND LI.CLPARU = #26 AND((REPLACE(REPLACE(TRANSLATE(LI.ADR_L3, #27, #28), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#29, #30, #31), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L4, #32, #33), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#34, #35, #36), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L5, #37, #38), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#39, #40, #41), '   ', ' '), '  ', ' ')) OR(SUBSTR(LI.CSIRET, 1, 9) = #42)) AND NOT EXISTS(SELECT XLI.ID_LIGPAR FROM TLIGPAR XLI, TINSBL XIB WHERE XIB.ID_BLOC = BL.ID_BLOC AND XIB.ID_LIGPAR = XLI.ID_LIGPAR AND XLI.ID_LIGPAR <> #43 AND((XLI.PAR_NAT <> #44) OR(XLI.PAR_DEP <> #45)))")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7)
			.param(9, varParam8)
			.param(10, varParam9)
			.param(11, varParam10)
			.param(12, varParam11)
			.param(13, varParam12)
			.param(14, varParam13)
			.param(15, varParam14)
			.param(16, varParam15)
			.param(17, varParam16)
			.param(18, varParam17)
			.param(19, varParam18)
			.param(20, varParam19)
			.param(21, varParam20)
			.param(22, varParam21)
			.param(23, varParam22)
			.param(24, varParam23)
			.param(25, varParam24)
			.param(26, varParam25)
			.param(27, varParam26)
			.param(28, varParam27)
			.param(29, varParam28)
			.param(30, varParam29)
			.param(31, varParam30)
			.param(32, varParam31)
			.param(33, varParam32)
			.param(34, varParam33)
			.param(35, varParam34)
			.param(36, varParam35)
			.param(37, varParam36)
			.param(38, varParam37)
			.param(39, varParam38)
			.param(40, varParam39)
			.param(41, varParam40)
			.param(42, varParam41)
			.param(43, varParam42)
			.param(44, varParam43)
			.param(45, varParam44) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJGI (950) :
		cursorClose(cur$csiret2);
		
		// FileName: F0XMAJGI (996) :
		try
		{
			m_cur = cursorOpen(cur$residentiel, "SELECT BL.ID_BLOC, BL.ORIG_BLOC, BL.NBRE_OPER, BL.NBRE_INSC, BL.IND_PROT FROM TLIGPAR LI, TINSBL IB, TBLOPAR BL  WHERE LI.ID_GROUPE = #1 AND LI.ID_LIGPAR <> #2 AND LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = BL.ID_BLOC AND LI.FORME_INTIT = #3 AND REPLACE(REPLACE(TRANSLATE(LI.NOM, #4, #5), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#6, #7, #8), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.PRENOM, #9, #10), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#11, #12, #13), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.CDN, #14, #15), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#16, #17, #18), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.MC, #19, #20), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#21, #22, #23), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.MDET, #24, #25), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#26, #27, #28), '   ', ' '), '  ', ' ') AND LI.C_TYPE_CLIENT = #29 AND LI.CDPARU = #30 AND LI.CLPARU = #31 AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L3, #32, #33), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#34, #35, #36), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L4, #37, #38), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#39, #40, #41), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L5, #42, #43), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#44, #45, #46), '   ', ' '), '  ', ' ') AND NOT EXISTS(SELECT XLI.ID_LIGPAR FROM TLIGPAR XLI, TINSBL XIB WHERE XIB.ID_BLOC = BL.ID_BLOC AND XIB.ID_LIGPAR = XLI.ID_LIGPAR AND XLI.ID_LIGPAR <> #47 AND((XLI.PAR_NAT <> #48) OR(XLI.PAR_DEP <> #49)))")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7)
			.param(9, varParam8)
			.param(10, varParam9)
			.param(11, varParam10)
			.param(12, varParam11)
			.param(13, varParam12)
			.param(14, varParam13)
			.param(15, varParam14)
			.param(16, varParam15)
			.param(17, varParam16)
			.param(18, varParam17)
			.param(19, varParam18)
			.param(20, varParam19)
			.param(21, varParam20)
			.param(22, varParam21)
			.param(23, varParam22)
			.param(24, varParam23)
			.param(25, varParam24)
			.param(26, varParam25)
			.param(27, varParam26)
			.param(28, varParam27)
			.param(29, varParam28)
			.param(30, varParam29)
			.param(31, varParam30)
			.param(32, varParam31)
			.param(33, varParam32)
			.param(34, varParam33)
			.param(35, varParam34)
			.param(36, varParam35)
			.param(37, varParam36)
			.param(38, varParam37)
			.param(39, varParam38)
			.param(40, varParam39)
			.param(41, varParam40)
			.param(42, varParam41)
			.param(43, varParam42)
			.param(44, varParam43)
			.param(45, varParam44)
			.param(46, varParam45)
			.param(47, varParam46)
			.param(48, varParam47)
			.param(49, varParam48) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJGI (1109) :
		cursorClose(cur$residentiel);
		
		// FileName: F0XMAJGI (1167) :
		try
		{
			m_cur = cursorOpen(cur$residentiel2, "SELECT BL.ID_BLOC, BL.ORIG_BLOC, BL.NBRE_OPER, BL.NBRE_INSC, BL.IND_PROT, LI.MDET FROM TLIGPAR LI, TINSBL IB, TBLOPAR BL  WHERE LI.ID_GROUPE = #1 AND LI.ID_LIGPAR <> #2 AND LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = BL.ID_BLOC AND LI.FORME_INTIT = #3 AND REPLACE(REPLACE(TRANSLATE(LI.NOM, #4, #5), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#6, #7, #8), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.PRENOM, #9, #10), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#11, #12, #13), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.CDN, #14, #15), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#16, #17, #18), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.MC, #19, #20), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#21, #22, #23), '   ', ' '), '  ', ' ') AND LI.C_TYPE_CLIENT = #24 AND LI.CDPARU = #25 AND LI.CLPARU = #26 AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L3, #27, #28), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#29, #30, #31), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L4, #32, #33), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#34, #35, #36), '   ', ' '), '  ', ' ') AND REPLACE(REPLACE(TRANSLATE(LI.ADR_L5, #37, #38), '   ', ' '), '  ', ' ') = REPLACE(REPLACE(TRANSLATE(#39, #40, #41), '   ', ' '), '  ', ' ') AND NOT EXISTS(SELECT XLI.ID_LIGPAR FROM TLIGPAR XLI, TINSBL XIB WHERE XIB.ID_BLOC = BL.ID_BLOC AND XIB.ID_LIGPAR = XLI.ID_LIGPAR AND XLI.ID_LIGPAR <> #42 AND((XLI.PAR_NAT <> #43) OR(XLI.PAR_DEP <> #44)))")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7)
			.param(9, varParam8)
			.param(10, varParam9)
			.param(11, varParam10)
			.param(12, varParam11)
			.param(13, varParam12)
			.param(14, varParam13)
			.param(15, varParam14)
			.param(16, varParam15)
			.param(17, varParam16)
			.param(18, varParam17)
			.param(19, varParam18)
			.param(20, varParam19)
			.param(21, varParam20)
			.param(22, varParam21)
			.param(23, varParam22)
			.param(24, varParam23)
			.param(25, varParam24)
			.param(26, varParam25)
			.param(27, varParam26)
			.param(28, varParam27)
			.param(29, varParam28)
			.param(30, varParam29)
			.param(31, varParam30)
			.param(32, varParam31)
			.param(33, varParam32)
			.param(34, varParam33)
			.param(35, varParam34)
			.param(36, varParam35)
			.param(37, varParam36)
			.param(38, varParam37)
			.param(39, varParam38)
			.param(40, varParam39)
			.param(41, varParam40)
			.param(42, varParam41)
			.param(43, varParam42)
			.param(44, varParam43) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJGI (1267) :
		cursorClose(cur$residentiel2);
		
	}
	Paragraph F0XMAJMI= new Paragraph(this);
	public void F0XMAJMI() {
	
		
		// FileName: F0XMAJMI (1252) :
		m_sql = sql("SELECT NBRE_INSC, NBRE_OPER FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (1344) :
		m_sql = sql("SELECT ORIG_BLOC, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (1432) :
		try
		{
			m_cur = cursorOpen(curblo, "SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR <> #2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJMI (1461) :
		cursorClose(curblo);
		
		// FileName: F0XMAJMI (1576) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (1588) :
		m_sql = sql("SELECT ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (1623) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (1635) :
		m_sql = sql("SELECT ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (1686) :
		m_sql = sql("SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (1757) :
		try
		{
			m_cur = cursorOpen(curaut, "SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR <> #2 AND BLOC_SEQ > #3 - 1")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJMI (1780) :
		cursorClose(curaut);
		
		// FileName: F0XMAJMI (1898) :
		m_sql = sql("SELECT NBRE_INSC, NBRE_OPER, ORIG_BLOC, IND_PROT, C_TYPE_CLIENT FROM TBLOPAR  WHERE ID_BLOC = #1")
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (3124) :
		m_sql = sql("SELECT ORIG_BLOC, CDPARU, CLPARU, NBRE_INSC, C_TYPE_CLIENT, NBRE_OPER FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (3176) :
		m_sql = sql("SELECT NBRE_INSC, NBRE_OPER, ORIG_BLOC, IND_PROT, C_TYPE_CLIENT FROM TBLOPAR  WHERE ID_BLOC = #1")
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (3220) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3234) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3260) :
		m_sql = sql("INSERT INTO TINTERF (ID_MVT, NIT, VRGS, COPBE, TYPIMA, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, ID_BLOC, BLOC_SEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, OD_NNAT, OD_NSIM, OD_DFVAL, OD_L4, OD_CDPARU, OD_CLPARU, OD_SERV_TEL, OD_EQUIP_TEL, COMMENTAIRE, DENOM_SUP, DENOM_ADD) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3320) :
		m_sql = sql("UPDATE TLIGPAR SET ARP = #1, CTIC = #2, NOM = #3, PRENOM = #4, CDN = #5, MC = #6, DL = #7, MDET = #8, EMAIL = #9, ADR_L2 = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #10 END, ADR_L3 = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #11 END, ADR_L4 = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #12 END, ADR_L5 = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #13 END, CVCPA = CASE C_TYPE_CLIENT WHEN 'O' THEN 0 ELSE #14 END, NUM_RUE = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #15 END, CNUM = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #16 END, TDVR = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #17 END, NOMV = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #18 END, ARD = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #19 END, BP = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #20 END, CEDEX = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #21 END, FORME_ADR = CASE C_TYPE_CLIENT WHEN 'O' THEN ' ' ELSE #22 END, CODE_POSTAL = #23, CDINSTAL = #24, CLINSTAL = #25, COP_ART = #26, C_SERV_TEL = #27, C_EQUIP_TEL = #28, C_TARIF_TEL = #29, CRPROF = #30, NAF = #31, CSIRET = #32, C_TYPE_CLIENT = #33, CELOC = #34, NNATN = #35, D_EFFET = #36 #37, DDMAJO = #38, COPO = #39, DDMAJE = #40 #41, PAR_NAT = #42, PAR_DEP = #43, FORME_NUM = #44, FORME_INTIT = #45, PODA = #46, ID_GROUPE = #47, CATPAR = #48, NIG = #49, NIGSEQ = #50, OPP_COMM = #51, OPP_PARUT = #52, OPP_RECH = #53, TYPE_PROT = #54, MARQ_PROT = #55, ORIG_INSC = #56, COMMENTAIRE = #57, ARD_PARU = #58, CPPARU = #59 WHERE ID_LIGPAR = #60")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.param(60, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3434) :
		m_sql = sql("DELETE FROM TLIGPAR WHERE ID_LIGPAR = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3471) :
		m_sql = sql("INSERT INTO TLIGPAR (ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3541) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (3559) :
		m_sql = sql("DELETE FROM TINSBL WHERE ID_LIGPAR = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3581) :
		m_sql = sql("INSERT INTO TINSBL (ID_BLOC, ID_LIGPAR, BLOC_SEQ) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3629) :
		m_sql = sql("UPDATE TLIGPAR SET DDMAJO = #1, COPO = #2, DDMAJE = #3 #4, OPP_RECH = #5 WHERE ID_LIGPAR = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.param(6, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3664) :
		m_sql = sql("UPDATE TLIGPAR SET DDMAJO = #1, COPO = #2, DDMAJE = #3 #4, OPP_PARUT = #5 WHERE ID_LIGPAR = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.param(6, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJMI (3683) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (3702) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (3755) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4330) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4350) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4367) :
		m_sql = sql("SELECT ID_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4381) :
		m_sql = sql("SELECT NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4394) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4539) :
		m_sql = sql("SELECT CHAR(CURRENT DATE, EUR) FROM SYSIBM.SYSDUMMY1  WHERE EXISTS(SELECT LI.ID_LIGPAR FROM TLIGPAR LI, TINSBL IB WHERE LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = #1 AND IB.ID_LIGPAR <> #2 AND((LI.FORME_INTIT <> #3) OR(LI.PAR_NAT <> #4) OR(LI.PAR_DEP <> #5)))")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4609) :
		m_sql = sql("UPDATE TBLOPAR SET NBRE_OPER =( SELECT COUNT( DISTINCT X.COP_ART ) FROM TLIGPAR X, TINSBL Y WHERE Y.ID_BLOC = #1 AND Y.ID_LIGPAR = X.ID_LIGPAR ) WHERE ID_BLOC = #2")
			.param(1, varParam0)
			.param(2, varParam1);
		handleSqlStatus();
		
		// FileName: F0XMAJMI (4655) :
		m_sql = sql("SELECT CHAR(CURRENT DATE, EUR) FROM SYSIBM.SYSDUMMY1  WHERE EXISTS(SELECT A.ID_LIGPAR FROM TINSBL A, TLIGPAR B WHERE A.ID_BLOC = #1 AND A.ID_LIGPAR = B.ID_LIGPAR AND A.ID_LIGPAR <> #2 AND B.PODA = #3)")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XMAJMI (4707) :
		try
		{
			m_cur = cursorOpen(curs$poda, "SELECT IB.ID_LIGPAR, IB.BLOC_SEQ FROM TINSBL IB, TLIGPAR LI  WHERE LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = #1 AND LI.ID_LIGPAR <> #2 AND LI.PODA = 'N'")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XMAJMI (4801) :
		m_sql = sql("UPDATE TLIGPAR SET PODA = 'O' WHERE ID_LIGPAR = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
		
		// FileName: F0XMAJMI (4853) :
		cursorClose(curs$poda);
		
		// FileName: F0XMAJMI (4988) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = 'REGLE AUTO' WHERE ID_BLOC = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJMI (5338) :
		m_sql = sql("INSERT INTO THDENOM (ID_MVT, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (5361) :
		m_sql = sql("INSERT INTO TDIFFUS (ID_MVT, C_ERREUR, ID_BLOC, BLOC_SEQ, DT_DIFFUS, ORIG_BLOC, DDMAJB, NBRE_INSC, NBRE_OPER) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8) ;
		handleSqlStatus();
		
		// FileName: F0XMAJMI (5408) :
		m_sql = sql("INSERT INTO TERRHIS (ID_MVT, C_ERREUR) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
	}
	Paragraph F0XMAJRI= new Paragraph(this);
	public void F0XMAJRI() {
	
		
		// FileName: F0XMAJRI (1413) :
		m_sql = sql("SELECT A.ID_LIGPAR FROM TINSBL A, TLIGPAR B  WHERE B.CDPARU = #1 AND B.CLPARU = #2 AND B.C_TYPE_CLIENT = 'O' AND A.ID_LIGPAR = B.ID_LIGPAR AND A.BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (1898) :
		try
		{
			m_cur = cursorOpen(csrblo, "SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR <> #2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XMAJRI (1941) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (2003) :
		m_sql = sql("UPDATE TLIGPAR SET CDPARU = #1, CLPARU = #2, NOM = #3, PRENOM = #4, CDN = #5, MC = #6, MDET = #7, CELOC = #8, D_EFFET = #9 #10, DDMAJO = #11, DDMAJE = #12 #13, NIG = '       ', NIGSEQ = 0 WHERE ID_LIGPAR = #14")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.param(14, varParam0);
		handleSqlStatus();
		
		
		// FileName: F0XMAJRI (2076) :
		cursorClose(csrblo);
		
		// FileName: F0XMAJRI (2532) :
		m_sql = sql("SELECT NBRE_INSC, NBRE_OPER, IND_PROT, C_TYPE_CLIENT FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (3274) :
		m_sql = sql("SELECT IB.ID_BLOC, IB.BLOC_SEQ, BL.ORIG_BLOC, BL.NBRE_INSC, BL.NBRE_OPER, BL.C_TYPE_CLIENT, BL.CDPARU, BL.CLPARU, BL.IND_PROT FROM TINSBL IB, TBLOPAR BL  WHERE IB.ID_LIGPAR = #1 AND IB.ID_BLOC = BL.ID_BLOC")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (3523) :
		try
		{
			m_cur = cursorOpen(cur$dm11, "SELECT NOM, PRENOM, CDN, MC, MDET FROM TLIGPAR LI, TINSBL IB  WHERE LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = #1 AND LI.ID_LIGPAR <> #2 AND((LI.FORME_INTIT <> #3) OR(LI.C_TYPE_CLIENT <> #4) OR(LI.NOM <> #5) OR(LI.PRENOM <> #6) OR(LI.CDN <> #7) OR(LI.MC <> #8) OR(LI.MDET <> #9) OR(LI.CDPARU <> #10) OR(LI.CLPARU <> #11) OR(LI.PAR_NAT <> #12) OR(LI.PAR_DEP <> #13))")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7)
			.param(9, varParam8)
			.param(10, varParam9)
			.param(11, varParam10)
			.param(12, varParam11)
			.param(13, varParam12) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJRI (3626) :
		cursorClose(cur$dm11);
		
		// FileName: F0XMAJRI (3746) :
		m_sql = sql("INSERT INTO TMVREC (ID_MVT, NIT, IND_VALIDE, IND_EFFICACE, C_ERREUR, ID_INTERL, DATE_MVT, IND_MODIF, C_MVT, ARP, NNAT, NSIM, CDPARUX, LPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTALX, LINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, LRPROF, NAF, CSIRET, C_TYPE_CLIENT, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, ADRTS, DEF, DFVAL8, PAR_NAT, PAR_DEP, FORME_NUM, LFORMAT, FORME_INTIT, FORME_ADR, CELOC, CATPAR, OPP_COMM, OPP_PARUT, OPP_RECH, COP_PORT, IND_PORT, ID_FICHIER, ID_FICMVT, ARD_PARU, CPPARU, DENOM_SUP, DENOM_ADD, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (4035) :
		m_sql = sql("UPDATE TLIGPAR SET DDMAJO = #1, COPO = #2, DDMAJE = #3 #4, OPP_RECH = #5 WHERE ID_LIGPAR = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.param(6, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJRI (4088) :
		m_sql = sql("UPDATE TLIGPAR SET DDMAJO = #1, COPO = #2, DDMAJE = #3 #4, OPP_PARUT = #5 WHERE ID_LIGPAR = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.param(6, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJRI (4169) :
		m_sql = sql("SELECT IND_PROT, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (4417) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = 'REGLE AUTO' WHERE ID_BLOC = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJRI (4468) :
		m_sql = sql("SELECT LI.COP_ART, LI.CTIC, LI.NOM, LI.PRENOM, LI.CDN, LI.MC, LI.MDET, LI.CDPARU, LI.CLPARU, LI.ID_GROUPE, LI.PODA, RE.CDINSTAL, RE.CLINSTAL, LI.NUM_RUE, LI.CNUM, LI.NOMV, LI.CODE_POSTAL, LI.CSIRET, LI.CRPROF, LI.CATPAR, LI.C_TYPE_CLIENT, LI.ID_LIGPAR FROM TLIGPAR LI, TREGINS RE  WHERE LI.NNAT = #1 AND LI.NSIM = #2 AND LI.ID_GROUPE = RE.ID_GROUPE")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (4525) :
		m_sql = sql("SELECT BL.ID_BLOC, BL.ORIG_BLOC, BL.NBRE_INSC, BL.NBRE_OPER, IB.BLOC_SEQ, BL.IND_PROT FROM TINSBL IB, TBLOPAR BL  WHERE IB.ID_LIGPAR = #1 AND BL.ID_BLOC = IB.ID_BLOC")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (4698) :
		m_sql = sql("SELECT ID_GROUPE FROM TREGINS  WHERE ID_GROUPE = #1 AND CDINSTAL = #2 AND CLINSTAL = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (4980) :
		m_sql = sql("INSERT INTO TLIGPAR (ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (5196) :
		m_sql = sql("SELECT CHAR(CURRENT DATE, EUR) FROM SYSIBM.SYSDUMMY1  WHERE EXISTS(SELECT LI.ID_LIGPAR FROM TLIGPAR LI, TINSBL IB WHERE LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = #1 AND((LI.FORME_INTIT <> #2) OR(LI.C_TYPE_CLIENT <> #3)) AND IB.ID_LIGPAR <> #4)")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (5251) :
		m_sql = sql("SELECT CHAR(CURRENT DATE, EUR) FROM SYSIBM.SYSDUMMY1  WHERE EXISTS(SELECT LI.ID_LIGPAR FROM TLIGPAR LI, TINSBL IB WHERE LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = #1 AND IB.ID_LIGPAR <> #2 AND((LI.PAR_NAT <> #3) OR(LI.PAR_DEP <> #4)))")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (5304) :
		m_sql = sql("SELECT CHAR(CURRENT DATE, EUR) FROM SYSIBM.SYSDUMMY1  WHERE EXISTS(SELECT LI.ID_LIGPAR FROM TLIGPAR LI, TINSBL IB WHERE LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = #1 AND IB.ID_LIGPAR <> #2 AND((LI.FORME_INTIT <> #3) OR(REPLACE(REPLACE(TRANSLATE(LI.NOM, #4, #5), '   ', ' '), '  ', ' ') <> REPLACE(REPLACE(TRANSLATE(#6, #7, #8), '   ', ' '), '  ', ' ')) OR(REPLACE(REPLACE(TRANSLATE(LI.PRENOM, #9, #10), '   ', ' '), '  ', ' ') <> REPLACE(REPLACE(TRANSLATE(#11, #12, #13), '   ', ' '), '  ', ' ')) OR(REPLACE(REPLACE(TRANSLATE(LI.CDN, #14, #15), '   ', ' '), '  ', ' ') <> REPLACE(REPLACE(TRANSLATE(#16, #17, #18), '   ', ' '), '  ', ' ')) OR(REPLACE(REPLACE(TRANSLATE(LI.MC, #19, #20), '   ', ' '), '  ', ' ') <> REPLACE(REPLACE(TRANSLATE(#21, #22, #23), '   ', ' '), '  ', ' ')) OR(REPLACE(REPLACE(TRANSLATE(LI.MDET, #24, #25), '   ', ' '), '  ', ' ') <> REPLACE(REPLACE(TRANSLATE(#26, #27, #28), '   ', ' '), '  ', ' ')) OR(LI.CDPARU <> #29) OR(LI.CLPARU <> #30) OR(LI.PAR_NAT <> #31) OR(LI.PAR_DEP <> #32)))")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7)
			.param(9, varParam8)
			.param(10, varParam9)
			.param(11, varParam10)
			.param(12, varParam11)
			.param(13, varParam12)
			.param(14, varParam13)
			.param(15, varParam14)
			.param(16, varParam15)
			.param(17, varParam16)
			.param(18, varParam17)
			.param(19, varParam18)
			.param(20, varParam19)
			.param(21, varParam20)
			.param(22, varParam21)
			.param(23, varParam22)
			.param(24, varParam23)
			.param(25, varParam24)
			.param(26, varParam25)
			.param(27, varParam26)
			.param(28, varParam27)
			.param(29, varParam28)
			.param(30, varParam29)
			.param(31, varParam30)
			.param(32, varParam31) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (5570) :
		m_sql = sql("INSERT INTO TINTERF (ID_MVT, NIT, VRGS, COPBE, TYPIMA, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, ID_BLOC, BLOC_SEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, OD_NNAT, OD_NSIM, OD_DFVAL, OD_L4, OD_CDPARU, OD_CLPARU, OD_SERV_TEL, OD_EQUIP_TEL, COMMENTAIRE, DENOM_SUP, DENOM_ADD) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (5905) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (6098) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (6315) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (6383) :
		m_sql = sql("SELECT LI.ID_LIGPAR, LI.ARP, LI.NNAT, LI.NSIM, LI.CDPARU, LI.CLPARU, LI.CTIC, LI.NOM, LI.PRENOM, LI.CDN, LI.MC, LI.DL, LI.MDET, LI.EMAIL, LI.ADR_L2, LI.ADR_L3, LI.ADR_L4, LI.ADR_L5, LI.CVCPA, LI.NUM_RUE, LI.CNUM, LI.TDVR, LI.NOMV, LI.ARD, LI.BP, LI.CODE_POSTAL, LI.CEDEX, LI.CDINSTAL, LI.CLINSTAL, LI.COP_ART, LI.C_SERV_TEL, LI.C_EQUIP_TEL, C_TARIF_TEL, LI.CRPROF, LI.NAF, LI.CSIRET, LI.C_TYPE_CLIENT, LI.CELOC, LI.NNATN, LI.D_EFFET, LI.DDMAJO, LI.COPO, LI.DDMAJE, LI.PAR_NAT, LI.PAR_DEP, LI.FORME_NUM, LI.FORME_INTIT, LI.FORME_ADR, LI.PODA, LI.ID_GROUPE, LI.CATPAR, LI.NIG, LI.NIGSEQ, LI.OPP_COMM, LI.OPP_PARUT, LI.OPP_RECH, LI.TYPE_PROT, LI.MARQ_PROT, LI.ORIG_INSC, LI.COMMENTAIRE, LI.ARD_PARU, LI.CPPARU, GR.CDINSTAL, GR.CLINSTAL FROM TLIGPAR LI, TREGINS GR  WHERE LI.ID_LIGPAR = #1 AND LI.ID_GROUPE = GR.ID_GROUPE")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.into(varInto62)
			.into(varInto63)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (6473) :
		m_sql = sql("SELECT LI.ID_LIGPAR, LI.ARP, LI.CDPARU, LI.CLPARU, LI.CTIC, LI.NOM, LI.PRENOM, LI.CDN, LI.MC, LI.DL, LI.MDET, LI.EMAIL, LI.ADR_L2, LI.ADR_L3, LI.ADR_L4, LI.ADR_L5, LI.CVCPA, LI.NUM_RUE, LI.CNUM, LI.TDVR, LI.NOMV, LI.ARD, LI.BP, LI.CODE_POSTAL, LI.CEDEX, LI.CDINSTAL, LI.CLINSTAL, LI.COP_ART, LI.C_SERV_TEL, LI.C_EQUIP_TEL, C_TARIF_TEL, LI.CRPROF, LI.NAF, LI.CSIRET, LI.C_TYPE_CLIENT, LI.CELOC, LI.NNATN, LI.D_EFFET, LI.DDMAJO, LI.COPO, LI.DDMAJE, LI.PAR_NAT, LI.PAR_DEP, LI.FORME_NUM, LI.FORME_INTIT, LI.FORME_ADR, LI.PODA, LI.ID_GROUPE, LI.CATPAR, LI.NIG, LI.NIGSEQ, LI.OPP_COMM, LI.OPP_PARUT, LI.OPP_RECH, LI.TYPE_PROT, LI.MARQ_PROT, LI.ORIG_INSC, LI.COMMENTAIRE, LI.ARD_PARU, LI.CPPARU, GR.CDINSTAL, GR.CLINSTAL FROM TLIGPAR LI, TREGINS GR  WHERE LI.NNAT = #1 AND LI.NSIM = #2 AND LI.ID_GROUPE = GR.ID_GROUPE")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (6600) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (7036) :
		m_sql = sql("UPDATE TLIGPAR SET ID_GROUPE = #1 WHERE ID_LIGPAR = #2")
			.value(1, varValue0)
			.param(2, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJRI (7126) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (7143) :
		m_sql = sql("SELECT ORIG_BLOC, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (7219) :
		try
		{
			m_cur = cursorOpen(curs$poda, "SELECT IB.ID_LIGPAR, IB.BLOC_SEQ FROM TINSBL IB, TLIGPAR LI  WHERE LI.ID_LIGPAR = IB.ID_LIGPAR AND IB.ID_BLOC = #1 AND LI.ID_LIGPAR <> #2 AND LI.PODA = 'N'")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XMAJRI (7314) :
		m_sql = sql("UPDATE TLIGPAR SET PODA = 'O' WHERE ID_LIGPAR = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
		
		// FileName: F0XMAJRI (7365) :
		cursorClose(curs$poda);
		
		// FileName: F0XMAJRI (7488) :
		try
		{
			m_cur = cursorOpen(curblo, "SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR <> #2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XMAJRI (7509) :
		cursorClose(curblo);
		
		// FileName: F0XMAJRI (8316) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (8390) :
		m_sql = sql("UPDATE TLIGPAR SET ARP = #1, CTIC = #2, NOM = #3, PRENOM = #4, CDN = #5, MC = #6, DL = #7, MDET = #8, EMAIL = #9, ADR_L2 = #10, ADR_L3 = #11, ADR_L4 = #12, ADR_L5 = #13, CVCPA = #14, NUM_RUE = #15, CNUM = #16, TDVR = #17, NOMV = #18, ARD = #19, BP = #20, CODE_POSTAL = #21, CEDEX = #22, CDINSTAL = #23, CLINSTAL = #24, COP_ART = #25, C_SERV_TEL = #26, C_EQUIP_TEL = #27, C_TARIF_TEL = #28, CRPROF = #29, NAF = #30, CSIRET = #31, C_TYPE_CLIENT = #32, CELOC = #33, NNATN = #34, D_EFFET = #35 #36, DDMAJO = #37, COPO = #38, DDMAJE = #39 #40, PAR_NAT = #41, PAR_DEP = #42, FORME_NUM = #43, FORME_INTIT = #44, FORME_ADR = #45, PODA = #46, ID_GROUPE = #47, CATPAR = #48, NIG = #49, NIGSEQ = #50, OPP_COMM = #51, OPP_PARUT = #52, OPP_RECH = #53, TYPE_PROT = #54, MARQ_PROT = #55, ORIG_INSC = #56, COMMENTAIRE = #57, ARD_PARU = #58, CPPARU = #59 WHERE ID_LIGPAR = #60")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.param(60, varParam0);
		handleSqlStatus();
		
		// FileName: F0XMAJRI (8464) :
		m_sql = sql("DELETE FROM TLIGPAR WHERE ID_LIGPAR = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (8482) :
		m_sql = sql("INSERT INTO TLIGPAR (ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (8555) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (8573) :
		m_sql = sql("DELETE FROM TINSBL WHERE ID_LIGPAR = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (8595) :
		m_sql = sql("INSERT INTO TINSBL (ID_BLOC, ID_LIGPAR, BLOC_SEQ) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (8674) :
		m_sql = sql("SELECT CHAR(CURRENT DATE, EUR) FROM SYSIBM.SYSDUMMY1  WHERE EXISTS(SELECT A.ID_LIGPAR FROM TINSBL A, TLIGPAR B WHERE A.ID_BLOC = #1 AND A.ID_LIGPAR = B.ID_LIGPAR AND A.ID_LIGPAR <> #2 AND B.PODA = #3)")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (8701) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJRI (9168) :
		m_sql = sql("INSERT INTO THDENOM (ID_MVT, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (9192) :
		m_sql = sql("INSERT INTO TDIFFUS (ID_MVT, C_ERREUR, ID_BLOC, BLOC_SEQ, DT_DIFFUS, ORIG_BLOC, DDMAJB, NBRE_INSC, NBRE_OPER) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8) ;
		handleSqlStatus();
		
		// FileName: F0XMAJRI (9238) :
		m_sql = sql("INSERT INTO TERRHIS (ID_MVT, C_ERREUR) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
	}
	Paragraph F0XMAJSI= new Paragraph(this);
	public void F0XMAJSI() {
	
		
		// FileName: F0XMAJSI (571) :
		m_sql = sql("SELECT ID_BLOC, BLOC_SEQ FROM TINSBL  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (661) :
		m_sql = sql("DELETE FROM TLIGPAR WHERE ID_LIGPAR = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XMAJSI (680) :
		m_sql = sql("SELECT ORIG_BLOC, NBRE_INSC, C_TYPE_CLIENT, IND_PROT, NBRE_OPER, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (1643) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XMAJSI (1658) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0XMAJSI (1684) :
		m_sql = sql("INSERT INTO TINTERF (ID_MVT, NIT, VRGS, COPBE, TYPIMA, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, ID_BLOC, BLOC_SEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, OD_NNAT, OD_NSIM, OD_DFVAL, OD_L4, OD_CDPARU, OD_CLPARU, OD_SERV_TEL, OD_EQUIP_TEL, COMMENTAIRE, DENOM_SUP, DENOM_ADD) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0XMAJSI (1701) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (1759) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (1842) :
		m_sql = sql("SELECT ID_BLOC FROM TIMPBLO  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (1856) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (1928) :
		m_sql = sql("SELECT NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (1941) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (1997) :
		m_sql = sql("SELECT NOM_REGLE FROM TVALREG  WHERE NOM_REGLE = 'LR' AND SENS_REGLE = 'A' AND VAL_REGLE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XMAJSI (2045) :
		m_sql = sql("INSERT INTO THDENOM (ID_MVT, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XMAJSI (2076) :
		m_sql = sql("INSERT INTO TDIFFUS (ID_MVT, C_ERREUR, ID_BLOC, BLOC_SEQ, DT_DIFFUS, ORIG_BLOC, DDMAJB, NBRE_INSC, NBRE_OPER) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8) ;
		handleSqlStatus();
		
		// FileName: F0XMAJSI (2124) :
		m_sql = sql("INSERT INTO TERRHIS (ID_MVT, C_ERREUR) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
	}
	Paragraph F0XZZ02= new Paragraph(this);
	public void F0XZZ02() {
	
		
		// FileName: F0XZZ02 (757) :
		m_sql = sql("SELECT CODPROD FROM TOPMENT  WHERE C_SERV_TEL = #1 AND C_EQUIP_TEL = #2 AND C_TARIF_TEL = #3 AND COP_ART = #4")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ02 (894) :
		m_sql = sql("SELECT COP_FT FROM TOPTEL  WHERE COP_ART = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ04= new Paragraph(this);
	public void F0XZZ04() {
	
		
		// FileName: F0XZZ04 (1108) :
		m_sql = sql("SELECT LOPER FROM TOPTEL  WHERE COP_ART = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (1147) :
		m_sql = sql("SELECT C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL FROM T24DBE  WHERE EZ = #1 AND EZAB = #2 AND EZABPQ = #3 AND CODPROD = #4")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (1186) :
		m_sql = sql("SELECT C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL FROM TFOPBE  WHERE C_SERV_OP = #1 AND C_EQUIP_OP = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (1225) :
		m_sql = sql("SELECT C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL FROM TFAUBE  WHERE C_SERV_AU = #1 AND C_EQUIP_AU = #2 AND C_TARIF_AU = #3 AND VERSION_AU = 1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (1269) :
		m_sql = sql("SELECT 1 FROM TOPMENT  WHERE COP_ART = #1 AND C_SERV_TEL = #2 AND C_EQUIP_TEL = #3 AND C_TARIF_TEL = #4")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (1324) :
		m_sql = sql("SELECT C_SERV_TEL FROM TSVPLAG  WHERE #1 BETWEEN NNAT_DEB AND NNAT_FIN AND C_SERV_TEL = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (1623) :
		m_sql = sql("SELECT LTIC_LONG FROM TCIVIL  WHERE CTIC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (2019) :
		try
		{
			m_cur = cursorOpen(cvalreg, "SELECT LONG_REGLE, VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'ABUSEDENOM'") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ04 (2049) :
		cursorClose(cvalreg);
		
		// FileName: F0XZZ04 (2663) :
		m_sql = sql("SELECT MDET, NOM FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (3573) :
		m_sql = sql("SELECT L_SERV_TEL FROM TSERTEL  WHERE C_SERV_TEL = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (3606) :
		m_sql = sql("SELECT L_EQUIP_TEL FROM TEQTEL  WHERE C_EQUIP_TEL = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (3639) :
		m_sql = sql("SELECT L_TARIF_TEL FROM TTARTEL  WHERE C_TARIF_TEL = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (3674) :
		m_sql = sql("SELECT NNAT_DEB, NNAT_FIN FROM TTFPLAG  WHERE #1 BETWEEN NNAT_DEB AND NNAT_FIN AND C_TARIF_TEL = #2")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (3711) :
		m_sql = sql("SELECT C_SERV_TEL, C_EQUIP_TEL FROM TSEREQ  WHERE C_SERV_TEL = #1 AND C_EQUIP_TEL = #2")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (4231) :
		m_sql = sql("SELECT L_TYPE_CLIENT FROM TTYPCLI  WHERE C_TYPE_CLIENT = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (4507) :
		m_sql = sql("SELECT NNAT FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (4530) :
		m_sql = sql("SELECT ID_MVT FROM TMVREC  WHERE NNAT = #1 AND IND_VALIDE = 'P' AND IND_EFFICACE = 'D' AND IND_PORT = 'O'")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (4877) :
		m_sql = sql("SELECT LFORMAT FROM TFORNUM  WHERE FORME_NUM = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (4936) :
		m_sql = sql("SELECT FORME_NUM FROM TFORPRO  WHERE C_SERV_TEL = #1 AND C_EQUIP_TEL = #2 AND FORME_NUM = #3 AND CATPAR = #4")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (5052) :
		m_sql = sql("SELECT FORME_NUM FROM TFORPRO  WHERE C_SERV_TEL = #1 AND C_EQUIP_TEL = #2 AND FORME_NUM = #3 AND CATPAR = #4")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (5122) :
		m_sql = sql("SELECT CDINSTAL, CLINSTAL, CSIRET, ADR_L3, ADR_L4, ADR_L5 FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = '0000'")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (5195) :
		m_sql = sql("SELECT FORME_NUM FROM TFORNUM  WHERE LFORMAT = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (5291) :
		m_sql = sql("SELECT FORME_NUM FROM TFORNUM  WHERE LFORMAT = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (5924) :
		m_sql = sql("SELECT 1 FROM TVALREG  WHERE VAL_REGLE = #1 AND NOM_REGLE = #2 AND SENS_REGLE = 'A'")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (5959) :
		m_sql = sql("SELECT 1 FROM TVALREG  WHERE VAL_REGLE = #1 AND NOM_REGLE = #2 AND SENS_REGLE = 'A'")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (6649) :
		m_sql = sql("SELECT NOM, PRENOM, CRPROF, MARQ_PROT, TYPE_PROT, CDINSTAL, CLINSTAL, CSIRET, DL, C_TYPE_CLIENT, CATPAR FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (6675) :
		m_sql = sql("SELECT DEST_ERR, C_TYPE_ERR FROM TLERROR  WHERE C_ERREUR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ04 (6712) :
		m_sql = sql("INSERT INTO GTERR (C_ERREUR, DEST_ERR, C_TYPE_ERR) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XZZ04 (7068) :
		m_sql = sql("SELECT NOM, PRENOM, CDN, MC, DL, MDET, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, NAF, CSIRET, FORME_INTIT, FORME_ADR, CDPARU, CLPARU, C_TYPE_CLIENT, CPPARU FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ05= new Paragraph(this);
	public void F0XZZ05() {
	
		
		// FileName: F0XZZ05 (502) :
		m_sql = sql("SELECT COP_ART FROM TOPTEL  WHERE COP_ART = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1170) :
		m_sql = sql("SELECT NOM_REGLE FROM TVALREG  WHERE NOM_REGLE = 'LR' AND SENS_REGLE = 'A' AND VAL_REGLE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1204) :
		m_sql = sql("SELECT 1 FROM TVALREG  WHERE NOM_REGLE = 'LR' AND SENS_REGLE = 'A' AND VAL_REGLE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1231) :
		m_sql = sql("SELECT COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_TYPE_CLIENT, ORIG_INSC FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1264) :
		m_sql = sql("SELECT COP_ART FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1287) :
		m_sql = sql("SELECT COP_ART FROM TLIGPAR  WHERE NNAT = #1 AND NSIM <> '0000' FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1314) :
		m_sql = sql("SELECT 1 FROM TVALREG  WHERE VAL_REGLE = #1 AND NOM_REGLE = #2 AND SENS_REGLE = 'A'")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1345) :
		m_sql = sql("SELECT ID_MVT, C_MVT, COP_ART FROM TMVREC  WHERE NNAT = #1 AND NSIM = #2 AND IND_VALIDE = 'R' FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1377) :
		m_sql = sql("SELECT COP_ART FROM TOPTEL  WHERE COP_80K = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1386) :
		m_sql = sql("SELECT COP_ART FROM TOPTEL  WHERE COP_ART = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1432) :
		m_sql = sql("SELECT COP_ART, ORIG_INSC FROM TLIGPAR  WHERE NNAT = #1 FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1453) :
		m_sql = sql("SELECT CODPROD FROM TOPMENT  WHERE COP_ART = #1 AND C_SERV_TEL = #2 AND C_EQUIP_TEL = #3 AND C_TARIF_TEL = #4")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1478) :
		m_sql = sql("SELECT ID_MVT FROM TMVREC A  WHERE A.NNAT = #1 AND A.NSIM = #2 AND((A.C_MVT <> 'S') OR(A.C_MVT = 'S' AND A.IND_PORT = 'O')) AND(A.IND_VALIDE = 'R' OR A.IND_EFFICACE = 'Q') AND EXISTS(SELECT B.C_ERREUR FROM TLERROR B WHERE B.C_ERREUR = A.C_ERREUR AND B.DEST_ERR = 'CED') FETCH FIRST 1 ROWS ONLY")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1530) :
		m_sql = sql("SELECT IND_PROT FROM THFAU1  WHERE NNAT = #1 AND ID_FICHIER = #2 AND ID_FICMVT = #3")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XZZ05 (1585) :
		try
		{
			m_cur = cursorOpen(czs, "SELECT NOM_GROUPE, NOM, POS_DEB, LNG, POS_IND FROM TZS  ORDER BY NOM_GROUPE") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ05 (1615) :
		cursorClose(czs);
		
	}
	Paragraph F0XZZ10= new Paragraph(this);
	public void F0XZZ10() {
	
		
		// FileName: F0XZZ10 (219) :
		m_sql = sql("SELECT CPARA, INDSPEC FROM TREFPAR  WHERE CATPAR = #1 AND C_SERV_TEL = #2 AND C_TARIF_TEL = #3 AND PAR_DEP = #4 AND OPP_PARUT = #5 AND C_TYPE_CLIENT = #6 AND FORME_ADR = #7 AND FORME_INTIT = #8")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6)
			.param(8, varParam7) ;
		handleSqlStatus();

		// FileName: F0XZZ10 (241) :
		m_sql = sql("SELECT CPARA, INDSPEC FROM TREFPAR  WHERE CATPAR = #1 AND C_SERV_TEL = '*  ' AND C_TARIF_TEL = '*  ' AND PAR_DEP = '*' AND OPP_PARUT = #2 AND C_TYPE_CLIENT = #3 AND FORME_ADR = #4 AND FORME_INTIT = #5")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
		handleSqlStatus();

		// FileName: F0XZZ10 (83) :
		try
		{
			m_cur = cursorOpen(curs_Trefpar3, "SELECT CPARA, INDSPEC FROM TREFPAR  WHERE CATPAR = #1 AND C_SERV_TEL = #2 AND C_TARIF_TEL = #3 AND PAR_DEP = #4 AND C_TYPE_CLIENT = #5 AND FORME_ADR = #6 AND FORME_INTIT = #7")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5)
			.param(7, varParam6) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ10 (311) :
		cursorClose(curs_Trefpar3);
		
		// FileName: F0XZZ10 (98) :
		try
		{
			m_cur = cursorOpen(curs_Trefpar4, "SELECT CPARA, INDSPEC FROM TREFPAR  WHERE CATPAR = #1 AND C_SERV_TEL = #2 AND C_TARIF_TEL = #3 AND PAR_DEP = #4 AND C_TYPE_CLIENT = #5 AND FORME_INTIT = #6")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4)
			.param(6, varParam5) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ10 (396) :
		cursorClose(curs_Trefpar4);
		
		// FileName: F0XZZ10 (112) :
		try
		{
			m_cur = cursorOpen(curs_Trefpar5, "SELECT CPARA, INDSPEC FROM TREFPAR  WHERE CATPAR = #1 AND C_SERV_TEL = #2 AND C_TARIF_TEL = #3 AND PAR_DEP = #4 AND C_TYPE_CLIENT = #5")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3)
			.param(5, varParam4) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ10 (424) :
		cursorClose(curs_Trefpar5);
		
		// FileName: F0XZZ10 (125) :
		try
		{
			m_cur = cursorOpen(curs_Trefpar6, "SELECT CPARA, INDSPEC FROM TREFPAR  WHERE CATPAR = #1 AND C_SERV_TEL = #2 AND C_TARIF_TEL = #3 AND C_TYPE_CLIENT = #4")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ10 (452) :
		cursorClose(curs_Trefpar6);
		
		// FileName: F0XZZ10 (137) :
		try
		{
			m_cur = cursorOpen(curs_Trefpar7, "SELECT CPARA, INDSPEC FROM TREFPAR  WHERE CATPAR = #1 AND C_SERV_TEL = #2 AND C_TARIF_TEL = #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ10 (480) :
		cursorClose(curs_Trefpar7);
		
		// FileName: F0XZZ10 (148) :
		try
		{
			m_cur = cursorOpen(curs_Trefpar8, "SELECT CPARA, INDSPEC FROM TREFPAR  WHERE C_SERV_TEL = #1 AND C_TARIF_TEL = #2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ10 (518) :
		cursorClose(curs_Trefpar8);
		
	}
	Paragraph F0XZZ14= new Paragraph(this);
	public void F0XZZ14() {
	
		
		// FileName: F0XZZ14 (291) :
		m_sql = sql("INSERT INTO GTMOTS (MOT_MOT) VALUES ()") ;
		handleSqlStatus();
		
		// FileName: F0XZZ14 (356) :
		try
		{
			m_cur = cursorOpen(curstric$crprof, "SELECT DISTINCT CRPROF, NUM_VE FROM TDICAN8, GTMOTS  WHERE PHON_MOT_RPROF = MOT_MOT AND MILLES_VE = #1 GROUP BY CRPROF, TDICAN8.NUM_VE HAVING COUNT(* ) = #2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XZZ14 (390) :
		try
		{
			m_cur = cursorOpen(curs$lar$crprof, "SELECT DISTINCT CRPROF, COUNT(* ), NUM_VE FROM TDICAN8, GTMOTS  WHERE PHON_MOT_RPROF = MOT_MOT AND MILLES_VE = #1 GROUP BY CRPROF, TDICAN8.NUM_VE ORDER BY 2 DESC, CRPROF")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ14 (423) :
		cursorClose(curstric$crprof);
		
		
		// FileName: F0XZZ14 (498) :
		cursorClose(curs$lar$crprof);
		
		// FileName: F0XZZ14 (544) :
		m_sql = sql("DELETE FROM GTMOTS") ;
		handleSqlStatus();
		
	}
	Paragraph F0XZZ21= new Paragraph(this);
	public void F0XZZ21() {
	
		
		// FileName: F0XZZ21 (435) :
		m_sql = sql("SELECT NNAT, NSIM, NOM, PRENOM, CDN, MC, MDET, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, CDPARU, CLPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ21 (466) :
		try
		{
			m_cur = cursorOpen(bloc21, "SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR <> #2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ21 (481) :
		cursorClose(bloc21);
		
		// FileName: F0XZZ21 (611) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ21 (743) :
		m_sql = sql("UPDATE TLIGPAR SET NOM = #1, PRENOM = #2, CDN = #3, MC = #4, MDET = #5, ADR_L3 = #6, ADR_L4 = #7, ADR_L5 = #8, CVCPA = #9, NUM_RUE = #10, CNUM = #11, TDVR = #12, NOMV = #13, DDMAJO = #14, COPO = #15, CDPARU = #16, CLPARU = #17, DDMAJE = #18 #19 WHERE ID_LIGPAR = #20")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.param(20, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ21 (1419) :
		m_sql = sql("INSERT INTO TINTERF (ID_MVT, NIT, VRGS, COPBE, TYPIMA, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, ID_BLOC, BLOC_SEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, OD_NNAT, OD_NSIM, OD_DFVAL, OD_L4, OD_CDPARU, OD_CLPARU, OD_SERV_TEL, OD_EQUIP_TEL, COMMENTAIRE, DENOM_SUP, DENOM_ADD) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0XZZ21 (1432) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XZZ21 (1532) :
		m_sql = sql("INSERT INTO THISTBE (ID_MVT, NIT, PER_HIST, DATE_MVT, DATE_SI_OP, NBAC, ID_INTERL, IND_VALIDE, IND_EFFICACE, C_ERREUR, ORIG_MVT, ID_LIGPAR, COP_ART, C_MVT, C_TYPE_MVT, C_TYPE_CLIENT, NNAT, NSIM, CTIC, DENOM, NOM, PRENOM, CDN, DL, MC, MDET, EMAIL, NAP, CODPROD, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_SERV_AU, C_EQUIP_AU, C_TARIF_AU, C_SERV_OP, C_EQUIP_OP, ENBA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDPARUX, LPARU, CDINSTALX, LINSTAL, CRPROF, LRPROF, NAF, CSIRET, DFVAL8, ADRTS, DEF, NNATI, NSIMI, NNATR, NSIMR, C_POSIT, CNIG, PAR_NAT, PAR_DEP, IND_LR, IND_LOR, IND_LSAF, IND_LRI, IND_INT, IND_LC, IND_AT, IND_AI, IND_SO, FORME_INTIT, FORME_ADR, LFORMAT, PODA, USAGE1, USAGE2, USAGE3, MNEMO, IPAYS, CPARA, INDSPEC, CELOC, IND_PORT, COP_PORT, IND_IPR, IND_ADR, IND_PORT_AU, ACTI_PROF, MVT_ADR_L2, MVT_ADR_L3, MVT_ADR_L4, MVT_ADR_L5, TYPE_PROT, MARQ_PROT, COMMENTAIRE, ID_FICHIER, ID_FICMVT, NOM_EPURE, PRENOM_EPURE, ARD_PARU, CPPARU, CRPROF_OP, LRPROF_OP, PROFESSION) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107) ;
		handleSqlStatus();
		
		// FileName: F0XZZ21 (1949) :
		m_sql = sql("INSERT INTO THDENOM (ID_MVT, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
	}
	Paragraph F0XZZ22= new Paragraph(this);
	public void F0XZZ22() {
	
		
		// FileName: F0XZZ22 (158) :
		try
		{
			m_cur = cursorOpen(curblo, "SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR <> #2 ORDER BY BLOC_SEQ")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ22 (185) :
		cursorClose(curblo);
		
		// FileName: F0XZZ22 (233) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ22 (285) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XZZ22 (609) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'VRGS'")
			.into(varInto0) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ24= new Paragraph(this);
	public void F0XZZ24() {
	
		
		// FileName: F0XZZ24 (298) :
		m_sql = sql("SELECT NNAT, NSIM, COP_ART, ARP, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, C_TYPE_CLIENT, OPP_COMM, OPP_PARUT, OPP_RECH, CDPARU, CLPARU, ARD, BP, CEDEX, CODE_POSTAL, ADR_L3, NUM_RUE, CNUM, TDVR, NOMV, ADR_L5, NAF, CSIRET, NOM, PRENOM, CDN, DL, MC, FORME_INTIT, FORME_ADR, CTIC, CRPROF, MDET, PAR_NAT, PAR_DEP, CDINSTAL, CLINSTAL, ADR_L4, CELOC, PODA, CVCPA FROM TLIGPAR  WHERE NNAT = #1 AND NSIM =(SELECT MIN(NSIM) FROM TLIGPAR WHERE NNAT = #2 AND TRANSLATE(NOM, 'ABCDEFGHIJKLMNOPQRSTUVWXYZAAAAAEEEEIIIIOOOOOUUUUYCN AAAAAEEEEIIIIOOOOOUUUUYCN', 'abcdefghijklmnopqrstuvwxyzâäàáãéêëèíîïìôöòóõûüùúýçñ ÂÄÀÁÃÉÊËÈÍÎÏÌÔÖÒÓÕÛÜÙÚÝÇÑ', ' ') = #3 AND TRANSLATE(PRENOM, 'ABCDEFGHIJKLMNOPQRSTUVWXYZAAAAAEEEEIIIIOOOOOUUUUYCN AAAAAEEEEIIIIOOOOOUUUUYCN', 'abcdefghijklmnopqrstuvwxyzâäàáãéêëèíîïìôöòóõûüùúýçñ ÂÄÀÁÃÉÊËÈÍÎÏÌÔÖÒÓÕÛÜÙÚÝÇÑ', ' ') = #4)")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ24 (775) :
		m_sql = sql("SELECT CODPROD FROM TOPMENT  WHERE C_SERV_TEL = #1 AND C_EQUIP_TEL = #2 AND C_TARIF_TEL = #3 AND COP_ART = #4")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2)
			.param(4, varParam3) ;
		handleSqlStatus();

		// FileName: F0XZZ24 (797) :
		m_sql = sql("SELECT C_SERV_OP, C_EQUIP_OP FROM TFOPBE  WHERE C_SERV_TEL = #1 AND C_EQUIP_TEL = #2 AND C_TARIF_TEL = #3")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ28= new Paragraph(this);
	public void F0XZZ28() {
	
		
		// FileName: F0XZZ28 (173) :
		m_sql = sql("DELETE FROM GTURG") ;
		handleSqlStatus();
		
		// FileName: F0XZZ28 (194) :
		try
		{
			m_cur = cursorOpen(csrlig, "SELECT CAST(VALUE(MIN(LONG_REGLE), 9999) AS SMALLINT), TRANSLATE(UCASE(DL), 'AAAEEEEIIOOUUUC', 'âàäéèêëïîôöùüûç'), B.ID_LIGPAR FROM TINSBL A, TLIGPAR B LEFT OUTER JOIN TVALREG C ON(C.NOM_REGLE = #1 AND C.SENS_REGLE = 'A' AND LOCATE(STRIP(TRANSLATE(UCASE(VAL_REGLE), 'AAAEEEEIIOOUUUC', 'âàäéèêëïîôöùüûç')), STRIP(TRANSLATE(UCASE(DL), 'AAAEEEEIIOOUUUC', 'âàäéèêëïîôöùüûç')), 1) > 0)  WHERE A.ID_BLOC = #2 AND A.ID_LIGPAR = B.ID_LIGPAR GROUP BY B.ID_LIGPAR, DL ORDER BY 1, 2")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XZZ28 (243) :
		m_sql = sql("INSERT INTO GTURG (POIDS, DL, ID_LIGPAR) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		
		// FileName: F0XZZ28 (285) :
		cursorClose(csrlig);
		
		// FileName: F0XZZ28 (323) :
		m_sql = sql("DELETE FROM TINSBL WHERE ID_BLOC = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XZZ28 (339) :
		try
		{
			m_cur = cursorOpen(csrurg, "SELECT POIDS, DL, ID_LIGPAR FROM GTURG  ORDER BY POIDS, DL") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XZZ28 (371) :
		m_sql = sql("INSERT INTO TINSBL (ID_BLOC, ID_LIGPAR, BLOC_SEQ) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		
		// FileName: F0XZZ28 (412) :
		cursorClose(csrurg);
		
		// FileName: F0XZZ28 (426) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = 'REGLE AUTO' WHERE ID_BLOC = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
	}
	Paragraph F0XZZ29= new Paragraph(this);
	public void F0XZZ29() {
	
		
		// FileName: F0XZZ29 (197) :
		try
		{
			m_cur = cursorOpen(cimpblo, "SELECT ID_BLOC FROM TIMPBLO") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ29 (219) :
		cursorClose(cimpblo);
		
		// FileName: F0XZZ29 (231) :
		m_sql = sql("UPDATE TLIGPAR L SET NIG = '       ', NIGSEQ = 0 WHERE L.ID_LIGPAR IN( SELECT DISTINCT A.ID_LIGPAR FROM TINSBL A, TIMPBLO B WHERE A.ID_BLOC = B.ID_BLOC ) AND NIG > '       '");
		handleSqlStatus();
		
		// FileName: F0XZZ29 (252) :
		m_sql = sql("DELETE FROM TIMPBLO") ;
		handleSqlStatus();
		
		// FileName: F0XZZ29 (283) :
		m_sql = sql("SELECT NBRE_INSC, ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ29 (311) :
		try
		{
			m_cur = cursorOpen(cinsbl, "SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 ORDER BY BLOC_SEQ")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ29 (336) :
		cursorClose(cinsbl);
		
		// FileName: F0XZZ29 (390) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ29 (590) :
		m_sql = sql("INSERT INTO TINTERF (ID_MVT, NIT, VRGS, COPBE, TYPIMA, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, ID_BLOC, BLOC_SEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, OD_NNAT, OD_NSIM, OD_DFVAL, OD_L4, OD_CDPARU, OD_CLPARU, OD_SERV_TEL, OD_EQUIP_TEL, COMMENTAIRE, DENOM_SUP, DENOM_ADD) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0XZZ29 (627) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XZZ29 (1082) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'VRGS'")
			.into(varInto0) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ36= new Paragraph(this);
	public void F0XZZ36() {
	
		
		// FileName: F0XZZ36 (94) :
		m_sql = sql("SELECT LONG_REGLE, VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'CARACSPECI'")
			.into(varInto0)
			.into(varInto1) ;
		handleSqlStatus();

		// FileName: F0XZZ36 (114) :
		try
		{
			m_cur = cursorOpen(cvalreg, "SELECT LONG_REGLE, VAL_REGLE FROM TVALREG  WHERE NOM_REGLE = 'MARQINTERD'") ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ36 (160) :
		cursorClose(cvalreg);
		
	}
	Paragraph F0XZZ37= new Paragraph(this);
	public void F0XZZ37() {
	
		
		// FileName: F0XZZ37 (93) :
		m_sql = sql("SELECT NOM, MDET FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ40= new Paragraph(this);
	public void F0XZZ40() {
	
		
		// FileName: F0XZZ40 (296) :
		m_sql = sql("SELECT SENS_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND VAL_REGLE = #2 AND SENS_REGLE LIKE #3")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XZZ40 (324) :
		m_sql = sql("SELECT SENS_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND SENS_REGLE LIKE #2 AND VAL_REGLE2 = #3")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
		handleSqlStatus();

		// FileName: F0XZZ40 (397) :
		try
		{
			m_cur = cursorOpen(ctvalreg3, "SELECT SENS_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND SENS_REGLE LIKE #2 AND VAL_REGLE LIKE #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ40 (424) :
		cursorClose(ctvalreg3);
		
		// FileName: F0XZZ40 (491) :
		try
		{
			m_cur = cursorOpen(ctvalreg4, "SELECT SENS_REGLE, LONG_REGLE, VAL_REGLE, VAL_REGLE2 FROM TVALREG  WHERE NOM_REGLE = #1 AND VAL_REGLE2 LIKE #2 AND SENS_REGLE LIKE #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ40 (518) :
		cursorClose(ctvalreg4);
		
	}
	Paragraph F0XZZ41= new Paragraph(this);
	public void F0XZZ41() {
	
		
		// FileName: F0XZZ41 (202) :
		m_sql = sql("SELECT ID_BLOC, CURRENT_TIMESTAMP FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (215) :
		m_sql = sql("UPDATE TBLOPAR SET IND_PROT = #1, DATE_MAJ_PROT = #2 WHERE ID_BLOC = #3")
			.value(1, varValue0)
			.value(2, varValue1)
			.param(3, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (230) :
		m_sql = sql("SELECT A.ID_BLOC, IND_PROT, DATE_MAJ_PROT FROM TBLOPAR A, TINSBL B  WHERE B.ID_LIGPAR = #1 AND B.ID_BLOC = A.ID_BLOC")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (250) :
		m_sql = sql("SELECT C.ID_BLOC, C.CDPARU, C.CLPARU, C.C_TYPE_CLIENT, C.CSUPPORT, C.ORIG_BLOC, C.ID_ETAB_14T, C.DDMAJB, C.NBRE_INSC, C.IND_PROT, C.DATE_MAJ_PROT FROM TLIGPAR A, TINSBL B, TBLOPAR C  WHERE A.NNAT = #1 AND A.NSIM = #2 AND A.ID_LIGPAR = B.ID_LIGPAR AND B.ID_BLOC = C.ID_BLOC")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (282) :
		m_sql = sql("SELECT B.C_TYPE_CLIENT FROM TINSBL A, TLIGPAR B  WHERE A.ID_BLOC = #1 AND A.BLOC_SEQ = 1 AND B.ID_LIGPAR = A.ID_LIGPAR")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (310) :
		m_sql = sql("SELECT ID_BLOC, CDPARU, CLPARU, C_TYPE_CLIENT, CSUPPORT, ORIG_BLOC, ID_ETAB_14T, DDMAJB, NBRE_INSC, IND_PROT, DATE_MAJ_PROT FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (365) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'ID_BLOC'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (371) :
		m_sql = sql("UPDATE TGESTID SET V_ID = #1 WHERE C_TYPE_ID = 'ID_BLOC'")
			.value(1, varValue0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (378) :
		m_sql = sql("INSERT INTO TBLOPAR (ID_BLOC, CDPARU, CLPARU, C_TYPE_CLIENT, CSUPPORT, ORIG_BLOC, ID_ETAB_14T, DDMAJB, NBRE_INSC, NBRE_OPER, IND_PROT, DATE_MAJ_PROT) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (402) :
		m_sql = sql("INSERT INTO TINSBL (ID_BLOC, ID_LIGPAR, BLOC_SEQ) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (420) :
		m_sql = sql("SELECT ID_BLOC, ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (458) :
		m_sql = sql("UPDATE TBLOPAR SET CDPARU = #1, CLPARU = #2, C_TYPE_CLIENT = #3, CSUPPORT = #4, ORIG_BLOC = #5, ID_ETAB_14T = #6, DDMAJB = #7 WHERE ID_BLOC = #8")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.param(8, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (480) :
		m_sql = sql("SELECT NBRE_INSC, ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (510) :
		m_sql = sql("UPDATE TINSBL SET BLOC_SEQ = BLOC_SEQ + 1 WHERE BLOC_SEQ >= #1 AND ID_BLOC = #2")
			.param(1, varParam0)
			.param(2, varParam1);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (518) :
		m_sql = sql("INSERT INTO TINSBL (ID_BLOC, ID_LIGPAR, BLOC_SEQ) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (537) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = #1, DDMAJB = #2, NBRE_INSC = #3, NBRE_OPER = #4 WHERE ID_BLOC = #5")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.param(5, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (562) :
		m_sql = sql("SELECT ID_BLOC FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (573) :
		m_sql = sql("SELECT NBRE_INSC, ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (594) :
		m_sql = sql("SELECT BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (608) :
		m_sql = sql("UPDATE TINSBL SET BLOC_SEQ = BLOC_SEQ + 1 WHERE ID_BLOC = #1 AND BLOC_SEQ < #2 AND BLOC_SEQ >= #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (615) :
		m_sql = sql("UPDATE TINSBL SET BLOC_SEQ = #1 WHERE ID_BLOC = #2 AND ID_LIGPAR = #3")
			.value(1, varValue0)
			.param(2, varParam0)
			.param(3, varParam1);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (624) :
		m_sql = sql("UPDATE TINSBL SET BLOC_SEQ = BLOC_SEQ - 1 WHERE ID_BLOC = #1 AND BLOC_SEQ <= #2 AND BLOC_SEQ > #3")
			.param(1, varParam0)
			.param(2, varParam1)
			.param(3, varParam2);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (631) :
		m_sql = sql("UPDATE TINSBL SET BLOC_SEQ = #1 WHERE ID_BLOC = #2 AND ID_LIGPAR = #3")
			.value(1, varValue0)
			.param(2, varParam0)
			.param(3, varParam1);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (649) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = #1, DDMAJB = #2 WHERE ID_BLOC = #3")
			.value(1, varValue0)
			.value(2, varValue1)
			.param(3, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (672) :
		m_sql = sql("SELECT ID_BLOC, NBRE_INSC, ORIG_BLOC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (682) :
		m_sql = sql("SELECT BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 AND ID_LIGPAR = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (703) :
		m_sql = sql("DELETE FROM TINSBL WHERE ID_BLOC = #1 AND ID_LIGPAR = #2")
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (719) :
		try
		{
			m_cur = cursorOpen(cursab, "SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ > #2 FOR UPDATE OF BLOC_SEQ")
			.param(1, varParam0)
			.param(2, varParam1) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ41 (730) :
		cursorClose(cursab);
		
		// FileName: F0XZZ41 (735) :
		m_sql = sql("SELECT BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (747) :
		m_sql = sql("DELETE FROM TBLOPAR WHERE ID_BLOC = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (779) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = #1, DDMAJB = #2, NBRE_INSC = #3, NBRE_OPER = #4 WHERE ID_BLOC = #5")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.param(5, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (801) :
		cursorUpdateCurrent(cursab, "UPDATE TINSBL SET BLOC_SEQ = #1 - 1")
			.value(1, varValue0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (817) :
		m_sql = sql("SELECT ID_BLOC, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (831) :
		m_sql = sql("SELECT ID_BLOC, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (855) :
		try
		{
			m_cur = cursorOpen(curstmp, "SELECT ID_LIGPAR, BLOC_SEQ FROM TINSBL  WHERE ID_BLOC = #1 ORDER BY BLOC_SEQ")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ41 (864) :
		cursorClose(curstmp);
		
		// FileName: F0XZZ41 (867) :
		m_sql = sql("DELETE FROM TINSBL WHERE ID_BLOC = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (871) :
		m_sql = sql("DELETE FROM TBLOPAR WHERE ID_BLOC = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (884) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = #1, DDMAJB = #2, NBRE_INSC = #3, NBRE_OPER = #4, IND_PROT = #5, DATE_MAJ_PROT = CURRENT_TIMESTAMP WHERE ID_BLOC = #6")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.param(6, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (908) :
		m_sql = sql("INSERT INTO TINSBL (ID_BLOC, ID_LIGPAR, BLOC_SEQ) VALUES (#1, #2, #3)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (923) :
		m_sql = sql("SELECT DISTINCT ID_GROUPE FROM TLIGPAR  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (935) :
		m_sql = sql("SELECT DISTINCT COUNT(* ) FROM TBLOPAR  WHERE ID_BLOC IN(SELECT ID_BLOC FROM TINSBL WHERE ID_LIGPAR IN(SELECT ID_LIGPAR FROM TLIGPAR WHERE ID_GROUPE = #1))")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (957) :
		m_sql = sql("SELECT PER_HIST FROM TPEHIST  WHERE PER_ACT = 'O'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (991) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1028) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1072) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1114) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1155) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1194) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1234) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1256) :
		m_sql = sql("SELECT ID_BLOC FROM TIMPBLO  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (1266) :
		m_sql = sql("UPDATE TIMPBLO SET C_MVT = #1, ID_LIGPAR = #2, ORIG_BLOC = #3 WHERE ID_BLOC = #4")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1274) :
		m_sql = sql("INSERT INTO TIMPBLO (ID_BLOC, C_MVT, ID_LIGPAR, ORIG_BLOC) VALUES (#1, #2, #3, #4)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3) ;
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1308) :
		m_sql = sql("SELECT COUNT(DISTINCT B.COP_ART) FROM TINSBL A, TLIGPAR B  WHERE A.ID_BLOC = #1 AND A.ID_LIGPAR = B.ID_LIGPAR")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (1362) :
		m_sql = sql("SELECT ORIG_BLOC, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (1387) :
		m_sql = sql("SELECT CURRENT_DATE FROM SYSIBM.SYSDUMMY1  WHERE EXISTS(SELECT X.ID_LIGPAR FROM TINSBL X, TLIGPAR Y WHERE X.ID_BLOC = #1 AND X.ID_LIGPAR = Y.ID_LIGPAR AND Y.C_TYPE_CLIENT = 'O')")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ41 (1404) :
		m_sql = sql("UPDATE TBLOPAR SET ORIG_BLOC = 'Opérateur' WHERE ID_BLOC = #1")
			.param(1, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ41 (1424) :
		try
		{
			m_cur = cursorOpen(csrtri, "SELECT BL.ID_LIGPAR, CAST((CASE WHEN DL.VAL_REGLE IS NULL THEN CONCAT('001000', TRANSLATE(UCASE(LI.DL), 'AAAEEEEIIOOUUUC', 'âàäéèêëïîôöùüûç')) ELSE CONCAT(CONCAT(REPEAT('0',(6 - LENGTH(STRIP(CHAR(DL.LONG_REGLE), TRAILING)))), STRIP(CHAR(DL.LONG_REGLE))), LI.DL) END) AS CHAR(106)) AS CRIT1, LI.NOMV, LI.TDVR, LI.NUM_RUE, LI.CNUM, LI.ADR_L5, LI.ADR_L3, CAST((CASE WHEN LI.C_EQUIP_TEL = 'TC' THEN CONCAT('400', LI.NNAT) WHEN NC.VAL_REGLE IS NOT NULL THEN CONCAT('100', LI.ARP) WHEN SUBSTR(LI.NNAT, 1, 2) >= '07' THEN CONCAT('100', LI.NNAT) WHEN SUBSTR(LI.NNAT, 1, 4) BETWEEN '0860' AND '0876' THEN CONCAT('200', LI.NNAT) WHEN SUBSTR(LI.NNAT, 1, 2) BETWEEN '01' AND '05' THEN CONCAT('200', LI.NNAT) WHEN SUBSTR(LI.NNAT, 1, 2) = '06' THEN CONCAT('300', LI.NNAT) ELSE CONCAT('200', LI.NNAT) END) AS CHAR(63)) AS CRIT3, LI.NNAT, LI.NSIM FROM TINSBL BL, TLIGPAR LI LEFT OUTER JOIN TVALREG NC ON(LI.ARP = NC.VAL_REGLE AND NC.NOM_REGLE = 'NUMCOURT' AND NC.SENS_REGLE = 'A') LEFT OUTER JOIN TVALREG DL ON(LI.DL = DL.VAL_REGLE AND DL.NOM_REGLE = 'PRIORITEDL' AND DL.SENS_REGLE = 'A')  WHERE BL.ID_BLOC = #1 AND BL.ID_LIGPAR = LI.ID_LIGPAR ORDER BY CRIT1, LI.NOMV, LI.TDVR, LI.NUM_RUE, LI.CNUM, LI.ADR_L5, LI.ADR_L3, CRIT3, LI.NNAT, LI.NSIM FOR FETCH ONLY")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XZZ41 (1494) :
		m_sql = sql("UPDATE TINSBL SET BLOC_SEQ = #1 WHERE ID_BLOC = #2 AND ID_LIGPAR = #3")
			.value(1, varValue0)
			.param(2, varParam0)
			.param(3, varParam1);
		handleSqlStatus();
		
		
		// FileName: F0XZZ41 (1504) :
		cursorClose(csrtri);
		
		// FileName: F0XZZ41 (1574) :
		m_sql = sql("INSERT INTO THISBLO (ID_MVT, NIT, PER_HIST, DATE_MVT, ID_BLOC, ID_LIGPAR, BLOC_SEQ, COP_BLOC, ID_INTERL, ORIG_MVT, COMMENTAIRE) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10) ;
		handleSqlStatus();
		
	}
	Paragraph F0XZZ42= new Paragraph(this);
	public void F0XZZ42() {
	
		
		// FileName: F0XZZ42 (137) :
		m_sql = sql("SELECT ID_GROUPE, CDINSTAL, CLINSTAL, C_TYPE_CLIENT, NBRE_INSC, DDMAJG, OR_MAJ, ID_CLIENT_PJ FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (185) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'ID_GROUPE'")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (191) :
		m_sql = sql("UPDATE TGESTID SET V_ID = #1 WHERE C_TYPE_ID = 'ID_GROUPE'")
			.value(1, varValue0);
		handleSqlStatus();
		
		// FileName: F0XZZ42 (197) :
		m_sql = sql("INSERT INTO TREGINS (ID_GROUPE, CDINSTAL, CLINSTAL, C_TYPE_CLIENT, NBRE_INSC, DDMAJG, OR_MAJ, ID_CLIENT_PJ) VALUES (#1, #2, #3, #4, #5, #6, #7, #8)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7) ;
		handleSqlStatus();
		
		// FileName: F0XZZ42 (213) :
		m_sql = sql("SELECT ID_GROUPE FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (231) :
		m_sql = sql("SELECT DISTINCT CDINSTAL FROM TLIGPAR  WHERE ID_GROUPE = #1 AND CDINSTAL <> #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (246) :
		m_sql = sql("SELECT DISTINCT CLINSTAL FROM TLIGPAR  WHERE ID_GROUPE = #1 AND CLINSTAL <> #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (275) :
		m_sql = sql("UPDATE TREGINS SET CDINSTAL = #1, CLINSTAL = #2, C_TYPE_CLIENT = #3, DDMAJG = #4, OR_MAJ = #5, ID_CLIENT_PJ = #6 WHERE ID_GROUPE = #7")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.param(7, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ42 (295) :
		m_sql = sql("SELECT ID_GROUPE, NBRE_INSC, CDINSTAL, CLINSTAL FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (326) :
		m_sql = sql("UPDATE TREGINS SET NBRE_INSC = #1, DDMAJG = #2, OR_MAJ = #3 WHERE ID_GROUPE = #4")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ42 (343) :
		m_sql = sql("SELECT ID_GROUPE, NBRE_INSC FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (365) :
		m_sql = sql("DELETE FROM TREGINS WHERE ID_GROUPE = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XZZ42 (373) :
		m_sql = sql("UPDATE TREGINS SET NBRE_INSC = #1, DDMAJG = #2, OR_MAJ = #3 WHERE ID_GROUPE = #4")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ42 (391) :
		m_sql = sql("SELECT ID_GROUPE FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (405) :
		m_sql = sql("SELECT ID_GROUPE FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (415) :
		m_sql = sql("SELECT CDINSTAL, CLINSTAL FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (421) :
		m_sql = sql("SELECT CDINSTAL, CLINSTAL FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (445) :
		m_sql = sql("SELECT ID_GROUPE FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (455) :
		m_sql = sql("SELECT ID_GROUPE FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (465) :
		m_sql = sql("SELECT CDINSTAL, CLINSTAL FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (471) :
		m_sql = sql("SELECT CDINSTAL, CLINSTAL FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (491) :
		m_sql = sql("SELECT NBRE_INSC FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (497) :
		m_sql = sql("SELECT NBRE_INSC FROM TREGINS  WHERE ID_GROUPE = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ42 (507) :
		m_sql = sql("UPDATE TLIGPAR SET ID_GROUPE = #1 WHERE ID_GROUPE = #2")
			.value(1, varValue0)
			.param(2, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ42 (513) :
		m_sql = sql("DELETE FROM TREGINS WHERE ID_GROUPE = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XZZ42 (522) :
		m_sql = sql("UPDATE TREGINS SET NBRE_INSC = #1, DDMAJG = #2, OR_MAJ = #3 WHERE ID_GROUPE = #4")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.param(4, varParam0);
		handleSqlStatus();
		
		// FileName: F0XZZ42 (539) :
		m_sql = sql("SELECT PER_HIST FROM TPEHIST  WHERE PER_ACT = 'O'")
			.into(varInto0) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ44= new Paragraph(this);
	public void F0XZZ44() {
	
		
		// FileName: F0XZZ44 (299) :
		try
		{
			m_cur = cursorOpen(ctdenom, "SELECT TYPE_DENOM, LIB_DENOM FROM TDENOM  WHERE ID_LIGPAR = #1 ORDER BY TYPE_DENOM ASC, NUM_DENOM ASC")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		
		// FileName: F0XZZ44 (338) :
		cursorClose(ctdenom);
		
		// FileName: F0XZZ44 (468) :
		m_sql = sql("INSERT INTO TDENOM (ID_LIGPAR, NNAT, NSIM, TYPE_DENOM, NUM_DENOM, LIB_DENOM) VALUES (#1, #2, #3, #4, #5, #6)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5) ;
		handleSqlStatus();
		
		// FileName: F0XZZ44 (519) :
		m_sql = sql("DELETE FROM TDENOM WHERE ID_LIGPAR = #1")
			.param(1, varParam0) ;
		handleSqlStatus();
		
		// FileName: F0XZZ44 (631) :
		m_sql = sql("SELECT ID_LIGPAR FROM TLIGPAR  WHERE NNAT = #1 AND NSIM = #2")
			.into(varInto0)
			.param(1, varParam0)
			.param(2, varParam1) ;
		handleSqlStatus();

		// FileName: F0XZZ44 (650) :
		m_sql = sql("SELECT NNAT, NSIM FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

	}
	Paragraph F0XZZ88= new Paragraph(this);
	public void F0XZZ88() {
	
		
		// FileName: F0XZZ88 (228) :
		m_sql = sql("SELECT ORIG_BLOC, NBRE_INSC FROM TBLOPAR  WHERE ID_BLOC = #1")
			.into(varInto0)
			.into(varInto1)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ88 (250) :
		m_sql = sql("SELECT ID_BLOC FROM TIMPBLO  WHERE ID_BLOC = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ88 (265) :
		m_sql = sql("SELECT ID_LIGPAR FROM TINSBL  WHERE ID_BLOC = #1 AND BLOC_SEQ = 1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ88 (332) :
		m_sql = sql("SELECT ID_LIGPAR, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, NIG, NIGSEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, COMMENTAIRE, ARD_PARU, CPPARU FROM TLIGPAR  WHERE ID_LIGPAR = #1")
			.into(varInto0)
			.into(varInto1)
			.into(varInto2)
			.into(varInto3)
			.into(varInto4)
			.into(varInto5)
			.into(varInto6)
			.into(varInto7)
			.into(varInto8)
			.into(varInto9)
			.into(varInto10)
			.into(varInto11)
			.into(varInto12)
			.into(varInto13)
			.into(varInto14)
			.into(varInto15)
			.into(varInto16)
			.into(varInto17)
			.into(varInto18)
			.into(varInto19)
			.into(varInto20)
			.into(varInto21)
			.into(varInto22)
			.into(varInto23)
			.into(varInto24)
			.into(varInto25)
			.into(varInto26)
			.into(varInto27)
			.into(varInto28)
			.into(varInto29)
			.into(varInto30)
			.into(varInto31)
			.into(varInto32)
			.into(varInto33)
			.into(varInto34)
			.into(varInto35)
			.into(varInto36)
			.into(varInto37)
			.into(varInto38)
			.into(varInto39)
			.into(varInto40)
			.into(varInto41)
			.into(varInto42)
			.into(varInto43)
			.into(varInto44)
			.into(varInto45)
			.into(varInto46)
			.into(varInto47)
			.into(varInto48)
			.into(varInto49)
			.into(varInto50)
			.into(varInto51)
			.into(varInto52)
			.into(varInto53)
			.into(varInto54)
			.into(varInto55)
			.into(varInto56)
			.into(varInto57)
			.into(varInto58)
			.into(varInto59)
			.into(varInto60)
			.into(varInto61)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZ88 (382) :
		m_sql = sql("INSERT INTO TINTERF (ID_MVT, NIT, VRGS, COPBE, TYPIMA, ARP, NNAT, NSIM, CDPARU, CLPARU, CTIC, NOM, PRENOM, CDN, MC, DL, MDET, EMAIL, ADR_L2, ADR_L3, ADR_L4, ADR_L5, CVCPA, NUM_RUE, CNUM, TDVR, NOMV, ARD, BP, CODE_POSTAL, CEDEX, CDINSTAL, CLINSTAL, COP_ART, C_SERV_TEL, C_EQUIP_TEL, C_TARIF_TEL, CRPROF, NAF, CSIRET, C_TYPE_CLIENT, CELOC, NNATN, D_EFFET, DDMAJO, COPO, DDMAJE, PAR_NAT, PAR_DEP, FORME_NUM, FORME_INTIT, FORME_ADR, PODA, ID_GROUPE, CATPAR, ID_BLOC, BLOC_SEQ, OPP_COMM, OPP_PARUT, OPP_RECH, TYPE_PROT, MARQ_PROT, ORIG_INSC, OD_NNAT, OD_NSIM, OD_DFVAL, OD_L4, OD_CDPARU, OD_CLPARU, OD_SERV_TEL, OD_EQUIP_TEL, COMMENTAIRE, DENOM_SUP, DENOM_ADD) VALUES (#1, #2)")
			.value(1, varValue0)
			.value(2, varValue1) ;
		handleSqlStatus();
		
		// FileName: F0XZZ88 (417) :
		m_sql = sql("INSERT INTO TIFSBE (NUMITF, NIT, VRGS, COP14D, TYPIMA, COPITF, DESTID, NNAT, NSIM, CDPARU, CLPARU, CVCPA, US, EDS, TYPCLI, OBL, OLD, CSM, CTIC, CGROS, CELOC, PODA, IODM, INDPROT, CDOPE, DDOPE, DDMAJ, CPARA, INDSPEC, TYPABO, CODPROD, INDMENT, MODREC, GROUPE, IPAYS, CDINSTAL, CLINSTAL, CFORMAT, NAP, MNEMO, CRPROF, NIG, NIGSEQ, NLD, NLDCOP, NLDDATD, NNATN, NUMR, CNUM, TDVR, ARD, BP, CP, CX, CVR, CI, EMAIL, CODMES, COP, CMV, NBAC, DET, HEURE, DEFITF, CPARS, TYPRESIL, TORIG, NNATR, NSIMR, NLDCODS, NLDCMV, NNATI, NSIMI, ARRT, TDVRA, NUMCLI, SDATET, INDSTAND, CAPE, CSIRET, SOUSAD1, SOUSAD2, SOUSAD3, SOUSAD4, SOUSAD5, CPS1, CPS2, CPS3, CPS4, CPS5, USAGE1, USAGE2, USAGE3, USAGE4, USAGE5, GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, MODREC1, MODREC2, MODREC3, MODREC4, MODREC5, NOM, PRENOM, CDN, MPG, MDET, ADN, NOMV, LDA, DL, ADRTS, LPARU, NOMV26, CAGSA, NOMGES, DENOM_SUP, DENOM_ADD) VALUES (#1, #2, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18, #19, #20, #21, #22, #23, #24, #25, #26, #27, #28, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38, #39, #40, #41, #42, #43, #44, #45, #46, #47, #48, #49, #50, #51, #52, #53, #54, #55, #56, #57, #58, #59, #60, #61, #62, #63, #64, #65, #66, #67, #68, #69, #70, #71, #72, #73, #74, #75, #76, #77, #78, #79, #80, #81, #82, #83, #84, #85, #86, #87, #88, #89, #90, #91, #92, #93, #94, #95, #96, #97, #98, #99, #100, #101, #102, #103, #104, #105, #106, #107, #108, #109, #110, #111, #112, #113, #114, #115, #116, #117, #118, #119, #120, #121)")
			.value(1, varValue0)
			.value(2, varValue1)
			.value(3, varValue2)
			.value(4, varValue3)
			.value(5, varValue4)
			.value(6, varValue5)
			.value(7, varValue6)
			.value(8, varValue7)
			.value(9, varValue8)
			.value(10, varValue9)
			.value(11, varValue10)
			.value(12, varValue11)
			.value(13, varValue12)
			.value(14, varValue13)
			.value(15, varValue14)
			.value(16, varValue15)
			.value(17, varValue16)
			.value(18, varValue17)
			.value(19, varValue18)
			.value(20, varValue19)
			.value(21, varValue20)
			.value(22, varValue21)
			.value(23, varValue22)
			.value(24, varValue23)
			.value(25, varValue24)
			.value(26, varValue25)
			.value(27, varValue26)
			.value(28, varValue27)
			.value(29, varValue28)
			.value(30, varValue29)
			.value(31, varValue30)
			.value(32, varValue31)
			.value(33, varValue32)
			.value(34, varValue33)
			.value(35, varValue34)
			.value(36, varValue35)
			.value(37, varValue36)
			.value(38, varValue37)
			.value(39, varValue38)
			.value(40, varValue39)
			.value(41, varValue40)
			.value(42, varValue41)
			.value(43, varValue42)
			.value(44, varValue43)
			.value(45, varValue44)
			.value(46, varValue45)
			.value(47, varValue46)
			.value(48, varValue47)
			.value(49, varValue48)
			.value(50, varValue49)
			.value(51, varValue50)
			.value(52, varValue51)
			.value(53, varValue52)
			.value(54, varValue53)
			.value(55, varValue54)
			.value(56, varValue55)
			.value(57, varValue56)
			.value(58, varValue57)
			.value(59, varValue58)
			.value(60, varValue59)
			.value(61, varValue60)
			.value(62, varValue61)
			.value(63, varValue62)
			.value(64, varValue63)
			.value(65, varValue64)
			.value(66, varValue65)
			.value(67, varValue66)
			.value(68, varValue67)
			.value(69, varValue68)
			.value(70, varValue69)
			.value(71, varValue70)
			.value(72, varValue71)
			.value(73, varValue72)
			.value(74, varValue73)
			.value(75, varValue74)
			.value(76, varValue75)
			.value(77, varValue76)
			.value(78, varValue77)
			.value(79, varValue78)
			.value(80, varValue79)
			.value(81, varValue80)
			.value(82, varValue81)
			.value(83, varValue82)
			.value(84, varValue83)
			.value(85, varValue84)
			.value(86, varValue85)
			.value(87, varValue86)
			.value(88, varValue87)
			.value(89, varValue88)
			.value(90, varValue89)
			.value(91, varValue90)
			.value(92, varValue91)
			.value(93, varValue92)
			.value(94, varValue93)
			.value(95, varValue94)
			.value(96, varValue95)
			.value(97, varValue96)
			.value(98, varValue97)
			.value(99, varValue98)
			.value(100, varValue99)
			.value(101, varValue100)
			.value(102, varValue101)
			.value(103, varValue102)
			.value(104, varValue103)
			.value(105, varValue104)
			.value(106, varValue105)
			.value(107, varValue106)
			.value(108, varValue107)
			.value(109, varValue108)
			.value(110, varValue109)
			.value(111, varValue110)
			.value(112, varValue111)
			.value(113, varValue112)
			.value(114, varValue113)
			.value(115, varValue114)
			.value(116, varValue115)
			.value(117, varValue116)
			.value(118, varValue117)
			.value(119, varValue118)
			.value(120, varValue119)
			.value(121, varValue120) ;
		handleSqlStatus();
		
		// FileName: F0XZZ88 (730) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = 'VRGS'")
			.into(varInto0) ;
		handleSqlStatus();

	}
	Paragraph F0XZZID= new Paragraph(this);
	public void F0XZZID() {
	
		
		// FileName: F0XZZID (143) :
		m_sql = sql("SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = #1")
			.into(varInto0)
			.param(1, varParam0) ;
		handleSqlStatus();

		// FileName: F0XZZID (101) :
		try
		{
			m_cur = cursorOpen(cur_Tgestid, "SELECT V_ID FROM TGESTID  WHERE C_TYPE_ID = #1 FOR UPDATE OF V_ID")
			.param(1, varParam0) ;
			handleSqlStatus(m_cur);
		}
		catch(Exception e)
		{
			handleExceptionCursor(e);
		}
		// FileName: F0XZZID (207) :
		cursorUpdateCurrent(cur_Tgestid, "UPDATE TGESTID SET V_ID = #1")
			.value(1, varValue0);
		handleSqlStatus();
		
		
		// FileName: F0XZZID (223) :
		cursorClose(cur_Tgestid);
		
		// FileName: F0XZZID (247) :
		m_sql = sql("SELECT MAX(ID_MVT) FROM TIDMVT")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0XZZID (282) :
		m_sql = sql("SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0XZZID (314) :
		m_sql = sql("INSERT INTO TIDMVT (ID_MVT) VALUES ()") ;
		handleSqlStatus();
		
		// FileName: F0XZZID (340) :
		m_sql = sql("SELECT MAX(ID_LIGPAR) FROM TIDLIPA")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0XZZID (374) :
		m_sql = sql("SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1")
			.into(varInto0) ;
		handleSqlStatus();

		// FileName: F0XZZID (406) :
		m_sql = sql("INSERT INTO TIDLIPA (ID_LIGPAR) VALUES ()") ;
		handleSqlStatus();
			}
}
// Generated 577 SQL Statements in 42 files
// Nb Select:260
// Nb Insert:94
// Nb Update:68
// Nb Delete:22
// Nb CursorOpen:66
// Nb CursorClose:67
