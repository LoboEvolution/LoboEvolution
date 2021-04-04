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

package org.loboevolution.css.selector;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * <p>PseudoSelectorTest class.</p>
 */
public class PseudoSelectorTest extends LoboUnitTest {
	
	/**
	 * <p>firstChildSelector.</p>
	 */
	@Test
    public void firstChildSelector() {
		final String css ="p:first-child {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", null);
        
    }
	
	/**
	 * <p>firstOfTypeChildSelector.</p>
	 */
	@Test
    public void firstOfTypeChildSelector() {
		final String css ="p:first-of-type {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", null);
        
    }
	
	/**
	 * <p>lastChildSelector.</p>
	 */
	@Test
    public void lastChildSelector() {
		final String css ="p:last-child {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, null, "red");
        
    }

	
	/**
	 * <p>lastOfTypeChildSelector.</p>
	 */
	@Test
    public void lastOfTypeChildSelector() {
		final String css ="p:last-of-type {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, null, "red");
        
    }
	
	
	/**
	 * <p>notSelector.</p>
	 */
	@Test
    public void notSelector() {
		final String css =":not(p) {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <div id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, null, "red");
        
    }
	
	/**
	 * <p>childParentSelector.</p>
	 */
	@Test
    public void childParentSelector() {
		final String css ="p:nth-child(2) {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, null, "red");
        
    }
	
	/**
	 * <p>lastChildParentSelector.</p>
	 */
	@Test
    public void lastChildParentSelector() {
		final String css ="p:nth-last-child(2) {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", null);
        
    }
	
	/**
	 * <p>childTypeParentSelector.</p>
	 */
	@Test
    public void childTypeParentSelector() {
		final String css ="p:nth-of-type(2) {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, null, "red");
        
    }
	
	
	/**
	 * <p>lastChildTypeParentSelector.</p>
	 */
	@Test
    public void lastChildTypeParentSelector() {
		final String css ="p:nth-last-of-type(2) {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", null);
        
    }
	
	/**
	 * <p>onlyTypeParentSelector.</p>
	 */
	@Test
    public void onlyTypeParentSelector() {
		final String css ="p:only-of-type {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div><p id='myId'>This is a paragraph.</p></div>"
            + "    <div><p id='myId2'>This is a paragraph.</p><p>This is a paragraph.</p></div>"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", null);
        
    }

    /**
     * <p>langSelector.</p>
     */
    @Test
    public void langSelector() {
        final String css ="p:lang(it) {color:red}";

        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div><p id='myId' lang='it'>Ciao.</p></div>"
                        + "    <div><p id='myId2'>This is a paragraph.</p><p>This is a paragraph.</p></div>"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", null);

    }

    /**
     * <p>linkSelector.</p>
     */
    @Test
    public void linkSelector() {
        final String css ="a:link {color:red}";

        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "    <a id='myId' href='" + mockCssLink(css) + "'>css</a>\n"
                        + "    <a id='myId2' href='" + mockCssLink(css) + "'>css2</a>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", "red");

    }

}
