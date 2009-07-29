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
 * Created on 10 déc. 2004
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
import nacaLib.varEx.*;
import nacaLib.program.*;

public class TestCond extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

	Var vCond = declare.level(5).picX(1).value("2").var();
		Cond condA = declare.condition().value("1", "5").value(9).var();
	
	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		perform(TestCond1);
		
		CESM.returnTrans();
	}

	Paragraph TestCond1 = new Paragraph(this){public void run(){TestCond1();}};void TestCond1()
	{
		assertIfFalse(is(condA));	// True by default
		
		move("3", vCond);	// True
		assertIfFalse(is(condA));

		move("7", vCond);	// False
		assertIfFalse(!is(condA));	
		
		setTrue(condA);	// Always true
		assertIfFalse(is(condA));

		//setTrue(condA, false);	// Always false: Doesn't work
		//assertIfFalse(is(condA));	// Keep being true

		move("2", vCond);	// True
		assertIfFalse(is(condA));
	}
}
