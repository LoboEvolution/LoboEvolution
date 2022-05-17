/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

/**
 * <p>ComputedCSSStyleDeclaration class.</p>
 */

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
        super(element, style.getStyleDeclarations() == null ? new com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl() : style.getStyleDeclarations().get(0));
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
        if (c != null) {
            final float alpha = (float) (c.getAlpha()) / 255.0f;
            if (alpha > 0 && alpha < 1) {
                return "rgba(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + alpha + ")";
            } else {
                return "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
            }
        } else {
            return "rgb(0, 0, 0)";
        }
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
        if (Strings.isBlank(style.getColor())) {
            return "rgb(0, 0, 0)";
        }
        final Color c = ColorFactory.getInstance().getColor(style.getColor());
        if (c != null) {
            final float alpha = (float) (c.getAlpha()) / 255.0f;
            if (alpha > 0 && alpha < 1) {
                return "rgba(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + alpha + ")";
            } else {
                return "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
            }
        } else {
            return "rgb(0, 0, 0)";
        }
    }

    public String getCssFloat() {
        return HtmlValues.getPixelSize(style.getCssFloat(), renderState, window, 0) + "px";
    }

    public String getCursor() {
        final RenderState rs = element.getRenderState();
        return rs.getCursor().get().getType() == 0 ? "auto" : style.getCursor();
    }

    public String getDisplay() {
        final String cssDisplay = style.getDisplay();
        if (Strings.isNotBlank(cssDisplay) && !"null".equals(cssDisplay)) {
            return cssDisplay;
        } else {
            final RenderState rs = element.getRenderState();
            CSSValues display;
            if (rs == null) {
                return null;
            } else {
                switch (rs.getDefaultDisplay()) {
                    case RenderState.DISPLAY_BLOCK:
                        display = CSSValues.BLOCK;
                        break;
                    case RenderState.DISPLAY_NONE:
                        display = CSSValues.NONE;
                        break;
                    case RenderState.DISPLAY_LIST_ITEM:
                        display = CSSValues.LIST_ITEM;
                        break;
                    case RenderState.DISPLAY_TABLE:
                        display = CSSValues.TABLE;
                        break;
                    case RenderState.DISPLAY_TABLE_CELL:
                        display = CSSValues.TABLE_CELL;
                        break;
                    case RenderState.DISPLAY_TABLE_ROW:
                        display = CSSValues.TABLE_ROW;
                        break;
                    case RenderState.DISPLAY_TABLE_CAPTION:
                        display = CSSValues.TABLE_CAPTION;
                        break;
                    case RenderState.DISPLAY_TABLE_COLUMN:
                        display = CSSValues.TABLE_COLUMN;
                        break;
                    case RenderState.DISPLAY_TABLE_COLUMN_GROUP:
                        display = CSSValues.TABLE_COLUMN_GROUP;
                        break;
                    case RenderState.DISPLAY_INLINE_BLOCK:
                        display = CSSValues.INLINE_BLOCK;
                        break;
                    case RenderState.DISPLAY_INLINE_TABLE:
                        display = CSSValues.INLINE_TABLE;
                        break;
                    case RenderState.DISPLAY_FLEX_BOX:
                        display = CSSValues.FLEX;
                        break;
                    default:
                        display = CSSValues.INLINE;
                        break;
                }
            }
            return display.getValue();
        }
    }

     public String getFont() {
        StringBuilder font = new StringBuilder();
        final String fontStyle = style.getFontStyle();
        final String lineHeight = getLineHeight();
        if (Strings.isNotBlank(fontStyle) && !fontStyle.equals(CSSValues.NORMAL.getValue())) font.append(CSSValues.ITALIC.getValue() + " ");
        font.append(getFontSize());
        if(Strings.isNotBlank(lineHeight) && !lineHeight.equals(CSSValues.NORMAL.getValue())) {
            font.append(" / " + lineHeight);
        }
        font.append( " " + getFontFamily());
        return font.toString().trim();
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
            fontSize = Float.valueOf(FontValues.getFontSize(style.getFontSize(), doc.getDefaultView(), null)).intValue();
        }

        return fontSize + "px";
    }

    public String getFontStyle() {
        final String fontStyle = style.getFontStyle();
        if (Strings.isNotBlank(fontStyle) && !fontStyle.equals(CSSValues.NORMAL.getValue())) return CSSValues.ITALIC.getValue();
        return CSSValues.NORMAL.getValue();
    }

    public String getFontVariant() {
        return FontValues.getFontVariant(style.getFontWeight());
    }

    public String getFontWeight() { return FontValues.getFontWeight(style.getFontWeight(), null); }

    public String getFontFamily() {
        return FontValues.getFontFamily(style.getFontFamily(), null);
    }

    public String getLineHeight() {
        return Strings.isBlank(style.getLineHeight()) ? CSSValues.NORMAL.getValue() : style.getLineHeight();
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