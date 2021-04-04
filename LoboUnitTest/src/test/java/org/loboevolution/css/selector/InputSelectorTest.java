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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input id='myId' required>\n"
                        + "     <input id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", null);
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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId'>\n"
                        + "     <input type='text' id='myId2' disabled='disabled'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", null);
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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId'>\n"
                        + "     <input type='text' id='myId2' disabled='disabled'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, null, "red");
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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='radio' id='myId' checked='checked'>\n"
                        + "     <input type='checkbox' id='myId2' checked='checked'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", "red");
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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId' placeholder='First name'>\n"
                        + "     <input type='text' id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", null);
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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId' readonly>\n"
                        + "     <input type='text' id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", null);
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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='text' id='myId' readonly>\n"
                        + "     <input type='text' id='myId2'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, null, "red");
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
                        + "    <link rel='stylesheet' type='text/css' href='" + mockCssLink(css) + "'/>\n"
                        + "  </head>\n"
                        + "  <body>\n"
                        + "    <div>\n"
                        + "     <input type='number' id='myId' min='5' max='10' value='17'>\n"
                        + "     <input type='number' id='myId2' min='5' max='10' value='6'>\n"
                        + "    </div>\n"
                        + "  </body>\n"
                        + "</html>";

        checkSelectorsTest(html, "red", null);
    }
}
