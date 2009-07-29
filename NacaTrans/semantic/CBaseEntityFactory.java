/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.CBaseLanguageExporter;
import generate.java.CJavaFileSelect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import parser.CIdentifier;
import parser.Cobol.elements.SQL.CSQLTableColDescriptor;
import parser.Cobol.elements.SQL.SQLSetDateTimeType;
import semantic.CICS.CEntityCICSAbend;
import semantic.CICS.CEntityCICSAddress;
import semantic.CICS.CEntityCICSAskTime;
import semantic.CICS.CEntityCICSAssign;
import semantic.CICS.CEntityCICSDeQ;
import semantic.CICS.CEntityCICSDelay;
import semantic.CICS.CEntityCICSDeleteQ;
import semantic.CICS.CEntityCICSEnQ;
import semantic.CICS.CEntityCICSGetMain;
import semantic.CICS.CEntityCICSHandleAID;
import semantic.CICS.CEntityCICSHandleCondition;
import semantic.CICS.CEntityCICSIgnoreCondition;
import semantic.CICS.CEntityCICSInquire;
import semantic.CICS.CEntityCICSLink;
import semantic.CICS.CEntityCICSReWrite;
import semantic.CICS.CEntityCICSRead;
import semantic.CICS.CEntityCICSReadQ;
import semantic.CICS.CEntityCICSReceiveMap;
import semantic.CICS.CEntityCICSRetrieve;
import semantic.CICS.CEntityCICSReturn;
import semantic.CICS.CEntityCICSSendMap;
import semantic.CICS.CEntityCICSSetTDQueue;
import semantic.CICS.CEntityCICSStart;
import semantic.CICS.CEntityCICSStartBrowse;
import semantic.CICS.CEntityCICSSyncPoint;
import semantic.CICS.CEntityCICSWrite;
import semantic.CICS.CEntityCICSWriteQ;
import semantic.CICS.CEntityCICSXctl;
import semantic.SQL.CEntityCondIsSQLCode;
import semantic.SQL.CEntitySQLCall;
import semantic.SQL.CEntitySQLCloseStatement;
import semantic.SQL.CEntitySQLCode;
import semantic.SQL.CEntitySQLCommit;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLCursorSelectStatement;
import semantic.SQL.CEntitySQLDeclareTable;
import semantic.SQL.CEntitySQLDeleteStatement;
import semantic.SQL.CEntitySQLExecute;
import semantic.SQL.CEntitySQLFetchStatement;
import semantic.SQL.CEntitySQLInsertStatement;
import semantic.SQL.CEntitySQLLock;
import semantic.SQL.CEntitySQLOpenStatement;
import semantic.SQL.CEntitySQLRollBack;
import semantic.SQL.CEntitySQLSelectStatement;
import semantic.SQL.CEntitySQLSessionDeclare;
import semantic.SQL.CEntitySQLSessionDrop;
import semantic.SQL.CEntitySQLSet;
import semantic.SQL.CEntitySQLSingleStatement;
import semantic.SQL.CEntitySQLUpdateStatement;
import semantic.SQL.CEntitySqlOnErrorGoto;
import semantic.Verbs.CEntityAccept;
import semantic.Verbs.CEntityAddTo;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntityAssignSpecial;
import semantic.Verbs.CEntityAssignWithAccessor;
import semantic.Verbs.CEntityBreak;
import semantic.Verbs.CEntityCalcul;
import semantic.Verbs.CEntityCallFunction;
import semantic.Verbs.CEntityCallProgram;
import semantic.Verbs.CEntityCase;
import semantic.Verbs.CEntityCaseSearchAll;
import semantic.Verbs.CEntityCloseFile;
import semantic.Verbs.CEntityConstantReturn;
import semantic.Verbs.CEntityContinue;
import semantic.Verbs.CEntityConvertReference;
import semantic.Verbs.CEntityCount;
import semantic.Verbs.CEntityDisplay;
import semantic.Verbs.CEntityDivide;
import semantic.Verbs.CEntityExec;
import semantic.Verbs.CEntityGoto;
import semantic.Verbs.CEntityInc;
import semantic.Verbs.CEntityInitialize;
import semantic.Verbs.CEntityInspectConverting;
import semantic.Verbs.CEntityLabelNextSentence;
import semantic.Verbs.CEntityLoopIter;
import semantic.Verbs.CEntityLoopWhile;
import semantic.Verbs.CEntityMultiply;
import semantic.Verbs.CEntityNextSentence;
import semantic.Verbs.CEntityOpenFile;
import semantic.Verbs.CEntityParseString;
import semantic.Verbs.CEntityReadFile;
import semantic.Verbs.CEntityReplace;
import semantic.Verbs.CEntityReturn;
import semantic.Verbs.CEntityRewriteFile;
import semantic.Verbs.CEntityRoutineEmulationCall;
import semantic.Verbs.CEntitySearch;
import semantic.Verbs.CEntitySetConstant;
import semantic.Verbs.CEntitySort;
import semantic.Verbs.CEntitySortRelease;
import semantic.Verbs.CEntitySortReturn;
import semantic.Verbs.CEntityStringConcat;
import semantic.Verbs.CEntitySubtractTo;
import semantic.Verbs.CEntitySwitchCase;
import semantic.Verbs.CEntityWriteFile;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityAddress;
import semantic.expression.CEntityAddressOf;
import semantic.expression.CEntityBoolean;
import semantic.expression.CEntityConcat;
import semantic.expression.CEntityCondAnd;
import semantic.expression.CEntityCondCompare;
import semantic.expression.CEntityCondEquals;
import semantic.expression.CEntityCondIsAll;
import semantic.expression.CEntityCondIsBoolean;
import semantic.expression.CEntityCondIsConstant;
import semantic.expression.CEntityCondIsKindOf;
import semantic.expression.CEntityCondNot;
import semantic.expression.CEntityCondOr;
import semantic.expression.CEntityConstant;
import semantic.expression.CEntityCurrentDate;
import semantic.expression.CEntityCurrentTimeStampSQLFunction;
import semantic.expression.CEntityCurrentDateSQLFunction;
import semantic.expression.CEntityDigits;
import semantic.expression.CEntityExprLengthOf;
import semantic.expression.CEntityExprOpposite;
import semantic.expression.CEntityExprProd;
import semantic.expression.CEntityExprSum;
import semantic.expression.CEntityExprTerminal;
import semantic.expression.CEntityFunctionCall;
import semantic.expression.CEntityInsertSQLFunction;
import semantic.expression.CEntityInternalBool;
import semantic.expression.CEntityIsFileEOF;
import semantic.expression.CEntityIsNamedCondition;
import semantic.expression.CEntityLengthOf;
import semantic.expression.CEntityList;
import semantic.expression.CEntityNamedSQLFunction;
import semantic.expression.CEntityNumber;
import semantic.expression.CEntitySQLNull;
import semantic.expression.CEntityString;
import semantic.expression.CEntityTally;
import semantic.forms.CEntityFieldArrayReference;
import semantic.forms.CEntityFieldAttribute;
import semantic.forms.CEntityFieldAttributeReference;
import semantic.forms.CEntityFieldColor;
import semantic.forms.CEntityFieldData;
import semantic.forms.CEntityFieldFlag;
import semantic.forms.CEntityFieldHighlight;
import semantic.forms.CEntityFieldLength;
import semantic.forms.CEntityFieldOccurs;
import semantic.forms.CEntityFieldRedefine;
import semantic.forms.CEntityFieldValidated;
import semantic.forms.CEntityFormRedefine;
import semantic.forms.CEntityGetKeyPressed;
import semantic.forms.CEntityIsFieldAttribute;
import semantic.forms.CEntityIsFieldColor;
import semantic.forms.CEntityIsFieldCursor;
import semantic.forms.CEntityIsFieldFlag;
import semantic.forms.CEntityIsFieldHighlight;
import semantic.forms.CEntityIsFieldModified;
import semantic.forms.CEntityIsKeyPressed;
import semantic.forms.CEntityKeyPressed;
import semantic.forms.CEntityResetKeyPressed;
import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceFieldArray;
import semantic.forms.CEntityResourceForm;
import semantic.forms.CEntityResourceFormContainer;
import semantic.forms.CEntitySetAttribute;
import semantic.forms.CEntitySetColor;
import semantic.forms.CEntitySetCursor;
import semantic.forms.CEntitySetFlag;
import semantic.forms.CEntitySetHighligh;
import semantic.forms.CEntitySkipFields;
import semantic.forms.CResourceStrings;
import utils.CObjectCatalog;



/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseEntityFactory
{

	/**
	 * 
	 */
	public CBaseEntityFactory(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		m_ProgramCatalog = cat ;
		m_LangOutput = out ;
	}

//	public abstract CEntity NewEntityCondition(int l, String name) ;
	public abstract CEntityIsFieldCursor NewEntityIsFieldCursor() ;
	public CEntityNoAction NewEntityNoAction(int l)
	{
		return new CEntityNoAction(l, m_ProgramCatalog, m_LangOutput); 
	}
	public CIgnoreExternalEntity NewIgnoreExternalEntity(String name)
	{
		return new CIgnoreExternalEntity(0, name, m_ProgramCatalog, m_LangOutput); 
	}
	public CIgnoredEntity NewIgnoreEntity(String name) 
	{
		return new CIgnoredEntity(0, name, m_ProgramCatalog, m_LangOutput); 
	};
	public CIgnoredEntity NewIgnoreEntity(int l, String name) 
	{
		return new CIgnoredEntity(l, name, m_ProgramCatalog, m_LangOutput); 
	};
	public abstract CEntityResetKeyPressed NewEntityResetKeyPressed(int l) ;
	public abstract CEntityCICSGetMain NewEntityCICSGetMain(int l) ;
	public abstract CEntityUnknownReference NewEntityUnknownReference(int nLine, String csName) ;
	public abstract CEntityFieldOccurs NewEntityFieldOccurs(int i, String string) ;
	public abstract CEntityGetKeyPressed NewEntityGetKeyPressed(String name) ;
	public abstract CEntityIsKeyPressed NewEntityIsKeyPressed() ;
	public abstract CEntityKeyPressed NewEntityKeyPressed(String name, String caption) ;
	public abstract CEntitySQLCursor NewEntitySQLCursor(String name) ;
	public abstract CEntityIndex NewEntityIndex(String name) ;
	public abstract CEntityFieldArrayReference NewEntityFieldArrayReference(int l) ;
	public abstract CEntitySQLCursorSection NewEntitySQLCursorSection() ;
	public abstract CEntityProcedureDivision NewEntityProcedureDivision(int l) ;
	public abstract CEntityCICSReWrite NewEntityCICSReWrite(int l) ;
	public abstract CEntityCICSDelay NewEntityCICSDelay(int l) ;
	public abstract CEntityCICSSetTDQueue NewEntityCICSSetTDQueue(int l) ;
	public abstract CEntityCICSDeQ NewEntityCICSDeQ(int l) ;
	public abstract CEntityCICSEnQ NewEntityCICSEnQ(int l) ;
	public abstract CEntityCount NewEntityCount(int l) ;
	public abstract CEntityDisplay NewEntityDisplay(int l, boolean b) ;
	public abstract CEntityCICSAssign NewEntityCICSAssign(int l) ;
	public abstract CEntityCICSWriteQ NewEntityCICSWriteQ(int l, boolean b) ;
	public abstract CEntityCICSReadQ NewEntityCICSReadQ(int l, boolean b) ;
	public abstract CEntityCICSDeleteQ NewEntityCICSDeleteQ(int l, boolean b) ;
	public abstract CEntityCICSStartBrowse NewEntityCICSStartBrowse(int l) ;
	public abstract CEntityCICSRead NewEntityCICSRead(int l, CEntityCICSRead.CEntityCICSReadMode mode) ;
	public abstract CEntityCICSAbend NewEntityCICSAbend(int l) ;
	public abstract CEntityCICSInquire NewEntityCICSInquire(int l) ;
	public abstract CEntityCICSSyncPoint NewEntityCICSSyncPoint(int l, boolean bRollBack) ;
	public abstract CEntityIsFieldModified NewEntityIsFieldModified() ;
	public abstract CEntityCICSReceiveMap NewEntityCICSReceiveMap(int l, CDataEntity name) ;
	public abstract CEntityCICSWrite NewEntityCICSWrite(int l) ;
	public abstract CEntityCICSSendMap NewEntityCICSSendMap(int l) ;
	public abstract CEntityCICSReturn NewEntityCICSReturn(int l) ;
	public abstract CEntityCICSStart NewEntityCICSStart(int l, CDataEntity TID) ;
	public abstract CEntityCICSRetrieve NewEntityCICSRetreive(int l, boolean bPointer) ;
	public abstract CEntityCICSIgnoreCondition NewEntityCICSIgnoreCondition(int l) ;
	public abstract CEntityCICSHandleCondition NewEntityCICSHandleCondition(int line) ;
	public abstract CEntityCICSHandleAID NewEntityCICSHandleAID(int line) ;
	public abstract CEntityCurrentDate NewEntityCurrentDate() ;
	public abstract CEntityAddressOf NewEntityAddressOf(CDataEntity data) ;
	public abstract CEntityLengthOf NewEntityLengthOf(CDataEntity data) ;
	public abstract CEntityCICSXctl NewEntityCICSXctl(int l) ;
	public abstract CEntityCICSLink NewEntityCICSLink(int l) ;
	public abstract CEntityCICSAddress NewEntityCICSAddress(int l) ;
	public abstract CEntityCICSAskTime NewEntityCICSAskTime(int l) ;
	public abstract CEntityExprOpposite NewEntityExprOpposite() ;
	public abstract CEntitySQLCommit NewEntitySQLCommit(int l) ;
	public abstract CEntitySQLRollBack NewEntitySQLRollBack(int l) ;
	public abstract CEntityParseString NewEntityParseString(int l) ;
	public abstract CEntityMultiply NewEntityMultiply(int l) ;
	public abstract CEntityDivide NewEntityDivide(int l) ;
	public abstract CEntityStringConcat NewEntityStringConcat(int l) ;
	public abstract CEntityIsFieldHighlight NewEntityIsFieldHighlight(CDataEntity ref) ;
	public abstract CEntityReplace NewEntityReplace(int l) ;
	public abstract CEntityInspectConverting NewEntityInspectConverting(int l) ;
	public abstract CEntityDataSection NewEntityDataSection(int l, String name) ;
	public abstract CEntityIOSection NewEntityIOSection(int l, String name) ;
	public abstract CEntityIsNamedCondition NewEntityIsNamedCondition() ;
	public abstract CEntitySubtractTo NewEntitySubtractTo(int l) ;
	public abstract CEntityMoveReference NewEntityMoveReference(int l) ;
	public abstract CEntityAddressReference NewEntityAddressReference(CDataEntity ref) ;
	public abstract CEntityIsFieldAttribute NewEntityIsFieldAttribute() ;
	public abstract CEntityIsFieldColor NewEntityIsFieldColor() ;
	public abstract CEntitySetConstant NewEntitySetConstant(int l) ;
	public abstract CEntityIsFieldFlag NewEntityIsFieldFlag() ;
	public abstract CEntityCondIsConstant NewEntityCondIsConstant() ;
	public abstract CEntityCondIsKindOf NewEntityCondIsKindOf() ;
	public abstract CEntityCondIsAll NewEntityCondIsAll() ;
	public abstract CEntityString NewEntityString(char[] value) ;
	public CEntityString NewEntityString(String value)
	{
		char arr[] = value.toCharArray() ;
		return NewEntityString(arr) ;
	}
	
	public abstract CEntityCurrentTimeStampSQLFunction NewEntityCurrentTimeStampSQLFunction(String csOriginalValue);
	public abstract CEntityCurrentDateSQLFunction NewEntityCurrentDateSQLFunction(String csOriginalValue);
	public abstract CEntityNamedSQLFunction NewEntityNamedSQLFunction(String csValue);
	public abstract CEntityInsertSQLFunction NewEntityInsertSQLFunction(CIdentifier id, String csFormat);
	
	public abstract CEntitySQLNull NewEntitySQLNull();
	
	public abstract CEntityCondOr NewEntityCondOr() ;
	public abstract CEntityNumber NewEntityNumber(String value) ;
	public CEntityNumber NewEntityNumber(int value)
	{
		return NewEntityNumber(String.valueOf(value)) ;
	}
	
	public abstract CEntityBoolean NewEntityBoolean(boolean bValue) ;
	
	public abstract CEntityExprTerminal NewEntityExprTerminal(CDataEntity eData) ;
	public abstract CEntityExprSum NewEntityExprSum() ;
	public abstract CEntityExprProd NewEntityExprProd() ;
	public abstract CEntityCondNot NewEntityCondNot() ;
	public abstract CEntityCondEquals NewEntityCondEquals() ;
	public abstract CEntityCondCompare NewEntityCondCompare() ;
	public abstract CEntityCondAnd NewEntityCondAnd() ;
	public abstract CEntityClass NewEntityClass(int l, String name) ;
	public abstract CEntityComment NewEntityComment(int l, String comment) ;
	public abstract CEntityAttribute NewEntityAttribute(int l, String name) ;
	public abstract CEntityStructure NewEntityStructure(int l, String name, String Level) ;
	public abstract CEntityProcedure NewEntityProcedure(int l, String name, CEntityProcedureSection section) ;
	public abstract CEntityProcedureSection NewEntityProcedureSection(int l, String name, boolean bLabelSentence) ;
	public abstract CEntityAssign NewEntityAssign(int l) ;
	public abstract CEntityAssignSpecial NewEntityAssignSpecial(int l) ;
	public abstract CEntityExternalDataStructure NewEntityExternalDataStructure(int l, String name) ;
	public abstract CEntityInline NewEntityInline(int l, CBaseExternalEntity ext) ;
	public abstract CEntityCondition NewEntityCondition(int l) ;
	public abstract CEntityBloc NewEntityBloc(int l) ;
	public abstract CEntityCalcul NewEntityCalcul(int l) ;
	public abstract CEntitySqlOnErrorGoto NewEntitySQLOnErrorGoto(int l, String ref) ;
	public abstract CEntitySqlOnErrorGoto NewEntitySQLOnWarningGoto(int l, String ref) ;
	public abstract CEntitySqlOnErrorGoto NewEntitySQLOnNotFoundGoto(int l, String ref) ;	// PJD Added
	public abstract CEntityExec NewEntityExec(int l, String statement) ;
	public abstract CEntityResourceFormContainer NewEntityFormContainer(int l, String name, boolean bSavCopy) ;
	public abstract CEntityResourceForm NewEntityForm(int l, String name, boolean bSavCopy) ;
	public abstract CEntityResourceField NewEntityEntryField(int l, String name) ;
	public abstract CEntityResourceField NewEntityLabelField(int l) ;
	public abstract CEntityCallFunction NewEntityCallFunction(int l, String reference, String refThru, CEntityProcedureSection section) ;
	public abstract CEntityInitialize NewEntityInitialize(int l, CDataEntity data) ;
	public abstract CEntityReturn NewEntityReturn(int l) ;
	public abstract CEntityCallProgram NewEntityCallProgram(int l, CDataEntity reference) ;
	public abstract CEntitySwitchCase NewEntitySwitchCase(int l) ;
	public abstract CEntityCase NewEntityCase(int l, int endline) ;
	public abstract CEntityCaseSearchAll NewEntityCaseSearchAll(int l, int endline) ;
	public abstract CSubStringAttributReference NewEntitySubString(int l) ;
	public abstract CEntityArrayReference NewEntityArrayReference(int l) ;
	public abstract CEntityGoto NewEntityGoto(int l, String Reference, CEntityProcedureSection section) ;
	public abstract CEntityLoopWhile NewEntityLoopWhile(int l) ;
	public abstract CEntityLoopIter NewEntityLoopIter(int l) ;
	public abstract CEntityAddTo NewEntityAddTo(int l) ;
	public abstract CEntityContinue NewEntityContinue(int l) ;
	public abstract CEntityNextSentence NewEntityNextSentence(int l, String csReference) ;
	public abstract CEntityNamedCondition NewEntityNamedCondition(int l, String name) ;
	public abstract CEntitySQLSingleStatement NewEntitySQLSingleStatement(int l, String name) ;

	
	public abstract CEntitySQLSelectStatement NewEntitySQLSelectStatement(int l, String name, Vector<CDataEntity> arrParameters, Vector<CDataEntity> arrInto, Vector<CDataEntity> arrInd) ;
	public abstract CEntitySQLCursorSelectStatement NewEntitySQLCursorSelectStatement(int l) ;	
	public abstract CEntitySQLFetchStatement NewEntitySQLFetchStatement(int l, CEntitySQLCursor cur) ;
	public abstract CEntitySQLOpenStatement NewEntitySQLOpenStatement(int l, CEntitySQLCursor cur) ;
	public abstract CEntitySQLCloseStatement NewEntitySQLCloseStatement(int l, CEntitySQLCursor cur) ;
	public abstract CEntitySQLDeleteStatement NewEntitySQLDeleteStatement(int l, String csStatement, Vector<CDataEntity> arrParameters) ;
	public abstract CEntitySQLUpdateStatement NewEntitySQLUpdateStatement(int l, String csStatement, Vector<CDataEntity> arrSets, Vector<CDataEntity> arrSetsIndicators, Vector<CDataEntity> arrParameters, Vector<CDataEntity> arrParametersIndicators) ;
	public abstract CEntitySQLInsertStatement NewEntitySQLInsertStatement(int l) ;
	public abstract CEntitySQLDeclareTable NewEntitySQLDeclareTable(int nLine, String csTableName, String csViewName, ArrayList<CSQLTableColDescriptor> arrTableColDescription);
	
	public abstract CEntitySQLSet NewEntitySQLSet(int nLine);
	

	public abstract CEntitySetColor NewEntitySetColor(int l, CDataEntity field) ;
	public abstract CEntityFieldLength NewEntityFieldLengh(int l, String name, CDataEntity field) ;
	public abstract CEntityFieldData NewEntityFieldData(int l, String name, CDataEntity field) ;
	public abstract CEntityFieldColor NewEntityFieldColor(int l, String name, CDataEntity field) ;
	public abstract CEntityFieldAttribute NewEntityFieldAttribute(int l, String name, CDataEntity field) ;
	public abstract CEntityFieldHighlight NewEntityFieldHighlight(int l, String name, CDataEntity field) ;
	public abstract CEntityFieldFlag NewEntityFieldFlag(int l, String name, CDataEntity field) ;
	public abstract CEntityFieldValidated NewEntityFieldValidated(int l, String name, CDataEntity field) ;
	public abstract CEntitySetHighligh NewEntitySetHighlight(int l, CDataEntity field) ;
	public abstract CEntitySetFlag NewEntitySetFlag(int l, CDataEntity field) ;
	public abstract CEntitySetCursor NewEntitySetCursor(int l, CDataEntity field) ;
	public abstract CEntitySetAttribute NewEntitySetAttribute(int l, CDataEntity field) ;

	public abstract CEntityAssignWithAccessor NewEntityAssignWithAccessor(int l) ;
	public abstract CResourceStrings NewResourceString(int nbLines, int nbCols) ;
	public abstract CEntityEnvironmentVariable NewEntityEnvironmentVariable(String namev, String acc, boolean bNumeric) ;
	public abstract CEntityEnvironmentVariable NewEntityEnvironmentVariable(String namev, String acc, String write, boolean bNumeric) ;
//	public abstract CEntityFormAccessor NewEntityFormAccessor(int l, String name, CEntityResourceForm owner) ;
	public abstract CEntitySkipFields NewEntityWorkingSkipField(int l, String name, int nbFields, String level) ;
	public abstract CEntityFieldRedefine NewEntityFieldRedefine(int l, String name, String level) ;
	public abstract CEntityFormRedefine NewEntityFormRedefine(int l, String name, CDataEntity eForm, boolean bSaveMap) ;
	 	
	public CObjectCatalog m_ProgramCatalog = null ;
	public CBaseLanguageExporter m_LangOutput = null;
	public abstract CDataEntity getSpecialConstantValue(String value) ;
	public abstract Collection<CDataEntity> getAllSpecialConstantAttributes() ;
	public abstract void addSpecialConstantValue(String value, String constant) ;
	public abstract CEntityResourceFieldArray NewEntityFieldArray() ;
	public abstract CEntitySQLCode NewEntitySQLCode(String name) ;
	public abstract CEntitySQLCode NewEntitySQLCode(String name, CBaseEntityExpression eHistoryItem) ;
	public abstract CEntityCondIsSQLCode NewEntityCondIsSQLCode() ;
	public abstract CEntityRoutineEmulationCall NewEntityRoutineEmulationCall(int l) ;
	public abstract CEntityConcat NewEntityConcat(CDataEntity e1, CDataEntity e2) ;
	public abstract CEntityList NewEntityList(String name) ;
	public abstract CEntityDigits NewEntityDigits(CDataEntity nel) ;
	public abstract CEntitySearch NewEntitySearch(int line) ;
	public abstract CEntityInternalBool NewEntityInternalBool(String name) ;
	public abstract CEntityBreak NewEntityBreak(int line) ;
	public abstract CEntityFileDescriptor NewEntityFileDescriptor(int line, String name) ;
	public abstract CEntitySortedFileDescriptor NewEntitySortedFileDescriptor(int line, String name) ;
	public abstract CEntityFileSelect NewEntityFileSelect(String ref);
	public abstract CEntityConfigurationSection NewEntityConfigurationSection();
//	{
//		return new CJavaFileSelect(ref, m_ProgramCatalog, m_LangOutput);
//	}
	public abstract CEntityOpenFile NewEntityOpenFile(int line) ;
	public abstract CEntityCloseFile NewEntityCloseFile(int line) ;
	public abstract CEntityReadFile NewEntityReadFile(int line) ;
	public abstract CEntityWriteFile NewEntityWriteFile(int line) ;
	public abstract CEntityAccept NewEntityAccept(int line) ;
	public abstract CEntitySort NewEntitySort(int line) ;
	public abstract CEntitySortRelease NewEntitySortRelease(int line);
	public abstract CEntitySortReturn NewEntitySortReturn(int line) ;
	public CEntityValueReference NewEntityValueReference(CDataEntity dep)	{
		return new CEntityValueReference(m_ProgramCatalog, m_LangOutput, dep);
	}
	public abstract CEntityRewriteFile NewEntityRewriteFile(int line) ;

	public CEntityFieldAttributeReference NewEntityFieldAttributeReference(CDataEntity field)
	{
		return new CEntityFieldAttributeReference(m_ProgramCatalog, m_LangOutput, field);
	}

	public CEntityFileBuffer NewEntityFileBuffer(String name, CEntityFileDescriptor att)	{
		return new CEntityFileBuffer(name, att, m_ProgramCatalog,m_LangOutput) ;
	}

	public abstract CEntityAddress NewEntityAddress(String csAddresse) ;
	public abstract CEntityFunctionCall NewEntityFunctionCall(String mehodName, CDataEntity object) ;
	public abstract CEntityCondIsBoolean NewEntityCondIsBoolean() ;
	public abstract CEntitySQLLock NewEntitySQLLock(int line) ;
	public abstract CEntitySQLSessionDeclare NewEntitySQLSessionDeclare(int line) ;
	public abstract CEntitySQLSessionDrop NewEntitySQLSessionDrop(int line) ;
	public abstract CEntitySQLExecute NewEntitySQLExecute(int line) ;
	public abstract CEntityFormatedVarReference NewEntityFormatedVarReference(CDataEntity object, String format) ;
	public abstract CEntityInc NewEntityInc(int line) ;
	public abstract CEntityConvertReference NewEntityConvert(int line) ;
	public abstract CEntityIsFileEOF NewEntityIsFileEOF(CEntityFileDescriptor fb) ;
	public abstract CEntityConstant NewEntityConstant(CEntityConstant.Value val) ;
	public abstract CEntityTally NewEntityTally(); 
	public abstract CEntityFileDescriptorLengthDependency NewEntityFileDescriptorLengthDependency(String name) ;
	public abstract CEntitySQLCall NewEntitySQLCall(int line) ;
	
	public abstract CEntityExprLengthOf NewEntityExprLengthOf(int line);
	public abstract CEntityConstantReturn NewEntityConstantReturn(int line, String cs);
	
}
