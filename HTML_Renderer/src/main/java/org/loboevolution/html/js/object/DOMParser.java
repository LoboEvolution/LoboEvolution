/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.loboevolution.html.js.object;

import org.loboevolution.html.js.xml.XMLDocument;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Undefined;

public class DOMParser extends AbstractScriptableDelegate {

	public XMLDocument parseFromString(String str, String type) {
		if (type == null || Undefined.instance == type) {
			throw Context.reportRuntimeError("Missing 'type' parameter");
		}
		if (!"text/html".equals(type) && !"text/xml".equals(type) && !"application/xml".equals(type)
				&& !"application/xhtml+xml".equals(type) && !"image/svg+xml".equals(type)) {
			throw Context.reportRuntimeError("Invalid 'type' parameter: " + type);
		}

		XMLDocument document = new XMLDocument();
		document.loadXML(str);
		return document;
	}

}
