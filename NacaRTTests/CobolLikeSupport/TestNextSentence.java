/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.program.Sentence;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;


public class TestNextSentence extends OnlineProgram
{
	DataSection workingstoragesection = declare.workingStorageSection() ; 
	
	//Var longfrom = declare.level(77).picS9(4).comp().sync().value(9999).var() ; // (154)  77  LONGFROM               PIC S9(4) COMP-4 SYNC VALUE +9999.
	
    public void procedureDivision() 
    { 
		display("PERFORM A"); 
		perform(A);
		//goTo(A);
		
		
		display("PERFORM SECTION S1");
		perform(S1);
		
		display("PERFORM SECTION S2");
		perform(S2);
		
		display("PERFORM SECTION S3");
		perform(S3);

		display("PERFORM THROUGH S3A10, S3A12");
		performThrough(S3A10, S3A12);
		
		display("With perform through ALL");
		performThrough(A, S3A12);
		
		display("END");
		stopRun(); 
    } 
    
    Paragraph A = new Paragraph(this);
	public void A()
	{
		display("A1 - Performed");
		nextSentence(sentence$0);
		display("A2 - never");
	}
	
	Sentence sentence$0 = new Sentence(this);
	public void sentence$0()
	{
		display("sentence$0");
	}
	
    Paragraph B = new Paragraph(this);
	public void B()
	{
		display("B1 - only in Perform A");
	}
	
	Paragraph C = new Paragraph(this);
	public void C()
	{
		display("C1 - only in Perform A");
	}
	
	
	
	Section S1 = new Section(this);
	public void S1()
	{
		display("Section S1 - performed section");
	}
	
	Paragraph S1A1 = new Paragraph(this);
	public void S1A1()
	{
		display("Section S1 - A1 - Start");
		nextSentence(sentence$1);
		display("Section S1 - A1 - Never");
	}
	
	Paragraph S1A2 = new Paragraph(this);
	public void S1A2()
	{
		display("Section S1 - A2 - Never");
	}
	
	Sentence sentence$1 = new Sentence(this);
	public void sentence$1()
	{
		display("Section S1 - sentence$1");
	}
	Paragraph S1A3 = new Paragraph(this);
	public void S1A3()
	{
		display("Section S1 - A3");
	}
	
	
	
	
	
	Section S2 = new Section(this);
	public void S2()
	{
		display("Section S2");
		nextSentence(sentence$2);
		display("Section S2 - Never");
	}
	
	Paragraph S2A1 = new Paragraph(this);
	public void S2A1()
	{
		display("Section S2 - A1 - Never");
	}
	
	Sentence sentence$2 = new Sentence(this);
	public void sentence$2()
	{
		display("Section S2 - sentence$2");
	}
	
	Paragraph S2A2 = new Paragraph(this);
	public void S2A2()
	{
		display("Section S2 - A2");
	}
	
	Paragraph S2A3 = new Paragraph(this);
	public void S2A3()
	{
		display("Section S2 - A3");
	}
	

	
	Section S3 = new Section(this);
	public void S3()
	{
		display("Section S3");
		nextSentence(sentence$3);
		display("Section S3 - Never");
	}
			
	Paragraph S3A1 = new Paragraph(this);
	public void S3A1()
	{
		display("Section S3 - A1 - Never");
	}
	
	Sentence sentence$3 = new Sentence(this);
	public void sentence$3()
	{
		display("Section S3 - sentence$3");
	}
	
	Paragraph S3A2 = new Paragraph(this);
	public void S3A2()
	{
		display("Section S3 - A2");
	}
	
	Paragraph S3A3 = new Paragraph(this);
	public void S3A3()
	{
		display("Section S3 - A3");
	}
	
	
	
	Paragraph S3A10 = new Paragraph(this);
	public void S3A10()
	{
		display("Section S3 - A10 - Start");
		nextSentence(sentence$4);
		display("Section S3 - A10 - Never ");		
	}

	Paragraph S3A11 = new Paragraph(this);
	public void S3A11()
	{
		display("Section S3 - A11 - Never");
	}
	
	Sentence sentence$4 = new Sentence(this);
	public void sentence$4()
	{
		display("Section S3 - sentence$4; Skipping S3A12");
		goTo(S3A13);
	}
	
	
	Paragraph S3A12 = new Paragraph(this);
	public void S3A12()
	{
		display("Section S3 - A12 - Never");
	}

	Paragraph S3A13 = new Paragraph(this);
	public void S3A13()
	{
		display("Section S3 - A13");
	}

	
}
