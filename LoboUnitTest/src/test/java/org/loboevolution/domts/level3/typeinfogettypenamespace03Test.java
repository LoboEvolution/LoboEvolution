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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.TypeInfo;

import static org.junit.Assert.assertEquals;

/**
 * The typeNamespace attribute states the namespace of a type declared for the associated element or
 * attribute, or null if unknown.
 * Invoke getSchemaTypeInfo method on an attribute having [type definition] property.  Expose
 * {name} and {target namespace} properties of the [type definition] property.
 * Verify that the typeName of class's schemaTypeInfo is correct.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-typeName">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#TypeInfo-typeName</a>
 */
public class typeinfogettypenamespace03Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Attr classAttr;
        TypeInfo attrTypeInfo;
        String typeNamespace;
        Element acElem;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acElem = (Element) elemList.item(1);
        classAttr = acElem.getAttributeNode("class");
        attrTypeInfo = classAttr.getSchemaTypeInfo();
        typeNamespace = attrTypeInfo.getTypeNamespace();
        assertEquals("typeinfogettypename03_1", "http://www.w3.org/1999/xhtml", typeNamespace);
    }
}

