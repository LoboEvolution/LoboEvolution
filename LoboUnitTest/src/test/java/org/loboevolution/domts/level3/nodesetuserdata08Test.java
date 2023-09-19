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
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertFalse;


/**
 * Invoke setUserData on a CDATASection and EntityReference node to set their
 * UserData to this Document and DocumentElement node.  Verify if the UserData
 * object that was set for both nodes is different.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Node3-setUserData</a>
 */
public class nodesetuserdata08Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        EntityReference entRef;
        CDATASection cData;
        HTMLCollection elemList;
        Element elemName;
        Object userData;
        Object returned1;
        Object returned2;
        boolean success;
        Object retUserData;

        doc = sampleXmlFile("hc_staff.xml");
        docElem = doc.getDocumentElement();
        entRef = doc.createEntityReference("delta");
        cData = doc.createCDATASection("CDATASection");
        /*Node */
        retUserData = entRef.setUserData("Key1", doc, null);
        /*Node */
        retUserData = cData.setUserData("Key2", docElem, null);
        returned1 = entRef.getUserData("Key1");
        returned2 = cData.getUserData("Key2");
        success = ((Node) /*DOMUserData */returned1).isEqualNode(((Node) /*DOMUserData */returned2));
        assertFalse("nodesetuserdata08", success);
    }
}

