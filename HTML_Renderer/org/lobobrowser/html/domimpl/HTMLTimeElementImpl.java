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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLTimeElement;

public class HTMLTimeElementImpl extends HTMLElementImpl implements
		HTMLTimeElement {

	public HTMLTimeElementImpl(String name) {
		super(name);
	}

	@Override
	public String getDateTime() {
		return this.getAttribute(HtmlAttributeProperties.DATETIME);
	}

	@Override
	public void setDateTime(String dateTime) {
		this.setAttribute(HtmlAttributeProperties.DATETIME, dateTime);
	}

	@Override
	public boolean getPubDate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPubDate(boolean pubDate) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getValueAsDate() {
		// TODO Auto-generated method stub
		return 0;
	}
}