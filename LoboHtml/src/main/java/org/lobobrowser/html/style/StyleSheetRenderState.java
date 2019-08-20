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
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.lobo.common.Strings;
import org.lobo.laf.ColorFactory;
import org.lobo.laf.FontFactory;
import org.lobo.laf.FontKey;
import org.lobobrowser.html.dom.HTMLElement;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;
import org.lobobrowser.html.renderer.LineBreak;
import org.w3c.dom.css.CSS3Properties;

/**
 * @author J. H. S.
 */
public class StyleSheetRenderState implements RenderState {
	private static final FontFactory FONT_FACTORY = FontFactory.getInstance();
	private static final Font DEFAULT_FONT = FONT_FACTORY.getFont(new FontKey());
	protected static final BackgroundInfo INVALID_BACKGROUND_INFO = new BackgroundInfo();
	protected static final BorderInfo INVALID_BORDER_INFO = new BorderInfo();
	protected static final Color INVALID_COLOR = new Color(100, 0, 100);
	protected static final HtmlInsets INVALID_INSETS = new HtmlInsets();

	private int alignXPercent = -1;
	protected BorderInfo borderInfo = INVALID_BORDER_INFO;

	private Integer cachedFloat;
	private Integer cachedPosition;
	private Integer cachedVisibility;
	private Map<String, ArrayList<?>> counters = null;
	protected final HTMLDocumentImpl document;
	protected final HTMLElementImpl element;
	private Color iBackgroundColor = INVALID_COLOR;
	protected BackgroundInfo iBackgroundInfo = INVALID_BACKGROUND_INFO;
	private int iBlankWidth = -1;
	private Color iColor;

	private Integer iDisplay;

	private Font iFont;

	private FontMetrics iFontMetrics;

	private boolean iHighlight;

	private Color iOverlayColor = INVALID_COLOR;

	private Color iTextBackgroundColor = INVALID_COLOR;

	private int iTextDecoration = -1;

	private String iTextIndentText = null;

	private int iTextTransform = -1;

	protected Integer iWhiteSpace;

	Map<String, WordInfo> iWordInfoMap = null;

	protected HtmlInsets marginInsets = INVALID_INSETS;

	protected int overflowX = -1;

	protected int overflowY = -1;

	protected HtmlInsets paddingInsets = INVALID_INSETS;

	protected final RenderState prevRenderState;
	
    private Integer cachedClear = null;

	public StyleSheetRenderState(HTMLDocumentImpl document) {
		this.prevRenderState = null;
		this.element = null;
		this.document = document;
	}

	public StyleSheetRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		this.prevRenderState = prevRenderState;
		this.element = element;
		this.document = (HTMLDocumentImpl) element.getOwnerDocument();
	}

	private void applyBackgroundHorizontalPositon(BackgroundInfo binfo, String xposition) {
		if (xposition.endsWith("%")) {
			binfo.backgroundXPositionAbsolute = false;
			try {
				binfo.backgroundXPosition = (int) Double
						.parseDouble(xposition.substring(0, xposition.length() - 1).trim());
			} catch (final NumberFormatException nfe) {
				binfo.backgroundXPosition = 0;
			}
		} else if ("center".equalsIgnoreCase(xposition)) {
			binfo.backgroundXPositionAbsolute = false;
			binfo.backgroundXPosition = 50;
		} else if ("right".equalsIgnoreCase(xposition)) {
			binfo.backgroundXPositionAbsolute = false;
			binfo.backgroundXPosition = 100;
		} else if ("left".equalsIgnoreCase(xposition)) {
			binfo.backgroundXPositionAbsolute = false;
			binfo.backgroundXPosition = 0;
		} else if ("bottom".equalsIgnoreCase(xposition)) {
			// Can happen
			binfo.backgroundYPositionAbsolute = false;
			binfo.backgroundYPosition = 100;
		} else if ("top".equalsIgnoreCase(xposition)) {
			// Can happen
			binfo.backgroundYPositionAbsolute = false;
			binfo.backgroundYPosition = 0;
		} else {
			binfo.backgroundXPositionAbsolute = true;
			binfo.backgroundXPosition = HtmlValues.getPixelSize(xposition, this, 0);
		}
	}

	private void applyBackgroundPosition(BackgroundInfo binfo, String position) {
		binfo.backgroundXPositionAbsolute = false;
		binfo.backgroundYPositionAbsolute = false;
		binfo.backgroundXPosition = 50;
		binfo.backgroundYPosition = 50;
		final StringTokenizer tok = new StringTokenizer(position, " \t\r\n");
		if (tok.hasMoreTokens()) {
			final String xposition = tok.nextToken();
			applyBackgroundHorizontalPositon(binfo, xposition);
			if (tok.hasMoreTokens()) {
				final String yposition = tok.nextToken();
				applyBackgroundVerticalPosition(binfo, yposition);
			}
		}
	}

	private void applyBackgroundRepeat(BackgroundInfo binfo, String backgroundRepeatText) {
		final String brtl = backgroundRepeatText.toLowerCase();
		if ("repeat".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_REPEAT;
		} else if ("repeat-x".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_REPEAT_X;
		} else if ("repeat-y".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_REPEAT_Y;
		} else if ("no-repeat".equals(brtl)) {
			binfo.backgroundRepeat = BackgroundInfo.BR_NO_REPEAT;
		}
	}

	private void applyBackgroundVerticalPosition(BackgroundInfo binfo, String yposition) {
		if (yposition.endsWith("%")) {
			binfo.backgroundYPositionAbsolute = false;
			try {
				binfo.backgroundYPosition = (int) Double
						.parseDouble(yposition.substring(0, yposition.length() - 1).trim());
			} catch (final NumberFormatException nfe) {
				binfo.backgroundYPosition = 0;
			}
		} else if ("center".equalsIgnoreCase(yposition)) {
			binfo.backgroundYPositionAbsolute = false;
			binfo.backgroundYPosition = 50;
		} else if ("bottom".equalsIgnoreCase(yposition)) {
			binfo.backgroundYPositionAbsolute = false;
			binfo.backgroundYPosition = 100;
		} else if ("top".equalsIgnoreCase(yposition)) {
			binfo.backgroundYPositionAbsolute = false;
			binfo.backgroundYPosition = 0;
		} else if ("right".equalsIgnoreCase(yposition)) {
			// Can happen
			binfo.backgroundXPositionAbsolute = false;
			binfo.backgroundXPosition = 100;
		} else if ("left".equalsIgnoreCase(yposition)) {
			// Can happen
			binfo.backgroundXPositionAbsolute = false;
			binfo.backgroundXPosition = 0;
		} else {
			binfo.backgroundYPositionAbsolute = true;
			binfo.backgroundYPosition = HtmlValues.getPixelSize(yposition, this, 0);
		}
	}

	@Override
	public int getAlignXPercent() {
		int axp = this.alignXPercent;
		if (axp != -1) {
			return axp;
		}
		final CSS3Properties props = getCssProperties();
		String textAlign = props == null ? null : props.getTextAlign();
		if (textAlign == null || textAlign.length() == 0) {
			// Fall back to align attribute.
			final HTMLElement element = this.element;
			if (element != null) {
				textAlign = element.getAttribute("align");
				if (textAlign == null || textAlign.length() == 0) {
					final RenderState prs = this.prevRenderState;
					if (prs != null) {
						return prs.getAlignXPercent();
					}
					textAlign = null;
				}
			}
		}
		if (textAlign == null) {
			axp = 0;
		} else if ("center".equalsIgnoreCase(textAlign)) {
			axp = 50;
		} else if ("right".equalsIgnoreCase(textAlign)) {
			axp = 100;
		} else {
			// TODO: justify, <string>
			axp = 0;
		}
		this.alignXPercent = axp;
		return axp;
	}

	@Override
	public int getAlignYPercent() {
		// This is only settable in table cells.
		// TODO: Does it work with display: table-cell?
		return 0;
	}

	@Override
	public Color getBackgroundColor() {
		final Color c = this.iBackgroundColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		Color localColor;
		final BackgroundInfo binfo = getBackgroundInfo();
		localColor = binfo == null ? null : binfo.backgroundColor;
		if (localColor == null && getDisplay() == DISPLAY_INLINE) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				final Color ancestorColor = prs.getBackgroundColor();
				if (ancestorColor != null) {
					this.iBackgroundColor = ancestorColor;
					return ancestorColor;
				}
			}
		}
		this.iBackgroundColor = localColor;
		return localColor;
	}

	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.iBackgroundInfo;
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props != null) {
			final String backgroundColorText = props.getBackgroundColor();
			if (backgroundColorText != null) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				binfo.backgroundColor = ColorFactory.getInstance().getColor(backgroundColorText);
			}
			final String backgroundImageText = props.getBackgroundImage();
			if (Strings.isNotBlank(backgroundImageText)) {
				final java.net.URL backgroundImage = HtmlValues.getURIFromStyleValue(backgroundImageText);
				if (backgroundImage != null) {
					if (binfo == null) {
						binfo = new BackgroundInfo();
					}
					binfo.backgroundImage = backgroundImage;
				}
			}
			final String backgroundRepeatText = props.getBackgroundRepeat();
			if (backgroundRepeatText != null) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				applyBackgroundRepeat(binfo, backgroundRepeatText);
			}
			final String backgroundPositionText = props.getBackgroundPosition();
			if (backgroundPositionText != null) {
				if (binfo == null) {
					binfo = new BackgroundInfo();
				}
				applyBackgroundPosition(binfo, backgroundPositionText);
			}
		}
		this.iBackgroundInfo = binfo;
		return binfo;
	}

	@Override
	public int getBlankWidth() {
		int bw = this.iBlankWidth;
		if (bw == -1) {
			bw = getFontMetrics().charWidth(' ');
			this.iBlankWidth = bw;
		}
		return bw;
	}

	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props != null) {
			binfo = HtmlValues.getBorderInfo(props, this);
		} else {
			binfo = null;
		}
		this.borderInfo = binfo;
		return binfo;
	}

	@Override
	public Color getColor() {
		Color c = this.iColor;
		if (c != null) {
			return c;
		}
		final AbstractCSSProperties props = getCssProperties();
		String colorValue = props == null ? null : props.getColor();
		if (colorValue == null || "".equals(colorValue)) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				c = prs.getColor();
				this.iColor = c;
				return c;
			} else {
				colorValue = "black";
			}
		}
		c = ColorFactory.getInstance().getColor(colorValue);
		this.iColor = c;
		return c;
	}

	@Override
	public int getCount(String counter, int nesting) {
		// Expected to be called only in GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.getCount(counter, nesting);
		}
		final Map<String, ArrayList<?>> counters = this.counters;
		if (counters == null) {
			return 0;
		}
		final ArrayList<?> counterArray = (ArrayList<?>) counters.get(counter);
		if (nesting < 0 || nesting >= counterArray.size()) {
			return 0;
		}
		final Integer integer = (Integer) counterArray.get(nesting);
		return integer == null ? 0 : integer.intValue();
	}

	protected final AbstractCSSProperties getCssProperties() {
		final HTMLElementImpl element = this.element;
		return element == null ? null : element.getCurrentStyle();
	}

	protected int getDefaultDisplay() {
		return DISPLAY_INLINE;
	}

	@Override
	public int getDisplay() {
		final Integer d = this.iDisplay;
		if (d != null) {
			return d.intValue();
		}
		final CSS3Properties props = this.getCssProperties();
		final String displayText = props == null ? null : props.getDisplay();
		int displayInt;
		final String displayTextTL = Strings.isNotBlank(displayText) ? displayText.toLowerCase() : "";
		switch (displayTextTL) {
		case "block":
			displayInt = DISPLAY_BLOCK;
			break;
		case "inline":
			displayInt = DISPLAY_INLINE;
			break;
		case "none":
			displayInt = DISPLAY_NONE;
			break;
		case "list-item":
			displayInt = DISPLAY_LIST_ITEM;
			break;
		case "table-row-group":
			displayInt = DISPLAY_TABLE_ROW_GROUP;
			break;
		case "table-header-group":
			displayInt = DISPLAY_TABLE_HEADER_GROUP;
			break;
		case "table-footer-group":
			displayInt = DISPLAY_TABLE_FOOTER_GROUP;
			break;
		case "table":
			displayInt = DISPLAY_TABLE;
			break;
		case "inline-table":
			displayInt = DISPLAY_INLINE_TABLE;
			break;
		case "table-cell":
			displayInt = DISPLAY_TABLE_CELL;
			break;
		case "table-row":
			displayInt = DISPLAY_TABLE_ROW;
			break;
		case "inline-block":
			displayInt = DISPLAY_INLINE_BLOCK;
			break;
		case "table-column":
			displayInt = DISPLAY_TABLE_COLUMN;
			break;
		case "table-column-group":
			displayInt = DISPLAY_TABLE_COLUMN_GROUP;
			break;
		case "table-caption":
			displayInt = DISPLAY_TABLE_CAPTION;
			break;
		case "inherit":
			displayInt = this.getPreviousRenderState().getDisplay();
			break;
		default:
			displayInt = this.getDefaultDisplay();
			break;
		}
		this.iDisplay = new Integer(displayInt);
		return displayInt;
	}

	@Override
	public int getFloat() {
		final Integer p = this.cachedFloat;
		if (p != null) {
			return p.intValue();
		}
		final AbstractCSSProperties props = getCssProperties();
		int floatValue;
		if (props == null) {
			floatValue = FLOAT_NONE;
		} else {
			final String floatText = props.getFloat();
			if (floatText == null || floatText.length() == 0) {
				floatValue = FLOAT_NONE;
			} else {
				final String floatTextTL = floatText.toLowerCase();
				if (floatTextTL.equals("left")) {
					floatValue = FLOAT_LEFT;
				} else if (floatTextTL.equals("right")) {
					floatValue = FLOAT_RIGHT;
				} else {
					floatValue = FLOAT_NONE;
				}
			}
		}
		this.cachedFloat = new Integer(floatValue);
		return floatValue;
	}

	@Override
	public Font getFont() {

		AbstractCSSProperties style = this.getCssProperties();
		RenderState prs = this.prevRenderState;

		if (this.iFont != null) {
			return this.iFont;
		}

		if (style == null) {
			if (prs != null) {
				this.iFont = prs.getFont();
				return this.iFont;
			}
			this.iFont = DEFAULT_FONT;
			return DEFAULT_FONT;
		}

		HTMLDocumentImpl document = this.document;
		FontKey key = new FontKey();
		key.setFontFamily(FontValues.getFontFamily(style.getFontFamily(), prs));
		key.setFontStyle(FontValues.getFontStyle(style.getFontStyle()));
		key.setFontVariant(style.getFontVariant());
		key.setFontWeight(FontValues.getFontWeight(style.getFontWeight()));
		key.setFontSize(Float.valueOf(FontValues.getFontSize(style.getFontSize(), prs)));
		key.setLocales(document == null ? null : document.getLocales());
		key.setSuperscript(FontValues.getFontSuperScript(style.getVerticalAlign(), prs));
		key.setLetterSpacing(HtmlValues.getPixelSize(style.getLetterSpacing(), prs, 0));
		key.setStrikethrough(FontValues.getFontStrikeThrough(style.getTextDecoration()));
		key.setUnderline(FontValues.getFontUnderline(style.getTextDecoration()));
		Font f = FONT_FACTORY.getFont(key);
		this.iFont = f;
		return f;
	}

	@Override
	public int getFontBase() {
		final RenderState prs = this.prevRenderState;
		return prs == null ? 3 : prs.getFontBase();
	}

	@Override
	public final FontMetrics getFontMetrics() {
		FontMetrics fm = this.iFontMetrics;
		if (fm == null) {
			// TODO getFontMetrics deprecated. How to get text width?
			fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
			this.iFontMetrics = fm;
		}
		return fm;
	}

	@Override
	public HtmlInsets getMarginInsets() {
		HtmlInsets mi = this.marginInsets;
		if (mi != INVALID_INSETS) {
			return mi;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = HtmlValues.getMarginInsets(props, this);
		}
		this.marginInsets = mi;
		return mi;
	}

	@Override
	public int getOverflowX() {
		int overflow = this.overflowX;
		if (overflow != -1) {
			return overflow;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-x");
			if (overflowText == null) {
				overflowText = props.getOverflow();
			}
			if (overflowText == null) {
				overflow = OVERFLOW_NONE;
			} else {
				final String overflowTextTL = overflowText.toLowerCase();
				if ("scroll".equals(overflowTextTL)) {
					overflow = OVERFLOW_SCROLL;
				} else if ("auto".equals(overflowTextTL)) {
					overflow = OVERFLOW_AUTO;
				} else if ("hidden".equals(overflowTextTL)) {
					overflow = OVERFLOW_HIDDEN;
				} else if ("visible".equals(overflowTextTL)) {
					overflow = OVERFLOW_VISIBLE;
				} else {
					overflow = OVERFLOW_NONE;
				}
			}
		}
		this.overflowX = overflow;
		return overflow;
	}

	@Override
	public int getOverflowY() {
		int overflow = this.overflowY;
		if (overflow != -1) {
			return overflow;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-y");
			if (overflowText == null) {
				overflowText = props.getOverflow();
			}
			if (overflowText == null) {
				overflow = OVERFLOW_NONE;
			} else {
				final String overflowTextTL = overflowText.toLowerCase();
				if ("scroll".equals(overflowTextTL)) {
					overflow = OVERFLOW_SCROLL;
				} else if ("auto".equals(overflowTextTL)) {
					overflow = OVERFLOW_AUTO;
				} else if ("hidden".equals(overflowTextTL)) {
					overflow = OVERFLOW_HIDDEN;
				} else if ("visible".equals(overflowTextTL)) {
					overflow = OVERFLOW_VISIBLE;
				} else {
					overflow = OVERFLOW_NONE;
				}
			}
		}
		this.overflowY = overflow;
		return overflow;
	}

	@Override
	public Color getOverlayColor() {
		Color c = this.iOverlayColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		final AbstractCSSProperties props = getCssProperties();
		String colorValue = props == null ? null : props.getOverlayColor();
		if (colorValue == null || colorValue.length() == 0) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				c = prs.getOverlayColor();
				this.iOverlayColor = c;
				return c;
			} else {
				colorValue = null;
			}
		}
		c = colorValue == null ? null : ColorFactory.getInstance().getColor(colorValue);
		this.iOverlayColor = c;
		return c;
	}

	@Override
	public HtmlInsets getPaddingInsets() {
		HtmlInsets mi = this.paddingInsets;
		if (mi != INVALID_INSETS) {
			return mi;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props == null) {
			mi = null;
		} else {
			mi = HtmlValues.getPaddingInsets(props, this);
			this.paddingInsets = mi;
		}
		return mi;
	}

	@Override
	public int getPosition() {
		final Integer p = this.cachedPosition;
		if (p != null) {
			return p.intValue();
		}
		final AbstractCSSProperties props = getCssProperties();
		int position;
		if (props == null) {
			position = POSITION_STATIC;
		} else {
			final String positionText = props.getPosition();
			if (positionText == null || positionText.length() == 0) {
				position = POSITION_STATIC;
			} else {
				final String positionTextTL = positionText.toLowerCase();
				if (positionTextTL.equals("absolute")) {
					position = POSITION_ABSOLUTE;
				} else if (positionTextTL.equals("static")) {
					position = POSITION_STATIC;
				} else if (positionTextTL.equals("relative")) {
					position = POSITION_RELATIVE;
				} else if (positionTextTL.equals("fixed")) {
					position = POSITION_FIXED;
				} else {
					position = POSITION_STATIC;
				}
			}
		}
		this.cachedPosition = new Integer(position);
		return position;
	}

	@Override
	public RenderState getPreviousRenderState() {
		return this.prevRenderState;
	}

	@Override
	public Color getTextBackgroundColor() {
		final Color c = this.iTextBackgroundColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		Color localColor;
		if (getDisplay() != DISPLAY_INLINE) {
			// Background painted by block.
			localColor = null;
		} else {
			final BackgroundInfo binfo = getBackgroundInfo();
			localColor = binfo == null ? null : binfo.backgroundColor;
			if (localColor == null) {
				final RenderState prs = this.prevRenderState;
				if (prs != null) {
					final Color ancestorColor = prs.getTextBackgroundColor();
					if (ancestorColor != null) {
						this.iTextBackgroundColor = ancestorColor;
						return ancestorColor;
					}
				}
			}
		}
		this.iTextBackgroundColor = localColor;
		return localColor;
	}

	@Override
	public int getTextDecorationMask() {
		int td = this.iTextDecoration;
		if (td != -1) {
			return td;
		}
		final AbstractCSSProperties props = getCssProperties();
		final String tdText = props == null ? null : props.getTextDecoration();
		if (tdText == null) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				td = prs.getTextDecorationMask();
				this.iTextDecoration = td;
				return td;
			}
		}
		td = 0;
		if (tdText != null) {
			final StringTokenizer tok = new StringTokenizer(tdText.toLowerCase(), ", \t\n\r");
			while (tok.hasMoreTokens()) {
				final String token = tok.nextToken();
				if ("none".equals(token)) {
					// continue
				} else if ("underline".equals(token)) {
					td |= StyleSheetRenderState.MASK_TEXTDECORATION_UNDERLINE;
				} else if ("line-through".equals(token)) {
					td |= StyleSheetRenderState.MASK_TEXTDECORATION_LINE_THROUGH;
				} else if ("blink".equals(token)) {
					td |= StyleSheetRenderState.MASK_TEXTDECORATION_BLINK;
				} else if ("overline".equals(token)) {
					td |= StyleSheetRenderState.MASK_TEXTDECORATION_OVERLINE;
				}
			}
		}
		this.iTextDecoration = td;
		return td;
	}

	@Override
	public int getTextIndent(int availSize) {
		// No caching for this one.
		final String tiText = getTextIndentText();
		if (tiText.length() == 0) {
			return 0;
		} else {
			return HtmlValues.getPixelSize(tiText, this, 0, availSize);
		}
	}

	@Override
	public String getTextIndentText() {
		String tiText = this.iTextIndentText;
		if (tiText != null) {
			return tiText;
		}
		final AbstractCSSProperties props = getCssProperties();
		tiText = props == null ? null : props.getTextIndent();
		if (tiText == null) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				final String parentText = prs.getTextIndentText();
				this.iTextIndentText = parentText;
				return parentText;
			} else {
				tiText = "";
			}
		}
		return tiText;
	}

	@Override
	public int getTextTransform() {
		int tt = this.iTextTransform;
		if (tt != -1) {
			return tt;
		}
		final AbstractCSSProperties props = getCssProperties();
		final String tdText = props == null ? null : props.getTextTransform();
		if (tdText == null) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				tt = prs.getTextTransform();
				this.iTextTransform = tt;
				return tt;
			}
		}
		tt = 0;
		if (tdText != null) {
			if ("none".equals(tdText)) {
				// continue
			} else if ("capitalize".equals(tdText)) {
				tt = TEXTTRANSFORM_CAPITALIZE;
			} else if ("uppercase".equals(tdText)) {
				tt = TEXTTRANSFORM_UPPERCASE;
			} else if ("lowercase".equals(tdText)) {
				tt = TEXTTRANSFORM_LOWERCASE;
			}
// TODO how the explicit "inherit" value is to be handled?
// 		Who is responsible for CSS cascading? 
//			... painting code? prevRenderState?
//			
//			else if("inherit".equals(tdText)) {
//				tt = TEXTTRANSFORM_INHERIT;								
//			}
		}
		this.iTextTransform = tt;
		return tt;
	}

	@Override
	public int getVisibility() {
		final Integer v = this.cachedVisibility;
		if (v != null) {
			return v.intValue();
		}
		final AbstractCSSProperties props = getCssProperties();
		int visibility;
		
		if (props != null && Strings.isNotBlank(props.getVisibility())) {
			switch (props.getVisibility()) {
			case "hidden":
				this.cachedVisibility = new Integer(VISIBILITY_HIDDEN);
				return VISIBILITY_HIDDEN;
			case "collapse":
				this.cachedVisibility = new Integer(VISIBILITY_COLLAPSE);
				return VISIBILITY_COLLAPSE;
			case "visible":
			default:
				return getPrevVisibility();
			}
		} else {
			return getPrevVisibility();
		}
	}
	
	private int getPrevVisibility() {
    	final RenderState prs = this.prevRenderState;
        if (prs != null) {
        	this.cachedVisibility = new Integer(prs.getVisibility());
            return prs.getVisibility();
        } else {
        	this.cachedVisibility = new Integer(VISIBILITY_VISIBLE);
			return VISIBILITY_VISIBLE;
        }
    }
	
	@Override
	public int getWhiteSpace() {
		if (RenderThreadState.getState().overrideNoWrap) {
			return WS_NOWRAP;
		}
		final Integer ws = this.iWhiteSpace;
		if (ws != null) {
			return ws.intValue();
		}
		final AbstractCSSProperties props = getCssProperties();
		final String whiteSpaceText = props == null ? null : props.getWhiteSpace();
		int wsValue;
		if (whiteSpaceText == null) {
			final RenderState prs = this.prevRenderState;
			if (prs != null) {
				wsValue = prs.getWhiteSpace();
			} else {
				wsValue = WS_NORMAL;
			}
		} else {
			final String whiteSpaceTextTL = whiteSpaceText.toLowerCase();
			if ("nowrap".equals(whiteSpaceTextTL)) {
				wsValue = WS_NOWRAP;
			} else if ("pre".equals(whiteSpaceTextTL)) {
				wsValue = WS_PRE;
			} else {
				wsValue = WS_NORMAL;
			}
		}
		this.iWhiteSpace = new Integer(wsValue);
		return wsValue;
	}

	@Override
	public final WordInfo getWordInfo(String word) {
		// Expected to be called only in the GUI (rendering) thread.
		// No synchronization necessary.
		Map<String, WordInfo> map = this.iWordInfoMap;
		if (map == null) {
			map = new HashMap<String, WordInfo>(1);
			this.iWordInfoMap = map;
		}
		WordInfo wi = (WordInfo) map.get(word);
		if (wi != null) {
			return wi;
		}
		wi = new WordInfo();
		final FontMetrics fm = getFontMetrics();
		wi.fontMetrics = fm;
		wi.ascentPlusLeading = fm.getAscent() + fm.getLeading();
		wi.descent = fm.getDescent();
		wi.height = fm.getHeight();
		wi.width = fm.stringWidth(word);
		map.put(word, wi);
		return wi;
	}

	@Override
	public int incrementCount(String counter, int nesting) {
		// Expected to be called only in the GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.incrementCount(counter, nesting);
		}
		Map<String, ArrayList<?>> counters = this.counters;
		if (counters == null) {
			counters = new HashMap<String, ArrayList<?>>(2);
			this.counters = counters;
			counters.put(counter, new ArrayList<Object>(0));
		}
		final ArrayList<Integer> counterArray = (ArrayList<Integer>) counters.get(counter);
		while (counterArray.size() <= nesting) {
			counterArray.add(null);
		}
		final Integer integer = (Integer) counterArray.get(nesting);
		final int prevValue = integer == null ? 0 : integer.intValue();
		counterArray.set(nesting, new Integer(prevValue + 1));
		return prevValue;
	}

	@Override
	public void invalidate() {
		final Map<String, WordInfo> map = this.iWordInfoMap;
		if (map != null) {
			map.clear();
		}
		this.iFont = null;
		this.iFontMetrics = null;
		this.iColor = null;
		this.iTextDecoration = -1;
		this.iBlankWidth = -1;
		this.alignXPercent = -1;
		this.iBackgroundColor = INVALID_COLOR;
		this.iTextBackgroundColor = INVALID_COLOR;
		this.iOverlayColor = INVALID_COLOR;
		this.iBackgroundInfo = INVALID_BACKGROUND_INFO;
		this.iDisplay = null;
		this.iTextIndentText = null;
		this.iWhiteSpace = null;
		this.marginInsets = INVALID_INSETS;
		this.paddingInsets = INVALID_INSETS;
		this.overflowX = -1;
		this.overflowY = -1;
		this.borderInfo = INVALID_BORDER_INFO;
		// Should NOT invalidate parent render state.
	}

	/**
	 * @return Returns the iHighlight.
	 */
	@Override
	public boolean isHighlight() {
		return this.iHighlight;
	}

	public void repaint() {
		// Dummy implementation
	}

	@Override
	public void resetCount(String counter, int nesting, int value) {
		// Expected to be called only in the GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			prs.resetCount(counter, nesting, value);
		} else {
			Map<String, ArrayList<?>> counters = this.counters;
			if (counters == null) {
				counters = new HashMap<String, ArrayList<?>>(2);
				this.counters = counters;
				counters.put(counter, new ArrayList<Object>(0));
			}
			final ArrayList<Integer> counterArray = (ArrayList<Integer>) counters.get(counter);
			while (counterArray.size() <= nesting) {
				counterArray.add(null);
			}
			counterArray.set(nesting, new Integer(value));
		}
	}

	/**
	 * @param highlight The iHighlight to set.
	 */
	@Override
	public void setHighlight(boolean highlight) {
		this.iHighlight = highlight;
	}

	@Override
	public String toString() {
		return "StyleSheetRenderState[font=" + getFont() + ",textDecoration=" + getTextDecorationMask() + "]";
	}
	
	@Override
	public int getClear() {
		if (cachedClear == null) {
			final AbstractCSSProperties props = this.getCssProperties();
			final String clearStr = props != null ? props.getClear() : "";
			if (clearStr == null) {
				return LineBreak.NONE;
			} else {
				switch (clearStr) {
				case "right":
					cachedClear = new Integer(LineBreak.RIGHT);
					break;
				case "left":
					cachedClear = new Integer(LineBreak.LEFT);
					break;
				case "both":
					cachedClear = new Integer(LineBreak.BOTH);
					break;
				default:
					cachedClear = new Integer(LineBreak.NONE);
					break;
				}
			}
		}
		return cachedClear;
	}
	
	@Override
	public String getBoxSizing() {
		final AbstractCSSProperties props = this.getCssProperties();
		String box = props != null ? props.getBoxSizing() : "content-box";
		if ("inherit".equals(box)) {
			box = this.getPreviousRenderState().getBoxSizing();
		}
		return box;
	}

    @Override
    public String getLeft() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getLeft();
    }

    @Override
    public String getTop() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getTop();
    }

    @Override
    public String getRight() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getRight();
    }

    @Override
    public String getBottom() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getBottom();
    }

}
