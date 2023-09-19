/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
         checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
		checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 128, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1 intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
         checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <div id='myId2' class='intro1-intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
         checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1 intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
         checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1 intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
         checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1intro2'></div>\n"
            + "    <div id='myId2' class='intro2'></div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
    }
}