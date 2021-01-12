/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Nov 20, 2005
 */
package org.loboevolution.html.style;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.loboevolution.common.Objects;
import org.loboevolution.html.style.setter.BackgroundImageSetter;
import org.loboevolution.html.style.setter.BackgroundSetter;
import org.loboevolution.html.style.setter.BorderSetter1;
import org.loboevolution.html.style.setter.BorderSetter2;
import org.loboevolution.html.style.setter.BorderStyleSetter;
import org.loboevolution.html.style.setter.FontSetter;
import org.loboevolution.html.style.setter.FourCornersSetter;
import org.loboevolution.html.style.setter.PropertyCSS;
import org.loboevolution.html.style.setter.SubPropertySetter;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSS3Properties;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.Property;
import com.gargoylesoftware.css.util.CSSProperties;

/**
 * <p>AbstractCSSProperties class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class AbstractCSSProperties extends AbstractScriptableDelegate implements CSSProperties, CSS3Properties {
	
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
	}

	private final CSSPropertiesContext context;

	private AbstractCSSProperties localStyleProperties;

	private String overlayColor;

	private List<CSSStyleDeclarationImpl> styleDeclarations;

	private Map<String, PropertyCSS> valueMap = null;

	/**
	 * <p>Constructor for AbstractCSSProperties.</p>
	 *
	 * @param context a {@link org.loboevolution.html.style.CSSPropertiesContext} object.
	 */
	public AbstractCSSProperties(CSSPropertiesContext context) {
		this.context = context;
	}

	/**
	 * <p>addStyleDeclaration.</p>
	 *
	 * @param styleDeclaration a {@link com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl} object.
	 */
	public void addStyleDeclaration(CSSStyleDeclarationImpl styleDeclaration) {
		synchronized (this) {
			List<CSSStyleDeclarationImpl> sd = this.styleDeclarations;
			if (sd == null) {
				sd = new LinkedList<>();
				this.styleDeclarations = sd;
			}
			sd.add(styleDeclaration);
			for (Property prop : styleDeclaration.getProperties()) {
				final String propertyName = prop.getName();
				final String propertyValue = styleDeclaration.getPropertyValue(propertyName);
				final String priority = styleDeclaration.getPropertyPriority(propertyName);
				final boolean important = priority != null && priority.length() != 0 && "important".equals(priority);
				setPropertyValueProcessed(propertyName.toLowerCase(), propertyValue, styleDeclaration, important);
	      
	        }
		}
	}

	/**
	 * Does nothing but may be overridden. This method is called by some property
	 * setters.
	 */
	protected void checkSetProperty() {
	}

	/**
	 * <p>Getter for the field overlayColor.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getOverlayColor() {
		return this.overlayColor;
	}

	/**
	 * <p>Setter for the field overlayColor.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setOverlayColor(String value) {
		this.overlayColor = value;
		this.context.informLookInvalid();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getAlignItems() {
		return this.getPropertyValueLC(ALIGN_ITEMS);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getAlignContent() {
		return this.getPropertyValueLC(ALIGN_CONTENT);
	}

	/** {@inheritDoc} */
	@Override
	public String getAzimuth() {
		return this.getPropertyValueLC(AZIMUTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getBackground() {
		return this.getPropertyValueLC(BACKGROUND);
	}

	/** {@inheritDoc} */
	@Override
	public String getBackgroundAttachment() {
		return this.getPropertyValueLC(BACKGROUND_ATTACHMENT);
	}

	/** {@inheritDoc} */
	@Override
	public String getBackgroundColor() {
		return this.getPropertyValueLC(BACKGROUND_COLOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getBackgroundImage() {
		return this.getPropertyValueLC(BACKGROUND_IMAGE);
	}

	/** {@inheritDoc} */
	@Override
	public String getBackgroundPosition() {
		return this.getPropertyValueLC(BACKGROUND_POSITION);
	}

	/** {@inheritDoc} */
	@Override
	public String getBackgroundRepeat() {
		return this.getPropertyValueLC(BACKGROUND_REPEAT);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorder() {
		return this.getPropertyValueLC(BORDER);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderBottom() {
		return this.getPropertyValueLC(BORDER_BOTTOM);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderBottomColor() {
		return this.getPropertyValueLC(BORDER_BOTTOM_COLOR);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getBoxSizing() {
		return this.getPropertyValueLC(BOX_SIZING);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderBottomStyle() {
		return this.getPropertyValueLC(BORDER_BOTTOM_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderBottomWidth() {
		return this.getPropertyValueLC(BORDER_BOTTOM_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderCollapse() {
		return this.getPropertyValueLC(BORDER_COLLAPSE);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderColor() {
		return this.getPropertyValueLC(BORDER_COLOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderLeft() {
		return this.getPropertyValueLC(BORDER_LEFT);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderLeftColor() {
		return this.getPropertyValueLC(BORDER_LEFT_COLOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderLeftStyle() {
		return this.getPropertyValueLC(BORDER_LEFT_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderLeftWidth() {
		return this.getPropertyValueLC(BORDER_LEFT_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderRight() {
		return this.getPropertyValueLC(BORDER_RIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderRightColor() {
		return this.getPropertyValueLC(BORDER_RIGHT_COLOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderRightStyle() {
		return this.getPropertyValueLC(BORDER_RIGHT_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderRightWidth() {
		return this.getPropertyValueLC(BORDER_RIGHT_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderSpacing() {
		return this.getPropertyValueLC(BORDER_SPACING);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderStyle() {
		return this.getPropertyValueLC(BORDER_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderTop() {
		return this.getPropertyValueLC(BORDER_TOP);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderTopColor() {
		return this.getPropertyValueLC(BORDER_TOP_COLOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderTopStyle() {
		return this.getPropertyValueLC(BORDER_TOP_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderTopWidth() {
		return this.getPropertyValueLC(BORDER_TOP_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getBorderWidth() {
		return this.getPropertyValueLC(BORDER_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getBottom() {
		return this.getPropertyValueLC(BOTTOM);
	}

	/** {@inheritDoc} */
	@Override
	public String getCaptionSide() {
		return this.getPropertyValueLC(CAPTION_SIDE);
	}

	/** {@inheritDoc} */
	@Override
	public String getClear() {
		return this.getPropertyValueLC(CLEAR);
	}

	/** {@inheritDoc} */
	@Override
	public String getClip() {
		return this.getPropertyValueLC(CLIP);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getClipPath() {
		return this.getPropertyValueLC(CLIP_PATH);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getClipRule() {
		return this.getPropertyValueLC(CLIP_RULE);
	}

	/** {@inheritDoc} */
	@Override
	public String getColor() {
		return this.getPropertyValueLC(COLOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getContent() {
		return this.getPropertyValueLC(CONTENT);
	}

	/** {@inheritDoc} */
	@Override
	public String getCounterIncrement() {
		return this.getPropertyValueLC(COUNTER_INCREMENT);
	}

	/** {@inheritDoc} */
	@Override
	public String getCounterReset() {
		return this.getPropertyValueLC(COUNTER_RESET);
	}

	/** {@inheritDoc} */
	@Override
	public String getCssFloat() {
		return this.getPropertyValueLC(CSS_FLOAT);
	}

	/** {@inheritDoc} */
	@Override
	public String getCue() {
		return this.getPropertyValueLC(CUE);
	}

	/** {@inheritDoc} */
	@Override
	public String getCueAfter() {
		return this.getPropertyValueLC(CUE_AFTER);
	}

	/** {@inheritDoc} */
	@Override
	public String getCueBefore() {
		return this.getPropertyValueLC(CUE_BEFORE);
	}

	/** {@inheritDoc} */
	@Override
	public String getCursor() {
		return this.getPropertyValueLC(CURSOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getDirection() {
		return this.getPropertyValueLC(DIRECTION);
	}

	/** {@inheritDoc} */
	@Override
	public String getDisplay() {

		return this.getPropertyValueLC(DISPLAY);
	}

	/** {@inheritDoc} */
	@Override
	public String getElevation() {
		return this.getPropertyValueLC(ELEVATION);
	}

	/** {@inheritDoc} */
	@Override
	public String getEmptyCells() {
		return this.getPropertyValueLC(EMPTY_CELLS);
	}

	/** {@inheritDoc} */
	@Override
	public String getFont() {
		return this.getPropertyValueLC(FONT);
	}

	/** {@inheritDoc} */
	@Override
	public String getFontFamily() {
		return this.getPropertyValueLC(FONT_FAMILY);
	}

	/** {@inheritDoc} */
	@Override
	public String getFontSize() {
		return this.getPropertyValueLC(FONT_SIZE);
	}

	/** {@inheritDoc} */
	@Override
	public String getFontSizeAdjust() {
		return this.getPropertyValueLC(FONT_SIZE_ADJUST);
	}

	/** {@inheritDoc} */
	@Override
	public String getFontStretch() {
		return this.getPropertyValueLC(FONT_STRETCH);
	}

	/** {@inheritDoc} */
	@Override
	public String getFontStyle() {
		return this.getPropertyValueLC(FONT_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getFontVariant() {
		return this.getPropertyValueLC(FONT_VARIANT);
	}

	/** {@inheritDoc} */
	@Override
	public String getFontWeight() {
		return this.getPropertyValueLC(FONT_WEIGHT);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getFloat() {
		return this.getPropertyValueLC(FLOAT);
	}
	
	@Override
	public String getFlexDirection() {
		return this.getPropertyValueLC(FLEX_DIRECTION);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getFlexWrap() {
		return this.getPropertyValueLC(FLEX_WRAP);
	}

	/** {@inheritDoc} */
	@Override
	public String getFlexFlow() {
		return this.getPropertyValueLC(FLEX_FLOW);
	}
	
	@Override
	public String getJustifyContent() {
		return this.getPropertyValueLC(JUSTIFY_CONTENT);
	}
	
	/** {@inheritDoc} */
	@Override
	public String getHeight() {
		return this.getPropertyValueLC(HEIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getLeft() {
		return this.getPropertyValueLC(LEFT);
	}

	/** {@inheritDoc} */
	@Override
	public String getLetterSpacing() {
		return this.getPropertyValueLC(LETTER_SPACING);
	}

	/** {@inheritDoc} */
	@Override
	public String getLineHeight() {
		return this.getPropertyValueLC(LINE_HEIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getListStyle() {
		return this.getPropertyValueLC(LIST_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getListStyleImage() {
		return this.getPropertyValueLC(LIST_STYLE_IMAGE);
	}

	/** {@inheritDoc} */
	@Override
	public String getListStylePosition() {
		return this.getPropertyValueLC(LIST_STYLE_POSITION);
	}

	/** {@inheritDoc} */
	@Override
	public String getListStyleType() {
		return this.getPropertyValueLC(LIST_STYLE_TYPE);
	}

	/** {@inheritDoc} */
	@Override
	public String getMargin() {
		return this.getPropertyValueLC(MARGIN);
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginBottom() {
		return this.getPropertyValueLC(MARGIN_BOTTOM);
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginLeft() {
		return this.getPropertyValueLC(MARGIN_LEFT);
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginRight() {
		return this.getPropertyValueLC(MARGIN_RIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getMarginTop() {
		return this.getPropertyValueLC(MARGIN_TOP);
	}

	/** {@inheritDoc} */
	@Override
	public String getMarkerOffset() {
		return this.getPropertyValueLC(MARKER_OFFSET);
	}

	/** {@inheritDoc} */
	@Override
	public String getMarks() {
		return this.getPropertyValueLC(MARKS);
	}

	/** {@inheritDoc} */
	@Override
	public String getMaxHeight() {
		return this.getPropertyValueLC(MAX_HEIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getMaxWidth() {
		return this.getPropertyValueLC(MAX_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getMinHeight() {
		return this.getPropertyValueLC(MIN_HEIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getMinWidth() {
		return this.getPropertyValueLC(MIN_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getOrphans() {
		return this.getPropertyValueLC(ORPHANS);
	}

	/** {@inheritDoc} */
	@Override
	public String getOutline() {
		return this.getPropertyValueLC(OUTLINE);
	}

	/** {@inheritDoc} */
	@Override
	public String getOutlineColor() {
		return this.getPropertyValueLC(OUTLINE_COLOR);
	}

	/** {@inheritDoc} */
	@Override
	public String getOutlineStyle() {
		return this.getPropertyValueLC(OUTLINE_STYLE);
	}

	/** {@inheritDoc} */
	@Override
	public String getOutlineWidth() {
		return this.getPropertyValueLC(OUTLINE_WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getOverflow() {
		return this.getPropertyValueLC(OVERFLOW);
	}

	/** {@inheritDoc} */
	@Override
	public String getPadding() {
		return this.getPropertyValueLC(PADDING);
	}

	/** {@inheritDoc} */
	@Override
	public String getPaddingBottom() {
		return this.getPropertyValueLC(PADDING_BOTTOM);
	}


	/** {@inheritDoc} */
	@Override
	public String getPaddingLeft() {
		return this.getPropertyValueLC(PADDING_LEFT);
	}

	/** {@inheritDoc} */
	@Override
	public String getPaddingRight() {
		return this.getPropertyValueLC(PADDING_RIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getPaddingTop() {
		return this.getPropertyValueLC(PADDING_TOP);
	}

	/** {@inheritDoc} */
	@Override
	public String getPage() {
		return this.getPropertyValueLC(PAGE);
	}

	/** {@inheritDoc} */
	@Override
	public String getPageBreakAfter() {
		return this.getPropertyValueLC(PAGE_BREAK_AFTER);
	}

	/** {@inheritDoc} */
	@Override
	public String getPageBreakBefore() {
		return this.getPropertyValueLC(PAGE_BREAK_BEFORE);
	}

	/** {@inheritDoc} */
	@Override
	public String getPageBreakInside() {
		return this.getPropertyValueLC(PAGE_BREAK_INSIDE);
	}

	/** {@inheritDoc} */
	@Override
	public String getPause() {
		return this.getPropertyValueLC(PAUSE);
	}

	/** {@inheritDoc} */
	@Override
	public String getPauseAfter() {
		return this.getPropertyValueLC(PAUSE_AFTER);
	}

	/** {@inheritDoc} */
	@Override
	public String getPauseBefore() {
		return this.getPropertyValueLC(PAUSE_BEFORE);
	}

	/** {@inheritDoc} */
	@Override
	public String getPitch() {
		return this.getPropertyValueLC(PITCH);
	}

	/** {@inheritDoc} */
	@Override
	public String getPitchRange() {
		return this.getPropertyValueLC(PITCH_RANGE);
	}

	/** {@inheritDoc} */
	@Override
	public String getPlayDuring() {
		return this.getPropertyValueLC(PLAY_DURING);
	}

	/** {@inheritDoc} */
	@Override
	public String getPosition() {
		return this.getPropertyValueLC(POSITION);
	}

	/** {@inheritDoc} */
	@Override
	public String getQuotes() {
		return this.getPropertyValueLC(QUOTES);
	}

	/** {@inheritDoc} */
	@Override
	public String getRichness() {
		return this.getPropertyValueLC(RICHNESS);
	}

	/** {@inheritDoc} */
	@Override
	public String getRight() {
		return this.getPropertyValueLC(RIGHT);
	}

	/** {@inheritDoc} */
	@Override
	public String getSize() {
		return this.getPropertyValueLC(SIZE);
	}

	/** {@inheritDoc} */
	@Override
	public String getSpeak() {
		return this.getPropertyValueLC(SPEAK);
	}

	/** {@inheritDoc} */
	@Override
	public String getSpeakHeader() {
		return this.getPropertyValueLC(SPEAK_HEADER);
	}

	/** {@inheritDoc} */
	@Override
	public String getSpeakNumeral() {
		return this.getPropertyValueLC(SPEAK_NUMERAL);
	}

	/** {@inheritDoc} */
	@Override
	public String getSpeakPunctuation() {
		return this.getPropertyValueLC(SPEAK_PUNCTUATION);
	}

	/** {@inheritDoc} */
	@Override
	public String getSpeechRate() {
		return this.getPropertyValueLC(SPEECH_RATE);
	}

	/** {@inheritDoc} */
	@Override
	public String getStress() {
		return this.getPropertyValueLC(STRESS);
	}

	/** {@inheritDoc} */
	@Override
	public String getTableLayout() {
		return this.getPropertyValueLC(TABLE_LAYOUT);
	}

	/** {@inheritDoc} */
	@Override
	public String getTextAlign() {
		return this.getPropertyValueLC(TEXT_ALIGN);
	}


	/** {@inheritDoc} */
	@Override
	public String getTextDecoration() {
		return this.getPropertyValueLC(TEXT_DECORATION);
	}

	/** {@inheritDoc} */
	@Override
	public String getTextIndent() {
		return this.getPropertyValueLC(TEXT_INDENT);
	}

	/** {@inheritDoc} */
	@Override
	public String getTextShadow() {
		return this.getPropertyValueLC(TEXT_SHADOW);
	}

	/** {@inheritDoc} */
	@Override
	public String getTextTransform() {
		return this.getPropertyValueLC(TEXT_TRANSFORM);
	}

	/** {@inheritDoc} */
	@Override
	public String getTop() {
		return this.getPropertyValueLC(TOP);
	}

	/** {@inheritDoc} */
	@Override
	public String getUnicodeBidi() {
		return this.getPropertyValueLC(UNICODE_BIDI);
	}

	/** {@inheritDoc} */
	@Override
	public String getVerticalAlign() {
		return this.getPropertyValueLC(VERTICAL_ALIGN);
	}

	/** {@inheritDoc} */
	@Override
	public String getVisibility() {
		return this.getPropertyValueLC(VISIBILITY);
	}

	/** {@inheritDoc} */
	@Override
	public String getVoiceFamily() {
		return this.getPropertyValueLC(VOICE_FAMILY);
	}

	/** {@inheritDoc} */
	@Override
	public String getVolume() {
		return this.getPropertyValueLC(VOLUME);
	}

	/** {@inheritDoc} */
	@Override
	public String getWhiteSpace() {
		return this.getPropertyValueLC(WHITE_SPACE);
	}

	/** {@inheritDoc} */
	@Override
	public String getWidows() {
		return this.getPropertyValueLC(WIDOWS);
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return this.getPropertyValueLC(WIDTH);
	}

	/** {@inheritDoc} */
	@Override
	public String getWordSpacing() {
		return this.getPropertyValueLC(WORD_SPACING);
	}

	/** {@inheritDoc} */
	@Override
	public String getZIndex() {
		return this.getPropertyValueLC(Z_INDEX);
	}

	/** {@inheritDoc} */
	@Override
	public void setAzimuth(String azimuth) throws DOMException {
		this.setPropertyValueLC(AZIMUTH, azimuth);
	}

	/** {@inheritDoc} */
	@Override
	public void setBackground(String background) throws DOMException {
		new BackgroundSetter().changeValue(this, background, null);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBackgroundAttachment(String backgroundAttachment) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_ATTACHMENT, backgroundAttachment);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBackgroundColor(String backgroundColor) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_COLOR, backgroundColor);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBackgroundPosition(String backgroundPosition) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_POSITION, backgroundPosition);
		this.context.informLookInvalid();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setBackgroundImage(String backgroundImage) throws DOMException {
		checkSetProperty();
		new BackgroundImageSetter().changeValue(this, backgroundImage, null);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBackgroundRepeat(String backgroundRepeat) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_REPEAT, backgroundRepeat);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorder(String border) throws DOMException {
		new BorderSetter1().changeValue(this, border, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderBottom(String borderBottom) throws DOMException {
		new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottom, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderBottomColor(String borderBottomColor) throws DOMException {
		this.setPropertyValueLC(BORDER_BOTTOM_COLOR, borderBottomColor);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderBottomStyle(String borderBottomStyle) throws DOMException {
		new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottomStyle, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderBottomWidth(String borderBottomWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_BOTTOM_WIDTH, borderBottomWidth);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderCollapse(String borderCollapse) throws DOMException {
		this.setPropertyValueLC(BORDER_COLLAPSE, borderCollapse);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderColor(String borderColor) throws DOMException {
		new FourCornersSetter(BORDER_COLOR, "border-", "-color").changeValue(this, borderColor, null);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderLeft(String borderLeft) throws DOMException {
		new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeft, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderLeftColor(String borderLeftColor) throws DOMException {
		this.setPropertyValueLC(BORDER_LEFT_COLOR, borderLeftColor);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderLeftStyle(String borderLeftStyle) throws DOMException {
		new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeftStyle, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderLeftWidth(String borderLeftWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_LEFT_WIDTH, borderLeftWidth);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderRight(String borderRight) throws DOMException {
		new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRight, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderRightColor(String borderRightColor) throws DOMException {
		this.setPropertyValueLC(BORDER_RIGHT_COLOR, borderRightColor);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderRightStyle(String borderRightStyle) throws DOMException {
		new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRightStyle, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderRightWidth(String borderRightWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_RIGHT_WIDTH, borderRightWidth);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderSpacing(String borderSpacing) throws DOMException {
		this.setPropertyValueLC(BORDER_SPACING, borderSpacing);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderStyle(String borderStyle) throws DOMException {
		new FourCornersSetter(BORDER_STYLE, "border-", "-style").changeValue(this, borderStyle, null);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderTop(String borderTop) throws DOMException {
		new BorderSetter2(BORDER_TOP).changeValue(this, borderTop, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderTopColor(String borderTopColor) throws DOMException {
		this.setPropertyValueLC(BORDER_TOP_COLOR, borderTopColor);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderTopStyle(String borderTopStyle) throws DOMException {
		new BorderSetter2(BORDER_TOP).changeValue(this, borderTopStyle, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderTopWidth(String borderTopWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_TOP_WIDTH, borderTopWidth);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBorderWidth(String borderWidth) throws DOMException {
		new FourCornersSetter(BORDER_WIDTH, "border-", "-width").changeValue(this, borderWidth, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setBottom(String bottom) throws DOMException {
		this.setPropertyValueLC(BOTTOM, bottom);
		this.context.informPositionInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setCaptionSide(String captionSide) throws DOMException {
		this.setPropertyValueLC(CAPTION_SIDE, captionSide);
	}

	/** {@inheritDoc} */
	@Override
	public void setClear(String clear) throws DOMException {
		this.setPropertyValueLC(CLEAR, clear);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setClip(String clip) throws DOMException {
		this.setPropertyValueLC(CLIP, clip);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setClipPath(String clip) {
		this.setPropertyValueLC(CLIP_PATH,clip);
	}
	
	/** {@inheritDoc} */
	@Override
	public void setClipRule(String clip) {
		this.setPropertyValueLC(CLIP_RULE,clip);
	}

	/** {@inheritDoc} */
	@Override
	public void setColor(String color) throws DOMException {
		this.setPropertyValueLC(COLOR, color);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setContent(String content) throws DOMException {
		this.setPropertyValueLC(CONTENT, content);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setCounterIncrement(String counterIncrement) throws DOMException {
		this.setPropertyValueLC(COUNTER_INCREMENT, counterIncrement);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setCounterReset(String counterReset) throws DOMException {
		this.setPropertyValueLC(COUNTER_RESET, counterReset);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setCssFloat(String cssFloat) throws DOMException {
		this.setPropertyValueLC(CSS_FLOAT, cssFloat);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setCue(String cue) throws DOMException {
		this.setPropertyValueLC(CUE, cue);
	}

	/** {@inheritDoc} */
	@Override
	public void setCueAfter(String cueAfter) throws DOMException {
		this.setPropertyValueLC(CUE_AFTER, cueAfter);
	}

	/** {@inheritDoc} */
	@Override
	public void setCueBefore(String cueBefore) throws DOMException {
		this.setPropertyValueLC(CUE_BEFORE, cueBefore);
	}

	/** {@inheritDoc} */
	@Override
	public void setCursor(String cursor) throws DOMException {
		this.setPropertyValueLC(CURSOR, cursor);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setDirection(String direction) throws DOMException {
		this.setPropertyValueLC(DIRECTION, direction);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setDisplay(String display) throws DOMException {
		this.setPropertyValueLC(DISPLAY, display);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setElevation(String elevation) throws DOMException {
		this.setPropertyValueLC(ELEVATION, elevation);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setEmptyCells(String emptyCells) throws DOMException {
		this.setPropertyValueLC(EMPTY_CELLS, emptyCells);
	}

	/** {@inheritDoc} */
	@Override
	public void setFont(String font) throws DOMException {
		new FontSetter().changeValue(this, font, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setFontFamily(String fontFamily) throws DOMException {
		this.setPropertyValueLC(FONT_FAMILY, fontFamily);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setFontSize(String fontSize) throws DOMException {
		this.setPropertyValueLC(FONT_SIZE, fontSize);
		this.context.informInvalid();
	}


	/** {@inheritDoc} */
	@Override
	public void setFontSizeAdjust(String fontSizeAdjust) throws DOMException {
		this.setPropertyValueLC(FONT_SIZE_ADJUST, fontSizeAdjust);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setFontStretch(String fontStretch) throws DOMException {
		this.setPropertyValueLC(FONT_STRETCH, fontStretch);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setFontStyle(String fontStyle) throws DOMException {
		this.setPropertyValueLC(FONT_STYLE, fontStyle);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setFontVariant(String fontVariant) throws DOMException {
		this.setPropertyValueLC(FONT_VARIANT, fontVariant);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setFontWeight(String fontWeight) throws DOMException {
		this.setPropertyValueLC(FONT_WEIGHT, fontWeight);
		this.context.informInvalid();
	}
	
	/** {@inheritDoc} */
	@Override
	public void setFloat(String value) {
		this.setPropertyValueLC(FLOAT, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(String height) throws DOMException {
		this.setPropertyValueLC(HEIGHT, height);
		this.context.informSizeInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setLeft(String left) throws DOMException {
		this.setPropertyValueLC(LEFT, left);
		this.context.informPositionInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setLetterSpacing(String letterSpacing) throws DOMException {
		this.setPropertyValueLC(LETTER_SPACING, letterSpacing);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setLineHeight(String lineHeight) throws DOMException {
		this.setPropertyValueLC(LINE_HEIGHT, lineHeight);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setListStyle(String listStyle) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE, listStyle);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setListStyleImage(String listStyleImage) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_IMAGE, listStyleImage);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setListStylePosition(String listStylePosition) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_POSITION, listStylePosition);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setListStyleType(String listStyleType) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_TYPE, listStyleType);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMargin(String margin) throws DOMException {
		new FourCornersSetter(MARGIN, "margin-", "").changeValue(this, margin, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginBottom(String marginBottom) throws DOMException {
		this.setPropertyValueLC(MARGIN_BOTTOM, marginBottom);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginLeft(String marginLeft) throws DOMException {
		this.setPropertyValueLC(MARGIN_LEFT, marginLeft);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginRight(String marginRight) throws DOMException {
		this.setPropertyValueLC(MARGIN_RIGHT, marginRight);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMarginTop(String marginTop) throws DOMException {
		this.setPropertyValueLC(MARGIN_TOP, marginTop);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMarkerOffset(String markerOffset) throws DOMException {
		this.setPropertyValueLC(MARKER_OFFSET, markerOffset);
	}

	/** {@inheritDoc} */
	@Override
	public void setMarks(String marks) throws DOMException {
		this.setPropertyValueLC(MARKS, marks);
	}

	/** {@inheritDoc} */
	@Override
	public void setMaxHeight(String maxHeight) throws DOMException {
		this.setPropertyValueLC(MAX_HEIGHT, maxHeight);
		this.context.informSizeInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMaxWidth(String maxWidth) throws DOMException {
		this.setPropertyValueLC(MAX_WIDTH, maxWidth);
		this.context.informSizeInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMinHeight(String minHeight) throws DOMException {
		this.setPropertyValueLC(MIN_HEIGHT, minHeight);
		this.context.informSizeInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setMinWidth(String minWidth) throws DOMException {
		this.setPropertyValueLC(MIN_WIDTH, minWidth);
		this.context.informSizeInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setOrphans(String orphans) throws DOMException {
		this.setPropertyValueLC(ORPHANS, orphans);
	}

	/** {@inheritDoc} */
	@Override
	public void setOutline(String outline) throws DOMException {
		this.setPropertyValueLC(OUTLINE, outline);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setOutlineColor(String outlineColor) throws DOMException {
		this.setPropertyValueLC(OUTLINE_COLOR, outlineColor);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setOutlineStyle(String outlineStyle) throws DOMException {
		this.setPropertyValueLC(OUTLINE_STYLE, outlineStyle);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setOutlineWidth(String outlineWidth) throws DOMException {
		this.setPropertyValueLC(OUTLINE_WIDTH, outlineWidth);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setOverflow(String overflow) throws DOMException {
		this.setPropertyValueLC(OVERFLOW, overflow);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPadding(String padding) throws DOMException {
		new FourCornersSetter(PADDING, "padding-", "").changeValue(this, padding, null);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPaddingBottom(String paddingBottom) throws DOMException {
		this.setPropertyValueLC(PADDING_BOTTOM, paddingBottom);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPaddingLeft(String paddingLeft) throws DOMException {
		this.setPropertyValueLC(PADDING_LEFT, paddingLeft);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPaddingRight(String paddingRight) throws DOMException {
		this.setPropertyValueLC(PADDING_RIGHT, paddingRight);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPaddingTop(String paddingTop) throws DOMException {
		this.setPropertyValueLC(PADDING_TOP, paddingTop);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPage(String page) throws DOMException {
		this.setPropertyValueLC(PAGE, page);
	}

	/** {@inheritDoc} */
	@Override
	public void setPageBreakAfter(String pageBreakAfter) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_AFTER, pageBreakAfter);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPageBreakBefore(String pageBreakBefore) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_BEFORE, pageBreakBefore);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPageBreakInside(String pageBreakInside) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_INSIDE, pageBreakInside);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setPause(String pause) throws DOMException {
		this.setPropertyValueLC(PAUSE, pause);
	}


	/** {@inheritDoc} */
	@Override
	public void setPauseAfter(String pauseAfter) throws DOMException {
		this.setPropertyValueLC(PAUSE_AFTER, pauseAfter);
	}

	/** {@inheritDoc} */
	@Override
	public void setPauseBefore(String pauseBefore) throws DOMException {
		this.setPropertyValueLC(PAUSE_BEFORE, pauseBefore);
	}

	/** {@inheritDoc} */
	@Override
	public void setPitch(String pitch) throws DOMException {
		this.setPropertyValueLC(PITCH, pitch);
	}

	/** {@inheritDoc} */
	@Override
	public void setPitchRange(String pitchRange) throws DOMException {
		this.setPropertyValueLC(PITCH_RANGE, pitchRange);
	}

	/** {@inheritDoc} */
	@Override
	public void setPlayDuring(String playDuring) throws DOMException {
		this.setPropertyValueLC(PLAY_DURING, playDuring);
	}

	/** {@inheritDoc} */
	@Override
	public void setPosition(String position) throws DOMException {
		this.setPropertyValueLC(POSITION, position);
		this.context.informPositionInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setQuotes(String quotes) throws DOMException {
		this.setPropertyValueLC(QUOTES, quotes);
	}

	/** {@inheritDoc} */
	@Override
	public void setRichness(String richness) throws DOMException {
		this.setPropertyValueLC(RICHNESS, richness);
	}

	/** {@inheritDoc} */
	@Override
	public void setRight(String right) throws DOMException {
		this.setPropertyValueLC(RIGHT, right);
		this.context.informPositionInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(String size) throws DOMException {
		this.setPropertyValueLC(SIZE, size);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setSpeak(String speak) throws DOMException {
		this.setPropertyValueLC(SPEAK, speak);
	}

	/** {@inheritDoc} */
	@Override
	public void setSpeakHeader(String speakHeader) throws DOMException {
		this.setPropertyValueLC(SPEAK_HEADER, speakHeader);
	}

	/** {@inheritDoc} */
	@Override
	public void setSpeakNumeral(String speakNumeral) throws DOMException {
		this.setPropertyValueLC(SPEAK_NUMERAL, speakNumeral);
	}

	/** {@inheritDoc} */
	@Override
	public void setSpeakPunctuation(String speakPunctuation) throws DOMException {
		this.setPropertyValueLC(SPEAK_PUNCTUATION, speakPunctuation);
	}

	/** {@inheritDoc} */
	@Override
	public void setSpeechRate(String speechRate) throws DOMException {
		this.setPropertyValueLC(SPEECH_RATE, speechRate);
	}

	/** {@inheritDoc} */
	@Override
	public void setStress(String stress) throws DOMException {
		this.setPropertyValueLC(STRESS, stress);
	}

	/** {@inheritDoc} */
	@Override
	public void setTableLayout(String tableLayout) throws DOMException {
		this.setPropertyValueLC(TABLE_LAYOUT, tableLayout);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setTextAlign(String textAlign) throws DOMException {
		this.setPropertyValueLC(TEXT_ALIGN, textAlign);
		this.context.informLayoutInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setTextDecoration(String textDecoration) throws DOMException {
		this.setPropertyValueLC(TEXT_DECORATION, textDecoration);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setTextIndent(String textIndent) throws DOMException {
		this.setPropertyValueLC(TEXT_INDENT, textIndent);
		this.context.informLayoutInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setTextShadow(String textShadow) throws DOMException {
		this.setPropertyValueLC(TEXT_SHADOW, textShadow);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setTextTransform(String textTransform) throws DOMException {
		this.setPropertyValueLC(TEXT_TRANSFORM, textTransform);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setTop(String top) throws DOMException {
		this.setPropertyValueLC(TOP, top);
		this.context.informPositionInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setUnicodeBidi(String unicodeBidi) throws DOMException {
		this.setPropertyValueLC(UNICODE_BIDI, unicodeBidi);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setVerticalAlign(String verticalAlign) throws DOMException {
		this.setPropertyValueLC(VERTICAL_ALIGN, verticalAlign);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setVisibility(String visibility) throws DOMException {
		this.setPropertyValueLC(VISIBILITY, visibility);
		this.context.informLookInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setVoiceFamily(String voiceFamily) throws DOMException {
		this.setPropertyValueLC(VOICE_FAMILY, voiceFamily);
	}

	/** {@inheritDoc} */
	@Override
	public void setVolume(String volume) throws DOMException {
		this.setPropertyValueLC(VOLUME, volume);
	}

	/** {@inheritDoc} */
	@Override
	public void setWhiteSpace(String whiteSpace) throws DOMException {
		this.setPropertyValueLC(WHITE_SPACE, whiteSpace);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setWidows(String widows) throws DOMException {
		this.setPropertyValueLC(WIDOWS, widows);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) throws DOMException {
		this.setPropertyValueLC(WIDTH, width);
		this.context.informSizeInvalid();
	}

	/** {@inheritDoc} */
	@Override
	public void setWordSpacing(String wordSpacing) throws DOMException {
		this.setPropertyValueLC(WORD_SPACING, wordSpacing);
		this.context.informInvalid();
	}

	/** {@inheritDoc} */
	@Override
    public String getFill() {
        return this.getPropertyValueLC(FILL);
    }

    /**
     * <p>setFill.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setFill(String value) {
        this.setPropertyValueLC(FILL, value);
    }
    
    /**
     * <p>getFillOpacity.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFillOpacity() {
        return this.getPropertyValueLC(FILL_OPACITY);
    }

    /**
     * <p>setFillOpacity.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setFillOpacity(String value) {
        this.setPropertyValueLC(FILL_OPACITY, value);
        this.context.informInvalid();
    }
    
    /**
     * <p>getOpacity.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getOpacity() {
        return this.getPropertyValueLC(OPACITY);
    }

    /**
     * <p>setOpacity.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setOpacity(String value) {
        this.setPropertyValueLC(OPACITY, value);
        this.context.informInvalid();
    }
    
    /**
     * <p>getStopColor.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStopColor() {
        return this.getPropertyValueLC(STOP_COLOR);
    }

    /**
     * <p>getStopOpacity.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStopOpacity() {
        return this.getPropertyValueLC(STOP_OPACITY);
    }

    /**
     * <p>getStroke.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStroke() {
        return this.getPropertyValueLC(STROKE);
    }

    /**
     * <p>setStroke.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setStroke(String value) {
        this.setPropertyValueLC(STROKE, value);
        this.context.informInvalid();
    }

    /**
     * <p>getStrokeDashArray.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStrokeDashArray() {
        return this.getPropertyValueLC(STROKE_DASHARRAY);
    }

    /**
     * <p>setStrokeDashArray.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setStrokeDashArray(String value) {
        this.setPropertyValueLC(STROKE_DASHARRAY, value);
        this.context.informInvalid();
    }

    /**
     * <p>getStrokeLineCap.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStrokeLineCap() {
        return this.getPropertyValueLC(STROKE_LINE_CAP);
    }

    /**
     * <p>setStrokeLineCap.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setStrokeLineCap(String value) {
        this.setPropertyValueLC(STROKE_LINE_CAP, value);
        this.context.informInvalid();
    }
    
    /**
     * <p>getStrokeLineJoin.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStrokeLineJoin() {
        return this.getPropertyValueLC(STROKE_LINE_JOINP);
    }

    /**
     * <p>setStrokeLineJoin.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setStrokeLineJoin(String value) {
        this.setPropertyValueLC(STROKE_LINE_JOINP, value);
        this.context.informInvalid();
    }

    /**
     * <p>getStrokeMiterLimit.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStrokeMiterLimit() {
        return this.getPropertyValueLC(STROKE_MITERLIMIT);
    }

    /**
     * <p>setStrokeMiterLimit.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setStrokeMiterLimit(String value) {
        this.setPropertyValueLC(STROKE_MITERLIMIT, value);
        this.context.informInvalid();
    }

    /**
     * <p>getStrokeOpacity.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStrokeOpacity() {
        return this.getPropertyValueLC(STROKE_OPACITY);
    }

    /**
     * <p>setStrokeOpacity.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setStrokeOpacity(String value) {
        this.setPropertyValueLC(STROKE_OPACITY, value);
        this.context.informInvalid();
    }

    /**
     * <p>getStrokeWidth.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStrokeWidth() {
        return this.getPropertyValueLC(STROKE_WIDTH);
    }

    /**
     * <p>setStrokeWidth.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setStrokeWidth(String value) {
        this.setPropertyValueLC(STROKE_WIDTH, value);
        this.context.informInvalid();
    }
    
    /**
     * <p>getTransform.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTransform() {
        return this.getPropertyValueLC(TRANSFORM);
    }

    /**
     * <p>setTransform.</p>
     *
     * @param value a {@link java.lang.String} object.
     */
    public void setTransform(String value) {
        this.setPropertyValueLC(TRANSFORM, value);
        this.context.informInvalid();
    }

	/** {@inheritDoc} */
	@Override
	public void setZIndex(String zIndex) throws DOMException {
		this.setPropertyValueLC(Z_INDEX, zIndex);
		this.context.informPositionInvalid();
	}
	
	/**
	 * <p>getPropertyValue.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public final String getPropertyValue(String name) {
		return getPropertyValueLC(name.toLowerCase());
	}

	private String getPropertyValueLC(String lowerCaseName) {
		final Map<String, PropertyCSS> vm = this.valueMap;
		synchronized (this) {
			// Local properties have precedence
			final AbstractCSSProperties localProps = this.localStyleProperties;
			if (localProps != null) {
				final String value = localProps.getPropertyValueLC(lowerCaseName);
				if (value != null) {
					return value;
				}
			}
			if (vm != null) {
				final PropertyCSS p = vm.get(lowerCaseName);
				return p == null ? null : p.value;
			}
		}
		return null;
	}
	
	/**
	 * <p>setPropertyValueLC.</p>
	 *
	 * @param lowerCaseName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	protected void setPropertyValueLC(String lowerCaseName, String value) {
		Map<String, PropertyCSS> vm = this.valueMap;
		synchronized (this) {
			if (vm == null) {
				vm = new HashMap<>(1);
				this.valueMap = vm;
			}
			vm.put(lowerCaseName, new PropertyCSS(value, true));
		}
	}
	
	/**
	 * <p>setPropertyValueLCAlt.</p>
	 *
	 * @param lowerCaseName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @param important a boolean.
	 */
	public final void setPropertyValueLCAlt(String lowerCaseName, String value, boolean important) {
		Map<String, PropertyCSS> vm = this.valueMap;
		synchronized (this) {
			if (vm == null) {
				vm = new HashMap<>(1);
				this.valueMap = vm;
			} else {
				if (!important) {
					final PropertyCSS oldProperty = vm.get(lowerCaseName);
					if (oldProperty != null && oldProperty.important) {
						// Ignore setting
						return;
					}
				}
			}
			vm.put(lowerCaseName, new PropertyCSS(value, important));
		}
	}
	
	/**
	 * <p>setPropertyValueProcessed.</p>
	 *
	 * @param lowerCaseName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @param declaration a {@link com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl} object.
	 * @param important a boolean.
	 */
	public final void setPropertyValueProcessed(String lowerCaseName, String value, CSSStyleDeclarationImpl declaration,boolean important) {
		final SubPropertySetter setter = SUB_SETTERS.get(lowerCaseName);
		if (setter != null) {
			setter.changeValue(this, value, declaration, important);
		} else {
			setPropertyValueLCAlt(lowerCaseName, value, important);
		}
	}
	
	/**
	 * <p>Setter for the field localStyleProperties.</p>
	 *
	 * @param properties a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	public void setLocalStyleProperties(AbstractCSSProperties properties) {
		if (Objects.equals(properties, this)) {
			throw new IllegalStateException("setting same");
		}
		synchronized (this) {
			this.localStyleProperties = properties;
		}
	}

	public CSSPropertiesContext getContext() {
		return context;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		int size;
		synchronized (this) {
			final Map<String, PropertyCSS> map = this.valueMap;
			size = map == null ? 0 : map.size();
		}
		return this.getClass().getSimpleName() + "[size=" + size + "]";
	}
}
