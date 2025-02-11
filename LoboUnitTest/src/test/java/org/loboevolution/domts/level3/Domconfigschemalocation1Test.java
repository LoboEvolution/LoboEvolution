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
 * Checks behavior of "schema-location" configuration parameter.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-schema-location">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-schema-location</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter</a>
 */
public class Domconfigschemalocation1Test extends LoboUnitTest {
    @Test
    public void runTest() {
        final DOMImplementation domImpl;
        final Document doc;
        final DOMConfiguration domConfig;

        boolean canSet;
        String state;
        final String parameter = "sChEma-location";

        final String sampleSchemaLocation = "http://www.example.com/schemas/sampleschema.xsd";
        domImpl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
        doc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", null);
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter(parameter, Boolean.TRUE);
        assertFalse(canSet, "Domconfigschemalocation1Assert1");

        try {
            state = (String) domConfig.getParameter(parameter);
            assertNull(state, "Domconfigschemalocation1Assert2");

        } catch (final DOMException ex) {
            if (ex.getCode() == 8) {
                return;
            }
            throw ex;
        }
        /*DOMString */
        canSet = domConfig.canSetParameter(parameter, sampleSchemaLocation);
        assertTrue(canSet, "Domconfigschemalocation1Assert3");
        /*DOMString */
        canSet = domConfig.canSetParameter(parameter, null);
        assertTrue(canSet, "Domconfigschemalocation1Assert4");
        /*DOMString */
        domConfig.setParameter(parameter, sampleSchemaLocation);
        state = (String) domConfig.getParameter(parameter);
        assertEquals(sampleSchemaLocation, state, "Domconfigschemalocation1Assert5");
        /*DOMString */
        domConfig.setParameter(parameter, null);
        state = (String) domConfig.getParameter(parameter);
        assertNull(state, "Domconfigschemalocation1Assert6");
    }
}

