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
package generate;

import generate.java.CJavaAddressReference;
import generate.java.CJavaArrayReference;
import generate.java.CJavaAttribute;
import generate.java.CJavaBloc;
import generate.java.CJavaClass;
import generate.java.CJavaComment;
import generate.java.CJavaCondition;
import generate.java.CJavaDataSection;
import generate.java.CJavaEnvironmentVariable;
import generate.java.CJavaExternalDataStructure;
import generate.java.CJavaFileDescriptor;
import generate.java.CJavaFileDescriptorLengthDependency;
import generate.java.CJavaIndex;
import generate.java.CJavaInline;
import generate.java.CJavaMoveReference;
import generate.java.CJavaNamedCondition;
import generate.java.CJavaProcedure;
import generate.java.CJavaProcedureDivision;
import generate.java.CJavaProcedureSection;
import generate.java.CJavaSortedFileDescriptor;
import generate.java.CJavaStructure;
import generate.java.CJavaSubStringReference;
import generate.java.CJavaUnknownReference;
import generate.java.CICS.CJavaCICSAbend;
import generate.java.CICS.CJavaCICSAddress;
import generate.java.CICS.CJavaCICSAskTime;
import generate.java.CICS.CJavaCICSAssign;
import generate.java.CICS.CJavaCICSDeQ;
import generate.java.CICS.CJavaCICSDelay;
import generate.java.CICS.CJavaCICSDeleteQ;
import generate.java.CICS.CJavaCICSEnQ;
import generate.java.CICS.CJavaCICSGetMain;
import generate.java.CICS.CJavaCICSHandleAID;
import generate.java.CICS.CJavaCICSHandleCondition;
import generate.java.CICS.CJavaCICSIgnoreCondition;
import generate.java.CICS.CJavaCICSInquire;
import generate.java.CICS.CJavaCICSLink;
import generate.java.CICS.CJavaCICSReWrite;
import generate.java.CICS.CJavaCICSRead;
import generate.java.CICS.CJavaCICSReadQ;
import generate.java.CICS.CJavaCICSReceiveMap;
import generate.java.CICS.CJavaCICSRetrieve;
import generate.java.CICS.CJavaCICSReturn;
import generate.java.CICS.CJavaCICSSendMap;
import generate.java.CICS.CJavaCICSSetTDQueue;
import generate.java.CICS.CJavaCICSStart;
import generate.java.CICS.CJavaCICSStartBrowse;
import generate.java.CICS.CJavaCICSSyncPoint;
import generate.java.CICS.CJavaCICSWrite;
import generate.java.CICS.CJavaCICSWriteQ;
import generate.java.CICS.CJavaCICSXctl;
import generate.java.SQL.CJavaCondIsSQLCode;
import generate.java.SQL.CJavaSQLCall;
import generate.java.SQL.CJavaSQLCloseStatement;
import generate.java.SQL.CJavaSQLCode;
import generate.java.SQL.CJavaSQLCommit;
import generate.java.SQL.CJavaSQLCursor;
import generate.java.SQL.CJavaSQLCursorSection;
import generate.java.SQL.CJavaSQLCursorSelectStatement;
import generate.java.SQL.CJavaSQLDeclareTable;
import generate.java.SQL.CJavaSQLDeleteStatement;
import generate.java.SQL.CJavaSQLExecute;
import generate.java.SQL.CJavaSQLFetchStatement;
import generate.java.SQL.CJavaSQLInsertStatement;
import generate.java.SQL.CJavaSQLLock;
import generate.java.SQL.CJavaSQLOpenStatement;
import generate.java.SQL.CJavaSQLRollBack;
import generate.java.SQL.CJavaSQLSelectStatement;
import generate.java.SQL.CJavaSQLSessionDeclare;
import generate.java.SQL.CJavaSQLSessionDrop;
import generate.java.SQL.CJavaSQLSingleStatement;
import generate.java.SQL.CJavaSQLUpdateStatement;
import generate.java.SQL.CJavaSqlOnErrorGoto;
import generate.java.expressions.CJavaAddressOf;
import generate.java.expressions.CJavaConcat;
import generate.java.expressions.CJavaCondAnd;
import generate.java.expressions.CJavaCondCompare;
import generate.java.expressions.CJavaCondEquals;
import generate.java.expressions.CJavaCondIsAll;
import generate.java.expressions.CJavaCondIsConstant;
import generate.java.expressions.CJavaCondIsKindOf;
import generate.java.expressions.CJavaCondNot;
import generate.java.expressions.CJavaCondOr;
import generate.java.expressions.CJavaConstant;
import generate.java.expressions.CJavaConstantValue;
import generate.java.expressions.CJavaCurrentDate;
import generate.java.expressions.CJavaDigits;
import generate.java.expressions.CJavaEntityNumber;
import generate.java.expressions.CJavaExprOpposite;
import generate.java.expressions.CJavaExprProd;
import generate.java.expressions.CJavaExprSum;
import generate.java.expressions.CJavaExprTerminal;
import generate.java.expressions.CJavaInternalBool;
import generate.java.expressions.CJavaIsNamedCondition;
import generate.java.expressions.CJavaLengthOf;
import generate.java.expressions.CJavaList;
import generate.java.expressions.CJavaString;
import generate.java.forms.CJavaField;
import generate.java.forms.CJavaFieldArray;
import generate.java.forms.CJavaFieldArrayReference;
import generate.java.forms.CJavaFieldAttribute;
import generate.java.forms.CJavaFieldColor;
import generate.java.forms.CJavaFieldData;
import generate.java.forms.CJavaFieldFlag;
import generate.java.forms.CJavaFieldHighligh;
import generate.java.forms.CJavaFieldLength;
import generate.java.forms.CJavaFieldOccurs;
import generate.java.forms.CJavaFieldRedefine;
import generate.java.forms.CJavaFieldValidated;
import generate.java.forms.CJavaForm;
import generate.java.forms.CJavaFormContainer;
import generate.java.forms.CJavaFormRedefine;
import generate.java.forms.CJavaGetKeyPressed;
import generate.java.forms.CJavaIsFieldAttribute;
import generate.java.forms.CJavaIsFieldColor;
import generate.java.forms.CJavaIsFieldCursor;
import generate.java.forms.CJavaIsFieldFlag;
import generate.java.forms.CJavaIsFieldHighlight;
import generate.java.forms.CJavaIsFieldModified;
import generate.java.forms.CJavaIsKeyPressed;
import generate.java.forms.CJavaKeyPressed;
import generate.java.forms.CJavaLabelField;
import generate.java.forms.CJavaResetKeyPressed;
import generate.java.forms.CJavaResourceStrings;
import generate.java.forms.CJavaSetAttribute;
import generate.java.forms.CJavaSetColor;
import generate.java.forms.CJavaSetCursor;
import generate.java.forms.CJavaSetFlag;
import generate.java.forms.CJavaSetHighlight;
import generate.java.forms.CJavaSkipField;
import generate.java.verbs.CJavaAccept;
import generate.java.verbs.CJavaAddTo;
import generate.java.verbs.CJavaAssign;
import generate.java.verbs.CJavaAssignWithAccessor;
import generate.java.verbs.CJavaBreak;
import generate.java.verbs.CJavaCalcul;
import generate.java.verbs.CJavaCallFunction;
import generate.java.verbs.CJavaCallProgram;
import generate.java.verbs.CJavaCase;
import generate.java.verbs.CJavaCloseFile;
import generate.java.verbs.CJavaContinue;
import generate.java.verbs.CJavaCount;
import generate.java.verbs.CJavaDisplay;
import generate.java.verbs.CJavaDivide;
import generate.java.verbs.CJavaExec;
import generate.java.verbs.CJavaGoto;
import generate.java.verbs.CJavaInitialize;
import generate.java.verbs.CJavaLoopIter;
import generate.java.verbs.CJavaLoopWhile;
import generate.java.verbs.CJavaMultiply;
import generate.java.verbs.CJavaNextSentence;
import generate.java.verbs.CJavaOpenFile;
import generate.java.verbs.CJavaParseString;
import generate.java.verbs.CJavaReadFile;
import generate.java.verbs.CJavaReplace;
import generate.java.verbs.CJavaReturn;
import generate.java.verbs.CJavaRewriteFile;
import generate.java.verbs.CJavaRoutineEmulationCall;
import generate.java.verbs.CJavaSearch;
import generate.java.verbs.CJavaSetConstant;
import generate.java.verbs.CJavaSort;
import generate.java.verbs.CJavaSortRelease;
import generate.java.verbs.CJavaSortReturn;
import generate.java.verbs.CJavaStringConcat;
import generate.java.verbs.CJavaSubtractTo;
import generate.java.verbs.CJavaSwitchCase;
import generate.java.verbs.CJavaWriteFile;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import semantic.CBaseEntityFactory;
import semantic.CBaseExternalEntity;
import semantic.CDataEntity;
import semantic.CEntityAddressReference;
import semantic.CEntityArrayReference;
import semantic.CEntityAttribute;
import semantic.CEntityBloc;
import semantic.CEntityClass;
import semantic.CEntityComment;
import semantic.CEntityCondition;
import semantic.CEntityDataSection;
import semantic.CEntityEnvironmentVariable;
import semantic.CEntityExternalDataStructure;
import semantic.CEntityFileDescriptor;
import semantic.CEntityFileDescriptorLengthDependency;
import semantic.CEntityFormatedVarReference;
import semantic.CEntityIndex;
import semantic.CEntityInline;
import semantic.CEntityMoveReference;
import semantic.CEntityNamedCondition;
import semantic.CEntityProcedure;
import semantic.CEntityProcedureDivision;
import semantic.CEntityProcedureSection;
import semantic.CEntitySQLCursorSection;
import semantic.CEntitySortedFileDescriptor;
import semantic.CEntityStructure;
import semantic.CEntityUnknownReference;
import semantic.CSubStringAttributReference;
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
import semantic.Verbs.CEntityCloseFile;
import semantic.Verbs.CEntityContinue;
import semantic.Verbs.CEntityConvertReference;
import semantic.Verbs.CEntityCount;
import semantic.Verbs.CEntityDisplay;
import semantic.Verbs.CEntityDivide;
import semantic.Verbs.CEntityExec;
import semantic.Verbs.CEntityGoto;
import semantic.Verbs.CEntityInc;
import semantic.Verbs.CEntityInitialize;
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
import semantic.expression.CEntityDigits;
import semantic.expression.CEntityExprOpposite;
import semantic.expression.CEntityExprProd;
import semantic.expression.CEntityExprSum;
import semantic.expression.CEntityExprTerminal;
import semantic.expression.CEntityFunctionCall;
import semantic.expression.CEntityInternalBool;
import semantic.expression.CEntityIsFileEOF;
import semantic.expression.CEntityIsNamedCondition;
import semantic.expression.CEntityLengthOf;
import semantic.expression.CEntityList;
import semantic.expression.CEntityNumber;
import semantic.expression.CEntityString;
import semantic.expression.CEntityConstant.Value;
import semantic.forms.CEntityFieldArrayReference;
import semantic.forms.CEntityFieldAttribute;
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
import utils.CGlobalCatalog;
import utils.CObjectCatalog;
import utils.NacaTransAssertException;



/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaEntityFactory extends CBaseEntityFactory
{

	public void InitCustomGlobalEntities(CGlobalCatalog cat)
	{		
		// manage HEXZONE
		CObjectCatalog ocat = new CObjectCatalog(cat, null, null, null) ;
		CEntityExternalDataStructure structure = new CJavaExternalDataStructure(0, "HEXZONE", ocat, null);
		structure.SetInline(true) ;
		CEntityAttribute att1 = new CJavaAttribute(0, "HEX-0E04", ocat, null) ;
		att1.SetTypeString(2) ;
		att1.SetInitialValue(getSpecialConstantValue("\u000E\u009C")) ;
		structure.AddChild(att1) ;
		ocat.RegisterAttribute(att1) ;
		CEntityAttribute att2 = new CJavaAttribute(0, "HEX-FF", ocat, null) ;
		att2.SetTypeString(1) ;
		att2.SetInitialValue(new CJavaString(ocat, null, new char[] {'\u00FF'})) ;
		structure.AddChild(att2) ;
		ocat.RegisterAttribute(att2) ;
		CEntityAttribute att3 = new CJavaAttribute(0, "HEX-80", ocat, null) ;
		att3.SetTypeString(1) ;
		att3.SetInitialValue(new CJavaString(ocat, null, new char[] {'\u0080'})) ;
		structure.AddChild(att3) ;
		ocat.RegisterAttribute(att3) ;
		cat.RegisterExternalDataStructure(structure) ;
	}
	
	public void InitCustomCICSEntities()
	{
		// some entries are no longer defined here : look into Pub2000/NacaTransRules.xml	
		NewEntityGetKeyPressed("EIBAID") ;
		NewEntitySQLCode("SQLCODE") ;
		NewEntitySQLCode("SQLERRD") ;
		
		
	}

	/**
	 * @param cat
	 */
	public CJavaEntityFactory(CObjectCatalog cat, CBaseLanguageExporter out)	{
		super(cat, out);
	}
	
	public CEntitySQLSelectStatement NewEntitySQLSelectStatement(int nLine, String csStatement, Vector<CDataEntity> arrParameters, Vector<CDataEntity> arrInto, Vector<CDataEntity> arrInd)	{
		return new CJavaSQLSelectStatement(nLine, m_ProgramCatalog, m_LangOutput, csStatement, arrParameters, arrInto, arrInd);
	}
	public CEntitySQLCursorSelectStatement NewEntitySQLCursorSelectStatement(int nLine)	{
		return new CJavaSQLCursorSelectStatement(nLine, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLFetchStatement NewEntitySQLFetchStatement(int nLine, CEntitySQLCursor cur)	{
		return new CJavaSQLFetchStatement(nLine, m_ProgramCatalog, m_LangOutput, cur);
	}
	public CEntitySQLOpenStatement NewEntitySQLOpenStatement(int nLine, CEntitySQLCursor cur)	{
		return new CJavaSQLOpenStatement(nLine, m_ProgramCatalog, m_LangOutput, cur);
	}
	public CEntitySQLCloseStatement NewEntitySQLCloseStatement(int nLine, CEntitySQLCursor cur)	{
		return new CJavaSQLCloseStatement(nLine, m_ProgramCatalog, m_LangOutput, cur);
	}
	public CEntitySQLDeleteStatement NewEntitySQLDeleteStatement(int nLine, String csStatement, Vector<CDataEntity> arrParameters)	{
		return new CJavaSQLDeleteStatement(nLine, m_ProgramCatalog, m_LangOutput, csStatement, arrParameters);
	}
	public CEntitySQLUpdateStatement NewEntitySQLUpdateStatement(int nLine, String csStatement, Vector<CDataEntity> arrSets, Vector<CDataEntity> arrParameters)	{
		return new CJavaSQLUpdateStatement(nLine, m_ProgramCatalog, m_LangOutput, csStatement, arrSets, arrParameters);
	}
	public CEntitySQLInsertStatement NewEntitySQLInsertStatement(int nLine)	{
		return new CJavaSQLInsertStatement(nLine, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLDeclareTable NewEntitySQLDeclareTable(int nLine, String csTableName, String csViewName, ArrayList arrTableColDescription)	{
		return new CJavaSQLDeclareTable(nLine, m_ProgramCatalog, m_LangOutput, csTableName, csViewName, arrTableColDescription);
	}
	public CEntityClass NewEntityClass(int l, String name)	{
		return new CJavaClass(l, name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityComment NewEntityComment(int l, String comment)	{
		return new CJavaComment(l, m_ProgramCatalog, m_LangOutput, comment);
	}
	public CEntityAttribute NewEntityAttribute(int l, String name)	{
		return new CJavaAttribute(l, name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityStructure NewEntityStructure(int l, String name, String level)	{
		return new CJavaStructure(l, name, m_ProgramCatalog, m_LangOutput, level);
	}
	public CEntityProcedure NewEntityProcedure(int l, String name, CEntityProcedureSection section)	{
		return new CJavaProcedure(l, name, m_ProgramCatalog, m_LangOutput, section);
	}
	public CEntityProcedureSection NewEntityProcedureSection(int l, String name)	{
		return new CJavaProcedureSection(l, name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityAssign NewEntityAssign(int l)	{
		return new CJavaAssign(l, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityExternalDataStructure NewEntityExternalDataStructure(int l, String name)	{
		return new CJavaExternalDataStructure(l, name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityInline NewEntityInline(int l, CBaseExternalEntity ext)	{
		return new CJavaInline(l, m_ProgramCatalog, m_LangOutput, ext);
	}
	public CEntityCondition NewEntityCondition(int l)	{
		return new CJavaCondition(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityBloc NewEntityBloc(int l)	{
		return new CJavaBloc(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCalcul NewEntityCalcul(int l)	{
		return new CJavaCalcul(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySqlOnErrorGoto NewEntitySQLOnErrorGoto(int l, String ref)	{
		return new CJavaSqlOnErrorGoto(l, m_ProgramCatalog, m_LangOutput, ref, false) ;
	}
	public CEntitySqlOnErrorGoto NewEntitySQLOnWarningGoto(int l, String ref)	{
		return new CJavaSqlOnErrorGoto(l, m_ProgramCatalog, m_LangOutput, ref, true) ;
	}
	public CEntityExec NewEntityExec(int l, String statement)	{
		return new CJavaExec(l, m_ProgramCatalog, m_LangOutput, statement);
	}
	public CEntityResourceFormContainer NewEntityFormContainer(int l, String name, boolean bSave)	{
		return new CJavaFormContainer(l, name, m_ProgramCatalog, m_LangOutput, bSave);
	}
	public CEntityResourceForm NewEntityForm(int l, String name, boolean bSave)	{
		return new CJavaForm(l, name, m_ProgramCatalog, m_LangOutput, bSave);
	}
	public CEntityFieldAttribute NewEntityFieldAttribute(int l, String name, CDataEntity owner)	{
		return new CJavaFieldAttribute(l, name, m_ProgramCatalog, m_LangOutput, owner);
	}
	public CEntityCallFunction NewEntityCallFunction(int l, String reference, String csRefThru, CEntityProcedureSection section)	{
		return new CJavaCallFunction(l, m_ProgramCatalog, m_LangOutput, reference, csRefThru, section);
	}
	public CEntityInitialize NewEntityInitialize(int l, CDataEntity data)	{
		return new CJavaInitialize(l, m_ProgramCatalog, m_LangOutput, data);
	}
	public CEntityReturn NewEntityReturn(int l)	{
		return new CJavaReturn(l, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityCallProgram NewEntityCallProgram(int l, CDataEntity reference)	{
		return new CJavaCallProgram(l, m_ProgramCatalog, m_LangOutput, reference);
	}
	public CEntitySwitchCase NewEntitySwitchCase(int l)	{
		return new CJavaSwitchCase(l, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityCase NewEntityCase(int l, int endline)	{
		return new CJavaCase(l, m_ProgramCatalog, m_LangOutput, endline);
	}
	public CSubStringAttributReference NewEntitySubString(int l)	{
		return new CJavaSubStringReference(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityArrayReference NewEntityArrayReference(int l)	{
		return new CJavaArrayReference(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityGoto NewEntityGoto(int l, String Reference, CEntityProcedureSection section)	{
		return new CJavaGoto(l, m_ProgramCatalog, m_LangOutput, Reference, section) ;
	}
	public CEntityLoopWhile NewEntityLoopWhile(int l)	{
		return new CJavaLoopWhile(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityLoopIter NewEntityLoopIter(int l)	{
		return new CJavaLoopIter(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityAddTo NewEntityAddTo(int l)	{
		return new CJavaAddTo(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityContinue NewEntityContinue(int l)	{
		return new CJavaContinue(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityNextSentence NewEntityNextSentence(int l)	{
		return new CJavaNextSentence(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityNamedCondition NewEntityNamedCondition(int l, String name)	{
		return new CJavaNamedCondition(l, name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLSingleStatement NewEntitySQLSingleStatement(int l, String st)	{
		return new CJavaSQLSingleStatement(l, m_ProgramCatalog, m_LangOutput, st);
	}
	public CEntitySetColor NewEntitySetColor(int l, CDataEntity field)	{
		m_ProgramCatalog.addImportDeclaration("MAP") ;
		return new CJavaSetColor(l, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntityFieldLength NewEntityFieldLengh(int l, String name, CDataEntity field)	{
		return new CJavaFieldLength(l, name, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntityFieldColor NewEntityFieldColor(int l, String name, CDataEntity field)	{
		return new CJavaFieldColor(l, name, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntityFieldHighlight NewEntityFieldHighlight(int l, String name, CDataEntity field)	{
		return new CJavaFieldHighligh(l, name, m_ProgramCatalog, m_LangOutput, field) ;
	}
//	public CEntityFieldFlag NewEntityFieldFlag(int l, String name, CBaseDataEntity field)
//	{
//		return new CJavaFieldFlag(l, name, m_ProgramCatalog, m_LangOutput, field) ;
//	}
	public CEntityFieldFlag NewEntityFieldFlag(int l, String name, CDataEntity field)	{
		return new CJavaFieldFlag(l, name, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntitySetHighligh NewEntitySetHighlight(int l, CDataEntity field)	{
		m_ProgramCatalog.addImportDeclaration("MAP") ;
		return new CJavaSetHighlight(l, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntitySetFlag NewEntitySetFlag(int l, CDataEntity field)	{
		return new CJavaSetFlag(l, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntitySetCursor NewEntitySetCursor(int l, CDataEntity field)	{
		return new CJavaSetCursor(l, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntitySetAttribute NewEntitySetAttribute(int l, CDataEntity field)	{
		m_ProgramCatalog.addImportDeclaration("MAP") ;
		return new CJavaSetAttribute(l, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntityAssignWithAccessor NewEntityAssignWithAccessor(int l)	{
		return new CJavaAssignWithAccessor(l, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityFieldData NewEntityFieldData(int l, String name, CDataEntity field)	{
		return new CJavaFieldData(l, name, m_ProgramCatalog, m_LangOutput, field);
	}
	public CResourceStrings NewResourceString(int nbLines, int nbCols)	{
		return new CJavaResourceStrings(nbLines, nbCols);
	}
	public CEntityEnvironmentVariable NewEntityEnvironmentVariable(String name, String acc, boolean bNumeric)	{
		return new CJavaEnvironmentVariable(0, name, m_ProgramCatalog, m_LangOutput, acc, bNumeric);
	}
	public CEntityEnvironmentVariable NewEntityEnvironmentVariable(String name, String acc, String write, boolean bNumeric)	{
		return new CJavaEnvironmentVariable(0, name, m_ProgramCatalog, m_LangOutput, acc, write, bNumeric);
	}
//	public CEntityFormAccessor NewEntityFormAccessor(int l, String name, CEntityResourceForm owner)	{
//		return new CJavaFormAccessor(l, name, m_ProgramCatalog, m_LangOutput, owner);
//	}
	public CEntitySkipFields NewEntityWorkingSkipField(int l, String name, int nbFields, String level)	{
		return new CJavaSkipField(l, name, m_ProgramCatalog, m_LangOutput, nbFields, level);
	}
	public CEntityResourceField NewEntityEntryField(int l, String name)	{
		return new CJavaField(l, name, m_ProgramCatalog, m_LangOutput);	
	}
	public CEntityResourceField NewEntityLabelField(int l)	{
		return new CJavaLabelField(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityFieldRedefine NewEntityFieldRedefine(int l, String name, String level)	{
		return new CJavaFieldRedefine(l, name, m_ProgramCatalog, m_LangOutput, level);	
	}
	public CEntityFormRedefine NewEntityFormRedefine(int l, String name, CDataEntity eForm, boolean bSaveMap)	{
		//m_ProgramCatalog.addImportDeclaration("MAP") ;
		return new CJavaFormRedefine(l, name, m_ProgramCatalog, m_LangOutput, eForm, bSaveMap);
	}
	public CEntityString NewEntityString(char[] value)	{
		CJavaString e = new CJavaString(m_ProgramCatalog, m_LangOutput, value) ;
		return e ;
	}
	public CEntityCondOr NewEntityCondOr()	{
		return new CJavaCondOr();
	}
	public CEntityNumber NewEntityNumber(String value)	{
		return new CJavaEntityNumber(m_ProgramCatalog, m_LangOutput, value) ;
	}
	public CEntityExprTerminal NewEntityExprTerminal(CDataEntity eData)	{
		return new CJavaExprTerminal(eData);
	}
	public CEntityExprSum NewEntityExprSum()	{
		return new CJavaExprSum();
	}
	public CEntityExprProd NewEntityExprProd()	{
		return new CJavaExprProd();
	}
	public CEntityCondNot NewEntityCondNot()	{
		return new CJavaCondNot();
	}
	public CEntityCondEquals NewEntityCondEquals()	{
		return new CJavaCondEquals();
	}
	public CEntityCondCompare NewEntityCondCompare()	{
		return new CJavaCondCompare();
	}
	public CEntityCondAnd NewEntityCondAnd()	{
		return new CJavaCondAnd();
	}
	public CEntityCondIsAll NewEntityCondIsAll()	{
		return new CJavaCondIsAll();
	}
	public CEntityCondIsKindOf NewEntityCondIsKindOf()	{
		return new CJavaCondIsKindOf();
	}
	public CEntityCondIsConstant NewEntityCondIsConstant()	{
		return new CJavaCondIsConstant() ;
	}
	public CEntityIsFieldFlag NewEntityIsFieldFlag()	{
		return new CJavaIsFieldFlag();
	}
	public CEntitySetConstant NewEntitySetConstant(int l)	{
		return new CJavaSetConstant(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityIsFieldColor NewEntityIsFieldColor()	{
		m_ProgramCatalog.addImportDeclaration("MAP") ;
		return new CJavaIsFieldColor();
	}
	public CEntityIsFieldAttribute NewEntityIsFieldAttribute()	{
		m_ProgramCatalog.addImportDeclaration("MAP") ;
		return new CJavaIsFieldAttribute() ;
	}
	public CEntityAddressReference NewEntityAddressReference(CDataEntity ref)	{
		return new CJavaAddressReference(m_ProgramCatalog, m_LangOutput, ref);
	}
	public CEntityMoveReference NewEntityMoveReference(int l)	{
		return new CJavaMoveReference(l, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntitySubtractTo NewEntitySubtractTo(int l)	{
		return new CJavaSubtractTo(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityIsNamedCondition NewEntityIsNamedCondition()	{
		return new CJavaIsNamedCondition();
	}
	public CEntityDataSection NewEntityDataSection(int l, String name)	{
		return new CJavaDataSection(l, name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityReplace NewEntityReplace(int l)	{
		return new CJavaReplace(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityIsFieldHighlight NewEntityIsFieldHighlight(CDataEntity ref)	{
		m_ProgramCatalog.addImportDeclaration("MAP") ;
		return new CJavaIsFieldHighlight(ref) ;
	}
	public CEntityFieldValidated NewEntityFieldValidated(int l, String name, CDataEntity field)	{
		return new CJavaFieldValidated(l, name, m_ProgramCatalog, m_LangOutput, field) ;
	}
	public CEntityStringConcat NewEntityStringConcat(int l)	{
		return new CJavaStringConcat(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityDivide NewEntityDivide(int l)	{
		return new CJavaDivide(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityMultiply NewEntityMultiply(int l)	{
		return new CJavaMultiply(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityParseString NewEntityParseString(int l)	{
		return new CJavaParseString(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLRollBack NewEntitySQLRollBack(int l)	{
		return new CJavaSQLRollBack(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLCommit NewEntitySQLCommit(int l)	{
		return new CJavaSQLCommit(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityExprOpposite NewEntityExprOpposite()	{
		return new CJavaExprOpposite();
	}
	public CEntityCICSXctl NewEntityCICSXctl(int l)	{
		return new CJavaCICSXctl(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSLink NewEntityCICSLink(int l)	{
		return new CJavaCICSLink(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSAddress NewEntityCICSAddress(int l) {
		return new CJavaCICSAddress(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSAskTime NewEntityCICSAskTime(int l)	{
		return new CJavaCICSAskTime(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCurrentDate NewEntityCurrentDate()	{
		return new CJavaCurrentDate(m_ProgramCatalog, m_LangOutput);
	}
	public CEntityAddressOf NewEntityAddressOf(CDataEntity data)	{
		return new CJavaAddressOf(m_ProgramCatalog, m_LangOutput, data);
	}
	public CEntityLengthOf NewEntityLengthOf(CDataEntity data)	{
		return new CJavaLengthOf(m_ProgramCatalog, m_LangOutput, data);
	}
	public CEntityCICSHandleCondition NewEntityCICSHandleCondition(int l)	{
		return new CJavaCICSHandleCondition(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSHandleAID NewEntityCICSHandleAID(int l)	{
		return new CJavaCICSHandleAID(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSIgnoreCondition NewEntityCICSIgnoreCondition(int l)	{
		return new CJavaCICSIgnoreCondition(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSRetrieve NewEntityCICSRetreive(int l, boolean bPointer)	{
		return new CJavaCICSRetrieve(l, m_ProgramCatalog, m_LangOutput, bPointer);
	}
	public CEntityCICSStart NewEntityCICSStart(int l, CDataEntity TID)	{
		return new CJavaCICSStart(l, m_ProgramCatalog, m_LangOutput, TID);
	}
	public CEntityCICSReturn NewEntityCICSReturn(int l)	{
		return new CJavaCICSReturn(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSSendMap NewEntityCICSSendMap(int l)	{
		return new CJavaCICSSendMap(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSWrite NewEntityCICSWrite(int l)	{
		return new CJavaCICSWrite(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSReceiveMap NewEntityCICSReceiveMap(int l, CDataEntity name)	{
		return new CJavaCICSReceiveMap(l, m_ProgramCatalog, m_LangOutput, name);
	}
	public CEntityIsFieldModified NewEntityIsFieldModified() {
		return new CJavaIsFieldModified();
	}
	public CEntityCICSSyncPoint NewEntityCICSSyncPoint(int l, boolean bRollBack)	{
		return new CJavaCICSSyncPoint(l, m_ProgramCatalog, m_LangOutput, bRollBack);
	}
	public CEntityCICSInquire NewEntityCICSInquire(int l)	{
		return new CJavaCICSInquire(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSAbend NewEntityCICSAbend(int l)	{
		return new CJavaCICSAbend(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSRead NewEntityCICSRead(int l, CEntityCICSRead.CEntityCICSReadMode mode)	{
		return new CJavaCICSRead(l, m_ProgramCatalog, m_LangOutput, mode);
	}
	public CEntityCICSStartBrowse NewEntityCICSStartBrowse(int l)	{
		return new CJavaCICSStartBrowse(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSDeleteQ NewEntityCICSDeleteQ(int l, boolean b)	{
		return new CJavaCICSDeleteQ(l, m_ProgramCatalog, m_LangOutput, b);
	}
	public CEntityCICSWriteQ NewEntityCICSWriteQ(int l, boolean b)	{
		return new CJavaCICSWriteQ(l, m_ProgramCatalog, m_LangOutput, b);
	}
	public CEntityCICSReadQ NewEntityCICSReadQ(int l, boolean b)	{
		return new CJavaCICSReadQ(l, m_ProgramCatalog, m_LangOutput, b);
	}
	public CEntityCICSAssign NewEntityCICSAssign(int l)	{
		return new CJavaCICSAssign(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityDisplay NewEntityDisplay(int l, boolean b)	{
		return new CJavaDisplay(l, m_ProgramCatalog, m_LangOutput, b);
	}
	public CEntityCount NewEntityCount(int l)	{
		return new CJavaCount(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSReWrite NewEntityCICSReWrite(int l)	{
		return new CJavaCICSReWrite(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSDelay NewEntityCICSDelay(int l)	{
		return new CJavaCICSDelay(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSSetTDQueue NewEntityCICSSetTDQueue(int l)	{
		return new CJavaCICSSetTDQueue(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSDeQ NewEntityCICSDeQ(int l)	{
		return new CJavaCICSDeQ(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSEnQ NewEntityCICSEnQ(int l)	{
		return new CJavaCICSEnQ(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityProcedureDivision NewEntityProcedureDivision(int l)	{
		return new CJavaProcedureDivision(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLCursorSection NewEntitySQLCursorSection()	{
		return new CJavaSQLCursorSection(m_ProgramCatalog, m_LangOutput);
	}
	public CEntityFieldArrayReference NewEntityFieldArrayReference(int l)	{
		return new CJavaFieldArrayReference(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityIndex NewEntityIndex(String name)	{
		return new CJavaIndex(name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLCursor NewEntitySQLCursor(String name)	{
		return new CJavaSQLCursor(name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityKeyPressed NewEntityKeyPressed(String name, String caption)	{
		//m_ProgramCatalog.UseMapSupport() ;
		return new CJavaKeyPressed(0, name, m_ProgramCatalog, m_LangOutput, caption);
	}
	public CEntityGetKeyPressed NewEntityGetKeyPressed(String name)	{
		return new CJavaGetKeyPressed(name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityIsKeyPressed NewEntityIsKeyPressed()	{
		m_ProgramCatalog.addImportDeclaration("KEYPRESSED") ;
		return new CJavaIsKeyPressed();
	}
	public CEntityFieldOccurs NewEntityFieldOccurs(int l, String name)	{
		return new CJavaFieldOccurs(l, name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityUnknownReference NewEntityUnknownReference(int nLine, String csName)
	{
		return new CJavaUnknownReference(nLine, csName, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCICSGetMain NewEntityCICSGetMain(int l)	{
		return new CJavaCICSGetMain(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityResetKeyPressed NewEntityResetKeyPressed(int l)	{
		return new CJavaResetKeyPressed(l, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityResourceFieldArray NewEntityFieldArray()	{
		return new CJavaFieldArray(0, "", m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLCode NewEntitySQLCode(String name)	{
		return new CJavaSQLCode(name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySQLCode NewEntitySQLCode(String name, CBaseEntityExpression eHistoryItem)	{
		return new CJavaSQLCode(name, m_ProgramCatalog, m_LangOutput, eHistoryItem);
	}
	public CEntityCondIsSQLCode NewEntityCondIsSQLCode()	{
		m_ProgramCatalog.addImportDeclaration("SQL") ;
		return new CJavaCondIsSQLCode();
	}
	public CEntityRoutineEmulationCall NewEntityRoutineEmulationCall(int l)	{
		return new CJavaRoutineEmulationCall(l, m_ProgramCatalog, m_LangOutput) ;
	}

	protected Hashtable<String, CDataEntity> m_tabConstantValues = new Hashtable<String, CDataEntity>() ; 
	public void addSpecialConstantValue(String value, String constant)
	{
		m_tabConstantValues.put(value, new CJavaConstantValue(m_ProgramCatalog, m_LangOutput, constant));
	}
	public CDataEntity getSpecialConstantValue(String value)
	{
		if (m_tabConstantValues.containsKey(value))
		{
			return m_tabConstantValues.get(value) ;
		}
		else
		{
			int n = m_ProgramCatalog.GetNbMap() ;
			for (int i=0; i<n; i++)
			{
				CEntityResourceForm form = m_ProgramCatalog.GetMap(i) ; 
				if (form.isFormAlias(value))
				{
					String code = "LanguageCode."+CResourceStrings.getOfficialLanguageCode(value);
					m_ProgramCatalog.addImportDeclaration("MAP") ;
					return new CJavaConstantValue(m_ProgramCatalog, m_LangOutput, code) ;
				}
			}
			return null;
		}
	}
	public Vector<CDataEntity> getAllSpecialConstantAttributes()
	{
		Vector<CDataEntity> arr = new Vector<CDataEntity>() ; 
		Enumeration<CDataEntity> iter = m_tabConstantValues.elements();
		while (iter.hasMoreElements())
		{
			arr.add(iter.nextElement()) ;
		}
		return arr ;
	}	
	public CEntityConcat NewEntityConcat(CDataEntity e1, CDataEntity e2)	{
		return new CJavaConcat(m_ProgramCatalog, m_LangOutput, e1, e2) ;
	}
	public CEntityIsFieldCursor NewEntityIsFieldCursor()	{
		return new CJavaIsFieldCursor() ;
	}
	public CEntityList NewEntityList(String name)	{
		return new CJavaList(name, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityDigits NewEntityDigits(CDataEntity nel)	{
		return new CJavaDigits(m_ProgramCatalog, m_LangOutput, nel);
	}
	public CEntitySearch NewEntitySearch(int line)	{
		return new CJavaSearch(line, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityInternalBool NewEntityInternalBool(String name)	{
		return new CJavaInternalBool(name, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityBreak NewEntityBreak(int line)	{
		return new CJavaBreak(line, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityFileDescriptor NewEntityFileDescriptor(int line, String name) {
		return new CJavaFileDescriptor(line, name, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntitySortedFileDescriptor NewEntitySortedFileDescriptor(int line, String name)	{
		return new CJavaSortedFileDescriptor(line, name, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityOpenFile NewEntityOpenFile(int line) 	{
		return new CJavaOpenFile(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityCloseFile NewEntityCloseFile(int line)	{
		return new CJavaCloseFile(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityReadFile NewEntityReadFile(int line)	{
		return new CJavaReadFile(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityWriteFile NewEntityWriteFile(int line) {
		return new CJavaWriteFile(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityAccept NewEntityAccept(int line){
		return new CJavaAccept(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySort NewEntitySort(int line)	{
		return new CJavaSort(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySortRelease NewEntitySortRelease(int line)	{
		return new CJavaSortRelease(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntitySortReturn NewEntitySortReturn(int line)	{
		return new CJavaSortReturn(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityRewriteFile NewEntityRewriteFile(int line)	{
		return new CJavaRewriteFile(line, m_ProgramCatalog, m_LangOutput);
	}
	public CEntityAddress NewEntityAddress(String csAddresse)	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntityFunctionCall NewEntityFunctionCall(String mehodName, CDataEntity object)	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntityCondIsBoolean NewEntityCondIsBoolean()	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntitySQLSessionDeclare NewEntitySQLSessionDeclare(int line)	{
		return new CJavaSQLSessionDeclare(line, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntitySQLSessionDrop NewEntitySQLSessionDrop(int line)	{
		return new CJavaSQLSessionDrop(line, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntitySQLLock NewEntitySQLLock(int line)	{
		return new CJavaSQLLock(line, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntitySQLExecute NewEntitySQLExecute(int line)	{
		return new CJavaSQLExecute(line, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityFormatedVarReference NewEntityFormatedVarReference(CDataEntity object, String format)	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntityInc NewEntityInc(int line)	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntityConvertReference NewEntityConvert(int line)	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntityIsFileEOF NewEntityIsFileEOF(CEntityFileDescriptor fb)	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntityConstant NewEntityConstant(Value val) {
		return new CJavaConstant(val) ;
	}
	public CEntityFileDescriptorLengthDependency NewEntityFileDescriptorLengthDependency(String name)	{
		return new CJavaFileDescriptorLengthDependency(name, m_ProgramCatalog, m_LangOutput) ;
	}
	public CEntityAssignSpecial NewEntityAssignSpecial(int l)	{
		throw new NacaTransAssertException("Method not implemented") ;
	}
	public CEntitySQLCall NewEntitySQLCall(int line) {
		return new CJavaSQLCall(line, m_ProgramCatalog, m_LangOutput);
	}


}
