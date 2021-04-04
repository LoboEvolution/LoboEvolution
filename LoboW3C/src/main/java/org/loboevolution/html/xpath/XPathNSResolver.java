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
 * The XPathNSResolver interface permit prefix
 * strings in the expression to be properly bound to
 * namespaceURI strings. XPathEvaluator can
 * construct an implementation of XPathNSResolver from a node,
 * or the interface may be implemented by any application.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 *
 *
 *
 */
public interface XPathNSResolver {
    /**
     * Look up the namespace URI associated to the given namespace prefix. The
     * XPath evaluator must never call this with a null or
     * empty argument, because the result of doing this is undefined.
     *
     * @param prefix The prefix to look for.
     * @return Returns the associated namespace URI or null if
     *   none is found.
     */
    public String lookupNamespaceURI(String prefix);

}
