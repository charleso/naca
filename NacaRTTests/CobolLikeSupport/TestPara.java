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
/*
 * Created on 17 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.*;
import nacaLib.varEx.*;

public class TestPara extends OnlineProgram
{	
	DataSection WorkingStorage = declare.workingStorageSection();

	Var VGroup = declare.level(1).var(); 
		Var VN = declare.level(2).pic9(7).var();
		Var VSN = declare.level(2).picS9(7).var();
		Var V3N = declare.level(2).picS9(7).comp3().var();
		Var VX = declare.level(2).picX(7).var();
		
	String m_cs = "";
	
	Section TestLoopWithGoto = new Section(this){public void run(){TestLoopWithGoto();}};void TestLoopWithGoto()
	{
		setAssertActive(true);
		move(0, VN);
		perform(section);
		assertIfDifferent(3, VN);
		assertIfFalse(m_cs.equals("S_p1_pf_"));
		
		move(0, VN);
		perform(Increment);
		
		// Never executed; see ParaDFin
		assertIfFalse(false);
	}

	Paragraph Increment = new Paragraph(this){public void run(){Increment();}};void Increment()
	{
		m_cs = m_cs + "I_";
		if(isLess(VN, 10))
		{
			inc(VN);
			goTo(Increment);	// Non recursive goto
		}
	}

	Paragraph Para2 = new Paragraph(this){public void run(){Para2();}};void Para2()
	{
		m_cs = m_cs + "P2_";
		int n = 0;
		goTo(Para4);	// gotTo to another paragrapg of the same section
		//CESM.returnTrans();
	}
	
	Paragraph Para3 = new Paragraph(this){public void run(){Para3();}};void Para3()	// never executed
	{
		m_cs = m_cs + "P3_";
		inc(100000, VN);
	}
	
	Paragraph Para4 = new Paragraph(this){public void run(){Para4();}};void Para4()
	{
		m_cs = m_cs + "P4_";
		inc(10000, VN);
	}

	Paragraph Para5 = new Paragraph(this){public void run(){Para5();}};void Para5()
	{
		m_cs = m_cs + "P5_";
		inc(1000, VN);
	}

	Section B = new Section(this){public void run(){b();}};void b()
	{
		m_cs = m_cs + "SB_";
		inc(300, VN);	
	}
	
	Paragraph ParaB2 = new Paragraph(this){public void run(){ParaB2();}};void ParaB2()	
	{
		m_cs = m_cs + "B2_";
		goTo(ParaC2);
	}
	
	Paragraph ParaB3 = new Paragraph(this){public void run(){ParaB3();}};void ParaB3()	// never exec
	{
		assertIfFalse(false);
		m_cs = m_cs + "B3_";
		move(-100, VN);
	}
	
	Section C = new Section(this){public void run(){C();}};void C()
	{
		m_cs = m_cs + "SC_";
		int n = 0;		// never exec
	}
	
	Paragraph ParaC2 = new Paragraph(this){public void run(){ParaC2();}};void ParaC2()
	{
		m_cs = m_cs + "C2_";
		inc(VN);	
	}
	
	Paragraph ParaC3 = new Paragraph(this){public void run(){ParaC3();}};void ParaC3()
	{
		m_cs = m_cs + "C3_";
		goTo(D);	// goto a section		
	}

	Paragraph ParaC4 = new Paragraph(this){public void run(){ParaC4();}};void ParaC4()
	{
		m_cs = m_cs + "C4_";
		int n = 0 ;	// never exec	
	}
	
	Section D = new Section(this){public void run(){D();}};void D()
	{
		m_cs = m_cs + "SD_";
		inc(100500, VN);		
	}
	
	Paragraph ParaD2 = new Paragraph(this){public void run(){ParaD2();}};void ParaD2()
	{
		m_cs = m_cs + "D2_";
		inc(VN);
	}
	
	Paragraph ParaD3 = new Paragraph(this){public void run(){ParaD3();}};void ParaD3()
	{
		m_cs = m_cs + "D3_";
		perform(E);	// Perform
		int n = 0;
	}

	Paragraph ParaD4 = new Paragraph(this){public void run(){ParaD4();}};void ParaD4()
	{
		m_cs = m_cs + "D4_";
		perform(ParaE2);
		goTo(ParaDFin);
	}
	
	Paragraph ParaDFin = new Paragraph(this){public void run(){ParaDFin();}};void ParaDFin()
	{
		m_cs = m_cs + "DF_";
		assertIfFalse(m_cs.equals("S_p1_pf_I_I_I_I_I_I_I_I_I_I_I_P2_P4_P5_SB_B2_C2_SC_C2_C3_SD_D2_D3_SE_E1_E2_E3_D4_E2_DF_"));
		CESM.returnTrans();
	}
	
	Section E = new Section(this){public void run(){E();}};void E()
	{
		m_cs = m_cs + "SE_";
		int n = 0;		
	}

	Paragraph ParaE1 = new Paragraph(this){public void run(){ParaE1();}};void ParaE1()
	{
		m_cs = m_cs + "E1_";
		int n = 0; 
	}

	Paragraph ParaE2 = new Paragraph(this){public void run(){ParaE2();}};void ParaE2()
	{
		m_cs = m_cs + "E2_";
		int n = 0; 
	}

	Paragraph ParaE3 = new Paragraph(this){public void run(){ParaE3();}};void ParaE3()
	{
		m_cs = m_cs + "E3_";
		int n = 0;  
	}
	
	Section section = new Section(this){public void run(){section();}};void section()
	{
		m_cs = m_cs + "S_";
		inc(VN);		
		perform(para1);		
		// Should never be executed		
	}
	
	Paragraph para1 = new Paragraph(this){public void run(){para1();}};void para1()
	{
		inc(VN);
		m_cs = m_cs + "p1_";		
		goTo(paraFin);
	}
	
	Paragraph para2 = new Paragraph(this){public void run(){para2();}};void para2()
	{
		assertIfFalse(false);
		// never executed
		m_cs = m_cs + "p2_";
		inc(VN);
	}
	
	Paragraph paraFin = new Paragraph(this){public void run(){paraFin();}};void paraFin()
	{
		m_cs = m_cs + "pf_";
		inc(VN);
	}
}
