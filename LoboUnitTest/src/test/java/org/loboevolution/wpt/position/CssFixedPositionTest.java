package org.loboevolution.wpt.position;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loboevolution.annotation.Alerts;
import org.loboevolution.annotation.AlertsExtension;
import org.loboevolution.driver.LoboUnitTest;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RBlock;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AlertsExtension.class)
public class CssFixedPositionTest extends LoboUnitTest {

    @Test
    @Alerts({"true"})
    public void fixedPositionWidthAutoShrinkToFit() {
        final String html =
                "<!DOCTYPE html>"
                        + "<html>"
                        + " <head>"
                        + "  <style>"
                        + "    #fixed { position: fixed; top: 10px; left: 10px; background: red; }"
                        + "  </style>"
                        + " </head>"
                        + " <body>"
                        + "  <div id='fixed'>Short</div>"
                        + " <script>"
                        + "  var el = document.getElementById('fixed');"
                        + "  alert(el.offsetWidth < 500);"
                        + " </script>"
                        + " </body>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void fixedPositionWidthExplicit() {
        final String html =
                "<!DOCTYPE html>"
                        + "<html>"
                        + " <head>"
                        + "  <style>"
                        + "    #fixed { position: fixed; width: 200px; top: 10px; left: 10px; background: red; }"
                        + "  </style>"
                        + " </head>"
                        + " <body>"
                        + "  <div id='fixed'>Content</div>"
                        + " <script>"
                        + "  var el = document.getElementById('fixed');"
                        + "  alert(el.offsetWidth >= 200);"
                        + "  alert(el.offsetWidth <= 220);"
                        + " </script>"
                        + " </body>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true"})
    public void fixedPositionWidthPercent() {
        final String html =
                "<!DOCTYPE html>"
                        + "<html>"
                        + " <head>"
                        + "  <style>"
                        + "    #fixed { position: fixed; width: 50%; top: 10px; left: 10px; background: red; }"
                        + "  </style>"
                        + " </head>"
                        + " <body>"
                        + "  <div id='fixed'>Content</div>"
                        + " <script>"
                        + "  var el = document.getElementById('fixed');"
                        + "  var vp = document.documentElement.clientWidth;"
                        + "  alert(Math.abs(el.offsetWidth - vp * 0.5) < 10);"
                        + " </script>"
                        + " </body>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true", "true"})
    public void fixedPositionContentGrowsWidth() {
        final String html =
                "<!DOCTYPE html>"
                        + "<html>"
                        + " <head>"
                        + "  <style>"
                        + "    #fixed { position: fixed; top: 10px; left: 10px; background: red; white-space: nowrap; }"
                        + "  </style>"
                        + " </head>"
                        + " <body>"
                        + "  <div id='fixed'>Short</div>"
                        + "  <div id='fixed2' style='position:fixed; top:40px; left:10px; background:blue; white-space:nowrap;'>Much longer content that should make the element wider</div>"
                        + " <script>"
                        + "  var el1 = document.getElementById('fixed');"
                        + "  var el2 = document.getElementById('fixed2');"
                        + "  alert(el2.offsetWidth > el1.offsetWidth);"
                        + "  alert(el1.offsetWidth < 500);"
                        + " </script>"
                        + " </body>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts({"true"})
    public void absolutePositionWidthAutoShrinkToFit() {
        final String html =
                "<!DOCTYPE html>"
                        + "<html>"
                        + " <head>"
                        + "  <style>"
                        + "    #abs { position: absolute; top: 10px; left: 10px; background: red; }"
                        + "  </style>"
                        + " </head>"
                        + " <body>"
                        + "  <div id='abs'>Short</div>"
                        + " <script>"
                        + "  var el = document.getElementById('abs');"
                        + "  alert(el.offsetWidth < 500);"
                        + " </script>"
                        + " </body>"
                        + "</html>";
        checkHtmlAlert(html);
    }

    @Test
    @Alerts("true")
    public void fixedWidthPercentageUsesViewport() {
        final String html = "<html><head></head><body>\n"
                + "<div style='width: 200px'>\n"
                + "  <div id='fixed' style='position: fixed; width: 100%'></div>\n"
                + "</div>\n"
                + "<script>\n"
                + "  var fixed = document.getElementById('fixed');\n"
                + "  alert(parseInt(window.getComputedStyle(fixed, null).width, 10) == window.innerWidth);\n"
                + "</script>\n"
                + "</body></html>";
        checkHtmlAlert(html);
    }

    @Test
    public void fixedPositionRendererHeightAutoShrinkToFit() throws Exception {
        final String html =
                "<!DOCTYPE html>"
                        + "<html><body>"
                        + "<div id='fixed' style='position:fixed; top:10px; left:10px; background:red'>Short</div>"
                        + "</body></html>";
        final HTMLDocumentImpl document = loadHtml(html);
        final HtmlRendererContext rendererContext = document.getHtmlRendererContext();
        final HtmlPanel panel = rendererContext.getHtmlPanel();
        SwingUtilities.invokeAndWait(() -> {
            panel.setSize(1600, 900);
            panel.setDocument(document, rendererContext);
            panel.doLayout();
        });
        final HTMLElementImpl fixed = (HTMLElementImpl) document.getElementById("fixed");
        final RBlock renderable = (RBlock) fixed.getUINode();
        assertNotNull(renderable);
        assertTrue(renderable.getHeight() < 500,
                "fixed renderer height should shrink to its content");
    }

    @Test
    public void fixedPositionRendererWidthAutoShrinkToFit() throws Exception {
        final String html =
                "<!DOCTYPE html>"
                        + "<html><body>"
                        + "<div id='fixed' style='position:fixed; top:10px; left:10px; background:red'>Short</div>"
                        + "</body></html>";
        final HTMLDocumentImpl document = loadHtml(html);
        final HtmlRendererContext rendererContext = document.getHtmlRendererContext();
        final HtmlPanel panel = rendererContext.getHtmlPanel();
        SwingUtilities.invokeAndWait(() -> {
            panel.setSize(1600, 900);
            panel.setDocument(document, rendererContext);
            panel.doLayout();
        });
        final HTMLElementImpl fixed = (HTMLElementImpl) document.getElementById("fixed");
        final RBlock renderable = (RBlock) fixed.getUINode();
        assertNotNull(renderable);
        assertTrue(renderable.getWidth() < 500,
                "fixed renderer width should shrink to its content");
    }

}