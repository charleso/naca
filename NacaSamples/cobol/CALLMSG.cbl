       IDENTIFICATION DIVISION.
      *=================================================================
       PROGRAM-ID.   CALLMSG.
       AUTHOR.       XXXXXXXXX.
       DATE-WRITTEN. 2008.

      *REMARKS.                   
      *
      * Programme demo ROUTINE
      *
      /
       ENVIRONMENT DIVISION.
      *=================================================================
       
       DATA DIVISION.
      *=================================================================
       
       WORKING-STORAGE SECTION.
      *------------------------
              
       LINKAGE SECTION.
      *----------------
           COPY MSGZONE. 
      /
       PROCEDURE DIVISION USING MSG-ZONE.
      *=================================================================
      
           EVALUATE MSG-NO
             WHEN '0001'
               MOVE 'Problem BATCH'     TO MSG-TEXT 
             WHEN '0002'
               MOVE 'Forbidden PFKey'   TO MSG-TEXT
             WHEN '0003'
               MOVE 'Record not found'  TO MSG-TEXT
             WHEN '0004'
               MOVE 'Mandatory field'   TO MSG-TEXT    
           END-EVALUATE
           
           GOBACK.
           
       