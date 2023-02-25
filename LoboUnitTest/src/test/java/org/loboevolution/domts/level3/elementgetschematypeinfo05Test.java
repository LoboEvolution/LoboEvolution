/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.domts.level3;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.TypeInfo;

import static org.junit.Assert.assertEquals;

/**
 * The getSchemaTypeInfo method retrieves the type information associated with this element.
 * Load a valid document with an XML Schema.
 * Invoke getSchemaTypeInfo method on an element having [type definition] property.  Expose {name} and {target namespace}
 * properties of the [type definition] property.  Verity that the typeName and typeNamespace of the acronym element's
 * schemaTypeInfo are correct.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Element-schemaTypeInfo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Element-schemaTypeInfo</a>
 */
public class elementgetschematypeinfo05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element acElem;
        TypeInfo elemTypeInfo;
        String typeName;
        String typeNamespace;
        HTMLCollection elemList;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("span");
        acElem = (Element) elemList.item(0);
        elemTypeInfo = acElem.getSchemaTypeInfo();
        typeName = elemTypeInfo.getTypeName();
        typeNamespace = elemTypeInfo.getTypeNamespace();
        assertEquals("typeNameString", "string", typeName);
        assertEquals("typeNsXSD", "http://www.w3.org/2001/XMLSchema", typeNamespace);
    }
}