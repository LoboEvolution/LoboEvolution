/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/

package org.loboevolution.html.js;

import org.loboevolution.html.js.geolocation.Geolocation;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.net.HttpNetwork;

public class Navigator extends AbstractScriptableDelegate {
	
	private Window window;
	
	public Navigator(Window window) {
		this.window = window;
	}

	public Geolocation getGeolocation() {
		return new Geolocation(window);
	}

	public boolean isOnLine() {
		return true;
	}

	public String getLanguage() {
		return "EN";
	}

	public String getAppName() {
		return "Lobo Evolution";
	}

	public String getAppVersion() {
		return "1.0";
	}

	public String getPlatform() {
		return System.getProperty("os.name");
	}

	public String getProduct() {
		return this.getAppName();
	}

	public String getUserAgent() {
		return HttpNetwork.getUserAgentValue();
	}
}