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
import org.lobobrowser.html.domimpl.HTMLOptionElementImpl;


public class HtmlOptionTest extends SimpleLoboTest {
	
	@Test
    public void basicTest() throws Exception {
        String htmlSource = "<html><head>"
            + "</head><body >"
            + "<select>"
            + "  <option id='myId'>test1</option>"
            + "  <option id='myId2'>test2</option>"
            + "</select>"
            + "</body></html>";
       
        HTMLDocumentImpl doc = loadPage(htmlSource);
        assertTrue(OPTION.equals(doc.getElementById("myId").getNodeName()));
    }
	
    @Test
    public void getValue() throws Exception {
        String htmlSource
            = "<html>"
            + "<head><title>foo</title></head>"
            + "<body>"
            + "  <form id='form1'>"
            + "    <select name='select1' id='select1'>"
            + "      <option value='option1' id='option1'>Option1</option>"
            + "      <option value='number2' id='option2' selected>Number Two</option>"
            + "  </select>"
            + "  </form>"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);

        final HTMLOptionElementImpl option1 = (HTMLOptionElementImpl)page.getElementById("option1");
        final HTMLOptionElementImpl option2 = (HTMLOptionElementImpl)page.getElementById("option2");

        assertEquals("option1", option1.getValue());
        assertEquals("number2", option2.getValue());
    }

    @Test
    public void asText() throws Exception {
        String htmlSource
            = "<html>"
            + "<head><title>foo</title></head>"
            + "<body>"
            + "  <form id='form1'>"
            + "    <select name='select1' id='select1'>"
            + "      <option id='option1'>Option1</option>"
            + "      <option id='option2' selected>Number Two</option>"
            + "      <option id='option3'>  Number 3 with blanks </option>"
            + "    </select>"
            + "  </form>"
            + "</body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);

        final HTMLOptionElementImpl option1 = (HTMLOptionElementImpl)page.getElementById("option1");
        assertEquals("Option1", option1.getTextContent());

        final HTMLOptionElementImpl option2 = (HTMLOptionElementImpl)page.getElementById("option2");
        assertEquals("Number Two", option2.getTextContent());

        final HTMLOptionElementImpl option3 = (HTMLOptionElementImpl)page.getElementById("option3");
        assertEquals("Number 3 with blanks", option3.getTextContent());
    }
}
