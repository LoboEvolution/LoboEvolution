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


import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Text;

import static org.junit.Assert.assertEquals;


/**
 * Invoke replaceWholeText on an existing Text Node to replace its value with a
 * new value containing white space characters.  Verify the replaceWholeText by
 * verifying the values returned by wholeText
 * of the returned Text node.
 *
 * @author IBM
 * @author Neil Delima
 * @see <a href="http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText">http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core#Text3-replaceWholeText</a>
 */
public class textreplacewholetext01Test extends LoboUnitTest {
    @Test
    public void runTest() {
        Document doc;
        HTMLCollection itemList;
        Element elementName;
        Text textNode;
        Text replacedText;
        String wholeText;
        doc = sampleXmlFile("hc_staff.xml");
        itemList = doc.getElementsByTagName("strong");
        elementName = (Element) itemList.item(0);
        textNode = (Text) elementName.getFirstChild();
        replacedText = textNode.replaceWholeText("New Content");
        wholeText = replacedText.getWholeText();
        assertEquals("textreplacewholetext01_1", "New Content", wholeText);
    }
}

