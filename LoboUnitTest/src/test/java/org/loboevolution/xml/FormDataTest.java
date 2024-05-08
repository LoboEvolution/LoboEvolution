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
package org.loboevolution.xml;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for FormData.
 */
@ExtendWith(AlertsExtension.class)
public class FormDataTest extends LoboUnitTest {

    @Test
    @Alerts({"function", "function", "function", "function", "function", "function",
            "function", "function", "function", "function"})
    public void functions() {
        final String html
                = "<html><head><script>\n"
                + "  function test() {\n"
                + "    if (window.FormData) {\n"
                + "      var formData = new FormData();\n"
                + "     alert(typeof formData.append);\n"
                + "     alert(typeof formData.delete);\n"
                + "     alert(typeof formData.entries);\n"
                + "     alert(typeof formData.forEach);\n"
                + "     alert(typeof formData.get);\n"
                + "     alert(typeof formData.getAll);\n"
                + "     alert(typeof formData.has);\n"
                + "     alert(typeof formData.keys);\n"
                + "     alert(typeof formData.set);\n"
                + "     alert(typeof formData.values);\n"
                + "    }\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myKey", "myKey1"})
    public void delete() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var formData = new FormData();\n"
                + "    if (!formData.delete) { alert('no delete'); return; }\n"
                + "    formData.append('myKey', 'myValue');\n"
                + "    formData.append('myKey1', '');\n"
                + "    formData.append('mykey 2', '');\n"
                + "    formData.append('mykey3', 'myVal3');\n"
                + "    formData.append('mykey3', 'myVal4');\n"
                + "  } catch (e) {\n"
                + "    alert('create: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "  try {\n"
                + "    formData.delete('mykey');\n"
                + "    formData.delete('mykey 2');\n"
                + "    formData.delete('mykey3');\n"
                + "    formData.delete('');\n"
                + "  } catch (e) {\n"
                + "    alert('delete: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "  try {\n"
                + "    var xhr = new XMLHttpRequest();\n"
                + "    xhr.open('POST', '/test2', false);\n"
                + "    xhr.send(formData);\n"
                + "    alert(xhr.responseText);\n"
                + "  } catch (e) {\n"
                + "    alert('send: ' + e.message);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myValue", "null", "null", "null", "null"})
    public void get() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var formData = new FormData();\n"
                + "    if (!formData.get) {alert('no get'); return; }\n"
                + "    formData.append('myKey', 'myValue');\n"
                + "    formData.append('myKey', 'myValue2');\n"
                + "    formData.append('mykey3', 'myValue3');\n"
                + "    formData.append('mykey4', '');\n"
                + "  } catch (e) {\n"
                + "   alert('create: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "  try {\n"
                + "   alert(formData.get('myKey'));\n"
                + "   alert(formData.get('mykey'));\n"
                + "   alert(formData.get('myKey3'));\n"
                + "   alert(formData.get('myKey4'));\n"
                + "   alert(formData.get(''));\n"
                + "  } catch (e) {\n"
                + "   alert('get: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myValue,myValue2", "", "", "", ""})
    public void getall() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var formData = new FormData();\n"
                + "    if (!formData.get) {alert('no getAll'); return; }\n"
                + "    formData.append('myKey', 'myValue');\n"
                + "    formData.append('myKey', 'myValue2');\n"
                + "    formData.append('mykey3', 'myValue3');\n"
                + "    formData.append('mykey4', '');\n"
                + "  } catch (e) {\n"
                + "   alert('create: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "  try {\n"
                + "   alert(formData.getAll('myKey'));\n"
                + "   alert(formData.getAll('mykey'));\n"
                + "   alert(formData.getAll('myKey3'));\n"
                + "   alert(formData.getAll('myKey4'));\n"
                + "   alert(formData.getAll(''));\n"
                + "  } catch (e) {\n"
                + "   alert('getAll: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "false", "false"})
    public void has() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var formData = new FormData();\n"
                + "    if (!formData.has) {alert('no has'); return; }\n"
                + "    formData.append('myKey', 'myValue');\n"
                + "    formData.append('myKey1', '');\n"
                + "    formData.append('mykey 2', '');\n"
                + "  } catch (e) {\n"
                + "   alert('create: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "  try {\n"
                + "   alert(formData.has('myKey'));\n"
                + "   alert(formData.has('mykey'));\n"
                + "   alert(formData.has(''));\n"
                + "  } catch (e) {\n"
                + "   alert('has: ' + e.message);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='test()'></body></html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("no set")
    public void set() {
        final String html
                = "<html><head><title>foo</title><script>\n"
                + "function test() {\n"
                + "  try {\n"
                + "    var formData = new FormData();\n"
                + "    if (!formData.set) { alert('no set'); return; }\n"
                + "    formData.append('myKey1', 'myValue1');\n"
                + "    formData.append('myKey1', 'myValue2');\n"
                + "    formData.append('myKey3', 'myValue3');\n"
                + "    formData.append('myKey4', 'myValue4');\n"
                + "    formData.set('myKey1', 'new1');\n"
                + "    formData.set('myKey4', 'new4');\n"
                + "    formData.set('myKeyX', 'newX');\n"
                + "  } catch (e) {\n"
                + "    alert('set: ' + e.message);\n"
                + "    return;\n"
                + "  }\n"
                + "  try {\n"
                + "    var xhr = new XMLHttpRequest();\n"
                + "    xhr.open('POST', '/test2', false);\n"
                + "    xhr.send(formData);\n"
                + "    alert(xhr.responseText);\n"
                + "  } catch (e) {\n"
                + "    alert('send: ' + e.message);\n"
                + "  }\n"
                + "}\n"
                + "</script></head><body onload='test()'></body></html>";

        checkHtmlAlert(html);
    }


    @Test
    @Alerts({"function keys() { [native code] }", "[object FormData Iterator]",
            "key1", "key2", "key1", "undefined", "true"})
    public void keys() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"
                        + "    function test() {\n"
                        + "      var formData = new FormData();\n"

                        + "      if (!formData.forEach) {\n"
                        + "       alert('no keys');\n"
                        + "        return;"
                        + "      }\n"

                        + "      formData.append('key1', 'val1');\n"
                        + "      formData.append('key2', undefined);\n"
                        + "      formData.append('key1', 'val3');\n"
                        + "      formData.append(undefined, 'val3');\n"


                        + "     alert(formData.keys);\n"
                        + "      var iter = formData.keys();\n"
                        + "     alert(iter);\n"

                        + "      var entry = iter.next().value;\n"
                        + "     alert(entry);\n"
                        + "      entry = iter.next().value;\n"
                        + "     alert(entry);\n"
                        + "      entry = iter.next().value;\n"
                        + "     alert(entry);\n"
                        + "      entry = iter.next().value;\n"
                        + "     alert(entry);\n"

                        + "     alert(iter.next().done);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"function values() { [native code] }", "[object FormData Iterator]",
            "val1", "undefined", "val3", "val4", "true"})
    public void values() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var formData = new FormData();\n"

                        + "      if (!formData.forEach) {\n"
                        + "       alert('no values');\n"
                        + "        return;"
                        + "      }\n"

                        + "      formData.append('key1', 'val1');\n"
                        + "      formData.append('key2', undefined);\n"
                        + "      formData.append('key1', 'val3');\n"
                        + "      formData.append(undefined, 'val4');\n"

                        + "     alert(formData.values);\n"
                        + "      var iter = formData.values();\n"
                        + "     alert(iter);\n"

                        + "      var entry = iter.next().value;\n"
                        + "     alert(entry);\n"
                        + "      entry = iter.next().value;\n"
                        + "     alert(entry);\n"
                        + "      entry = iter.next().value;\n"
                        + "     alert(entry);\n"
                        + "      entry = iter.next().value;\n"
                        + "     alert(entry);\n"

                        + "     alert(iter.next().done);\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"val1", "undefined", "val3", "val4"})
    public void valuesForOf() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var formData = new FormData();\n"

                        + "      if (!formData.forEach) {\n"
                        + "       alert('no values');\n"
                        + "        return;"
                        + "      }\n"

                        + "      formData.append('key1', 'val1');\n"
                        + "      formData.append('key2', undefined);\n"
                        + "      formData.append('key1', 'val3');\n"
                        + "      formData.append(undefined, 'val4');\n"

                        + "      for (var i of formData.values()) {\n"
                        + "       alert(i);\n"
                        + "      }\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"key1-val1", "key2-val2", "key3-val3",
            "key1-val1", "key3-val3",
            "key2-val2", "key3-val3"})
    public void forEach() {
        final String html =
                "<html>\n"
                        + "<head>\n"
                        + "  <script>\n"

                        + "    function test() {\n"
                        + "      var formData = new FormData();\n"

                        + "      if (!formData.forEach) {\n"
                        + "       alert('no forEach');\n"
                        + "        return;"
                        + "      }\n"

                        + "      formData.append('key1', 'val1');\n"
                        + "      formData.append('key2', 'val2');\n"
                        + "      formData.append('key3', 'val3');\n"

                        + "      formData.forEach((value, key) => {\n"
                        + "       alert(key + '-' + value);\n"
                        + "      });\n"

                        + "      formData.forEach((value, key) => {\n"
                        + "       alert(key + '-' + value);\n"
                        + "        if (value == 'val1' || value == 'val2') {\n"
                        + "          formData.delete(key);\n"
                        + "        }\n"
                        + "      });\n"

                        + "      formData.forEach((value, key) => {\n"
                        + "       alert(key + '-' + value);\n"
                        + "      });\n"
                        + "    }\n"
                        + "  </script>\n"
                        + "</head>\n"
                        + "<body onload='test()'>\n"
                        + "</body>\n"
                        + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"myKey", "myValue", "myKey2", "", "myKey", "myvalue2"})
    public void entries_forOf() {
        final String html
                = "<html><head>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var formData = new FormData();\n"
                + "  if (!formData.get) {\n"
                + "   alert('no entries');\n"
                + "    return;"
                + "  }\n"
                + "  formData.append('myKey', 'myValue');\n"
                + "  formData.append('myKey2', '');\n"
                + "  formData.append('myKey', 'myvalue2');\n"
                + "  for (var pair of formData.entries()) {\n"
                + "   alert(pair[0]);\n"
                + "   alert(pair[1]);\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "[object FormData Iterator]", "done", "value",
            "myKey", "myValue", "myKey2", "", "myKey", "myvalue2"})
    public void entriesIterator() {
        final String html
                = "<html><head><script>\n"
                + "function test() {\n"
                + "  var formData = new FormData();\n"
                + "  if (!formData.get) {\n"
                + "   alert('no entries');\n"
                + "    return;"
                + "  }\n"
                + "  formData.append('myKey', 'myValue');\n"
                + "  formData.append('myKey2', '');\n"
                + "  formData.append('myKey', 'myvalue2');\n"
                + "  if (typeof Symbol != 'undefined') {\n"
                + "   alert(formData[Symbol.iterator] === formData.entries);\n"
                + "  }\n"
                + "  var iterator = formData.entries();\n"
                + " alert(iterator);\n"
                + "  var nextItem = iterator.next();\n"
                + "  for (var x in nextItem) {\n"
                + "   alert(x);\n"
                + "  }\n"
                + "  while (nextItem.done == false) {\n"
                + "   alert(nextItem.value[0]);\n"
                + "   alert(nextItem.value[1]);\n"
                + "    nextItem = iterator.next();\n"
                + "  }\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'></body>\n"
                + "</html>";

        checkHtmlAlert(html);
    }
}
