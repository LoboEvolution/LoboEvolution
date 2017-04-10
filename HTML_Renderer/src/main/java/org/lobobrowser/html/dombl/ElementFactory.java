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
 * Created on Oct 8, 2005
 */
package org.lobobrowser.html.dombl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.lobobrowser.html.HtmlMapping;
import org.lobobrowser.html.builder.HTMLElementBuilder;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.w3c.html.HTMLElement;
import org.w3c.dom.DOMException;

/**
 * A factory for creating Element objects.
 */
public class ElementFactory {

    /** The builders. */
    private Map<String, Object> builders = new HashMap<String, Object>();

    /**
     * Instantiates a new element factory.
     */
    private ElementFactory() {
        this.builders = HtmlMapping.mappingHtml();
    }

    /** The instance. */
    private static ElementFactory instance = new ElementFactory();

    /** Gets the instance.
	 *
	 * @return the instance
	 */
    public static ElementFactory getInstance() {
        return instance;
    }

    /**
     * Creates a new Element object.
     *
     * @param document
     *            the document
     * @param name
     *            the name
     * @return the HTML element
     * @throws DOMException
     *             the DOM exception
     */
    public final HTMLElement createElement(HTMLDocumentImpl document,
            String name) throws DOMException {
        String normalName = name.toUpperCase(Locale.ENGLISH);
        // No need to synchronize; read-only map at this point.
        HTMLElementBuilder builder = (HTMLElementBuilder) this.builders.get(normalName);
        if (builder == null) {
            // TODO: IE would assume name is html text here?
            HTMLElementImpl element = new HTMLElementImpl(name);
            element.setOwnerDocument(document);
            return element;
        } else {
            return builder.create(document, name);
        }
    }
}
