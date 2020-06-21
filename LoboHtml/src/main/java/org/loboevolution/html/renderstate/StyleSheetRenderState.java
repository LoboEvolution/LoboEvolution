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
package org.loboevolution.html.renderstate;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.LineBreak;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.GradientStyle;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.html.style.MarginInsets;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.info.WordInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import org.w3c.dom.css.CSS3Properties;

/**
 * <p>StyleSheetRenderState class.</p>
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class StyleSheetRenderState implements RenderState {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(StyleSheetRenderState.class.getName());
	
	private static final FontFactory FONT_FACTORY = FontFactory.getInstance();
	
	private static final Font DEFAULT_FONT = FONT_FACTORY.getFont(new FontKey());
	
	/** Constant INVALID_BACKGROUND_INFO */
	protected static final BackgroundInfo INVALID_BACKGROUND_INFO = new BackgroundInfo();
	
	/** Constant INVALID_BORDER_INFO */
	protected static final BorderInfo INVALID_BORDER_INFO = new BorderInfo();
	
	/** Constant INVALID_COLOR */
	protected static final Color INVALID_COLOR = new Color(100, 0, 100);
	
	/** Constant INVALID_INSETS */
	protected static final HtmlInsets INVALID_INSETS = new HtmlInsets();
	
	protected HtmlInsets paddingInsets = INVALID_INSETS;

	protected HtmlInsets marginInsets = INVALID_INSETS;

	protected final HTMLDocumentImpl document;
	
	protected final HTMLElementImpl element;
	
	protected BorderInfo borderInfo = INVALID_BORDER_INFO;

	protected BackgroundInfo iBackgroundInfo = INVALID_BACKGROUND_INFO;
	
	private Color iOverlayColor = INVALID_COLOR;

	private Color iTextBackgroundColor = INVALID_COLOR;
	
	private Color iBackgroundColor = INVALID_COLOR;

	private Integer cachedFloat;
	
	private Integer cachedPosition;
	
	private Integer cachedVisibility;
	
	private Integer iDisplay;

	protected Integer iWhiteSpace;

	private Integer cachedClear = null;
    
    private int iBlankWidth = -1;

	private int alignXPercent = -1;
	
	private int iTextDecoration = -1;

	private int iTextTransform = -1;
	
	protected int overflowX = -1;

	protected int overflowY = -1;

	private Map<String, ArrayList<Integer>> counters = null;
	
	private Color iColor;

	private Font iFont;

	private FontMetrics iFontMetrics;

	private boolean iHighlight;
	
	private String iTextIndentText = null;

	protected final RenderState prevRenderState;
	    
    private Optional<Cursor> cursor;

	Map<String, WordInfo> iWordInfoMap = null;

	/**
	 * <p>Constructor for StyleSheetRenderState.</p>
	 *
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	public StyleSheetRenderState(HTMLDocumentImpl document) {
		this.prevRenderState = null;
		this.element = null;
		this.document = document;
	}

	/**
	 * <p>Constructor for StyleSheetRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public StyleSheetRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		this.prevRenderState = prevRenderState;
		this.element = element;
		this.document = (HTMLDocumentImpl) element.getOwnerDocument();
	}
	
	/** {@inheritDoc} */
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

		CSSValues aling = CSSValues.get(textAlign);
		switch (aling) {
		case CENTER:
			axp = 50;
			break;
		case RIGHT:
			axp = 100;
			break;
		case INHERIT:
			axp = this.getPreviousRenderState().getAlignXPercent();
			break;
		case INITIAL:
		default:
			axp = 0;
			break;
		}
		this.alignXPercent = axp;
		return axp;
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignYPercent() {
		// This is only settable in table cells.
		// TODO: Does it work with display: table-cell?
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public Color getBackgroundColor() {
		final Color c = this.iBackgroundColor;
		if (c != INVALID_COLOR) {
			return c;
		}
		Color localColor;
		final BackgroundInfo binfo = getBackgroundInfo();
		localColor = binfo == null ? null : binfo.getBackgroundColor();
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

	/** {@inheritDoc} */
	@Override
	public BackgroundInfo getBackgroundInfo() {
		BackgroundInfo binfo = this.iBackgroundInfo;
		final AbstractCSSProperties props = getCssProperties();
		if (binfo != INVALID_BACKGROUND_INFO) {
			return binfo;
		}
		
		if (element != null && !"BODY".equals(element.getNodeName())) {
			binfo = null;
		}
		
		if (props != null) {
			final String backgroundColorText = props.getBackgroundColor();
			final String backgroundImageText = props.getBackgroundImage();
			final String backgroundRepeatText = props.getBackgroundRepeat();
			final String backgroundPositionText = props.getBackgroundPosition();
						
			if (Strings.isNotBlank(backgroundColorText)  || 
				Strings.isNotBlank(backgroundImageText)  || 
				Strings.isNotBlank(backgroundRepeatText) || 
				Strings.isNotBlank(backgroundPositionText)) {
				binfo = new BackgroundInfo();
			}
			
			if (Strings.isNotBlank(backgroundColorText)) {
				CSSValues bc = CSSValues.get(backgroundColorText);
				if(bc.equals(CSSValues.INHERIT)) {
					binfo.setBackgroundColor(this.getPreviousRenderState().getBackgroundColor());
				} else {
					binfo.setBackgroundColor(ColorFactory.getInstance().getColor(backgroundColorText));
				}
			}
			
			if (Strings.isNotBlank(backgroundRepeatText)) {
				applyBackgroundRepeat(binfo, backgroundRepeatText);
			}
			
			if (Strings.isNotBlank(backgroundPositionText)) {
				applyBackgroundPosition(binfo, backgroundPositionText);
			}
			
			if (Strings.isNotBlank(backgroundImageText)) {
				applyBackgroundImage(binfo, backgroundImageText, this.document, props);
			}
		}
		this.iBackgroundInfo = binfo;		
		return binfo;
	}

	/** {@inheritDoc} */
	@Override
	public int getBlankWidth() {
		int bw = this.iBlankWidth;
		if (bw == -1) {
			bw = getFontMetrics().charWidth(' ');
			this.iBlankWidth = bw;
		}
		return bw;
	}

	/** {@inheritDoc} */
	@Override
	public BorderInfo getBorderInfo() {
		BorderInfo binfo = this.borderInfo;
		if (binfo != INVALID_BORDER_INFO) {
			return binfo;
		}
		final AbstractCSSProperties props = getCssProperties();
		if (props != null) {
			binfo = BorderInsets.getBorderInfo(props, this);
		} else {
			binfo = null;
		}
		this.borderInfo = binfo;
		return binfo;
	}

	/**
	 * <p>getColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getColor() {
		Color c = this.iColor;
		if (c != null) {
			return c;
		}
		AbstractCSSProperties props = this.getCssProperties();
		String colorValue = props == null ? null : props.getColor();
		CSSValues color = CSSValues.get(colorValue);
		switch (color) {
		case INHERIT:
			if(this.getPreviousRenderState() != null) {
				return this.getPreviousRenderState().getColor();
			} else {
				return Color.BLACK;
			}
		case INITIAL:
			return Color.BLACK;
		default:
			if (colorValue == null || "".equals(colorValue)) {
				RenderState prs = this.prevRenderState;
				if (prs != null) {
					c = prs.getColor();
					this.iColor = c;
					return c;
				} else {
					this.iColor = Color.BLACK;
					return Color.BLACK;
				}
			} else {
				c = colorValue == null ? null : ColorFactory.getInstance().getColor(colorValue);
				this.iColor = c;
			}
			break;
		}
		return c;
	}

	/** {@inheritDoc} */
	@Override
	public int getCount(String counter, int nesting) {
		// Expected to be called only in GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.getCount(counter, nesting);
		}
		final Map<String, ArrayList<Integer>> counters = this.counters;
		if (counters == null) {
			return 0;
		}
		final ArrayList<Integer> counterArray = counters.get(counter);
		if (nesting < 0 || nesting >= counterArray.size()) {
			return 0;
		}
		final Integer integer = counterArray.get(nesting);
		return integer == null ? 0 : integer.intValue();
	}

	/**
	 * <p>getCssProperties.</p>
	 *
	 * @return a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 */
	protected final AbstractCSSProperties getCssProperties() {
		final HTMLElementImpl element = this.element;
		return element == null ? null : element.getCurrentStyle();
	}

	/**
	 * <p>getDefaultDisplay.</p>
	 *
	 * @return a int.
	 */
	protected int getDefaultDisplay() {
		return DISPLAY_INLINE;
	}

	/** {@inheritDoc} */
	@Override
	public int getDisplay() {
		final Integer d = this.iDisplay;
		if (d != null) {
			return d.intValue();
		}
		final CSS3Properties props = this.getCssProperties();
		final String displayText = props == null ? null : props.getDisplay();
		int displayInt;
		final String displayTextTL = Strings.isNotBlank(displayText) ? displayText : "";
		final CSSValues display = CSSValues.get(displayTextTL);
		switch (display) {
		case BLOCK:
			displayInt = DISPLAY_BLOCK;
			break;
		case NONE:
			displayInt = DISPLAY_NONE;
			break;
		case LIST_ITEM:
			displayInt = DISPLAY_LIST_ITEM;
			break;
		case TABLE:
			displayInt = DISPLAY_TABLE;
			break;
		case TABLE_CELL:
			displayInt = DISPLAY_TABLE_CELL;
			break;
		case TABLE_ROW:
			displayInt = DISPLAY_TABLE_ROW;
			break;
		case INLINE:
			displayInt = DISPLAY_INLINE;
			break;
		case INLINE_BLOCK:
			displayInt = DISPLAY_INLINE_BLOCK;
			break;
		case INLINE_TABLE:
			displayInt = DISPLAY_INLINE_TABLE;
			break;
		case INHERIT:
			displayInt = this.getPreviousRenderState().getDisplay();
			break;
		case INITIAL:
		default:
			displayInt = this.getDefaultDisplay();
			break;
		}
		this.iDisplay = Integer.valueOf(displayInt);
		return displayInt;
	}

	/** {@inheritDoc} */
	@Override
	public int getFloat() {
		Integer p = this.cachedFloat;
		if (p != null) {
			return p.intValue();
		}
		AbstractCSSProperties props = this.getCssProperties();
		int floatValue = 0;
		String floatText = props == null ? null : props.getFloat();
		final String floatTextTL = Strings.isBlank(floatText) ? "" : floatText;
		CSSValues flt = CSSValues.get(floatTextTL);
		switch (flt) {
		case LEFT:
			floatValue = FLOAT_LEFT;
			break;
		case RIGHT:
			floatValue = FLOAT_RIGHT;
			break;
		case NONE:
			floatValue = FLOAT_NONE;
			break;
		case INHERIT:
			floatValue = this.getPreviousRenderState().getFloat();
			break;
		case INITIAL:
		default:
			floatValue = FLOAT_NONE;
			break;
		}
		this.cachedFloat = Integer.valueOf(floatValue);
		return floatValue;
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public int getFontBase() {
		final RenderState prs = this.prevRenderState;
		return prs == null ? 3 : prs.getFontBase();
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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
			mi = MarginInsets.getMarginInsets(props, this);
		}
		this.marginInsets = mi;
		return mi;
	}

	/** {@inheritDoc} */
	@Override
	public int getOverflowX() {
		int overflow = this.overflowX;
		if (overflow != -1) {
			return overflow;
		}
		AbstractCSSProperties props = this.getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-x");
			if (overflowText == null) {
				overflowText = props.getOverflow();

				if (overflowText == null) {
					return OVERFLOW_NONE;
				}
			}
			CSSValues overx = CSSValues.get(overflowText);
			switch (overx) {
			case SCROLL:
				overflow = OVERFLOW_SCROLL;
				break;
			case AUTO:
				overflow = OVERFLOW_AUTO;
				break;
			case HIDDEN:
				overflow = OVERFLOW_HIDDEN;
				break;
			case VISIBLE:
				overflow = OVERFLOW_VISIBLE;
				break;
			case INHERIT:
				overflow = this.getPreviousRenderState().getOverflowX();
				break;
			case INITIAL:
			default:
				overflow = OVERFLOW_NONE;
				break;
			}
		}
		this.overflowX = overflow;
		return overflow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderstate.RenderState#getOverflowY()
	 */
	/** {@inheritDoc} */
	@Override
	public int getOverflowY() {
		int overflow = this.overflowY;
		if (overflow != -1) {
			return overflow;
		}
		AbstractCSSProperties props = this.getCssProperties();
		if (props == null) {
			overflow = OVERFLOW_NONE;
		} else {
			String overflowText = props.getPropertyValue("overflow-y");
			if (overflowText == null) {
				overflowText = props.getOverflow();

				if (overflowText == null) {
					return OVERFLOW_NONE;
				}
			}
			CSSValues overy = CSSValues.get(overflowText);
			switch (overy) {
			case SCROLL:
				overflow = OVERFLOW_SCROLL;
				break;
			case AUTO:
				overflow = OVERFLOW_AUTO;
				break;
			case HIDDEN:
				overflow = OVERFLOW_HIDDEN;
				break;
			case VISIBLE:
				overflow = OVERFLOW_VISIBLE;
				break;
			case INHERIT:
				overflow = this.getPreviousRenderState().getOverflowY();
				break;
			case INITIAL:
			default:
				overflow = OVERFLOW_NONE;
				break;
			}
		}
		this.overflowY = overflow;
		return overflow;
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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
			mi = MarginInsets.getPaddingInsets(props, this);
			this.paddingInsets = mi;
		}
		return mi;
	}

	/** {@inheritDoc} */
	@Override
	public int getPosition() {
		Integer p = this.cachedPosition;
		if (p != null) {
			return p.intValue();
		}
		AbstractCSSProperties props = this.getCssProperties();
		int position = 0;
		String positionText = props == null ? null : props.getPosition();
		final String positionTextTL = Strings.isBlank(positionText) ? "" : positionText;
		CSSValues pos = CSSValues.get(positionTextTL);
		switch (pos) {
		case ABSOLUTE:
			position = POSITION_ABSOLUTE;
			break;
		case RELATIVE:
			position = POSITION_RELATIVE;
			break;
		case FIXED:
			position = POSITION_FIXED;
			break;
		case INHERIT:
			position = this.getPreviousRenderState().getPosition();
			break;
		case STATIC:
		case INITIAL:
		default:
			position = POSITION_STATIC;
			break;
		}

		this.cachedPosition = Integer.valueOf(position);
		return position;
	}

	/** {@inheritDoc} */
	@Override
	public RenderState getPreviousRenderState() {
		return this.prevRenderState;
	}

	/** {@inheritDoc} */
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
			localColor = binfo == null ? null : binfo.getBackgroundColor();
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

	/** {@inheritDoc} */
	@Override
	public int getTextDecorationMask() {
		int td = this.iTextDecoration;
		if (td != -1) {
			return td;
		}
		AbstractCSSProperties props = this.getCssProperties();
		String tdText = props == null ? null : props.getTextDecoration();
		if (tdText == null) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				td = prs.getTextDecorationMask();
				this.iTextDecoration = td;
				return td;
			}
		}
		td = 0;
		if (tdText != null) {
			StringTokenizer tok = new StringTokenizer(tdText.toLowerCase(), ", \t\n\r");
			while (tok.hasMoreTokens()) {
				final String token = tok.nextToken();
				final CSSValues tkn = CSSValues.get(token);
				switch (tkn) {
				case UNDERLINE:
					td |= MASK_TEXTDECORATION_UNDERLINE;
					break;
				case LINE_THROUGH:
					td |= MASK_TEXTDECORATION_LINE_THROUGH;
					break;
				case BLINK:
					td |= MASK_TEXTDECORATION_BLINK;
					break;
				case OVERLINE:
					td |= MASK_TEXTDECORATION_OVERLINE;
					break;
				case INHERIT:
					td |= this.getPreviousRenderState().getTextDecorationMask();
					break;
				case NONE:
				case INITIAL:
				default:
					td |= MASK_TEXTDECORATION_NONE;
					break;
				}
			}
		}
		this.iTextDecoration = td;
		return td;
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public int getTextTransform() {
		int tt = this.iTextTransform;
		if (tt != -1) {
			return tt;
		}
		AbstractCSSProperties props = this.getCssProperties();
		String tdText = props == null ? null : props.getTextTransform();
		if (tdText == null) {
			RenderState prs = this.prevRenderState;
			if (prs != null) {
				tt = prs.getTextTransform();
				this.iTextTransform = tt;
				return tt;
			}
		}
		tt = 0;
		final CSSValues text = CSSValues.get(tdText);
		switch (text) {
		case CAPITALIZE:
			tt |= TEXTTRANSFORM_CAPITALIZE;
			break;
		case UPPERCASE:
			tt |= TEXTTRANSFORM_UPPERCASE;
			break;
		case LOWERCASE:
			tt |= TEXTTRANSFORM_LOWERCASE;
			break;
		case INHERIT:
			tt |= this.getPreviousRenderState().getTextTransform();
			break;
		case NONE:
		case INITIAL:
		default:
			tt |= TEXTTRANSFORM_NONE;
			break;
		}
		this.iTextTransform = tt;
		return tt;
	}

	/** {@inheritDoc} */
	@Override
	public int getVisibility() {
		Integer v = this.cachedVisibility;
		if (v != null) {
			return v.intValue();
		}
		AbstractCSSProperties props = this.getCssProperties();
		int visibility;
		String visibText = props == null ? null : props.getVisibility();
		final String visibTextTL = Strings.isBlank(visibText) ? "" : visibText;
		CSSValues visy = CSSValues.get(visibTextTL);
		switch (visy) {
		case HIDDEN:
			visibility = VISIBILITY_HIDDEN;
			break;
		case VISIBLE:
			visibility = VISIBILITY_VISIBLE;
			break;
		case COLLAPSE:
			visibility = VISIBILITY_COLLAPSE;
			break;
		case INHERIT:
			visibility = this.getPreviousRenderState().getVisibility();
			break;
		case INITIAL:
		default:
			visibility = VISIBILITY_VISIBLE;
			break;
		}
		this.cachedVisibility = Integer.valueOf(visibility);
		return visibility;
	}
	
	/** {@inheritDoc} */
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
		final String whiteSpaceTextTL = Strings.isBlank(whiteSpaceText) ? "" : whiteSpaceText;
		CSSValues white = CSSValues.get(whiteSpaceTextTL);
		switch (white) {
		case NOWRAP:
			wsValue = WS_NOWRAP;
			break;
		case INHERIT:
			wsValue = this.getPreviousRenderState().getWhiteSpace();
			break;
		case INITIAL:
		default:
			if (whiteSpaceText == null) {
				final RenderState prs = this.prevRenderState;
				if (prs != null) {
					wsValue = prs.getWhiteSpace();
				} else {
					wsValue = WS_NORMAL;
				}
			} else {
				wsValue = WS_NORMAL;
			}
			break;
		}
		this.iWhiteSpace = Integer.valueOf(wsValue);
		return wsValue;
	}

	/** {@inheritDoc} */
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
		wi.setFontMetrics(fm);
		wi.setAscentPlusLeading(fm.getAscent() + fm.getLeading());
		wi.setDescent(fm.getDescent());
		wi.setHeight(fm.getHeight());
		wi.setWidth(fm.stringWidth(word));
		map.put(word, wi);
		return wi;
	}

	/** {@inheritDoc} */
	@Override
	public int incrementCount(String counter, int nesting) {
		// Expected to be called only in the GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			return prs.incrementCount(counter, nesting);
		}
		Map<String, ArrayList<Integer>> counters = this.counters;
		if (counters == null) {
			counters = new HashMap<String, ArrayList<Integer>>();
			this.counters = counters;
			counters.put(counter, new ArrayList<Integer>());
		}
		final ArrayList<Integer> counterArray = counters.get(counter);
		while (counterArray.size() <= nesting) {
			counterArray.add(null);
		}
		final Integer integer = (Integer) counterArray.get(nesting);
		final int prevValue = (integer == null || integer == 0) ? 1 : integer.intValue();
		counterArray.set(nesting, Integer.valueOf(prevValue + 1));
		return prevValue;
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public boolean isHighlight() {
		return this.iHighlight;
	}

	/**
	 * <p>repaint.</p>
	 */
	public void repaint() {
		// Dummy implementation
	}

	/** {@inheritDoc} */
	@Override
	public void resetCount(String counter, int nesting, int value) {
		// Expected to be called only in the GUI thread.
		final RenderState prs = this.prevRenderState;
		if (prs != null) {
			prs.resetCount(counter, nesting, value);
		} else {
			Map<String, ArrayList<Integer>> counters = this.counters;
			if (counters == null) {
				counters = new HashMap<String, ArrayList<Integer>>();
				this.counters = counters;
				counters.put(counter, new ArrayList<Integer>());
			}
			final ArrayList<Integer> counterArray = (ArrayList<Integer>) counters.get(counter);
			while (counterArray.size() <= nesting) {
				counterArray.add(null);
			}
			counterArray.set(nesting, Integer.valueOf(value));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setHighlight(boolean highlight) {
		this.iHighlight = highlight;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "StyleSheetRenderState[font=" + getFont() + ",textDecoration=" + getTextDecorationMask() + "]";
	}
	
	/** {@inheritDoc} */
	@Override
	public int getClear() {
		if (cachedClear == null) {
			final AbstractCSSProperties props = this.getCssProperties();
			final String clearStr = props != null ? props.getClear() : "";
			CSSValues clear = CSSValues.get(clearStr);
			switch (clear) {
			case RIGHT:
				cachedClear = Integer.valueOf(LineBreak.RIGHT);
				break;
			case LEFT:
				cachedClear = Integer.valueOf(LineBreak.LEFT);
				break;
			default:
				cachedClear = Integer.valueOf(LineBreak.NONE);
				break;
			}
		}
		return cachedClear;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getBoxSizing() {
		AbstractCSSProperties props = this.getCssProperties();
		String boxSizing = props != null ? props.getBoxSizing() : "";
		final String visibTextTL = Strings.isBlank(boxSizing) ? "" : boxSizing;
		CSSValues box = CSSValues.get(visibTextTL);
		switch (box) {
		case INHERIT:
			return this.getPreviousRenderState().getBoxSizing();
		case INITIAL:
		case BORDER_BOX:
		case CONTENT_BOX:
		default:
			return boxSizing;
		}

	}

    /** {@inheritDoc} */
    @Override
    public String getLeft() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getLeft();
    }

    /** {@inheritDoc} */
    @Override
    public String getTop() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getTop();
    }

    /** {@inheritDoc} */
    @Override
    public String getRight() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getRight();
    }

    /** {@inheritDoc} */
    @Override
    public String getBottom() {
        final AbstractCSSProperties props = this.getCssProperties();
        return props == null ? null : props.getBottom();
    }
    
    /** {@inheritDoc} */
    @Override
    public void setCursor(Optional<Cursor> cursor) {
       this.cursor = cursor;
        
    }

	/** {@inheritDoc} */
	@Override
	public Optional<Cursor> getCursor() {

		Optional<Cursor> prevCursorOpt = Optional.empty();
		AbstractCSSProperties props = this.getCssProperties();

		if (this.cursor != null) {
			prevCursorOpt = this.cursor;
		}

		if (props == null) {
			return prevCursorOpt;
		} else {

			String cursor = props.getPropertyValue("cursor");
			CSSValues key = CSSValues.get(cursor);

			switch (key) {
			case AUTO:
			case TEXT_CSS:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				break;
			case CROSSHAIR:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				break;
			case E_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				break;
			case MOVE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				break;
			case N_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				break;
			case NE_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
				break;
			case NW_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
				break;
			case GRAB:
			case POINTER:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case S_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				break;
			case SE_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
				break;
			case SW_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
				break;
			case W_RESIZE:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				break;
			case WAIT:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				break;
			case INHERIT:
				prevCursorOpt = this.getPreviousRenderState().getCursor();
				break;
			case DEFAULT:
			case INITIAL:
			default:
				prevCursorOpt = Optional.of(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				break;
			}

			return prevCursorOpt;
		}
	}
   
   /** {@inheritDoc} */
   @Override
   public String getVerticalAlign() {
	   final AbstractCSSProperties props = this.getCssProperties();
       return props == null ? null : props.getVerticalAlign();
   }
   
   
   private void applyBackgroundPosition(BackgroundInfo binfo, String position) {
		binfo.setBackgroundXPositionAbsolute(false);
		binfo.setBackgroundYPositionAbsolute(false);
		binfo.setBackgroundXPosition(50);
		binfo.setBackgroundYPosition(50);
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
		if (binfo.getBackgroundRepeat() == BackgroundInfo.BR_REPEAT) {
			final CSSValues rep = CSSValues.get(backgroundRepeatText);
			switch (rep) {
			case REPEAT_X:
				binfo.setBackgroundRepeat(BackgroundInfo.BR_REPEAT_X);
				break;
			case REPEAT_Y:
				binfo.setBackgroundRepeat(BackgroundInfo.BR_REPEAT_Y);
				break;
			case REPEAT_NO:
				binfo.setBackgroundRepeat(BackgroundInfo.BR_NO_REPEAT);
				break;
			case INHERIT:
				BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
				if (bi != null) {
					binfo.setBackgroundRepeat(bi.getBackgroundRepeat());
				}
				break;
			case INITIAL:
			case REPEAT:
			default:
				binfo.setBackgroundRepeat(BackgroundInfo.BR_REPEAT);
				break;
			}
		}
	}

	private void applyBackgroundVerticalPosition(BackgroundInfo binfo, String yposition) {
		if (yposition.endsWith("%")) {
			binfo.setBackgroundYPositionAbsolute(false);
			try {
				binfo.setBackgroundYPosition(
						(int) Double.parseDouble(yposition.substring(0, yposition.length() - 1).trim()));
			} catch (NumberFormatException nfe) {
				binfo.setBackgroundYPosition(0);
			}
		} else {

			final CSSValues ypos = CSSValues.get(yposition);
			switch (ypos) {
			case CENTER:
				binfo.setBackgroundYPositionAbsolute(false);
				binfo.setBackgroundYPosition(50);
				break;
			case RIGHT:
				binfo.setBackgroundYPositionAbsolute(false);
				binfo.setBackgroundYPosition(100);
				break;
			case LEFT:
				binfo.setBackgroundYPositionAbsolute(false);
				binfo.setBackgroundYPosition(0);
				break;
			case BOTTOM:
				binfo.setBackgroundYPositionAbsolute(false);
				binfo.setBackgroundYPosition(100);
				break;
			case TOP:
				binfo.setBackgroundYPositionAbsolute(false);
				binfo.setBackgroundYPosition(0);
				break;
			case INHERIT:
				BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
				if (bi != null) {
					binfo.setBackgroundYPositionAbsolute(bi.isBackgroundYPositionAbsolute());
					binfo.setBackgroundYPosition(bi.getBackgroundYPosition());
				}
				break;
			case INITIAL:
			default:
				binfo.setBackgroundYPositionAbsolute(true);
				binfo.setBackgroundYPosition(HtmlValues.getPixelSize(yposition, prevRenderState, 0));
				break;
			}
		}
	}
	
	private void applyBackgroundHorizontalPositon(BackgroundInfo binfo, String xposition) {
		if (xposition.endsWith("%")) {
			binfo.setBackgroundXPositionAbsolute(false);
			try {
				binfo.setBackgroundXPosition((int) Double.parseDouble(xposition.substring(0, xposition.length() - 1).trim()));
			} catch (NumberFormatException nfe) {
				binfo.setBackgroundXPosition(0);
			}
		} else {

			final CSSValues xpos = CSSValues.get(xposition);
			switch (xpos) {
			case CENTER:
				binfo.setBackgroundXPositionAbsolute(false);
				binfo.setBackgroundXPosition(50);
				break;
			case RIGHT:
				binfo.setBackgroundXPositionAbsolute(false);
				binfo.setBackgroundXPosition(100);
				break;
			case LEFT:
				binfo.setBackgroundXPositionAbsolute(false);
				binfo.setBackgroundXPosition(0);
				break;
			case BOTTOM:
				binfo.setBackgroundYPositionAbsolute(false);
				binfo.setBackgroundYPosition(100);
				break;
			case TOP:
				binfo.setBackgroundYPositionAbsolute(false);
				binfo.setBackgroundYPosition(0);
				break;
			case INHERIT:
				BackgroundInfo bi = prevRenderState.getPreviousRenderState().getBackgroundInfo();
				if (bi != null) {
					binfo.setBackgroundXPositionAbsolute(bi.isBackgroundXPositionAbsolute());
					binfo.setBackgroundXPosition(bi.getBackgroundXPosition());
				}
				break;
			case INITIAL:
			default:
				binfo.setBackgroundXPositionAbsolute(true);
				binfo.setBackgroundXPosition(HtmlValues.getPixelSize(xposition, prevRenderState, 0));
				break;
			}
		}
	}
	
	private void applyBackgroundImage(BackgroundInfo binfo, String backgroundImageText, HTMLDocumentImpl document, AbstractCSSProperties props) {

   	if (HtmlValues.isUrl(backgroundImageText)) {
           String start = "url(";
           int startIdx = start.length() +1;
           int closingIdx = backgroundImageText.lastIndexOf(')') -1;
           String quotedUri = backgroundImageText.substring(startIdx, closingIdx);
           String[] items = {"http", "https", "file"};            
           if(Strings.containsWords(quotedUri, items)) {
               try {
                   binfo.setBackgroundImage(new URL(quotedUri));
               } catch (Exception e) {
                   binfo.setBackgroundImage(null);
               }
           } else {
           	if (quotedUri.contains(";base64,")) {
   				final String base64 = backgroundImageText.split(";base64,")[1];
   				final byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
   				quotedUri = String.valueOf(decodedBytes);
           	}
               binfo.setBackgroundImage(document.getFullURL(quotedUri));
           }
		} else if (HtmlValues.isGradient(backgroundImageText)) {
			try {
				GradientStyle style = new GradientStyle();
				BufferedImage img = style.gradientToImg(props, this, backgroundImageText);
				if (img != null) {
					File f = File.createTempFile("temp", null);
					ImageIO.write(img, "png", f);
					binfo.setBackgroundImage(f.toURI().toURL());
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
   }  
}
