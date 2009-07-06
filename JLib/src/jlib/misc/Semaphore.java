/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

// @see http://www.javaworld.com/javaworld/javaqa/1999-11/02-qa-semaphore.html
/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Semaphore
{
    private int counter;

    public Semaphore() 
    {
        this(0);
    }

    public Semaphore(int n) 
    {
        if (n < 0) 
        	throw new IllegalArgumentException(n + " < 0");
        counter = n;
    }

    /**
     * Increments internal counter, possibly awakening a thread
     * wait()ing in acquire().
     */
    public synchronized void release() 
    {
        if (counter == 0) 
        {
            notify();
        }
        counter++;
    }

    /**
     * Decrements internal counter, blocking if the counter is already
     * zero.
     *
     * @exception InterruptedException passed from this.wait().
     */
    public synchronized void acquire() throws InterruptedException 
	{
        while (counter == 0) 
        {
            wait();
        }
        counter--;
    }

    public synchronized boolean acquireNoInterrupt() 
	{
        while (counter == 0) 
        {
            try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				return false;
			}
        }
        counter--;
        return true;
    }
    

}
