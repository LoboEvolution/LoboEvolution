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
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Invoke setUserData on a new Attr to set its UserData to two Document nodes
 * obtained by parsing the same xml document.  Using getUserData and isNodeEqual
 * verify if the returned nodes are Equal.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class Nodesetuserdata05Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final Document doc;
        final Document doc2;
        Object userData;
        final Object returned1;
        final Object returned2;
        Object retUserData;
        final boolean success;
        final Attr attr;

        doc = sampleXmlFile("hc_staff.xml");
        doc2 = sampleXmlFile("hc_staff.xml");
        attr = doc.createAttributeNS("http://www.w3.org/XML/1998/namespace", "lang");
        /*Node */
        retUserData = attr.setUserData("Key1", doc, null);
        /*Node */
        retUserData = attr.setUserData("Key2", doc2, null);
        returned1 = attr.getUserData("Key1");
        returned2 = attr.getUserData("Key2");
        success = ((Node) /*DOMUserData */returned1).isEqualNode(((Node) /*DOMUserData */returned2));
        assertTrue(success, "Nodesetuserdata05Assert2");
    }
}

