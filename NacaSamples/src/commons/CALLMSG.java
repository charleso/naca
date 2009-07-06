import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.callPrg.CalledProgram;
public class CALLMSG extends CalledProgram                                      // (1)  IDENTIFICATION DIVISION.
{
	// =================================================================        // (2)
	                                                                            // (3)  PROGRAM-ID.   CALLMSG.
	                                                                            // (4)  AUTHOR.       XXXXXXXXX.
	                                                                            // (5)  DATE-WRITTEN. 2008.
	                                                                            // (6) 
	// REMARKS.                                                                 // (7)
	//                                                                          // (8)
	//  Programme demo ROUTINE                                                  // (9)
	//                                                                          // (10)
	//                                                                          // (11)
	                                                                            // (12)  ENVIRONMENT DIVISION.
	// =================================================================        // (13)
	                                                                            // (14)  
	                                                                            // (15)  DATA DIVISION.
	// =================================================================        // (16)
	                                                                            // (17)  
	                                                                            // (18)  WORKING-STORAGE SECTION.
	// ------------------------                                                 // (19)
	                                                                            // (20)         
	DataSection linkagesection = declare.linkageSection() ;                     // (21)  LINKAGE SECTION.
	// ----------------                                                            (22) *
	MSGZONE msgzone = MSGZONE.Copy(this) ;                                      // (23)      COPY MSGZONE. 
	//                                                                             (24) /
	ParamDeclaration callParameters = declare.using(msgzone.msg_Zone);          // (25)  PROCEDURE DIVISION USING MSG-ZONE.
	public void procedureDivision() {
		// =================================================================    // (26)
		                                                                        // (27) 
		                                                                        // (28)      EVALUATE MSG-NO
		if (isEqual(msgzone.msg_No, "0001"))
		{                                                                       // (29)        WHEN '0001'
			move("Problem BATCH", msgzone.msg_Text);                            // (30)          MOVE 'Problem BATCH'     TO MSG-TEXT 
		}
		else if (isEqual(msgzone.msg_No, "0002"))
		{                                                                       // (31)        WHEN '0002'
			move("Forbidden PFKey", msgzone.msg_Text);                          // (32)          MOVE 'Forbidden PFKey'   TO MSG-TEXT
		}
		else if (isEqual(msgzone.msg_No, "0003"))
		{                                                                       // (33)        WHEN '0003'
			move("Record not found", msgzone.msg_Text);                         // (34)          MOVE 'Record not found'  TO MSG-TEXT
		}
		else if (isEqual(msgzone.msg_No, "0004"))
		{                                                                       // (35)        WHEN '0004'
			move("Mandatory field", msgzone.msg_Text);                          // (36)          MOVE 'Mandatory field'   TO MSG-TEXT    
		}
		                                                                        // (37)      END-EVALUATE
		                                                                        // (38)      
		exitProgram();                                                          // (39)      GOBACK.
	}
}
                                                                                // (40)      
