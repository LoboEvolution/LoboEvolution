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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Checks behavior of "check-character-normalization" configuration parameter.

 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-check-character-normalization">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-check-character-normalization</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter</a>
 */
public class Domconfigcheckcharacternormalization1Test extends LoboUnitTest {    
    /**
     * Runs the test case.
     */
    @Test
    public void runTest() {
        final DOMImplementation domImpl;
        final Document doc;
        final DOMConfiguration domConfig;

        boolean canSet;
        boolean state;
        final String parameter = "cHeCk-character-normalization";
        domImpl = new DOMImplementationImpl(new UserAgentContext(new LocalHtmlRendererConfig(), true));
        doc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", null);
        domConfig = doc.getDomConfig();
        state = ((Boolean) domConfig.getParameter(parameter));
        assertFalse(state, "Domconfigcheckcharacternormalization1Assert3");
        canSet = domConfig.canSetParameter(parameter, Boolean.FALSE);
        assertTrue(canSet, "Domconfigcheckcharacternormalization1Assert4");
        canSet = domConfig.canSetParameter(parameter, Boolean.TRUE);

        if (canSet) {
            domConfig.setParameter(parameter, Boolean.TRUE);
            state = ((Boolean) domConfig.getParameter(parameter));
            assertTrue(state, "Domconfigcheckcharacternormalization1Assert5");
        } else {

            {
                boolean success = false;
                try {
                    domConfig.setParameter(parameter, Boolean.TRUE);
                } catch (final DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue(success, "Domconfigcheckcharacternormalization1Assert6");
            }
            state = ((Boolean) domConfig.getParameter(parameter));
            assertFalse(state, "Domconfigcheckcharacternormalization1Assert7");
        }

        domConfig.setParameter(parameter, Boolean.FALSE);
    }
}

