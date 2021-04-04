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
 * <p>AttributeSelectorTest class.</p>
 */
public class AttributeSelectorTest extends LoboUnitTest{
	
	/**
	 * <p>attributeSelector.</p>
	 */
	@Test
    public void attributeSelector() {
		final String css = "div[id] {color: red}";		
		
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
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>attributeValueSelector.</p>
	 */
	@Test
    public void attributeValueSelector() {
		final String css = "div[id] {color: green} div[id=myId] {color: red}";
		
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
	 * <p>attributeValueContainsSelector.</p>
	 */
	@Test
    public void attributeValueContainsSelector() {
		final String css = "div[class~=intro2] {color: red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1 intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>attributeValueStartingSelector.</p>
	 */
	@Test
    public void attributeValueStartingSelector() {
		final String css = "div[class|=intro1] {color: red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <div id='myId2' class='intro1-intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>attributeValueBeginSelector.</p>
	 */
	@Test
    public void attributeValueBeginSelector() {
		final String css = "div[id^=my] {color: red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1 intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>attributeValueEndSelector.</p>
	 */
	@Test
    public void attributeValueEndSelector() {
		final String css = "div[class$=intro2] {color: red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1 intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
        
    }
	
	/**
	 * <p>attributeValueContainSelector.</p>
	 */
	@Test
    public void attributeValueContainSelector() {
		final String css = "div[class*=intro2] {color: red}";
		
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        
        checkSelectorsTest(html, "red", "red");
        
    }
}
