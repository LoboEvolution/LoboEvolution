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
package org.loboevolution.w3c.html;

import org.mozilla.javascript.Function;

/**
 * The public interface ApplicationCache.
 */
public interface ApplicationCache {
	// ApplicationCache
	/** The Constant UNCACHED. */
	short UNCACHED = 0;

	/** The Constant IDLE. */
	short IDLE = 1;

	/** The Constant CHECKING. */
	short CHECKING = 2;

	/** The Constant DOWNLOADING. */
	short DOWNLOADING = 3;

	/** The Constant UPDATEREADY. */
	short UPDATEREADY = 4;

	/** The Constant OBSOLETE. */
	short OBSOLETE = 5;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	short getStatus();

	/**
	 * Update.
	 */
	void update();

	/**
	 * Swap cache.
	 */
	void swapCache();

	/**
	 * Gets the onchecking.
	 *
	 * @return the onchecking
	 */
	Function getOnchecking();

	/**
	 * Sets the onchecking.
	 *
	 * @param onchecking
	 *            the new onchecking
	 */
	void setOnchecking(Function onchecking);

	/**
	 * Gets the onerror.
	 *
	 * @return the onerror
	 */
	Function getOnerror();

	/**
	 * Sets the onerror.
	 *
	 * @param onerror
	 *            the new onerror
	 */
	void setOnerror(Function onerror);

	/**
	 * Gets the onnoupdate.
	 *
	 * @return the onnoupdate
	 */
	Function getOnnoupdate();

	/**
	 * Sets the onnoupdate.
	 *
	 * @param onnoupdate
	 *            the new onnoupdate
	 */
	void setOnnoupdate(Function onnoupdate);

	/**
	 * Gets the ondownloading.
	 *
	 * @return the ondownloading
	 */
	Function getOndownloading();

	/**
	 * Sets the ondownloading.
	 *
	 * @param ondownloading
	 *            the new ondownloading
	 */
	void setOndownloading(Function ondownloading);

	/**
	 * Gets the onprogress.
	 *
	 * @return the onprogress
	 */
	Function getOnprogress();

	/**
	 * Sets the onprogress.
	 *
	 * @param onprogress
	 *            the new onprogress
	 */
	void setOnprogress(Function onprogress);

	/**
	 * Gets the onupdateready.
	 *
	 * @return the onupdateready
	 */
	Function getOnupdateready();

	/**
	 * Sets the onupdateready.
	 *
	 * @param onupdateready
	 *            the new onupdateready
	 */
	void setOnupdateready(Function onupdateready);

	/**
	 * Gets the oncached.
	 *
	 * @return the oncached
	 */
	Function getOncached();

	/**
	 * Sets the oncached.
	 *
	 * @param oncached
	 *            the new oncached
	 */
	void setOncached(Function oncached);

	/**
	 * Gets the onobsolete.
	 *
	 * @return the onobsolete
	 */
	Function getOnobsolete();

	/**
	 * Sets the onobsolete.
	 *
	 * @param onobsolete
	 *            the new onobsolete
	 */
	void setOnobsolete(Function onobsolete);
}
