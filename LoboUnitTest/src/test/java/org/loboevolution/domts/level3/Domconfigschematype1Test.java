/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.junit.jupiter.api.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.domimpl.DOMImplementationImpl;
import org.loboevolution.html.dom.DOMConfiguration;
import org.loboevolution.html.dom.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.http.UserAgentContext;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Checks behavior of "schema-type" configuration parameter.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-schema-type">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-schema-type</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter</a>
 */
public class Domconfigschematype1Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final DOMImplementation domImpl;
        final Document doc;
        final DOMConfiguration domConfig;

        boolean canSet;
        String state;
        final String parameter = "sChEma-type";
        final String xmlSchemaType = "http://www.w3.org/2001/XMLSchema";
        final String dtdType = "http://www.w3.org/TR/REC-xml";
        domImpl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
        doc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", null);
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter(parameter, Boolean.TRUE);
        assertFalse(canSet, "Domconfigschematype1Assert1");

        try {
            domConfig.getParameter(parameter);

        } catch (final DOMException ex) {
            if (ex.getCode() == 8) {
                return;
            }
            throw ex;
        }
        /*DOMString */
        canSet = domConfig.canSetParameter(parameter, dtdType);

        if (canSet) {
            /*DOMString */
            domConfig.setParameter(parameter, dtdType);
            state = (String) domConfig.getParameter(parameter);
            assertEquals(dtdType, state, "Domconfigschematype1Assert2");
        } else {

            {
                boolean success = false;
                try {
                    /*DOMString */
                    domConfig.setParameter(parameter, dtdType);
                } catch (final DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue(success, "Domconfigschematype1Assert3");
            }
        }

        /*DOMString */
        canSet = domConfig.canSetParameter(parameter, xmlSchemaType);

        if (canSet) {
            /*DOMString */
            domConfig.setParameter(parameter, xmlSchemaType);
            state = (String) domConfig.getParameter(parameter);
            assertEquals(xmlSchemaType, state, "Domconfigschematype1Assert4");
        } else {

            {
                boolean success = false;
                try {
                    /*DOMString */
                    domConfig.setParameter(parameter, xmlSchemaType);
                } catch (final DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue(success, "Domconfigschematype1Assert5");
            }
        }

    }
}

