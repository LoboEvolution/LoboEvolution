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
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.DOMStringList;
import org.loboevolution.html.node.Document;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * The contains method of the DOMStringList tests if a string is part of this DOMStringList.
 * <p>
 * Invoke the contains method on the list searching for several of the parameters recognized by the
 * DOMConfiguration object.
 * Verify that the list contains features that are required and supported by this DOMConfiguration object.
 * Verify that the contains method returns false for a string that is not contained in this DOMStringList.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-contains">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMStringList-contains</a>
 */
public class Domstringlistcontains02Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final DOMStringList paramList;
        final DOMConfiguration domConfig;
        boolean contain;
        doc = sampleXmlFile("hc_staff.xml");
        domConfig = doc.getDomConfig();
        paramList = domConfig.getParameterNames();
        contain = paramList.contains("comments");
        assertTrue(contain, "Domstringlistcontains02Assert3");
        contain = paramList.contains("cdata-sections");
        assertTrue(contain, "Domstringlistcontains02Assert4");
        contain = paramList.contains("entities");
        assertTrue(contain, "Domstringlistcontains02Assert5");
        contain = paramList.contains("error-handler");
        assertTrue(contain, "Domstringlistcontains02Assert6");
        contain = paramList.contains("infoset");
        assertTrue(contain, "Domstringlistcontains02Assert7");
        contain = paramList.contains("namespace-declarations");
        assertTrue(contain, "Domstringlistcontains02Assert8");
        contain = paramList.contains("element-content-whitespace");
        assertTrue(contain, "Domstringlistcontains02Assert9");
        contain = paramList.contains("test");
        assertFalse(contain, "Domstringlistcontains02Assert10");
    }
}

