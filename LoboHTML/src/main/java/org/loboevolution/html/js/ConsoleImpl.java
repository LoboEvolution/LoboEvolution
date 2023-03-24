/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
