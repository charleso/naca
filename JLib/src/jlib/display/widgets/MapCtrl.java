/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.display.widgets;

import java.util.Vector;

public class MapCtrl
{
	public class MapMarker
	{
		public String m_csLabel = "" ;
		public double m_dLng = 0 ;
		public double m_dLat = 0 ;
	}
	private double m_dCenterLat = 0 ;
	private double m_dCenterLng = 0 ;
	private int m_nZoom = 0 ;
	
	public double getCenterLng()
	{
		return m_dCenterLng ;
	}
	public void setCenterLng(double d)
	{
		m_dCenterLng = d ;
	}
	public double getCenterLat()
	{
		return m_dCenterLat ;
	}
	public void setCenterLat(double d)
	{
		m_dCenterLat = d ;
	}
	/**
	 * @return Returns the zoom.
	 */
	public int getZoom()
	{
		return m_nZoom;
	}
	/**
	 * @param zoom The zoom to set.
	 */
	public void setZoom(int zoom)
	{
		m_nZoom = zoom;
	}
	public void setCenter(double lat, double lng)
	{
		m_dCenterLat = lat ;
		m_dCenterLng = lng ;
	}
	public void AddMarker(String label, double lat, double lng)
	{
		MapMarker mark = new MapMarker() ;
		mark.m_csLabel = label ;
		mark.m_dLat = lat ;
		mark.m_dLng = lng ;
		m_arrMarkers.add(mark) ;
	}
	protected Vector<MapMarker> m_arrMarkers = new Vector<MapMarker>() ;

	public int getNbmarkers()
	{
		return m_arrMarkers.size() ;
	}
	public MapMarker getMarker(int i)
	{
		return m_arrMarkers.get(i) ;
	}
}
