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
package parser.FPac.elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.FPac.CFPacElement;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntityAssignWithAccessor;
import semantic.expression.CBaseEntityExpression;

public class CFPacAssign extends CFPacElement
{
	
	protected CIdentifier m_Identifier= null ;
	protected CExpression m_expression = null ;

	public CFPacAssign(int line, CIdentifier id, CExpression exp)
	{
		super(line);
		m_Identifier = id ;
		m_expression = exp ;
	}

	@Override
	protected boolean DoParsing()
	{
		// nothing : done in CFPacCodeBloc.ParseLine
		return true;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CDataEntity eid = m_Identifier.GetDataReference(getLine(), factory) ;
		if (eid != null)
		{
			CBaseEntityExpression exp = m_expression.AnalyseExpression(factory) ;
			if (exp != null)
			{
				if (eid.HasAccessors())
				{
					CEntityAssignWithAccessor ass = factory.NewEntityAssignWithAccessor(getLine())  ;
					parent.AddChild(ass) ;
					ass.SetAssign(eid, exp) ;
					return ass ;
				}
				else
				{
					CEntityAssign ass = factory.NewEntityAssign(getLine())  ;
					parent.AddChild(ass) ;
					ass.SetValue(exp);
					ass.AddRefTo(eid) ;
					return ass ;
				}
			}
		}
		return null ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Assign") ;
		Element eVar = root.createElement("Var") ;
		e.appendChild(eVar) ;
		m_Identifier.ExportTo(eVar, root) ;
		Element eVal = root.createElement("Value") ;
		e.appendChild(eVal) ;
		eVal.appendChild(m_expression.Export(root));
		return e;
	}

}
