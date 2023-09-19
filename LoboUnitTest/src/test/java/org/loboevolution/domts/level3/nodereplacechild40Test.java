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


import org.htmlunit.cssparser.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Attempt to add a second document element by a comment.  The attempt should result
 * in a HIERARCHY_REQUEST_ERR or NOT_SUPPORTED_ERR.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#ID-785887307</a>
 */
public class nodereplacechild40Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        Element docElem;
        String rootName;
        String publicId = null;

        String systemId = null;

        Comment newComment;
        DocumentType newDocType;
        DOMImplementation domImpl;
        doc = sampleXmlFile("barfoo.xml");
        docElem = doc.getDocumentElement();
        rootName = docElem.getTagName();
        domImpl = doc.getImplementation();
        newDocType = domImpl.createDocumentType(rootName, publicId, systemId);
        newComment = doc.createComment("second element goes here");
        doc.insertBefore(newComment, docElem);

        boolean success = false;
        try {
            doc.replaceChild(newDocType, newComment);
        } catch (DOMException ex) {
            success = (ex.getCode() == DOMException.HIERARCHY_REQUEST_ERR);
        }
        assertTrue("throw_HIERARCHY_REQUEST_ERR", success);
    }
}

