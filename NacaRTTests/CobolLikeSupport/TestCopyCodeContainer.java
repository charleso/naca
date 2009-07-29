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
 * Created on 20 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.Paragraph;
import nacaLib.varEx.*;

public class TestCopyCodeContainer extends OnlineProgram implements TestCopyCodeContainerIntf 
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var W3 = declare.level(1).occurs(10).var();
		Var V9Comp010 = declare.level(5).pic9(10).var();
		Var V9Comp014V4 = declare.level(5).pic9(14, 4).var();
	
	Var VX10 = declare.level(5).picX(10).var();
        
	public void procedureDivision()
	{
		move("Initialized", VX10);
		System.out.println("In CALLER: Before Copy inline call: VX10="+VX10.toString());
		TestCopyCode testCopyCode = new TestCopyCode();
		testCopyCode.run(this);
		System.out.println("In CALLER: After Copy inline call: VX10="+VX10.toString());
		stopRun();
	}
	
	Paragraph sub = new Paragraph(this);                                                    
	public void sub()
	{
		System.out.println("In Sub Paragraph !");
	}
	
	public Var getVX10()
	{
		return VX10;
	}
	
	public Paragraph getSub()
	{
		return sub;
	}	
}
