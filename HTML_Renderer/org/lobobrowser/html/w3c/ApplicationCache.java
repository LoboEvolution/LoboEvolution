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

public interface ApplicationCache {
	// ApplicationCache
	public static final short UNCACHED = 0;
	public static final short IDLE = 1;
	public static final short CHECKING = 2;
	public static final short DOWNLOADING = 3;
	public static final short UPDATEREADY = 4;
	public static final short OBSOLETE = 5;

	public short getStatus();

	public void update();

	public void swapCache();

	public Function getOnchecking();

	public void setOnchecking(Function onchecking);

	public Function getOnerror();

	public void setOnerror(Function onerror);

	public Function getOnnoupdate();

	public void setOnnoupdate(Function onnoupdate);

	public Function getOndownloading();

	public void setOndownloading(Function ondownloading);

	public Function getOnprogress();

	public void setOnprogress(Function onprogress);

	public Function getOnupdateready();

	public void setOnupdateready(Function onupdateready);

	public Function getOncached();

	public void setOncached(Function oncached);

	public Function getOnobsolete();

	public void setOnobsolete(Function onobsolete);
}
