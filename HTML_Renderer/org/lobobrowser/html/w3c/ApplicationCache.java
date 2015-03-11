/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.w3c;

import org.mozilla.javascript.Function;


/**
 * The Interface ApplicationCache.
 */
public interface ApplicationCache {
	// ApplicationCache
	/** The Constant UNCACHED. */
	public static final short UNCACHED = 0;
	
	/** The Constant IDLE. */
	public static final short IDLE = 1;
	
	/** The Constant CHECKING. */
	public static final short CHECKING = 2;
	
	/** The Constant DOWNLOADING. */
	public static final short DOWNLOADING = 3;
	
	/** The Constant UPDATEREADY. */
	public static final short UPDATEREADY = 4;
	
	/** The Constant OBSOLETE. */
	public static final short OBSOLETE = 5;

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public short getStatus();

	/**
	 * Update.
	 */
	public void update();

	/**
	 * Swap cache.
	 */
	public void swapCache();

	/**
	 * Gets the onchecking.
	 *
	 * @return the onchecking
	 */
	public Function getOnchecking();

	/**
	 * Sets the onchecking.
	 *
	 * @param onchecking the new onchecking
	 */
	public void setOnchecking(Function onchecking);

	/**
	 * Gets the onerror.
	 *
	 * @return the onerror
	 */
	public Function getOnerror();

	/**
	 * Sets the onerror.
	 *
	 * @param onerror the new onerror
	 */
	public void setOnerror(Function onerror);

	/**
	 * Gets the onnoupdate.
	 *
	 * @return the onnoupdate
	 */
	public Function getOnnoupdate();

	/**
	 * Sets the onnoupdate.
	 *
	 * @param onnoupdate the new onnoupdate
	 */
	public void setOnnoupdate(Function onnoupdate);

	/**
	 * Gets the ondownloading.
	 *
	 * @return the ondownloading
	 */
	public Function getOndownloading();

	/**
	 * Sets the ondownloading.
	 *
	 * @param ondownloading the new ondownloading
	 */
	public void setOndownloading(Function ondownloading);

	/**
	 * Gets the onprogress.
	 *
	 * @return the onprogress
	 */
	public Function getOnprogress();

	/**
	 * Sets the onprogress.
	 *
	 * @param onprogress the new onprogress
	 */
	public void setOnprogress(Function onprogress);

	/**
	 * Gets the onupdateready.
	 *
	 * @return the onupdateready
	 */
	public Function getOnupdateready();

	/**
	 * Sets the onupdateready.
	 *
	 * @param onupdateready the new onupdateready
	 */
	public void setOnupdateready(Function onupdateready);

	/**
	 * Gets the oncached.
	 *
	 * @return the oncached
	 */
	public Function getOncached();

	/**
	 * Sets the oncached.
	 *
	 * @param oncached the new oncached
	 */
	public void setOncached(Function oncached);

	/**
	 * Gets the onobsolete.
	 *
	 * @return the onobsolete
	 */
	public Function getOnobsolete();

	/**
	 * Sets the onobsolete.
	 *
	 * @param onobsolete the new onobsolete
	 */
	public void setOnobsolete(Function onobsolete);
}
