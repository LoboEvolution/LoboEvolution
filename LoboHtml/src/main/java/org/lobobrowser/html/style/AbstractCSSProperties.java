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
package org.lobobrowser.html.style;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobo.common.Urls;
import org.lobo.laf.ColorFactory;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSS3Properties;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;

public abstract class AbstractCSSProperties extends AbstractScriptableDelegate implements CSS3Properties {
	private static class BackgroundImageSetter implements SubPropertySetter {
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration,
				boolean important) {
			String baseHref = null;
			String finalValue;
			if (declaration != null) {
				final CSSRule rule = declaration.getParentRule();
				if (rule != null) {
					final CSSStyleSheet sheet = rule.getParentStyleSheet();
					if (sheet instanceof CSSStyleSheetImpl) {
						final CSSStyleSheetImpl ssheet = (CSSStyleSheetImpl) sheet;
						baseHref = ssheet.getHref();
					}
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
							finalValue = "url("
									+ HtmlValues.quoteAndEscape(Urls.createURL(styleUrl, tentativeUri).toExternalForm())
									+ ")";
						} catch (final Exception mfu) {
							logger.log(Level.WARNING, "Unable to create URL for URI=[" + tentativeUri + "], with base=["
									+ baseHref + "].", mfu);
							finalValue = newValue;
						}
					}
				}
			}
			properties.setPropertyValueLCAlt(BACKGROUND_IMAGE, finalValue, important);
		}
	}

	private static class BackgroundSetter implements SubPropertySetter {
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration,
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
					if (ColorFactory.getInstance().isColor(token)) {
						color = token;
					} else if (HtmlValues.isUrl(token)) {
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
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration,
				boolean important) {
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

		public void changeValue(AbstractCSSProperties properties, String value, CSSStyleDeclaration declaration) {
			this.changeValue(properties, value, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String value, CSSStyleDeclaration declaration,
				boolean important) {
			properties.setPropertyValueLCAlt(this.name, value, important);
			if (value != null && value.length() > 0) {
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
				if (color != null) {
					properties.setPropertyValueLCAlt(name + "-color", color, important);
				}
				if (width != null) {
					properties.setPropertyValueLCAlt(name + "-width", width, important);
				}
				if (style != null) {
					properties.setPropertyValueLCAlt(name + "-style", style, important);
				}
			}
		}
	}

	private static class FontSetter implements SubPropertySetter {
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration,
				boolean important) {
			properties.setPropertyValueLCAlt(FONT, newValue, important);
			if (newValue != null && newValue.length() > 0) {
				final String fontSpecTL = newValue.toLowerCase();
				final FontInfo fontInfo = (FontInfo) HtmlValues.SYSTEM_FONTS.get(fontSpecTL);
				if (fontInfo != null) {
					if (fontInfo.fontFamily != null) {
						properties.setPropertyValueLCAlt(FONT_FAMILY, fontInfo.fontFamily, important);
					}
					if (fontInfo.fontSize != null) {
						properties.setPropertyValueLCAlt(FONT_SIZE, fontInfo.fontSize, important);
					}
					if (fontInfo.fontStyle != null) {
						properties.setPropertyValueLCAlt(FONT_STYLE, fontInfo.fontStyle, important);
					}
					if (fontInfo.fontVariant != null) {
						properties.setPropertyValueLCAlt(FONT_VARIANT, fontInfo.fontVariant, important);
					}
					if (fontInfo.fontWeight != null) {
						properties.setPropertyValueLCAlt(FONT_WEIGHT, fontInfo.fontWeight, important);
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
						final StringBuffer fontFamilyBuff = new StringBuffer();
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

		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration) {
			this.changeValue(properties, newValue, declaration, true);
		}

		@Override
		public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration,
				boolean important) {
			properties.setPropertyValueLCAlt(this.property, newValue, important);
			if (newValue != null && newValue.length() > 0) {
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

	private static class Property {
		public final boolean important;
		public final String value;

		public Property(final String value, final boolean important) {
			this.value = value;
			this.important = important;
		}
	}

	private interface SubPropertySetter {
		void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclaration declaration,
				boolean important);
	}

	public static final String AZIMUTH = "azimuth";
	public static final String BACKGROUND = "background";
	public static final String BACKGROUND_ATTACHMENT = "background-attachment";
	public static final String BACKGROUND_COLOR = "background-color";
	public static final String BACKGROUND_IMAGE = "background-image";
	public static final String BACKGROUND_POSITION = "background-position";
	public static final String BACKGROUND_REPEAT = "background-repeat";
	public static final String BORDER = "border";
	public static final String BORDER_BOTTOM = "border-bottom";
	public static final String BORDER_BOTTOM_COLOR = "border-bottom-color";
	public static final String BORDER_BOTTOM_STYLE = "border-bottom-style";
	public static final String BORDER_BOTTOM_WIDTH = "border-bottom-width";
	public static final String BORDER_COLLAPSE = "border-collapse";
	public static final String BORDER_COLOR = "border-color";
	public static final String BORDER_LEFT = "border-left";
	public static final String BORDER_LEFT_COLOR = "border-left-color";
	public static final String BORDER_LEFT_STYLE = "border-left-style";
	public static final String BORDER_LEFT_WIDTH = "border-left-width";
	public static final String BORDER_RIGHT = "border-right";
	public static final String BORDER_RIGHT_COLOR = "border-right-color";
	public static final String BORDER_RIGHT_STYLE = "border-right-style";
	public static final String BORDER_RIGHT_WIDTH = "border-right-width";
	public static final String BORDER_SPACING = "border-spacing";
	public static final String BORDER_STYLE = "border-style";
	public static final String BORDER_TOP = "border-top";
	public static final String BORDER_TOP_COLOR = "border-top-color";
	public static final String BORDER_TOP_STYLE = "border-top-style";
	public static final String BORDER_TOP_WIDTH = "border-top-width";
	public static final String BORDER_WIDTH = "border-width";
	public static final String BOTTOM = "bottom";
	public static final String BOX_SIZING = "box-sizing";
	public static final String CAPTION_SIDE = "caption-side";
	public static final String CLEAR = "clear";
	public static final String CLIP = "clip";
	public static final String COLOR = "color";
	public static final String CONTENT = "content";
	public static final String COUNTER_INCREMENT = "counter-increment";
	public static final String COUNTER_RESET = "counter-reset";
	public static final String CSS_FLOAT = "css-float";
	public static final String CUE = "cue";
	public static final String CUE_AFTER = "cue-after";
	public static final String CUE_BEFORE = "cue-before";
	public static final String CURSOR = "cursor";
	public static final String DIRECTION = "direction";
	public static final String DISPLAY = "display";
	public static final String ELEVATION = "elevation";
	public static final String EMPTY_CELLS = "empty-cells";
	public static final String FLOAT = "float";
	public static final String FONT = "font";
	public static final String FONT_FAMILY = "font-family";
	public static final String FONT_SIZE = "font-size";
	public static final String FONT_SIZE_ADJUST = "font-size-adjust";
	public static final String FONT_STRETCH = "font-stretch";
	public static final String FONT_STYLE = "font-style";
	public static final String FONT_VARIANT = "font-variant";
	public static final String FONT_WEIGHT = "font-weight";
	public static final String HEIGHT = "height";
	public static final String LEFT = "left";
	public static final String LETTER_SPACING = "letter-spacing";
	public static final String LINE_HEIGHT = "line-height";
	public static final String LIST_STYLE = "list-style";
	public static final String LIST_STYLE_IMAGE = "list-style-image";
	public static final String LIST_STYLE_POSITION = "list-style-position";
	public static final String LIST_STYLE_TYPE = "list-style-type";
	private static final Logger logger = Logger.getLogger(AbstractCSSProperties.class.getName());
	public static final String MARGIN = "margin";
	public static final String MARGIN_BOTTOM = "margin-bottom";
	public static final String MARGIN_LEFT = "margin-left";
	public static final String MARGIN_RIGHT = "margin-right";
	public static final String MARGIN_TOP = "margin-top";
	public static final String MARKER_OFFSET = "marker-offset";
	public static final String MARKS = "marks";
	public static final String MAX_HEIGHT = "max-height";
	public static final String MAX_WIDTH = "max-width";
	public static final String MIN_HEIGHT = "min-height";
	public static final String MIN_WIDTH = "min-width";
	public static final String ORPHANS = "orphans";
	public static final String OUTLINE = "outline";
	public static final String OUTLINE_COLOR = "outline-color";
	public static final String OUTLINE_STYLE = "outline-style";
	public static final String OUTLINE_WIDTH = "outline-width";
	public static final String OVERFLOW = "overflow";
	public static final String PADDING = "padding";
	public static final String PADDING_BOTTOM = "padding-bottom";
	public static final String PADDING_LEFT = "padding-left";
	public static final String PADDING_RIGHT = "padding-right";
	public static final String PADDING_TOP = "padding-top";
	public static final String PAGE = "page";
	public static final String PAGE_BREAK_AFTER = "page-break-after";
	public static final String PAGE_BREAK_BEFORE = "page-break-before";
	public static final String PAGE_BREAK_INSIDE = "page-break-inside";
	public static final String PAUSE = "pause";
	public static final String PAUSE_AFTER = "pause-after";
	public static final String PAUSE_BEFORE = "pause-before";
	public static final String PITCH = "pitch";
	public static final String PITCH_RANGE = "pitch-range";
	public static final String PLAY_DURING = "play-during";
	public static final String POSITION = "position";
	public static final String QUOTES = "quotes";
	public static final String RICHNESS = "richness";
	public static final String RIGHT = "right";
	public static final String SIZE = "size";
	public static final String SPEAK = "speak";
	public static final String SPEAK_HEADER = "speak-header";
	public static final String SPEAK_NUMERAL = "speak-numeral";
	public static final String SPEAK_PUNCTUATION = "speak-puctuation";
	public static final String SPEECH_RATE = "speech-rate";
	public static final String STRESS = "stress";
	public static final String TABLE_LAYOUT = "table-layout";
	public static final String TEXT_ALIGN = "text-align";
	public static final String TEXT_DECORATION = "text-decoration";
	public static final String TEXT_INDENT = "text-indent";
	public static final String TEXT_SHADOW = "text-shadow";
	public static final String TEXT_TRANSFORM = "text-transform";
	public static final String TOP = "top";
	public static final String UNICODE_BIDI = "unicode-bidi";

	public static final String VERTICAL_ALIGN = "vertical-align";

	public static final String VISIBILITY = "visibility";
	public static final String VOICE_FAMILY = "voice-family";
	public static final String VOLUME = "volume";
	public static final String WHITE_SPACE = "white-space";

	public static final String WIDOWS = "widows";

	public static final String WIDTH = "width";

	public static final String WORD_SPACING = "word_spacing";

	public static final String Z_INDEX = "z-index";
	
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

	private Collection styleDeclarations;

	private Map<String, Property> valueMap = null;

	public AbstractCSSProperties(CSSPropertiesContext context) {
		this.context = context;
	}

	public void addStyleDeclaration(CSSStyleDeclaration styleDeclaration) {
		synchronized (this) {
			Collection<CSSStyleDeclaration> sd = this.styleDeclarations;
			if (sd == null) {
				sd = new LinkedList();
				this.styleDeclarations = sd;
			}
			sd.add(styleDeclaration);
			final int length = styleDeclaration.getLength();
			for (int i = 0; i < length; i++) {
				final String propertyName = styleDeclaration.item(i);
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

	@Override
	public String getAzimuth() {
		return getPropertyValueLC(AZIMUTH);
	}

	@Override
	public String getBackground() {
		return getPropertyValueLC(BACKGROUND);
	}

	// ---------- Implemented properties

	@Override
	public String getBackgroundAttachment() {
		return getPropertyValueLC(BACKGROUND_ATTACHMENT);
	}

	@Override
	public String getBackgroundColor() {
		return getPropertyValueLC(BACKGROUND_COLOR);
	}

	@Override
	public String getBackgroundImage() {
		return getPropertyValueLC(BACKGROUND_IMAGE);
	}

	@Override
	public String getBackgroundPosition() {
		return getPropertyValueLC(BACKGROUND_POSITION);
	}

	@Override
	public String getBackgroundRepeat() {
		return getPropertyValueLC(BACKGROUND_REPEAT);
	}

	@Override
	public String getBorder() {
		return getPropertyValueLC(BORDER);
	}

	@Override
	public String getBorderBottom() {
		return getPropertyValueLC(BORDER_BOTTOM);
	}

	@Override
	public String getBorderBottomColor() {
		return getPropertyValueLC(BORDER_BOTTOM_COLOR);
	}

	@Override
	public String getBorderBottomStyle() {
		return getPropertyValueLC(BORDER_BOTTOM_STYLE);
	}

	@Override
	public String getBorderBottomWidth() {
		return getPropertyValueLC(BORDER_BOTTOM_WIDTH);
	}

	@Override
	public String getBorderCollapse() {
		return getPropertyValueLC(BORDER_COLLAPSE);
	}

	@Override
	public String getBorderColor() {
		return getPropertyValueLC(BORDER_COLOR);
	}

	@Override
	public String getBorderLeft() {
		return getPropertyValueLC(BORDER_LEFT);
	}

	@Override
	public String getBorderLeftColor() {
		return getPropertyValueLC(BORDER_LEFT_COLOR);
	}

	@Override
	public String getBorderLeftStyle() {
		return getPropertyValueLC(BORDER_LEFT_STYLE);
	}

	@Override
	public String getBorderLeftWidth() {
		return getPropertyValueLC(BORDER_LEFT_WIDTH);
	}

	@Override
	public String getBorderRight() {
		return getPropertyValueLC(BORDER_RIGHT);
	}

	@Override
	public String getBorderRightColor() {
		return getPropertyValueLC(BORDER_RIGHT_COLOR);
	}

	@Override
	public String getBorderRightStyle() {
		return getPropertyValueLC(BORDER_RIGHT_STYLE);
	}

	@Override
	public String getBorderRightWidth() {
		return getPropertyValueLC(BORDER_RIGHT_WIDTH);
	}

	@Override
	public String getBorderSpacing() {
		return getPropertyValueLC(BORDER_SPACING);
	}

	@Override
	public String getBorderStyle() {
		return getPropertyValueLC(BORDER_STYLE);
	}

	@Override
	public String getBorderTop() {
		return getPropertyValueLC(BORDER_TOP);
	}

	@Override
	public String getBorderTopColor() {
		return getPropertyValueLC(BORDER_TOP_COLOR);
	}

	@Override
	public String getBorderTopStyle() {
		return getPropertyValueLC(BORDER_TOP_STYLE);
	}

	@Override
	public String getBorderTopWidth() {
		return getPropertyValueLC(BORDER_TOP_WIDTH);
	}

	@Override
	public String getBorderWidth() {
		return getPropertyValueLC(BORDER_WIDTH);
	}

	@Override
	public String getBottom() {
		return getPropertyValueLC(BOTTOM);
	}

	@Override
	public String getCaptionSide() {
		return getPropertyValueLC(CAPTION_SIDE);
	}

	@Override
	public String getClear() {
		return getPropertyValueLC(CLEAR);
	}
	
	@Override
	public String getBoxSizing() {
		return getPropertyValueLC(BOX_SIZING);
	}

	@Override
	public String getClip() {
		return getPropertyValueLC(CLIP);
	}

	@Override
	public String getColor() {

		return getPropertyValueLC(COLOR);
	}

	@Override
	public String getContent() {

		return getPropertyValueLC(CONTENT);
	}

	@Override
	public String getCounterIncrement() {

		return getPropertyValueLC(COUNTER_INCREMENT);
	}

	@Override
	public String getCounterReset() {

		return getPropertyValueLC(COUNTER_RESET);
	}

	@Override
	public String getCssFloat() {

		return getPropertyValueLC(CSS_FLOAT);
	}

	@Override
	public String getCue() {

		return getPropertyValueLC(CUE);
	}

	@Override
	public String getCueAfter() {

		return getPropertyValueLC(CUE_AFTER);
	}

	@Override
	public String getCueBefore() {

		return getPropertyValueLC(CUE_BEFORE);
	}

	@Override
	public String getCursor() {

		return getPropertyValueLC(CURSOR);
	}

	@Override
	public String getDirection() {

		return getPropertyValueLC(DIRECTION);
	}

	@Override
	public String getDisplay() {

		return getPropertyValueLC(DISPLAY);
	}

	@Override
	public String getElevation() {

		return getPropertyValueLC(ELEVATION);
	}

	@Override
	public String getEmptyCells() {

		return getPropertyValueLC(EMPTY_CELLS);
	}

	public String getFloat() {
		return getPropertyValueLC(FLOAT);
	}

	@Override
	public String getFont() {

		return getPropertyValueLC(FONT);
	}

	@Override
	public String getFontFamily() {
		return getPropertyValueLC(FONT_FAMILY);
	}

	@Override
	public String getFontSize() {
		return getPropertyValueLC(FONT_SIZE);
	}

	@Override
	public String getFontSizeAdjust() {

		return getPropertyValueLC(FONT_SIZE_ADJUST);
	}

	@Override
	public String getFontStretch() {

		return getPropertyValueLC(FONT_STRETCH);
	}

	@Override
	public String getFontStyle() {

		return getPropertyValueLC(FONT_STYLE);
	}

	@Override
	public String getFontVariant() {

		return getPropertyValueLC(FONT_VARIANT);
	}

	@Override
	public String getFontWeight() {

		return getPropertyValueLC(FONT_WEIGHT);
	}

	@Override
	public String getHeight() {

		return getPropertyValueLC(HEIGHT);
	}

	@Override
	public String getLeft() {

		return getPropertyValueLC(LEFT);
	}

	@Override
	public String getLetterSpacing() {

		return getPropertyValueLC(LETTER_SPACING);
	}

	@Override
	public String getLineHeight() {

		return getPropertyValueLC(LINE_HEIGHT);
	}

	@Override
	public String getListStyle() {
		return getPropertyValueLC(LIST_STYLE);
	}

	@Override
	public String getListStyleImage() {
		return getPropertyValueLC(LIST_STYLE_IMAGE);
	}

	@Override
	public String getListStylePosition() {
		return getPropertyValueLC(LIST_STYLE_POSITION);
	}

	@Override
	public String getListStyleType() {
		return getPropertyValueLC(LIST_STYLE_TYPE);
	}

	public AbstractCSSProperties getLocalStyleProperties() {
		synchronized (this) {
			return this.localStyleProperties;
		}
	}

	@Override
	public String getMargin() {

		return getPropertyValueLC(MARGIN);
	}

	@Override
	public String getMarginBottom() {

		return getPropertyValueLC(MARGIN_BOTTOM);
	}

	@Override
	public String getMarginLeft() {

		return getPropertyValueLC(MARGIN_LEFT);
	}

	@Override
	public String getMarginRight() {

		return getPropertyValueLC(MARGIN_RIGHT);
	}

	@Override
	public String getMarginTop() {

		return getPropertyValueLC(MARGIN_TOP);
	}

	@Override
	public String getMarkerOffset() {

		return getPropertyValueLC(MARKER_OFFSET);
	}

	@Override
	public String getMarks() {

		return getPropertyValueLC(MARKS);
	}

	@Override
	public String getMaxHeight() {

		return getPropertyValueLC(MAX_HEIGHT);
	}

	@Override
	public String getMaxWidth() {

		return getPropertyValueLC(MAX_WIDTH);
	}

	@Override
	public String getMinHeight() {

		return getPropertyValueLC(MIN_HEIGHT);
	}

	@Override
	public String getMinWidth() {

		return getPropertyValueLC(MIN_WIDTH);
	}

	@Override
	public String getOrphans() {

		return getPropertyValueLC(ORPHANS);
	}

	@Override
	public String getOutline() {

		return getPropertyValueLC(OUTLINE);
	}

	@Override
	public String getOutlineColor() {

		return getPropertyValueLC(OUTLINE_COLOR);
	}

	@Override
	public String getOutlineStyle() {

		return getPropertyValueLC(OUTLINE_STYLE);
	}

	@Override
	public String getOutlineWidth() {

		return getPropertyValueLC(OUTLINE_WIDTH);
	}

	@Override
	public String getOverflow() {
		return getPropertyValueLC(OVERFLOW);
	}

	public String getOverlayColor() {
		return this.overlayColor;
	}

	@Override
	public String getPadding() {
		return getPropertyValueLC(PADDING);
	}

	@Override
	public String getPaddingBottom() {
		return getPropertyValueLC(PADDING_BOTTOM);
	}

	@Override
	public String getPaddingLeft() {
		return getPropertyValueLC(PADDING_LEFT);
	}

	@Override
	public String getPaddingRight() {

		return getPropertyValueLC(PADDING_RIGHT);
	}

	@Override
	public String getPaddingTop() {

		return getPropertyValueLC(PADDING_TOP);
	}

	@Override
	public String getPage() {

		return getPropertyValueLC(PAGE);
	}

	@Override
	public String getPageBreakAfter() {

		return getPropertyValueLC(PAGE_BREAK_AFTER);
	}

	@Override
	public String getPageBreakBefore() {

		return getPropertyValueLC(PAGE_BREAK_BEFORE);
	}

	@Override
	public String getPageBreakInside() {

		return getPropertyValueLC(PAGE_BREAK_INSIDE);
	}

	@Override
	public String getPause() {

		return getPropertyValueLC(PAUSE);
	}

	@Override
	public String getPauseAfter() {

		return getPropertyValueLC(PAUSE_AFTER);
	}

	@Override
	public String getPauseBefore() {

		return getPropertyValueLC(PAUSE_BEFORE);
	}

	@Override
	public String getPitch() {

		return getPropertyValueLC(PITCH);
	}

	@Override
	public String getPitchRange() {

		return getPropertyValueLC(PITCH_RANGE);
	}

	@Override
	public String getPlayDuring() {

		return getPropertyValueLC(PLAY_DURING);
	}

	@Override
	public String getPosition() {

		return getPropertyValueLC(POSITION);
	}

	public final String getPropertyValue(String name) {
		return getPropertyValueLC(name.toLowerCase());
	}

	private final String getPropertyValueLC(String lowerCaseName) {
		final Map<String, Property> vm = this.valueMap;
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
				final Property p = (Property) vm.get(lowerCaseName);
				return p == null ? null : p.value;
			}
		}
		return null;
	}

	@Override
	public String getQuotes() {

		return getPropertyValueLC(QUOTES);
	}

	@Override
	public String getRichness() {

		return getPropertyValueLC(RICHNESS);
	}

	@Override
	public String getRight() {

		return getPropertyValueLC(RIGHT);
	}

	@Override
	public String getSize() {

		return getPropertyValueLC(SIZE);
	}

	@Override
	public String getSpeak() {

		return getPropertyValueLC(SPEAK);
	}

	@Override
	public String getSpeakHeader() {

		return getPropertyValueLC(SPEAK_HEADER);
	}

	@Override
	public String getSpeakNumeral() {

		return getPropertyValueLC(SPEAK_NUMERAL);
	}

	@Override
	public String getSpeakPunctuation() {

		return getPropertyValueLC(SPEAK_PUNCTUATION);
	}

	@Override
	public String getSpeechRate() {

		return getPropertyValueLC(SPEECH_RATE);
	}

	@Override
	public String getStress() {

		return getPropertyValueLC(STRESS);
	}

	@Override
	public String getTableLayout() {

		return getPropertyValueLC(TABLE_LAYOUT);
	}

	@Override
	public String getTextAlign() {
		return getPropertyValueLC(TEXT_ALIGN);
	}

	@Override
	public String getTextDecoration() {

		return getPropertyValueLC(TEXT_DECORATION);
	}

	@Override
	public String getTextIndent() {

		return getPropertyValueLC(TEXT_INDENT);
	}

	@Override
	public String getTextShadow() {

		return getPropertyValueLC(TEXT_SHADOW);
	}

	@Override
	public String getTextTransform() {

		return getPropertyValueLC(TEXT_TRANSFORM);
	}

	@Override
	public String getTop() {

		return getPropertyValueLC(TOP);
	}

	@Override
	public String getUnicodeBidi() {

		return getPropertyValueLC(UNICODE_BIDI);
	}

	@Override
	public String getVerticalAlign() {

		return getPropertyValueLC(VERTICAL_ALIGN);
	}

	@Override
	public String getVisibility() {

		return getPropertyValueLC(VISIBILITY);
	}

	@Override
	public String getVoiceFamily() {

		return getPropertyValueLC(VOICE_FAMILY);
	}

	@Override
	public String getVolume() {

		return getPropertyValueLC(VOLUME);
	}

	@Override
	public String getWhiteSpace() {

		return getPropertyValueLC(WHITE_SPACE);
	}

	@Override
	public String getWidows() {

		return getPropertyValueLC(WIDOWS);
	}

	@Override
	public String getWidth() {

		return getPropertyValueLC(WIDTH);
	}

	@Override
	public String getWordSpacing() {

		return getPropertyValueLC(WORD_SPACING);
	}

	@Override
	public String getZIndex() {

		return getPropertyValueLC(Z_INDEX);
	}

	@Override
	public void setAzimuth(String azimuth) throws DOMException {
		setPropertyValueLC(AZIMUTH, azimuth);
	}

	@Override
	public void setBackground(String background) throws DOMException {
		checkSetProperty();
		new BackgroundSetter().changeValue(this, background, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundAttachment(String backgroundAttachment) throws DOMException {
		setPropertyValueLC(BACKGROUND_ATTACHMENT, backgroundAttachment);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundColor(String backgroundColor) throws DOMException {
		setPropertyValueLC(BACKGROUND_COLOR, backgroundColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundImage(String backgroundImage) throws DOMException {
		checkSetProperty();
		new BackgroundImageSetter().changeValue(this, backgroundImage, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundPosition(String backgroundPosition) throws DOMException {
		setPropertyValueLC(BACKGROUND_POSITION, backgroundPosition);
		this.context.informLookInvalid();
	}

	@Override
	public void setBackgroundRepeat(String backgroundRepeat) throws DOMException {
		setPropertyValueLC(BACKGROUND_REPEAT, backgroundRepeat);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorder(String border) throws DOMException {
		checkSetProperty();
		new BorderSetter1().changeValue(this, border, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderBottom(String borderBottom) throws DOMException {
		checkSetProperty();
		new BorderSetter2(BORDER_BOTTOM).changeValue(this, borderBottom, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderBottomColor(String borderBottomColor) throws DOMException {
		setPropertyValueLC(BORDER_BOTTOM_COLOR, borderBottomColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderBottomStyle(String borderBottomStyle) throws DOMException {
		setPropertyValueLC(BORDER_BOTTOM_STYLE, borderBottomStyle);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderBottomWidth(String borderBottomWidth) throws DOMException {
		setPropertyValueLC(BORDER_BOTTOM_WIDTH, borderBottomWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderCollapse(String borderCollapse) throws DOMException {
		setPropertyValueLC(BORDER_COLLAPSE, borderCollapse);
		this.context.informInvalid();
	}

	@Override
	public void setBorderColor(String borderColor) throws DOMException {
		checkSetProperty();
		new FourCornersSetter(BORDER_COLOR, "border-", "-color").changeValue(this, borderColor, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderLeft(String borderLeft) throws DOMException {
		checkSetProperty();
		new BorderSetter2(BORDER_LEFT).changeValue(this, borderLeft, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderLeftColor(String borderLeftColor) throws DOMException {
		setPropertyValueLC(BORDER_LEFT_COLOR, borderLeftColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderLeftStyle(String borderLeftStyle) throws DOMException {
		setPropertyValueLC(BORDER_LEFT_STYLE, borderLeftStyle);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderLeftWidth(String borderLeftWidth) throws DOMException {
		setPropertyValueLC(BORDER_LEFT_WIDTH, borderLeftWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderRight(String borderRight) throws DOMException {
		checkSetProperty();
		new BorderSetter2(BORDER_RIGHT).changeValue(this, borderRight, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderRightColor(String borderRightColor) throws DOMException {
		setPropertyValueLC(BORDER_RIGHT_COLOR, borderRightColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderRightStyle(String borderRightStyle) throws DOMException {
		setPropertyValueLC(BORDER_RIGHT_STYLE, borderRightStyle);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderRightWidth(String borderRightWidth) throws DOMException {
		setPropertyValueLC(BORDER_RIGHT_WIDTH, borderRightWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderSpacing(String borderSpacing) throws DOMException {
		setPropertyValueLC(BORDER_SPACING, borderSpacing);
		this.context.informInvalid();
	}

	@Override
	public void setBorderStyle(String borderStyle) throws DOMException {
		checkSetProperty();
		new FourCornersSetter(BORDER_STYLE, "border-", "-style").changeValue(this, borderStyle, null);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderTop(String borderTop) throws DOMException {
		checkSetProperty();
		new BorderSetter2(BORDER_TOP).changeValue(this, borderTop, null);
		this.context.informInvalid();
	}

	@Override
	public void setBorderTopColor(String borderTopColor) throws DOMException {
		setPropertyValueLC(BORDER_TOP_COLOR, borderTopColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderTopStyle(String borderTopStyle) throws DOMException {
		setPropertyValueLC(BORDER_TOP_STYLE, borderTopStyle);
		this.context.informLookInvalid();
	}

	@Override
	public void setBorderTopWidth(String borderTopWidth) throws DOMException {
		setPropertyValueLC(BORDER_TOP_WIDTH, borderTopWidth);
		this.context.informInvalid();
	}

	@Override
	public void setBorderWidth(String borderWidth) throws DOMException {
		checkSetProperty();
		new FourCornersSetter(BORDER_WIDTH, "border-", "-width").changeValue(this, borderWidth, null);
		this.context.informInvalid();
	}

	@Override
	public void setBottom(String bottom) throws DOMException {
		setPropertyValueLC(BOTTOM, bottom);
		this.context.informPositionInvalid();
	}

	@Override
	public void setCaptionSide(String captionSide) throws DOMException {
		setPropertyValueLC(CAPTION_SIDE, captionSide);
	}

	@Override
	public void setClear(String clear) throws DOMException {
		setPropertyValueLC(CLEAR, clear);
		this.context.informInvalid();
	}

	@Override
	public void setClip(String clip) throws DOMException {
		setPropertyValueLC(CLIP, clip);
	}

	@Override
	public void setColor(String color) throws DOMException {
		setPropertyValueLC(COLOR, color);
		this.context.informLookInvalid();
	}

	@Override
	public void setContent(String content) throws DOMException {
		setPropertyValueLC(CONTENT, content);
		this.context.informInvalid();
	}

	@Override
	public void setCounterIncrement(String counterIncrement) throws DOMException {
		setPropertyValueLC(COUNTER_INCREMENT, counterIncrement);
		this.context.informLookInvalid();
	}

	@Override
	public void setCounterReset(String counterReset) throws DOMException {
		setPropertyValueLC(COUNTER_RESET, counterReset);
		this.context.informLookInvalid();
	}

	@Override
	public void setCssFloat(String cssFloat) throws DOMException {
		setPropertyValueLC(CSS_FLOAT, cssFloat);
		this.context.informInvalid();
	}

	@Override
	public void setCue(String cue) throws DOMException {
		setPropertyValueLC(CUE, cue);
	}

	@Override
	public void setCueAfter(String cueAfter) throws DOMException {
		setPropertyValueLC(CUE_AFTER, cueAfter);
	}

	@Override
	public void setCueBefore(String cueBefore) throws DOMException {
		setPropertyValueLC(CUE_BEFORE, cueBefore);
	}

	@Override
	public void setCursor(String cursor) throws DOMException {
		setPropertyValueLC(CURSOR, cursor);
		this.context.informLookInvalid();
	}

	@Override
	public void setDirection(String direction) throws DOMException {
		setPropertyValueLC(DIRECTION, direction);
		this.context.informInvalid();
	}

	@Override
	public void setDisplay(String display) throws DOMException {
		setPropertyValueLC(DISPLAY, display);
		this.context.informInvalid();
	}

	@Override
	public void setElevation(String elevation) throws DOMException {
		setPropertyValueLC(ELEVATION, elevation);
		this.context.informInvalid();
	}

	@Override
	public void setEmptyCells(String emptyCells) throws DOMException {
		setPropertyValueLC(EMPTY_CELLS, emptyCells);
	}

	public void setFloat(String value) {
		setPropertyValueLC(FLOAT, value);
	}

	@Override
	public void setFont(String font) throws DOMException {
		checkSetProperty();
		new FontSetter().changeValue(this, font, null);
		this.context.informInvalid();
	}

	@Override
	public void setFontFamily(String fontFamily) throws DOMException {
		setPropertyValueLC(FONT_FAMILY, fontFamily);
		this.context.informInvalid();
	}

	@Override
	public void setFontSize(String fontSize) throws DOMException {
		setPropertyValueLC(FONT_SIZE, fontSize);
		this.context.informInvalid();
	}

	@Override
	public void setFontSizeAdjust(String fontSizeAdjust) throws DOMException {
		setPropertyValueLC(FONT_SIZE_ADJUST, fontSizeAdjust);
		this.context.informInvalid();
	}

	@Override
	public void setFontStretch(String fontStretch) throws DOMException {
		setPropertyValueLC(FONT_STRETCH, fontStretch);
		this.context.informInvalid();
	}

	@Override
	public void setFontStyle(String fontStyle) throws DOMException {
		setPropertyValueLC(FONT_STYLE, fontStyle);
		this.context.informInvalid();
	}

	@Override
	public void setFontVariant(String fontVariant) throws DOMException {
		setPropertyValueLC(FONT_VARIANT, fontVariant);
		this.context.informInvalid();
	}

	@Override
	public void setFontWeight(String fontWeight) throws DOMException {
		setPropertyValueLC(FONT_WEIGHT, fontWeight);
		this.context.informInvalid();
	}

	@Override
	public void setHeight(String height) throws DOMException {
		setPropertyValueLC(HEIGHT, height);
		this.context.informSizeInvalid();
	}

	@Override
	public void setLeft(String left) throws DOMException {
		setPropertyValueLC(LEFT, left);
		this.context.informPositionInvalid();
	}

	@Override
	public void setLetterSpacing(String letterSpacing) throws DOMException {
		setPropertyValueLC(LETTER_SPACING, letterSpacing);
		this.context.informInvalid();
	}

	@Override
	public void setLineHeight(String lineHeight) throws DOMException {
		setPropertyValueLC(LINE_HEIGHT, lineHeight);
		this.context.informInvalid();
	}

	@Override
	public void setListStyle(String listStyle) throws DOMException {
		setPropertyValueLC(LIST_STYLE, listStyle);
		this.context.informInvalid();
	}

	@Override
	public void setListStyleImage(String listStyleImage) throws DOMException {
		setPropertyValueLC(LIST_STYLE_IMAGE, listStyleImage);
		this.context.informLookInvalid();
	}

	@Override
	public void setListStylePosition(String listStylePosition) throws DOMException {
		setPropertyValueLC(LIST_STYLE_POSITION, listStylePosition);
		this.context.informInvalid();
	}

	@Override
	public void setListStyleType(String listStyleType) throws DOMException {
		setPropertyValueLC(LIST_STYLE_TYPE, listStyleType);
		this.context.informLookInvalid();
	}

	public void setLocalStyleProperties(AbstractCSSProperties properties) {
		if (properties == this) {
			throw new IllegalStateException("setting same");
		}
		synchronized (this) {
			this.localStyleProperties = properties;
		}
	}

	@Override
	public void setMargin(String margin) throws DOMException {
		checkSetProperty();
		new AbstractCSSProperties.FourCornersSetter(MARGIN, "margin-", "").changeValue(this, margin, null);
		this.context.informInvalid();
	}

	@Override
	public void setMarginBottom(String marginBottom) throws DOMException {
		setPropertyValueLC(MARGIN_BOTTOM, marginBottom);
		this.context.informInvalid();
	}

	@Override
	public void setMarginLeft(String marginLeft) throws DOMException {
		setPropertyValueLC(MARGIN_LEFT, marginLeft);
		this.context.informInvalid();
	}

	@Override
	public void setMarginRight(String marginRight) throws DOMException {
		setPropertyValueLC(MARGIN_RIGHT, marginRight);
		this.context.informInvalid();
	}

	@Override
	public void setMarginTop(String marginTop) throws DOMException {
		setPropertyValueLC(MARGIN_TOP, marginTop);
		this.context.informInvalid();
	}

	@Override
	public void setMarkerOffset(String markerOffset) throws DOMException {
		setPropertyValueLC(MARKER_OFFSET, markerOffset);
	}

	@Override
	public void setMarks(String marks) throws DOMException {
		setPropertyValueLC(MARKS, marks);
	}

	@Override
	public void setMaxHeight(String maxHeight) throws DOMException {
		setPropertyValueLC(MAX_HEIGHT, maxHeight);
		this.context.informSizeInvalid();
	}

	@Override
	public void setMaxWidth(String maxWidth) throws DOMException {
		setPropertyValueLC(MAX_WIDTH, maxWidth);
		this.context.informSizeInvalid();
	}

	@Override
	public void setMinHeight(String minHeight) throws DOMException {
		setPropertyValueLC(MIN_HEIGHT, minHeight);
		this.context.informSizeInvalid();
	}

	@Override
	public void setMinWidth(String minWidth) throws DOMException {
		setPropertyValueLC(MIN_WIDTH, minWidth);
		this.context.informSizeInvalid();
	}

	@Override
	public void setOrphans(String orphans) throws DOMException {
		setPropertyValueLC(ORPHANS, orphans);
	}

	@Override
	public void setOutline(String outline) throws DOMException {
		setPropertyValueLC(OUTLINE, outline);
		this.context.informInvalid();
	}

	@Override
	public void setOutlineColor(String outlineColor) throws DOMException {
		setPropertyValueLC(OUTLINE_COLOR, outlineColor);
		this.context.informLookInvalid();
	}

	@Override
	public void setOutlineStyle(String outlineStyle) throws DOMException {
		setPropertyValueLC(OUTLINE_STYLE, outlineStyle);
		this.context.informLookInvalid();
	}

	@Override
	public void setOutlineWidth(String outlineWidth) throws DOMException {
		setPropertyValueLC(OUTLINE_WIDTH, outlineWidth);
		this.context.informInvalid();
	}

	@Override
	public void setOverflow(String overflow) throws DOMException {
		setPropertyValueLC(OVERFLOW, overflow);
		this.context.informInvalid();
	}

	public void setOverlayColor(String value) {
		this.overlayColor = value;
		this.context.informLookInvalid();
	}

	@Override
	public void setPadding(String padding) throws DOMException {
		checkSetProperty();
		new FourCornersSetter(PADDING, "padding-", "").changeValue(this, padding, null);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingBottom(String paddingBottom) throws DOMException {
		setPropertyValueLC(PADDING_BOTTOM, paddingBottom);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingLeft(String paddingLeft) throws DOMException {
		setPropertyValueLC(PADDING_LEFT, paddingLeft);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingRight(String paddingRight) throws DOMException {
		setPropertyValueLC(PADDING_RIGHT, paddingRight);
		this.context.informInvalid();
	}

	@Override
	public void setPaddingTop(String paddingTop) throws DOMException {
		setPropertyValueLC(PADDING_TOP, paddingTop);
		this.context.informInvalid();
	}

	@Override
	public void setPage(String page) throws DOMException {
		setPropertyValueLC(PAGE, page);
	}

	@Override
	public void setPageBreakAfter(String pageBreakAfter) throws DOMException {
		setPropertyValueLC(PAGE_BREAK_AFTER, pageBreakAfter);
		this.context.informInvalid();
	}

	@Override
	public void setPageBreakBefore(String pageBreakBefore) throws DOMException {
		setPropertyValueLC(PAGE_BREAK_BEFORE, pageBreakBefore);
		this.context.informInvalid();
	}

	@Override
	public void setPageBreakInside(String pageBreakInside) throws DOMException {
		setPropertyValueLC(PAGE_BREAK_INSIDE, pageBreakInside);
		this.context.informInvalid();
	}

	@Override
	public void setPause(String pause) throws DOMException {
		setPropertyValueLC(PAUSE, pause);
	}

	@Override
	public void setPauseAfter(String pauseAfter) throws DOMException {
		setPropertyValueLC(PAUSE_AFTER, pauseAfter);
	}

	@Override
	public void setPauseBefore(String pauseBefore) throws DOMException {
		setPropertyValueLC(PAUSE_BEFORE, pauseBefore);
	}

	@Override
	public void setPitch(String pitch) throws DOMException {
		setPropertyValueLC(PITCH, pitch);
	}

	@Override
	public void setPitchRange(String pitchRange) throws DOMException {
		setPropertyValueLC(PITCH_RANGE, pitchRange);
	}

	@Override
	public void setPlayDuring(String playDuring) throws DOMException {
		setPropertyValueLC(PLAY_DURING, playDuring);
	}

	@Override
	public void setPosition(String position) throws DOMException {
		setPropertyValueLC(POSITION, position);
		this.context.informPositionInvalid();
	}

	/**
	 * Method called by property setters to set property values.
	 * 
	 * @param lowerCaseName The name of the property in lowercase.
	 * @param value         The property value.
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
	 * @param lowerCaseName The name of the property in lowercase.
	 * @param value         The property value.
	 */
	protected final void setPropertyValueLCAlt(String lowerCaseName, String value, boolean important) {
		Map<String, Property> vm = this.valueMap;
		synchronized (this) {
			if (vm == null) {
				vm = new HashMap<String, Property>(1);
				this.valueMap = vm;
			} else {
				if (!important) {
					final Property oldProperty = (Property) vm.get(lowerCaseName);
					if (oldProperty != null && oldProperty.important) {
						// Ignore setting
						return;
					}
				}
			}
			vm.put(lowerCaseName, new Property(value, important));
		}
	}

	protected final void setPropertyValueProcessed(String lowerCaseName, String value, CSSStyleDeclaration declaration,
			boolean important) {
		final SubPropertySetter setter = (SubPropertySetter) SUB_SETTERS.get(lowerCaseName);
		if (setter != null) {
			setter.changeValue(this, value, declaration, important);
		} else {
			setPropertyValueLCAlt(lowerCaseName, value, important);
		}
	}

	@Override
	public void setQuotes(String quotes) throws DOMException {
		setPropertyValueLC(QUOTES, quotes);
	}

	@Override
	public void setRichness(String richness) throws DOMException {
		setPropertyValueLC(RICHNESS, richness);
	}

	@Override
	public void setRight(String right) throws DOMException {
		setPropertyValueLC(RIGHT, right);
		this.context.informPositionInvalid();
	}

	@Override
	public void setSize(String size) throws DOMException {
		setPropertyValueLC(SIZE, size);
		this.context.informInvalid();
	}

	@Override
	public void setSpeak(String speak) throws DOMException {
		setPropertyValueLC(SPEAK, speak);
	}

	@Override
	public void setSpeakHeader(String speakHeader) throws DOMException {
		setPropertyValueLC(SPEAK_HEADER, speakHeader);
	}

	@Override
	public void setSpeakNumeral(String speakNumeral) throws DOMException {
		setPropertyValueLC(SPEAK_NUMERAL, speakNumeral);
	}

	@Override
	public void setSpeakPunctuation(String speakPunctuation) throws DOMException {
		setPropertyValueLC(SPEAK_PUNCTUATION, speakPunctuation);
	}

	@Override
	public void setSpeechRate(String speechRate) throws DOMException {
		setPropertyValueLC(SPEECH_RATE, speechRate);
	}

	@Override
	public void setStress(String stress) throws DOMException {
		setPropertyValueLC(STRESS, stress);
	}

	@Override
	public void setTableLayout(String tableLayout) throws DOMException {
		setPropertyValueLC(TABLE_LAYOUT, tableLayout);
		this.context.informInvalid();
	}

	@Override
	public void setTextAlign(String textAlign) throws DOMException {
		setPropertyValueLC(TEXT_ALIGN, textAlign);
		this.context.informLayoutInvalid();
	}

	@Override
	public void setTextDecoration(String textDecoration) throws DOMException {
		setPropertyValueLC(TEXT_DECORATION, textDecoration);
		this.context.informLookInvalid();
	}

	@Override
	public void setTextIndent(String textIndent) throws DOMException {
		setPropertyValueLC(TEXT_INDENT, textIndent);
		this.context.informLayoutInvalid();
	}

	@Override
	public void setTextShadow(String textShadow) throws DOMException {
		setPropertyValueLC(TEXT_SHADOW, textShadow);
		this.context.informLookInvalid();
	}

	@Override
	public void setTextTransform(String textTransform) throws DOMException {
		setPropertyValueLC(TEXT_TRANSFORM, textTransform);
		this.context.informInvalid();
	}

	@Override
	public void setTop(String top) throws DOMException {
		setPropertyValueLC(TOP, top);
		this.context.informPositionInvalid();
	}

	@Override
	public void setUnicodeBidi(String unicodeBidi) throws DOMException {
		setPropertyValueLC(UNICODE_BIDI, unicodeBidi);
		this.context.informInvalid();
	}

	@Override
	public void setVerticalAlign(String verticalAlign) throws DOMException {
		setPropertyValueLC(VERTICAL_ALIGN, verticalAlign);
		this.context.informInvalid();
	}

	@Override
	public void setVisibility(String visibility) throws DOMException {
		setPropertyValueLC(VISIBILITY, visibility);
		this.context.informLookInvalid();
	}

	@Override
	public void setVoiceFamily(String voiceFamily) throws DOMException {
		setPropertyValueLC(VOICE_FAMILY, voiceFamily);
	}

	@Override
	public void setVolume(String volume) throws DOMException {
		setPropertyValueLC(VOLUME, volume);
	}

	@Override
	public void setWhiteSpace(String whiteSpace) throws DOMException {
		setPropertyValueLC(WHITE_SPACE, whiteSpace);
		this.context.informInvalid();
	}

	@Override
	public void setWidows(String widows) throws DOMException {
		setPropertyValueLC(WIDOWS, widows);
	}

	@Override
	public void setWidth(String width) throws DOMException {
		setPropertyValueLC(WIDTH, width);
		this.context.informSizeInvalid();
	}

	@Override
	public void setWordSpacing(String wordSpacing) throws DOMException {
		setPropertyValueLC(WORD_SPACING, wordSpacing);
		this.context.informInvalid();
	}

	@Override
	public void setZIndex(String zIndex) throws DOMException {
		setPropertyValueLC(Z_INDEX, zIndex);
		this.context.informPositionInvalid();
	}

	@Override
	public String toString() {
		int size;
		synchronized (this) {
			final Map<String, Property> map = this.valueMap;
			size = map == null ? 0 : map.size();
		}
		return this.getClass().getSimpleName() + "[size=" + size + "]";
	}
}
