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
		    + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId'></div>\n"
            + "    <div id='myId2'></div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId'></div>\n"
            + "    <div id='myId2'></div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 128, 0)");
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
	 * <p>elementClassSelector.</p>
	 */
	@Test
    public void elementClassSelector() {
		final String css ="div.intro1 {color:red} div.intro2 {color: green}";
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
	 * <p>elementElementSelector.</p>
	 */
	@Test
    public void elementElementSelector() {
		final String css ="div, p {color:red}";
        final String html =
              "<html>\n"
            + "  <head>\n"
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div id='myId' class='intro1'></div>\n"
            + "    <p id='myId2' class='intro2'></p>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
        
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div></div>\n"
            + "    <p id='myId'/>\n" 
            + "    <p id='myId2'/>\n"
            + "  </body>\n"
            + "</html>";
		checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div></div>\n"
            + "    <p id='myId'/>\n" 
            + "    <p id='myId2'/>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
    }
}
