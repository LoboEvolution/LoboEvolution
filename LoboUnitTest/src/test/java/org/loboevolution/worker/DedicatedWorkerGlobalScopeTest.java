/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.worker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Unit tests for DedicatedWorkerGlobalScope.
 */
@ExtendWith(AlertsExtension.class)
public class DedicatedWorkerGlobalScopeTest extends LoboUnitTest {
    @Test
    @Alerts("Received: Result = 15")
    public void onmessage() {
        final String html = "<html><body>"
                + "<script>\n"
                + "try {\n"
                + "  var myWorker = new Worker('worker.js');\n"
                + "  myWorker.onmessage = function(e) {\n"
                + "   alert('Received: ' + e.data);\n"
                + "  };\n"
                + "  setTimeout(function() { myWorker.postMessage([5, 3]);}, 10);\n"
                + "} catch(e) {alert('exception'); }\n"
                + "</script></body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Received: Result = 15")
    public void selfOnmessage() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var myWorker = new Worker('worker.js');\n"
                + "  myWorker.onmessage = function(e) {\n"
                + "   alert('Received: ' + e.data);\n"
                + "  };\n"
                + "  setTimeout(function() { myWorker.postMessage([5, 3]);}, 10);\n"
                + "} catch(e) {alert('exception'); }\n"
                + "</script></body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Received: Result = 15")
    public void selfAddEventListener() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var myWorker = new Worker('worker.js');\n"
                + "  myWorker.onmessage = function(e) {\n"
                + "   alert('Received: ' + e.data);\n"
                + "  };\n"
                + "  setTimeout(function() { myWorker.postMessage([5, 3]);}, 10);\n"
                + "} catch(e) {alert('exception'); }\n"
                + "</script></body></html>\n";

        final String workerJs = "self.addEventListener('message', (e) => {\n"
                + "  var workerResult = 'Result = ' + (e.data[0] * e.data[1]);\n"
                + "  postMessage(workerResult);\n"
                + "});\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Received: timeout")
    public void selfSetTimeout() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var myWorker = new Worker('worker.js');\n"
                + "  myWorker.onmessage = function(e) {\n"
                + "   alert('Received: ' + e.data);\n"
                + "  };\n"
                + "} catch(e) {alert('exception'); }\n"
                + "</script></body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Received: interval")
    public void selfSetInterval() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var myWorker = new Worker('worker.js');\n"
                + "  myWorker.onmessage = function(e) {\n"
                + "   alert('Received: ' + e.data);\n"
                + "  };\n"
                + "} catch(e) {alert('exception'); }\n"
                + "</script></body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Received: func=function addEventListener() { [native code] }")
    public void functionDefaultValue() {
        final String html = "<html><body><script>\n"
                + "try {\n"
                + "  var myWorker = new Worker('worker.js');\n"
                + "  myWorker.onmessage = function(e) {\n"
                + "   alert('Received: ' + e.data);\n"
                + "  };\n"
                + "} catch(e) {alert('exception'); }\n"
                + "</script></body></html>\n";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts("Received: Result = 15")
    public void workerCodeWithWrongMimeType() {
        final String html = "<html><body>"
                + "<script>\n"
                + "try {\n"
                + "  var myWorker = new Worker('worker.js');\n"
                + "  myWorker.onmessage = function(e) {\n"
                + "   alert('Received: ' + e.data);\n"
                + "  };\n"
                + "  setTimeout(function() { myWorker.postMessage([5, 3]);}, 10);\n"
                + "} catch(e) {alert('exception'); }\n"
                + "</script></body></html>\n";

        checkHtmlAlert(html);
    }
}
