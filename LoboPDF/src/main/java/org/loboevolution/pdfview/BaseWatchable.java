/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.pdfview;

import lombok.Getter;
import lombok.Setter;

/**
 * An abstract implementation of the watchable interface, that is extended
 * by the parser and renderer to do their thing.
 */
public abstract class BaseWatchable implements Watchable, Runnable {

    /**
     * suppress local stack trace on setError.
     */
    @Getter
    @Setter
    private static boolean SuppressSetErrorStackTrace = false;

    @Setter
    private static PDFErrorHandler errorHandler = new PDFErrorHandler();
    /**
     * a lock for status-related operations
     */
    private final Object statusLock = new Object();
    /**
     * a lock for parsing operations
     */
    private final Object parserLock = new Object();
    /**
     * the current status, from the list in Watchable
     */
    private int status = Watchable.UNKNOWN;
    /**
     * when to stop
     */
    private Gate gate;
    /**
     * the thread we are running in
     */
    private Thread thread;
    @Getter
    private Exception exception;

    /**
     * Creates a new instance of BaseWatchable
     */
    protected BaseWatchable() {
        setStatus(Watchable.NOT_STARTED);
    }

    /**
     * <p>Getter for the field <code>errorHandler</code>.</p>
     *
     * @return a {@link org.loboevolution.pdfview.PDFErrorHandler} object.
     */
    public static PDFErrorHandler getErrorHandler() {
        if (errorHandler == null) {
            errorHandler = new PDFErrorHandler();
        }
        return errorHandler;
    }

    /**
     * Perform a single iteration of this watchable.  This is the minimum
     * granularity which the go() commands operate over.
     *
     * @return one of three values: <ul>
     * <li> Watchable.RUNNING if there is still data to be processed
     * <li> Watchable.NEEDS_DATA if there is no data to be processed but
     * the execution is not yet complete
     * <li> Watchable.COMPLETED if the execution is complete
     * </ul>
     * @throws java.lang.Exception if any.
     */
    protected abstract int iterate() throws Exception;

    /**
     * Prepare for a set of iterations.  Called before the first iterate() call
     * in a sequence.  Subclasses should extend this method if they need to do
     * anything to setup.
     */
    protected void setup() {
        // do nothing
    }

    /**
     * Clean up after a set of iterations. Called after iteration has stopped
     * due to completion, manual stopping, or error.
     */
    protected void cleanup() {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            Thread.sleep(1);
            if (getStatus() == Watchable.NOT_STARTED) {
                setup();
            }

            setStatus(Watchable.PAUSED);

            while (!isFinished() && getStatus() != Watchable.STOPPED) {
                // Check if executable without holding any locks first
                if (!isExecutable()) {
                    // Wait for status change without any other locks held
                    synchronized (this.statusLock) {
                        while (!isExecutable() && !isFinished() && getStatus() != Watchable.STOPPED) {
                            try {
                                this.statusLock.wait();
                            } catch (final InterruptedException ie) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
                    // After wait, continue to next iteration to re-check conditions
                    continue;
                }

                // Now we know we're executable - do the work with parserLock
                synchronized (this.parserLock) {
                    if (!isExecutable()) { // Re-check inside lock
                        continue;
                    }

                    setStatus(Watchable.RUNNING);
                    try {
                        int laststatus = Watchable.RUNNING;
                        while ((getStatus() == Watchable.RUNNING) && (this.gate == null || !this.gate.iterate())) {
                            final int status = iterate();
                            if (status != laststatus) {
                                setStatus(status);
                                laststatus = status;
                            }
                        }

                        if (getStatus() == Watchable.RUNNING) {
                            setStatus(Watchable.PAUSED);
                        }
                    } catch (final Exception ex) {
                        setError(ex);
                    }
                }
            }

            if (getStatus() == Watchable.COMPLETED || getStatus() == Watchable.ERROR) {
                cleanup();
            }
        } catch (final InterruptedException e) {
            PDFDebugger.debug("Interrupted.");
        }
        this.thread = null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Get the status of this watchable
     */
    @Override
    public int getStatus() {
        return this.status;
    }

    /**
     * Set the status of this watchable
     *
     * @param status a {@link java.lang.Integer} object.
     */
    protected void setStatus(final int status) {
        synchronized (this.statusLock) {
            this.status = status;

            this.statusLock.notifyAll();
        }
    }

    /**
     * Return whether this watchable has finished.  A watchable is finished
     * when its status is either COMPLETED, STOPPED or ERROR
     *
     * @return a boolean.
     */
    public boolean isFinished() {
        final int s = getStatus();
        return (s == Watchable.COMPLETED ||
                s == Watchable.ERROR);
    }

    /**
     * return true if this watchable is ready to be executed
     *
     * @return a boolean.
     */
    public boolean isExecutable() {
        return ((this.status == Watchable.PAUSED || this.status == Watchable.RUNNING) &&
                (this.gate == null || !this.gate.stop()));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Stop this watchable if it is not already finished.
     * Stop will cause all processing to cease,
     * and the watchable to be destroyed.
     */
    @Override
    public void stop() {
        if (!isFinished()) setStatus(Watchable.STOPPED);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Start this watchable and run in a new thread until it is finished or
     * stopped.
     * Note the watchable may be stopped if go() with a
     * different time is called during execution.
     */
    @Override
    public synchronized void go() {
        this.gate = null;

        execute(false);
    }

    /**
     * Start this watchable and run until it is finished or stopped.
     * Note the watchable may be stopped if go() with a
     * different time is called during execution.
     *
     * @param synchronous if true, run in this thread
     */
    public synchronized void go(final boolean synchronous) {
        this.gate = null;

        execute(synchronous);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Start this watchable and run for the given number of steps or until
     * finished or stopped.
     */
    @Override
    public synchronized void go(final int steps) {
        this.gate = new Gate();
        this.gate.setStopIterations(steps);

        execute(false);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Start this watchable and run for the given amount of time, or until
     * finished or stopped.
     */
    @Override
    public synchronized void go(final long millis) {
        this.gate = new Gate();
        this.gate.setStopTime(millis);

        execute(false);
    }

    /**
     * Start executing this watchable
     *
     * @param synchronous if true, run in this thread
     */
    protected void execute(final boolean synchronous) {
        // Use finer-grained locking instead of method synchronization
        synchronized (this) {
            if (this.thread != null || isFinished()) {
                synchronized (this.statusLock) {
                    this.statusLock.notifyAll();
                }
                return;
            }
        }

        if (synchronous) {
            synchronized (this) {
                this.thread = Thread.currentThread();
            }
            run();
        } else {
            Thread newThread = new Thread(this);
            newThread.setName(getClass().getName());

            // Configure thread outside any locks
            final Thread.UncaughtExceptionHandler h = (th, ex) ->
                    PDFDebugger.debug("Uncaught exception: " + ex);
            newThread.setUncaughtExceptionHandler(h);

            synchronized (this.statusLock) {
                synchronized (this) {
                    this.thread = newThread;
                }
                newThread.start();
                try {
                    statusLock.wait();  // Now only holding statusLock
                } catch (final InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    PDFDebugger.debug("Thread interrupted while waiting for status change.");
                }
            }
        }
    }

    /**
     * Set an error on this watchable
     *
     * @param error a {@link java.lang.Exception} object.
     */
    protected void setError(final Exception error) {
        exception = error;
        if (!SuppressSetErrorStackTrace) {
            errorHandler.publishException(error);
        }

        setStatus(Watchable.ERROR);
    }

    /**
     * A class that lets us give it a target time or number of steps,
     * and will tell us to stop after that much time or that many steps
     */
    static class Gate {

        /**
         * whether this is a time-based (true) or step-based (false) gate
         */
        private boolean timeBased;
        /**
         * the next gate, whether time or iterations
         */
        private long nextGate;

        /**
         * set the stop time
         */
        public void setStopTime(final long millisFromNow) {
            this.timeBased = true;
            this.nextGate = System.currentTimeMillis() + millisFromNow;
        }

        /**
         * set the number of iterations until we stop
         */
        public void setStopIterations(final int iterations) {
            this.timeBased = false;
            this.nextGate = iterations;
        }

        /**
         * check whether we should stop.
         */
        public boolean stop() {
            if (this.timeBased) {
                return (System.currentTimeMillis() >= this.nextGate);
            } else {
                return (this.nextGate < 0);
            }
        }

        /**
         * Notify the gate of one iteration.  Returns true if we should
         * stop or false if not
         */
        public boolean iterate() {
            if (!this.timeBased) {
                this.nextGate--;
            }

            return stop();
        }
    }
}
