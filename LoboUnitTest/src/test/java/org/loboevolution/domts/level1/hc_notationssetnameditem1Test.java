
/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.domts.level1;

import com.gargoylesoftware.css.dom.DOMException;
import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.node.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


/**
 * An attempt to add an element to the named node map returned by notations should
 * result in a NO_MODIFICATION_ERR or HIERARCHY_REQUEST_ERR.
 *
 * @author Curt Arnold
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D46829EF">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-D46829EF</a>
 * @see <a href="http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788">http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1025163788</a>
 */
public class hc_notationssetnameditem1Test extends LoboUnitTest {

    /**
     * Runs the test case.
     *
     */
    @Test
    public void runTest() {
        Document doc;
        NamedNodeMap notations;
        DocumentType docType;
        Element elem;
        doc = sampleXmlFile("hc_staff.xml");
        docType = doc.getDoctype();

        assertNotNull("docTypeNotNull", docType);
        notations = docType.getNotations();
        assertNotNull("notationsNotNull", notations);
        elem = doc.createElement("br");

        try {
            notations.setNamedItem(elem);
            fail("throw_HIER_OR_NO_MOD_ERR");

        } catch (DOMException ex) {
            switch (ex.getCode()) {
                case 3:
                    break;
                case 7:
                    break;
                default:
                    throw ex;
            }
        }

    }

}

