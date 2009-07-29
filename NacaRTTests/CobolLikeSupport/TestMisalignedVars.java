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
 * Created on 19 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author u930di
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.Paragraph;
import nacaLib.varEx.Var;


public class TestMisalignedVars extends OnlineProgram
{
	//DataSection WorkingStorage = declare.workingStorageSection();



	Var l1 = declare.level(1).var() ;
		Var idx1 = declare.index() ;
	 
		Var l10 = declare.level(5).picX(10).var() ;   
		Var l11 = declare.level(5).picX(1).var() ;                          
		Var l12 = declare.level(5).occurs(4).var() ;
			Var l121 = declare.level(10).picX(2).var() ;
		Var l13 = declare.level(5).redefines(l11).var() ;
	                                                                                                  
	Var l3 = declare.level(3).var() ;
		Var l31 = declare.level(5).pic9(3).var() ;
		Var l32 = declare.level(5).pic9(4).var() ;

	Var la1 = declare.level(1).var() ;
		Var idx2 = declare.index() ;
		
		Var la5 = declare.level(5).var() ;
			Var la101 = declare.level(10).picX(10).var() ;
			Var la102 = declare.level(10).picX(10).var() ;
		Var la4 = declare.level(4).var() ;
			Var la81 = declare.level(8).picX(10).var() ;
			Var la82 = declare.level(8).picX(10).var() ;

	
	public void procedureDivision()
	{
		move("a", l11);
		move("bc", l12);
		move("123", l31);
		move("4567", l32);
		
		perform(p1);
	}
	
	Paragraph p1 = new Paragraph(this){public void run(){p1();}};void p1()
	{
		int gg= 0;
	}
}
