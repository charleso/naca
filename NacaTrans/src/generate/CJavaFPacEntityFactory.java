/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate;

import generate.fpacjava.*;
import generate.java.expressions.CJavaCondEquals;
import generate.java.expressions.CJavaString;
import generate.java.verbs.CJavaBreak;
import generate.java.verbs.CJavaRoutineEmulationCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import semantic.*;
import semantic.CICS.*;
import semantic.CICS.CEntityCICSRead.CEntityCICSReadMode;
import semantic.SQL.*;
import semantic.Verbs.*;
import semantic.Verbs.CEntityDisplay.Upon;
import semantic.expression.*;
import semantic.expression.CEntityConstant.Value;
import semantic.forms.*;
import utils.CObjectCatalog;
import utils.NacaTransAssertException;

public class CJavaFPacEntityFactory extends CBaseEntityFactory
{

	public CJavaFPacEntityFactory(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out);
	}

	@Override
	public CEntityIsFieldCursor NewEntityIsFieldCursor()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityResetKeyPressed NewEntityResetKeyPressed(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSGetMain NewEntityCICSGetMain(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityUnknownReference NewEntityUnknownReference(int nLine, String csName)
	{
		return new CFPacJavaUnknownReference(nLine, csName, m_ProgramCatalog, m_LangOutput);
	}

	@Override
	public CEntityFieldOccurs NewEntityFieldOccurs(int i, String string)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityGetKeyPressed NewEntityGetKeyPressed(String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityIsKeyPressed NewEntityIsKeyPressed()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityKeyPressed NewEntityKeyPressed(String name, String caption)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLCursor NewEntitySQLCursor(String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityIndex NewEntityIndex(String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldArrayReference NewEntityFieldArrayReference(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLCursorSection NewEntitySQLCursorSection()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityProcedureDivision NewEntityProcedureDivision(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSReWrite NewEntityCICSReWrite(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSDelay NewEntityCICSDelay(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSSetTDQueue NewEntityCICSSetTDQueue(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSDeQ NewEntityCICSDeQ(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSEnQ NewEntityCICSEnQ(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCount NewEntityCount(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityInspectConverting NewEntityInspectConverting(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityDisplay NewEntityDisplay(int l, Upon t)
	{
		return new CFPacJavaDisplay(l, m_ProgramCatalog, m_LangOutput, t)  ;
	}

	@Override
	public CEntityCICSAssign NewEntityCICSAssign(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSWriteQ NewEntityCICSWriteQ(int l, boolean b)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSReadQ NewEntityCICSReadQ(int l, boolean b)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSDeleteQ NewEntityCICSDeleteQ(int l, boolean b)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSStartBrowse NewEntityCICSStartBrowse(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSRead NewEntityCICSRead(int l, CEntityCICSReadMode mode)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSAbend NewEntityCICSAbend(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSInquire NewEntityCICSInquire(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSSyncPoint NewEntityCICSSyncPoint(int l, boolean bRollBack)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityIsFieldModified NewEntityIsFieldModified()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSReceiveMap NewEntityCICSReceiveMap(int l, CDataEntity name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSWrite NewEntityCICSWrite(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSSendMap NewEntityCICSSendMap(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSReturn NewEntityCICSReturn(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSStart NewEntityCICSStart(int l, CDataEntity TID)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSRetrieve NewEntityCICSRetreive(int l, boolean bPointer)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSIgnoreCondition NewEntityCICSIgnoreCondition(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSHandleCondition NewEntityCICSHandleCondition(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSHandleAID NewEntityCICSHandleAID(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCurrentDate NewEntityCurrentDate()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityAddressOf NewEntityAddressOf(CDataEntity data)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityLengthOf NewEntityLengthOf(CDataEntity data)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSXctl NewEntityCICSXctl(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSLink NewEntityCICSLink(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSAddress NewEntityCICSAddress(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCICSAskTime NewEntityCICSAskTime(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityExprOpposite NewEntityExprOpposite()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLCommit NewEntitySQLCommit(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLRollBack NewEntitySQLRollBack(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityParseString NewEntityParseString(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityMultiply NewEntityMultiply(int l)
	{
		return new CFPacJavaMultiply(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityDivide NewEntityDivide(int l)
	{
		return new CFPacJavaDivide(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityStringConcat NewEntityStringConcat(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityIsFieldHighlight NewEntityIsFieldHighlight(CDataEntity ref)
	{
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityReplace NewEntityReplace(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityDataSection NewEntityDataSection(int l, String name)
	{
		return new CFPacJavaDataSection(l, name, m_ProgramCatalog, m_LangOutput);
	}

	@Override
	public CEntityIsNamedCondition NewEntityIsNamedCondition()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySubtractTo NewEntitySubtractTo(int l)
	{
		return new CFPacJavaSubtractTo(l, m_ProgramCatalog, m_LangOutput);
	}

	@Override
	public CEntityMoveReference NewEntityMoveReference(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityAddressReference NewEntityAddressReference(CDataEntity ref)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityIsFieldAttribute NewEntityIsFieldAttribute()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityIsFieldColor NewEntityIsFieldColor()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySetConstant NewEntitySetConstant(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityIsFieldFlag NewEntityIsFieldFlag()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCondIsConstant NewEntityCondIsConstant()
	{
		return new CFPacJavaCondIsConstant() ;
	}

	@Override
	public CEntityCondIsKindOf NewEntityCondIsKindOf()
	{
		return new CFPacJavaCondIsKindOf() ;
	}

	@Override
	public CEntityCondIsAll NewEntityCondIsAll()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityString NewEntityString(char[] value)
	{
		return new CJavaString(m_ProgramCatalog, m_LangOutput, value) ;
	}

	@Override
	public CEntityCondOr NewEntityCondOr()
	{
		return new CFPacJavaCondOr() ;
	}

	@Override
	public CEntityNumber NewEntityNumber(String value)
	{
		return new CFPacJavaNumber(m_ProgramCatalog, m_LangOutput, value) ;
	}

	@Override
	public CEntityExprTerminal NewEntityExprTerminal(CDataEntity eData)
	{
		return new CFPacJavaExprTerminal(eData) ;
	}

	@Override
	public CEntityExprSum NewEntityExprSum()
	{
		return new CFPacExprSum() ;
	}

	@Override
	public CEntityExprProd NewEntityExprProd()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCondNot NewEntityCondNot()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCondEquals NewEntityCondEquals()
	{
		return new CJavaCondEquals() ;
	}

	@Override
	public CEntityCondCompare NewEntityCondCompare()
	{
		return new CFPacJavaCondCompare() ;
	}

	@Override
	public CEntityCondAnd NewEntityCondAnd()
	{
		return new CFPacJavaCondAnd() ;
	}

	@Override
	public CEntityClass NewEntityClass(int l, String name)	{
		return new CFPacJavaClass(l, name, m_ProgramCatalog, m_LangOutput);
	}

	@Override
	public CEntityComment NewEntityComment(int l, String comment)
	{
		return new CFPacJavaComment(l, m_ProgramCatalog, m_LangOutput, comment) ;
	}

	@Override
	public CEntityAttribute NewEntityAttribute(int l, String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityStructure NewEntityStructure(int l, String name, String Level)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityProcedure NewEntityProcedure(int l, String name,	CEntityProcedureSection section)
	{
		return new CFPacJavaProcedure(l, name, m_ProgramCatalog, m_LangOutput, section) ;
	}

	@Override
	public CEntityProcedureSection NewEntityProcedureSection(int l, String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityAssign NewEntityAssign(int l)
	{
		return new CFPacJavaAssign(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityExternalDataStructure NewEntityExternalDataStructure(int l,
					String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityInline NewEntityInline(int l, CBaseExternalEntity ext)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCondition NewEntityCondition(int l)
	{
		return new CFPacJavaCondition(l, m_ProgramCatalog,m_LangOutput) ;
	}

	@Override
	public CEntityBloc NewEntityBloc(int l)
	{
		return new CFPacJavaBloc(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityCalcul NewEntityCalcul(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySqlOnErrorGoto NewEntitySQLOnErrorGoto(int l, String ref)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySqlOnErrorGoto NewEntitySQLOnWarningGoto(int l, String ref)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityExec NewEntityExec(int l, String statement)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityResourceFormContainer NewEntityFormContainer(int l,
					String name, boolean bSavCopy)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityResourceForm NewEntityForm(int l, String name,
					boolean bSavCopy)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityResourceField NewEntityEntryField(int l, String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityResourceField NewEntityLabelField(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCallFunction NewEntityCallFunction(int l, String reference,
					String refThru, CEntityProcedureSection section)
	{
		return new CFPacJavaCallFunction(l, m_ProgramCatalog, m_LangOutput, reference) ;
	}

	@Override
	public CEntityInitialize NewEntityInitialize(int l, CDataEntity data)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityReturn NewEntityReturn(int l)
	{
		return new CFPacJavaReturn(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityCallProgram NewEntityCallProgram(int l, CDataEntity reference)
	{
		return new CFPacJavaCallProgram(l, m_ProgramCatalog, m_LangOutput, reference) ;
	}

	@Override
	public CEntitySwitchCase NewEntitySwitchCase(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCase NewEntityCase(int l, int endline)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CSubStringAttributReference NewEntitySubString(int l)
	{
		return new CFPacJavaSubStringAttributeReference(l, m_ProgramCatalog,m_LangOutput) ;
	}

	@Override
	public CEntityArrayReference NewEntityArrayReference(int l)
	{
		return new CFPacJavaArrayReference(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityGoto NewEntityGoto(int l, String Reference, CEntityProcedureSection section)
	{
		return new CFPacJavaGoto(l, m_ProgramCatalog, m_LangOutput, Reference, section) ;
	}

	@Override
	public CEntityGoto NewEntityGotoDepending(int l, List<String> refs, CDataEntity dep, CEntityProcedureSection section)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityLoopWhile NewEntityLoopWhile(int l)
	{
		return new CFPacJavaLoopWhile(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityLoopIter NewEntityLoopIter(int l)
	{
		return new CFPacJavaLoopIter(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityAddTo NewEntityAddTo(int l)
	{
		return new CFPacJavaAddTo(l, m_ProgramCatalog, m_LangOutput);
	}

	@Override
	public CEntityContinue NewEntityContinue(int l)
	{
		return new CFPacJavaContinue(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityNextSentence NewEntityNextSentence(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityNamedCondition NewEntityNamedCondition(int l, String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLSingleStatement NewEntitySQLSingleStatement(int l,
					String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLSelectStatement NewEntitySQLSelectStatement(int l,
					String name, Vector<CDataEntity> arrParameters,
					Vector<CDataEntity> arrInto, Vector<CDataEntity> arrInd)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLCursorSelectStatement NewEntitySQLCursorSelectStatement(
					int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLFetchStatement NewEntitySQLFetchStatement(int l,
					CEntitySQLCursor cur)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLOpenStatement NewEntitySQLOpenStatement(int l,
					CEntitySQLCursor cur)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLCloseStatement NewEntitySQLCloseStatement(int l,
					CEntitySQLCursor cur)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLDeleteStatement NewEntitySQLDeleteStatement(int l,
					String csStatement, Vector<CDataEntity> arrParameters)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLUpdateStatement NewEntitySQLUpdateStatement(int l,
					String csStatement, Vector<CDataEntity> arrSets,
					Vector<CDataEntity> arrParameters)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLInsertStatement NewEntitySQLInsertStatement(int l)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLDeclareTable NewEntitySQLDeclareTable(int nLine,
					String csTableName, String csViewName,
					ArrayList arrTableColDescription)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySetColor NewEntitySetColor(int l, CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldLength NewEntityFieldLengh(int l, String name,
					CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldData NewEntityFieldData(int l, String name,
					CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldColor NewEntityFieldColor(int l, String name,
					CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldAttribute NewEntityFieldAttribute(int l, String name,
					CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldHighlight NewEntityFieldHighlight(int l, String name,
					CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldFlag NewEntityFieldFlag(int l, String name,
					CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldValidated NewEntityFieldValidated(int l, String name,
					CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySetHighligh NewEntitySetHighlight(int l, CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySetFlag NewEntitySetFlag(int l, CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySetCursor NewEntitySetCursor(int l, CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySetAttribute NewEntitySetAttribute(int l, CDataEntity field)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityAssignWithAccessor NewEntityAssignWithAccessor(int l)
	{
		return new CFPacJavaAssignWithAccessor(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CResourceStrings NewResourceString(int nbLines, int nbCols)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityEnvironmentVariable NewEntityEnvironmentVariable(
					String namev, String acc, boolean bNumeric)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityEnvironmentVariable NewEntityEnvironmentVariable(String namev, String acc, String write, boolean bNumeric)
	{
		return new CFPacJavaEnvironmentVariable(0, namev, m_ProgramCatalog, m_LangOutput, acc, write, bNumeric) ;
	}

	@Override
	public CEntitySkipFields NewEntityWorkingSkipField(int l, String name,
					int nbFields, String level)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFieldRedefine NewEntityFieldRedefine(int l, String name,
					String level)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityFormRedefine NewEntityFormRedefine(int l, String name,
					CDataEntity eForm, boolean bSaveMap)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CDataEntity getSpecialConstantValue(String value)
	{
		return null ;
	}

	@Override
	public CEntityResourceFieldArray NewEntityFieldArray()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLCode NewEntitySQLCode(String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySQLCode NewEntitySQLCode(String name,
					CBaseEntityExpression eHistoryItem)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityCondIsSQLCode NewEntityCondIsSQLCode()
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityRoutineEmulationCall NewEntityRoutineEmulationCall(int l)
	{
		return new CJavaRoutineEmulationCall(l, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityConcat NewEntityConcat(CDataEntity e1, CDataEntity e2)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityList NewEntityList(String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityDigits NewEntityDigits(CDataEntity nel)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySearch NewEntitySearch(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityInternalBool NewEntityInternalBool(String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityBreak NewEntityBreak(int line)
	{
		return new CJavaBreak(line, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityFileDescriptor NewEntityFileDescriptor(int line, String name)
	{
		return new CFPacJavaFileDescriptor(line, name, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntitySortedFileDescriptor NewEntitySortedFileDescriptor(int line,
					String name)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityOpenFile NewEntityOpenFile(int line)
	{
		return new CFpacJavaOpenFile(line, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityCloseFile NewEntityCloseFile(int line)
	{
		return new CFPacJavaCloseFile(line, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityReadFile NewEntityReadFile(int line)
	{
		return new CFPacJavaReadFile(line, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityWriteFile NewEntityWriteFile(int line)
	{
		return new CFPacJavaWriteFile(line, m_ProgramCatalog, m_LangOutput) ;
	}

	@Override
	public CEntityAccept NewEntityAccept(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySort NewEntitySort(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySortRelease NewEntitySortRelease(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntitySortReturn NewEntitySortReturn(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityRewriteFile NewEntityRewriteFile(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	@Override
	public CEntityAddress NewEntityAddress(String csAddress)
	{
		return new CFPacJavaAddress(m_ProgramCatalog, m_LangOutput, csAddress);
	}

	@Override
	public CEntityFunctionCall NewEntityFunctionCall(String mehodName, CDataEntity object)
	{
		if (mehodName.equalsIgnoreCase("ReadAndTestFile"))
		{
			return new CFPacJavaReadAndTestFile(m_ProgramCatalog, m_LangOutput, object) ;
		}
		else
		{
			throw new NacaTransAssertException("Method not implemented") ;
		}
	}

	@Override
	public CEntityCondIsBoolean NewEntityCondIsBoolean()
	{
		return new CFPacJavaCondIsBoolean() ;
	}
	
	/**
	 * @see semantic.CBaseEntityFactory#NewEntitySQLLock(int)
	 */
	@Override
	public CEntitySQLSessionDeclare NewEntitySQLSessionDeclare(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}
	
	/**
	 * @see semantic.CBaseEntityFactory#NewEntitySQLLock(int)
	 */
	@Override
	public CEntitySQLSessionDrop NewEntitySQLSessionDrop(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntitySQLLock(int)
	 */
	@Override
	public CEntitySQLLock NewEntitySQLLock(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntitySQLExecute(int)
	 */
	@Override
	public CEntitySQLExecute NewEntitySQLExecute(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntityFormatedVarReference(semantic.CDataEntity, java.lang.String)
	 */
	@Override
	public CEntityFormatedVarReference NewEntityFormatedVarReference(CDataEntity object, String format)
	{
		return new CFPacJavaFormatedVarReference(object, m_ProgramCatalog, m_LangOutput, format) ;
	}
	@Override
	public CEntityInc NewEntityInc(int line)
	{
		return new CFPacJavaInc(line,  m_ProgramCatalog, m_LangOutput);
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntityConvert(int)
	 */
	@Override
	public CEntityConvertReference NewEntityConvert(int line)
	{
		return new CFPacJavaConvertReference(m_ProgramCatalog, m_LangOutput) ;
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntityIsFileEOF(semantic.CEntityFileBuffer)
	 */
	@Override
	public CEntityIsFileEOF NewEntityIsFileEOF(CEntityFileDescriptor fb)
	{
		return new CFPacJavaIsFileEOF(fb) ;
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntityConstant(semantic.expression.CEntityConstant.Value)
	 */
	@Override
	public CEntityConstant NewEntityConstant(Value val)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}

	/**
	 * @see semantic.CBaseEntityFactory#addSpecialConstantValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void addSpecialConstantValue(String value, String constant)
	{
		
	}

	/**
	 * @see semantic.CBaseEntityFactory#getAllSpecialConstantAttributes()
	 */
	@Override
	public Collection<CDataEntity> getAllSpecialConstantAttributes()
	{
		throw new NacaTransAssertException("Method not implemented") ;
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntityFileDescriptorLengthDependency(java.lang.String)
	 */
	@Override
	public CEntityFileDescriptorLengthDependency NewEntityFileDescriptorLengthDependency(String name)
	{
		throw new NacaTransAssertException("Method not implemented") ;
	}

	public CEntityAssignSpecial NewEntityAssignSpecial(int l)	{
		return new CFPacJavaAssignSpecial(l, m_ProgramCatalog, m_LangOutput);
	}

	/**
	 * @see semantic.CBaseEntityFactory#NewEntitySQLCall(int)
	 */
	@Override
	public CEntitySQLCall NewEntitySQLCall(int line)
	{
		// TODO Auto-generated method stub
		throw new NacaTransAssertException("Method not implemented") ;
	}
	
}
