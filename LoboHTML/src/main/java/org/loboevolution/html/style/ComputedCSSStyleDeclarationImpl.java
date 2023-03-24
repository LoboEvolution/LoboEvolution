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
 * */

package org.loboevolution.html.style;

import org.loboevolution.common.Strings;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.node.css.ComputedCSSStyleDeclaration;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;

/**
 * <p>ComputedCSSStyleDeclaration class.</p>
 */
public class ComputedCSSStyleDeclarationImpl implements ComputedCSSStyleDeclaration {

    private final HTMLElementImpl element;

    private final RenderState renderState;

    private final Window window;

    private CSSStyleDeclaration style;

    private int availHeight = -1;

    private int availWidth = -1;


    /**
     * <p>Constructor for AbstractCSSProperties.</p>
     *
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     **/

    public ComputedCSSStyleDeclarationImpl(HTMLElementImpl element, CSSStyleDeclaration style) {
        this.element = element;
        this.renderState = element.getRenderState();        
        this.style = style;

        final HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
        this.window = doc.getDefaultView();
        
        final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
        final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
        final HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();

        if (parent != null) {
            final CSSStyleDeclaration currentStyle = parent.getStyle();
            availHeight = HtmlValues.getPixelSize(currentStyle.getHeight(), renderState, window, -1);
            availWidth = HtmlValues.getPixelSize(currentStyle.getWidth(), renderState, window, -1);
        }

        availHeight = availHeight == -1 ? htmlPanel.getHeight() : availHeight;
        availWidth = availWidth == -1 ? htmlPanel.getWidth() : availWidth;
    }

     /** {@inheritDoc} */
    @Override
    public String getAlignItems() {
        return Strings.isBlank(style.getAlignItems()) ? CSSValues.NONE.getValue() : style.getAlignItems();
    }

     /** {@inheritDoc} */
    @Override
    public String getAlignContent() {
        return Strings.isBlank(style.getAlignContent()) ? CSSValues.NONE.getValue() : style.getAlignContent();
    }

     /** {@inheritDoc} */
    @Override
    public String getAzimuth() {
        return style.getAzimuth();
    }

     /** {@inheritDoc} */
    @Override
    public String getBackground() {
        return style.getBackground();
    }

     /** {@inheritDoc} */
    @Override
    public String getBackgroundAttachment() {
        return style.getBackgroundAttachment();
    }

     /** {@inheritDoc} */
    @Override
    public String getBackgroundColor() {
        if (Strings.isBlank(style.getBackgroundColor())) {
            return "rgb(0, 0, 0)";
        }

        final Color c = ColorFactory.getInstance().getColor(style.getBackgroundColor());
        if (c != null) {
            final float alpha = (float) (c.getAlpha()) / 255.0f;

            if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
                return "rgb(0, 0, 0)";
            }

            if (alpha > 0 && alpha < 1) {
                return "rgba(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + alpha + ")";
            } else {
                return "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
            }
        } else {
            return "rgb(0, 0, 0)";
        }
    }

     /** {@inheritDoc} */
    @Override
    public String getBackgroundImage() {
        return style.getBackgroundImage();
    }

     /** {@inheritDoc} */
    @Override
    public String getBackgroundPosition() {
        return style.getBackgroundPosition();
    }

     /** {@inheritDoc} */
    @Override
    public String getBackgroundRepeat() {
        return style.getBackgroundRepeat();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorder() {
        return style.getBorder();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderBottom() {
        return style.getBorderBottom();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderWidth() {
        final String ccsBorderWidth = style.getBorderWidth();
        final int borderWidth = HtmlValues.getPixelSize(ccsBorderWidth, renderState, window.getWindow(), -1, 0);
        if(borderWidth == -1) {
            if(element.getParentNode() == null) {
                return "";
            } else{
                return "0px";
            }
        }
        return borderWidth + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderBottomColor() {
        return style.getBorderBottomColor();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderBottomStyle() {
        return style.getBorderBottomStyle();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderBottomWidth() {
        final String cssBorderBottomWidth = style.getBorderBottomWidth();
        final int borderBottomWidth = HtmlValues.getPixelSize(cssBorderBottomWidth, renderState, window.getWindow(), -1, availHeight);
        if(borderBottomWidth == -1) {
            if(element.getParentNode() == null) {
                return "";
            } else{
                return "0px";
            }
        }
        return borderBottomWidth + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderCollapse() {
        return style.getBorderCollapse();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderLeftColor() {
        return style.getBorderLeftColor();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderLeftStyle() {
        return style.getBorderLeftStyle();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderLeftWidth() {
        final String cssBorderBottomLeft = style.getBorderLeftWidth();
        final int borderBottomLeft = HtmlValues.getPixelSize(cssBorderBottomLeft, renderState, window.getWindow(), -1, availWidth);
        if(borderBottomLeft == -1) {
            if(element.getParentNode() == null) {
                return "";
            } else{
                return "0px";
            }
        }
        return borderBottomLeft + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderRight() {
        return style.getBorderRight();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderRightColor() {
        return Strings.isBlank(style.getBorderRightColor()) ? "rgb(0, 0, 0)" : style.getBorderRightColor();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderRightStyle() {
        return Strings.isBlank(style.getBorderRightStyle()) ? CSSValues.NONE.getValue() : style.getBorderRightStyle();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderRightWidth() {
        final String getBorderRightWidth = style.getBorderRightWidth();
        final int borderRightWidth = HtmlValues.getPixelSize(getBorderRightWidth, renderState, window.getWindow(), -1, availWidth);
        if(borderRightWidth == -1) {
            if(element.getParentNode() == null) {
                return "";
            } else{
                return "0px";
            }
        }
        return borderRightWidth + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderSpacing() {
        return style.getBorderSpacing();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderColor() {
        return Strings.isBlank(style.getBorderColor()) ? "rgb(0, 0, 0)" : style.getBorderColor();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderLeft() {
        return style.getBorderSpacing();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderTopColor() {
        return Strings.isBlank(style.getBorderTopColor()) ? "rgb(0, 0, 0)" : style.getBorderTopColor();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderStyle() {
        return Strings.isBlank(style.getBorderStyle()) ? CSSValues.NONE.getValue() : style.getBorderStyle();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderTop() {
        return style.getBorderTop();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderTopStyle() {
        return Strings.isBlank(style.getBorderTopStyle()) ? CSSValues.NONE.getValue() : style.getBorderTopStyle();
    }

     /** {@inheritDoc} */
    @Override
    public String getBorderTopWidth() {
        final String cssBorderToptWidth = style.getBorderTopWidth();
        final int borderTopWidth = HtmlValues.getPixelSize(cssBorderToptWidth, renderState, window.getWindow(), -1, availHeight);
        if(borderTopWidth == -1) {
            if(element.getParentNode() == null) {
                return "";
            } else{
                return "0px";
            }
        }
        return borderTopWidth + "px";
    }

     /** {@inheritDoc} */
     @Override
     public String getBottom() {
         if (Strings.isCssBlank(style.getBottom())) {
             if (element.getParentNode() != null) {
                 return CSSValues.AUTO.getValue();
             } else {
                 return "";
             }
         } else {
             return HtmlValues.getPixelSize(style.getBottom(), renderState, window, 0, availHeight) + "px";
         }
     }

     /** {@inheritDoc} */
    @Override
    public String getColor() {
        final String col = style.getColor();

        if (Strings.isBlank(col)) {
            return "rgb(0, 0, 0)";
        }
        final Color c = ColorFactory.getInstance().getColor(col);
        if (c != null) {
            if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
                return "rgb(0, 0, 0)";
            }
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

     /** {@inheritDoc} */
    @Override
    public String getContent() {
        return style.getContent();
    }

     /** {@inheritDoc} */
    @Override
    public String getCounterIncrement() {
        return style.getCounterIncrement();
    }

     /** {@inheritDoc} */
    @Override
    public String getCounterReset() {
        return style.getCounterReset();
    }

     /** {@inheritDoc} */
    @Override
    public String getClear() {
        return Strings.isBlank(style.getClear()) ? CSSValues.NONE.getValue() : style.getClear();
    }

     /** {@inheritDoc} */
    @Override
    public String getClip() {
        return style.getClip();
    }

     /** {@inheritDoc} */
    @Override
    public String getCssFloat() {
        final String ccFloat = style.getCssFloat();
        return Strings.isCssBlank(ccFloat) ? CSSValues.NONE.getValue() :
                HtmlValues.getPixelSize(ccFloat, renderState, window, 0, availWidth) + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getCue() {
        return style.getCue();
    }

     /** {@inheritDoc} */
    @Override
    public String getCueAfter() {
        return style.getCueAfter();
    }

     /** {@inheritDoc} */
    @Override
    public String getCueBefore() {
        return style.getCueBefore();
    }

     /** {@inheritDoc} */
    @Override
    public String getCursor() {
        final RenderState rs = element.getRenderState();
        return rs.getCursor().get().getType() == Cursor.DEFAULT_CURSOR ? "auto" : style.getCursor();
    }

     /** {@inheritDoc} */
    @Override
    public String getDirection() {
        return style.getDirection();
    }

     /** {@inheritDoc} */
    @Override
    public String getDisplay() {
        final String cssDisplay = style.getDisplay();
        if (Strings.isNotBlank(cssDisplay) && !"null".equals(cssDisplay)) {
            return cssDisplay;
        } else {
            final RenderState rs = element.getRenderState();
            CSSValues display;
            if (rs == null) {
                return style.getDisplay() == null ? "" : style.getDisplay();
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

    @Override
    /** {@inheritDoc} */
    public String getElevation() {
        return style.getElevation();
    }

    @Override
    /** {@inheritDoc} */
    public String getEmptyCells() {
        return style.getEmptyCells();
    }

     /** {@inheritDoc} */
    @Override
    public String getFont() {
        StringBuilder font = new StringBuilder();
        final String fontStyle = style.getFontStyle();
        final String lineHeight = getLineHeight();
        final String fontSize = getFontSize();
        final String fontFamily = getFontFamily();

        if (Strings.isCssNotBlank(fontStyle) && !fontStyle.equals(CSSValues.NORMAL.getValue())) {
            font.append(CSSValues.ITALIC.getValue() + " ");
        }

        font.append(fontSize);

        if (Strings.isCssNotBlank(lineHeight) && !lineHeight.equals(CSSValues.NORMAL.getValue())) {
            font.append(" / " + lineHeight);
        }

        if (Strings.isCssNotBlank(fontFamily)) {
            font.append(" " + fontFamily);
        }

        return font.toString().trim();
    }


     /** {@inheritDoc} */
    @Override
    public String getFontSize() {
        final HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();
        final CSSStyleDeclaration style = element.getStyle();
        int fontSize;

        if (Strings.isCssBlank(style.getFontSize()) && parent != null && parent.getStyle().getLength() > 0) {
            final CSSStyleDeclaration currentStyle = parent.getStyle();
            fontSize = FontValues.getPixelSize(currentStyle.getFontSize(), null, window, -1);
        } else {
            fontSize = Float.valueOf(FontValues.getFontSize(style.getFontSize(), window, null)).intValue();
        }
        return fontSize + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getFontSizeAdjust() {
        return style.getFontSizeAdjust();
    }

     /** {@inheritDoc} */
    @Override
    public String getFontStretch() {
        return style.getFontStretch();
    }

     /** {@inheritDoc} */
    @Override
    public String getFontStyle() {
        final String fontStyle = style.getFontStyle();
        if (Strings.isCssNotBlank(fontStyle)) {
            return !fontStyle.equals(CSSValues.NORMAL.getValue()) ? CSSValues.ITALIC.getValue() : fontStyle;
        } else {
            return CSSValues.NORMAL.getValue();
        }
    }

     /** {@inheritDoc} */
    @Override
    public String getFontVariant() {
        return FontValues.getFontVariant(style.getFontVariant());
    }

     /** {@inheritDoc} */
    @Override
    public String getFontWeight() {
        final String font = FontValues.getFontWeight(style.getFontWeight(), null, false);
        return Strings.isBlank(font) ?CSSValues.BOLD400.getValue()  : font;
    }

     /** {@inheritDoc} */
    @Override
    public String getFontFamily() {
        final CSSStyleDeclaration style = element.getStyle();
        return FontValues.getFontFamily(style.getFontFamily(), null);
    }

     /** {@inheritDoc} */
    @Override
    public String getFlexDirection() {
        return Strings.isBlank(style.getFlexDirection()) ? CSSValues.NONE.getValue() : style.getFlexDirection();
    }

     /** {@inheritDoc} */
    @Override
    public String getFlexWrap() {
        return Strings.isBlank(style.getFlexWrap()) ? CSSValues.NONE.getValue() : style.getFlexWrap();
    }

     /** {@inheritDoc} */
    @Override
    public String getFlexFlow() {
        return Strings.isBlank(style.getFlexFlow()) ? CSSValues.NONE.getValue() : style.getFlexFlow();
    }

     /** {@inheritDoc} */
    @Override
    public String getLineHeight() {
        final CSSStyleDeclaration style = element.getStyle();
        final String lineHeight = style.getLineHeight();
        return Strings.isCssBlank(lineHeight) ? CSSValues.NORMAL.getValue() : lineHeight;
    }

     /** {@inheritDoc} */
    @Override
    public String getHeight() {
        final String cssheight = style.getHeight();

        if (element.getParentNode() == null) {
            return "";
        }

        if (Strings.isCssBlank(element.getTextContent()) && (Strings.isCssBlank(cssheight) || CSSValues.AUTO.isEqual(cssheight))) {
            return CSSValues.AUTO.getValue();
        }

        if (Strings.isCssNotBlank(element.getTextContent()) && Strings.isCssBlank(cssheight)) {
            return "-1px";
        }

        final int height = HtmlValues.getPixelSize(cssheight, renderState, window, -1, availHeight);
        return height + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getJustifyContent() {
        return Strings.isBlank(style.getJustifyContent()) ? CSSValues.NONE.getValue() : style.getJustifyContent();
    }

     /** {@inheritDoc} */
    @Override
    public void setClipPath(String clip) {
        style.setClipPath(clip);
    }

     /** {@inheritDoc} */
    @Override
    public void setClipRule(String clip) {
        style.setClipRule(clip);
    }

     /** {@inheritDoc} */
    @Override
    public void setFloat(String flt) {
        style.setFloat(flt);
    }

     /** {@inheritDoc} */
    @Override
    public void setFill(String value) {
        style.setFill(value);

    }

     /** {@inheritDoc} */
    @Override
    public void setFillOpacity(String value) {
        style.setFillOpacity(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setOpacity(String value) {
        style.setOpacity(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setTransform(String value) {
        style.setTransform(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setStrokeWidth(String value) {
        style.setStrokeWidth(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setStrokeOpacity(String value) {
        style.setStrokeOpacity(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setStrokeMiterLimit(String value) {
        style.setStrokeMiterLimit(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setStrokeLineJoin(String value) {
        style.setStrokeLineJoin(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setStrokeDashArray(String value) {
        style.setStrokeDashArray(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setStroke(String value) {
        style.setStroke(value);
    }

     /** {@inheritDoc} */
    @Override
    public void setStrokeLineCap(String value) {
        style.setStrokeLineCap(value);
    }

     /** {@inheritDoc} */
    @Override
    public String getLeft() {
        return Strings.isCssBlank(style.getLeft()) ? "" : HtmlValues.getPixelSize(style.getLeft(), renderState, window, 0, availWidth) + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getLetterSpacing() {
        return Strings.isBlank(style.getLetterSpacing()) ? CSSValues.NORMAL.getValue() : style.getLetterSpacing();
    }

     /** {@inheritDoc} */
    @Override
    public String getMargin() {
        return Strings.isBlank(style.getMargin()) ? CSSValues.MARGIN.getValue() : style.getMargin();
    }

     /** {@inheritDoc} */
    @Override
    public String getMarginBottom() {
        final String cssMarginBottom = style.getMarginBottom();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginBottom)) return CSSValues.NONE.getValue();
        final int marginBottom = HtmlValues.getPixelSize(cssMarginBottom, renderState, window.getWindow(), -1, availHeight);
        return marginBottom + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getMarginLeft() {
        final String cssMarginLeft = style.getMarginLeft();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginLeft)) return CSSValues.NONE.getValue();
        final int marginLeft = HtmlValues.getPixelSize(cssMarginLeft, renderState, window.getWindow(), -1, availWidth);
        return marginLeft + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getMarginRight() {
        final String cssMarginRight = style.getMarginRight();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginRight)) return "0px";
        final int marginRight = HtmlValues.getPixelSize(cssMarginRight, renderState, window.getWindow(), -1, availWidth);
        return marginRight + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getMarginTop() {
        final String cssMarginTop = style.getMarginTop();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginTop)) return CSSValues.NONE.getValue();
        final int marginTop = HtmlValues.getPixelSize(cssMarginTop, renderState, window.getWindow(), -1, availHeight);
        return marginTop + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getMarkerOffset() {
        return style.getMarkerOffset();
    }

     /** {@inheritDoc} */
    @Override
    public String getMarks() {
        return style.getMarks();
    }

     /** {@inheritDoc} */
    @Override
    public String getMaxHeight() {
        return Strings.isBlank(style.getMaxHeight()) ? CSSValues.NONE.getValue() : style.getMaxHeight();
    }

     /** {@inheritDoc} */
    @Override
    public String getMaxWidth() {
        return Strings.isBlank(style.getMaxWidth()) ? CSSValues.NONE.getValue() : style.getMaxWidth();
    }

     /** {@inheritDoc} */
    @Override
    public String getMinHeight() {
        return Strings.isBlank(style.getMinHeight()) ? CSSValues.NONE.getValue() : style.getMinHeight();
    }

     /** {@inheritDoc} */
    @Override
    public String getMinWidth() {
        return Strings.isBlank(style.getMinWidth()) ? CSSValues.NONE.getValue() : style.getMinWidth();
    }

     /** {@inheritDoc} */
    @Override
    public String getOpacity() {
        return Strings.isBlank(style.getOpacity()) ? "1" : style.getOpacity();
    }

     /** {@inheritDoc} */
    @Override
    public String getOutlineWidth() {
        return Strings.isBlank(style.getOutlineWidth()) ? CSSValues.NONE.getValue() : style.getOutlineWidth();
    }

     /** {@inheritDoc} */
    @Override
    public String getPadding() {
        return Strings.isBlank(style.getPadding()) ? CSSValues.NONE.getValue() : style.getPadding();
    }

     /** {@inheritDoc} */
    @Override
    public String getPaddingBottom() {
        final String cssPaddingBottom = style.getPaddingBottom();
        final int paddingBottom = HtmlValues.getPixelSize(cssPaddingBottom, renderState, window.getWindow(), -1, availHeight);
        return paddingBottom + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getPaddingLeft() {
        final String cssPaddingLeft = style.getPaddingLeft();
        final int paddingLeft = HtmlValues.getPixelSize(cssPaddingLeft, renderState, window.getWindow(), -1, availWidth);
        return paddingLeft + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getPaddingRight() {
        final String cssPaddingRight = style.getPaddingRight();
        final int paddingRight = HtmlValues.getPixelSize(cssPaddingRight, renderState, window.getWindow(), -1, availWidth);
        return paddingRight + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getPaddingTop() {
        final String cssPaddingTop = style.getPaddingTop();
        final int paddingTop = HtmlValues.getPixelSize(cssPaddingTop, renderState, window.getWindow(), -1, availHeight);
        return paddingTop + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getPage() {
        return style.getPage();
    }

     /** {@inheritDoc} */
    @Override
    public String getPageBreakAfter() {
        return style.getPageBreakAfter();
    }

     /** {@inheritDoc} */
    @Override
    public String getPageBreakBefore() {
        return style.getPageBreakBefore();
    }

     /** {@inheritDoc} */
    @Override
    public String getPageBreakInside() {
        return style.getPageBreakInside();
    }

     /** {@inheritDoc} */
    @Override
    public String getPause() {
        return style.getPause();
    }

     /** {@inheritDoc} */
    @Override
    public String getPauseAfter() {
        return style.getPauseAfter();
    }

     /** {@inheritDoc} */
    @Override
    public String getPauseBefore() {
        return style.getPauseBefore();
    }

     /** {@inheritDoc} */
    @Override
    public String getPitch() {
        return style.getPitch();
    }

     /** {@inheritDoc} */
    @Override
    public String getPitchRange() {
        return style.getPitchRange();
    }

     /** {@inheritDoc} */
    @Override
    public String getPlayDuring() {
        return style.getPlayDuring();
    }

     /** {@inheritDoc} */
    @Override
    public String getRight() {
        return Strings.isBlank(style.getRight()) ? CSSValues.AUTO.getValue() : style.getRight();
    }

     /** {@inheritDoc} */
    @Override
    public String getSize() {
        return style.getSize();
    }

     /** {@inheritDoc} */
    @Override
    public String getSpeak() {
        return style.getSpeak();
    }

     /** {@inheritDoc} */
    @Override
    public String getSpeakHeader() {
        return style.getSpeakHeader();
    }

     /** {@inheritDoc} */
    @Override
    public String getSpeakNumeral() {
        return style.getSpeakNumeral();
    }

     /** {@inheritDoc} */
    @Override
    public String getSpeakPunctuation() {
        return style.getSpeakPunctuation();
    }

     /** {@inheritDoc} */
    @Override
    public String getSpeechRate() {
        return style.getSpeechRate();
    }

     /** {@inheritDoc} */
    @Override
    public String getStress() {
        return style.getStress();
    }

     /** {@inheritDoc} */
    @Override
    public String getTableLayout() {
        return style.getTableLayout();
    }

     /** {@inheritDoc} */
    @Override
    public String getTextAlign() {
        return Strings.isBlank(style.getTextAlign()) ? CSSValues.NONE.getValue() : style.getTextAlign();
    }

     /** {@inheritDoc} */
    @Override
    public String getTextIndent() {
        return Strings.isBlank(style.getTextIndent()) ? CSSValues.NONE.getValue() : style.getTextIndent();
    }

     /** {@inheritDoc} */
    @Override
    public String getTextShadow() {
        return style.getTextShadow();
    }

     /** {@inheritDoc} */
    @Override
    public String getTextTransform() {
        return Strings.isBlank(style.getTextTransform()) ? CSSValues.NONE.getValue() : style.getTextTransform();
    }

     /** {@inheritDoc} */
    @Override
    public String getTop() {
        if (Strings.isCssBlank(style.getTop())) {
            if (element.getParentNode() != null) {
                return CSSValues.AUTO.getValue();
            } else {
                return "";
            }
        } else {
            return HtmlValues.getPixelSize(style.getTop(), renderState, window, 0, availHeight) + "px";
        }
    }

     /** {@inheritDoc} */
    @Override
    public String getUnicodeBidi() {
        return style.getUnicodeBidi();
    }

     /** {@inheritDoc} */
    @Override
    public String getTransform() {
        return Strings.isBlank(style.getTransform()) ? CSSValues.NONE.getValue() : style.getTransform();
    }

     /** {@inheritDoc} */
    @Override
    public String getVerticalAlign() {
        return Strings.isBlank(style.getVerticalAlign()) ? CSSValues.BASELINE.getValue() : style.getVerticalAlign();
    }

     /** {@inheritDoc} */
    @Override
    public String getWidows() {
        return style.getWidows();
    }

     /** {@inheritDoc} */
    @Override
    public String getOrphans() {
        return style.getOrphans();
    }

     /** {@inheritDoc} */
    @Override
    public String getOutline() {
        return style.getOutline();
    }

     /** {@inheritDoc} */
    @Override
    public String getOutlineColor() {
        return style.getOutlineColor();
    }

     /** {@inheritDoc} */
    @Override
    public String getOutlineStyle() {
        return style.getOutlineStyle();
    }


     /** {@inheritDoc} */
    @Override
    public String getPosition() {
        final String position = style.getPosition();
        CSSValues pos = CSSValues.get(position);
        return pos != null && pos != CSSValues.DEFAULT ? pos.getValue() : "static";
    }

     /** {@inheritDoc} */
    @Override
    public String getQuotes() {
        return style.getQuotes();
    }

     /** {@inheritDoc} */
    @Override
    public String getRichness() {
        return style.getRichness();
    }

     /** {@inheritDoc} */
    @Override
    public String getWidth() {
        final String csswidth = style.getWidth();

        if (element.getParentNode() == null) {
            return "";
        }

        if (Strings.isCssBlank(csswidth) || CSSValues.AUTO.isEqual(csswidth)) {
            return CSSValues.AUTO.getValue();
        }



        final int width = HtmlValues.getPixelSize(csswidth, renderState, window, -1, availWidth);
        return width + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getWordSpacing() {
        return style.getWordSpacing();
    }

     /** {@inheritDoc} */
    @Override
    public void setAzimuth(String azimuth) {
        style.setAzimuth(azimuth);
    }

     /** {@inheritDoc} */
    @Override
    public void setBackground(String background) {
        style.setBackground(background);
    }

     /** {@inheritDoc} */
    @Override
    public void setBackgroundAttachment(String backgroundAttachment) {
        style.setBackgroundAttachment(backgroundAttachment);
    }

     /** {@inheritDoc} */
    @Override
    public void setBackgroundColor(String backgroundColor) {
        style.setBackgroundColor(backgroundColor);
    }

     /** {@inheritDoc} */
    @Override
    public void setBackgroundImage(String backgroundImage) {
        style.setBackgroundImage(backgroundImage);
    }

     /** {@inheritDoc} */
    @Override
    public void setBackgroundPosition(String backgroundPosition) {
        style.setBackgroundPosition(backgroundPosition);
    }

     /** {@inheritDoc} */
    @Override
    public void setBackgroundRepeat(String backgroundRepeat) {
        style.setBackgroundRepeat(backgroundRepeat);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorder(String border) {
        style.setBorder(border);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderBottom(String borderBottom) {
        style.setBorderBottom(borderBottom);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderBottomColor(String borderBottomColor) {
        style.setBorderBottomColor(borderBottomColor);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderBottomStyle(String borderBottomStyle) {
        style.setBorderBottomStyle(borderBottomStyle);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderBottomWidth(String borderBottomWidth) {
        style.setBorderBottomWidth(borderBottomWidth);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderCollapse(String borderCollapse) {
        style.setBorderCollapse(borderCollapse);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderColor(String borderColor) {
        style.setBorderColor(borderColor);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderLeft(String borderLeft) {
        style.setBorderLeft(borderLeft);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderLeftColor(String borderLeftColor) {
        style.setBorderLeftColor(borderLeftColor);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderLeftStyle(String borderLeftStyle) {
        style.setBorderLeftStyle(borderLeftStyle);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderLeftWidth(String borderLeftWidth) {
        style.setBorderLeftWidth(borderLeftWidth);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderRight(String borderRight) {
        style.setBorderRight(borderRight);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderRightColor(String borderRightColor) {
        style.setBorderRightColor(borderRightColor);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderRightStyle(String borderRightStyle) {
        style.setBorderRightStyle(borderRightStyle);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderRightWidth(String borderRightWidth) {
        style.setBorderRightWidth(borderRightWidth);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderSpacing(String borderSpacing) {
        style.setBorderSpacing(borderSpacing);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderStyle(String borderStyle) {
        style.setBorderStyle(borderStyle);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderTop(String borderTop) {
        style.setBorderTop(borderTop);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderTopColor(String borderTopColor) {
        style.setBorderTopColor(borderTopColor);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderTopStyle(String borderTopStyle) {
        style.setBorderTopStyle(borderTopStyle);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderTopWidth(String borderTopWidth) {
        style.setBorderTopWidth(borderTopWidth);
    }

     /** {@inheritDoc} */
    @Override
    public void setBorderWidth(String borderWidth) {
        style.setBorderWidth(borderWidth);
    }

     /** {@inheritDoc} */
    @Override
    public void setBottom(String bottom) {
        style.setBottom(bottom);
    }

     /** {@inheritDoc} */
    @Override
    public void setCaptionSide(String captionSide) {
        style.setCaptionSide(captionSide);
    }

     /** {@inheritDoc} */
    @Override
    public void setClear(String clear) {
        style.setClear(clear);
    }

     /** {@inheritDoc} */
    @Override
    public void setClip(String clip) {
        style.setClip(clip);
    }

     /** {@inheritDoc} */
    @Override
    public void setColor(String color) {
        style.setColor(color);
    }

     /** {@inheritDoc} */
    @Override
    public void setContent(String content) {
        style.setContent(content);
    }

     /** {@inheritDoc} */
    @Override
    public void setCounterIncrement(String counterIncrement) {
        style.setCounterIncrement(counterIncrement);
    }

     /** {@inheritDoc} */
    @Override
    public void setCounterReset(String counterReset) {
        style.setCounterReset(counterReset);
    }

     /** {@inheritDoc} */
    @Override
    public void setCssFloat(String cssFloat) {
        style.setCssFloat(cssFloat);
    }

     /** {@inheritDoc} */
    @Override
    public void setCue(String cue) {
        style.setCue(cue);
    }

     /** {@inheritDoc} */
    @Override
    public void setCueAfter(String cueAfter) {
        style.setCueAfter(cueAfter);
    }

     /** {@inheritDoc} */
    @Override
    public void setCueBefore(String cueBefore) {
        style.setCueBefore(cueBefore);
    }

     /** {@inheritDoc} */
    @Override
    public void setCursor(String cursor) {
        style.setCursor(cursor);
    }

     /** {@inheritDoc} */
    @Override
    public void setDirection(String direction) {
        style.setDirection(direction);
    }

     /** {@inheritDoc} */
    @Override
    public void setDisplay(String display) {
        style.setDisplay(display);
    }

     /** {@inheritDoc} */
    @Override
    public void setElevation(String elevation) {
        style.setElevation(elevation);
    }

     /** {@inheritDoc} */
    @Override
    public void setEmptyCells(String emptyCells) {
        style.setEmptyCells(emptyCells);
    }

     /** {@inheritDoc} */
    @Override
    public void setFont(String font) {
        style.setFont(font);
    }

     /** {@inheritDoc} */
    @Override
    public void setFontFamily(String fontFamily) {
        style.setFontFamily(fontFamily);
    }

     /** {@inheritDoc} */
    @Override
    public void setFontSize(String fontSize) {
        style.setFontSize(fontSize);
    }

     /** {@inheritDoc} */
    @Override
    public void setFontSizeAdjust(String fontSizeAdjust) {
        style.setFontSizeAdjust(fontSizeAdjust);
    }

     /** {@inheritDoc} */
    @Override
    public void setFontStretch(String fontStretch) {
        style.setFontStretch(fontStretch);
    }

     /** {@inheritDoc} */
    @Override
    public void setFontStyle(String fontStyle) {
        style.setFontStyle(fontStyle);
    }

     /** {@inheritDoc} */
    @Override
    public void setFontVariant(String fontVariant) {
        style.setFontVariant(fontVariant);
    }

     /** {@inheritDoc} */
    @Override
    public void setFontWeight(String fontWeight) {
        style.setFontWeight(fontWeight);
    }

     /** {@inheritDoc} */
    @Override
    public String getWhiteSpace() {
        return Strings.isBlank(style.getWhiteSpace()) ? CSSValues.NONE.getValue() : style.getWhiteSpace();
    }

     /** {@inheritDoc} */
    @Override
    public String getzIndex() {
        return Strings.isBlank(style.getzIndex()) ? CSSValues.AUTO.getValue() : style.getzIndex() + "px";
    }

     /** {@inheritDoc} */
    @Override
    public String getOverflow() {
        return style.getOverflow();
    }

     /** {@inheritDoc} */
    @Override
    public String getBoxSizing() {
        if (style.getBoxSizing() == null) {
            if (element.getParentNode() == null) {
                return "";
            } else {
                return CSSValues.CONTENT_BOX.getValue();
            }
        }
        return style.getBoxSizing();
    }

     /** {@inheritDoc} */
    @Override
    public String getFill() {

        if (Strings.isBlank(style.getFill())) {
            return "rgb(0, 0, 0)";
        }

        if (style.getFill().toLowerCase().contains("url")) {
            return style.getFill();
        }

        final Color c = ColorFactory.getInstance().getColor(style.getFill());
        if (c != null) {
            if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
                return "rgb(0, 0, 0)";
            }
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

     /** {@inheritDoc} */
    @Override
    public String getFillOpacity() {
        return Strings.isBlank(style.getFillOpacity()) ? CSSValues.NONE.getValue() : style.getFillOpacity();
    }

     /** {@inheritDoc} */
    @Override
    public String getStroke() {
        return Strings.isBlank(style.getStroke()) ? CSSValues.NONE.getValue() : style.getStroke();
    }

     /** {@inheritDoc} */
    @Override
    public String getStrokeOpacity() {
        return Strings.isBlank(style.getStrokeOpacity()) ? CSSValues.NONE.getValue() : style.getStrokeOpacity();
    }

     /** {@inheritDoc} */
    @Override
    public String getStrokeLineCap() {
        return Strings.isBlank(style.getStrokeLineCap()) ? CSSValues.NONE.getValue() : style.getStrokeLineCap();
    }

     /** {@inheritDoc} */
    @Override
    public String getStrokeWidth() {
        return Strings.isBlank(style.getStrokeWidth()) ? CSSValues.NONE.getValue() : style.getStrokeWidth();
    }

     /** {@inheritDoc} */
    @Override
    public String getStrokeLineJoin() {
        return Strings.isBlank(style.getStrokeLineJoin()) ? CSSValues.NONE.getValue() : style.getStrokeLineJoin();
    }

     /** {@inheritDoc} */
    @Override
    public String getStrokeMiterLimit() {
        return Strings.isBlank(style.getStrokeMiterLimit()) ? CSSValues.NONE.getValue() : style.getStrokeMiterLimit();
    }

     /** {@inheritDoc} */
    @Override
    public String getClipPath() {
        return Strings.isBlank(style.getClipPath()) ? CSSValues.NONE.getValue() : style.getClipPath();
    }

     /** {@inheritDoc} */
    @Override
    public String getVisibility() {
        return Strings.isBlank(style.getVisibility()) ? CSSValues.NONE.getValue() : style.getVisibility();
    }

     /** {@inheritDoc} */
    @Override
    public String getVoiceFamily() {
        return style.getVoiceFamily();
    }

     /** {@inheritDoc} */
    @Override
    public String getVolume() {
        return style.getVolume();
    }

     /** {@inheritDoc} */
    @Override
    public String getClipRule() {
        return Strings.isBlank(style.getClipRule()) ? CSSValues.NONE.getValue() : style.getClipRule();
    }

     /** {@inheritDoc} */
    @Override
    public String getStopColor() {
        return Strings.isBlank(style.getStopColor()) ? CSSValues.NONE.getValue() : style.getStopColor();
    }

     /** {@inheritDoc} */
    @Override
    public String getStopOpacity() {
        return Strings.isBlank(style.getStopOpacity()) ? CSSValues.NONE.getValue() : style.getStopOpacity();
    }

     /** {@inheritDoc} */
    @Override
    public String getStrokeDashArray() {
        return Strings.isBlank(style.getStrokeDashArray()) ? CSSValues.NONE.getValue() : style.getStrokeDashArray();
    }

     /** {@inheritDoc} */
    @Override
    public String getFloat() {
        return style.getFloat();
    }

     /** {@inheritDoc} */
    @Override
    public String getListStyle() {
        return style.getListStyle();
    }

     /** {@inheritDoc} */
    @Override
    public String getListStyleType() {
        return style.getListStyleType();
    }

     /** {@inheritDoc} */
    @Override
    public String getListStyleImage() {
        return style.getListStyleImage();
    }

     /** {@inheritDoc} */
    @Override
    public String getListStylePosition() {
        return style.getListStylePosition();
    }

     /** {@inheritDoc} */
    @Override
    public String getCaptionSide() {
        return style.getCaptionSide();
    }

     /** {@inheritDoc} */
    @Override
    public String getTextDecoration() {
        return Strings.isBlank(style.getTextDecoration()) ? CSSValues.NONE.getValue() : style.getTextDecoration();
    }

     /** {@inheritDoc} */
    @Override
    public void setTextAlign(String textAlign) {
        style.setTextAlign(textAlign);
    }

     /** {@inheritDoc} */
    @Override
    public void setTextDecoration(String textDecoration) {

    }

     /** {@inheritDoc} */
    @Override
    public void setTextIndent(String textIndent) {

    }

     /** {@inheritDoc} */
    @Override
    public void setTextShadow(String textShadow) {

    }

     /** {@inheritDoc} */
    @Override
    public void setTextTransform(String textTransform) {

    }

     /** {@inheritDoc} */
    @Override
    public void setTop(String top) {

    }

     /** {@inheritDoc} */
    @Override
    public void setUnicodeBidi(String unicodeBidi) {

    }

     /** {@inheritDoc} */
    @Override
    public void setVerticalAlign(String verticalAlign) {

    }

     /** {@inheritDoc} */
    @Override
    public void setVisibility(String visibility) {

    }

     /** {@inheritDoc} */
    @Override
    public void setVoiceFamily(String voiceFamily) {

    }

     /** {@inheritDoc} */
    @Override
    public void setVolume(String volume) {

    }

     /** {@inheritDoc} */
    @Override
    public void setWhiteSpace(String whiteSpace) {

    }

     /** {@inheritDoc} */
    @Override
    public void setWidows(String widows) {

    }

     /** {@inheritDoc} */
    @Override
    public void setWidth(String width) {

    }

     /** {@inheritDoc} */
    @Override
    public void setWordSpacing(String wordSpacing) {

    }

     /** {@inheritDoc} */
    @Override
    public void setzIndex(String zIndex) {
        style.setzIndex(zIndex);
    }

     /** {@inheritDoc} */
    @Override
    public void setHeight(String textAlign) {
        style.setHeight(textAlign);
    }

     /** {@inheritDoc} */
    @Override
    public void setLeft(String left) {

    }

     /** {@inheritDoc} */
    @Override
    public void setLineHeight(String lineHeight) {

    }

     /** {@inheritDoc} */
    @Override
    public void setListStyle(String listStyle) {

    }

     /** {@inheritDoc} */
    @Override
    public void setListStyleImage(String listStyleImage) {

    }

     /** {@inheritDoc} */
    @Override
    public void setListStylePosition(String listStylePosition) {

    }

     /** {@inheritDoc} */
    @Override
    public void setListStyleType(String listStyleType) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMargin(String margin) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMarginBottom(String marginBottom) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMarginLeft(String marginLeft) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMarginRight(String marginRight) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMarginTop(String marginTop) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMarkerOffset(String markerOffset) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMarks(String marks) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMaxHeight(String maxHeight) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMaxWidth(String maxWidth) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMinHeight(String minHeight) {

    }

     /** {@inheritDoc} */
    @Override
    public void setMinWidth(String minWidth) {

    }

     /** {@inheritDoc} */
    @Override
    public void setLetterSpacing(String letterSpacing) {

    }

     /** {@inheritDoc} */
    @Override
    public void setOrphans(String orphans) {

    }

     /** {@inheritDoc} */
    @Override
    public void setOutline(String outline) {

    }

     /** {@inheritDoc} */
    @Override
    public void setOutlineColor(String outlineColor) {

    }

     /** {@inheritDoc} */
    @Override
    public void setOutlineStyle(String outlineStyle) {

    }

     /** {@inheritDoc} */
    @Override
    public void setOutlineWidth(String outlineWidth) {

    }

     /** {@inheritDoc} */
    @Override
    public void setOverflow(String overflow) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPadding(String padding) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPaddingBottom(String paddingBottom) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPaddingLeft(String paddingLeft) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPaddingRight(String paddingRight) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPaddingTop(String paddingTop) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPage(String page) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPageBreakAfter(String pageBreakAfter) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPageBreakBefore(String pageBreakBefore) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPageBreakInside(String pageBreakInside) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPause(String pause) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPauseAfter(String pauseAfter) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPauseBefore(String pauseBefore) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPitch(String pitch) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPitchRange(String pitchRange) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPlayDuring(String playDuring) {

    }

     /** {@inheritDoc} */
    @Override
    public void setPosition(String position) {

    }

     /** {@inheritDoc} */
    @Override
    public void setQuotes(String quotes) {

    }

     /** {@inheritDoc} */
    @Override
    public void setRichness(String richness) {

    }

     /** {@inheritDoc} */
    @Override
    public void setRight(String right) {

    }

     /** {@inheritDoc} */
    @Override
    public void setSize(String size) {

    }

     /** {@inheritDoc} */
    @Override
    public void setSpeak(String speak) {

    }

     /** {@inheritDoc} */
    @Override
    public void setSpeakHeader(String speakHeader) {

    }

     /** {@inheritDoc} */
    @Override
    public void setSpeakNumeral(String speakNumeral) {

    }

     /** {@inheritDoc} */
    @Override
    public void setSpeakPunctuation(String speakPunctuation) {

    }

     /** {@inheritDoc} */
    @Override
    public void setSpeechRate(String speechRate) {

    }

     /** {@inheritDoc} */
    @Override
    public void setStress(String stress) {

    }

     /** {@inheritDoc} */
    @Override
    public void setTableLayout(String tableLayout) {

    }
    @Override
    public String getPropertyValue(String property) {
        return style.getPropertyValue(property);
    }

    @Override
    public String toString() {
        return "[object CSSStyleDeclaration]";
    }
}