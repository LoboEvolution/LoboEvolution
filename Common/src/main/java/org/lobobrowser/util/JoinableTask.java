/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.util;

/**
 * A task that can be used in a thread or thread pool. The caller can wait for
 * the task to finish by joining it.
 */
public abstract class JoinableTask implements SimpleThreadPoolTask {
    /** The done. */
    private boolean done = false;
    
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public final void run() {
        try {
            this.execute();
        } finally {
            synchronized (this) {
                this.done = true;
                this.notifyAll();
            }
        }
    }
    
    /**
     * Force done.
     */
    public final void forceDone() {
        synchronized (this) {
            this.done = true;
            this.notifyAll();
        }
    }
    
    /**
     * Join.
     *
     * @throws InterruptedException
     *             the interrupted exception
     */
    public void join() throws InterruptedException {
        synchronized (this) {
            while (!this.done) {
                this.wait();
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.lobobrowser.util.SimpleThreadPoolTask#cancel()
     */
    @Override
    public void cancel() {
        this.forceDone();
    }
    
    /**
     * Execute.
     */
    protected abstract void execute();
}
