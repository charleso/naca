/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.*;
import nacaLib.varEx.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class TestQueues extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

	Var VGroup = declare.level(1).var(); 
		Var VN = declare.level(2).pic9(7).var();
		Var VSN = declare.level(2).picS9(7).var();
		Var V3N = declare.level(2).picS9(7).comp3().var();
		Var VX = declare.level(2).picX(7).var();
		Var VNNbItems = declare.level(2).pic9(7).var();
		

	public void procedureDivision()
	{
		setAssertActive(true);
		perform(WriteTempQ);
		perform(ReadTempQ);
		
		CESM.returnTrans();
	}

	Paragraph WriteTempQ = new Paragraph(this){public void run(){WriteTempQ();}};void WriteTempQ()
	{
		move("A", VX);
		CESM.writeTempQueue("Q1").from(VX);
		
		move("B", VX);
		CESM.writeTempQueue("Q1").from(VX);
		
		move(10, VN);
		CESM.writeTempQueue("Q1").from(VN);
	}
	
	Paragraph ReadTempQ = new Paragraph(this){public void run(){ReadTempQ();}};void ReadTempQ()
	{
		move(0, VN);
		moveSpace(VX);

		// Sequential access
		CESM.readTempQueue("Q1").nextInto(VX).numItem(VNNbItems);
		assertIfFalse(VX.equals("A      "));
		assertIfFalse(VNNbItems.getInt() == 3);

		CESM.readTempQueue("Q1").nextInto(VX).numItem(VNNbItems);
		assertIfFalse(VX.equals("B      "));
		assertIfFalse(VNNbItems.getInt() == 3);
		
		CESM.readTempQueue("Q1").nextInto(VN).numItem(VNNbItems);
		assertIfFalse(VN.getInt() == 10);
		assertIfFalse(VNNbItems.getInt() == 3);
		
		CESM.readTempQueue("Q1").nextInto(VN).numItem(VNNbItems);
		
		// Direct access
		move(1, VN);
		CESM.readTempQueue("Q1").itemInto(VN, VX);
		assertIfFalse(VX.equals("A      "));

		move(12345, VNNbItems);
		dec(VN);
		assertIfFalse(VN.getInt() == 0);
		CESM.readTempQueue("Q1").itemInto(VN, VX).numItem(VNNbItems);
		assertIfFalse(VX.equals("A      "));
		assertIfFalse(VNNbItems.getInt() == 3);

		CESM.readTempQueue("Q1").nextInto(VX);
		assertIfFalse(VX.equals("A      "));

		CESM.readTempQueue("Q1").nextInto(VX);
		assertIfFalse(VX.equals("B      "));
		
		CESM.readTempQueue("Q1").nextInto(VX);
		assertIfFalse(VX.equals("0000010"));

		// Queue is empty; what to do ???
		CESM.readTempQueue("Q1").nextInto(VX);
		assertIfFalse(VX.equals("0000010"));
		
		// Queue deleted
		CESM.deleteTempQueue("Q1");
		
		CESM.readTempQueue("Q1").nextInto(VX).numItem(VNNbItems);
		assertIfFalse(VX.equals("0000010"));
		assertIfFalse(VNNbItems.getInt() == 3);
	}
}
