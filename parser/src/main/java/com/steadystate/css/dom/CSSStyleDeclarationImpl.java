/*
 * Copyright (C) 1999-2017 David Schweinsberg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.steadystate.css.dom;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSS2Properties;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.CSSProperties;
import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link CSSStyleDeclaration}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 * @author Ahmed Ashour
 */
public class CSSStyleDeclarationImpl implements CSSStyleDeclaration, CSSFormatable, CSS2Properties,CSSProperties, Serializable {
	private static final long serialVersionUID = -2373755821317100189L;

    private static final String PRIORITY_IMPORTANT = "important";

    private CSSRule parentRule_;
    private List<Property> properties_ = new ArrayList<Property>();

    public void setParentRule(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    public List<Property> getProperties() {
        return properties_;
    }

    public void setProperties(final List<Property> properties) {
        properties_ = properties;
    }

    public CSSStyleDeclarationImpl(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    public CSSStyleDeclarationImpl() {
        // Empty.
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText() {
        return getCssText(null);
    }

    /**
     * {@inheritDoc}
     */
    public String getCssText(final CSSFormat format) {
        final boolean nl = format != null && format.getPropertiesInSeparateLines();

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < properties_.size(); ++i) {
            final Property p = properties_.get(i);
            if (p != null) {
                if (nl) {
                    sb.append(format.getNewLine());
                    sb.append(format.getPropertiesIndent());
                }
                sb.append(p.getCssText(format));
            }
            if (i < properties_.size() - 1) {
                sb.append(";");
                if (!nl) {
                    sb.append(' ');
                }
            }
            else if (nl) {
                sb.append(format.getNewLine());
            }
        }
        return sb.toString();
    }

    public void setCssText(final String cssText) throws DOMException {
        try {
            final InputSource is = new InputSource(new StringReader(cssText));
            final CSSOMParser parser = new CSSOMParser();
            properties_.clear();
            parser.parseStyleDeclaration(this, is);
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    public String getPropertyValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        if (p == null || p.getValue() == null) {
            return "";
        }
        return p.getValue().toString();
    }

    public CSSValue getPropertyCSSValue(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        return (p == null) ? null : p.getValue();
    }

    public String removeProperty(final String propertyName) throws DOMException {
        if (null == propertyName) {
            return "";
        }
        for (int i = 0; i < properties_.size(); i++) {
            final Property p = properties_.get(i);
            if (p != null && propertyName.equalsIgnoreCase(p.getName())) {
                properties_.remove(i);
                if (p.getValue() == null) {
                    return "";
                }
                return p.getValue().toString();
            }
        }
        return "";
    }

    public String getPropertyPriority(final String propertyName) {
        final Property p = getPropertyDeclaration(propertyName);
        if (p == null) {
            return "";
        }
        return p.isImportant() ? PRIORITY_IMPORTANT : "";
    }

    public void setProperty(
            final String propertyName,
            final String value,
            final String priority) throws DOMException {
        try {
            CSSValue expr = null;
            if (!value.isEmpty()) {
                final CSSOMParser parser = new CSSOMParser();
                final InputSource is = new InputSource(new StringReader(value));
                expr = parser.parsePropertyValue(is);
            }
            Property p = getPropertyDeclaration(propertyName);
            final boolean important = PRIORITY_IMPORTANT.equalsIgnoreCase(priority);
            if (p == null) {
                p = new Property(propertyName, expr, important);
                addProperty(p);
            }
            else {
                p.setValue(expr);
                p.setImportant(important);
            }
        }
        catch (final Exception e) {
            throw new DOMExceptionImpl(
                    DOMException.SYNTAX_ERR,
                    DOMExceptionImpl.SYNTAX_ERROR,
                    e.getMessage());
        }
    }

    public int getLength() {
        return properties_.size();
    }

    public String item(final int index) {
        final Property p = properties_.get(index);
        return (p == null) ? "" : p.getName();
    }

    public CSSRule getParentRule() {
        return parentRule_;
    }

    public void addProperty(final Property p) {
        if (null == p) {
            return;
        }
        properties_.add(p);
    }

    public Property getPropertyDeclaration(final String propertyName) {
        if (null == propertyName) {
            return null;
        }
        for (int i = properties_.size() - 1; i > -1; i--) {
            final Property p = properties_.get(i);
            if (p != null && propertyName.equalsIgnoreCase(p.getName())) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getCssText();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSStyleDeclaration)) {
            return false;
        }
        final CSSStyleDeclaration csd = (CSSStyleDeclaration) obj;

        // don't use parentRule in equals()
        // recursive loop -> stack overflow!
        return equalsProperties(csd);
    }

    private boolean equalsProperties(final CSSStyleDeclaration csd) {
        if ((csd == null) || (getLength() != csd.getLength())) {
            return false;
        }
        for (int i = 0; i < getLength(); i++) {
            final String propertyName = item(i);
            // CSSValue propertyCSSValue1 = getPropertyCSSValue(propertyName);
            // CSSValue propertyCSSValue2 = csd.getPropertyCSSValue(propertyName);
            final String propertyValue1 = getPropertyValue(propertyName);
            final String propertyValue2 = csd.getPropertyValue(propertyName);
            if (!LangUtils.equals(propertyValue1, propertyValue2)) {
                return false;
            }
            final String propertyPriority1 = getPropertyPriority(propertyName);
            final String propertyPriority2 = csd.getPropertyPriority(propertyName);
            if (!LangUtils.equals(propertyPriority1, propertyPriority2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        // don't use parentRule in hashCode()
        // recursive loop -> stack overflow!
        hash = LangUtils.hashCode(hash, properties_);
        return hash;
    }

	public String getAzimuth() {
		return this.getPropertyValue(AZIMUTH);
	}

	public String getBackground() {
		return this.getPropertyValue(BACKGROUND);
	}

	public String getBackgroundAttachment() {
		return this.getPropertyValue(BACKGROUND_ATTACHMENT);
	}

	public String getBackgroundColor() {
		return this.getPropertyValue(BACKGROUND_COLOR);
	}

	public String getBackgroundImage() {
		return this.getPropertyValue(BACKGROUND_IMAGE);
	}

	public String getBackgroundPosition() {
		return this.getPropertyValue(BACKGROUND_POSITION);
	}

	public String getBackgroundRepeat() {
		return this.getPropertyValue(BACKGROUND_REPEAT);
	}

	public String getBorder() {
		return this.getPropertyValue(BORDER);
	}

	public String getBorderBottom() {
		return this.getPropertyValue(BORDER_BOTTOM);
	}

	public String getBorderBottomColor() {
		return this.getPropertyValue(BORDER_BOTTOM_COLOR);
	}

	public String getBorderBottomStyle() {
		return this.getPropertyValue(BORDER_BOTTOM_STYLE);
	}

	public String getBorderBottomWidth() {
		return this.getPropertyValue(BORDER_BOTTOM_WIDTH);
	}

	public String getBorderCollapse() {
		return this.getPropertyValue(BORDER_COLLAPSE);
	}

	public String getBorderColor() {
		return this.getPropertyValue(BORDER_COLOR);
	}

	public String getBorderLeft() {
		return this.getPropertyValue(BORDER_LEFT);
	}

	public String getBorderLeftColor() {
		return this.getPropertyValue(BORDER_LEFT_COLOR);
	}

	public String getBorderLeftStyle() {
		return this.getPropertyValue(BORDER_LEFT_STYLE);
	}

	public String getBorderLeftWidth() {
		return this.getPropertyValue(BORDER_LEFT_WIDTH);
	}

	public String getBorderRight() {
		return this.getPropertyValue(BORDER_RIGHT);
	}

	public String getBorderRightColor() {
		return this.getPropertyValue(BORDER_RIGHT_COLOR);
	}

	public String getBorderRightStyle() {
		return this.getPropertyValue(BORDER_RIGHT_STYLE);
	}

	public String getBorderRightWidth() {
		return this.getPropertyValue(BORDER_RIGHT_WIDTH);
	}

	public String getBorderSpacing() {
		return this.getPropertyValue(BORDER_SPACING);
	}

	public String getBorderStyle() {
		return this.getPropertyValue(BORDER_STYLE);
	}

	public String getBorderTop() {
		return this.getPropertyValue(BORDER_TOP);
	}

	public String getBorderTopColor() {
		return this.getPropertyValue(BORDER_TOP_COLOR);
	}

	public String getBorderTopStyle() {
		return this.getPropertyValue(BORDER_TOP_STYLE);
	}

	public String getBorderTopWidth() {
		return this.getPropertyValue(BORDER_TOP_WIDTH);
	}

	public String getBorderWidth() {
		return this.getPropertyValue(BORDER_WIDTH);
	}

	public String getBottom() {
		return this.getPropertyValue(BOTTOM);
	}

	public String getCaptionSide() {
		return this.getPropertyValue(CAPTION_SIDE);
	}

	public String getClear() {
		return this.getPropertyValue(CLEAR);
	}

	public String getClip() {
		return this.getPropertyValue(CLIP);
	}

	public String getColor() {
		return this.getPropertyValue(COLOR);
	}

	public String getContent() {
		return this.getPropertyValue(CONTENT);
	}

	public String getCounterIncrement() {
		return this.getPropertyValue(COUNTER_INCREMENT);
	}

	public String getCounterReset() {
		return this.getPropertyValue(COUNTER_RESET);
	}

	public String getCssFloat() {
		return this.getPropertyValue(CSS_FLOAT);
	}

	public String getCue() {
		return this.getPropertyValue(CUE);
	}

	public String getCueAfter() {
		return this.getPropertyValue(CUE_AFTER);
	}

	public String getCueBefore() {
		return this.getPropertyValue(CUE_BEFORE);
	}

	public String getCursor() {
		return this.getPropertyValue(CURSOR);
	}

	public String getDirection() {
		return this.getPropertyValue(DIRECTION);
	}

	public String getDisplay() {
		return this.getPropertyValue(DISPLAY);
	}

	public String getElevation() {
		return this.getPropertyValue(ELEVATION);
	}

	public String getEmptyCells() {
		return this.getPropertyValue(EMPTY_CELLS);
	}

	public String getFont() {
		return this.getPropertyValue(FONT);
	}

	public String getFontFamily() {
		return this.getPropertyValue(FONT_FAMILY);
	}

	public String getFontSize() {
		return this.getPropertyValue(FONT_SIZE);
	}

	public String getFontSizeAdjust() {
		return this.getPropertyValue(FONT_SIZE_ADJUST);
	}

	public String getFontStretch() {
		return this.getPropertyValue(FONT_STRETCH);
	}

	public String getFontStyle() {
		return this.getPropertyValue(FONT_STYLE);
	}

	public String getFontVariant() {
		return this.getPropertyValue(FONT_VARIANT);
	}

	public String getFontWeight() {
		return this.getPropertyValue(FONT_WEIGHT);
	}

	public String getHeight() {
		return this.getPropertyValue(HEIGHT);
	}

	public String getLeft() {
		return this.getPropertyValue(LEFT);
	}

	public String getLetterSpacing() {
		return this.getPropertyValue(LETTER_SPACING);
	}

	public String getLineHeight() {
		return this.getPropertyValue(LINE_HEIGHT);
	}

	public String getListStyle() {
		return this.getPropertyValue(LIST_STYLE);
	}

	public String getListStyleImage() {
		return this.getPropertyValue(LIST_STYLE_IMAGE);
	}

	public String getListStylePosition() {
		return this.getPropertyValue(LIST_STYLE_POSITION);
	}

	public String getListStyleType() {
		return this.getPropertyValue(LIST_STYLE_TYPE);
	}

	public String getMargin() {
		return this.getPropertyValue(MARGIN);
	}

	public String getMarginBottom() {
		return this.getPropertyValue(MARGIN_BOTTOM);
	}

	public String getMarginLeft() {
		return this.getPropertyValue(MARGIN_LEFT);
	}

	public String getMarginRight() {
		return this.getPropertyValue(MARGIN_RIGHT);
	}

	public String getMarginTop() {
		return this.getPropertyValue(MARGIN_TOP);
	}

	public String getMarkerOffset() {
		return this.getPropertyValue(MARKER_OFFSET);
	}

	public String getMarks() {
		return this.getPropertyValue(MARKS);
	}

	public String getMaxHeight() {
		return this.getPropertyValue(MAX_HEIGHT);
	}

	public String getMaxWidth() {
		return this.getPropertyValue(MAX_WIDTH);
	}

	public String getMinHeight() {
		return this.getPropertyValue(MIN_HEIGHT);
	}

	public String getMinWidth() {
		return this.getPropertyValue(MIN_WIDTH);
	}

	public String getOrphans() {
		return this.getPropertyValue(ORPHANS);
	}

	public String getOutline() {
		return this.getPropertyValue(OUTLINE);
	}

	public String getOutlineColor() {
		return this.getPropertyValue(OUTLINE_COLOR);
	}

	public String getOutlineStyle() {
		return this.getPropertyValue(OUTLINE_STYLE);
	}

	public String getOutlineWidth() {
		return this.getPropertyValue(OUTLINE_WIDTH);
	}

	public String getOverflow() {
		return this.getPropertyValue(OVERFLOW);
	}

	public String getPadding() {
		return this.getPropertyValue(PADDING);
	}

	public String getPaddingBottom() {
		return this.getPropertyValue(PADDING_BOTTOM);
	}

	public String getPaddingLeft() {
		return this.getPropertyValue(PADDING_LEFT);
	}

	public String getPaddingRight() {
		return this.getPropertyValue(PADDING_RIGHT);
	}

	public String getPaddingTop() {
		return this.getPropertyValue(PADDING_TOP);
	}

	public String getPage() {
		return this.getPropertyValue(PAGE);
	}

	public String getPageBreakAfter() {
		return this.getPropertyValue(PAGE_BREAK_AFTER);
	}

	public String getPageBreakBefore() {
		return this.getPropertyValue(PAGE_BREAK_BEFORE);
	}

	public String getPageBreakInside() {
		return this.getPropertyValue(PAGE_BREAK_INSIDE);
	}

	public String getPause() {
		return this.getPropertyValue(PAUSE);
	}

	public String getPauseAfter() {
		return this.getPropertyValue(PAUSE_AFTER);
	}

	public String getPauseBefore() {
		return this.getPropertyValue(PAUSE_BEFORE);
	}

	public String getPitch() {
		return this.getPropertyValue(PITCH);
	}

	public String getPitchRange() {
		return this.getPropertyValue(PITCH_RANGE);
	}

	public String getPlayDuring() {
		return this.getPropertyValue(PLAY_DURING);
	}

	public String getPosition() {
		return this.getPropertyValue(POSITION);
	}

	public String getQuotes() {
		return this.getPropertyValue(QUOTES);
	}

	public String getRichness() {
		return this.getPropertyValue(RICHNESS);
	}

	public String getRight() {
		return this.getPropertyValue(RIGHT);
	}

	public String getSize() {
		return this.getPropertyValue(SIZE);
	}

	public String getSpeak() {
		return this.getPropertyValue(SPEAK);
	}

	public String getSpeakHeader() {
		return this.getPropertyValue(SPEAK_HEADER);
	}

	public String getSpeakNumeral() {
		return this.getPropertyValue(SPEAK_NUMERAL);
	}

	public String getSpeakPunctuation() {
		return this.getPropertyValue(SPEAK_PUNCTUATION);
	}

	public String getSpeechRate() {
		return this.getPropertyValue(SPEECH_RATE);
	}

	public String getStress() {
		return this.getPropertyValue(STRESS);
	}

	public String getTableLayout() {
		return this.getPropertyValue(TABLE_LAYOUT);
	}

	public String getTextAlign() {
		return this.getPropertyValue(TEXT_ALIGN);
	}

	public String getTextDecoration() {
		return this.getPropertyValue(TEXT_DECORATION);
	}

	public String getTextIndent() {
		return this.getPropertyValue(TEXT_INDENT);
	}

	public String getTextShadow() {
		return this.getPropertyValue(TEXT_SHADOW);
	}

	public String getTextTransform() {
		return this.getPropertyValue(TEXT_TRANSFORM);
	}

	public String getTop() {
		return this.getPropertyValue(TOP);
	}

	public String getUnicodeBidi() {
		return this.getPropertyValue(UNICODE_BIDI);
	}

	public String getVerticalAlign() {
		return this.getPropertyValue(VERTICAL_ALIGN);
	}

	public String getVisibility() {
		return this.getPropertyValue(VISIBILITY);
	}

	public String getVoiceFamily() {
		return this.getPropertyValue(VOICE_FAMILY);
	}

	public String getVolume() {
		return this.getPropertyValue(VOLUME);
	}

	public String getWhiteSpace() {
		return this.getPropertyValue(WHITE_SPACE);
	}

	public String getWidows() {
		return this.getPropertyValue(WIDOWS);
	}

	public String getWidth() {
		return this.getPropertyValue(WIDTH);
	}

	public String getWordSpacing() {

		return this.getPropertyValue(WORD_SPACING);
	}

	public String getZIndex() {
		return this.getPropertyValue(Z_INDEX);
	}

	public void setAzimuth(String azimuth) {
		this.setProperty(AZIMUTH, azimuth, "");
	}

	public void setBackground(String background) {

		this.setProperty(BACKGROUND, background, "");

	}

	public void setBackgroundAttachment(String backgroundAttachment) {
		this.setProperty(BACKGROUND_ATTACHMENT, backgroundAttachment, "");

	}

	public void setBackgroundColor(String backgroundColor) {
		this.setProperty(BACKGROUND_COLOR, backgroundColor, "");

	}

	public void setBackgroundPosition(String backgroundPosition) {
		this.setProperty(BACKGROUND_POSITION, backgroundPosition, "");

	}

	public void setBackgroundRepeat(String backgroundRepeat) {
		this.setProperty(BACKGROUND_REPEAT, backgroundRepeat, "");

	}

	public void setBorderBottom(String borderBottom) {

		this.setProperty(BORDER_BOTTOM, borderBottom, "");

	}

	public void setBorderBottomColor(String borderBottomColor) {
		this.setProperty(BORDER_BOTTOM_COLOR, borderBottomColor, "");

	}

	public void setBorderBottomStyle(String borderBottomStyle) {
		this.setProperty(BORDER_BOTTOM_STYLE, borderBottomStyle, "");

	}

	public void setBorderBottomWidth(String borderBottomWidth) {
		this.setProperty(BORDER_BOTTOM_WIDTH, borderBottomWidth, "");

	}

	public void setBorderCollapse(String borderCollapse) {
		this.setProperty(BORDER_COLLAPSE, borderCollapse, "");

	}

	public void setBorderColor(String borderColor) {

		this.setProperty(BORDER_COLOR, borderColor, "");

	}

	public void setBorderLeft(String borderLeft) {

		this.setProperty(BORDER_LEFT, borderLeft, "");

	}

	public void setBorderLeftColor(String borderLeftColor) {
		this.setProperty(BORDER_LEFT_COLOR, borderLeftColor, "");

	}

	public void setBorderLeftStyle(String borderLeftStyle) {
		this.setProperty(BORDER_LEFT_STYLE, borderLeftStyle, "");

	}

	public void setBorderLeftWidth(String borderLeftWidth) {
		this.setProperty(BORDER_LEFT_WIDTH, borderLeftWidth, "");

	}

	public void setBorderRight(String borderRight) {
		this.setProperty(BORDER_RIGHT, borderRight, "");
	}

	public void setBorderRightColor(String borderRightColor) {
		this.setProperty(BORDER_RIGHT_COLOR, borderRightColor, "");

	}

	public void setBorderRightStyle(String borderRightStyle) {
		this.setProperty(BORDER_RIGHT_STYLE, borderRightStyle, "");

	}

	public void setBorderRightWidth(String borderRightWidth) {
		this.setProperty(BORDER_RIGHT_WIDTH, borderRightWidth, "");

	}

	public void setBorderSpacing(String borderSpacing) {
		this.setProperty(BORDER_SPACING, borderSpacing, "");

	}

	public void setBorderStyle(String borderStyle) {
		this.setProperty(BORDER_STYLE, borderStyle, "");
	}

	public void setBorderTop(String borderTop) {
		this.setProperty(BORDER_TOP, borderTop, "");
	}

	public void setBorderTopColor(String borderTopColor) {
		this.setProperty(BORDER_TOP_COLOR, borderTopColor, "");

	}

	public void setBorderTopStyle(String borderTopStyle) {
		this.setProperty(BORDER_TOP_STYLE, borderTopStyle, "");

	}

	public void setBorderTopWidth(String borderTopWidth) {
		this.setProperty(BORDER_TOP_WIDTH, borderTopWidth, "");

	}

	public void setBorderWidth(String borderWidth) {

		this.setProperty(BORDER, borderWidth, "");

	}

	public void setBottom(String bottom) {
		this.setProperty(BOTTOM, bottom, "");

	}

	public void setCaptionSide(String captionSide) {
		this.setProperty(CAPTION_SIDE, captionSide, "");
	}

	public void setClear(String clear) {
		this.setProperty(CLEAR, clear, "");

	}

	public void setClip(String clip) {
		this.setProperty(CLIP, clip, "");
	}

	public void setColor(String color) {
		this.setProperty(COLOR, color, "");

	}

	public void setContent(String content) {
		this.setProperty(CONTENT, content, "");

	}

	public void setCounterIncrement(String counterIncrement) {
		this.setProperty(COUNTER_INCREMENT, counterIncrement, "");

	}

	public void setCounterReset(String counterReset) {
		this.setProperty(COUNTER_RESET, counterReset, "");

	}

	public void setCssFloat(String cssFloat) {
		this.setProperty(CSS_FLOAT, cssFloat, "");

	}

	public void setCue(String cue) {
		this.setProperty(CUE, cue, "");
	}

	public void setCueAfter(String cueAfter) {
		this.setProperty(CUE_AFTER, cueAfter, "");
	}

	public void setCueBefore(String cueBefore) {
		this.setProperty(CUE_BEFORE, cueBefore, "");
	}

	public void setCursor(String cursor) {
		this.setProperty(CURSOR, cursor, "");

	}

	public void setDirection(String direction) {
		this.setProperty(DIRECTION, direction, "");

	}

	public void setDisplay(String display) {
		this.setProperty(DISPLAY, display, "");

	}

	public void setElevation(String elevation) {
		this.setProperty(ELEVATION, elevation, "");

	}

	public void setEmptyCells(String emptyCells) {
		this.setProperty(EMPTY_CELLS, emptyCells, "");
	}

	public void setFont(String font) {
		this.setProperty(FONT, font, "");
	}

	public void setFontFamily(String fontFamily) {
		this.setProperty(FONT_FAMILY, fontFamily, "");

	}

	public void setFontSize(String fontSize) {
		this.setProperty(FONT_SIZE, fontSize, "");

	}

	public void setFontSizeAdjust(String fontSizeAdjust) {
		this.setProperty(FONT_SIZE_ADJUST, fontSizeAdjust, "");

	}

	public void setFontStretch(String fontStretch) {
		this.setProperty(FONT_STRETCH, fontStretch, "");

	}

	public void setFontStyle(String fontStyle) {
		this.setProperty(FONT_STYLE, fontStyle, "");

	}

	public void setFontVariant(String fontVariant) {
		this.setProperty(FONT_VARIANT, fontVariant, "");

	}

	public void setFontWeight(String fontWeight) {
		this.setProperty(FONT_WEIGHT, fontWeight, "");

	}

	public void setHeight(String height) {
		this.setProperty(HEIGHT, height, "");

	}

	public void setLeft(String left) {
		this.setProperty(LEFT, left, "");

	}

	public void setLetterSpacing(String letterSpacing) {
		this.setProperty(LETTER_SPACING, letterSpacing, "");

	}

	public void setLineHeight(String lineHeight) {
		this.setProperty(LINE_HEIGHT, lineHeight, "");

	}

	public void setListStyle(String listStyle) {
		this.setProperty(LIST_STYLE, listStyle, "");

	}

	public void setListStyleImage(String listStyleImage) {
		this.setProperty(LIST_STYLE_IMAGE, listStyleImage, "");

	}

	public void setListStylePosition(String listStylePosition) {
		this.setProperty(LIST_STYLE_POSITION, listStylePosition, "");

	}

	public void setListStyleType(String listStyleType) {
		this.setProperty(LIST_STYLE_TYPE, listStyleType, "");

	}

	public void setMargin(String margin) {

		this.setProperty(MARGIN, margin, "");

	}

	public void setMarginBottom(String marginBottom) {
		this.setProperty(MARGIN_BOTTOM, marginBottom, "");

	}

	public void setMarginLeft(String marginLeft) {
		this.setProperty(MARGIN_LEFT, marginLeft, "");

	}

	public void setMarginRight(String marginRight) {
		this.setProperty(MARGIN_RIGHT, marginRight, "");

	}

	public void setMarginTop(String marginTop) {
		this.setProperty(MARGIN_TOP, marginTop, "");

	}

	public void setMarkerOffset(String markerOffset) {
		this.setProperty(MARKER_OFFSET, markerOffset, "");
	}

	public void setMarks(String marks) {
		this.setProperty(MARKS, marks, "");
	}

	public void setMaxHeight(String maxHeight) {
		this.setProperty(MAX_HEIGHT, maxHeight, "");

	}

	public void setMaxWidth(String maxWidth) {
		this.setProperty(MAX_WIDTH, maxWidth, "");

	}

	public void setMinHeight(String minHeight) {
		this.setProperty(MIN_HEIGHT, minHeight, "");

	}

	public void setMinWidth(String minWidth) {
		this.setProperty(MIN_WIDTH, minWidth, "");

	}

	public void setOrphans(String orphans) {
		this.setProperty(ORPHANS, orphans, "");
	}

	public void setOutline(String outline) {
		this.setProperty(OUTLINE, outline, "");

	}

	public void setOutlineColor(String outlineColor) {
		this.setProperty(OUTLINE_COLOR, outlineColor, "");

	}

	public void setOutlineStyle(String outlineStyle) {
		this.setProperty(OUTLINE_STYLE, outlineStyle, "");
	}

	public void setOutlineWidth(String outlineWidth) {
		this.setProperty(OUTLINE_WIDTH, outlineWidth, "");

	}

	public void setOverflow(String overflow) {
		this.setProperty(OVERFLOW, overflow, "");

	}

	public void setPadding(String padding) {
		this.setProperty(PADDING, padding, "");

	}

	public void setPaddingBottom(String paddingBottom) {
		this.setProperty(PADDING_BOTTOM, paddingBottom, "");

	}

	public void setPaddingLeft(String paddingLeft) {
		this.setProperty(PADDING_LEFT, paddingLeft, "");

	}

	public void setPaddingRight(String paddingRight) {
		this.setProperty(PADDING_RIGHT, paddingRight, "");

	}

	public void setPaddingTop(String paddingTop) {
		this.setProperty(PADDING_TOP, paddingTop, "");

	}

	public void setPage(String page) {
		this.setProperty(PAGE, page, "");
	}

	public void setPageBreakAfter(String pageBreakAfter) {
		this.setProperty(PAGE_BREAK_AFTER, pageBreakAfter, "");

	}

	public void setPageBreakBefore(String pageBreakBefore) {
		this.setProperty(PAGE_BREAK_BEFORE, pageBreakBefore, "");

	}

	public void setPageBreakInside(String pageBreakInside) {
		this.setProperty(PAGE_BREAK_INSIDE, pageBreakInside, "");

	}

	public void setPause(String pause) {
		this.setProperty(PAUSE, pause, "");
	}

	public void setPauseAfter(String pauseAfter) {
		this.setProperty(PAUSE_AFTER, pauseAfter, "");
	}

	public void setPauseBefore(String pauseBefore) {
		this.setProperty(PAUSE_BEFORE, pauseBefore, "");
	}

	public void setPitch(String pitch) {
		this.setProperty(PITCH, pitch, "");
	}

	public void setPitchRange(String pitchRange) {
		this.setProperty(PITCH_RANGE, pitchRange, "");
	}

	public void setPlayDuring(String playDuring) {
		this.setProperty(PLAY_DURING, playDuring, "");
	}

	public void setPosition(String position) {
		this.setProperty(POSITION, position, "");

	}

	public void setQuotes(String quotes) {
		this.setProperty(QUOTES, quotes, "");
	}

	public void setRichness(String richness) {
		this.setProperty(RICHNESS, richness, "");
	}

	public void setRight(String right) {
		this.setProperty(RIGHT, right, "");

	}

	public void setSize(String size) {
		this.setProperty(SIZE, size, "");

	}

	public void setSpeak(String speak) {
		this.setProperty(SPEAK, speak, "");
	}

	public void setSpeakHeader(String speakHeader) {
		this.setProperty(SPEAK_HEADER, speakHeader, "");
	}

	public void setSpeakNumeral(String speakNumeral) {
		this.setProperty(SPEAK_NUMERAL, speakNumeral, "");
	}

	public void setSpeakPunctuation(String speakPunctuation) {
		this.setProperty(SPEAK_PUNCTUATION, speakPunctuation, "");
	}

	public void setSpeechRate(String speechRate) {
		this.setProperty(SPEECH_RATE, speechRate, "");
	}

	public void setStress(String stress) {
		this.setProperty(STRESS, stress, "");
	}

	public void setTableLayout(String tableLayout) {
		this.setProperty(TABLE_LAYOUT, tableLayout, "");

	}

	public void setTextAlign(String textAlign) {
		this.setProperty(TEXT_ALIGN, textAlign, "");

	}

	public void setTextDecoration(String textDecoration) {
		this.setProperty(TEXT_DECORATION, textDecoration, "");

	}

	public void setTextIndent(String textIndent) {
		this.setProperty(TEXT_INDENT, textIndent, "");

	}

	public void setTextShadow(String textShadow) {
		this.setProperty(TEXT_SHADOW, textShadow, "");

	}

	public void setTextTransform(String textTransform) {
		this.setProperty(TEXT_TRANSFORM, textTransform, "");

	}

	public void setTop(String top) {
		this.setProperty(TOP, top, "");

	}

	public void setUnicodeBidi(String unicodeBidi) {
		this.setProperty(UNICODE_BIDI, unicodeBidi, "");

	}

	public void setVerticalAlign(String verticalAlign) {
		this.setProperty(VERTICAL_ALIGN, verticalAlign, "");

	}

	public void setVisibility(String visibility) {
		this.setProperty(VISIBILITY, visibility, "");

	}

	public void setVoiceFamily(String voiceFamily) {
		this.setProperty(VOICE_FAMILY, voiceFamily, "");
	}

	public void setVolume(String volume) {
		this.setProperty(VOLUME, volume, "");
	}

	public void setWhiteSpace(String whiteSpace) {
		this.setProperty(WHITE_SPACE, whiteSpace, "");

	}

	public void setWidows(String widows) {
		this.setProperty(WIDOWS, widows, "");
	}

	public void setWidth(String width) {
		this.setProperty(WIDTH, width, "");

	}

	public void setWordSpacing(String wordSpacing) {
		this.setProperty(WORD_SPACING, wordSpacing, "");

	}

	public void setZIndex(String zIndex) {
		this.setProperty(Z_INDEX, zIndex, "");

	}
	
	@Override
	public void setBackgroundImage(String backgroundImage) throws DOMException {
		this.setProperty(BACKGROUND_IMAGE, backgroundImage, "");

	}

	@Override
	public void setBorder(String border) throws DOMException {
		this.setProperty(BORDER, border, "");
	}
}
