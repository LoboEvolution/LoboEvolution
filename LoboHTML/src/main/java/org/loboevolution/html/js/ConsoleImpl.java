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

package org.loboevolution.html.js;

import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.js.storage.SessionStorage;
import org.loboevolution.html.node.js.console.Console;

import java.util.Date;

/**
 * <p>ConsoleImpl class.</p>
 */
public class ConsoleImpl implements Console {

    /**
     * The time.
     */
    private Date time;

    /**
     * The str time.
     */
    private String strTime;

    private final SessionStorage sessionStorage;

    public ConsoleImpl(HtmlRendererConfig config) {
        this.sessionStorage = new SessionStorage(config);
    }

	/** {@inheritDoc}*/
	@Override
    public void log(Object message) {
		logging(message);
	}

	/** {@inheritDoc}*/
	@Override
    public void debug(Object message) {
		logging(message);
	}

	/** {@inheritDoc}*/
	@Override
    public void info(Object message) {
		logging(message);
    }

	/** {@inheritDoc}*/
	@Override
    public void warn(Object message) {
        logging(message);
	}

	/** {@inheritDoc}*/
	@Override
    public void error(Object message) {
		logging(message);
    }

	/** {@inheritDoc}*/
	@Override
	public void trace(Object message) {
		logging(message);
	}

    /**
     * {@inheritDoc}
     *
     * <p>time.</p>
     */
    public void time(String name) {
        time = new Date();
       // logger.info(name + ": timer started");
        strTime = name;

    }

    /**
     * {@inheritDoc}
     *
     * <p>timeEnd.</p>
     */
    public void timeEnd(String name) {
        if (name.equals(strTime)) {
            Date date = new Date();
            Date result = new Date(time.getTime() - date.getTime());
           // logger.info(strTime + ": " + result);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        sessionStorage.removeItem("log");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void count(String label) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void count() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dir() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void group(String groupTitle) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void group() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void groupCollapsed(String groupTitle) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void groupCollapsed() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void groupEnd() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void markTimeline(String label) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void markTimeline() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void profile(String reportName) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void profile() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void profileEnd(String reportName) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void profileEnd() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void time() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timeEnd() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timeStamp(String label) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timeStamp() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timeline(String label) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timeline() {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timelineEnd(String label) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void timelineEnd() {
        // TODO Auto-generated method stub

    }

	private void logging(Object message){
		final Object log = sessionStorage.getItem("log");
		if (log == null) {
			sessionStorage.setItem("log", String.valueOf(message));
		} else {
			sessionStorage.setItem("log", log + "\n" + message);
		}
	}
}
