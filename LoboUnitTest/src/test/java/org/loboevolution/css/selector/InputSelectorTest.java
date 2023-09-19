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
 * <p>InputSelectorTest class.</p>
 */
public class InputSelectorTest extends LoboUnitTest {

    /**
     * <p>required.</p>
     */
    @Test
    public void required() {
        final String css ="input:required { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input id='myId' required>\n"
                        + "     <input id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
    }

    /**
     * <p>enabled.</p>
     */
    @Test
    public void enabled() {
        final String css ="input[type=text]:enabled { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId'>\n"
                        + "     <input type='text' id='myId2' disabled='disabled'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
    }

    /**
     * <p>disabled.</p>
     */
    @Test
    public void disabled() {
        final String css ="input[type=text]:disabled { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId'>\n"
                        + "     <input type='text' id='myId2' disabled='disabled'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(0, 0, 0)", "rgb(255, 0, 0)");
    }

    /**
     * <p>checked.</p>
     */
    @Test
    public void checked() {
        final String css ="input:checked { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='radio' id='myId' checked='checked'>\n"
                        + "     <input type='checkbox' id='myId2' checked='checked'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(255, 0, 0)");
    }

    /**
     * <p>placeholder.</p>
     */
    @Test
    public void placeholder() {
        final String css ="::placeholder { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId' placeholder='First name'>\n"
                        + "     <input type='text' id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
    }

    /**
     * <p>readOnly.</p>
     */
    @Test
    public void readOnly() {
        final String css ="input:read-only { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId' readonly>\n"
                        + "     <input type='text' id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
    }

    /**
     * <p>readWrite.</p>
     */
    @Test
    public void readWrite() {
        final String css ="input:read-write { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId' readonly>\n"
                        + "     <input type='text' id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(0, 0, 0)", "rgb(255, 0, 0)");
    }

    /**
     * <p>outOfRange.</p>
     */
    @Test
    public void outOfRange() {
        final String css ="input:out-of-range { color: red;}";
        final String html =
                "<html>\n"
                        + "  <head>\n"
                        + "  <style type=\"text/css\">" + css + "</style>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='number' id='myId' min='5' max='10' value='17'>\n"
                        + "     <input type='number' id='myId2' min='5' max='10' value='6'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";
        checkSelectorsTest(html, "rgb(255, 0, 0)", "rgb(0, 0, 0)");
    }
}
