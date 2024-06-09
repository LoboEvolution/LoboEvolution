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

package org.loboevolution.html.style;

import org.loboevolution.common.Strings;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.css.ComputedCSSStyleDeclaration;
import org.loboevolution.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;
import java.util.Arrays;

/**
 * <p>ComputedCSSStyleDeclaration class.</p>
 */
public class ComputedCSSStyleDeclarationImpl implements ComputedCSSStyleDeclaration {

    private final HTMLElementImpl element;

    private final Window window;

    private final CSSStyleDeclaration style;

    private int availHeight = -1;

    private int availWidth = -1;


    /**
     * <p>Constructor for AbstractCSSProperties.</p>
     *
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     **/

    public ComputedCSSStyleDeclarationImpl(final HTMLElementImpl element, final CSSStyleDeclaration style) {
        this.element = element;
        this.style = style;

        final HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
        this.window = doc.getDefaultView();

        final HtmlRendererContext htmlRendererContext = doc.getHtmlRendererContext();
        final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
        final HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();

        if (parent != null) {
            final CSSStyleDeclaration currentStyle = parent.getStyle();
            availHeight = HtmlValues.getPixelSize(currentStyle.getHeight(), element.getRenderState(), window, -1);
            availWidth = HtmlValues.getPixelSize(currentStyle.getWidth(), element.getRenderState(), window, -1);
        }

        availHeight = availHeight == -1 ? htmlPanel.getHeight() : availHeight;
        availWidth = availWidth == -1 ? htmlPanel.getWidth() : availWidth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlignItems() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getAlignItems()) ? CSSValues.NONE.getValue() : style.getAlignItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAlignContent() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getAlignContent()) ? CSSValues.NONE.getValue() : style.getAlignContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAzimuth() {
        return this.element.getParentNode() == null ? null : style.getAzimuth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackground() {
        return this.element.getParentNode() == null ? null : style.getBackground();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundAttachment() {
        return this.element.getParentNode() == null ? null : style.getBackgroundAttachment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getBackgroundColor(), CSSValues.INITIAL.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundImage() {
        return this.element.getParentNode() == null ? null : style.getBackgroundImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundPosition() {
        String backgroundPosition = style.getBackgroundPosition();
        if (this.element.getParentNode() != null && Strings.isNotBlank(backgroundPosition)) {
            StringBuilder builder = new StringBuilder();
            Arrays.stream(backgroundPosition.split(" ")).forEach(position -> {

                if (builder.length() > 0) {
                    builder.append(" ");
                }

                switch (CSSValues.get(position)) {
                    case BOTTOM:
                    case RIGHT:
                        builder.append("100%");
                        break;
                    case TOP:
                    case LEFT:
                        if (builder.indexOf("100%") > -1) {
                            builder.insert(0, "0% ");
                        } else {
                            builder.append("0%");
                        }
                        break;
                    case CENTER:
                        builder.append("50%");
                        break;
                    default:
                        builder.append(position);
                        break;
                }
            });

            return builder.toString().trim();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBackgroundRepeat() {
        return this.element.getParentNode() == null ? null : style.getBackgroundRepeat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorder() {
        return this.element.getParentNode() == null ? null : style.getBorder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderBottom() {
        return this.element.getParentNode() == null ? null : style.getBorderBottom();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderWidth() {
        final String ccsBorderWidth = style.getBorderWidth();
        final int borderWidth = HtmlValues.getPixelSize(ccsBorderWidth, element.getRenderState(), window.getWindow(), -1, 0);
        return this.element.getParentNode() == null ? "" : borderWidth == -1 ? "0px" : borderWidth + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderBottomColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getBorderBottomColor(), "rgb(0, 0, 0)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderBottomStyle() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getBorderBottomStyle()) ? CSSValues.INITIAL.getValue() : style.getBorderBottomStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderBottomWidth() {
        if (element.getRenderState() != null) {
            final int borderBottomWidth = ((HtmlInsets) element.getRenderState().getBorderInfo().getInsets()).getBottom();
            return this.element.getParentNode() == null ? "" : borderBottomWidth == -1 ? "0px" : borderBottomWidth + "px";
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderCollapse() {
        return this.element.getParentNode() == null ? null : style.getBorderCollapse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderLeftColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getBorderLeftColor(), "rgb(0, 0, 0)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderLeftStyle() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getBorderLeftStyle()) ? CSSValues.INITIAL.getValue() : style.getBorderLeftStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderLeftWidth() {
        if (element.getRenderState() != null) {
            final int borderLeftWidth = ((HtmlInsets) element.getRenderState().getBorderInfo().getInsets()).getLeft();
            return this.element.getParentNode() == null ? "" : borderLeftWidth == -1 ? "0px" : borderLeftWidth + "px";
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderRight() {
        return this.element.getParentNode() == null ? null : style.getBorderRight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderRightColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getBorderRightColor(), "rgb(0, 0, 0)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderRightStyle() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getBorderRightStyle()) ? CSSValues.INITIAL.getValue() : style.getBorderRightStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderRightWidth() {
        if (element.getRenderState() != null) {
            final int borderRightWidth = ((HtmlInsets) element.getRenderState().getBorderInfo().getInsets()).getRight();
            return this.element.getParentNode() == null ? "" : borderRightWidth == -1 ? "0px" : borderRightWidth + "px";
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderSpacing() {
        return this.element.getParentNode() == null ? null : style.getBorderSpacing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getBorderColor(), "rgb(0, 0, 0)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderLeft() {
        return this.element.getParentNode() == null ? null : style.getBorderLeft();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderTopColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getBorderTopColor(), "rgb(0, 0, 0)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderStyle() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getBorderStyle()) ? CSSValues.NONE.getValue() : style.getBorderStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderTop() {
        return this.element.getParentNode() == null ? null : style.getBorderTop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderTopStyle() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getBorderTopStyle()) ? CSSValues.INITIAL.getValue() : style.getBorderTopStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBorderTopWidth() {
        if (element.getRenderState() != null) {
            final int borderTopWidth = ((HtmlInsets) element.getRenderState().getBorderInfo().getInsets()).getTop();
            return this.element.getParentNode() == null ? "" : borderTopWidth == -1 ? "0px" : borderTopWidth + "px";
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBottom() {
        if (Strings.isCssBlank(style.getBottom())) {
            if (element.getParentNode() != null) {
                return this.element.getParentNode() == null ? null : CSSValues.AUTO.getValue();
            } else {
                return "";
            }
        } else {
            return this.element.getParentNode() == null ? "" : HtmlValues.getPixelSize(style.getBottom(), element.getRenderState(), window, 0, availHeight) + "px";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getColor(), "rgb(0, 0, 0)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContent() {
        return this.element.getParentNode() == null ? null : style.getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCounterIncrement() {
        return this.element.getParentNode() == null ? null : style.getCounterIncrement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCounterReset() {
        return this.element.getParentNode() == null ? null : style.getCounterReset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClear() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getClear()) ? CSSValues.NONE.getValue() : style.getClear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClip() {
        return this.element.getParentNode() == null ? null : style.getClip();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCssFloat() {
        final String cssFloat = Strings.isNotBlank(style.getCssFloat()) ? style.getCssFloat() : Strings.isNotBlank(getFloat()) ? getFloat() : CSSValues.NONE.getValue();
        return this.element.getParentNode() == null ? null : cssFloat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCue() {
        return this.element.getParentNode() == null ? null : style.getCue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCueAfter() {
        return this.element.getParentNode() == null ? null : style.getCueAfter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCueBefore() {
        return this.element.getParentNode() == null ? null : style.getCueBefore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCursor() {
        final RenderState rs = element.getRenderState();
        return this.element.getParentNode() == null ? null : rs.getCursor().getType() == Cursor.DEFAULT_CURSOR ? "auto" : style.getCursor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDirection() {
        return this.element.getParentNode() == null ? null : style.getDirection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplay() {

        final String cssDisplay = style.getDisplay();
        if (Strings.isNotBlank(cssDisplay) && !"null".equals(cssDisplay)) {
            return this.element.getParentNode() == null ? "" : cssDisplay;
        } else {
            final RenderState rs = element.getRenderState();
            final CSSValues display;
            if (rs == null) {
                return this.element.getParentNode() == null ? "" : style.getDisplay() == null ? "" : style.getDisplay();
            } else {
                display = switch (rs.getDefaultDisplay()) {
                    case RenderState.DISPLAY_BLOCK -> CSSValues.BLOCK;
                    case RenderState.DISPLAY_NONE -> CSSValues.NONE;
                    case RenderState.DISPLAY_LIST_ITEM -> CSSValues.LIST_ITEM;
                    case RenderState.DISPLAY_TABLE -> CSSValues.TABLE;
                    case RenderState.DISPLAY_TABLE_CELL -> CSSValues.TABLE_CELL;
                    case RenderState.DISPLAY_TABLE_ROW -> CSSValues.TABLE_ROW;
                    case RenderState.DISPLAY_TABLE_CAPTION -> CSSValues.TABLE_CAPTION;
                    case RenderState.DISPLAY_TABLE_COLUMN -> CSSValues.TABLE_COLUMN;
                    case RenderState.DISPLAY_TABLE_FOOTER_GROUP -> CSSValues.TABLE_FOOTER_GROUP;
                    case RenderState.DISPLAY_TABLE_HEADER_GROUP -> CSSValues.TABLE_HEADER_GROUP;
                    case RenderState.DISPLAY_TABLE_COLUMN_GROUP -> CSSValues.TABLE_COLUMN_GROUP;
                    case RenderState.DISPLAY_TABLE_ROW_GROUP -> CSSValues.TABLE_ROW_GROUP;
                    case RenderState.DISPLAY_INLINE_BLOCK -> CSSValues.INLINE_BLOCK;
                    case RenderState.DISPLAY_INLINE_TABLE -> CSSValues.INLINE_TABLE;
                    case RenderState.DISPLAY_FLEX_BOX -> CSSValues.FLEX;
                    default -> CSSValues.INLINE;
                };
            }
            return this.element.getParentNode() == null ? "" : display.getValue();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getElevation() {
        return this.element.getParentNode() == null ? null : style.getElevation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEmptyCells() {
        return this.element.getParentNode() == null ? null : style.getEmptyCells();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFont() {
        final StringBuilder font = new StringBuilder();
        final String fontStyle = style.getFontStyle();
        final String lineHeight = getLineHeight();
        final String fontSize = getFontSize();
        final String fontFamily = getFontFamily();

        if (Strings.isCssNotBlank(fontStyle) && !fontStyle.equals(CSSValues.NORMAL.getValue())) {
            font.append(CSSValues.ITALIC.getValue()).append(" ");
        }

        font.append(fontSize);

        if (Strings.isCssNotBlank(lineHeight) && !lineHeight.equals(CSSValues.NORMAL.getValue())) {
            font.append(" / ").append(lineHeight);
        }

        if (Strings.isCssNotBlank(fontFamily)) {
            font.append(" ").append(fontFamily);
        }

        return this.element.getParentNode() == null ? null : font.toString().trim();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontSize() {
        final HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();
        final CSSStyleDeclaration style = element.getStyle();
        final int fontSize;

        if (Strings.isCssBlank(style.getFontSize()) && parent != null && parent.getStyle().getLength() > 0) {
            final CSSStyleDeclaration currentStyle = parent.getStyle();
            fontSize = FontValues.getPixelSize(currentStyle.getFontSize(), null, window, -1);
        } else {
            fontSize = Float.valueOf(FontValues.getFontSize(style.getFontSize(), window, null)).intValue();
        }
        return this.element.getParentNode() == null ? null : (fontSize < 7 ? 6 : fontSize) + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontSizeAdjust() {
        return this.element.getParentNode() == null ? null : style.getFontSizeAdjust();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontStretch() {
        return this.element.getParentNode() == null ? null : style.getFontStretch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontStyle() {
        final String fontStyle = style.getFontStyle();
        if (Strings.isCssNotBlank(fontStyle)) {
            return this.element.getParentNode() == null ? null : !fontStyle.equals(CSSValues.NORMAL.getValue()) ? CSSValues.ITALIC.getValue() : fontStyle;
        } else {
            return this.element.getParentNode() == null ? null : CSSValues.NORMAL.getValue();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontVariant() {
        return this.element.getParentNode() == null ? null : FontValues.getFontVariant(style.getFontVariant());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontWeight() {
        final String font = FontValues.getFontWeight(style.getFontWeight(), null, false);
        return this.element.getParentNode() == null ? null : Strings.isBlank(font) ? CSSValues.BOLD400.getValue() : font;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFontFamily() {
        final CSSStyleDeclaration style = element.getStyle();
        return this.element.getParentNode() == null ? null : FontValues.getFontFamily(style.getFontFamily(), null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFlexDirection() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getFlexDirection()) ? CSSValues.NONE.getValue() : style.getFlexDirection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFlexWrap() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getFlexWrap()) ? CSSValues.NONE.getValue() : style.getFlexWrap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFlexFlow() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getFlexFlow()) ? CSSValues.NONE.getValue() : style.getFlexFlow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLineHeight() {
        final HTMLElementImpl parent = (HTMLElementImpl) element.getParentElement();
        final CSSStyleDeclaration style = element.getStyle();
        int lineHeight;

        if (Strings.isCssBlank(style.getLineHeight()) && parent != null && parent.getStyle().getLength() > 0) {
            final CSSStyleDeclaration currentStyle = parent.getStyle();
            lineHeight = FontValues.getPixelSize(currentStyle.getLineHeight(), element.getRenderState(), window, -1);
        } else {
            if (style.getLineHeight() == null || CSSValues.NORMAL.isEqual(style.getLineHeight())) {
                return CSSValues.NORMAL.getValue();
            } else {
                lineHeight = Float.valueOf(FontValues.getFontSize(style.getLineHeight(), window, element.getRenderState())).intValue();
            }
        }
        return this.element.getParentNode() == null ? null : lineHeight + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeight() {
        if (element.getParentNode() == null) {
            return "";
        }

        if (CSSValues.NONE.isEqual(style.getDisplay())) {
            return this.element.getParentNode() == null ? null : CSSValues.AUTO.getValue();
        }

        return this.element.getParentNode() == null ? "" : element.calculateHeight(false, false, true) + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJustifyContent() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getJustifyContent()) ? CSSValues.NONE.getValue() : style.getJustifyContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLeft() {
        if (Strings.isCssBlank(style.getLeft())) {
            if (element.getParentNode() != null && !(element.getParentNode() instanceof HTMLBodyElement)) {
                return "0px";
            } else if (element.getParentNode() != null && element.getParentNode() instanceof HTMLBodyElement) {
                return CSSValues.AUTO.getValue();
            } else {
                return "";
            }
        } else {
            return this.element.getParentNode() == null ? "" : HtmlValues.getPixelSize(style.getLeft(), element.getRenderState(), window, 0, availHeight) + "px";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLetterSpacing() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getLetterSpacing()) ? CSSValues.NORMAL.getValue() : style.getLetterSpacing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMargin() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getMargin()) ? "" : style.getMargin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginBottom() {
        final String cssMarginBottom = style.getMarginBottom();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginBottom))
            return this.element.getParentNode() == null ? null : CSSValues.NONE.getValue();
        final int marginBottom = HtmlValues.getPixelSize(cssMarginBottom, element.getRenderState(), window.getWindow(), -1, availHeight);
        return this.element.getParentNode() == null ? "" : marginBottom + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginLeft() {
        final String cssMarginLeft = style.getMarginLeft();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginLeft))
            return this.element.getParentNode() == null ? null : CSSValues.NONE.getValue();
        final int marginLeft = HtmlValues.getPixelSize(cssMarginLeft, element.getRenderState(), window.getWindow(), -1, availWidth);
        return this.element.getParentNode() == null ? "" : marginLeft + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginRight() {
        final String cssMarginRight = style.getMarginRight();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginRight)) return this.element.getParentNode() == null ? null : "0px";
        final int marginRight = HtmlValues.getPixelSize(cssMarginRight, element.getRenderState(), window.getWindow(), -1, availWidth);
        return this.element.getParentNode() == null ? "" : marginRight + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarginTop() {
        final String cssMarginTop = style.getMarginTop();
        if (element.getParentNode() == null) return "";
        if (Strings.isCssBlank(cssMarginTop))
            return this.element.getParentNode() == null ? null : CSSValues.NONE.getValue();
        final int marginTop = HtmlValues.getPixelSize(cssMarginTop, element.getRenderState(), window.getWindow(), -1, availHeight);
        return this.element.getParentNode() == null ? "" : marginTop + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarkerOffset() {
        return this.element.getParentNode() == null ? null : style.getMarkerOffset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarks() {
        return this.element.getParentNode() == null ? null : style.getMarks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaxHeight() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getMaxHeight()) ? CSSValues.NONE.getValue() : style.getMaxHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaxWidth() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getMaxWidth()) ? CSSValues.NONE.getValue() : style.getMaxWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinHeight() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getMinHeight()) ? CSSValues.NONE.getValue() : style.getMinHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMinWidth() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getMinWidth()) ? CSSValues.NONE.getValue() : style.getMinWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOpacity() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getOpacity()) ? "1" : style.getOpacity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutlineWidth() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getOutlineWidth()) ? CSSValues.NONE.getValue() : style.getOutlineWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPadding() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getPadding()) ? CSSValues.NONE.getValue() : style.getPadding();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingBottom() {
        final String cssPaddingBottom = style.getPaddingBottom();
        final int paddingBottom = HtmlValues.getPixelSize(cssPaddingBottom, element.getRenderState(), window.getWindow(), -1, availHeight);
        return this.element.getParentNode() == null ? null : paddingBottom + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingLeft() {
        final String cssPaddingLeft = style.getPaddingLeft();
        final int paddingLeft = HtmlValues.getPixelSize(cssPaddingLeft, element.getRenderState(), window.getWindow(), -1, availWidth);
        return this.element.getParentNode() == null ? null : paddingLeft + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingRight() {
        final String cssPaddingRight = style.getPaddingRight();
        final int paddingRight = HtmlValues.getPixelSize(cssPaddingRight, element.getRenderState(), window.getWindow(), -1, availWidth);
        return this.element.getParentNode() == null ? null : paddingRight + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPaddingTop() {
        final String cssPaddingTop = style.getPaddingTop();
        final int paddingTop = HtmlValues.getPixelSize(cssPaddingTop, element.getRenderState(), window.getWindow(), -1, availHeight);
        return this.element.getParentNode() == null ? null : paddingTop + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPage() {
        return this.element.getParentNode() == null ? null : style.getPage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPageBreakAfter() {
        return this.element.getParentNode() == null ? null : style.getPageBreakAfter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPageBreakBefore() {
        return this.element.getParentNode() == null ? null : style.getPageBreakBefore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPageBreakInside() {
        return this.element.getParentNode() == null ? null : style.getPageBreakInside();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPause() {
        return this.element.getParentNode() == null ? null : style.getPause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPauseAfter() {
        return this.element.getParentNode() == null ? null : style.getPauseAfter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPauseBefore() {
        return this.element.getParentNode() == null ? null : style.getPauseBefore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPitch() {
        return this.element.getParentNode() == null ? null : style.getPitch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPitchRange() {
        return this.element.getParentNode() == null ? null : style.getPitchRange();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayDuring() {
        return this.element.getParentNode() == null ? null : style.getPlayDuring();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRight() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getRight()) ? CSSValues.AUTO.getValue() : style.getRight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSize() {
        return this.element.getParentNode() == null ? null : style.getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpeak() {
        return this.element.getParentNode() == null ? null : style.getSpeak();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpeakHeader() {
        return this.element.getParentNode() == null ? null : style.getSpeakHeader();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpeakNumeral() {
        return this.element.getParentNode() == null ? null : style.getSpeakNumeral();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpeakPunctuation() {
        return this.element.getParentNode() == null ? null : style.getSpeakPunctuation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpeechRate() {
        return this.element.getParentNode() == null ? null : style.getSpeechRate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStress() {
        return this.element.getParentNode() == null ? null : style.getStress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTableLayout() {
        return this.element.getParentNode() == null ? null : style.getTableLayout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextAlign() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getTextAlign()) ? CSSValues.NONE.getValue() : style.getTextAlign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextIndent() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getTextIndent()) ? CSSValues.NONE.getValue() : style.getTextIndent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextShadow() {
        return this.element.getParentNode() == null ? null : style.getTextShadow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextTransform() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getTextTransform()) ? CSSValues.NONE.getValue() : style.getTextTransform();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTop() {
        if (Strings.isCssBlank(style.getTop())) {
            if (element.getParentNode() != null && !(element.getParentNode() instanceof HTMLBodyElement)) {
                return "0px";
            } else if (element.getParentNode() != null && element.getParentNode() instanceof HTMLBodyElement) {
                return CSSValues.AUTO.getValue();
            } else {
                return "";
            }
        } else {
            return this.element.getParentNode() == null ? "" : HtmlValues.getPixelSize(style.getTop(), element.getRenderState(), window, 0, availHeight) + "px";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUnicodeBidi() {
        return this.element.getParentNode() == null ? null : style.getUnicodeBidi();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTransform() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getTransform()) ? CSSValues.NONE.getValue() : style.getTransform();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVerticalAlign() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getVerticalAlign()) ? CSSValues.BASELINE.getValue() : style.getVerticalAlign();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVisibility() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getVisibility()) ? CSSValues.NONE.getValue() : style.getVisibility();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrphans() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getOrphans()) ? "2" : style.getOrphans();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutline() {
        return this.element.getParentNode() == null ? null : style.getOutline();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutlineColor() {
        return ColorFactory.getInstance().getColorString(this.element, style.getOutlineColor(), CSSValues.INITIAL.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOutlineStyle() {
        return this.element.getParentNode() == null ? null : style.getOutlineStyle();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getPosition() {
        final String position = style.getPosition();
        final CSSValues pos = CSSValues.get(position);
        return this.element.getParentNode() == null ? null : pos != null && pos != CSSValues.DEFAULT ? pos.getValue() : "static";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getQuotes() {
        return this.element.getParentNode() == null ? null : style.getQuotes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRichness() {
        return this.element.getParentNode() == null ? null : style.getRichness();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWidth() {
        if (element.getParentNode() == null) {
            return "";
        }

        if (CSSValues.NONE.isEqual(style.getDisplay())) {
            return this.element.getParentNode() == null ? "" : CSSValues.AUTO.getValue();
        }

        return this.element.getParentNode() == null ? "" : element.calculateWidth(false, false, false) + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWidows() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getWidows()) ? "2" : style.getWidows();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWordSpacing() {
        return this.element.getParentNode() == null ? null : style.getWordSpacing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAzimuth(final String azimuth) {
        style.setAzimuth(azimuth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackground(final String background) {
        style.setBackground(background);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundAttachment(final String backgroundAttachment) {
        style.setBackgroundAttachment(backgroundAttachment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundColor(final String backgroundColor) {
        style.setBackgroundColor(backgroundColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundImage(final String backgroundImage) {
        style.setBackgroundImage(backgroundImage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundPosition(final String backgroundPosition) {
        style.setBackgroundPosition(backgroundPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackgroundRepeat(final String backgroundRepeat) {
        style.setBackgroundRepeat(backgroundRepeat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorder(final String border) {
        style.setBorder(border);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderBottom(final String borderBottom) {
        style.setBorderBottom(borderBottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderBottomColor(final String borderBottomColor) {
        style.setBorderBottomColor(borderBottomColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderBottomStyle(final String borderBottomStyle) {
        style.setBorderBottomStyle(borderBottomStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderBottomWidth(final String borderBottomWidth) {
        style.setBorderBottomWidth(borderBottomWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderCollapse(final String borderCollapse) {
        style.setBorderCollapse(borderCollapse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderColor(final String borderColor) {
        style.setBorderColor(borderColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderLeft(final String borderLeft) {
        style.setBorderLeft(borderLeft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderLeftColor(final String borderLeftColor) {
        style.setBorderLeftColor(borderLeftColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderLeftStyle(final String borderLeftStyle) {
        style.setBorderLeftStyle(borderLeftStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderLeftWidth(final String borderLeftWidth) {
        style.setBorderLeftWidth(borderLeftWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderRight(final String borderRight) {
        style.setBorderRight(borderRight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderRightColor(final String borderRightColor) {
        style.setBorderRightColor(borderRightColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderRightStyle(final String borderRightStyle) {
        style.setBorderRightStyle(borderRightStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderRightWidth(final String borderRightWidth) {
        style.setBorderRightWidth(borderRightWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderSpacing(final String borderSpacing) {
        style.setBorderSpacing(borderSpacing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderStyle(final String borderStyle) {
        style.setBorderStyle(borderStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderTop(final String borderTop) {
        style.setBorderTop(borderTop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderTopColor(final String borderTopColor) {
        style.setBorderTopColor(borderTopColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderTopStyle(final String borderTopStyle) {
        style.setBorderTopStyle(borderTopStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderTopWidth(final String borderTopWidth) {
        style.setBorderTopWidth(borderTopWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBorderWidth(final String borderWidth) {
        style.setBorderWidth(borderWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBottom(final String bottom) {
        style.setBottom(bottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaptionSide(final String captionSide) {
        style.setCaptionSide(captionSide);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClear(final String clear) {
        style.setClear(clear);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClip(final String clip) {
        style.setClip(clip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClipPath(final String clip) {
        style.setClipPath(clip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClipRule(final String clip) {
        style.setClipRule(clip);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColor(final String color) {
        style.setColor(color);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContent(final String content) {
        style.setContent(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCounterIncrement(final String counterIncrement) {
        style.setCounterIncrement(counterIncrement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCounterReset(final String counterReset) {
        style.setCounterReset(counterReset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCssFloat(final String cssFloat) {
        style.setCssFloat(cssFloat);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCue(final String cue) {
        style.setCue(cue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCueAfter(final String cueAfter) {
        style.setCueAfter(cueAfter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCueBefore(final String cueBefore) {
        style.setCueBefore(cueBefore);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCursor(final String cursor) {
        style.setCursor(cursor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection(final String direction) {
        style.setDirection(direction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisplay(final String display) {
        style.setDisplay(display);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setElevation(final String elevation) {
        style.setElevation(elevation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEmptyCells(final String emptyCells) {
        style.setEmptyCells(emptyCells);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFill(final String value) {
        style.setFill(value);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFillOpacity(final String value) {
        style.setFillOpacity(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFloat(final String flt) {
        style.setFloat(flt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFont(final String font) {
        style.setFont(font);
    }

    /**
     * {@inheritDoc}
     */
    public void setFontFamily(final String fontFamily) {
        style.setFontFamily(fontFamily);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFontSize(final String fontSize) {
        style.setFontSize(fontSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFontSizeAdjust(final String fontSizeAdjust) {
        style.setFontSizeAdjust(fontSizeAdjust);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFontStretch(final String fontStretch) {
        style.setFontStretch(fontStretch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFontStyle(final String fontStyle) {
        style.setFontStyle(fontStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFontVariant(final String fontVariant) {
        style.setFontVariant(fontVariant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFontWeight(final String fontWeight) {
        style.setFontWeight(fontWeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWhiteSpace() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getWhiteSpace()) ? CSSValues.NONE.getValue() : style.getWhiteSpace();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getzIndex() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getzIndex()) ? CSSValues.AUTO.getValue() : style.getzIndex() + "px";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOverflow() {
        return this.element.getParentNode() == null ? null : style.getOverflow();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getBoxSizing() {
        return this.element.getParentNode() == null ? "" : style.getBoxSizing() == null ? CSSValues.CONTENT_BOX.getValue() : style.getBoxSizing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFill() {

        if (Strings.isBlank(style.getFill())) {
            return this.element.getParentNode() == null ? null : "rgb(0, 0, 0)";
        }

        if (style.getFill().toLowerCase().contains("url")) {
            return this.element.getParentNode() == null ? null : style.getFill();
        }

        final Color c = ColorFactory.getInstance().getColor(style.getFill());
        if (c != null) {
            if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 0) {
                return this.element.getParentNode() == null ? null : "rgb(0, 0, 0)";
            }
            final float alpha = (float) (c.getAlpha()) / 255.0f;
            if (alpha > 0 && alpha < 1) {
                return this.element.getParentNode() == null ? null : "rgba(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ", " + alpha + ")";
            } else {
                return this.element.getParentNode() == null ? null : "rgb(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
            }
        } else {
            return this.element.getParentNode() == null ? null : "rgb(0, 0, 0)";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFillOpacity() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getFillOpacity()) ? CSSValues.NONE.getValue() : style.getFillOpacity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStroke() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStroke()) ? CSSValues.NONE.getValue() : style.getStroke();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStrokeOpacity() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStrokeOpacity()) ? CSSValues.NONE.getValue() : style.getStrokeOpacity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStrokeLineCap() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStrokeLineCap()) ? CSSValues.NONE.getValue() : style.getStrokeLineCap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStrokeWidth() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStrokeWidth()) ? CSSValues.NONE.getValue() : style.getStrokeWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStrokeLineJoin() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStrokeLineJoin()) ? CSSValues.NONE.getValue() : style.getStrokeLineJoin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStrokeMiterLimit() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStrokeMiterLimit()) ? CSSValues.NONE.getValue() : style.getStrokeMiterLimit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClipPath() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getClipPath()) ? CSSValues.NONE.getValue() : style.getClipPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVoiceFamily() {
        return this.element.getParentNode() == null ? null : style.getVoiceFamily();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVolume() {
        return this.element.getParentNode() == null ? null : style.getVolume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClipRule() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getClipRule()) ? CSSValues.NONE.getValue() : style.getClipRule();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStopColor() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStopColor()) ? CSSValues.NONE.getValue() : style.getStopColor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStopOpacity() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStopOpacity()) ? CSSValues.NONE.getValue() : style.getStopOpacity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStrokeDashArray() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getStrokeDashArray()) ? CSSValues.NONE.getValue() : style.getStrokeDashArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFloat() {
        return this.element.getParentNode() == null ? null : style.getFloat();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListStyle() {
        return this.element.getParentNode() == null ? null : style.getListStyle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListStyleType() {
        return this.element.getParentNode() == null ? null : style.getListStyleType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListStyleImage() {
        return this.element.getParentNode() == null ? null : style.getListStyleImage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListStylePosition() {
        return this.element.getParentNode() == null ? null : style.getListStylePosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCaptionSide() {
        return this.element.getParentNode() == null ? null : style.getCaptionSide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTextDecoration() {
        return this.element.getParentNode() == null ? null : Strings.isBlank(style.getTextDecoration()) ? CSSValues.NONE.getValue() : style.getTextDecoration();
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoxSizing(String boxSizing) {
        style.setBoxSizing(boxSizing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeight(final String textAlign) {
        style.setHeight(textAlign);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLeft(final String left) {
        style.setLeft(left);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLineHeight(final String lineHeight) {
        style.setLineHeight(lineHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListStyle(final String listStyle) {
        style.setListStyle(listStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListStyleImage(final String listStyleImage) {
        style.setListStyleImage(listStyleImage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListStylePosition(final String listStylePosition) {
        style.setListStylePosition(listStylePosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListStyleType(final String listStyleType) {
        style.setListStyleType(listStyleType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMargin(final String margin) {
        style.setMargin(margin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarginBottom(final String marginBottom) {
        style.setMarginBottom(marginBottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarginLeft(final String marginLeft) {
        style.setMarginLeft(marginLeft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarginRight(final String marginRight) {
        style.setMarginRight(marginRight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarginTop(final String marginTop) {
        style.setMarginTop(marginTop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarkerOffset(final String markerOffset) {
        style.setMarkerOffset(markerOffset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarks(final String marks) {
        style.setMarks(marks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxHeight(final String maxHeight) {
        style.setMaxHeight(maxHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxWidth(final String maxWidth) {
        style.setMaxWidth(maxWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinHeight(final String minHeight) {
        style.setMinHeight(minHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinWidth(final String minWidth) {
        style.setMinWidth(minWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLetterSpacing(final String letterSpacing) {
        style.setLetterSpacing(letterSpacing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOpacity(final String value) {
        style.setOpacity(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrphans(final String orphans) {
        style.setOrphans(orphans);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutline(final String outline) {
        style.setOutline(outline);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutlineColor(final String outlineColor) {
        style.setOutlineColor(outlineColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutlineStyle(final String outlineStyle) {
        style.setOutlineStyle(outlineStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOutlineWidth(final String outlineWidth) {
        style.setOutlineWidth(outlineWidth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOverflow(final String overflow) {
        style.setOverflow(overflow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPadding(final String padding) {
        style.setPadding(padding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaddingBottom(final String paddingBottom) {
        style.setPaddingBottom(paddingBottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaddingLeft(final String paddingLeft) {
        style.setPaddingLeft(paddingLeft);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaddingRight(final String paddingRight) {
        style.setPaddingRight(paddingRight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPaddingTop(final String paddingTop) {
        style.setPaddingTop(paddingTop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPage(final String page) {
        style.setPage(page);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageBreakAfter(final String pageBreakAfter) {
        style.setPageBreakAfter(pageBreakAfter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageBreakBefore(final String pageBreakBefore) {
        style.setPageBreakBefore(pageBreakBefore);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPageBreakInside(final String pageBreakInside) {
        style.setPageBreakInside(pageBreakInside);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPause(final String pause) {
        style.setPause(pause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPauseAfter(final String pauseAfter) {
        style.setPauseAfter(pauseAfter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPauseBefore(final String pauseBefore) {
        style.setPauseBefore(pauseBefore);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPitch(final String pitch) {
        style.setPitch(pitch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPitchRange(final String pitchRange) {
        style.setPitchRange(pitchRange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayDuring(final String playDuring) {
        style.setPlayDuring(playDuring);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final String position) {
        style.setPosition(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setQuotes(final String quotes) {
        style.setQuotes(quotes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRichness(final String richness) {
        style.setRichness(richness);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRight(final String right) {
        style.setRight(right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSize(final String size) {
        style.setSize(size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeak(final String speak) {
        style.setSpeak(speak);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeakHeader(final String speakHeader) {
        style.setSpeakHeader(speakHeader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeakNumeral(final String speakNumeral) {
        style.setSpeakNumeral(speakNumeral);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeakPunctuation(final String speakPunctuation) {
        style.setSpeakPunctuation(speakPunctuation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeechRate(final String speechRate) {
        style.setSpeechRate(speechRate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStress(final String stress) {
        style.setStress(stress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStrokeWidth(final String value) {
        style.setStrokeWidth(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStrokeOpacity(final String value) {
        style.setStrokeOpacity(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStrokeMiterLimit(final String value) {
        style.setStrokeMiterLimit(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStrokeLineJoin(final String value) {
        style.setStrokeLineJoin(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStrokeDashArray(final String value) {
        style.setStrokeDashArray(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStroke(final String value) {
        style.setStroke(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStrokeLineCap(final String value) {
        style.setStrokeLineCap(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTableLayout(final String tableLayout) {
        style.setTableLayout(tableLayout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextAlign(final String textAlign) {
        style.setTextAlign(textAlign);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextDecoration(final String textDecoration) {
        style.setTextDecoration(textDecoration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextIndent(final String textIndent) {
        style.setTextIndent(textIndent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextShadow(final String textShadow) {
        style.setTextShadow(textShadow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextTransform(final String textTransform) {
        style.setTransform(textTransform);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTop(final String top) {
        style.setTop(top);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTransform(final String value) {
        style.setTransform(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUnicodeBidi(final String unicodeBidi) {
        style.setUnicodeBidi(unicodeBidi);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVerticalAlign(final String verticalAlign) {
        style.setVerticalAlign(verticalAlign);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisibility(final String visibility) {
        style.setVisibility(visibility);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVoiceFamily(final String voiceFamily) {
        style.setVoiceFamily(voiceFamily);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVolume(final String volume) {
        style.setVolume(volume);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWhiteSpace(final String whiteSpace) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWidows(final String widows) {
        style.setWidows(widows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWidth(final String width) {
        style.setWidth(width);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWordSpacing(final String wordSpacing) {
        style.setWordSpacing(wordSpacing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setzIndex(final String zIndex) {
        style.setzIndex(zIndex);
    }

    @Override
    public String getPropertyValue(final String property) {
        return style.getPropertyValue(property);
    }

    @Override
    public String toString() {
        return "[object CSSStyleDeclaration]";
    }
}