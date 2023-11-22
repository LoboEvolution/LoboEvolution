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

package org.loboevolution.html.js.css;

import org.htmlunit.cssparser.dom.AbstractCSSRuleImpl;
import org.htmlunit.cssparser.dom.Property;
import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSSRule;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.setter.*;
import org.mozilla.javascript.annotations.JSFunction;

import java.util.*;
import java.util.regex.Pattern;

public class CSSStyleDeclarationImpl implements CSSStyleDeclaration {

    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\d+)(\\.)?((\\d+)?)" +
                    "([eE][+-]?(\\d+))?)|(\\.((\\d+))([eE][+-]?(\\d+))?)|" +
                    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                    "[pP][+-]?(\\d+)))[fFdD]?))[\\x00-\\x20]*");

    private final Pattern NUMERIC_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    private String overlayColor;
    private final HTMLElementImpl element;
    private final org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl style;

    private static final Map<String, Boolean> propertyLenght = new HashMap<>();

    private static final Map<String, SubPropertySetter> SUB_SETTERS = new HashMap<>();

    static {
        final Map<String, SubPropertySetter> subSetters = SUB_SETTERS;
        subSetters.put(MARGIN, new FourCornersSetter(MARGIN, "margin-", ""));
        subSetters.put(PADDING, new FourCornersSetter(PADDING, "padding-", ""));
        subSetters.put(BORDER, new BorderSetter1());
        subSetters.put(BORDER_TOP, new BorderSetter2(BORDER_TOP));
        subSetters.put(BORDER_LEFT, new BorderSetter2(BORDER_LEFT));
        subSetters.put(BORDER_BOTTOM, new BorderSetter2(BORDER_BOTTOM));
        subSetters.put(BORDER_RIGHT, new BorderSetter2(BORDER_RIGHT));
        subSetters.put(BORDER_TOP_STYLE, new BorderSetter2(BORDER_TOP));
        subSetters.put(BORDER_LEFT_STYLE, new BorderSetter2(BORDER_LEFT));
        subSetters.put(BORDER_BOTTOM_STYLE, new BorderSetter2(BORDER_BOTTOM));
        subSetters.put(BORDER_RIGHT_STYLE, new BorderSetter2(BORDER_RIGHT));
        subSetters.put(BORDER_COLOR, new FourCornersSetter(BORDER_COLOR, "border-", "-color"));
        subSetters.put(BORDER_STYLE, new BorderStyleSetter(BORDER_STYLE, "border-", "-style"));
        subSetters.put(BORDER_WIDTH, new FourCornersSetter(BORDER_WIDTH, "border-", "-width"));
        subSetters.put(BACKGROUND, new BackgroundSetter());
        subSetters.put(BACKGROUND_IMAGE, new BackgroundImageSetter());
        subSetters.put(FONT, new FontSetter());

        propertyLenght.put(WIDTH, true);
        propertyLenght.put(HEIGHT, true);
        propertyLenght.put(TOP, true);
        propertyLenght.put(LEFT, true);
        propertyLenght.put(BOTTOM, true);
        propertyLenght.put(RIGHT, true);
        propertyLenght.put(MARGIN_TOP, true);
        propertyLenght.put(MARGIN_LEFT, true);
        propertyLenght.put(MARGIN_RIGHT, true);
        propertyLenght.put(MARGIN_BOTTOM, true);
        propertyLenght.put(PADDING_TOP, true);
        propertyLenght.put(PADDING_LEFT, true);
        propertyLenght.put(PADDING_RIGHT, true);
        propertyLenght.put(PADDING_BOTTOM, true);
        propertyLenght.put(BORDER_TOP_WIDTH, true);
        propertyLenght.put(BORDER_LEFT_WIDTH, true);
        propertyLenght.put(BORDER_RIGHT_WIDTH, true);
        propertyLenght.put(BORDER_BOTTOM_WIDTH, true);
        propertyLenght.put(MAX_WIDTH, true);
        propertyLenght.put(MIN_WIDTH, true);
        propertyLenght.put(MAX_HEIGHT, true);
        propertyLenght.put(MIN_HEIGHT, true);
        propertyLenght.put(TEXT_INDENT, true);
        propertyLenght.put(FONT_SIZE, true);
        propertyLenght.put(WORD_SPACING, true);
        propertyLenght.put(LETTER_SPACING, true);
        propertyLenght.put(VERTICAL_ALIGN, true);
        propertyLenght.put(OUTLINE_WIDTH, true);
        propertyLenght.put(Z_INDEX, true);
    }

    /**
     * <p>Constructor for CSSStyleDeclarationImpl.</p>
     *
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     * @param style a {@link org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl} object.
     */
    public CSSStyleDeclarationImpl(final HTMLElementImpl element, final org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl style) {
        this.element = element;
        this.style = style;
    }

    /**
     * <p>Constructor for CSSStyleDeclarationImpl.</p>

     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     */
    public CSSStyleDeclarationImpl(final HTMLElementImpl element) {
        this.element = element;
        this.style = new org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl();
    }

    /**
     * <p>Constructor for CSSStyleDeclarationImpl.</p>
     *
     * @param style a {@link org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl} object.
     */
    public CSSStyleDeclarationImpl(final org.htmlunit.cssparser.dom.CSSStyleDeclarationImpl style) {
        this.element = new HTMLElementImpl("");
        this.style = style;
    }

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        return style.getCssText();
    }

    /** {@inheritDoc} */
    @Override
    @JSFunction
    public String getPropertyValue(final String property) {
        return style.getPropertyValue(property);
    }

    /** {@inheritDoc} */
    @Override
    @JSFunction
    public String getPropertyPriority(final String property) {
        return style.getPropertyPriority(property);
    }

    public Property getPropertyDeclaration(final String property) {
        return style.getPropertyDeclaration(property);
    }

    public boolean isPropertyPriority(final String property) {
        return Strings.isNotBlank(style.getPropertyPriority(property));
    }

    @JSFunction
    public void setProperty(final String propertyName, final String value) {
       this.setProperty(propertyName, value, "");
    }

    @Override
    public void setProperty(final String propertyName, final String vl, final String priority) {
        final String propertyPriority1 = getPropertyPriority(propertyName);
        String value = vl;

        if(Strings.isNotBlank(propertyPriority1) ||
                (Strings.isNotBlank(priority) && !"important".equalsIgnoreCase(priority))) return;

        if (Strings.isBlank(value) || "null".equals(value)) {
            if (Strings.isBlank(value)) value = "";
            style.setProperty(propertyName, value, priority);
        } else {
            value = value.toLowerCase().trim();

            if (HtmlValues.isUnits(value)) {
                final int val = HtmlValues.getPixelSize(value, null, element.getOwnerDocument() != null ? element.getOwnerDocument().getDefaultView() : null, -1);
                if (val > -1) {
                    style.setProperty(propertyName, value, priority);
                }
            } else if (propertyLenght.get(propertyName) != null && propertyLenght.get(propertyName)) {

                if (DOUBLE_PATTERN.matcher(value).matches()) {
                    final double d = Double.parseDouble(value);
                    if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                        value += "px";
                        style.setProperty(propertyName, value, priority);
                    }
                } else if (NUMERIC_PATTERN.matcher(value).matches()) {
                    value += "px";
                    style.setProperty(propertyName, value, priority);
                } else if (CSSValues.AUTO.getValue().equals(value) ||
                        CSSValues.INHERIT.getValue().equals(value) ||
                        CSSValues.INITIAL.getValue().equals(value) ||
                        value.endsWith("%")) {
                    style.setProperty(propertyName, value, priority);
                } else if(CSSValues.THICK.isEqual(value) || CSSValues.THIN.isEqual(value) || CSSValues.MEDIUM.isEqual(value)) {
                    style.setProperty(propertyName, value, priority);
                }
            } else {
                style.setProperty(propertyName, value, priority);
            }
        }
    }

    /**
     * <p>setPropertyValueProcessed.</p>
     *
     * @param lowerCaseName a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @param important a {@link java.lang.Boolean} object.
     */
    public final void setPropertyValueProcessed(final String lowerCaseName, final String value, final boolean important) {
        final SubPropertySetter setter = SUB_SETTERS.get(lowerCaseName);
        if (setter != null) {
            setter.changeValue(this, value);
        } else {
            setProperty(lowerCaseName, value, important ? "important" : "");
        }
    }

    public void merge(final CSSStyleDeclarationImpl style) {
        final List<Property> tmp = new ArrayList<>();
        final List<Property> locals = getProperties();
        final List<Property> styles = style.getProperties();

        if (locals.size() == 0 && styles.size() > 0) {
            locals.addAll(styles);
        } else if (locals.size() > 0 && styles.size() > 0) {
            locals.forEach(prop -> {
                final String propertyValue2 = style.getPropertyValue(prop.getName());
                if (Strings.isBlank(propertyValue2)) {
                    tmp.add(prop);
                }

                if (Strings.isNotBlank(propertyValue2)) {
                    final boolean isImportant1 = prop.isImportant();
                    final boolean isImportant2 = style.isPropertyPriority(prop.getName());
                    if ((isImportant1 && isImportant2) || (!isImportant1 && !isImportant2)) {
                        tmp.add(prop);
                        styles.removeIf(p -> p.getName().equals(prop.getName()));
                    } else {
                        tmp.add(isImportant1 ? prop : style.getPropertyDeclaration(prop.getName()));

                    }
                }
            });

            tmp.addAll(styles);
            locals.clear();
            locals.addAll(tmp);
        }
    }

    @Override
    public String item(final int index) {
        try {
            final Property property = style.getProperties().get(index);
            return property.getName();
        } catch (final Exception e) {
            return null;
        }
    }

    @Override
    public String removeProperty(final String property) {
        final String prop = style.removeProperty(property);
        return prop;
    }

    @Override
    public int getLength() {
        return style.getLength();
    }

    public List<Property> getProperties() {
        return style.getProperties();
    }

    @Override
    public AbstractCSSRuleImpl getParentRule() {
        return (AbstractCSSRuleImpl) style.getParentRule();
    }

    @Override
    public void setCssText(final String text) {
        style.setCssText(text);
        this.element.setStyle(text);
    }

    @Override
    public void setOverlayColor(final String value) {
        this.overlayColor = value;
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getAlignItems() {
        return this.getPropertyValue(ALIGN_ITEMS);
    }

    /** {@inheritDoc} */
    @Override
    public String getAlignContent() {
        return this.getPropertyValue(ALIGN_CONTENT);
    }

    /** {@inheritDoc} */
    @Override
    public String getAzimuth() {
        return this.getPropertyValue(AZIMUTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getBackground() {
        return this.getPropertyValue(BACKGROUND);
    }

    /** {@inheritDoc} */
    @Override
    public String getBackgroundAttachment() {
        return this.getPropertyValue(BACKGROUND_ATTACHMENT);
    }

    /** {@inheritDoc} */
    @Override
    public String getBackgroundColor() {
        return this.getPropertyValue(BACKGROUND_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getBackgroundImage() {
        return this.getPropertyValue(BACKGROUND_IMAGE);
    }

    /** {@inheritDoc} */
    @Override
    public String getBackgroundPosition() {
        return this.getPropertyValue(BACKGROUND_POSITION);
    }

    /** {@inheritDoc} */
    @Override
    public String getBackgroundRepeat() {
        return this.getPropertyValue(BACKGROUND_REPEAT);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorder() {
        return this.getPropertyValue(BORDER);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderBottom() {
        return this.getPropertyValue(BORDER_BOTTOM);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderBottomColor() {
        return this.getPropertyValue(BORDER_BOTTOM_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getBoxSizing() {
        return this.getPropertyValue(BOX_SIZING);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderBottomStyle() {
        return this.getPropertyValue(BORDER_BOTTOM_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderBottomWidth() {
        return this.getPropertyValue(BORDER_BOTTOM_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderCollapse() {
        return this.getPropertyValue(BORDER_COLLAPSE);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderColor() {
        return this.getPropertyValue(BORDER_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderLeft() {
        return this.getPropertyValue(BORDER_LEFT);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderLeftColor() {
        return this.getPropertyValue(BORDER_LEFT_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderLeftStyle() {
        return this.getPropertyValue(BORDER_LEFT_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderLeftWidth() {
        return this.getPropertyValue(BORDER_LEFT_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderRight() {
        return this.getPropertyValue(BORDER_RIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderRightColor() {
        return this.getPropertyValue(BORDER_RIGHT_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderRightStyle() {
        return this.getPropertyValue(BORDER_RIGHT_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderRightWidth() {
        return this.getPropertyValue(BORDER_RIGHT_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderSpacing() {
        return this.getPropertyValue(BORDER_SPACING);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderStyle() {
        return this.getPropertyValue(BORDER_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderTop() {
        return this.getPropertyValue(BORDER_TOP);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderTopColor() {
        return this.getPropertyValue(BORDER_TOP_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderTopStyle() {
        return this.getPropertyValue(BORDER_TOP_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderTopWidth() {
        return this.getPropertyValue(BORDER_TOP_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getBorderWidth() {
        return this.getPropertyValue(BORDER_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getBottom() {
        return this.getPropertyValue(BOTTOM);
    }

    /** {@inheritDoc} */
    @Override
    public String getCaptionSide() {
        return this.getPropertyValue(CAPTION_SIDE);
    }

    /** {@inheritDoc} */
    @Override
    public String getClear() {
        return this.getPropertyValue(CLEAR);
    }

    /** {@inheritDoc} */
    @Override
    public String getClip() {
        return this.getPropertyValue(CLIP);
    }

    /** {@inheritDoc} */
    @Override
    public String getClipPath() {
        return this.getPropertyValue(CLIP_PATH);
    }

    /** {@inheritDoc} */
    @Override
    public String getClipRule() {
        return this.getPropertyValue(CLIP_RULE);
    }

    /** {@inheritDoc} */
    @Override
    public String getColor() {
        return this.getPropertyValue(COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getContent() {
        return this.getPropertyValue(CONTENT);
    }

    /** {@inheritDoc} */
    @Override
    public String getCounterIncrement() {
        return this.getPropertyValue(COUNTER_INCREMENT);
    }

    /** {@inheritDoc} */
    @Override
    public String getCounterReset() {
        return this.getPropertyValue(COUNTER_RESET);
    }

    /** {@inheritDoc} */
    @Override
    public String getCssFloat() {
        return this.getPropertyValue(CSS_FLOAT);
    }

    /** {@inheritDoc} */
    @Override
    public String getCue() {
        return this.getPropertyValue(CUE);
    }

    /** {@inheritDoc} */
    @Override
    public String getCueAfter() {
        return this.getPropertyValue(CUE_AFTER);
    }

    /** {@inheritDoc} */
    @Override
    public String getCueBefore() {
        return this.getPropertyValue(CUE_BEFORE);
    }

    /** {@inheritDoc} */
    @Override
    public String getCursor() {
        return this.getPropertyValue(CURSOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getDirection() {
        return this.getPropertyValue(DIRECTION);
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplay() {

        return this.getPropertyValue(DISPLAY);
    }

    /** {@inheritDoc} */
    @Override
    public String getElevation() {
        return this.getPropertyValue(ELEVATION);
    }

    /** {@inheritDoc} */
    @Override
    public String getEmptyCells() {
        return this.getPropertyValue(EMPTY_CELLS);
    }

    /** {@inheritDoc} */
    @Override
    public String getFont() {
        final String fontStyle = this.getPropertyValue(FONT_STYLE);
        final String fontVariant = this.getPropertyValue(FONT_VARIANT);
        final String fontWeight = this.getPropertyValue(FONT_WEIGHT);
        final String fontSize = this.getPropertyValue(FONT_SIZE);
        final String fontLineHeight = this.getPropertyValue(LINE_HEIGHT);
        final String fontFamily = this.getPropertyValue(FONT_FAMILY);

        final StringBuilder font = new StringBuilder();
        if(Strings.isBlank(fontSize)) return "";
        if (Strings.isNotBlank(fontStyle) && !fontStyle.equals(CSSValues.NORMAL.getValue())) font.append(fontStyle).append(" ");
        if (Strings.isNotBlank(fontVariant) && !fontVariant.equals(CSSValues.NORMAL.getValue())) font.append(fontVariant).append(" ");
        if (Strings.isNotBlank(fontWeight) && !fontWeight.equals(CSSValues.BOLD400.getValue())) font.append(fontWeight).append(" ");
        if (Strings.isNotBlank(fontLineHeight) && !fontLineHeight.equals(CSSValues.NORMAL.getValue())) font.append(fontSize).append(" / ").append(fontLineHeight).append(" "); else font.append(fontSize).append(" ");
        if (Strings.isNotBlank(fontFamily)) font.append(fontFamily).append(" ");
        return font.toString().trim();
    }

    /** {@inheritDoc} */
    @Override
    public String getFontFamily() {
        return this.getPropertyValue(FONT_FAMILY);
    }

    /** {@inheritDoc} */
    @Override
    public String getFontSize() {
        return this.getPropertyValue(FONT_SIZE);
    }

    /** {@inheritDoc} */
    @Override
    public String getFontSizeAdjust() {
        return this.getPropertyValue(FONT_SIZE_ADJUST);
    }

    /** {@inheritDoc} */
    @Override
    public String getFontStretch() {
        return this.getPropertyValue(FONT_STRETCH);
    }

    /** {@inheritDoc} */
    @Override
    public String getFontStyle() {
        return this.getPropertyValue(FONT_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getFontVariant() {
        return this.getPropertyValue(FONT_VARIANT);
    }

    /** {@inheritDoc} */
    @Override
    public String getFontWeight() {
        return this.getPropertyValue(FONT_WEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getFloat() {
        return this.getPropertyValue(FLOAT);
    }

    /** {@inheritDoc} */
    @Override
    public String getFlexDirection() {
        return this.getPropertyValue(FLEX_DIRECTION);
    }

    /** {@inheritDoc} */
    @Override
    public String getFlexWrap() {
        return this.getPropertyValue(FLEX_WRAP);
    }

    /** {@inheritDoc} */
    @Override
    public String getFlexFlow() {
        return this.getPropertyValue(FLEX_FLOW);
    }

    /** {@inheritDoc} */
    @Override
    public String getJustifyContent() {
        return this.getPropertyValue(JUSTIFY_CONTENT);
    }

    /** {@inheritDoc} */
    @Override
    public String getHeight() {
        return this.getPropertyValue(HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getLeft() {
        return this.getPropertyValue(LEFT);
    }

    /** {@inheritDoc} */
    @Override
    public String getLetterSpacing() {
        return this.getPropertyValue(LETTER_SPACING);
    }

    /** {@inheritDoc} */
    @Override
    public String getLineHeight() {
        return this.getPropertyValue(LINE_HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getListStyle() {
        return this.getPropertyValue(LIST_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getListStyleImage() {
        return this.getPropertyValue(LIST_STYLE_IMAGE);
    }

    /** {@inheritDoc} */
    @Override
    public String getListStylePosition() {
        return this.getPropertyValue(LIST_STYLE_POSITION);
    }

    /** {@inheritDoc} */
    @Override
    public String getListStyleType() {
        return this.getPropertyValue(LIST_STYLE_TYPE);
    }

    /** {@inheritDoc} */
    @Override
    public String getMargin() {
        return this.getPropertyValue(MARGIN);
    }

    /** {@inheritDoc} */
    @Override
    public String getMarginBottom() {
        return this.getPropertyValue(MARGIN_BOTTOM);
    }

    /** {@inheritDoc} */
    @Override
    public String getMarginLeft() {
        return this.getPropertyValue(MARGIN_LEFT);
    }

    /** {@inheritDoc} */
    @Override
    public String getMarginRight() {
        return this.getPropertyValue(MARGIN_RIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getMarginTop() {
        return this.getPropertyValue(MARGIN_TOP);
    }

    /** {@inheritDoc} */
    @Override
    public String getMarkerOffset() {
        return this.getPropertyValue(MARKER_OFFSET);
    }

    /** {@inheritDoc} */
    @Override
    public String getMarks() {
        return this.getPropertyValue(MARKS);
    }

    /** {@inheritDoc} */
    @Override
    public String getMaxHeight() {
        return this.getPropertyValue(MAX_HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getMaxWidth() {
        return this.getPropertyValue(MAX_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getMinHeight() {
        return this.getPropertyValue(MIN_HEIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getMinWidth() {
        return this.getPropertyValue(MIN_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getOrphans() {
        return this.getPropertyValue(ORPHANS);
    }

    /** {@inheritDoc} */
    @Override
    public String getOutline() {
        return this.getPropertyValue(OUTLINE);
    }

    /** {@inheritDoc} */
    @Override
    public String getOutlineColor() {
        return this.getPropertyValue(OUTLINE_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getOutlineStyle() {
        return this.getPropertyValue(OUTLINE_STYLE);
    }

    /** {@inheritDoc} */
    @Override
    public String getOutlineWidth() {
        return this.getPropertyValue(OUTLINE_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getOverflow() {
        return this.getPropertyValue(OVERFLOW);
    }

    /** {@inheritDoc} */
    @Override
    public String getPadding() {
        return this.getPropertyValue(PADDING);
    }

    /** {@inheritDoc} */
    @Override
    public String getPaddingBottom() {
        return this.getPropertyValue(PADDING_BOTTOM);
    }


    /** {@inheritDoc} */
    @Override
    public String getPaddingLeft() {
        return this.getPropertyValue(PADDING_LEFT);
    }

    /** {@inheritDoc} */
    @Override
    public String getPaddingRight() {
        return this.getPropertyValue(PADDING_RIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getPaddingTop() {
        return this.getPropertyValue(PADDING_TOP);
    }

    /** {@inheritDoc} */
    @Override
    public String getPage() {
        return this.getPropertyValue(PAGE);
    }

    /** {@inheritDoc} */
    @Override
    public String getPageBreakAfter() {
        return this.getPropertyValue(PAGE_BREAK_AFTER);
    }

    /** {@inheritDoc} */
    @Override
    public String getPageBreakBefore() {
        return this.getPropertyValue(PAGE_BREAK_BEFORE);
    }

    /** {@inheritDoc} */
    @Override
    public String getPageBreakInside() {
        return this.getPropertyValue(PAGE_BREAK_INSIDE);
    }

    /** {@inheritDoc} */
    @Override
    public String getPause() {
        return this.getPropertyValue(PAUSE);
    }

    /** {@inheritDoc} */
    @Override
    public String getPauseAfter() {
        return this.getPropertyValue(PAUSE_AFTER);
    }

    /** {@inheritDoc} */
    @Override
    public String getPauseBefore() {
        return this.getPropertyValue(PAUSE_BEFORE);
    }

    /** {@inheritDoc} */
    @Override
    public String getPitch() {
        return this.getPropertyValue(PITCH);
    }

    /** {@inheritDoc} */
    @Override
    public String getPitchRange() {
        return this.getPropertyValue(PITCH_RANGE);
    }

    /** {@inheritDoc} */
    @Override
    public String getPlayDuring() {
        return this.getPropertyValue(PLAY_DURING);
    }

    /** {@inheritDoc} */
    @Override
    public String getPosition() {
        final String position = this.getPropertyValue(POSITION);

        if(Strings.isNotBlank(position)) {
            final CSSValues pos = CSSValues.get(position);
            if(CSSValues.DEFAULT == pos){
                return null;
            } else{
                return position.toLowerCase();
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getQuotes() {
        return this.getPropertyValue(QUOTES);
    }

    /** {@inheritDoc} */
    @Override
    public String getRichness() {
        return this.getPropertyValue(RICHNESS);
    }

    /** {@inheritDoc} */
    @Override
    public String getRight() {
        return this.getPropertyValue(RIGHT);
    }

    /** {@inheritDoc} */
    @Override
    public String getSize() {
        return this.getPropertyValue(SIZE);
    }

    /** {@inheritDoc} */
    @Override
    public String getSpeak() {
        return this.getPropertyValue(SPEAK);
    }

    /** {@inheritDoc} */
    @Override
    public String getSpeakHeader() {
        return this.getPropertyValue(SPEAK_HEADER);
    }

    /** {@inheritDoc} */
    @Override
    public String getSpeakNumeral() {
        return this.getPropertyValue(SPEAK_NUMERAL);
    }

    /** {@inheritDoc} */
    @Override
    public String getSpeakPunctuation() {
        return this.getPropertyValue(SPEAK_PUNCTUATION);
    }

    /** {@inheritDoc} */
    @Override
    public String getSpeechRate() {
        return this.getPropertyValue(SPEECH_RATE);
    }

    /** {@inheritDoc} */
    @Override
    public String getStress() {
        return this.getPropertyValue(STRESS);
    }

    /** {@inheritDoc} */
    @Override
    public String getTableLayout() {
        return this.getPropertyValue(TABLE_LAYOUT);
    }

    /** {@inheritDoc} */
    @Override
    public String getTextAlign() {
        return this.getPropertyValue(TEXT_ALIGN);
    }


    /** {@inheritDoc} */
    @Override
    public String getTextDecoration() {
        return this.getPropertyValue(TEXT_DECORATION);
    }

    /** {@inheritDoc} */
    @Override
    public String getTextIndent() {
        return this.getPropertyValue(TEXT_INDENT);
    }

    /** {@inheritDoc} */
    @Override
    public String getTextShadow() {
        return this.getPropertyValue(TEXT_SHADOW);
    }

    /** {@inheritDoc} */
    @Override
    public String getTextTransform() {
        return this.getPropertyValue(TEXT_TRANSFORM);
    }

    /** {@inheritDoc} */
    @Override
    public String getTop() {
        return this.getPropertyValue(TOP);
    }

    /** {@inheritDoc} */
    @Override
    public String getUnicodeBidi() {
        return this.getPropertyValue(UNICODE_BIDI);
    }

    /** {@inheritDoc} */
    @Override
    public String getVerticalAlign() {
        return this.getPropertyValue(VERTICAL_ALIGN);
    }

    /** {@inheritDoc} */
    @Override
    public String getVisibility() {
        return this.getPropertyValue(VISIBILITY);
    }

    /** {@inheritDoc} */
    @Override
    public String getVoiceFamily() {
        return this.getPropertyValue(VOICE_FAMILY);
    }

    /** {@inheritDoc} */
    @Override
    public String getVolume() {
        return this.getPropertyValue(VOLUME);
    }

    /** {@inheritDoc} */
    @Override
    public String getWhiteSpace() {
        return this.getPropertyValue(WHITE_SPACE);
    }

    /** {@inheritDoc} */
    @Override
    public String getWidows() {
        return this.getPropertyValue(WIDOWS);
    }

    /** {@inheritDoc} */
    @Override
    public String getWidth() {
        return this.getPropertyValue(WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public String getWordSpacing() {
        return this.getPropertyValue(WORD_SPACING);
    }

    /** {@inheritDoc} */
    @Override
    public String getzIndex() {
        final String zIndex = this.getPropertyValue(Z_INDEX);
        final int val = HtmlValues.getPixelSize(zIndex, null, null, -1);
        return val == -1 ? "" : String.valueOf(val);
    }

    /** {@inheritDoc} */
    @Override
    public void setAzimuth(final String azimuth) {
       this.setProperty(AZIMUTH, azimuth);
    }

    /** {@inheritDoc} */
    @Override
    public void setBackground(final String background) {
        new BackgroundSetter().changeValue(this, background);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundAttachment(final String backgroundAttachment) {
       this.setProperty(BACKGROUND_ATTACHMENT, backgroundAttachment);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundColor(final String backgroundColor) {
       this.setProperty(BACKGROUND_COLOR, backgroundColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundPosition(final String backgroundPosition) {
       this.setProperty(BACKGROUND_POSITION, backgroundPosition);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundImage(final String backgroundImage) {
        new BackgroundImageSetter().changeValue(this, backgroundImage);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundRepeat(final String backgroundRepeat) {
       this.setProperty(BACKGROUND_REPEAT, backgroundRepeat);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorder(final String border) {
        new BorderSetter1().changeValue(this, border);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottom(final String borderBottom) {
        new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottom);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottomColor(final String borderBottomColor) {
       this.setProperty(BORDER_BOTTOM_COLOR, borderBottomColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottomStyle(final String borderBottomStyle) {
        new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottomStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottomWidth(final String borderBottomWidth) {
        this.setProperty(BORDER_BOTTOM_WIDTH, borderBottomWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderCollapse(final String borderCollapse) {
       this.setProperty(BORDER_COLLAPSE, borderCollapse);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderColor(final String borderColor) {
        new FourCornersSetter(BORDER_COLOR, "border-", "-color").changeValue(this, borderColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeft(final String borderLeft) {
        new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeft);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeftColor(final String borderLeftColor) {
       this.setProperty(BORDER_LEFT_COLOR, borderLeftColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeftStyle(final String borderLeftStyle) {
        new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeftStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeftWidth(final String borderLeftWidth) {
        this.setProperty(BORDER_LEFT_WIDTH, borderLeftWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRight(final String borderRight) {
        new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRightColor(final String borderRightColor) {
       this.setProperty(BORDER_RIGHT_COLOR, borderRightColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRightStyle(final String borderRightStyle) {
        new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRightStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRightWidth(final String borderRightWidth) {
        this.setProperty(BORDER_RIGHT_WIDTH, borderRightWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderSpacing(final String borderSpacing) {
       this.setProperty(BORDER_SPACING, borderSpacing);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderStyle(final String borderStyle) {
        new FourCornersSetter(BORDER_STYLE, "border-", "-style").changeValue(this, borderStyle);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTop(final String borderTop) {
        new BorderSetter2(BORDER_TOP).changeValue(this, borderTop);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTopColor(final String borderTopColor) {
       this.setProperty(BORDER_TOP_COLOR, borderTopColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTopStyle(final String borderTopStyle) {
        new BorderSetter2(BORDER_TOP).changeValue(this, borderTopStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTopWidth(final String borderTopWidth) {
        this.setProperty(BORDER_TOP_WIDTH, borderTopWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderWidth(final String borderWidth) {
        new FourCornersSetter(BORDER_WIDTH, "border-", "-width").changeValue(this, borderWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBottom(final String bottom) {
        this.setProperty(BOTTOM, bottom);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCaptionSide(final String captionSide) {
       this.setProperty(CAPTION_SIDE, captionSide);
    }

    /** {@inheritDoc} */
    @Override
    public void setClear(final String clear) {
       this.setProperty(CLEAR, clear);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setClip(final String clip) {
       this.setProperty(CLIP, clip);
    }

    /** {@inheritDoc} */
    @Override
    public void setClipPath(final String clip) {
       this.setProperty(CLIP_PATH,clip);
    }

    /** {@inheritDoc} */
    @Override
    public void setClipRule(final String clip) {
       this.setProperty(CLIP_RULE,clip);
    }

    /** {@inheritDoc} */
    @Override
    public void setColor(final String color) {
        this.setProperty(COLOR, color);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setContent(final String content) {
       this.setProperty(CONTENT, content);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCounterIncrement(final String counterIncrement) {
       this.setProperty(COUNTER_INCREMENT, counterIncrement);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCounterReset(final String counterReset) {
       this.setProperty(COUNTER_RESET, counterReset);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCssFloat(final String cssFloat) {
       this.setProperty(CSS_FLOAT, cssFloat);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCue(final String cue) {
       this.setProperty(CUE, cue);
    }

    /** {@inheritDoc} */
    @Override
    public void setCueAfter(final String cueAfter) {
       this.setProperty(CUE_AFTER, cueAfter);
    }

    /** {@inheritDoc} */
    @Override
    public void setCueBefore(final String cueBefore) {
       this.setProperty(CUE_BEFORE, cueBefore);
    }

    /** {@inheritDoc} */
    @Override
    public void setCursor(final String cursor) {
       this.setProperty(CURSOR, cursor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setDirection(final String direction) {
       this.setProperty(DIRECTION, direction);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setDisplay(final String display) {
       this.setProperty(DISPLAY, display);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setElevation(final String elevation) {
       this.setProperty(ELEVATION, elevation);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setEmptyCells(final String emptyCells) {
       this.setProperty(EMPTY_CELLS, emptyCells);
    }

    /** {@inheritDoc} */
    @Override
    public void setFont(final String font) {
        new FontSetter().changeValue(this, font);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontFamily(final String fontFamily) {
       this.setProperty(FONT_FAMILY, fontFamily);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontSize(final String fontSize) {
        this.setProperty(FONT_SIZE, fontSize);
        this.element.informInvalid();
    }


    /** {@inheritDoc} */
    @Override
    public void setFontSizeAdjust(final String fontSizeAdjust) {
       this.setProperty(FONT_SIZE_ADJUST, fontSizeAdjust);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontStretch(final String fontStretch) {
       this.setProperty(FONT_STRETCH, fontStretch);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontStyle(final String fontStyle) {
       this.setProperty(FONT_STYLE, fontStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontVariant(final String fontVariant) {
       this.setProperty(FONT_VARIANT, fontVariant);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontWeight(final String fontWeight) {
       this.setProperty(FONT_WEIGHT, fontWeight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFloat(final String value) {
       this.setProperty(FLOAT, value);
    }

    /** {@inheritDoc} */
    @Override
    public void setHeight(final String height) {
        this.setProperty(HEIGHT, height);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setLeft(final String left) {
        this.setProperty(LEFT, left);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setLetterSpacing(final String letterSpacing) {
        this.setProperty(LETTER_SPACING, letterSpacing);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setLineHeight(final String lineHeight) {
       this.setProperty(LINE_HEIGHT, lineHeight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStyle(final String listStyle) {
       this.setProperty(LIST_STYLE, listStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStyleImage(final String listStyleImage) {
       this.setProperty(LIST_STYLE_IMAGE, listStyleImage);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStylePosition(final String listStylePosition) {
       this.setProperty(LIST_STYLE_POSITION, listStylePosition);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStyleType(final String listStyleType) {
       this.setProperty(LIST_STYLE_TYPE, listStyleType);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMargin(final String margin) {
        new FourCornersSetter(MARGIN, "margin-", "").changeValue(this, margin);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginBottom(final String marginBottom) {
        this.setProperty(MARGIN_BOTTOM, marginBottom);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginLeft(final String marginLeft) {
        this.setProperty(MARGIN_LEFT, marginLeft);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginRight(final String marginRight) {
        this.setProperty(MARGIN_RIGHT, marginRight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginTop(final String marginTop) {
        this.setProperty(MARGIN_TOP, marginTop);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarkerOffset(final String markerOffset) {
       this.setProperty(MARKER_OFFSET, markerOffset);
    }

    /** {@inheritDoc} */
    @Override
    public void setMarks(final String marks) {
       this.setProperty(MARKS, marks);
    }

    /** {@inheritDoc} */
    @Override
    public void setMaxHeight(final String maxHeight) {
        this.setProperty(MAX_HEIGHT, maxHeight);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMaxWidth(final String maxWidth) {
        this.setProperty(MAX_WIDTH, maxWidth);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMinHeight(final String minHeight) {
        this.setProperty(MIN_HEIGHT, minHeight);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMinWidth(final String minWidth) {
        this.setProperty(MIN_WIDTH, minWidth);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOrphans(final String orphans) {
        if (Strings.isNotBlank(orphans) && Integer.parseInt(orphans) > 0) {
            this.setProperty(ORPHANS, orphans);
            this.element.informInvalid();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setOutline(final String outline) {
       this.setProperty(OUTLINE, outline);
       this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOutlineColor(final String outlineColor) {
       this.setProperty(OUTLINE_COLOR, outlineColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOutlineStyle(final String outlineStyle) {
       this.setProperty(OUTLINE_STYLE, outlineStyle);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOutlineWidth(final String outlineWidth) {
        this.setProperty(OUTLINE_WIDTH, outlineWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOverflow(final String overflow) {
       this.setProperty(OVERFLOW, overflow);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPadding(final String padding) {
        new FourCornersSetter(PADDING, "padding-", "").changeValue(this, padding);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingBottom(final String paddingBottom) {
        this.setProperty(PADDING_BOTTOM, paddingBottom);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingLeft(final String paddingLeft) {
        this.setProperty(PADDING_LEFT, paddingLeft);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingRight(final String paddingRight) {
        this.setProperty(PADDING_RIGHT, paddingRight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingTop(final String paddingTop) {
        this.setProperty(PADDING_TOP, paddingTop);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPage(final String page) {
       this.setProperty(PAGE, page);
    }

    /** {@inheritDoc} */
    @Override
    public void setPageBreakAfter(final String pageBreakAfter) {
       this.setProperty(PAGE_BREAK_AFTER, pageBreakAfter);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPageBreakBefore(final String pageBreakBefore) {
       this.setProperty(PAGE_BREAK_BEFORE, pageBreakBefore);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPageBreakInside(final String pageBreakInside) {
       this.setProperty(PAGE_BREAK_INSIDE, pageBreakInside);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPause(final String pause) {
       this.setProperty(PAUSE, pause);
    }


    /** {@inheritDoc} */
    @Override
    public void setPauseAfter(final String pauseAfter) {
       this.setProperty(PAUSE_AFTER, pauseAfter);
    }

    /** {@inheritDoc} */
    @Override
    public void setPauseBefore(final String pauseBefore) {
       this.setProperty(PAUSE_BEFORE, pauseBefore);
    }

    /** {@inheritDoc} */
    @Override
    public void setPitch(final String pitch) {
       this.setProperty(PITCH, pitch);
    }

    /** {@inheritDoc} */
    @Override
    public void setPitchRange(final String pitchRange) {
       this.setProperty(PITCH_RANGE, pitchRange);
    }

    /** {@inheritDoc} */
    @Override
    public void setPlayDuring(final String playDuring) {
       this.setProperty(PLAY_DURING, playDuring);
    }

    /** {@inheritDoc} */
    @Override
    public void setPosition(final String position) {
       this.setProperty(POSITION, position);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setQuotes(final String quotes) {
       this.setProperty(QUOTES, quotes);
    }

    /** {@inheritDoc} */
    @Override
    public void setRichness(final String richness) {
       this.setProperty(RICHNESS, richness);
    }

    /** {@inheritDoc} */
    @Override
    public void setRight(final String right) {
        this.setProperty(RIGHT, right);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(final String size) {
       this.setProperty(SIZE, size);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeak(final String speak) {
       this.setProperty(SPEAK, speak);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeakHeader(final String speakHeader) {
       this.setProperty(SPEAK_HEADER, speakHeader);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeakNumeral(final String speakNumeral) {
       this.setProperty(SPEAK_NUMERAL, speakNumeral);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeakPunctuation(final String speakPunctuation) {
       this.setProperty(SPEAK_PUNCTUATION, speakPunctuation);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeechRate(final String speechRate) {
       this.setProperty(SPEECH_RATE, speechRate);
    }

    /** {@inheritDoc} */
    @Override
    public void setStress(final String stress) {
       this.setProperty(STRESS, stress);
    }

    /** {@inheritDoc} */
    @Override
    public void setTableLayout(final String tableLayout) {
       this.setProperty(TABLE_LAYOUT, tableLayout);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextAlign(final String textAlign) {
       this.setProperty(TEXT_ALIGN, textAlign);
        this.element.informLayoutInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextDecoration(final String textDecoration) {
       this.setProperty(TEXT_DECORATION, textDecoration);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextIndent(final String textIndent) {
        this.setProperty(TEXT_INDENT, textIndent);
        this.element.informLayoutInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextShadow(final String textShadow) {
       this.setProperty(TEXT_SHADOW, textShadow);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextTransform(final String textTransform) {
       this.setProperty(TEXT_TRANSFORM, textTransform);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTop(final String top) {
        this.setProperty(TOP, top);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setUnicodeBidi(final String unicodeBidi) {
       this.setProperty(UNICODE_BIDI, unicodeBidi);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setVerticalAlign(final String verticalAlign) {
        this.setProperty(VERTICAL_ALIGN, verticalAlign);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibility(final String visibility) {
       this.setProperty(VISIBILITY, visibility);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setVoiceFamily(final String voiceFamily) {
       this.setProperty(VOICE_FAMILY, voiceFamily);
    }

    /** {@inheritDoc} */
    @Override
    public void setVolume(final String volume) {
       this.setProperty(VOLUME, volume);
    }

    /** {@inheritDoc} */
    @Override
    public void setWhiteSpace(final String whiteSpace) {
       this.setProperty(WHITE_SPACE, whiteSpace);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setWidows(final String widows) {
       this.setProperty(WIDOWS, widows);
    }

    /** {@inheritDoc} */
    @Override
    public void setWidth(final String width) {
        this.setProperty(WIDTH, width);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setWordSpacing(final String wordSpacing) {
        this.setProperty(WORD_SPACING, wordSpacing);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getFill() {
        return this.getPropertyValue(FILL);
    }

    /** {@inheritDoc} */
    @Override
    public void setFill(final String value) {
       this.setProperty(FILL, value);
    }

    /** {@inheritDoc} */
    @Override
    public String getFillOpacity() {
        return this.getPropertyValue(FILL_OPACITY);
    }

    /** {@inheritDoc} */
    @Override
    public void setFillOpacity(final String value) {
       this.setProperty(FILL_OPACITY, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getOpacity() {
        return this.getPropertyValue(OPACITY);
    }

    /** {@inheritDoc} */
    @Override
    public void setOpacity(final String value) {
       this.setProperty(OPACITY, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getStopColor() {
        return this.getPropertyValue(STOP_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public String getStopOpacity() {
        return this.getPropertyValue(STOP_OPACITY);
    }

    /** {@inheritDoc} */
    @Override
    public String getStroke() {
        return this.getPropertyValue(STROKE);
    }

    /** {@inheritDoc} */
    @Override
    public void setStroke(final String value) {
       this.setProperty(STROKE, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getStrokeDashArray() {
        return this.getPropertyValue(STROKE_DASHARRAY);
    }

    /** {@inheritDoc} */
    @Override
    public void setStrokeDashArray(final String value) {
       this.setProperty(STROKE_DASHARRAY, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getStrokeLineCap() {
        return this.getPropertyValue(STROKE_LINE_CAP);
    }

    /** {@inheritDoc} */
    @Override
    public void setStrokeLineCap(final String value) {
       this.setProperty(STROKE_LINE_CAP, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getStrokeLineJoin() {
        return this.getPropertyValue(STROKE_LINE_JOINP);
    }

    /** {@inheritDoc} */
    @Override
    public void setStrokeLineJoin(final String value) {
       this.setProperty(STROKE_LINE_JOINP, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getStrokeMiterLimit() {
        return this.getPropertyValue(STROKE_MITERLIMIT);
    }

    /** {@inheritDoc} */
    @Override
    public void setStrokeMiterLimit(final String value) {
       this.setProperty(STROKE_MITERLIMIT, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getStrokeOpacity() {
        return this.getPropertyValue(STROKE_OPACITY);
    }

    /** {@inheritDoc} */
    @Override
    public void setStrokeOpacity(final String value) {
       this.setProperty(STROKE_OPACITY, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getStrokeWidth() {
        return this.getPropertyValue(STROKE_WIDTH);
    }

    /** {@inheritDoc} */
    @Override
    public void setStrokeWidth(final String value) {
       this.setProperty(STROKE_WIDTH, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public String getTransform() {
        return this.getPropertyValue(TRANSFORM);
    }


    /** {@inheritDoc} */
    @Override
    public String getOverlayColor() {
        return this.overlayColor;
    }

    public HTMLElementImpl getContext() {
        return  this.element;
    }

    /** {@inheritDoc} */
    @Override
    public void setTransform(final String value) {
       this.setProperty(TRANSFORM, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setzIndex(final String zIndex) {
        if (Strings.isNotBlank(zIndex)) {
            if (zIndex.contains(".")) {
                final int i = Integer.parseInt(zIndex.split("\\.")[1]);
                if (i == 0) {
                    this.setProperty(Z_INDEX, zIndex);
                    this.element.informPositionInvalid();
                }
            } else {
                this.setProperty(Z_INDEX, zIndex);
                this.element.informPositionInvalid();
            }
        } else {
            this.setProperty(Z_INDEX, zIndex);
            this.element.informPositionInvalid();
        }
    }

    @Override
    public String toString() {
        return "[object CSSStyleDeclaration]";
    }
}
