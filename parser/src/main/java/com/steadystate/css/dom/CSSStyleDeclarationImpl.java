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
 * @author <a href="mailto:davidsch@users.sourceforge.net">David
 *         Schweinsberg</a>
 * @author rbri
 * @author Ahmed Ashour
 */
public class CSSStyleDeclarationImpl
		implements CSSStyleDeclaration, CSSFormatable, CSS2Properties, CSSProperties, Serializable {
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
	@Override
	public String getCssText() {
		return getCssText(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
			} else if (nl) {
				sb.append(format.getNewLine());
			}
		}
		return sb.toString();
	}

	@Override
	public void setCssText(final String cssText) throws DOMException {
		try {
			final InputSource is = new InputSource(new StringReader(cssText));
			final CSSOMParser parser = new CSSOMParser();
			properties_.clear();
			parser.parseStyleDeclaration(this, is);
		} catch (final Exception e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR, DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
	}

	@Override
	public String getPropertyValue(final String propertyName) {
		final Property p = getPropertyDeclaration(propertyName);
		if (p == null || p.getValue() == null) {
			return "";
		}
		return p.getValue().toString();
	}

	@Override
	public CSSValue getPropertyCSSValue(final String propertyName) {
		final Property p = getPropertyDeclaration(propertyName);
		return p == null ? null : p.getValue();
	}

	@Override
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

	@Override
	public String getPropertyPriority(final String propertyName) {
		final Property p = getPropertyDeclaration(propertyName);
		if (p == null) {
			return "";
		}
		return p.isImportant() ? PRIORITY_IMPORTANT : "";
	}

	@Override
	public void setProperty(final String propertyName, final String value, final String priority) throws DOMException {
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
			} else {
				p.setValue(expr);
				p.setImportant(important);
			}
		} catch (final Exception e) {
			throw new DOMExceptionImpl(DOMException.SYNTAX_ERR, DOMExceptionImpl.SYNTAX_ERROR, e.getMessage());
		}
	}

	@Override
	public int getLength() {
		return properties_.size();
	}

	@Override
	public String item(final int index) {
		final Property p = properties_.get(index);
		return p == null ? "" : p.getName();
	}

	@Override
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
		if (csd == null || getLength() != csd.getLength()) {
			return false;
		}
		for (int i = 0; i < getLength(); i++) {
			final String propertyName = item(i);
			// CSSValue propertyCSSValue1 = getPropertyCSSValue(propertyName);
			// CSSValue propertyCSSValue2 =
			// csd.getPropertyCSSValue(propertyName);
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

	@Override
	public String getAzimuth() {
		return this.getPropertyValue(AZIMUTH);
	}

	@Override
	public String getBackground() {
		return this.getPropertyValue(BACKGROUND);
	}

	@Override
	public String getBackgroundAttachment() {
		return this.getPropertyValue(BACKGROUND_ATTACHMENT);
	}

	@Override
	public String getBackgroundColor() {
		return this.getPropertyValue(BACKGROUND_COLOR);
	}

	@Override
	public String getBackgroundImage() {
		return this.getPropertyValue(BACKGROUND_IMAGE);
	}

	@Override
	public String getBackgroundPosition() {
		return this.getPropertyValue(BACKGROUND_POSITION);
	}

	@Override
	public String getBackgroundRepeat() {
		return this.getPropertyValue(BACKGROUND_REPEAT);
	}

	@Override
	public String getBorder() {
		return this.getPropertyValue(BORDER);
	}

	@Override
	public String getBorderBottom() {
		return this.getPropertyValue(BORDER_BOTTOM);
	}

	@Override
	public String getBorderBottomColor() {
		return this.getPropertyValue(BORDER_BOTTOM_COLOR);
	}

	@Override
	public String getBorderBottomStyle() {
		return this.getPropertyValue(BORDER_BOTTOM_STYLE);
	}

	@Override
	public String getBorderBottomWidth() {
		return this.getPropertyValue(BORDER_BOTTOM_WIDTH);
	}

	@Override
	public String getBorderCollapse() {
		return this.getPropertyValue(BORDER_COLLAPSE);
	}

	@Override
	public String getBorderColor() {
		return this.getPropertyValue(BORDER_COLOR);
	}

	@Override
	public String getBorderLeft() {
		return this.getPropertyValue(BORDER_LEFT);
	}

	@Override
	public String getBorderLeftColor() {
		return this.getPropertyValue(BORDER_LEFT_COLOR);
	}

	@Override
	public String getBorderLeftStyle() {
		return this.getPropertyValue(BORDER_LEFT_STYLE);
	}

	@Override
	public String getBorderLeftWidth() {
		return this.getPropertyValue(BORDER_LEFT_WIDTH);
	}

	@Override
	public String getBorderRight() {
		return this.getPropertyValue(BORDER_RIGHT);
	}

	@Override
	public String getBorderRightColor() {
		return this.getPropertyValue(BORDER_RIGHT_COLOR);
	}

	@Override
	public String getBorderRightStyle() {
		return this.getPropertyValue(BORDER_RIGHT_STYLE);
	}

	@Override
	public String getBorderRightWidth() {
		return this.getPropertyValue(BORDER_RIGHT_WIDTH);
	}

	@Override
	public String getBorderSpacing() {
		return this.getPropertyValue(BORDER_SPACING);
	}

	@Override
	public String getBorderStyle() {
		return this.getPropertyValue(BORDER_STYLE);
	}

	@Override
	public String getBorderTop() {
		return this.getPropertyValue(BORDER_TOP);
	}

	@Override
	public String getBorderTopColor() {
		return this.getPropertyValue(BORDER_TOP_COLOR);
	}

	@Override
	public String getBorderTopStyle() {
		return this.getPropertyValue(BORDER_TOP_STYLE);
	}

	@Override
	public String getBorderTopWidth() {
		return this.getPropertyValue(BORDER_TOP_WIDTH);
	}

	@Override
	public String getBorderWidth() {
		return this.getPropertyValue(BORDER_WIDTH);
	}

	@Override
	public String getBottom() {
		return this.getPropertyValue(BOTTOM);
	}

	@Override
	public String getCaptionSide() {
		return this.getPropertyValue(CAPTION_SIDE);
	}

	@Override
	public String getClear() {
		return this.getPropertyValue(CLEAR);
	}

	@Override
	public String getClip() {
		return this.getPropertyValue(CLIP);
	}

	@Override
	public String getColor() {
		return this.getPropertyValue(COLOR);
	}

	@Override
	public String getContent() {
		return this.getPropertyValue(CONTENT);
	}

	@Override
	public String getCounterIncrement() {
		return this.getPropertyValue(COUNTER_INCREMENT);
	}

	@Override
	public String getCounterReset() {
		return this.getPropertyValue(COUNTER_RESET);
	}

	@Override
	public String getCssFloat() {
		return this.getPropertyValue(CSS_FLOAT);
	}

	@Override
	public String getCue() {
		return this.getPropertyValue(CUE);
	}

	@Override
	public String getCueAfter() {
		return this.getPropertyValue(CUE_AFTER);
	}

	@Override
	public String getCueBefore() {
		return this.getPropertyValue(CUE_BEFORE);
	}

	@Override
	public String getCursor() {
		return this.getPropertyValue(CURSOR);
	}

	@Override
	public String getDirection() {
		return this.getPropertyValue(DIRECTION);
	}

	@Override
	public String getDisplay() {
		return this.getPropertyValue(DISPLAY);
	}

	@Override
	public String getElevation() {
		return this.getPropertyValue(ELEVATION);
	}

	@Override
	public String getEmptyCells() {
		return this.getPropertyValue(EMPTY_CELLS);
	}

	@Override
	public String getFont() {
		return this.getPropertyValue(FONT);
	}

	@Override
	public String getFontFamily() {
		return this.getPropertyValue(FONT_FAMILY);
	}

	@Override
	public String getFontSize() {
		return this.getPropertyValue(FONT_SIZE);
	}

	@Override
	public String getFontSizeAdjust() {
		return this.getPropertyValue(FONT_SIZE_ADJUST);
	}

	@Override
	public String getFontStretch() {
		return this.getPropertyValue(FONT_STRETCH);
	}

	@Override
	public String getFontStyle() {
		return this.getPropertyValue(FONT_STYLE);
	}

	@Override
	public String getFontVariant() {
		return this.getPropertyValue(FONT_VARIANT);
	}

	@Override
	public String getFontWeight() {
		return this.getPropertyValue(FONT_WEIGHT);
	}

	@Override
	public String getHeight() {
		return this.getPropertyValue(HEIGHT);
	}

	@Override
	public String getLeft() {
		return this.getPropertyValue(LEFT);
	}

	@Override
	public String getLetterSpacing() {
		return this.getPropertyValue(LETTER_SPACING);
	}

	@Override
	public String getLineHeight() {
		return this.getPropertyValue(LINE_HEIGHT);
	}

	@Override
	public String getListStyle() {
		return this.getPropertyValue(LIST_STYLE);
	}

	@Override
	public String getListStyleImage() {
		return this.getPropertyValue(LIST_STYLE_IMAGE);
	}

	@Override
	public String getListStylePosition() {
		return this.getPropertyValue(LIST_STYLE_POSITION);
	}

	@Override
	public String getListStyleType() {
		return this.getPropertyValue(LIST_STYLE_TYPE);
	}

	@Override
	public String getMargin() {
		return this.getPropertyValue(MARGIN);
	}

	@Override
	public String getMarginBottom() {
		return this.getPropertyValue(MARGIN_BOTTOM);
	}

	@Override
	public String getMarginLeft() {
		return this.getPropertyValue(MARGIN_LEFT);
	}

	@Override
	public String getMarginRight() {
		return this.getPropertyValue(MARGIN_RIGHT);
	}

	@Override
	public String getMarginTop() {
		return this.getPropertyValue(MARGIN_TOP);
	}

	@Override
	public String getMarkerOffset() {
		return this.getPropertyValue(MARKER_OFFSET);
	}

	@Override
	public String getMarks() {
		return this.getPropertyValue(MARKS);
	}

	@Override
	public String getMaxHeight() {
		return this.getPropertyValue(MAX_HEIGHT);
	}

	@Override
	public String getMaxWidth() {
		return this.getPropertyValue(MAX_WIDTH);
	}

	@Override
	public String getMinHeight() {
		return this.getPropertyValue(MIN_HEIGHT);
	}

	@Override
	public String getMinWidth() {
		return this.getPropertyValue(MIN_WIDTH);
	}

	@Override
	public String getOrphans() {
		return this.getPropertyValue(ORPHANS);
	}

	@Override
	public String getOutline() {
		return this.getPropertyValue(OUTLINE);
	}

	@Override
	public String getOutlineColor() {
		return this.getPropertyValue(OUTLINE_COLOR);
	}

	@Override
	public String getOutlineStyle() {
		return this.getPropertyValue(OUTLINE_STYLE);
	}

	@Override
	public String getOutlineWidth() {
		return this.getPropertyValue(OUTLINE_WIDTH);
	}

	@Override
	public String getOverflow() {
		return this.getPropertyValue(OVERFLOW);
	}

	@Override
	public String getPadding() {
		return this.getPropertyValue(PADDING);
	}

	@Override
	public String getPaddingBottom() {
		return this.getPropertyValue(PADDING_BOTTOM);
	}

	@Override
	public String getPaddingLeft() {
		return this.getPropertyValue(PADDING_LEFT);
	}

	@Override
	public String getPaddingRight() {
		return this.getPropertyValue(PADDING_RIGHT);
	}

	@Override
	public String getPaddingTop() {
		return this.getPropertyValue(PADDING_TOP);
	}

	@Override
	public String getPage() {
		return this.getPropertyValue(PAGE);
	}

	@Override
	public String getPageBreakAfter() {
		return this.getPropertyValue(PAGE_BREAK_AFTER);
	}

	@Override
	public String getPageBreakBefore() {
		return this.getPropertyValue(PAGE_BREAK_BEFORE);
	}

	@Override
	public String getPageBreakInside() {
		return this.getPropertyValue(PAGE_BREAK_INSIDE);
	}

	@Override
	public String getPause() {
		return this.getPropertyValue(PAUSE);
	}

	@Override
	public String getPauseAfter() {
		return this.getPropertyValue(PAUSE_AFTER);
	}

	@Override
	public String getPauseBefore() {
		return this.getPropertyValue(PAUSE_BEFORE);
	}

	@Override
	public String getPitch() {
		return this.getPropertyValue(PITCH);
	}

	@Override
	public String getPitchRange() {
		return this.getPropertyValue(PITCH_RANGE);
	}

	@Override
	public String getPlayDuring() {
		return this.getPropertyValue(PLAY_DURING);
	}

	@Override
	public String getPosition() {
		return this.getPropertyValue(POSITION);
	}

	@Override
	public String getQuotes() {
		return this.getPropertyValue(QUOTES);
	}

	@Override
	public String getRichness() {
		return this.getPropertyValue(RICHNESS);
	}

	@Override
	public String getRight() {
		return this.getPropertyValue(RIGHT);
	}

	@Override
	public String getSize() {
		return this.getPropertyValue(SIZE);
	}

	@Override
	public String getSpeak() {
		return this.getPropertyValue(SPEAK);
	}

	@Override
	public String getSpeakHeader() {
		return this.getPropertyValue(SPEAK_HEADER);
	}

	@Override
	public String getSpeakNumeral() {
		return this.getPropertyValue(SPEAK_NUMERAL);
	}

	@Override
	public String getSpeakPunctuation() {
		return this.getPropertyValue(SPEAK_PUNCTUATION);
	}

	@Override
	public String getSpeechRate() {
		return this.getPropertyValue(SPEECH_RATE);
	}

	@Override
	public String getStress() {
		return this.getPropertyValue(STRESS);
	}

	@Override
	public String getTableLayout() {
		return this.getPropertyValue(TABLE_LAYOUT);
	}

	@Override
	public String getTextAlign() {
		return this.getPropertyValue(TEXT_ALIGN);
	}

	@Override
	public String getTextDecoration() {
		return this.getPropertyValue(TEXT_DECORATION);
	}

	@Override
	public String getTextIndent() {
		return this.getPropertyValue(TEXT_INDENT);
	}

	@Override
	public String getTextShadow() {
		return this.getPropertyValue(TEXT_SHADOW);
	}

	@Override
	public String getTextTransform() {
		return this.getPropertyValue(TEXT_TRANSFORM);
	}

	@Override
	public String getTop() {
		return this.getPropertyValue(TOP);
	}

	@Override
	public String getUnicodeBidi() {
		return this.getPropertyValue(UNICODE_BIDI);
	}

	@Override
	public String getVerticalAlign() {
		return this.getPropertyValue(VERTICAL_ALIGN);
	}

	@Override
	public String getVisibility() {
		return this.getPropertyValue(VISIBILITY);
	}

	@Override
	public String getVoiceFamily() {
		return this.getPropertyValue(VOICE_FAMILY);
	}

	@Override
	public String getVolume() {
		return this.getPropertyValue(VOLUME);
	}

	@Override
	public String getWhiteSpace() {
		return this.getPropertyValue(WHITE_SPACE);
	}

	@Override
	public String getWidows() {
		return this.getPropertyValue(WIDOWS);
	}

	@Override
	public String getWidth() {
		return this.getPropertyValue(WIDTH);
	}

	@Override
	public String getWordSpacing() {

		return this.getPropertyValue(WORD_SPACING);
	}

	@Override
	public String getZIndex() {
		return this.getPropertyValue(Z_INDEX);
	}

	@Override
	public void setAzimuth(String azimuth) {
		this.setProperty(AZIMUTH, azimuth, "");
	}

	@Override
	public void setBackground(String background) {

		this.setProperty(BACKGROUND, background, "");

	}

	@Override
	public void setBackgroundAttachment(String backgroundAttachment) {
		this.setProperty(BACKGROUND_ATTACHMENT, backgroundAttachment, "");

	}

	@Override
	public void setBackgroundColor(String backgroundColor) {
		this.setProperty(BACKGROUND_COLOR, backgroundColor, "");

	}

	@Override
	public void setBackgroundPosition(String backgroundPosition) {
		this.setProperty(BACKGROUND_POSITION, backgroundPosition, "");

	}

	@Override
	public void setBackgroundRepeat(String backgroundRepeat) {
		this.setProperty(BACKGROUND_REPEAT, backgroundRepeat, "");

	}

	@Override
	public void setBorderBottom(String borderBottom) {

		this.setProperty(BORDER_BOTTOM, borderBottom, "");

	}

	@Override
	public void setBorderBottomColor(String borderBottomColor) {
		this.setProperty(BORDER_BOTTOM_COLOR, borderBottomColor, "");

	}

	@Override
	public void setBorderBottomStyle(String borderBottomStyle) {
		this.setProperty(BORDER_BOTTOM_STYLE, borderBottomStyle, "");

	}

	@Override
	public void setBorderBottomWidth(String borderBottomWidth) {
		this.setProperty(BORDER_BOTTOM_WIDTH, borderBottomWidth, "");

	}

	@Override
	public void setBorderCollapse(String borderCollapse) {
		this.setProperty(BORDER_COLLAPSE, borderCollapse, "");

	}

	@Override
	public void setBorderColor(String borderColor) {

		this.setProperty(BORDER_COLOR, borderColor, "");

	}

	@Override
	public void setBorderLeft(String borderLeft) {

		this.setProperty(BORDER_LEFT, borderLeft, "");

	}

	@Override
	public void setBorderLeftColor(String borderLeftColor) {
		this.setProperty(BORDER_LEFT_COLOR, borderLeftColor, "");

	}

	@Override
	public void setBorderLeftStyle(String borderLeftStyle) {
		this.setProperty(BORDER_LEFT_STYLE, borderLeftStyle, "");

	}

	@Override
	public void setBorderLeftWidth(String borderLeftWidth) {
		this.setProperty(BORDER_LEFT_WIDTH, borderLeftWidth, "");

	}

	@Override
	public void setBorderRight(String borderRight) {
		this.setProperty(BORDER_RIGHT, borderRight, "");
	}

	@Override
	public void setBorderRightColor(String borderRightColor) {
		this.setProperty(BORDER_RIGHT_COLOR, borderRightColor, "");

	}

	@Override
	public void setBorderRightStyle(String borderRightStyle) {
		this.setProperty(BORDER_RIGHT_STYLE, borderRightStyle, "");

	}

	@Override
	public void setBorderRightWidth(String borderRightWidth) {
		this.setProperty(BORDER_RIGHT_WIDTH, borderRightWidth, "");

	}

	@Override
	public void setBorderSpacing(String borderSpacing) {
		this.setProperty(BORDER_SPACING, borderSpacing, "");

	}

	@Override
	public void setBorderStyle(String borderStyle) {
		this.setProperty(BORDER_STYLE, borderStyle, "");
	}

	@Override
	public void setBorderTop(String borderTop) {
		this.setProperty(BORDER_TOP, borderTop, "");
	}

	@Override
	public void setBorderTopColor(String borderTopColor) {
		this.setProperty(BORDER_TOP_COLOR, borderTopColor, "");

	}

	@Override
	public void setBorderTopStyle(String borderTopStyle) {
		this.setProperty(BORDER_TOP_STYLE, borderTopStyle, "");

	}

	@Override
	public void setBorderTopWidth(String borderTopWidth) {
		this.setProperty(BORDER_TOP_WIDTH, borderTopWidth, "");

	}

	@Override
	public void setBorderWidth(String borderWidth) {

		this.setProperty(BORDER, borderWidth, "");

	}

	@Override
	public void setBottom(String bottom) {
		this.setProperty(BOTTOM, bottom, "");

	}

	@Override
	public void setCaptionSide(String captionSide) {
		this.setProperty(CAPTION_SIDE, captionSide, "");
	}

	@Override
	public void setClear(String clear) {
		this.setProperty(CLEAR, clear, "");

	}

	@Override
	public void setClip(String clip) {
		this.setProperty(CLIP, clip, "");
	}

	@Override
	public void setColor(String color) {
		this.setProperty(COLOR, color, "");

	}

	@Override
	public void setContent(String content) {
		this.setProperty(CONTENT, content, "");

	}

	@Override
	public void setCounterIncrement(String counterIncrement) {
		this.setProperty(COUNTER_INCREMENT, counterIncrement, "");

	}

	@Override
	public void setCounterReset(String counterReset) {
		this.setProperty(COUNTER_RESET, counterReset, "");

	}

	@Override
	public void setCssFloat(String cssFloat) {
		this.setProperty(CSS_FLOAT, cssFloat, "");

	}

	@Override
	public void setCue(String cue) {
		this.setProperty(CUE, cue, "");
	}

	@Override
	public void setCueAfter(String cueAfter) {
		this.setProperty(CUE_AFTER, cueAfter, "");
	}

	@Override
	public void setCueBefore(String cueBefore) {
		this.setProperty(CUE_BEFORE, cueBefore, "");
	}

	@Override
	public void setCursor(String cursor) {
		this.setProperty(CURSOR, cursor, "");

	}

	@Override
	public void setDirection(String direction) {
		this.setProperty(DIRECTION, direction, "");

	}

	@Override
	public void setDisplay(String display) {
		this.setProperty(DISPLAY, display, "");

	}

	@Override
	public void setElevation(String elevation) {
		this.setProperty(ELEVATION, elevation, "");

	}

	@Override
	public void setEmptyCells(String emptyCells) {
		this.setProperty(EMPTY_CELLS, emptyCells, "");
	}

	@Override
	public void setFont(String font) {
		this.setProperty(FONT, font, "");
	}

	@Override
	public void setFontFamily(String fontFamily) {
		this.setProperty(FONT_FAMILY, fontFamily, "");

	}

	@Override
	public void setFontSize(String fontSize) {
		this.setProperty(FONT_SIZE, fontSize, "");

	}

	@Override
	public void setFontSizeAdjust(String fontSizeAdjust) {
		this.setProperty(FONT_SIZE_ADJUST, fontSizeAdjust, "");

	}

	@Override
	public void setFontStretch(String fontStretch) {
		this.setProperty(FONT_STRETCH, fontStretch, "");

	}

	@Override
	public void setFontStyle(String fontStyle) {
		this.setProperty(FONT_STYLE, fontStyle, "");

	}

	@Override
	public void setFontVariant(String fontVariant) {
		this.setProperty(FONT_VARIANT, fontVariant, "");

	}

	@Override
	public void setFontWeight(String fontWeight) {
		this.setProperty(FONT_WEIGHT, fontWeight, "");

	}

	@Override
	public void setHeight(String height) {
		this.setProperty(HEIGHT, height, "");

	}

	@Override
	public void setLeft(String left) {
		this.setProperty(LEFT, left, "");

	}

	@Override
	public void setLetterSpacing(String letterSpacing) {
		this.setProperty(LETTER_SPACING, letterSpacing, "");

	}

	@Override
	public void setLineHeight(String lineHeight) {
		this.setProperty(LINE_HEIGHT, lineHeight, "");

	}

	@Override
	public void setListStyle(String listStyle) {
		this.setProperty(LIST_STYLE, listStyle, "");

	}

	@Override
	public void setListStyleImage(String listStyleImage) {
		this.setProperty(LIST_STYLE_IMAGE, listStyleImage, "");

	}

	@Override
	public void setListStylePosition(String listStylePosition) {
		this.setProperty(LIST_STYLE_POSITION, listStylePosition, "");

	}

	@Override
	public void setListStyleType(String listStyleType) {
		this.setProperty(LIST_STYLE_TYPE, listStyleType, "");

	}

	@Override
	public void setMargin(String margin) {

		this.setProperty(MARGIN, margin, "");

	}

	@Override
	public void setMarginBottom(String marginBottom) {
		this.setProperty(MARGIN_BOTTOM, marginBottom, "");

	}

	@Override
	public void setMarginLeft(String marginLeft) {
		this.setProperty(MARGIN_LEFT, marginLeft, "");

	}

	@Override
	public void setMarginRight(String marginRight) {
		this.setProperty(MARGIN_RIGHT, marginRight, "");

	}

	@Override
	public void setMarginTop(String marginTop) {
		this.setProperty(MARGIN_TOP, marginTop, "");

	}

	@Override
	public void setMarkerOffset(String markerOffset) {
		this.setProperty(MARKER_OFFSET, markerOffset, "");
	}

	@Override
	public void setMarks(String marks) {
		this.setProperty(MARKS, marks, "");
	}

	@Override
	public void setMaxHeight(String maxHeight) {
		this.setProperty(MAX_HEIGHT, maxHeight, "");

	}

	@Override
	public void setMaxWidth(String maxWidth) {
		this.setProperty(MAX_WIDTH, maxWidth, "");

	}

	@Override
	public void setMinHeight(String minHeight) {
		this.setProperty(MIN_HEIGHT, minHeight, "");

	}

	@Override
	public void setMinWidth(String minWidth) {
		this.setProperty(MIN_WIDTH, minWidth, "");

	}

	@Override
	public void setOrphans(String orphans) {
		this.setProperty(ORPHANS, orphans, "");
	}

	@Override
	public void setOutline(String outline) {
		this.setProperty(OUTLINE, outline, "");

	}

	@Override
	public void setOutlineColor(String outlineColor) {
		this.setProperty(OUTLINE_COLOR, outlineColor, "");

	}

	@Override
	public void setOutlineStyle(String outlineStyle) {
		this.setProperty(OUTLINE_STYLE, outlineStyle, "");
	}

	@Override
	public void setOutlineWidth(String outlineWidth) {
		this.setProperty(OUTLINE_WIDTH, outlineWidth, "");

	}

	@Override
	public void setOverflow(String overflow) {
		this.setProperty(OVERFLOW, overflow, "");

	}

	@Override
	public void setPadding(String padding) {
		this.setProperty(PADDING, padding, "");

	}

	@Override
	public void setPaddingBottom(String paddingBottom) {
		this.setProperty(PADDING_BOTTOM, paddingBottom, "");

	}

	@Override
	public void setPaddingLeft(String paddingLeft) {
		this.setProperty(PADDING_LEFT, paddingLeft, "");

	}

	@Override
	public void setPaddingRight(String paddingRight) {
		this.setProperty(PADDING_RIGHT, paddingRight, "");

	}

	@Override
	public void setPaddingTop(String paddingTop) {
		this.setProperty(PADDING_TOP, paddingTop, "");

	}

	@Override
	public void setPage(String page) {
		this.setProperty(PAGE, page, "");
	}

	@Override
	public void setPageBreakAfter(String pageBreakAfter) {
		this.setProperty(PAGE_BREAK_AFTER, pageBreakAfter, "");

	}

	@Override
	public void setPageBreakBefore(String pageBreakBefore) {
		this.setProperty(PAGE_BREAK_BEFORE, pageBreakBefore, "");

	}

	@Override
	public void setPageBreakInside(String pageBreakInside) {
		this.setProperty(PAGE_BREAK_INSIDE, pageBreakInside, "");

	}

	@Override
	public void setPause(String pause) {
		this.setProperty(PAUSE, pause, "");
	}

	@Override
	public void setPauseAfter(String pauseAfter) {
		this.setProperty(PAUSE_AFTER, pauseAfter, "");
	}

	@Override
	public void setPauseBefore(String pauseBefore) {
		this.setProperty(PAUSE_BEFORE, pauseBefore, "");
	}

	@Override
	public void setPitch(String pitch) {
		this.setProperty(PITCH, pitch, "");
	}

	@Override
	public void setPitchRange(String pitchRange) {
		this.setProperty(PITCH_RANGE, pitchRange, "");
	}

	@Override
	public void setPlayDuring(String playDuring) {
		this.setProperty(PLAY_DURING, playDuring, "");
	}

	@Override
	public void setPosition(String position) {
		this.setProperty(POSITION, position, "");

	}

	@Override
	public void setQuotes(String quotes) {
		this.setProperty(QUOTES, quotes, "");
	}

	@Override
	public void setRichness(String richness) {
		this.setProperty(RICHNESS, richness, "");
	}

	@Override
	public void setRight(String right) {
		this.setProperty(RIGHT, right, "");

	}

	@Override
	public void setSize(String size) {
		this.setProperty(SIZE, size, "");

	}

	@Override
	public void setSpeak(String speak) {
		this.setProperty(SPEAK, speak, "");
	}

	@Override
	public void setSpeakHeader(String speakHeader) {
		this.setProperty(SPEAK_HEADER, speakHeader, "");
	}

	@Override
	public void setSpeakNumeral(String speakNumeral) {
		this.setProperty(SPEAK_NUMERAL, speakNumeral, "");
	}

	@Override
	public void setSpeakPunctuation(String speakPunctuation) {
		this.setProperty(SPEAK_PUNCTUATION, speakPunctuation, "");
	}

	@Override
	public void setSpeechRate(String speechRate) {
		this.setProperty(SPEECH_RATE, speechRate, "");
	}

	@Override
	public void setStress(String stress) {
		this.setProperty(STRESS, stress, "");
	}

	@Override
	public void setTableLayout(String tableLayout) {
		this.setProperty(TABLE_LAYOUT, tableLayout, "");

	}

	@Override
	public void setTextAlign(String textAlign) {
		this.setProperty(TEXT_ALIGN, textAlign, "");

	}

	@Override
	public void setTextDecoration(String textDecoration) {
		this.setProperty(TEXT_DECORATION, textDecoration, "");

	}

	@Override
	public void setTextIndent(String textIndent) {
		this.setProperty(TEXT_INDENT, textIndent, "");

	}

	@Override
	public void setTextShadow(String textShadow) {
		this.setProperty(TEXT_SHADOW, textShadow, "");

	}

	@Override
	public void setTextTransform(String textTransform) {
		this.setProperty(TEXT_TRANSFORM, textTransform, "");

	}

	@Override
	public void setTop(String top) {
		this.setProperty(TOP, top, "");

	}

	@Override
	public void setUnicodeBidi(String unicodeBidi) {
		this.setProperty(UNICODE_BIDI, unicodeBidi, "");

	}

	@Override
	public void setVerticalAlign(String verticalAlign) {
		this.setProperty(VERTICAL_ALIGN, verticalAlign, "");

	}

	@Override
	public void setVisibility(String visibility) {
		this.setProperty(VISIBILITY, visibility, "");

	}

	@Override
	public void setVoiceFamily(String voiceFamily) {
		this.setProperty(VOICE_FAMILY, voiceFamily, "");
	}

	@Override
	public void setVolume(String volume) {
		this.setProperty(VOLUME, volume, "");
	}

	@Override
	public void setWhiteSpace(String whiteSpace) {
		this.setProperty(WHITE_SPACE, whiteSpace, "");

	}

	@Override
	public void setWidows(String widows) {
		this.setProperty(WIDOWS, widows, "");
	}

	@Override
	public void setWidth(String width) {
		this.setProperty(WIDTH, width, "");

	}

	@Override
	public void setWordSpacing(String wordSpacing) {
		this.setProperty(WORD_SPACING, wordSpacing, "");

	}

	@Override
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
