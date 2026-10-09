package org.loboevolution.wpt.inblock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;

@ExtendWith(AlertsExtension.class)
public class Css2InlineBlockWptPortTest extends LoboUnitTest {

    @Test
    @Alerts({"true", "true"})
    public void liInlineBlockHasSize() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>A</li>"
                + "    <li id='b'>B</li>"
                + "    <li id='c'>C</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  alert(a.offsetWidth > 0);"
                + "  alert(a.offsetHeight > 0);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"100", "50"})
    public void liInlineBlockExplicitDimensions() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; width: 100px; height: 50px; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>A</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  alert(a.offsetWidth);"
                + "  alert(a.offsetHeight);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true"})
    public void liInlineBlockWidthGrowsWithContent() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>A</li>"
                + "    <li id='b'>BB</li>"
                + "    <li id='c'>CCC</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var b = document.getElementById('b');"
                + "  var c = document.getElementById('c');"
                + "  alert(a.offsetWidth > 0);"
                + "  alert(b.offsetWidth >= a.offsetWidth);"
                + "  alert(c.offsetWidth >= b.offsetWidth);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"inline-block", "true", "true"})
    public void liInlineBlockComputedDisplay() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>A</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var cs = getComputedStyle(a);"
                + "  alert(cs.display);"
                + "  alert(a.offsetWidth > 0);"
                + "  alert(a.offsetHeight > 0);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"inline-block", "5px", "3px", "2px"})
    public void liInlineBlockPaddingMarginBorder() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; padding: 5px; margin: 3px; border: 2px solid black; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>A</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var cs = getComputedStyle(a);"
                + "  alert(cs.display);"
                + "  alert(cs.paddingTop);"
                + "  alert(cs.marginTop);"
                + "  alert(cs.borderTopWidth);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"inline-block", "block"})
    public void liInlineBlockInlineVsBlockDisplay() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    .inline { display: inline-block; }"
                + "    .block { display: block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='inline1' class='inline'>A</li>"
                + "    <li id='inline2' class='inline'>B</li>"
                + "    <li id='block1' class='block'>C</li>"
                + "    <li id='block2' class='block'>D</li>"
                + "  </ul>"
                + " <script>"
                + "  var inline1 = document.getElementById('inline1');"
                + "  var cs1 = getComputedStyle(inline1);"
                + "  var block1 = document.getElementById('block1');"
                + "  var cs2 = getComputedStyle(block1);"
                + "  alert(cs1.display);"
                + "  alert(cs2.display);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "true", "inline-block"})
    public void liInlineBlockBreadcrumbSizes() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    body { margin: 0; font-family: Arial; }"
                + "    ul.breadcrumb { background: #f5f5f5; padding: 8px 15px; }"
                + "    ul.breadcrumb li { display: inline-block; margin-right: 5px; }"
                + "    li.pull-right { margin-left: 3px; margin-right: 3px; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul class='breadcrumb'>"
                + "    <li id='a'>Last Published: 04-07-2026<span class='divider'>|</span></li>"
                + "    <li id='b'>Version: 5.0</li>"
                + "    <li id='c' class='pull-right'><a href='./'>Lobo Evolution</a></li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var b = document.getElementById('b');"
                + "  var c = document.getElementById('c');"
                + "  alert(a.offsetWidth > 0);"
                + "  alert(a.offsetHeight > 0);"
                + "  alert(b.offsetWidth > 0);"
                + "  alert(b.offsetHeight > 0);"
                + "  var cs = getComputedStyle(a);"
                + "  alert(cs.display);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"inline", "true"})
    public void liInlineBlockDividerSpanDisplay() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; }"
                + "    .divider { }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>Last Published: 04-07-2026<span id='s' class='divider'>|</span></li>"
                + "  </ul>"
                + " <script>"
                + "  var s = document.getElementById('s');"
                + "  var cs = getComputedStyle(s);"
                + "  alert(cs.display);"
                + "  alert(s.offsetWidth > 0);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true"})
    public void liInlineBlockSpanNotOnSameLine() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; }"
                + "    .divider { }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>A<span class='divider'>|</span></li>"
                + "    <li id='b'>B</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var b = document.getElementById('b');"
                + "  var spanA = a.querySelector('span');"
                + "  alert(a.offsetTop === b.offsetTop);"
                + "  alert(spanA.offsetTop >= a.offsetTop);"
                + "  alert(spanA.offsetTop < a.offsetTop + a.offsetHeight);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true"})
    public void liInlineBlockBreadcrumbLineCheck() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    body { margin: 0; font-family: Arial; font-size: 14px; }"
                + "    ul.breadcrumb { background: #f5f5f5; padding: 8px 15px; }"
                + "    ul.breadcrumb li { display: inline-block; margin-right: 5px; }"
                + "    li.pull-right { margin-left: 3px; margin-right: 3px; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul class='breadcrumb'>"
                + "    <li id='a'>Last Published: 04-07-2026<span class='divider'>|</span></li>"
                + "    <li id='b'>Version: 5.0</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var b = document.getElementById('b');"
                + "  var span = document.querySelector('.divider');"
                + "  alert(a.offsetTop === b.offsetTop);"
                + "  alert(span.offsetTop >= a.offsetTop);"
                + "  alert(span.offsetTop < a.offsetTop + a.offsetHeight);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true", "true"})
    public void liInlineBlockSpanVsTextPosition() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>Text<span id='s'>|</span></li>"
                + "  </ul>"
                + " <script>"
                + "  var li = document.getElementById('a');"
                + "  var s = document.getElementById('s');"
                + "  var liRect = li.getBoundingClientRect();"
                + "  var sRect = s.getBoundingClientRect();"
                + "  alert(sRect.top >= liRect.top);"
                + "  alert(sRect.bottom <= liRect.bottom);"
                + "  alert(sRect.left >= liRect.left);"
                + "  alert(sRect.right <= liRect.right);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void liInlineBlockWidthRespectsExplicit() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { display: inline-block; width: 200px; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>Short</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  alert(a.offsetWidth >= 200);"
                + "  alert(a.offsetWidth < 300);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"inline-block", "inline"})
    public void liInlineBlockBreadcrumbSelectorMatching() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    .breadcrumb>li { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul class='breadcrumb'>"
                + "    <li id='a'>Last Published: 04-07-2026<span class='divider'>|</span></li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var s = document.querySelector('.divider');"
                + "  var csLi = getComputedStyle(a);"
                + "  var csSpan = getComputedStyle(s);"
                + "  alert(csLi.display);"
                + "  alert(csSpan.display);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"2", "inline", "true", "true"})
    public void liInlineBlockTwoItemsMultilineSpan() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    .breadcrumb>li { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul class='breadcrumb'>"
                + "    <li id='a'>Last Published: 04-07-2026"
                + "        <span class='divider'>|</span>"
                + "    </li>"
                + "    <li id='b'>Last Published: 04-07-2026"
                + "        <span class='divider'>|</span>"
                + "    </li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var b = document.getElementById('b');"
                + "  var s = a.querySelector('.divider');"
                + "  var cs = getComputedStyle(s);"
                + "  var children = a.childNodes;"
                + "  alert(children.length);"
                + "  alert(cs.display);"
                + "  alert(s.offsetWidth > 0);"
                + "  alert(a.offsetTop === b.offsetTop);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void liInlineBlockDivGetBoundingClientRect() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    .ib { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <div>"
                + "    <div id='d1' class='ib'>A</div>"
                + "    <div id='d2' class='ib'>B</div>"
                + "  </div>"
                + " <script>"
                + "  var d1 = document.getElementById('d1');"
                + "  var d2 = document.getElementById('d2');"
                + "  var r1 = d1.getBoundingClientRect();"
                + "  var r2 = d2.getBoundingClientRect();"
                + "  alert(r1.top === r2.top);"
                + "  alert(r1.left < r2.left);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true", "true"})
    public void liInlineBlockDivVsLiLayout() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    .ib { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <div>"
                + "    <div id='d1' class='ib'>A</div>"
                + "    <div id='d2' class='ib'>B</div>"
                + "  </div>"
                + "  <ul>"
                + "    <li id='l1' class='ib'>A</li>"
                + "    <li id='l2' class='ib'>B</li>"
                + "  </ul>"
                + " <script>"
                + "  var d1 = document.getElementById('d1');"
                + "  var d2 = document.getElementById('d2');"
                + "  var l1 = document.getElementById('l1');"
                + "  var l2 = document.getElementById('l2');"
                + "  alert(d1.offsetTop === d2.offsetTop);"
                + "  alert(d1.offsetLeft < d2.offsetLeft);"
                + "  alert(l1.offsetTop === l2.offsetTop);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"false", "false"})
    public void liFloatLeftHorizontalLayout() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    li { float: left; margin-right: 5px; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul>"
                + "    <li id='a'>A</li>"
                + "    <li id='b'>B</li>"
                + "  </ul>"
                + " <script>"
                + "  var a = document.getElementById('a');"
                + "  var b = document.getElementById('b');"
                + "  alert(a.offsetTop === b.offsetTop);"
                + "  alert(a.offsetLeft < b.offsetLeft);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void liInlineBlockSpanTextNodeWhiteSpace() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    .breadcrumb>li { display: inline-block; }"
                + "    .divider { white-space: nowrap; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul class='breadcrumb'>"
                + "    <li id='a'>Last Published: 04-07-2026"
                + "        <span class='divider'>|</span>"
                + "    </li>"
                + "  </ul>"
                + " <script>"
                + "  var li = document.getElementById('a');"
                + "  var s = document.querySelector('.divider');"
                + "  var liBottom = li.offsetTop + li.offsetHeight;"
                + "  alert(s.offsetTop >= li.offsetTop);"
                + "  alert(s.offsetTop + s.offsetHeight <= liBottom);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"16", "17"})
    public void inlineBlockSpanOffset() {
        final String html =
                "<style>" +
                " .breadcrumb>li { display: inline-block; }" +
                " </style>" +
                " <ul id='ul' class='breadcrumb'>" +
                " <li id='publishDate'>Last Published: 04-07-2026" +
                " <span class='divider'>|</span>" +
                " </li>" +
                " <li id='publishDate2'>Last Published: 04-07-2026" +
                "        <span class='divider'>|</span>" +
                "    </li>" +
                " </ul>" +
                " <script>" +
                "  var li = document.getElementById('ul');" +
                "  alert(li.offsetTop);" +
                "  alert(li.offsetHeight);" +
                " </script>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void inlineBlockSpanSameLine() {
        final String html =
                "<!DOCTYPE html>"
                + "<html>"
                + " <head>"
                + "  <style>"
                + "    .breadcrumb>li { display: inline-block; }"
                + "  </style>"
                + " </head>"
                + " <body>"
                + "  <ul class='breadcrumb'>"
                + "    <li id='a'>Last Published: 04-07-2026"
                + "        <span id='s' class='divider'>|</span>"
                + "    </li>"
                + "  </ul>"
                + " <script>"
                + "  var li = document.getElementById('a');"
                + "  var s = document.getElementById('s');"
                + "  var liRect = li.getBoundingClientRect();"
                + "  var sRect = s.getBoundingClientRect();"
                + "  alert(sRect.top >= liRect.top);"
                + "  alert(sRect.bottom <= liRect.bottom);"
                + " </script>"
                + " </body>"
                + "</html>";
        checkHtmlAlert(html);
    }
}
