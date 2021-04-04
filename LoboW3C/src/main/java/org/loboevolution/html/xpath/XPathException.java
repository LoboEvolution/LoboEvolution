/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.xpath;

/**
 * A new exception has been created for exceptions specific to these XPath
 * interfaces.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 *
 *
 *
 */
public class XPathException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * <p>Constructor for XPathException.</p>
     *
     * @param code a short.
     * @param message a {@link java.lang.String} object.
     */
    public XPathException(short code, String message) {
       super(message);
       this.code = code;
    }
    public final short   code;
    // XPathExceptionCode
    /**
     * If the expression has a syntax error or otherwise is not a legal
     * expression according to the rules of the specific
     * XPathEvaluator or contains specialized extension
     * functions or variables not supported by this implementation.
     */
    public static final short INVALID_EXPRESSION_ERR    = 1;
    /**
     * If the expression cannot be converted to return the specified type.
     */
    public static final short TYPE_ERR                  = 2;

}
