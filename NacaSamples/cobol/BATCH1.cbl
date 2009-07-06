       IDENTIFICATION DIVISION.
      *=================================================================
       PROGRAM-ID.   BATCH1.
       AUTHOR.       XXXXXXXXX.
       DATE-WRITTEN. 2008.

      *REMARKS.                   
      *
      * Programme demo BATCH
      *
      /
       ENVIRONMENT DIVISION. 
      *=================================================================
       CONFIGURATION SECTION.
      *-----------------------------------------------------------------
       SOURCE-COMPUTER. IBM-3081.
       OBJECT-COMPUTER. IBM-3081.

       INPUT-OUTPUT SECTION.
      *-----------------------------------------------------------------
       FILE-CONTROL.
           SELECT FILEIN  ASSIGN TO UT-S-FILEIN.
           SELECT FILEOUT ASSIGN TO UT-S-FILEOUT.
       DATA DIVISION. 
      *=================================================================
       FILE SECTION.
      *-----------------------------------------------------------------
       FD  FILEIN
           LABEL RECORDS STANDARD
           BLOCK 0
           RECORD 69
           RECORDING F.
       01  FILEIN-Z.
           05  FILEIN-CODE         PIC X(1).
           05  FILLER              PIC X(68).

       FD  FILEOUT
           LABEL RECORDS STANDARD
           BLOCK 0
           RECORD 69
           RECORDING F.
       01  FILEOUT-Z               PIC X(69).

       WORKING-STORAGE SECTION.
      *------------------------
       77  CPT-IN                  PIC S9(7) COMP-3   VALUE ZERO.
       77  CPT-OUT                 PIC S9(7) COMP-3   VALUE ZERO.

       77  FIN-TRAIT               PIC X              VALUE SPACE.
       
       01  SYS-TIME                PIC 9(8)          VALUE ZEROS.
       01  FILLER REDEFINES SYS-TIME.
           03 SYS-TIME1            PIC 9(7).
           03 SYS-TIME2            PIC 9.
       
           COPY MSGZONE.
           
      /
       PROCEDURE DIVISION.
      *=================================================================
      
           OPEN INPUT  FILEIN
                OUTPUT FILEOUT
                
           ACCEPT SYS-TIME FROM TIME
           
           DISPLAY 'DEBUG - TIME : ' SYS-TIME1
           
           PERFORM READ-FILEIN     
           
           PERFORM TRAITEMENT UNTIL FIN-TRAIT = 'F'

           DISPLAY 'STAT FILEIN  - READ RECORDS   : '
                    CPT-IN           UPON CONSOLE.
           DISPLAY 'STAT FILEOUT - WRITE RECORDS  : '
                    CPT-OUT          UPON CONSOLE.
                    
           CLOSE FILEIN
                 FILEOUT
                 
           STOP RUN.
           
       READ-FILEIN.
      *-----------------------------------------------------------------
           READ FILEIN
                AT END MOVE 'F' TO FIN-TRAIT.    
           
       TRAITEMENT.
      *-----------------------------------------------------------------     
           ADD 1 TO CPT-IN
           EVALUATE FILEIN-CODE
             WHEN '1'           
               DISPLAY 'DEBUG 1 - ' FILEIN-Z
               WRITE FILEOUT-Z FROM FILEIN-Z
               ADD 1 TO CPT-OUT
             WHEN '2'
               MOVE '0001'     TO MSG-NO
               CALL 'CALLMSG'  USING MSG-ZONE
               DISPLAY 'DEBUG 2 - ' MSG-TEXT  
           END-EVALUATE
           PERFORM READ-FILEIN.