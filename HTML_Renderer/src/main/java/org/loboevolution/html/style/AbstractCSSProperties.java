/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.style;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.loboevolution.html.style.setter.BackgroundSetter;
import org.loboevolution.html.style.setter.BorderSetter1;
import org.loboevolution.html.style.setter.BorderSetter2;
import org.loboevolution.html.style.setter.FontSetter;
import org.loboevolution.html.style.setter.FourCornersSetter;
import org.loboevolution.html.style.setter.Property;
import org.loboevolution.html.style.setter.SubPropertySetter;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.util.Objects;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.steadystate.css.util.CSSProperties;

/**
 * The Class AbstractCSSProperties.
 */
public abstract class AbstractCSSProperties extends AbstractScriptableDelegate
		implements CSSProperties, CSS3Properties {

	/** The Constant SUB_SETTERS. */
	private static final Map<String, Object> SUB_SETTERS = new HashMap<String, Object>(20);

	/** The context. */
	public final CSSPropertiesContext context;

	/** The local style properties. */
	private AbstractCSSProperties localStyleProperties;

	/** The style declarations. */
	private Collection<CSSStyleDeclaration> styleDeclarations;

	/** The value map. */
	private Map<String, Property> valueMap = null;

	/** The overlay color. */
	private String overlayColor;

	static {
		Map<String, Object> subSetters = SUB_SETTERS;
		subSetters.put(MARGIN, new FourCornersSetter(MARGIN, "margin-", ""));
		subSetters.put(PADDING, new FourCornersSetter(PADDING, "padding-", ""));
		subSetters.put(BORDER, new BorderSetter1());
		subSetters.put(BORDER_TOP, new BorderSetter2(BORDER_TOP));
		subSetters.put(BORDER_LEFT, new BorderSetter2(BORDER_LEFT));
		subSetters.put(BORDER_BOTTOM, new BorderSetter2(BORDER_BOTTOM));
		subSetters.put(BORDER_RIGHT, new BorderSetter2(BORDER_RIGHT));
		subSetters.put(BORDER_COLOR, new FourCornersSetter(BORDER_COLOR, "border-", "-color"));
		subSetters.put(BORDER_STYLE, new FourCornersSetter(BORDER_STYLE, "border-", "-style"));
		subSetters.put(BORDER_WIDTH, new FourCornersSetter(BORDER_WIDTH, "border-", "-width"));
		subSetters.put(FONT, new FontSetter());
	}

	/**
	 * Instantiates a new abstract cs s2 properties.
	 *
	 * @param context
	 *            the context
	 */
	public AbstractCSSProperties(CSSPropertiesContext context) {
		this.context = context;
	}

	/**
	 * Adds the style declaration.
	 *
	 * @param styleDeclaration
	 *            the style declaration
	 */
	public void addStyleDeclaration(CSSStyleDeclaration styleDeclaration) {
		synchronized (this) {
			Collection<CSSStyleDeclaration> sd = this.styleDeclarations;
			if (sd == null) {
				sd = new LinkedList<CSSStyleDeclaration>();
				this.styleDeclarations = sd;
			}
			sd.add(styleDeclaration);
			int length = styleDeclaration.getLength();
			for (int i = 0; i < length; i++) {
				String propertyName = styleDeclaration.item(i);
				String propertyValue = styleDeclaration.getPropertyValue(propertyName);
				String priority = styleDeclaration.getPropertyPriority(propertyName);
				boolean important = "important".equals(priority);
				this.setPropertyValueProcessed(propertyName.toLowerCase(), propertyValue, styleDeclaration, important);
			}
		}
	}

	/**
	 * Sets the local style properties.
	 *
	 * @param properties
	 *            the new local style properties
	 */
	public void setLocalStyleProperties(AbstractCSSProperties properties) {
		if (Objects.equals(properties, this)) {
			throw new IllegalStateException("setting same");
		}
		synchronized (this) {
			this.localStyleProperties = properties;
		}
	}

	/**
	 * Gets the local style properties.
	 *
	 * @return the local style properties
	 */
	public AbstractCSSProperties getLocalStyleProperties() {
		synchronized (this) {
			return this.localStyleProperties;
		}
	}
	
	/**
	 * Gets the property value.
	 *
	 * @param name
	 *            the name
	 * @return the property value
	 */
	public final String getPropertyValue(String name) {
		return this.getPropertyValueLC(name.toLowerCase());
	}

	/**
	 * Gets the property value lc.
	 *
	 * @param lowerCaseName
	 *            the lower case name
	 * @return the property value lc
	 */
	private final String getPropertyValueLC(String lowerCaseName) {
		Map<String, Property> vm = this.valueMap;
		synchronized (this) {
			// Local properties have precedence
			AbstractCSSProperties localProps = this.localStyleProperties;
			if (localProps != null) {
				String value = localProps.getPropertyValueLC(lowerCaseName);
				if (value != null) {
					return value;
				}
			}
			if (vm != null) {
				Property p = vm.get(lowerCaseName);
				return p == null ? null : p.value;
			}
		}
		return null;
	}

	/**
	 * Method called by property setters to set property values.
	 *
	 * @param lowerCaseName
	 *            The name of the property in lowercase.
	 * @param value
	 *            The property value.
	 */
	protected void setPropertyValueLC(String lowerCaseName, String value) {
		Map<String, Property> vm = this.valueMap;
		synchronized (this) {
			if (vm == null) {
				vm = new HashMap<String, Property>(1);
				this.valueMap = vm;
			}
			vm.put(lowerCaseName, new Property(value, true));
		}
	}

	/**
	 * Alternate method called to set property values from CSS declarations.
	 * <p>
	 * This method checks importance of the value.
	 *
	 * @param lowerCaseName
	 *            The name of the property in lowercase.
	 * @param value
	 *            The property value.
	 * @param important
	 *            the important
	 */
	public final void setPropertyValueLCAlt(String lowerCaseName, String value, boolean important) {
		Map<String, Property> vm = this.valueMap;
		synchronized (this) {
			if (vm == null) {
				vm = new HashMap<String, Property>(1);
				this.valueMap = vm;
			} else {
				if (!important) {
					Property oldProperty = vm.get(lowerCaseName);
					if (oldProperty != null && oldProperty.important) {
						// Ignore setting
						return;
					}
				}
			}
			vm.put(lowerCaseName, new Property(value, important));
		}
	}

	/**
	 * Sets the property value processed.
	 *
	 * @param lowerCaseName
	 *            the lower case name
	 * @param value
	 *            the value
	 * @param declaration
	 *            the declaration
	 * @param important
	 *            the important
	 */
	public final void setPropertyValueProcessed(String lowerCaseName, String value, CSSStyleDeclaration declaration,
			boolean important) {
		SubPropertySetter setter = (SubPropertySetter) SUB_SETTERS.get(lowerCaseName);
		if (setter != null) {
			setter.changeValue(this, value, declaration, important);
		} else {
			this.setPropertyValueLCAlt(lowerCaseName, value, important);
		}
	}

	/**
	 * Gets the overlay color.
	 *
	 * @return the overlay color
	 */
	public String getOverlayColor() {
		return this.overlayColor;
	}

	/**
	 * Sets the overlay color.
	 *
	 * @param value
	 *            the new overlay color
	 */
	public void setOverlayColor(String value) {
		this.overlayColor = value;
		this.context.informLookInvalid();
	}

	/**
	 * Gets the float.
	 *
	 * @return the float
	 */
	public String getFloat() {
		return this.getPropertyValueLC(FLOAT);
	}

	/**
	 * Sets the float.
	 *
	 * @param value
	 *            the Float.valueOf
	 */
	public void setFloat(String value) {
		this.setPropertyValueLC(FLOAT, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getAzimuth()
	 */
	@Override
	public String getAzimuth() {
		return this.getPropertyValueLC(AZIMUTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackground()
	 */
	@Override
	public String getBackground() {
		return this.getPropertyValueLC(BACKGROUND);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundAttachment()
	 */
	@Override
	public String getBackgroundAttachment() {
		return this.getPropertyValueLC(BACKGROUND_ATTACHMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundColor()
	 */
	@Override
	public String getBackgroundColor() {
		return this.getPropertyValueLC(BACKGROUND_COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundImage()
	 */
	@Override
	public String getBackgroundImage() {
		return this.getPropertyValueLC(BACKGROUND_IMAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundPosition()
	 */
	@Override
	public String getBackgroundPosition() {
		return this.getPropertyValueLC(BACKGROUND_POSITION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBackgroundRepeat()
	 */
	@Override
	public String getBackgroundRepeat() {
		return this.getPropertyValueLC(BACKGROUND_REPEAT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorder()
	 */
	@Override
	public String getBorder() {
		return this.getPropertyValueLC(BORDER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottom()
	 */
	@Override
	public String getBorderBottom() {
		return this.getPropertyValueLC(BORDER_BOTTOM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottomColor()
	 */
	@Override
	public String getBorderBottomColor() {
		return this.getPropertyValueLC(BORDER_BOTTOM_COLOR);
	}
	
	@Override
	public String getBoxSizing() {
		return this.getPropertyValueLC(BOX_SIZING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottomStyle()
	 */
	@Override
	public String getBorderBottomStyle() {
		return this.getPropertyValueLC(BORDER_BOTTOM_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderBottomWidth()
	 */
	@Override
	public String getBorderBottomWidth() {
		return this.getPropertyValueLC(BORDER_BOTTOM_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderCollapse()
	 */
	@Override
	public String getBorderCollapse() {
		return this.getPropertyValueLC(BORDER_COLLAPSE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderColor()
	 */
	@Override
	public String getBorderColor() {
		return this.getPropertyValueLC(BORDER_COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeft()
	 */
	@Override
	public String getBorderLeft() {
		return this.getPropertyValueLC(BORDER_LEFT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeftColor()
	 */
	@Override
	public String getBorderLeftColor() {
		return this.getPropertyValueLC(BORDER_LEFT_COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeftStyle()
	 */
	@Override
	public String getBorderLeftStyle() {
		return this.getPropertyValueLC(BORDER_LEFT_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderLeftWidth()
	 */
	@Override
	public String getBorderLeftWidth() {
		return this.getPropertyValueLC(BORDER_LEFT_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRight()
	 */
	@Override
	public String getBorderRight() {
		return this.getPropertyValueLC(BORDER_RIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRightColor()
	 */
	@Override
	public String getBorderRightColor() {
		return this.getPropertyValueLC(BORDER_RIGHT_COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRightStyle()
	 */
	@Override
	public String getBorderRightStyle() {
		return this.getPropertyValueLC(BORDER_RIGHT_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderRightWidth()
	 */
	@Override
	public String getBorderRightWidth() {
		return this.getPropertyValueLC(BORDER_RIGHT_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderSpacing()
	 */
	@Override
	public String getBorderSpacing() {
		return this.getPropertyValueLC(BORDER_SPACING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderStyle()
	 */
	@Override
	public String getBorderStyle() {
		return this.getPropertyValueLC(BORDER_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTop()
	 */
	@Override
	public String getBorderTop() {
		return this.getPropertyValueLC(BORDER_TOP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTopColor()
	 */
	@Override
	public String getBorderTopColor() {
		return this.getPropertyValueLC(BORDER_TOP_COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTopStyle()
	 */
	@Override
	public String getBorderTopStyle() {
		return this.getPropertyValueLC(BORDER_TOP_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderTopWidth()
	 */
	@Override
	public String getBorderTopWidth() {
		return this.getPropertyValueLC(BORDER_TOP_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBorderWidth()
	 */
	@Override
	public String getBorderWidth() {
		return this.getPropertyValueLC(BORDER_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getBottom()
	 */
	@Override
	public String getBottom() {
		return this.getPropertyValueLC(BOTTOM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCaptionSide()
	 */
	@Override
	public String getCaptionSide() {
		return this.getPropertyValueLC(CAPTION_SIDE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getClear()
	 */
	@Override
	public String getClear() {
		return this.getPropertyValueLC(CLEAR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getClip()
	 */
	@Override
	public String getClip() {
		return this.getPropertyValueLC(CLIP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getColor()
	 */
	@Override
	public String getColor() {

		return this.getPropertyValueLC(COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getContent()
	 */
	@Override
	public String getContent() {

		return this.getPropertyValueLC(CONTENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCounterIncrement()
	 */
	@Override
	public String getCounterIncrement() {

		return this.getPropertyValueLC(COUNTER_INCREMENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCounterReset()
	 */
	@Override
	public String getCounterReset() {

		return this.getPropertyValueLC(COUNTER_RESET);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCssFloat()
	 */
	@Override
	public String getCssFloat() {

		return this.getPropertyValueLC(CSS_FLOAT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCue()
	 */
	@Override
	public String getCue() {

		return this.getPropertyValueLC(CUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCueAfter()
	 */
	@Override
	public String getCueAfter() {

		return this.getPropertyValueLC(CUE_AFTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCueBefore()
	 */
	@Override
	public String getCueBefore() {

		return this.getPropertyValueLC(CUE_BEFORE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getCursor()
	 */
	@Override
	public String getCursor() {

		return this.getPropertyValueLC(CURSOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getDirection()
	 */
	@Override
	public String getDirection() {

		return this.getPropertyValueLC(DIRECTION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getDisplay()
	 */
	@Override
	public String getDisplay() {

		return this.getPropertyValueLC(DISPLAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getElevation()
	 */
	@Override
	public String getElevation() {

		return this.getPropertyValueLC(ELEVATION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getEmptyCells()
	 */
	@Override
	public String getEmptyCells() {

		return this.getPropertyValueLC(EMPTY_CELLS);
	}

	public String getFill() {
		return this.getPropertyValueLC(FILL);
	}

	public void setFill(String value) {
		this.setPropertyValueLC(FILL, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFont()
	 */
	@Override
	public String getFont() {

		return this.getPropertyValueLC(FONT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontFamily()
	 */
	@Override
	public String getFontFamily() {
		return this.getPropertyValueLC(FONT_FAMILY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontSize()
	 */
	@Override
	public String getFontSize() {
		return this.getPropertyValueLC(FONT_SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontSizeAdjust()
	 */
	@Override
	public String getFontSizeAdjust() {

		return this.getPropertyValueLC(FONT_SIZE_ADJUST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontStretch()
	 */
	@Override
	public String getFontStretch() {

		return this.getPropertyValueLC(FONT_STRETCH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontStyle()
	 */
	@Override
	public String getFontStyle() {

		return this.getPropertyValueLC(FONT_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontVariant()
	 */
	@Override
	public String getFontVariant() {

		return this.getPropertyValueLC(FONT_VARIANT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getFontWeight()
	 */
	@Override
	public String getFontWeight() {

		return this.getPropertyValueLC(FONT_WEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getHeight()
	 */
	@Override
	public String getHeight() {

		return this.getPropertyValueLC(HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getLeft()
	 */
	@Override
	public String getLeft() {

		return this.getPropertyValueLC(LEFT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getLetterSpacing()
	 */
	@Override
	public String getLetterSpacing() {

		return this.getPropertyValueLC(LETTER_SPACING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getLineHeight()
	 */
	@Override
	public String getLineHeight() {

		return this.getPropertyValueLC(LINE_HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStyle()
	 */
	@Override
	public String getListStyle() {
		return this.getPropertyValueLC(LIST_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStyleImage()
	 */
	@Override
	public String getListStyleImage() {
		return this.getPropertyValueLC(LIST_STYLE_IMAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStylePosition()
	 */
	@Override
	public String getListStylePosition() {
		return this.getPropertyValueLC(LIST_STYLE_POSITION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getListStyleType()
	 */
	@Override
	public String getListStyleType() {
		return this.getPropertyValueLC(LIST_STYLE_TYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMargin()
	 */
	@Override
	public String getMargin() {

		return this.getPropertyValueLC(MARGIN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginBottom()
	 */
	@Override
	public String getMarginBottom() {

		return this.getPropertyValueLC(MARGIN_BOTTOM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginLeft()
	 */
	@Override
	public String getMarginLeft() {

		return this.getPropertyValueLC(MARGIN_LEFT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginRight()
	 */
	@Override
	public String getMarginRight() {

		return this.getPropertyValueLC(MARGIN_RIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarginTop()
	 */
	@Override
	public String getMarginTop() {

		return this.getPropertyValueLC(MARGIN_TOP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarkerOffset()
	 */
	@Override
	public String getMarkerOffset() {

		return this.getPropertyValueLC(MARKER_OFFSET);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMarks()
	 */
	@Override
	public String getMarks() {

		return this.getPropertyValueLC(MARKS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMaxHeight()
	 */
	@Override
	public String getMaxHeight() {

		return this.getPropertyValueLC(MAX_HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMaxWidth()
	 */
	@Override
	public String getMaxWidth() {

		return this.getPropertyValueLC(MAX_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMinHeight()
	 */
	@Override
	public String getMinHeight() {

		return this.getPropertyValueLC(MIN_HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getMinWidth()
	 */
	@Override
	public String getMinWidth() {

		return this.getPropertyValueLC(MIN_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOrphans()
	 */
	@Override
	public String getOrphans() {

		return this.getPropertyValueLC(ORPHANS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutline()
	 */
	@Override
	public String getOutline() {

		return this.getPropertyValueLC(OUTLINE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutlineColor()
	 */
	@Override
	public String getOutlineColor() {

		return this.getPropertyValueLC(OUTLINE_COLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutlineStyle()
	 */
	@Override
	public String getOutlineStyle() {

		return this.getPropertyValueLC(OUTLINE_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOutlineWidth()
	 */
	@Override
	public String getOutlineWidth() {

		return this.getPropertyValueLC(OUTLINE_WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getOverflow()
	 */
	@Override
	public String getOverflow() {
		return this.getPropertyValueLC(OVERFLOW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPadding()
	 */
	@Override
	public String getPadding() {
		return this.getPropertyValueLC(PADDING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingBottom()
	 */
	@Override
	public String getPaddingBottom() {
		return this.getPropertyValueLC(PADDING_BOTTOM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingLeft()
	 */
	@Override
	public String getPaddingLeft() {
		return this.getPropertyValueLC(PADDING_LEFT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingRight()
	 */
	@Override
	public String getPaddingRight() {
		return this.getPropertyValueLC(PADDING_RIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPaddingTop()
	 */
	@Override
	public String getPaddingTop() {
		return this.getPropertyValueLC(PADDING_TOP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPage()
	 */
	@Override
	public String getPage() {
		return this.getPropertyValueLC(PAGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPageBreakAfter()
	 */
	@Override
	public String getPageBreakAfter() {
		return this.getPropertyValueLC(PAGE_BREAK_AFTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPageBreakBefore()
	 */
	@Override
	public String getPageBreakBefore() {
		return this.getPropertyValueLC(PAGE_BREAK_BEFORE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPageBreakInside()
	 */
	@Override
	public String getPageBreakInside() {
		return this.getPropertyValueLC(PAGE_BREAK_INSIDE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPause()
	 */
	@Override
	public String getPause() {
		return this.getPropertyValueLC(PAUSE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPauseAfter()
	 */
	@Override
	public String getPauseAfter() {
		return this.getPropertyValueLC(PAUSE_AFTER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPauseBefore()
	 */
	@Override
	public String getPauseBefore() {
		return this.getPropertyValueLC(PAUSE_BEFORE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPitch()
	 */
	@Override
	public String getPitch() {
		return this.getPropertyValueLC(PITCH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPitchRange()
	 */
	@Override
	public String getPitchRange() {
		return this.getPropertyValueLC(PITCH_RANGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPlayDuring()
	 */
	@Override
	public String getPlayDuring() {
		return this.getPropertyValueLC(PLAY_DURING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getPosition()
	 */
	@Override
	public String getPosition() {
		return this.getPropertyValueLC(POSITION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getQuotes()
	 */
	@Override
	public String getQuotes() {
		return this.getPropertyValueLC(QUOTES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getRichness()
	 */
	@Override
	public String getRichness() {
		return this.getPropertyValueLC(RICHNESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getRight()
	 */
	@Override
	public String getRight() {
		return this.getPropertyValueLC(RIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSize()
	 */
	@Override
	public String getSize() {
		return this.getPropertyValueLC(SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeak()
	 */
	@Override
	public String getSpeak() {
		return this.getPropertyValueLC(SPEAK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeakHeader()
	 */
	@Override
	public String getSpeakHeader() {
		return this.getPropertyValueLC(SPEAK_HEADER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeakNumeral()
	 */
	@Override
	public String getSpeakNumeral() {
		return this.getPropertyValueLC(SPEAK_NUMERAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeakPunctuation()
	 */
	@Override
	public String getSpeakPunctuation() {
		return this.getPropertyValueLC(SPEAK_PUNCTUATION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getSpeechRate()
	 */
	@Override
	public String getSpeechRate() {
		return this.getPropertyValueLC(SPEECH_RATE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getStress()
	 */
	@Override
	public String getStress() {
		return this.getPropertyValueLC(STRESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTableLayout()
	 */
	@Override
	public String getTableLayout() {
		return this.getPropertyValueLC(TABLE_LAYOUT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextAlign()
	 */
	@Override
	public String getTextAlign() {
		return this.getPropertyValueLC(TEXT_ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextDecoration()
	 */
	@Override
	public String getTextDecoration() {
		return this.getPropertyValueLC(TEXT_DECORATION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextIndent()
	 */
	@Override
	public String getTextIndent() {
		return this.getPropertyValueLC(TEXT_INDENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextShadow()
	 */
	@Override
	public String getTextShadow() {
		return this.getPropertyValueLC(TEXT_SHADOW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTextTransform()
	 */
	@Override
	public String getTextTransform() {
		return this.getPropertyValueLC(TEXT_TRANSFORM);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getTop()
	 */
	@Override
	public String getTop() {
		return this.getPropertyValueLC(TOP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getUnicodeBidi()
	 */
	@Override
	public String getUnicodeBidi() {
		return this.getPropertyValueLC(UNICODE_BIDI);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVerticalAlign()
	 */
	@Override
	public String getVerticalAlign() {
		return this.getPropertyValueLC(VERTICAL_ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVisibility()
	 */
	@Override
	public String getVisibility() {
		return this.getPropertyValueLC(VISIBILITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVoiceFamily()
	 */
	@Override
	public String getVoiceFamily() {
		return this.getPropertyValueLC(VOICE_FAMILY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getVolume()
	 */
	@Override
	public String getVolume() {
		return this.getPropertyValueLC(VOLUME);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWhiteSpace()
	 */
	@Override
	public String getWhiteSpace() {
		return this.getPropertyValueLC(WHITE_SPACE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWidows()
	 */
	@Override
	public String getWidows() {
		return this.getPropertyValueLC(WIDOWS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWidth()
	 */
	@Override
	public String getWidth() {
		return this.getPropertyValueLC(WIDTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getWordSpacing()
	 */
	@Override
	public String getWordSpacing() {
		return this.getPropertyValueLC(WORD_SPACING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#getZIndex()
	 */
	@Override
	public String getZIndex() {
		return this.getPropertyValueLC(Z_INDEX);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setAzimuth(java.lang.String)
	 */
	@Override
	public void setAzimuth(String azimuth) throws DOMException {
		this.setPropertyValueLC(AZIMUTH, azimuth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackground(java.lang.String)
	 */
	@Override
	public void setBackground(String background) throws DOMException {
		new BackgroundSetter().changeValue(this, background, null);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.w3c.dom.css.CSS2Properties#setBackgroundAttachment(java.lang.String)
	 */
	@Override
	public void setBackgroundAttachment(String backgroundAttachment) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_ATTACHMENT, backgroundAttachment);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundColor(java.lang.String)
	 */
	@Override
	public void setBackgroundColor(String backgroundColor) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_COLOR, backgroundColor);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundImage(java.lang.String)
	 */
	@Override
	public void setBackgroundImage(String backgroundImage) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_POSITION, backgroundImage);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.w3c.dom.css.CSS2Properties#setBackgroundPosition(java.lang.String)
	 */
	@Override
	public void setBackgroundPosition(String backgroundPosition) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_POSITION, backgroundPosition);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBackgroundRepeat(java.lang.String)
	 */
	@Override
	public void setBackgroundRepeat(String backgroundRepeat) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_REPEAT, backgroundRepeat);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorder(java.lang.String)
	 */
	@Override
	public void setBorder(String border) throws DOMException {
		new BorderSetter1().changeValue(this, border, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderBottom(java.lang.String)
	 */
	@Override
	public void setBorderBottom(String borderBottom) throws DOMException {
		new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottom, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.w3c.dom.css.CSS2Properties#setBorderBottomColor(java.lang.String)
	 */
	@Override
	public void setBorderBottomColor(String borderBottomColor) throws DOMException {
		this.setPropertyValueLC(BORDER_BOTTOM_COLOR, borderBottomColor);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.w3c.dom.css.CSS2Properties#setBorderBottomStyle(java.lang.String)
	 */
	@Override
	public void setBorderBottomStyle(String borderBottomStyle) throws DOMException {
		this.setPropertyValueLC(BORDER_BOTTOM_STYLE, borderBottomStyle);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.w3c.dom.css.CSS2Properties#setBorderBottomWidth(java.lang.String)
	 */
	@Override
	public void setBorderBottomWidth(String borderBottomWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_BOTTOM_WIDTH, borderBottomWidth);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderCollapse(java.lang.String)
	 */
	@Override
	public void setBorderCollapse(String borderCollapse) throws DOMException {
		this.setPropertyValueLC(BORDER_COLLAPSE, borderCollapse);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderColor(java.lang.String)
	 */
	@Override
	public void setBorderColor(String borderColor) throws DOMException {
		new FourCornersSetter(BORDER_COLOR, "border-", "-color").changeValue(this, borderColor, null);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeft(java.lang.String)
	 */
	@Override
	public void setBorderLeft(String borderLeft) throws DOMException {
		new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeft, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeftColor(java.lang.String)
	 */
	@Override
	public void setBorderLeftColor(String borderLeftColor) throws DOMException {
		this.setPropertyValueLC(BORDER_LEFT_COLOR, borderLeftColor);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeftStyle(java.lang.String)
	 */
	@Override
	public void setBorderLeftStyle(String borderLeftStyle) throws DOMException {
		this.setPropertyValueLC(BORDER_LEFT_STYLE, borderLeftStyle);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderLeftWidth(java.lang.String)
	 */
	@Override
	public void setBorderLeftWidth(String borderLeftWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_LEFT_WIDTH, borderLeftWidth);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRight(java.lang.String)
	 */
	@Override
	public void setBorderRight(String borderRight) throws DOMException {
		new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRight, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRightColor(java.lang.String)
	 */
	@Override
	public void setBorderRightColor(String borderRightColor) throws DOMException {
		this.setPropertyValueLC(BORDER_RIGHT_COLOR, borderRightColor);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRightStyle(java.lang.String)
	 */
	@Override
	public void setBorderRightStyle(String borderRightStyle) throws DOMException {
		this.setPropertyValueLC(BORDER_RIGHT_STYLE, borderRightStyle);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderRightWidth(java.lang.String)
	 */
	@Override
	public void setBorderRightWidth(String borderRightWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_RIGHT_WIDTH, borderRightWidth);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderSpacing(java.lang.String)
	 */
	@Override
	public void setBorderSpacing(String borderSpacing) throws DOMException {
		this.setPropertyValueLC(BORDER_SPACING, borderSpacing);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderStyle(java.lang.String)
	 */
	@Override
	public void setBorderStyle(String borderStyle) throws DOMException {
		new FourCornersSetter(BORDER_STYLE, "border-", "-style").changeValue(this, borderStyle, null);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTop(java.lang.String)
	 */
	@Override
	public void setBorderTop(String borderTop) throws DOMException {
		new BorderSetter2(BORDER_TOP).changeValue(this, borderTop, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTopColor(java.lang.String)
	 */
	@Override
	public void setBorderTopColor(String borderTopColor) throws DOMException {
		this.setPropertyValueLC(BORDER_TOP_COLOR, borderTopColor);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTopStyle(java.lang.String)
	 */
	@Override
	public void setBorderTopStyle(String borderTopStyle) throws DOMException {
		this.setPropertyValueLC(BORDER_TOP_STYLE, borderTopStyle);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderTopWidth(java.lang.String)
	 */
	@Override
	public void setBorderTopWidth(String borderTopWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_TOP_WIDTH, borderTopWidth);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBorderWidth(java.lang.String)
	 */
	@Override
	public void setBorderWidth(String borderWidth) throws DOMException {
		new FourCornersSetter(BORDER_WIDTH, "border-", "-width").changeValue(this, borderWidth, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setBottom(java.lang.String)
	 */
	@Override
	public void setBottom(String bottom) throws DOMException {
		this.setPropertyValueLC(BOTTOM, bottom);
		this.context.informPositionInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCaptionSide(java.lang.String)
	 */
	@Override
	public void setCaptionSide(String captionSide) throws DOMException {
		this.setPropertyValueLC(CAPTION_SIDE, captionSide);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setClear(java.lang.String)
	 */
	@Override
	public void setClear(String clear) throws DOMException {
		this.setPropertyValueLC(CLEAR, clear);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setClip(java.lang.String)
	 */
	@Override
	public void setClip(String clip) throws DOMException {
		this.setPropertyValueLC(CLIP, clip);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setColor(java.lang.String)
	 */
	@Override
	public void setColor(String color) throws DOMException {
		this.setPropertyValueLC(COLOR, color);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setContent(java.lang.String)
	 */
	@Override
	public void setContent(String content) throws DOMException {
		this.setPropertyValueLC(CONTENT, content);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCounterIncrement(java.lang.String)
	 */
	@Override
	public void setCounterIncrement(String counterIncrement) throws DOMException {
		this.setPropertyValueLC(COUNTER_INCREMENT, counterIncrement);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCounterReset(java.lang.String)
	 */
	@Override
	public void setCounterReset(String counterReset) throws DOMException {
		this.setPropertyValueLC(COUNTER_RESET, counterReset);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCssFloat(java.lang.String)
	 */
	@Override
	public void setCssFloat(String cssFloat) throws DOMException {
		this.setPropertyValueLC(CSS_FLOAT, cssFloat);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCue(java.lang.String)
	 */
	@Override
	public void setCue(String cue) throws DOMException {
		this.setPropertyValueLC(CUE, cue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCueAfter(java.lang.String)
	 */
	@Override
	public void setCueAfter(String cueAfter) throws DOMException {
		this.setPropertyValueLC(CUE_AFTER, cueAfter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCueBefore(java.lang.String)
	 */
	@Override
	public void setCueBefore(String cueBefore) throws DOMException {
		this.setPropertyValueLC(CUE_BEFORE, cueBefore);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setCursor(java.lang.String)
	 */
	@Override
	public void setCursor(String cursor) throws DOMException {
		this.setPropertyValueLC(CURSOR, cursor);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setDirection(java.lang.String)
	 */
	@Override
	public void setDirection(String direction) throws DOMException {
		this.setPropertyValueLC(DIRECTION, direction);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setDisplay(java.lang.String)
	 */
	@Override
	public void setDisplay(String display) throws DOMException {
		this.setPropertyValueLC(DISPLAY, display);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setElevation(java.lang.String)
	 */
	@Override
	public void setElevation(String elevation) throws DOMException {
		this.setPropertyValueLC(ELEVATION, elevation);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setEmptyCells(java.lang.String)
	 */
	@Override
	public void setEmptyCells(String emptyCells) throws DOMException {
		this.setPropertyValueLC(EMPTY_CELLS, emptyCells);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFont(java.lang.String)
	 */
	@Override
	public void setFont(String font) throws DOMException {
		new FontSetter().changeValue(this, font, null);
		this.context.informInvalid();
	}

	public String getFillOpacity() {
		return this.getPropertyValueLC(FILL_OPACITY);
	}

	public void setFillOpacity(String value) {
		this.setPropertyValueLC(FILL_OPACITY, value);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontFamily(java.lang.String)
	 */
	@Override
	public void setFontFamily(String fontFamily) throws DOMException {
		this.setPropertyValueLC(FONT_FAMILY, fontFamily);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontSize(java.lang.String)
	 */
	@Override
	public void setFontSize(String fontSize) throws DOMException {
		this.setPropertyValueLC(FONT_SIZE, fontSize);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontSizeAdjust(java.lang.String)
	 */
	@Override
	public void setFontSizeAdjust(String fontSizeAdjust) throws DOMException {
		this.setPropertyValueLC(FONT_SIZE_ADJUST, fontSizeAdjust);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontStretch(java.lang.String)
	 */
	@Override
	public void setFontStretch(String fontStretch) throws DOMException {
		this.setPropertyValueLC(FONT_STRETCH, fontStretch);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontStyle(java.lang.String)
	 */
	@Override
	public void setFontStyle(String fontStyle) throws DOMException {
		this.setPropertyValueLC(FONT_STYLE, fontStyle);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontVariant(java.lang.String)
	 */
	@Override
	public void setFontVariant(String fontVariant) throws DOMException {
		this.setPropertyValueLC(FONT_VARIANT, fontVariant);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setFontWeight(java.lang.String)
	 */
	@Override
	public void setFontWeight(String fontWeight) throws DOMException {
		this.setPropertyValueLC(FONT_WEIGHT, fontWeight);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setHeight(java.lang.String)
	 */
	@Override
	public void setHeight(String height) throws DOMException {
		this.setPropertyValueLC(HEIGHT, height);
		this.context.informSizeInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setLeft(java.lang.String)
	 */
	@Override
	public void setLeft(String left) throws DOMException {
		this.setPropertyValueLC(LEFT, left);
		this.context.informPositionInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setLetterSpacing(java.lang.String)
	 */
	@Override
	public void setLetterSpacing(String letterSpacing) throws DOMException {
		this.setPropertyValueLC(LETTER_SPACING, letterSpacing);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setLineHeight(java.lang.String)
	 */
	@Override
	public void setLineHeight(String lineHeight) throws DOMException {
		this.setPropertyValueLC(LINE_HEIGHT, lineHeight);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setListStyle(java.lang.String)
	 */
	@Override
	public void setListStyle(String listStyle) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE, listStyle);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setListStyleImage(java.lang.String)
	 */
	@Override
	public void setListStyleImage(String listStyleImage) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_IMAGE, listStyleImage);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.w3c.dom.css.CSS2Properties#setListStylePosition(java.lang.String)
	 */
	@Override
	public void setListStylePosition(String listStylePosition) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_POSITION, listStylePosition);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setListStyleType(java.lang.String)
	 */
	@Override
	public void setListStyleType(String listStyleType) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_TYPE, listStyleType);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMargin(java.lang.String)
	 */
	@Override
	public void setMargin(String margin) throws DOMException {
		new FourCornersSetter(MARGIN, "margin-", "").changeValue(this, margin, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginBottom(java.lang.String)
	 */
	@Override
	public void setMarginBottom(String marginBottom) throws DOMException {
		this.setPropertyValueLC(MARGIN_BOTTOM, marginBottom);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginLeft(java.lang.String)
	 */
	@Override
	public void setMarginLeft(String marginLeft) throws DOMException {
		this.setPropertyValueLC(MARGIN_LEFT, marginLeft);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginRight(java.lang.String)
	 */
	@Override
	public void setMarginRight(String marginRight) throws DOMException {
		this.setPropertyValueLC(MARGIN_RIGHT, marginRight);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarginTop(java.lang.String)
	 */
	@Override
	public void setMarginTop(String marginTop) throws DOMException {
		this.setPropertyValueLC(MARGIN_TOP, marginTop);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarkerOffset(java.lang.String)
	 */
	@Override
	public void setMarkerOffset(String markerOffset) throws DOMException {
		this.setPropertyValueLC(MARKER_OFFSET, markerOffset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMarks(java.lang.String)
	 */
	@Override
	public void setMarks(String marks) throws DOMException {
		this.setPropertyValueLC(MARKS, marks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMaxHeight(java.lang.String)
	 */
	@Override
	public void setMaxHeight(String maxHeight) throws DOMException {
		this.setPropertyValueLC(MAX_HEIGHT, maxHeight);
		this.context.informSizeInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMaxWidth(java.lang.String)
	 */
	@Override
	public void setMaxWidth(String maxWidth) throws DOMException {
		this.setPropertyValueLC(MAX_WIDTH, maxWidth);
		this.context.informSizeInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMinHeight(java.lang.String)
	 */
	@Override
	public void setMinHeight(String minHeight) throws DOMException {
		this.setPropertyValueLC(MIN_HEIGHT, minHeight);
		this.context.informSizeInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setMinWidth(java.lang.String)
	 */
	@Override
	public void setMinWidth(String minWidth) throws DOMException {
		this.setPropertyValueLC(MIN_WIDTH, minWidth);
		this.context.informSizeInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOrphans(java.lang.String)
	 */
	@Override
	public void setOrphans(String orphans) throws DOMException {
		this.setPropertyValueLC(ORPHANS, orphans);
	}

	public String getOpacity() {
		return this.getPropertyValueLC(OPACITY);
	}

	public void setOpacity(String value) {
		this.setPropertyValueLC(OPACITY, value);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutline(java.lang.String)
	 */
	@Override
	public void setOutline(String outline) throws DOMException {
		this.setPropertyValueLC(OUTLINE, outline);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutlineColor(java.lang.String)
	 */
	@Override
	public void setOutlineColor(String outlineColor) throws DOMException {
		this.setPropertyValueLC(OUTLINE_COLOR, outlineColor);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutlineStyle(java.lang.String)
	 */
	@Override
	public void setOutlineStyle(String outlineStyle) throws DOMException {
		this.setPropertyValueLC(OUTLINE_STYLE, outlineStyle);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOutlineWidth(java.lang.String)
	 */
	@Override
	public void setOutlineWidth(String outlineWidth) throws DOMException {
		this.setPropertyValueLC(OUTLINE_WIDTH, outlineWidth);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setOverflow(java.lang.String)
	 */
	@Override
	public void setOverflow(String overflow) throws DOMException {
		this.setPropertyValueLC(OVERFLOW, overflow);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPadding(java.lang.String)
	 */
	@Override
	public void setPadding(String padding) throws DOMException {
		new FourCornersSetter(PADDING, "padding-", "").changeValue(this, padding, null);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingBottom(java.lang.String)
	 */
	@Override
	public void setPaddingBottom(String paddingBottom) throws DOMException {
		this.setPropertyValueLC(PADDING_BOTTOM, paddingBottom);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingLeft(java.lang.String)
	 */
	@Override
	public void setPaddingLeft(String paddingLeft) throws DOMException {
		this.setPropertyValueLC(PADDING_LEFT, paddingLeft);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingRight(java.lang.String)
	 */
	@Override
	public void setPaddingRight(String paddingRight) throws DOMException {
		this.setPropertyValueLC(PADDING_RIGHT, paddingRight);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPaddingTop(java.lang.String)
	 */
	@Override
	public void setPaddingTop(String paddingTop) throws DOMException {
		this.setPropertyValueLC(PADDING_TOP, paddingTop);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPage(java.lang.String)
	 */
	@Override
	public void setPage(String page) throws DOMException {
		this.setPropertyValueLC(PAGE, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPageBreakAfter(java.lang.String)
	 */
	@Override
	public void setPageBreakAfter(String pageBreakAfter) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_AFTER, pageBreakAfter);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPageBreakBefore(java.lang.String)
	 */
	@Override
	public void setPageBreakBefore(String pageBreakBefore) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_BEFORE, pageBreakBefore);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPageBreakInside(java.lang.String)
	 */
	@Override
	public void setPageBreakInside(String pageBreakInside) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_INSIDE, pageBreakInside);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPause(java.lang.String)
	 */
	@Override
	public void setPause(String pause) throws DOMException {
		this.setPropertyValueLC(PAUSE, pause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPauseAfter(java.lang.String)
	 */
	@Override
	public void setPauseAfter(String pauseAfter) throws DOMException {
		this.setPropertyValueLC(PAUSE_AFTER, pauseAfter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPauseBefore(java.lang.String)
	 */
	@Override
	public void setPauseBefore(String pauseBefore) throws DOMException {
		this.setPropertyValueLC(PAUSE_BEFORE, pauseBefore);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPitch(java.lang.String)
	 */
	@Override
	public void setPitch(String pitch) throws DOMException {
		this.setPropertyValueLC(PITCH, pitch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPitchRange(java.lang.String)
	 */
	@Override
	public void setPitchRange(String pitchRange) throws DOMException {
		this.setPropertyValueLC(PITCH_RANGE, pitchRange);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPlayDuring(java.lang.String)
	 */
	@Override
	public void setPlayDuring(String playDuring) throws DOMException {
		this.setPropertyValueLC(PLAY_DURING, playDuring);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setPosition(java.lang.String)
	 */
	@Override
	public void setPosition(String position) throws DOMException {
		this.setPropertyValueLC(POSITION, position);
		this.context.informPositionInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setQuotes(java.lang.String)
	 */
	@Override
	public void setQuotes(String quotes) throws DOMException {
		this.setPropertyValueLC(QUOTES, quotes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setRichness(java.lang.String)
	 */
	@Override
	public void setRichness(String richness) throws DOMException {
		this.setPropertyValueLC(RICHNESS, richness);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setRight(java.lang.String)
	 */
	@Override
	public void setRight(String right) throws DOMException {
		this.setPropertyValueLC(RIGHT, right);
		this.context.informPositionInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSize(java.lang.String)
	 */
	@Override
	public void setSize(String size) throws DOMException {
		this.setPropertyValueLC(SIZE, size);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeak(java.lang.String)
	 */
	@Override
	public void setSpeak(String speak) throws DOMException {
		this.setPropertyValueLC(SPEAK, speak);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeakHeader(java.lang.String)
	 */
	@Override
	public void setSpeakHeader(String speakHeader) throws DOMException {
		this.setPropertyValueLC(SPEAK_HEADER, speakHeader);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeakNumeral(java.lang.String)
	 */
	@Override
	public void setSpeakNumeral(String speakNumeral) throws DOMException {
		this.setPropertyValueLC(SPEAK_NUMERAL, speakNumeral);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeakPunctuation(java.lang.String)
	 */
	@Override
	public void setSpeakPunctuation(String speakPunctuation) throws DOMException {
		this.setPropertyValueLC(SPEAK_PUNCTUATION, speakPunctuation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setSpeechRate(java.lang.String)
	 */
	@Override
	public void setSpeechRate(String speechRate) throws DOMException {
		this.setPropertyValueLC(SPEECH_RATE, speechRate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setStress(java.lang.String)
	 */
	@Override
	public void setStress(String stress) throws DOMException {
		this.setPropertyValueLC(STRESS, stress);
	}

	public String getStopColor() {
		return this.getPropertyValueLC(STOP_COLOR);
	}

	public String getStopOpacity() {
		return this.getPropertyValueLC(STOP_OPACITY);
	}

	public String getStroke() {
		return this.getPropertyValueLC(STROKE);
	}

	public void setStroke(String value) {
		this.setPropertyValueLC(STROKE, value);
		this.context.informInvalid();
	}

	public String getStrokeDashArray() {
		return this.getPropertyValueLC(STROKE_DASHARRAY);
	}

	public void setStrokeDashArray(String value) {
		this.setPropertyValueLC(STROKE_DASHARRAY, value);
		this.context.informInvalid();
	}

	public String getStrokeLineCap() {
		return this.getPropertyValueLC(STROKE_LINE_CAP);
	}

	public void setStrokeLineCap(String value) {
		this.setPropertyValueLC(STROKE_LINE_CAP, value);
		this.context.informInvalid();
	}

	public String getStrokeMiterLimit() {
		return this.getPropertyValueLC(STROKE_MITERLIMIT);
	}

	public void setStrokeMiterLimit(String value) {
		this.setPropertyValueLC(STROKE_MITERLIMIT, value);
		this.context.informInvalid();
	}

	public String getStrokeOpacity() {
		return this.getPropertyValueLC(STROKE_OPACITY);
	}

	public void setStrokeOpacity(String value) {
		this.setPropertyValueLC(STROKE_OPACITY, value);
		this.context.informInvalid();
	}

	public String getStrokeWidth() {
		return this.getPropertyValueLC(STROKE_WIDTH);
	}

	public void setStrokeWidth(String value) {
		this.setPropertyValueLC(STROKE_WIDTH, value);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTableLayout(java.lang.String)
	 */
	@Override
	public void setTableLayout(String tableLayout) throws DOMException {
		this.setPropertyValueLC(TABLE_LAYOUT, tableLayout);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextAlign(java.lang.String)
	 */
	@Override
	public void setTextAlign(String textAlign) throws DOMException {
		this.setPropertyValueLC(TEXT_ALIGN, textAlign);
		this.context.informLayoutInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextDecoration(java.lang.String)
	 */
	@Override
	public void setTextDecoration(String textDecoration) throws DOMException {
		this.setPropertyValueLC(TEXT_DECORATION, textDecoration);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextIndent(java.lang.String)
	 */
	@Override
	public void setTextIndent(String textIndent) throws DOMException {
		this.setPropertyValueLC(TEXT_INDENT, textIndent);
		this.context.informLayoutInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextShadow(java.lang.String)
	 */
	@Override
	public void setTextShadow(String textShadow) throws DOMException {
		this.setPropertyValueLC(TEXT_SHADOW, textShadow);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTextTransform(java.lang.String)
	 */
	@Override
	public void setTextTransform(String textTransform) throws DOMException {
		this.setPropertyValueLC(TEXT_TRANSFORM, textTransform);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setTop(java.lang.String)
	 */
	@Override
	public void setTop(String top) throws DOMException {
		this.setPropertyValueLC(TOP, top);
		this.context.informPositionInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setUnicodeBidi(java.lang.String)
	 */
	@Override
	public void setUnicodeBidi(String unicodeBidi) throws DOMException {
		this.setPropertyValueLC(UNICODE_BIDI, unicodeBidi);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVerticalAlign(java.lang.String)
	 */
	@Override
	public void setVerticalAlign(String verticalAlign) throws DOMException {
		this.setPropertyValueLC(VERTICAL_ALIGN, verticalAlign);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVisibility(java.lang.String)
	 */
	@Override
	public void setVisibility(String visibility) throws DOMException {
		this.setPropertyValueLC(VISIBILITY, visibility);
		this.context.informLookInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVoiceFamily(java.lang.String)
	 */
	@Override
	public void setVoiceFamily(String voiceFamily) throws DOMException {
		this.setPropertyValueLC(VOICE_FAMILY, voiceFamily);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setVolume(java.lang.String)
	 */
	@Override
	public void setVolume(String volume) throws DOMException {
		this.setPropertyValueLC(VOLUME, volume);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWhiteSpace(java.lang.String)
	 */
	@Override
	public void setWhiteSpace(String whiteSpace) throws DOMException {
		this.setPropertyValueLC(WHITE_SPACE, whiteSpace);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWidows(java.lang.String)
	 */
	@Override
	public void setWidows(String widows) throws DOMException {
		this.setPropertyValueLC(WIDOWS, widows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(String width) throws DOMException {
		this.setPropertyValueLC(WIDTH, width);
		this.context.informSizeInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setWordSpacing(java.lang.String)
	 */
	@Override
	public void setWordSpacing(String wordSpacing) throws DOMException {
		this.setPropertyValueLC(WORD_SPACING, wordSpacing);
		this.context.informInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.css.CSS2Properties#setZIndex(java.lang.String)
	 */
	@Override
	public void setZIndex(String zIndex) throws DOMException {
		this.setPropertyValueLC(Z_INDEX, zIndex);
		this.context.informPositionInvalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		int size;
		synchronized (this) {
			Map<String, Property> map = this.valueMap;
			size = map == null ? 0 : map.size();
		}
		return this.getClass().getSimpleName() + "[size=" + size + "]";
	}
}
