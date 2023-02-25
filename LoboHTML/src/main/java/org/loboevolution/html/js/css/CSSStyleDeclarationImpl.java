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
 */

package org.loboevolution.html.js.css;

import com.gargoylesoftware.css.dom.Property;
import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSSRule;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.setter.*;
import org.mozilla.javascript.annotations.JSFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CSSStyleDeclarationImpl implements CSSStyleDeclaration {

    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
                    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\d+))([eE][+-]?(\\p{Digit}+))?)|" +
                    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    private Pattern NUMERIC_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    private String overlayColor;
    private final HTMLElementImpl element;
    private final com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl style;

    private static Map<String, Boolean> propertyLenght = new HashMap<>();

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
     * @param style a {@link com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl} object.
     */
    public CSSStyleDeclarationImpl(HTMLElementImpl element, com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl style) {
        this.element = element;
        this.style = style;
    }

    /**
     * <p>Constructor for CSSStyleDeclarationImpl.</p>

     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     */
    public CSSStyleDeclarationImpl(HTMLElementImpl element) {
        this.element = element;
        this.style = new com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl();
    }

    /**
     * <p>Constructor for CSSStyleDeclarationImpl.</p>
     *
     * @param style a {@link com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl} object.
     */
    public CSSStyleDeclarationImpl(com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl style) {
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
    public String getPropertyValue(String property) {
        return style.getPropertyValue(property);
    }

    /** {@inheritDoc} */
    @Override
    @JSFunction
    public String getPropertyPriority(String property) {
        return style.getPropertyPriority(property);
    }

    public Property getPropertyDeclaration(String property) {
        return style.getPropertyDeclaration(property);
    }

    public boolean isPropertyPriority(String property) {
        return style.isPropertyPriority(property);
    }

    /** {@inheritDoc} */
    @JSFunction
    public void setProperty(final String propertyName, final String value) {
       this.setProperty(propertyName, value, "");
    }

    @Override
    public void setProperty(String propertyName, String value, String priority) {
        final String propertyPriority1 = getPropertyPriority(propertyName);

        if(Strings.isNotBlank(propertyPriority1)) return;

        if (Strings.isBlank(value) || "null".equals(value)) {
            if (Strings.isBlank(value)) value = "";
            style.setProperty(propertyName, value, priority);
        } else {
            value = value.toLowerCase().trim();

            if (HtmlValues.isUnits(value)) {
                final int val = HtmlValues.getPixelSize(value, null, null, -1);
                if (val > -1) {
                    style.setProperty(propertyName, value, priority);
                }
            } else if (propertyLenght.get(propertyName) != null && propertyLenght.get(propertyName)) {

                if (DOUBLE_PATTERN.matcher(value).matches()) {
                    final Double d = Double.parseDouble(value);
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
     * @param set a {@link java.lang.Boolean} object.
     */
    public final void setPropertyValueProcessed(String lowerCaseName, String value, boolean important, boolean set) {
        final SubPropertySetter setter = SUB_SETTERS.get(lowerCaseName);
        if (setter != null) {
            setter.changeValue(this, value);
        } else {
            if(set) setProperty(lowerCaseName, value, important ? "important" : "");
        }
    }

    public void merge(CSSStyleDeclarationImpl style) {
        List<Property> tmp = new ArrayList<>();
        List<Property> locals = getProperties();
        List<Property> styles = style.getProperties();

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
    public String item(int index) {
        return null;
    }
    @Override
    public String removeProperty(String property) {
        return style.removeProperty(property);
    }

    @Override
    public int getLength() {
        return style.getLength();
    }

    public List<Property> getProperties() {
        return style.getProperties();
    }

    @Override
    public CSSRule getParentRule() {
        return (CSSRule) style.getParentRule();
    }

    @Override
    public void setCssText(String text) {
        style.setCssText(text);
        this.element.setStyle(text);
    }

    @Override
    public void setOverlayColor(String value) {
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

        StringBuilder font = new StringBuilder();
        if(Strings.isBlank(fontSize)) return "";
        if (Strings.isNotBlank(fontStyle) && !fontStyle.equals(CSSValues.NORMAL.getValue())) font.append(fontStyle + " ");
        if (Strings.isNotBlank(fontVariant) && !fontVariant.equals(CSSValues.NORMAL.getValue())) font.append(fontVariant + " ");
        if (Strings.isNotBlank(fontWeight) && !fontWeight.equals(CSSValues.BOLD400.getValue())) font.append(fontWeight + " ");
        if (Strings.isNotBlank(fontLineHeight) && !fontLineHeight.equals(CSSValues.NORMAL.getValue())) font.append(fontSize + " / " + fontLineHeight + " "); else font.append(fontSize + " ");
        if (Strings.isNotBlank(fontFamily)) font.append(fontFamily + " ");
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
            CSSValues pos = CSSValues.get(position);
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
        int val = -1;
        String zIndex = this.getPropertyValue(Z_INDEX);

        if (Strings.isNotBlank(zIndex)) {
            if (zIndex.contains(".")) {
                int i = Integer.parseInt(zIndex.split("\\.")[1]);
                if (i == 0) {
                    val = HtmlValues.getPixelSize(zIndex, null, null, -1);
                }
            } else {
                val = HtmlValues.getPixelSize(zIndex, null, null, -1);
            }
        }
        return val == -1 ? "" : String.valueOf(val);
    }

    /** {@inheritDoc} */
    @Override
    public void setAzimuth(String azimuth) {
       this.setProperty(AZIMUTH, azimuth);
    }

    /** {@inheritDoc} */
    @Override
    public void setBackground(String background) {
        new BackgroundSetter().changeValue(this, background);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundAttachment(String backgroundAttachment) {
       this.setProperty(BACKGROUND_ATTACHMENT, backgroundAttachment);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundColor(String backgroundColor) {
       this.setProperty(BACKGROUND_COLOR, backgroundColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundPosition(String backgroundPosition) {
       this.setProperty(BACKGROUND_POSITION, backgroundPosition);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundImage(String backgroundImage) {
        new BackgroundImageSetter().changeValue(this, backgroundImage);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBackgroundRepeat(String backgroundRepeat) {
       this.setProperty(BACKGROUND_REPEAT, backgroundRepeat);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorder(String border) {
        new BorderSetter1().changeValue(this, border);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottom(String borderBottom) {
        new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottom);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottomColor(String borderBottomColor) {
       this.setProperty(BORDER_BOTTOM_COLOR, borderBottomColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottomStyle(String borderBottomStyle) {
        new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottomStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderBottomWidth(String borderBottomWidth) {
        this.setProperty(BORDER_BOTTOM_WIDTH, borderBottomWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderCollapse(String borderCollapse) {
       this.setProperty(BORDER_COLLAPSE, borderCollapse);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderColor(String borderColor) {
        new FourCornersSetter(BORDER_COLOR, "border-", "-color").changeValue(this, borderColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeft(String borderLeft) {
        new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeft);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeftColor(String borderLeftColor) {
       this.setProperty(BORDER_LEFT_COLOR, borderLeftColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeftStyle(String borderLeftStyle) {
        new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeftStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderLeftWidth(String borderLeftWidth) {
        this.setProperty(BORDER_LEFT_WIDTH, borderLeftWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRight(String borderRight) {
        new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRightColor(String borderRightColor) {
       this.setProperty(BORDER_RIGHT_COLOR, borderRightColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRightStyle(String borderRightStyle) {
        new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRightStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderRightWidth(String borderRightWidth) {
        this.setProperty(BORDER_RIGHT_WIDTH, borderRightWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderSpacing(String borderSpacing) {
       this.setProperty(BORDER_SPACING, borderSpacing);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderStyle(String borderStyle) {
        new FourCornersSetter(BORDER_STYLE, "border-", "-style").changeValue(this, borderStyle);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTop(String borderTop) {
        new BorderSetter2(BORDER_TOP).changeValue(this, borderTop);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTopColor(String borderTopColor) {
       this.setProperty(BORDER_TOP_COLOR, borderTopColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTopStyle(String borderTopStyle) {
        new BorderSetter2(BORDER_TOP).changeValue(this, borderTopStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderTopWidth(String borderTopWidth) {
        this.setProperty(BORDER_TOP_WIDTH, borderTopWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderWidth(String borderWidth) {
        new FourCornersSetter(BORDER_WIDTH, "border-", "-width").changeValue(this, borderWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setBottom(String bottom) {
        this.setProperty(BOTTOM, bottom);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCaptionSide(String captionSide) {
       this.setProperty(CAPTION_SIDE, captionSide);
    }

    /** {@inheritDoc} */
    @Override
    public void setClear(String clear) {
       this.setProperty(CLEAR, clear);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setClip(String clip) {
       this.setProperty(CLIP, clip);
    }

    /** {@inheritDoc} */
    @Override
    public void setClipPath(String clip) {
       this.setProperty(CLIP_PATH,clip);
    }

    /** {@inheritDoc} */
    @Override
    public void setClipRule(String clip) {
       this.setProperty(CLIP_RULE,clip);
    }

    /** {@inheritDoc} */
    @Override
    public void setColor(String color) {
       this.setProperty(COLOR, color);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setContent(String content) {
       this.setProperty(CONTENT, content);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCounterIncrement(String counterIncrement) {
       this.setProperty(COUNTER_INCREMENT, counterIncrement);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCounterReset(String counterReset) {
       this.setProperty(COUNTER_RESET, counterReset);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCssFloat(String cssFloat) {
       this.setProperty(CSS_FLOAT, cssFloat);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setCue(String cue) {
       this.setProperty(CUE, cue);
    }

    /** {@inheritDoc} */
    @Override
    public void setCueAfter(String cueAfter) {
       this.setProperty(CUE_AFTER, cueAfter);
    }

    /** {@inheritDoc} */
    @Override
    public void setCueBefore(String cueBefore) {
       this.setProperty(CUE_BEFORE, cueBefore);
    }

    /** {@inheritDoc} */
    @Override
    public void setCursor(String cursor) {
       this.setProperty(CURSOR, cursor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setDirection(String direction) {
       this.setProperty(DIRECTION, direction);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setDisplay(String display) {
       this.setProperty(DISPLAY, display);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setElevation(String elevation) {
       this.setProperty(ELEVATION, elevation);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setEmptyCells(String emptyCells) {
       this.setProperty(EMPTY_CELLS, emptyCells);
    }

    /** {@inheritDoc} */
    @Override
    public void setFont(String font) {
        new FontSetter().changeValue(this, font);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontFamily(String fontFamily) {
       this.setProperty(FONT_FAMILY, fontFamily);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontSize(String fontSize) {
        this.setProperty(FONT_SIZE, fontSize);
        this.element.informInvalid();
    }


    /** {@inheritDoc} */
    @Override
    public void setFontSizeAdjust(String fontSizeAdjust) {
       this.setProperty(FONT_SIZE_ADJUST, fontSizeAdjust);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontStretch(String fontStretch) {
       this.setProperty(FONT_STRETCH, fontStretch);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontStyle(String fontStyle) {
       this.setProperty(FONT_STYLE, fontStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontVariant(String fontVariant) {
       this.setProperty(FONT_VARIANT, fontVariant);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFontWeight(String fontWeight) {
       this.setProperty(FONT_WEIGHT, fontWeight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setFloat(String value) {
       this.setProperty(FLOAT, value);
    }

    /** {@inheritDoc} */
    @Override
    public void setHeight(String height) {
        this.setProperty(HEIGHT, height);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setLeft(String left) {
        this.setProperty(LEFT, left);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setLetterSpacing(String letterSpacing) {
        this.setProperty(LETTER_SPACING, letterSpacing);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setLineHeight(String lineHeight) {
       this.setProperty(LINE_HEIGHT, lineHeight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStyle(String listStyle) {
       this.setProperty(LIST_STYLE, listStyle);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStyleImage(String listStyleImage) {
       this.setProperty(LIST_STYLE_IMAGE, listStyleImage);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStylePosition(String listStylePosition) {
       this.setProperty(LIST_STYLE_POSITION, listStylePosition);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setListStyleType(String listStyleType) {
       this.setProperty(LIST_STYLE_TYPE, listStyleType);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMargin(String margin) {
        new FourCornersSetter(MARGIN, "margin-", "").changeValue(this, margin);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginBottom(String marginBottom) {
        this.setProperty(MARGIN_BOTTOM, marginBottom);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginLeft(String marginLeft) {
        this.setProperty(MARGIN_LEFT, marginLeft);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginRight(String marginRight) {
        this.setProperty(MARGIN_RIGHT, marginRight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarginTop(String marginTop) {
        this.setProperty(MARGIN_TOP, marginTop);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMarkerOffset(String markerOffset) {
       this.setProperty(MARKER_OFFSET, markerOffset);
    }

    /** {@inheritDoc} */
    @Override
    public void setMarks(String marks) {
       this.setProperty(MARKS, marks);
    }

    /** {@inheritDoc} */
    @Override
    public void setMaxHeight(String maxHeight) {
        this.setProperty(MAX_HEIGHT, maxHeight);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMaxWidth(String maxWidth) {
        this.setProperty(MAX_WIDTH, maxWidth);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMinHeight(String minHeight) {
        this.setProperty(MIN_HEIGHT, minHeight);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setMinWidth(String minWidth) {
        this.setProperty(MIN_WIDTH, minWidth);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOrphans(String orphans) {
       this.setProperty(ORPHANS, orphans);
    }

    /** {@inheritDoc} */
    @Override
    public void setOutline(String outline) {
       this.setProperty(OUTLINE, outline);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOutlineColor(String outlineColor) {
       this.setProperty(OUTLINE_COLOR, outlineColor);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOutlineStyle(String outlineStyle) {
       this.setProperty(OUTLINE_STYLE, outlineStyle);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOutlineWidth(String outlineWidth) {
        this.setProperty(OUTLINE_WIDTH, outlineWidth);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setOverflow(String overflow) {
       this.setProperty(OVERFLOW, overflow);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPadding(String padding) {
        new FourCornersSetter(PADDING, "padding-", "").changeValue(this, padding);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingBottom(String paddingBottom) {
        this.setProperty(PADDING_BOTTOM, paddingBottom);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingLeft(String paddingLeft) {
        this.setProperty(PADDING_LEFT, paddingLeft);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingRight(String paddingRight) {
        this.setProperty(PADDING_RIGHT, paddingRight);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPaddingTop(String paddingTop) {
        this.setProperty(PADDING_TOP, paddingTop);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPage(String page) {
       this.setProperty(PAGE, page);
    }

    /** {@inheritDoc} */
    @Override
    public void setPageBreakAfter(String pageBreakAfter) {
       this.setProperty(PAGE_BREAK_AFTER, pageBreakAfter);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPageBreakBefore(String pageBreakBefore) {
       this.setProperty(PAGE_BREAK_BEFORE, pageBreakBefore);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPageBreakInside(String pageBreakInside) {
       this.setProperty(PAGE_BREAK_INSIDE, pageBreakInside);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setPause(String pause) {
       this.setProperty(PAUSE, pause);
    }


    /** {@inheritDoc} */
    @Override
    public void setPauseAfter(String pauseAfter) {
       this.setProperty(PAUSE_AFTER, pauseAfter);
    }

    /** {@inheritDoc} */
    @Override
    public void setPauseBefore(String pauseBefore) {
       this.setProperty(PAUSE_BEFORE, pauseBefore);
    }

    /** {@inheritDoc} */
    @Override
    public void setPitch(String pitch) {
       this.setProperty(PITCH, pitch);
    }

    /** {@inheritDoc} */
    @Override
    public void setPitchRange(String pitchRange) {
       this.setProperty(PITCH_RANGE, pitchRange);
    }

    /** {@inheritDoc} */
    @Override
    public void setPlayDuring(String playDuring) {
       this.setProperty(PLAY_DURING, playDuring);
    }

    /** {@inheritDoc} */
    @Override
    public void setPosition(String position) {
       this.setProperty(POSITION, position);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setQuotes(String quotes) {
       this.setProperty(QUOTES, quotes);
    }

    /** {@inheritDoc} */
    @Override
    public void setRichness(String richness) {
       this.setProperty(RICHNESS, richness);
    }

    /** {@inheritDoc} */
    @Override
    public void setRight(String right) {
        this.setProperty(RIGHT, right);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setSize(String size) {
       this.setProperty(SIZE, size);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeak(String speak) {
       this.setProperty(SPEAK, speak);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeakHeader(String speakHeader) {
       this.setProperty(SPEAK_HEADER, speakHeader);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeakNumeral(String speakNumeral) {
       this.setProperty(SPEAK_NUMERAL, speakNumeral);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeakPunctuation(String speakPunctuation) {
       this.setProperty(SPEAK_PUNCTUATION, speakPunctuation);
    }

    /** {@inheritDoc} */
    @Override
    public void setSpeechRate(String speechRate) {
       this.setProperty(SPEECH_RATE, speechRate);
    }

    /** {@inheritDoc} */
    @Override
    public void setStress(String stress) {
       this.setProperty(STRESS, stress);
    }

    /** {@inheritDoc} */
    @Override
    public void setTableLayout(String tableLayout) {
       this.setProperty(TABLE_LAYOUT, tableLayout);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextAlign(String textAlign) {
       this.setProperty(TEXT_ALIGN, textAlign);
        this.element.informLayoutInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextDecoration(String textDecoration) {
       this.setProperty(TEXT_DECORATION, textDecoration);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextIndent(String textIndent) {
        this.setProperty(TEXT_INDENT, textIndent);
        this.element.informLayoutInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextShadow(String textShadow) {
       this.setProperty(TEXT_SHADOW, textShadow);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTextTransform(String textTransform) {
       this.setProperty(TEXT_TRANSFORM, textTransform);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setTop(String top) {
        this.setProperty(TOP, top);
        this.element.informPositionInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setUnicodeBidi(String unicodeBidi) {
       this.setProperty(UNICODE_BIDI, unicodeBidi);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setVerticalAlign(String verticalAlign) {
        this.setProperty(VERTICAL_ALIGN, verticalAlign);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibility(String visibility) {
       this.setProperty(VISIBILITY, visibility);
        this.element.informLookInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setVoiceFamily(String voiceFamily) {
       this.setProperty(VOICE_FAMILY, voiceFamily);
    }

    /** {@inheritDoc} */
    @Override
    public void setVolume(String volume) {
       this.setProperty(VOLUME, volume);
    }

    /** {@inheritDoc} */
    @Override
    public void setWhiteSpace(String whiteSpace) {
       this.setProperty(WHITE_SPACE, whiteSpace);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setWidows(String widows) {
       this.setProperty(WIDOWS, widows);
    }

    /** {@inheritDoc} */
    @Override
    public void setWidth(String width) {
        this.setProperty(WIDTH, width);
        this.element.informSizeInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setWordSpacing(String wordSpacing) {
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
    public void setFill(String value) {
       this.setProperty(FILL, value);
    }

    /** {@inheritDoc} */
    @Override
    public String getFillOpacity() {
        return this.getPropertyValue(FILL_OPACITY);
    }

    /** {@inheritDoc} */
    @Override
    public void setFillOpacity(String value) {
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
    public void setOpacity(String value) {
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
    public void setStroke(String value) {
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
    public void setStrokeDashArray(String value) {
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
    public void setStrokeLineCap(String value) {
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
    public void setStrokeLineJoin(String value) {
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
    public void setStrokeMiterLimit(String value) {
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
    public void setStrokeOpacity(String value) {
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
    public void setStrokeWidth(String value) {
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
    public void setTransform(String value) {
       this.setProperty(TRANSFORM, value);
        this.element.informInvalid();
    }

    /** {@inheritDoc} */
    @Override
    public void setzIndex(String zIndex) {
        if (Strings.isNotBlank(zIndex)) {
            if (zIndex.contains(".")) {
                int i = Integer.parseInt(zIndex.split("\\.")[1]);
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
