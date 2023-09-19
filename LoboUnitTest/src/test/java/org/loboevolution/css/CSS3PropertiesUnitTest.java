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
package org.loboevolution.css;

import org.junit.Test;
import org.loboevolution.driver.LoboUnitTest;

/**
 * Tests for {@link org.loboevolution.html.node.css.CSS3Properties}.
 */
public class CSS3PropertiesUnitTest extends LoboUnitTest {

    @Test
    public void cssFloat() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var e = document.getElementById('myDiv');\n"
                + "    var s = window.getComputedStyle(e, null);\n"
                + "    alert(s.cssFloat);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";
        final String[] messages = {"none"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleElement() {
        final String html = "<html><head>\n"
                + "<style type='text/css'>\n"
                + "  /* <![CDATA[ */\n"
                + "  #myDiv2 {cursor: pointer}\n"
                + "  /* ]]> */\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('myDiv1');\n"
                + "    var div2 = document.getElementById('myDiv2');\n"
                + "    alert(div1.style.cursor);\n"
                + "    alert(div2.style.cursor);\n"
                + "    alert(window.getComputedStyle(div1, null).cursor);\n"
                + "    alert(window.getComputedStyle(div2, null).cursor);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='myDiv1'/>\n"
                + "  <div id='myDiv2'/>\n"
                + "</body></html>";
        final String[] messages = {null, null, "auto", "pointer"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleElement2() {
        final String html = "<html><head>\n"
                + "<style type='text/css'>\n"
                + "  /* <![CDATA[ */\n"
                + "  #style_test_1 {cursor: pointer}\n"
                + "  /* ]]> */\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('style_test_1');\n"
                + "    var div2 = document.getElementById('myDiv2');\n"
                + "    alert(div1.style.cursor);\n"
                + "    alert(div2.style.cursor);\n"
                + "    alert(window.getComputedStyle(div1, null).cursor);\n"
                + "    alert(window.getComputedStyle(div2, null).cursor);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div id='style_test_1'/>\n"
                + "  <div id='myDiv2'/>\n"
                + "</body></html>";
        final String[] messages = {"", "", "pointer", "pointer"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndex() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var d = document.getElementById('myDiv');\n"
                + "    var style = window.getComputedStyle(d, null);\n"
                + "    alert(style.zIndex);\n"
                + "    alert(typeof style.zIndex);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>";
        final String[] messages = {"auto", "string"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void styleAttributePreferredOverStylesheet() {
        final String html = "<html>\n"
                + "<head><style>div { width: 30px; }</style></head>\n"
                + "<body>\n"
                + "<div id='d' style='width:50px'>foo</div>\n"
                + "<script>\n"
                + "var d = document.getElementById('d');\n"
                + "var style = window.getComputedStyle(d, null);\n"
                + "alert(style.width);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"50px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void lengthsConvertedToPixels() {
        final String html = "<html><body>\n"
                + "<div id='d' style='width:1em; height:1em; border:1em solid black; padding:1em; margin:1em;'>d</div>\n"
                + "<script>\n"
                + "var d = document.getElementById('d');\n"
                + "var cs = window.getComputedStyle(d, null);\n"
                + "alert(d.style.width + ' ' + cs.width);\n"
                + "alert(d.style.height + ' ' + cs.height);\n"
                + "alert(d.style.borderBottomWidth + ' ' + cs.borderBottomWidth);\n"
                + "alert(d.style.borderLeftWidth + ' ' + cs.borderLeftWidth);\n"
                + "alert(d.style.borderTopWidth + ' ' + cs.borderTopWidth);\n"
                + "alert(d.style.borderRightWidth + ' ' + cs.borderRightWidth);\n"
                + "alert(d.style.paddingBottom + ' ' + cs.paddingBottom);\n"
                + "alert(d.style.paddingLeft + ' ' + cs.paddingLeft);\n"
                + "alert(d.style.paddingTop + ' ' + cs.paddingTop);\n"
                + "alert(d.style.paddingRight + ' ' + cs.paddingRight);\n"
                + "alert(d.style.marginBottom + ' ' + cs.marginBottom);\n"
                + "alert(d.style.marginLeft + ' ' + cs.marginLeft);\n"
                + "alert(d.style.marginTop + ' ' + cs.marginTop);\n"
                + "alert(d.style.marginRight + ' ' + cs.marginRight);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"1em 16px", "1em 16px", "1em 16px", "1em 16px", "1em 16px", "1em 16px", "1em 16px", "1em 16px",
                "1em 16px", "1em 16px", "1em 16px", "1em 16px", "1em 16px", "1em 16px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesA() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p id='p'>\n"
                + "    <a id='a'></a>\n"
                + "    <abbr id='abbr'></abbr>\n"
                + "    <address id='address'></address>\n"
                + "    <article id='article'></article>\n"
                + "    <aside id='aside'></aside>\n"
                + "  </p>\n"
                + "  <img usemap='#imgmap'>\n"
                + "    <map name='imgmap'>\n"
                + "      <area id='area'>\n"
                + "    </map>\n"
                + "  </img>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('a');\n"
                + "    x('abbr');\n"
                + "    x('address');\n"
                + "    x('area');\n"
                + "    x('article');\n"
                + "    x('aside');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "inline", "inline", "inline", "block", "block"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void defaultDisplayValuesB() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p id='p'>\n"
                + "    <b id='b'></b>\n"
                + "    <bdi id='bdi'></bdi>\n"
                + "    <bdo id='bdo'></bdo>\n"
                + "    <blockquote id='blockquote'></blockquote>\n"
                + "    <br id='br'>\n"
                + "    <button id='button' type='button'></button>\n"
                + "  </p>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('b');\n"
                + "    x('bdi');\n"
                + "    x('bdo');\n"
                + "    x('blockquote');\n"
                + "    x('br');\n"
                + "    x('button');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "inline", "inline", "block", "inline", "inline-block"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void defaultDisplayValuesC() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <canvas id='canvas'></canvas>\n"
                + "  <center id='center'></center>\n"
                + "  <code id='code'></code>\n"
                + "  <table>\n"
                + "    <caption id='caption'></caption>\n"
                + "    <colgroup id='colgroup'>\n"
                + "      <col id='col'>\n"
                + "    </colgroup>\n"
                + "  </table>\n"
                + "  <p id='p'>\n"
                + "    <cite id='cite'></cite>\n"
                + "  </p>\n"
                + "  <menu>\n"
                + "    <command id='command'></command>\n"
                + "  </menu>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('canvas');\n"
                + "    x('caption');\n"
                + "    x('center');\n"
                + "    x('cite');\n"
                + "    x('code');\n"
                + "    x('col');\n"
                + "    x('colgroup');\n"
                + "    x('command');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "table-caption", "block", "inline", "inline", "table-column", "table-column-group", "inline"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void defaultDisplayValuesD() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <dl id='dl'>\n"
                + "    <dt id='dt'></dt>\n"
                + "      <dd id='dd'><dd>\n"
                + "  </dl>\n"
                + "  <p id='p'>\n"
                + "    <del id='del'></del>\n"
                + "  </p>\n"
                + "  <details id='details'></details>\n"
                + "  <dfn id='dfn'></dfn>\n"
                + "  <dialog id='dialog'></dialog>\n"
                + "  <dir id='dir'></dir>\n"
                + "  <dir id='div'></div>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('dd');\n"
                + "    x('del');\n"
                + "    x('details');\n"
                + "    x('dfn');\n"
                + "    x('dialog');\n"
                + "    x('dir');\n"
                + "    x('div');\n"
                + "    x('dl');\n"
                + "    x('dt');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"block", "inline", "block", "inline", "none", "block", "block", "block", "block"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void defaultDisplayValuesE() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p id='p'>\n"
                + "    <em id='em'></em>\n"
                + "  </p>\n"
                + "  <embed id='embed'>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('em');\n"
                + "    x('embed');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "inline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesF() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <form id='form'>\n"
                + "    <fieldset id='fieldset'></fieldset>\n"
                + "  </form>\n"
                + "  <figure id='figure'>\n"
                + "    <figcaption id='figcaption'></figcaption>\n"
                + "  </figure>\n"
                + "  <footer id='footer'></footer>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('fieldset');\n"
                + "    x('figcaption');\n"
                + "    x('figure');\n"
                + "    x('footer');\n"
                + "    x('form');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"block", "block", "block", "block", "block"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesH() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <h1 id='h1'></h1>\n"
                + "  <h2 id='h2'></h2>\n"
                + "  <h3 id='h3'></h3>\n"
                + "  <h4 id='h4'></h4>\n"
                + "  <h5 id='h5'></h5>\n"
                + "  <h6 id='h6'></h6>\n"
                + "  <header id='header'></header>\n"
                + "  <hr id='hr'>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('h1');\n"
                + "    x('h2');\n"
                + "    x('h3');\n"
                + "    x('h4');\n"
                + "    x('h5');\n"
                + "    x('h6');\n"
                + "    x('header');\n"
                + "    x('hr');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"block", "block", "block", "block", "block", "block", "block", "block"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesI() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p id='p'>\n"
                + "    <i id='i'></i>\n"
                + "    <ins id='ins'></ins>\n"
                + "  </p>\n"
                + "  <iframe id='iframe'></iframe>\n"
                + "  <img id='img'></img>\n"
                + "  <form id='form'>\n"
                + "    <input id='submit' type='submit'>\n"
                + "    <input id='reset' type='reset'>\n"
                + "    <input id='text' type='text'>\n"
                + "    <input id='password' type='password'>\n"
                + "    <input id='checkbox' type='checkbox'>\n"
                + "    <input id='radio' type='radio'>\n"
                + "  </form>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('i');\n"
                + "    x('iframe');\n"
                + "    x('img');\n"
                + "    x('submit');\n"
                + "    x('reset');\n"
                + "    x('text');\n"
                + "    x('password');\n"
                + "    x('checkbox');\n"
                + "    x('radio');\n"
                + "    x('ins');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "inline", "inline-block", "inline-block", "inline-block",
                "inline-block", "inline-block", "inline-block", "inline-block", "inline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesKL() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p id='p'>\n"
                + "    <kbd id='kbd'></kbd>\n"
                + "  </p>\n"
                + "  <ol>\n"
                + "    <li id='li'></li>\n"
                + "  </ol>\n"
                + "  <form id='form'>\n"
                + "    <keygen id='keygen'>\n"
                + "    <label id='label'>\n"
                + "    <fieldset id='fieldset'>\n"
                + "      <legend id='legend'></legend>\n"
                + "    </fieldset>\n"
                + "  </form>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('kbd');\n"
                + "    x('keygen');\n"
                + "    x('label');\n"
                + "    x('legend');\n"
                + "    x('li');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "inline", "inline", "block", "list-item"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesM() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <img usemap='#imgmap'>\n"
                + "    <map id='map' name='imgmap'>\n"
                + "      <area id='area'>\n"
                + "    </map>\n"
                + "  </img>\n"
                + "  <p id='p'>\n"
                + "    <mark id='mark'></mark>\n"
                + "  </p>\n"
                + "  <menu id='menu'>\n"
                + "    <li id='li'></li>\n"
                + "  </menu>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('map');\n"
                + "    x('mark');\n"
                + "    x('menu');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "inline", "block"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void defaultDisplayValuesNO() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <nav id='nav'>\n"
                + "    <a id='a'></a>\n"
                + "  </nav>\n"
                + "  <noscript id='noscript'></noscript> \n"
                + "  <object id='object'></object> "
                + "  <ol id='ol'>\n"
                + "    <li></li>\n"
                + "  </ol>\n"
                + "  <form>\n"
                + "    <select>\n"
                + "      <optgroup id='optgroup'>\n"
                + "        <option></option>\n"
                + "      </optgroup>\n"
                + "      <option id='option'></option>\n"
                + "    </select>\n"
                + "    <output id='output'></output>\n"
                + "  </form>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('nav');\n"
                + "    x('noscript');\n"
                + "    x('object');\n"
                + "    x('ol');\n"
                + "    x('optgroup');\n"
                + "    x('option');\n"
                + "    x('output');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"block", "none", "inline", "block", "block", "block", "inline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesPQ() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p id='p'><q id='q'></q></p>\n"
                + "  <object>\n"
                + "    <param id='param' name='movie' value=''></param>\n"
                + "  </object> "
                + "  <pre id='pre'></pre>\n"
                + "  <progress id='progress'></progress>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('p');\n"
                + "    x('pre');\n"
                + "    x('progress');\n"
                + "    x('q');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"block", "block", "inline-block", "inline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesS() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p>\n"
                + "    <s id='s'></s>\n"
                + "    <small id='small'></small>\n"
                + "    <span id='span'></span>\n"
                + "    <strike id='strike'></strike>\n"
                + "    <strong id='strong'></strong>\n"
                + "    <sub id='sub'></sub>\n"
                + "    <sup id='sup'></sup>\n"
                + "  </p> \n"
                + "  <samp id='samp'></samp>\n"
                + "  <section id='section'></section>\n"
                + "  <summary id='summary'></summary>\n"
                + "  <audio>\n"
                + "    <source id='source'>\n"
                + "  </audio>\n"
                + "  <form>\n"
                + "    <select id='select'>\n"
                + "      <option></option>\n"
                + "    </select>\n"
                + "  </form>\n"
                + "  <script id='script'>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('s');\n"
                + "    x('samp');\n"
                + "    x('script');\n"
                + "    x('section');\n"
                + "    x('select');\n"
                + "    x('small');\n"
                + "    x('source');\n"
                + "    x('span');\n"
                + "    x('strike');\n"
                + "    x('strong');\n"
                + "    x('sub');\n"
                + "    x('summary');\n"
                + "    x('sup');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "inline", "none", "block", "inline-block", "block",
                "inline", "inline", "inline", "inline", "inline", "block", "inline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void defaultDisplayValuesT() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <table id='table'>\n"
                + "    <thead id='thead'><tr id='tr'><th id='th'>header</th></tr></thead>\n"
                + "    <tfoot id='tfoot'><tr><td>footer</td></tr></tfoot>\n"
                + "    <tbody id='tbody'><tr><td id='td'>body</td></tr></tbody>\n"
                + "  </table>\n"
                + "  <form>\n"
                + "    <textarea id='textarea'></textarea>\n"
                + "  </form>\n"
                + "  <p>\n"
                + "    <time id='time'></time>\n"
                + "    <tt id='tt'></tt>\n"
                + "  </p> \n"
                + "  <video>\n"
                + "    <track id='track'>\n"
                + "  </video>\n"
                + "  <script id='script'>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('table');\n"
                + "    x('tbody');\n"
                + "    x('td');\n"
                + "    x('textarea');\n"
                + "    x('tfoot');\n"
                + "    x('th');\n"
                + "    x('thead');\n"
                + "    x('time');\n"
                + "    x('tr');\n"
                + "    x('track');\n"
                + "    x('tt');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"table", "table-row-group", "table-cell", "inline-block", "table-footer-group",
                "table-cell", "table-header-group", "inline", "table-row", "inline", "inline"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void defaultDisplayValuesUVW() {
        final String html = "<!DOCTYPE HTML>\n<html><body>\n"
                + "  <p>\n"
                + "    <u id='u'></u>\n"
                + "    <wbr id='wbr'></wbr>\n"
                + "  </p> \n"
                + "  <ul id='ul'>\n"
                + "    <li></li>\n"
                + "  </ul>\n"
                + "  <var id='var'></var>\n"
                + "  <video id='video'></video>\n"
                + "  <script id='script'>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      var cs = window.getComputedStyle(e, null);\n"
                + "      var disp = cs ? cs.display : null;\n"
                + "      alert(disp);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('u');\n"
                + "    x('ul');\n"
                + "    x('var');\n"
                + "    x('video');\n"
                + "    x('wbr');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"inline", "block", "inline", "inline", "inline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void backgroundColor() {
        final String html = "<html><body>\n"
                + "<div id='d0'>div 0</div>\n"
                + "<div id='d1' style='background: red'>d</div>\n"
                + "<div id='d2' style='background: white url(http://htmlunit.sf.net/foo.png) repeat-x fixed top right'>\n"
                + "second div</div>\n"
                + "<script>\n"
                + "function getStyle(x) {\n"
                + "  var d = document.getElementById(x);\n"
                + "  var cs = window.getComputedStyle(d, null);\n"
                + "  return cs;\n"
                + "}\n"
                + "var cs0 = getStyle('d0');\n"
                + "alert(cs0.backgroundColor);\n"
                + "var cs1 = getStyle('d1');\n"
                + "alert(cs1.backgroundColor);\n"
                + "var cs2 = getStyle('d2');\n"
                + "alert(cs2.backgroundColor);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"rgb(0, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 255, 255)"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void fontSize() {
        final String html = "<html><body>\n"
                + "<div id='d0' style='font-size: 10px;'>\n"
                + "<div id='d1'>inside</div>\n"
                + "</div>\n"
                + "<script>\n"
                + "function getStyle(x) {\n"
                + "  var d = document.getElementById(x);\n"
                + "  var cs = window.getComputedStyle(d, null);\n"
                + "  return cs;\n"
                + "}\n"
                + "var cs1 = getStyle('d1');\n"
                + "alert(cs1.fontSize);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"10px"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void fontSize2() {
        final String html = "<html><body>\n"
                + "<div id='d0' style='font-size: 0.6em;'>\n"
                + "<div id='d1'>inside</div>\n"
                + "</div>\n"
                + "<script>\n"
                + "  function getStyle(x) {\n"
                + "    var d = document.getElementById(x);\n"
                + "    var cs = window.getComputedStyle(d, null);\n"
                + "    return cs;\n"
                + "  }\n"
                + "  var cs1 = getStyle('d1');\n"
                + "  alert(cs1.fontSize);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"10px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void fontSizeVH() {
        final String html = "<html><body>\n"
                + "<div id='d0' style='font-size: 0.6vh;'>\n"
                + "<div id='d1'>inside</div>\n"
                + "</div>\n"
                + "<script>\n"
                + "  function getStyle(x) {\n"
                + "    var d = document.getElementById(x);\n"
                + "    var cs = window.getComputedStyle(d, null);\n"
                + "    return cs;\n"
                + "  }\n"
                + "  var cs1 = getStyle('d1');\n"
                + "  alert(cs1.fontSize);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"2px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void fontSizeVW() {
        final String html = "<html><body>\n"
                + "<div id='d0' style='font-size: 0.6vw;'>\n"
                + "<div id='d1'>inside</div>\n"
                + "</div>\n"
                + "<script>\n"
                + "  function getStyle(x) {\n"
                + "    var d = document.getElementById(x);\n"
                + "    var cs = window.getComputedStyle(d, null);\n"
                + "    return cs;\n"
                + "  }\n"
                + "  var cs1 = getStyle('d1');\n"
                + "  alert(cs1.fontSize);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"5px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void computedWidthOfHiddenElements() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('myDiv1');\n"
                + "    var cs1 = window.getComputedStyle(div1, null);\n"
                + "    alert(cs1.width);\n"
                + "    var div2 = document.getElementById('myDiv2');\n"
                + "    var cs2 = window.getComputedStyle(div2, null);\n"
                + "    alert(cs2.width);\n"
                + "  }\n"
                + "</script></head><body onload='test()'>\n"
                + "  <div style='width: 111px'>\n"
                + "    <div id='myDiv1'></div>\n"
                + "    <div id='myDiv2' style='display:none'/>\n"
                + "  </div>\n"
                + "</body></html>";
        final String[] messages = {"111px", "auto"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void inheritedImplicitly() {
        final String html
                = "<html><body><table id='a'><tr id='b'><td>a</td></tr></table><script>\n"
                + "var a = document.getElementById('a');\n"
                + "var b = document.getElementById('b');\n"
                + "var as = a.style;\n"
                + "var bs = b.style;\n"
                + "var acs = window.getComputedStyle(a, null);\n"
                + "var bcs = window.getComputedStyle(b, null);\n"
                + "alert(as.borderCollapse + ',' + bs.borderCollapse);\n"
                + "alert(acs.borderCollapse + ',' + bcs.borderCollapse);\n"
                + "as.borderCollapse = 'collapse';\n"
                + "alert(as.borderCollapse + ',' + bs.borderCollapse);\n"
                + "alert(acs.borderCollapse + ',' + bcs.borderCollapse);\n"
                + "</script></body></html>";
        final String[] messages = {",", "separate,separate", "collapse,", "collapse,collapse"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void changeInParentClassNodeReferencedByRule() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "function readDecoration(id) {\n"
                + "  var e = document.getElementById(id);\n"
                + "  var s = window.getComputedStyle(e, null);\n"
                + "  alert(s.textDecoration);\n"
                + "}\n"
                + "function test() {\n"
                + "  var fooA = document.getElementById('fooA');\n"
                + "  readDecoration('fooB');\n"
                + "  fooA.setAttribute('class', '');\n"
                + "  readDecoration('fooB');\n"
                + "  fooA.setAttribute('class', 'A');\n"
                + "  readDecoration('fooB');\n"
                + "}\n"
                + "</script>\n"
                + "<style>\n"
                + ".A .B { text-decoration: underline }\n"
                + "</style>\n"
                + "</head><body onload='test()'>\n"
                + "<div class='A' id='fooA'>A\n"
                + "<div class='B' id='fooB'>B</div></div>\n"
                + "</body></html>";
        final String[] messages = {"underline", "none", "underline"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void widthAndHeightPercentagesAndPx() {
        final String html = "<html><body onload='test()'>\n"
                + "<div id='d1' style='width:200px;height:400px'><div id='d2' style='width:50%;height:25%'></div></div>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var d1 = document.getElementById('d1');\n"
                + "    var s1 = window.getComputedStyle(d1, null);\n"
                + "    var d2 = document.getElementById('d2');\n"
                + "    var s2 = window.getComputedStyle(d2, null);\n"
                + "    alert(d1.style.width + ',' + d1.style.height);\n"
                + "    alert(d1.offsetWidth + ',' + d1.offsetHeight);\n"
                + "    alert(s1.width + ',' + s1.height);\n"
                + "    alert(d2.style.width + ',' + d2.style.height);\n"
                + "    alert(d2.offsetWidth + ',' + d2.offsetHeight);\n"
                + "    alert(s2.width + ',' + s2.height);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"200px,400px", "200,400", "200px,400px", "50%,25%", "100,100", "100px,100px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void widthAndHeightPercentagesAndEm() {
        final String html = "<html><body onload='test()'>\n"
                + "<div id='d1' style='width:10em;height:20em'><div id='d2' style='width:50%;height:25%'></div></div>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var d1 = document.getElementById('d1');\n"
                + "    var s1 = window.getComputedStyle(d1, null);\n"
                + "    var d2 = document.getElementById('d2');\n"
                + "    var s2 = window.getComputedStyle(d2, null);\n"
                + "    alert(d1.style.width + ',' + d1.style.height);\n"
                + "    alert(d1.offsetWidth + ',' + d1.offsetHeight);\n"
                + "    alert(s1.width + ',' + s1.height);\n"
                + "    alert(d2.style.width + ',' + d2.style.height);\n"
                + "    alert(d2.offsetWidth + ',' + d2.offsetHeight);\n"
                + "    alert(s2.width + ',' + s2.height);\n"
                + "  }\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"10em,20em", "160,320", "160px,320px", "50%,25%", "80,80", "80px,80px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void widthAndHeightPercentagesHTML() {
        final String html = "<html style='height: 100%'>\n"
                + "<body>\n"
                + "<script>\n"
                + "  var h = document.documentElement;\n"
                + "  alert(h.offsetWidth > 0);\n"
                + "  alert(h.offsetHeight > 0);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void widthAndHeightInputElements() {
        final String html = "<html>\n"
                + "<body>\n"
                + "  <form id='form'>\n"
                + "    <input id='submit' type='submit'>\n"
                + "    <input id='reset' type='reset'>\n"
                + "    <input id='text' type='text'>\n"
                + "    <input id='password' type='password'>\n"
                + "    <input id='checkbox' type='checkbox'>\n"
                + "    <input id='radio' type='radio'>\n"
                + "    <input id='hidden' type='hidden'>\n"
                + "    <button id='button' type='button'></button>\n"
                + "    <textarea id='myTextarea'></textarea>\n"
                + "  </form>\n"
                + "  <script>\n"
                + "    function x(id) {\n"
                + "      var e = document.getElementById(id);\n"
                + "      alert(e.offsetWidth > 0);\n"
                + "      alert(e.offsetHeight > 0);\n"
                + "    }\n"
                + "  </script>\n"
                + "  <script>\n"
                + "    x('submit');\n"
                + "    x('reset');\n"
                + "    x('text');\n"
                + "    x('password');\n"
                + "    x('checkbox');\n"
                + "    x('radio');\n"
                + "    x('hidden');\n"
                + "    x('button');\n"
                + "    x('myTextarea');\n"
                + "  </script>\n"
                + "</body></html>";
        final String[] messages = {"true", "true", "true", "true", "true", "true", "true", "true",
                "true", "true", "true", "true", "false", "false",
                "true", "true", "true", "true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void widthAndHeightDisconnected() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      var e = document.createElement('div');\n"
                + "      var style = window.getComputedStyle(e, null);\n"
                + "      alert(style.width);\n"
                + "      alert(style.height);\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {null, null};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void widthAuto() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <script>\n"
                + "    function test() {\n"
                + "      tester(document.body);\n"
                + "      tester(document.getElementById('div'));\n"
                + "    }\n"
                + "    function tester(el) {\n"
                + "      alert(el.style.width == 'auto');\n"
                + "      alert(el.clientWidth > 100);\n"
                + "      alert(el.offsetWidth > 100);\n"
                + "      var style = window.getComputedStyle(el, null);\n"
                + "      alert(style.width == 'auto');\n"
                + "    }\n"
                + "  </script>\n"
                + "</head>\n"
                + "<body style='width: auto' onload='test();'>\n"
                + "<div id='div'></div>\n"
                + "</body></html>";
        final String[] messages = {"true", "true", "true", "false", "false", "true", "true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getPropertyValue() {
        final String html = "<html><head><script>\n"
                + "function doTest() {\n"
                + "  try {\n"
                + "    var d = document.getElementById('div1');\n"
                + "    var s = window.getComputedStyle(d, null);\n"
                + "    alert(s.getPropertyValue('background-color'));\n"
                + "    alert(s.getPropertyValue('color'));\n"
                + "  } catch (e) { alert(e); }\n"
                + "}\n"
                + "</script>\n"
                + "<style>#div1 { background-color: red }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "<div id='div1' style='color: blue'>foo</div></body></html>";
        final String[] messages = {"red", "blue"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void handleImportant() {
        final String html = "<html><head><script>\n"
                + "  function doTest() {\n"
                + "    alertFF(document.getElementById('div1'));\n"
                + "    alertFF(document.getElementById('div2'));\n"
                + "    alertFF(document.getElementById('div3'));\n"
                + "  }\n"
                + "  function alertFF(e) {\n"
                + "    var s = window.getComputedStyle(e, null);\n"
                + "    alert(s.getPropertyValue('font-family'));\n"
                + "  }\n"
                + "</script>\n"
                + "  <style>#div1 { font-family: swiss }</style>\n"
                + "  <style>#div2 { font-family: swiss !important }</style>\n"
                + "  <style>#div3 { font-family: swiss !important }</style>\n"
                + "</head>\n"
                + "<body onload='doTest()'>\n"
                + "  <div id='div1' style='font-family: roman'>foo</div>\n"
                + "  <div id='div2' style='font-family: roman'>foo</div>\n"
                + "  <div id='div3' style='font-family: roman !important'>foo</div>\n"
                + "</body></html>";
        final String[] messages = {"roman", "swiss", "roman"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightemptytag() {
        final String html = "<html><head><script>\n"

                + "  function test() {\n"
                + "    alert(document.getElementById('div1').offsetHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "</body></html>";
        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightempty() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('div1').offsetHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'></div>\n"
                + "</body></html>";
        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightdisplayNone() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('div1').offsetHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1' style='display: none'></div>\n"
                + "</body></html>";
        final String[] messages = {"0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightwithcontent() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('div1').offsetHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'>foo</div>\n"
                + "</body></html>";
        final String[] messages = {"18"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightwithchild() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('div1').offsetHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'><div>foo</div></div>\n"
                + "</body></html>";
        final String[] messages = {"18"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightwithchildHeight() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    alert(document.getElementById('div1').offsetHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'><iframe height='77'>foo</iframe></div>\n"
                + "</body></html>";
        final String[] messages = {"18"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightsettingheight() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  .v-loading-indicator {\n"
                + "    height: 100%\n"
                + "  }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    alert(div1.offsetHeight == 0);\n"
                + "    div1.className = 'v-loading-indicator';\n"
                + "    alert(div1.offsetHeight == 0);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'/>\n"
                + "</body></html>";
        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void scrollbarWidth() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var scroller = document.createElement('div');\n"
                + "    scroller.style['width'] = '50px';\n"
                + "    scroller.style['height'] = '50px';\n"
                + "    scroller.style['overflow'] = 'scroll';\n"
                + "    alert(scroller.offsetWidth - scroller.clientWidth == 0);\n"
                + "    document.body.appendChild(scroller);\n"
                + "    alert(scroller.offsetWidth - scroller.clientWidth == 0);\n"
                + "    document.body.removeChild(scroller);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void scrollbarHeight() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var scroller = document.createElement('div');\n"
                + "    scroller.style['width'] = '50px';\n"
                + "    scroller.style['height'] = '50px';\n"
                + "    scroller.style['overflow'] = 'scroll';\n"
                + "    alert(scroller.offsetHeight - scroller.clientHeight == 0);\n"
                + "    document.body.appendChild(scroller);\n"
                + "    alert(scroller.offsetHeight - scroller.clientHeight == 0);\n"
                + "    document.body.removeChild(scroller);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"true", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void zIndexComputed() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  .abc { z-index: 10; color:green }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "    alert(div.style.zIndex);\n"
                + "    alert(div.style['z-index']);\n"
                + "    alert(div.style.color);\n"
                + "    alert(window.getComputedStyle(div, '').zIndex);\n"
                + "    alert(window.getComputedStyle(div, '')['z-index']);\n"
                + "    alert(window.getComputedStyle(div, '').color);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv' class='abc'></div>\n"
                + "</body></html>";
        final String[] messages = {"", "", null, "10", "10", "rgb(0, 128, 0)"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void offsetWidth() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    div.style.width = '100px';\n"
                + "    div.style.height = '100px';\n"
                + "    div.style.padding = '2px';\n"
                + "    div.style.margin = '3px';\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "    alert(div.offsetWidth);\n"
                + "    alert(div.offsetHeight);\n"
                + "    alert(div.clientWidth);\n"
                + "    alert(div.clientHeight);\n"
                + "    alert(style.top);\n"
                + "    alert(style.width);\n"
                + "    alert(style.height);\n"
                + "    alert(style.marginRight);\n"
                + "    alert(style.display);\n"
                + "    alert(style.boxSizing);\n"
                + "    alert(style.borderRightWidth);\n"
                + "    alert(style.borderLeftWidth);\n"
                + "    document.body.appendChild(div);\n"
                + "    alert(div.offsetWidth);\n"
                + "    alert(div.offsetHeight);\n"
                + "    alert(div.clientWidth);\n"
                + "    alert(div.clientHeight);\n"
                + "    alert(style.top);\n"
                + "    alert(style.width);\n"
                + "    alert(style.height);\n"
                + "    alert(style.marginRight);\n"
                + "    alert(style.display);\n"
                + "    alert(style.boxSizing);\n"
                + "    alert(style.borderRightWidth);\n"
                + "    alert(style.borderLeftWidth);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "</body></html>";
        final String[] messages = {"0", "0", "0", "0", "", "", "", "", "", "", "", "",
                "104", "104", "104", "104", "auto", "100px", "100px",
                "3px", "block", "content-box", "0px", "0px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void cssFloat2() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  .abc { float: right }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    div.style.float = 'left';\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "    alert(style.float);\n"
                + "    alert(style.cssFloat);\n"
                + "    document.body.appendChild(div);\n"
                + "    alert(style.float);\n"
                + "    alert(style.cssFloat);\n"
                + "    div = document.getElementById('mydiv');\n"
                + "    style = window.getComputedStyle(div, null);\n"
                + "    alert(style.float);\n"
                + "    alert(style.cssFloat);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv' class='abc'></div>\n"
                + "</body></html>";
        final String[] messages = {"", "", "left", "left", "right", "right"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetTopTableRows() {
        final String html = "<html>\n"
                + "<body>\n"
                + "  <table>\n"
                + "    <tr id='row1'><td>row1</td></tr>\n"
                + "    <tr id='row2'><td>row2</td></tr>\n"
                + "  </table>\n"
                + "<script>\n"
                + "  var r1 = document.getElementById('row1');\n"
                + "  var r2 = document.getElementById('row2');\n"
                + "  alert(r2.offsetTop > r1.offsetTop);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetTopListItems() {
        final String html = "<html>\n"
                + "<body>\n"
                + "  <ul>\n"
                + "    <li id='li1'>row1</li>\n"
                + "    <li id='li2'>row2</li>\n"
                + "  </ul>\n"
                + "<script>\n"
                + "  var li1 = document.getElementById('li1');\n"
                + "  var li2 = document.getElementById('li2');\n"
                + "  alert(li2.offsetTop > li1.offsetTop);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetLeftAfterTable() {
        final String html = "<html>\n"
                + "<body>\n"
                + "  <table>\n"
                + "    <tr><td>abcdefghijklmnopqrstuvwxyz</td></tr>\n"
                + "  </table>\n"
                + "  <div id='mydiv'>Heho</div>\n"
                + "<script>\n"
                + "  var d1 = document.getElementById('mydiv');\n"
                + "  alert(d1.offsetLeft < 10);\n"
                + "</script>\n"
                + "</body>\n"
                + "</html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void custom() {
        final String html = "<html><head>\n"
                + "<style>\n"
                + "  .abc { xyz: 1 }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('mydiv');\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "    alert(style.xyz);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv' class='abc'></div>\n"
                + "</body></html>";
        final String[] messages = {"undefined"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void selector() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    alert(document.querySelectorAll('div *').length);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='mydiv'>\n"
                + "    <p>p</p>\n"
                + "  </div>\n"
                + "</body></html>";
        final String[] messages = {"1"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void marginLeftRight() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    var container = document.createElement('div');\n"
                + "    container.style.width = '400px';\n"
                + "    div1.appendChild(container);\n"
                + "    alert(container.style.marginRight);\n"
                + "    alert(window.getComputedStyle(container, null).marginRight);\n"
                + "\n"
                + "    var el = document.createElement('div');\n"
                + "    el.style.width = '10%';\n"
                + "    el.style.marginRight = '20%';\n"
                + "    container.appendChild(el);\n"
                + "    alert(el.style.marginRight);\n"
                + "    alert(window.getComputedStyle(el, null).marginRight);\n"
                + "\n"
                + "    el = document.createElement('div');\n"
                + "    el.style.width = '30%';\n"
                + "    el.style.minWidth = '300px';\n"
                + "    el.style.marginLeft = '25%';\n"
                + "    container.appendChild(el);\n"
                + "    alert(el.style.marginLeft);\n"
                + "    alert(window.getComputedStyle(el, null).marginLeft);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'></div>\n"
                + "</body></html>\n";
        final String[] messages = {null, "0px", "20%", "80px", "25%", "100px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void topLeft() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    var parent = document.createElement('div');\n"
                + "    parent.style = 'position: relative; width: 200px; height: 200px; margin: 0; padding: 0;"
                + " border-width: 0';\n"
                + "    div1.appendChild(parent);\n"
                + "\n"
                + "    var div = document.createElement('div');\n"
                + "    div.style = 'position: absolute; width: 20px; height: 20px; top: 50%; left: 50%';\n"
                + "    parent.appendChild(div);\n"
                + "\n"
                + "    alert(parent.style.top);\n"
                + "    alert(window.getComputedStyle(parent, null).top);\n"
                + "    alert(parent.style.left);\n"
                + "    alert(window.getComputedStyle(parent, null).left);\n"
                + "    alert(div.style.top);\n"
                + "    alert(window.getComputedStyle(div, null).top);\n"
                + "    alert(div.style.left);\n"
                + "    alert(window.getComputedStyle(div, null).left);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'></div>\n"
                + "</body></html>\n";
        final String[] messages = {null, "0px",null, "0px", "50%", "100px", "50%", "100px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void borderBoxAffectsOffsetWidth() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"
                + "    var empty = getOffsetWidth('width: 300px; height: 300px;');\n"
                + "    var marginAndPadding = getOffsetWidth('width: 300px; height: 300px; margin: 3px; padding: 5px;');\n"
                + "    var withBorderBox = getOffsetWidth('width: 300px; height: 300px; margin: 3px; padding: 5px;"
                + " box-sizing: border-box;');\n"
                + "    alert(marginAndPadding - empty);\n"
                + "    alert(withBorderBox - empty);\n"
                + "  }\n"
                + "  function getOffsetWidth(style) {\n"
                + "    var d = document.createElement('div');\n"
                + "    d.appendChild(document.createTextNode('test'));\n"
                + "    d.style = style;\n"
                + "    div1.appendChild(d);\n"
                + "    var offsetWidth = d.offsetWidth;\n"
                + "    div1.removeChild(d);\n"
                + "    return offsetWidth;\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'></div>\n"
                + "</body>\n"
                + "</html>\n";
        final String[] messages = {"10", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void borderBoxAffectsOffsetHeight() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<script>\n"

                + "  function test() {\n"
                + "    var div1 = document.getElementById('div1');\n"

                + "    var empty = getOffsetHeight('width: 300px; height: 300px;');\n"
                + "    var marginAndPadding = getOffsetHeight('width: 300px; height: 300px; margin: 3px; padding: 5px;');\n"
                + "    var withBorderBox = getOffsetHeight('width: 300px; height: 300px; margin: 3px; padding: 5px;"
                + " box-sizing: border-box;');\n"
                + "    alert(marginAndPadding - empty);\n"
                + "    alert(withBorderBox - empty);\n"
                + "  }\n"
                + "  function getOffsetHeight(style) {\n"
                + "    var d = document.createElement('div');\n"
                + "    d.appendChild(document.createTextNode('test'));\n"
                + "    d.style = style;\n"
                + "    div1.appendChild(d);\n"
                + "    var offsetHeight = d.offsetHeight;\n"
                + "    div1.removeChild(d);\n"
                + "    return offsetHeight;\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'></div>\n"
                + "</body>\n"
                + "</html>\n";
        final String[] messages = {"10", "0"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetWidthWithDisplayInline() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var div = document.createElement('div');\n"
                + "    document.body.appendChild(div);\n"
                + "    div.style.cssText = 'display: inline; margin:0; border: 0; padding: 5px; width: 7px';\n"
                + "    alert(div.offsetWidth);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>\n";
        final String[] messages = {"10"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void borderBoxAffectsOffsetWidth2() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var divNormal = document.createElement('div');\n"
                + "    divNormal.style = 'box-sizing: border-box; width: 100px; height: 100px; border: 10px solid white;"
                + " padding: 2px; margin: 3px';\n"
                + "    document.body.appendChild(divNormal);\n"
                + "\n"
                + "   if (window.navigator.userAgent.indexOf('Trident/') == -1) {\n"
                + "     alert(divNormal.offsetWidth);\n"
                + "   } else {\n"
                + "     alert(divNormal.offsetWidth == window.innerWidth - 16);\n"
                + "   }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>\n";
        final String[] messages = {"100"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void borderBoxAffectsOffsetHeight2() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var divNormal = document.createElement('div');\n"
                + "    divNormal.style = 'box-sizing: border-box; width: 100px; height: 100px; border: 10px solid white;"
                + " padding: 2px; margin: 3px';\n"
                + "    document.body.appendChild(divNormal);\n"
                + "\n"
                + "   if (window.navigator.userAgent.indexOf('Trident/') == -1) {\n"
                + "     alert(divNormal.offsetHeight);\n"
                + "   } else {\n"
                + "     alert(divNormal.offsetHeight);\n"
                + "   }\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>\n";
        final String[] messages = {"100"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void bodyOffsetWidth() {
        final String html = "<html><head><script>\n"
                + "  function test() {\n"
                + "    var win = window.innerWidth;\n"
                + "    var html = document.documentElement.offsetWidth;\n"
                + "    var body = document.body.offsetWidth;\n"
                + "    alert(window.getComputedStyle(document.body, null).margin);\n"
                + "    alert(win - html);\n"
                + "    alert(win - body);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "</body>\n"
                + "</html>\n";
        final String[] messages = {"", "0", "16"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void offsetHeightTable() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var table = document.createElement('table');\n"
                + "    table.style.fontSize = '16px';\n"
                + "    document.getElementById('myDiv').appendChild(table);\n"
                + "    alert(table.offsetHeight);\n"
                + "\n"
                + "    var tr = document.createElement('tr');\n"
                + "    table.appendChild(tr);\n"
                + "    var td = document.createElement('td');\n"
                + "    tr.appendChild(td);\n"
                + "    td.appendChild(document.createTextNode('DATA'));\n"
                + "    alert(table.offsetHeight);\n"
                + "  }\n"
                + "</script>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'></div>\n"
                + "</body></html>\n";
        final String[] messages = {"0", "24"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void height() {
        final String html = "<html>\n"
                + "<head>\n"
                + "  <style>\n"
                + "    .autoheight {\n"
                + "      height: auto;\n"
                + "    }\n"
                + "  </style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('myDiv');\n"
                + "    var style = window.getComputedStyle(div, null);\n"
                + "    alert(style.height == '0px');\n"
                + "    div.className = 'autoheight';\n"
                + "    alert(style.height == '0px');\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv'>A</div>\n"
                + "</body></html>\n";
        final String[] messages = {"false", "false"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void heightManyLines() {
        final String html = "<html>\n"
                + "<head><script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('test1');\n"
                + "    alert(div.offsetHeight);\n"
                + "    alert(window.getComputedStyle(div, null).height);\n"
                + "    div = document.getElementById('test2');\n"
                + "    alert(div.offsetHeight);\n"
                + "    alert(window.getComputedStyle(div, null).height);\n"
                + "    div = document.getElementById('test3');\n"
                + "    alert(div.offsetHeight);\n"
                + "    alert(window.getComputedStyle(div, null).height);\n"
                + "  }\n"
                + "</script></head>\n"
                + "<body onload='test()'>\n"
                + "  <div id=\"test1\">This is a long string of text.</div>\n"
                + "  <div id=\"test2\">This is a long string of text.<br>Some more text<br></div>\n"
                + "  <div id=\"test3\">This is a long string of text.<br>Some more text<br>and some more...</div>\n"
                + "</body></html>\n";
        final String[] messages = {"18", "18px", "36", "36px", "54", "54px"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void getHeightInvisible() {
        final String html = "<html><head>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var node = document.getElementById('outer');\n"
                + "    var style = node.style;\n"
                + "    alert(style.height);\n"
                + "    var style = window.getComputedStyle(node, null);\n"
                + "    alert(style.height);\n" + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='outer' style='display: none'>\n"
                + "    <div>line 1</div>\n"
                + "    <div>line 2</div>\n"
                + "  </div>\n"
                + "</body></html>";
        final String[] messages = {null, "auto"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void pseudoBefore() {
        final String html = "<html><head>\n"
                + "<style type='text/css'>\n"
                + "  /* <![CDATA[ */\n"
                + "  #myDiv:before { content: '@' }\n"
                + "  #myDiv2::before { content: '#' }\n"
                + "  /* ]]> */\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var node = document.getElementById('myDiv');\n"
                + "    var style = window.getComputedStyle(node, ':before');\n"
                + "    alert(style.content);\n"
                + "    var style = window.getComputedStyle(node, 'before');\n"
                + "    alert(style.content);\n"
                + "    var style = window.getComputedStyle(node, '::before');\n"
                + "    alert(style.content);\n"
                + "    node = document.getElementById('myDiv2');\n"
                + "    var style = window.getComputedStyle(node, ':before');\n"
                + "    alert(style.content);\n"
                + "    var style = window.getComputedStyle(node, 'before');\n"
                + "    alert(style.content);\n"
                + "    var style = window.getComputedStyle(node, '::before');\n"
                + "    alert(style.content);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='myDiv' >Test</div>\n"
                + "  <div id='myDiv2' >Test</div>\n"
                + "</body></html>";
        final String[] messages = {"\"@\"", "\"@\"", "\"@\"", "\"#\"", "\"#\"", "\"#\""};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void calculateContentHeightAfterSVG() {
        final String html =
                "<html><body>\n"
                        + "<svg/>\n"
                        + "<img />\n"
                        + "<textarea id='myTextarea' cols='120' rows='20'></textarea>\n"
                        + "<script>\n"
                        + "  alert(document.body.offsetHeight > 10);\n"
                        + "</script>\n"
                        + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void combineStyles() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style type='text/css'>\n"
                + "  div { margin: 10px 20px 30px 40px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('div1');\n"
                + "    var left = div.style.marginLeft;\n" // force the resolution
                + "    alert(div.offsetLeft);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1' style='margin-left: 123px'></div>\n"
                + "</body></html>\n";
        final String[] messages = {"131"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void combineStylesImportant() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style type='text/css'>\n"
                + "  div { margin: 10px 20px 30px 40px !important; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('div1');\n"
                + "    var left = div.style.marginLeft;\n" // force the resolution
                + "    alert(div.offsetLeft);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1' style='margin-left: 123px'></div>\n"
                + "</body></html>\n";
        final String[] messages = {"48"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void combineStylesBrowserDefaults() {
        final String html = "<html>\n"
                + "<head>\n"
                + "<style type='text/css'>\n"
                + "  body { margin: 3px; }\n"
                + "  div { margin: 20px; }\n"
                + "</style>\n"
                + "<script>\n"
                + "  function test() {\n"
                + "    var div = document.getElementById('div1');\n"
                + "    var left = div.style.marginLeft;\n" // force the resolution
                + "    alert(div.offsetLeft);\n"
                + "  }\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + "  <div id='div1'></div>\n"
                + "</body></html>\n";
        final String[] messages = {"23"};
        checkHtmlAlert(html, messages);
    }

    @Test
    public void boundingClientRectIgnoreSiblingWhitespace() {
        final String html = "<html><body>\n"
                + "<table>\n"
                + "<tr>\n"
                + "  <td>  \n\t    <div id='a'>a</div></td>\n"
                + "</tr>\n"
                + "</table>\n"
                + "<script>\n"
                + "  var e = document.getElementById('a');\n"
                + "  alert(e.getBoundingClientRect().left < 12);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }


    @Test
    public void boundingClientRectTopOnlyHasToTakeCareOfPreviousBlockSibling() {
        final String html = "<html><body>\n"
                + "<div style='height: 100'>100</div>\n"
                + "<span style='height: 200'>200</span>\n"
                + "<span id='tester'>tester</span>\n"
                + "<script>\n"
                + "  var e = document.getElementById('tester');\n"
                + "  alert(e.getBoundingClientRect().top < 120);\n"
                + "</script>\n"
                + "</body></html>";
        final String[] messages = {"true"};
        checkHtmlAlert(html, messages);
    }
}
