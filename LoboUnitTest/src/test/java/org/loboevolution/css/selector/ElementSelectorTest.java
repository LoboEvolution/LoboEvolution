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
 * <p>ElementSelectorTest class.</p>
 */
public class ElementSelectorTest extends LoboUnitTest {
	

	/**
	 * <p>elementSelector.</p>
	 */
	@Test
    public void elementSelector() {
		final String css ="div {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId'></div>\n"
            + "    <div id='myId2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
    }
	
	/**
	 * <p>idSelector.</p>
	 */
	@Test
    public void idSelector() {
		final String css ="#myId {color:red} #myId2 {color:green}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId'></div>\n"
            + "    <div id='myId2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "green");
    }
	
	/**
	 * <p>classSelector.</p>
	 */
	@Test
    public void classSelector() {
		final String css =".intro1 {color:red} .intro2 {color: green}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "green");
    }
	
	/**
	 * <p>elementClassSelector.</p>
	 */
	@Test
    public void elementClassSelector() {
		final String css ="div.intro1 {color:red} div.intro2 {color: green}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "green");
        
    }
	
	/**
	 * <p>elementElementSelector.</p>
	 */
	@Test
    public void elementElementSelector() {
		final String css ="div, p {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <p id='myId2' class='intro2'></p>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>elementElement2Selector.</p>
	 */
	@Test
    public void elementElement2Selector() {
		final String css ="div p {color:red}";
		
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
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>elementParentSelector.</p>
	 */
	@Test
    public void elementParentSelector() {
		final String css ="div > p {color:red}";
		
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
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>elementAfterSelector.</p>
	 */
	@Test
    public void elementAfterSelector() {
		final String css ="div + p {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div></div>\n"
            + "    <p id='myId'/>\n" 
            + "    <p id='myId2'/>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", null);
        
    }
	
	/**
	 * <p>elementPrecededSelector.</p>
	 */
	@Test
    public void elementPrecededSelector() {
		final String css ="div ~ p {color:red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div></div>\n"
            + "    <p id='myId'/>\n" 
            + "    <p id='myId2'/>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
        
    }
}
