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
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.TypeInfo;

import static org.junit.Assert.assertEquals;


/**
 * The getSchemaTypeInfo method retrieves the type information associated with this element.
 * Load a valid document with an XML Schema.
 * Invoke getSchemaTypeInfo method on an element having [type definition] property.  Expose {name} and {target namespace}
 * properties of the [type definition] property.  Verity that the typeName and typeNamespace of the code element's
 * schemaTypeInfo are correct.
 *
 * @author IBM
 * @author Jenny Hsu
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Element-schemaTypeInfo">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Element-schemaTypeInfo</a>
 */
public class elementgetschematypeinfo04Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element codeElem;
        TypeInfo elemTypeInfo;
        String typeName;
        String typeNamespace;
        HTMLCollection elemList;
        doc = sampleXmlFile("hc_staff.xml");
        elemList = doc.getElementsByTagName("code");
        codeElem = (Element) elemList.item(1);
        elemTypeInfo = codeElem.getSchemaTypeInfo();
        typeName = elemTypeInfo.getTypeName();
        typeNamespace = elemTypeInfo.getTypeNamespace();
        assertEquals("elementgetschematypeinfo04_typeName", "code", typeName);
        assertEquals("elementgetschematypeinfo04_typeNamespace", "http://www.w3.org/1999/xhtml", typeNamespace);
    }
}

