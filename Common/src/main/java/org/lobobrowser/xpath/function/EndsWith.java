/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.xpath.function;

import java.util.List;

import javax.xml.xpath.XPathFunctionException;

/**
 * The Class EndsWith.
 *
 * @author richardallenbair
 */
public class EndsWith extends AbstractFunction {
	/**
	 * Creates a new instance of EndsWith.
	 */
	public EndsWith() {
		super("ends-with", 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.xpath.XPathFunction#evaluate(java.util.List)
	 */
	@Override
	public Object evaluate(List args) throws XPathFunctionException {
		String s1 = getStringParam(args.get(0));
		String s2 = getStringParam(args.get(1));
		return s1.endsWith(s2);
	}
}
