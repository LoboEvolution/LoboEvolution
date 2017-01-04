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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.w3c.html.TextMetrics;

/**
 * The Class DOMTextMetricsImpl.
 */
public class DOMTextMetricsImpl implements TextMetrics {

	/** The txt. */
	private String txt;

	public DOMTextMetricsImpl(String txt) {
		this.setTxt(txt);
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the txt.
	 *
	 * @return the txt
	 */
	public String getTxt() {
		return txt;
	}

	/**
	 * Sets the txt.
	 *
	 * @param txt
	 *            the new txt
	 */
	public void setTxt(String txt) {
		this.txt = txt;
	}

}
