/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

public class Mutex
{
    private Thread  owner = null;  // Owner of mutex, null if nobody
    private int lock_count = 0;

    /**
     * Acquire the mutex. The mutex can be acquired multiple times
     * by the same thread, provided that it is released as many
     * times as it is acquired. The calling thread blocks until
     * it has acquired the mutex. (There is no timeout).
     */
    public synchronized void acquire() throws InterruptedException
    {
        while (tryToAcquire() == false)
        {
            wait();
        }
    }

    /**
     * Attempts to acquire the mutex. Returns false (and does not
     * block) if it can't get it.
     */

    public synchronized boolean tryToAcquire()
    {
        // Try to get the mutex. Return true if you got it.

        if( owner == null )
        {
            owner = Thread.currentThread();
            lock_count = 1;
            return true;
        }

        if(owner == Thread.currentThread())
        {
            ++lock_count;
            return true;
        }

        return false;
    }

    /**
     * Release the mutex. The mutex has to be released as many times
     * as it was acquired to actually unlock the resource. The mutex
     * must be released by the thread that acquired it
     *
     * @throws IllegalStateException (a RuntimeException) if a thread
     *      other than the current owner tries to release the mutex.
     */

    public synchronized void release()
    {
        if (owner != Thread.currentThread())
            throw new IllegalStateException("Thread calling release() doesn't own mutex");

        if (--lock_count <= 0)
        {
            owner = null;
            notify();
        }
    }
}
