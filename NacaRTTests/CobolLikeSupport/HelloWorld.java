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
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;
import idea.onlinePrgEnv.OnlineProgram;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class HelloWorld extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	Var counter = declare.level(1).pic9(3).comp3().var();
	Var maxCounter = declare.level(1).pic9(3).comp3().var();
	
	Var text = declare.level(1).var();
		Var text1 = declare.level(5).picX(12).var();
		Var text2 = declare.level(5).picX(8).value("; count=").var();
		Var text3 = declare.level(5).picX(3).var();
	
	public void procedureDivision()
	{
		setAssertActive(true);
		
		move("Hello world", text1);
		move(0, counter);
		move(10, maxCounter);
		
		perform(DisplayAllHello);
		CESM.returnTrans();
	}
	
	Paragraph DisplayAllHello = new Paragraph(this){public void run(){DisplayAllHello();}};void DisplayAllHello()
	{
		while(isLess(counter, maxCounter))
		{
			perform(DisplayHello);
			inc(counter);
		}
	}
	
	Paragraph DisplayHello = new Paragraph(this){public void run(){DisplayHello();}};void DisplayHello()
	{
		move(counter, text3);
		display(text);
	}
}
