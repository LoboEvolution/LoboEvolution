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
		implements CSSStyleDeclaration, CSSFormatable, CSSProperties, CSS2Properties, Serializable {
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
		return (p == null) ? null : p.getValue();
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
		return (p == null) ? "" : p.getName();
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

	// ---- start CSS2Properties interface ----
	@Override
	public String getAzimuth() {
		return getPropertyValue(AZIMUTH);
	}

	@Override
	public void setAzimuth(final String azimuth) throws DOMException {
		setProperty(AZIMUTH, azimuth, null);
	}

	@Override
	public String getBackground() {
		return getPropertyValue(BACKGROUND);
	}

	@Override
	public void setBackground(final String background) throws DOMException {
		setProperty(BACKGROUND, background, null);
	}

	@Override
	public String getBackgroundAttachment() {
		return getPropertyValue(BACKGROUND_ATTACHMENT);
	}

	@Override
	public void setBackgroundAttachment(final String backgroundAttachment) throws DOMException {
		setProperty(BACKGROUND_ATTACHMENT, backgroundAttachment, null);
	}

	@Override
	public String getBackgroundColor() {
		return getPropertyValue(BACKGROUND_COLOR);
	}

	@Override
	public void setBackgroundColor(final String backgroundColor) throws DOMException {
		setProperty(BACKGROUND_COLOR, backgroundColor, null);
	}

	@Override
	public String getBackgroundImage() {
		return getPropertyValue(BACKGROUND_IMAGE);
	}

	@Override
	public void setBackgroundImage(final String backgroundImage) throws DOMException {
		setProperty(BACKGROUND_IMAGE, backgroundImage, null);
	}

	@Override
	public String getBackgroundPosition() {
		return getPropertyValue(BACKGROUND_POSITION);
	}

	@Override
	public void setBackgroundPosition(final String backgroundPosition) throws DOMException {
		setProperty(BACKGROUND_POSITION, backgroundPosition, null);
	}

	@Override
	public String getBackgroundRepeat() {
		return getPropertyValue(BACKGROUND_REPEAT);
	}

	@Override
	public void setBackgroundRepeat(final String backgroundRepeat) throws DOMException {
		setProperty(BACKGROUND_REPEAT, backgroundRepeat, null);
	}

	@Override
	public String getBorder() {
		return getPropertyValue(BORDER);
	}

	@Override
	public void setBorder(final String border) throws DOMException {
		setProperty(BORDER, border, null);
	}

	@Override
	public String getBorderCollapse() {
		return getPropertyValue(BORDER_COLLAPSE);
	}

	@Override
	public void setBorderCollapse(final String borderCollapse) throws DOMException {
		setProperty(BORDER_COLLAPSE, borderCollapse, null);
	}

	@Override
	public String getBorderColor() {
		return getPropertyValue(BORDER_COLOR);
	}

	@Override
	public void setBorderColor(final String borderColor) throws DOMException {
		setProperty(BORDER_COLOR, borderColor, null);
	}

	@Override
	public String getBorderSpacing() {
		return getPropertyValue(BORDER_SPACING);
	}

	@Override
	public void setBorderSpacing(final String borderSpacing) throws DOMException {
		setProperty(BORDER_SPACING, borderSpacing, null);
	}

	@Override
	public String getBorderStyle() {
		return getPropertyValue(BORDER_STYLE);
	}

	@Override
	public void setBorderStyle(final String borderStyle) throws DOMException {
		setProperty(BORDER_STYLE, borderStyle, null);
	}

	@Override
	public String getBorderTop() {
		return getPropertyValue(BORDER_TOP);
	}

	@Override
	public void setBorderTop(final String borderTop) throws DOMException {
		setProperty(BORDER_TOP, borderTop, null);
	}

	@Override
	public String getBorderRight() {
		return getPropertyValue(BORDER_RIGHT);
	}

	@Override
	public void setBorderRight(final String borderRight) throws DOMException {
		setProperty(BORDER_RIGHT, borderRight, null);
	}

	@Override
	public String getBorderBottom() {
		return getPropertyValue(BORDER_BOTTOM);
	}

	@Override
	public void setBorderBottom(final String borderBottom) throws DOMException {
		setProperty(BORDER_BOTTOM, borderBottom, null);
	}

	@Override
	public String getBorderLeft() {
		return getPropertyValue(BORDER_LEFT);
	}

	@Override
	public void setBorderLeft(final String borderLeft) throws DOMException {
		setProperty(BORDER_LEFT, borderLeft, null);
	}

	@Override
	public String getBorderTopColor() {
		return getPropertyValue(BORDER_TOP_COLOR);
	}

	@Override
	public void setBorderTopColor(final String borderTopColor) throws DOMException {
		setProperty(BORDER_TOP_COLOR, borderTopColor, null);
	}

	@Override
	public String getBorderRightColor() {
		return getPropertyValue(BORDER_RIGHT_COLOR);
	}

	@Override
	public void setBorderRightColor(final String borderRightColor) throws DOMException {
		setProperty(BORDER_RIGHT_COLOR, borderRightColor, null);
	}

	@Override
	public String getBorderBottomColor() {
		return getPropertyValue(BORDER_BOTTOM_COLOR);
	}

	@Override
	public void setBorderBottomColor(final String borderBottomColor) throws DOMException {
		setProperty(BORDER_BOTTOM_COLOR, borderBottomColor, null);
	}

	@Override
	public String getBorderLeftColor() {
		return getPropertyValue(BORDER_LEFT_COLOR);
	}

	@Override
	public void setBorderLeftColor(final String borderLeftColor) throws DOMException {
		setProperty(BORDER_LEFT_COLOR, borderLeftColor, null);
	}

	@Override
	public String getBorderTopStyle() {
		return getPropertyValue(BORDER_TOP_STYLE);
	}

	@Override
	public void setBorderTopStyle(final String borderTopStyle) throws DOMException {
		setProperty(BORDER_TOP_STYLE, borderTopStyle, null);
	}

	@Override
	public String getBorderRightStyle() {
		return getPropertyValue(BORDER_RIGHT_STYLE);
	}

	@Override
	public void setBorderRightStyle(final String borderRightStyle) throws DOMException {
		setProperty(BORDER_RIGHT_STYLE, borderRightStyle, null);
	}

	@Override
	public String getBorderBottomStyle() {
		return getPropertyValue(BORDER_BOTTOM_STYLE);
	}

	@Override
	public void setBorderBottomStyle(final String borderBottomStyle) throws DOMException {
		setProperty(BORDER_BOTTOM_STYLE, borderBottomStyle, null);
	}

	@Override
	public String getBorderLeftStyle() {
		return getPropertyValue(BORDER_LEFT_STYLE);
	}

	@Override
	public void setBorderLeftStyle(final String borderLeftStyle) throws DOMException {
		setProperty(BORDER_LEFT_STYLE, borderLeftStyle, null);
	}

	@Override
	public String getBorderTopWidth() {
		return getPropertyValue(BORDER_TOP_WIDTH);
	}

	@Override
	public void setBorderTopWidth(final String borderTopWidth) throws DOMException {
		setProperty(BORDER_TOP_WIDTH, borderTopWidth, null);
	}

	@Override
	public String getBorderRightWidth() {
		return getPropertyValue(BORDER_RIGHT_WIDTH);
	}

	@Override
	public void setBorderRightWidth(final String borderRightWidth) throws DOMException {
		setProperty(BORDER_RIGHT_WIDTH, borderRightWidth, null);
	}

	@Override
	public String getBorderBottomWidth() {
		return getPropertyValue(BORDER_BOTTOM_WIDTH);
	}

	@Override
	public void setBorderBottomWidth(final String borderBottomWidth) throws DOMException {
		setProperty(BORDER_BOTTOM_WIDTH, borderBottomWidth, null);
	}

	@Override
	public String getBorderLeftWidth() {
		return getPropertyValue(BORDER_LEFT_WIDTH);
	}

	@Override
	public void setBorderLeftWidth(final String borderLeftWidth) throws DOMException {
		setProperty(BORDER_LEFT_WIDTH, borderLeftWidth, null);
	}

	@Override
	public String getBorderWidth() {
		return getPropertyValue(BORDER_WIDTH);
	}

	@Override
	public void setBorderWidth(final String borderWidth) throws DOMException {
		setProperty(BORDER_WIDTH, borderWidth, null);
	}

	@Override
	public String getBottom() {
		return getPropertyValue(BOTTOM);
	}

	@Override
	public void setBottom(final String bottom) throws DOMException {
		setProperty(BOTTOM, bottom, null);
	}

	@Override
	public String getCaptionSide() {
		return getPropertyValue(CAPTION_SIDE);
	}

	@Override
	public void setCaptionSide(final String captionSide) throws DOMException {
		setProperty(CAPTION_SIDE, captionSide, null);
	}

	@Override
	public String getClear() {
		return getPropertyValue(CLEAR);
	}

	@Override
	public void setClear(final String clear) throws DOMException {
		setProperty(CLEAR, clear, null);
	}

	@Override
	public String getClip() {
		return getPropertyValue(CLIP);
	}

	@Override
	public void setClip(final String clip) throws DOMException {
		setProperty(CLIP, clip, null);
	}

	@Override
	public String getColor() {
		return getPropertyValue(COLOR);
	}

	@Override
	public void setColor(final String color) throws DOMException {
		setProperty(COLOR, color, null);
	}

	@Override
	public String getContent() {
		return getPropertyValue(CONTENT);
	}

	@Override
	public void setContent(final String content) throws DOMException {
		setProperty(CONTENT, content, null);
	}

	@Override
	public String getCounterIncrement() {
		return getPropertyValue(COUNTER_INCREMENT);
	}

	@Override
	public void setCounterIncrement(final String counterIncrement) throws DOMException {
		setProperty(COUNTER_INCREMENT, counterIncrement, null);
	}

	@Override
	public String getCounterReset() {
		return getPropertyValue(COUNTER_RESET);
	}

	@Override
	public void setCounterReset(final String counterReset) throws DOMException {
		setProperty(COUNTER_RESET, counterReset, null);
	}

	@Override
	public String getCue() {
		return getPropertyValue(CUE);
	}

	@Override
	public void setCue(final String cue) throws DOMException {
		setProperty(CUE, cue, null);
	}

	@Override
	public String getCueAfter() {
		return getPropertyValue(CUE_AFTER);
	}

	@Override
	public void setCueAfter(final String cueAfter) throws DOMException {
		setProperty(CUE_AFTER, cueAfter, null);
	}

	@Override
	public String getCueBefore() {
		return getPropertyValue(CUE_BEFORE);
	}

	@Override
	public void setCueBefore(final String cueBefore) throws DOMException {
		setProperty(CUE_BEFORE, cueBefore, null);
	}

	@Override
	public String getCursor() {
		return getPropertyValue(CURSOR);
	}

	@Override
	public void setCursor(final String cursor) throws DOMException {
		setProperty(CURSOR, cursor, null);
	}

	@Override
	public String getDirection() {
		return getPropertyValue(DIRECTION);
	}

	@Override
	public void setDirection(final String direction) throws DOMException {
		setProperty(DIRECTION, direction, null);
	}

	@Override
	public String getDisplay() {
		return getPropertyValue(DISPLAY);
	}

	@Override
	public void setDisplay(final String display) throws DOMException {
		setProperty(DISPLAY, display, null);
	}

	@Override
	public String getElevation() {
		return getPropertyValue(ELEVATION);
	}

	@Override
	public void setElevation(final String elevation) throws DOMException {
		setProperty(ELEVATION, elevation, null);
	}

	@Override
	public String getEmptyCells() {
		return getPropertyValue(EMPTY_CELLS);
	}

	@Override
	public void setEmptyCells(final String emptyCells) throws DOMException {
		setProperty(EMPTY_CELLS, emptyCells, null);
	}

	@Override
	public String getCssFloat() {
		return getPropertyValue(CSS_FLOAT);
	}

	@Override
	public void setCssFloat(final String cssFloat) throws DOMException {
		setProperty(CSS_FLOAT, cssFloat, null);
	}

	@Override
	public String getFont() {
		return getPropertyValue(FONT);
	}

	@Override
	public void setFont(final String font) throws DOMException {
		setProperty(FONT, font, null);
	}

	@Override
	public String getFontFamily() {
		return getPropertyValue(FONT_FAMILY);
	}

	@Override
	public void setFontFamily(final String fontFamily) throws DOMException {
		setProperty(FONT_FAMILY, fontFamily, null);
	}

	@Override
	public String getFontSize() {
		return getPropertyValue(FONT_SIZE);
	}

	@Override
	public void setFontSize(final String fontSize) throws DOMException {
		setProperty(FONT_SIZE, fontSize, null);
	}

	@Override
	public String getFontSizeAdjust() {
		return getPropertyValue(FONT_SIZE_ADJUST);
	}

	@Override
	public void setFontSizeAdjust(final String fontSizeAdjust) throws DOMException {
		setProperty(FONT_SIZE_ADJUST, fontSizeAdjust, null);
	}

	@Override
	public String getFontStretch() {
		return getPropertyValue(FONT_STRETCH);
	}

	@Override
	public void setFontStretch(final String fontStretch) throws DOMException {
		setProperty(FONT_STRETCH, fontStretch, null);
	}

	@Override
	public String getFontStyle() {
		return getPropertyValue(FONT_STYLE);
	}

	@Override
	public void setFontStyle(final String fontStyle) throws DOMException {
		setProperty(FONT_STYLE, fontStyle, null);
	}

	@Override
	public String getFontVariant() {
		return getPropertyValue(FONT_VARIANT);
	}

	@Override
	public void setFontVariant(final String fontVariant) throws DOMException {
		setProperty(FONT_VARIANT, fontVariant, null);
	}

	@Override
	public String getFontWeight() {
		return getPropertyValue(FONT_WEIGHT);
	}

	@Override
	public void setFontWeight(final String fontWeight) throws DOMException {
		setProperty(FONT_WEIGHT, fontWeight, null);
	}

	@Override
	public String getHeight() {
		return getPropertyValue(HEIGHT);
	}

	@Override
	public void setHeight(final String height) throws DOMException {
		setProperty(HEIGHT, height, null);
	}

	@Override
	public String getLeft() {
		return getPropertyValue(LEFT);
	}

	@Override
	public void setLeft(final String left) throws DOMException {
		setProperty(LEFT, left, null);
	}

	@Override
	public String getLetterSpacing() {
		return getPropertyValue(LETTER_SPACING);
	}

	@Override
	public void setLetterSpacing(final String letterSpacing) throws DOMException {
		setProperty(LETTER_SPACING, letterSpacing, null);
	}

	@Override
	public String getLineHeight() {
		return getPropertyValue(LINE_HEIGHT);
	}

	@Override
	public void setLineHeight(final String lineHeight) throws DOMException {
		setProperty(LINE_HEIGHT, lineHeight, null);
	}

	@Override
	public String getListStyle() {
		return getPropertyValue(LIST_STYLE);
	}

	@Override
	public void setListStyle(final String listStyle) throws DOMException {
		setProperty(LIST_STYLE, listStyle, null);
	}

	@Override
	public String getListStyleImage() {
		return getPropertyValue(LIST_STYLE_IMAGE);
	}

	@Override
	public void setListStyleImage(final String listStyleImage) throws DOMException {
		setProperty(LIST_STYLE_IMAGE, listStyleImage, null);
	}

	@Override
	public String getListStylePosition() {
		return getPropertyValue(LIST_STYLE_POSITION);
	}

	@Override
	public void setListStylePosition(final String listStylePosition) throws DOMException {
		setProperty(LIST_STYLE_POSITION, listStylePosition, null);
	}

	@Override
	public String getListStyleType() {
		return getPropertyValue(LIST_STYLE_TYPE);
	}

	@Override
	public void setListStyleType(final String listStyleType) throws DOMException {
		setProperty(LIST_STYLE_TYPE, listStyleType, null);
	}

	@Override
	public String getMargin() {
		return getPropertyValue(MARGIN);
	}

	@Override
	public void setMargin(final String margin) throws DOMException {
		setProperty(MARGIN, margin, null);
	}

	@Override
	public String getMarginTop() {
		return getPropertyValue(MARGIN_TOP);
	}

	@Override
	public void setMarginTop(final String marginTop) throws DOMException {
		setProperty(MARGIN_TOP, marginTop, null);
	}

	@Override
	public String getMarginRight() {
		return getPropertyValue(MARGIN_RIGHT);
	}

	@Override
	public void setMarginRight(final String marginRight) throws DOMException {
		setProperty(MARGIN_RIGHT, marginRight, null);
	}

	@Override
	public String getMarginBottom() {
		return getPropertyValue(MARGIN_BOTTOM);
	}

	@Override
	public void setMarginBottom(final String marginBottom) throws DOMException {
		setProperty(MARGIN_BOTTOM, marginBottom, null);
	}

	@Override
	public String getMarginLeft() {
		return getPropertyValue(MARGIN_LEFT);
	}

	@Override
	public void setMarginLeft(final String marginLeft) throws DOMException {
		setProperty(MARGIN_LEFT, marginLeft, null);
	}

	@Override
	public String getMarkerOffset() {
		return getPropertyValue(MARKER_OFFSET);
	}

	@Override
	public void setMarkerOffset(final String markerOffset) throws DOMException {
		setProperty(MARKER_OFFSET, markerOffset, null);
	}

	@Override
	public String getMarks() {
		return getPropertyValue(MARKS);
	}

	@Override
	public void setMarks(final String marks) throws DOMException {
		setProperty(MARKS, marks, null);
	}

	@Override
	public String getMaxHeight() {
		return getPropertyValue(MAX_HEIGHT);
	}

	@Override
	public void setMaxHeight(final String maxHeight) throws DOMException {
		setProperty(MAX_HEIGHT, maxHeight, null);
	}

	@Override
	public String getMaxWidth() {
		return getPropertyValue(MAX_WIDTH);
	}

	@Override
	public void setMaxWidth(final String maxWidth) throws DOMException {
		setProperty(MAX_WIDTH, maxWidth, null);
	}

	@Override
	public String getMinHeight() {
		return getPropertyValue(MIN_HEIGHT);
	}

	@Override
	public void setMinHeight(final String minHeight) throws DOMException {
		setProperty(MIN_HEIGHT, minHeight, null);
	}

	@Override
	public String getMinWidth() {
		return getPropertyValue(MIN_WIDTH);
	}

	@Override
	public void setMinWidth(final String minWidth) throws DOMException {
		setProperty(MIN_WIDTH, minWidth, null);
	}

	@Override
	public String getOrphans() {
		return getPropertyValue(ORPHANS);
	}

	@Override
	public void setOrphans(final String orphans) throws DOMException {
		setProperty(ORPHANS, orphans, null);
	}

	@Override
	public String getOutline() {
		return getPropertyValue(OUTLINE);
	}

	@Override
	public void setOutline(final String outline) throws DOMException {
		setProperty(OUTLINE, outline, null);
	}

	@Override
	public String getOutlineColor() {
		return getPropertyValue(OUTLINE_COLOR);
	}

	@Override
	public void setOutlineColor(final String outlineColor) throws DOMException {
		setProperty(OUTLINE_COLOR, outlineColor, null);
	}

	@Override
	public String getOutlineStyle() {
		return getPropertyValue(OUTLINE_STYLE);
	}

	@Override
	public void setOutlineStyle(final String outlineStyle) throws DOMException {
		setProperty(OUTLINE_STYLE, outlineStyle, null);
	}

	@Override
	public String getOutlineWidth() {
		return getPropertyValue(OUTLINE_WIDTH);
	}

	@Override
	public void setOutlineWidth(final String outlineWidth) throws DOMException {
		setProperty(OUTLINE_WIDTH, outlineWidth, null);
	}

	@Override
	public String getOverflow() {
		return getPropertyValue(OVERFLOW);
	}

	@Override
	public void setOverflow(final String overflow) throws DOMException {
		setProperty(OVERFLOW, overflow, null);
	}

	@Override
	public String getPadding() {
		return getPropertyValue(PADDING);
	}

	@Override
	public void setPadding(final String padding) throws DOMException {
		setProperty(PADDING, padding, null);
	}

	@Override
	public String getPaddingTop() {
		return getPropertyValue(PADDING_TOP);
	}

	@Override
	public void setPaddingTop(final String paddingTop) throws DOMException {
		setProperty(PADDING_TOP, paddingTop, null);
	}

	@Override
	public String getPaddingRight() {
		return getPropertyValue(PADDING_RIGHT);
	}

	@Override
	public void setPaddingRight(final String paddingRight) throws DOMException {
		setProperty(PADDING_RIGHT, paddingRight, null);
	}

	@Override
	public String getPaddingBottom() {
		return getPropertyValue(PADDING_BOTTOM);
	}

	@Override
	public void setPaddingBottom(final String paddingBottom) throws DOMException {
		setProperty(PADDING_BOTTOM, paddingBottom, null);
	}

	@Override
	public String getPaddingLeft() {
		return getPropertyValue(PADDING_LEFT);
	}

	@Override
	public void setPaddingLeft(final String paddingLeft) throws DOMException {
		setProperty(PADDING_LEFT, paddingLeft, null);
	}

	@Override
	public String getPage() {
		return getPropertyValue(PAGE);
	}

	@Override
	public void setPage(final String page) throws DOMException {
		setProperty(PAGE, page, null);
	}

	@Override
	public String getPageBreakAfter() {
		return getPropertyValue(PAGE_BREAK_AFTER);
	}

	@Override
	public void setPageBreakAfter(final String pageBreakAfter) throws DOMException {
		setProperty(PAGE_BREAK_AFTER, pageBreakAfter, null);
	}

	@Override
	public String getPageBreakBefore() {
		return getPropertyValue(PAGE_BREAK_BEFORE);
	}

	@Override
	public void setPageBreakBefore(final String pageBreakBefore) throws DOMException {
		setProperty(PAGE_BREAK_BEFORE, PAGE_BREAK_BEFORE, null);
	}

	@Override
	public String getPageBreakInside() {
		return getPropertyValue(PAGE_BREAK_INSIDE);
	}

	@Override
	public void setPageBreakInside(final String pageBreakInside) throws DOMException {
		setProperty(PAGE_BREAK_INSIDE, pageBreakInside, null);
	}

	@Override
	public String getPause() {
		return getPropertyValue(PAUSE);
	}

	@Override
	public void setPause(final String pause) throws DOMException {
		setProperty(PAUSE, pause, null);
	}

	@Override
	public String getPauseAfter() {
		return getPropertyValue(PAUSE_AFTER);
	}

	@Override
	public void setPauseAfter(final String pauseAfter) throws DOMException {
		setProperty(PAUSE_AFTER, pauseAfter, null);
	}

	@Override
	public String getPauseBefore() {
		return getPropertyValue(PAUSE_BEFORE);
	}

	@Override
	public void setPauseBefore(final String pauseBefore) throws DOMException {
		setProperty(PAUSE_BEFORE, PAUSE_BEFORE, null);
	}

	@Override
	public String getPitch() {
		return getPropertyValue(PITCH);
	}

	@Override
	public void setPitch(final String pitch) throws DOMException {
		setProperty(PITCH, pitch, null);
	}

	@Override
	public String getPitchRange() {
		return getPropertyValue(PITCH_RANGE);
	}

	@Override
	public void setPitchRange(final String pitchRange) throws DOMException {
		setProperty(PITCH_RANGE, pitchRange, null);
	}

	@Override
	public String getPlayDuring() {
		return getPropertyValue(PLAY_DURING);
	}

	@Override
	public void setPlayDuring(final String playDuring) throws DOMException {
		setProperty(PLAY_DURING, playDuring, null);
	}

	@Override
	public String getPosition() {
		return getPropertyValue(POSITION);
	}

	@Override
	public void setPosition(final String position) throws DOMException {
		setProperty(POSITION, position, null);
	}

	@Override
	public String getQuotes() {
		return getPropertyValue(QUOTES);
	}

	@Override
	public void setQuotes(final String quotes) throws DOMException {
		setProperty(QUOTES, quotes, null);
	}

	@Override
	public String getRichness() {
		return getPropertyValue(RICHNESS);
	}

	@Override
	public void setRichness(final String richness) throws DOMException {
		setProperty(RICHNESS, richness, null);
	}

	@Override
	public String getRight() {
		return getPropertyValue(RIGHT);
	}

	@Override
	public void setRight(final String right) throws DOMException {
		setProperty(RIGHT, right, null);
	}

	@Override
	public String getSize() {
		return getPropertyValue(SIZE);
	}

	@Override
	public void setSize(final String size) throws DOMException {
		setProperty(SIZE, size, null);
	}

	@Override
	public String getSpeak() {
		return getPropertyValue(SPEAK);
	}

	@Override
	public void setSpeak(final String speak) throws DOMException {
		setProperty(SPEAK, speak, null);
	}

	@Override
	public String getSpeakHeader() {
		return getPropertyValue(SPEAK_HEADER);
	}

	@Override
	public void setSpeakHeader(final String speakHeader) throws DOMException {
		setProperty(SPEAK_HEADER, speakHeader, null);
	}

	@Override
	public String getSpeakNumeral() {
		return getPropertyValue(SPEAK_NUMERAL);
	}

	@Override
	public void setSpeakNumeral(final String speakNumeral) throws DOMException {
		setProperty(SPEAK_NUMERAL, speakNumeral, null);
	}

	@Override
	public String getSpeakPunctuation() {
		return getPropertyValue(SPEAK_PUNCTUATION);
	}

	@Override
	public void setSpeakPunctuation(final String speakPunctuation) throws DOMException {
		setProperty(SPEAK_PUNCTUATION, speakPunctuation, null);
	}

	@Override
	public String getSpeechRate() {
		return getPropertyValue(SPEECH_RATE);
	}

	@Override
	public void setSpeechRate(final String speechRate) throws DOMException {
		setProperty(SPEECH_RATE, speechRate, null);
	}

	@Override
	public String getStress() {
		return getPropertyValue(STRESS);
	}

	@Override
	public void setStress(final String stress) throws DOMException {
		setProperty(STRESS, stress, null);
	}

	@Override
	public String getTableLayout() {
		return getPropertyValue(TABLE_LAYOUT);
	}

	@Override
	public void setTableLayout(final String tableLayout) throws DOMException {
		setProperty(TABLE_LAYOUT, tableLayout, null);
	}

	@Override
	public String getTextAlign() {
		return getPropertyValue(TEXT_ALIGN);
	}

	@Override
	public void setTextAlign(final String textAlign) throws DOMException {
		setProperty(TEXT_ALIGN, textAlign, null);
	}

	@Override
	public String getTextDecoration() {
		return getPropertyValue(TEXT_DECORATION);
	}

	@Override
	public void setTextDecoration(final String textDecoration) throws DOMException {
		setProperty(TEXT_DECORATION, textDecoration, null);
	}

	@Override
	public String getTextIndent() {
		return getPropertyValue(TEXT_INDENT);
	}

	@Override
	public void setTextIndent(final String textIndent) throws DOMException {
		setProperty(TEXT_INDENT, textIndent, null);
	}

	@Override
	public String getTextShadow() {
		return getPropertyValue(TEXT_SHADOW);
	}

	@Override
	public void setTextShadow(final String textShadow) throws DOMException {
		setProperty(TEXT_SHADOW, textShadow, null);
	}

	@Override
	public String getTextTransform() {
		return getPropertyValue(TEXT_TRANSFORM);
	}

	@Override
	public void setTextTransform(final String textTransform) throws DOMException {
		setProperty(TEXT_TRANSFORM, textTransform, null);
	}

	@Override
	public String getTop() {
		return getPropertyValue(TOP);
	}

	@Override
	public void setTop(final String top) throws DOMException {
		setProperty(TOP, top, null);
	}

	@Override
	public String getUnicodeBidi() {
		return getPropertyValue(UNICODE_BIDI);
	}

	@Override
	public void setUnicodeBidi(final String unicodeBidi) throws DOMException {
		setProperty(UNICODE_BIDI, unicodeBidi, null);
	}

	@Override
	public String getVerticalAlign() {
		return getPropertyValue(VERTICAL_ALIGN);
	}

	@Override
	public void setVerticalAlign(final String verticalAlign) throws DOMException {
		setProperty(VERTICAL_ALIGN, verticalAlign, null);
	}

	@Override
	public String getVisibility() {
		return getPropertyValue(VISIBILITY);
	}

	@Override
	public void setVisibility(final String visibility) throws DOMException {
		setProperty(VISIBILITY, visibility, null);
	}

	@Override
	public String getVoiceFamily() {
		return getPropertyValue(VOICE_FAMILY);
	}

	@Override
	public void setVoiceFamily(final String voiceFamily) throws DOMException {
		setProperty(VOICE_FAMILY, voiceFamily, null);
	}

	@Override
	public String getVolume() {
		return getPropertyValue(VOLUME);
	}

	@Override
	public void setVolume(final String volume) throws DOMException {
		setProperty(VOLUME, volume, null);
	}

	@Override
	public String getWhiteSpace() {
		return getPropertyValue(WHITE_SPACE);
	}

	@Override
	public void setWhiteSpace(final String whiteSpace) throws DOMException {
		setProperty(WHITE_SPACE, whiteSpace, null);
	}

	@Override
	public String getWidows() {
		return getPropertyValue(WIDOWS);
	}

	@Override
	public void setWidows(final String widows) throws DOMException {
		setProperty(WIDOWS, widows, null);
	}

	@Override
	public String getWidth() {
		return getPropertyValue(WIDTH);
	}

	@Override
	public void setWidth(final String width) throws DOMException {
		setProperty(WIDTH, width, null);
	}

	@Override
	public String getWordSpacing() {
		return getPropertyValue(WORD_SPACING);
	}

	@Override
	public void setWordSpacing(final String wordSpacing) throws DOMException {
		setProperty(WORD_SPACING, wordSpacing, null);
	}

	@Override
	public String getZIndex() {
		return getPropertyValue(Z_INDEX);
	}

	@Override
	public void setZIndex(final String zIndex) throws DOMException {
		setProperty(Z_INDEX, zIndex, null);
	}

	// ---- end CSS2Properties interface ----
}
