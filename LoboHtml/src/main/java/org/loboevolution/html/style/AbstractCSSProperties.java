/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Nov 20, 2005
 */
package org.loboevolution.html.style;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Objects;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.CSSValues;
import org.loboevolution.info.FontInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSS3Properties;
import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.dom.Property;
import com.gargoylesoftware.css.util.CSSProperties;

public class AbstractCSSProperties extends AbstractScriptableDelegate implements CSSProperties, CSS3Properties {
	
	private static class BackgroundImageSetter implements SubPropertySetter {
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
				boolean important) {
			String baseHref = null;
			String finalValue;
			if (declaration != null) {
				final AbstractCSSRuleImpl rule = declaration.getParentRule();
				if (rule != null) {
					final CSSStyleSheetImpl sheet = rule.getParentStyleSheet();
					final CSSStyleSheetImpl ssheet = sheet;
					baseHref = ssheet.getHref();
				}
			}
			if (baseHref == null) {
				baseHref = properties.context.getDocumentBaseURI();
			}
			final String start = "url(";
			if (newValue == null || !newValue.toLowerCase().startsWith(start)) {
				finalValue = newValue;
			} else {
				final int startIdx = start.length();
				final int closingIdx = newValue.lastIndexOf(')');
				if (closingIdx == -1) {
					finalValue = newValue;
				} else {
					final String quotedUri = newValue.substring(startIdx, closingIdx);
					final String tentativeUri = HtmlValues.unquoteAndUnescape(quotedUri);
					if (baseHref == null) {
						finalValue = newValue;
					} else {
						try {
							final URL styleUrl = Urls.createURL(null, baseHref);
							if(tentativeUri.contains("data:image")) {
								finalValue = tentativeUri;
							} else {
								finalValue = "url(" + HtmlValues.quoteAndEscape(Urls.createURL(styleUrl, tentativeUri).toExternalForm()) + ")";
							}
							
						} catch (final Exception mfu) {
							logger.log(Level.WARNING, "Unable to create URL for URI=[" + tentativeUri + "], with base=[" + baseHref + "].", mfu);
							finalValue = newValue;
						}
					}
				}
			}
			properties.setPropertyValueLCAlt(BACKGROUND_IMAGE, finalValue, important);
		}
	}

	private static class BackgroundSetter implements SubPropertySetter {
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
				boolean important) {
			properties.setPropertyValueLCAlt(BACKGROUND, newValue, important);
			if (newValue != null && newValue.length() > 0) {
				final String[] tokens = HtmlValues.splitCssValue(newValue);
				boolean hasXPosition = false;
				boolean hasYPosition = false;
				String color = null;
				String image = null;
				String backgroundRepeat = null;
				String position = null;
				for (final String token : tokens) {
					if (ColorFactory.getInstance().isColor(token) || CSSValues.INITIAL.equals(CSSValues.get(token))) {
						color = token;
					} else if (HtmlValues.isUrl(token) || HtmlValues.isGradient(token)) {
						image = token;
					} else if (HtmlValues.isBackgroundRepeat(token)) {
						backgroundRepeat = token;
					} else if (HtmlValues.isBackgroundPosition(token)) {
						if (hasXPosition && !hasYPosition) {
							position += " " + token;
							hasYPosition = true;
						} else {
							hasXPosition = true;
							position = token;
						}
					}
				}
				if (color != null) {
					if(CSSValues.INITIAL.equals(CSSValues.get(color))){
						color = "white";
					}
					properties.setPropertyValueLCAlt(BACKGROUND_COLOR, color, important);
				}
				if (image != null) {
					properties.setPropertyValueProcessed(BACKGROUND_IMAGE, image, declaration, important);
				}
				if (backgroundRepeat != null) {
					properties.setPropertyValueLCAlt(BACKGROUND_REPEAT, backgroundRepeat, important);
				}
				if (position != null) {
					properties.setPropertyValueLCAlt(BACKGROUND_POSITION, position, important);
				}
			}
		}

	}

	private static class BorderSetter1 implements SubPropertySetter {
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration, boolean important) {
			properties.setPropertyValueLCAlt(BORDER, newValue, important);
			properties.setPropertyValueProcessed(BORDER_TOP, newValue, declaration, important);
			properties.setPropertyValueProcessed(BORDER_LEFT, newValue, declaration, important);
			properties.setPropertyValueProcessed(BORDER_BOTTOM, newValue, declaration, important);
			properties.setPropertyValueProcessed(BORDER_RIGHT, newValue, declaration, important);
		}
	}

	private static class BorderSetter2 implements SubPropertySetter {
		private final String name;

		public BorderSetter2(String baseName) {
			this.name = baseName;
		}

		public void changeValue(AbstractCSSProperties properties, String value, CSSStyleDeclarationImpl declaration) {
			this.changeValue(properties, value, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String value, CSSStyleDeclarationImpl declaration, boolean important) {
			properties.setPropertyValueLCAlt(this.name, value, important);
			if (Strings.isNotBlank(value)) {
				final String[] array = HtmlValues.splitCssValue(value);
				String color = null;
				String style = null;
				String width = null;
				for (final String token : array) {
					if (HtmlValues.isBorderStyle(token)) {
						style = token;
					} else if (ColorFactory.getInstance().isColor(token)) {
						color = token;
					} else {
						width = token;
					}
				}
				final String name = this.name;
				if (style != null) {
					properties.setPropertyValueLCAlt(name + "-style", style, important);
					if(color == null) {
						color = "black";
					}
					
					if(width == null) {
						width = "2px";
					}
				}
				
				if (color != null) {
					properties.setPropertyValueLCAlt(name + "-color", color, important);
				}
				if (width != null) {
					properties.setPropertyValueLCAlt(name + "-width", width, important);
				}				
			}
		}
	}

	private static class FontSetter implements SubPropertySetter {
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
				boolean important) {
			properties.setPropertyValueLCAlt(FONT, newValue, important);
			if (Strings.isNotBlank(newValue)) {
				final String fontSpecTL = newValue.toLowerCase();
				final FontInfo fontInfo = (FontInfo) HtmlValues.SYSTEM_FONTS.get(fontSpecTL);
				if (fontInfo != null) {
					if (fontInfo.getFontFamily() != null) {
						properties.setPropertyValueLCAlt(FONT_FAMILY, fontInfo.getFontFamily(), important);
					}
					if (fontInfo.getFontSize() != null) {
						properties.setPropertyValueLCAlt(FONT_SIZE, fontInfo.getFontSize(), important);
					}
					if (fontInfo.getFontStyle() != null) {
						properties.setPropertyValueLCAlt(FONT_STYLE, fontInfo.getFontStyle(), important);
					}
					if (fontInfo.getFontVariant() != null) {
						properties.setPropertyValueLCAlt(FONT_VARIANT, fontInfo.getFontVariant(), important);
					}
					if (fontInfo.getFontWeight() != null) {
						properties.setPropertyValueLCAlt(FONT_WEIGHT, fontInfo.getFontWeight(), important);
					}
					return;
				}
				final String[] tokens = HtmlValues.splitCssValue(fontSpecTL);
				String token = null;
				final int length = tokens.length;
				int i;
				for (i = 0; i < length; i++) {
					token = tokens[i];
					if (HtmlValues.isFontStyle(token)) {
						properties.setPropertyValueLCAlt(FONT_STYLE, token, important);
						continue;
					}
					if (HtmlValues.isFontVariant(token)) {
						properties.setPropertyValueLCAlt(FONT_VARIANT, token, important);
						continue;
					}
					if (HtmlValues.isFontWeight(token)) {
						properties.setPropertyValueLCAlt(FONT_WEIGHT, token, important);
						continue;
					}
					// Otherwise exit loop
					break;
				}
				if (token != null) {
					final int slashIdx = token.indexOf('/');
					final String fontSizeText = slashIdx == -1 ? token : token.substring(0, slashIdx);
					properties.setPropertyValueLCAlt(FONT_SIZE, fontSizeText, important);
					final String lineHeightText = slashIdx == -1 ? null : token.substring(slashIdx + 1);
					if (lineHeightText != null) {
						properties.setPropertyValueLCAlt(LINE_HEIGHT, lineHeightText, important);
					}
					if (++i < length) {
						final StringBuilder fontFamilyBuff = new StringBuilder();
						do {
							token = tokens[i];
							fontFamilyBuff.append(token);
							fontFamilyBuff.append(' ');
						} while (++i < length);
						properties.setPropertyValueLCAlt(FONT_FAMILY, fontFamilyBuff.toString(), important);
					}
				}
			}
		}
	}

	private static class FourCornersSetter implements SubPropertySetter {
		private final String prefix;
		private final String property;
		private final String suffix;

		public FourCornersSetter(String property, String prefix, String suffix) {
			this.prefix = prefix;
			this.suffix = suffix;
			this.property = property;
		}

		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration, boolean important) {
			
			if (Strings.isNotBlank(newValue)) {
				properties.setPropertyValueLCAlt(this.property, newValue, important);
				if (BORDER_STYLE.equals(property)) {
					properties.setPropertyValueLCAlt(BORDER_TOP_WIDTH, "2px", important);
					properties.setPropertyValueLCAlt(BORDER_BOTTOM_WIDTH, "2px", important);
					properties.setPropertyValueLCAlt(BORDER_LEFT_WIDTH, "2px", important);
					properties.setPropertyValueLCAlt(BORDER_RIGHT_WIDTH, "2px", important);
				}
				
				final String[] array = HtmlValues.splitCssValue(newValue);
				final int size = array.length;
				if (size == 1) {
					final String prefix = this.prefix;
					final String suffix = this.suffix;
					final String value = array[0];
					properties.setPropertyValueLCAlt(prefix + "top" + suffix, value, important);
					properties.setPropertyValueLCAlt(prefix + "right" + suffix, value, important);
					properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, value, important);
					properties.setPropertyValueLCAlt(prefix + "left" + suffix, value, important);
				} else if (size >= 4) {
					final String prefix = this.prefix;
					final String suffix = this.suffix;
					properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
					properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
					properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
					properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[3], important);
				} else if (size == 2) {
					final String prefix = this.prefix;
					final String suffix = this.suffix;
					properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
					properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
					properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[0], important);
					properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
				} else if (size == 3) {
					final String prefix = this.prefix;
					final String suffix = this.suffix;
					properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
					properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
					properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
					properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
				}
			}
		}
	}

	private static class PropertyCSS {
		public final boolean important;
		public final String value;

		public PropertyCSS(final String value, final boolean important) {
			this.value = value;
			this.important = important;
		}
	}

	private interface SubPropertySetter {
		void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
				boolean important);
	}

	private static final Logger logger = Logger.getLogger(AbstractCSSProperties.class.getName());
	
	private static final Map<String, SubPropertySetter> SUB_SETTERS = new HashMap<String, SubPropertySetter>();

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
		subSetters.put(BORDER_STYLE, new FourCornersSetter(BORDER_STYLE, "border-", "-style"));
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

	public AbstractCSSProperties(CSSPropertiesContext context) {
		this.context = context;
	}

	public void addStyleDeclaration(CSSStyleDeclarationImpl styleDeclaration) {
		synchronized (this) {
			List<CSSStyleDeclarationImpl> sd = this.styleDeclarations;
			if (sd == null) {
				sd = new LinkedList<CSSStyleDeclarationImpl>();
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

	public String getOverlayColor() {
		return this.overlayColor;
	}

	public void setOverlayColor(String value) {
		this.overlayColor = value;
		this.context.informLookInvalid();
	}

	public String getFloat() {
		return this.getPropertyValueLC(FLOAT);
	}

	public void setFloat(String value) {
		this.setPropertyValueLC(FLOAT, value);
	}

	@Override
	public String getAzimuth() {
		return this.getPropertyValueLC(AZIMUTH);
	}

	@Override
	public String getBackground() {
		return this.getPropertyValueLC(BACKGROUND);
	}

	@Override
	public String getBackgroundAttachment() {
		return this.getPropertyValueLC(BACKGROUND_ATTACHMENT);
	}

	@Override
	public String getBackgroundColor() {
		return this.getPropertyValueLC(BACKGROUND_COLOR);
	}

	@Override
	public String getBackgroundImage() {
		return this.getPropertyValueLC(BACKGROUND_IMAGE);
	}

	@Override
	public String getBackgroundPosition() {
		return this.getPropertyValueLC(BACKGROUND_POSITION);
	}

	@Override
	public String getBackgroundRepeat() {
		return this.getPropertyValueLC(BACKGROUND_REPEAT);
	}

	@Override
	public String getBorder() {
		return this.getPropertyValueLC(BORDER);
	}

	@Override
	public String getBorderBottom() {
		return this.getPropertyValueLC(BORDER_BOTTOM);
	}

	@Override
	public String getBorderBottomColor() {
		return this.getPropertyValueLC(BORDER_BOTTOM_COLOR);
	}
	
	@Override
	public String getBoxSizing() {
		return this.getPropertyValueLC(BOX_SIZING);
	}

	@Override
	public String getBorderBottomStyle() {
		return this.getPropertyValueLC(BORDER_BOTTOM_STYLE);
	}

	@Override
	public String getBorderBottomWidth() {
		return this.getPropertyValueLC(BORDER_BOTTOM_WIDTH);
	}

	@Override
	public String getBorderCollapse() {
		return this.getPropertyValueLC(BORDER_COLLAPSE);
	}

	@Override
	public String getBorderColor() {
		return this.getPropertyValueLC(BORDER_COLOR);
	}

	@Override
	public String getBorderLeft() {
		return this.getPropertyValueLC(BORDER_LEFT);
	}

	@Override
	public String getBorderLeftColor() {
		return this.getPropertyValueLC(BORDER_LEFT_COLOR);
	}

	@Override
	public String getBorderLeftStyle() {
		return this.getPropertyValueLC(BORDER_LEFT_STYLE);
	}

	@Override
	public String getBorderLeftWidth() {
		return this.getPropertyValueLC(BORDER_LEFT_WIDTH);
	}

	@Override
	public String getBorderRight() {
		return this.getPropertyValueLC(BORDER_RIGHT);
	}

	@Override
	public String getBorderRightColor() {
		return this.getPropertyValueLC(BORDER_RIGHT_COLOR);
	}

	@Override
	public String getBorderRightStyle() {
		return this.getPropertyValueLC(BORDER_RIGHT_STYLE);
	}

	@Override
	public String getBorderRightWidth() {
		return this.getPropertyValueLC(BORDER_RIGHT_WIDTH);
	}

	@Override
	public String getBorderSpacing() {
		return this.getPropertyValueLC(BORDER_SPACING);
	}

	@Override
	public String getBorderStyle() {
		return this.getPropertyValueLC(BORDER_STYLE);
	}

	@Override
	public String getBorderTop() {
		return this.getPropertyValueLC(BORDER_TOP);
	}

	@Override
	public String getBorderTopColor() {
		return this.getPropertyValueLC(BORDER_TOP_COLOR);
	}

	@Override
	public String getBorderTopStyle() {
		return this.getPropertyValueLC(BORDER_TOP_STYLE);
	}

	@Override
	public String getBorderTopWidth() {
		return this.getPropertyValueLC(BORDER_TOP_WIDTH);
	}

	@Override
	public String getBorderWidth() {
		return this.getPropertyValueLC(BORDER_WIDTH);
	}

	@Override
	public String getBottom() {
		return this.getPropertyValueLC(BOTTOM);
	}

	@Override
	public String getCaptionSide() {
		return this.getPropertyValueLC(CAPTION_SIDE);
	}

	@Override
	public String getClear() {
		return this.getPropertyValueLC(CLEAR);
	}

	@Override
	public String getClip() {
		return this.getPropertyValueLC(CLIP);
	}
	
	@Override
	public String getClipPath() {
		return this.getPropertyValueLC(CLIP_PATH);
	}
	
	@Override
	public String getClipRule() {
		return this.getPropertyValueLC(CLIP_RULE);
	}

	@Override
	public String getColor() {
		return this.getPropertyValueLC(COLOR);
	}

	@Override
	public String getContent() {
		return this.getPropertyValueLC(CONTENT);
	}

	@Override
	public String getCounterIncrement() {
		return this.getPropertyValueLC(COUNTER_INCREMENT);
	}

	@Override
	public String getCounterReset() {
		return this.getPropertyValueLC(COUNTER_RESET);
	}

	@Override
	public String getCssFloat() {
		return this.getPropertyValueLC(CSS_FLOAT);
	}

	@Override
	public String getCue() {
		return this.getPropertyValueLC(CUE);
	}

	@Override
	public String getCueAfter() {
		return this.getPropertyValueLC(CUE_AFTER);
	}

	@Override
	public String getCueBefore() {
		return this.getPropertyValueLC(CUE_BEFORE);
	}

	@Override
	public String getCursor() {
		return this.getPropertyValueLC(CURSOR);
	}

	@Override
	public String getDirection() {
		return this.getPropertyValueLC(DIRECTION);
	}

	@Override
	public String getDisplay() {

		return this.getPropertyValueLC(DISPLAY);
	}

	@Override
	public String getElevation() {
		return this.getPropertyValueLC(ELEVATION);
	}

	@Override
	public String getEmptyCells() {
		return this.getPropertyValueLC(EMPTY_CELLS);
	}

	@Override
	public String getFont() {
		return this.getPropertyValueLC(FONT);
	}

	@Override
	public String getFontFamily() {
		return this.getPropertyValueLC(FONT_FAMILY);
	}

	@Override
	public String getFontSize() {
		return this.getPropertyValueLC(FONT_SIZE);
	}

	@Override
	public String getFontSizeAdjust() {
		return this.getPropertyValueLC(FONT_SIZE_ADJUST);
	}

	@Override
	public String getFontStretch() {
		return this.getPropertyValueLC(FONT_STRETCH);
	}

	@Override
	public String getFontStyle() {
		return this.getPropertyValueLC(FONT_STYLE);
	}

	@Override
	public String getFontVariant() {
		return this.getPropertyValueLC(FONT_VARIANT);
	}

	@Override
	public String getFontWeight() {
		return this.getPropertyValueLC(FONT_WEIGHT);
	}

	@Override
	public String getHeight() {
		return this.getPropertyValueLC(HEIGHT);
	}

	@Override
	public String getLeft() {
		return this.getPropertyValueLC(LEFT);
	}

	@Override
	public String getLetterSpacing() {
		return this.getPropertyValueLC(LETTER_SPACING);
	}

	@Override
	public String getLineHeight() {
		return this.getPropertyValueLC(LINE_HEIGHT);
	}

	@Override
	public String getListStyle() {
		return this.getPropertyValueLC(LIST_STYLE);
	}

	@Override
	public String getListStyleImage() {
		return this.getPropertyValueLC(LIST_STYLE_IMAGE);
	}

	@Override
	public String getListStylePosition() {
		return this.getPropertyValueLC(LIST_STYLE_POSITION);
	}

	@Override
	public String getListStyleType() {
		return this.getPropertyValueLC(LIST_STYLE_TYPE);
	}

	@Override
	public String getMargin() {
		return this.getPropertyValueLC(MARGIN);
	}

	@Override
	public String getMarginBottom() {
		return this.getPropertyValueLC(MARGIN_BOTTOM);
	}

	@Override
	public String getMarginLeft() {
		return this.getPropertyValueLC(MARGIN_LEFT);
	}

	@Override
	public String getMarginRight() {
		return this.getPropertyValueLC(MARGIN_RIGHT);
	}

	@Override
	public String getMarginTop() {
		return this.getPropertyValueLC(MARGIN_TOP);
	}

	@Override
	public String getMarkerOffset() {
		return this.getPropertyValueLC(MARKER_OFFSET);
	}

	@Override
	public String getMarks() {
		return this.getPropertyValueLC(MARKS);
	}

	@Override
	public String getMaxHeight() {
		return this.getPropertyValueLC(MAX_HEIGHT);
	}

	@Override
	public String getMaxWidth() {
		return this.getPropertyValueLC(MAX_WIDTH);
	}

	@Override
	public String getMinHeight() {
		return this.getPropertyValueLC(MIN_HEIGHT);
	}

	@Override
	public String getMinWidth() {
		return this.getPropertyValueLC(MIN_WIDTH);
	}

	@Override
	public String getOrphans() {
		return this.getPropertyValueLC(ORPHANS);
	}

	@Override
	public String getOutline() {
		return this.getPropertyValueLC(OUTLINE);
	}

	@Override
	public String getOutlineColor() {
		return this.getPropertyValueLC(OUTLINE_COLOR);
	}

	@Override
	public String getOutlineStyle() {
		return this.getPropertyValueLC(OUTLINE_STYLE);
	}

	@Override
	public String getOutlineWidth() {
		return this.getPropertyValueLC(OUTLINE_WIDTH);
	}

	@Override
	public String getOverflow() {
		return this.getPropertyValueLC(OVERFLOW);
	}

	@Override
	public String getPadding() {
		return this.getPropertyValueLC(PADDING);
	}

	@Override
	public String getPaddingBottom() {
		return this.getPropertyValueLC(PADDING_BOTTOM);
	}


	@Override
	public String getPaddingLeft() {
		return this.getPropertyValueLC(PADDING_LEFT);
	}

	@Override
	public String getPaddingRight() {
		return this.getPropertyValueLC(PADDING_RIGHT);
	}

	@Override
	public String getPaddingTop() {
		return this.getPropertyValueLC(PADDING_TOP);
	}

	@Override
	public String getPage() {
		return this.getPropertyValueLC(PAGE);
	}

	@Override
	public String getPageBreakAfter() {
		return this.getPropertyValueLC(PAGE_BREAK_AFTER);
	}

	@Override
	public String getPageBreakBefore() {
		return this.getPropertyValueLC(PAGE_BREAK_BEFORE);
	}

	@Override
	public String getPageBreakInside() {
		return this.getPropertyValueLC(PAGE_BREAK_INSIDE);
	}

	@Override
	public String getPause() {
		return this.getPropertyValueLC(PAUSE);
	}

	@Override
	public String getPauseAfter() {
		return this.getPropertyValueLC(PAUSE_AFTER);
	}

	@Override
	public String getPauseBefore() {
		return this.getPropertyValueLC(PAUSE_BEFORE);
	}

	@Override
	public String getPitch() {
		return this.getPropertyValueLC(PITCH);
	}

	@Override
	public String getPitchRange() {
		return this.getPropertyValueLC(PITCH_RANGE);
	}

	@Override
	public String getPlayDuring() {
		return this.getPropertyValueLC(PLAY_DURING);
	}

	@Override
	public String getPosition() {
		return this.getPropertyValueLC(POSITION);
	}

	@Override
	public String getQuotes() {
		return this.getPropertyValueLC(QUOTES);
	}

	@Override
	public String getRichness() {
		return this.getPropertyValueLC(RICHNESS);
	}

	@Override
	public String getRight() {
		return this.getPropertyValueLC(RIGHT);
	}

	@Override
	public String getSize() {
		return this.getPropertyValueLC(SIZE);
	}

	@Override
	public String getSpeak() {
		return this.getPropertyValueLC(SPEAK);
	}

	@Override
	public String getSpeakHeader() {
		return this.getPropertyValueLC(SPEAK_HEADER);
	}

	@Override
	public String getSpeakNumeral() {
		return this.getPropertyValueLC(SPEAK_NUMERAL);
	}

	@Override
	public String getSpeakPunctuation() {
		return this.getPropertyValueLC(SPEAK_PUNCTUATION);
	}

	@Override
	public String getSpeechRate() {
		return this.getPropertyValueLC(SPEECH_RATE);
	}

	@Override
	public String getStress() {
		return this.getPropertyValueLC(STRESS);
	}

	@Override
	public String getTableLayout() {
		return this.getPropertyValueLC(TABLE_LAYOUT);
	}

	@Override
	public String getTextAlign() {
		return this.getPropertyValueLC(TEXT_ALIGN);
	}


	@Override
	public String getTextDecoration() {
		return this.getPropertyValueLC(TEXT_DECORATION);
	}

	@Override
	public String getTextIndent() {
		return this.getPropertyValueLC(TEXT_INDENT);
	}

	@Override
	public String getTextShadow() {
		return this.getPropertyValueLC(TEXT_SHADOW);
	}

	@Override
	public String getTextTransform() {
		return this.getPropertyValueLC(TEXT_TRANSFORM);
	}

	@Override
	public String getTop() {
		return this.getPropertyValueLC(TOP);
	}

	@Override
	public String getUnicodeBidi() {
		return this.getPropertyValueLC(UNICODE_BIDI);
	}

	@Override
	public String getVerticalAlign() {
		return this.getPropertyValueLC(VERTICAL_ALIGN);
	}

	@Override
	public String getVisibility() {
		return this.getPropertyValueLC(VISIBILITY);
	}

	@Override
	public String getVoiceFamily() {
		return this.getPropertyValueLC(VOICE_FAMILY);
	}

	@Override
	public String getVolume() {
		return this.getPropertyValueLC(VOLUME);
	}

	@Override
	public String getWhiteSpace() {
		return this.getPropertyValueLC(WHITE_SPACE);
	}

	@Override
	public String getWidows() {
		return this.getPropertyValueLC(WIDOWS);
	}

	@Override
	public String getWidth() {
		return this.getPropertyValueLC(WIDTH);
	}

	@Override
	public String getWordSpacing() {
		return this.getPropertyValueLC(WORD_SPACING);
	}

	@Override
	public String getZIndex() {
		return this.getPropertyValueLC(Z_INDEX);
	}

	@Override
	public void setAzimuth(String azimuth) throws DOMException {
		this.setPropertyValueLC(AZIMUTH, azimuth);
	}

	@Override
	public void setBackground(String background) throws DOMException {
		new BackgroundSetter().changeValue(this, background, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundAttachment(String backgroundAttachment) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_ATTACHMENT, backgroundAttachment);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundColor(String backgroundColor) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_COLOR, backgroundColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundPosition(String backgroundPosition) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_POSITION, backgroundPosition);
		this.context.informLookInvalid();
	}
	
	@Override
	public void setBackgroundImage(String backgroundImage) throws DOMException {
		checkSetProperty();
		new BackgroundImageSetter().changeValue(this, backgroundImage, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundRepeat(String backgroundRepeat) throws DOMException {
		this.setPropertyValueLC(BACKGROUND_REPEAT, backgroundRepeat);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorder(String border) throws DOMException {
		new BorderSetter1().changeValue(this, border, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderBottom(String borderBottom) throws DOMException {
		new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottom, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderBottomColor(String borderBottomColor) throws DOMException {
		this.setPropertyValueLC(BORDER_BOTTOM_COLOR, borderBottomColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderBottomStyle(String borderBottomStyle) throws DOMException {
		new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottomStyle, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderBottomWidth(String borderBottomWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_BOTTOM_WIDTH, borderBottomWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderCollapse(String borderCollapse) throws DOMException {
		this.setPropertyValueLC(BORDER_COLLAPSE, borderCollapse);
		this.context.informInvalid();
	}

	@Override
	public void setBorderColor(String borderColor) throws DOMException {
		new FourCornersSetter(BORDER_COLOR, "border-", "-color").changeValue(this, borderColor, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderLeft(String borderLeft) throws DOMException {
		new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeft, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderLeftColor(String borderLeftColor) throws DOMException {
		this.setPropertyValueLC(BORDER_LEFT_COLOR, borderLeftColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderLeftStyle(String borderLeftStyle) throws DOMException {
		new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeftStyle, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderLeftWidth(String borderLeftWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_LEFT_WIDTH, borderLeftWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderRight(String borderRight) throws DOMException {
		new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRight, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderRightColor(String borderRightColor) throws DOMException {
		this.setPropertyValueLC(BORDER_RIGHT_COLOR, borderRightColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderRightStyle(String borderRightStyle) throws DOMException {
		new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRightStyle, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderRightWidth(String borderRightWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_RIGHT_WIDTH, borderRightWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderSpacing(String borderSpacing) throws DOMException {
		this.setPropertyValueLC(BORDER_SPACING, borderSpacing);
		this.context.informInvalid();
	}

	@Override
	public void setBorderStyle(String borderStyle) throws DOMException {
		new FourCornersSetter(BORDER_STYLE, "border-", "-style").changeValue(this, borderStyle, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderTop(String borderTop) throws DOMException {
		new BorderSetter2(BORDER_TOP).changeValue(this, borderTop, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderTopColor(String borderTopColor) throws DOMException {
		this.setPropertyValueLC(BORDER_TOP_COLOR, borderTopColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderTopStyle(String borderTopStyle) throws DOMException {
		new BorderSetter2(BORDER_TOP).changeValue(this, borderTopStyle, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderTopWidth(String borderTopWidth) throws DOMException {
		this.setPropertyValueLC(BORDER_TOP_WIDTH, borderTopWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderWidth(String borderWidth) throws DOMException {
		new FourCornersSetter(BORDER_WIDTH, "border-", "-width").changeValue(this, borderWidth, null);
		this.context.informInvalid();
	}

	@Override
	public void setBottom(String bottom) throws DOMException {
		this.setPropertyValueLC(BOTTOM, bottom);
		this.context.informPositionInvalid();
	}

	@Override
	public void setCaptionSide(String captionSide) throws DOMException {
		this.setPropertyValueLC(CAPTION_SIDE, captionSide);
	}

	@Override
	public void setClear(String clear) throws DOMException {
		this.setPropertyValueLC(CLEAR, clear);
		this.context.informInvalid();
	}

	@Override
	public void setClip(String clip) throws DOMException {
		this.setPropertyValueLC(CLIP, clip);
	}
	
	@Override
	public void setClipPath(String clip) {
		this.setPropertyValueLC(CLIP_PATH,clip);
	}
	
	@Override
	public void setClipRule(String clip) {
		this.setPropertyValueLC(CLIP_RULE,clip);
	}

	@Override
	public void setColor(String color) throws DOMException {
		this.setPropertyValueLC(COLOR, color);
		this.context.informLookInvalid();
	}

	@Override
	public void setContent(String content) throws DOMException {
		this.setPropertyValueLC(CONTENT, content);
		this.context.informInvalid();
	}

	@Override
	public void setCounterIncrement(String counterIncrement) throws DOMException {
		this.setPropertyValueLC(COUNTER_INCREMENT, counterIncrement);
		this.context.informLookInvalid();
	}

	@Override
	public void setCounterReset(String counterReset) throws DOMException {
		this.setPropertyValueLC(COUNTER_RESET, counterReset);
		this.context.informLookInvalid();
	}

	@Override
	public void setCssFloat(String cssFloat) throws DOMException {
		this.setPropertyValueLC(CSS_FLOAT, cssFloat);
		this.context.informInvalid();
	}

	@Override
	public void setCue(String cue) throws DOMException {
		this.setPropertyValueLC(CUE, cue);
	}

	@Override
	public void setCueAfter(String cueAfter) throws DOMException {
		this.setPropertyValueLC(CUE_AFTER, cueAfter);
	}

	@Override
	public void setCueBefore(String cueBefore) throws DOMException {
		this.setPropertyValueLC(CUE_BEFORE, cueBefore);
	}

	@Override
	public void setCursor(String cursor) throws DOMException {
		this.setPropertyValueLC(CURSOR, cursor);
		this.context.informLookInvalid();
	}

	@Override
	public void setDirection(String direction) throws DOMException {
		this.setPropertyValueLC(DIRECTION, direction);
		this.context.informInvalid();
	}

	@Override
	public void setDisplay(String display) throws DOMException {
		this.setPropertyValueLC(DISPLAY, display);
		this.context.informInvalid();
	}

	@Override
	public void setElevation(String elevation) throws DOMException {
		this.setPropertyValueLC(ELEVATION, elevation);
		this.context.informInvalid();
	}

	@Override
	public void setEmptyCells(String emptyCells) throws DOMException {
		this.setPropertyValueLC(EMPTY_CELLS, emptyCells);
	}

	@Override
	public void setFont(String font) throws DOMException {
		new FontSetter().changeValue(this, font, null);
		this.context.informInvalid();
	}

	@Override
	public void setFontFamily(String fontFamily) throws DOMException {
		this.setPropertyValueLC(FONT_FAMILY, fontFamily);
		this.context.informInvalid();
	}

	@Override
	public void setFontSize(String fontSize) throws DOMException {
		this.setPropertyValueLC(FONT_SIZE, fontSize);
		this.context.informInvalid();
	}


	@Override
	public void setFontSizeAdjust(String fontSizeAdjust) throws DOMException {
		this.setPropertyValueLC(FONT_SIZE_ADJUST, fontSizeAdjust);
		this.context.informInvalid();
	}

	@Override
	public void setFontStretch(String fontStretch) throws DOMException {
		this.setPropertyValueLC(FONT_STRETCH, fontStretch);
		this.context.informInvalid();
	}

	@Override
	public void setFontStyle(String fontStyle) throws DOMException {
		this.setPropertyValueLC(FONT_STYLE, fontStyle);
		this.context.informInvalid();
	}

	@Override
	public void setFontVariant(String fontVariant) throws DOMException {
		this.setPropertyValueLC(FONT_VARIANT, fontVariant);
		this.context.informInvalid();
	}

	@Override
	public void setFontWeight(String fontWeight) throws DOMException {
		this.setPropertyValueLC(FONT_WEIGHT, fontWeight);
		this.context.informInvalid();
	}

	@Override
	public void setHeight(String height) throws DOMException {
		this.setPropertyValueLC(HEIGHT, height);
		this.context.informSizeInvalid();
	}

	@Override
	public void setLeft(String left) throws DOMException {
		this.setPropertyValueLC(LEFT, left);
		this.context.informPositionInvalid();
	}

	@Override
	public void setLetterSpacing(String letterSpacing) throws DOMException {
		this.setPropertyValueLC(LETTER_SPACING, letterSpacing);
		this.context.informInvalid();
	}

	@Override
	public void setLineHeight(String lineHeight) throws DOMException {
		this.setPropertyValueLC(LINE_HEIGHT, lineHeight);
		this.context.informInvalid();
	}

	@Override
	public void setListStyle(String listStyle) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE, listStyle);
		this.context.informInvalid();
	}

	@Override
	public void setListStyleImage(String listStyleImage) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_IMAGE, listStyleImage);
		this.context.informLookInvalid();
	}

	@Override
	public void setListStylePosition(String listStylePosition) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_POSITION, listStylePosition);
		this.context.informInvalid();
	}

	@Override
	public void setListStyleType(String listStyleType) throws DOMException {
		this.setPropertyValueLC(LIST_STYLE_TYPE, listStyleType);
		this.context.informLookInvalid();
	}

	@Override
	public void setMargin(String margin) throws DOMException {
		new FourCornersSetter(MARGIN, "margin-", "").changeValue(this, margin, null);
		this.context.informInvalid();
	}

	@Override
	public void setMarginBottom(String marginBottom) throws DOMException {
		this.setPropertyValueLC(MARGIN_BOTTOM, marginBottom);
		this.context.informInvalid();
	}

	@Override
	public void setMarginLeft(String marginLeft) throws DOMException {
		this.setPropertyValueLC(MARGIN_LEFT, marginLeft);
		this.context.informInvalid();
	}

	@Override
	public void setMarginRight(String marginRight) throws DOMException {
		this.setPropertyValueLC(MARGIN_RIGHT, marginRight);
		this.context.informInvalid();
	}

	@Override
	public void setMarginTop(String marginTop) throws DOMException {
		this.setPropertyValueLC(MARGIN_TOP, marginTop);
		this.context.informInvalid();
	}

	@Override
	public void setMarkerOffset(String markerOffset) throws DOMException {
		this.setPropertyValueLC(MARKER_OFFSET, markerOffset);
	}

	@Override
	public void setMarks(String marks) throws DOMException {
		this.setPropertyValueLC(MARKS, marks);
	}

	@Override
	public void setMaxHeight(String maxHeight) throws DOMException {
		this.setPropertyValueLC(MAX_HEIGHT, maxHeight);
		this.context.informSizeInvalid();
	}

	@Override
	public void setMaxWidth(String maxWidth) throws DOMException {
		this.setPropertyValueLC(MAX_WIDTH, maxWidth);
		this.context.informSizeInvalid();
	}

	@Override
	public void setMinHeight(String minHeight) throws DOMException {
		this.setPropertyValueLC(MIN_HEIGHT, minHeight);
		this.context.informSizeInvalid();
	}

	@Override
	public void setMinWidth(String minWidth) throws DOMException {
		this.setPropertyValueLC(MIN_WIDTH, minWidth);
		this.context.informSizeInvalid();
	}

	@Override
	public void setOrphans(String orphans) throws DOMException {
		this.setPropertyValueLC(ORPHANS, orphans);
	}

	@Override
	public void setOutline(String outline) throws DOMException {
		this.setPropertyValueLC(OUTLINE, outline);
		this.context.informInvalid();
	}

	@Override
	public void setOutlineColor(String outlineColor) throws DOMException {
		this.setPropertyValueLC(OUTLINE_COLOR, outlineColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setOutlineStyle(String outlineStyle) throws DOMException {
		this.setPropertyValueLC(OUTLINE_STYLE, outlineStyle);
		this.context.informLookInvalid();
	}

	@Override
	public void setOutlineWidth(String outlineWidth) throws DOMException {
		this.setPropertyValueLC(OUTLINE_WIDTH, outlineWidth);
		this.context.informInvalid();
	}

	@Override
	public void setOverflow(String overflow) throws DOMException {
		this.setPropertyValueLC(OVERFLOW, overflow);
		this.context.informInvalid();
	}

	@Override
	public void setPadding(String padding) throws DOMException {
		new FourCornersSetter(PADDING, "padding-", "").changeValue(this, padding, null);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingBottom(String paddingBottom) throws DOMException {
		this.setPropertyValueLC(PADDING_BOTTOM, paddingBottom);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingLeft(String paddingLeft) throws DOMException {
		this.setPropertyValueLC(PADDING_LEFT, paddingLeft);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingRight(String paddingRight) throws DOMException {
		this.setPropertyValueLC(PADDING_RIGHT, paddingRight);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingTop(String paddingTop) throws DOMException {
		this.setPropertyValueLC(PADDING_TOP, paddingTop);
		this.context.informInvalid();
	}

	@Override
	public void setPage(String page) throws DOMException {
		this.setPropertyValueLC(PAGE, page);
	}

	@Override
	public void setPageBreakAfter(String pageBreakAfter) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_AFTER, pageBreakAfter);
		this.context.informInvalid();
	}

	@Override
	public void setPageBreakBefore(String pageBreakBefore) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_BEFORE, pageBreakBefore);
		this.context.informInvalid();
	}

	@Override
	public void setPageBreakInside(String pageBreakInside) throws DOMException {
		this.setPropertyValueLC(PAGE_BREAK_INSIDE, pageBreakInside);
		this.context.informInvalid();
	}

	@Override
	public void setPause(String pause) throws DOMException {
		this.setPropertyValueLC(PAUSE, pause);
	}


	@Override
	public void setPauseAfter(String pauseAfter) throws DOMException {
		this.setPropertyValueLC(PAUSE_AFTER, pauseAfter);
	}

	@Override
	public void setPauseBefore(String pauseBefore) throws DOMException {
		this.setPropertyValueLC(PAUSE_BEFORE, pauseBefore);
	}

	@Override
	public void setPitch(String pitch) throws DOMException {
		this.setPropertyValueLC(PITCH, pitch);
	}

	@Override
	public void setPitchRange(String pitchRange) throws DOMException {
		this.setPropertyValueLC(PITCH_RANGE, pitchRange);
	}

	@Override
	public void setPlayDuring(String playDuring) throws DOMException {
		this.setPropertyValueLC(PLAY_DURING, playDuring);
	}

	@Override
	public void setPosition(String position) throws DOMException {
		this.setPropertyValueLC(POSITION, position);
		this.context.informPositionInvalid();
	}

	@Override
	public void setQuotes(String quotes) throws DOMException {
		this.setPropertyValueLC(QUOTES, quotes);
	}

	@Override
	public void setRichness(String richness) throws DOMException {
		this.setPropertyValueLC(RICHNESS, richness);
	}

	@Override
	public void setRight(String right) throws DOMException {
		this.setPropertyValueLC(RIGHT, right);
		this.context.informPositionInvalid();
	}

	@Override
	public void setSize(String size) throws DOMException {
		this.setPropertyValueLC(SIZE, size);
		this.context.informInvalid();
	}

	@Override
	public void setSpeak(String speak) throws DOMException {
		this.setPropertyValueLC(SPEAK, speak);
	}

	@Override
	public void setSpeakHeader(String speakHeader) throws DOMException {
		this.setPropertyValueLC(SPEAK_HEADER, speakHeader);
	}

	@Override
	public void setSpeakNumeral(String speakNumeral) throws DOMException {
		this.setPropertyValueLC(SPEAK_NUMERAL, speakNumeral);
	}

	@Override
	public void setSpeakPunctuation(String speakPunctuation) throws DOMException {
		this.setPropertyValueLC(SPEAK_PUNCTUATION, speakPunctuation);
	}

	@Override
	public void setSpeechRate(String speechRate) throws DOMException {
		this.setPropertyValueLC(SPEECH_RATE, speechRate);
	}

	@Override
	public void setStress(String stress) throws DOMException {
		this.setPropertyValueLC(STRESS, stress);
	}

	@Override
	public void setTableLayout(String tableLayout) throws DOMException {
		this.setPropertyValueLC(TABLE_LAYOUT, tableLayout);
		this.context.informInvalid();
	}

	@Override
	public void setTextAlign(String textAlign) throws DOMException {
		this.setPropertyValueLC(TEXT_ALIGN, textAlign);
		this.context.informLayoutInvalid();
	}

	@Override
	public void setTextDecoration(String textDecoration) throws DOMException {
		this.setPropertyValueLC(TEXT_DECORATION, textDecoration);
		this.context.informLookInvalid();
	}

	@Override
	public void setTextIndent(String textIndent) throws DOMException {
		this.setPropertyValueLC(TEXT_INDENT, textIndent);
		this.context.informLayoutInvalid();
	}

	@Override
	public void setTextShadow(String textShadow) throws DOMException {
		this.setPropertyValueLC(TEXT_SHADOW, textShadow);
		this.context.informLookInvalid();
	}

	@Override
	public void setTextTransform(String textTransform) throws DOMException {
		this.setPropertyValueLC(TEXT_TRANSFORM, textTransform);
		this.context.informInvalid();
	}

	@Override
	public void setTop(String top) throws DOMException {
		this.setPropertyValueLC(TOP, top);
		this.context.informPositionInvalid();
	}

	@Override
	public void setUnicodeBidi(String unicodeBidi) throws DOMException {
		this.setPropertyValueLC(UNICODE_BIDI, unicodeBidi);
		this.context.informInvalid();
	}

	@Override
	public void setVerticalAlign(String verticalAlign) throws DOMException {
		this.setPropertyValueLC(VERTICAL_ALIGN, verticalAlign);
		this.context.informInvalid();
	}

	@Override
	public void setVisibility(String visibility) throws DOMException {
		this.setPropertyValueLC(VISIBILITY, visibility);
		this.context.informLookInvalid();
	}

	@Override
	public void setVoiceFamily(String voiceFamily) throws DOMException {
		this.setPropertyValueLC(VOICE_FAMILY, voiceFamily);
	}

	@Override
	public void setVolume(String volume) throws DOMException {
		this.setPropertyValueLC(VOLUME, volume);
	}

	@Override
	public void setWhiteSpace(String whiteSpace) throws DOMException {
		this.setPropertyValueLC(WHITE_SPACE, whiteSpace);
		this.context.informInvalid();
	}

	@Override
	public void setWidows(String widows) throws DOMException {
		this.setPropertyValueLC(WIDOWS, widows);
	}

	@Override
	public void setWidth(String width) throws DOMException {
		this.setPropertyValueLC(WIDTH, width);
		this.context.informSizeInvalid();
	}

	@Override
	public void setWordSpacing(String wordSpacing) throws DOMException {
		this.setPropertyValueLC(WORD_SPACING, wordSpacing);
		this.context.informInvalid();
	}

	@Override
    public String getFill() {
        return this.getPropertyValueLC(FILL);
    }

    public void setFill(String value) {
        this.setPropertyValueLC(FILL, value);
    }
    
    public String getFillOpacity() {
        return this.getPropertyValueLC(FILL_OPACITY);
    }

    public void setFillOpacity(String value) {
        this.setPropertyValueLC(FILL_OPACITY, value);
        this.context.informInvalid();
    }
    
    public String getOpacity() {
        return this.getPropertyValueLC(OPACITY);
    }

    public void setOpacity(String value) {
        this.setPropertyValueLC(OPACITY, value);
        this.context.informInvalid();
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
    
    public String getStrokeLineJoin() {
        return this.getPropertyValueLC(STROKE_LINE_JOINP);
    }

    public void setStrokeLineJoin(String value) {
        this.setPropertyValueLC(STROKE_LINE_JOINP, value);
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
    
    public String getTransform() {
        return this.getPropertyValueLC(TRANSFORM);
    }

    public void setTransform(String value) {
        this.setPropertyValueLC(TRANSFORM, value);
        this.context.informInvalid();
    }

	@Override
	public void setZIndex(String zIndex) throws DOMException {
		this.setPropertyValueLC(Z_INDEX, zIndex);
		this.context.informPositionInvalid();
	}
	
	public final String getPropertyValue(String name) {
		return getPropertyValueLC(name.toLowerCase());
	}

	private final String getPropertyValueLC(String lowerCaseName) {
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
				final PropertyCSS p = (PropertyCSS) vm.get(lowerCaseName);
				return p == null ? null : p.value;
			}
		}
		return null;
	}
	
	protected void setPropertyValueLC(String lowerCaseName, String value) {
		Map<String, PropertyCSS> vm = this.valueMap;
		synchronized (this) {
			if (vm == null) {
				vm = new HashMap<String, PropertyCSS>(1);
				this.valueMap = vm;
			}
			vm.put(lowerCaseName, new PropertyCSS(value, true));
		}
	}
	
	public final void setPropertyValueLCAlt(String lowerCaseName, String value, boolean important) {
		Map<String, PropertyCSS> vm = this.valueMap;
		synchronized (this) {
			if (vm == null) {
				vm = new HashMap<String, PropertyCSS>(1);
				this.valueMap = vm;
			} else {
				if (!important) {
					final PropertyCSS oldProperty = (PropertyCSS) vm.get(lowerCaseName);
					if (oldProperty != null && oldProperty.important) {
						// Ignore setting
						return;
					}
				}
			}
			vm.put(lowerCaseName, new PropertyCSS(value, important));
		}
	}
	
	protected final void setPropertyValueProcessed(String lowerCaseName, String value, CSSStyleDeclarationImpl declaration,boolean important) {
		final SubPropertySetter setter = (SubPropertySetter) SUB_SETTERS.get(lowerCaseName);
		if (setter != null) {
			setter.changeValue(this, value, declaration, important);
		} else {
			setPropertyValueLCAlt(lowerCaseName, value, important);
		}
	}
	
	public void setLocalStyleProperties(AbstractCSSProperties properties) {
		if (Objects.equals(properties, this)) {
			throw new IllegalStateException("setting same");
		}
		synchronized (this) {
			this.localStyleProperties = properties;
		}
	}

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
