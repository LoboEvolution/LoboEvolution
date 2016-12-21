/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;

public class HtmlInlineFrameTest extends SimpleLoboTest {

	@Test
    public void basicTest() throws Exception {
        String htmlSource = "<html><head>\n"
            + "</head><body >\n"
            + "  <iframe id='myId'>\n"
            + "</body></html>";

        HTMLDocumentImpl doc = loadPage(htmlSource);
        assertTrue(IFRAME.equals(doc.getElementById("myId").getNodeName()));
    }
	 

    @Test
    public void brokenIframe() throws Exception {
        String htmlSource = "<html>\n"
                + "<head></head>\n"
                + "<body>"
                + "1<div>2<iframe/>3</div>4"
                + "</body>\n"
                + "</html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        assertEquals("1" + SEPARATOR_LINE + "2", page.getTextContent());
    }
}
