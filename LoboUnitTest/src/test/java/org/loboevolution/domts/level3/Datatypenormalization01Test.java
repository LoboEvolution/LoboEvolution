/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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


import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.DOMErrorMonitor;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Normalize document with datatype-normalization set to true.
 * Check if double values were normalized.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-normalizeDocument</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-datatype-normalization">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-datatype-normalization</a>
 */
public class Datatypenormalization01Test extends LoboUnitTest {


    @Test
    public void runTest() {
        final Document doc;
        final HTMLCollection elemList;
        Element element;
        final DOMConfiguration domConfig;
        String str;
        final boolean canSetNormalization;
        final boolean canSetValidate;
        final boolean canSetXMLSchema;
        final String xsdNS = "http://www.w3.org/2001/XMLSchema";
        final DOMErrorMonitor errorMonitor = new DOMErrorMonitor();

        doc = sampleXmlFile("datatype_normalization.xml");
        domConfig = doc.getDomConfig();
        canSetNormalization = domConfig.canSetParameter("datatype-normalization", Boolean.TRUE);
        canSetValidate = domConfig.canSetParameter("validate", Boolean.TRUE);
        /*DOMString */
        canSetXMLSchema = domConfig.canSetParameter("schema-type", xsdNS);

        if (canSetNormalization && canSetValidate && canSetXMLSchema) {
            domConfig.setParameter("datatype-normalization", Boolean.TRUE);
            domConfig.setParameter("validate", Boolean.TRUE);
            /*DOMString */
            domConfig.setParameter("schema-type", xsdNS);
            /*DOMErrorMonitor */
            domConfig.setParameter("error-handler", errorMonitor);
            doc.normalizeDocument();
            assertTrue(errorMonitor.assertLowerSeverity(2), "Datatypenormalization01Assert3");
            elemList = doc.getElementsByTagNameNS("http://www.w3.org/2001/DOM-Test-Suite/Level-3/datatype_normalization", "data:double");
            element = (Element) elemList.item(0);
            str = element.getAttribute("data:value");
            assertEquals("+0003.141592600E+0000", str, "Datatypenormalization01Assert4");
            str = element.getAttribute("data:union");
            assertEquals("+0003.141592600E+0000", str, "Datatypenormalization01Assert5");
            str = element.getTextContent();
            assertEquals("-31415926.00E-7 2.718", str, "Datatypenormalization01Assert6");
            element = (Element) elemList.item(1);
            str = element.getAttribute("data:value");
            assertEquals("NaN", str, "Datatypenormalization01Assert7");
            str = element.getAttribute("data:union");
            assertEquals("NaN", str, "Datatypenormalization01Assert8");
            str = element.getTextContent();
            assertEquals("INF -INF", str, "Datatypenormalization01Assert9");
            element = (Element) elemList.item(2);
            str = element.getAttribute("data:value");
            assertEquals("1", str, "Datatypenormalization01Assert10");
            str = element.getAttribute("data:union");
            assertEquals("1", str, "Datatypenormalization01Assert11");
            str = element.getTextContent();
            assertEquals("-0", str, "Datatypenormalization01Assert12");
        }
    }
}

