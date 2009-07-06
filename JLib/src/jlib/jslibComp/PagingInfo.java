/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.jslibComp;

import jlib.misc.JSon;
import jlib.misc.JSonParser;

public class PagingInfo
{
	public int _pageSize;
	public int _minPage = 0;		// PJD Added
	public String _txtKind = "Title";	// PJD Added
	public String _txtOf = "out of";	// PJD Added
	public int _pageNr;
	public int _maxItem;
	public int _maxPage;
	public int _firstItem;
	public int _lastItem;
	//***** sorting *****
	public String _defaultSortType; 
	public String _sortType;
	public boolean _sortAscendant;
	//***** user informations *****
	public int _totalItems;

	public PagingInfo()
	{
		_pageSize = 10;   // max items per pages
		_minPage = 0;		// PJD Added
		_txtKind = "Title";	// PJD Added
		_txtOf = "out of";	// PJD Added
		_pageNr = 1;     // curent page
		_maxItem = -1;   // max items
		_maxPage = 1;    // max number of page
		_firstItem = 0;
		_lastItem = 0;
		_totalItems = -1; // the number of item containing in the DB without filters.(used only to show it to the user interface)
	
		_sortType=" ";  // sorting type (ex: name, )
		_sortAscendant = true;
	}
	
	public String getAsJSon()
	{
		JSon json = new JSon();
		json.setLines(false);
		boolean b = json.exportAsJSon(this);	//._objectsList);
		String csJSon = json.getResult();
		return csJSon;
	}
	
	public boolean setFromJSon(String csJSon)
	{
		JSonParser jsonParser = new JSonParser();
		boolean b = jsonParser.fill(csJSon, this);
		return b; 
	}
}
