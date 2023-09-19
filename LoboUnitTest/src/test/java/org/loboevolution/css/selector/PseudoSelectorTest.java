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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(0, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(0, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <div id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(0, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(0, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(0, 0, 0)", "rgb(255, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div>\n"
            + "     <p id='myId'/>\n"
            + "     <p id='myId2'/>\n"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
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
            + "  <style type=\"text/css\">" + css + "</style>\n"
            + "  </head>\n"
            + "  <body>\n"
            + "    <div><p id='myId'>This is a paragraph.</p></div>"
            + "    <div><p id='myId2'>This is a paragraph.</p><p>This is a paragraph.</p></div>"
            + "    </div>\n"
            + "  </body>\n"
            + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
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
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div><p id='myId' lang='it'>Ciao.</p></div>"
                        + "    <div><p id='myId2'>This is a paragraph.</p><p>This is a paragraph.</p></div>"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
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
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "    <a id='myId' style='" + css + "'>css</a>\n"
                        + "    <a id='myId2' style='" + css + "'>css2</a>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
    }
}
