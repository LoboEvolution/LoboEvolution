/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
import static org.junit.Assert.assertNotNull;


/**
 * The getSchemaTypeInfo method retrieves the type information associated with this attribute.
 * Load a valid document with an XML Schema.
 * Invoke getSchemaTypeInfo method on an attribute having [type definition] property.  Expose {name} and {target namespace}
 * properties of the [type definition] property.  Verity that the typeName and typeNamespace of the title attribute's
 * schemaTypeInfo are correct. getSchemaTypeInfo on the 'id' attribute of the fourth 'acronym' element
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Attr-schemaTypeInfo</a>
 */
public class attrgetschematypeinfo07Test extends LoboUnitTest {

    @Test
    public void runTest() {
        Document doc;
        HTMLCollection elemList;
        Element acElem;
        Attr attr;
        TypeInfo attrTypeInfo;
        String typeName;
        String typeNamespace;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("acronym");
        acElem = (Element) elemList.item(3);
        attr = acElem.getAttributeNode("id");
        attrTypeInfo = attr.getSchemaTypeInfo();
        assertNotNull("typeInfoNotNull", attrTypeInfo);
        typeName = attrTypeInfo.getTypeName();
        typeNamespace = attrTypeInfo.getTypeNamespace();
        assertEquals("attrgetschematypeinfo07_typeName", "ID", typeName);
        assertEquals("attrgetschematypeinfo07_typeNamespace", "http://www.w3.org/2001/XMLSchema", typeNamespace);
    }
}

