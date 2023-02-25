/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.js;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.node.js.webstorage.Storage}.
 */

public class StorageUnitTest extends LoboUnitTest {

    @Test
    public void storage() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  alert(window.globalStorage);\n"
                + "  alert(window.localStorage);\n"
                + "  alert(window.sessionStorage);\n"
                + "</script>\n"
                + "</body></html>";
       final String[] messages = {"undefined", "[object Storage]", "[object Storage]"};
       checkHtmlAlert(html, messages);
    }
    
    @Test
    public void storageEquals() {
        final String html
                = "<html><body><script>\n"
                + "  alert('global: ' + (window.globalStorage === window.globalStorage));\n"
                + "  try { alert('local: ' + (window.localStorage === window.localStorage)); }"
                + " catch(e) { alert('exception'); }\n"
                + "  try { alert('session: ' + (window.sessionStorage === window.sessionStorage)); }"
                + " catch(e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"global: true", "local: true", "session: true"};
       checkHtmlAlert(html, messages);
    }


    @Test
    public void sessionStorage() {
        final String html
                = "<html><head></head><body>\n"
                + "<script>\n"
                + "  if (window.sessionStorage) {\n"
                + "    alert(sessionStorage.length);\n"
                + "    sessionStorage.hi = 'there';\n"
                + "    sessionStorage.setItem('hello', 'world');\n"
                + "    alert(sessionStorage.length);\n"
                + "    alert(sessionStorage.getItem('hi'));\n"
                + "    alert(sessionStorage.getItem('hello'));\n"
                + "    sessionStorage.removeItem(sessionStorage.key(0));\n"
                + "    alert(sessionStorage.length);\n"
                + "    if (sessionStorage.clear) {\n"
                + "      sessionStorage.clear();\n"
                + "      alert(sessionStorage.length);\n"
                + "    }\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"0", "2", "there", "world", "1", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void prototypeIsExtensible() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  localStorage.clear();\n"
                + "  alert(localStorage.extraMethod);\n"
                + "  alert(localStorage.getItem('extraMethod'));\n"
                + "  Storage.prototype.extraMethod = function() {\n"
                + "    alert('extraMethod called');\n"
                + "  };\n"
                + "  try {\n"
                + "    localStorage.extraMethod();\n"
                + "  } catch (e2) {\n"
                + "    alert('localStorage.extraMethod not callable');\n"
                + "  }\n"
                + "  alert(localStorage.getItem('extraMethod'));\n"
                + "} catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
        final String[] messages = {"undefined", "null", "extraMethod called", "null"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void prototypePropertiesAreVisible() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  localStorage.clear();\n"
                + "  alert(typeof localStorage.hasOwnProperty);\n"
                + "  alert(localStorage.getItem('hasOwnProperty'));\n"
                + "  localStorage.setItem('hasOwnProperty', 'value');\n"
                + "  alert(typeof localStorage.hasOwnProperty);\n"
                + "  alert(localStorage.getItem('hasOwnProperty'));\n"
                + "} catch (e) { alert('exception'); }\n"
                + "  alert(localStorage.length);\n"
                + "</script></body></html>";
        final String[] messages = {"function", "null", "function", "value", "1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void writeToPrototypeProperty() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  localStorage.clear();\n"
                + "  alert(typeof localStorage.hasOwnProperty);\n"
                + "  alert(localStorage.getItem('hasOwnProperty'));\n"
                + "  localStorage.hasOwnProperty = 'value';\n"
                + "  alert(typeof localStorage.hasOwnProperty);\n"
                + "  alert(localStorage.getItem('hasOwnProperty'));\n"
                + "  alert(localStorage.length);\n"
                + "} catch (e) { alert('exception'); }\n"
                + "</script></body></html>";
            final String[] messages = {"function", "null", "string", "null", "0"};
            checkHtmlAlert(html, messages);
    }
}
