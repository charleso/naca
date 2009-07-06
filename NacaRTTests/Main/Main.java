/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */

import jlib.misc.StopWatchNano;
import jlib.misc.Time_ms;
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.program.*;

public class Main extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
     
	public void procedureDivision()
	{
		setAssertActive(true);
		
		perform(Tests);
		
		CESM.returnTrans();
	}
	
	Paragraph Tests = new Paragraph(this){public void run(){Tests();}};void Tests()
	{	
		// SQL 
		//CESM.link("TestSQL").commarea(null);
		
		//CESM.link("TestVarNum").commarea(null);
		CESM.link("TestVarMisc").commarea(null);
		CESM.link("TestWorkingOccurs").commarea(null);
		CESM.link("TestOptimizationComp3").commarea(null);
		CESM.link("TestVarLong").commarea(null);
		
		// Right justification support
		CESM.link("TestWorkingRightJustify").commarea(null);
		
		// Variable declaration and access
		CESM.link("TestWorkingLevel").commarea(null);
		CESM.link("TestWorking2").commarea(null);
		CESM.link("TestWorkingDeclaration").commarea(null);
		CESM.link("TestWriteOnReadVars").commarea(null);
		
		// Screen maps
		CESM.link("TestRedefines").commarea(null);
		CESM.link("TestMap").commarea(null);
		CESM.link("TestMap4").commarea(null);
		CESM.link("TestMap8").commarea(null);
		CESM.link("TestMapRedefines").commarea(null);
		

		// Program chaining
		CESM.link("TestCall").commarea(null);
		CESM.link("TestCallAndLink").commarea(null);
		
		// Math 
		CESM.link("TestMath").commarea(null);
		
		// Paragrph/section perform, goto
		CESM.link("TestPara").commarea(null);
				
		// String handling
		CESM.link("TestStrings").commarea(null);
		CESM.link("TestUnstring").commarea(null);
		CESM.link("TestInspect").commarea(null);
//		CESM.link("TestSubstring").commarea(null);
		
		// Type support
		CESM.link("TestLong").commarea(null);
		CESM.link("TestVarTypes").commarea(null);
		CESM.link("TestVarNumEdited").commarea(null);
		CESM.link("TestVarX").commarea(null);
		CESM.link("TestVarGroup").commarea(null);
		
		// Conditions
		CESM.link("TestCond").commarea(null);

		// Queues
		CESM.link("TestQueues").commarea(null);
		CESM.link("TestOccurs").commarea(null);
	}
}

