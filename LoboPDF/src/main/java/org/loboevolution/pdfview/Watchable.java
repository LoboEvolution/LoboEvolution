/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

/**
 * An interface for rendering or parsing, which can be stopped and started.
 *
  *
  *
 */
public interface Watchable {

    /** the possible statuses */
    int UNKNOWN = 0;
    /** Constant <code>NOT_STARTED=1</code> */
    int NOT_STARTED = 1;
    /** Constant <code>PAUSED=2</code> */
    int PAUSED = 2;
    /** Constant <code>NEEDS_DATA=3</code> */
    int NEEDS_DATA = 3;
    /** Constant <code>RUNNING=4</code> */
    int RUNNING = 4;
    /** Constant <code>STOPPED=5</code> */
    int STOPPED = 5;
    /** Constant <code>COMPLETED=6</code> */
    int COMPLETED = 6;
    /** Constant <code>ERROR=7</code> */
    int ERROR = 7;

    /**
     * Get the status of this watchable
     *
     * @return one of the well-known statuses
     */
    int getStatus();

    /**
     * Stop this watchable.  Stop will cause all processing to cease,
     * and the watchable to be destroyed.
     */
    void stop();

    /**
     * Start this watchable and run until it is finished or stopped.
     * Note the watchable may be stopped if go() with a
     * different time is called during execution.
     */
    void go();

    /**
     * Start this watchable and run for the given number of steps or until
     * finished or stopped.
     *
     * @param steps the number of steps to run for
     */
    void go(int steps);

    /**
     * Start this watchable and run for the given amount of time, or until
     * finished or stopped.
     *
     * @param millis the number of milliseconds to run for
     */
    void go(long millis);
}
