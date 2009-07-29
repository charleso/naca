/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.util.ArrayList;

// See http://max.cs.kzoo.edu/~abrady/java/sorting/QSort.html

public class QuickSortExternal<Item> 
{
	protected ArrayList<Item> m_arr = null;
	private BaseQuickSortComparer<Item> m_comparer = null; 
	/** This is a generic version of C.A.R Hoare's Quick Sort 
	* algorithm.  This will handle arrays that are already
	* sorted, and arrays with duplicate keys.
	*
	* If you think of a one dimensional array as going from
	* the lowest index on the left to the highest index on the right
	* then the parameters to this function are lowest index or
	* left and highest index or right.  The first time you call
	* this function it will be with the parameters 0, a.length - 1.
	*
	* @param a       an integer array
	* @param lo0     left boundary of array partition
	* @param hi0     right boundary of array partition
	*/

	public QuickSortExternal(ArrayList<Item> arr, BaseQuickSortComparer<Item> comparer)
	{
		m_arr = arr;
		m_comparer = comparer;	
	}
	
	public void sort()
	{
		if(m_arr != null)
			sort(0, m_arr.size()-1);
	}
	
	private void sort(int lo0, int hi0)
	{
		int lo = lo0;
		int hi = hi0;
		Item mid;

		// pause for redraw
		if ( hi0 > lo0)
		{

			/* Arbitrarily establishing partition element as the midpoint of
			* the array.
			*/
			int nIndex = (lo0 + hi0 ) / 2; 
			mid = m_arr.get(nIndex);

			// loop through the array until indices cross
			while( lo <= hi )
			{
				/* find the first element that is greater than or equal to 
				* the partition element starting from the left Index.
				*/
				while( ( lo < hi0 ) && (m_comparer.compare(m_arr.get(lo), mid) < 0 ) )
					++lo;

				/* find an element that is smaller than or equal to 
				* the partition element starting from the right Index.
				*/
				while( ( hi > lo0 ) && (m_comparer.compare(m_arr.get(hi), mid) > 0 ) )
					--hi;

				// if the indexes have not crossed, swap
				if( lo <= hi ) 
				{
					swap(lo, hi);
					// pause

					++lo;
					--hi;
				}
			}

			/* If the right index has not reached the left side of array
			* must now sort the left partition.
			*/
			if( lo0 < hi )
				sort(lo0, hi );

			/* If the left index has not reached the right side of array
			* must now sort the right partition.
			*/
			if( lo < hi0 )
				sort(lo, hi0 );

		}
	}

	protected void swap(int i, int j)
	{
		Item ti = m_arr.get(i);
		Item tj = m_arr.get(j);
		m_arr.set(i, tj);
		m_arr.set(j, ti);
	}
}
