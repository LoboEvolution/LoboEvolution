/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.domts.level3;


import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.nodeimpl.DOMImplementationImpl;
import org.loboevolution.html.node.DOMConfiguration;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.http.UserAgentContext;

import static org.junit.Assert.*;


/**
 * Checks behavior of "schema-type" configuration parameter.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-schema-type">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#parameter-schema-type</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-getParameter</a>
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#DOMConfiguration-setParameter</a>
 */
public class domconfigschematype1Test extends LoboUnitTest {
    @Test
    public void runTest() {
        DOMImplementation domImpl;
        Document doc;
        DOMConfiguration domConfig;
        DocumentType nullDocType = null;

        boolean canSet;
        String state;
        String parameter = "sChEma-type";
        String xmlSchemaType = "http://www.w3.org/2001/XMLSchema";
        String dtdType = "http://www.w3.org/TR/REC-xml";
        domImpl = new DOMImplementationImpl(new UserAgentContext(true));
        doc = domImpl.createDocument("http://www.w3.org/1999/xhtml", "html", nullDocType);
        domConfig = doc.getDomConfig();
        canSet = domConfig.canSetParameter(parameter, Boolean.TRUE);
        assertFalse("canSetTrue", canSet);

        try {
            state = (String) domConfig.getParameter(parameter);

        } catch (DOMException ex) {
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
            assertEquals("setDTDEffective", dtdType, state);
        } else {

            {
                boolean success = false;
                try {
                    /*DOMString */
                    domConfig.setParameter(parameter, dtdType);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue("throw_NOT_SUPPORTED_ERR_dtd", success);
            }
        }

        /*DOMString */
        canSet = domConfig.canSetParameter(parameter, xmlSchemaType);

        if (canSet) {
            /*DOMString */
            domConfig.setParameter(parameter, xmlSchemaType);
            state = (String) domConfig.getParameter(parameter);
            assertEquals("setSchemaEffective", xmlSchemaType, state);
        } else {

            {
                boolean success = false;
                try {
                    /*DOMString */
                    domConfig.setParameter(parameter, xmlSchemaType);
                } catch (DOMException ex) {
                    success = (ex.getCode() == DOMException.NOT_SUPPORTED_ERR);
                }
                assertTrue("throw_NOT_SUPPORTED_ERR_schema", success);
            }
        }

    }
}

