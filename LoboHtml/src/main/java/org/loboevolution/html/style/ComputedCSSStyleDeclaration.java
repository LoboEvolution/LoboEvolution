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
 * */

package org.loboevolution.html.style;

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;

public class ComputedCSSStyleDeclaration extends CSSStyleDeclarationImpl {

    private final HTMLElementImpl element;

    private final RenderState renderState;

    private final Window window;

    private final AbstractCSSProperties style;

    /**
     * <p>Constructor for AbstractCSSProperties.</p>
     *
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     **/
    public ComputedCSSStyleDeclaration(HTMLElementImpl element, AbstractCSSProperties style) {
        super(style.getStyleDeclarations() == null ? new com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl() : style.getStyleDeclarations().get(0));
        this.element = element;
        this.renderState = element.getRenderState();
        this.window = element.getDocumentNode().getDefaultView();
        this.style = style;
    }

    public String getBackgroundAttachment() {
        return style.getBackgroundAttachment();
    }

    public String getBackgroundColor() {
        if (Strings.isBlank(style.getBackgroundColor())) {
            return "rgb(0, 0, 0)";
        }
        final Color c = ColorFactory.getInstance().getColor(style.getBackgroundColor());
        return "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";
    }

    public String getBackgroundImage() {
        return style.getBackgroundImage();
    }

    public String getBackgroundPosition() {
        return style.getBackgroundPosition();
    }

    public String getBackgroundRepeat() {
        return style.getBackgroundRepeat();
    }

    public String getBorderBottomColor() {
        return style.getBorderBottomColor();
    }

    public String getBorderBottomStyle() {
        return style.getBorderBottomStyle();
    }

    public String getBorderBottomWidth() {
        return HtmlValues.getPixelSize(style.getBorderBottomWidth(), renderState, window, 0) + "px";
    }

    public String getBorderLeftColor() {
        return style.getBorderLeftColor();
    }

    public String getBorderLeftStyle() {
        return style.getBorderLeftStyle();
    }

    public String getBorderLeftWidth() {
        return HtmlValues.getPixelSize(style.getBorderLeftWidth(), renderState, window, 0) + "px";
    }

    public String getBorderRightColor() {
        return Strings.isBlank(style.getBorderRightColor()) ? "rgb(0, 0, 0)" : style.getBorderRightColor();
    }

    public String getBorderRightStyle() {
        return Strings.isBlank(style.getBorderRightStyle()) ? CSSValues.NONE.getValue() : style.getBorderRightStyle();
    }

    public String getBorderRightWidth() {
        return HtmlValues.getPixelSize(style.getBorderRightWidth(), element.getRenderState(), element.getDocumentNode().getDefaultView(), 0) + "px";
    }

    public String getBorderTopColor() {
        return Strings.isBlank(style.getBorderTopColor()) ? "rgb(0, 0, 0)" : style.getBorderTopColor();
    }

    public String getBorderTopStyle() {
        return Strings.isBlank(style.getBorderTopStyle()) ? CSSValues.NONE.getValue() : style.getBorderTopStyle();
    }

    public String getBorderTopWidth() {
        return HtmlValues.getPixelSize(style.getBorderTopWidth(), renderState, window, 0) + "px";
    }

    public String getBottom() {
        return Strings.isBlank(style.getBottom()) ? CSSValues.AUTO.getValue() : style.getBottom();
    }

    public String getColor() {
        return style.getColor();
    }

    public String getCssFloat() {
        return HtmlValues.getPixelSize(style.getCssFloat(), renderState, window, 0) + "px";
    }

    public String getDisplay() {
        final String value = style.getDisplay();
        if (Strings.isBlank(value)) {
            final RenderState rs = element.getRenderState();
            switch (rs.getDisplay()) {
                case RenderState.DISPLAY_NONE:
                    return CSSValues.NONE.getValue();
                case RenderState.DISPLAY_BLOCK:
                    return CSSValues.BLOCK.getValue();
                case RenderState.DISPLAY_LIST_ITEM:
                    return CSSValues.LIST_ITEM.getValue();
                case RenderState.DISPLAY_TABLE_ROW:
                    return CSSValues.TABLE_ROW.getValue();
                case RenderState.DISPLAY_TABLE_CELL:
                    return CSSValues.TABLE_CELL.getValue();
                case RenderState.DISPLAY_TABLE:
                    return CSSValues.TABLE.getValue();
                case RenderState.DISPLAY_TABLE_CAPTION:
                    return CSSValues.TABLE_CAPTION.getValue();
                case RenderState.DISPLAY_TABLE_COLUMN:
                    return CSSValues.TABLE_COLUMN.getValue();
                case RenderState.DISPLAY_TABLE_COLUMN_GROUP:
                    return CSSValues.TABLE_COLUMN_GROUP.getValue();
                case RenderState.DISPLAY_TABLE_ROW_GROUP:
                    return CSSValues.TABLE_ROW_GROUP.getValue();
                case RenderState.DISPLAY_TABLE_HEADER_GROUP:
                    return CSSValues.TABLE_HEADER_GROUP.getValue();
                case RenderState.DISPLAY_TABLE_FOOTER_GROUP:
                    return CSSValues.TABLE_FOOTER_GROUP.getValue();
                case RenderState.DISPLAY_INLINE_BLOCK:
                    return CSSValues.INLINE_BLOCK.getValue();
                case RenderState.DISPLAY_INLINE_TABLE:
                    return CSSValues.INLINE_TABLE.getValue();
                case RenderState.DISPLAY_INLINE:
                default:
                    return CSSValues.INLINE.getValue();
            }
        }
        return value;
    }

    public String getFont() {
        return style.getFont();
    }

    public String getFontSize() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
        HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();
        int fontSize = -1;
        if (parent != null) {
            AbstractCSSProperties currentStyle = parent.getCurrentStyle();
            fontSize = FontValues.getPixelSize(currentStyle.getFontSize(), null, doc.getDefaultView(), -1);
        }

        if(fontSize == -1){
            fontSize = FontValues.getPixelSize(style.getFontSize(), renderState, window, 0);
        }

        return fontSize + "px";
    }

    public String getLineHeight() {
        return style.getLineHeight();
    }

    public String getFontFamily() {
        return style.getFontFamily();
    }

    public String getHeight() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
        HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();
        int availSize = -1;
        if (parent != null) {
            AbstractCSSProperties currentStyle = parent.getCurrentStyle();
            availSize = HtmlValues.getPixelSize(currentStyle.getHeight(), null, doc.getDefaultView(), -1);
        }

        if (availSize == -1) {
            HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
            HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
            Dimension preferredSize = htmlPanel.getPreferredSize();
            availSize = preferredSize.height;
        }
        final int height = HtmlValues.getPixelSize(style.getHeight(), null, doc.getDefaultView(), -1, availSize);
        return height + "px";
    }

    public String getLeft() {
        return Strings.isBlank(style.getLeft()) ? "" : HtmlValues.getPixelSize(style.getLeft(), renderState, window, 0) + "px";
    }

    public String getLetterSpacing() {
        return Strings.isBlank(style.getLetterSpacing()) ? CSSValues.NORMAL.getValue() : style.getLetterSpacing();
    }

    public String getMargin() {
        return Strings.isBlank(style.getMargin()) ? CSSValues.MARGIN.getValue() : style.getMargin();
    }

    public String getMarginBottom() {
        return HtmlValues.getPixelSize(style.getMarginBottom(), renderState, window, 0) + "px";
    }

    public String getMarginLeft() {
        return HtmlValues.getPixelSize(style.getMarginLeft(), renderState, window, 0) + "px";
    }

    public String getMarginRight() {
        return HtmlValues.getPixelSize(style.getMarginRight(), renderState, window, 0) + "px";
    }

    public String getMarginTop() {
        return HtmlValues.getPixelSize(style.getMarginTop(), renderState, window, 0) + "px";
    }

    public String getMaxHeight() {
        return Strings.isBlank(style.getMaxHeight()) ? CSSValues.NONE.getValue() : style.getMaxHeight();
    }

    public String getMaxWidth() {
        return Strings.isBlank(style.getMaxWidth()) ? CSSValues.NONE.getValue() : style.getMaxWidth();
    }

    public String getMinHeight() {
        return HtmlValues.getPixelSize(style.getMinHeight(), renderState, window, 0) + "px";
    }

    public String getMinWidth() {
        return HtmlValues.getPixelSize(style.getMinWidth(), renderState, window, 0) + "px";
    }

    public String getOpacity() {
        return Strings.isBlank(style.getOpacity()) ? "1" : style.getOpacity();
    }

    public String getOutlineWidth() {
        return HtmlValues.getPixelSize(style.getOutlineWidth(), renderState, window, 0) + "px";
    }

    public String getPadding() {
        return HtmlValues.getPixelSize(style.getPadding(), renderState, window, 0) + "px";
    }

    public String getPaddingBottom() {
        return HtmlValues.getPixelSize(style.getPaddingBottom(), renderState, window, 0) + "px";
    }

    public String getPaddingLeft() {
        return HtmlValues.getPixelSize(style.getPaddingLeft(), renderState, window, 0) + "px";
    }

    public String getPaddingRight() {
        return HtmlValues.getPixelSize(style.getPaddingRight(), renderState, window, 0) + "px";
    }


    public String getPaddingTop() {
        return HtmlValues.getPixelSize(style.getPaddingTop(), renderState, window, 0) + "px";
    }

    public String getRight() {
        return Strings.isBlank(style.getRight()) ? CSSValues.AUTO.getValue() : style.getRight();
    }

    public String getTextIndent() {
        return HtmlValues.getPixelSize(style.getTextIndent(), renderState, window, 0) + "px";
    }

    public String getTop() {
        return Strings.isBlank(style.getTop()) ? "" : HtmlValues.getPixelSize(style.getTop(), renderState, window, 0) + "px";
    }

    public String getVerticalAlign() {
        return Strings.isBlank(style.getVerticalAlign()) ? CSSValues.BASELINE.getValue() : style.getVerticalAlign();
    }

    public String getWidows() {
        return style.getWidows();
    }

    public String getOrphans() {
        return style.getOrphans();
    }


    public String getPosition() {
        return style.getPosition();
    }

    public String getWidth() {
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
        HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();
        int availSize = -1;
        if (parent != null) {
            AbstractCSSProperties currentStyle = parent.getCurrentStyle();
            availSize = HtmlValues.getPixelSize(currentStyle.getWidth(), null, doc.getDefaultView(), -1);
        }

        if (availSize == -1) {
            HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
            HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
            Dimension preferredSize = htmlPanel.getPreferredSize();
            availSize = preferredSize.width;
        }
        final int width = HtmlValues.getPixelSize(style.getWidth(), null, doc.getDefaultView(), -1, availSize);
        return width + "px";
    }

    public String getzIndex() {
        return style.getZIndex();
    }
}