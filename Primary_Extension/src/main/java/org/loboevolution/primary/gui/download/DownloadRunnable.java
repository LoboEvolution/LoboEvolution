/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.download;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.request.RequestEngine;
import org.loboevolution.request.RequestHandler;

/**
 * The Class DownloadRunnable.
 */
public class DownloadRunnable implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(DownloadRunnable.class);

	/** The handler. */
	private final RequestHandler handler;
	
	private DownloadDialog download;

	/**
	 * Instantiates a new download runnable.
	 *
	 * @param handler
	 *            the handler
	 */
	public DownloadRunnable(RequestHandler handler, DownloadDialog download) {
		this.handler = handler;
		this.download = download;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			RequestEngine.getInstance().inlineRequest(this.handler);
		} catch (Exception err) {
			logger.error("Unexpected error on download of [" + download.getUrl().toExternalForm() + "].", err);
		}
	}
}
