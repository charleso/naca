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

import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.batchPrgEnv.BatchProgram; 
import nacaLib.program.Paragraph; 
import nacaLib.program.Sentence;
import nacaLib.varEx.DataSection;


public class TestNestedPerformThrough extends OnlineProgram
{
	DataSection workingstoragesection = declare.workingStorageSection() ; 

	
    public void procedureDivision() 
    { 
		display("BEGIN"); 
		performThrough(aaa, aaa_Exit); 
		display("END"); 
		stopRun(); 
    } 

    Paragraph aaa = new Paragraph(this);
	public void aaa() 
	{ 
		display("1st nested level starts");
		performThrough(bbb, bbb_Exit); 
	} 


    Paragraph aaaMiddle = new Paragraph(this);
	public void aaaMiddle() 
	{ 
		display("1st nested level continues");
    } 

    Paragraph aaa_Exit = new Paragraph(this); 
	public void aaa_Exit() 
	{ 
		display("1st nested level ends");
    } 


    Paragraph bbb = new Paragraph(this) ;
	public void bbb() 
	{ 
		display("2nd nested level starts");
		performThrough(ccc , ccc_Exit); 
    } 
    
    Paragraph bbbMiddle = new Paragraph(this);
    public void bbbMiddle() 
	{ 
		display("2nd nested level continues; next sentence to bbbMiddle3");
		nextSentence(bbbMiddle3);
		display("2nd nested level continues - never displayed");
    }

    Paragraph bbbMiddle2 = new Paragraph(this);
    public void bbbMiddle2() 
	{ 
		display("bbbMiddle2: Skipped");
    }
    
    Sentence bbbMiddle3 = new Sentence(this);
    public void bbbMiddle3() 
	{ 
		display("bbbMiddle3: Label for next sentence");
    }

    Paragraph bbb_Exit = new Paragraph(this);
    public void bbb_Exit() 
	{ 
		display("2nd nested level ends");
	} 


    Paragraph ccc = new Paragraph(this); 
    public void ccc() 
	{ 
    	display("3rd nested level starts"); 
	} 
    
    Paragraph cccMiddle = new Paragraph(this);
	public void cccMiddle() 
	{ 
    	display("3rd nested level continues");
    }

    Paragraph ccc_Exit = new Paragraph(this);
    public void ccc_Exit() 
    { 
    	display("3rd nested level ends");
    }  
}

