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
import org.loboevolution.html.node.Document;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * Set the value of the version attribute of the XML declaration of this document to
 * various invalid characters and  verify if a NOT_SUPPORTED_ERR is thrown.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-version">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Document3-version</a>
 */
public class documentsetxmlversion01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        String versionValue;
        List<String> illegalVersion = new ArrayList<>();
        illegalVersion.add("{");
        illegalVersion.add("}");
        illegalVersion.add("~");
        illegalVersion.add("'");
        illegalVersion.add("!");
        illegalVersion.add("@");
        illegalVersion.add("#");
        illegalVersion.add("$");
        illegalVersion.add("%");
        illegalVersion.add("^");
        illegalVersion.add("&");
        illegalVersion.add("*");
        illegalVersion.add("(");
        illegalVersion.add(")");
        illegalVersion.add("+");
        illegalVersion.add("=");
        illegalVersion.add("[");
        illegalVersion.add("]");
        illegalVersion.add("\\");
        illegalVersion.add("/");
        illegalVersion.add(";");
        illegalVersion.add("`");
        illegalVersion.add("<");
        illegalVersion.add(">");
        illegalVersion.add(",");
        illegalVersion.add("a ");
        illegalVersion.add("\"");
        illegalVersion.add("---");

        doc = sampleXmlFile("hc_staff.xml");
        for (int indexN10087 = 0; indexN10087 < illegalVersion.size(); indexN10087++) {
            versionValue = illegalVersion.get(indexN10087);

            {
                boolean success = false;
                try {
                    doc.setXmlVersion(versionValue);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue("NOT_SUPPORTED_ERR_documentsetversion01", success);
            }
        }
    }
}

