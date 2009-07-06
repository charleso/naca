/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.batchPrgEnv.BatchProgram;
import nacaLib.program.Paragraph;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;

public class TestHelloWorld extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var varHelloWorld = declare.level(1).var();
		Var hello = declare.level(5).picX(5).var();
		Var space = declare.level(5).picX(1).value(" ").filler();
		Var world = declare.level(5).picX(5).var();
		

	public void procedureDivision()
	{
		move("Hello", hello);
		move("World", world);
		//goTo(coucou);
		perform(DisplayHelloWorld);
	}
	
	Paragraph DisplayHelloWorld = new Paragraph(this){public void run(){DisplayHelloWorld();}};void DisplayHelloWorld()
	{
		setAssertActive(true);
		
		display(varHelloWorld);
		stopRun();
	}
	
/*
	Paragraph coucou = new Paragraph(this){public void run(){coucou();}};void coucou()
	{
		int i, j;
		$outer:
		    for( i=0; i<10; i++ )
		    {
		        for( j=10; j>0; j--)
		        {
		            if( j == 5 ) 
		            {
		            	continue $outer;       // exit entire loop
		            } 
		        }            
		    }
	}
	*/
}
