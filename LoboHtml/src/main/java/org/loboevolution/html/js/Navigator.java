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

import org.loboevolution.net.HttpNetwork;
import org.loboevolution.js.AbstractScriptableDelegate;

public class Navigator extends AbstractScriptableDelegate {
	
	private MimeTypesCollection mimeTypes;

	public String getAppCodeName() {
		return this.getAppName();
	}

	public String getAppMinorVersion() {
		return "99.3";
	}

	public String getAppName() {
		return "Lobo";
	}

	public String getAppVersion() {
		return "0";
	}

	public MimeTypesCollection getMimeTypes() {
		synchronized (this) {
			MimeTypesCollection mt = this.mimeTypes;
			if (mt == null) {
				mt = new MimeTypesCollection();
				this.mimeTypes = mt;
			}
			return mt;
		}
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

	public String getVendor() {
		return "The Lobo Evolution";
	}

	public boolean javaEnabled() {
		return true;
	}

	public class MimeTypesCollection {
		// Class must be public to allow JavaScript access
		public int getLength() {
			return 0;
		}

		public Object item(int index) {
			return null;
		}

		public Object namedItem(String name) {
			return null;
		}
	}
}