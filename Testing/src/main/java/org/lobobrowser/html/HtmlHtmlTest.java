/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.junit.Test;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLHtmlElementImpl;
import org.w3c.dom.Element;

public class HtmlHtmlTest extends SimpleLoboTest {

    @Test
    public void attributes() throws Exception {
        String htmlSource = "<?xml version=\"1.0\"?>"
            + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
            + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
            + "<html xmlns='http://www.w3.org/1999/xhtml' lang='en' xml:lang='en'>"
            + "<head><title>test</title></head>"
            + "<body></body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        HTMLHtmlElementImpl root = (HTMLHtmlElementImpl)page.getDocumentElement();
        assertEquals("en", root.getLang());
    }

    @Test
    public void canonicalXPath() throws Exception {
        String htmlSource = "<?xml version=\"1.0\"?>"
            + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
            + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
            + "<html xmlns='http://www.w3.org/1999/xhtml' lang='en' xml:lang='en'>"
            + "<head><title>test</title></head>"
            + "<body></body></html>";

        HTMLDocumentImpl page = loadPage(htmlSource);
        Element root = page.getDocumentElement();
        assertEquals("/html", root.getElementsByTagName(HTML));
    }
}
