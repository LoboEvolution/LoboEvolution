/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
/*
 * Created on Mar 14, 2005
 */
package org.lobobrowser.protocol.res;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * The Class Handler.
 *
 * @author J. H. S.
 */
public class Handler extends URLStreamHandler {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#openConnection(URL)
	 */
	@Override
	protected URLConnection openConnection(URL arg0) throws IOException {
		return new ResURLConnection(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#openConnection(URL, java.net.Proxy)
	 */
	@Override
	protected URLConnection openConnection(URL u, Proxy p) throws IOException {
		return this.openConnection(u);
	}
}
