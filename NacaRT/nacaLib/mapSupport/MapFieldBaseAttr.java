/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 30 nov. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.mapSupport;

public abstract class MapFieldBaseAttr
{
	protected MapFieldBaseAttr(int nValue, String text)
	{
		m_nValue = nValue;
		m_csText = text ;
	}
	
	int getBitEncoding()
	{
		return m_nValue;
	}	

//	void setBitEncoding(int nValue)
//	{
//		m_nValue = nValue;
//	}		

	
	private int m_nValue = 0;
	private String m_csText = "" ;
	protected int getInternalValue()
	{
		return m_nValue ;
	}
	public String toString()
	{
		return getText();
	}
	public String getText()
	{
		return m_csText ;
	}
	
	public String getSTCheckValue()
	{
		return toString();
	}
	
//	abstract static int getNbBitsEncoding();
//	abstract static int getMask();
	
	/*
	Caractéristique de chaque mapset, map ou champs, qui décrit précisément les environnements. par exemple pour un champ, les attributs indiqueront la position (ligna colonne), la longueur, le type d?information (alphanumérique,...), le cadrage (droite, gauche), la brillance, reverse vidée, protége ou non, etc
	Pour les champs libellés, les attributs sont représentés par un suffixe, qui indique le type d?attribut que l?on est en train de traiter. Ce qui explique que la longueur du nom d?un champ est >= 7. Le nombre des attributs générés sera dépendant du type attribut standard ou étendu.
	Les suffixes sont les suivants :
		L 2 octets longueur du champ, si longueur = -1, alors curseur positionné. (PIC S9(4) comp)
		F 1 octet zone flag indique protégé ou non.
		C 1 octet couleur
		H 1 octet brillance, soulignement, reverse vidéo.
		V 1 octet validation
		P 1 octet zone flag
		I/O n octets zone de données
*/
}
